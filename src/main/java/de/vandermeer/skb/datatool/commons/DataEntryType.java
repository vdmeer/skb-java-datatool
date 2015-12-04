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

import java.util.Set;

/**
 * A data entry type.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public interface DataEntryType {

	/**
	 * Returns the name of the type
	 * @return type name
	 */
	String getType();

	/**
	 * Returns the file extension the type is using.
	 * @return file extension
	 */
	String getInputFileExtension();

	/**
	 * Returns the full file extension (type plus JOSN).
	 * @return full type extension
	 */
	String getFullInputFileExtension();

	/**
	 * Returns the target the type supports
	 * @return supported targets, empty if none
	 */
	Set<DataTarget> getSupportedTargets();
}
