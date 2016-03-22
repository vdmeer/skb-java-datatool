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

package de.vandermeer.skb.datatool.entries.charactermaps;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.target.DataTarget;

/**
 * Prepares a loaded character map for a target.
 * Particularly, it removes entry from the data set that are not valid (need to be excluded) or not have no information for the target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.2
 */
public class PrepareDataSet {

	/**
	 * Characters to be excluded from any translation array when converting from HTML.
	 * Since we do not know when the characters '&amp;', '#', and ';' appear in a data set, and we are translating HTML codings and entities, we need to exclude them from translation.
	 */
	public static final String[] FROM_HTML_EXCLUDES = new String[]{
			"&", "#", ";"
	};

	/**
	 * Characters to be excluded from any translation array when converting to HTML.
	 * When translating from plain text, standard characters do not need a translation into HTML entities.
	 * This array defines those standard characters.
	 * A translation will not fail if the characters in this array ar not excluded, but the resulting text will not be human readable anymore.
	 */
	public static final String[] TO_HTML_EXCLUDES = new String[]{
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X","Y", "Z",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"0", "1","2", "3", "4", "5", "6", "7", "8", "9",
			" ", "!", "$", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "=", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"
	};

	public PrepareDataSet(){}

	public void prepare(DataSet<CharacterMapEntry> ds, DataTarget target){
		Set<CharacterMapEntry> remove = new HashSet<>();

		CharacterMapTargetDefinitions cmTarget = null;
		for(CharacterMapTargetDefinitions t : CharacterMapTargetDefinitions.values()){
			if(target.getDefinition().getTargetName().equals(t.getTargetName())){
				cmTarget = t;
				break;
			}
		}
		if(cmTarget==null){
			//no target found that we need to take care of
			return;
		}

		for(CharacterMapEntry e : ds.getEntries()){
			switch(cmTarget){
				case JAVA_HTML_2_ASCIIDOC:
					if(ArrayUtils.contains(FROM_HTML_EXCLUDES, e.getChar())){
						remove.add(e);
					}
					else if(StringUtils.isEmpty(e.getAd())){
						remove.add(e);
					}
					break;
				case JAVA_HTML_2_LATEX:
					if(ArrayUtils.contains(FROM_HTML_EXCLUDES, e.getChar())){
						remove.add(e);
					}
					else if(StringUtils.isEmpty(e.getLatex())){
						remove.add(e);
					}
					break;
				case JAVA_TEXT_2_ASCIIDOC:
					if(StringUtils.isEmpty(e.getAd())){
						remove.add(e);
					}
					else if(e.getChar().equals(e.getAd())){
						remove.add(e);
					}
					break;
				case JAVA_TEXT_2_HTML:
					if(ArrayUtils.contains(TO_HTML_EXCLUDES, e.getChar())){
						remove.add(e);
					}
					else if(StringUtils.isEmpty(e.getHtmlCode()) && StringUtils.isEmpty(e.getHtmlEntity())){
						remove.add(e);
					}
					break;
				case JAVA_TEXT_2_LATEX:
					if(StringUtils.isEmpty(e.getLatex())){
						remove.add(e);
					}
					break;
			}
		}

		for(CharacterMapEntry entry : remove){
			ds.getMap().values().remove(entry);
		}
	}
}
