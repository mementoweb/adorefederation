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

package gov.lanl.archive;

import java.util.Properties;

/**
 * Adore Archive Configurations
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 */

public class ArchiveConfig implements ArchiveConstants {
    
    Properties properties;

    public static final String[] mandatory_list = { 
        PROP_ARC_RESOLVER_URL,
        PROP_ARC_STORAGE_DIR, 
        PROP_TMP_DIR,
        PROP_TAPEREG_OAIURL,
        PROP_TAPEREG_PUTURL,
        PROP_TAPE_STORAGE_DIR, 
        PROP_TAPE_INDEX_DIR, 
        PROP_TAPE_ACCESSOR_URL};

    /**
     * Construtor to set archive configuration settings using provided 
     * properties objects; typically archive.properties is used as source.
     * @param prop
     *            Archive property file to initialize ArchiveConfig
     * @exception ArchiveException
     *            If provided archive property file is invalid
     *  
     */
    public ArchiveConfig(Properties prop) throws ArchiveException {
        properties = prop;
        validate();
    }
    
    /**
     * Gets the BaseURL path to the ARCfile Resolver
     * @return
     *        BaseURL to adore-arcfile-resolver
     */
    public String getARCResolverURL() {
        return properties.getProperty(PROP_ARC_RESOLVER_URL);
    }

    /**
     * Get Local ARCfile Prefix
     * @return
     *        Local Prefix Pre-pended to ARCfile identifier
     */
    public String getLocalArcFilePrefix(){
        return properties.getProperty(PROP_ARC_PREFIX_URL);
    }
    
    /**
     * Gets Local Datastream Prefix
     * @return
     *        Local Prefix Pre-pended to stored datastreams
     */
    public String getLocalDataStreamPrefix(){
            return properties.getProperty(PROP_ARC_DS_PREFIX);
    }
    
    /**
     * Gets the ARCfile Storage Directory Path 
     * @return
     *        Absolute path to the base ARCfile Storage directory
     */
    public String getARCStoredDirectory() {
        return properties.getProperty(PROP_ARC_STORAGE_DIR);
    }

    /**
     * Gets whether or not compression should be applied to arc files
     * @return
     *        boolean indicating compression usage
     */
    public String getARCCompression() {
        return properties.getProperty(PROP_ARC_COMPRESSION);
    }

    /**
     * Gets the BaseURL path to the OAI-PMH ARCfile Registry
     * @return
     *        BaseURL to adore-arcfile-registry
     */
    public String getARCRegistryOAIURL() {
        return properties.getProperty(PROP_ARCREG_OAIURL);
    }

    /**
     * Gets the local organizations info URI SID 
     * @return
     *        Local info URI for OpenURL SID
     */
    public String getSystemOrganization() {
        return properties.getProperty(PROP_ORGANIZATION);
    }

    /**
     * Gets the path to the tmp processing directory
     * @return
     *        Absolute path to tmp processing directory
     */
    public String getSystemTmp() {
        return properties.getProperty(PROP_TMP_DIR);
    }

    /**
     * Gets the number of threads to use for indexing and registration
     * @return
     *        number of threads to use during processing
     */
    public int getSystemThreadCount() {
        String num = properties.getProperty(PROP_SYS_THREADS);
        int cnt = (num != null) ? Integer.parseInt(num) : 1;
        return cnt;
    }
    
    /**
     * Gets the BaseURL path to the OAI-PMH XMLtape Registry
     * @return
     *        BaseURL to adore-xmltape-registry
     */
    public String getTapeRegistryOAIURL() {
        return properties.getProperty(PROP_TAPEREG_OAIURL);
    }
    
    /**
     * Gets the BaseURL path to the XMLtape Registry PutRecord servlet
     * @return
     *        BaseURL to adore-xmltape-registry PutRecord servlet
     */
    public String getTapeRegistryPutRecordURL() { 
        return properties.getProperty(PROP_TAPEREG_PUTURL);
    }

    /**
     * Gets the BaseURL path to the OAI-PMH Arc Registry
     * @return
     *        BaseURL to adore-arc-registry
     */
    public String getArcRegistryPutRecordURL() { 
        return properties.getProperty(PROP_ARCREG_PUTURL);
    }

    /**
     * Gets the XMLtape Storage Directory Path 
     * @return
     *        Absolute path to the base XMLtape storage directory
     */
    public String getTapeStoredDirectory() {
        return properties.getProperty(PROP_TAPE_STORAGE_DIR);
    }

    /**
     * Gets the XMLtape Index Directory Path 
     * @return
     *        Absolute path to the base XMLtape index directory
     */
    public String getTapeIndexDirectory() {
        return properties.getProperty(PROP_TAPE_INDEX_DIR);
    }

    /**
     * Get Local XMLTape File Prefix
     * @return
     *        Local Prefix Pre-pended to XMLTape identifier
     */
    public String getLocalXmlTapePrefix(){
        return properties.getProperty(PROP_TAPE_PREFIX_URL);
    }
    
