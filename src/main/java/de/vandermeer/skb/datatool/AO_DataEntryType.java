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

package de.vandermeer.skb.datatool;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.execs.options.AbstractApplicationOption;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataTarget;

/**
 * Application option "entry-type", sets the data entry type.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AO_DataEntryType extends AbstractApplicationOption<String> {

	/** The entry types supported by an application for long description. */
	private Set<DataEntryType<?, ?>> entryTypes;

	/**
	 * Returns the new option.
	 * @param entryTypes entry types supported by an application for long description
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_DataEntryType(Set<DataEntryType<?, ?>> entryTypes){
		super("specifies the type the tool should process", "###");

		Option.Builder builder = Option.builder("e");
		builder.longOpt("entry-type");
		builder.required(true);
		builder.hasArg().argName("TYPE");
		this.setCliOption(builder.build());

		this.entryTypes = entryTypes;
	}

	@Override
	public String convertValue(Object value) {
		if(value==null){
			return null;
		}
		return value.toString();
	}

	@Override
	public String getDescriptionLong(){
		StrBuilder ret = new StrBuilder(50);

		ret.append("This options directs the data tool to process a particular entry type. ");
		ret.append("The tool will then load all required entry types and de-reference any SKB links. ");
		ret.appendNewLine();

		ret.append("Available targets for output generation depend on the type, not all types support all targets.");
		ret.appendNewLine();

		ret.append("Available types are: ");
		for(DataEntryType<?, ?> type : this.entryTypes){
			ret.append(" - ").append(type.getType());
			ret.append(" -> supporting targets: ");
			TreeSet<String> st = new TreeSet<>();
			for(DataTarget target : type.getSupportedTargets().values()){
				st.add(target.getDefinition().getTargetName());
			}
			ret.appendWithSeparators(st, ", ");
			ret.appendNewLine();
		}

		ret.appendNewLine();
		return ret.toString();
	}
}
