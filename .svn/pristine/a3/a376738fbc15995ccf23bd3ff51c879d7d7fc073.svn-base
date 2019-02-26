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

package gov.lanl.util.xquery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class XQueryProfileFactory {
    
    static Logger log = Logger.getLogger(XQueryProfileFactory.class.getName());
    
    public static final String PROFILE_ID = "profile.id";
   
    public static final String PROFILE_NS = "profile.namespace";
    
    public static final String PROFILE_NS_PREFIX = "profile.namespace.prefix";

    public static final String XQUERY = "profile.xquery";

    public static final String ROOT_PATH = "profile.filter.path";

    public static XQueryProfile generateXQueryProfile(File file) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(file));
            return generateXQueryProfile(props);
        } catch (FileNotFoundException e) {
            log.error("XQuery Properties File was not found at " + file);
        } catch (IOException e) {
            log.error("Error reading file from " + file);
        }
        return null;
    }
    
    public static XQueryProfile generateXQueryProfile(Properties props) {
        XQueryProfile profile = new XQueryProfile();
        profile.setProfileId(props.getProperty(PROFILE_ID));
        profile.setXQuery(props.getProperty(XQUERY));
        profile.setFilterPath(props.getProperty(ROOT_PATH));
        
        // Iterate through / set namespace profiles
        for (int i = 0; i < props.size(); i++) {
            if (props.containsKey(PROFILE_NS + "." + i)) {
                String ns = props.getProperty(PROFILE_NS + "." + i);
                String pre = props.getProperty(PROFILE_NS_PREFIX + "." + i);
                profile.addNamespaceProfile(pre, ns);
            } 
        }
        
        // Add the props object for ext purposes
        profile.setProperties(props);

        return profile;
    }
}
