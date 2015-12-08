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

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.LocalEntryKeys;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;

/**
 * A data entry for HTML entities (used for instance in encoding translations).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class Htmlentry implements DataEntry {

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	/** HTML entity schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.HTML_ENTITIES;

	/** A pattern to replace entities with - start. */
	static final String REPLACEMENT_PATTERN_START = "(((";

	/** A pattern to replace entities with - end. */
	static final String REPLACEMENT_PATTERN_END = ")))";

	@Override
	public String getCompareString() {
		return this.getHtmlEntity();
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
	public void loadEntry(DataLoader loader) throws URISyntaxException {
		this.entryMap = loader.loadEntry(this.schema);
		this.entryMap.put(StandardEntryKeys.KEY, this.getHtmlEntity());

		this.entryMap.put(LocalEntryKeys.HTML_REPLACEMENT, StringUtils.replace(StringUtils.replace(this.getHtmlEntity(), "<", REPLACEMENT_PATTERN_START), ">", REPLACEMENT_PATTERN_END));
//		this.htmlEntityRepl = StringUtils.replace(StringUtils.replace(this.getHtmlEntity(), "<", REPLACEMENT_PATTERN_START), ">", REPLACEMENT_PATTERN_END);

		if(this.getLatex()!=null){
			this.entryMap.put(StandardEntryKeys.LATEX, StringUtils.replaceEach(
					this.getLatex(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			));
		}

//		if(this.latex!=null){
//			this.latex = StringUtils.replaceEach(
//					(String)this.latex,
//					new String[]{"\"", "\\"},
//					new String[]{"\\\"", "\\\\"}
//			);
//		}

	}

	/**
	 * Returns the HTML entity.
	 * @return the HTML entity
	 */
	public String getHtmlEntity(){
		return (String)this.entryMap.get(StandardEntryKeys.HTML_ENTITY);
	}

	/**
	 * Returns the HTML entity replacement string.
	 * @return the HTML entity replacement string
	 */
	public String getHtmlEntityReplacement(){
		return (String)this.entryMap.get(LocalEntryKeys.HTML_REPLACEMENT);
	}

	/**
	 * Returns LaTeX representation.
	 * @return LaTeX representation, null if not set
	 */
	public String getLatex(){
		return (String)this.entryMap.get(StandardEntryKeys.LATEX);
	}

	/**
	 * Returns AsciiDoc representation.
	 * @return AsciiDoc representation, null if not set
	 */
	public String getAd(){
		return (String)this.entryMap.get(StandardEntryKeys.ASCII_DOC);
	}

	/**
	 * Returns a description for the entity.
	 * @return encoding description, null if not set
	 */
	public String getDescription(){
		return (String)this.entryMap.get(StandardEntryKeys.DESCR);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}

}
