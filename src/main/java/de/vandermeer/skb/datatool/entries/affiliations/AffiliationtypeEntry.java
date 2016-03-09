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

package de.vandermeer.skb.datatool.entries.affiliations;

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
 * A single affiliation type entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class AffiliationtypeEntry implements DataEntry {

	/** Affiliation types entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"affiliation-types", "aff-types"
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.HTML_TABLE, "de/vandermeer/skb/datatool/targets/affiliation-types/html-table.stg"))
	;

	/** Affiliation types schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(AffiliationKeys.AFF_LONG, false);
				put(AffiliationKeys.AFF_SHORT, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/**
	 * Returns the long name of the affiliation type.
	 * @return affiliation type long name
	 */
	public String getLong(){
		return (String)this.entryMap.get(AffiliationKeys.AFF_LONG);
	}

	/**
	 * Return the short name of the affiliation type.
	 * @return affiliation type short name
	 */
	public String getShort(){
		return (String)this.entryMap.get(AffiliationKeys.AFF_SHORT);
	}

	@Override
	public String getCompareString() {
		return this.getShort();
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, data, cs);
		this.entryMap.put(CommonKeys.KEY, DataUtilities.loadDataString(AffiliationKeys.AFF_SHORT, data));
	}

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
