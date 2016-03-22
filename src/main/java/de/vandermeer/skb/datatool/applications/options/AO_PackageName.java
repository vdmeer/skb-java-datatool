/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
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

import org.apache.commons.cli.Option;

import de.vandermeer.execs.options.AbstractApplicationOption;

/**
 * Application option "package-name", sets the (Java) package name.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.2
 */
public class AO_PackageName extends AbstractApplicationOption<String> {

	/**
	 * Returns the new option.
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_PackageName(){
		super("sets the Java package name for the generated class", "###");

		Option.Builder builder = Option.builder("p");
		builder.longOpt("package-name");
		builder.required(true);
		builder.hasArg().argName("PKG-NAME");
		this.setCliOption(builder.build());
	}

	@Override
	public String convertValue(Object value) {
		if(value==null){
			return null;
		}
		return value.toString();
	}
}
