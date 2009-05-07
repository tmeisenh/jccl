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

package com.indexoutofbounds.mainframe.exception;

/**
 * <p>General exception thrown by any conversion.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class MainFrameConversionException extends Exception {
	
	
	/**
	 * Default constructor.
	 */
	public MainFrameConversionException() {
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param e - Exception thrown
	 */
	public MainFrameConversionException(Exception e) {		
		super(e.getLocalizedMessage(), e);
	}
	
	/**
	 * Constructor
	 * 
	 * @param msg - String containing the detailed message
	 */
	public MainFrameConversionException (String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * 
	 * @param msg - String containing the detailed message
	 * @param e - Exception thrown
	 */
	public MainFrameConversionException(String msg, Exception e) {		
		super(msg, e);
	}
	
}
