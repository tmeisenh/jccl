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
import java.util.Iterator;

import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;

/**
 * <p>Abstract Class which handles the translation of a mainframe flatfile that is defined
 * as a FlatFile with one or more FileRecords defined each to hold one or more
 * FileRecordFields into an XML file.</p>
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

public abstract class MainFrameFlatFiletoXMLConverter extends MainFrameFlatFileConverter {

	/**
	 * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */

	public MainFrameFlatFiletoXMLConverter(FlatFileLayout flatfile, File outfile, ICobolAndJavaConverter converter) {

		super(flatfile, outfile, converter);

	}

	/**
	 * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param _outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public MainFrameFlatFiletoXMLConverter(FlatFileLayout flatfile, String _outfile, ICobolAndJavaConverter converter) {

		super(flatfile, _outfile, converter);

	}
	
	/**
	 * MainFrameFlatFiletoXMLConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file.  This constructor is here to make life
	 * easier on programmers by setting up everything behind-the-scenes to do an
	 * EBCDIC to ASCII conversion using the DEFAULT_ENCODING.  This 'll work for most USPS
	 * systems... 
	 *  
	 * @param flatfile
	 * @param outfileString
	 * @throws MainFrameConversionException
	 */
	public MainFrameFlatFiletoXMLConverter(FlatFileLayout flatfile, String outfileString) throws MainFrameConversionException {
		super(flatfile, outfileString);
	}
	
	/**
	 * Contains specific validity checking for an XMLConversion
	 * 
	 * Currently just calls the super.isValidFile()
	 * 
	 * @return boolean
	 */
	public boolean isValidFile() throws MalformedURLException, IOException {
		
		return super.isValidFile();
		
	}

	/**
	 * Converts a given FileRecord into a String, even unpacking any COMP-3
	 * decimals. Any given FlatFile should only have ONE FileRecord which
	 * describes the physical file. The FileRecord should have one or more
	 * fields which need translated.
	 * 
	 * @param record file record to translate
	 * @param data the byte[] which contains the whole record
	 * @param converter the converter to use
	 * @return String of converted text for the whole record
	 * @throws MainFrameConversionException if something went wrong
	 */
	public String convertFileRecord(FileRecordLayout record, byte[] data, ICobolAndJavaConverter converter)
		throws MainFrameConversionException {
			
		StringBuffer text = new StringBuffer();
		int byteRead = 0;

		for (Iterator it = record.getFields().iterator(); it.hasNext();) {
			FileRecordFieldLayout field = (FileRecordFieldLayout) it.next();
			if (field.isProcessable()) {
				byte[] fieldData = new byte[field.getSize()];
				text.append(
					dataRecordToXML(
						field.getName(),
						convertFileRecordField(field, copyArray(data, fieldData, byteRead), converter)));
			}
			byteRead += field.getSize();
		}

		return text.toString();
	}
	
	/**
	 * Generates XML to represent a flat file's record
	 * 
	 * @param record the FileRecord we're converting
	 * @param recordDate the binary data of the record
	 * @param bytesRead the number of btyes read thus far
	 * @param charsetconverter the converter to use
	 * @throws MainFrameConversionException if something goes wrong
	 */
	public String makeGenericXmlForRecord(FileRecordLayout record, byte[] recordData, int bytesRead, ICobolAndJavaConverter charsetconverter)
		throws MainFrameConversionException {
		
		StringBuffer sb = new StringBuffer();

		sb.append(recordHeader(record.getName(), record.getRecordSize(), bytesRead));
		sb.append(convertFileRecord(record, recordData, charsetconverter));
		sb.append(recordFooter());

		return sb.toString();
	}
	
	//------------------------------ Private

	/**
	 * Creates the XML for a file record
	 * 
	 * @param name (of the record)
	 * @param len length of the record in bytes
	 * @param spos the start (byte) position where this record is located
	 * @return String
	 */
	private String recordHeader(String name, int len, int spos) {
		
		return "<record>\n" +
			   "\t<record-type>" + escapeQuote(name) + "</record-type>\n" +
			   "\t<record-start-byte-pos>" + spos + "</record-start-byte-pos>\n" +
			   "\t<record-length>" + len + "</record-length>\n";
	}

	/**
	 * Returns the end tag for a record
	 * 
	 * @return String
	 */
	private String recordFooter() {
		return "\n</record>\n\n";
	}

	/**
	 * Wraps a file field in XML which describes the data
	 * 
	 * @param name the field name
	 * @param data the field data
	 * @return String
	 */
	private String dataRecordToXML(String name, String data) {
		
		return "\n\t<record-field>\n"
			+ "\t\t<field-name>"
			+ escapeQuote(name)
			+ "</field-name>\n"
			+ "\t\t<field-data>"
			+ escapeQuote(data)
			+ "</field-data>\n"
			+ "\t</record-field>";
	}

	/**
	 * Makes a basic xml header for the output file
	 * 
	 * @param in the file name of the input file
	 * @param out the file name of the output file
	 * @return String
	 */
	protected String makeXmlFileHeader(String in, String out) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("\n" + TAB + "<!--");
		sb.append("\n" + TAB + "   - " + "Input file: " + in);
		sb.append("\n" + TAB + "   - " + "Output file: " + out);
		sb.append(
			"\n"
				+ TAB
				+ "   - "
				+ "Automagically generated by the MainFrameFlatFile Library at "
				+ (new java.util.Date()));
		sb.append("\n" + TAB + "-->\n\n");

		return sb.toString();
	}
}
