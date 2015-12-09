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

	/** Core settings. */
	private CoreSettings cs;

	/** A map of entries the load can process. */
	private Map<String, Object> entryMap;

	/**
	 * Returns a new data loader.
	 * @param keyStart the string preceding a key
	 * @param cs core settings
	 * @param entryMap a map with key/value pairs to read data from
	 * @throws IllegalArgumentException if any of the input arguments are illegal
	 */
	public AbstractDataLoader(String keyStart, CoreSettings cs, Map<String, Object> entryMap){
		if(keyStart==null){
			throw new IllegalArgumentException("no keyStart given");
		}
		else if(!StringUtils.endsWith(keyStart, Character.toString(cs.getKeySeparator()))){
			throw new IllegalArgumentException("wrong end of keyStart");
		}
		this.keyStart = keyStart;

		this.cs = cs;

		this.entryMap = entryMap;
		if(entryMap==null){
			throw new IllegalArgumentException("entryMap is null");
		}
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
		if(ClassUtils.isAssignable(key.getType(), EntryObject.class)){
			EntryObject eo = (EntryObject)key.getType().newInstance();
			if(data instanceof Map){
				eo.loadObject(new AbstractDataLoader(this.keyStart, this.getCs(), (Map<String, Object>)data));
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

//	/**
//	 * Returns the link map of the loader
//	 * @return link map
//	 */
//	public LoadedTypeMap getLoadedTypes(){
//		return this.loadedTypes;
//	}

	/**
	 * Returns the entry map of the loader
	 * @return entry map
	 */
	public Map<String, Object> getEntryMap(){
		return this.entryMap;
	}

	@Override
	public CoreSettings getCs(){
		return this.cs;
	}

//	/**
//	 * Returns the key separator
//	 * @return key separator
//	 */
//	public char getKeySeparator(){
//		return this.keySeparator;
//	}

//	@Override
//	public Translator getTranslator() {
//		return this.translator;
//	}
}
