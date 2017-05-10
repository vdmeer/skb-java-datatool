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

package de.vandermeer.skb.datatool.entries.conferences;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.CommonKeys;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataUtilities;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;
import de.vandermeer.skb.datatool.commons.target.AbstractDataTarget;
import de.vandermeer.skb.datatool.commons.target.StandardDataTargetDefinitions;
import de.vandermeer.skb.datatool.entries.EntryKeys;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntry;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationEntry;
import de.vandermeer.skb.datatool.entries.date.edate.ObjectEDate;
import de.vandermeer.skb.datatool.entries.date.month.MonthEntry;
import de.vandermeer.skb.datatool.entries.geo.GeoKeys;
import de.vandermeer.skb.datatool.entries.geo.cities.CityEntry;
import de.vandermeer.skb.datatool.entries.links.object.ObjectLinks;

/**
 * A single conference entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class ConferenceEntry implements DataEntry {

	/** Conference entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"conferences", "conf",
					new DataEntryType[]{
							AffiliationEntry.ENTRY_TYPE,
							CityEntry.ENTRY_TYPE,
							MonthEntry.ENTRY_TYPE,
					}
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.TEXT_PLAIN, "de/vandermeer/skb/datatool/targets/conferences/text-plain.stg"))
	;

	/** Conference schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(CommonKeys.KEY, false);
				put(ConferenceKeys.SPONSORS, false);
				put(EntryKeys.ACRONYM, true);
				put(ConferenceKeys.NOTES, false);
				put(ConferenceKeys.NUMBER, false);
				put(ObjectEDate.OBJ_EDATE, true);
				put(GeoKeys.GEO_CITY, true);
				put(ObjectLinks.OBJ_LINKS, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Map with linkeable data entries from other sets. */
	private LoadedTypeMap loadedTypes;

	/**
	 * Creates a new affiliation entry with loaded types.
	 * @param loadedTypes loaded types
	 */
	ConferenceEntry(LoadedTypeMap loadedTypes){
		this.loadedTypes = loadedTypes;
	}

//	/**
//	 * Returns the long name of the affiliation.
//	 * @return affiliation long name
//	 */
//	public String getName(){
//		return (String)this.entryMap.get(AffiliationKeys.AFF_LONG);
//	}
//
//	/**
//	 * Return the short name of the affiliation.
//	 * @return affiliation short name
//	 */
//	public String getShortName(){
//		return (String)this.entryMap.get(AffiliationKeys.AFF_SHORT);
//	}

	/**
	 * Returns affiliation acronym link.
	 * @return affiliation acronym link
	 */
	public String getAcronymLink(){
		return (String)this.entryMap.get(EntryKeys.LOCAL_ACRONYM_LINK);
	}

	/**
	 * Returns the expanded acronym type.
	 * @return expanded acronym type
	 */
	public AcronymEntry getAcronym(){
		return (AcronymEntry)this.entryMap.get(EntryKeys.ACRONYM);
	}
//
//	/**
//	 * Returns the affiliation type link.
//	 * @return affiliation type link
//	 */
//	public String getTypeLink(){
//		return (String)this.entryMap.get(AffiliationKeys.LOCAL_AFF_TYPE_LINK);
//	}
//
//	/**
//	 * Returns the expanded affiliation type.
//	 * @return affiliation type
//	 */
//	public String getType(){
//		return (String)this.entryMap.get(AffiliationKeys.AFF_TYPE);
//	}
//
//	/**
//	 * Returns the affiliation geo information.
//	 * @return affiliation geo information
//	 */
//	public ObjectGeo getGeo(){
//		return (ObjectGeo)this.entryMap.get(ObjectGeo.OBJ_GEO);
//	}
//
//	/**
//	 * Returns the affiliation address.
//	 * @return affiliation address
//	 */
//	public String getAddress(){
//		return (String)this.entryMap.get(AffiliationKeys.AFF_ADDR);
//	}
//
//	/**
//	 * Returns the links associated with the affiliation.
//	 * @return links, null if not set
//	 */
//	public ObjectLinks getLinks() {
//		return (ObjectLinks)this.entryMap.get(ObjectLinks.OBJ_LINKS);
//	}

	@Override
	public String getCompareString() {
//		if(this.getName()!=null){
//			return this.getName();
//		}
//		else if(this.getShortName()!=null){
//			return this.getShortName();
//		}
		return this.getAcronymLink();
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, data, this.loadedTypes, cs);

		this.entryMap.put(EntryKeys.LOCAL_ACRONYM_LINK, DataUtilities.loadDataString(EntryKeys.ACRONYM, data));
//		this.entryMap.put(AffiliationKeys.LOCAL_AFF_TYPE_LINK, DataUtilities.loadDataString(AffiliationKeys.AFF_TYPE, data));

		StrBuilder msg = new StrBuilder(50);
//		if(this.getName()==null){
//			if(this.getAcronymLink()==null){
//				msg.appendSeparator(", ");
//				msg.append("no long name and no acronym given");
//			}
//		}
//		else{
//			if(this.getAcronymLink()==null && this.getShortName()==null){
//				msg.appendSeparator(", ");
//				msg.append("no short name nor acronym given");
//			}
//			if(this.getAcronymLink()!=null && this.getShortName()!=null){
//				msg.appendSeparator(", ");
//				msg.append("no short name and acronym given");
//			}
//		}

		if(this.getKey()!=null){
			this.entryMap.put(CommonKeys.KEY, keyStart + this.getKey());
		}
		else if(this.getAcronymLink()!=null){
//			this.entryMap.put(AffiliationKeys.AFF_LONG, this.getAcronym().getLong());
//			this.entryMap.put(AffiliationKeys.AFF_SHORT, this.getAcronym().getShort());
			this.entryMap.put(CommonKeys.KEY, keyStart + this.getAcronym().getShort());
		}
		else{
			msg.appendSeparator(", ");
			msg.append("cannot generate key");
		}

		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	}

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
