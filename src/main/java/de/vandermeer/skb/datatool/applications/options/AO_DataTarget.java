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

package de.vandermeer.skb.datatool.applications.options;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.execs.options.AbstractApplicationOption;
import de.vandermeer.skb.datatool.commons.TypeLoaderMap;

/**
 * Application option "target", sets the data target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class AO_DataTarget extends AbstractApplicationOption<String> {

	/** The entry types supported by an application for long description. */
	private TypeLoaderMap tlMap;

	/**
	 * Returns the new option.
	 * @param required true if option is required, false of it is optional
	 * @param tlMap entry types supported by an application for long description
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_DataTarget(boolean required, TypeLoaderMap tlMap){
		super("specifies a target for output generation and character conversion", "###");

		Option.Builder builder = Option.builder("t");
		builder.longOpt("target");
		builder.hasArg().argName("TARGET");
		builder.required(required);
		this.setCliOption(builder.build());
		this.tlMap = tlMap;
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
		ret.appendNewLine();
		Map<String, Set<String>> targets = this.tlMap.getTargets();
		for(Entry<String, Set<String>> entry : targets.entrySet()){
			ret.append(" - ").append(entry.getKey());
			ret.append(" -> supporting types: ");
			ret.appendWithSeparators(entry.getValue(), ", ");
			ret.appendNewLine();
		}

		ret.appendNewLine();
		return ret.toString();
	}
}
