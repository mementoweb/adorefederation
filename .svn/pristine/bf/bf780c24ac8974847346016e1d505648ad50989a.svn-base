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

package gov.lanl.adore.repo;

import java.util.Properties;

/**
 * Adore Federation Ingestion Configurations
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 */

public class RepoConfig implements RepoConstants {
    
    Properties properties;

    /**
     * Construtor to set adore repo configuration settings using provided 
     * properties objects; typically archive.properties is used as source.
     * @param prop
     *            Repo property file to initialize ArchiveConfig/RepoConfig
     * @throws Exception 
     *  
     */
    public RepoConfig(Properties prop) throws Exception {
        properties = prop;
    }
    
    /**
     * Gets the BaseURL path to the OAI-PMH Service Registry
     * @return
     *        BaseURL to adore-service-registry
     */
    public String getServiceRegistryOAIURL() {
        return properties.getProperty(PROP_SVCREG_OAIURL);
    }
    
    /**
     * Gets the BaseURL path to the OpenURL Service Registry
     * @return
     *        BaseURL to adore-service-registry
     */
    public String getServiceRegistryResolverURL() {
        return properties.getProperty(PROP_SVCREG_RESOLVER_URL);
    }
    
    /**
     * Gets the BaseURL path to the Service Registry Record Handler Service
     * @return
     *        BaseURL to adore-service-registry record handler service
     */
    public String getServiceRegistryRecordHandlerURL() {
        return properties.getProperty(PROP_SVCREG_HANDLERURL);
    }
    
    /**
     * Gets the BaseURL path to the OpenURL ID Locator
     * @return
     *        BaseURL to adore-id-locator
     */
    public String getIdLocatorResolverURL() {
        return properties.getProperty(PROP_IDLOC_RESOLVER_URL);
    }
    
    /**
     * Gets the file path to the ID Locator DB Config
     * @return
     *        File path to adore-id-locator config file
     */
    public String getIdLocatorDbProps() {
        return properties.getProperty(PROP_IDLOC_DBCONFIG);
    }
    
    /**
     * Gets Path to Collection Svc Registry Properties
     * @param collection
     *        Prefix of collection profile as defined in adore.properties
     * @return
     *        Path of collection's service registry properties
     */
    public String getCollectionSvcRegPropDir(String collection) {
        return properties.getProperty(collection + ".ServiceRegPropertyDir");
    }
    
    /**
     * Index Item Formats (e.g. iesr:itemFormat -&gt; application/xml)
     * @param collection
     *        Prefix of collection profile as defined in adore.properties
     * @return
     *        Whether or not itemFormats should be indexed 
     */
    public boolean getCollectionSvcRegAddItemFormats(String collection) {
        return Boolean.parseBoolean(properties.getProperty(collection + ".iesr.itemFormats"));
    }
    
    /**
     * Index Item Types (e.g. iesr:itemType -&gt; info:lanl-repo/sem/1)
     * @param collection
     *        Prefix of collection profile as defined in adore.properties
     * @return
     *        Whether or not itemTypes should be indexed 
     */
    public boolean getCollectionSvcRegAddItemTypes(String collection) {
        return Boolean.parseBoolean(properties.getProperty(collection + ".iesr.itemTypes"));
    }
    
    /**
     * Index Subjects (e.g. dc:subject -&gt; MATHEMATICS)
     * @param collection
     *        Prefix of collection profile as defined in adore.properties
     * @return
     *        Whether or not subjects should be indexed 
     */
    public boolean getCollectionSvcRegAddSubjects(String collection) {
        return Boolean.parseBoolean(properties.getProperty(collection + ".iesr.subjects"));
    }
    
    //  MOD rtfteam@lanl.gov 20070619 quick support for inline temporal range processing
    /**
     * Index temporal range (e.g. marc tag 008 years lowest - highest)
     * @param collection
     *        Prefix of collection profile as defined in adore.properties
     * @return
     *        Whether or not temporal range should be indexed 
     */
    public boolean getCollectionSvcRegAddTemporalRange(String collection) {
        return Boolean.parseBoolean(properties.getProperty(collection + ".iesr.temporalrange"));
    }
    
    /**
     * Gets complete properties objects initialized with archive.properties.
     * @return Returns the properties.
     */
    public Properties getProperties() {
        return properties;
    }
}
