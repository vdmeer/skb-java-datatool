package de.vandermeer.skb.datatool.commons;

import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

public class ObjectGeo  implements EntryObject {

	/** Geo schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.OBJECT_GEO;

	/** A city as name. */
	Object city;

	/** A city as SKB link. */
	Object cityLink;

	/** A city as expanded object from an SKB link. */
	Object cityExp;

	/** A country as name. */
	Object country;

	/** A country as SKB link. */
	Object countryLink;

	/** A country as expanded object from an SKB link. */
	Object countryExp;

	@Override
	public String load(Map<String, Object> entryMap) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			return msg.toString();
		}

		this.city = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_CITY, entryMap);
		this.cityLink = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_CITY_LINK, entryMap);
//		this.cityExp = TypeUtilities.getDataObject(StandardEntryKeys.OBJ_GEO_CITY, entryMap);

		this.country = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_COUNTRY, entryMap);
		this.countryLink = Utilities.getDataObject(StandardEntryKeys.OBJ_GEO_COUNTRY_LINK, entryMap);
//		this.countryExp = TypeUtilities.getDataObject(StandardEntryKeys.OBJ_GEO_CITY, entryMap);

		return null;
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	public String getCity(){
		return (String)this.city;
	}

	public String getCountry(){
		return (String)this.country;
	}

	public String getCityLink(){
		return (String)this.cityLink;
	}

	public String getCountryLink(){
		return (String)this.countryLink;
	}
}
