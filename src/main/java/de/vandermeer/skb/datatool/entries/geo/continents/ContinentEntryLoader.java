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

package de.vandermeer.skb.datatool.entries.geo.continents;

import java.net.URISyntaxException;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.AbstractDataSetLoader;
import de.vandermeer.skb.datatool.commons.DataEntryFactory;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataSetLoader;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;
import de.vandermeer.skb.interfaces.MessageConsole;

/**
 * Loader for continents.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class ContinentEntryLoader extends AbstractDataSetLoader<ContinentEntry> {

	@Override
	public void load(Map<DataEntryType, DataSetLoader<?>> supportedTypes, LoadedTypeMap loadedType) {
		super.load(supportedTypes, loadedType);
		DataSet<ContinentEntry> ds = this.loadFiles(this.getDataEntryType());
		if(ds==null){
			MessageConsole.conError("{}: errors creating data set for <{}>", new Object[]{this.getCs().getAppName(), this.getDataEntryType().getType()});
			return;
		}
		loadedType.put(this.getDataEntryType(), ds);
		this.writeStats();
	}

	@Override
	public DataEntryType getDataEntryType() {
		return ContinentEntry.ENTRY_TYPE;
	}

	@Override
	public DataSet<ContinentEntry> newSetInstance() {
		return new DataSet<>(this.getCs(), this.getEntryFactory());
	}

	@Override
	public DataEntryFactory<ContinentEntry> getEntryFactory() {
		return new DataEntryFactory<ContinentEntry>() {
			
			@Override
			public ContinentEntry newInstanceLoaded(String keyStart, Map<String, Object> data) throws URISyntaxException {
				ContinentEntry ae = this.newInstance();
				ae.load(keyStart, data, getCs());
				return ae;
			}
			
			@Override
			public ContinentEntry newInstance() {
				return new ContinentEntry();
			}
		};
	}
}
