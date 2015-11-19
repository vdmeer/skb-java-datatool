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

/**
 * Encoding targets with configuration information for the tool.
 *
 * @author     Sven van der Meer &lt;sven.van.der.meer@ericsson.com&gt;
 * @version    v2.0.0 build 150826 (26-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public enum Target implements EncodingTarget {

	/** Target for SKB java - translator for text to LaTeX. */
	JAVA_SKB_T2L(
			"java-skb-t2l",
			"java",
			"de/vandermeer/skb/datatools/encodings/targets/java-skb-t2l.stg",
			new String[]{},
			"An SKB JAVA translator implementation from text to LaTeX"
	),

	/** Target for SKB java - translator for HTML to LaTeX. */
	JAVA_SKB_H2L(
			"java-skb-h2l",
			"java",
			"de/vandermeer/skb/datatools/encodings/targets/java-skb-h2l.stg",
			new String[]{
					"&", "#", ";"
			},
			"An SKB JAVA translator implementation from HTML to LaTeX"
	),

	/** Target for SKB java - translator for text to HTML. */
	JAVA_SKB_T2H(
			"html-t2h",
			"java",
			"de/vandermeer/skb/datatools/encodings/targets/java-skb-t2h.stg",
			new String[]{
					"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X","Y", "Z",
					"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
					"0", "1","2", "3", "4", "5", "6", "7", "8", "9",
					" ", "!", "$", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "=", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"
			},
			"An SKB JAVA translator implementation from text to HTML"
	),

	;

	/** Target name. */
	String targetName;

	/** File extension. */
	String extension;

	/** STG file name. */
	String stgFile;

	/** Characters that should be excluded from any code generation. */
	String[] excluded;

	/** Description. */
	String description;

	Target(String targetName, String extension, String stgFile, String[] excluded, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.stgFile = stgFile;
		this.excluded = excluded;
		this.description = description;
	}

	@Override
	public String[] getExcluded(){
		return this.excluded;
	}

	@Override
	public String getTargetName(){
		return this.targetName;
	}

	@Override
	public String getExtension(){
		return this.extension;
	}

	@Override
	public String getStgFileName(){
		return this.stgFile;
	}

	@Override
	public String getDescription(){
		return this.description;
	}
}
