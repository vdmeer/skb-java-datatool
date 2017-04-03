/* Copyright 2015 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.datatool.entries.charactermaps;

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
 * A single character map entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class CharacterMapEntry implements DataEntry {

	/** Character Map entry type. */
	public static DataEntryType ENTRY_TYPE =
			new AbstractDataEntryType(
					"character-maps", "cmap"
			)
			.addTarget(new AbstractDataTarget(CharacterMapTargetDefinitions.JAVA_TEXT_2_LATEX,    "de/vandermeer/skb/datatool/targets/character-maps/java-text2latex.stg"))
			.addTarget(new AbstractDataTarget(CharacterMapTargetDefinitions.JAVA_HTML_2_LATEX,    "de/vandermeer/skb/datatool/targets/character-maps/java-html2latex.stg"))
			.addTarget(new AbstractDataTarget(CharacterMapTargetDefinitions.JAVA_TEXT_2_HTML,     "de/vandermeer/skb/datatool/targets/character-maps/java-text2html.stg"))
			.addTarget(new AbstractDataTarget(CharacterMapTargetDefinitions.JAVA_HTML_2_ASCIIDOC, "de/vandermeer/skb/datatool/targets/character-maps/java-html2asciiddoc.stg"))
			.addTarget(new AbstractDataTarget(CharacterMapTargetDefinitions.JAVA_TEXT_2_ASCIIDOC, "de/vandermeer/skb/datatool/targets/character-maps/java-text2asciidoc.stg"))
	;

	/** Character Map schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(CharacterMapKeys.ENC_DEC, true);
				put(CharacterMapKeys.ENC_CHAR, true);
				put(EntryKeys.HTML_ENTITY, false);
				put(CharacterMapKeys.UNICODE_BLOCK, false);
				put(CharacterMapKeys.UNICODE_SET, false);
				put(EntryKeys.LATEX, false);
				put(EntryKeys.ASCII_DOC, false);
				put(EntryKeys.DESCR, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

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
		this.entryMap.put(CommonKeys.KEY, Integer.toString(this.getDec()));

		if(this.getChar()!=null){
			this.entryMap.put(CharacterMapKeys.ENC_CHAR, StringUtils.replaceEach(
					this.getChar(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			));
		}

		this.entryMap.put(CharacterMapKeys.LOCAL_HTML_CODE, String.format("&#%d;", this.getDec()));
		this.entryMap.put(CharacterMapKeys.LOCAL_CHARACTER_UC_NUMBER, String.format("U+%4H", this.getDec()));

		if(this.getLatex()!=null){
			this.entryMap.put(EntryKeys.LATEX, StringUtils.replaceEach(
					this.getLatex(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			));
		}
	}

	@Override
	public String getCompareString() {
		return this.getChar();
	}

	/**
	 * Returns the decimal number of the character.
	 * @return decimal number of the character
	 */
	public int getDec(){
		return (Integer)this.entryMap.get(CharacterMapKeys.ENC_DEC);
	}

	/**
	 * Returns textual representation of the character.
	 * @return textual representation of the character, null if not set
	 */
	public String getChar(){
		return (String)this.entryMap.get(CharacterMapKeys.ENC_CHAR);
	}

	/**
	 * Returns the HTML code set of the character.
	 * @return the HTML code set of the character, null if not set
	 */
	public String getHtmlCode(){
		return (String)this.entryMap.get(CharacterMapKeys.LOCAL_HTML_CODE);
	}

	/**
	 * Returns the HTML entity set of the character.
	 * @return the HTML entity set of the character, null if not set
	 */
	public String getHtmlEntity(){
		return (String)this.entryMap.get(EntryKeys.HTML_ENTITY);
	}

	/**
	 * Returns the Unicode number of the character.
	 * @return the Unicode number of the character, null if not set
	 */
	public String getUcNumber(){
		return (String)this.entryMap.get(CharacterMapKeys.LOCAL_CHARACTER_UC_NUMBER);
	}

	/**
	 * Returns the Unicode block of the character.
	 * @return the Unicode block of the character, null if not set
	 */
	public String getUcBlock(){
		return (String)this.entryMap.get(CharacterMapKeys.UNICODE_BLOCK);
	}

	/**
	 * Returns the Unicode set of the character.
	 * @return the Unicode set of the character, null if not set
	 */
	public String getUcSet(){
		return (String)this.entryMap.get(CharacterMapKeys.UNICODE_SET);
	}

	/**
	 * Returns LaTeX representation of the character.
	 * @return LaTeX representation of the character, null if not set
	 */
	public String getLatex(){
		return (String)this.entryMap.get(EntryKeys.LATEX);
	}

	/**
	 * Returns AsciiDoc representation of the character.
	 * Since AsciiDoc is basically a UTF-8 text, the return is the same as getChar unless an explicit AsciiDoc translation is provided.
	 * @return AsciiDoc representation of the character, null if not set
	 */
	public String getAd(){
		String ad = (String)this.entryMap.get(EntryKeys.ASCII_DOC);
		if(ad==null){
			ad = this.getChar();
		}
		return ad;
	}

	/**
	 * Returns a description for the character.
	 * @return character description, null if not set
	 */
	public String getDescription(){
		return (String)this.entryMap.get(EntryKeys.DESCR);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}

}
