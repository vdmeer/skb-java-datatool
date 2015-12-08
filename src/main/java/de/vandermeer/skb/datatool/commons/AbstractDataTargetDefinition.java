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

package de.vandermeer.skb.datatool.commons;

import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.base.encodings.TranslatorFactory.Target;

/**
 * Abstract implementation of a data target definition.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class AbstractDataTargetDefinition implements DataTargetDefinition {

	/** Target name. */
	String targetName;

	/** File extension. */
	String extension;

	/** Characters that should be excluded from any code generation. */
	String[] excluded;

	/** Description. */
	String description;

	/** Target for encoding translations. */
	TranslatorFactory.Target translationTarget;

	/**
	 * Creates a new data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param description a description
	 */
	public AbstractDataTargetDefinition(String targetName, String extension, String description) {
		this(targetName, extension, null, null, description);
	}

	/**
	 * Creates a new data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param translationTarget a target for character translation
	 * @param description a description
	 */
	public AbstractDataTargetDefinition(String targetName, String extension, TranslatorFactory.Target translationTarget, String description) {
		this(targetName, extension, null, translationTarget, description);
	}

	/**
	 * Creates a new data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param excluded a set of characters that have to be excluded from any character translation
	 * @param description a description
	 */
	public AbstractDataTargetDefinition(String targetName, String extension, String[] excluded, String description) {
		this(targetName, extension, excluded, null, description);
	}

	/**
	 * Creates a new data target.
	 * @param targetName the target name
	 * @param extension the file extension for output files
	 * @param excluded a set of characters that have to be excluded from any character translation
	 * @param translationTarget a target for character translation
	 * @param description a description
	 */
	public AbstractDataTargetDefinition(String targetName, String extension, String[] excluded, TranslatorFactory.Target translationTarget, String description) {
		this.targetName = targetName;
		this.extension = extension;
		this.excluded = excluded;
		this.description = description;
		this.translationTarget = translationTarget;
	}

	@Override
	public String[] getExcluded() {
		return this.excluded;
	}

	@Override
	public String getTargetName() {
		return this.targetName;
	}

	@Override
	public Target getTranslationTarget() {
		return this.translationTarget;
	}

	@Override
	public String getExtension() {
		return this.extension;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
