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

package de.vandermeer.skb.datatool.entries.charactermaps;

import de.vandermeer.skb.datatool.commons.target.DataTargetDefinition;
import de.vandermeer.skb.interfaces.translators.TargetTranslator;

/**
 * A character map specific data targets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.2
 */
public enum CharacterMapTargetDefinitions implements DataTargetDefinition {

	/** SKB Java: translator for text to LaTeX. */
	JAVA_TEXT_2_LATEX(
			"java-text-2-latex",
			"java",
			"An SKB JAVA translator implementation from text to LaTeX"
	),

	/** SKB Java: translator for text to AsciiDoc. */
	JAVA_TEXT_2_ASCIIDOC(
			"java-text-2-asciidoc",
			"java",
			"An SKB JAVA translator implementation from text to AsciiDoc"
	),

	/** SKB Java: translator for HTML to LaTeX. */
	JAVA_HTML_2_LATEX(
			"java-html-2-latex",
			"java",
			"An SKB JAVA translator implementation from HTML to LaTeX"
	),

	/** SKB Java: translator for HTML to AsciiDoc. */
	JAVA_HTML_2_ASCIIDOC(
			"java-html-2-asciidoc",
			"java",
			"An SKB JAVA translator implementation from HTML to AsciiDoc"
	),

	/** SKB Java: translator for text to HTML. */
	JAVA_TEXT_2_HTML(
			"java-text-2-html",
			"java",
			"An SKB JAVA translator implementation from text to HTML"
	),
	;

	/** Target name. */
	String targetName;

	/** File extension. */
	String extension;

	/** Description. */
	String description;

	/**
	 * Creates a new standard data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param description a description
	 */
	CharacterMapTargetDefinitions(String targetName, String extension, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.description = description;
	}

	@Override
	public String getTargetName(){
		return this.targetName;
	}

	@Override
	public TargetTranslator getTranslator(){
		return null;
	}

	@Override
	public String getExtension(){
		return this.extension;
	}

	@Override
	public String getDescription(){
		return this.description;
	}

}
