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

package de.vandermeer.skb.datatool.entries;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A data entry for continents.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
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
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, Translator translator) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	
		msg = new StrBuilder(50);

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
