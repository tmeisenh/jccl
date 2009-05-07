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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;
import com.indexoutofbounds.mainframe.legacy.converter.IFileReaderAndWriter;


/**
 * <p>Class which handles the translation of a mainframe flatfile and
 * outputs an XML flatfile.  This class actually does I/O as it is a 
 * <code>IFileReaderAndWriter</code>.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class MainFrameFlatFiletoXMLFileWriter extends MainFrameFlatFiletoXMLConverter implements IFileReaderAndWriter {

	/**
	 * MainFrameFlatFiletoXMLFileWriter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public MainFrameFlatFiletoXMLFileWriter(FlatFileLayout flatfile, File outfile, ICobolAndJavaConverter converter) {
		super(flatfile, outfile, converter);
	}

	/**
	 * MainFrameFlatFiletoXMLFileWriter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file
	 * 
	 * @param flatfile the FlatFile we wish to convert
	 * @param _outfile  the output file we will write
	 * @param converter the IConverter we are using to do the conversion
	 */
	public MainFrameFlatFiletoXMLFileWriter(FlatFileLayout flatfile, String _outfile, ICobolAndJavaConverter converter) {
		super(flatfile, _outfile, converter);
	}

	/**
	 * MainFrameFlatFiletoXMLFileWriter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file.  This constructor is here to make life
	 * easier on programmers by setting up everything behind-the-scenes to do an
	 * EBCDIC to ASCII conversion using the DEFAULT_ENCODING.  This 'll work for most USPS
	 * systems... 
	 *  
	 * @param flatfile
	 * @param outfileString
	 * @throws MainFrameConversionException
	 */
	public MainFrameFlatFiletoXMLFileWriter(FlatFileLayout flatfile, String outfileString) throws MainFrameConversionException {
		super(flatfile, outfileString);
	}

	/**
	  * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	  * Default translation is to write the file, call this method.
	  * TODO : This needs to be reworked
	  * 
	  * @param flatfile the FlatFile we wish to convert
	  * @param _outfile  the output file we will write
	  * @param converter the IConverter we are using to do the conversion
	 */
	public void convertAndWriteFlatFile(FlatFileLayout in, File out, ICobolAndJavaConverter charsetconverter) throws MainFrameConversionException, IOException {

		writeConvertedRecordToOutputFile(super.makeXmlFileHeader(in.getFile().getAbsolutePath(), out.getAbsolutePath()));

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(in.getFile()));

		int bytesRead = 0;
		int totalBytesRead = 0;
		for (Iterator it = in.getRecords().iterator(); it.hasNext();) {
			FileRecordLayout record = (FileRecordLayout) it.next();
			byte[] recordData = new byte[record.getRecordSize()];
			while (bytesRead != -1 & totalBytesRead != record.getTotalRecordByteSize()) {
				if (record.isDoTranslation()) {
					bytesRead = bis.read(recordData, 0, record.getRecordSize());

					if (!isReadRecordFullLength(bytesRead, record.getRecordSize()))
						throw new MainFrameConversionException(
							"Warning: The byte amount most recently read did not match the amount "
								+ "specified by the record and was not the END OF FILE. Check for a variable length file, that we did BINARY"
								+ " AFTP, and make sure file field/record layouts are correct.  Failed at byte read: "
								+ bytesRead	+ "trying to read: " + record.getRecordSize() + " bytes.");

					writeConvertedRecordToOutputFile(makeXmlForRecord(record, recordData, totalBytesRead, charsetconverter));
				} else {
					bis.skip(record.getRecordSize());
				}
				totalBytesRead += bytesRead;
			}
		}
		bis.close();
	}

	/**
	 * 
	 * MainFrameFlatFileConverter converts a FlatFile based on its IConverter.
	 * Default translation is to write the file, call this method.
	 *
	 */
	public void convertAndWriteFlatFile() throws MainFrameConversionException, IOException {

		convertAndWriteFlatFile(this.getFlatfile(), this.getOutfile(), this.getConverter());
	}

	public String makeXmlForRecord(FileRecordLayout record, byte[] recordData, int bytesRead, ICobolAndJavaConverter charsetconverter) throws MainFrameConversionException {

		return super.makeGenericXmlForRecord(record, recordData, bytesRead, charsetconverter);
	}

	// -------------- Private Methods	

	/**
	 * Writes the converted text into the output file
	 * All implementations of this class use this method
	 * 
	 * @param convertedRecord text String of the record
	 * @throws MainFrameConversionException if something went wrong
	 * @throws IOException if something went wrong
	 */
	private void writeConvertedRecordToOutputFile(String convertedRecord) throws IOException {

		writeConvertedRecordToOutputFile(convertedRecord, new FileOutputStream(this.getOutfile().getAbsolutePath(), true));
	}

	/**
	 * Writes the converted text into the given output stream
	 * 
	 * @param convertedRecord text String of the record
	 * @throws MainFrameConversionException if something went wrong
	 * @throws IOException if something went wrong
	 */
	private void writeConvertedRecordToOutputFile(String convertedRecord, OutputStream out) throws IOException {

		ByteArrayInputStream bais = new ByteArrayInputStream(convertedRecord.getBytes());

		byte[] buffer = new byte[4096];
		int byteRead = 0;

		while ((byteRead = bais.read(buffer)) != -1) {
			out.write(buffer, 0, byteRead);
		}

		bais.close();
		out.flush();
		out.close();
	}
}
