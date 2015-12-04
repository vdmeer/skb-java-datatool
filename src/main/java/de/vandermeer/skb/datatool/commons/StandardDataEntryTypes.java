/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.datatool.commons;

import java.util.HashSet;
import java.util.Set;

import de.vandermeer.skb.datatool.entries.AcronymEntry;
import de.vandermeer.skb.datatool.entries.AffiliationEntry;
import de.vandermeer.skb.datatool.entries.CityEntry;
import de.vandermeer.skb.datatool.entries.ContinentEntry;
import de.vandermeer.skb.datatool.entries.CountryEntry;
import de.vandermeer.skb.datatool.entries.EncodingEntry;
import de.vandermeer.skb.datatool.entries.HtmlEntry;

/**
 * A standard set of data entry types.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public enum StandardDataEntryTypes implements DataEntryType {

	ACRONYMS ("acronyms", "acr", AcronymEntry.class),

	AFFILIATIONS ("affiliations", "aff", AffiliationEntry.class),

	CONTINENTS ("continents", "cont", ContinentEntry.class),

	COUNTRIES ("countries", "country", CountryEntry.class),

	CITIES ("cities", "city", CityEntry.class),

	ENCODINGS ("encodings", "cmap", EncodingEntry.class),

	HTML_ENTITIES ("html-entities", "hmap", HtmlEntry.class),
	;

	/** Name of the type .*/
	String type;

	/** File extension for the type, without ".json" .*/
	String inputFileExtension;

	/** The supported class of the type. */
	Class<?> typeClass;

	/** An SKB link that can be used to point to this entry type. */
	String skbLink;

	/**
	 * Creates a new data entry type.
	 * @param type the type name
	 * @param inputFileExtension the file extension
	 */
	StandardDataEntryTypes(String type, String inputFileExtension, Class<?> typeClass){
		this.type = type;
		this.inputFileExtension = inputFileExtension;
		this.typeClass = typeClass;
		this.skbLink = "skb://" + type;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getInputFileExtension() {
		return this.inputFileExtension;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends DataEntry> Class<E> getTypeClass(){
		return (Class<E>) this.typeClass;
	}

	@Override
	public Set<DataTarget> getSupportedTargets() {
		Set<DataTarget> ret = new HashSet<>();
		for(StandardDataTargets target : StandardDataTargets.values()){
			if(target.getStgFileName(this)!=null){
				ret.add(target);
			}
		}
		return ret;
	}

	//TODO JDOC
	public static StandardDataEntryTypes getTypeForLink(String skbLink){
		for(StandardDataEntryTypes t : StandardDataEntryTypes.values()){
			if(t.skbLink.equals(skbLink)){
				return t;
			}
		}
		return null;
	}
}
