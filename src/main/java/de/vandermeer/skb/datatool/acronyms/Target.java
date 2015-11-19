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

package de.vandermeer.skb.datatool.acronyms;

import de.vandermeer.skb.base.encodings.TranslatorFactory;

/**
 * Acronym targets with configuration information for the tool.
 *
 * @author     Sven van der Meer &lt;sven.van.der.meer@ericsson.com&gt;
 * @version    v2.0.0 build 150826 (26-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public enum Target implements AcronymTarget {

	/** Target for ASCIIDOC using tables. */
	latexAcr(
			"latex-acr",
			"tex",
			"de/vandermeer/skb/datatools/acronyms/targets/latex-acronym.stg",
			TranslatorFactory.Target.HTML2LaTeX,
			"LaTeX for acronym package"
	),

	/** Target for ASCIIDOC using plain text. */
	latexTab(
			"latex-tab",
			"tex",
			"de/vandermeer/skb/datatools/acronyms/targets/latex-table.stg",
			TranslatorFactory.Target.Text2LaTeX,
			"LaTeX for tabular environment"
	),

	htmlTable(
			"html-tab",
			"html",
			"de/vandermeer/skb/datatools/acronyms/targets/html-table.stg",
			TranslatorFactory.Target.Text2HTML,
			"Simple HTML table"
	),

	/** Target for ASCIIDOC using tables. */
	sqlSimple(
			"sql-simple",
			"sql",
			"de/vandermeer/skb/datatools/acronyms/targets/sql-simple.stg",
			null,//TODO
			"SQL commands for a table acronym"
	),

	/** Target for ASCIIDOC using plain text. */
	textPlain(
			"text-plain",
			"txt",
			"de/vandermeer/skb/datatools/acronyms/targets/text-plain.stg",
			null, //TODO
			"Plain text without any formatting"
	),

	;

	/** Target name. */
	String targetName;

	/** File extension. */
	String extension;

	/** STG file name. */
	String stgFile;

	/** Description. */
	String description;

	/** Target for encoding translations. */
	TranslatorFactory.Target translationTarget;

	Target(String targetName, String extension, String stgFile, TranslatorFactory.Target translationTarget, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.stgFile = stgFile;
		this.translationTarget = translationTarget;
		this.description = description;
	}

	@Override
	public TranslatorFactory.Target getTranslationTarget(){
		return this.translationTarget;
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
