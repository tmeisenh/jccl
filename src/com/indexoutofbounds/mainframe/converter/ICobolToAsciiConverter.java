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
 * <p>This interface defines methods for converting COBOL data to ASCII format.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public interface ICobolToAsciiConverter {
	
	public abstract String convertAlphaNumericDisplay(byte[] ddata) throws MainFrameConversionException;
	public abstract String convertCOMP3(byte [] data, int decimalPosition) throws MainFrameConversionException;
	public abstract String convertNumericValue(byte[] number, int scale) throws MainFrameConversionException;
	public abstract String convertCOMP3WithHighPrecision(byte[] number, int scale) throws MainFrameConversionException;
	public abstract String convertNumericValueWithHighPrecision(byte[] number, int scale) throws MainFrameConversionException;	
	
	
}
