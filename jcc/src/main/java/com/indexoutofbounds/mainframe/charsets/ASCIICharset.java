/*
 * Copyright (c) 2005 - 2008 Travis B. Meisenheimer
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
 * Contains the ASCII charset
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 * @version $Id$
 */
public class ASCIICharset extends Charset {

    /**
     * Contains the entire ASCII table in hex format.
     * ASCII null is represented as a space.
     */
    private static final int ASCII_TABLE[] = {
        0x003f, 0x0020, 0x0021, 0x0022, 0x0023, 0x0024, 0x0025, 0x0026, 
        0x0027, 0x0028, 0x0029, 0x002a, 0x002b, 0x002c, 0x002d, 0x002e, 
        0x002f, 0x0030, 0x0031, 0x0032, 0x0033, 0x0034, 0x0035, 0x0036, 
        0x0037, 0x0038, 0x0039, 0x003a, 0x003b, 0x003c, 0x003d, 0x003e, 
        0x003f, 0x0040, 0x0041, 0x0042, 0x0043, 0x0044, 0x0045, 0x0046, 
        0x0047, 0x0048, 0x0049, 0x004a, 0x004b, 0x004c, 0x004d, 0x004e, 
        0x004f, 0x0050, 0x0051, 0x0052, 0x0053, 0x0054, 0x0055, 0x0056, 
        0x0057, 0x0058, 0x0059, 0x005a, 0x005b, 0x005c, 0x005d, 0x005e, 
        0x005f, 0x0060, 0x0061, 0x0062, 0x0063, 0x0064, 0x0065, 0x0066, 
        0x0067, 0x0068, 0x0069, 0x006a, 0x006b, 0x006c, 0x006d, 0x006e, 
        0x006f, 0x0070, 0x0071, 0x0072, 0x0073, 0x0074, 0x0075, 0x0076, 
        0x0077, 0x0078, 0x0079, 0x007a, 0x007b, 0x007c, 0x007d, 0x007e};
    
    /**
     * Returns the ASCII charset!
     * @return
     */
    public int[] getCharset() {
        return this.ASCII_TABLE;
    }
    
}
