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
 
package com.indexoutofbounds.mainframe.file;

import java.util.HashMap;
import java.util.List;

import com.indexoutofbounds.util.ReflectionObject;

/**
 * <p>Class which contains the converted mainframe record's value and other important information.</p>
 * 
 * <p>This is not thread-safe since internally we use a HashMap to hold the converted fields.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class Record extends ReflectionObject {

	private int length = 0;
	private int recordNumber = 0;
	private String name = "";
	private HashMap fields;
	private byte[] rawRecord;

	public Record() {
		fields = new HashMap();
	}

	public Record(int length) {
		this.length = length;
		fields = new HashMap();
	}

	public Record(int length, String name) {
		this.length = length;
		this.name = name;
		fields = new HashMap();
	}

	public Field getFieldByName(String name) {
		return (Field) fields.get(name);
	}

	/**
	 * @return gets the full HashMap of fields
	 */
	public HashMap getFields() {
		return fields;
	}

	/**
	 * @return length of this record (byte length)
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return name of this record
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param map
	 */
	public void setFields(HashMap map) {
		fields = map;
	}

	/**
	 * @param i
	 */
	public void setLength(int i) {
		length = i;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return index of this record relative to the file
	 */
	public int getRecordNumber() {
		return recordNumber;
	}

	/**
	 * @param i
	 */
	public void setRecordNumber(int i) {
		recordNumber = i;
	}
	
	public byte[] getRawRecord() {
		return rawRecord;
	}

	public void setRawRecord(byte[] bs) {
		rawRecord = bs;
	}	

//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append("Record Name: " + this.getName() + " ");
//		sb.append("Record Length: " + this.getLength() + " ");
//		sb.append("Number of Fields: " + this.getFields().size() + " ");
//
//		return sb.toString();
//	}
}
