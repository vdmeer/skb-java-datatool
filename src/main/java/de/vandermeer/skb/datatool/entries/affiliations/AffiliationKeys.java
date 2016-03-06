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

package de.vandermeer.skb.datatool.entries.affiliations;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the affiliation entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class AffiliationKeys {

	/** Key for the type of an affiliation. */
	public static EntryKey AFF_TYPE = new AbstractEntryKey("type", "SKB link to an affiliation type", String.class, false, "skb://affiliation-types");

	/** Key for the short name of an affiliation. */
	public static EntryKey AFF_SHORT = new AbstractEntryKey("short", "short name of an affiliation as either plain text or an SKB link", String.class, true, null);//TODO Link extra

	/** Key for the long name of an affiliation. */
	public static EntryKey AFF_LONG = new AbstractEntryKey("long", "long name of an affiliation, not required if short is an SKB link to an acronym", String.class, true, null);

	/** Key for the address of an affiliation. */
	public static EntryKey AFF_ADDR = new AbstractEntryKey("addr", "address information for an affiliation", String.class, true, null);

	/** Key (local) for the original affiliation type link. */
	public static EntryKey LOCAL_AFF_TYPE_LINK = new AbstractEntryKey("type-link", "original affiliation type link", String.class, false, null);
}
