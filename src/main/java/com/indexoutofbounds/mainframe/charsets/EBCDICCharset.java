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
 * Contains the EBCDIC charset
 * @author <a href="mailto:travis@indexoutofbounds.com">Travis Meisenheimer</a>
 * @version $Id$
 */
public class EBCDICCharset extends Charset {

    
    /**
     * Contains most of the EBCDIC table in hex format.  EBCDIC null is represented
     * by a space.  The values that don't have an ASCII value were left out.
     */
    private static final int EBCDIC_TABLE[] = {
        0x0000, 0x0040, 0x005a, 0x007f, 0x007b, 0x005b, 0x006c, 0x0050, 
        0x007d, 0x004d, 0x005d, 0x005c, 0x004e, 0x006b, 0x0060, 0x004b, 
        0x0061, 0x00f0, 0x00f1, 0x00f2, 0x00f3, 0x00f4, 0x00f5, 0x00f6, 
        0x00f7, 0x00f8, 0x00f9, 0x007a, 0x005e, 0x004c, 0x007e, 0x006e, 
        0x006f, 0x007c, 0x00c1, 0x00c2, 0x00c3, 0x00c4, 0x00c5, 0x00c6, 
        0x00c7, 0x00c8, 0x00c9, 0x00d1, 0x00d2, 0x00d3, 0x00d4, 0x00d5, 
        0x00d6, 0x00d7, 0x00d8, 0x00d9, 0x00e2, 0x00e3, 0x00e4, 0x00e5, 
        0x00e6, 0x00e7, 0x00e8, 0x00e9, 0x00ad, 0x00e0, 0x00bd, 0x005f, 
        0x006d, 0x0079, 0x0081, 0x0082, 0x0083, 0x0084, 0x0085, 0x0086, 
        0x0087, 0x0088, 0x0089, 0x0091, 0x0092, 0x0093, 0x0094, 0x0095, 
        0x0096, 0x0097, 0x0098, 0x0099, 0x00a2, 0x00a3, 0x00a4, 0x00a5, 
        0x00a6, 0x00a7, 0x00a8, 0x00a9, 0x00c0, 0x006a, 0x00d0, 0x00a1};
    
    /**
     * Returns the EBCDIC table!
     * @return
     */
    public int[] getCharset() {
        return this.EBCDIC_TABLE;
    }    
}
