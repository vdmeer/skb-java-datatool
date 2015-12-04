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

import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * A special data objects for links (URLs and the like).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectLinks implements EntryObject {

	/** Links schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.OBJECT_LINKS;

	/** A URL as link. */
	Object url;

	/** A link to a Wikipedia page. */
	Object wikipedia;

	@Override
	public String load(Map<String, Object> entryMap, Map<DataEntryType, Map<String, Object>> linkMap) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			return msg.toString();
		}

		this.url = Utilities.getDataObject(StandardEntryKeys.OBJ_LINKS_U, entryMap, linkMap);
		this.wikipedia = Utilities.getDataObject(StandardEntryKeys.OBJ_LINKS_W, entryMap, linkMap);

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
