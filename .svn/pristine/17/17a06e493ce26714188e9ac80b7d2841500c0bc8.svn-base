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

package gov.lanl.xmltape;

import java.util.Properties;

/**
 * Properties container for XMLTape operations; overrides default settings with local values.
 */

public class TapeConfig {
    Properties tapeProperties;
    
    /**
     * Default XMLTape Index Type
     */
    public static final String DEFAULT_INDEX_TYPE = "gov.lanl.xmltape.index.BasicTapeIndex";
    public static final String DEFAULT_ID_IDX_TYPE_IMPL = "gov.lanl.identifier.index.jdbImpl.IdentifierIndex";
    public static final String DEFAULT_ID_IDX_RECORD_IMPL = "gov.lanl.identifier.index.record.didl.DidlRecordDOM";
    
    /**
     * String Array of mandatory properties
     */ 
    public static final String[] mandatory_list = {
            "adore-xmltape.AccessorURL", 
            "adore-xmltape.StoreDirectory",
            "adore-xmltape.IndexDirectory" };

    /**
     * Initialize TapeConfig using enviroment properties object
     * @param prop
     *        Properties object containing adore-xmltape keys
     */
    public TapeConfig(Properties prop) {
        tapeProperties = prop;
        validate();
    }

    /**
     * Gets property defined as adore-xmltape-registry.OAIURL
     * @return
     *       Value defined in properties file as adore-xmltape-registry.OAIURL
     */
    public String getTapeRegistryOAIURL() {
        return tapeProperties.getProperty("adore-xmltape-registry.OAIURL");
    }

    /**
     * Gets property defined as adore-xmltape-registry.PutRecordURL
     * @return
     *       Value defined in properties file as adore-xmltape-registry.PutRecordURL
     */
    public String getTapeRegistryPutRecordURL() { 
        return tapeProperties.getProperty("adore-xmltape-registry.PutRecordURL"); 
    } 

    /**
     * Gets property defined as adore-xmltape.StoreDirectory
     * @return
     *       Value defined in properties file as adore-xmltape.StoreDirectory
     */
    public String getTapeStoredDirectory() {
        return tapeProperties.getProperty("adore-xmltape.StoreDirectory");
    }

    /**
     * Gets property defined as adore-xmltape.IndexDirectory
     * @return
     *       Value defined in properties file as adore-xmltape.IndexDirectory
     */
    public String getTapeIndexDirectory() {
        return tapeProperties.getProperty("adore-xmltape.IndexDirectory");
    }

    /**
     * Gets property defined as adore-xmltape.AccessorURL
     * @return
     *       Value defined in properties file as adore-xmltape.AccessorURL
     */
    public String getTapeAccessorURL() {
        return tapeProperties.getProperty("adore-xmltape.AccessorURL");
    }

    /**
     * Gets property defined as adore-xmltape-registry.SoapURL
     * @return
     *       Value defined in properties file as adore-xmltape-registry.SoapURL
     */
    public String getTapeRegistrySoapURL() {
        return tapeProperties.getProperty("adore-xmltape-registry.SoapURL");
    }

    /**
     * Gets property defined as [collection] + .FullName
     * @return
     *       Value defined in properties file as [collection] + .FullName
     */
    public String getCollectionFullName(String collection) {
        return tapeProperties.getProperty(collection + ".FullName");
    }
    
    /**
     * Gets property defined as [collection] + .pmh-defaultproperty
     * @return
     *       Value defined in properties file as [collection] + .pmh-defaultproperty
     */
    public String getCollection_pmh_defaultproperty(String collection) {
        return tapeProperties.getProperty(collection + ".pmh-defaultproperty");
    }
    
    /**
     * Gets property defined as [collection] + .indexSetSpecProp
     * @return
     *       Value defined in properties file as [collection] + .pmh-defaultproperty
     */
    public String getCollection_index_setSpecProps(String collection) {
        return tapeProperties.getProperty(collection + ".IndexSetSpecProps");
    }
    
    /**
     * Gets property defined as adore-xmltape-index.Plugin
     * @return
     *       Value defined in properties file as adore-xmltape-index.Plugin
     */
    public String getTapeIndexPlugin() {
        return tapeProperties.getProperty("adore-xmltape-index.Plugin", DEFAULT_INDEX_TYPE);
    }
    
    /**
     * Gets property defined as adore-xmltape-index.IdIdxPlugin
     * @return
     *       Value defined in properties file as adore-xmltape-index.IdIdxPlugin
     */
    public String getIdentifierIndexPlugin() {
        return tapeProperties.getProperty("adore-xmltape-index.IdIdxPlugin", DEFAULT_ID_IDX_TYPE_IMPL);
    }
    
    /**
     * Gets property defined as [collection] + .identifierIdxPlugin
     * @return
     *       Value defined in properties file as [collection] + .identifierIdxRecordPlugin
     */
    public String getCollectionIdentifierIndexRecordPlugin(String collection) {
        return tapeProperties.getProperty(collection + ".identifierIdxRecordPlugin", DEFAULT_ID_IDX_RECORD_IMPL);
    }
    
    /**
     * Get the collection XMLtape store directory
     * @return Collection XMLtape Storage Directory 
     *         (e.g. biosis.TapeStoreDirectory)
     */
    public String getCollectionTapeStoreDirectory(String collection) {
        return tapeProperties.getProperty(collection + ".TapeStoreDirectory");
    }
    
    /**
     * Get the collection XMLtape index directory
     * @return Collection XMLtape Index Directory 
     *         (e.g. biosis.TapeIndexDirectory)
     */
    public String getCollectionTapeIndexDirectory(String collection) {
        return tapeProperties.getProperty(collection + ".TapeIndexDirectory");
    }
    
    /**
     * Valididates properties objects against mandatory props list
     * @throws Exception
     */
    public void validate() {
        try {
            for (int i = 0; i < mandatory_list.length; i++) {

                if (tapeProperties.getProperty(mandatory_list[i]) == null)
                    throw new Exception("mandatory property "
                            + mandatory_list[i] + " is not defined");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Gets underlying Properties object
     * @return Returns the tapeProperties.
     */
    public Properties getTapeProperties() {
        return tapeProperties;
    }
}
