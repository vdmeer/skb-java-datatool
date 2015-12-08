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

package de.vandermeer.skb.datatool.commons;

import java.util.Map;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.datatool.target.DataTarget;

/**
 * A loader for a data set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public interface DataSetLoader<E extends DataEntry> {

	/**
	 * Sets the loader an initial loader.
	 * @param appName the name of the calling application
	 * @param keySep the key separator
	 * @param inputDir the input directory
	 * @param target the target
	 * @param verbose flag for verbose mode
	 * @param supportedTypes types supported by the calling object
	 */
	void setInitial(String appName, char keySep, String inputDir, DataTarget target, boolean verbose, Map<DataEntryType, DataSetLoader<?>> supportedTypes);

	/**
	 * Sets the loader for loading required entries.
	 * @param originalLoader the original loader with all relevant settings
	 */
	void setAsRequired(DataSetLoader<?> originalLoader);

	/**
	 * Returns the target set for the loader.
	 * @return set target
	 */
	DataTarget getTarget();

	/**
	 * Returns the name of the calling application.
	 * @return name of the calling application
	 */
	String getAppName();

	/**
	 * Returns the loader's data set builder.
	 * @return date set builder
	 */
	DataSetBuilder<E> getDataSetBuilder();

	/**
	 * Returns the loader's verbose flag.
	 * @return verbose flag
	 */
	boolean getVerboseFlag();

	/**
	 * Returns the specific data entry type for the loader.
	 * @return data entry type
	 */
	DataEntryType getDataEntryType();

	/**
	 * Loads a set of data entries.
	 */
	default void load() {
		if(this.getDataEntryType().getRequiredTypes()!=null){
			for(DataEntryType dt : this.getDataEntryType().getRequiredTypes()){
				DataSetLoader<?> dsl = this.getDataSetBuilder().supportedTypes.get(dt);
				if(dsl==null){
					Skb_Console.conError("{}: loading type <{}> requires <{}>, which is not supported in system", new Object[]{this.getAppName(), this.getDataEntryType().getType(), dt.getType()});
				}
				else{
					dsl.setAsRequired(this);
					if(!this.getDataSetBuilder().linkMapContainsKey(dsl.getDataEntryType())){
						dsl.load();
					}
				}
			}
		}
	}

	/**
	 * Writes statistics about a data entry set, for instance loaded entries and parsed files.
	 */
	default void writeStats(){
		if(this.getVerboseFlag()==true){
			Skb_Console.conInfo("{}: parsed <{}> {} from <{}> files", new Object[]{this.getAppName(), this.getDataSetBuilder().getLoadedTypes().getTypeEntrySize(this.getDataEntryType()), this.getDataEntryType().getType(), this.getDataSetBuilder().getLoadedTypes().get(this.getDataEntryType()).getFileNumber()});
		}
	}

	/**
	 * Returns the main data set loaded by the loader.
	 * @return main data set
	 */
	default DataSet<?> getMainDataSet(){
		return this.getDataSetBuilder().getLoadedTypes().get(this.getDataEntryType());
	}

	/**
	 * Returns a secondary data set loaded by the loader.
	 * @return secondary data set, null if not applicable
	 */
	default DataSet<?> getDataSet2(){
		return null;
	}

	/**
	 * Returns a new instance of the data entry type the loader supports.
	 * @return new instance
	 */
	DataSet<E> newSetInstance();
}
