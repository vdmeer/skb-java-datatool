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

package de.vandermeer.skb.datatool.entries.acronyms;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.CommonConstants;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.entries.EntryConstants;
import de.vandermeer.skb.datatool.entries.links.object.ObjectLinks;
import de.vandermeer.skb.datatool.target.AbstractDataTarget;
import de.vandermeer.skb.datatool.target.StandardDataTargetDefinitions;

/**
 * A single acronym entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AcronymEntry implements DataEntry {

	/** Acronym entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
				"acronyms", "acr"
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.LATEX_TABLE, "de/vandermeer/skb/datatool/acronyms/targets/latex-table.stg"))
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.LATEX_ACRONYMS, "de/vandermeer/skb/datatool/acronyms/targets/latex-acronym.stg"))
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.HTML_TABLE, "de/vandermeer/skb/datatool/acronyms/targets/html-table.stg"))
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.SQL_SIMPLE, "de/vandermeer/skb/datatool/acronyms/targets/sql-simple.stg"))
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.TEXT_PLAIN, "de/vandermeer/skb/datatool/acronyms/targets/text-plain.stg"))
	;

	/** Acronym schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(AcronymKeys.ACR_SHORT, true);
				put(AcronymKeys.ACR_LONG, true);
				put(CommonConstants.EK_KEY, false);
				put(EntryConstants.EK_DESCR, false);
				put(ObjectLinks.OBJ_LINKS, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Longest acronym, null if this entry is not the longest, acShort otherwise. */
	String longestAcr;

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
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
	public void loadEntry(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException {
		this.entryMap = loader.loadEntry(this.getSchema());
		this.entryMap.put(AcronymKeys.LOCAL_ACRONYM_SHORT_ORIG, loader.loadDataString(AcronymKeys.ACR_SHORT));

		if(this.entryMap.get(CommonConstants.EK_KEY)!=null){
			this.entryMap.put(CommonConstants.EK_KEY, loader.getKeyStart() + this.entryMap.get(CommonConstants.EK_KEY));
		}
		else{
			this.entryMap.put(CommonConstants.EK_KEY, loader.getKeyStart() + this.entryMap.get(AcronymKeys.LOCAL_ACRONYM_SHORT_ORIG));
		}
	}

	/**
	 * Returns the short form of the acronym.
	 * @return acronym short form
	 */
	public String getShort() {
		return (String)this.entryMap.get(AcronymKeys.ACR_SHORT);
	}

	/**
	 * Returns the short form of the acronym, not translated.
	 * @return acronym short form, not translated
	 */
	public String getShortOrig() {
		return (String)this.entryMap.get(AcronymKeys.LOCAL_ACRONYM_SHORT_ORIG);
	}

	/**
	 * Returns the long form of the acronym.
	 * @return acronym long form
	 */
	public String getLong() {
		return (String)this.entryMap.get(AcronymKeys.ACR_LONG);
	}

	/**
	 * Returns the links associated with the acronym.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.entryMap.get(ObjectLinks.OBJ_LINKS);
	}

	/**
	 * Returns a description for the acronym.
	 * @return acronym description, null if not set
	 */
	public String getDescription() {
		return (String)this.entryMap.get(EntryConstants.EK_DESCR);
	}

	@Override
	public String getCompareString() {
		return (String)this.entryMap.get(AcronymKeys.ACR_SHORT);
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
