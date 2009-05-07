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

package com.indexoutofbounds.mainframe.converter.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.indexoutofbounds.mainframe.charsets.Charset;
import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.converter.IRecordConverter;
import com.indexoutofbounds.mainframe.converter.MainFrameGlobals;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.Field;
import com.indexoutofbounds.mainframe.file.Record;
import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;
import com.indexoutofbounds.mainframe.file.layout.MetaDataType;

/**
 * <p> Class which implements the <code>IRecordConverter</code> interface and
 * allows one to read in a mainframe flat-file and convert it into useful data
 * in a very simple manner.</p>
 * 
 * <p>Defining simple: This class converts a COBOL flat-file to an ASCII record
 * on a row-by-row basis.  The COBOL file can contain only one type of record however.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 */

public class SimpleCOBOLToAsciiRecordConverter implements IRecordConverter {

	private static Logger log = Logger.getLogger(SimpleCOBOLToAsciiRecordConverter.class);

	private FileRecordLayout recordLayout;

	private FileInputStream fileInputStream;

	private BufferedInputStream bufferedInputStream;

	private Charset charset;

	private ICobolAndJavaConverter charconv;

	private int rowCount = 0;

	/**
	 * Constructor
	 * 
	 * @param ffl the FlatFile object
	 * @throws MainFrameConversionException
	 */
	public SimpleCOBOLToAsciiRecordConverter(FlatFileLayout ffl) throws MainFrameConversionException {

		try {
			log.debug("SimpleCOBOLToAsciiRecordConverter constructor");

			if (ffl.getRecords().size() != 1) {
				throw new MainFrameConversionException(
						"SimpleCOBOLToAsciiRecordConverter only supports conversion of files with one type of record, you spcified: "
								+ ffl.getRecords().size());
			}
			this.recordLayout = (FileRecordLayout) ffl.getRecords().get(0);
			fileInputStream = new FileInputStream(ffl.getFile());
			bufferedInputStream = new BufferedInputStream(fileInputStream);

			// set character set for EBCDIC to ASCII
			charset = new Charset();
			charset.configureEBCDICToAscii();
			charconv = new CobolAndAsciiConverter(charset);

		} catch (FileNotFoundException e) {
			throw new MainFrameConversionException(e.getMessage());
		}
	}

	/**
	 * Constructor
	 * 
	 * @param filename the filename
	 * @param recordLayout what the record looks like
	 * @throws MainFrameConversionException
	 */
	public SimpleCOBOLToAsciiRecordConverter(String filename, FileRecordLayout recordLayout)
			throws MainFrameConversionException {

		try {

			log.debug("SimpleCOBOLToAsciiRecordConverter constructor");

			this.recordLayout = recordLayout;
			fileInputStream = new FileInputStream(filename);

			bufferedInputStream = new BufferedInputStream(fileInputStream);

			// set character set for EBCDIC to ASCII
			charset = new Charset();
			charset.configureEBCDICToAscii();
			charconv = new CobolAndAsciiConverter(charset);

		} catch (FileNotFoundException e) {
			throw new MainFrameConversionException(e.getMessage());
		}

	}

	/**
	 * Gets the next record from the file. Returns the converted Record or null
	 * if we've hit the EOF. Unlike <code>getNextConvertableRecord()</code>,
	 * this method does not catch any exceptions; it is up to the caller to
	 * handle the mis-translation cases.
	 * 
	 * @return the newly converted Record or NULL if we hit EOF
	 * @throws MainFrameConversionException
	 */
	public Record getNextRecord() throws MainFrameConversionException {

		log.debug("getNextRecord()");

		byte[] bytes;

		if ((bytes = getNextRecordsBytes()) != null) {

			Record record = new Record();
			record.setLength(bytes.length);
			record.setRecordNumber(++rowCount);
			record.setFields(breakApartRow(bytes));

			return record;
		}

		verifyFileIsClosed();

		return null;
	}

	/**
	 * Opens the file for i/o.
	 * 
	 * @throws MainFrameConversionException
	 */
	public void verifyFileIsOpen() throws MainFrameConversionException {

		if (fileInputStream == null || bufferedInputStream == null) {
			throw new MainFrameConversionException("File is not open.");
		}
	}

	/**
	 * Closes the file
	 * 
	 * @throws MainFrameConversionException
	 */
	public void verifyFileIsClosed() throws MainFrameConversionException {

		log.debug("verifyFileIsClosed()");

		try {

			if (fileInputStream != null)
				fileInputStream.close();

			if (bufferedInputStream != null)
				bufferedInputStream.close();

		} catch (IOException e) {

			/*
			 * Ignore because we should be expecting this exception. Trying to
			 * close something that's already closed would throw an exception
			 * This isn't the best way to do this I'd imagine...
			 */

			throw new MainFrameConversionException("File can't be closed?", e);
		}
	}

	/**
	 * @return the file record's layout
	 */
	public FileRecordLayout getFileRecordLayout() {
		return this.recordLayout;
	}

	/**
	 * @return row count
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Does the I/O required to get a byte[] of data for the MainFrame record.
	 * Returns the byte[] of data or NULL if the EOF was hit. If during the
	 * reading in of data we encounter a byte[] of data read in that was not
	 * equal to the record's size or -1 (indicating EOF) then Something Bad
	 * happened and we throw up.
	 * 
	 * @return byte[]
	 * @throws MainFrameConversionException
	 */

