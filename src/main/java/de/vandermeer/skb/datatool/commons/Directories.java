package de.vandermeer.skb.datatool.commons;

import java.io.File;

public enum Directories {

	ACRONYMS ("acronyms"),
	AFFILIATIONS ("affiliations"),

	;

	/** A directory with data entries. */
	String directory;

	Directories(String dir){
		this.directory = dir;
	}

	/**
	 * Returns the directory.
	 * @param baseDir base directory
	 * @return directory as base directory plus the set directory
	 */
	public String getDir(String baseDir){
		return baseDir + File.separator + this.directory;
	}
}
