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

package de.vandermeer.skb.datatool.entries.geo.object;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataUtilities;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.EntryObject;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;
import de.vandermeer.skb.datatool.entries.geo.GeoKeys;
import de.vandermeer.skb.datatool.entries.geo.cities.CityEntry;
import de.vandermeer.skb.datatool.entries.geo.countries.CountryEntry;

/**
 * A special data object for geographic information (continent, country, city).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectGeo implements EntryObject {

	/** Key pointing to a geo object. */
	public static EntryKey OBJ_GEO = new AbstractEntryKey("geo", "geographic information, e.g. city or country", ObjectGeo.class, false, null);

	/** Geo object schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(ObjectGeoKeys.OBJ_GEO_CITY_NAME, false);
				put(ObjectGeoKeys.OBJ_GEO_CITY_LINK, false);
				put(ObjectGeoKeys.OBJ_GEO_COUNTRY_NAME, false);
				put(ObjectGeoKeys.OBJ_GEO_COUNTRY_LINK, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	@Override
	public void loadObject(String keyStart, Object data, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException {
		if(!(data instanceof Map)){
			throw new IllegalArgumentException("object geo - data must be a map");
		}

		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, (Map<?, ?>)data, loadedTypes, cs);

		this.entryMap.put(GeoKeys.LOCAL_GEO_CITY_LINK, DataUtilities.loadDataString(ObjectGeoKeys.OBJ_GEO_CITY_LINK, (Map<?, ?>)data));
		this.entryMap.put(GeoKeys.LOCAL_GEO_COUNTRY_LINK, DataUtilities.loadDataString(ObjectGeoKeys.OBJ_GEO_COUNTRY_LINK, (Map<?, ?>)data));

		StrBuilder msg = new StrBuilder(50);


//		this.city = Utilities.getDataObject(GeoObjectConstants.EK_OBJ_GEO_CITY, entryMap, linkMap);
//		this.cityLink = Utilities.getDataObject(GeoObjectConstants.EK_OBJ_GEO_CITY_LINK, entryMap, linkMap);
//		if(this.cityLink!=null){
//			try {
//				Object obj = Utilities.getLinkObject(this.cityLink, linkMap);
//				if(obj instanceof CityEntry){
//					CityEntry city = (CityEntry)obj;
//					this.cityExp = city.getName();
//					if(city.getCountry()!=null){
//						this.countryLink = "skb://countries/" + city.getKey();//TODO
//						this.countryExp = city.getCountryEntry().getName();
//					}
//				}
//				else{
//					msg.appendSeparator(", ").append(obj);
//				}
//			}
//			catch (URISyntaxException e) {
//				msg.appendSeparator(", ").append(e.getMessage());
//			}
//		}
//		else{
//			this.country = Utilities.getDataObject(GeoObjectConstants.EK_OBJ_GEO_COUNTRY, entryMap, linkMap);
//			this.countryLink = Utilities.getDataObject(GeoObjectConstants.EK_OBJ_GEO_COUNTRY_LINK, entryMap, linkMap);
//			if(this.countryLink!=null){
//				try {
//					Object obj = Utilities.getLinkObject(this.countryLink, linkMap);
//					if(obj instanceof CountryEntry){
//						this.countryExp = ((CountryEntry)obj).getName();
//					}
//				}
//				catch (URISyntaxException e) {
//					msg.appendSeparator(", ").append(e.getMessage());
//				}
//			}
//		}
//
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	}

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	/**
	 * Returns the name of the city.
	 * @return city name
	 */
	public String getCityName(){
		return (String)this.entryMap.get(ObjectGeoKeys.OBJ_GEO_CITY_NAME);
	}

	/**
	 * Returns the city entry.
	 * @return city entry
	 */
	public CityEntry getCity(){
		return (CityEntry)this.entryMap.get(ObjectGeoKeys.OBJ_GEO_CITY_LINK);
	}

	/**
	 * Returns the name of the country.
	 * @return country name
	 */
	public String getCountryName(){
		return (String)this.entryMap.get(ObjectGeoKeys.OBJ_GEO_COUNTRY_NAME);
	}

	/**
	 * Returns the country entry.
	 * @return country entry
	 */
	public CountryEntry getCountry(){
		return (CountryEntry)this.entryMap.get(ObjectGeoKeys.OBJ_GEO_COUNTRY_LINK);
	}

	/**
	 * Returns the SKB link of the city.
	 * @return city SKB link
	 */
	public String getCityLink(){
		return (String)this.entryMap.get(GeoKeys.LOCAL_GEO_CITY_LINK);
	}

	/**
	 * Returns the SKB link of the country.
	 * @return country SKB link
	 */
	public String getCountryLink(){
		return (String)this.entryMap.get(GeoKeys.LOCAL_GEO_COUNTRY_LINK);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
