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

package de.vandermeer.skb.datatool.entries;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.datatool.commons.AbstractDataEntryType;
import de.vandermeer.skb.datatool.commons.AbstractDataSetLoader;
import de.vandermeer.skb.datatool.commons.AbstractDataTarget;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.StandardDataTargetDefinitions;

/**
 * Loader and type definition for the affiliations.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class AffiliationEntryLoader extends AbstractDataSetLoader<AffiliationEntry> {

	/** Affiliation entry type. */
	public static DataEntryType<AffiliationEntry, AffiliationEntryLoader> ENTRY_TYPE =
			new AbstractDataEntryType<>(
					"affiliations", "aff", AffiliationEntry.class, AffiliationEntryLoader.class,
					new DataEntryType[]{
						AcronymEntryLoader.ENTRY_TYPE,
						AffiliationtypeEntryLoader.ENTRY_TYPE,
						CityEntryLoader.ENTRY_TYPE
					}
			)
			.addTarget(new AbstractDataTarget(StandardDataTargetDefinitions.TEXT_PLAIN, "de/vandermeer/skb/datatool/affiliations/targets/text-plain.stg"))
	;

	@Override
	public void load() throws InstantiationException, IllegalAccessException {
		super.load();
		DataSet<AffiliationEntry> ds = this.getDataSetBuilder().build(this.getDataEntryType());
		if(ds==null){
			Skb_Console.conError("{}: errors creating data set for <{}>", new Object[]{this.getAppName(), this.getDataEntryType().getType()});
			return;
		}
		this.getDataSetBuilder().putLinkMap(this.getDataEntryType(), ds);
		this.writeStats();
	}

	@Override
	public DataEntryType<AffiliationEntry, AffiliationEntryLoader> getDataEntryType() {
		return ENTRY_TYPE;
	}
}
