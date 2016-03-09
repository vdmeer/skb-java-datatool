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

package de.vandermeer.skb.datatool.entries.date.dow;

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
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;
import de.vandermeer.skb.datatool.commons.target.AbstractDataTarget;
import de.vandermeer.skb.datatool.commons.target.StandardDataTargetDefinitions;
import de.vandermeer.skb.datatool.entries.EntryKeys;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntry;

/**
 * A single day-of-week entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class DayofweekEntry implements DataEntry {

	/** Day-of-week entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"day-of-week", "dow",
					new DataEntryType[]{
							AcronymEntry.ENTRY_TYPE
					}
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.HTML_TABLE, "de/vandermeer/skb/datatool/targets/date/dow/html-table.stg"))
	;

	/** Day-of-week schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(EntryKeys.ACRONYM, true);
				put(DayofweekKeys.NUMBER, true);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Map with linkeable data entries from other sets. */
	private LoadedTypeMap loadedTypes;

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	/**
	 * Creates a new month entry with loaded types.
	 * @param loadedTypes loaded types
	 */
	DayofweekEntry(LoadedTypeMap loadedTypes){
		this.loadedTypes = loadedTypes;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		return null;
	}

	@Override
	public void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, data, this.loadedTypes, cs);

		this.entryMap.put(EntryKeys.LOCAL_ACRONYM_LINK, DataUtilities.loadDataString(EntryKeys.ACRONYM, data));
		this.entryMap.put(CommonKeys.KEY, this.getAcronym().getShort());
	}

	/**
	 * Returns the long name of the dow.
	 * @return dow long name
	 */
	public String getName(){
		return this.getAcronym().getLong();
	}

	/**
	 * Returns the number of the dow.
	 * @return dow number
	 */
	public int getNumber(){
		return (Integer)this.entryMap.get(DayofweekKeys.NUMBER);
	}

	/**
	 * Return the short name of the dow.
	 * @return dow short name
	 */
	public String getShortName(){
		return this.getAcronym().getShort();
	}

	/**
	 * Returns dow acronym link.
	 * @return dow acronym link
	 */
	public String getAcronymLink(){
		return (String)this.entryMap.get(EntryKeys.LOCAL_ACRONYM_LINK);
	}

	/**
	 * Returns the expanded acronym.
	 * @return expanded acronym
	 */
	public AcronymEntry getAcronym(){
		return (AcronymEntry)this.entryMap.get(EntryKeys.ACRONYM);
	}

	@Override
	public String getCompareString() {
		return this.getAcronym().getShort();
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