	private byte[] getNextRecordsBytes() throws MainFrameConversionException {

		log.debug("getNextRecordsBytes()");

		int bytesToRead = recordLayout.getRecordSize();
		byte[] bytes = new byte[bytesToRead];

		try {
			if (bufferedInputStream.read(bytes, 0, bytesToRead) > 0) {

				/*
				 * TODO What happens if we can't read in the full COBOL record
				 * amount? Valid 'amounts' are -1 (hit EOF) or the full
				 * amount...
				 */

				if (bytes.length != bytesToRead) {
					throw new MainFrameConversionException(
							MainFrameGlobals.SimpleRecordConverter_BytesReadNotFulLRecord);
				}

				return bytes;
			}

		} catch (IOException e) {
			throw new MainFrameConversionException(e);

		} catch (ArrayIndexOutOfBoundsException a) {
			throw new MainFrameConversionException(a);
		}

		return null;
	}

	/**
	 * Returns a HashMap of converted fields from a MainFrame record. We only
	 * convert those Fields who are marked with a isProcess of true.
	 * 
	 * @param rowbytes
	 * @return HashMap
	 * @throws MainFrameConversionException
	 */
	private HashMap breakApartRow(byte[] rowbytes) throws MainFrameConversionException {

		log.debug("breakApartRow");

		HashMap fields = new HashMap();
		byte[] fieldBytes;

		Iterator it = recordLayout.getFields().iterator();
		int bytesRead = 0;

		while (it.hasNext()) {

			FileRecordFieldLayout frfl = (FileRecordFieldLayout) it.next();

			if (frfl.isProcessable()) {

				fieldBytes = byteArrayCopy(rowbytes, bytesRead, frfl.getSize());

				if (log.isEnabledFor(Level.DEBUG)) {
					log.debug("Converting: " + frfl.getName());
					log.debug("Raw bytes: " + displayInBytePattern(fieldBytes));
				}

				Field field = null;
				try {
					field = translateField(frfl, fieldBytes);
				} catch (MainFrameConversionException e) {
					StringBuffer sb = new StringBuffer();

					sb.append("Original Error: " + e.getLocalizedMessage());
					sb.append("\nField: " + frfl.getName() + " could not be converted. ");
					sb.append("Row count: " + rowCount);
					sb.append(" Byte Offset: " + bytesRead);
					sb.append("\n" + frfl.toString() + "\n");

					if (log.isEnabledFor(Level.ERROR)) {
						sb.append("Raw Bytes: " + displayBytes(fieldBytes));
					}
					throw new MainFrameConversionException(sb.toString(), e);
				}

				fields.put(frfl.getName(), field);

			} else {
				if (log.isEnabledFor(Level.DEBUG)) {
					log.debug("Skipping: " + frfl.getName());
				}
				// Do nothing?
			}

			bytesRead += frfl.getSize();
			fieldBytes = null;
		}

		return fields;
	}

	/**
	 * Converts a field based on the input bytes and FileRecordFieldLayout
	 * 
	 * @param frfl the FileRecordFieldLayout
	 * @param fieldBytes the bytes of the field
	 * @return the converted Field
	 * @throws MainFrameConversionException
	 */
	private Field translateField(FileRecordFieldLayout frfl, byte[] fieldBytes) throws MainFrameConversionException {

		log.debug("translateField() " + frfl.getName());

		Field field = new Field();
		String value = "";

		// We have to wrap in a try/catch in order to stick the field name in
		// the Exception message

		switch (frfl.getType()) {
		case MetaDataType.PACKED_DECIMAL:
			value = charconv.convertCOMP3(fieldBytes, frfl.getScale());
			break;
		case MetaDataType.ALLNUM:
			value = charconv.convertNumericValue(fieldBytes, frfl.getScale());
			break;
		case MetaDataType.DISPLAY:
			value = charconv.convertAlphaNumericDisplay(fieldBytes);
			break;
		case MetaDataType.NUMBER:
			value = charconv.convertNumericValue(fieldBytes, frfl.getScale());
			break;
		default:
			throw new MainFrameConversionException("Unknown field type...SHOULD NOT HAPPEN");
		}

		field.setData(fieldBytes);
		field.setLayout(frfl);
		field.setName(frfl.getName());
		field.setValue(value);

		return field;
	}

	/**
	 * This method takes an array of bytes and returns a subset array of bytes
	 * based on the parameters passed in.
	 * 
	 * @param src the bytes to copy from
	 * @param src_position the position in the bytes to start reading from
	 * @param length the amount of bytes to read
	 * @return the bytes from the original array of bytes
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private static byte[] byteArrayCopy(byte[] src, int src_position, int length) throws ArrayIndexOutOfBoundsException {

		byte[] dst = new byte[length];

		if ((src_position < 0) || (length < 0) || (src_position + length > src.length)) {
			throw new ArrayIndexOutOfBoundsException("Can not copy byte array properly");
		}
		if (src == dst) {
			// overlapping case
			for (int i = length - 1; i >= 0; --i) {
				dst[i] = src[src_position + i];
			}
			return dst;
		}
		for (int i = 0; i < length; ++i) {
			dst[i] = src[src_position + i];
		}
		return dst;
	}

	private static String displayInBytePattern(byte[] val) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < val.length - 1; i += 2) {
			sb.append(val[i]);
			sb.append(val[i + 1]);
			sb.append(" ");
		}

		return sb.toString();
	}

	private static String displayBytes(byte[] val) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < val.length; i++) {
			sb.append("{");
			sb.append(val[i]);
			sb.append("}");
			sb.append(" ");
		}

		return sb.toString();
	}
}
