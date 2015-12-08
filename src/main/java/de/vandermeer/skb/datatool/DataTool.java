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

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

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
import de.vandermeer.skb.base.info.StgFileLoader;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataSetLoader;
import de.vandermeer.skb.datatool.commons.TypeLoaderMap;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntry;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntryLoader;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationEntry;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationEntryLoader;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationtypeEntry;
import de.vandermeer.skb.datatool.entries.affiliations.AffiliationtypeEntryLoader;
import de.vandermeer.skb.datatool.entries.encodings.EncodingEntry;
import de.vandermeer.skb.datatool.entries.encodings.EncodingEntryLoader;
import de.vandermeer.skb.datatool.entries.encodings.Htmlentry;
import de.vandermeer.skb.datatool.entries.encodings.HtmlentryLoader;
import de.vandermeer.skb.datatool.entries.geo.cities.CityEntry;
import de.vandermeer.skb.datatool.entries.geo.cities.CityEntryLoader;
import de.vandermeer.skb.datatool.entries.geo.continents.ContinentEntry;
import de.vandermeer.skb.datatool.entries.geo.continents.ContinentEntryLoader;
import de.vandermeer.skb.datatool.entries.geo.countries.CountryEntry;
import de.vandermeer.skb.datatool.entries.geo.countries.CountryEntryLoader;
import de.vandermeer.skb.datatool.entries.people.PeopleEntry;
import de.vandermeer.skb.datatool.entries.people.PeopleEntryLoader;
import de.vandermeer.skb.datatool.target.DataTarget;

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
	protected AO_DataEntryType optionType;

	/** The option for the target. */
	protected AO_DataTarget optionTarget;

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

	/** Map of supported entry types. */
	final TypeLoaderMap tlMap;

	/**
	 * Returns a new integrated tool.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public DataTool() {
		this.verbose = false;
		Skb_Console.USE_CONSOLE = true;

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
		this.tlMap.put(new EncodingEntryLoader());
		this.tlMap.put(new HtmlentryLoader());
		this.tlMap.put(new PeopleEntryLoader());

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

		String typeCli = this.optionType.getValue();
		if(typeCli==null){
			Skb_Console.conError("{}: requires a type, try '--help entry-type' for details", this.getAppName());
			return -1;
		}

		DataEntryType type = null;
		for(DataEntryType sdet : this.tlMap.getMap().keySet()){
			if(sdet.getType().equals(typeCli)){
				type = sdet;
				break;
			}
		}
		if(type==null){
			Skb_Console.conError("{}: unsupported type <{}>", new Object[]{this.getAppName(), typeCli});
			return -1;
		}

		DataTarget target = null;
		if(this.optionTarget.getValue()==null){
			if(this.verbose){
				Skb_Console.conInfo("{}: no target given, will not generate output", new Object[]{this.getAppName()});
			}
		}
		else{
			String targetCli = this.optionTarget.getValue();
			for(String targetName : type.getSupportedTargets().keySet()){
				if(targetName.equals(targetCli)){
					target = type.getSupportedTargets().get(targetName);
					break;
				}
			}
			if(target==null){
				Skb_Console.conError("{}: type <{}> does not support target <{}>", new Object[]{this.getAppName(), typeCli, targetCli});
				return -1;
			}
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
			String fn = this.optionFileOut.getValue() + "." + target.getDefinition().getExtension();
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
				Skb_Console.conInfo("{}: processing {} for target <{}> writing to STDOUT", new Object[]{this.getAppName(), type.getType(), target.getDefinition().getTargetName()});
			}
			else if(target!=null && ft!=null){
				Skb_Console.conInfo("{}: processing {} for target <{}> writing to <{}>", new Object[]{this.getAppName(), type.getType(), target.getDefinition().getTargetName(), (ft==null)?"standard out":ft.getAbsoluteName()});
			}
		}

		DataSetLoader<?> dsl = this.tlMap.getLoader(type);
		dsl.setInitial(this.getAppName(), this.optionKeySep.getValue(), this.optionDirIn.getValue(), target, this.verbose, this.tlMap.getMap());
		dsl.load();

		DataSet<?> entries1 = dsl.getMainDataSet();
		DataSet<?> entries2 = dsl.getDataSet2();

		if(entries1==null){
			return -4;
		}

		if(target!=null){
			ST st = this.writeST(entries1, target);
			if(entries2!=null){
				st = this.addToST(entries2, st);
			}
			ret = this.writeOutput(st, ft);
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

	/**
	 * Writes output to a file or to STDOUT.
	 * @param st the template with generated output
	 * @param ft a file target pointing to a file to write output to, will use STDOUT if null
	 * @return a negative integer if file was given but not writable, 0 on success
	 */
	public int writeOutput(ST st, FileTarget ft) {
		if(ft!=null && ft.asFile()!=null){
			try {
				FileUtils.write(ft.asFile(), st.render());
			}
			catch (IOException e) {
				Skb_Console.conError("{}: catched IO Exception <{}> -> {}", new Object[]{this.getAppName(), e.getCause(), e.getMessage()});
				return -7;
			}
		}
		else{
			System.out.println(st.render());
			return 0;
		}
		return 0;
	}

	/**
	 * Adds to an existing template.
	 * Adding means that all entries of the given data set will be added to an attribute "entry2" of the template. 
	 * @param ds data set with entries to write to the template
	 * @param st the template, must have an argument "entry2"
	 * @param <E> type of the data entry
	 * @return the template on success, null on error (errors are logged)
	 */
	public <E extends DataEntry> ST addToST(DataSet<E> ds, ST st) {
		if(st==null){
			Skb_Console.conError("{}: cannot add to ST, give ST is null", new Object[]{this.getAppName()});
			return null;
		}
		for(E entry2 : ds.getEntries()){
			st.add("entry2", entry2);
		}
		return st;
	}

	/**
	 * Writes a data set to a string template.
	 * @param ds the data set with entries
	 * @param target the target with information about the template file
	 * @param <E> type of the data entry
	 * @return a new template on success, null on error (errors are logged)
	 */
	public <E extends DataEntry> ST writeST(DataSet<E> ds, DataTarget target) {
		StgFileLoader stgLoader = new StgFileLoader(target.getStgFileName());
		if(stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{this.getAppName(), target.getStgFileName(), stgLoader.getLoadErrors().render()});
			return null;
		}

		STGroup stg = stgLoader.load();
		if(stg==null || stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{this.getAppName(), target.getStgFileName(), stgLoader.getLoadErrors().render()});
			return null;
		}
		//TODO validate STG file
		ST st = stg.getInstanceOf("build");
		for(E entry : ds.getEntries()){
				st.add("entry", entry);
		}

		return st;
	}
}
