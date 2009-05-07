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

import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;

/**
 * <p>This class is an extension of <code>MainFrameFlatFiletoXMLConverter</code> which
 * allows the assumes that all File I/O is handled elsewhere (This is your warning).
 * This class allows the user to work with a single record of data which can make batch
 * processing more efficient than <code>MainFrameFlatFiletoXMLFileWriter</code> by avoiding
 * the penalty for writing the XML to file.</p>
 * 
 * <p>If you want to read in a row of data from a MainFrame file, convert it, and then
 * do some logic checking to determine how to process that data (process != writing the XML
 * to another file) then this is the class to use.</p>
 * 
 * <p>This class makes some guesses to determine if the file looks like it is valid or not
 * but the general assumption is that this is a valid file.</p>
 * 
 * <p>In a given record there are N number of fields to be translated.  If one of these fields
 * can not be translated (garbage chars) then a detailed MainFrameConversionException is thrown
 * which should describe what happened.  Note that if one of the N fields can't be translated then
 * the whole row is considered bad.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class RowToXMLConverter extends MainFrameFlatFiletoXMLConverter {
	
	/**
	 * VoisConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param _outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public RowToXMLConverter(FlatFileLayout flatfile, String _outfile, ICobolAndJavaConverter converter) {
		super(flatfile, _outfile, converter);
	}

	/**
	 * VoisConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public RowToXMLConverter(FlatFileLayout flatfile, File outfile, ICobolAndJavaConverter converter) {
		super(flatfile, outfile, converter);
	}
	
	/**
	 * RowToXMLConverter converts a FlatFileLayout based on its IConverter.
	 * Default translation is to write the file.  This constructor is here to make life
	 * easier on programmers by setting up everything behind-the-scenes to do an
	 * EBCDIC to ASCII conversion using the DEFAULT_ENCODING.  This 'll work for most USPS
	 * systems... 
	 *  
	 * @param flatfile
	 * @param outfileString
	 * @throws MainFrameConversionException
	 */
	public RowToXMLConverter(FlatFileLayout flatfile, String outfileString) throws MainFrameConversionException {
		super(flatfile, outfileString);
	}	
	
	/**
	 * Generates XML to represent a flat file's record
	 * 
	 * @param record the FileRecord we're converting
	 * @param recordDate the binary data of the record
	 * @param bytesRead the number of btyes read thus far
	 * @throws MainFrameConversionException if something goes wrong
	 */
	public String makeXmlForRecord(FileRecordLayout record, byte[] recordData, int bytesRead)
		throws MainFrameConversionException {
	
		return makeXmlForRecord(record, recordData, bytesRead, this.getConverter());		
	}
	
	/**
	 * Generates XML to represent a flat file's record
	 * 
	 * @param record the FlatFileLayout we're converting
	 * @param recordDate the binary data of the record
	 * @param bytesRead the number of btyes read thus far
	 * @param charsetconverter the converter to use
	 * @throws MainFrameConversionException if something goes wrong
	 */
	public String makeXmlForRecord(FileRecordLayout record, byte[] recordData, int bytesRead, ICobolAndJavaConverter charsetconverter)
		throws MainFrameConversionException {
	
		return super.makeGenericXmlForRecord(record, recordData, bytesRead, charsetconverter);		
	}
	
	/**
	 * Contains specific validity checking for an XMLConversion
	 * Currently just calls the super.isValidFile()
	 * 
	 * @return boolean
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public boolean isValidFile() throws MalformedURLException, IOException {
	
		if(!super.isValidFile())
			return false;
		
		return true;	
	}
}
