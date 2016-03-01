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

import java.util.Map;

import de.vandermeer.skb.datatool.commons.target.DataTarget;

/**
 * A data entry type.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
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
	 * Returns an SKB URI for the data entry type.
	 * @return SKB URI
	 */
	default String getLinkUri(){
		return "skb://" + this.getType();
	}

	/**
	 * Returns the types required to be loaded for link expansions.
	 * @return required types
	 */
	DataEntryType[] getRequiredTypes();

	/**
	 * Returns the targets supported by this entry type.
	 * @return targets as a mapping of target name (identifier) to actual target
	 */
	Map<String, DataTarget> getSupportedTargets();
}
