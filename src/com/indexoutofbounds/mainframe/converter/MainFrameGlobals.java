/* $Id$ */
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
 
package com.indexoutofbounds.mainframe.converter;

/**
 * <p>This class holds a few constants that are used between the classes in the
 * mainframe translation library.  You can change these values, but you better
 * know what the heck you are doing.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class MainFrameGlobals {
	
	/**
	 *  It should be easy to find out what version we have.
	 */
	public final static String MFC_VERSION = "1.3.5";
	
	/**
	 * Reasonable list of possible String encoding values.
	 * ISO-8859-1 works on all my systems at home and work.
	 */
	
	public final static String DEFAULT_ENCODING = "ISO-8859-1";
	public final static String US_ASCII_ENCODING = "US-ASCII";
	public final static String UTF_8_ENCODING = "UTF-8";
	public final static String UTF_16_BE = "UTF-16BE";
	public final static String UTF_16_LE = "UTF-16LE";
	public final static String UTF_16 = "UTF-16";

	/**
	 * Any double value that is greater than this is expressed in 
	 * Scientific Notation.  Generally, any double value greater than
	 * this is run through a DecimalFormat.format() operation to strip
	 * out the SI Notation.
	 */
	public final static int DOUBLE_THRESHOLD = 9999999;
	
	/**
	 * Format for displaying double values -- strips out SI Notation
	 */
	public final static String DOUBLE_FORMAT = "0.00";
	
	/**
	 * Format for displaying int values -- strips out SI Notation
	 */
	public final static String INT_FORMAT = "0";
	
	/**
	 * Exception Messages that should be in a resource file...
	 */
	
	public static final String CharsetConverter_CONVERT_BAD_TRANSLATION = "Bad Translation";
	public static final String CharsetConverter_ByteArrayToCharArray = "Unsupported Encoding Selected";
	public static final String COBOLConversionUtils_NumberSizeMessage = "Input value too large";
	public static final String COBOLConversionUtils_InvalidScale = "Input scale is invalid for data";
	public static final String COBOLConversionUtils_ByteLengthTooSmall = "Input byte length is too small for data";
	public static final String COBOLConversionUtils_ByteLengthTooLarge = "Byte contained too large value";
	public static final String COBOLConversionUtils_ByteLengthNotMatch = "Expected byte length did not match actual byte length.";
	public static final String COBOLConversionUtils_NumericUpperNybbleLow = "The upper nybble in this declared NUMERIC field did not contain a full set of bits (1111).";
	public static final String COBOLConversionUtils_COMP3_Value = "The unpacked values of this COMP-3 byte[] contained nybble values greater than 9.";
	public static final String COBOLConversionUtils_SignBit = "The Sign bit did not contain a valid sign indicator. 0xC, 0xD, 0xF";
	public static final String SimpleRecordConverter_BytesReadNotFulLRecord = "Could not read in the defined amount of bytes for the record.  The COBOL file is junk.";

}
