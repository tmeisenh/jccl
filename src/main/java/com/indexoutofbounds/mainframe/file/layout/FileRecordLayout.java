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
package com.indexoutofbounds.mainframe.file.layout;

import java.util.ArrayList;
import java.util.List;

import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.util.ReflectionObject;

/**
 * <p>Class which describes a particular record within a file.  In reality, this class
 * describes the structure of the mainframe flat file as it contains the descriptions
 * of the fields within the file itself.</p>
 * 
 * <p>A file can have multiple records.  The only catch is that each different record
 * type must come in a specific order and we must know how many bytes we have of each
 * record.</p>
 * 
 * <p>Accepted types are: Signed Numeric [PIC S9(3)], Unsigned Numeric[PIC 9(4)], Text 
 * (alphanumeric) [PIC X(20)] and COMP-3  fields [PIC S9(3) COMP-3].
 * 
 * <tt>OCCURS</tt> is supported by just repeating the field how many times you need it.
 * Variable occurs is not supported nor is variable occurs with "variable" length fields.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 * @version $Id$
 */
public class FileRecordLayout extends ReflectionObject {

	private int recordSize = 0;
	private String name = "";
	private List fields = null;
	private boolean doTranslation = false;
	private int totalRecordByteSize = -1; /* -1 Indicates "until the EOF" */
	private char[] data;

	/**
	 * Default Constructor which goes to EOF
	 * We will process this record until the total number of bytes read is equal
	 * to the totalRecordByteSize.  If you wish to process a record until the
	 * end of file, then totalRecordByteSize is -1.
	 * 
	 * @param recordSize the amount of bytes for this record
	 * @param name the name of this record type
	 */
	public FileRecordLayout(int recordSize, String name) {
		
		data = new char[recordSize];
		this.recordSize = recordSize;
		this.name = name;
		setFields(new ArrayList());
		this.totalRecordByteSize = -1;
		this.doTranslation = true;
	}

	/**
	 * Constructor which sets up a record to be processed for totalRecordByteSize bytes.
	 * We will process this record until the total number of bytes read is equal
	 * to the totalRecordByteSize.  If you wish to process a record until the
	 * end of file, then totalRecordByteSize is -1.
	 * 
	 * @param recordSize the amount of bytes for this record
	 * @param totalRecordByteSize total amount of bytes for all like-type records
	 * @param name the name of this record type
	 */
	public FileRecordLayout(int recordSize, int totalRecordByteSize, String name) {
		
		data = new char[recordSize];
		this.recordSize = recordSize;
		this.name = name;
		setFields(new ArrayList());
		this.totalRecordByteSize = totalRecordByteSize;
		this.doTranslation = true;
	}

	/**
	 * Constructor which sets up a record to be processed for totalRecordByteSize bytes.
	 * We will process this record until the total number of bytes read is equal
	 * to the totalRecordByteSize.  If you wish to process a record until the
	 * end of file, then totalRecordByteSize is -1.
	 * 
	 * @param recordSize the amount of bytes for this record
	 * @param totalRecordByteSize total amount of bytes for all like-type records
	 * @param name the name of this record type
	 * @param doTranslation if we translate this record or not
	 */
	public FileRecordLayout(int recordSize, int totalRecordByteSize, String name, boolean doTranslation) {
		
		data = new char[recordSize];
		this.recordSize = recordSize;
		this.name = name;
		setFields(new ArrayList());
		this.totalRecordByteSize = totalRecordByteSize;
		this.doTranslation = doTranslation;
	}

	/**
	 * Default Constructor which goes until end of file
	 * @param fields the List of fields for the record
	 * @param recordSize the amount of bytes for this record
	 * @param name the name of this record type
	 */
	public FileRecordLayout(List fields, int recordSize, String name) {
		
		this.fields = fields;
		this.recordSize = recordSize;
		this.totalRecordByteSize = -1;
		this.name = name;
		this.doTranslation = true;
	}

