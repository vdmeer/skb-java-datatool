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

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.vandermeer.skb.datatool.entries.AffiliationEntry;

/**
 * A special data objects for affiliation lists as SKB links.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectAffiliations implements EntryObject {

	/** Map of keys to affiliation entries, the key is the original link the entry the expanded entry. */
	final private Map<String, AffiliationEntry> affiliationMap;

	public ObjectAffiliations(){
		this.affiliationMap = new HashMap<>();
	}

	@Override
	public DataEntrySchema getSchema(){
		return null;//TODO
	}

	@Override
	public void loadObject(DataLoader loader) throws URISyntaxException {
		Object obj = loader.getEntryMap().get(StandardEntryKeys.OBJ_AFF_LINKS.getKey());
		if(!(obj instanceof List)){
			throw new IllegalArgumentException("expecting list, found <" + obj.getClass().getSimpleName() + ">");
		}

		for(Object l : (List<?>)obj){
			this.affiliationMap.put(l.toString(), (AffiliationEntry)loader.loadLink(l.toString()));
		}
	}

	/**
	 * Returns the created affiliation map.
	 * @return affiliation map, empty if no entries have been produced
	 */
	public Map<String, AffiliationEntry> getAffiliationMap(){
		return this.affiliationMap;
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return null;
	}
}
