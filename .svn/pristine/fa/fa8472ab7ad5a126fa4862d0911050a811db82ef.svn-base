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

package gov.lanl.oai.resource;

import gov.lanl.harvester.ListRecords2Tape;
import gov.lanl.ingest.oaitape.DirIngester;
import gov.lanl.xmltape.TapeConstants;
import gov.lanl.xmltape.TapeWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * <p>Wrapper class for OAIResource package; integrates OAI Harvesting & Dereferencing</p>
 *                
 *   OAIResource resource = new OAIResource();<br>
 *   envProps = new Properties();<br>
 *   envProps.load(new FileInputStream(new File(envPropsFile)));<br>
 *   resource.setEnvParameters(envProps);<br>
 *   projectProps = new Properties();<br>
 *   projectProps.load(new FileInputStream(new File(projectPropsFile)));<br>
 *   resource.setProjectParameters(projectProps);<br>
 *   resource.beginHarvest();<br>
 *   resource.beginDeref();<br>
 * 
 *  @author rchute
 */
public class OAIResource implements OAIResourceConstants {
    
    public static final boolean DEFAULT_COMPRESSION = false;
    
    private String envPropsFile;
    private Properties envProps;
    private String projectPropsFile;
    private Properties projectProps;
    private String lastIngestFile;
    private Properties lastIngestProps;
    private String lastHarvestFile;
    private Properties lastHarvestProps;
    private String projectName;
    private String baseURL;
    private String from;
    private String until;
    private String sets;
    private String metadataPrefix;
    private String tapeDir;
    private String tapePrefix;
    private boolean gzip = DEFAULT_COMPRESSION;
    private int maxsize = TapeConstants.MAX_TAPE_SIZE;
    
    private String lockFile = "lock.txt";
    
    private String localSIDPrefix;
    private String localDSPrefix;
    private String projectDir;
    private String ingestPlugin;
    private String orgURIPrefix;
    private String lastIngest;

    static Logger log = Logger.getLogger(OAIResource.class.getName());
    
    /**
     * Wrapper method for OAI Harvester - Writes OAI List Record 
     * Result Set to XMLTape
     * @throws OAIResourceException
     */
    public void beginHarvest() throws OAIResourceException {
        String timedTapeDir = tapeDir + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "/";
        new File(timedTapeDir).mkdirs();
        try {
            TapeWriter writer;
            if (maxsize != 0)
                writer = new TapeWriter(new File(timedTapeDir), tapePrefix, gzip, maxsize);
            else
                writer = new TapeWriter(new File(timedTapeDir), tapePrefix, gzip);
            
            if (until.length() == 0 || until == null)
                until = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            
            ListRecords2Tape listrecords = new ListRecords2Tape(baseURL, from,
                    until, sets, metadataPrefix, writer);
            listrecords.start();
            writer.close();
            if (lastHarvestFile != null)
               this.setLastHarvestProperty(until);
        } catch (Exception e) {
            throw new OAIResourceException(e);
        }
    }
    
    /**
     * Wrapper method for Dereferencing API - Reads harvested OAI 
     * records, resolves URL, and writes resources as datastream in ArcFile
     * @throws OAIResourceException
     */
    public void beginDeref() throws OAIResourceException {
        DirIngester ingester = new DirIngester();
        ingester.setProjectDir(projectDir);
        ingester.setPlugin(ingestPlugin);
        ingester.setProject(projectName);
        ingester.setXmlTapesDir(tapeDir);
        ingester.setLastIngest(lastIngest);
        ingester.setMaxsize(maxsize);
        ingester.setSidPrefix(localSIDPrefix);
        ingester.setDataStreamPrefix(localDSPrefix);
        ingester.runIt();
        if (lastIngestFile != null)
          this.setLastIngestProperty(ingester.getLastIngest());
    }
    
    /**
     * Get the project name for the dereferenced content
     * @return Returns the projectName.
     */
    public String getProjectName() {
        return projectName;
    }
    /**
     * Set the project name for the dereferenced content
     * @param projectName The projectName to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    /**
     * Get the OAI Record Request baseURL variable
     * @return Returns the baseURL.
     */
    public String getBaseURL() {
        return this.baseURL;
    }
    /**
     * Set the OAI Record Request baseURL variable
     * @param baseURL The baseURL to set.
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
    /**
     * Get the OAI Record Request from variable
     * @return Returns the from.
     */
    public String getFrom() {
        return this.from;
    }
    /**
     * Set the OAI Record Request from variable
     * @param from The from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     * Get the time of the last ingest in the form "yyyyMMddHHmmss"
     * @return Returns the lastIngest time in string form.
     */
    public String getLastIngest() {
        return lastIngest;
    }

