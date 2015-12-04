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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A data entry for HTML entities (used for instance in encoding translations).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class HtmlEntry implements DataEntry {

	/** HTML entity. */
	Object htmlEntity;

	/** HTML entity replacement string. */
	String htmlEntityRepl;

	/** Description for the entity. */
	Object description;

	/** LaTeX representation of the encoding. */
	Object latex;

	/** AsciiDoc representation of the encoding. */
	Object ad;

	/** HTML entity schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.HTML_ENTITIES;

	/** A pattern to replace entities with - start. */
	static final String REPLACEMENT_PATTERN_START = "(((";

	/** A pattern to replace entities with - end. */
	static final String REPLACEMENT_PATTERN_END = ")))";

	@Override
	public String getKey() {
		return (String)this.htmlEntity;
	}

	@Override
	public String getCompareString() {
		return (String)this.htmlEntity;
	}

	@Override
	public String testDuplicate(Collection<DataEntry> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataEntrySchema getSchema(){
		return this.schema;
	}

	@Override
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, DataTarget target) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}

		this.htmlEntity = Utilities.getDataObject(StandardEntryKeys.HTML_ENTITY, entryMap);
		this.htmlEntityRepl = StringUtils.replace(StringUtils.replace((String)this.htmlEntity, "<", REPLACEMENT_PATTERN_START), ">", REPLACEMENT_PATTERN_END);

		this.latex = Utilities.getDataObject(StandardEntryKeys.LATEX, entryMap);
		if(this.latex!=null){
			this.latex = StringUtils.replaceEach(
					(String)this.latex,
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			);
		}

		this.ad = Utilities.getDataObject(StandardEntryKeys.ASCII_DOC, entryMap);
		this.description = Utilities.getDataObject(StandardEntryKeys.DESCR, entryMap);
	}

	/**
	 * Returns the HTML entity.
	 * @return the HTML entity
	 */
	public String getHtmlEntity(){
		return (String)this.htmlEntity;
	}

	/**
	 * Returns the HTML entity replacement string.
	 * @return the HTML entity replacement string
	 */
	public String getHtmlEntityRepl(){
		return this.htmlEntityRepl;
	}

	/**
	 * Returns LaTeX representation.
	 * @return LaTeX representation, null if not set
	 */
	public String getLatex(){
		return (String)this.latex;
	}

	/**
	 * Returns AsciiDoc representation.
	 * @return AsciiDoc representation, null if not set
	 */
	public String getAd(){
		return (String)this.ad;
	}

	/**
	 * Returns a description for the entity.
	 * @return encoding description, null if not set
	 */
	public String getDescription(){
		return (String)this.description;
	}

	@Override
	public void setRefKeyMap(Map<String, Pair<String, String>> map) {
		// HTML entries do not need that
	}
}
