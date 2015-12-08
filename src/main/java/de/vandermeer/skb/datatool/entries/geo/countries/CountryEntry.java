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
import de.vandermeer.skb.datatool.commons.CommonConstants;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.entries.geo.GeoConstants;
import de.vandermeer.skb.datatool.entries.geo.continents.ContinentEntry;

/**
 * A data entry for countries.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class CountryEntry implements DataEntry {

	/** Country schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(GeoConstants.EK_GEO_NAME, true);
				put(GeoConstants.EK_GEO_CONTINENT, true);
				put(CountryKeys.GEO_COUNTRY_A2, true);
				put(CountryKeys.GEO_COUNTRY_A3, false);
				put(CountryKeys.GEO_COUNTRY_NU, false);
				put(CountryKeys.GEO_COUNTRY_E164, false);
				put(CountryKeys.GEO_COUNTRY_TLD, true);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/**
	 * Returns the country name.
	 * @return country name
	 */
	public String getName(){
		return (String)this.entryMap.get(GeoConstants.EK_GEO_NAME);
	}

	/**
	 * Returns the country continent.
	 * @return country continent
	 */
	public ContinentEntry getContinent(){
		return (ContinentEntry)this.entryMap.get(GeoConstants.EK_GEO_CONTINENT);
	}

	/**
	 * Returns the country continent link.
	 * @return country continent link
	 */
	public String getContinentLink(){
		return (String)this.entryMap.get(GeoConstants.EKLOCAL_GEO_CONTINENT_LINK);
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
	public void loadEntry(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException {
		this.entryMap = loader.loadEntry(this.getSchema());
		this.entryMap.put(GeoConstants.EKLOCAL_GEO_CONTINENT_LINK, loader.loadDataString(GeoConstants.EK_GEO_CONTINENT));
		this.entryMap.put(CommonConstants.EK_KEY, this.getTld());
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
