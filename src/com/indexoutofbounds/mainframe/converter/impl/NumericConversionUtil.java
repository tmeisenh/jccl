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
package com.indexoutofbounds.mainframe.converter.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.indexoutofbounds.mainframe.converter.MainFrameGlobals;
import com.indexoutofbounds.mainframe.exception.NumberSizeException;
import com.indexoutofbounds.mainframe.exception.Sock7Exception;

public class NumericConversionUtil extends BaseCobolUtils {


	/**
	 * Logger this class uses.
	 */
	private static Logger log = Logger.getLogger(NumericConversionUtil.class);	

	public NumericConversionUtil() {
		
	}
	
	/**
	 * Build a Java <code>String</code> from a fixed point numeric byte
	 * representation.  The precision is calculated internally based
	 * on the length of the byte[].
	 * 
	 * This method is the most efficient way in dealing with large values and
	 * large precision values. This method also returns a String representation
	 * of the data EXACTLY as it appeared in the byte[] except for the inserting
	 * of a period where a decimal point was required.
	 *
	 * @param buffer - the byte[] containing the packed decimal
	 * @param scale - the number of digits after the implied decimal
	 * @return String of the converted packed data
	 * @throws Sock7Exception if the specified representation is not recognized.
	 */
	public static final String convertNumericToString(byte[] buffer, int scale) throws Sock7Exception {

		StringBuffer sb = new StringBuffer();
		
		// This throws an exception if the sign bit isn't valid.		
		if (isNegativeHighNybbleSignBit(buffer[buffer.length - 1])) {
			sb.append('-');
		}

		sb.append(numericNybblesToString(buffer));
		// set the decimal at the right place
		if (scale > 0) {
			sb.insert((sb.length() - scale), '.');
		} else {
			for (int i = 0; i < scale; i++)
				sb.append("0");
		}

		return sb.toString();
	}

