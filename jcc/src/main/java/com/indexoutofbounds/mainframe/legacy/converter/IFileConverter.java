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

package com.indexoutofbounds.mainframe.legacy.converter;

import java.io.IOException;
import java.net.MalformedURLException;

import com.indexoutofbounds.mainframe.converter.ICobolAndJavaConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;

/**
 * <p>This interface defines the abilities of a mainframe file converter.</p>
 * 
 * <p>Author's note: It is entirely possible and probable that any given mainframe
 * flat file will contain "junk" data in it as filler (not everyone uses whitespaces).
 * This "junk" data could be similiar to what you would get if you did a 
 * <code>cat /dev/random >> some_file</code> on a unix box in that the junk data is
 * just random garbage.  This gets us to the point of this comment in that because the
 * data is random garbage it can occur that one could end up reading the EOF char/value
 * before we hit the actual end-of-file.  Therefore we must take special care to make sure
 * that we've hit the real EOF and not the EOF value stuck somewhere in the file at random.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public interface IFileConverter {
	
	public abstract String makeXmlForRecord(FileRecordLayout record, byte[] recordData, int bytesRead, ICobolAndJavaConverter charsetconverter) throws MainFrameConversionException;
	public abstract boolean isValidFile() throws MalformedURLException, IOException;
}
