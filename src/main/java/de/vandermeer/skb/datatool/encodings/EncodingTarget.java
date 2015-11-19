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

package de.vandermeer.skb.datatool.encodings;

/**
 * An encoding targets with configuration information for the tool.
 *
 * @author     Sven van der Meer &lt;sven.van.der.meer@ericsson.com&gt;
 * @version    v2.0.0 build 150826 (26-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public interface EncodingTarget {

	/**
	 * Returns the characters to be excluded from processing.
	 * @return excluded characters, empty if none set
	 */
	String[] getExcluded();

	/**
	 * Returns the name of the target, as it should be used in the CLI.
	 * @return target name
	 */
	String getTargetName();

	/**
	 * Returns the file extension used for this target.
	 * @return file extension
	 */
	String getExtension();

	/**
	 * Returns the file name of the STG template for the target.
	 * @return STG file name
	 */
	String getStgFileName();

	/**
	 * Returns the description of the target.
	 * @return description
	 */
	String getDescription();
}
