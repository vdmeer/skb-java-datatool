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

package de.vandermeer.skb.datatool.entries.acronyms;

import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.datatool.commons.target.DataTargetDefinition;

/**
 * A acronym specific data targets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.2
 */
public enum AcronymTargetDefinitions implements DataTargetDefinition {

	/** LaTeX: list of acronyms using the "acronym" environment. */
	LATEX_ACRONYMS(
			"latex-acr",
			"tex",
			TranslatorFactory.Target.Text2LaTeX,
			"LaTeX for acronym package"
	),
	;

	/** Target name. */
	String targetName;

	/** File extension. */
	String extension;

	/** Description. */
	String description;

	/** Target for encoding translations. */
	TranslatorFactory.Target translationTarget;

	/**
	 * Creates a new standard data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param translationTarget a target for character translation
	 * @param description a description
	 */
	AcronymTargetDefinitions(String targetName, String extension, TranslatorFactory.Target translationTarget, String description){
		this.targetName = targetName;
		this.extension = extension;
		this.translationTarget = translationTarget;
		this.description = description;
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
	public String getDescription(){
		return this.description;
	}

}
