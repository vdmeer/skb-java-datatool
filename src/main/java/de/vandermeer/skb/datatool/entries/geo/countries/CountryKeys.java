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

package de.vandermeer.skb.datatool.entries.geo.countries;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Constants for countries (entry type, keys, schema).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class CountryKeys {

	/** Key for an ISO 3122 2-character code of a country. */
	public static EntryKey GEO_COUNTRY_A2 = new AbstractEntryKey("a2", "ISO 3166-1, 2 character code for a country", String.class, false, null);

	/** Key for an ISO 3122 3-character code of a country. */
	public static EntryKey GEO_COUNTRY_A3 = new AbstractEntryKey("a3", "ISO 3166-1, 3 character code for a country", String.class, false, null);

	/** Key for an ISO 3122 numeric code of a country. */
	public static EntryKey GEO_COUNTRY_NU = new AbstractEntryKey("nu", "ISO 3166-1, numeric code for a country", String.class, false, null);

	/** Key for an E.164 code of a country. */
	public static EntryKey GEO_COUNTRY_E164 = new AbstractEntryKey("e", "E.164 code for a country", String.class, false, null);

	/** Key for cc TLD of a country. */
	public static EntryKey GEO_COUNTRY_TLD = new AbstractEntryKey("t", "cc TLD (top level domain) for a country", String.class, false, null);
}
