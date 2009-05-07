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
package com.indexoutofbounds;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.indexoutofbounds.util.PropertiesUtil;

import junit.framework.TestCase;

/**
 * Configures logging.
 * 
 * @author Travis B. Meisenheimer
 * @version $Id$
 */
public abstract class AbstractBasicTestCase extends TestCase {
    
    public AbstractBasicTestCase() {
        super();
    }
    
    public AbstractBasicTestCase(String name) {
        super(name);
    }
    
    /**
     * This implementation is final. Override <code>onSetUp</code> for custom behavior.
     *
     * @see #onSetUp()
     */
    protected final void setUp() throws Exception {
        Properties p = PropertiesUtil.getConfigProperties("log4j.properties");
        //p.putAll(PropertiesUtil.getConfigProperties("commons-logging.properties"));
        PropertyConfigurator.configure(p);
        onSetUp();
    }
    
    /**
     * Subclasses can override this method in place of the <code>setUp()</code>
     * method, which is final in this class. This implementation does nothing.
     *
     * @throws Exception simply let any exception propagate
     */
    protected abstract void onSetUp() throws Exception;
    
    /**
     * This implementation is final. Override <code>onTearDown</code> for
     * custom behavior.
     *
     * @see #onTearDown()
     */
    protected final void tearDown() throws Exception {
        onTearDown();
    }

    /**
     * Subclasses can override this to add custom behavior on teardown.
     *
     * @throws Exception simply let any exception propagate
     */
    protected abstract void onTearDown() throws Exception;
    
    /**
     * Display
     * @param val
     * @return
     */
    protected final String displayInBytePattern(byte[] val) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < val.length - 1; i += 2) {
            sb.append(val[i]);
            sb.append(val[i + 1]);
            sb.append(" ");
        }

        return sb.toString();
    }

    /**
     * Display
     * @param val
     * @return
     */
    protected final String displayBytes(byte[] val) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < val.length; i++) {
            sb.append("{");
            sb.append(val[i]);
            sb.append("}");
            sb.append(" ");
        }

        return sb.toString();
    }    
    
    protected final void assertEqualsByteArray(byte[] a, byte[] b) {
        assertEqualsByteArray(null, a, b);
    }
    protected final void assertEqualsByteArray(String message, byte[] a, byte[] b) {
        
        if (a.length != b.length) {
             fail(message);
         }
        
        for(int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                fail(message);
            }
        }
    }
    
    protected final void assertEqualsCharArray(char[] a, char[] b) {
        assertEqualsCharArray(null, a, b);
    }
    protected final void assertEqualsCharArray(String message, char[] a, char[] b) {
        
        if (a.length != b.length) {
             fail(message);
         }
        
        for(int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                fail(message);
            }
        }
    }    

}
