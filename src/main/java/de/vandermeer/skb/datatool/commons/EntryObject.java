package de.vandermeer.skb.datatool.commons;

import java.util.Map;

public interface EntryObject {

	/**
	 * Loads an entry object from a given map with tests against expected keys
	 * @param entryMap map of entries to load from
	 * @return
	 */
	String load(Map<String, Object> entryMap);

	/**
	 * Returns the schema for the entry object.
	 * @return entry object schema
	 */
	DataEntrySchema getSchema();
}
