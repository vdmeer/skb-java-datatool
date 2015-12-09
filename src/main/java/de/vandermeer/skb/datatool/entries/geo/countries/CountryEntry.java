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

package de.vandermeer.skb.datatool.entries.geo.countries;

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
import de.vandermeer.skb.datatool.entries.geo.GeoKeys;
import de.vandermeer.skb.datatool.entries.geo.continents.ContinentEntry;

/**
 * A data entry for countries.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class CountryEntry implements DataEntry {

	/** Country entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"countries", "country",
					new DataEntryType[]{
						ContinentEntry.ENTRY_TYPE
					}
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.HTML_TABLE, "de/vandermeer/skb/datatool/geo/countries/targets/html-table.stg"))
	;

	/** Country schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(GeoKeys.GEO_NAME, true);
				put(GeoKeys.GEO_CONTINENT, true);
				put(CountryKeys.GEO_COUNTRY_A2, true);
				put(CountryKeys.GEO_COUNTRY_A3, false);
				put(CountryKeys.GEO_COUNTRY_NU, false);
				put(CountryKeys.GEO_COUNTRY_E164, false);
				put(CountryKeys.GEO_COUNTRY_TLD, true);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Map with linkeable data entries from other sets. */
	private LoadedTypeMap loadedTypes;

	/**
	 * Creates a new affiliation entry with loaded types.
	 * @param loadedTypes loaded types
	 */
	CountryEntry(LoadedTypeMap loadedTypes){
		this.loadedTypes = loadedTypes;
	}

	/**
	 * Returns the country name.
	 * @return country name
	 */
	public String getName(){
		return (String)this.entryMap.get(GeoKeys.GEO_NAME);
	}

	/**
	 * Returns the country continent.
	 * @return country continent
	 */
	public ContinentEntry getContinent(){
		return (ContinentEntry)this.entryMap.get(GeoKeys.GEO_CONTINENT);
	}

	/**
	 * Returns the country continent link.
	 * @return country continent link
	 */
	public String getContinentLink(){
		return (String)this.entryMap.get(GeoKeys.LOCAL_GEO_CONTINENT_LINK);
	}

	/**
	 * Returns the ISO 2 character code.
	 * @return ISO 2 character code
	 */
	public String getIsoA2(){
		return (String)this.entryMap.get(CountryKeys.GEO_COUNTRY_A2);
	}

	/**
	 * Returns the ISO 3 character code.
	 * @return ISO 3 character code
	 */
	public String getIsoA3(){
		return (String)this.entryMap.get(CountryKeys.GEO_COUNTRY_A3);
	}

	/**
	 * Returns the ISO numeric code.
	 * @return ISO numeric code
	 */
	public Integer getIsoNumeric(){
		return (Integer)this.entryMap.get(CountryKeys.GEO_COUNTRY_NU);
	}

	/**
	 * Returns the Top Level Domain.
	 * @return top-level domain
	 */
	public String getTld(){
		return (String)this.entryMap.get(CountryKeys.GEO_COUNTRY_TLD);
	}

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, data, this.loadedTypes, cs);
		this.entryMap.put(GeoKeys.LOCAL_GEO_CONTINENT_LINK, DataUtilities.loadDataString(GeoKeys.GEO_CONTINENT, data));
		this.entryMap.put(CommonKeys.KEY, this.getTld());
	}

	@Override
	public String getCompareString() {
		return this.getTld();
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
