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

import de.vandermeer.skb.base.console.Skb_Console;

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
	 * @param cs core arguments for the loader
	 */
	void setInitial(CoreSettings cs);

	/**
	 * Sets the loader for loading required entries.
	 * @param originalLoader the original loader with all relevant settings
	 */
	void setAsRequired(DataSetLoader<?> originalLoader);

	/**
	 * Returns the core settings.
	 * @return core settings
	 */
	CoreSettings getCs();

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
				DataSetLoader<?> dsl = this.getCs().getSupportedTypes().getMap().get(dt);
				if(dsl==null){
					Skb_Console.conError("{}: loading type <{}> requires <{}>, which is not supported in system", new Object[]{this.getCs().getAppName(), this.getDataEntryType().getType(), dt.getType()});
				}
				else{
					dsl.setAsRequired(this);
					if(!this.getCs().getLoadedTypes().containsKey(dsl.getDataEntryType())){
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
		if(this.getCs().getVerbose()==true){
			Skb_Console.conInfo("{}: parsed <{}> {} from <{}> files", new Object[]{this.getCs().getAppName(), this.getCs().getLoadedTypes().getTypeEntrySize(this.getDataEntryType()), this.getDataEntryType().getType(), this.getCs().getLoadedTypes().get(this.getDataEntryType()).getFileNumber()});
		}
	}

	/**
	 * Returns the main data set loaded by the loader.
	 * @return main data set
	 */
	default DataSet<?> getMainDataSet(){
		return this.getCs().getLoadedTypes().get(this.getDataEntryType());
	}

	/**
	 * Returns a secondary data set loaded by the loader.
	 * @return secondary data set, null if not applicable
	 */
	default DataSet<?> getDataSet2(){
		return null;
	}

	/**
	 * Returns a new instance of the data set the loader supports.
	 * @return new data set instance
	 */
	DataSet<E> newSetInstance();

	/**
	 * Returns a new instance of the data entry the loader supports.
	 * @return new data entry instance
	 */
	DataEntry newEntryInstance();
}