    /**
     * Set the time of the last ingest in the form "yyyyMMddHHmmss"
     * @param lastIngest The lastIngest to set.
     */
    public void setLastIngest(String lastIngest) {
        this.lastIngest = lastIngest;
    }
    /**
     * Get the OAI Record Request metadataPrefix variable
     * @return Returns the metadataPrefix.
     */
    public String getMetadataPrefix() {
        return this.metadataPrefix;
    }
    /**
     * Set the OAI Record Request metadataPrefix variable
     * @param metadataPrefix The metadataPrefix to set.
     */
    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }
    /**
     * Get the OAI Record Request sets variable
     * @return Returns the sets.
     */
    public String getSet() {
        return this.sets;
    }
    /**
     * Set the OAI Record Request sets variable
     * @param sets The sets to set.
     */
    public void setSet(String sets) {
        this.sets = sets;
    }
    /**
     * Get the OAI Record Request until variable
     * @return Returns the until.
     */
    public String getUntil() {
        return this.until;
    }
    /**
     * Set the OAI Record Request until variable
     * @param until The until to set.
     */
    public void setUntil(String until) {
        this.until = until;
    }
    /**
     * Indicates if the generated XML Tape is gzipped
     * @return Returns the gzip.
     */
    public boolean isGzip() {
        return this.gzip;
    }
    /**
     * Indicate if the generated XML Tape should be gzipped
     * @param gzip The gzip to set.
     */
    public void setGzip(boolean gzip) {
        this.gzip = gzip;
    }
    /**
     * Get max size for each arc file to ge generated
     * @return Returns the maxsize.
     */
    public int getMaxsize() {
        return this.maxsize;
    }
    /**
     * Defines the max size for each arc file (default is 100000000)
     * @param maxsize The maxsize to set.
     */
    public void setMaxsize(int maxsize) {
        this.maxsize = maxsize;
    }
    /**
     * Get the location to which the harvested xmltapes are to be / have been written
     * @return Returns the tapeDir.
     */
    public String getTapeDir() {
        return this.tapeDir;
    }
    /**
     * Set the location to which the harvested xmltapes are to be / have been written
     * @param tapeDir The tapeDir to set.
     */
    public void setTapeDir(String tapeDir) {
        this.tapeDir = tapeDir;
    }
    /**
     * Get the prefix value used for dereferenced xmltapes
     * @return Returns the tapePrefix.
     */
    public String getTapePrefix() {
        return this.tapePrefix;
    }
    /**
     * Set the prefix value to be used for dereferenced xmltapes
     * @param tapePrefix The tapePrefix to set.
     */
    public void setTapePrefix(String tapePrefix) {
        this.tapePrefix = tapePrefix;
    }
    /**
     * Get the Environment Properties Object
     * @return Returns the props.
     */
    public Properties getenvProps() {
        return envProps;
    }
    
    // Application Methods
    
    /**
     * Main method - Expects the location of two properties files <br>
     * argv[0] = absolute path to env.properties file <br>
     * argv[1] = absolute path to project.properties file 
     */
    public static void main(String[] argv) {
        if (argv.length == 0)
            printUsage();
        
        OAIResource resource = new OAIResource();
         resource.processArgs(argv);
        try {
            resource.setLockFile();
        } catch (OAIResourceException e) {
            log.error("\n");
            log.error("Unable to generate lock file: \n");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            resource.beginHarvest();
        } catch (OAIResourceException e) {
            log.error("Exception occured in Harvesting Operation: \n");
            e.printStackTrace();
        }
        
        try {
            resource.beginDeref();
        } catch (OAIResourceException e) {
            log.error("Exception occured in Dereferencing Operation: \n");
            e.printStackTrace();
        }
        resource.clearLockFile();
    }
    
    /** Prints the usage. */
    private static void printUsage() {
        System.out.println("gov.lanl.oai.resource.OAIResource <envPropFile> <projectPropFile>");
    }
    
    /** Convert String to boolean */
    private static boolean getbooleanValue(String str) {
        if (str.trim().equalsIgnoreCase("true"))
            return true;
        if (str.trim().equalsIgnoreCase("false"))
            return false;
        else
            return false;
    }
    
    /** Convert String to int */
    private static int getintValue(String str) {
        int value = new Integer(str).intValue();
        return value;
    }
    
    /**
     * Writes time of last ingest to the Ingest Property File
     * @param time
     */
    public void setLastIngestProperty(String time) {
          Properties fprop = new Properties();
          File f = new File(lastIngestFile);
          fprop.setProperty(PROP_INGEST_LASTTIME, time);
          try {
            fprop.store(new FileOutputStream(f), "Last Ingest Timestamp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Writes time of last harvest to the Harvest Property File
     * @param time
     */
    public void setLastHarvestProperty(String time) {
          Properties fprop = new Properties();
          File f = new File(lastHarvestFile);
          fprop.setProperty(PROP_HARVEST_LASTTIME, time);
          try {
            fprop.store(new FileOutputStream(f), "Last Harvest Timestamp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Set local project instance variables based on values contain
     * in a properties object. 
     * @param props
     * @throws OAIResourceException 
     */
    public void setProjectParameters(Properties props) throws OAIResourceException {
        // Required Properties
        ArrayList<String> missingProps = new ArrayList<String>();
        if (props.getProperty(PROP_PROJECT_NAME) != null) 
            this.projectName = props.getProperty(PROP_PROJECT_NAME);
        else
            missingProps.add(PROP_PROJECT_NAME);
        if (props.getProperty(PROP_OAI_BASEURL) != null) 
            this.baseURL = props.getProperty(PROP_OAI_BASEURL);
        else
            missingProps.add(PROP_OAI_BASEURL);
        if (props.getProperty(PROP_OAI_METADATAPREFIX) != null) 
            this.metadataPrefix = props.getProperty(PROP_OAI_METADATAPREFIX);
        else
            missingProps.add(PROP_OAI_METADATAPREFIX);
        if (props.getProperty(PROP_INGEST_PLUGIN) != null)    
            this.ingestPlugin = props.getProperty(PROP_INGEST_PLUGIN);
        else
            missingProps.add(PROP_INGEST_PLUGIN);
        if (props.getProperty(PROP_PROJECT_DIR) != null) {
            this.projectDir = new File(props.getProperty(PROP_PROJECT_DIR)).getAbsolutePath();
            if (!this.projectDir.endsWith("/"))
                this.projectDir = this.projectDir + File.separator;
            this.tapeDir = projectDir + "tape/";
        } else 
            missingProps.add(PROP_PROJECT_DIR);
        if (missingProps.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (Iterator i = missingProps.iterator(); i.hasNext();) {
                sb.append(i.next() + "\n");
            }
            throw new OAIResourceException("Missing required project properties:\n" + sb.toString());
        }
        
        // Optional Properties
        this.from = props.getProperty(PROP_OAI_FROM);
        this.until = props.getProperty(PROP_OAI_UNTIL);
        this.sets = props.getProperty(PROP_OAI_SETS);
    }
    
    /**
     * Set local environment instance variables based on values contain
     * in a properties object. 
     * @param props
     * @throws OAIResourceException 
     */
    public void setEnvParameters(Properties props) throws OAIResourceException {
        // Required Properties
        ArrayList<String> missingProps = new ArrayList<String>();
        if (props.getProperty(PROP_TAPE_PREFIX) != null)
            this.tapePrefix = props.getProperty(PROP_TAPE_PREFIX);
        else
            missingProps.add(PROP_TAPE_PREFIX);
        if (props.getProperty(PROP_LOCAL_DS_PREFIX) != null)
            this.localDSPrefix = props.getProperty(PROP_LOCAL_DS_PREFIX);
        else
            missingProps.add(PROP_LOCAL_DS_PREFIX);
        if (props.getProperty(PROP_LOCAL_SID_PREFIX) != null)        
            this.localSIDPrefix = props.getProperty(PROP_LOCAL_SID_PREFIX);
        else
            missingProps.add(PROP_LOCAL_SID_PREFIX);
        if (missingProps.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (Iterator i = missingProps.iterator(); i.hasNext();) {
                sb.append(i.next() + "\n");
            }
            throw new OAIResourceException("Missing required env properties:\n" + sb.toString());
        }
        
        // Optional 
        
        if (props.getProperty(PROP_TAPE_GZIP) != null)
            this.gzip = getbooleanValue(props.getProperty(PROP_TAPE_GZIP));
        if (props.getProperty(PROP_TAPE_MAXSIZE) != null)
            this.maxsize = getintValue(props.getProperty(PROP_TAPE_MAXSIZE));
    }
    
    /**
     * Creates a project specific lock file to prevent concurrent processing
     * @throws OAIResourceException
     */
    public void setLockFile() throws OAIResourceException {
          Properties fprop = new Properties();
          File f = new File(projectDir + lockFile);
          if (f.exists()) {
                try {
                    fprop.load(new FileInputStream(f));
                } catch (FileNotFoundException e) {
                } 
                  catch (IOException e) {
                }
                throw new OAIResourceException("Process Currently Running Since " + fprop.getProperty("invocationTime") 
                                                + "\nDelete " + lockFile + " to override."                        );
          }
          fprop.setProperty("invocationTime", new Date().toString());
          try {
            fprop.store(new FileOutputStream(f), "Invocation Time");
        } catch (FileNotFoundException e) {
                throw new OAIResourceException("Unable to create " + lockFile + ":\n " + e.toString());
        } catch (IOException e) {
            throw new OAIResourceException("IOException attempting to access " + lockFile + ":\n " + e.toString());
        }
    }
    
    /**
     * Clears lock file generated by setLockFile(), should be called before exit.
     *
     */
    public void clearLockFile() {
          Properties fprop = new Properties();
          File f = new File(projectDir + lockFile);
          if (f.exists())
                f.delete();
    }
    
    private void processArgs(String[] argv) {
        Hashtable parahash = new Hashtable();
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(0);
            }
            if (arg.startsWith("--"))
                parahash.put(arg.substring(2), argv[++i]);
            else {
                printUsage();
                System.exit(0);
            }
        }
        if (parahash.get("env") != null) {
            envPropsFile = (String) (parahash.get("env"));
            File envFile = new File(envPropsFile);

            try {
                 envProps = new Properties();
                 envProps.load(new FileInputStream(envFile));
                 setEnvParameters(envProps);
            } catch (FileNotFoundException e) {
                log.error("Environment Properties File was not found at " + envPropsFile);
                System.exit(0);
            } catch (IOException e) {
                log.error("Error reading file from " + envPropsFile);
                System.exit(0);
            } catch (OAIResourceException e) {
                log.error(e.getMessage());
                System.exit(0);
            }
        }
        if (parahash.get("project") != null) {
            projectPropsFile = (String) (parahash.get("project"));
            File projFile = new File(projectPropsFile);
            
            try {
                 // Project Properties File
                 projectProps = new Properties();
                 projectProps.load(new FileInputStream(projFile));
                 setProjectParameters(projectProps);
        
                 // Last Harvest Properties File
                 lastHarvestFile = projectDir + getProjectName().toLowerCase() + "_" + FILE_LASTHARVEST;
                 File harvest = new File(lastHarvestFile);
                 if (harvest.exists()) {
                    lastHarvestProps = new Properties();
                    lastHarvestProps.load(new FileInputStream(new File(lastHarvestFile)));
                    this.from = lastHarvestProps.getProperty(PROP_HARVEST_LASTTIME);
                 }
                 else
                        this.from = projectProps.getProperty(PROP_OAI_FROM);
                 
                  //    Last Ingest Properties File
                 lastIngestFile = projectDir + getProjectName().toLowerCase() + "_" + FILE_LASTINGEST;
                 File ingest = new File(lastIngestFile);
                 if (ingest.exists()) {
                    lastIngestProps = new Properties();
                    lastIngestProps.load(new FileInputStream(new File(lastIngestFile)));
                    this.lastIngest = lastIngestProps.getProperty(PROP_INGEST_LASTTIME);
                 }
                 else
                        this.lastIngest = "19000101010101";
                 
            } catch (FileNotFoundException e) {
                log.error("Project Properties File was not found at " + projectPropsFile);
                System.exit(0);
            } catch (IOException e) {
                log.error("Error reading file from " + projectPropsFile);
                System.exit(0);
            } catch (OAIResourceException e) {
                log.error(e.getMessage());
                System.exit(0);
            }   
        }
    }


    
}
