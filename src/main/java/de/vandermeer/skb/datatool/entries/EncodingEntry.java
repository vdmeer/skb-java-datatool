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

package de.vandermeer.skb.datatool.entries;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.StandardDataEntrySchemas;
import de.vandermeer.skb.datatool.commons.StandardEntryKeys;
import de.vandermeer.skb.datatool.commons.Utilities;

/**
 * A single encoding entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class EncodingEntry implements DataEntry {

	/** Decimal number of the encoding. */
	Object dec;

	/** Textual representation of the encoding. */
	Object text;

	/** HTML code for the encoding. */
	String htmlCode;

	/** HTML entity of the encoding. */
	Object htmlEntity;

	/** Unicode number. */
	String ucNumber;

	/** Unicode block of the encoding. */
	Object ucBlock;

	/** Unicode set of the encoding. */
	Object ucSet;

	/** Description for the encoding. */
	Object description;

	/** LaTeX representation of the encoding. */
	Object latex;

	/** AsciiDoc representation of the encoding. */
	Object ad;

	/** Encoding schema. */
	DataEntrySchema schema = StandardDataEntrySchemas.CHAR_ENCODINGS;

	@Override
	public String getKey() {
		return Integer.toString((Integer)this.dec);
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
	public void load(Map<String, Object> entryMap, String keyStart, char keySeparator, Translator translator) {
		StrBuilder msg;
		msg = this.schema.testSchema(entryMap);
		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
		msg = new StrBuilder(50);

		this.dec = Utilities.getDataObject(StandardEntryKeys.ENC_DEC, entryMap);
		this.text = Utilities.getDataObject(StandardEntryKeys.ENC_CHAR, entryMap);
		this.text = StringUtils.replaceEach(
				(String)this.text,
				new String[]{"\"", "\\"},
				new String[]{"\\\"", "\\\\"}
		);

		this.htmlCode = String.format("&#%d;", (Integer)this.dec);
		this.ucNumber = String.format("U+%4H", (Integer)this.dec);

		this.htmlEntity = Utilities.getDataObject(StandardEntryKeys.HTML_ENTITY, entryMap);

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

		this.ucBlock = Utilities.getDataObject(StandardEntryKeys.UNICODE_BLOCK, entryMap);
		this.ucSet = Utilities.getDataObject(StandardEntryKeys.UNICODE_SET, entryMap);
	}

	@Override
	public String getCompareString() {
		return (String)this.text;
	}

	/**
	 * Returns the decimal number of the encoding.
	 * @return decimal number of the encoding
	 */
	public int getDec(){
		return (Integer)this.dec;
	}

	/**
	 * Returns textual representation of the encoding.
	 * @return textual representation of the encoding, null if not set
	 */
	public String getText(){
		return (String)this.text;
	}

	/**
	 * Returns the HTML code set of the encoding.
	 * @return the HTML code set of the encoding, null if not set
	 */
	public String getHtmlCode(){
		return this.htmlCode;
	}

	/**
	 * Returns the HTML entity set of the encoding.
	 * @return the HTML entity set of the encoding, null if not set
	 */
	public String getHtmlEntity(){
		return (String)this.htmlEntity;
	}

	/**
	 * Returns the Unicode number of the encoding.
	 * @return the Unicode number of the encoding, null if not set
	 */
	public String getUcNumber(){
		return this.ucNumber;
	}

	/**
	 * Returns the Unicode block of the encoding.
	 * @return the Unicode block of the encoding, null if not set
	 */
	public String getUcBlock(){
		return (String)this.ucBlock;
	}

	/**
	 * Returns the Unicode set of the encoding.
	 * @return the Unicode set of the encoding, null if not set
	 */
	public String getUcSet(){
		return (String)this.ucSet;
	}

	/**
	 * Returns LaTeX representation of the encoding.
	 * @return LaTeX representation of the encoding, null if not set
	 */
	public String getLatex(){
		return (String)this.latex;
	}

	/**
	 * Returns AsciiDoc representation of the encoding.
	 * @return AsciiDoc representation of the encoding, null if not set
	 */
	public String getAd(){
		return (String)this.ad;
	}

	/**
	 * Returns a description for the encoding.
	 * @return encoding description, null if not set
	 */
	public String getDescription(){
		return (String)this.description;
	}

	@Override
	public void setRefKeyMap(Map<String, Pair<String, String>> map) {
		// encodings do not need that
	}
}
