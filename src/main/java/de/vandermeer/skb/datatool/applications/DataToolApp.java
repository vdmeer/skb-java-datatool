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
import de.vandermeer.execs.options.AO_FileOut;
import de.vandermeer.execs.options.AO_Verbose;
import de.vandermeer.execs.options.ApplicationOption;
import de.vandermeer.execs.options.ExecS_CliParser;
import de.vandermeer.skb.datatool.applications.options.AO_DataEntryType;
import de.vandermeer.skb.datatool.applications.options.AO_DataTarget;
import de.vandermeer.skb.datatool.applications.options.AO_KeySeparator;
import de.vandermeer.skb.datatool.backend.BackendLoader;
import de.vandermeer.skb.datatool.backend.BackendWriter;
import de.vandermeer.skb.datatool.commons.TypeLoaderMap;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntryLoader;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationEntryLoader;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationtypeEntryLoader;
import de.vandermeer.skb.datatool.entries.charactermaps.CharacterMapEntryLoader;
import de.vandermeer.skb.datatool.entries.conferences.ConferenceEntryLoader;
import de.vandermeer.skb.datatool.entries.date.dow.DayofweekEntryLoader;
import de.vandermeer.skb.datatool.entries.date.month.MonthEntryLoader;
import de.vandermeer.skb.datatool.entries.geo.cities.CityEntryLoader;
import de.vandermeer.skb.datatool.entries.geo.continents.ContinentEntryLoader;
import de.vandermeer.skb.datatool.entries.geo.countries.CountryEntryLoader;
import de.vandermeer.skb.datatool.entries.helemmaps.HtmlElementEntryLoader;
import de.vandermeer.skb.datatool.entries.people.PeopleEntryLoader;
import de.vandermeer.skb.interfaces.MessageConsole;

/**
 * Application to read all SKB data and cross reference if possible.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class DataToolApp implements ExecS_Application {

	/** Application name. */
	public final static String APP_NAME = "data-tool";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "SKB Datatool";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8";

	/** Application description. */
	public final static String APP_DESCR = "Reads all SKB data from a given directory, builds cross references if possible, and generates targeted output.";

	/** Tool CLI parser and interface. */
	protected ExecS_CliParser cli;

	/** The option for the data entry type. */
	protected AO_DataEntryType optionType;

	/** The option for the target. */
	protected AO_DataTarget optionTarget;

	/** The option for the input directory. */
	protected AO_DirectoryIn optionDirIn = new AO_DirectoryIn(true, 'd', "The top level directory with JSON files for the selected type.");

	/** The option for the output file. */
	protected AO_FileOut optionFileOut = new AO_FileOut(false, 'o', "The output file name, without extension.");

	/** The option for verbose mode, with extended progress messages. */
	protected AO_Verbose optionVerbose = new AO_Verbose();

	/** The options for the key separator. */
	protected AO_KeySeparator optionKeySep = new AO_KeySeparator(':', "The separator for key elements, default is ':'.");

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/** Map of supported entry types. */
	final TypeLoaderMap tlMap;

	/**
	 * Returns a new data tool.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public DataToolApp() {
		this.verbose = false;
		MessageConsole.PRINT_MESSAGES = true;

		this.cli = new ExecS_CliParser();
		this.cli.addOption(this.optionDirIn);
		this.cli.addOption(this.optionFileOut);
		this.cli.addOption(this.optionVerbose);
		this.cli.addOption(this.optionKeySep);

		this.tlMap = new TypeLoaderMap();
		this.tlMap.put(new AcronymEntryLoader());
		this.tlMap.put(new AffiliationtypeEntryLoader());
		this.tlMap.put(new AffiliationEntryLoader());
		this.tlMap.put(new CityEntryLoader());
		this.tlMap.put(new CountryEntryLoader());
		this.tlMap.put(new ContinentEntryLoader());
		this.tlMap.put(new CharacterMapEntryLoader());
		this.tlMap.put(new HtmlElementEntryLoader());
		this.tlMap.put(new PeopleEntryLoader());
		this.tlMap.put(new MonthEntryLoader());
		this.tlMap.put(new DayofweekEntryLoader());
		this.tlMap.put(new ConferenceEntryLoader());

		this.optionType = new AO_DataEntryType(this.tlMap);
		this.cli.addOption(this.optionType);
		this.optionTarget = new AO_DataTarget(false, this.tlMap);
		this.cli.addOption(this.optionTarget);
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
			BackendLoader bl = new BackendLoader(this.tlMap, this.optionDirIn.getValue(), this.getAppName(), this.optionKeySep.getValue(), this.verbose);
			bl.setType(this.optionType.getValue());

			if(this.optionTarget.getValue()==null){
				if(this.verbose){
					MessageConsole.conInfo("{}: no target given, will not generate output", new Object[]{this.getAppName()});
				}
			}
			else{
				bl.setTarget(this.optionTarget.getValue());
			}

			if(bl.getTarget()==null && this.optionFileOut.getValue()!=null){
				if(this.verbose){
					MessageConsole.conInfo("{}: no target given but output file specified - will ignore output file", new Object[]{this.getAppName()});
				}
			}

			bl.setCs();
			BackendWriter bw = new BackendWriter(this.optionFileOut.getValue(), bl.getCs());
			if(this.verbose){
				MessageConsole.conInfo("{}: processing <{}> {}", new Object[]{this.getAppName(), bl.getType().getType(), bw.getOutputMode()});
			}

			bl.loadEntry();
			bl.getDataSetLoader().prepareDataSet();
			bw.writeOutput(bl);
		}
		catch(Exception ex){
			MessageConsole.conError("{}: {}", new Object[]{this.getAppName(), ex.getMessage()});
//TODO add a print-stack-trace option
//System.err.println("@: " + ex.getCause());
//System.err.println("#: " + ex.getMessage());
ex.printStackTrace();
			return -1;
		}

		if(this.verbose){
			MessageConsole.conInfo("{}: done", this.getAppName());
		}
		return ret;
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
