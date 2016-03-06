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

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataUtilities;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.EntryObject;
import de.vandermeer.skb.datatool.commons.LoadedTypeMap;
import de.vandermeer.skb.datatool.entries.date.month.MonthEntry;

/**
 * A special data object for date information (for instance start/end day/month).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectEDate implements EntryObject {

	/** Key pointing to an edate object. */
	public static EntryKey OBJ_EDATE = new AbstractEntryKey("edate", "combined date information, e.g. start/end day/month", ObjectEDate.class, false, null);

	/** EDate object schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(ObjectEDateKeys.OBJ_EDATE_YEAR, true);
				put(ObjectEDateKeys.OBJ_EDATE_DAY, false);
				put(ObjectEDateKeys.OBJ_EDATE_DAY_START, false);
				put(ObjectEDateKeys.OBJ_EDATE_DAY_END, false);
				put(ObjectEDateKeys.OBJ_EDATE_MONTH_LINK, false);
				put(ObjectEDateKeys.OBJ_EDATE_MONTH_START_LINK, false);
				put(ObjectEDateKeys.OBJ_EDATE_MONTH_END_LINK, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	@Override
	public void loadObject(String keyStart, Object data, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException {
		if(!(data instanceof Map)){
			throw new IllegalArgumentException("object edate - data must be a map");
		}

		this.entryMap = DataUtilities.loadEntry(this.getSchema(), keyStart, (Map<?, ?>)data, loadedTypes, cs);

		this.entryMap.put(ObjectEDateKeys.LOCAL_OBJ_EDATE_MONTH_LINK, DataUtilities.loadDataString(ObjectEDateKeys.OBJ_EDATE_MONTH_LINK, (Map<?, ?>)data));
		this.entryMap.put(ObjectEDateKeys.LOCAL_OBJ_EDATE_MONTH_START_LINK, DataUtilities.loadDataString(ObjectEDateKeys.OBJ_EDATE_MONTH_START_LINK, (Map<?, ?>)data));
		this.entryMap.put(ObjectEDateKeys.LOCAL_OBJ_EDATE_MONTH_END_LINK, DataUtilities.loadDataString(ObjectEDateKeys.OBJ_EDATE_MONTH_END_LINK, (Map<?, ?>)data));

		StrBuilder msg = new StrBuilder(50);

		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}
	}

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	/**
	 * Returns the year.
	 * @return year
	 */
	public int getYear(){
		return (Integer)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_YEAR);
	}

	/**
	 * Returns the day.
	 * @return day
	 */
	public int getDay(){
		return (Integer)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_DAY);
	}

	/**
	 * Returns the day start.
	 * @return day-start
	 */
	public int getDayStart(){
		return (Integer)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_DAY_START);
	}

	/**
	 * Returns the day end.
	 * @return day-end
	 */
	public int getDayEnd(){
		return (Integer)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_DAY_END);
	}

	/**
	 * Returns the month entry.
	 * @return month entry
	 */
	public MonthEntry getMonth(){
		return (MonthEntry)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_MONTH_LINK);
	}

	/**
	 * Returns the month-start entry.
	 * @return month-start entry
	 */
	public MonthEntry getMonthStart(){
		return (MonthEntry)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_MONTH_START_LINK);
	}

	/**
	 * Returns the month-end entry.
	 * @return month-end entry
	 */
	public MonthEntry getMonthEnd(){
		return (MonthEntry)this.entryMap.get(ObjectEDateKeys.OBJ_EDATE_MONTH_END_LINK);
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
