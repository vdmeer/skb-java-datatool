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

package de.vandermeer.skb.datatool;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataSetBuilder;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.commons.StandardDataEntryTypes;
import de.vandermeer.skb.datatool.entries.AcronymEntry;
import de.vandermeer.skb.datatool.entries.AcronymUtilities;
import de.vandermeer.skb.datatool.entries.AffiliationEntry;
import de.vandermeer.skb.datatool.entries.CityEntry;
import de.vandermeer.skb.datatool.entries.ContinentEntry;
import de.vandermeer.skb.datatool.entries.CountryEntry;
import de.vandermeer.skb.datatool.entries.EncodingEntry;
import de.vandermeer.skb.datatool.entries.HtmlEntry;

/**
 * Set of methods to load data sets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class DataLoader {

	/** Name of the calling application. */
	String appName;

	/** Initialized data set builder. */
	DataSetBuilder dsb;

	/** Flag for verbose mode. */
	boolean verbose;

	public DataLoader(String appName, char keySep, String dir, DataTarget target, boolean verbose) {
		this.appName = appName;
		this.verbose = verbose;

		this.dsb = new DataSetBuilder()
			.setAppName(appName)
			.setKeySeparator(keySep)
			.setDirectory(dir)
		;

		if(target!=null){
			this.dsb.setTranslator(TranslatorFactory.getTranslator(target.getTranslationTarget()));
		}
	}

	public void clearLinkMap(){
		this.dsb.cleanLinkMap();
	}

	public DataSet<AcronymEntry> loadAcronyms(){
		DataSet<AcronymEntry> ret = this.dsb.build(StandardDataEntryTypes.ACRONYMS);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.ACRONYMS.getType()});
			return ret;
		}
		AcronymUtilities.setLongestAcr(ret);
		ToolUtils.writeStats(ret, StandardDataEntryTypes.ACRONYMS.getType(), this.appName, this.verbose);
		return ret;
	}

	public DataSet<AffiliationEntry> loadAffiliations(){
		if(!this.dsb.linkMapContainsKey(StandardDataEntryTypes.ACRONYMS)){
			this.dsb.putLinkMap(StandardDataEntryTypes.ACRONYMS, this.loadAcronyms());
		}
		if(!this.dsb.linkMapContainsKey(StandardDataEntryTypes.COUNTRIES)){
			this.dsb.putLinkMap(StandardDataEntryTypes.COUNTRIES, this.loadCountries());
		}
		if(!this.dsb.linkMapContainsKey(StandardDataEntryTypes.CITIES)){
			this.dsb.putLinkMap(StandardDataEntryTypes.CITIES, this.loadCities());
		}

		DataSet<AffiliationEntry> ret = this.dsb.build(StandardDataEntryTypes.AFFILIATIONS);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.AFFILIATIONS.getType()});
			return ret;
		}
		ToolUtils.writeStats(ret, StandardDataEntryTypes.AFFILIATIONS.getType(), this.appName, this.verbose);
		return ret;
	}

	public DataSet<HtmlEntry> loadHtmlEntyties(String[] excluded){
		DataSet<HtmlEntry> ret = dsb.build(StandardDataEntryTypes.HTML_ENTITIES, excluded);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.HTML_ENTITIES.getType()});
			return ret;
		}
		ToolUtils.writeStats(ret, StandardDataEntryTypes.HTML_ENTITIES.getType(), this.appName, this.verbose);
		return ret;
	}

	public DataSet<EncodingEntry> loadEncodings(String[] excluded){
		DataSet<EncodingEntry> ret = dsb.build(StandardDataEntryTypes.ENCODINGS, excluded);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.ENCODINGS.getType()});
			return ret;
		}
		ToolUtils.writeStats(ret, StandardDataEntryTypes.ENCODINGS.getType(), this.appName, this.verbose);
		return ret;
	}

	public DataSet<ContinentEntry> loadContinents(){
		DataSet<ContinentEntry> ret = this.dsb.build(StandardDataEntryTypes.CONTINENTS);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.CONTINENTS.getType()});
			return ret;
		}
		ToolUtils.writeStats(ret, StandardDataEntryTypes.CONTINENTS.getType(), this.appName, this.verbose);
		return ret;
	}

	public DataSet<CountryEntry> loadCountries(){
		if(!this.dsb.linkMapContainsKey(StandardDataEntryTypes.CONTINENTS)){
			this.dsb.putLinkMap(StandardDataEntryTypes.CONTINENTS, this.loadContinents());
		}
		DataSet<CountryEntry> ret = this.dsb.build(StandardDataEntryTypes.COUNTRIES);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.COUNTRIES.getType()});
			return ret;
		}
		ToolUtils.writeStats(ret, StandardDataEntryTypes.COUNTRIES.getType(), this.appName, this.verbose);
		return ret;
	}

	public DataSet<CityEntry> loadCities(){
		if(!this.dsb.linkMapContainsKey(StandardDataEntryTypes.COUNTRIES)){
			this.dsb.putLinkMap(StandardDataEntryTypes.COUNTRIES, this.loadCountries());
		}
		DataSet<CityEntry> ret = this.dsb.build(StandardDataEntryTypes.CITIES);
		if(ret==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.appName, StandardDataEntryTypes.CITIES.getType()});
			return ret;
		}
		ToolUtils.writeStats(ret, StandardDataEntryTypes.CITIES.getType(), this.appName, this.verbose);
		return ret;
	}
}
