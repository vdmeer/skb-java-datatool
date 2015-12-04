package de.vandermeer.skb.datatools;

import org.junit.Test;

import de.vandermeer.skb.datatool.DataTool;

public class QuickTest {

	@Test
	public void testDataTool(){
		String[] args = new String[]{
//				"--help",
//				"target",
//				"entry-type",

				"--entry-type",
				"acronyms",
//				"affiliations",

				"--target",
				"html-tab",
//				"latex-acr",
//				"text-plain",
//				"java-skb-t2h",

				"--input-dir",
				"V:/dev/github/skb/data",

				"--verbose",

				"--output-file",
				"2",

//				"--key-sep",
//				"/"
		};
		DataTool dt = new DataTool();
		int ret = dt.executeApplication(args);
		if(ret!=0){
			System.err.println(ret);
		}
	}

}
