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

import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.base.encodings.Translator;

/**
 * Utility methods for common tasks.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Utilities {

	/**
	 * Takes the given entry map and tries to generate a special data object from it.
	 * @param key the key pointing to the map entry with object class information indicating which data object should be generated
	 * @param entryMap the map with entries used to generate the data object
	 * @return a new data object of specific type (as read from the map) on success, null on no success
	 */
	public static Object getDataObject(EntryKey key, Map<String, Object> entryMap){
		return getDataObject(key, entryMap, null);
	}

	/**
	 * Takes the given entry map and tries to generate a special data object from it.
	 * @param key the key pointing to the map entry with object class information indicating which data object should be generated
	 * @param entryMap the map with entries used to generate the data object
	 * @param translator a translator for character encoding translations
	 * @return a new data object of specific type (as read from the map) on success, null on no success
	 */
	public static Object getDataObject(EntryKey key, Map<String, Object> entryMap, Translator translator){
		if(!entryMap.containsKey(key.getKey())){
			return null;
		}
		Object data = entryMap.get(key.getKey());

		if(key.getType().equals(String.class) && data instanceof String){
			if(translator!=null){
				return translator.translate((String)data);
			}
			return data;
		}
		if(key.getType().equals(Integer.class) && data instanceof Integer){
			return data;
		}
		if(ClassUtils.isAssignable(key.getType(), ObjectLinks.class)){
			Object ret = new ObjectLinks();
			if(ret instanceof Map){
				@SuppressWarnings("unchecked")
				String err = ((ObjectLinks)ret).load((Map<String, Object>)data);
				if(StringUtils.isNoneEmpty(err)){
					throw new IllegalArgumentException(err);
				}
			}
			return ret;
		}
		return null;
	}
}
