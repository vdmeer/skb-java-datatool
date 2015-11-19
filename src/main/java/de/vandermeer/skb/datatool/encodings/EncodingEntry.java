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

package de.vandermeer.skb.datatool.encodings;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * A single encoding with all its definitions.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class EncodingEntry implements Comparable<EncodingEntry> {

	/** Decimal number of the encoding. */
	int dec;

	/** Textual representation of the encoding. */
	String text;

	/** HTML code for the encoding. */
	String htmlCode;

	/** HTML entity of the encoding. */
	String htmlEntity;

	/** Unicode number. */
	String ucNumber;

	/** Unicode block of the encoding. */
	String ucBlock;

	/** Unicode set of the encoding. */
	String ucSet;

	/** Description for the acronym. */
	String description;

	/** LaTeX representation of the encoding. */
	String latex;

	/** AsciiDoc representation of the encoding. */
	String ad;

	/**
	 * Returns a new encoding entry.
	 * @param map a map with information for short form, long form, URL, Wikipedia URL and description
	 * @throws IllegalArgumentException if any of the required arguments are not set or empty
	 */
	public EncodingEntry(Map<String, Object> map){
		StrBuilder msg = new StrBuilder(50);

		if(!map.containsKey("d") || map.get("d")==null || StringUtils.isEmpty(map.get("d").toString())){
			msg.appendSeparator(", ");
			msg.append("no decimal entry (d)");
		}
		else{
			this.dec = (Integer)map.get("d");
		}

		if(!map.containsKey("c") || map.get("c")==null || StringUtils.isEmpty(map.get("c").toString())){
			msg.appendSeparator(", ");
			msg.append("no character entry (c)");
		}
		else{
			this.text = StringUtils.replaceEach(
					map.get("c").toString(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			);
		}

		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}

		this.htmlCode = String.format("&#%d;", this.dec);
		this.ucNumber = String.format("U+%4H", this.dec);

		if(map.get("he")!=null){
			this.htmlEntity = map.get("he").toString();
		}

		if(map.get("l")!=null){
			this.latex = StringUtils.replaceEach(
					map.get("l").toString(),
					new String[]{"\"", "\\"},
					new String[]{"\\\"", "\\\\"}
			);
		}
		if(map.get("ad")!=null){
			this.ad = map.get("ad").toString();
		}

		if(map.get("b")!=null){
			this.ucBlock = map.get("b").toString();
		}
		if(map.get("s")!=null){
			this.ucSet = map.get("s").toString();
		}
		if(map.get("dn")!=null){
			this.description = map.get("dn").toString();
		}
	}

	/**
	 * Returns the decimal number of the encoding.
	 * @return decimal number of the encoding
	 */
	public int getDec(){
		return this.dec;
	}

	/**
	 * Returns textual representation of the encoding.
	 * @return textual representation of the encoding, null if not set
	 */
	public String getText(){
		return this.text;
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
		return this.htmlEntity;
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
		return this.ucBlock;
	}

	/**
	 * Returns the Unicode set of the encoding.
	 * @return the Unicode set of the encoding, null if not set
	 */
	public String getUcSet(){
		return this.ucSet;
	}

	/**
	 * Returns LaTeX representation of the encoding.
	 * @return LaTeX representation of the encoding, null if not set
	 */
	public String getLatex(){
		return this.latex;
	}

	/**
	 * Returns AsciiDoc representation of the encoding.
	 * @return AsciiDoc representation of the encoding, null if not set
	 */
	public String getAd(){
		return this.ad;
	}

	/**
	 * Returns a description for the acronym.
	 * @return acronym description, null if not set
	 */
	public String getDescription(){
		return this.description;
	}

	@Override
	public int compareTo(EncodingEntry o) {
		if(o==null){
			return -1;
		}
		return this.text.compareTo(o.text);
	}
}
