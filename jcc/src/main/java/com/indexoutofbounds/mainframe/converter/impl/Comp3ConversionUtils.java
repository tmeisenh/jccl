/*
 * Copyright (c) 2005 - 2007 Travis B. Meisenheimer
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
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;

/**
 * Algorithms to convert COMP-3 data.
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 * @version $Id$
 */
public class Comp3ConversionUtils extends BaseCobolUtils {

	/**
	 * Logger this class uses.
	 */
	private static Logger log = Logger.getLogger(Comp3ConversionUtils.class);

	public Comp3ConversionUtils() {
	}
    
    public static final char SIGN_POS = 0x43;
    public static final char SIGN_NEG = 0x44;
    public static final char SIGN_UNSIGNED = 0x46;
    
	/**
	 * Build a Java <code>String</code> from a fixed point decimal byte COMP-3
	 * representation. The precision is calculated internally based on the
	 * length of the byte[]. This method is the most efficient way in dealing
	 * with large values and large precision values. This method also returns a
	 * String representation of the data EXACTLY as it appeared in the byte[]
	 * except for the inserting of a period where a decimal point was required.
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @param scale - the number of digits after the implied decimal
	 * @return String of the converted packed data
	 * @throws MainFrameConversionException if the specified representation is not recognized.
	 */
	public static final String convertCOMP3ToString(byte[] buffer, int scale) throws MainFrameConversionException {

		StringBuffer sb = new StringBuffer();

		if (isNegativeLowNybbleSignBit(buffer[buffer.length - 1])) {
			sb.append('-');
		}

		sb.append(packedNybblesToString(buffer));

		// set the decimal at the right place
		if (scale > 0) {
			// TODO check this here?
			sb.insert((sb.length() - scale), '.');
		} else {
			for (int i = 0; i < scale; i++)
				sb.append("0");
		}

		return sb.toString();
	}

	/**
	 * Build a Java <code>double</code> from a fixed point decimal byte
	 * representation. The precision is calculated internally based on the
	 * length of the byte[].
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @param scale - the number of digits after the implied decimal
	 * @return double amount of the packed data
	 * @throws MainFrameConversionException if the specified representation is not recognized.
	 */
	public static final double convertCOMP3ToDouble(byte[] buffer, int scale) throws MainFrameConversionException {

		// check for sign.
		int signum = digOutSignMultiplierLowNybble(buffer[buffer.length - 1]);

		if (determinePrecision(buffer.length) > DOUBLE_PRECISION_LIMIT) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		return signum * packedNybblesToDouble(buffer) / Math.pow(10, scale);
	}

	/**
	 * Build a Java <code>long</code> from a fixed point decimal byte
	 * representation. The precision is calculated internally based on the
	 * length of the byte[]. This implies that the returned value will be up to
	 * 18 digits WITHOUT a decimal place.
	 * 
	 * @deprecated use packedBytesIntoDouble instead
	 * @param buffer - the byte[] containing the packed decimal
	 * @param scale - the number of digits after the implied decimal
	 * @return long amount of the packed data
	 * @throws MainFrameConversionException if the specified representation is not recognized.
	 */
	public static final long convertCOMP3ToLong(byte[] buffer, int scale) throws MainFrameConversionException {

		if (determinePrecision(buffer.length) > DOUBLE_PRECISION_LIMIT) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		double d = (digOutSignMultiplierLowNybble(buffer[buffer.length - 1]) * packedNybblesToDouble(buffer));

		if (Long.MAX_VALUE > d && d < Long.MAX_VALUE) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		return Long.parseLong(decimalformat.format(d / Math.pow(10, scale)));
	}

