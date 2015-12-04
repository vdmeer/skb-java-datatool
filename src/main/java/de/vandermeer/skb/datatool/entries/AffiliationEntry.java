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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.ObjectGeo;
import de.vandermeer.skb.datatool.commons.ObjectLinks;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A single affiliation entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AffiliationEntry implements DataEntry {

	/** The key for the acronym. */
	String key;

	/** Long name of the affiliation. */
	Object longName;

	/** Short version of the affiliation name. */
	Object shortName;

	/** Affiliation name as SKB link to an acronym. */
	Object acronymLink;

	/** Affiliation geo information. */
	Object geo;

	/** Affiliation address. */
	Object address;

	/** Links for the affiliation. */
	Object links;

	/** Affiliation schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.AFFILIATIONS;

	/** A map of keys to string pairs for de-referenced values that impact key auto-generation. */
	Map<String, Pair<String, String>> refKeyMap;

	/**
	 * Returns the long name of the affiliation.
	 * @return affiliation long name
	 */
	public String getName(){
		return (String)this.longName;
	}

	/**
	 * Return the short name of the affiliation.
	 * @return affiliation short name
	 */
	public String getShortName(){
		return (String)this.shortName;
	}

	/**
	 * Returns the name as SKB link to an acronym.
	 * @return affiliation name SKB link
	 */
	public String getAcronymLink(){
		return (String)this.acronymLink;
	}

	/**
	 * Returns the affiliation geo information.
	 * @return affiliation geo information
	 */
	public ObjectGeo getGeo(){
		return (ObjectGeo)this.geo;
	}

	/**
	 * Returns the affiliation address.
	 * @return affiliation address
	 */
	public String getAddress(){
		return (String)this.address;
	}

	/**
	 * Returns the links associated with the affiliation.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.links;
	}

	@Override
	public String getKey() {
		return this.key;
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
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, Translator translator) throws URISyntaxException {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}

		msg = new StrBuilder(50);

		this.longName = Utilities.getDataObject(StandardEntryKeys.AFF_LONG, entryMap, translator);
		this.shortName = Utilities.getDataObject(StandardEntryKeys.AFF_SHORT, entryMap, translator);
		this.acronymLink = Utilities.getDataObject(StandardEntryKeys.ACR, entryMap);
		this.address = Utilities.getDataObject(StandardEntryKeys.AFF_ADDR, entryMap, translator);
		this.geo = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO, entryMap);
		this.links = Utilities.getDataObject(StandardEntryKeys.OBJ_LINKS, entryMap);

		if(this.longName==null){
			if(this.acronymLink==null){
				msg.appendSeparator(", ");
				msg.append("no long name and no acronym given");
			}
		}
		else{
			if(this.acronymLink==null && this.shortName==null){
				msg.appendSeparator(", ");
				msg.append("no short name nor acronym given");
			}
			if(this.acronymLink!=null){
				if(!((String)this.acronymLink).startsWith("skb://acronyms/")){
					msg.appendSeparator(", ");
					msg.append("invalid acronym format");
				}
			}
			if(this.acronymLink!=null && this.shortName!=null){
				msg.appendSeparator(", ");
				msg.append("no short name and acronym given");
			}
		}

		if(keyStart==null){
			msg.appendSeparator(", ");
			msg.append("no keyStart given");
		}
		else if(!StringUtils.endsWith(keyStart, Character.toString(keySeparator))){
			msg.appendSeparator(", ");
			msg.append("wrong end of keyStart");
		}
		else{
			Object k = Utilities.getDataObject(StandardEntryKeys.KEY, entryMap, translator);
			if(k!=null){
				this.key = keyStart + k;
			}
			else if(this.shortName!=null){
				this.key = keyStart + this.shortName;
			}
			else if(this.acronymLink!=null){
				if(this.refKeyMap==null){
					this.key = keyStart + StringUtils.substringAfterLast((String)this.acronymLink, ":");
				}
				else{
					URI uri = new URI((String)this.acronymLink);
					String acr = StringUtils.substringAfterLast(uri.getPath(), "/");
					Pair<String, String> p = this.refKeyMap.get(acr);
					if(p==null){
						msg.appendSeparator(", ");
						msg.append("invalid link to acronym <" + this.acronymLink + "> - cannot create key");
					}
					else{
						this.longName = p.getValue();
						this.shortName = p.getKey();
						this.key = keyStart + this.shortName;
					}
				}
			}
			else{
				msg.appendSeparator(", ");
				msg.append("cannot generate key");
			}
		}

		if(this.key!=null && this.key.contains(" ")){
			msg.appendSeparator(", ");
			msg.appendSeparator("acronym <" + this.key + "> contains illegal characters in key");
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
	public void setRefKeyMap(Map<String, Pair<String, String>> map){
		this.refKeyMap = map;
	}
}
