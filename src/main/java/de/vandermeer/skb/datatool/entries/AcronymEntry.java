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

package de.vandermeer.skb.datatool.entries;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.LocalEntryKeys;
import de.vandermeer.skb.datatool.commons.ObjectLinks;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;

/**
 * A single acronym entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AcronymEntry implements DataEntry {

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Acronym schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.ACRONYMS;

	/** Longest acronym, null if this entry is not the longest, acShort otherwise. */
	String longestAcr;

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		for(DataEntry ae : set){
			if(ae instanceof AcronymEntry){
				if(((AcronymEntry) ae).getShort().equals(this.getShort())){
					if(((AcronymEntry) ae).getLong().equals(this.getLong())){
						return ae.getKey();
					}
				}
			}
		}
		return null;
	}

	@Override
	public void loadEntry(DataLoader loader) throws URISyntaxException {
		this.entryMap = loader.loadEntry(this.schema);
		this.entryMap.put(LocalEntryKeys.ACRONYM_SHORT_ORIG, loader.loadDataString(StandardEntryKeys.ACR_SHORT));

		if(this.entryMap.get(StandardEntryKeys.KEY)!=null){
			this.entryMap.put(StandardEntryKeys.KEY, loader.getKeyStart() + this.entryMap.get(StandardEntryKeys.KEY));
		}
		else{
			this.entryMap.put(StandardEntryKeys.KEY, loader.getKeyStart() + this.entryMap.get(LocalEntryKeys.ACRONYM_SHORT_ORIG));
		}
	}

	/**
	 * Returns the short form of the acronym.
	 * @return acronym short form
	 */
	public String getShort() {
		return (String)this.entryMap.get(StandardEntryKeys.ACR_SHORT);
	}

	/**
	 * Returns the short form of the acronym, not translated.
	 * @return acronym short form, not translated
	 */
	public String getShortOrig() {
		return (String)this.entryMap.get(LocalEntryKeys.ACRONYM_SHORT_ORIG);
	}

	/**
	 * Returns the long form of the acronym.
	 * @return acronym long form
	 */
	public String getLong() {
		return (String)this.entryMap.get(StandardEntryKeys.ACR_LONG);
	}

	/**
	 * Returns the links associated with the acronym.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.entryMap.get(StandardEntryKeys.OBJ_LINKS);
	}

	/**
	 * Returns a description for the acronym.
	 * @return acronym description, null if not set
	 */
	public String getDescription() {
		return (String)this.entryMap.get(StandardEntryKeys.DESCR);
	}

	@Override
	public String getCompareString() {
		return (String)this.entryMap.get(StandardEntryKeys.ACR_SHORT);
	}

	/**
	 * Sets this acronym entry to be the longest string for short version of an acronym list.
	 */
	public void setLongestAcr(){
		this.longestAcr = this.getShort();
	}

	/**
	 * Returns a string indicating if this entry is the longest short version of an acronym in an acronym list.
	 * @return null if not, short version of acronym if
	 */
	public String getLongestAcr(){
		return this.longestAcr;
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
