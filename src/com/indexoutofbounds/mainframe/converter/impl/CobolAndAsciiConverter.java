/*
 * Copyright (c) 2005 - 2006, Travis B. Meisenheimer
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

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.indexoutofbounds.mainframe.charsets.Charset;
import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.converter.MainFrameGlobals;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.exception.Sock7Exception;

/**
 * <p>This class converts between one charset and another, including handling COMP-3
 * fields.  This is the preferred way to pack/unpack data because it handles all the
 * formatting/oddities that COMP-3 and COBOL will use.</p>
 * 
 * <p>Pay attention to the encoding being used because an improper encoding selection 
 * could result in mistranslation of numeric (NUMERIC/COMP-3) values. Also be careful
 * when you are doing your File I/O to read the binary data (data must be read by a reader
 * which reads data in as a byte[]) because mainframe files commonly contain random garbage 
 * which can cause the file reader to behave erradically. It is possible to have random 
 * EOF characters in the mainframe file at places <i>other</i> than at the EOF position.</p>
 * 
 * <p>There are 255 different EBCDIC values. There are 127 different ASCII values.
 * There are 32 different ASCII control values really only correspond to things
 * like keymappings and terminal bells. These don't mean anything and this is
 * how we get the magic number of 95.</p>
 * 
 * <p>Interesting to note is that Java prefers to use Scientific Notation to represent
 * really large (and also valid) values for the primitives.  You'll never see this in
 * COBOL (Well, you will, but you won't see 1.3E2 you'll see stored 13 and just know that the 
 * scale is something negative) and we must pass large numbers though a formatter to strip 
 * out the SI Notation. However this takes a lot of time so we only do this on numbers 
 * greater than the <code>DOUBLE_THRESHOLD</code> value.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class CobolAndAsciiConverter implements ICobolAndJavaConverter {

	public final int BAD_TRANSLATION_CHAR = (int) '?';
	public final int SIZE = 95;
	private final static DecimalFormat decimalformat = new DecimalFormat("0.00");

	private static Logger log = Logger.getLogger(CobolAndAsciiConverter.class);

	private Charset charset;
	private String encoding = "";

	/**
	 * A CharsetConverter converts text based on a Charset which
	 * dictates the 'to' and 'from' character encodings.  Uses
	 * <code>MainFrameGlobals.DEFAULT_ENCODING</code> as the
	 * encoding setting.
	 * 
	 * This constructor configures the Charset to convert from
	 * EBCDIC to ASCII. 
	 * 
	 * for more information on the default Charsets. 
	 */
	public CobolAndAsciiConverter() {

		this.charset = new Charset();
		this.encoding = MainFrameGlobals.DEFAULT_ENCODING;

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("Creating new CharsetConverter with default encoding: " + this.getEncoding());
		}
	}

	/**
	 * A CharsetConverter converts text based on a Charset which
	 * dictates the 'to' and 'from' character encodings.  Uses
	 * <code>MainFrameGlobals.DEFAULT_ENCODING</code> as the
	 * encoding setting.
	 * 
	 * @param charset - the charset we are using for conversion
	 * for more information on the default Charsets. 
	 */
	public CobolAndAsciiConverter(Charset charset) {

		this.charset = charset;
		this.encoding = MainFrameGlobals.DEFAULT_ENCODING;

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("Creating new CharsetConverter with default encoding: " + this.getEncoding());
		}
	}

	/**
	 * A CharsetConverter converts text based on a Charset which
	 * dictates the 'to' and 'from' character encodings.
	 * 
	 * @param charset - the charset we are using for conversion
	 * @param encoding - the encoding to use
	 * @link http://java.sun.com/j2se/1.4.2/docs/api/java/nio/charset/Charset.html
	 * for more information on the default Charsets.
	 */
	public CobolAndAsciiConverter(Charset charset, String encoding) {

		this.charset = charset;
		this.encoding = encoding;

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("Creating new CharsetConverter with user supplied encoding: " + this.getEncoding());
		}
	}
	/**
	 * 
	 * @return Charset
	 */
	public Charset getCharset() {
		return this.charset;
	}

	/**
	 * 
	 * @param c Charset
	 */

	public void setCharset(Charset c) {
		this.charset = c;
	}

	/**
	 * @return String
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param string
	 */
	public void setEncoding(String string) {
		encoding = string;
	}

	/**
	 * Converts a byte[] containing alpha-numeric (DISPLAY, PIC) COBOL data into a String.
	 *  
	 * This also strips out any ScientificNotation that might have been applied to Really Large numbers.
	 * 
	 * @param ddata the data in bytes
	 * @return String of converted data
	 * @throws MainFrameConversionException if something went wrong
	 */
	public String convertAlphaNumericDisplay(byte[] ddata) throws Sock7Exception {

		// This step is VERY important...
		char[] data = byteArrayToCharArray(ddata, getEncoding());

		StringBuffer text = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			text.append(convert((int) data[i]));
		}

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("convertAlphaNumericDisplay() byte[] of " + String.valueOf(data) + " into " + text.toString());
		}

		return text.toString();
	}

	/**
	 * Converts a byte[] containing a numeric value to a String value corresponding to its
	 * scale and decimal position
	 * 
	 * This also strips out any ScientificNotation that might have been applied to Really Large numbers.
	 * 
	 * @param number byte[] containing the number
	 * @param scale digits right of the decimal point (can be negative)
	 * @return String
	 * @throws Sock7Exception
	 */
	public String convertNumericValue(byte[] number, int scale) throws MainFrameConversionException {

		double d = NumericConversionUtil.convertNumericToDouble(number, scale);

		if (log.isEnabledFor(Level.INFO)) {
			log.info("Raw Numeric value: " + d);
		}

		StringBuffer buff = new StringBuffer(String.valueOf(d));

		// Leave off any decimal and trailing zeros if the scale indicates such.
		if (scale == 0) {
			return buff.substring(0, buff.toString().indexOf("."));
		}

		// Add any additional zeros to the String to satisfy the precision requirements...
		// Plus the one to exclude the period
		int curScale = buff.toString().substring(buff.toString().indexOf(".") + 1).length();
		int amtToPad = scale - curScale;
		/* 
		 * If curScale is ever larger than scale, something went wrong like we described the
		 * data wrong. 
		 */

		if (amtToPad > 0) {
			for (int i = 0; i < amtToPad; i++)
				buff.append("0");
		}

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("convertNumericValue() byte[] of " + String.valueOf(number) + " into " + buff.toString());
		}

		return buff.toString();
	}
	

	/**
	 * Converts a byte[] containing a numeric value to a String value 
	 * corresponding to its scale and decimal position using as the internal
	 * storage variable a String.  This method is the best way for high precision
	 * double values.  This conversion should match the data exactly, including
	 * leading zeros
	 * 
	 * @param number - the byte [] of the comp value
	 * @param scale - the implied decimal location
	 * @throws - MainFrameConversionException
	 */
	public String convertNumericValueWithHighPrecision(byte[] number, int scale) throws MainFrameConversionException {

		StringBuffer sb = new StringBuffer();

		sb.append(NumericConversionUtil.convertNumericToString(number, scale));
		
		return sb.toString();
	}
	

	/**
	 * Converts a byte[] COMP-3 packed decimal into a String.  Internally, the guts for this is taken
	 * from Apache's Derby project.  I just cleaned it up a bit so that "27.50" and not "27.5" is returned.
	 * We wouldn't want to return data that wasn't compliant with the COPYBOOK.
	 * 
	 * This also strips out any ScientificNotation that might have been applied to Really Large numbers.
	 * 
	 * @param buffer - byte[] - This MUST be a byte[]
	 * @param scale digits right of implicit decimal point
	 * @throws MainFrameConversionException if something went wrong
	 */
	public String convertCOMP3(byte[] buffer, int scale) throws MainFrameConversionException {

		double d = Comp3ConversionUtil.convertCOMP3ToDouble(buffer, scale);

		if (log.isEnabledFor(Level.INFO)) {
			log.info("Raw COMP-3 value: " + d);
		}

		StringBuffer buff = new StringBuffer();

		if (d > MainFrameGlobals.DOUBLE_THRESHOLD) {
			decimalformat.applyPattern(MainFrameGlobals.DOUBLE_FORMAT);
			buff.append(decimalformat.format(d));
		} else {
			buff.append(String.valueOf(d));
		}

		// Leave off any decimal and trailing zeros if the scale indicates such.
		if (scale == 0) {
			return buff.substring(0, buff.toString().indexOf("."));
		}

		// Add any additional zeros to the String to satisfy the precision requirements...
		// Plus the one to exclude the period
		int curScale = buff.toString().substring(buff.toString().indexOf(".") + 1).length();
		int amtToPad = scale - curScale;
		/* 
		 * If curScale is ever larger than scale, something went wrong like we described the
		 * data wrong. 
		 */

		if (amtToPad > 0) {
			for (int i = 0; i < amtToPad; i++)
				buff.append("0");
		}

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug(
				"CharsetConverter.convertCOMP3("
					+ String.valueOf(buffer)
					+ ", "
					+ scale
					+ ") byte[] of "
					+ String.valueOf(buffer)
					+ " into "
					+ buff.toString());
		}

		return buff.toString();

	}
	
	/**
	 * Converts a packed decimal, COMP-3 valut into a String using as the internal
	 * storage variable a String.  This method is the best way for high precision
	 * double values.  This conversion should match the data exactly, including
	 * leading zeros
	 * 
	 * @param number - the byte [] of the comp value
	 * @param scale - the implied decimal location
	 * @throws - MainFrameConversionException
	 */

	public String convertCOMP3WithHighPrecision(byte[] number, int scale) throws MainFrameConversionException {

		StringBuffer sb = new StringBuffer();

		sb.append(Comp3ConversionUtil.convertCOMP3ToString(number, scale));
		
		return sb.toString();
	}

	/**
	 * Converts a String of one encoding into a byte[] or another encoding.
	 * Keep in mind that to verify that this was translated properly, you must convert it
	 * back to ascii.
	 * 
	 * TODO This is lazy.
	 * @param value - The String to convert
	 * @param byteLength - the output bytelength
	 * @return byte[] of the output value
	 * @throws MainFrameConversionException if the byte lengths don't match up.
	 */
	public byte[] convertString(String value, int byteLength) throws MainFrameConversionException {

		if (byteLength != value.getBytes().length)
			throw new MainFrameConversionException(MainFrameGlobals.COBOLConversionUtils_ByteLengthNotMatch);

		byte[] ebcdic = new byte[byteLength];

		byte[] ascii = value.getBytes();

		for (int i = 0; i < ascii.length; i++) {
			ebcdic[i] = (byte) convert(ascii[i]);
		}

		return ebcdic;
	}

	/**
	 * Converts a double into a packed decimal.  Typically in COBOL a packed number
	 * is written like S9(3) which means the largest number is 999 but the number
	 * can range like 099 and 009 -- note the zeros for padding.  This method handles
	 * all the oddities of COBOL land with regard to packing the number.
	 * 
	 * Note: scale can be less than zero. This means that the assumed decimal point 
	 * is -scale digits to the right of the LSD. For example, if the data in the 
	 * input file is 123 and the scale is –2, then the value thus represented is 12300. 
	 * 
	 * @param value the value to pack
	 * @param byteLength the expected output byte[] length
	 * @param scale 
	 * @return byte[] reprensentation of a packed decimal
	 */
	public byte[] packIntIntoCOMP3(int value, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException {

		byte[] bytes = Comp3ConversionUtil.convertIntToCOMP3(value, byteLength, scale, isSigned);

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("CharsetConverter.packIntIntoCOMP3(" + value + ", " + byteLength + ", " + scale + isSigned + ") output: " + String.valueOf(bytes));
		}

		return bytes;
	}

	/**
	 * Converts a double into a packed decimal.  Typically in COBOL a packed decimal
	 * is written like S9(3)V99 which means the largest number is 999.99 but the number
	 * can range like 099.99 and 009.90 -- note the zeros for padding.  This method handles
	 * all the oddities of COBOL land with regard to packing the decimal.
	 * 
	 * Note: scale can be less than zero. This means that the assumed decimal point 
	 * is -scale digits to the right of the LSD. For example, if the data in the 
	 * input file is 123 and the scale is –2, then the value thus represented is 12300.  
	 * 
	 * @param value the value to pack
	 * @param byteLength the expected output byte[] length
	 * @param scale the scale
	 * @param isSigned - if this double is signed
	 * @return byte[] reprensentation of a packed decimal
	 */
	public byte[] packDoubleIntoCOMP3(double value, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException {

		byte[] bytes = Comp3ConversionUtil.convertDoubleToCOMP3(value, byteLength, scale, isSigned);

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("CharsetConverter.packDoubleIntoCOMP3(" + value + ", " + byteLength + ", " + scale + isSigned + ") output: " + String.valueOf(bytes));
		}

		return bytes;
	}

	/**
	 * Converts an int into a Signed Numeric or a S(9)
	 * 
	 * @param value - the value to convert
	 * @param byteLength the expected output byte[] length
	 * @param scale the number of digits after the decimal
	 * @param isSigned - if this is a signed numeric or not
	 * @throws Sock7Exception if something went bad
	 */
	public byte[] numericFromInt(int value, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException {

		byte[] bytes = NumericConversionUtil.convertIntToNumeric(value, byteLength, scale, isSigned);

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("CharsetConverter.numericFromInt(" + value + ", " + byteLength + ", " + scale + isSigned + ") output: " + String.valueOf(bytes));
		}

		return bytes;
	}

	/**
	 * Converts a double into a Signed Numeric or a S(9)v99
	 * 
	 * @param value - the value to convert
	 * @param byteLength the expected output byte[] length
	 * @param scale the number of digits after the decimal
	 * @param isSigned - if this is a signed numeric or not
	 * @throws Sock7Exception if something went bad
	 */
	public byte[] numericFromDouble(double value, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException {

		byte[] bytes = NumericConversionUtil.convertDoubleToNumeric(value, byteLength, scale, isSigned);

		if (log.isEnabledFor(Level.DEBUG)) {
			log.debug("CharsetConverter.numericFromDouble(" + value + ", " + byteLength + ", " + scale + isSigned + ") output: " + String.valueOf(bytes));
		}
		return bytes;
	}

	/**
	 * Converts an int into a char using the configured IConverter
	 * 
	 * @param value the int to convert
	 * @throws Sock7Exception if something went wrong
	 */
	protected char convert(int value) throws Sock7Exception {
		return convert(value, this.getCharset());
	}

	/**
	 * Converts a char using the given IConverter
	 * 
	 * @param value the int to convert
	 * @param charset - the Converter to use
	 * @throws Sock7Exception if something went wrong
	 */
	protected char convert(int value, Charset charset) throws Sock7Exception {
		return convert(value, charset.getToCharset(), charset.getFromCharset());
	}

	/**
	 * Converts and int into a char using the given charset arrays.
	 * 
	 * @param value the int to convert
	 * @param toCharset the Charset describing the output format
	 * @param fromCharset the Charset describing the input format
	 * @throws Sock7Exception if something went wrong
	 */
	protected char convert(int value, int[] toCharset, int[] fromCharset) throws Sock7Exception {

		for (int i = 0; i < SIZE; i++) {
			if (value == fromCharset[i]) {
				return (char) toCharset[i];
			}
		}

		//return BAD_TRANSLATION_CHAR;
		throw new Sock7Exception(MainFrameGlobals.CharsetConverter_CONVERT_BAD_TRANSLATION + " " + value);
	}

	/**
	 * Configures the translation to Ascii -> Ebcdic
	 */
	public void configureAsciiToEBCDIC() {
		getCharset().configureAsciiToEBCDIC();

	}

	/**
	 * Configures the translation to Ebcdic -> Ascii
	 */
	public void configureEBCDICToAscii() {
		getCharset().configureEBCDICToAscii();

	}

	/**
	 * Formats a value by converting any Scientific Notation to
	 * the normal (real) value.
	 * 
	 * @param d double
	 * @return String
	 */
	private static String normalizeDouble(double d) {
		return decimalformat.format(d);
	}

	/**
	 * Function tries to determine if a COMP-3 packed decimal was translated
	 * properly.  Basically, a properly converted decimal will contain a plus,
	 * minus, or zero to indicate the sign, then one or more numbers which may
	 * also be separated by a period.  This really just guesses as we don't
	 * really have a better way to check this.
	 * 
	 * @param str
	 * @return true/false if this converted COMP-3 appears to good
	 */
	private boolean isValidNumberTranslation(String str) {

		char c;
		boolean foundPeriod = false;

		// First element must indicate the sign and be a 0, +, or -
		if (!((c = str.charAt(0)) != '0' || c != '+' || c != '-'))
			return false;
		// All other elements must be numeric, except for the first
		// occurance of the period.
		for (int i = 1; i < str.length(); i++) {
			c = str.charAt(i);
			if (!Character.isDigit(c) & !foundPeriod) {
				if (c == '.')
					foundPeriod = true;
				else
					return false;
			} else if (!Character.isDigit(c))
				return false;
		}

		return true;
	}

	private boolean isValidTextTranslation(String str) {
		return true;
	}

	/**
	 * Uses String's toCharArray function to turn this byte[] into
	 * a char[] encoded using the specified encoding.  This is useful
	 * if the flatfile sits on a machine other than this block of code
	 * and is in a different format.
	 * 
	 * @param b byte[]
	 * @param encoding String name of the <code>java.nio.charset.Charset</code> to use
	 * @return chare []
	 * @throws UnsupportedEncodingException this is unrecoverable!
	 */
	private static char[] byteArrayToCharArray(byte[] b, String encoding) throws Sock7Exception {

		try {
			return new String(b, encoding).toCharArray();

		} catch (UnsupportedEncodingException e) {

			throw new Sock7Exception(MainFrameGlobals.CharsetConverter_ByteArrayToCharArray);
		}
	}

	/**
	 * Uses String's toCharArray function to turn this byte[] into
	 * a char[] encoded using the specified encoding.  This is useful
	 * if the flatfile sits on a machine other than this block of code
	 * and is in a different format.
	 * 
	 * @param b byte[]
	 * @return char []
	 * @throws UnsupportedEncodingException this is unrecoverable!
	 */
	private char[] byteArrayToCharArray(byte[] b) throws MainFrameConversionException {

		try {
			return new String(b, getEncoding()).toCharArray();

		} catch (UnsupportedEncodingException e) {

			throw new Sock7Exception(MainFrameGlobals.CharsetConverter_ByteArrayToCharArray);
		}
	}
}
