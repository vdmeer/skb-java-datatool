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

import de.vandermeer.skb.base.encodings.TranslatorFactory;

/**
 * Abstract implementation of a data set loader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.3.0 build 150928 (28-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class AbstractDataSetLoader<E extends DataEntry> implements DataSetLoader<E> {

	/** Name of the calling application. */
	private String appName;

	/** Initialized data set builder. */
	private DataSetBuilder<E> dsb;

	/** Flag for verbose mode. */
	private boolean verbose;

	/** Loaders target. */
	private DataTarget target;

	@Override
	public void setInitial(String appName, char keySep, String inputDir, DataTarget target, boolean verbose) {
		if(appName==null){
			throw new IllegalArgumentException("appName is null");
		}
		if(inputDir==null){
			throw new IllegalArgumentException("input directory is null");
		}

		this.appName = appName;
		this.verbose = verbose;

		this.dsb = new DataSetBuilder<E>()
			.setAppName(appName)
			.setKeySeparator(keySep)
			.setDirectory(inputDir)
		;

		if(target!=null){
			this.dsb.setTranslator(TranslatorFactory.getTranslator(target.getDefinition().getTranslationTarget()));
			this.target = target;
		}
	}

	@Override
	public void setAsRequired(DataSetLoader<?> originalLoader) {
		if(originalLoader==null){
			throw new IllegalArgumentException("originalLoader is null");
		}
		this.setInitial(originalLoader.getAppName(), originalLoader.getDataSetBuilder().keySeparator, originalLoader.getDataSetBuilder().directory, originalLoader.getTarget(), originalLoader.getVerboseFlag());
		this.dsb.linkMap = originalLoader.getDataSetBuilder().linkMap;
	}

	@Override
	public String getAppName() {
		return this.appName;
	}

	@Override
	public DataSetBuilder<E> getDataSetBuilder() {
		return this.dsb;
	}

	@Override
	public boolean getVerboseFlag() {
		return this.verbose;
	}

	@Override
	public DataTarget getTarget(){
		return this.target;
	}
}
