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
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * Base of the special data objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public interface EntryObject {

	/**
	 * Loads an entry object from a given map with tests against expected keys.
	 * @param keyStart string used to start a key
	 * @param data the data to load information from, usually a mapping of strings to objects
	 * @param loadedTypes loaded types as lookup for links
	 * @param cs core settings required for loading data
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 */
	void loadObject(String keyStart, Object data, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException;

	/**
	 * Returns the schema for the entry object.
	 * @return entry object schema
	 */
	DataEntrySchema getSchema();

	/**
	 * Loads an entry object from a given map with tests against expected keys.
	 * @param keyStart string used to start a key
	 * @param data the data to load information from, usually a mapping of strings to objects
	 * @param loadedTypes loaded types as lookup for links
	 * @param cs core settings required for loading data
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 */
	default void load(String keyStart, Object data, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException {
		StrBuilder err = this.getSchema().testSchema(data);
		if(err.size()>0){
			throw new IllegalArgumentException(err.toString());
		}
		this.loadObject(keyStart, data, loadedTypes, cs);
	}

	/**
	 * Returns the local entry map.
	 * @return local entry map
	 */
	Map<EntryKey, Object> getEntryMap();
}
