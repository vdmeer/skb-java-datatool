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

import org.apache.commons.lang3.text.StrBuilder;

/**
 * Generic data entry for the data tools.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public interface DataEntry extends Comparable<DataEntry> {

	/**
	 * Returns the key the entry is using.
	 * @return key, should not be null
	 */
	default String getKey(){
		return (String)this.getEntryMap().get(CommonConstants.EK_KEY);
	}

	/**
	 * Sets an entry key, which usually means reading an existing key and doing consistency checks and character transformations.
	 * @param key new key
	 */
	default void setKey(String key){
		this.getEntryMap().put(CommonConstants.EK_KEY, key);
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
	 * @param loader a fully configured loader object
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if an SKB link is used to de-reference an entry and the URL is not formed well
	 * @throws IllegalAccessException if an entry object could not be created due to a class error (type class)
	 * @throws InstantiationException if an entry object could not be created due to a class error (type class)
	 */
	default void load(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException{
		StrBuilder err = this.getSchema().testSchema(loader.getEntryMap());
		if(err.size()>0){
			throw new IllegalArgumentException(err.toString());
		}
		this.loadEntry(loader);
		this.setKey(loader.toKey(this.getKey()));
	}

	/**
	 * Local (entry specific) load operation.
	 * @param loader a fully configured loader object
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if an SKB link is used to de-reference an entry and the URL is not formed well
	 * @throws IllegalAccessException if an entry object could not be created due to a class error (type class)
	 * @throws InstantiationException if an entry object could not be created due to a class error (type class)
	 */
	void loadEntry(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException;

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
