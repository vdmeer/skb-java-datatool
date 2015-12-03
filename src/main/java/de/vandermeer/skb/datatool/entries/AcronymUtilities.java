package de.vandermeer.skb.datatool.entries;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.datatool.commons.DataSet;

public abstract class AcronymUtilities {

	public final static Map<String, Pair<String, String>> toREfKeyMap(DataSet<AcronymEntry> ds){
		Map<String, Pair<String, String>> ret = new HashMap<>();
		for(AcronymEntry entry : ds.getEntries()){
			ret.put(entry.getKey(), Pair.of(entry.getShort(), entry.getLong()));
		}
		return ret;
	}

	public final static void setLongestAcr(DataSet<AcronymEntry> ds){
		String maxShort = "";
		String key = null;
		for(AcronymEntry entry : ds.getEntries()){
			if(((String)entry.acShortOrig).length()>maxShort.length()){
				maxShort = entry.getShort();
				key = entry.getKey();
			}
		}
		ds.getMap().get(key).setLongestAcr();
	}
}
