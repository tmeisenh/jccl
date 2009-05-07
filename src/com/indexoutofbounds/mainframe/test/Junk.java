package com.indexoutofbounds.mainframe.test;

public class Junk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte [] b = new byte [] {0, 8, 0xC};
		
		for(int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}

	}

}
