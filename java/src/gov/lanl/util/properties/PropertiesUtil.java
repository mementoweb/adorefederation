/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 */

package gov.lanl.util.properties;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
    /**
     * Loads the given configuration file by class path.
     * 
     */
    public static Properties loadConfigByCP(String name) throws Exception {

        // Get our class loader
        ClassLoader cl = PropertiesUtil.class.getClassLoader();

        // Attempt to open an input stream to the configuration file.
        // The configuration file is considered to be a system
        // resource.
        java.io.InputStream in;

        if (cl != null) {
            in = cl.getResourceAsStream(name);
        } else {
            in = ClassLoader.getSystemResourceAsStream(name);
        }

        // If the input stream is null, then the configuration file
        // was not found
        if (in == null) {
            throw new Exception("configuration file '" + name + "' not found");
        } else {
            return loadProperty(in);
        }

    }
    
    /**
     * Loads the configuration file from specified system path.
     * 
     */
    public static Properties loadConfigByPath(String path) throws Exception {
        FileInputStream fi = new FileInputStream(path);
        return loadProperty(fi);
    }

    /**
     * Primes a properties obejct from the given InputStream
     * 
     */
    public static Properties loadProperty(InputStream in) throws IOException {
        Properties prop;
        try {
            prop = new java.util.Properties();
            // Load the configuration file into the properties table
            prop.load(in);
        } finally {
            // Always close the input stream
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        return prop;
    }
    
    public static String getGlobalProperty(String key) {
        String value = null;
        // Check if available in Java System Properties
        if (System.getProperty(key) != null) {
            return System.getProperty(key);
        }

        // Format key to OS ENV Parameter format 
        // s/[^a-z0-9]/_/g
        key = key.trim().replaceAll("[^a-zA-Z0-9]", "_").toUpperCase();
        if (System.getenv(key) != null) {
            value = System.getenv(key);
        } else
            throw new NullPointerException(key + " is not configured");
        return value;
    }
    
    public static Set<String> getPropertiesList(Properties props, String prefix) {
        ArrayList<String> al = new ArrayList<String>();
        for (int i = 0; i < props.size(); i++) {
            if (props.containsKey(prefix + "." + i)) {
                al.add(props.getProperty(prefix + "." + i));
            }
        }
        
        if (al.isEmpty()) {
            if (props.containsKey(prefix))
                al.add(props.getProperty(prefix));
        }
        return new HashSet(al);
    }
    
    public static Properties loadConfigByClasspath(String path) throws IOException {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        return props;
    }
}
