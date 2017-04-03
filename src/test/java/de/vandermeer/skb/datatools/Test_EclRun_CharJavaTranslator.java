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

import de.vandermeer.skb.datatool.applications.CharJavaTranslatorApp;

/**
 * Tests for running the Data Tool in eclipse.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_EclRun_CharJavaTranslator {

	@Test
	public void test_Run_DataTool(){
		if("true".equals(System.getProperty("EclRun"))){
			String[] args = new String[]{
//					"--help",
//					"target",

					"--target",
//					"java-skb-h2l",
//					"java-skb-t2h",
					"java-text-2-latex",
//					"java-skb-h2ad",
//					"java-skb-t2ad",

					"--input-dir",
					"V:/dev/github/skb/data",

					"-p",
					"org.test.cmap",

//					"--verbose",

//					"--output-file",
//					"target/2",
			};

			CharJavaTranslatorApp cjt = new CharJavaTranslatorApp();
			int ret = cjt.executeApplication(args);
			if(ret!=0){
				System.err.println(ret);
			}
		}
	}

}
