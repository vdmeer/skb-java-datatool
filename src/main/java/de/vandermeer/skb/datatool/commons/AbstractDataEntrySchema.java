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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation of a data entry schema.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class AbstractDataEntrySchema implements DataEntrySchema {

	/** Entry key map, all understood keys. */
	private final Map<EntryKey, Boolean> keyMap;

	/** Entry key set. */
	private final Set<String> keySet;

	/**
	 * Returns a new data entry schema.
	 * @param keyMap map of keys for the schema
	 */
	public AbstractDataEntrySchema(Map<EntryKey, Boolean> keyMap) {
		this.keyMap = new HashMap<>(keyMap);

		this.keySet = new HashSet<>();
		for(EntryKey key : keyMap.keySet()){
			this.keySet.add(key.getKey());
		}
	}

	@Override
	public Map<EntryKey, Boolean> getKeyMap() {
		return this.keyMap;
	}

	@Override
	public Set<String> getKeySet() {
		return this.keySet;
	}

}
