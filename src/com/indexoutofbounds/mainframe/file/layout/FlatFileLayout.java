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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.indexoutofbounds.util.ReflectionObject;

/**
 * <p>A FlatFile contains a bunch of Records, which themselves contain individual
 * fields. A FlatFile can have multiple Records too.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class FlatFileLayout extends ReflectionObject {

	private File file = null;
	private List records = null;

	public FlatFileLayout(String str, FileRecordLayout masterRecord) {
		
		this.file = new File(str);
		this.records = new ArrayList();
		records.add(masterRecord);
	}

	public FlatFileLayout(File file, FileRecordLayout masterRecord) {
		
		this.file = file;
		this.records = new ArrayList();
		records.add(masterRecord);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void addRecord(FileRecordLayout r) {
		this.records.add(r);
	}

	/**
	 * @return list
	 */
	public List getRecords() {
		return records;
	}

	/**
	 * @param list
	 */
	public void setRecords(List list) {
		records = list;
	}

}
