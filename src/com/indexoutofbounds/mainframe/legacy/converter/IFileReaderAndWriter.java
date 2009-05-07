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
 
package com.indexoutofbounds.mainframe.legacy.converter;

import java.io.File;
import java.io.IOException;

import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;

/**
 * <p>This interface defines the abilities of a mainframe file reader/writer.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public interface IFileReaderAndWriter {
	public abstract void convertAndWriteFlatFile() throws MainFrameConversionException, IOException;
	public abstract void convertAndWriteFlatFile(FlatFileLayout file, File out, ICobolAndJavaConverter charset) throws MainFrameConversionException, IOException;
}
