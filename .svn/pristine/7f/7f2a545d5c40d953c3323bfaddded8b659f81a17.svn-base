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

package gov.lanl.xmltape.resolver.xquery;

import gov.lanl.util.xquery.XQueryProfile;
import gov.lanl.util.xquery.XQueryProfileFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ServiceProfileUtil {
    
    private static Logger log = Logger.getLogger(ServiceProfileUtil.class.getName());
    private static final String PROP_PROFILE_FORMAT = "profile.format";
    private static final String PROP_PROFILE_FORMAT_SUFFIX = "suffix";
    private static final String PROP_PROFILE_FORMAT_CLASS = "class";
    
    private HashMap<String, XQueryProfile> profiles = new HashMap<String, XQueryProfile>();
    private HashMap<String, String> implMap = new HashMap<String, String>();
    private String path;
    
    public ServiceProfileUtil(String path) {
        this.path = path;
        this.init();
    }
    
    public HashMap<String, XQueryProfile> getServiceProfileMap() {
        return profiles;
    }
    
    private void init() {
        ArrayList<File> props = getFileList(path, new AdoreXQueryFileFilter());
        for (File prop : props) {
            XQueryProfile xqp = XQueryProfileFactory.generateXQueryProfile(prop);
            for (int i = 0; i < props.size(); i++) {
                Properties p = xqp.getProperties();
                if (p.containsKey(PROP_PROFILE_FORMAT + "." + i + "." + PROP_PROFILE_FORMAT_CLASS)) {
                    String impl = p.getProperty(PROP_PROFILE_FORMAT + "." + i + "." + PROP_PROFILE_FORMAT_CLASS);
                    String svcid = xqp.getProfileId();
                    if (p.containsKey(PROP_PROFILE_FORMAT + "." + i + "." + PROP_PROFILE_FORMAT_SUFFIX))
                        svcid += "." + p.getProperty(PROP_PROFILE_FORMAT + "." + i + "." + PROP_PROFILE_FORMAT_SUFFIX);
                    log.info("Adding " + svcid + " using " + impl);
                    profiles.put(svcid, xqp);
                    implMap.put(svcid, impl);
                }
            }
        }
    }
    
    public HashMap<String, String> getServiceImplMap() {
        return implMap;
    }
    
    /**
     * Gets a ArrayList of File objects provided a dir or file path.
     * @param filePath
     *        Absolute path to file or directory
     * @param fileFilter
     *        Filter dir content by extention; allows "null"
     * @return
     *        ArrayList of File objects matching specified criteria.
     */
    public static ArrayList<File> getFileList(String filePath, FileFilter fileFilter) {
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            File[] fa = file.listFiles(fileFilter);
            for (int i = 0; i < fa.length; i++) {
                if (fa[i].isFile())
                  files.add(fa[i]);
            }
        }
        else if (file.exists() && file.isFile()) {
            files.add(file);
        }
        
        return files;
    }
    
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        byte[] bytes = new byte[(int)length];
    
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+ file.getName());
        }
    
        is.close();
        return bytes;
    }
    
    public class AdoreXQueryFileFilter implements java.io.FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) return true;
            String name = f.getName().toLowerCase();
            return name.endsWith("axq");
        }
    }
}
