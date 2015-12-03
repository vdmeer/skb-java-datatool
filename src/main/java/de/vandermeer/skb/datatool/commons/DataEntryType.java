package de.vandermeer.skb.datatool.commons;

import java.util.Set;

public interface DataEntryType {

	/**
	 * Returns the name of the type
	 * @return type name
	 */
	String getType();

	/**
	 * Returns the file extension the type is using.
	 * @return file extension
	 */
	String getInputFileExtension();

	/**
	 * Returns the full file extension (type plus JOSN).
	 * @return full type extension
	 */
	String getFullInputFileExtension();

	/**
	 * Returns the target the type supports
	 * @return supported targets, empty if none
	 */
	Set<DataTarget> getSupportedTargets();
}
