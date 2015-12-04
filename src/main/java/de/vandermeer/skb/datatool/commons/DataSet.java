/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.skb.datatool.commons;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.info.FileSource;

/**
 * Generic data set for the data tools.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class DataSet<E extends DataEntry> {

	/** The map of entries. */
	Map<String, E> entries;

	/** Number of read files. */
	int files;

	/** Class of the data entry implementation. */
	Class<E> clazz;

	/**
	 * Reference key map.
	 * This map is used for key auto-generation where the key is a reference (SKB link) to another data entry.
	 * This happens for instance when the name of an affiliation is given as an acronym.
	 * Here, a set of acronyms with a pair of sort and long strings can be set.
	 */
	Map<String, Pair<String, String>> refKeyMap;

	/** Separator character for auto-generated keys. */
	char keySeparator;

	/** Character and encoding translator. */
	Translator translator;

	/** Characters excluded from translation. */
	String[] excluded;

	/** Name of the calling application. */
	String appName;

	/**
	 * Returns a new, empty data set.
	 */
	DataSet(Class<E> clazz){
		this.entries = new HashMap<>();
		this.files = 0;
		this.clazz = clazz;
	}

	/**
	 * Returns the path common to all files in the given file list.
	 * The rest (after this common path) will be used for auto-key-generation
	 * @param fsl list of data files
	 * @return common path of all files in the list
	 */
	String calcCommonPath(List<FileSource> fsl){
		//get shortest absolute path, everything else is part of the key
		Set<String> paths = new HashSet<>();
		for(FileSource fs : fsl){
			paths.add(fs.getAbsolutePath());
		}
		String ret = StringUtils.getCommonPrefix(paths.toArray(new String[]{}));
		if(ret.endsWith(File.separator)){
			ret = StringUtils.substringBeforeLast(ret, File.separator);
		}
		return ret;
	}

	/**
	 * Returns the start of a key for key auto-generation
	 * @param fs the file name
	 * @param commonPath the common path of all file names
	 * @return start of the key
	 */
	String calcKeyStart(FileSource fs, String commonPath){
		//remove common path
		String ret = StringUtils.substringAfterLast(fs.getAbsoluteName(), commonPath + File.separator);

		//replace all path sepataros with key separators
		ret = StringUtils.replaceChars(ret, File.separatorChar, this.keySeparator);

		//remove the last dot (".json")
		ret = StringUtils.substringBeforeLast(ret, ".");
		//remove the last dot (".entry") - the entry file extension
		ret = StringUtils.substringBeforeLast(ret, ".");

		//return the key plus a final separator
		return ret + this.keySeparator;
	}

	/**
	 * Loads a data set from file system.
	 * @param fsl list of files to load data from
	 * @param fileExt the file extension used (translated to "." + fileExt + ".json"), empty if none used
	 * @return 0 on success, larger than zero on JSON parsing error (number of found errors)
	 */
	public int load(List<FileSource> fsl, String fileExt){
		int ret = 0;
		String commonPath = this.calcCommonPath(fsl);

		for(FileSource fs : fsl){
			String keyStart = this.calcKeyStart(fs, commonPath);
			ObjectMapper om = new ObjectMapper();
			om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			try{
				List<Map<String, Object>> jsonList = om.readValue(fs.asFile(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});
				for(Map<String, Object> entryMap : jsonList){
					E entry = this.clazz.newInstance();
					entry.setRefKeyMap(this.refKeyMap);
					entry.load(entryMap, keyStart, this.keySeparator, this.translator);

					if(StringUtils.isEmpty(entry.getKey())){
						Skb_Console.conError("{}: empty key found in file <{}>", new Object[]{this.appName, fs.getAbsoluteName()});
						continue;
					}
					else if(entry.getKey().contains("#dummy")){
						continue;
					}

					@SuppressWarnings("unchecked")
					String dup = entry.testDuplicate((Collection<DataEntry>) this.entries.values());
					if(this.entries.containsKey(entry.getKey())){
						Skb_Console.conError("{}: duplicate key <{}> found in file <{}>", new Object[]{this.appName, entry.getKey(), fs.getAbsoluteName()});
					}
					else if(dup!=null){
						Skb_Console.conError("{}: entry already in map: k1 <{}> <> k2 <{}> found in file <{}>", new Object[]{this.appName, dup, entry.getKey(), fs.getAbsoluteName()});
					}
					else{
						if(this.excluded==null || (!ArrayUtils.contains(this.excluded, entry.getCompareString()))){
							this.entries.put(entry.getKey(), entry);
						}
					}
				}
				this.files++;
			}
			catch(IllegalArgumentException iaex){
				Skb_Console.conError("{}: problem creating entry: <{}> in file <{}>", new Object[]{this.appName, iaex.getMessage(), fs.getAbsoluteName()});
			}
			catch(NullPointerException npe){
				npe.printStackTrace();
			}
			catch(Exception ex){
				Skb_Console.conError("reading acronym from JSON failed with exception <{}>, cause <{}> and message <{}> in file <{}>", new Object[]{ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage(), fs.getAbsoluteName()});
				ret++;
			}
		}

		return ret;
	}

	/**
	 * Returns the number of read files.
	 * @return number of files read
	 */
	public int getFileNumber(){
		return this.files;
	}

	/**
	 * Returns loaded data set.
	 * @return loaded data set, empty if none loaded or found
	 */
	public TreeSet<E> getEntries(){
		return new TreeSet<E>(this.entries.values());
	}

	/**
	 * Returns the loaded data map.
	 * @return loaded data map, empty if none loaded or found
	 */
	public Map<String, E> getMap(){
		return this.entries;
	}
}