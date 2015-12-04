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
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.FileTarget;
import de.vandermeer.skb.base.info.StgFileLoader;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataTarget;

/**
 * Utility methods for the data tool.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class ToolUtils {

	/**
	 * Writes statistics about a data entry set, for instance loaded entries and parsed files.
	 * @param ds data entry set (to get stats from)
	 * @param type data entry type (to determine what the stats are about)
	 * @param appName name of the calling application
	 * @param verbose flag for printing stats, true means print, false means do not print
	 */
	public static <E extends DataEntry> void writeStats(DataSet<E> ds, String type, String appName, boolean verbose){
		if(verbose){
			Skb_Console.conInfo("{}: parsed <{}> " + type + " from <{}> files", new Object[]{appName, ds.getEntries().size(), ds.getFileNumber()});
		}
	}

	/**
	 * Writes output to a file or to STDOUT.
	 * @param st the template with generated output
	 * @param ft a file target pointing to a file to write output to, will use STDOUT if null
	 * @param appName name of the calling application
	 * @return a negative integer if file was given but not writable, 0 on success
	 */
	public static int writeOutput(ST st, FileTarget ft, String appName) {
		if(ft!=null && ft.asFile()!=null){
			try {
				FileUtils.write(ft.asFile(), st.render());
			}
			catch (IOException e) {
				Skb_Console.conError("{}: catched IO Exception <{}> -> {}", new Object[]{appName, e.getCause(), e.getMessage()});
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
	 * @param appName name of the calling application
	 * @return the template on success, null on error (errors are logged)
	 */
	public static <E extends DataEntry> ST addToST(DataSet<E> ds, ST st, String appName) {
		if(st==null){
			Skb_Console.conError("{}: cannot add to ST, give ST is null", new Object[]{appName, appName});
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
	 * @param type the entry type, required to retrieve the right template file
	 * @param target the target with information about the template file
	 * @param appName name of the calling application
	 * @return a new template on success, null on error (errors are logged)
	 */
	public static <E extends DataEntry> ST writeST(DataSet<E> ds, DataEntryType type, DataTarget target, String appName) {
		StgFileLoader stgLoader = new StgFileLoader(target.getStgFileName(type));
		if(stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{appName, target.getStgFileName(type), stgLoader.getLoadErrors().render()});
			return null;
		}

		STGroup stg = stgLoader.load();
		if(stg==null || stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{appName, target.getStgFileName(type), stgLoader.getLoadErrors().render()});
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
