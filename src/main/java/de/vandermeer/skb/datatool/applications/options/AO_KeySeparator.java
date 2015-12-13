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

package de.vandermeer.skb.datatool.applications.options;

import org.apache.commons.cli.Option;

import de.vandermeer.execs.options.AbstractApplicationOption;

/**
 * Application option "key-sep", a string to separate keys.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 151209 (09-Dec-15) for Java 1.8
 * @since      v0.0.1
 */
public class AO_KeySeparator extends AbstractApplicationOption<Character> {

	/**
	 * Returns the new option.
	 * @param defaultValue option default value
	 * @param longDescription option long description
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_KeySeparator(Character defaultValue, String longDescription){
		super(defaultValue, "a character as separator between elements of a key", longDescription);

		Option.Builder builder = Option.builder();
		builder.longOpt("key-sep");
		builder.hasArg().argName("SEP");
		builder.required(false);
		this.setCliOption(builder.build());
	}

	@Override
	public Character convertValue(Object value) {
		if(value instanceof Character){
			return (Character)value;
		}
		else if(value instanceof String){
			String s = (String)value;
			if(s.length()==0 || s.length()>1){
				return null;
			}
			return s.toCharArray()[0];
		}
		return null;
	}

}