	/**
	 * Constructor 
	 * @param fields the List of fields for the record
	 * @param recordSize the amount of bytes for this record
	 * @param totalRecordByteSize total amount of bytes for all like-type records
	 * @param name the name of this record type
	 */
	public FileRecordLayout(List fields, int recordSize, int totalRecordByteSize, String name) {
		
		this.fields = fields;
		this.recordSize = recordSize;
		this.totalRecordByteSize = totalRecordByteSize;
		this.name = name;
		this.doTranslation = true;
	}

	/**
	 * Constructor which sets up a record to be processed for totalRecordByteSize bytes.
	 * @param fields the List of fields for the record
	 * @param recordSize the amount of bytes for this record
	 * @param totalRecordByteSize total amount of bytes for all like-type records
	 * @param name the name of this record type
	 * @param doTranslation if we translate this record or not
	 */
	public FileRecordLayout(List fields, int recordSize, int totalRecordByteSize, String name, boolean doTranslation) {
		
		this.fields = fields;
		this.recordSize = recordSize;
		this.totalRecordByteSize = totalRecordByteSize;
		this.name = name;
		this.doTranslation = doTranslation;
	}

	/**
	 * Adds a field to the internal list
	 * @param fileField
	 */
	public void addField(FileRecordFieldLayout fileField) {
		fields.add(fileField);
	}

	/**
	 * Removes a field from the internal list.
	 * @param fileField
	 */
	public void removeField(FileRecordFieldLayout fileField) {
		fields.remove(fileField);
	}

	/**
	 * Checks to see if the declared amount of bytes for this record matches the sum of its fields's size.
	 * 
	 * @throws MainFrameConversionException if the sum of the fields's size doesn't match that of the whole record
	 */
	public void closeForEdits() throws MainFrameConversionException {
		
		FileRecordFieldLayout[] arr = this.getFieldsAsArray();
		int workingSize = 0;

		for (int i = 0; i < arr.length; i++)
			workingSize += arr[i].getSize();

		if (this.recordSize != workingSize)
			throw new MainFrameConversionException("Sum of the byte amount in field metadata for all fields does not equal that of the file.");
	}

	/**
	 * Returns an array of this record's fields.
	 * @return FileRecordField[] of all the fields for this record
	 */
	public FileRecordFieldLayout[] getFieldsAsArray() {
		
		Object[] arr = fields.toArray();
		FileRecordFieldLayout[] ff = new FileRecordFieldLayout[arr.length];

		for (int i = 0; i < arr.length; i++)
			ff[i] = (FileRecordFieldLayout) arr[i];

		return ff;
	}

	/**
	 * 
	 * @return record size in bytes
	 */
	public int getRecordSize() {
		return recordSize;
	}

	/**
	 * 
	 * @param i record size in bytes
	 */
	public void setRecordSize(int i) {
		recordSize = i;
	}

	/**
	 * 
	 * @return list of fields
	 */
	public List getFields() {
		return fields;
	}

	/**
	 * 
	 * @param list of fields
	 */
	public void setFields(List list) {
		fields = list;
	}

	/**
	 * 
	 * @return char[] of this record's data
	 */
	public char[] getData() {
		return data;
	}

	/**
	 * 
	 * @param data this record's data
	 */
	public void setData(char[] data) {
		this.data = data;
	}
	/**
	 * @return total bytes that this record encompases in the flat file
	 */
	public int getTotalRecordByteSize() {
		return totalRecordByteSize;
	}

	/**
	 * @param i
	 */
	public void setTotalRecordByteSize(int i) {
		totalRecordByteSize = i;
	}

	/**
	 * @return string
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return boolean if we translate this or not
	 */
	public boolean isDoTranslation() {
		return doTranslation;
	}

	/**
	 * @param b
	 */
	public void setDoTranslation(boolean b) {
		doTranslation = b;
	}

}
