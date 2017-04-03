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

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.entries.geo.cities.CityEntry;
import de.vandermeer.skb.datatool.entries.geo.countries.CountryEntry;

/**
 * Keys used by the object geo.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectGeoKeys {

	/** Name of a city. */
	public static EntryKey OBJ_GEO_CITY_NAME = new AbstractEntryKey("city-name", "name of a city", String.class, true, null);

	/** SKB link to a city. */
	public static EntryKey OBJ_GEO_CITY_LINK = new AbstractEntryKey("city", "SKB link to a city entry", CityEntry.class, false, "skb://cities");

	/** Name of a country. */
	public static EntryKey OBJ_GEO_COUNTRY_NAME = new AbstractEntryKey("country-name", "name of a country", String.class, true, null);

	/** SKB link to a country. */
	public static EntryKey OBJ_GEO_COUNTRY_LINK = new AbstractEntryKey("country", "SKB link to a country entry", CountryEntry.class, false, "skb://countries");

}
