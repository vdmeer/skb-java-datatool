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

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * Generic data entry for the data tools.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface DataEntry extends Comparable<DataEntry> {

	/**
	 * Returns the key the entry is using.
	 * @return key, should not be null
	 */
	default String getKey(){
		return (String)this.getEntryMap().get(CommonKeys.KEY);
	}

	/**
	 * Sets an entry key, which usually means reading an existing key and doing consistency checks and character transformations.
	 * @param key new key
	 */
	default void setKey(String key){
		this.getEntryMap().put(CommonKeys.KEY, key);
	}

	/**
	 * Returns a compare string for natural sorting entries.
	 * @return compare string
	 */
	String getCompareString();

	/**
	 * Tests if a given set has duplicate entries.
	 * @param set entry set to test for
	 * @return name of the duplicate entry, null if none found
	 */
	String testDuplicate(Collection<DataEntry> set);

	/**
	 * Loads the entry content using a given loader.
	 * @param keyStart string used to start a key
	 * @param data the data to load information from, usually a mapping of strings to objects
	 * @param cs core settings required for loading data
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if an SKB link is used to de-reference an entry and the URL is not formed well
	 */
	default void load(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		StrBuilder err = this.getSchema().testSchema(data);
		if(err.size()>0){
			throw new IllegalArgumentException(err.toString());
		}
		this.loadEntry(keyStart, data, cs);
		this.setKey(this.toKey(this.getKey()));
	}

	/**
	 * Local (entry specific) load operation.
	 * @param keyStart string used to start a key
	 * @param data the data to load information from, usually a mapping of strings to objects
	 * @param cs core settings required for loading data
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if an SKB link is used to de-reference an entry and the URL is not formed well
	 */
	void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException;

	/**
	 * Replaces several characters in a string to return a valid key element.
	 * @param input the input string
	 * @return a string with replaced characters (" " to "-", "." to "")
	 * @throws IllegalArgumentException if any illegal characters are in the final key
	 */
	default String toKey(String input){
		if(input==null){
			return input;
		}
		String ret = input;

		ret = StringUtils.replace(ret, " ", "-");
		ret = StringUtils.replace(ret, ".", "");

		if(ret.contains("%")){
			throw new IllegalArgumentException("key contains '%'");
		}

		return ret.toLowerCase();
	}

	/**
	 * Returns the schema for the data entry.
	 * @return data entry schema
	 */
	DataEntrySchema getSchema();

	@Override
	default int compareTo(DataEntry o) {
		if(o==null){
			return -1;
		}
		if(this.getCompareString().compareTo(o.getCompareString())==0){
			return -1;
		}
		return this.getCompareString().compareTo(o.getCompareString());
	}

	/**
	 * Returns the local entry map.
	 * @return local entry map
	 */
	Map<EntryKey, Object> getEntryMap();
}
