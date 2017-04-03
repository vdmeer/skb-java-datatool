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

package de.vandermeer.skb.datatool.entries.affiliations.object;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataUtilities;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.EntryObject;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;

/**
 * A special data objects for affiliation lists as SKB links.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectAffiliations implements EntryObject {

	public static EntryKey OBJ_AFF_LIST = new AbstractEntryKey("list", "a list of SKB links to affiliations", String.class, false, "skb://affiliations");

	/** Affiliation object object schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(OBJ_AFF_LIST, true);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	@Override
	public void loadObject(String keyStart, Object data, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException {
		if(!(data instanceof Map)){
			throw new IllegalArgumentException("object links - data must be a map");
		}
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, (Map<?, ?>)data, loadedTypes, cs);

//		Object obj = loader.getEntryMap().get(OBJ_AFF_LIST.getKey());
//		if(!(obj instanceof List)){
//			throw new IllegalArgumentException("expecting list, found <" + obj.getClass().getSimpleName() + ">");
//		}
//
//		for(Object l : (List<?>)obj){
//			if(l!=null){
//				this.affiliationMap.put(l.toString(), (AffiliationEntry)DataUtilities.loadLink(l, loader.getCs()));
//			}
//		}
	}

	public List<?> getList(){
System.err.println("@@: " + this.entryMap.get(OBJ_AFF_LIST));
		return (List<?>)this.entryMap.get(OBJ_AFF_LIST);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
