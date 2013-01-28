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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;


/**
 * Various utility methods for dealing with a properties file.  
 * This was taken from a utility class in Hibernate and modified slightly.
 * @version $Id$
 */
public class PropertiesUtil {

    private final static String EXTENSION = "properties";

    public PropertiesUtil() throws Exception {
    }

    public static Properties getConfigProperties(String path) throws Exception {

        try {

            Properties properties = new Properties();
            properties.load(ClassPathInputStreamUtils.getInputStream(path));

            return properties;

        } catch (IOException e) {
            throw new Exception("Unable to load properties from specified config file: " + path + ", " + e.getLocalizedMessage());
        }

    }

    public static String getProperty(String fileName, String key) throws Exception {

        return getProperties(checkName(fileName)).getProperty(key);
    }

    // -------------- Private Methods

    private static final String checkName(String fileName) {

        return (fileName.endsWith(EXTENSION)) ? fileName : fileName + "." + EXTENSION;
    }

    private static Properties getProperties(String fileName) throws Exception {
        Properties p = new Properties();
        return getConfigProperties(fileName);
    }

    protected static ArrayList getSortedKeys(Properties p) {
        ArrayList keys = new ArrayList();
        Iterator it = p.keySet().iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            keys.add(key);
        }

        Collections.sort(keys);
        return keys;
    }
}