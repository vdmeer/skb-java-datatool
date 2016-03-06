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

import de.vandermeer.execs.ExecS_Application;
import de.vandermeer.execs.options.AO_DirectoryIn;
import de.vandermeer.execs.options.AO_FileIn;
import de.vandermeer.execs.options.AO_FileOut;
import de.vandermeer.execs.options.AO_Verbose;
import de.vandermeer.execs.options.ApplicationOption;
import de.vandermeer.execs.options.ExecS_CliParser;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.datatool.backend.BackendLatexAcrLog;

/**
 * Application to process acronyms and LaTeX log files creating definitions for the LaTeX package acronyms.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class LatexAcrApp implements ExecS_Application {

	/** Application name. */
	public final static String APP_NAME = "dt-ltx-acr";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "SKB Datatool - LaTeX Acronyms";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v0.0.2-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8";

	/** Application description. */
	public final static String APP_DESCR = "Processes acronyms and LaTeX log files creating definitions for the LaTeX package acronyms.";

	/** Tool CLI parser and interface. */
	protected ExecS_CliParser cli;

	/** The option for the input directory. */
	protected AO_DirectoryIn optionDirIn = new AO_DirectoryIn(true, 'd', "The top level directory with JSON files for the selected type.");

	/** The option for the output LaTeX file. */
	protected AO_FileOut optionFileOut = new AO_FileOut(false, 'o', "The output file name, without extension, to write the final LaTeX acronyms to.");

	/** The option for the input log file. */
	protected AO_FileIn optionFileInput= new AO_FileIn(true, 'i', "The LaTeX log file to scan for used/not-found acronyms");

	/** The option for verbose mode, with extended progress messages. */
	protected AO_Verbose optionVerbose = new AO_Verbose();

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/**
	 * Returns a new latex acronym tool.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public LatexAcrApp() {
		this.verbose = false;
		Skb_Console.USE_CONSOLE = true;

		this.cli = new ExecS_CliParser();
		this.cli.addOption(this.optionDirIn);
		this.cli.addOption(this.optionFileOut);
		this.cli.addOption(this.optionFileInput);
		this.cli.addOption(this.optionVerbose);
	}

	@Override
	public int executeApplication(String[] args) {
		// parse command line, exit with help screen if error
		int ret = ExecS_Application.super.executeApplication(args);
		if(ret!=0){
			return ret;
		}

		if(this.optionVerbose.inVerboseMode()){
			this.verbose = true;
		}

		try{
			BackendLatexAcrLog llog = new BackendLatexAcrLog(this.optionFileInput.getValue());
			llog.loadLatexLog();
			llog.prepareBackend(this.getAppName(), this.verbose, this.optionDirIn.getValue(), this.optionFileOut.getValue());

			if(this.verbose){
				Skb_Console.conInfo("{}: processing <{}> {}", new Object[]{this.getAppName(), llog.getEntryType().getType(), llog.getOutputMode()});
			}

			llog.processAcronyms();
			llog.writeOutput();
		}
		catch(Exception ex){
			Skb_Console.conError("{}: {}", new Object[]{this.getAppName(), ex.getMessage()});
			return -1;
		}

		if(this.verbose){
			Skb_Console.conInfo("{}: done", this.getAppName());
		}
		return 0;
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

	@Override
	public ApplicationOption<?>[] getAppOptions() {
		return new ApplicationOption<?>[]{
				this.optionDirIn,
				this.optionFileOut,
				this.optionFileInput,
				this.optionVerbose,
			};
	}

	@Override
	public ExecS_CliParser getCli(){
		return this.cli;
	}
}
