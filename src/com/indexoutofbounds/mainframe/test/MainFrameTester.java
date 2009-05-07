package com.indexoutofbounds.mainframe.test;

import java.text.DecimalFormat;

import com.indexoutofbounds.mainframe.charsets.Charset;
import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.converter.MainFrameGlobals;
import com.indexoutofbounds.mainframe.converter.impl.CobolAndAsciiConverter;

/**
 * Simple testing routine which...
 * 	Packs an int
 * 	Packs a decimal
 *  Converts an ASCII string into EBCDIC
 *  Converts a (recently converted) EBCDIC string back to Ascii
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */
public class MainFrameTester {

	public static void main(String[] args) {
		
		
	
		try {
			
			double test = 23.12345678958465716;
				
			// Pack Java types into COMP-3 and NUMERIC byte arrays
			double big = Math.pow(2, 1008);
							
			int ii = 1000000000;
			
			Charset charset = new Charset();
			ICobolAndJavaConverter charconv = new CobolAndAsciiConverter(charset, MainFrameGlobals.DEFAULT_ENCODING);			
			
			byte [] t1 = charconv.packIntIntoCOMP3(7600, 3, 0, true);
			byte [] t2 = charconv.packIntIntoCOMP3(-76, 3, 0, true);
			
			byte [] t3 = charconv.packDoubleIntoCOMP3(big, 154, 2, true);
			
			byte [] t4 = charconv.numericFromDouble(-18.65, 4, 2, true);
			byte [] t5 = charconv.numericFromInt(76, 3, 0, true); // pad with F0
			
			System.out.println("TEST: " + charconv.convertCOMP3WithHighPrecision(t3, 2));
			System.out.println("TEST 2: " + charconv.convertNumericValueWithHighPrecision(t4, 2));
			System.out.println("Test 2 ORIG " + charconv.convertNumericValue(t4,2));
			
			System.out.println("Packing data into COMP-3 and NUMERIC...");
				
			System.out.println(appendByteArrayToString(t1));
			System.out.println(appendByteArrayToString(t2));
			System.out.println(appendByteArrayToString(t3));
			System.out.println(appendByteArrayToString(t4));
			System.out.println(appendByteArrayToString(t5));
			
			// Unpack what we just packed...
			
			System.out.println("Unpacking what we just packed...");

			System.out.println("COMP-3 " + charconv.convertCOMP3(t1,0));
			System.out.println("COMP-3 " + charconv.convertCOMP3(t2,0)); /* leading zeros get stripped! */
			System.out.println("COMP-3 " + charconv.convertCOMP3(t3,2));
			
			System.out.println("NUMERIC " + charconv.convertNumericValue(t4,2));
			System.out.println("NUMERIC " + charconv.convertNumericValue(t5,0));

			// Test out converting to EBCDIC from ASCII

			charconv.configureAsciiToEBCDIC();
			String str = "With the lights out it's less dangerous, Here we are now, entertain us...";
			
			System.out.println("Converting ASCII to EBCDIC");
			
			byte [] ebcdicvalue = charconv.convertString(str, str.length());
			
			String convertedEBCDIC = new String(ebcdicvalue);
			
			//System.out.println(convertedEBCDIC);
			System.out.println(appendByteArrayToString(ebcdicvalue));
			
			System.out.println("");
			//æ?£?@£??@????£¢@?¤£@?£}¢@??¢¢@???????¤¢k@È???@¦?@???@??¦k@??£??£???@¤¢KKK
			
			// Convert from ASCII to EBCDIC
			System.out.println("Converting EBDCIC to ASCII ");
			charconv.configureEBCDICToAscii();
			
			String ascii = charconv.convertAlphaNumericDisplay(ebcdicvalue);
			System.out.println(ascii);
			
				
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}
	
	public static String appendByteArrayToString(byte [] b) {
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < b.length; i++) {
			sb.append( (char) b[i] );
		}
		
		return sb.toString();
	}
}
