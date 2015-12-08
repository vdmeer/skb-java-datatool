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

import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.LocalEntryKeys;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;

/**
 * A data entry for countries.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class CountryEntry implements DataEntry {

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Country schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.GEO_COUNTRIES;

	/**
	 * Returns the country name.
	 * @return country name
	 */
	public String getName(){
		return (String)this.entryMap.get(StandardEntryKeys.GEO_NAME);
	}

	/**
	 * Returns the country continent.
	 * @return country continent
	 */
	public ContinentEntry getContinent(){
		return (ContinentEntry)this.entryMap.get(StandardEntryKeys.GEO_CONTINENT);
	}

	/**
	 * Returns the country continent link.
	 * @return country continent link
	 */
	public String getContinentLink(){
		return (String)this.entryMap.get(LocalEntryKeys.GEO_CONTINENT_LINK);
	}

	/**
	 * Returns the ISO 2 character code.
	 * @return ISO 2 character code
	 */
	public String getIsoA2(){
		return (String)this.entryMap.get(StandardEntryKeys.GEO_COUNTRY_A2);
	}

	/**
	 * Returns the ISO 3 character code.
	 * @return ISO 3 character code
	 */
	public String getIsoA3(){
		return (String)this.entryMap.get(StandardEntryKeys.GEO_COUNTRY_A3);
	}

	/**
	 * Returns the ISO numeric code.
	 * @return ISO numeric code
	 */
	public Integer getIsoNumeric(){
		return (Integer)this.entryMap.get(StandardEntryKeys.GEO_COUNTRY_NU);
	}

	/**
	 * Returns the Top Level Domain.
	 * @return top-level domain
	 */
	public String getTld(){
		return (String)this.entryMap.get(StandardEntryKeys.GEO_COUNTRY_TLD);
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadEntry(DataLoader loader) throws URISyntaxException {
		this.entryMap = loader.loadEntry(this.schema);
		this.entryMap.put(LocalEntryKeys.GEO_CONTINENT_LINK, loader.loadDataString(StandardEntryKeys.GEO_CONTINENT));
		this.entryMap.put(StandardEntryKeys.KEY, this.getTld());
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
