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

package gov.lanl.xmltape.identifier.index.access;

import gov.lanl.xmltape.identifier.index.record.didl.DidlRecordDOM;

import java.util.Properties;

import org.apache.log4j.Logger;

public class RecordAccessorConfig {

    private static Logger log = Logger.getLogger(RecordAccessorConfig.class.getName());
    public static final String DEFAULT_ID_IDX_RECORD_IMPL = DidlRecordDOM.class.getName();
    
    public static final String TAG_TAPE_IDX_PLUGIN = "RecordAccessor.indexPlugin";
    public static final String TAG_TAPE_IDX_FILE = "RecordAccessor.indexFile";
    public static final String TAG_TAPE_FILE_NAME = "RecordAccessor.xmltapeFile";
    public static final String TAG_TAPE_FILE_ID = "RecordAccessor.databasename";
    public static final String TAG_TAPE_IDX_RECORD_PLUGIN = "RecordAccessor.identifierIdxRecordPlugin";
    
    private String indexPlugin;
    private String indexDBDir;
    private String xmltapeFile;
    private String databaseName;
    private String idIdxRecordPlugin;
    private Properties props;
    
    public RecordAccessorConfig(Properties properties) throws IllegalArgumentException {
        this.props = properties;
        
        // Set XMLTape to process
        xmltapeFile = properties.getProperty(TAG_TAPE_FILE_NAME);
        if (xmltapeFile == null) {
            throw new IllegalArgumentException(
                    TAG_TAPE_FILE_NAME + " is missing from the properties object");
        } else {
            log.debug("xmltapeFile=" + xmltapeFile);
        }
        
        // Set Index Implementation
        indexPlugin = properties.getProperty(TAG_TAPE_IDX_PLUGIN);
        
        // Set Database Dir
        indexDBDir = properties.getProperty(TAG_TAPE_IDX_FILE);

        // Set Database Name
        databaseName = properties.getProperty(TAG_TAPE_FILE_ID);
        
        // Set Record Implementation
        idIdxRecordPlugin = properties.getProperty(TAG_TAPE_IDX_RECORD_PLUGIN, DEFAULT_ID_IDX_RECORD_IMPL);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        props.setProperty(TAG_TAPE_FILE_ID, databaseName);
    }

    public String getIndexDBDir() {
        return indexDBDir;
    }

    public void setIndexDBDir(String indexDBDir) {
        this.indexDBDir = indexDBDir;
        props.setProperty(TAG_TAPE_IDX_FILE, databaseName);
    }

    public String getIndexPlugin() {
        return indexPlugin;
    }

    public void setIndexPlugin(String indexPlugin) {
        this.indexPlugin = indexPlugin;
        props.setProperty(TAG_TAPE_IDX_PLUGIN, indexPlugin);
    }

    public String getXmltapeFile() {
        return xmltapeFile;
    }

    public void setXmltapeFile(String xmltapeFile) {
        this.xmltapeFile = xmltapeFile;
        props.setProperty(TAG_TAPE_FILE_NAME, xmltapeFile);
    }
    
    public String getRecordPlugin() {
        if (idIdxRecordPlugin != null)
            return idIdxRecordPlugin;
        else
            return DEFAULT_ID_IDX_RECORD_IMPL;
    }

    public void setRecordPlugin(String recordPlugin) {
        this.idIdxRecordPlugin = recordPlugin;
        props.setProperty(TAG_TAPE_IDX_RECORD_PLUGIN, recordPlugin);
    }
}
