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

package de.vandermeer.skb.datatool.acronyms;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.encodings.TranslatorFactory;

/**
 * A single acronym with all its definitions.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class AcronymEntry implements Comparable<AcronymEntry> {

	/** The key for the acronym. */
	String key;

	/** Short form of the acronym. */
	String acShort;

	/** Long form of the acronym. */
	String acLong;

	/** URL for the acronym. */
	String url;

	/** Wikipedia URL for the acronym. */
	String wikipedia;

	/** Description for the acronym. */
	String description;

	/**
	 * Returns a new acronym entry.
	 * @param key the key for the acronym
	 * @param map a map with information for short form, long form, URL, Wikipedia URL and description
	 * @param keySep character to be used as separator of key elements
	 * @param translationTarget target for encoding translations, null if none required
	 * @throws IllegalArgumentException if any of the required arguments (key, short, long) are not set or empty
	 */
	public AcronymEntry(String key, Map<String, Object> map, char keySep, TranslatorFactory.Target translationTarget){
		StrBuilder msg = new StrBuilder(50);
		Translator translator = TranslatorFactory.getTranslator(translationTarget);

		if(!map.containsKey("s") || map.get("s")==null || StringUtils.isEmpty(map.get("s").toString())){
			msg.appendSeparator(", ");
			msg.append("no short version");
		}
		else{
			this.acShort = map.get("s").toString();
			if(translator!=null){
				this.acShort = translator.translate(this.acShort);
			}
		}

		if(!map.containsKey("l") || map.get("l")==null || StringUtils.isEmpty(map.get("l").toString())){
			msg.appendSeparator(", ");
			msg.append("no long version");
		}
		else{
			this.acLong = map.get("l").toString();
			if(translator!=null){
				this.acLong = translator.translate(this.acLong);
			}
		}

		if(key==null){
			msg.appendSeparator(", ");
			msg.append("no key given");
		}
		else if(!StringUtils.endsWith(key, Character.toString(keySep))){
			msg.appendSeparator(", ");
			msg.append("wrong end of key");
		}
		else{
			if(map.containsKey("k")){
				this.key = key + map.get("k");
			}
			else{
				this.key = key + this.acShort;
			}
		}

		if(this.key.contains(" ")){
			msg.appendSeparator(", ");
			msg.appendSeparator("acronym <" + this.key + "> contains illegal characters in key");
		}
//		if(StringUtils.containsAny(this.acLong, ",%'")){
//			msg.appendSeparator(", ");
//			msg.appendSeparator("acronym <" + this.key + "> contains illegal characters in long form");
//		}


		if(msg.size()>0){
			throw new IllegalArgumentException(msg.toString());
		}

		if(map.get("u")!=null){
			this.url = map.get("u").toString();
		}
		if(map.get("w")!=null){
			this.wikipedia = map.get("w").toString();
		}
		if(map.get("d")!=null){
			this.description = map.get("d").toString();
		}
	}

	/**
	 * Returns the short form of the acronym.
	 * @return acronym short form
	 */
	public String getShort(){
		return this.acShort;
	}

	/**
	 * Returns the long form of the acronym.
	 * @return acronym long form
	 */
	public String getLong(){
		return this.acLong;
	}

	/**
	 * Returns the acronym key.
	 * @return acronym key.
	 */
	public String getKey(){
		return this.key;
	}

	/**
	 * Returns a URL associated with the acronym.
	 * @return URL, null if not set
	 */
	public String getUrl(){
		return this.url;
	}

	/**
	 * Returns a Wikipedia URL associated with the acronym.
	 * @return Wikipedia URL, null if not set
	 */
	public String getWikipedia(){
		return this.wikipedia;
	}

	/**
	 * Returns a description for the acronym.
	 * @return acronym description, null if not set
	 */
	public String getDescription(){
		return this.description;
	}

	@Override
	public int compareTo(AcronymEntry o) {
		if(o==null){
			return -1;
		}
		return this.acShort.compareTo(o.acShort);
	}
}
