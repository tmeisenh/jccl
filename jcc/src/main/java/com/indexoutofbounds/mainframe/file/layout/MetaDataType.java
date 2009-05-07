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

package com.indexoutofbounds.mainframe.file.layout;
/**
 * <p>All possible types for translation.</p>
 * <p>NUMERIC (NUMBER), COMP-3 (PACKED_DECIMAL), and Alhpanumeric (DISPLAY) 
 * data are the only supported types.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 *
 */

public class MetaDataType {
	
	public static final int PACKED_DECIMAL = 0;
	public static final int ALLNUM = PACKED_DECIMAL + 2;
	public static final int DISPLAY = ALLNUM + 2;
	public static final int NUMBER = DISPLAY + 2;
}
