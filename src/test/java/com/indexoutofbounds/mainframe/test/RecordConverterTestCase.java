package com.indexoutofbounds.mainframe.test;

import org.junit.Test;

import com.indexoutofbounds.AbstractBasicTestCase;
import com.indexoutofbounds.mainframe.converter.IRecordConverter;

/**
 * 
 * @author Travis B. Meisenheimer
 * @version $Id$
 */
public class RecordConverterTestCase extends AbstractBasicTestCase {

    private IRecordConverter conv;
    
    protected void onSetUp() throws Exception {

    }

    protected void onTearDown() throws Exception {
        conv = null;
    }    
    
    @Test
    public void testFoo() throws Exception {
        //fail("Not implemented");
    }
}
