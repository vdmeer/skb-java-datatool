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

import de.vandermeer.execs.ExecS;
import de.vandermeer.skb.datatool.applications.CharJavaTranslatorApp;
import de.vandermeer.skb.datatool.applications.DataToolApp;
import de.vandermeer.skb.datatool.applications.HeJavaTranslatorApp;
import de.vandermeer.skb.datatool.applications.LatexAcrApp;

/**
 * The Data Tool execution service with all registered applications.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class DtExecS extends ExecS {

	/**
	 * Returns a new ES service.
	 */
	public DtExecS(){
		super("dt");

		this.addApplication(DataToolApp.APP_NAME,				DataToolApp.class);
		this.addApplication(LatexAcrApp.APP_NAME,				LatexAcrApp.class);
		this.addApplication(CharJavaTranslatorApp.APP_NAME,		CharJavaTranslatorApp.class);
		this.addApplication(HeJavaTranslatorApp.APP_NAME,		HeJavaTranslatorApp.class);
	}

	/**
	 * Main method.
	 * @param args CLI arguments
	 */
	public static void main(String[] args) {
		DtExecS run=new DtExecS();
		int ret=run.execute(args);
		System.exit(ret);
	}
}
