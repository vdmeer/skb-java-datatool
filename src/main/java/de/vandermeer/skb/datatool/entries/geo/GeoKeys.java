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

package de.vandermeer.skb.datatool.entries.geo;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Constants for geographic entries (keys).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class GeoKeys {

	/** Key for a geographic name. */
	public static EntryKey GEO_NAME = new AbstractEntryKey("n", "name of a geography entry, e.g. a city or a country", String.class, true, null);

	/** Key for an SKB link to a country. */
	public static EntryKey GEO_COUNTRY = new AbstractEntryKey("country", "SKB link to a country entry", String.class, false, "skb://countries");

	/** Key for an SKB link to a continent. */
	public static EntryKey GEO_CONTINENT = new AbstractEntryKey("continent", "SKB link to a continent entry", String.class, false, "skb://continents");

	/** Key (local) for continent link. */
	public static EntryKey LOCAL_GEO_CONTINENT_LINK = new AbstractEntryKey("continent-link", "original continent link", String.class, false, null);

	/** Key (local) for country link. */
	public static EntryKey LOCAL_GEO_COUNTRY_LINK = new AbstractEntryKey	("country-link", "original country link", String.class, false, null);

	/** Key (local) for city link. */
	public static EntryKey LOCAL_GEO_CITY_LINK = new AbstractEntryKey("city-link", "original city link", String.class, false, null);
}
