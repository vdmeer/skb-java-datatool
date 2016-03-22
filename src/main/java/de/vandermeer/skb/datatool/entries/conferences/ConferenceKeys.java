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

package de.vandermeer.skb.datatool.entries.conferences;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the conference entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class ConferenceKeys {

	/** Key for number of the conference. */
	public static EntryKey NUMBER = new AbstractEntryKey("#", "number of the conference", Integer.class, false, null);

	/** Key for number of the month. */
	public static EntryKey NOTES = new AbstractEntryKey("notes", "any note for the conference", String.class, false, null);

	/** Key for list of sponsors. */
	public static EntryKey SPONSORS = new AbstractEntryKey("sponsors", "a list of SKB links to affiliations naming sponsors", String.class, false, "skb://affiliations");

}
