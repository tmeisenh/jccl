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
 
package com.indexoutofbounds.mainframe.file;

import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.util.ReflectionObject;


/**
 * <p>Class which contains the converted mainframe field's value and other important information.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class Field extends ReflectionObject {
	/** 
	 * Field name 
	 */
	private String name = "";
	
	/**
	 * Converted Value
	 */ 
	private String value = "";
	
	/**
	 * Raw data
	 */
	private byte[] data;
	
	/**
	 * Layout, used for translation
	 */
	private FileRecordFieldLayout layout;
	
	public Field() {
		
	}
	
	/**
	 * @return byte[]
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @return the layout
	 */
	public FileRecordFieldLayout getLayout() {
		return layout;
	}

	/**
	 * @return field name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return field value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param bs
	 */
	public void setData(byte[] bs) {
		data = bs;
	}

	/**
	 * @param layout
	 */
	public void setLayout(FileRecordFieldLayout layout) {
		this.layout = layout;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}
	
//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append("Field Name: " + this.getName() + " ");
//		sb.append("Field byte length: " + this.getLayout().getSize() + " ");
//		sb.append("Field Value: " + this.getValue() + " ");
//		sb.append("Raw data (not encoded): " + new String(this.getData()) + " ");
//		
//		return sb.toString();
//	}

}
