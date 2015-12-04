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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A data entry for countries.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class CountryEntry implements DataEntry {

	/** The key for the country. */
	String key;

	/** Country name. */
	Object name;

	/** Country's continent. */
	Object continent;

	/** A de-referenced continent entry. */
	Object continentEntry;

	/** ISO 2 character code. */
	Object isoA2;

	/** ISO 3 character code. */
	Object isoA3;

	/** ISO numeric code. */
	Object isoNumeric;

	/** E.164 code. */
	Object e164;

	/** Top-level Domain. */
	Object tld;

	/** Country schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.GEO_COUNTRIES;

	/**
	 * Returns the country name.
	 * @return country name
	 */
	public String getName(){
		return (String)this.name;
	}

	/**
	 * Returns the country continent.
	 * @return country continent
	 */
	public String getContinent(){
		return (String)this.continent;
	}

	/**
	 * Returns the country continent entry.
	 * @return country continent entry
	 */
	public ContinentEntry getContinentEntry(){
		return (ContinentEntry)this.continentEntry;
	}

	/**
	 * Returns the ISO 2 character code.
	 * @return ISO 2 character code
	 */
	public String getIsoA2(){
		return (String)this.isoA2;
	}

	/**
	 * Returns the ISO 3 character code.
	 * @return ISO 3 character code
	 */
	public String getIsoA3(){
		return (String)this.isoA3;
	}

	/**
	 * Returns the ISO numeric code.
	 * @return ISO numeric code
	 */
	public Integer getIsoNumeric(){
		return (Integer)this.isoNumeric;
	}

	/**
	 * Returns the Top Level Domain.
	 * @return top-level domain
	 */
	public String getTld(){
		return (String)this.tld;
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, Translator translator) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	
		msg = new StrBuilder(50);

		this.name = Utilities.getDataObject(StandardEntryKeys.GEO_NAME, entryMap, translator);

		this.continent = Utilities.getDataObject(StandardEntryKeys.GEO_CONTINENT, entryMap, translator);
		if(!((String)this.continent).startsWith("skb://continents/")){
			msg.appendSeparator(", ");
			msg.append("invalid continent link format");
		}

		this.isoA2 = Utilities.getDataObject(StandardEntryKeys.GEO_COUNTRY_A2, entryMap);
		this.isoA3 = Utilities.getDataObject(StandardEntryKeys.GEO_COUNTRY_A3, entryMap);
		this.isoNumeric = Utilities.getDataObject(StandardEntryKeys.GEO_COUNTRY_NU, entryMap);
		this.e164 = Utilities.getDataObject(StandardEntryKeys.GEO_COUNTRY_E164, entryMap);
		this.tld = Utilities.getDataObject(StandardEntryKeys.GEO_COUNTRY_TLD, entryMap);

		this.key = (String)this.tld;

		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	}

	@Override
	public String getCompareString() {
		return (String)this.tld;
	}

	@Override
	public void setRefKeyMap(Map<String, Pair<String, String>> map) {
		// countries do not need that
	}

	public void setContinentEntry(DataSet<ContinentEntry> ds) throws URISyntaxException{
		URI uri = new URI((String)this.continent);
		String c = StringUtils.substringAfterLast(uri.getPath(), "/");
		this.continentEntry = ds.getMap().get(c);
	}
}
