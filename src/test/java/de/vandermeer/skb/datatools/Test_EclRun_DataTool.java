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

package de.vandermeer.skb.datatools;

import org.junit.Test;

import de.vandermeer.skb.datatool.DataTool;

/**
 * Tests for running the Data Tool in eclipse.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class Test_EclRun_DataTool {

	@Test
	public void test_Run_DataTool(){
		if("true".equals(System.getProperty("EclRun"))){
			String[] args = new String[]{
//					"--help",
//					"target",
//					"entry-type",

					"--entry-type",
//					"acronyms",
					"affiliations",
//					"affiliation-types",
//					"people",
//					"continents",
//					"countries",
//					"cities",
//					"encodings",

					"--target",
//					"html-tab",
//					"latex-acr",
					"text-plain",
//					"java-skb-h2l",
//					"java-skb-t2h",
//					"java-skb-t2l",

					"--input-dir",
					"V:/dev/github/skb/data",

					"--verbose",

//					"--output-file",
//					"target/2",

//					"--key-sep",
//					"/"
			};
			DataTool dt = new DataTool();
			int ret = dt.executeApplication(args);
			if(ret!=0){
				System.err.println(ret);
			}
		}
	}

}
