/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.datatool.entries.acronyms;

import de.vandermeer.skb.datatool.commons.DataSet;

/**
 * Utility methods for dealing with acronym data entries.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public abstract class AcronymUtilities {

	/**
	 * Sets the longest acronym (plain text) in a set of acronyms.
	 * @param ds data set of acronyms
	 */
	public final static void setLongestAcr(DataSet<AcronymEntry> ds){
		String maxShort = "";
		String key = null;
		for(AcronymEntry entry : ds.getEntries()){
			if(entry.getShortOrig().length()>maxShort.length()){
				maxShort = entry.getShort();
				key = entry.getKey();
			}
		}
		ds.getMap().get(key).setLongestAcr();
	}
}
