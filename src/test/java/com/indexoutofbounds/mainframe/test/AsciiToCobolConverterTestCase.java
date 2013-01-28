package com.indexoutofbounds.mainframe.test;

import org.junit.Test;

import com.indexoutofbounds.AbstractBasicTestCase;
import com.indexoutofbounds.mainframe.charsets.ASCIICharset;
import com.indexoutofbounds.mainframe.charsets.Charset;
import com.indexoutofbounds.mainframe.charsets.EBCDICCharset;
import com.indexoutofbounds.mainframe.converter.IAsciiToCobolConverter;
import com.indexoutofbounds.mainframe.converter.impl.CobolAndAsciiConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;

/**
 * 
 * @author Travis B. Meisenheimer
 * @version $Id$
 */
public class AsciiToCobolConverterTestCase extends AbstractBasicTestCase {
    
    private IAsciiToCobolConverter conv;
    private Charset ascii;
    private Charset ebcdic;

    protected void onSetUp() throws Exception {
        ascii = new ASCIICharset();
        ebcdic = new EBCDICCharset();
        
        conv = new CobolAndAsciiConverter(ebcdic, ascii);
    }

    protected void onTearDown() throws Exception {
        ascii = null;
        ebcdic = null;
        conv = null;
    }

    @Test
    public void testConvertStrings() throws Exception {
        
        // test perfect match
        String str = "foo bar";
        byte[] strb = conv.convertString(str, 7);
        assertTrue(strb.length == 7);   
        byte[] foo_bar = {102, 111, 111, 32, 98, 97, 114};
        assertEqualsByteArray("Testing converted bytes with known good ones", strb, foo_bar);
        
        // test adding filler
        str = "foo bar";
        strb = conv.convertString(str, 9);
        assertTrue(strb.length == 9);
        byte[] foo_bar_pad = {102, 111, 111, 32, 98, 97, 114, 32, 32};   
        assertEqualsByteArray("Testing converted bytes with known good ones", strb, foo_bar_pad);
        
        // test not enough space
        
        try {
            conv.convertString(str, 4);
            fail("Expected exception not thrown");
        } catch (MainFrameConversionException m) {}
        
    }
    
    /**
     * COMP-3
     * @throws Exception
     */
    @Test
    public void testConvertPackedNumbers() throws Exception {

        byte[] a3846 = conv.packIntIntoCOMP3(3846, 3, 0, true);
        assertTrue(a3846.length == 3);
        byte[] a3846_known = {3, -124, 108};
        assertEqualsByteArray("Testing converted bytes with known good ones", a3846, a3846_known);        
        
        // pad it
        byte[] a03846 = conv.packIntIntoCOMP3(3846, 4, 0, true);
        assertTrue(a03846.length == 4);
        byte[] a03846_known = {0, 3, -124, 108};
        assertEqualsByteArray("Testing converted bytes with known good ones", a03846, a03846_known);
        
        
        a3846 = conv.packDoubleIntoCOMP3(38.46, 3, 2, true);
        assertTrue(a3846.length == 3);
        byte[] dec3846_known = {3, -124, 108};
        assertEqualsByteArray("Testing converted bytes with known good ones", a3846, dec3846_known);        
        
        // pad it
        a03846 = conv.packDoubleIntoCOMP3(384.6, 4, 1, true);
        assertTrue(a03846.length == 4);
        byte[] dec03846_known = {0, 3, -124, 108};
        assertEqualsByteArray("Testing converted bytes with known good ones", a03846, dec03846_known);        
    }
    
    @Test
    public void testConvertDoubleNumerics() throws Exception {
        // signed numeric
        byte[] a1865 = conv.numericFromDouble(18.65, 4, 2, true);
        assertTrue(a1865.length == 4);
        byte[] a1865_known = {-15, -8, -10, -59};
        assertEqualsByteArray("Testing converted bytes with known good ones", a1865, a1865_known);
        
        byte[] neg1865 = conv.numericFromDouble(-18.65, 4, 2, true);
        assertTrue(neg1865.length == 4);
        byte[] neg1865_known = {-15, -8, -10, -43};
        assertEqualsByteArray("Testing converted bytes with known good ones", neg1865, neg1865_known);

        //unsigned
        byte[] una1865 = conv.numericFromDouble(18.65, 4, 2, false);
        assertTrue(una1865.length == 4);
        byte[] una1865_known = {-15, -8, -10, -11};
        assertEqualsByteArray("Testing converted bytes with known good ones", una1865, una1865_known);
        
        byte[] unneg1865 = conv.numericFromDouble(-18.65, 4, 2, false);
        assertTrue(neg1865.length == 4);
        byte[] unneg1865_known = {-15, -8, -10, -11};
        assertEqualsByteArray("Testing converted bytes with known good ones", unneg1865, unneg1865_known);             
    }
    
    @Test
    public void testConvertIntNumerics() throws Exception {
        // signed numeric
        byte[] a1865 = conv.numericFromInt(1865, 4, 2, true);
        assertTrue(a1865.length == 4);
        byte[] a1865_known = {-15, -8, -10, -59};
        assertEqualsByteArray("Testing converted bytes with known good ones", a1865, a1865_known);
        
        byte[] neg1865 = conv.numericFromInt(-1865, 4, 2, true);
        assertTrue(neg1865.length == 4);
        byte[] neg1865_known = {-15, -8, -10, -43};
        assertEqualsByteArray("Testing converted bytes with known good ones", neg1865, neg1865_known);

        //unsigned
        byte[] una1865 = conv.numericFromInt(1865, 4, 2, false);
        assertTrue(una1865.length == 4);
        byte[] una1865_known = {-15, -8, -10, -11};
        assertEqualsByteArray("Testing converted bytes with known good ones", una1865, una1865_known);
        
        byte[] unneg1865 = conv.numericFromInt(-1865, 4, 2, false);
        assertTrue(neg1865.length == 4);
        byte[] unneg1865_known = {-15, -8, -10, -11};
        assertEqualsByteArray("Testing converted bytes with known good ones", unneg1865, unneg1865_known);             
    }    
    
}
