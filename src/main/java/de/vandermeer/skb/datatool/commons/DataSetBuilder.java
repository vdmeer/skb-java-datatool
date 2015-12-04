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

package de.vandermeer.skb.datatool.commons;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileSourceList;

/**
 * Builds data sets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class DataSetBuilder {

	/** Separator character for auto-generated keys. */
	char keySeparator = ':';

	/** Character and encoding translator. */
	Translator translator;

	/** Name of the calling application. */
	String appName;

	/** Start directory for loading files. */
	String directory;

	/** Map with linkeable data entries from other sets. */
	final Map<DataEntryType, Map<String, Object>> linkMap;

	/**
	 * Returns a new data set builder.
	 */
	public DataSetBuilder(){
		this.linkMap = new HashMap<>();
	}

	/**
	 * Sets the key separator.
	 * @param sep new key separator
	 * @return self to allow chaining
	 */
	public DataSetBuilder setKeySeparator(char sep){
		this.keySeparator = sep;
		return this;
	}

	/**
	 * Sets a character/string translator
	 * @param translator new character / string translator
	 * @return self to allow chaining
	 */
	public DataSetBuilder setTranslator(Translator translator){
		this.translator = translator;
		return this;
	}

	/**
	 * Sets the name of the calling application
	 * @param appName application name
	 * @return self to allow chaining
	 */
	public DataSetBuilder setAppName(String appName){
		this.appName = appName;
		return this;
	}

	/**
	 * Sets the directory to read files from (recursively)
	 * @param directory read directory
	 * @return self to allow chaining
	 */
	public DataSetBuilder setDirectory(String directory){
		this.directory = directory;
		return this;
	}

	/**
	 * Puts a new link map into the builders overall link map.
	 * @param type data entry type (used as key)
	 * @param links actual map
	 */
	@SuppressWarnings("unchecked")
	public void putLinkMap(DataEntryType type, DataSet<?> ds){
		if(ds!=null){
			this.linkMap.put(type, (Map<String, Object>) ds.getMap());
		}
	}

	/**
	 * Tests if the link map contains a given key
	 * @param type key to test for
	 * @return true if the key exists, false otherwise
	 */
	public boolean linkMapContainsKey(DataEntryType type){
		return this.linkMap.containsKey(type);
	}

	/**
	 * Loads a data set with entries, does consistency checks, marks errors, translates encodings.
	 * The local link map will be cleared.
	 * @param entryType the data entry type
	 * @return a fully loaded, checked data set on success, null on error (errors are logged)
	 */
	public <E extends DataEntry> DataSet<E> build(DataEntryType entryType){
		return this.build(entryType, null);
	}

	/**
	 * Loads a data set with entries, does consistency checks, marks errors, translates encodings.
	 * The local link map will be cleared.
	 * @param entryType the data entry type
	 * @param excluded a set of characters excluded from translations, null or empty if not applicable
	 * @return a fully loaded, checked data set on success, null on error (errors are logged)
	 */
	public <E extends DataEntry> DataSet<E> build(DataEntryType entryType, String[] excluded){
		IOFileFilter fileFilter = new WildcardFileFilter(new String[]{
				"*." + entryType.getInputFileExtension() + ".json"
		});
		DirectoryLoader dl = new CommonsDirectoryWalker(directory, DirectoryFileFilter.INSTANCE, fileFilter);
		if(dl.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading files from directory <{}>\n{}", new Object[]{this.appName, this.directory, dl.getLoadErrors().render()});
			return null;
		}

		DataSet<E> ds = new DataSet<>(entryType.getTypeClass());
		ds.keySeparator = this.keySeparator;
		ds.appName = this.appName;
		ds.excluded = excluded;
		ds.translator = this.translator;
		ds.linkMap.putAll(this.linkMap);

		FileSourceList fsl = dl.load();
		ds.load(fsl.getSource(), entryType.getInputFileExtension());

		return ds;
	}

	/**
	 * Clears the link map, removes all entries.
	 */
	public void cleanLinkMap(){
		this.linkMap.clear();
	}
}