package de.vandermeer.skb.datatool.html;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.datatool.commons.DataSet;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.entries.HtmlEntry;

public class Hes {

	/** File extension. */
	public final String FILE_EXT = "hmap";

	/** Flag for verbose mode, true means on, false means off. */
	boolean verbose;

	/** Application name for logging. */
	String appName;

	/**
	 * Sets verbose mode
	 * @param flag true for on and false for off
	 * @return self to allow chaining
	 */
	public Hes setVerbose(boolean flag){
		this.verbose = flag;
		return this;
	}

	/**
	 * Sets the application name for logging.
	 * @param appName new application name
	 * @return self to allow chaining
	 */
	public Hes setAppName(String appName){
		this.appName = appName;
		return this;
	}

	/**
	 * Loads encoding definitions from files and creates a list of encodings.
	 * @param directory input directory
	 * @param keySep character to separate key elements
	 * @param target conversion target for encodings
	 * @return list of encodings, null on error
	 */
	public DataSet<HtmlEntry> load(String directory, char keySep, DataTarget target) {
		IOFileFilter fileFilter = new WildcardFileFilter(new String[]{
				"*." + FILE_EXT + ".json"
		});
		DirectoryLoader dl = new CommonsDirectoryWalker(directory, DirectoryFileFilter.INSTANCE, fileFilter);
		if(dl.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading files from directory <{}>\n{}", new Object[]{this.appName, directory, dl.getLoadErrors().render()});
			return null;
		}

		DataSet<HtmlEntry> ds = new DataSet<>(HtmlEntry.class);
		ds.load(dl, FILE_EXT, keySep, target, this.appName);
		return ds;
	}

}
