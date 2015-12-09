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

package de.vandermeer.skb.datatool.entries.encodings;

import java.net.URISyntaxException;
import java.util.Map;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.datatool.commons.AbstractDataSetLoader;
import de.vandermeer.skb.datatool.commons.DataEntryFactory;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataSetLoader;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;

/**
 * Loader for HTML entities.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class HtmlentryLoader extends AbstractDataSetLoader<Htmlentry> {

	@Override
	public void load(Map<DataEntryType, DataSetLoader<?>> supportedTypes, LoadedTypeMap loadedType) {
		super.load(supportedTypes, loadedType);
		DataSet<Htmlentry> ds = this.loadFiles(this.getDataEntryType(), this.getCs().getTarget().getDefinition().getExcluded());
		if(ds==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.getCs().getAppName(), this.getDataEntryType().getType()});
			return;
		}
		loadedType.put(this.getDataEntryType(), ds);
		this.writeStats();
	}

	@Override
	public DataEntryType getDataEntryType() {
		return Htmlentry.ENTRY_TYPE;
	}

	@Override
	public DataSet<Htmlentry> newSetInstance() {
		return new DataSet<>(this.getCs(), this.getEntryFactory());
	}

	@Override
	public DataEntryFactory<Htmlentry> getEntryFactory() {
		return new DataEntryFactory<Htmlentry>() {
			
			@Override
			public Htmlentry newInstanceLoaded(String keyStart, Map<String, Object> data) throws URISyntaxException {
				Htmlentry ae = this.newInstance();
				ae.load(keyStart, data, getCs());
				return ae;
			}
			
			@Override
			public Htmlentry newInstance() {
				return new Htmlentry();
			}
		};
	}
}
