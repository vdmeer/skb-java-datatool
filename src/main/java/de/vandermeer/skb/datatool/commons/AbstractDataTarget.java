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

/**
 * Abstract implementation of a data target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class AbstractDataTarget implements DataTarget {

	/** Target definition. */
	DataTargetDefinition definition;

	/** STG file name. */
	String stgFileName;

	/**
	 * Creates a new data target.
	 * @param definition the target definition
	 * @param stgFileName STG file name
	 */
	public AbstractDataTarget(DataTargetDefinition definition, String stgFileName) {
		this.definition = definition;
		this.stgFileName = stgFileName;
	}

	@Override
	public String getStgFileName() {
		return this.stgFileName;
	}

	@Override
	public DataTargetDefinition getDefinition() {
		return this.definition;
	}

}
