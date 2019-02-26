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

package gov.lanl.arc;

import java.util.Properties;

public class ARCEnvConfig  {
    Properties arcProperties;

    public static final String TAG_ARC_RESOLVER_URL = "adore-arcfile.ResolverURL";
    public static final String TAG_ARC_STORAGE_DIR = "adore-arcfile.StoreDirectory";
    public static final String TAG_ARC_COMPRESSION = "adore-arcfile.Compression";
    public static final String TAG_ARC_OAI_URL = "adore-arcfile-registry.OAIURL";
    public static final String TAG_ARC_PUT_RECORD = "adore-arcfile-registry.PutRecordURL";
    public static final String TAG_ARC_FILE_ID = "adore-arcfile.FileID";
    public static final String TAG_ARC_FILE_NAME = "adore-arcfile.FileName";
    public static final String TAG_ARC_FILE_INDEX = "adore-arcfile.FileIndex";
    public static final String TAG_ARC_FILE_DIGEST = "adore-arcfile.FileDigest";
    
    /**
     * List of mandatory properties 
     * Required: adore-arcfile.ResolverURL, adore-arcfile.StoreDirectory, adore-arcfile.IndexDirectory
     */
    public static final String[] mandatory_list = {
        TAG_ARC_RESOLVER_URL, TAG_ARC_STORAGE_DIR 
    };

    public ARCEnvConfig(Properties prop) {
        arcProperties = prop;
        //validate();
    }

    /**
     * Gets the arc configuration properties object
     */
    public Properties getProperties() {
        return arcProperties;
    }
    
    /**
     * Gets the ARC Resolver URL property
     */
    public String getARCResolverURL() {
        return arcProperties.getProperty(TAG_ARC_RESOLVER_URL);
    }
    
    /**
     * Get directory to which the ARC files will be written
     */
    public String getARCStoredDirectory() {
        return arcProperties.getProperty(TAG_ARC_STORAGE_DIR);
    }
    
    /**
     * Gets the boolean of whether gzip compression should be used
     */
    public String getARCCompression() {
        return arcProperties.getProperty(TAG_ARC_COMPRESSION);
    }
    
    /**
     * Gets the ARC Registry OAI Repository URL
     */
    public String getARCRegistryOAIURL() {
        return arcProperties.getProperty(TAG_ARC_OAI_URL);
    }
    
    /**
     * Gets the Full Collection Name
     */
    public String getCollectionFullName(String collection) {
        return arcProperties.getProperty(collection + ".FullName");
    }
    
    /**
     * Gets the ARC file id 
     */
    public String getARCFileID() {
        return arcProperties.getProperty(TAG_ARC_FILE_ID);
    }
    
    /**
     * Gets the ARC file name
     */
    public String getARCFileName() {
        return arcProperties.getProperty(TAG_ARC_FILE_NAME);
    }
    
    /**
     * Gets the ARC index file name
     */
    public String getARCIndexFileName() {
        return arcProperties.getProperty(TAG_ARC_FILE_INDEX);
    }    
    
    /**
     * Gets the ARC Registry PutRecord Servlet URL
     */
    public String getARCRegistryPutRecordURL() {
        return arcProperties.getProperty(TAG_ARC_PUT_RECORD);
    }
    
    /**
     * Gets the ARC Digest URI
     */
    public String getARCDigest() {
        return arcProperties.getProperty(TAG_ARC_FILE_DIGEST);
    }
    
    /**
     * Get the collection ARC Storage directory
     * 
     * @return
     *         Collection ARC Storage directory
     *         (e.g. biosis.ArcStoreDirectory)
     */
    public String getCollectionArcStoreDirectory(String collection) {
        return arcProperties.getProperty(collection + ".ArcStoreDirectory");
    }
    
    /**
     * Validate Properties to ensure mandatory properties are defined
     * @throws Exception
     *  
     */
    public void validate() {
        try {
            for (int i = 0; i < mandatory_list.length; i++) {

                if (arcProperties.getProperty(mandatory_list[i]) == null)
                    throw new Exception("mandatory property "
                            + mandatory_list[i] + " is not defined");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
