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

package de.vandermeer.skb.datatool.commons;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilities to load data from maps.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 151209 (09-Dec-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class DataUtilities {

	/**
	 * Loads data if the given key points to a string, without any de-references or translation.
	 * @param key the key pointing to the map entry
	 * @param map key/value mappings to load the key from
	 * @return null on failure (key not in map, value not of type string), loaded string otherwise (can also be null or empty, no tests done here)
	 */
	public static String loadDataString(EntryKey key, Map<?, ?> map){
		Object data = map.get(key.getKey());
		if(data instanceof String){
			return (String)data;
		}
		return null;
	}

	/**
	 * Loads an entry, does no link substitution.
	 * @param schema the entry's schema for auto loading and testing
	 * @param keyStart string used to start a key
	 * @param map key/value mappings to load the key from
	 * @param cs core settings required for loading data
	 * @return mapping of entry keys to objects loaded
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	public static Map<EntryKey, Object> loadEntry(DataEntrySchema schema, String keyStart, Map<?, ?> map, CoreSettings cs) throws URISyntaxException {
		return loadEntry(schema, keyStart, map, null, cs);
	}

	/**
	 * Loads an entry with link substitution (if loaded types is set).
	 * @param schema the entry's schema for auto loading and testing
	 * @param keyStart string used to start a key
	 * @param map key/value mappings to load the key from
	 * @param loadedTypes loaded types as lookup for links
	 * @param cs core settings required for loading data
	 * @return mapping of entry keys to objects loaded
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	public static Map<EntryKey, Object> loadEntry(DataEntrySchema schema, String keyStart, Map<?, ?> map, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException {
		Map<EntryKey, Object> ret = new HashMap<>();
		for(Entry<EntryKey, Boolean> key : schema.getKeyMap().entrySet()){
			Object obj = loadData(key.getKey(), keyStart, map, loadedTypes, cs);
			if(obj!=null){
				ret.put(key.getKey(), obj);
			}
		}
		return ret;
	}

	/**
	 * Takes the given entry map and tries to generate a special data object from it.
	 * @param key the key pointing to the map entry
	 * @param keyStart string used to start a key
	 * @param map key/value mappings to load the key from
	 * @param loadedTypes loaded types as lookup for links
	 * @param cs core settings required for loading data
	 * @return a new data object of specific type (as read from the map) on success, null on no success
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	public static Object loadData(EntryKey key, String keyStart, Map<?, ?> map, LoadedTypeMap loadedTypes, CoreSettings cs) throws URISyntaxException {
		if(!map.containsKey(key.getKey())){
			return null;
		}

		Object data = map.get(key.getKey());
		if(key.getSkbUri()!=null && data instanceof String){
			return loadLink(data, loadedTypes);
		}

		if(key.getType().equals(String.class) && data instanceof String){
			if(key.useTranslator()==true && cs.getTranslator()!=null){
				return cs.getTranslator().translate((String)data);
			}
			return data;
		}
		if(key.getType().equals(Integer.class) && data instanceof Integer){
			return data;
		}

		if(ClassUtils.isAssignable(key.getType(), EntryObject.class)){
			EntryObject eo;
			try {
				eo = (EntryObject)key.getType().newInstance();
				if(data instanceof Map){
					eo.loadObject(keyStart, data, loadedTypes, cs);
				}
				return eo;
			}
			catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Loads an data for an SKB link.
	 * @param skbLink the link to load an data for
	 * @param loadedTypes loaded types as lookup for links
	 * @return an object if successfully loaded, null otherwise
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	public static Object loadLink(Object skbLink, LoadedTypeMap loadedTypes) throws URISyntaxException{
		if(skbLink==null){
			throw new IllegalArgumentException("skb link null");
		}
		if(loadedTypes==null){
			throw new IllegalArgumentException("trying to load a link, but no loaded types given");
		}

		URI uri = new URI(skbLink.toString());
		if(!"skb".equals(uri.getScheme())){
			throw new IllegalArgumentException("unknown scheme in link <" + skbLink +">");
		}

		String uriReq = uri.getScheme() + "://" + uri.getAuthority();
		DataEntryType type = null;
		for(DataEntryType det : loadedTypes.keySet()){
			if(uriReq.equals(det.getLinkUri())){
				type = det;
				break;
			}
		}
		if(type==null){
			throw new IllegalArgumentException("no data entry type for link <" + uri.getScheme() + "://" + uri.getAuthority() +">");
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) loadedTypes.getTypeMap(type);
		if(map==null){
			throw new IllegalArgumentException("no entry for type <" + type.getType() + "> in link map");
		}

		String key = StringUtils.substringAfterLast(uri.getPath(), "/");
		Object ret = map.get(key);
		if(ret==null){
			throw new IllegalArgumentException("no entry for <" + uri.getAuthority() + "> key <" + key + "> in link map");
		}
		return ret;
	}
}
