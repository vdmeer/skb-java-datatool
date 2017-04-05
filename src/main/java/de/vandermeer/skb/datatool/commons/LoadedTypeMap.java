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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Mapping of data entry types to data sets for types that have been loaded.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class LoadedTypeMap {

	/** A map of other entries the loader might use for further load operations. */
	private final Map<DataEntryType, DataSet<?>> loadedTypes;

	/**
	 * Returns a new loaded type map.
	 */
	public LoadedTypeMap(){
		this.loadedTypes = new HashMap<>();
	}

	/**
	 * Adds a new entry to the map if all arguments are not null.
	 * @param type the data entry type to put
	 * @param ds the associated loaded data set to put
	 * @return self to allow chaining
	 */
	public LoadedTypeMap put(DataEntryType type, DataSet<?> ds){
		if(type!=null && ds!=null){
			this.loadedTypes.put(type, ds);
		}
		return this;
	}

	/**
	 * Tests if the given key exists in the map
	 * @param key key to test
	 * @return true if key exists, false otherwise
	 */
	public boolean containsKey(Object key){
		return this.loadedTypes.containsKey(key);
	}

	/**
	 * Returns a data set for a given data entry type.
	 * @param key key to retrieve a data set for
	 * @return data set if key found, null otherwise
	 */
	public DataSet<?> get(Object key){
		return this.loadedTypes.get(key);
	}

	/**
	 * Returns the key set, that is all put data entry types of the map
	 * @return all data entry types
	 */
	public Set<DataEntryType> keySet(){
		return this.loadedTypes.keySet();
	}

	/**
	 * Returns the type map a data set holds.
	 * @param type type to load the data set for
	 * @return type map of the data set for the type, null if not available
	 */
	public Map<String, ?> getTypeMap(DataEntryType type){
		if(this.loadedTypes.containsKey(type)){
			DataSet<?> ds = this.loadedTypes.get(type);
			if(ds!=null){
				return ds.getMap();
			}
		}
		return null;
	}

	/**
	 * Returns the size of the entry map of the given type
	 * @param type type to load the data set for
	 * @return size of the entry map of the associated data set, -1 if none found
	 */
	public int getTypeEntrySize(DataEntryType type){
		if(this.loadedTypes.containsKey(type)){
			DataSet<?> ds = this.loadedTypes.get(type);
			if(ds!=null){
				if(ds.getEntries()!=null){
					return ds.getEntries().size();
				}
			}
		}
		return -1;
	}
}
