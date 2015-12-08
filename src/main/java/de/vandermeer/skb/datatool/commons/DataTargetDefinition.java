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

/**
 * Definitions for a data target, for instance name (identifier) and file extension.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public interface DataTargetDefinition {

	/**
	 * Returns the entries to be excluded from processing.
	 * @return excluded characters, empty if none set
	 */
	String[] getExcluded();

	/**
	 * Returns the name of the target, as it should be used in the CLI.
	 * @return target name
	 */
	String getTargetName();

	/**
	 * Returns the required translation target.
	 * @return translation target, null if none required
	 */
	TranslatorFactory.Target getTranslationTarget();

	/**
	 * Returns the file extension used for this target.
	 * @return file extension
	 */
	String getExtension();

	/**
	 * Returns the description of the target.
	 * @return description
	 */
	String getDescription();
}
