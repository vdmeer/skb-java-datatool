package de.vandermeer.skb.datatools.encodings;

import org.junit.Test;

import de.vandermeer.skb.datatool.encodings.Encodings;

public class QuickTest {

	@Test
	public void testMe(){
		String[] args = new String[]{
//				"--help",
				"--target",
				"html-t2h",
				"--input-dir",
				"V:/dev/github/skb/data/encodings",
				"--verbose",
				"--output-file",
				"2",
//				"--key-sep",
//				"/"
		};
		Encodings e = new Encodings();
		e.executeApplication(args);
	}
}
