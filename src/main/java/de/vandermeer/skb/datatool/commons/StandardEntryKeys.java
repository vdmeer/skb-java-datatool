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

package de.vandermeer.skb.datatool.commons;

/**
 * Standard entry keys.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public enum StandardEntryKeys implements EntryKey {

	KEY	("key", "a manually set key, overwriting any auto-generated key", String.class),
	ACR	("acr", "an acronym or a link to an acronym as SKB link", String.class),

	ACR_SHORT	("short", "short version of an acronym", String.class),
	ACR_LONG	("long", "long version of an acronym", String.class),

	AFF_TYPE	("type", "type of an affiliation as an SKB link", String.class),
	AFF_SHORT	("short", "short name of an affiliation as either plain text or an SKB link", String.class),
	AFF_LONG	("long", "long name of an affiliation, not required if short is an SKB link to an acronym", String.class),
	AFF_ADDR	("addr", "address information for an affiliation", String.class),

	DESCR		("descr", "description of a data entry", String.class),

	LATEX		("ltx", "latex representation", String.class),
	ASCII_DOC	("ad", "AsciiDoc representation", String.class),

	HTML_ENTITY	("he", "an HTML entity", String.class),

	ENC_DEC		("d", "decimal value of a character encoding", Integer.class),
	ENC_CHAR	("c", "character representation of an encoding", String.class),

	UNICODE_BLOCK	("b", "a Unicode block", String.class),
	UNICODE_SET		("s", "a Unicode set", String.class),

	GEO_NAME			("n", "name of a geography entry, e.g. a city or a country", String.class),
	GEO_COUNTRY			("c", "SKB link to a country", String.class),
	GEO_CONTINENT		("c", "SKB link to a continent", String.class),
	GEO_CITY_WAC		("wac", "WAC code for a city", String.class),
	GEO_CITY_IATA		("iata", "IATA code for a city", String.class),
	GEO_CITY_ICAO		("icao", "ICAO code for a city", String.class),
	GEO_CITY_STATE		("s", "a state the city is in", String.class),
	GEO_CITY_REGION		("r", "a region the city is in", String.class),
	GEO_CITY_COUNTY		("y", "county the city is in", String.class),
	GEO_COUNTRY_A2		("a2", "ISO 3166-1, 2 character code for a country", String.class),
	GEO_COUNTRY_A3		("a3", "ISO 3166-1, 3 character code for a country", String.class),
	GEO_COUNTRY_NU		("nu", "ISO 3166-1, numeric code for a country", Integer.class),
	GEO_COUNTRY_E164	("e", "E.164 code for a country", Integer.class),
	GEO_COUNTRY_TLD		("t", "cc TLD (top level domain) for a country", String.class),

	OBJ_LINKS	("links", "a links object with URLs and URNs", ObjectLinks.class),
	OBJ_LINKS_U	("u", "a URL inside a links object", String.class),
	OBJ_LINKS_W	("w", "a URL to a Wikipedia page inside a links object", String.class),

	OBJ_GEO					("geo", "geographic information, e.g. city or country", ObjectGeo.class),
	OBJ_GEO_CITY			("city", "name of a city", String.class),
	OBJ_GEO_CITY_LINK		("city-link", "SKB link pointing to a city", String.class),
	OBJ_GEO_COUNTRY			("country", "name of a country", String.class),
	OBJ_GEO_COUNTRY_LINK	("country-link", "SKB link pointing to a country", String.class),
	;

	/** Key. */
	private String key;

	/** Key description. */
	private String description;

	/** Key type. */
	private Class<?> type;

	/**
	 * Creates a new standard entry key.
	 * @param key the name of the key
	 * @param description the key's description
	 * @param type the class the key expects as type for values
	 */
	StandardEntryKeys(String key, String description, Class<?> type){
		this.key = key;
		this.description = description;
		this.type = type;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

}
