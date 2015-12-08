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

import de.vandermeer.skb.base.encodings.Translator;

/**
 * Helper object for loading data, transforming them, and if required load complex data objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class DataLoader {

	/** A string used to start a key. */
	private String keyStart;

	/** A character used to separate key elements. */
	private char keySeparator;

	/** A map of entries the load can process. */
	private Map<String, Object> entryMap;

	/** A translator the loader should use. */
	private Translator translator;

	/** A map of other entries the loader might use for further load operations. */
	private Map<DataEntryType<?,?>, DataSet<?>> linkMap;

	/**
	 * Returns a new data loader.
	 * @param keyStart the string preceding a key
	 * @param keySeparator the character used to separate key elements
	 * @param entryMap a map with key/value pairs to read data from
	 * @throws IllegalArgumentException if any of the input arguments are illegal
	 */
	public DataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap){
		this(keyStart, keySeparator, entryMap, null, null);
	}

	/**
	 * Returns a new data loader.
	 * @param keyStart the string preceding a key
	 * @param keySeparator the character used to separate key elements
	 * @param entryMap a map with key/value pairs to read data from
	 * @param translator a character encoding and/or string translator (null if none required)
	 * @throws IllegalArgumentException if any of the input arguments are illegal
	 */
	public DataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap, Translator translator){
		this(keyStart, keySeparator, entryMap, translator, null);
	}

	/**
	 * Returns a new data loader.
	 * @param keyStart the string preceding a key
	 * @param keySeparator the character used to separate key elements
	 * @param entryMap a map with key/value pairs to read data from
	 * @param linkMap a map of other Data Entries (null if none required)
	 * @throws IllegalArgumentException if any of the input arguments are illegal
	 */
	public DataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap, Map<DataEntryType<?,?>, DataSet<?>> linkMap){
		this(keyStart, keySeparator, entryMap, null, linkMap);
	}

	/**
	 * Returns a new data loader.
	 * @param keyStart the string preceding a key
	 * @param keySeparator the character used to separate key elements
	 * @param entryMap a map with key/value pairs to read data from
	 * @param translator a character encoding and/or string translator (null if none required)
	 * @param linkMap a map of other Data Entries (null if none required)
	 * @throws IllegalArgumentException if any of the input arguments are illegal
	 */
	public DataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap, Translator translator, Map<DataEntryType<?,?>, DataSet<?>> linkMap){
		if(keyStart==null){
			throw new IllegalArgumentException("no keyStart given");
		}
		else if(!StringUtils.endsWith(keyStart, Character.toString(keySeparator))){
			throw new IllegalArgumentException("wrong end of keyStart");
		}
		this.keyStart = keyStart;
		this.keySeparator = keySeparator;

		this.entryMap = entryMap;
		if(entryMap==null){
			throw new IllegalArgumentException("entryMap is null");
		}

		this.translator = translator;
		this.linkMap = linkMap;
	}

	/**
	 * Loads data if the given key points to a string, without any de-references or translation.
	 * @param key the key pointing to the map entry
	 * @return null on failure (key no in map, value not of type string), loaded string otherwise (can also be null or empty, no tests done here)
	 */
	public String loadDataString(EntryKey key){
		Object data = this.entryMap.get(key.getKey());
		if(data instanceof String){
			return (String)data;
		}
		return null;
	}

	/**
	 * Loads an entry
	 * @param schema the entry's schema for auto loading and testing
	 * @return mapping of entry keys to objects loaded
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	public Map<EntryKey, Object> loadEntry(DataEntrySchema schema) throws URISyntaxException{
		Map<EntryKey, Object> ret = new HashMap<>();
		for(Entry<EntryKey, Boolean> key : schema.getKeyMap().entrySet()){
			Object obj = this.loadData(key.getKey());
			if(obj!=null){
				ret.put(key.getKey(), obj);
			}
		}
		return ret;
	}

	/**
	 * Takes the given entry map and tries to generate a special data object from it.
	 * @param key the key pointing to the map entry
	 * @return a new data object of specific type (as read from the map) on success, null on no success
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	@SuppressWarnings("unchecked")
	public Object loadData(EntryKey key) throws URISyntaxException{
		if(!this.entryMap.containsKey(key.getKey())){
			return null;
		}
		Object data = this.entryMap.get(key.getKey());
		if(key.getSkbUri()!=null && data instanceof String){
			return this.loadLink((String)data);
		}

		if(key.getType().equals(String.class) && data instanceof String){
			if(key.useTranslator()==true && this.translator!=null){
				return this.translator.translate((String)data);
			}
			return data;
		}
		if(key.getType().equals(Integer.class) && data instanceof Integer){
			return data;
		}

		if(ClassUtils.isAssignable(key.getType(), ObjectLinks.class)){
			ObjectLinks olRet = new ObjectLinks();
			if(data instanceof Map){
				olRet.loadObject(new DataLoader(this.keyStart, keySeparator, (Map<String, Object>)data, null, linkMap));
			}
			return olRet;
		}
		if(ClassUtils.isAssignable(key.getType(), ObjectGeo.class)){
			ObjectGeo ogRet = new ObjectGeo();
			if(data instanceof Map){
				ogRet.loadObject(new DataLoader(this.keyStart, keySeparator, (Map<String, Object>)data, null, linkMap));
			}
			return ogRet;
		}
		if(ClassUtils.isAssignable(key.getType(), ObjectAffiliations.class)){
			ObjectAffiliations affRet = new ObjectAffiliations();
			Map<String, Object> m = new HashMap<>();
			m.put(StandardEntryKeys.OBJ_AFF_LINKS.getKey(), data);
			affRet.loadObject(new DataLoader(this.keyStart, keySeparator, m, null, linkMap));
			return affRet;
		}
		return null;
	}

	/**
	 * Loads an data for an SKB link.
	 * @param skbLink the link to load an data for
	 * @return an object if successfully loaded, null otherwise
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 */
	public Object loadLink(String skbLink) throws URISyntaxException{
		if(skbLink==null){
			throw new IllegalArgumentException("skb link null");
		}

		URI uri = new URI(skbLink.toString());
		if(!"skb".equals(uri.getScheme())){
			throw new IllegalArgumentException("unknown scheme in link <" + skbLink +">");
		}

		String uriReq = uri.getScheme() + "://" + uri.getAuthority();
		DataEntryType<?,?> type = null;
		for(DataEntryType<?, ?> det : this.linkMap.keySet()){
			if(uriReq.equals(det.getLinkUri())){
				type = det;
				break;
			}
		}
		if(type==null){
			throw new IllegalArgumentException("no data entry type for link <" + uri.getScheme() + "://" + uri.getAuthority() +">");
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) this.linkMap.get(type).getMap();
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

	/**
	 * Replaces several characters in a string to return a valid key element.
	 * @param input the input string
	 * @return a string with replaced characters (" " to "-", "." to "")
	 * @throws IllegalArgumentException if any illegal characters are in the final key
	 */
	public String toKey(String input){
		if(input==null){
			return input;
		}
		String ret = input;

		ret = StringUtils.replace(ret, " ", "-");
		ret = StringUtils.replace(ret, ".", "");

		if(ret.contains("%")){
			throw new IllegalArgumentException("key contains '%'");
		}

		return ret.toLowerCase();
	}

	/**
	 * Returns the key-start string of the loader
	 * @return key-start string
	 */
	public String getKeyStart(){
		return this.keyStart;
	}

	/**
	 * Returns the link map of the loader
	 * @return link map
	 */
	public Map<DataEntryType<?,?>, DataSet<?>> getLinkMap(){
		return this.linkMap;
	}

	/**
	 * Returns the entry map of the loader
	 * @return entry map
	 */
	public Map<String, Object> getEntryMap(){
		return this.entryMap;
	}

//	/**
//	 * Returns the key separator
//	 * @return key separator
//	 */
//	public char getKeySeparator(){
//		return this.keySeparator;
//	}
}
