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

import java.util.HashMap;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.target.DataTarget;

/**
 * Abstract implementation of a data entry type.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class AbstractDataEntryType implements DataEntryType {

	/** Name of the type .*/
	String type;

	/** File extension for the type, without ".json" .*/
	String inputFileExtension;

	/** An SKB link that can be used to point to this entry type. */
	String skbLink;

	/** Map of entry types required to be loaded to process all entries (and links) of this type. */
	DataEntryType[] requiredTypes;

	/** The supported targets. */
	Map<String, DataTarget> targets;

	/**
	 * Returns a new data entry type
	 * @param type the type (name)
	 * @param inputFileExtension the supported file extension
	 */
	public AbstractDataEntryType(String type, String inputFileExtension) {
		this(type, inputFileExtension, new DataEntryType[0]);
	}

	/**
	 * Returns a new data entry type
	 * @param type the type (name)
	 * @param inputFileExtension the supported file extension
	 * @param requiredTypes set of entry types this type requires to be loaded to dereference SKB links
	 */
	public AbstractDataEntryType(String type, String inputFileExtension, DataEntryType[] requiredTypes) {
		this.type = type;
		this.inputFileExtension = inputFileExtension;
		this.requiredTypes = requiredTypes;
		this.targets = new HashMap<>();
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getInputFileExtension() {
		return this.inputFileExtension;
	}

	@Override
	public DataEntryType[] getRequiredTypes() {
		return this.requiredTypes;
	}

	@Override
	public Map<String, DataTarget> getSupportedTargets(){
		return this.targets;
	}

	public AbstractDataEntryType addTarget(DataTarget target){
		this.targets.put(target.getDefinition().getTargetName(), target);
		return this;
	}
}
