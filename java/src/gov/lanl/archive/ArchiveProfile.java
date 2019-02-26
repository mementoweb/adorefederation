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
 * Collection Processing Profile Object
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 */
public class ArchiveProfile {
    
    Properties properties;
    String collection;
    
    /**
     * Initializes ArchiveProfile using collection prefix
     * @param prefix
     *               Short Collection Alias
     */
    public ArchiveProfile(String prefix) {
        collection = prefix;
        properties = new Properties();
    }
    
    /**
     * Initializes ArchiveProfile using Standalone Collection Properties Object <br>
     * Example: <br>
     * profilePrefix=example<br>
     * example.FullName=info:sid/library.lanl.gov:sample<br>
     * example.ProcessorClass=gov.lanl.archive.impl.StandardProcessor<br>
     * example.pmh-defaultproperty=/adoreArchive/etc/moai/sample.properties<br>
     * 
     * @param prop
     *               Properties Object containing collection profile info
     */
    public ArchiveProfile(Properties prop) throws ArchiveException {
        properties = prop;
        collection = properties.getProperty("profilePrefix");
    }
    
    /**
     * Get the collection profile prefix 
     * @return
     *         Collection Profile Prefix
     */
    public String getCollectionProfilePrefix() {
        return collection;
    }
    
    /**
     * Get the collection processing profile fullname
     * @return
     *         Collection Processing Profile Fullname
     */
    public String getCollectionProfileFullName() {
        return properties.getProperty(collection + ".FullName");
    }
    
    /**
     * Gets the collection processing plug-in 
     * @return
     *         Collection Processing Plug-in
     */
    public String getCollectionProcessorPlugin() {
        return properties.getProperty(collection + ".ProcessorClass");
    }
    
    /**
     * Gets the collection conversion class.  Should pre-processing be 
     * required, this converter should be implemented in processor plug-in.
     * @return
     *         Collection Pre-processing Converter
     */
    public String getCollectionConverterPlugin() {
        return properties.getProperty(collection + ".ConverterClass");
    }
    
    /**
     * Gets the absolute path to the default OAI-PMH props file
     * @return
     *         Absolute path to the OAI-PMH default properties file
     */
    public String getOaiPmhDefaultProperties() {
        return properties.getProperty(collection + ".pmh-defaultproperty");
    }
    
    /**
     * Gets the classpath to the ID Index Implementation
     * @return
     *         Class path to ID Idx Impl
     */
    public String getIdentifierIdxRecordPlugin() {
        return properties.getProperty(collection + ".identifierIdxRecordPlugin");
    }
    
    /**
     * Sets the collection profile prefix
     * @param prefix
     *               Collection Processing Profile Alias
     */
    public void setCollectionProfilePrefix(String prefix) {
        properties.setProperty("profilePrefix", prefix);
        collection = prefix;
    }
    
    /**
     * Sets the collection processing profile fullname
     * @param fullname
     *               Collection Processing Profile Fullname
     */
    public void setCollectionProfileFullName(String fullname) {
        properties.setProperty(collection + ".FullName", fullname);
    }
    
    /**
     * Sets the collection processing plug-in classname
     * @param pclass
     *          Full path to Processing Plug-in
     */
    public void setCollectionProcessorPlugin(String pclass) {
        properties.setProperty(collection + ".ProcessorClass", pclass);
    }
    
    /**
     * Sets the collection conversion class.  Should pre-processing be 
     * required, this converter should be implemented in processor plug-in.
     * @param cclass
     *         Collection Pre-processing Converter
     */
    public void setCollectionConverterPlugin(String cclass) {
        properties.setProperty(collection + ".ConverterClass", cclass);
    }

    /**
     * Sets the absolute path to the default OAI-PMH props file
     * @param propFile
     *         Absolute path to the OAI-PMH default properties file
     */
    public void setOaiPmhDefaultProperties(String propFile) {
        properties.setProperty(collection + ".pmh-defaultproperty", propFile);
    }
    
    /**
     * Sets the classpath to the ID Index Implementation
     * @param path
     *         Class path to ID Idx Impl
     */
    public void setIdentifierIdxRecordPlugin(String path) {
        properties.setProperty(collection + ".identifierIdxRecordPlugin", path);
    }
    
    /**
     * Get the collection XMLtape store directory
     * @return
     *         Collection XMLtape Storage Directory
     *         (e.g. biosis.TapeStoreDirectory)
     */
    public String getCollectionTapeStoreDirectory() {
        return properties.getProperty(collection + ".TapeStoreDirectory");
    }
    
    /**
     * Set the collection XMLtape store directory
     * @param path
     *         Collection XMLtape Storage Directory
     *         (e.g. biosis.TapeStoreDirectory)
     */
    public void setCollectionTapeStoreDirectory(String path) {
        properties.setProperty(collection + ".TapeStoreDirectory", path);
    }
    
    /**
     * Get the collection XMLtape index directory
     * @return
     *         Collection XMLtape Index Directory
     *         (e.g. biosis.TapeIndexDirectory)
     */
    public String getCollectionTapeIndexDirectory() {
        return properties.getProperty(collection + ".TapeIndexDirectory");
    }
    
    /**
     * Set the collection XMLtape index directory
     * @param path
     *         Collection XMLtape Index Directory
     *         (e.g. biosis.TapeIndexDirectory)
     */
    public void setCollectionTapeIndexDirectory(String path) {
        properties.setProperty(collection + ".TapeIndexDirectory", path);
    }
    
    /**
     * Get the collection ARC Storage directory
     * @return
     *         Collection ARC Storage directory
     *         (e.g. biosis.ArcStoreDirectory)
     */
    public String getCollectionArcStoreDirectory() {
        return properties.getProperty(collection + ".ArcStoreDirectory");
    }
    
    /**
     * Set the collection ARC Storage directory
     * @param path
     *         Collection ARC Storage Directory
     *         (e.g. biosis.ArcStoreDirectory)
     */
    public void setCollectionArcStoreDirectory(String path) {
        properties.setProperty(collection + ".ArcStoreDirectory", path);
    }
    
    /**
     * Gets the properties associated with the collection processing profile
     * @return
     *         Properties object containing collection processing key/value pairs
     */ 
    public Properties getProperties() {
        return properties;
    }
    
}
