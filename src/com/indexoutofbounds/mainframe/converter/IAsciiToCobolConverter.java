/*
 * Copyright (c) 2005 - 2006, Travis B. Meisenheimer
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

package com.indexoutofbounds.mainframe.converter;

import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;

/**
 * <p>This interface defines methods for converting ASCII data to COBOL format.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */
public interface IAsciiToCobolConverter {
	
	public abstract byte [] convertString(String value, int byteLength) throws MainFrameConversionException;
	public abstract byte [] packIntIntoCOMP3(int value, int byteLength, int scale, boolean isSigned)	throws MainFrameConversionException;
	public abstract byte [] packDoubleIntoCOMP3(double value, int byteLength, int scale, boolean isSigned)	throws MainFrameConversionException;
	public abstract byte [] numericFromInt(int value, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException;
	public abstract byte [] numericFromDouble(double value, int byteLength, int scale, boolean isSigned) throws MainFrameConversionException;

}
