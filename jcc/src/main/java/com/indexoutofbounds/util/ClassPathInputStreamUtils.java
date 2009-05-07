
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
package com.indexoutofbounds.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * Class which makes it easier to build InputStreams from a variety
 * of sources: file system, remote, classpath, etc.
 * 
 * @author Travis B. Meisenheimer
 * @version $Id$
 */
public class ClassPathInputStreamUtils {
    
    /**
     * Creates an InputStream from the path which can be either something on
     * the file system, a remote system, or a classpath entry.
     * @param path
     * @return
     * @throws Exception
     */
    public final static InputStream getInputStream(final String path) throws IOException {

        if (StringUtils.isEmpty(path)) {
            throw new IOException("Unable to locate empty config file parameter");
        }
        
        final URL url = locateURL(path);
        if (url == null) {
            throw new IOException("Unable to locate config file: " + path);
        }

        try {

            return new BufferedInputStream(url.openStream());
        } catch (IOException e) {
            throw new IOException("Unable to open config file: " + path + ", " + e.getLocalizedMessage());
        }
    }
    
    public static final Reader getConfigStreamReader(final String path) throws Exception {
        return new InputStreamReader(getInputStream(path));
    }
    
    /**
     * Trys to build a URL from the direct path, or the classpath.
     * @param path
     * @return
     */
    private static final URL locateURL(final String path) {

        try {
            return new URL(path);
        } catch (MalformedURLException e) {
            return locateAsResource(path);
        }
    }
    
    /**
     * Trys to locate a file from the classpath.
     * @param path
     * @return
     */
    private static final URL locateAsResource(final String path) {

        URL url = null;
        // First, try to locate this resource through the current
        // context classloader.
        url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url != null)
            return url;

        // Next, try to locate this resource through this class's classloader
        url = null;
        if (url != null)
            return url;

        // Next, try to locate this resource through the system classloader
        url = ClassLoader.getSystemClassLoader().getResource(path);

        // Anywhere else we should look?
        return url;
    }
}