	/**
	 * Build a Java <code>double</code> from a signed numeric (NOT PACKED) decimal byte
	 * representation.  The precision is calculated internally based
	 * on the length of the byte[].
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @param scale - the number of digits after the implied decimal
	 * @return double amount of the packed data
	 * @throws Sock7Exception if the specified representation is not recognized.
	 */
	public static final double convertNumericToDouble(byte[] buffer, int scale) throws Sock7Exception {

		if (buffer.length > DOUBLE_PRECISION_LIMIT) {

			throw new NumberSizeException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		// check for sign.
		int signum = digOutSignMultiplierHighNybble(buffer[buffer.length - 1]);

		double value = numericNybblesToDouble(buffer);
		return signum * value / Math.pow(10, scale);
	}

	/**
	 * Build a Java <code>long</code> from a signed numeric (NOT PACKED) decimal byte
	 * representation.  The precision is calculated internally based
	 * on the length of the byte[].
	 *  
	 * @deprecated use numericBytesIntoDouble instead
	 * @param buffer - the byte[] containing the packed decimal
	 * @param scale - the number of digits after the implied decimal
	 * @return long amount of the packed data
	 * @throws Sock7Exception if the specified representation is not recognized.
	 */
	public static final long convertNumericToLong(byte[] buffer, int scale) throws Sock7Exception {

		if (buffer.length > DOUBLE_PRECISION_LIMIT) {
			throw new NumberSizeException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		// check for sign.	
		int signum = digOutSignMultiplierHighNybble(buffer[buffer.length - 1]);

		double d = numericNybblesToDouble(buffer);

		if (Long.MAX_VALUE > d && d < Long.MAX_VALUE) {
			throw new NumberSizeException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}
		// The double shouldn't contain any precision values...
		return (long) (signum * d / Math.pow(10, scale));
	}	


	/**
	  * Converts an int into a numeric.  Typically in COBOL a NUMERIC
	  * is written like S9(3) which means the largest number is 999 but the number
	  * can range like 099 and 009 -- note the zeros for padding.  This method handles
	  * all the oddities of COBOL land with regard to packing the number.
	  * 
	  * Note: scale can be less than zero. This means that the assumed decimal point 
	  * is -scale digits to the right of the LSD. For example, if the data in the 
	  * input file is 123 and the scale is –2, then the value thus represented is 12300. 
	  * 
	  * @param val the value to pack 
	  * @param byteLength the expected output byte[] length
	  * @param scale the number of digits after the implied decimal
	  * @return byte[] reprensentation of a packed decimal
	  * @throws Sock7Exception
	  */
	public static byte[] convertIntToNumeric(int val, int byteLength, int scale, boolean isSigned) throws Sock7Exception {

		if (!isWithinTolerance(val)) {
			throw new NumberSizeException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		char sign;
		if (!isSigned) {
			sign = SIGN_UNSIGNED;
		} else {
			sign = (val < 0) ? SIGN_NEG : SIGN_POS;
		}

		return packDataIntoBytes(normalizeJavaDataIntoNumericByteString(formatIntValue(val), byteLength, scale, sign));
	}

	/**
	 * Converts a double into a signed numeric.  Typically in COBOL a NUMERIC
	 * is written like S9(3)V99 which means the largest number is 999.99 but the number
	 * can range like 099.99 and 009.90 -- note the zeros for padding.  This method handles
	 * all the oddities of COBOL land with regard to packing the decimal.
	 * 
	 * Note: scale can be less than zero. This means that the assumed decimal point 
	 * is -scale digits to the right of the LSD. For example, if the data in the 
	 * input file is 123 and the scale is –2, then the value thus represented is 12300.  
	 * 
	 * @param val the value to sign
	 * @param byteLength the expected output byte[] length
	 * @param scale the number of digits after the implied decimal
	 * @param isSigned - if this double is signed
	 * @return byte[] reprensentation of a packed decimal
	 * @throws Sock7Exception
	 */
	public static byte[] convertDoubleToNumeric(double val, int byteLength, int scale, boolean isSigned) throws Sock7Exception {

		if (!isWithinTolerance(val)) {
			throw new NumberSizeException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		char sign;
		if (!isSigned) {
			sign = SIGN_UNSIGNED;
		} else {
			sign = (val < 0) ? SIGN_NEG : SIGN_POS;
		}

		return packDataIntoBytes(normalizeJavaDataIntoNumericByteString(formatDoubleValue(val), byteLength, scale, sign));
	}

	/** 
	 * Unpacks a numeric (signed/un) field checking the upper nybble to make sure
	 * it contains a full bit mask and that the byte value is within the limits
	 * of what a numeric can hold.
	 *
	 * @param b
	 * @return int
	 * @throws Sock7Exception
	 */
	private static int unpackNumericByte(byte b) throws Sock7Exception {

		if (b > NUMERIC_BYTE_MAX_VALUE)
			throw new Sock7Exception(MainFrameGlobals.COBOLConversionUtils_ByteLengthTooLarge);
		
		int[] nbyte = bustByteIntoNybbles(b);
		
		// According to the COBOL standards, the upper nybble of a NUMERIC field's byte can only contain a F (1111)
		// for the non-sign-bit-bytes.  The sign-bit can contain either an 0xF (unsigned), 0xC or 0xD (for signed numerics),
		// or possibly a 0 if the field is "NULLED."	
		
		// Thus, nulled values contain 0 in both nybbles
		if(nbyte[0] == 0 & nbyte[1] == 0)
			return nbyte[1];
		
		if (STRICT_TRANSLATION != 0) {
			if (nbyte[0] != 0xF)
				throw new Sock7Exception(MainFrameGlobals.COBOLConversionUtils_NumericUpperNybbleLow + " , " + nbyte[0] + ":" + nbyte[1]);
		}
		
		return nbyte[1];
	}

	/**
	 * Convert a range of signed nybbles to a String. Each byte up until the sign bit
	 * containing byte gets checked to make sure the upper nybble contains a full set of bits. 
	 *
	 * @param buffer - the byte[] containing the packed decimal
	 * @return String containing the unpacked data
	 * @throws Sock7Exception
	 */
	private static final String numericNybblesToString(byte[] buffer) throws Sock7Exception {

		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < buffer.length - 1; i++) {
			sb.append(unpackNumericByte(buffer[i]));
		}
		
		// We skip this value in the loop b/c the sign-bit will throw off the check.
		// The signbit is checked for validity elsewhere.	
		sb.append(digOutLowNybbleValue(buffer[buffer.length - 1]));

		return sb.toString();
	}

	/**
	 * Convert a range of signed nybbles to a int. Each byte up until the sign bit
	 * containing byte gets checked to make sure the upper nybble contains a full set of bits. 
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @return int amount of the packed data
	 * @throws Sock7Exception
	 */
	private static final int numericNybblesToInt(byte[] buffer) throws Sock7Exception {

		int value = 0;
		
		for (int i = 0; i < buffer.length - 1; i++) {
			value = value * 10 + unpackNumericByte(buffer[i]);
		}

		// We skip this value in the loop b/c the sign-bit will throw off the check.
		// The signbit is checked for validity elsewhere.		
		value = value * 10 + digOutLowNybbleValue(buffer[buffer.length]);

		return value;
	}

	/**
	 * Convert a range of signed nybbles to a long. Each byte up until the sign bit
	 * containing byte gets checked to make sure the upper nybble contains a full set of bits. 
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @return long amount of the packed data
	 * @throws Sock7Exception
	 */
	private static final long numericNybblesToLong(byte[] buffer) throws Sock7Exception {

		long value = 0;
		
		for (int i = 0; i < buffer.length - 1; i++) {
			value = value * 10 + unpackNumericByte(buffer[i]);
		}
		
		// We skip this value in the loop b/c the sign-bit will throw off the check.
		// The signbit is checked for validity elsewhere.
		value = value * 10 + digOutLowNybbleValue(buffer[buffer.length]);
		
		return value;
	}

	/**
	 * Convert a range of signed nybbles to a double. Each byte up until the sign bit
	 * containing byte gets checked to make sure the upper nybble contains a full set of bits. 
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @return double amount of the packed data
	 * @throws Sock7Exception
	 */
	private static final double numericNybblesToDouble(byte[] buffer) throws Sock7Exception  {

		double value = 0;

		for (int i = 0; i < buffer.length - 1; i++) {
			value = value * 10 + unpackNumericByte(buffer[i]);
		}

		// We skip this value in the loop b/c the sign-bit will throw off the check.
		// The signbit is checked for validity elsewhere.
		value = value * 10 + digOutLowNybbleValue(buffer[buffer.length - 1]);
		
		return value;
	}

	/**
	 * Converts an input numeric String into the proper form for COBOL NUMERIC.  That is, we
	 * append the sign and do some length checking and adding zeros when necessary (as well as
	 * stripping the period out of a double).
	 * 
	 * Note: scale can be less than zero. This means that the assumed decimal point 
	 * is -scale digits to the right of the LSD. For example, if the data in the 
	 * input file is 123 and the scale is –2, then the value thus represented is 12300. 
	 * 
	 * @param value the numeric value just String.valueOf(...)
	 * @param byteLength length of bytes the output array must be
	 * @param scale - the scale.  We can have negative scales, btw. It's retarded but COBOL guys use it.
	 * @param sign the sign
	 * @return normalized String of the value
	 * @throws Sock7Exception
	 */
	private static String normalizeJavaDataIntoNumericByteString(String value, int byteLength, int scale, char sign) throws Sock7Exception {

		StringBuffer sb = new StringBuffer();
		int precedingZeros = 0;

		StringBuffer tmp = new StringBuffer();
		tmp.append(value);

		// Remove the - sign
		if (tmp.charAt(0) == '-')
			tmp.deleteCharAt(0);

		for (int i = 0; i < tmp.length() - 1; i++) {
			if (tmp.charAt(i) != '.') {
				sb.append('F');
				sb.append(tmp.charAt(i));
			}
		}

		// Stick in the sign
		sb.append(sign);
		sb.append(tmp.charAt(tmp.length() - 1));

		// Find out if we have a decimal
		int decPos = value.indexOf(".");

		if (scale < 0)
			throw new Sock7Exception(MainFrameGlobals.COBOLConversionUtils_InvalidScale);

		if (decPos > -1 & scale <= 0)
			throw new Sock7Exception(MainFrameGlobals.COBOLConversionUtils_InvalidScale);

		if (decPos != -1) {
			precedingZeros = scale - sb.toString().substring(sb.toString().indexOf(".") + 1).length();
		}

		int zeroFill = (byteLength * 2) - sb.length();
		if (zeroFill < 0) {
			throw new Sock7Exception(MainFrameGlobals.COBOLConversionUtils_ByteLengthTooSmall);
		}

		for (int i = 0; i < zeroFill; i++)
			sb.insert(0, "F0");

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug(
				"COBOLConversionUtil.normalizeJavaDataIntoNumericByteString("
					+ value
					+ ", "
					+ byteLength
					+ ", "
					+ scale
					+ ", "
					+ sign
					+ ") output: "
					+ displayInBytePattern(sb.toString()));
		}

		return sb.toString();
	}

	
}
