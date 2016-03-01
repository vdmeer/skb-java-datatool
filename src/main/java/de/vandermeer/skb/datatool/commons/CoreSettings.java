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

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.base.encodings.TranslatorFactory.Target;
import de.vandermeer.skb.datatool.commons.target.DataTarget;

/**
 * Core settings for the data tool and all processing components.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class CoreSettings {

	/** Separator character for auto-generated keys. */
	private char keySeparator = ':';

	/** Flag for verbose mode. */
	private boolean verbose;

	/** Name of the calling application. */
	private String appName;

	/** Start directory for loading files. */
	private String inputDir;

	/** Loaders target. */
	private DataTarget target;

	/**
	 * Returns a new core settings object
	 * @param keySeparator the separator of key elements
	 * @param verbose flag for verbose mode
	 * @param appName the name of the calling application
	 * @param inputDir the input directory to load files from
	 * @param target the target for code/output generation
	 */
	public CoreSettings(char keySeparator, boolean verbose, String appName, String inputDir, DataTarget target){
		this.keySeparator = keySeparator;
		this.verbose = verbose;
		this.appName = appName;
		this.inputDir = inputDir;
		this.target = target;
	}

	/**
	 * returns the key separator.
	 * @return key separator
	 */
	public char getKeySeparator(){
		return this.keySeparator;
	}

	/**
	 * Returns the flag for verbose mode
	 * @return flag fro verbose mode
	 */
	public boolean getVerbose(){
		return this.verbose;
	}

	/**
	 * Returns the application name
	 * @return application name
	 */
	public String getAppName(){
		return this.appName;
	}

	/**
	 * Returns the input directory.
	 * @return input directory
	 */
	public String getInputDir(){
		return this.inputDir;
	}

	/**
	 * Returns the data target.
	 * @return data target
	 */
	public DataTarget getTarget(){
		return this.target;
	}

	/**
	 * Returns the translator.
	 * @return translator, null if none set in the target
	 */
	public Translator getTranslator(){
		if(this.getTarget()!=null){
			Target target = this.getTarget().getDefinition().getTranslationTarget();
			if(target!=null){
				return TranslatorFactory.getTranslator(target);
			}
		}
		return null;
	}
}
