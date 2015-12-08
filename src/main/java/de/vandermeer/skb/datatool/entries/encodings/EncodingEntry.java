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

package de.vandermeer.skb.datatool.entries.encodings;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.CommonConstants;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.entries.EntryConstants;

/**
 * A single encoding entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class EncodingEntry implements DataEntry {

	/** Encoding schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(EncodingKeys.ENC_DEC, true);
				put(EncodingKeys.ENC_CHAR, true);
				put(EntryConstants.EK_HTML_ENTITY, false);
				put(EncodingKeys.UNICODE_BLOCK, false);
				put(EncodingKeys.UNICODE_SET, false);
				put(EntryConstants.EK_LATEX, false);
				put(EntryConstants.EK_ASCII_DOC, false);
				put(EntryConstants.EK_DESCR, false);
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
	public void loadEntry(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException {
		this.entryMap = loader.loadEntry(this.getSchema());
		this.entryMap.put(CommonConstants.EK_KEY, Integer.toString(this.getDec()));

		if(this.getText()!=null){
			this.entryMap.put(EncodingKeys.ENC_CHAR, StringUtils.replaceEach(
					this.getText(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			));
		}

//		this.text = StringUtils.replaceEach(
//				(String)this.text,
//				new String[]{"\"", "\\"},
//				new String[]{"\\\"", "\\\\"}
//		);

		this.entryMap.put(EncodingKeys.LOCAL_HTML_CODE, String.format("&#%d;", this.getDec()));
		this.entryMap.put(EncodingKeys.LOCAL_ENCODING_UC_NUMBER, String.format("U+%4H", this.getDec()));

//		this.htmlCode = String.format("&#%d;", this.getDec());
//		this.ucNumber = String.format("U+%4H", this.getDec());

		if(this.getLatex()!=null){
			this.entryMap.put(EntryConstants.EK_LATEX, StringUtils.replaceEach(
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

	@Override
	public String getCompareString() {
		return this.getText();
	}

	/**
	 * Returns the decimal number of the encoding.
	 * @return decimal number of the encoding
	 */
	public int getDec(){
		return (Integer)this.entryMap.get(EncodingKeys.ENC_DEC);
	}

	/**
	 * Returns textual representation of the encoding.
	 * @return textual representation of the encoding, null if not set
	 */
	public String getText(){
		return (String)this.entryMap.get(EncodingKeys.ENC_CHAR);
	}

	/**
	 * Returns the HTML code set of the encoding.
	 * @return the HTML code set of the encoding, null if not set
	 */
	public String getHtmlCode(){
		return (String)this.entryMap.get(EncodingKeys.LOCAL_HTML_CODE);
	}

	/**
	 * Returns the HTML entity set of the encoding.
	 * @return the HTML entity set of the encoding, null if not set
	 */
	public String getHtmlEntity(){
		return (String)this.entryMap.get(EntryConstants.EK_HTML_ENTITY);
	}

	/**
	 * Returns the Unicode number of the encoding.
	 * @return the Unicode number of the encoding, null if not set
	 */
	public String getUcNumber(){
		return (String)this.entryMap.get(EncodingKeys.LOCAL_ENCODING_UC_NUMBER);
	}

	/**
	 * Returns the Unicode block of the encoding.
	 * @return the Unicode block of the encoding, null if not set
	 */
	public String getUcBlock(){
		return (String)this.entryMap.get(EncodingKeys.UNICODE_BLOCK);
	}

	/**
	 * Returns the Unicode set of the encoding.
	 * @return the Unicode set of the encoding, null if not set
	 */
	public String getUcSet(){
		return (String)this.entryMap.get(EncodingKeys.UNICODE_SET);
	}

	/**
	 * Returns LaTeX representation of the encoding.
	 * @return LaTeX representation of the encoding, null if not set
	 */
	public String getLatex(){
		return (String)this.entryMap.get(EntryConstants.EK_LATEX);
	}

	/**
	 * Returns AsciiDoc representation of the encoding.
	 * @return AsciiDoc representation of the encoding, null if not set
	 */
	public String getAd(){
		return (String)this.entryMap.get(EntryConstants.EK_ASCII_DOC);
	}

	/**
	 * Returns a description for the encoding.
	 * @return encoding description, null if not set
	 */
	public String getDescription(){
		return (String)this.entryMap.get(EntryConstants.EK_DESCR);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}

}
