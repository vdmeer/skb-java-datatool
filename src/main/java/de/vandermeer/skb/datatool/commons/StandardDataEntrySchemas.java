package de.vandermeer.skb.datatool.commons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum StandardDataEntrySchemas implements DataEntrySchema {

	/** Schema keys for acronyms. */
	ACRONYMS(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.ACR_SHORT, true);
				put(StandardEntryKeys.ACR_LONG, true);
				put(StandardEntryKeys.KEY, false);
				put(StandardEntryKeys.DESCR, false);
				put(StandardEntryKeys.OBJ_LINKS, false);
			}}
	),

	/** Schema keys for affiliations. */
	AFFILIATIONS(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.AFF_TYPE, true);
				put(StandardEntryKeys.KEY, false);
				put(StandardEntryKeys.AFF_LONG, false);
				put(StandardEntryKeys.AFF_SHORT, false);
				put(StandardEntryKeys.ACR, false);
				put(StandardEntryKeys.AFF_ADDR, false);
				put(StandardEntryKeys.OBJ_GEO, false);
				put(StandardEntryKeys.OBJ_LINKS, false);
			}}
	),

	/** Schema for keys of an HTML entity. */
	HTML_ENTITIES(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.HTML_ENTITY, true);
				put(StandardEntryKeys.LATEX, false);
				put(StandardEntryKeys.ASCII_DOC, false);
				put(StandardEntryKeys.DESCR, false);
			}}
	),

	/** Schema for keys of a character encoding. */
	CHAR_ENCODINGS(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.ENC_DEC, true);
				put(StandardEntryKeys.ENC_CHAR, true);
				put(StandardEntryKeys.HTML_ENTITY, false);
				put(StandardEntryKeys.UNICODE_BLOCK, false);
				put(StandardEntryKeys.UNICODE_SET, false);
				put(StandardEntryKeys.LATEX, false);
				put(StandardEntryKeys.ASCII_DOC, false);
				put(StandardEntryKeys.DESCR, false);
			}}
	),

	/** Schema for keys of a continent. */
	GEO_CONTINENTS(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.KEY, true);
				put(StandardEntryKeys.GEO_NAME, true);
			}}
	),

	/** Schema for keys of a country. */
	GEO_COUNTRIES(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.GEO_NAME, true);
				put(StandardEntryKeys.GEO_CONTINENT, true);
				put(StandardEntryKeys.GEO_COUNTRY_A2, true);
				put(StandardEntryKeys.GEO_COUNTRY_A3, true);
				put(StandardEntryKeys.GEO_COUNTRY_NU, true);
				put(StandardEntryKeys.GEO_COUNTRY_E164, false);
				put(StandardEntryKeys.GEO_COUNTRY_TLD, true);
			}}
	),

	/** Schema for keys of a city. */
	GEO_CITIES(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.GEO_NAME, true);
				put(StandardEntryKeys.KEY, false);
				put(StandardEntryKeys.GEO_COUNTRY, true);
				put(StandardEntryKeys.GEO_CITY_IATA, false);
				put(StandardEntryKeys.GEO_CITY_ICAO, false);
				put(StandardEntryKeys.GEO_CITY_WAC, false);
				put(StandardEntryKeys.GEO_CITY_COUNTY, false);
				put(StandardEntryKeys.GEO_CITY_REGION, false);
				put(StandardEntryKeys.GEO_CITY_STATE, false);
				put(StandardEntryKeys.OBJ_LINKS, false);
			}}
	),

	/** Schema keys for a data object geo. */
	OBJECT_GEO(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.OBJ_GEO_CITY, false);
				put(StandardEntryKeys.OBJ_GEO_CITY_LINK, false);
				put(StandardEntryKeys.OBJ_GEO_COUNTRY, false);
				put(StandardEntryKeys.OBJ_GEO_COUNTRY_LINK, false);
			}}
	),

	/** Schema keys for a data object links. */
	OBJECT_LINKS(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(StandardEntryKeys.OBJ_LINKS_U, false);
				put(StandardEntryKeys.OBJ_LINKS_W, false);
			}}
	),

	;

	/** Entry key map, all understood keys. */
	private final Map<EntryKey, Boolean> keyMap;

	/** Entry key set. */
	private final Set<String> keySet;

	StandardDataEntrySchemas(Map<EntryKey, Boolean> keyMap) {
		this.keyMap = new HashMap<>(keyMap);

		this.keySet = new HashSet<>();
		for(EntryKey key : keyMap.keySet()){
			this.keySet.add(key.getKey());
		}
	}

	@Override
	public Map<EntryKey, Boolean> getKeyMap() {
		return this.keyMap;
	}

	@Override
	public Set<String> getKeySet() {
		return this.keySet;
	}

}
