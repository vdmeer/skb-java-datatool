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

import java.net.URISyntaxException;
import java.util.Map;

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
public class AbstractDataLoader implements DataLoader {

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
	public AbstractDataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap){
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
	public AbstractDataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap, Translator translator){
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
	public AbstractDataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap, Map<DataEntryType<?,?>, DataSet<?>> linkMap){
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
	public AbstractDataLoader(String keyStart, char keySeparator, Map<String, Object> entryMap, Translator translator, Map<DataEntryType<?,?>, DataSet<?>> linkMap){
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
	 * Takes the given entry map and tries to generate a special data object from it.
	 * @param key the key pointing to the map entry
	 * @return a new data object of specific type (as read from the map) on success, null on no success
	 * @throws IllegalArgumentException if any of the required arguments or map entries are not set or empty
	 * @throws URISyntaxException if creating a URI for an SKB link failed
	 * @throws IllegalAccessException if an entry object could not be created due to a class error (type class)
	 * @throws InstantiationException if an entry object could not be created due to a class error (type class)
	 */
	@SuppressWarnings("unchecked")
	public Object loadData(EntryKey key) throws URISyntaxException, InstantiationException, IllegalAccessException{
		Object ret = DataLoader.super.loadData(key);
		if(ret!=null){
			return ret;
		}

		if(!this.entryMap.containsKey(key.getKey())){
			return null;
		}
		Object data = this.entryMap.get(key.getKey());
//		if(key.getSkbUri()!=null && data instanceof String){
//			return this.loadLink((String)data);
//		}
//
//		if(key.getType().equals(String.class) && data instanceof String){
//			if(key.useTranslator()==true && this.translator!=null){
//				return this.translator.translate((String)data);
//			}
//			return data;
//		}
//		if(key.getType().equals(Integer.class) && data instanceof Integer){
//			return data;
//		}

		if(ClassUtils.isAssignable(key.getType(), EntryObject.class)){
			EntryObject eo = (EntryObject)key.getType().newInstance();
			if(data instanceof Map){
				eo.loadObject(new AbstractDataLoader(this.keyStart, keySeparator, (Map<String, Object>)data, null, linkMap));
			}
			return eo;
		}
		return null;
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

	/**
	 * Returns the key separator
	 * @return key separator
	 */
	public char getKeySeparator(){
		return this.keySeparator;
	}

	@Override
	public Translator getTranslator() {
		return this.translator;
	}
}