	/**
	 * Converts an int into a packed decimal. Typically in COBOL a packed number
	 * is written like S9(3) which means the largest number is 999 but the
	 * number can range like 099 and 009 -- note the zeros for padding. This
	 * method handles all the oddities of COBOL land with regard to packing the
	 * number. Note: scale can be less than zero. This means that the assumed
	 * decimal point is -scale digits to the right of the LSD. For example, if
	 * the data in the input file is 123 and the scale is –2, then the value
	 * thus represented is 12300.
	 * 
	 * @param val the value to pack
	 * @param byteLength the expected output byte[] length
	 * @param scale the number of digits after the implied decimal
	 * @return byte[] reprensentation of a packed decimal
	 * @throws MainFrameConversionException
	 */
	public static byte[] convertIntToCOMP3(int val, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException {

		if (!isWithinTolerance(val)) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		char sign;
		if (!isSigned) {
			sign = SIGN_UNSIGNED;
		} else {
			sign = (val < 0) ? SIGN_NEG : SIGN_POS;
		}

		return packDataIntoBytes(normalizeJavaDataIntoCOMP3ByteString(formatIntValue(val), byteLength, scale, sign));
	}

	/**
	 * Converts a double into a packed decimal. Typically in COBOL a packed
	 * decimal is written like S9(3)V99 which means the largest number is 999.99
	 * but the number can range like 099.99 and 009.90 -- note the zeros for
	 * padding. This method handles all the oddities of COBOL land with regard
	 * to packing the decimal. Note: scale can be less than zero. This means
	 * that the assumed decimal point is -scale digits to the right of the LSD.
	 * For example, if the data in the input file is 123 and the scale is –2,
	 * then the value thus represented is 12300.
	 * 
	 * @param val the value to pack
	 * @param byteLength the expected output byte[] length
	 * @param scale the number of digits after the implied decimal
	 * @param isSigned - if this double is signed
	 * @return byte[] reprensentation of a packed decimal
	 * @throws MainFrameConversionException
	 */
	public static byte[] convertDoubleToCOMP3(double val, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException {

		if (!isWithinTolerance(val)) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_NumberSizeMessage);
		}

		char sign;
		if (!isSigned) {
			sign = SIGN_UNSIGNED;
		} else {
			sign = (val < 0) ? SIGN_NEG : SIGN_POS;
		}

		return packDataIntoBytes(normalizeJavaDataIntoCOMP3ByteString(formatDoubleValue(val), byteLength, scale, sign));
	}

