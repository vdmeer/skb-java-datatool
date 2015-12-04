package de.vandermeer.skb.datatools;

import org.junit.Test;

import de.vandermeer.skb.datatool.DataTool;

public class Test_EclRun_DataTool {

	@Test
	public void test_Run_DataTool(){
		if("true".equals(System.getProperty("EclRun"))){
			String[] args = new String[]{
//					"--help",
//					"target",
//					"entry-type",

					"--entry-type",
//					"acronyms",
					"affiliations",
//					"continents",
//					"countries",
//					"cities",
//					"encodings",

					"--target",
//					"html-tab",
//					"latex-acr",
					"text-plain",
//					"java-skb-h2l",
//					"java-skb-t2h",
//					"java-skb-t2l",

					"--input-dir",
					"V:/dev/github/skb/data",

					"--verbose",

//					"--output-file",
//					"2",

//					"--key-sep",
//					"/"
			};
			DataTool dt = new DataTool();
			int ret = dt.executeApplication(args);
			if(ret!=0){
				System.err.println(ret);
			}
		}
	}

}
