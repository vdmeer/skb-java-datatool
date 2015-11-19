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

package de.vandermeer.skb.datatool.encodings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileSource;
import de.vandermeer.skb.base.info.FileSourceList;

/**
 * Set of encodings wit additional (mostly statistical) information.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public class EncodingSet {

	/** Application name for printouts and logs. */
	String appName;

	/** The set of encodings. */
	TreeSet<EncodingEntry> encodings;

	/** Number of read files. */
	int files;

	/** Key separator. */
	char keySep;

	/** Characters to be excluded from processing. */
	String[] excluded;

	public EncodingSet(String appName, String[] excluded){
		this.appName = appName;
		this.encodings = new TreeSet<>();
		this.excluded = (excluded==null)?new String[]{}:excluded;
		this.files = 0;
	}

	/**
	 * Loads encodings from file system.
	 * @param dl the directory loader with files to load encodings from
	 * @return 0 on success, larger than zero on JSON parsing error (number of found errors)
	 */
	public int load(DirectoryLoader dl){
		int ret = 0;
		FileSourceList fsl = dl.load();

		for(FileSource fs : fsl.getSource()){
			ObjectMapper om = new ObjectMapper();
			om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			try{
				List<Map<String, Object>> acrJson = om.readValue(fs.asFile(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});
				for(Map<String, Object> entry : acrJson){
					EncodingEntry ee = new EncodingEntry(entry);
					if(!ArrayUtils.contains(this.excluded, ee.getText())){
						this.encodings.add(ee);
					}
				}
				this.files++;
			}
			catch(Exception ex){
				Skb_Console.conError("creating event from JSON failed with exception <{}>, cause <{}> and message <{}> in file <{}>", new Object[]{ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage(), fs.getAbsoluteName()});
				ret++;
			}
		}
		return ret;
	}

	/**
	 * Returns the number of read files.
	 * @return number of files read
	 */
	public int getFileNumber(){
		return this.files;
	}

	/**
	 * Returns encodings found in the encoding definitions.
	 * @return set of encodings, empty if none loaded or found
	 */
	public TreeSet<EncodingEntry> getEncodings(){
		return this.encodings;
	}

	/**
	 * Validates encodings.
	 * @return 0 on success, number of found errors otherwise
	 */
	public int validateEncodings(){
//		Map<String, String> errors = new TreeMap<>();
//		for(AcronymEntry ae : this.encodings){
//			if(ae.getKey().contains(" ")){
//				errors.put(ae.getKey(), "   - acronym <" + ae.getKey() + "> contains illegal characters in key");
//			}
//			if(StringUtils.containsAny(ae.getLong(), ",%'")){
//				errors.put(ae.getKey(), "   - acronym <" + ae.getKey() + "> contains illegal characters in long form");
//			}
//		}
//
//		if(errors.size()>0){
//			for(String error : errors.keySet()){
//				Skb_Console.conError(errors.get(error));
//			}
//		}
//
//		return errors.size();
		return 0;
	}
}
