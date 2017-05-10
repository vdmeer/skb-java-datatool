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

package de.vandermeer.skb.datatool.applications;

import de.vandermeer.execs.AbstractAppliction;
import de.vandermeer.execs.options.simple.AO_Verbose;
import de.vandermeer.execs.options.typed.AO_DirectoryIn;
import de.vandermeer.execs.options.typed.AO_FileIn;
import de.vandermeer.execs.options.typed.AO_FileOut;
import de.vandermeer.skb.datatool.backend.BackendLatexAcrLog;
import de.vandermeer.skb.interfaces.MessageConsole;
import de.vandermeer.skb.interfaces.application.ApoCliParser;

/**
 * Application to process acronyms and LaTeX log files creating definitions for the LaTeX package acronyms.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class LatexAcrApp extends AbstractAppliction {

	/** Application name. */
	public final static String APP_NAME = "dt-ltx-acr";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "SKB Datatool - LaTeX Acronyms";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8";

	/** Application description. */
	public final static String APP_DESCR = "Processes acronyms and LaTeX log files creating definitions for the LaTeX package acronyms.";

	/** The option for the input directory. */
	protected AO_DirectoryIn optionDirIn = new AO_DirectoryIn('i', true, "directory name", "sets the top level JSON directory", "The top level directory with JSON files for character maps.");

	/** The option for the output LaTeX file. */
	protected AO_FileOut optionFileOut = new AO_FileOut('o', false, "output file name", "LaTeX target file", "The output file name, without extension, to write the final LaTeX acronyms to.");

	/** The option for the input log file. */
	protected AO_FileIn optionFileInput= new AO_FileIn('l', true, "input file name", "LaTeX log file for acronym processing", "The LaTeX log file to scan for used/not-found acronyms.");

	/** The option for verbose mode, with extended progress messages. */
	protected AO_Verbose optionVerbose = new AO_Verbose('v', "Application in verbose mode");

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/**
	 * Returns a new latex acronym tool.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public LatexAcrApp() {
		super(APP_NAME, ApoCliParser.defaultParser(APP_NAME), null, null, null);

		this.verbose = false;
		MessageConsole.activateAll();

		this.getCliParser().getOptions().addOption(this.optionDirIn);
		this.getCliParser().getOptions().addOption(this.optionFileOut);
		this.getCliParser().getOptions().addOption(this.optionFileInput);
		this.getCliParser().getOptions().addOption(this.optionVerbose);
	}

	@Override
	public void runApplication() {
		if(this.optionVerbose.inCli()){
			this.verbose = true;
		}

		try{
			BackendLatexAcrLog llog = new BackendLatexAcrLog(this.optionFileInput.getValue());
			llog.loadLatexLog();
			llog.prepareBackend(this.getAppName(), this.verbose, this.optionDirIn.getValue(), this.optionFileOut.getValue());

			if(this.verbose){
				MessageConsole.conInfo("{}: processing <{}> {}", new Object[]{this.getAppName(), llog.getEntryType().getType(), llog.getOutputMode()});
			}

			llog.processAcronyms();
			llog.writeOutput();
		}
		catch(Exception ex){
			MessageConsole.conError("{}: {}", new Object[]{this.getAppName(), ex.getMessage()});
			this.errNo = -1;
			return;
		}

		if(this.verbose){
			MessageConsole.conInfo("{}: done", this.getAppName());
		}
	}

	@Override
	public String getAppName() {
		return APP_NAME;
	}

	@Override
	public String getAppDisplayName(){
		return APP_DISPLAY_NAME;
	}

	@Override
	public String getAppDescription() {
		return APP_DESCR;
	}

	@Override
	public String getAppVersion() {
		return APP_VERSION;
	}
}
