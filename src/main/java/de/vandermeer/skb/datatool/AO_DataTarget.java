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

import java.util.TreeSet;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.execs.options.AbstractApplicationOption;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.StandardDataTargets;

/**
 * Application option "target", sets the data target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AO_DataTarget extends AbstractApplicationOption<String> {

	/**
	 * Returns the new option.
	 * @param required true if option is required, false of it is optional
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_DataTarget(boolean required){
		super("specifies a target for output generation and character conversion", "###");

		Option.Builder builder = Option.builder("t");
		builder.longOpt("target");
		builder.hasArg().argName("TARGET");
		builder.required(required);
		this.setCliOption(builder.build());
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

		ret.append("This options sets the target for the data tool. ");
		ret.append("A target is important for two things: character conversion and output generation. ");
		ret.append("Since most text is given as UTF-8, targets such as HTML and LaTeX will need a conversion of some characters into their specific format. ");
		ret.append("For auto-generating output, the target will have all information required to generate a file in the target language, for instance file extension, special rules for character translation, and a template for output generation. ");
		ret.appendNewLine();

		ret.append("Targets support specific data entry types. ");
		ret.append("Not all targets support all data entry types. ");
		ret.append("The selected target must support the selected data entry type. ");
		ret.appendNewLine();

		ret.append("Available targets are: ");
		for(StandardDataTargets target : StandardDataTargets.values()){
			ret.append(" - ").append(target.getTargetName());
			ret.append(" -> supporting types: ");
			TreeSet<String> st = new TreeSet<>();
			for(DataEntryType type : target.getSupportedTypes()){
				st.add(type.getType());
			}
			ret.appendWithSeparators(st, ", ");
			ret.appendNewLine();
		}

		ret.appendNewLine();
		return ret.toString();
	}
}
