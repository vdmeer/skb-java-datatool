package de.vandermeer.skb.datatool.commons;

import de.vandermeer.skb.base.encodings.Translator;
import de.vandermeer.skb.base.encodings.TranslatorFactory;
import de.vandermeer.skb.base.encodings.TranslatorFactory.Target;
import de.vandermeer.skb.datatool.target.DataTarget;

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

	/** Map with linkeable data entries from other sets. */
	private LoadedTypeMap loadedTypes;

	/** Types supported by the loader. */
	private TypeLoaderMap supportedTypes;

	public CoreSettings(char keySeparator, boolean verbose, String appName, String inputDir, DataTarget target, TypeLoaderMap supportedTypes){
		this.keySeparator = keySeparator;
		this.verbose = verbose;
		this.appName = appName;
		this.inputDir = inputDir;
		this.target = target;
		this.supportedTypes = supportedTypes;

		this.loadedTypes = new LoadedTypeMap();
	}

	public char getKeySeparator(){
		return this.keySeparator;
	}

	public boolean getVerbose(){
		return this.verbose;
	}

	public String getAppName(){
		return this.appName;
	}

	public String getInputDir(){
		return this.inputDir;
	}

	public DataTarget getTarget(){
		return this.target;
	}

	public LoadedTypeMap getLoadedTypes(){
		return this.loadedTypes;
	}

	public TypeLoaderMap getSupportedTypes(){
		return this.supportedTypes;
	}

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
