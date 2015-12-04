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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.vandermeer.skb.base.encodings.TranslatorFactory;

/**
 * A standard set of data targets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public enum StandardDataTargets implements DataTarget {

	/** LaTeX: list of acronyms using the "acronym" environment. */
	latexAcr(
			"latex-acr",
			"tex",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatool/acronyms/targets/latex-acronym.stg");
			}},
			TranslatorFactory.Target.Text2LaTeX,
			"LaTeX for acronym package"
	),

	/** LaTeX: a simple table of data entries. */
	latexTab(
			"latex-tab",
			"tex",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatool/acronyms/targets/latex-table.stg");
			}},
			TranslatorFactory.Target.Text2LaTeX,
			"LaTeX for tabular environment"
	),

	/** HTML: a simple table of data entries. */
	htmlTable(
			"html-tab",
			"html",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatool/acronyms/targets/html-table.stg");
				put(StandardDataEntryTypes.CONTINENTS, "de/vandermeer/skb/datatool/continents/targets/html-table.stg");
				put(StandardDataEntryTypes.COUNTRIES, "de/vandermeer/skb/datatool/countries/targets/html-table.stg");
			}},
			TranslatorFactory.Target.Text2HTML,
			"Simple HTML table"
	),

	/** SQL: commands to create a table for the data entries and then fill it with all entries. */
	sqlSimple(
			"sql-simple",
			"sql",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatool/acronyms/targets/sql-simple.stg");
			}},
			(TranslatorFactory.Target)null,//TODO
			"SQL commands for a table of given type"
	),

	/** Text: simple text output, very simple formatting. */
	textPlain(
			"text-plain",
			"txt",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatool/acronyms/targets/text-plain.stg");
				put(StandardDataEntryTypes.AFFILIATIONS, "de/vandermeer/skb/datatool/affiliations/targets/text-plain.stg");
			}},
			(TranslatorFactory.Target)null, //TODO
			"Plain text without any formatting"
	),

	/** SKB Java: translator for text to LaTeX. */
	JAVA_SKB_T2L(
			"java-skb-t2l",
			"java",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ENCODINGS, "de/vandermeer/skb/datatool/encodings/targets/java-skb-t2l.stg");
			}},
			new String[]{},
			"An SKB JAVA translator implementation from text to LaTeX"
	),

	/** SKB Java: translator for HTML to LaTeX. */
	JAVA_SKB_H2L(
			"java-skb-h2l",
			"java",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ENCODINGS, "de/vandermeer/skb/datatool/encodings/targets/java-skb-h2l.stg");
			}},
			new String[]{
					"&", "#", ";"
			},
			"An SKB JAVA translator implementation from HTML to LaTeX"
	),

	/** SKB Java: translator for text to HTML. */
	JAVA_SKB_T2H(
			"java-skb-t2h",
			"java",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ENCODINGS, "de/vandermeer/skb/datatool/encodings/targets/java-skb-t2h.stg");
			}},
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

	/** A map of data entry types to STG files. */
	Map<DataEntryType, String> stgFileMap;

	/** Characters that should be excluded from any code generation. */
	String[] excluded;

	/** Description. */
	String description;

	/** Target for encoding translations. */
	TranslatorFactory.Target translationTarget;

	/**
	 * Creates a new standard data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param stgFileMap a map of ST files with supported types
	 * @param excluded a set of characters that have to be excluded from any character translation
	 * @param description a description
	 */
	StandardDataTargets(String targetName, String extension, Map<DataEntryType, String> stgFileMap, String[] excluded, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.stgFileMap = stgFileMap;
		this.excluded = excluded;
		this.description = description;
	}

	/**
	 * Creates a new standard data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param stgFileMap a map of ST files with supported types
	 * @param translationTarget a target for character translation
	 * @param description a description
	 */
	StandardDataTargets(String targetName, String extension, Map<DataEntryType, String> stgFileMap, TranslatorFactory.Target translationTarget, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.stgFileMap = stgFileMap;
		this.translationTarget = translationTarget;
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
	public TranslatorFactory.Target getTranslationTarget(){
		return this.translationTarget;
	}

	@Override
	public String getExtension(){
		return this.extension;
	}

	@Override
	public String getStgFileName(DataEntryType type){
		return this.stgFileMap.get(type);
	}

	@Override
	public String getDescription(){
		return this.description;
	}

	@Override
	public Set<DataEntryType> getSupportedTypes() {
		return this.stgFileMap.keySet();
	}

}
