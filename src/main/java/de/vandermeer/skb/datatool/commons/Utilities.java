package de.vandermeer.skb.datatool.commons;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileTarget;
import de.vandermeer.skb.base.info.StgFileLoader;

public abstract class Utilities {

	public static <E extends DataEntry> DataSet<E> loadDataSet(String directory, Class<E> type, FileExtensions extension, char keySep, DataTarget target, String appName, boolean verbose){
		return loadDataSet(directory, type, extension, keySep, target, appName, verbose, null);
	}

	public static <E extends DataEntry> DataSet<E> loadDataSet(String directory, Class<E> type, FileExtensions extension, char keySep, DataTarget target, String appName, boolean verbose, Map<String, Pair<String, String>> refKeyMap){
		IOFileFilter fileFilter = new WildcardFileFilter(new String[]{
				"*." + extension.getFullExtension()
		});
		DirectoryLoader dl = new CommonsDirectoryWalker(directory, DirectoryFileFilter.INSTANCE, fileFilter);
		if(dl.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading files from directory <{}>\n{}", new Object[]{appName, directory, dl.getLoadErrors().render()});
			return null;
		}

		DataSet<E> ds = new DataSet<>(type);
		ds.setRefKeyMap(refKeyMap);
		ds.load(dl, extension.getExtension(), keySep, target, appName);
		return ds;
	}

	public static <E extends DataEntry> void writeStats(DataSet<E> ds, String type, String appName, boolean verbose){
		if(verbose){
			Skb_Console.conInfo("{}: parsed <{}> " + type + " from <{}> files", new Object[]{appName, ds.getEntries().size(), ds.getFileNumber()});
		}
	}

	public static <E extends DataEntry> ST writeST(DataSet<E> ds, DataEntryType type, DataTarget target, String appName) {
		StgFileLoader stgLoader = new StgFileLoader(target.getStgFileName(type));
		if(stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{appName, target.getStgFileName(type), stgLoader.getLoadErrors().render()});
			return null;
		}

		STGroup stg = stgLoader.load();
		if(stg==null || stgLoader.getLoadErrors().size()>0){
			Skb_Console.conError("{}: errors loading STG file <{}>\n{}", new Object[]{appName, target.getStgFileName(type), stgLoader.getLoadErrors().render()});
			return null;
		}
		//TODO validate STG file
		ST st = stg.getInstanceOf("build");
		for(E entry : ds.getEntries()){
				st.add("entry", entry);
		}

		return st;
	}

	public static <E extends DataEntry> ST addToST(DataSet<E> ds, ST st, String appName){
		for(E entry2 : ds.getEntries()){
			st.add("entry2", entry2);
		}
		return st;
	}

	public static int writeFile(ST st, FileTarget ft, String appName) {
		if(ft!=null && ft.asFile()!=null){
			try {
				FileUtils.write(ft.asFile(), st.render());
			}
			catch (IOException e) {
				Skb_Console.conError("{}: catched IO Exception <{}> -> {}", new Object[]{appName, e.getCause(), e.getMessage()});
				return -7;
			}
		}
		else{
			System.out.println(st.render());
			return 0;
		}
		return 0;
	}

	public static Object getDataObject(EntryKey key, Map<String, Object> entryMap){
		return getDataObject(key, entryMap, null);
	}

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
