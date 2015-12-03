package de.vandermeer.skb.datatool.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.vandermeer.skb.base.encodings.TranslatorFactory;

public enum StandardDataTargets implements DataTarget {

	latexAcr(
			"latex-acr",
			"tex",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatools/acronyms/targets/latex-acronym.stg");
			}},
			TranslatorFactory.Target.Text2LaTeX,
			"LaTeX for acronym package"
	),

	latexTab(
			"latex-tab",
			"tex",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatools/acronyms/targets/latex-table.stg");
			}},
			TranslatorFactory.Target.Text2LaTeX,
			"LaTeX for tabular environment"
	),

	htmlTable(
			"html-tab",
			"html",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatools/acronyms/targets/html-table.stg");
				put(StandardDataEntryTypes.CONTINENTS, "de/vandermeer/skb/datatools/continents/targets/html-table.stg");
				put(StandardDataEntryTypes.COUNTRIES, "de/vandermeer/skb/datatools/countries/targets/html-table.stg");
			}},
			TranslatorFactory.Target.Text2HTML,
			"Simple HTML table"
	),

	sqlSimple(
			"sql-simple",
			"sql",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatools/acronyms/targets/sql-simple.stg");
			}},
			(TranslatorFactory.Target)null,//TODO
			"SQL commands for a table of given type"
	),

	textPlain(
			"text-plain",
			"txt",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ACRONYMS, "de/vandermeer/skb/datatools/acronyms/targets/text-plain.stg");
				put(StandardDataEntryTypes.AFFILIATIONS, "de/vandermeer/skb/datatools/affiliations/targets/text-plain.stg");
			}},
			(TranslatorFactory.Target)null, //TODO
			"Plain text without any formatting"
	),

	/** Target for SKB java - translator for text to LaTeX. */
	JAVA_SKB_T2L(
			"java-skb-t2l",
			"java",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ENCODINGS, "de/vandermeer/skb/datatools/encodings/targets/java-skb-t2l.stg");
			}},
			new String[]{},
			"An SKB JAVA translator implementation from text to LaTeX"
	),

	/** Target for SKB java - translator for HTML to LaTeX. */
	JAVA_SKB_H2L(
			"java-skb-h2l",
			"java",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ENCODINGS, "de/vandermeer/skb/datatools/encodings/targets/java-skb-h2l.stg");
			}},
			new String[]{
					"&", "#", ";"
			},
			"An SKB JAVA translator implementation from HTML to LaTeX"
	),

	/** Target for SKB java - translator for text to HTML. */
	JAVA_SKB_T2H(
			"java-skb-t2h",
			"java",
			new HashMap<DataEntryType, String>() {private static final long serialVersionUID = 1L;{
				put(StandardDataEntryTypes.ENCODINGS, "de/vandermeer/skb/datatools/encodings/targets/java-skb-t2h.stg");
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

	StandardDataTargets(String targetName, String extension, Map<DataEntryType, String> stgFileMap, String[] excluded, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.stgFileMap = stgFileMap;
		this.excluded = excluded;
		this.description = description;
	}

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
