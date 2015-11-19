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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import de.vandermeer.execs.ExecS_Application;
import de.vandermeer.execs.options.AO_DirectoryIn;
import de.vandermeer.execs.options.AO_FileOut;
import de.vandermeer.execs.options.AO_Target;
import de.vandermeer.execs.options.AO_Verbose;
import de.vandermeer.execs.options.ApplicationOption;
import de.vandermeer.execs.options.ExecS_CliParser;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileTarget;
import de.vandermeer.skb.base.info.StgFileLoader;

/**
 * Character maps executable service.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class Encodings implements ExecS_Application {

	/** Application name. */
	public final static String APP_NAME = "skb-dt-encodings";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "SKB Datatools: Encodings";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v0.0.1 build 150812 (12-Aug-15) for Java 1.8";

	/** Application description. */
	public final static String APP_DESCR = "Reads encodings from JSON files, validates them, and creates various output formats.";

	/** Tool CLI parser and interface. */
	protected ExecS_CliParser cli;

	/** The option for the target. */
	protected AO_Target optionTarget = new AO_Target(true, "The target defines the output target.");

	/** The option for the input directory. */
	protected AO_DirectoryIn optionDirIn = new AO_DirectoryIn(true, "The top level directory with JSON files for encodings.");

	/** The option for the output file. */
	protected AO_FileOut optionFileOut = new AO_FileOut(false, "The output file name, without extension.");

	/** The option for verbose mode, with extended progress messages. */
	protected AO_Verbose optionVerbose = new AO_Verbose();

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/** The tools' targets. */
	Map<String, EncodingTarget> targets;

	/**
	 * Returns a new Encoding object.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public Encodings() {
		this.verbose = false;
		Skb_Console.USE_CONSOLE = true;
		this.cli = new ExecS_CliParser();

		this.cli.addOption(this.optionTarget);
		this.cli.addOption(this.optionDirIn);
		this.cli.addOption(this.optionFileOut);
		this.cli.addOption(this.optionVerbose);

		this.targets = new HashMap<>();
		for(Target t : Target.values()){
			this.addTarget(t);
		}
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

		String targetCli = this.optionTarget.getValue();
		if(targetCli==null){
			Skb_Console.conError("{}: compiler requires a target", this.getAppName());
			Skb_Console.conError("{}: available targets are:", this.getAppName());
			for(Target t : Target.values()){
				Skb_Console.conError("- {} - {}", new Object[]{t.getTargetName(), t.getDescription()});
			}
			return -1;
		}

		EncodingTarget target = this.targets.get(targetCli);
		if(target==null){
			Skb_Console.conError("{}: unknown target <{}>", new Object[]{this.getAppName(), targetCli});
			return -1;
		}

		String fn = this.optionFileOut.getValue() + "." + target.getExtension();
		FileTarget.createFile(fn);
		FileTarget ft = null;
		if(this.optionFileOut.getValue()!=null){
			ft = new FileTarget(fn);
			if(ft.getInitError().size()>0){
				Skb_Console.conError("{}: errors writing to file <{}>\n{}", new Object[]{this.getAppName(), this.optionFileOut.getValue(), ft.getInitError().render()});
				return -4;
			}
		}

		IOFileFilter fileFilter = new WildcardFileFilter(new String[]{
				"*.cmap.json"
		});
		DirectoryLoader dl = new CommonsDirectoryWalker(this.optionDirIn.getValue(), DirectoryFileFilter.INSTANCE, fileFilter);
		if(dl.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading files from directory <{}>\n{}", new Object[]{this.getAppName(), this.optionDirIn.getValue(), dl.getLoadErrors().render()});
			return -4;
		}

		if(this.verbose){
			Skb_Console.conInfo("{}: processing encodings for target <{}> writing to <{}>", new Object[]{this.getAppName(), target.getTargetName(), (ft==null)?"standard out":ft.getAbsoluteName()});
		}

		try{
			ret = this.compile(dl, target, ft);
		}
		catch(Exception ignore){}

		if(this.verbose){
			Skb_Console.conInfo("{}: done", this.getAppName());
		}

		return ret;
	}

	/**
	 * Compiles all information about found encodings.
	 * @param dl a directory loader with set filters to read files from
	 * @param target target environment
	 * @param ft output file target
	 * @return 0 on success, -1 on error
	 * @throws FileNotFoundException if the file could not be written to
	 */
	protected int compile(DirectoryLoader dl, EncodingTarget target, FileTarget ft) throws FileNotFoundException {
		EncodingSet es = new EncodingSet(this.getAppName(), target.getExcluded());
		es.load(dl);
		int errors = es.validateEncodings();
		if(errors>0){
			Skb_Console.conError("{}: found <{}> errors in encoding definitions", new Object[]{this.getAppName(), errors});
		}

		if(this.verbose){
			Skb_Console.conInfo("{}: parsed <{}> encodings from <{}> files", new Object[]{this.getAppName(), es.getEncodings().size(), es.getFileNumber()});
		}

		StgFileLoader stgLoader = new StgFileLoader(target.getStgFileName());
		if(stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{this.getAppName(), target.getStgFileName(), stgLoader.getLoadErrors().render()});
			return -5;
		}

		STGroup stg = stgLoader.load();
		if(stg==null || stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{this.getAppName(), target.getStgFileName(), stgLoader.getLoadErrors().render()});
			return -6;
		}
		//TODO validate STG file

		ST st = stg.getInstanceOf("build");
		for(EncodingEntry ee : es.getEncodings()){
			if(ee.getLatex()!=null){
				st.add("ee", ee);
			}
		}

		if(ft!=null && ft.asFile()!=null){
			try {
				FileUtils.write(ft.asFile(), st.render());
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -7;
			}
		}
		else{
			System.out.println(st.render());
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
				this.optionTarget,
				this.optionVerbose};
	}

	@Override
	public ExecS_CliParser getCli(){
		return this.cli;
	}

	/**
	 * Adds a target and re-generates the target option long description.
	 * @param target new target
	 */
	public void addTarget(EncodingTarget target){
		if(target!=null){
			this.targets.put(target.getTargetName(), target);
			StrBuilder sb = new StrBuilder(100);
			for(EncodingTarget et : this.targets.values()){
				sb.append("   - ").append(et.getTargetName()).append(": ").append(et.getDescription());
			}
			this.optionTarget.setDescriptionLong("The target defines the output target for the encoding tool. Supported targets are:\n" + sb);
		}
	}
}
