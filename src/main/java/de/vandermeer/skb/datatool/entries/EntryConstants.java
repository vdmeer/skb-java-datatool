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

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Constants for entries (keys).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class EntryConstants {

	public static EntryKey EK_ACRONYM = new AbstractEntryKey("acronym", "an SKB link to an acronym", String.class, false, "skb://acronyms");

	public static EntryKey EK_DESCR = new AbstractEntryKey("descr", "description of a data entry", String.class, true, null);

	public static EntryKey EK_LATEX = new AbstractEntryKey("ltx", "latex representation", String.class, false, null);

	public static EntryKey EK_ASCII_DOC = new AbstractEntryKey("ad", "AsciiDoc representation", String.class, false, null);

	public static EntryKey EK_HTML_ENTITY = new AbstractEntryKey("he", "an HTML entity", String.class, false, null);

	public static EntryKey EKLOCAL_ACRONYM_LINK = new AbstractEntryKey("acronym-link", "orignal acronym link", String.class, false, null);

}
