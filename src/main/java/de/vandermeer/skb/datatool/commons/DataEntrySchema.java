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

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * A data entry schema.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface DataEntrySchema {

	/**
	 * Returns the key map of the data entry.
	 * The key map contains keys and a flag for the requirement level (true for mandatory, false for optional).
	 * @return data entry key map
	 */
	Map<EntryKey, Boolean> getKeyMap();

	/**
	 * Returns a string representation of all keys of the data entry.
	 * @return set of all keys
	 */
	Set<String> getKeySet();

	/**
	 * Tests the map against the data entry schema (programmatic).
	 * @param data original data, should be a mapping of strings to objects
	 * @return empty on success, string builder with explanations on error
	 */
	default StrBuilder testSchema(Object data){
		StrBuilder ret = new StrBuilder();
		if(!(data instanceof Map)){
			ret.append("test schema: input data not of type map, cannot test schema");
			return ret;
		}

		Map<?, ?> map = (Map<?, ?>)data;
		for(Entry<EntryKey, Boolean> e : this.getKeyMap().entrySet()){
			if(e.getValue()==true && !map.containsKey(e.getKey().getKey())){
				ret.append("missing mandatory entry key <").append(e.getKey().getKey()).append(">").appendNewLine();
			}
			else if(e.getValue()==true && StringUtils.isEmpty(map.get(e.getKey().getKey()).toString())){
				ret.append("empty mandatory entry key <").append(e.getKey().getKey()).append(">").appendNewLine();
			}
		}
		for(Object o : map.keySet()){
			if(!this.getKeySet().contains(o.toString())){
				ret.append("unknown entry key <").append(o).append(">").appendNewLine();
			}
		}
		return ret;
	}
}
