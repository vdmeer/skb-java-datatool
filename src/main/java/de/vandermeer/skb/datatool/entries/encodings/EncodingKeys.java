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

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;

/**
 * Keys used by the encoding entry.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 151209 (09-Dec-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class EncodingKeys {

	/** Key for the decimal of an encoding. */
	public static EntryKey ENC_DEC = new AbstractEntryKey("d", "decimal value of a character encoding", Integer.class, false, null);

	/** Key for the chatacter (text) of an encoding. */
	public static EntryKey ENC_CHAR = new AbstractEntryKey("c", "character representation of an encoding", String.class, false, null);

	/** Key for the Unicode bloc of an encoding. */
	public static EntryKey UNICODE_BLOCK = new AbstractEntryKey("b", "a Unicode block", String.class, false, null);

	/** Key for the Unicode set of an encoding. */
	public static EntryKey UNICODE_SET = new AbstractEntryKey("s", "a Unicode set", String.class, false, null);

	/** Key (local) for the Unicode number of an encoding. */
	public static EntryKey LOCAL_ENCODING_UC_NUMBER = new AbstractEntryKey("unicode-number", "Unicode number of an encoding", String.class, false, null);

	/** Key (local) for the HTML code of an encoding. */
	public static EntryKey LOCAL_HTML_CODE = new AbstractEntryKey("html-code", "HTML code for an encoding", String.class, false, null);
}
