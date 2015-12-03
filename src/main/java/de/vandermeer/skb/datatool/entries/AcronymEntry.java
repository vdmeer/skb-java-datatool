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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.commons.ObjectLinks;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A single acronym entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AcronymEntry implements DataEntry {

	/** The key for the acronym. */
	String key;

	/** Short form of the acronym. */
	Object acShort;

	/** Hack to calculate longest short version of an acronym. */
	Object acShortOrig;

	/** Long form of the acronym. */
	Object acLong;

	/** Links for the acronym. */
	Object links;

	/** Description for the acronym. */
	Object description;

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
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, DataTarget target) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}

		msg = new StrBuilder(50);
		Translator translator = null;
		if(target!=null){
			translator = TranslatorFactory.getTranslator(target.getTranslationTarget());
		}

		this.acShort = Utilities.getDataObject(StandardEntryKeys.ACR_SHORT, entryMap, translator);
		this.acShortOrig = Utilities.getDataObject(StandardEntryKeys.ACR_SHORT, entryMap);
		this.acLong = Utilities.getDataObject(StandardEntryKeys.ACR_LONG, entryMap, translator);

		if(keyStart==null){
			msg.appendSeparator(", ");
			msg.append("no keyStart given");
		}
		else if(!StringUtils.endsWith(keyStart, Character.toString(keySeparator))){
			msg.appendSeparator(", ");
			msg.append("wrong end of keyStart");
		}
		else{
			if(entryMap.containsKey(StandardEntryKeys.KEY.getKey())){
				this.key = keyStart + entryMap.get(StandardEntryKeys.KEY.getKey());
			}
			else{
				this.key = keyStart + this.acShort;
			}
		}

		if(this.key.contains(" ")){
			msg.appendSeparator(", ");
			msg.appendSeparator("acronym <" + this.key + "> contains illegal characters in key");
		}
//		if(StringUtils.containsAny(this.acLong, ",%'")){
//			msg.appendSeparator(", ");
//			msg.appendSeparator("acronym <" + this.key + "> contains illegal characters in long form");
//		}

		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}

		this.description = Utilities.getDataObject(StandardEntryKeys.DESCR, entryMap, translator);
		this.links = Utilities.getDataObject(StandardEntryKeys.OBJ_LINKS, entryMap);
	}

	/**
	 * Returns the short form of the acronym.
	 * @return acronym short form
	 */
	public String getShort() {
		return (String)this.acShort;
	}

	/**
	 * Returns the long form of the acronym.
	 * @return acronym long form
	 */
	public String getLong() {
		return (String)this.acLong;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	/**
	 * Returns the links associated with the acronym.
	 * @return links, null if not set
	 */
	public ObjectLinks getLinks() {
		return (ObjectLinks)this.links;
	}

	/**
	 * Returns a description for the acronym.
	 * @return acronym description, null if not set
	 */
	public String getDescription() {
		return (String)this.description;
	}

	@Override
	public String getCompareString() {
		return (String)this.acShort;
	}

	@Override
	public void setRefKeyMap(Map<String, Pair<String, String>> map) {
		// acronyms do not need that
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
}
