/* $Id$ */
package com.indexoutofbounds.mainframe.charset.test;

import com.indexoutofbounds.mainframe.charsets.Charset;

import junit.framework.TestCase;

public class CharsetTest extends TestCase {
	
	public Charset getAsciiCharset() {
		Charset c = new Charset();
		c.configureAsciiToEBCDIC();
		return c;
	}
	
	public Charset getEbcdicCharset() {
		Charset c = new Charset();
		c.configureEBCDICToAscii();
		return c;
	}
	
	public void testCharsetConfiguration() {
		Charset ascii = getAsciiCharset();
		assertTrue("Testing Ascii Configuration", ascii.isConfiguredForAsciiToEBCDIC());

	
			
	}

}