    /**
     * Gets the BaseURL path to the XMLtape Accessor
     * @return
     *        BaseURL to adore-archive-accessor
     */
    public String getTapeAccessorURL() {
        return properties.getProperty(PROP_TAPE_ACCESSOR_URL);
    }
    
    /**
     * Gets the BaseURL path to the XMLtape Resolver
     * @return
     *        BaseURL to adore-xmltape-resolver
     */
    public String getTapeResolverURL() {
        return properties.getProperty(PROP_TAPE_RESOLVER_URL);
    }
    
    /**
     * Gets the BaseURL path to the XMLtape XQuery Resolver
     * @return
     *        BaseURL to adore-xmltape-xquery
     */
    public String getTapeXQueryResolverURL() {
        return properties.getProperty(PROP_XQUERY_RESOLVER_URL);
    }
    
    /**
     * Gets the Adore Archive XMLtape Indexing Plug-in class name
     * @return
     *        Class name for use during XMLtape indexing 
     */
    public String getTapeIndexPlugin() {
        return properties.getProperty(PROP_TAPE_INDEX_PLUGIN);
    }

    /**
     * Gets the Adore Archive XMLtape Identifier Indexing Plug-in class name
     * @return
     *        Class name for use during XMLtape identifier indexing 
     */
    public String getTapeIdentifierIndexPlugin() {
        return properties.getProperty(PROP_TAPE_IDIDX_PLUGIN);
    }
    
    /**
     * Gets full name of Collection Profile
     * @param collection
     *        Prefix of collection profile as defined in archive.properties
     * @return
     *        Full name of provided collection prefix
     */
    public String getCollectionFullName(String collection) {
        return properties.getProperty(collection + ".FullName");
    }

    /**
     * Gets Collection PMH Default Properties filepath
     * @param collection
     *        Prefix of collection profile as defined in archive.properties
     * @return
     *        Path to Collection's OAI-PMH Default Properties file
     */
    public String getCollection_pmh_defaultproperty(String collection) {
        return properties.getProperty(collection + ".pmh-defaultproperty");
    }

    /**
     * Get Adore Archive pre-processing classname
     * @param collection
     *        Prefix of collection profile as defined in archive.properties
     * @return
     *        Name of Adore Archive pre-processing class
     */       
    public String getCollectionProcessor(String collection){
        return properties.getProperty(collection + ".ProcessorClass");
    }
    
    /**
     * Gets DIDL pre-processing class name provide a collection profile prefix
     * @param collection
     *        Prefix of collection profile as defined in archive.properties
     * @return
     *        Name of DIDL pre-processing class
     */
    public String getCollectionConverter(String collection){
        return properties.getProperty(collection + ".ConverterClass");
    }
    
    /**
     * Gets Identifier pre-processing class name provide a collection profile prefix
     * @param collection
     *        Prefix of collection profile as defined in archive.properties
     * @return
     *        Name of identifier pre-processing class
     */
    public String getCollectionIdentifierIndexTypePlugin(String collection){
        return properties.getProperty(collection + ".identifierIdxTypePlugin");
    }
    
    /**
     * Gets Identifier pre-processing class name provide a collection profile prefix
     * @param collection
     *        Prefix of collection profile as defined in archive.properties
     * @return
     *        Name of identifier pre-processing class
     */
    public String getCollectionIdentifierIndexRecordPlugin(String collection){
        return properties.getProperty(collection + ".identifierIdxRecordPlugin");
    }
    
    /**
     * Get the collection XMLtape store directory
     * @return
     *         Collection XMLtape Storage Directory
     *         (e.g. biosis.TapeStoreDirectory)
     */
    public String getCollectionTapeStoreDirectory(String collection) {
        return properties.getProperty(collection + ".TapeStoreDirectory");
    }
    
    /**
     * Get the collection XMLtape index directory
     * @return
     *         Collection XMLtape Index Directory
     *         (e.g. biosis.TapeIndexDirectory)
     */
    public String getCollectionTapeIndexDirectory(String collection) {
        return properties.getProperty(collection + ".TapeIndexDirectory");
    }
    
    /**
     * Get the collection ARC Storage directory
     * @return
     *         Collection ARC Storage directory
     *         (e.g. biosis.ArcStoreDirectory)
     */
    public String getCollectionArcStoreDirectory(String collection) {
        return properties.getProperty(collection + ".ArcStoreDirectory");
    }
    
    /**
     * Iterates through list of mandatory properties, throws exception if
     * properties is not defined.
     * @throws ArchiveException
     */
    public void validate() throws ArchiveException {
        for (int i = 0; i < mandatory_list.length; i++) {
            if (properties.getProperty(mandatory_list[i]) == null)
                throw new ArchiveException("mandatory property "
                        + mandatory_list[i] + " is not defined");
        }
    }
    /**
     * Gets complete properties objects initialized with archive.properties.
     * @return Returns the properties.
     */
    public Properties getProperties() {
        return properties;
    }
}
