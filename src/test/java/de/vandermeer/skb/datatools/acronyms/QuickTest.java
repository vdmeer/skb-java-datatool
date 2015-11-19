package de.vandermeer.skb.datatools.acronyms;

import org.junit.Test;

import de.vandermeer.skb.datatool.acronyms.Acronyms;

public class QuickTest {

	@Test
	public void testMe(){
		String[] args = new String[]{
//				"--help",
				"--target",
				"html-tab",
				"--input-dir",
				"V:/dev/github/skb/data/acronyms",
				"--verbose",
				"--output-file",
				"2",
//				"--key-sep",
//				"/"
		};
		Acronyms a = new Acronyms();
		a.executeApplication(args);
	}
}
