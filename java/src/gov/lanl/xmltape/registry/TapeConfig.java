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

package gov.lanl.xmltape.registry;

import java.util.Properties;
import java.util.Enumeration;
import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

/**
 * Generate property list for a tape, if it uses a pmh-defaultproperty file,
 * both files will be combined to produce a single list. (tapeconfig will
 * overwrite pmh-defaultproperty if same entry exists)
 *  
 */
public class TapeConfig {
    Properties tapeProperties;
    static Logger log = Logger.getLogger(TapeConfig.class.getName());
    
    public static final String TAG_TAPE_REPO_ID = "TapeOAICatalog.databasename";
    public static final String TAG_TAPE_URI = "TapeOAICatalog.xmltapeUri";
    public static final String TAG_TAPE_FILE_NAME = "TapeOAICatalog.xmltapeFile";
    public static final String TAG_TAPE_FILE_INDEX = "TapeOAICatalog.indexDBDir";
    public static final String TAG_TAPE_PMH_DEFAULT = "TapeOAICatalog.pmh-defaultproperty";
    public static final String TAG_TAPE_REG_DATESTAMP = "TapeOAICatalog.registertime";
    public static final String TAG_TAPE_ADMIN = "TapeOAICatalog.admin";
    public static final String TAG_TAPE_FILE_DIGEST = "TapeOAICatalog.digest";

    public TapeConfig(Properties prop) throws RegistryException {
        try {
            tapeProperties = new Properties();

            if (prop.getProperty(TAG_TAPE_PMH_DEFAULT) != null) {
                File oaiProps = new File(prop.getProperty(TAG_TAPE_PMH_DEFAULT));
                if (oaiProps.exists())
                    tapeProperties.load(new FileInputStream(oaiProps));
            }

            tapeProperties.putAll(prop);

            logProperties(tapeProperties);
        } catch (java.io.IOException ex) {
            throw new RegistryException(prop
                    .getProperty(TAG_TAPE_PMH_DEFAULT), ex);
        }
    }

    /**
     * Return results in a property format
     */
    public Properties getProperties() {
        return tapeProperties;
    }
    
    /**
     * Gets property defined as TapeOAICatalog.databasename
     * @return
     *       Value defined in properties file as TapeOAICatalog.databasename
     */
    public String getXMLTapeId() {
        return tapeProperties.getProperty(TAG_TAPE_REPO_ID);
    }
    
    /**
     * Gets property defined as TapeOAICatalog.xmltapeFile
     * @return
     *       Value defined in properties file as TapeOAICatalog.xmltapeFile
     */
    public String getXMLTapeUri() {
        return tapeProperties.getProperty(TAG_TAPE_URI);
    }
    
    /**
     * Gets property defined as TapeOAICatalog.xmltapeFile
     * @return
     *       Value defined in properties file as TapeOAICatalog.xmltapeFile
     */
    public String getXMLTapeFile() {
        return tapeProperties.getProperty(TAG_TAPE_FILE_NAME);
    }

    /**
     * Gets property defined as TapeOAICatalog.indexDBDir
     * @return
     *       Value defined in properties file as TapeOAICatalog.indexDBDir
     */
    public String getTapeIndexFile() {
        return tapeProperties.getProperty(TAG_TAPE_FILE_INDEX);
    }
    
    /**
     * Gets property defined as TapeOAICatalog.digest
     * @return
     *       Value defined in properties file as TapeOAICatalog.digest
     */
    public String getXMLTapeDigest() {
        return tapeProperties.getProperty(TAG_TAPE_FILE_DIGEST);
    }
    
    private void logProperties(Properties prop) {
        for (Enumeration enums = prop.keys(); enums.hasMoreElements();) {
            String key = (String) (enums.nextElement());
            String value = (String) (prop.get(key));
            log.debug(key + "=" + value);
        }

    }

}
