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
package com.indexoutofbounds.mainframe.charsets;


/**
 * <p>Class which keeps track of the formats we are using for conversion.  Specifically,
 * we have ASCII and EBCDIC conversion tables only.  Future versions may contain more
 * conversion tables.</p>
 * 
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 * @version $Id$
 * 
 */
public abstract class Charset {
	
    /**
     * Returns the ASCII charset!
     * @return
     */
    public abstract int[] getCharset();
    
}
