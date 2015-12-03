package de.vandermeer.skb.datatool.commons;

import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

public class ObjectLinks implements EntryObject {

	/** Links schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.OBJECT_LINKS;

	/** A URL as link. */
	Object url;

	/** A link to a Wikipedia page. */
	Object wikipedia;

	@Override
	public String load(Map<String, Object> entryMap) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			return msg.toString();
		}

		this.url = Utilities.getDataObject(StandardEntryKeys.OBJ_LINKS_U, entryMap);
		this.wikipedia = Utilities.getDataObject(StandardEntryKeys.OBJ_LINKS_W, entryMap);

		return null;
	}

	/**
	 * Returns the URL link.
	 * @return URL link, null if none set
	 */
	public String getUrl(){
		return (String)this.url;
	}

	/**
	 * Returns the Wikipedia link.
	 * @return Wikipedia link, null if none set
	 */
	public String getWikipedia(){
		return (String)this.wikipedia;
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

}
