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

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.stringtemplate.v4.ST;

import de.vandermeer.execs.ExecS_Application;
import de.vandermeer.execs.options.AO_DirectoryIn;
import de.vandermeer.execs.options.AO_FileOut;
import de.vandermeer.execs.options.AO_Verbose;
import de.vandermeer.execs.options.ApplicationOption;
import de.vandermeer.execs.options.ExecS_CliParser;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileTarget;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.commons.StandardDataEntryTypes;
import de.vandermeer.skb.datatool.commons.StandardDataTargets;

/**
 * Tool to read all SKB data and cross reference if possible.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class DataTool implements ExecS_Application {

	/** Application name. */
	public final static String APP_NAME = "data-tool";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "SKB Datatool";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v0.0.1 build 150812 (12-Aug-15) for Java 1.8";

	/** Application description. */
	public final static String APP_DESCR = "Reads all SKB data from a given directory, builds cross references if possible, and generates targeted output.";

	/** Tool CLI parser and interface. */
	protected ExecS_CliParser cli;

	/** The option for the data entry type. */
	protected AO_DataEntryType optionType = new AO_DataEntryType();

	/** The option for the target. */
	protected AO_DataTarget optionTarget = new AO_DataTarget(false);

	/** The option for the input directory. */
	protected AO_DirectoryIn optionDirIn = new AO_DirectoryIn(true, "The top level directory with JSON files for the selected type.");

	/** The option for the output file. */
	protected AO_FileOut optionFileOut = new AO_FileOut(false, "The output file name, without extension.");

	/** The option for verbose mode, with extended progress messages. */
	protected AO_Verbose optionVerbose = new AO_Verbose();

	/** The options for the key separator. */
	protected AO_KeySeparator optionKeySep = new AO_KeySeparator(':', "The separator for key elements, default is ':'.");

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/**
	 * Returns a new integrated tool.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public DataTool() {
		this.verbose = false;
		Skb_Console.USE_CONSOLE = true;
		this.cli = new ExecS_CliParser();

		this.cli.addOption(this.optionType);
		this.cli.addOption(this.optionTarget);
		this.cli.addOption(this.optionDirIn);
		this.cli.addOption(this.optionFileOut);
		this.cli.addOption(this.optionVerbose);
		this.cli.addOption(this.optionKeySep);
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

		String typeCli = this.optionType.getValue();
		if(typeCli==null){
			Skb_Console.conError("{}: requires a type, try '--help entry-type' for details", this.getAppName());
			return -1;
		}
		StandardDataEntryTypes type = null;
		for(StandardDataEntryTypes sdet : StandardDataEntryTypes.values()){
			if(sdet.getType().equals(typeCli)){
				type = sdet;
				break;
			}
		}
		if(type==null){
			Skb_Console.conError("{}: unknown type <{}>", new Object[]{this.getAppName(), typeCli});
			return -1;
		}

		String targetCli = this.optionTarget.getValue();
		DataTarget target = null;
		for(StandardDataTargets sdt : StandardDataTargets.values()){
			if(sdt.getTargetName().equals(targetCli)){
				target = sdt;
				break;
			}
		}
		if(target==null){
			if(this.verbose){
				Skb_Console.conInfo("{}: no target given, will not generate output", new Object[]{this.getAppName()});
			}
		}
		if(target!=null && !type.getSupportedTargets().contains(target)){
			Skb_Console.conError("{}: target <{}> does not support type <{}>", new Object[]{this.getAppName(), targetCli, typeCli});
			return -1;
		}

		DirectoryLoader dl = new CommonsDirectoryWalker(this.optionDirIn.getValue(), DirectoryFileFilter.INSTANCE, DirectoryFileFilter.INSTANCE);
		if(dl.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors reading from directory <{}>\n{}", new Object[]{this.getAppName(), this.optionDirIn.getValue(), dl.getLoadErrors().render()});
			return -2;
		}

		if(target==null && this.optionFileOut.getValue()!=null){
			if(this.verbose){
				Skb_Console.conInfo("{}: no target given but output file specified - will ignore output file", new Object[]{this.getAppName()});
			}
		}
		FileTarget ft = null;
		if(target!=null && this.optionFileOut.getValue()!=null){
			String fn = this.optionFileOut.getValue() + "." + target.getExtension();
			FileTarget.createFile(fn);
			if(this.optionFileOut.getValue()!=null){
				ft = new FileTarget(fn);
				if(ft.getInitError().size()>0){
					Skb_Console.conError("{}: errors writing to file <{}>\n{}", new Object[]{this.getAppName(), this.optionFileOut.getValue(), ft.getInitError().render()});
					return -3;
				}
			}
		}

		if(this.verbose){
			if(target==null){
				Skb_Console.conInfo("{}: processing {} without output generation", new Object[]{this.getAppName(), type.getType()});
			}
			else if(target!=null && ft==null){
				Skb_Console.conInfo("{}: processing {} for target <{}> writing to STDOUT", new Object[]{this.getAppName(), type.getType(), target.getTargetName()});
			}
			else if(target!=null && ft!=null){
				Skb_Console.conInfo("{}: processing {} for target <{}> writing to <{}>", new Object[]{this.getAppName(), type.getType(), target.getTargetName(), (ft==null)?"standard out":ft.getAbsoluteName()});
			}
		}

		String[] excluded = null;
		if(target!=null){
			excluded = target.getExcluded();
		}
		DataLoader loader = new DataLoader(this.getAppName(), this.optionKeySep.getValue(), this.optionDirIn.getValue(), target, verbose);
		DataSet<?> entries1 = null;
		DataSet<?> entries2 = null;

		switch(type){
			case ACRONYMS:
				entries1 = loader.loadAcronyms();
				break;
			case AFFILIATIONS:
				entries1 = loader.loadAffiliations();
				break;
			case CONTINENTS:
				entries1 = loader.loadContinents();
				break;
			case COUNTRIES:
				entries1 = loader.loadCountries();
				break;
			case CITIES:
				entries1 = loader.loadCities();
				break;
			case HTML_ENTITIES:
				entries1 = loader.loadHtmlEntyties(excluded);
				break;
			case ENCODINGS:
				entries1 = loader.loadEncodings(excluded);
				entries2 = loader.loadHtmlEntyties(excluded);
				break;
			default:
				break;
		}

		if(entries1==null){
			return -4;
		}

		if(target!=null){
			ST st = ToolUtils.writeST(entries1, type, target, this.getAppName());
			if(entries2!=null){
				st = ToolUtils.addToST(entries2, st, this.getAppName());
			}
			ret = ToolUtils.writeOutput(st, ft, this.getAppName());
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
				this.optionType,
				this.optionDirIn,
				this.optionFileOut,
				this.optionTarget,
				this.optionVerbose,
				this.optionKeySep,
			};
	}

	@Override
	public ExecS_CliParser getCli(){
		return this.cli;
	}
}
