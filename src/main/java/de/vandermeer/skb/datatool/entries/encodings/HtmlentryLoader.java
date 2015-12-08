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

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.AbstractDataSetLoader;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;

/**
 * Loader and type definition for the HTML entities.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class HtmlentryLoader extends AbstractDataSetLoader<Htmlentry> {

	/** HTML entity entry type. */
	public static DataEntryType<Htmlentry, HtmlentryLoader> ENTRY_TYPE = new AbstractDataEntryType<>(
			"html-entities", "hmap", Htmlentry.class, HtmlentryLoader.class
	);

	@Override
	public void load() throws InstantiationException, IllegalAccessException {
		super.load();
		DataSet<Htmlentry> ds = this.getDataSetBuilder().build(this.getDataEntryType(), this.getTarget().getDefinition().getExcluded());
		if(ds==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.getAppName(), this.getDataEntryType().getType()});
			return;
		}
		this.getDataSetBuilder().putLinkMap(this.getDataEntryType(), ds);
		this.writeStats();
	}

	@Override
	public DataEntryType<Htmlentry, HtmlentryLoader> getDataEntryType() {
		return ENTRY_TYPE;
	}

}
