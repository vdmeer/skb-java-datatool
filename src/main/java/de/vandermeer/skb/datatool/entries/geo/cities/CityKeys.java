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

package de.vandermeer.skb.datatool.entries.geo.cities;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the city entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class CityKeys {

	/** Key for the WAC code of a city. */
	public static EntryKey GEO_CITY_WAC = new AbstractEntryKey("wac", "WAC code for a city", String.class, false, null);

	/** Key for the IATA code of a city. */
	public static EntryKey GEO_CITY_IATA = new AbstractEntryKey("iata", "IATA code for a city", String.class, false, null);

	/** Key for the ICAO code of a city. */
	public static EntryKey GEO_CITY_ICAO = new AbstractEntryKey("icao", "ICAO code for a city", String.class, false, null);

	/** Key for the state of a city. */
	public static EntryKey GEO_CITY_STATE = new AbstractEntryKey("s", "a state the city is in", String.class, true, null);

	/** Key for the region of a city. */
	public static EntryKey GEO_CITY_REGION = new AbstractEntryKey("r", "a region the city is in", String.class, true, null);

	/** Key for the county of a city. */
	public static EntryKey GEO_CITY_COUNTY = new AbstractEntryKey("y", "county the city is in", String.class, true, null);
}
