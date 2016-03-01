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

package de.vandermeer.skb.datatool.entries.acronyms;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the acronym entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class AcronymKeys {

	/** Key for the short version of the acronym. */
	public static EntryKey ACR_SHORT = new AbstractEntryKey("short", "short version of an acronym", String.class, true, null);

	/** Key for the long version of the acronym. */
	public static EntryKey ACR_LONG = new AbstractEntryKey("long", "long version of an acronym", String.class, true, null);

	/** Key (local) for the short version of the acronym without any translations. */
	public static EntryKey LOCAL_ACRONYM_SHORT_ORIG = new AbstractEntryKey("short", "orignal string of short version of an acronym", String.class, false, null);
}
