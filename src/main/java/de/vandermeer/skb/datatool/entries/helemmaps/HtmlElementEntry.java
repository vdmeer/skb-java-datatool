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

package de.vandermeer.skb.datatool.entries.helemmaps;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.CommonKeys;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataUtilities;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.target.AbstractDataTarget;
import de.vandermeer.skb.datatool.entries.EntryKeys;

/**
 * A data entry for HTML entities (used for instance in character translations).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class HtmlElementEntry implements DataEntry {

	/** HTML entity entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"html-entities", "hmap"
			)
			.addTarget(new AbstractDataTarget(HtmlElementTargetDefinitions.JAVA_TEXT_2_ASCIIDOC, "de/vandermeer/skb/datatool/targets/helemmaps/java-text2asciidoc.stg"))
			.addTarget(new AbstractDataTarget(HtmlElementTargetDefinitions.JAVA_TEXT_2_HTML,     "de/vandermeer/skb/datatool/targets/helemmaps/java-text2html.stg"))
			.addTarget(new AbstractDataTarget(HtmlElementTargetDefinitions.JAVA_TEXT_2_LATEX,    "de/vandermeer/skb/datatool/targets/helemmaps/java-text2latex.stg"))
	;

	/** HTML entity schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(EntryKeys.HTML_ENTITY, true);
				put(EntryKeys.LATEX, false);
				put(EntryKeys.ASCII_DOC, false);
				put(EntryKeys.DESCR, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

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
		return SCHEMA;
	}

	@Override
	public void loadEntry(String keyStart, Map<String, Object> data, CoreSettings cs) throws URISyntaxException {
		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, data, cs);
		this.entryMap.put(CommonKeys.KEY, this.getHtmlEntity());

		this.entryMap.put(HtmlElementEntryKeys.LOCAL_HTML_REPLACEMENT, StringUtils.replace(StringUtils.replace(this.getHtmlEntity(), "<", REPLACEMENT_PATTERN_START), ">", REPLACEMENT_PATTERN_END));

		if(this.getLatex()!=null){
			this.entryMap.put(EntryKeys.LATEX, StringUtils.replaceEach(
					this.getLatex(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			));
		}
	}

	/**
	 * Returns the HTML entity.
	 * @return the HTML entity
	 */
	public String getHtmlEntity(){
		return (String)this.entryMap.get(EntryKeys.HTML_ENTITY);
	}

	/**
	 * Returns the HTML entity replacement string.
	 * @return the HTML entity replacement string
	 */
	public String getHtmlEntityReplacement(){
		return (String)this.entryMap.get(HtmlElementEntryKeys.LOCAL_HTML_REPLACEMENT);
	}

	/**
	 * Returns LaTeX representation.
	 * @return LaTeX representation, null if not set
	 */
	public String getLatex(){
		return (String)this.entryMap.get(EntryKeys.LATEX);
	}

	/**
	 * Returns AsciiDoc representation.
	 * @return AsciiDoc representation, null if not set
	 */
	public String getAd(){
		return (String)this.entryMap.get(EntryKeys.ASCII_DOC);
	}

	/**
	 * Returns a description for the entity.
	 * @return description, null if not set
	 */
	public String getDescription(){
		return (String)this.entryMap.get(EntryKeys.DESCR);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}

}
