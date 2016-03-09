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

package de.vandermeer.skb.datatool.entries.date.edate;

import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.entries.date.month.MonthEntry;

/**
 * Keys used by the object edate.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectEDateKeys {

	/** Year for the date. */
	public static EntryKey OBJ_EDATE_YEAR = new AbstractEntryKey("y", "year", Integer.class, true, null);

	/** Single day for the date. */
	public static EntryKey OBJ_EDATE_DAY = new AbstractEntryKey("d", "(single) day", Integer.class, true, null);

	/** Starting day for the date. */
	public static EntryKey OBJ_EDATE_DAY_START = new AbstractEntryKey("ds", "day start", Integer.class, true, null);

	/** Ending day for the date. */
	public static EntryKey OBJ_EDATE_DAY_END = new AbstractEntryKey("de", "day end", Integer.class, true, null);

	/** Single month for the date. */
	public static EntryKey OBJ_EDATE_MONTH_LINK = new AbstractEntryKey("m", "(single)", MonthEntry.class, true, "skb://months");

	/** Starting month for the date. */
	public static EntryKey OBJ_EDATE_MONTH_START_LINK = new AbstractEntryKey("ms", "month start", MonthEntry.class, true, "skb://months");

	/** Ending month for the date. */
	public static EntryKey OBJ_EDATE_MONTH_END_LINK = new AbstractEntryKey("me", "month end", MonthEntry.class, true, "skb://months");

	/** Key (local) for month link. */
	public static EntryKey LOCAL_OBJ_EDATE_MONTH_LINK = new AbstractEntryKey("month-link", "original month link", String.class, false, null);

	/** Key (local) for month link. */
	public static EntryKey LOCAL_OBJ_EDATE_MONTH_START_LINK = new AbstractEntryKey("month-start-link", "original month-start link", String.class, false, null);

	/** Key (local) for month link. */
	public static EntryKey LOCAL_OBJ_EDATE_MONTH_END_LINK = new AbstractEntryKey("month-end-link", "original month-end link", String.class, false, null);

}
