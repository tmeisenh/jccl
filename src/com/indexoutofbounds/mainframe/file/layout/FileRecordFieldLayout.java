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

package com.indexoutofbounds.mainframe.file.layout;

import com.indexoutofbounds.util.ReflectionObject;

/**
 * <p>Class which represents a parcitular type of field within a given file.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class FileRecordFieldLayout extends ReflectionObject {

	/**
	 * Field name 
	 */
	private String name = "";
	 
	/**
	 * Do we want to process this field, or is it just zero-filled...
	 */
	private boolean processable = true; 

	 /**
	  * One of MetaDataType's 'types
	  */
	int type = MetaDataType.ALLNUM;
	
	/**
	 * Bytes from beginning of record
	 */
	int offset = 0;
	 
	/**
	 * Size in bytes of this field
	 */
	int size = 0;
	 
	/**
	 * Digits right of implicit decimal point 
	 */
	int scale = 0; 


	/**
	 * Constructor
	 * @param size size in bytes of this field
	 * @param type the mainframe type of this field
	 * @param name the name of this field
	 */

	public FileRecordFieldLayout(int size, int type, String name) {
		
		this.size = size;
		this.type = type;
		this.name = name;
		this.processable = true;
	}

	/**
	 * Constructor
	 * @param size size in bytes of this field
	 * @param type the mainframe type of this field
	 * @param name the name of this field
	 * @param processable if we want to process this field
	 */

	public FileRecordFieldLayout(int size, int type, String name, boolean processable) {
		
		this.size = size;
		this.type = type;
		this.name = name;
		this.processable = processable;
	}

	/**
	 * Packed Decimal (COMP-3) Constructor
	 * @param size size in bytes of this field
	 * @param type the mainframe type of this field
	 * @param scale Digits right of the implicit decimal point
	 * @param name the name of this field
	 */
	public FileRecordFieldLayout(int size, int type, int scale, String name) {
		
		this.size = size;
		this.type = type;
		this.scale = scale;
		this.name = name;
		this.processable = true;
	}

	/**
	 * Packed Decimal (COMP-3) Constructor
	 * @param size size in bytes of this field
	 * @param type the mainframe type of this field
	 * @param scale Digits right of the implicit decimal point
	 * @param name the name of this field
	 * @param processable if we want to process this field
	 */
	public FileRecordFieldLayout(int size, int type, int scale, String name, boolean processable) {
		
		this.size = size;
		this.type = type;
		this.scale = scale;
		this.name = name;
		this.processable = processable;
	}

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return Digits right of the implicit decimal point
	 */
	public int getScale() {
		return this.scale;
	}

	/**
	 * 
	 * @param scale Digits right of the implicit decimal point
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * 
	 * @return size in bytes of this field
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * 
	 * @param size size in bytes of this field
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 
	 * @return the type of this field
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * 
	 * @param type the type of this field
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @return boolean if we process this field or not
	 */
	public boolean isProcessable() {
		return processable;
	}

	/**
	 * @param i
	 */
	public void setOffset(int i) {
		offset = i;
	}

	/**
	 * @param b
	 */
	public void setProcessable(boolean b) {
		processable = b;
	}

}
