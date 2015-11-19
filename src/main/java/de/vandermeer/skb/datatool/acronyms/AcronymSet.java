/* Copyright 2015 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.datatool.acronyms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileSource;
import de.vandermeer.skb.base.info.FileSourceList;

/**
 * Set of acronyms wit additional (mostly statistical) information.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AcronymSet {

	/** Application name for printouts and logs. */
	String appName;

	/** The set of acronyms. */
	TreeSet<AcronymEntry> acronyms;

	/** Longest found acronym (short version being used here). */
	String maxShort;

	/** The keys found in the acronym definitions. */
	TreeSet<String> keys;

	/** Number of read files. */
	int files;

	/** Key separator. */
	char keySep;

	public AcronymSet(String appName, char keySep){
		this.appName = appName;
		this.acronyms = new TreeSet<>();
		this.maxShort = "";
		this.keys = new TreeSet<>();
		this.files = 0;
		this.keySep = keySep;
	}

	/**
	 * Loads acronyms from file system.
	 * @param dl the directory loader with files to load acronyms from
	 * @return 0 on success, larger than zero on JSON parsing error (number of found errors)
	 */
	public int load(DirectoryLoader dl){
		return this.load(dl, null);
	}

	/**
	 * Loads acronyms from file system.
	 * @param dl the directory loader with files to load acronyms from
	 * @param translationTarget target for encoding translations
	 * @return 0 on success, larger than zero on JSON parsing error (number of found errors)
	 */
	public int load(DirectoryLoader dl, TranslatorFactory.Target translationTarget){
		int ret = 0;

		String pathRemove ="";
		FileSourceList fsl = dl.load();

		//get shortest absolute path, everything else is part of the key
		for(FileSource fs : fsl.getSource()){
			if(pathRemove.length()==0){
				pathRemove = fs.getAbsolutePath();
			}
			if(pathRemove.length()>fs.getAbsolutePath().length()){
				pathRemove = fs.getAbsolutePath();
			}
		}

		for(FileSource fs : fsl.getSource()){
			String keyStart = StringUtils.substringAfterLast(fs.getAbsoluteName(), pathRemove + File.separator);
			keyStart = StringUtils.replaceChars(keyStart, File.separatorChar, this.keySep);
			keyStart = StringUtils.replace(keyStart, ".json", Character.toString(this.keySep));
			this.keys.add(keyStart);

			ObjectMapper om = new ObjectMapper();
			om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			try{
				List<Map<String, Object>> acrJson = om.readValue(fs.asFile(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});
				for(Map<String, Object> entry : acrJson){
					AcronymEntry ae = new AcronymEntry(keyStart, entry, this.keySep, translationTarget);
					if(!StringUtils.startsWith(ae.getShort(), "#dummy")){
						this.acronyms.add(ae);
						if(ae.getShort().length()>this.maxShort.length()){
							this.maxShort = ae.getShort();
						}
					}
				}
				this.files++;
			}
			catch(Exception ex){
				Skb_Console.conError("reading acronym from JSON failed with exception <{}>, cause <{}> and message <{}> in file <{}>", new Object[]{ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage(), fs.getAbsoluteName()});
				ret++;
			}
		}
		return ret;
	}

	/**
	 * Returns the longest acronym found in the given set.
	 * @return longest acronym, that is the longest string of a short version of an acronym, empty if none found
	 */
	public String getLongestAcronym(){
		return this.maxShort;
	}

	/**
	 * Returns keys found in the acronym definitions.
	 * @return set of keys, empty if none loaded or found
	 */
	public TreeSet<String> getKeys(){
		return this.keys;
	}

	/**
	 * Returns the number of read files.
	 * @return number of files read
	 */
	public int getFileNumber(){
		return this.files;
	}

	/**
	 * Returns acronyms found in the acronym definitions.
	 * @return set of acronyms, empty if none loaded or found
	 */
	public TreeSet<AcronymEntry> getAcronyms(){
		return this.acronyms;
	}
}
