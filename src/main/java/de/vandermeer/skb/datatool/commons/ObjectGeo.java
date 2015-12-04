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

package de.vandermeer.skb.datatool.commons;

import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.datatool.entries.CityEntry;
import de.vandermeer.skb.datatool.entries.CountryEntry;

/**
 * A special data object for geographic information (continent, country, city).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectGeo  implements EntryObject {

	/** Geo schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.OBJECT_GEO;

	/** A city as name. */
	Object city;

	/** A city as SKB link. */
	Object cityLink;

	/** A city as expanded object from an SKB link. */
	String cityExp;

	/** A country as name. */
	Object country;

	/** A country as SKB link. */
	Object countryLink;

	/** A country as expanded object from an SKB link. */
	String countryExp;

	@Override
	public String load(Map<String, Object> entryMap, Map<DataEntryType, Map<String, Object>> linkMap) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			return msg.toString();
		}

		this.city = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_CITY, entryMap, linkMap);
		this.cityLink = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_CITY_LINK, entryMap, linkMap);
		if(this.cityLink!=null){
			try {
				Object obj = Utilities.getLinkObject(this.cityLink, linkMap);
				if(obj instanceof CityEntry){
					CityEntry city = (CityEntry)obj;
					this.cityExp = city.getName();
					if(city.getCountry()!=null){
						this.countryLink = "skb://countries/" + city.getKey();//TODO
						this.countryExp = city.getCountryEntry().getName();
					}
				}
				else{
					msg.appendSeparator(", ").append(obj);
				}
			}
			catch (URISyntaxException e) {
				msg.appendSeparator(", ").append(e.getMessage());
			}
		}
		else{
			this.country = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_COUNTRY, entryMap, linkMap);
			this.countryLink = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_COUNTRY_LINK, entryMap, linkMap);
			if(this.countryLink!=null){
				try {
					Object obj = Utilities.getLinkObject(this.countryLink, linkMap);
					if(obj instanceof CountryEntry){
						this.countryExp = ((CountryEntry)obj).getName();
					}
				}
				catch (URISyntaxException e) {
					msg.appendSeparator(", ").append(e.getMessage());
				}
			}
		}

		if(msg.size()>0){
			return msg.toString();
		}
		return null;
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	/**
	 * Returns the name of the city.
	 * @return city name
	 */
	public String getCity(){
		return (String)this.city;
	}

	/**
	 * Returns the name of the expanded city.
	 * @return expanded city
	 */
	public String getCityExp(){
		return this.cityExp;
	}

	/**
	 * Returns the name of the country.
	 * @return country name
	 */
	public String getCountry(){
		return (String)this.country;
	}

	/**
	 * Returns the name of the expanded country.
	 * @return expanded country
	 */
	public String getCountryExp(){
		return this.countryExp;
	}

	/**
	 * Returns the SKB link of the city.
	 * @return city SKB link
	 */
	public String getCityLink(){
		return (String)this.cityLink;
	}

	/**
	 * Returns the SKB link of the country.
	 * @return country SKB link
	 */
	public String getCountryLink(){
		return (String)this.countryLink;
	}
}
