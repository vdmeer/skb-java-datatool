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

package de.vandermeer.skb.datatool.entries;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.ObjectLinks;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A data entry for a city.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class CityEntry implements DataEntry {

	/** The key for the city. */
	String key;

	/** City name. */
	Object name;

	/** City's country. */
	Object country;

	/** A de-referenced continent entry. */
	CountryEntry countryEntry;

	/** Links for the city. */
	Object links;

	/** IATA code of the city. */
	Object iata;

	/** ICAO code of the city. */
	Object icao;

	/** WAC code of the city. */
	Object wac;

	/** Region of the city. */
	Object region;

	/** State of the city. */
	Object state;

	/** County of the city. */
	Object county;

	/** City schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.GEO_CITIES;

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	/**
	 * Returns the country name.
	 * @return country name
	 */
	public String getName(){
		return (String)this.name;
	}

	/**
	 * Returns the city's country.
	 * @return ccity's country
	 */
	public String getCountry(){
		return (String)this.country;
	}

	/**
	 * Returns the city's country entry.
	 * @return ccity's country entry
	 */
	public CountryEntry getCountryEntry(){
		return this.countryEntry;
	}

	/**
	 * Returns the links associated with the acronym.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.links;
	}

	/**
	 * Returns the city's IATA code
	 * @return city IATA code
	 */
	public String getIata(){
		return (String)this.iata;
	}

	/**
	 * Returns the city's ICAO code
	 * @return city ICAO code
	 */
	public String getIcao(){
		return (String)this.icao;
	}

	/**
	 * Returns the city's WAC code
	 * @return city WAC code
	 */
	public String getWac(){
		return (String)this.wac;
	}

	/**
	 * Returns the city's region
	 * @return city region
	 */
	public String getRegion(){
		return (String)this.region;
	}

	/**
	 * Returns the city's state
	 * @return city state
	 */
	public String getState(){
		return (String)this.state;
	}

	/**
	 * Returns the city's county
	 * @return city county
	 */
	public String getCounty(){
		return (String)this.county;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, Translator translator, Map<DataEntryType, Map<String, Object>> linkMap) throws URISyntaxException {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	
		msg = new StrBuilder(50);

		this.name = Utilities.getDataObject(StandardEntryKeys.GEO_NAME, entryMap, translator, linkMap);

		this.country = Utilities.getDataObject(StandardEntryKeys.GEO_COUNTRY, entryMap, linkMap);
		Object obj = Utilities.getLinkObject(this.country, linkMap);
		if(obj instanceof CountryEntry){
			this.countryEntry = (CountryEntry)obj;
		}
		else{
			msg.appendSeparator(", ").append(obj);
		}

		this.iata = Utilities.getDataObject(StandardEntryKeys.GEO_CITY_IATA, entryMap, translator, linkMap);
		this.icao = Utilities.getDataObject(StandardEntryKeys.GEO_CITY_ICAO, entryMap, translator, linkMap);
		this.wac = Utilities.getDataObject(StandardEntryKeys.GEO_CITY_WAC, entryMap, translator, linkMap);
		this.county = Utilities.getDataObject(StandardEntryKeys.GEO_CITY_COUNTY, entryMap, translator, linkMap);
		this.region = Utilities.getDataObject(StandardEntryKeys.GEO_CITY_REGION, entryMap, translator, linkMap);
		this.state = Utilities.getDataObject(StandardEntryKeys.GEO_CITY_STATE, entryMap, translator, linkMap);

		Object k = Utilities.getDataObject(StandardEntryKeys.KEY, entryMap, linkMap);
		if(k!=null){
			this.key = keyStart + k.toString().toLowerCase();
			this.key = StringUtils.replaceChars(this.key, " ", "-");
			this.key = StringUtils.replaceChars(this.key, ".", "");
		}
		else if(this.name!=null){
			this.key = keyStart + this.name.toString().toLowerCase();
			this.key = StringUtils.replaceChars(this.key, " ", "-");
			this.key = StringUtils.replaceChars(this.key, ".", "");
		}
		else{
			msg.appendSeparator(", ").append("could not generate key, no key or name given");
		}

		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	}

	@Override
	public String getCompareString() {
		return (String)this.name;
	}
}
