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

package de.vandermeer.skb.datatool.entries.people;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the people entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class PeopleKeys {

	/** Key for person first name. */
	public static EntryKey PEOPLE_FIRST = new AbstractEntryKey("first", "first name of a person", String.class, true, null);

	/** Key for person middle name. */
	public static EntryKey PEOPLE_MIDDLE = new AbstractEntryKey("middle", "middle name of a person", String.class, true, null);

	/** Key for person last name. */
	public static EntryKey PEOPLE_LAST = new AbstractEntryKey("last", "last name of a person", String.class, true, null);
}
