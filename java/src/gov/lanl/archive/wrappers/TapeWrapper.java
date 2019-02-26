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

package gov.lanl.archive.wrappers;

import gov.lanl.archive.ArchiveException;
import gov.lanl.identifier.IndexException;
import gov.lanl.repo.oaidb.srv.PutPostClient;
import gov.lanl.repo.oaidb.srv.PutResponseHandler;
import gov.lanl.repo.oaidb.tapereg.TapeRegXML;
import gov.lanl.repo.oaidb.tapereg.TapeRegistryConstants;
import gov.lanl.util.DigestUtil;
import gov.lanl.xmltape.SingleTapeReader;
import gov.lanl.xmltape.SingleTapeWriter;
import gov.lanl.xmltape.TapeConfig;
import gov.lanl.xmltape.TapeException;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.identifier.index.IdentifierIndexRegistry;
import gov.lanl.xmltape.identifier.index.access.RecordAccessor;
import gov.lanl.xmltape.identifier.index.access.RecordAccessorConfig;
import gov.lanl.xmltape.identifier.index.access.SeqTapeAccessor;
import gov.lanl.xmltape.identifier.index.record.Record;
import gov.lanl.xmltape.index.TapeIndexInterface; 
import gov.lanl.xmltape.index.TapeIndexer;
import gov.lanl.xmltape.index.sets.SetSpecNamespace;
import gov.lanl.xmltape.index.sets.SetSpecProfile;
import gov.lanl.xmltape.index.sets.SetSpecProfileFactory;
import gov.lanl.xmltape.index.sets.SetSpecXPath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TapeWrapper implements TapeRegistryConstants {
    static Logger log = Logger.getLogger(TapeWrapper.class.getName());

    private static final int TAPE_INDEX = 1;

    private static final int TAPE_REGISTER = 2;

    private TapeConfig config;

    private static String propFile;

    private static String tapeFile;

    private static String collection;

    private static int verb;

    private String indexPlugin;

    private static String tapeAdmin;

    /**
     * Usage gov.lanl.archive.wrappers.TapeWrapper --verb register --propFile [propFile]
     * --tapeFile [tapeFile] --collection biosis --verb Options: index / register
     * --propFile - location of properties file --tapeFile - location to XMLTape
     * --collection - collection
     */
    public static void main(String[] args) throws Exception {

        Hashtable parahash = new Hashtable();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(1);
            }

            log.info(arg + "=" + args[i + 1]);
            // options
            if (arg.startsWith("--"))
                parahash.put(arg.substring(2), args[++i]);
        }

        readParameters(parahash);

        TapeWrapper tapeRepo = new TapeWrapper(propFile);
        tapeFile = getFileNameMinusExt(tapeFile);
        tapeAdmin = "test";

        if (verb == TAPE_INDEX) {
            tapeRepo.indexTapeFile(collection, tapeFile);
            tapeRepo.indexTapeIdentifiers(collection, tapeFile);
        }

        if (verb == TAPE_REGISTER)
            tapeRepo.registerTapeFile(collection, tapeFile, tapeAdmin);
    }

    /**
     * Print Usage for main()
     */
    private static void printUsage() {
        log.warn("Usage gov.lanl.archive.wrappers.TapeWrapper --verb register --propFile <propFile> --arcFile <arcFile> --collection biosis");
        log.warn("--verb Options: index / register");
        log.warn("--propFile - location of properties file");
        log.warn("--tapeFile - arcname");
        log.warn("--collection - collection");
        System.exit(0);
    }

    /**
     * Construtor - Uses initialized properties object
     * @param prop
     *        Properties used to initialize the TapeWrapper
     * @throws Exception
     */
    public TapeWrapper(Properties prop) throws Exception {
        config = new TapeConfig(prop);
        config.validate();
    }

    /**
     * Construtor - Uses source properties file.
     * @param propfile
     *        Path to properties file containing arc configurations
     * @throws Exception
     */
    public TapeWrapper(String propfile) throws Exception {
        Properties prop = new Properties();
        log.debug("Initializing TapeWrapper from " + propfile);
        prop.load(new FileInputStream(new File(propfile)));
        config = new TapeConfig(prop);
        config.validate();
    }

    /**
     * Gets a SingleTapeWriter for the specified tapename; provided a name,
     * will prepend XMLtape Storage Path & Extension
     * @param tapename
     *        Name of tape to be created, minus extension
     * @return
     *        SingleTapeWriter, used to write XMLtape records
     * @throws TapeException
     */
    public SingleTapeWriter openTapeWriter(String tapename)
            throws TapeException {
        return openTapeWriter(tapename, config.getTapeStoredDirectory());
    }

    /**
     * Gets a SingleTapeWriter for the specified tapename and storage dir; 
     * provided a name, will prepend XMLtape Storage Path & Extension
     * @param tapename
     *        Name of tape to be created, minus extension
     * @param storageDir
     *        Path to XMLtape Storage Directory
     * @return
     *        SingleTapeWriter, used to write XMLtape records
     * @throws TapeException
     */
    public SingleTapeWriter openTapeWriter(String tapename, String storageDir)
            throws TapeException {
        try {
            File dir = new File(storageDir);
            if (!dir.exists())
                dir.mkdirs();
            log.debug("Opening Tape Writer for " + tapename);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(new File(storageDir,
                                    tapename2file(tapename))), "UTF-8"));
            if (config.getTapeProperties().containsKey("adoreArchive.tmpDir"))
                return new SingleTapeWriter(writer, new File((String) config.getTapeProperties().get("adoreArchive.tmpDir")));
            else
                return new SingleTapeWriter(writer);
        } catch (Exception ex) {
            log.error("Error in openTapeWriter() for " + tapename + ": " + ex.getMessage());
            throw new TapeException("error in openTapeWriter "
                    + tapename, ex);
        }
    }
    
    /**
     * Gets a SingleTapeReader for specified tapename
     * @param tapename
     *        Name of tape to be read, minus extension
     * @return
     *        SingleTapeReader, used to read specified tape
     * @throws TapeException
     */
    public SingleTapeReader openTapeReader(String tapename)
            throws TapeException {
        String indexdir = config.getTapeIndexDirectory();
        String tapefile = config.getTapeStoredDirectory();
        return openTapeReader(tapename, tapefile, indexdir);
    }
    
    /**
     * Gets a SingleTapeReader for specified tapename
     * @param tapename
     *        Name of tape to be read, minus extension
     * @param storageDir
     *        Path to XMLtape Storage Directory
     * @param indexDir
     *        Path to XMLtape Index Directory
     * @return
     *        SingleTapeReader, used to read specified tape
     * @throws TapeException
     */
    public SingleTapeReader openTapeReader(String tapename, String storageDir, String indexDir)
            throws TapeException {
        try {
            log.debug("Opening Tape Reader for " + tapename);
            String indexdir = indexDir + File.separator
                    + tapename2index(tapename);
            String tapefile = storageDir + File.separator
                    + tapename2file(tapename);
            // Determine Indexing API
            indexPlugin = config.getTapeIndexPlugin();
            Class impl = Class.forName(indexPlugin);
            TapeIndexInterface tapeIdx = (TapeIndexInterface) impl
                    .newInstance();
            tapeIdx.setTapeName(tapename);
            tapeIdx.setIndexDir(indexdir);
             // Read Tape
            SingleTapeReader reader = new SingleTapeReader(tapeIdx, tapefile);
            reader.open();
            return reader;
        } catch (Exception ex) {
            log.error("Error in openTapeReader() for " + tapename + ": " + ex.getMessage());
            throw new TapeException("error in openSingleTapeReader "
                    + tapename, ex);
        }
    }

    
    /**
     * Index XMLtape file; uses tape indexing plug-in specified in ArchiveConfig
     * @param tapename
     *            Name of xml tape file
     * @throws TapeException
     */
    public int indexTapeFile(String collection, String tapename) throws TapeException {
        try {
            log.debug("Creating XMLtape index for " + tapename);
            
            // Determine where the files are stored
            String tapeStore = config.getTapeStoredDirectory();
            String idxStore = config.getTapeIndexDirectory();
            if (config.getCollectionTapeStoreDirectory(collection) != null)
                tapeStore = config.getCollectionTapeStoreDirectory(collection);
            if (config.getCollectionTapeIndexDirectory(collection) != null)
                idxStore = config.getCollectionTapeIndexDirectory(collection);
            
            String tapefile = tapeStore + File.separator + tapename2file(tapename);  
            String indexdir = idxStore + File.separator  + tapename2index(tapename);
            File dir = new File(indexdir);
            indexPlugin = config.getTapeIndexPlugin();
            if (!dir.exists())
                dir.mkdirs();
            Class impl = Class.forName(indexPlugin);
            log.debug("Plugin: " + indexPlugin);
            TapeIndexInterface tapeIdx = (TapeIndexInterface) impl.newInstance();
            tapeIdx.setTapeName(tapename);
            tapeIdx.setIndexDir(indexdir);
            TapeIndexer indexer = new TapeIndexer(tapeIdx);
            String setPropFile = config.getCollection_index_setSpecProps(collection);
            if (setPropFile != null) {
                Properties setSpecProps = new Properties();
                setSpecProps.load(new FileInputStream(new File(setPropFile)));
                SetSpecProfile sspp = SetSpecProfileFactory.generateSetSpecProfile(setSpecProps);
                // Process Namespace Defs
                for (Iterator i = sspp.getNamespaces().iterator(); i.hasNext();) {
                    SetSpecNamespace ssn = (SetSpecNamespace) i.next();
                    indexer.addSetNamespaces(ssn.getNamespacePrefix(), ssn.getNamespace());
                }
                // Process Set Spec XPath Information
                for (Iterator j = sspp.getXpaths().iterator(); j.hasNext();) {
                    SetSpecXPath ssx = (SetSpecXPath) j.next();
                    indexer.addSetElementXPath(ssx.getXpath(), ssx.getXpathPrefix());
                }
            }
            indexer.parse(tapefile);
            return indexer.getTapeRecordCount();
        } catch (Exception ex) {
            log.error("Error in indexTapeFile() for " + tapename + ": " + ex.getMessage());
            throw new TapeException("error in indexTapeFile(): " + tapename, ex);
        }
    }

    /**
     * Index Identifiers contained in the TapeRecords
     */
    public void indexTapeIdentifiers(String collection, String tapename) throws TapeException {
        log.debug("Creating XMLtape Identifier index for " + tapename);
        
        // Determine where the files are stored
        String tapeStore = config.getTapeStoredDirectory();
        String idxStore = config.getTapeIndexDirectory();
        if (config.getCollectionTapeStoreDirectory(collection) != null)
            tapeStore = config.getCollectionTapeStoreDirectory(collection);
        if (config.getCollectionTapeIndexDirectory(collection) != null)
            idxStore = config.getCollectionTapeIndexDirectory(collection);
        
        String indexdir = idxStore + File.separator + tapename2index(tapename);
        String tapefile = tapeStore + File.separator + tapename2file(tapename);
        String idxPlugin = config.getIdentifierIndexPlugin();
        
        File dir = new File(indexdir);
        if (!dir.exists())
            dir.mkdir();
        
        Properties props = new Properties();
        props.put(RecordAccessorConfig.TAG_TAPE_FILE_NAME, tapefile);
        props.put(RecordAccessorConfig.TAG_TAPE_IDX_RECORD_PLUGIN, config.getCollectionIdentifierIndexRecordPlugin(collection));
        props.put(RecordAccessorConfig.TAG_TAPE_IDX_FILE, indexdir);
     
        RecordAccessor ta = new SeqTapeAccessor();
        IdentifierIndexInterface idx = null;
        try {
            idx = IdentifierIndexRegistry.getIdentifierIndexImpl(idxPlugin, indexdir);
            ta.init(props);
            idx.setIndexDir(indexdir);
            idx.open(false);
        } catch (IndexException e) {
            log.error("Error initializing tape identifier index for " + tapename);
            throw new TapeException("Error initializing tape identifier index", e);
        }
        
        // Iterate through records, adding identifiers to index
        while(ta.hasNext()) {
            Record rec = ta.next();
            try {
                idx.putIdentifiers(rec.getIdentifiers());
            } catch (IndexException e) {
                log.error("Unable to process ids from " + rec);
            }
        }
        
        // Close Index
        try {
            idx.close();
            ta.close();
        } catch (IndexException e) {
            log.error("Unable to close SeqTapeAccessor or Identifier Index");
        }
    }
    
    /**
     * Register xml tape in adore-xmltape-registry
     * @param collection
     *            Collection Profile Prefix to which the tape belongs
     * @param tapename
     *            Name of xml tape file, minus extension
     * @param tapeadmin
     *            TapeAdmin XML fragament from tapeAdmin
     * @throws ArchiveException
     */
    public void registerTapeFile(String collection, String tapename, String tapeadmin) 
       throws TapeException {
        
        // Determine where the files are stored
        String tapeStore = config.getTapeStoredDirectory();
        String idxStore = config.getTapeIndexDirectory();
        if (config.getCollectionTapeStoreDirectory(collection) != null)
            tapeStore = config.getCollectionTapeStoreDirectory(collection);
        if (config.getCollectionTapeIndexDirectory(collection) != null)
            idxStore = config.getCollectionTapeIndexDirectory(collection);
        
        try {
            Properties prop = new Properties();
            String xmlTapeID = TapeProperties.getLocalXmlTapePrefix() + tapename;
            String fileUriPrefix = "file://";
            String xmlTapeFilePath = new File(tapeStore, tapename2file(tapename)).getAbsolutePath();
            String digest = DigestUtil.getSHA1Digest(new FileInputStream(xmlTapeFilePath));
            digest = DigestUtil.toURN(digest, "sha1");
            String xmlTapeIdxPath = new File(idxStore, tapename2index(tapename)).getAbsolutePath();
            String oaiPropsPath = config.getCollection_pmh_defaultproperty(collection);
            
            // Handles Windows File URI Properly
            if (xmlTapeFilePath.charAt(1) == ':') {
                fileUriPrefix = "file:///";
                xmlTapeFilePath = xmlTapeFilePath.replace("\\", "/");
                xmlTapeIdxPath = xmlTapeIdxPath.replace("\\", "/");
                oaiPropsPath = oaiPropsPath.replace("\\", "/");
            }
            
            String xmlTapePathURI = fileUriPrefix + xmlTapeFilePath;
            String xmlTapeIdxPathURI = fileUriPrefix + xmlTapeIdxPath;
            String oai0 = config.getTapeAccessorURL();
            String oai1 = oai0.substring(0, oai0.lastIndexOf("/"));
            String oai2 = oai0.substring(oai0.lastIndexOf("/") + 1);
            String oaipmhURL= oai1 + "/" + tapename + "/" + oai2;
            
            String oaipmhProps = fileUriPrefix + oaiPropsPath;
            
            prop.put(ELEMENT_XML_TAPE_ID, xmlTapeID);
            prop.put(ELEMENT_XML_TAPE_FILEPATH, xmlTapePathURI);
            prop.put(ELEMENT_XML_TAPE_IDX, xmlTapeIdxPathURI);
            prop.put(ELEMENT_OAIPMH_URL, oaipmhURL);
            prop.put(ELEMENT_OAIPMH_PROPERTIES, oaipmhProps);
            prop.put(ELEMENT_TAPE_ADMIN, tapeadmin);
            prop.put(ELEMENT_XML_TAPE_DIGEST, digest);
            
            TapeRegXML repo = new TapeRegXML();
            PutPostClient client = new PutPostClient();
            String result = client.execPut(config.getTapeRegistryPutRecordURL(), repo.ProptoXML(prop));
            log.debug(prop.toString());
            log.debug("\n**** XMLtape Registry PutRecord Response ****\n " + result);
            PutResponseHandler pmp = new PutResponseHandler(result);
            if (!pmp.isSuccess())
                throw new TapeException(pmp.getError());
        } catch (Exception ex) {
            throw new TapeException(ex.getMessage(), ex);
        }

    }

    /**
     * Gets XMLtape filename from tapename
     * @param tapename
     *        Filename without extension suffix
     * @return
     *        Name of XMLtape file    
     */
    private static String tapename2file(String tapename) {
        return tapename + ".xml";
    }

    /**
     * Gets XMLtape index filename from tapename
     * @param tapename
     *        Filename without extension suffix
     * @return
     *        Name of index file    
     */
    private static String tapename2index(String tapename) {
        return tapename + ".idx";
    }

    /**
     * Gets XMLtape identifier index filename from tapename
     * @param tapename
     *        Filename without extension suffix
     * @return
     *        Name of index file    
     */
    private static String tapename2idindex(String tapename) {
        return tapename + ".idi";
    }
    
    /**
     * Gets the file name minus extension suffix
     * @param name
     *        Path to file
     * @return
     *        Name of file minus extension 
     */
    public static String getFileNameMinusExt(String name) {
        int j = name.lastIndexOf("/");
        name = name.substring(j + 1);
        int jj = name.lastIndexOf(".");
        name = name.substring(0, jj);
        return name;
    }

    /**
     * Read & Parse Command Line Parameters
     * @param ht
     *        String Array of parameters
     */
    private static void readParameters(Hashtable ht) {
        if ((ht.get("verb") == null)) {
            printUsage();
            System.exit(1);
        }
        if ((ht.get("tapeFile") == null)) {
            printUsage();
            System.exit(1);
        }
        if ((ht.get("propFile") == null)) {
            printUsage();
            System.exit(1);
        } else {
            String verbtext = (String) (ht.get("verb"));
            if (verbtext.equals("index"))
                verb = TAPE_INDEX;
            else if (verbtext.equals("register"))
                verb = TAPE_REGISTER;
            else {
                printUsage();
                System.exit(1);
            }
        }
        if ((ht.get("propFile") != null)) {
            propFile = (String) (ht.get("propFile"));
        }
        if ((ht.get("tapeFile") != null)) {
            tapeFile = (String) (ht.get("tapeFile"));
        }
        if ((ht.get("collection") != null)) {
            collection = (String) (ht.get("collection"));
        }
        if ((ht.get("tapeAdmin") != null)) {
            tapeAdmin = (String) (ht.get("tapeAdmin"));
        }
    }
}
