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

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.LocalEntryKeys;
import de.vandermeer.skb.datatool.commons.ObjectGeo;
import de.vandermeer.skb.datatool.commons.ObjectLinks;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;

/**
 * A single affiliation entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AffiliationEntry implements DataEntry {

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** Affiliation schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.AFFILIATIONS;

	/**
	 * Returns the long name of the affiliation.
	 * @return affiliation long name
	 */
	public String getName(){
		return (String)this.entryMap.get(StandardEntryKeys.AFF_LONG);
	}

	/**
	 * Return the short name of the affiliation.
	 * @return affiliation short name
	 */
	public String getShortName(){
		return (String)this.entryMap.get(StandardEntryKeys.AFF_SHORT);
	}

	/**
	 * Returns affiliation acronym link.
	 * @return affiliation acronym link
	 */
	public String getAcronymLink(){
		return (String)this.entryMap.get(LocalEntryKeys.ACRONYM_LINK);
	}

	/**
	 * Returns the expanded acronym type.
	 * @return expanded acronym type
	 */
	public AcronymEntry getAcronym(){
		return (AcronymEntry)this.entryMap.get(StandardEntryKeys.ACRONYM);
	}

	/**
	 * Returns the affiliation type link.
	 * @return affiliation type link
	 */
	public String getTypeLink(){
		return (String)this.entryMap.get(LocalEntryKeys.AFF_TYPE_LINK);
	}

	/**
	 * Returns the expanded affiliation type.
	 * @return affiliation type
	 */
	public String getType(){
		return (String)this.entryMap.get(StandardEntryKeys.AFF_TYPE);
	}

	/**
	 * Returns the affiliation geo information.
	 * @return affiliation geo information
	 */
	public ObjectGeo getGeo(){
		return (ObjectGeo)this.entryMap.get(StandardEntryKeys.OBJ_GEO);
	}

	/**
	 * Returns the affiliation address.
	 * @return affiliation address
	 */
	public String getAddress(){
		return (String)this.entryMap.get(StandardEntryKeys.AFF_ADDR);
	}

	/**
	 * Returns the links associated with the affiliation.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.entryMap.get(StandardEntryKeys.OBJ_LINKS);
	}

	@Override
	public String getCompareString() {
		if(this.getName()!=null){
			return this.getName();
		}
		else if(this.getShortName()!=null){
			return this.getShortName();
		}
		return this.getAcronymLink();
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadEntry(DataLoader loader) throws URISyntaxException {
		this.entryMap = loader.loadEntry(this.schema);

		this.entryMap.put(LocalEntryKeys.ACRONYM_LINK, loader.loadDataString(StandardEntryKeys.ACRONYM));
		this.entryMap.put(LocalEntryKeys.AFF_TYPE_LINK, loader.loadDataString(StandardEntryKeys.AFF_TYPE));

		StrBuilder msg = new StrBuilder(50);
		if(this.getName()==null){
			if(this.getAcronymLink()==null){
				msg.appendSeparator(", ");
				msg.append("no long name and no acronym given");
			}
		}
		else{
			if(this.getAcronymLink()==null && this.getShortName()==null){
				msg.appendSeparator(", ");
				msg.append("no short name nor acronym given");
			}
			if(this.getAcronymLink()!=null && this.getShortName()!=null){
				msg.appendSeparator(", ");
				msg.append("no short name and acronym given");
			}
		}

		if(this.getKey()!=null){
			this.entryMap.put(StandardEntryKeys.KEY, loader.getKeyStart() + this.getKey());
		}
		else if(this.getShortName()!=null){
			this.entryMap.put(StandardEntryKeys.KEY, loader.getKeyStart() + this.getShortName());
		}
		else if(this.getAcronymLink()!=null){
			this.entryMap.put(StandardEntryKeys.AFF_LONG, this.getAcronym().getLong());
			this.entryMap.put(StandardEntryKeys.AFF_SHORT, this.getAcronym().getShort());
			this.entryMap.put(StandardEntryKeys.KEY, loader.getKeyStart() + this.getShortName());
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
		return this.schema;
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
