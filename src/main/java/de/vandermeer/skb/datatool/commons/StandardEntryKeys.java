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

import de.vandermeer.skb.datatool.entries.CityEntry;
import de.vandermeer.skb.datatool.entries.CountryEntry;

/**
 * Standard entry keys.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public enum StandardEntryKeys implements EntryKey {

	KEY				("key", "a manually set key, overwriting any auto-generated key", String.class, false, null),
	ACRONYM			("acronym", "an SKB link to an acronym", String.class, false, "skb://acronyms"),

	ACR_SHORT		("short", "short version of an acronym", String.class, true, null),
	ACR_LONG		("long", "long version of an acronym", String.class, true, null),

	AFF_TYPE		("type", "SKB link to an affiliation type", String.class, false, "skb://affiliation-types"),
	AFF_SHORT		("short", "short name of an affiliation as either plain text or an SKB link", String.class, true, null),//TODO Link extra
	AFF_LONG		("long", "long name of an affiliation, not required if short is an SKB link to an acronym", String.class, true, null),
	AFF_ADDR		("addr", "address information for an affiliation", String.class, true, null),

	DESCR			("descr", "description of a data entry", String.class, true, null),

	LATEX			("ltx", "latex representation", String.class, false, null),
	ASCII_DOC		("ad", "AsciiDoc representation", String.class, false, null),

	HTML_ENTITY		("he", "an HTML entity", String.class, false, null),

	ENC_DEC			("d", "decimal value of a character encoding", Integer.class, false, null),
	ENC_CHAR		("c", "character representation of an encoding", String.class, false, null),

	UNICODE_BLOCK	("b", "a Unicode block", String.class, false, null),
	UNICODE_SET		("s", "a Unicode set", String.class, false, null),

	GEO_NAME			("n", "name of a geography entry, e.g. a city or a country", String.class, true, null),
	GEO_COUNTRY			("country", "SKB link to a country entry", String.class, false, "skb://countries"),
	GEO_CONTINENT		("continent", "SKB link to a continent entry", String.class, false, "skb://continents"),
	GEO_CITY_WAC		("wac", "WAC code for a city", String.class, false, null),
	GEO_CITY_IATA		("iata", "IATA code for a city", String.class, false, null),
	GEO_CITY_ICAO		("icao", "ICAO code for a city", String.class, false, null),
	GEO_CITY_STATE		("s", "a state the city is in", String.class, true, null),
	GEO_CITY_REGION		("r", "a region the city is in", String.class, true, null),
	GEO_CITY_COUNTY		("y", "county the city is in", String.class, true, null),
	GEO_COUNTRY_A2		("a2", "ISO 3166-1, 2 character code for a country", String.class, false, null),
	GEO_COUNTRY_A3		("a3", "ISO 3166-1, 3 character code for a country", String.class, false, null),
	GEO_COUNTRY_NU		("nu", "ISO 3166-1, numeric code for a country", String.class, false, null),
	GEO_COUNTRY_E164	("e", "E.164 code for a country", String.class, false, null),
	GEO_COUNTRY_TLD		("t", "cc TLD (top level domain) for a country", String.class, false, null),

	PEOPLE_FIRST		("first", "first name of a person", String.class, true, null),
	PEOPLE_MIDDLE		("middle", "middle name of a person", String.class, true, null),
	PEOPLE_LAST			("last", "last name of a person", String.class, true, null),

	OBJ_LINKS	("links", "a links object with URLs and URNs", ObjectLinks.class, false, null),
	OBJ_LINKS_U	("u", "a URL inside a links object", String.class, false, null),
	OBJ_LINKS_W	("w", "a URL to a Wikipedia page inside a links object", String.class, false, null),

	OBJ_GEO					("geo", "geographic information, e.g. city or country", ObjectGeo.class, false, null),
	OBJ_GEO_CITY_NAME		("city-name", "name of a city", String.class, true, null),
	OBJ_GEO_CITY_LINK		("city", "SKB link to a city entry", CityEntry.class, false, "skb://cities"),
	OBJ_GEO_COUNTRY_NAME	("country-name", "name of a country", String.class, true, null),
	OBJ_GEO_COUNTRY_LINK	("country", "SKB link to a country entry", CountryEntry.class, false, "skb://countries"),

	OBJ_AFF_LINKS			("affiliations", "SKB link pointing to an array of affiliation entries", ObjectAffiliations.class, false, "skb://affiliations"),
	;

	/** Key. */
	private String key;

	/** Key description. */
	private String description;

	/** Key type. */
	private Class<?> type;

	/** Flag for using translator on processing values for the key. */
	private boolean useTranslator;

	/** URI of an skb link, null if none supported by the key. */
	private String skbUri;

	/**
	 * Creates a new standard entry key.
	 * @param key the name of the key
	 * @param description the key's description
	 * @param type the class the key expects as type for values
	 * @param useTranslator flag for using translator
	 * @param skbUri an SKB URI if type requires one, null if not
	 */
	StandardEntryKeys(String key, String description, Class<?> type, boolean useTranslator, String skbUri){
		this.key = key;
		this.description = description;
		this.type = type;
		this.useTranslator = useTranslator;
		this.skbUri = skbUri;
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

	@Override
	public boolean useTranslator() {
		return this.useTranslator;
	}

	@Override
	public String getSkbUri(){
		return this.skbUri;
	}
}
