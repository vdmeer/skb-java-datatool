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
 * Abstract implementation of a data set loader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class AbstractDataSetLoader<E extends DataEntry> implements DataSetLoader<E> {

	/** Core settings. */
	private CoreSettings cs;

	/** Map with linkeable data entries from other sets. */
	private LoadedTypeMap loadedTypes;

	@Override
	public void setCs(CoreSettings cs) {
		this.cs = cs;
	}

	@Override
	public CoreSettings getCs(){
		return this.cs;
	}

	@Override
	public void setLoadedTypes(LoadedTypeMap loadedTypes) {
		this.loadedTypes = loadedTypes;
	}

	@Override
	public LoadedTypeMap getLoadedTypes() {
		return this.loadedTypes;
	}

}
