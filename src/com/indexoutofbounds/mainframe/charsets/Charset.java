/*
 * Copyright (c) 2005, Travis B. Meisenheimer
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.indexoutofbounds.mainframe.charsets;

import org.apache.log4j.Logger;

/**
 * <p>Class which keeps track of the formats we are using for conversion.  Specifically,
 * we have ASCII and EBCDIC conversion tables only.  Future versions may contain more
 * conversion tables.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class Charset {
	
	public static final int ASCII = 0;
	public static final int EBCDIC = 1;
	
	private int [] toCharset;
	private int [] fromCharset;
	
	private static Logger log = Logger.getLogger(Charset.class);
	
	/**
	 * Contains the entire ASCII table in hex format.
	 * ASCII null is represented as a space.
	 */
	private static final int ASCII_TABLE[] = {0x003f, 0x0020, 0x0021, 0x0022, 0x0023,
		0x0024, 0x0025, 0x0026, 0x0027, 0x0028, 0x0029, 0x002a, 0x002b,
		0x002c, 0x002d, 0x002e, 0x002f, 0x0030, 0x0031, 0x0032, 0x0033,
		0x0034, 0x0035, 0x0036, 0x0037, 0x0038, 0x0039, 0x003a, 0x003b,
		0x003c, 0x003d, 0x003e, 0x003f, 0x0040, 0x0041, 0x0042, 0x0043,
		0x0044, 0x0045, 0x0046, 0x0047, 0x0048, 0x0049, 0x004a, 0x004b,
		0x004c, 0x004d, 0x004e, 0x004f, 0x0050, 0x0051, 0x0052, 0x0053,
		0x0054, 0x0055, 0x0056, 0x0057, 0x0058, 0x0059, 0x005a, 0x005b,
		0x005c, 0x005d, 0x005e, 0x005f, 0x0060, 0x0061, 0x0062, 0x0063,
		0x0064, 0x0065, 0x0066, 0x0067, 0x0068, 0x0069, 0x006a, 0x006b,
		0x006c, 0x006d, 0x006e, 0x006f, 0x0070, 0x0071, 0x0072, 0x0073,
		0x0074, 0x0075, 0x0076, 0x0077, 0x0078, 0x0079, 0x007a, 0x007b,
		0x007c, 0x007d, 0x007e };

	/**
	 * Contains most of the EBCDIC table in hex format.  EBCDIC null is represented
	 * by a space.  The values that don't have an ASCII value were left out.
	 */
	private static final int EBCDIC_TABLE[] = {0x0000, 0x0040, 0x005a, 0x007f, 0x007b,
		0x005b, 0x006c, 0x0050, 0x007d, 0x004d, 0x005d, 0x005c, 0x004e,
		0x006b, 0x0060, 0x004b, 0x0061, 0x00f0, 0x00f1, 0x00f2, 0x00f3,
		0x00f4, 0x00f5, 0x00f6, 0x00f7, 0x00f8, 0x00f9, 0x007a, 0x005e,
		0x004c, 0x007e, 0x006e, 0x006f, 0x007c, 0x00c1, 0x00c2, 0x00c3,
		0x00c4, 0x00c5, 0x00c6, 0x00c7, 0x00c8, 0x00c9, 0x00d1, 0x00d2,
		0x00d3, 0x00d4, 0x00d5, 0x00d6, 0x00d7, 0x00d8, 0x00d9, 0x00e2,
		0x00e3, 0x00e4, 0x00e5, 0x00e6, 0x00e7, 0x00e8, 0x00e9, 0x00ad,
		0x00e0, 0x00bd, 0x005f, 0x006d, 0x0079, 0x0081, 0x0082, 0x0083,
		0x0084, 0x0085, 0x0086, 0x0087, 0x0088, 0x0089, 0x0091, 0x0092,
		0x0093, 0x0094, 0x0095, 0x0096, 0x0097, 0x0098, 0x0099, 0x00a2,
		0x00a3, 0x00a4, 0x00a5, 0x00a6, 0x00a7, 0x00a8, 0x00a9, 0x00c0,
		0x006a, 0x00d0, 0x00a1 };
	
	public static final int [][] TABLES = {ASCII_TABLE, EBCDIC_TABLE};
	
	/**
	 * Constructor which sets the Charset for EBCDIC to ASCII conversion,
	 * which will be the most used path.
	 */
	public Charset() {
		configureEBCDICToAscii();
	} 
	
	/**
	 * Easy switching into Ascii to EBCDIC translation
	 */
	public void configureAsciiToEBCDIC() {
		
		setFromCharset(ASCII);
		setToCharset(EBCDIC);
		
		log.debug("Configuring Charset for ASCII to EBCDIC Translation.");
	}
	
	/**
	 * Easy switching into EBCDIC to ASCII translation.
	 */
	public void configureEBCDICToAscii() {
		
		setFromCharset(EBCDIC);
		setToCharset(ASCII);
		
		log.debug("Configuring Charset for EBCDIC to ASCII Translation.");	
	}
	
	/**
	 * Tests if we are configured for EBCDIC to ASCII translation
	 * @return true/false
	 */
	public boolean isConfiguredForEBCDICToAscii() {
		return (TABLES[EBCDIC] == this.getFromCharset() & TABLES[ASCII] == this.getToCharset());
	}
	
	/**
	 * Tests if we are configured for ASCII to EBCDIC translation
	 * @return true/false
	 */
	public boolean isConfiguredForAsciiToEBCDIC() {
		return (TABLES[ASCII] == this.getFromCharset() & TABLES[EBCDIC] == this.getToCharset());
	}
	
	/**
	 * Gets the Character Tables.
	 * 
	 * @return int [][]
	 */
	
	public int[][] getTables() {
		return TABLES;
	}

	/**
	 * Returns the input charset array table
	 * @return int []
	 */
	public int[] getFromCharset() {
		return fromCharset;
	}

	/**
	 * Returns the output charset array table
	 * @return int []
	 */
	public int[] getToCharset() {
		return toCharset;
	}
	
	public int[] getAsciiTables() {
		return this.ASCII_TABLE;
	}
	
	public int[] getEbcdicTables() {
		return this.EBCDIC_TABLE;
	}
		
//----------------------- Private Methods

	/**
	 * Sets the output charset table to use
	 * @param to int indicating the charset table to use
	 */
	public void setToCharset(int to) {
		this.toCharset = TABLES[to];
	}
	
	/**
	 * Sets the input charset table to use
	 * @param from int indicating the charset table to use
	 */
	private void setFromCharset(int from) {
		this.fromCharset = TABLES[from];
	}
}
