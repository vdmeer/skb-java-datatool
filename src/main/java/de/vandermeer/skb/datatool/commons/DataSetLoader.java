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

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileSourceList;

/**
 * A loader for a data set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface DataSetLoader<E extends DataEntry> {

	/**
	 * Returns the core settings.
	 * @return core settings
	 */
	CoreSettings getCs();

	/**
	 * Sets core settings for the loader.
	 * @param cs core settings
	 */
	void setCs(CoreSettings cs);

	/**
	 * Sets core settings for the loader.
	 * @param loader another loader
	 */
	default void setCs(DataSetLoader<?> loader){
		this.setCs(loader.getCs());
	}

	/**
	 * Sets loaded types this loader produced or can use.
	 * @param loadedTypes loaded data entry types
	 */
	void setLoadedTypes(LoadedTypeMap loadedTypes);

	/**
	 * Sets loaded types this loader produced or can use.
	 * @param loader another loader
	 */
	default void setLoadedTypes(DataSetLoader<?> loader){
		this.setLoadedTypes(loader.getLoadedTypes());
	}

	/**
	 * Takes settings from a loader to set local settings.
	 * @param loader another loader
	 */
	default void set(DataSetLoader<?> loader){
		this.setCs(loader);
		this.setLoadedTypes(loader);
	}

	/**
	 * Returns the loaded types this loader has.
	 * @return loaded data entry types
	 */
	LoadedTypeMap getLoadedTypes();

	/**
	 * Returns the specific data entry type for the loader.
	 * @return data entry type
	 */
	DataEntryType getDataEntryType();

	/**
	 * Loads a set of data entries.
	 * @param supportedTypes the entry types that are supported for loading
	 * @param loadedTypes types that have been already loaded
	 */
	default void load(Map<DataEntryType, DataSetLoader<?>> supportedTypes, LoadedTypeMap loadedTypes) {
		if(this.getDataEntryType().getRequiredTypes()!=null){
			for(DataEntryType dt : this.getDataEntryType().getRequiredTypes()){
				DataSetLoader<?> dsl = supportedTypes.get(dt);
				if(dsl==null){
					Skb_Console.conError("{}: loading type <{}> requires <{}>, which is not supported in system", new Object[]{this.getCs().getAppName(), this.getDataEntryType().getType(), dt.getType()});
				}
				else{
					dsl.setCs(this);
					if(!loadedTypes.containsKey(dsl.getDataEntryType())){
						dsl.load(supportedTypes, loadedTypes);
					}
				}
			}
			this.setLoadedTypes(loadedTypes);
		}
	}

	/**
	 * Writes statistics about a data entry set, for instance loaded entries and parsed files.
	 */
	default void writeStats(){
		if(this.getCs().getVerbose()==true){
			Skb_Console.conInfo("{}: parsed <{}> {} from <{}> files", new Object[]{this.getCs().getAppName(), this.getLoadedTypes().getTypeEntrySize(this.getDataEntryType()), this.getDataEntryType().getType(), getLoadedTypes().get(this.getDataEntryType()).getFileNumber()});
		}
	}

	/**
	 * Returns the main data set loaded by the loader.
	 * @return main data set
	 */
	default DataSet<?> getMainDataSet(){
		return this.getLoadedTypes().get(this.getDataEntryType());
	}

	/**
	 * Returns a secondary data set loaded by the loader.
	 * @return secondary data set, null if not applicable
	 */
	default DataSet<?> getDataSet2(){
		return null;
	}

	/**
	 * Loads a data set with entries, does consistency checks, marks errors, translates encodings.
	 * The local link map will be cleared.
	 * @param entryType the data entry type
	 * @return a fully loaded, checked data set on success, null on error (errors are logged)
	 */
	default DataSet<E> loadFiles(DataEntryType entryType){
		return this.loadFiles(entryType, null);
	}

	/**
	 * Loads a data set with entries, does consistency checks, marks errors, translates encodings.
	 * The local link map will be cleared.
	 * @param entryType the data entry type
	 * @param excluded a set of characters excluded from translations, null or empty if not applicable
	 * @return a fully loaded, checked data set on success, null on error (errors are logged)
	 */
	default DataSet<E> loadFiles(DataEntryType entryType, String[] excluded){
		IOFileFilter fileFilter = new WildcardFileFilter(new String[]{
				"*." + entryType.getInputFileExtension() + ".json"
		});
		DirectoryLoader dl = new CommonsDirectoryWalker(this.getCs().getInputDir(), DirectoryFileFilter.INSTANCE, fileFilter);
		if(dl.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading files from directory <{}>\n{}", new Object[]{this.getCs().getAppName(), this.getCs().getInputDir(), dl.getLoadErrors().render()});
			return null;
		}

		DataSet<E> ds = this.newSetInstance();
		FileSourceList fsl = dl.load();
		ds.load(fsl.getSource(), entryType.getInputFileExtension());
		return ds;
	}

	/**
	 * Returns a new instance of the data set the loader supports.
	 * @return new data set instance
	 */
	DataSet<E> newSetInstance();

	/**
	 * Returns a factory for creating data entries.
	 * @return data entry factory
	 */
	DataEntryFactory<E> getEntryFactory();
}
