package de.vandermeer.skb.datatool.commons;

import java.util.HashSet;
import java.util.Set;

public enum StandardDataEntryTypes implements DataEntryType {

	ACRONYMS ("acronyms", "acr"),

	AFFILIATIONS ("affiliations", "aff"),

	CONTINENTS ("continents", "cont"),

	COUNTRIES ("countries", "country"),

	ENCODINGS ("encodings", "cmap"),

	HTML_ENTITIES ("html-entitis", "hmap"),
	;

	String type;

	String inputFileExtension;

	StandardDataEntryTypes(String type, String inputFileExtension){
		this.type = type;
		this.inputFileExtension = inputFileExtension;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getInputFileExtension() {
		return this.inputFileExtension;
	}

	@Override
	public String getFullInputFileExtension() {
		return this.inputFileExtension + ".json";
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

}
