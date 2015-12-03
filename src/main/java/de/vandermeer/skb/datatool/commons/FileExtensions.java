package de.vandermeer.skb.datatool.commons;

public enum FileExtensions {

	ACRONYMS ("acr"),

	AFFILIATIONS ("aff"),

	CONTINENTS ("cont"),

	ENCODINGS ("cmap"),

	HTML_ENTITIES ("hmap"),
	;

	String extension;

	FileExtensions(String extension){
		this.extension = extension;
	}

	public String getFullExtension(){
		return this.extension + ".json";
	}

	public String getExtension(){
		return this.extension;
	}
}
