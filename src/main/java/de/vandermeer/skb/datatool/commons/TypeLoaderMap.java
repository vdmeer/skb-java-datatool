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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A map of data entry types and associated loader classes.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class TypeLoaderMap {

	/** The type/loader map. */
	private final Map<DataEntryType, DataSetLoader<?>> tlMap;

	/**
	 * Returns a new type/loader map.
	 */
	public TypeLoaderMap(){
		this.tlMap = new HashMap<>();
	}

	/**
	 * Puts a new entry in the map, if all arguments are not null.
	 * @param loader associated loader object
	 * @param <E> type of the data entry and the associated loader
	 * @return self to allow chaining
	 */
	public <E extends DataEntry> TypeLoaderMap put(DataSetLoader<E> loader){
		if(loader !=null){
			this.tlMap.put(loader.getDataEntryType(), loader);
		}
		return this;
	}

	/**
	 * Returns a loader for a given type.
	 * @param type the data entry type to lookup
	 * @param <E> type of the data entry and the associated loader
	 * @return null if no loader exists (or type was null), a loader otherwise
	 */
	@SuppressWarnings("unchecked")
	public <E extends DataEntry> DataSetLoader<E> getLoader(DataEntryType type){
		return (DataSetLoader<E>) this.tlMap.get(type);
	}

	/**
	 * Returns the local type/loader map.
	 * @return type/loader map
	 */
	public Map<DataEntryType, DataSetLoader<?>> getMap(){
		return this.tlMap;
	}

	/**
	 * Returns all types with their supported targets as string to list mapping
	 * @return all types and their supported targets
	 */
	public Map<String, Set<String>> getTypes(){
		Map<String, Set<String>> ret = new TreeMap<>();
		for(DataEntryType type : this.tlMap.keySet()){
			ret.put(type.getType(), new TreeSet<>(type.getSupportedTargets().keySet()));
		}
		return ret;
	}

	/**
	 * Returns all known targets and the types that support them as a string to list mapping.
	 * @return all targets and the types that support them
	 */
	public Map<String, Set<String>> getTargets(){
		Map<String, Set<String>> ret = new TreeMap<>();
		for(DataEntryType type : this.tlMap.keySet()){
			for(String target : type.getSupportedTargets().keySet()){
				if(!ret.containsKey(target)){
					ret.put(target, new TreeSet<String>());
				}
				ret.get(target).add(type.getType());
			}
		}
		return ret;
	}
}
