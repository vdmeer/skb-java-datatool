package de.vandermeer.skb.datatool.entries;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

public class ContinentEntry implements DataEntry {

	/** The key for the continent. */
	Object key;

	/** Continent name. */
	Object name;

	/** Continent schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.GEO_CONTINENTS;

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public String getKey() {
		return (String)this.key;
	}

	/**
	 * Returns the continent name.
	 * @return continent name
	 */
	public String getName(){
		return (String)this.name;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
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

		this.key = Utilities.getDataObject(StandardEntryKeys.KEY, entryMap);
		this.name = Utilities.getDataObject(StandardEntryKeys.GEO_NAME, entryMap, translator);
	}

	@Override
	public String getCompareString() {
		return (String)this.name;
	}

	@Override
	public void setRefKeyMap(Map<String, Pair<String, String>> map) {
		// continents do not need that
	}
}
