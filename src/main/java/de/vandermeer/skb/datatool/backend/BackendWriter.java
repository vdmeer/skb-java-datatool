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

package de.vandermeer.skb.datatool.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import de.vandermeer.skb.base.info.FileTarget;
import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.info.StgFileLoader;
import de.vandermeer.skb.datatool.commons.CoreSettings;
import de.vandermeer.skb.datatool.commons.DataEntry;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.interfaces.transformers.Map_To_Text;

/**
 * Backend to write templates to file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class BackendWriter {

	/** The original file name. */
	private String fileName;

	/** The final file name to write to. */
	private String fileNameFinal;

	/** The loader for STG files. */
	private StgFileLoader stgLoader;

	/** Local STG. */
	private STGroup stg;

	/** The file target created from the file name. */
	private FileTarget fileTarget;

	/** Core settings. */
	private CoreSettings cs;

	/** Expected chunks for the ST template. */
	private final Map<String, Set<String>> expectedStChunks = new HashMap<String, Set<String>>() {private static final long serialVersionUID = 1L;{
		put("build", new HashSet<String>() {private static final long serialVersionUID = 1L;{
			add("context");
			add("entry");
		}});
	}};

	/**
	 * Creates a new backend writer
	 * @param fileName the output file name
	 * @param cs the core settings for the writer
	 * @throws IllegalArgumentException if any required argument is not valid
	 */
	public BackendWriter(String fileName, CoreSettings cs){
		Validate.notNull(cs);
		this.cs = cs;

		if(fileName!=null){
			this.fileName = fileName;
			if(this.cs.getTarget()!=null){
				this.fileNameFinal = fileName + "." + cs.getTarget().getDefinition().getExtension();
			}
			else{
				this.fileNameFinal = fileName;
			}

			FileTarget.createFile(this.fileNameFinal);
			this.fileTarget = new FileTarget(this.fileNameFinal);
			Validate.validState(this.fileTarget.isValid(), "errors writing to file <%s>\n%s", this.fileName, this.fileTarget.getInitError().render());
		}

		if(cs.getTarget()!=null){
			this.stgLoader = new StgFileLoader(cs.getTarget().getStgFileName());
			Validate.validState(!this.stgLoader.getLoadErrors().hasErrors(), "problem creating STG loader for file <%s>\n%s", cs.getTarget().getStgFileName(), this.stgLoader.getLoadErrors().render());

			STGroup stg = this.stgLoader.load();
			Validate.validState(!this.stgLoader.getLoadErrors().hasErrors(), "errors loading STG file <%s>\n%s", cs.getTarget().getStgFileName(), this.stgLoader.getLoadErrors().render());
			Validate.notNull(stg, "unknown error loading STG file <%s>", cs.getTarget().getStgFileName());

			STGroupValidator stgVal = new STGroupValidator(stg, this.expectedStChunks);
			Validate.validState(!stgVal.getValidationErrors().hasErrors(), "STG validation errors for file <%s>\n%s", cs.getTarget().getStgFileName(), stgVal.getValidationErrors().render());

			this.stg = stg;
			Validate.notNull(this.stg);
		}
	}

	/**
	 * Writes output.
	 * @param bl the backend loader
	 * @throws IllegalArgumentException if any required argument is not valid
	 * @throws IOException if writing to a file failed
	 */
	public void writeOutput(BackendLoader bl) throws IOException{
		this.writeOutput(bl, null);
	}

	/**
	 * Writes output.
	 * @param bl the backend loader
	 * @param context a context object with information that might be understood by the template
	 * @throws IllegalArgumentException if any required argument is not valid
	 * @throws IOException if writing to a file failed
	 */
	public void writeOutput(BackendLoader bl, Object context) throws IOException{
		Validate.notNull(bl);
		String toWrite = null;

		if(this.cs.getTarget()!=null){
			ST st = this.fillTemplate(bl, context);
			if(st!=null){
				toWrite = st.render();
			}
		}
		else{
			toWrite = Map_To_Text.create().transform(bl.getDataSet().getMap());
		}

		if(toWrite!=null){
			if(this.fileTarget!=null && this.fileTarget.asFile()!=null){
				FileUtils.write(this.fileTarget.asFile(), toWrite, "UTF-8");
			}
			else{
				System.out.println(toWrite);
			}
		}
		else{
			throw new IllegalArgumentException("failed to create output string, tried ST and Transformer");
		}
	}

	/**
	 * Fills the template with all found data sets
	 * @param bl backend loader with data sets
	 * @param context a context object with information that might be understood by the template
	 * @return created template
	 */
	public ST fillTemplate(BackendLoader bl, Object context){
		Validate.notNull(bl);
		DataSet<?> entries1 = bl.getDataSet();
		if(entries1!=null){
			ST st = this.writeST(entries1, context);
			return st;
		}
		return null;
	}

	/**
	 * Writes a data set to a template.
	 * @param ds the data set to write
	 * @param context a context object with information that might be understood by the template
	 * @param <E> type of the data set
	 * @return the created template
	 */
	public <E extends DataEntry> ST writeST(DataSet<E> ds, Object context) {
		Validate.notNull(ds);

		if(this.cs.getTarget()!=null){
			ST st = stg.getInstanceOf("build");
			for(E entry : ds.getEntries()){
				st.add("entry", entry);
			}
			st.add("context", context);
			return st;
		}
		return null;
	}

	/**
	 * Returns the output mode.
	 * @return description of what a write will do
	 */
	public String getOutputMode(){
		if(this.cs.getTarget()==null){
			return "no target, writing Map<>";
		}
		else if(this.cs.getTarget()!=null && this.fileTarget==null){
			return "for target <" + this.cs.getTarget().getDefinition().getTargetName() + "> writing to STDOUT";
		}
		else if(this.cs.getTarget()!=null && this.fileTarget!=null){
			return "for target <" + this.cs.getTarget().getDefinition().getTargetName() + "> writing to <" + ((this.fileTarget==null)?"standard out":this.fileTarget.getAbsoluteName()) +">";
		}
		return null;
	}
}
