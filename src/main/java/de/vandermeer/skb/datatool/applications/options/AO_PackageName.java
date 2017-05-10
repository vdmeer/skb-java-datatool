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

import de.vandermeer.execs.options.Option_TypedC_String;

/**
 * Application option "package-name", sets the (Java) package name.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public class AO_PackageName extends Option_TypedC_String {

	/**
	 * Returns the new option.
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_PackageName(){
		super(
				"Package Name",
				'p', 
				"package-name",
				true, 
				"PKG-NAME",
				false,
				"the name of the package",
				"sets the Java package name for the generated class",
				"Simply sets the name to use as 'package' name for a generateed Java class."
		);
	}

}
