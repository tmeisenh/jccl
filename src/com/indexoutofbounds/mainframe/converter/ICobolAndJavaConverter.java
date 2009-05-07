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

package com.indexoutofbounds.mainframe.converter;


/**
 * <p>This interface defines the abilities of a class which controls the conversion of data.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public interface ICobolAndJavaConverter extends IAsciiToCobolConverter, ICobolToAsciiConverter {
	
	public abstract String getEncoding();
	public abstract void setEncoding(String str);
	
	public abstract void configureAsciiToEBCDIC();
	public abstract void configureEBCDICToAscii();	
}
