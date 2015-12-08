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

package de.vandermeer.skb.datatool.entries.geo.cities;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.CommonConstants;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.entries.geo.GeoConstants;
import de.vandermeer.skb.datatool.entries.geo.countries.CountryEntry;
import de.vandermeer.skb.datatool.entries.links.object.ObjectLinks;
import de.vandermeer.skb.datatool.target.AbstractDataTarget;
import de.vandermeer.skb.datatool.target.StandardDataTargetDefinitions;

/**
 * A data entry for a city.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class CityEntry implements DataEntry {

	/** City entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"cities", "city",
					new DataEntryType[]{
						CountryEntry.ENTRY_TYPE
					}
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.HTML_TABLE, "de/vandermeer/skb/datatool/cities/targets/html-table.stg"))
	;

	/** City schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(GeoConstants.EK_GEO_NAME, true);
				put(CommonConstants.EK_KEY, false);
				put(GeoConstants.EK_GEO_COUNTRY, true);
				put(CityKeys.GEO_CITY_IATA, false);
				put(CityKeys.GEO_CITY_ICAO, false);
				put(CityKeys.GEO_CITY_WAC, false);
				put(CityKeys.GEO_CITY_COUNTY, false);
				put(CityKeys.GEO_CITY_REGION, false);
				put(CityKeys.GEO_CITY_STATE, false);
				put(ObjectLinks.OBJ_LINKS, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	/**
	 * Returns the city name.
	 * @return city name
	 */
	public String getName(){
		return (String)this.entryMap.get(GeoConstants.EK_GEO_NAME);
	}

	/**
	 * Returns the city's country.
	 * @return ccity's country
	 */
	public CountryEntry getCountry(){
		return (CountryEntry)this.entryMap.get(GeoConstants.EK_GEO_COUNTRY);
	}

	/**
	 * Returns the city's country link.
	 * @return ccity's country link
	 */
	public String getCountryLink(){
		return (String)this.entryMap.get(GeoConstants.EKLOCAL_GEO_COUNTRY_LINK);
	}

	/**
	 * Returns the links associated with the acronym.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.entryMap.get(ObjectLinks.OBJ_LINKS);
	}

	/**
	 * Returns the city's IATA code
	 * @return city IATA code
	 */
	public String getIata(){
		return (String)this.entryMap.get(CityKeys.GEO_CITY_IATA);
	}

	/**
	 * Returns the city's ICAO code
	 * @return city ICAO code
	 */
	public String getIcao(){
		return (String)this.entryMap.get(CityKeys.GEO_CITY_ICAO);
	}

	/**
	 * Returns the city's WAC code
	 * @return city WAC code
	 */
	public String getWac(){
		return (String)this.entryMap.get(CityKeys.GEO_CITY_WAC);
	}

	/**
	 * Returns the city's region
	 * @return city region
	 */
	public String getRegion(){
		return (String)this.entryMap.get(CityKeys.GEO_CITY_REGION);
	}

	/**
	 * Returns the city's state
	 * @return city state
	 */
	public String getState(){
		return (String)this.entryMap.get(CityKeys.GEO_CITY_STATE);
	}

	/**
	 * Returns the city's county
	 * @return city county
	 */
	public String getCounty(){
		return (String)this.entryMap.get(CityKeys.GEO_CITY_COUNTY);
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadEntry(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException {
		this.entryMap = loader.loadEntry(this.getSchema());
		this.entryMap.put(GeoConstants.EKLOCAL_GEO_COUNTRY_LINK, loader.loadDataString(GeoConstants.EK_GEO_COUNTRY));

		if(this.getKey()!=null){
			this.entryMap.put(CommonConstants.EK_KEY, loader.getKeyStart() + this.getKey());
		}
		else if(this.getName()!=null){
			this.entryMap.put(CommonConstants.EK_KEY, loader.getKeyStart() + loader.loadDataString(GeoConstants.EK_GEO_NAME));
		}
		else{
			throw new IllegalArgumentException("could not generate key, no key or name given");
		}
	}

	@Override
	public String getCompareString() {
		return this.getName();
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
