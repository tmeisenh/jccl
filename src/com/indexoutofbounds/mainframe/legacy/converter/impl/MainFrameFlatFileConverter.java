/* $Id$ */
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

package com.indexoutofbounds.mainframe.legacy.converter.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.indexoutofbounds.mainframe.charsets.Charset;
import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.converter.MainFrameGlobals;
import com.indexoutofbounds.mainframe.converter.impl.CobolAndAsciiConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;
import com.indexoutofbounds.mainframe.file.layout.MetaDataType;
import com.indexoutofbounds.mainframe.legacy.converter.IFileConverter;

/**
 * <p>Abstract Class which handles the translation of a mainframe flatfile record 
 * that is defined as a FlatFile with one or more FileRecords defined each to 
 * hold one or more FileRecordFields into another flat-file. I/O is not done in this
 * class and as such none of its exposed methods deal with I/O.</p>
 * 
 * <p>NUMERIC, COMP-3, and Alhpanumeric (String) data are the only supported types.</p>
 * 
 * <p>COMP-3 forces us to read in and deal with data using a byte[] and not a char[]
 * because of the fact that the conversion requires some bit-shifting and masking
 * and the char value will mess up the bit pattern. (I don't know what or why but
 * the numbers get screwed up if you use a char and cast down to byte).  For the
 * other translations we pass the byte[] into a char[] and then process it because
 * it is easier for non-COMP-3 to use a char[] (we're not worried about the bits
 * here.).</p>
 * 
 * <p>Note that when we pull the flatfile using AFTP Windows will "molest" the binary file
 * and add ASCII padding.  Also Java's <code>char</code> is  two bytes in length which is
 * too large for our use.  This is why we have to deal with byte and byte[].</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public abstract class MainFrameFlatFileConverter implements IFileConverter {

	public char TAB = '\t';
	private FlatFileLayout flatfile = null;
	private File outfile = null;

	private ICobolAndJavaConverter converter = null;

	/**
	 * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public MainFrameFlatFileConverter(FlatFileLayout flatfile, File outfile, ICobolAndJavaConverter converter) {

		this.flatfile = flatfile;
		this.converter = converter;
		this.outfile = outfile;
	}

	/**
	 * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param _outfile the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public MainFrameFlatFileConverter(FlatFileLayout flatfile, String _outfile, ICobolAndJavaConverter converter) {

		this.flatfile = flatfile;
		this.converter = converter;
		outfile = new File(_outfile);
	}

	/**
	 * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file.  This constructor is here to make life
	 * easier on programmers by setting up everything behind-the-scenes to do an
	 * EBCDIC to ASCII conversion using the DEFAULT_ENCODING.  This 'll work for most USPS
	 * systems... 
	 *  
	 * @param flatfile
	 * @param outfileString
	 * @throws MainFrameConversionException
	 */
	public MainFrameFlatFileConverter(FlatFileLayout flatfile, String outfileString) throws MainFrameConversionException {

		this.flatfile = flatfile;
		this.outfile = new File(outfileString);
		this.converter = new CobolAndAsciiConverter(new Charset(), MainFrameGlobals.DEFAULT_ENCODING);
	}

	/**
	 * Gets the FlatFile
	 * 
	 * @return FlatFile
	 */
	public FlatFileLayout getFlatfile() {
		return flatfile;
	}

	/**
	 * Sets the FlatFile to be translated/converted
	 * 
	 * @param flatfile
	 */
	public void setFlatfile(FlatFileLayout flatfile) {
		this.flatfile = flatfile;
	}

	/**
	 * Returns the IConverter used to translated/convert this file
	 * 
	 * @return an IConverter
	 */
	public ICobolAndJavaConverter getConverter() {
		return this.converter;
	}

	/**
	 * Sets the converter to convert this FlatFile
	 * 
	 * @param c
	 */
	public void setConverter(ICobolAndJavaConverter c) {
		this.converter = c;
	}

	/**
	 * Converts a given FileRecord into a String, even unpacking any COMP-3
	 * decimals. Any given FlatFile should only have ONE FileRecord which
	 * describes the physical file. The FileRecord should have one or more
	 * fields which need translated.
	 * 
	 * This left abstract in order to allow the implementor to define how
	 * they want things converted (XML, csv, and so on).
	 * 
	 * 
	 * @param record file record to translate
	 * @param data the byte[] which contains the whole record
	 * @param converter the converter to use
	 * @return String of converted text for the whole record
	 * @throws MainFrameConversionException if something went wrong
	 */
	public abstract String convertFileRecord(FileRecordLayout record, byte[] data, ICobolAndJavaConverter converter) throws MainFrameConversionException;

	/**
	 * Gets the output file
	 * 
	 * @return the output file
	 */
	public File getOutfile() {
		return outfile;
	}

	/**
	 * Sets the output file
	 * 
	 * @param file
	 */
	public void setOutfile(File file) {
		outfile = file;
	}

	/**
	 * Determines if the byte amount just read matches that of the declared record size.
	 * Ignores an EOF (-1).  If this ever returns true then we've got a problem with either
	 * the file having a variable length record or we screwed up the file layout.
	 * 
	 * @param bytesRead
	 * @param recordSize
	 * @return boolean
	 */
	public boolean isReadRecordFullLength(int bytesRead, int recordSize) {

		if (bytesRead == -1)
			return true;

		return bytesRead == recordSize;
	}

	/**
	 * Tries to reasonably guess if this file is in a valid format or not.
	 * This just looks to see if the flatfile exists or not.
	 * 
	 * @throws MalformedURLException - does not throw
	 * @throws IOException - does not throw
	 */
	public boolean isValidFile() throws MalformedURLException, IOException {

		// The mainframe file should exist!
		return this.getFlatfile().getFile().exists();
	}

	//------------------------------ Protected 

	/**
	 * Converts a given field into a String, even unpacking any COMP-3 decimals.
	 * This method just delegates the actual "work" to functions that work on
	 * the particular MetaDataType of the field in question.
	 * All implementations of this class use this method
	 * 
	 * @param field the FileRecordField which describes this particular field
	 * @param data char [] containing this parcitular field
	 * @param converter the converter to use
	 * @return String of converted text for this field
	 * @throws MainFrameConversionException
	 */
	protected String convertFileRecordField(FileRecordFieldLayout field, byte[] data, ICobolAndJavaConverter converter) throws MainFrameConversionException {

		try {
			switch (field.getType()) {
				case MetaDataType.PACKED_DECIMAL :
					return translate_packed(data, field.getScale(), converter);

				case MetaDataType.ALLNUM :
					return translate_allnum(data, converter);

				case MetaDataType.DISPLAY :
					return translate_display(data, converter);

				case MetaDataType.NUMBER :
					return translate_number(data, field.getScale(), converter);

				default :
					System.err.println("Unknown field type...SHOULD NOT HAPPEN");
					return translate_display(data, converter);
			}
		} catch (MainFrameConversionException m) {
			System.out.println("Data length: " + data.length);
			System.out.println("Datea byte pattern: " + displayInBytePattern(data));
			System.out.println("Raw: " + displayRawBytes(data));
			throw new MainFrameConversionException("Error Converting: " + field.getName() + " \n"+ m.getLocalizedMessage(), m);
		}
		
	}

	/**
	 * Copies from a larger in[] into a smaller out[] all the elements between
	 * the start and the end of the array.
	 * 
	 * @param in char[]
	 * @param out char[]
	 * @param start position at which to start copying
	 * @return char[]
	 */
	protected static byte[] copyArray(byte[] in, byte[] out, int start) {

		for (int i = 0; i < out.length; i++)
			out[i] = in[i + start];

		return out;
	}

	//------------------------------ Private	

	/**
	 * Converts a byte[] containing the binary representation of a COMP-3 packed
	 * decimal into a String. Only a negative signed number is given a sign
	 * indicator.
	 * 
	 * @param data byte[] containing this field
	 * @param decimalPos digits to the right of the decimal
	 * @param precision number of digits in this number
	 * @param converter the converter to use
	 * @return String of the translated field
	 * @throws MainFrameConversionException something blew up
	 */
	private String translate_packed(byte[] data, int decimalPos, ICobolAndJavaConverter converter) throws MainFrameConversionException {

		return converter.convertCOMP3(data, decimalPos);
	}

	/**
	 * Converts a signed number represented by the byte[] into a String. This doesn't
	 * really do anything yet.  This works for NUMERIC types of both signed and unsigned.
	 * 
	 * @param data byte[] containing this field
	 * @param scale - the scale
	 * @param converter the converter to use
	 * @return String of the translated field
	 * @throws MainFrameConversionException something blew up
	 */
	private String translate_number(byte[] ddata, int scale, ICobolAndJavaConverter converter) throws MainFrameConversionException {

		return converter.convertNumericValue(ddata, scale);
	}

	/**
	 * Converts a text represented by the byte[] into a String. The IConverter
	 * is responsible for the in and out formats.
	 * 
	 * Typically, DISPLAY format is typically a numberic that isn't signed or
	 * an alphanumeric string.
	 * 
	 * @param data byte[] containing this field
	 * @param converter the converter to use
	 * @return String of the translated field
	 * @throws MainFrameConversionException something blew up
	 */

	private String translate_display(byte[] ddata, ICobolAndJavaConverter converter) throws MainFrameConversionException {

		return converter.convertAlphaNumericDisplay(ddata);
	}

	/**
	 * Converts a number represented by the byte[] into a String. This doesn't
	 * really do anything yet.
	 * 
	 * @param data byte[] containing this field
	 * @param converter the converter to use
	 * @return String of the translated field
	 * @throws MainFrameConversionException something blew up
	 */
	private String translate_allnum(byte[] ddata, ICobolAndJavaConverter converter) throws MainFrameConversionException {

		return converter.convertAlphaNumericDisplay(ddata);
	}

	/**
	 * Escapes any quote or tick marks that may appear in the data.
	 * 
	 * @param str input
	 * @return String
	 */
	protected String escapeQuote(String str) {
		StringBuffer sb = new StringBuffer();
		char[] escape = { '\'', '\"', ' ', '\t' };
		for (int i = 0; i < str.length(); i++) {
			char c;
			if ((c = str.charAt(i)) == escape[0] || c == escape[1]) {
				sb.append("\\");
				sb.append(c);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Debugging function which breaks up a normalized bit-pattern 
	 * string into byte increments by adding a space every two chars.
	 * 
	 * @param val
	 * @return String
	 */
	protected static String displayInBytePattern(byte[] val) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < val.length - 1; i += 2) {
			sb.append(val[i]);
			sb.append(val[i + 1]);
			sb.append(" ");
		}

		return sb.toString();

	}
	
	protected static String displayRawBytes(byte[] val) {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < val.length; i++) {
			sb.append(val[i] + ",");
		}

		return sb.toString();	
	}
}
