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

package de.vandermeer.skb.datatool.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.TypeLoaderMap;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntry;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymEntryLoader;
import de.vandermeer.skb.datatool.entries.acronyms.AcronymUtilities;
import de.vandermeer.skb.interfaces.console.MessageConsole;

/**
 * Backend to process a LaTeX log file for acronyms.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class BackendLatexAcrLog {

	/** The log file name. */
	private String fileName;

	/** The file opbject for the log file. */
	private File file;

	/** Set of found acronyms. */
	final private Set<String> acronymsLog;

	/** How often are (any) acronyms used in the LaTeX log. */
	private int acronymsUsed;

	/** Acronym loader. */
	private BackendLoader bl;

	/** Acronym writer. */
	private BackendWriter bw;

	/**
	 * Creates a new backend.
	 * @param fileName file name of the LaTeX log file to read
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public BackendLatexAcrLog(String fileName){
		Validate.notBlank(fileName, "input file cannot be null");

		this.file = new File(fileName);
		Validate.validState(file.exists(), "input file <%s> does not exist", fileName);
		Validate.validState(file.canRead(), "input file <%s> not readable", fileName);

		this.fileName = fileName;
		this.acronymsLog = new TreeSet<>();
	}

	/**
	 * Loads and processes the the LaTeX, creates list of found acronyms.
	 * @throws IOException if there is a problem with the file read
	 */
	public void loadLatexLog() throws IOException {
		Validate.validState(this.file!=null);
		BufferedReader br = new BufferedReader(new FileReader(this.file));
		String line;
		while ((line = br.readLine()) != null) {
			if(StringUtils.contains(line, "Package acronym ")){
				if(StringUtils.contains(line, "Info: ")){
					String acro = StringUtils.substringAfter(line, "acro:");
					acro = StringUtils.substringBefore(acro, "'");
					this.acronymsLog.add(acro);
					this.acronymsUsed++;
				}
				else if(StringUtils.contains(line, "Warning: ")){
					String acro = StringUtils.substringAfter(line, "`");
					acro = StringUtils.substringBefore(acro, "'");
					this.acronymsLog.add(acro);
					this.acronymsUsed++;
				}
				else{
					System.err.println("##########");
				}
			}
		}
		br.close();
	}

	/**
	 * Prepares the backend: loader and writer.
	 * @param appName the calling application name
	 * @param verbose flag for verbose mode
	 * @param dirIn input directory for acronym entries
	 * @param fileOut output file to write results to (null means STDOUT)
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public void prepareBackend(String appName, boolean verbose, String dirIn, String fileOut){
		TypeLoaderMap tlMap = new TypeLoaderMap();
		tlMap.put(new AcronymEntryLoader());
		this.bl = new BackendLoader(tlMap, dirIn, appName, ':', verbose);
		this.bl.setType(AcronymEntry.ENTRY_TYPE);
		this.bl.setTarget("latex-acr");
		this.bl.setCs();
		this.bw = new BackendWriter(fileOut, this.bl.getCs());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Process acronyms.
	 * Lookup acronyms from LaTeX log file in the data set.
	 * Minimize data set to found acronyms.
	 */
	public void processAcronyms(){
		this.bl.loadEntry();
		if(bl.getCs().getVerbose()==true){
			MessageConsole.conInfo("{}: found <{}> acronyms in LaTeX log used <{}> times", new Object[]{this.bl.getCs().getAppName(), this.acronymsLog.size(), this.acronymsUsed});
		}

		Set<String> found = new TreeSet<>();
		Set<String> notFound = new TreeSet<>();
		Set<String> dsAcronyms = this.bl.getDataSet().getMap().keySet();

		for(String a : this.acronymsLog){
			if(dsAcronyms.contains(a)){
				found.add(a);
			}
			else{
				notFound.add(a);
			}
		}
		if(bl.getCs().getVerbose()==true){
			MessageConsole.conInfo("{}: acronyms found in data:     <{}>", new Object[]{this.bl.getCs().getAppName(), found.size()});
			MessageConsole.conInfo("{}: acronyms not found in data: <{}>", new Object[]{this.bl.getCs().getAppName(), notFound.size()});
		}

		Set<String> remove = new HashSet<String>();
		for(String a : dsAcronyms){
			if(!found.contains(a)){
				remove.add(a);
			}
		}
		for(String a : remove){
			this.bl.getDataSet().getMap().remove(a);
		}
		AcronymUtilities.setLongestAcr((DataSet<AcronymEntry>)this.bl.getDataSet());

		if(bl.getCs().getVerbose()==true){
			MessageConsole.conInfo("{}: acronyms not found are: {}", new Object[]{this.bl.getCs().getAppName(), notFound});
		}

	}

	/**
	 * Writes minimized data set to file our STDOUT.
	 * @throws IOException if writing to file failed
	 */
	public void writeOutput() throws IOException{
		this.bw.writeOutput(this.bl);
	}

	/**
	 * Returns the input file name (LaTeX log).
	 * @return LaTeX log file name
	 */
	public String getFileName(){
		return this.fileName;
	}

	/**
	 * Returns the data entry type (acronyms).
	 * @return data entry type (acronyms)
	 */
	public DataEntryType getEntryType(){
		return this.bl.getType();
	}

	/**
	 * Returns the output mode of the writer.
	 * @return writer output mode
	 */
	public String getOutputMode(){
		return this.bw.getOutputMode();
	}
}
