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

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import de.vandermeer.skb.datatool.commons.AbstractDataEntrySchema;
import de.vandermeer.skb.datatool.commons.AbstractEntryKey;
import de.vandermeer.skb.datatool.commons.DataEntrySchema;
import de.vandermeer.skb.datatool.commons.DataLoader;
import de.vandermeer.skb.datatool.commons.EntryKey;
import de.vandermeer.skb.datatool.commons.EntryObject;

/**
 * A special data objects for links (URLs and the like).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class ObjectLinks implements EntryObject {

	public static EntryKey OBJ_LINKS = new AbstractEntryKey("links", "a links object with URLs and URNs", ObjectLinks.class, false, null);
	public static EntryKey OBJ_LINKS_U = new AbstractEntryKey("u", "a URL inside a links object", String.class, false, null);
	public static EntryKey OBJ_LINKS_W = new AbstractEntryKey("w", "a URL to a Wikipedia page inside a links object", String.class, false, null);

	/** Links object schema. */
	public static DataEntrySchema SCHEMA = new AbstractDataEntrySchema(
			new HashMap<EntryKey, Boolean>() {private static final long serialVersionUID = 1L;{
				put(OBJ_LINKS_U, false);
				put(OBJ_LINKS_W, false);
			}}
	);

	/** The local entry map. */
	private Map<EntryKey, Object> entryMap;

	@Override
	public void loadObject(DataLoader loader) throws URISyntaxException, InstantiationException, IllegalAccessException {
		this.entryMap = loader.loadEntry(this.getSchema());
	}

	/**
	 * Returns the URL link.
	 * @return URL link, null if none set
	 */
	public String getUrl(){
		return (String)this.entryMap.get(OBJ_LINKS_U);
	}

	/**
	 * Returns the Wikipedia link.
	 * @return Wikipedia link, null if none set
	 */
	public String getWikipedia(){
		return (String)this.entryMap.get(OBJ_LINKS_W);
	}

	@Override
	public DataEntrySchema getSchema(){
		return SCHEMA;
	}

	@Override
	public Map<EntryKey, Object> getEntryMap(){
		return this.entryMap;
	}
}
