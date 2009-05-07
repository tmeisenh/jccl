package com.indexoutofbounds.mainframe.test;

import com.indexoutofbounds.AbstractBasicTestCase;
import com.indexoutofbounds.mainframe.charsets.ASCIICharset;
import com.indexoutofbounds.mainframe.charsets.Charset;
import com.indexoutofbounds.mainframe.charsets.EBCDICCharset;
import com.indexoutofbounds.mainframe.converter.ICobolToAsciiConverter;
import com.indexoutofbounds.mainframe.converter.impl.CobolAndAsciiConverter;

/**
 * 
 * @author Travis B. Meisenheimer
 * @version $Id$
 */
public class CobolToAsciiConverterTestCase extends AbstractBasicTestCase {

    private ICobolToAsciiConverter conv;
    
    private Charset ascii;
    private Charset ebcdic;
    
    protected void onSetUp() throws Exception {
        ascii = new ASCIICharset();
        ebcdic = new EBCDICCharset();
        
        conv = new CobolAndAsciiConverter(ebcdic, ascii);
    }

    protected void onTearDown() throws Exception {
        conv = null;
    }
    
    public void testFoo() throws Exception {
        
    }
}
