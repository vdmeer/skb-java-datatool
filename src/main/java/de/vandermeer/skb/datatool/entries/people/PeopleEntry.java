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

package de.vandermeer.skb.datatool.entries.people;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.CommonKeys;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataUtilities;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.target.AbstractDataTarget;
import de.vandermeer.skb.datatool.commons.target.StandardDataTargetDefinitions;

/**
 * A single people entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class PeopleEntry implements DataEntry {

	/** People entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"people", "people"
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.HTML_TABLE, "de/vandermeer/skb/datatool/targets/people/html-table.stg"))
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.TEXT_PLAIN, "de/vandermeer/skb/datatool/targets/people/text-plain.stg"))
	;

	/** People schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(PeopleKeys.PEOPLE_FIRST, true);
				put(PeopleKeys.PEOPLE_MIDDLE, false);
				put(PeopleKeys.PEOPLE_LAST, true);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/**
	 * Returns the first name of the person.
	 * @return person first name
	 */
	public String getFirstName(){
		return (String)this.entryMap.get(PeopleKeys.PEOPLE_FIRST);
	}

	/**
	 * Return the middle name of the person.
	 * @return person middle name
	 */
	public String getMiddleName(){
		return (String)this.entryMap.get(PeopleKeys.PEOPLE_MIDDLE);
	}

	/**
	 * Return the last name of the person.
	 * @return person last name
	 */
	public String getLastName(){
		return (String)this.entryMap.get(PeopleKeys.PEOPLE_LAST);
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
		return SCHEMA;
	}

	@Override
	public void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, data, cs);
		this.entryMap.put(CommonKeys.KEY, keyStart + this.getLastName() + "-" + this.getFirstName());
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
