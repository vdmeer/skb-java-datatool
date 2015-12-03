package de.vandermeer.skb.datatool.commons;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

public interface DataEntrySchema {

	/**
	 * Returns the key map of the data entry.
	 * The key map contains keys and a flag for the requirement level (true for mandatory, false for optional).
	 * @return data entry key map
	 */
	Map<EntryKey, Boolean> getKeyMap();

	/**
	 * Returns a string representation of all keys of the data entry.
	 * @return set of all keys
	 */
	Set<String> getKeySet();

	/**
	 * Tests the map against the data entry schema (programmatic).
	 * @param entryMap map for the entry
	 * @return empty on success, string builder with explanations on error
	 */
	default StrBuilder testSchema(Map<String, Object> entryMap){
		StrBuilder ret = new StrBuilder();
		for(Entry<EntryKey, Boolean> e : this.getKeyMap().entrySet()){
			if(e.getValue()==true && !entryMap.containsKey(e.getKey().getKey())){
				ret.append("missing mandatory entry key <").append(e.getKey().getKey()).append(">").appendNewLine();
			}
			else if(e.getValue()==true && StringUtils.isEmpty(entryMap.get(e.getKey().getKey()).toString())){
				ret.append("empty mandatory entry key <").append(e.getKey().getKey()).append(">").appendNewLine();
			}
		}
		for(String s : entryMap.keySet()){
			if(!this.getKeySet().contains(s)){
				ret.append("unknown entry key <").append(s).append(">").appendNewLine();
			}
		}
		return ret;
	}
}