	/**
	 * Unpacks a COMP3 byte into an int[] checking to make sure the bytes are
	 * within our tolerable limits. Note: You can't pass the sign-bit containing
	 * byte into this method or it will puke.
	 * 
	 * @param b
	 * @return int[]
	 * @throws MainFrameConversionException
	 */
	private static int[] unpackCOMP3Byte(byte b) throws MainFrameConversionException {

		if (b > PACKED_BYTE_MAX_VALUE) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_ByteLengthTooLarge);
		}

		int[] cbyte = bustByteIntoNybbles(b);

		if (STRICT_TRANSLATION != 0) {
			// TODO check this
			if (cbyte[0] > 9 || cbyte[1] > 9)
				throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_COMP3_Value + " [" + cbyte[0] + ":" + cbyte[1] + "]");
		}
		return cbyte;
	}

	/**
	 * Convert a range of packed (COMP-3) nybbles to a double. Each byte up
	 * until the sign bit containing byte gets checked to make sure the nybbles
	 * contain valid values.
	 * 
	 * @deprecated - use packedNybblesToDouble instead. Functionally, this
	 *             method is still safe to use.
	 * @param buffer - the byte[] containing the packed decimal
	 * @return int amount of the packed data
	 * @throws MainFrameConversionException if the bytes contain values too large
	 */
	private static final int packedNybblesToInt(byte[] buffer) throws MainFrameConversionException {

		int value = 0;

		for (int i = 0; i < buffer.length - 1; i++) {

			int[] cbyte = unpackCOMP3Byte(buffer[i]);
			// We multiply by 10 to advance the number to the next order of
			// magnitude
			// in terms of one, tens, hundreds, thousands, etc
			value = value * 10 + cbyte[0];
			value = value * 10 + cbyte[1];
		}

		// We skip this value in the loop b/c the sign-bit will throw off the
		// check.
		// The signbit is checked for validity elsewhere.
		value = value * 10 + digOutHighNybbleValue(buffer[buffer.length - 1]);

		return value;
	}

	/**
	 * Convert a range of packed (COMP-3) nybbles to a double. Each byte up
	 * until the sign bit containing byte gets checked to make sure the nybbles
	 * contain valid values.
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @return double amount of the packed data
	 * @throws MainFrameConversionException if the bytes contain values too large
	 */
	private static final double packedNybblesToDouble(byte[] buffer) throws MainFrameConversionException {

		double value = 0;

		for (int i = 0; i < buffer.length - 1; i++) {

			int[] cbyte = unpackCOMP3Byte(buffer[i]);
			// We multiply by 10 to advance the number to the next order of
			// magnitude
			// in terms of one, tens, hundreds, thousands, etc
			value = value * 10 + cbyte[0];
			value = value * 10 + cbyte[1];
		}

		// We skip this value in the loop b/c the sign-bit will throw off the
		// check.
		// The signbit is checked for validity elsewhere.
		value = value * 10 + digOutHighNybbleValue(buffer[buffer.length - 1]);

		return value;
	}

	/**
	 * Convert a range of packed nybbles (up to 9 digits without overflow) to an
	 * String.
	 * 
	 * @param buffer - the byte[] containing the packed decimal
	 * @return String containing the unpacked data
	 * @throws MainFrameConversionException if the bytes contain values too large
	 */
	private static final String packedNybblesToString(byte[] buffer) throws MainFrameConversionException {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < buffer.length - 1; i++) {

			int[] cbyte = unpackCOMP3Byte(buffer[i]);
			// We multiply by 10 to advance the number to the next order of
			// magnitude
			// in terms of one, tens, hundreds, thousands, etc
			sb.append(cbyte[0]);
			sb.append(cbyte[1]);
		}

		// We skip this value in the loop b/c the sign-bit will throw off the
		// check.
		// The signbit is checked for validity elsewhere.
		sb.append(digOutHighNybbleValue(buffer[buffer.length - 1]));

		return sb.toString();
	}

	/**
	 * Converts an input numeric String into the proper form for COBOL COMP-3.
	 * That is, we append the sign and do some length checking and adding zeros
	 * when necessary (as well as stripping the period out of a double). Note:
	 * scale can be less than zero. This means that the assumed decimal point is
	 * -scale digits to the right of the LSD. For example, if the data in the
	 * input file is 123 and the scale is –2, then the value thus represented is
	 * 12300.
	 * 
	 * @param value the numeric value just String.valueOf(...)
	 * @param byteLength length of bytes the output array must be
	 * @param scale - the scale. We can have negative scales, btw. It's retarded
	 *            but COBOL guys use it.
	 * @param sign the sign
	 * @return normalized String of the value
	 * @throws MainFrameConversionException
	 */
	private static String normalizeJavaDataIntoCOMP3ByteString(String value, int byteLength, int scale, char sign) throws MainFrameConversionException {

		StringBuffer sb = new StringBuffer();
		sb.append(value);

		// Find out if we have a decimal
		int decPos = value.indexOf(".");

		// Remove the negative sign :^)
		if (sb.charAt(0) == '-')
			sb.deleteCharAt(0);

		if (scale < 0)
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_InvalidScale);

		if (decPos > -1 & scale <= 0)
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_InvalidScale);

		if (decPos != -1) {
			// Figure out by how much we need to move the decimal position
			int trailingZeros = scale - sb.toString().substring(sb.toString().indexOf(".") + 1).length();
			sb.deleteCharAt(decPos);

			for (int i = 0; i < trailingZeros; i++)
				sb.append("0");
		}

		sb.append(Character.toUpperCase(sign));
		// Figure out how much we need to pad this number to make it the right
		// byte length.
		int zeroFill = (byteLength * 2 - 1) - sb.length();
		if (zeroFill < 0) {
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_ByteLengthTooSmall);
		}

		for (int i = 0; i < zeroFill; i++)
			sb.insert(0, "0");

		// Insert zero to make string even lengthed
		if (sb.toString().length() % 2 != 0)
			sb.insert(0, "0");

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("Comp3ConversionUtils.normalizeJavaDataIntoCOMP3ByteString(" + value + ", " + byteLength + ", "
					+ scale + ", " + sign + ") output: " + displayInBytePattern(sb.toString()));
		}

		return sb.toString();
	}

}
