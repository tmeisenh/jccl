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

package com.indexoutofbounds.mainframe.exception;

/**
 * <p>In MainFrame/COBOL world, you would get a S0CK7 if there was some operation error.
 * So this represents some operation error.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class Sock7Exception extends MainFrameConversionException {

	public Sock7Exception() {
		super();
	}
	
	public Sock7Exception(Exception e) {
		super(e);
	}
	
	public Sock7Exception(String msg, Exception e) {
		super(msg, e);
	}
	
	public Sock7Exception (String msg){
		super(msg);
	}
}
