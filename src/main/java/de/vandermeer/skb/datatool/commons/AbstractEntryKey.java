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

/**
 * Abstract implementation of an entry key.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class AbstractEntryKey implements EntryKey {

	/** Key. */
	private String key;

	/** Key description. */
	private String description;

	/** Key type. */
	private Class<?> type;

	/** Flag for using translator on processing values for the key. */
	private boolean useTranslator;

	/** URI of an skb link, null if none supported by the key. */
	private String skbUri;

	/**
	 * Creates a new entry key.
	 * @param key the name of the key
	 * @param description the key's description
	 * @param type the class the key expects as type for values
	 * @param useTranslator flag for using translator
	 * @param skbUri an SKB URI if type requires one, null if not
	 */
	public AbstractEntryKey(String key, String description, Class<?> type, boolean useTranslator, String skbUri){
		this.key = key;
		this.description = description;
		this.type = type;
		this.useTranslator = useTranslator;
		this.skbUri = skbUri;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	@Override
	public boolean useTranslator() {
		return this.useTranslator;
	}

	@Override
	public String getSkbUri(){
		return this.skbUri;
	}
}
