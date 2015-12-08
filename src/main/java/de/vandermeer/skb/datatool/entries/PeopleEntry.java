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

package de.vandermeer.skb.datatool.entries;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.ObjectAffiliations;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;

/**
 * A single people entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class PeopleEntry implements DataEntry {

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Affiliation schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.PEOPLE;

	/**
	 * Returns the first name of the person.
	 * @return person first name
	 */
	public String getFirstName(){
		return (String)this.entryMap.get(StandardEntryKeys.PEOPLE_FIRST);
	}

	/**
	 * Return the middle name of the person.
	 * @return person middle name
	 */
	public String getMiddleName(){
		return (String)this.entryMap.get(StandardEntryKeys.PEOPLE_MIDDLE);
	}

	/**
	 * Return the last name of the person.
	 * @return person last name
	 */
	public String getLastName(){
		return (String)this.entryMap.get(StandardEntryKeys.PEOPLE_LAST);
	}

	/**
	 * Return the last affiliation entries for the person.
	 * @return person last name
	 */
	public Map<String, AffiliationEntry> getAffiliations(){
		return ((ObjectAffiliations)this.entryMap.get(StandardEntryKeys.OBJ_AFF_LINKS)).getAffiliationMap();
	}

	@Override
	public String getCompareString() {
		return this.getLastName();
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public void loadEntry(DataLoader loader) throws URISyntaxException {
		this.entryMap = loader.loadEntry(this.schema);
		this.entryMap.put(StandardEntryKeys.KEY, loader.getKeyStart() + this.getLastName() + "-" + this.getFirstName());
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
