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

package gov.lanl.xmltape.create;

import gov.lanl.xmltape.TapeException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * TapeCreation Properties Object
 *
 */
public class TapeCreateConfig {

    Properties properties;
    
    /** List of required properties */
    public static final String[] mandatory_list = {"profile.plugin", 
        "profile.record.location", "profile.record.identifier.location", 
        "profile.record.datestamp.location" };
    
    /**
     * Creates a new TapeCreateConfig instance using the provided
     * properties file reference
     * @param propFile
     *          Properties file containing Tape Creation props
     * @throws TapeException
     */
    public TapeCreateConfig(String propFile) throws TapeException {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(propFile));
        } catch (FileNotFoundException e) {
            throw new TapeException(e);
        } catch (IOException e) {
            throw new TapeException(e);
        }
        validate();
    }

    /**
     * Gets the TapeCreateInterface implementation 
     * as a qualified classpath sting 
     * (e.g. gov.lanl.xmltape.create.RegExTapeCreateImpl)
     * 
     * @return
     *      property stored as "profile.plugin"
     */
    public String getTapeCreatePlugin() {
        return properties.getProperty("profile.plugin");
    }
    
    /**
     * Gets the record location criteria / condition
     * (e.g.  (&lt;didl:DIDL.*?&lt;/didl:DIDL&gt;))
     *  
     * @return 
     *     property stored as "profile.record.location"
     */
    public String getRecordLocationCriteria() {
        return properties.getProperty("profile.record.location");
    }
    
    /**
     * Gets the record identifier criteria / condition
     * (e.g. diext:DIDid="([^"]+))
     * 
     * @return 
     *     property stored as "profile.record.identifier.location"
     */
    public String getRecordIdentifierCriteria() {
        return properties.getProperty("profile.record.identifier.location");
    }
    
    /**
     * Gets the record datestamp criteria / condition
     * (e.g. diext:DIDcreated="([^"]+))
     * 
     * @return 
     *     property stored as "profile.record.datestamp.location"
     */
    public String getRecordDatestampCriteria() {
        return properties.getProperty("profile.record.datestamp.location");
    }
    
    /**
     * Gets the record metadata criteria / condition
     * 
     * @return 
     *     property stored as "profile.record.metadata.location"
     */
    public String getRecordMetadataCriteria() {
        return properties.getProperty("profile.record.metadata.location");
    }
    
    /**
     * Gets the local URI prefix
     * (e.g. info:lanl-repo/)
     * 
     * @return
     *     property stored as "profile.local.prefix"
     */
    public String getLocalInfoURIPrefix() {
        String prefix = properties.getProperty("profile.local.prefix");
        
        if (prefix != null && !prefix.endsWith("/"))
            prefix.concat("/");
        
        return prefix;
    }
    
    /**
     * Gets the record identifier prefix (optional)
     * 
     * @return 
     *     property stored as "profile.record.identifier.prefix"
     */
    public String getRecordIdentifierPrefix() {
        return properties.getProperty("profile.record.identifier.prefix");
    }
    
    /**
     * Gets the record identifier suffix (optional)
     * 
     * @return 
     *     property stored as "profile.record.identifier.prefix"
     */
    public String getRecordIdentifierSuffix() {
        return properties.getProperty("profile.record.identifier.suffix");
    }
    
    /**
     * Gets the underlying Java Properties object
     * @return
     *     properties object
     */
    public Properties getProperties() {
        return properties;
    }
    
    /**
     * Validate Properties file to ensure required props are available
     * @throws TapeException
     */
    public void validate() throws TapeException {
        for (int i = 0; i < mandatory_list.length; i++) {
            if (properties.getProperty(mandatory_list[i]) == null)
                throw new TapeException("mandatory property "
                        + mandatory_list[i] + " is not defined");
        }
    }    
}
