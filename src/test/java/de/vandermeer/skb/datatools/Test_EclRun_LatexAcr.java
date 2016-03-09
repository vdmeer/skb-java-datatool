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

import de.vandermeer.skb.datatool.applications.LatexAcrApp;

/**
 * Tests for running the Latex-Acr Application in eclipse.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class Test_EclRun_LatexAcr {

	@Test
	public void test_Run_LatexAcr(){
		if("true".equals(System.getProperty("EclRun"))){
			String[] args = new String[]{
//					"--help",

					"--input-dir",
					"V:/dev/github/skb/data",

					"--verbose",

					// for the DMS docs
//					"--input-file",
//					"V:/dev/ericsson/pristine/pristine-code/dms/doc/latex/dms.log",
//					"--output-file",
//					"V:/dev/ericsson/pristine/pristine-code/dms/doc/latex/acronyms/acronyms",

					//for the SKB-LaTeX docs
					"--input-file",
					"V:/dev/github/skb-latex/doc/skb.log",
					"--output-file",
					"V:/dev/github/skb-latex/doc/user-guide/database/acronyms",

//					"--output-file",
//					"target/2",

			};

			LatexAcrApp ltx = new LatexAcrApp();
			int ret = ltx.executeApplication(args);
			if(ret!=0){
				System.err.println(ret);
			}
		}
	}
}
