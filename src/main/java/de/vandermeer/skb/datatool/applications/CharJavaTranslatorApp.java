/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import de.vandermeer.execs.AbstractAppliction;
import de.vandermeer.execs.options.simple.AO_Verbose;
import de.vandermeer.execs.options.typed.AO_DirectoryIn;
import de.vandermeer.execs.options.typed.AO_FileOut;
import de.vandermeer.skb.datatool.applications.options.AO_DataTarget;
import de.vandermeer.skb.datatool.applications.options.AO_PackageName;
import de.vandermeer.skb.datatool.backend.BackendLoader;
import de.vandermeer.skb.datatool.backend.BackendWriter;
import de.vandermeer.skb.datatool.commons.TypeLoaderMap;
import de.vandermeer.skb.datatool.entries.charactermaps.CharacterMapEntry;
import de.vandermeer.skb.datatool.entries.charactermaps.CharacterMapEntryLoader;
import de.vandermeer.skb.interfaces.application.ApoCliParser;
import de.vandermeer.skb.interfaces.console.MessageConsole;

/**
 * Application to process character maps and generate Java translator classes.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public class CharJavaTranslatorApp extends AbstractAppliction {

	/** Application name. */
	public final static String APP_NAME = "dt-cmap-jtrans";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "SKB Datatool - Character Map - Java Translators";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8";

	/** Application description. */
	public final static String APP_DESCR = "Processes character maps and generate Java translator classes.";

	/** The option for the input directory. */
	protected AO_DirectoryIn optionDirIn = new AO_DirectoryIn('i', true, "directory name", "sets the top level JSON directory", "The top level directory with JSON files for character maps.");

	/** The option for the output Java file. */
	protected AO_FileOut optionFileOut = new AO_FileOut('o', false, "file name", "sets output file name", "The output file name, without extension, to write the final Java class to (class name is generated from the filename).");

	/** The option for the Java package name. */
	protected AO_PackageName optionPkgName = new AO_PackageName();

	/** The option for the target. */
	protected AO_DataTarget optionTarget;

	/** The option for verbose mode, with extended progress messages. */
	protected AO_Verbose optionVerbose = new AO_Verbose('v', "Application in verbose mode");

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/** Map of supported entry types. */
	final TypeLoaderMap tlMap;

	/**
	 * Returns a new class-map translator generator tool.
	 * The object uses CLI arguments for input directory and target name.
	 * Use the executeService method to initiate a compilation manually, all arguments presented as if they are coming from command line.
	 */
	public CharJavaTranslatorApp() {
		super(APP_NAME, ApoCliParser.defaultParser(APP_NAME), null, null, null);

		this.verbose = false;
		MessageConsole.activateAll();

		this.tlMap = new TypeLoaderMap();
		this.tlMap.put(new CharacterMapEntryLoader());

		this.getCliParser().getOptions().addOption(this.optionDirIn);
		this.getCliParser().getOptions().addOption(this.optionFileOut);
		this.getCliParser().getOptions().addOption(this.optionPkgName);
		this.getCliParser().getOptions().addOption(this.optionVerbose);

		this.optionTarget = new AO_DataTarget(false, this.tlMap);
		this.getCliParser().getOptions().addOption(this.optionTarget);
	}

	@Override
	public void runApplication() {
		if(this.optionVerbose.inCli()){
			this.verbose = true;
		}

		if(this.optionPkgName.getValue()==null){
			if(this.verbose){
				MessageConsole.conError("{}: no java package name given", new Object[]{this.getAppName()});
				this.errNo = -1;
				return;
			}
		}

		try{
			BackendLoader bl = new BackendLoader(this.tlMap, this.optionDirIn.getValue(), this.getAppName(), this.verbose);
			bl.setType(CharacterMapEntry.ENTRY_TYPE);

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

			//load entries
			bl.loadEntry();
			bl.getDataSetLoader().prepareDataSet();

			//create a context object for the ST templates with Java class name and package name
			Map<String, String> context = new HashMap<>();
			context.put("javaPackage", this.optionPkgName.getValue());
			context.put("javaClassname", FilenameUtils.getBaseName(this.optionFileOut.getValue()));
			bw.writeOutput(bl, context);
		}
		catch(Exception ex){
			MessageConsole.conError("{}: {}", new Object[]{this.getAppName(), ex.getMessage()});
ex.printStackTrace();
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
