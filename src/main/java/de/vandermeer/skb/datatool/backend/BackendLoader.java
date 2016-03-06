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

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataSetLoader;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;
import de.vandermeer.skb.datatool.commons.TypeLoaderMap;
import de.vandermeer.skb.datatool.commons.target.DataTarget;

/**
 * Backend to read all SKB data and cross reference if possible.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class BackendLoader {

	/** Map of supported entry types. */
	final private TypeLoaderMap tlMap;

	/** The core settings for the process. */
	private CoreSettings cs;

	/** The main data entry type to process. */
	private DataEntryType type;

	/** The data target to process. */
	private DataTarget target;

	/** The data set loader for the required data entry. */
	private DataSetLoader<?> dsl;

	/** The input directory. */
	private String inputDir;

	/** The key separator. */
	private char keySeparator;

	/** Verbose flag. */
	private boolean verbose;

	/** Application name for logging. */
	private String appName;

	/**
	 * Creates a new backend loader
	 * @param tlMap supported type map
	 * @param inputDirectory input directory
	 * @param appName calling application name
	 * @param keySep character to separate key elements
	 * @param verbose flag for verbose mode
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public BackendLoader(TypeLoaderMap tlMap, String inputDirectory, String appName, Character keySep, boolean verbose){
		Validate.notNull(tlMap);
		this.tlMap = tlMap;

		Validate.notBlank(inputDirectory);
		DirectoryLoader dl = new CommonsDirectoryWalker(inputDirectory, DirectoryFileFilter.INSTANCE, DirectoryFileFilter.INSTANCE);
		Validate.validState(dl.getLoadErrors().size()==0, "errors reading from directory <%s>\n%s", inputDirectory, dl.getLoadErrors().render());
		this.inputDir = inputDirectory;

		this.appName = appName;
		this.keySeparator = keySep;
		this.verbose = verbose;
	}

	/**
	 * Sets the entry type
	 * @param type string naming the type
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public void setType(String type){
		Validate.notBlank(type);
		DataEntryType tt = null;
		for(DataEntryType sdet : this.tlMap.getMap().keySet()){
			if(sdet.getType().equals(type)){
				tt = sdet;
				break;
			}
		}
		this.setType(tt);
	}

	/**
	 * Sets the entry type
	 * @param type actual antry type
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public void setType(DataEntryType type){
		Validate.notNull(type, "data entry type cannot be null");
		Validate.validState(this.tlMap.getMap().containsKey(type), "unsupported type <%s>", type.getType());
		this.type = type;
	}

	/**
	 * Sets the target.
	 * @param targetReq string naming the required target
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public void setTarget(String targetReq){
		DataTarget tgt = null;
		Validate.notBlank(targetReq);
		for(String targetName : this.type.getSupportedTargets().keySet()){
			if(targetReq.equals(targetName)){
				tgt = type.getSupportedTargets().get(targetName);
				break;
			}
		}
		Validate.notNull(tgt, "type <%s> does not support target <%s>", this.type.getType(), targetReq);
		this.setTarget(tgt);
	}

	/**
	 * Sets the target.
	 * @param target actual target
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public void setTarget(DataTarget target){
		Validate.notNull(target);
		Validate.validState(this.type.getSupportedTargets().containsValue(target), "type <%s> does not support target <%s>", this.type.getType(), target.getDefinition().getTargetName());
		this.target = target;
	}

	/**
	 * Sets the core settings.
	 */
	public void setCs(){
		this.cs = new CoreSettings(this.keySeparator, this.verbose, this.appName, this.inputDir, this.target);
	}

	/**
	 * Loads the entry.
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public void loadEntry(){
		Validate.notNull(this.cs);
		Validate.notNull(this.type);
		Validate.notBlank(this.inputDir);

		this.dsl = this.tlMap.getLoader(this.type);
		this.dsl.setCs(this.cs);
		this.dsl.load(this.tlMap.getMap(), new LoadedTypeMap());
	}

	/**
	 * Returns the supported type map.
	 * @return supported type map
	 */
	public TypeLoaderMap getTlMap(){
		return this.tlMap;
	}

	/**
	 * Returns the processed data entry type.
	 * @return data entry type
	 */
	public DataEntryType getType(){
		return this.type;
	}

	/**
	 * Returns the target
	 * @return target, null if none set
	 */
	public DataTarget getTarget(){
		return this.target;
	}

	/**
	 * Returns the data set loader.
	 * @return data set loader
	 */
	public DataSetLoader<?> getDataSetLoader(){
		return this.dsl;
	}

	/**
	 * Returns the created core settings
	 * @return core settings
	 */
	public CoreSettings getCs(){
		return this.cs;
	}

	/**
	 * Returns the loaded main data set.
	 * @return main data set
	 */
	public DataSet<?> getMainDataSet(){
		if(this.dsl!=null){
			return this.dsl.getMainDataSet();
		}
		return null;
	}

	/**
	 * Returns the loaded secondary data set.
	 * @return secondary data set
	 */
	public DataSet<?> getSecondayDataSet(){
		if(this.dsl!=null){
			return this.dsl.getDataSet2();
		}
		return null;
	}
}
