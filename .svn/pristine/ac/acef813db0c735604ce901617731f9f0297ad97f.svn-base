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

package gov.lanl.xmltape.oai;

import java.util.Properties;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * <code>Config</code> defines the properties used by a TapeOAICatalog 
 * instance. 
 */

public class Config {
    final int DEFAULT_MAX_SIZE = 100;
    
    int maxListSize;

    String indexPlugin;
    
    String indexDBDir;

    String xmltapeFile;
    
    String xmltapeUri;

    String databaseName;

    String loggerLevel;

    String forcedDatestamp;

    SetsList forcedSets;

    static Logger log = Logger.getLogger(Config.class.getName());

    public Config(Properties properties) throws IllegalArgumentException {

        String ListSize = properties.getProperty("TapeOAICatalog.maxListSize");
        if (ListSize == null) {
            maxListSize = DEFAULT_MAX_SIZE;
        } else {
            maxListSize = Integer.parseInt(ListSize);
        }
        log.debug("maxListSize=" + maxListSize);

        indexPlugin = properties.getProperty("TapeOAICatalog.indexPlugin");
        if (indexPlugin == null) {
            throw new IllegalArgumentException(
                    "TapeOAICatalog.indexPlugin is missing from the properties file");
        } else {
            log.debug("indexPlugin=" + indexPlugin);
        }
        
        // Legacy Config File Support
        // indexDBDir = properties.getProperty("TapeOAICatalog.berkeleyDBDir");
        
        indexDBDir = properties.getProperty("TapeOAICatalog.indexDBDir");
        if (indexDBDir == null) {
            throw new IllegalArgumentException(
                    "TapeOAICatalog.indexDBDir is missing from the properties file");
        } else {
            log.debug("indexDBDir=" + indexDBDir);
        }

        xmltapeFile = properties.getProperty("TapeOAICatalog.xmltapeFile");
        if (xmltapeFile == null) {
            throw new IllegalArgumentException(
                    "TapeOAICatalog.xmltapeFile is missing from the properties file");
        } else {
            log.debug("xmltapeFile=" + xmltapeFile);
        }

        xmltapeUri = properties.getProperty("TapeOAICatalog.xmltapeUri");
        if (xmltapeUri == null) {
            throw new IllegalArgumentException(
                    "TapeOAICatalog.xmltapeUri is missing from the properties file");
        } else {
            log.debug("xmltapeUri=" + xmltapeUri);
        }
        
        databaseName = properties.getProperty("TapeOAICatalog.databasename");
        if (databaseName == null) {
            throw new IllegalArgumentException(
                    "TapeOAICatalog.databasename is missing from the properties file");
        } else {
            log.debug("databaseName=" + databaseName);
        }
        // If tapeoaidatestamp is specified, we assume all records have same
        // datestamp. (never use the indexed datestmap)
        forcedDatestamp = properties.getProperty("TapeOAICatalog.forcedDatestamp");
        log.debug("forcedDatestamp=" + forcedDatestamp);
        try {
            String sets = properties.getProperty("TapeOAICatalog.forcedSets");
            log.debug("forcedSets=" + sets);
            if (sets != null)
                forcedSets = new SetsList(sets);
        } catch (SetformatException ex) {
            log.warn(ex.getMessage());
            throw new IllegalArgumentException("Error caused by TapeOAICatalog.forcedSets");
        }

        // Change repositoryName
        String pname=null;
        if (properties.getProperty("Identify.repositoryName")==null)
            pname="XMLTape Repository";          
        else
            pname=properties.getProperty("Identify.repositoryName");
           
        // If there is already an appended database name, we don't append it again
        if (pname.indexOf(databaseName)==-1){
            properties.setProperty("Identify.repositoryName", pname +" : "+ databaseName);
            log.info("repository Name "+ properties.getProperty("Identify.repositoryName"));
        }

        // Change OAIHandler.baseURL    
        if (properties.getProperty("OAIHandler.baseURL")!=null){
            String baseURL=properties.getProperty("OAIHandler.baseURL");
            if (baseURL.indexOf(databaseName)==-1){
            properties.setProperty("OAIHandler.baseURL", baseURL+"/"+ databaseName);
            }
        }      
    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public String getIndexDBDir() {
        return indexDBDir;
    }

    public String getXmltapeFile() {
        return xmltapeFile;
    }

    public String getXmltapeUri() {
        return xmltapeUri;
    }
    
    public String getdbname() {
        return databaseName;
    }

    public String getLoggerLevel() {
        return loggerLevel;
    }

    public String getForcedDatestamp() {
        return forcedDatestamp;
    }

    public List getXMLSets() {
        if (forcedSets != null)
            return forcedSets.getXMLList();
        else
            return null;
    }

    public List getSetSpecs() {
        if (forcedSets != null)
            return forcedSets.getSpecList();
        else
            return null;
    }

    public boolean useForcedDatestamp() {
        return (forcedDatestamp != null);
    }

    public boolean useForcedSets() {
        return (forcedSets != null);
    }

    public String getIndexPlugin() {
        return indexPlugin;
    }

}
