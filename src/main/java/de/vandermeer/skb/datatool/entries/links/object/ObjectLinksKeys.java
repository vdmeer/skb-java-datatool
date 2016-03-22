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

package de.vandermeer.skb.datatool.entries.links.object;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the object links.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectLinksKeys {

	/** Key for a normal URL. */
	public static EntryKey OBJ_LINKS_U = new AbstractEntryKey("u", "a URL inside a links object", String.class, false, null);

	/** Key for a URL to a Wikipedia article. */
	public static EntryKey OBJ_LINKS_W = new AbstractEntryKey("w", "a URL to a Wikipedia page inside a links object", String.class, false, null);
}
