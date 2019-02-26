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

package gov.lanl.adore.diag;

import gov.lanl.adore.repo.RepoConfig;
import gov.lanl.adore.repo.adorearchive.AdoreArchiveRepository;
import gov.lanl.arc.ARCProperties;
import gov.lanl.arc.heritrixImpl.ARCFileReader;
import gov.lanl.archive.ArchiveCollection;
import gov.lanl.archive.ArchiveConfig;
import gov.lanl.archive.ArchiveException;
import gov.lanl.archive.ArchiveProfile;
import gov.lanl.archive.util.ARCFileFilter;
import gov.lanl.archive.util.FileUtil;
import gov.lanl.archive.util.XMLFileFilter;
import gov.lanl.archive.wrappers.ARCWrapper;
import gov.lanl.archive.wrappers.TapeWrapper;
import gov.lanl.locator.DbCleaner;
import gov.lanl.util.oai.oaiharvesterwrapper.GetRecord;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeAdmin;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.index.berkeleydbImpl.BDBIndex;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class AdoreRegistryChecker {
    static Logger log = Logger.getLogger(AdoreRegistryChecker.class.getName());
    private ArchiveConfig archiveConfig;
    private RepoConfig repoConfig;
    private ArrayList<File> tapes = new ArrayList<File>();
    private ArrayList<File> arcs = new ArrayList<File>();
    private boolean recoveryMode;
    private boolean verifyTapeIndexMode;
    private String profile;
    private ARCWrapper aw;
    private TapeWrapper tw;
    private ArchiveProfile archiveProfile;
    private AdoreArchiveRepository repoProcessor;
    private DbCleaner dbCleaner;

    public AdoreRegistryChecker(Properties props) throws Exception {
        try {
            this.archiveConfig = new ArchiveConfig(props);
            this.repoConfig = new RepoConfig(props);
            this.aw = new ARCWrapper(props);
            this.tw = new TapeWrapper(props);
            this.repoProcessor = new AdoreArchiveRepository();
            if (repoConfig != null)
                this.repoProcessor.setRepoConfig(repoConfig);
            Properties idLoc = new Properties();
            idLoc.load(new FileInputStream(repoConfig.getIdLocatorDbProps()));
            this.dbCleaner = new DbCleaner(idLoc);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setArchiveProfile(String profilePrefix) throws Exception {
        archiveProfile = new ArchiveProfile(profilePrefix); 
        // Set the Mandatory Properties, use default if missing
        String fullName = archiveConfig.getCollectionFullName(profilePrefix);
        if (fullName == null || fullName.trim().equals("")) {
            log.warn(profilePrefix + ".FullName is not defined; using default");
            fullName = archiveConfig.getCollectionFullName("default");
            if (fullName == null || fullName.trim().equals(""))
                throw new Exception("default.FullName is not defined");
        }
        String pmhDefaultProp = archiveConfig.getCollection_pmh_defaultproperty(profilePrefix);
        if (pmhDefaultProp == null || pmhDefaultProp.trim().equals("")) {
            log.warn(profilePrefix + ".pmh-defaultproperty is not defined; using default");
            pmhDefaultProp = archiveConfig.getCollection_pmh_defaultproperty("default");
            if (pmhDefaultProp == null || pmhDefaultProp.trim().equals(""))
                throw new Exception("default.pmh-defaultproperty is not defined");
        }
        String idIdxRecordImpl = archiveConfig.getCollectionIdentifierIndexRecordPlugin(profilePrefix);
        if (idIdxRecordImpl == null || idIdxRecordImpl.trim().equals("")) {
            log.warn(profilePrefix + ".identifierIdxRecordPlugin is not defined; using default");
            idIdxRecordImpl = archiveConfig.getCollectionIdentifierIndexRecordPlugin("default");
            if (idIdxRecordImpl == null || idIdxRecordImpl.trim().equals(""))
                throw new Exception("default.identifierIdxRecordPlugin is not defined");
        }
        // Set collection xmltape and arc dirs
        String tapeDir = archiveConfig.getCollectionTapeStoreDirectory(profilePrefix);
        String idxDir = archiveConfig.getCollectionTapeIndexDirectory(profilePrefix);
        String arcDir = archiveConfig.getCollectionArcStoreDirectory(profilePrefix);
        // Set the Mandatory Properties, throw exception if missing
        String processorClass = archiveConfig.getCollectionProcessor(profilePrefix).trim();
        if (processorClass == null || processorClass.trim().equals(""))
            throw new Exception(profilePrefix + ".processorClass is not defined");
        // Set optional Profile-Specific Properties
        String convertClass = archiveConfig.getCollectionConverter(profilePrefix);
        archiveProfile.setCollectionProfileFullName(fullName);
        archiveProfile.setCollectionProcessorPlugin(processorClass);
        archiveProfile.setOaiPmhDefaultProperties(pmhDefaultProp);
        archiveProfile.setIdentifierIdxRecordPlugin(idIdxRecordImpl);
        if (convertClass != null)
            archiveProfile.setCollectionConverterPlugin(convertClass);
        if (tapeDir != null)
            archiveProfile.setCollectionTapeStoreDirectory(tapeDir);
        if (idxDir != null)
            archiveProfile.setCollectionTapeIndexDirectory(idxDir);
        if (arcDir != null)
            archiveProfile.setCollectionArcStoreDirectory(arcDir);
    }
    
    private static void printUsage() {
        System.out.println("Usage: " +
                "--config [archiveProp] " +
                "--profile [collectionPrefix] " +
                "--xmltape <xmlContentPath> " +
                "--arcfile <arcFilePath> " +
                "--recover " +
                "--verifyTapeIndex");
    }
    
    public static void main(String[]  args) {
        Hashtable parahash = new Hashtable();
        AdoreRegistryChecker c = null;
        boolean recover = false;
        boolean verifyTapeIndex = false;
        String config = null, profile = null, tape = null, arc = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(0);
            }
            if (arg.startsWith("--recover"))
                recover = true;
            else if (arg.startsWith("--verifyTapeIndex"))
                verifyTapeIndex = true;
            else if (arg.startsWith("--wait")) {
    			try {
    				// This is a test
    				Thread.sleep(2000);
    			} catch (InterruptedException e1) {
    				e1.printStackTrace();
    			}
            }
            else if (arg.startsWith("--"))
                parahash.put(arg.substring(2), args[++i]);
        }
        if (parahash.get("config") != null)
            config = (String) (parahash.get("config"));
        if (parahash.get("profile") != null)
            profile = (String) parahash.get("profile");
        if (parahash.get("xmltape") != null)
            tape = (String) parahash.get("xmltape");
        if (parahash.get("arcfile") != null)
            arc = (String) parahash.get("arcfile");
        
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(new File(config)));
            c = new AdoreRegistryChecker(p);
            if (recover)
               c.setRecoveryProfile(profile);
            if (verifyTapeIndex) {
                c.setRecoveryProfile(profile);
                c.setVerifyTapeIndex(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (tape != null) {
            if (tape.startsWith("file://")) {
                try {
                    tape = new URI(tape).getPath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            c.setTapes(FileUtil.getFileList(tape, new XMLFileFilter(), false));
        } if (arc != null) {
            if (arc.startsWith("file://")) {
                try {
                    arc = new URI(arc).getPath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            c.setArcs(FileUtil.getFileList(arc, new ARCFileFilter(), false));
        }
        c.verify();
    }
    
    public void setVerifyTapeIndex(boolean verify) {
        this.verifyTapeIndexMode = verify;
    }
    
    public void setRecoveryProfile(String profile) throws Exception {
        this.recoveryMode = true;
        this.profile = profile;
        setArchiveProfile(profile);
    }

    public boolean recoverTape(File file, int level) throws Exception {
        String xmlTapePath = file.getAbsolutePath();
        String xmlTape = getFilenameMinusExt(file.getName());
        int extent = 0;
        
        // Read XMLtape Admin Information
        SeqTapeReader reader;
        String tapeAdmin = null;
        ArrayList<String> arcIds;
        try {
            log.info("Reading XMLtape Admin Information: " + xmlTape);
            reader = new SeqTapeReader(xmlTapePath);
            reader.open();
            tapeAdmin = reader.getTapeAdminElement();
            TapeAdmin ta = TapeAdmin.read(new InputSource(new ByteArrayInputStream(tapeAdmin.getBytes("UTF-8"))));
            arcIds = ta.getArcFileIds();
            reader.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        
        if (level == 1) {
            // rc 2007-10-03: Delete Existing Index
            String idxStore = archiveConfig.getTapeIndexDirectory();
            if (archiveProfile.getCollectionTapeIndexDirectory() != null)
                idxStore = archiveProfile.getCollectionTapeIndexDirectory();
            File idx = new File(idxStore);
            if (idx.exists())
                idx.delete();
            
            // Index Current XMLtape
            try {
                log.info("Indexing XMLtape: " + xmlTape);
                extent = tw.indexTapeFile(profile, xmlTape);
                tw.indexTapeIdentifiers(profile, xmlTape);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
            
            // Register Current XMLtape
            try {
                log.info("Registering XMLtape: " + xmlTape);
                tw.registerTapeFile(profile, xmlTape, tapeAdmin);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            log.info("Get extent from existing index: " + xmlTape);
            String idxDirRoot = archiveProfile.getCollectionTapeIndexDirectory();
            if (idxDirRoot == null)
                idxDirRoot = archiveConfig.getTapeIndexDirectory();
            String idxDir = new File(idxDirRoot, xmlTape).getAbsolutePath() + ".idx";
            log.info("Existing Index Location: " + idxDir);
            BDBIndex indexdb = new BDBIndex(idxDir, xmlTape);
            indexdb.open(true);
            extent = (int) indexdb.count(null);
            indexdb.close();
        }
        
        log.info("Creating ArchiveCollection for: " + xmlTape);
        ArchiveCollection ac = new ArchiveCollection();
        ac.setCollectionType(ArchiveCollection.TYPE_TAPE);
        ac.setCollectionPrefix(profile);
        ac.setCollectionCreated(new Date());
        ac.setCollectionFullname(archiveProfile.getCollectionProfileFullName());
        ac.setCollectionRawId(xmlTape);
        ac.setCollectionUri(TapeProperties.getLocalXmlTapePrefix() + xmlTape);
        if (extent > 0)
            ac.setCollectionExtent(extent);
        if (arcIds.size() > 0)
            ac.setCollectionAssociations(arcIds);
        
        if (level < 3) {
            log.info("Registering " + ac.getCollectionUri() + " with Service Registry & ID Locator");
            repoProcessor.processXMLtapeCollection(ac);
        } else {
            log.info("Registering " + ac.getCollectionUri() + " with ID Locator");
            repoProcessor.processIdLocator(ac);
        }
        
        return true;
    }
    
    public boolean recoverArc(File file, int level) throws Exception {
        String arcFilePath = file.getAbsolutePath();
        String arcFile = getFilenameMinusExt(file.getName());
        String collection = archiveProfile.getCollectionProfilePrefix();
        int extent = 0;
        
        if (level == 1) {
            // Index Current ARCfile
            try {
                log.info("Indexing ARCfile: " + arcFile);
                extent = aw.indexARCFile(collection, arcFile);
            } catch (ArchiveException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
            
            // Register Current ARCfile
            try {
                log.info("Registering ARCfile: " + arcFile);
                aw.registerARCFile(collection, arcFile);
            } catch (ArchiveException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            log.info("Get extent from existing index: " + arcFilePath);
            ARCFileReader arcReader = new ARCFileReader(arcFilePath, true);
            extent = arcReader.getCdxInstance().size();
        }
        
        log.info("Creating ArchiveCollection for: " + arcFile);
        ArchiveCollection ac = new ArchiveCollection();
        ac.setCollectionType(ArchiveCollection.TYPE_ARC);
        ac.setCollectionPrefix(collection);
        ac.setCollectionCreated(new Date());
        ac.setCollectionFullname(archiveProfile.getCollectionProfileFullName());
        ac.setCollectionRawId(arcFile);
        ac.setCollectionUri(ARCProperties.getLocalArcFilePrefix() + arcFile);
        if (extent > 0)
            ac.setCollectionExtent(extent);
        
        if (level < 3) {
            log.info("Registering " + ac.getCollectionUri() + " with Service Registry & ID Locator");
            repoProcessor.processArcCollection(ac);
        } else {
            log.info("Registering " + ac.getCollectionUri() + " with ID Locator");
            repoProcessor.processIdLocator(ac);
        }
        
        return true;
    }
    
    public boolean verifyTape(File f) {
        String name = f.getName();
        name = name.substring(0, name.lastIndexOf("."));
        if (!name.contains("/"))
            name = archiveConfig.getLocalXmlTapePrefix() + name;
        try {
            // Check XMLtape Registry
            ArrayList results = getOAIPMHRecord(name, "tape");
            if (results.size() == 0) {
                if (recoveryMode) {
                    recoverTape(f, 1);
                    System.out.println("SUCCESS   " + name);
                    return true;
                } else {
                    System.out.println("ERROR TAPEREG " + name);
                    return false;
                }
            }
            
            // Check XMLtape Index (i.e. verifyTapeIndexMode)
            if (verifyTapeIndexMode) {
                String xmlTape = getFilenameMinusExt(f.getName());
                String idxDirRoot = archiveProfile.getCollectionTapeIndexDirectory();
                if (idxDirRoot == null)
                    idxDirRoot = archiveConfig.getTapeIndexDirectory();
                String idxDir = new File(idxDirRoot, xmlTape).getAbsolutePath() + ".idx";
                log.debug("Verifying Index: " + idxDir);
                BDBIndex indexdb = new BDBIndex(idxDir, xmlTape);
                indexdb.open(true);
                if (!indexdb.isValid()) {
                    System.out.println("ERROR TAPEIDX " + name);
                    return false;
                }
                indexdb.close();
                gov.lanl.xmltape.identifier.index.bdbImpl.BDBIndex idIndexdb;
                idIndexdb = new gov.lanl.xmltape.identifier.index.bdbImpl.BDBIndex();
                idIndexdb.setIndexDir(idxDir);
                idIndexdb.open(true);
                if (!idIndexdb.isValid()) {
                    System.out.println("ERROR TAPEIDIDX " + name);
                    return false;
                }
                idIndexdb.close();
            }
            
            // Check Service Registry
            results = getOAIPMHRecord(name, "oai_iesr");
            if (results.size() == 0) {
                if (recoveryMode) {
                    recoverTape(f, 2);
                    System.out.println("SUCCESS   " + name);
                    return true;
                } else {
                    System.out.println("ERROR SVCREG " + name);
                    return false;
                }
            }
            // Check IDLocator
            if (!dbCleaner.isValid(name)) {
                if (recoveryMode) {
                    recoverTape(f, 3);
                    System.out.println("SUCCESS   " + name);
                    return true;
                } else {
                    System.out.println("ERROR IDLOC " + name);
                    return false;
                }
            }
            
           System.out.println("SUCCESS   " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean verifyArc(File f) {
        String name = f.getName();
        name = name.substring(0, name.lastIndexOf("."));
        if (!name.contains("/"))
            name = archiveConfig.getLocalArcFilePrefix() + name;
        try {
            // Check XMLtape Registry
            ArrayList results = getOAIPMHRecord(name, "arc");
            if (results.size() == 0) {
                if (recoveryMode) {
                    recoverArc(f, 1);
                    System.out.println("SUCCESS   " + name);
                    return true;
                } else {
                    System.out.println("ERROR ARCREG " + name);
                    return false;
                }
            }
            // Check Service Registry
            results = getOAIPMHRecord(name, "oai_iesr");
            if (results.size() == 0) {
                if (recoveryMode) {
                    recoverArc(f, 2);
                    System.out.println("SUCCESS   " + name);
                    return true;
                } else {
                    System.out.println("ERROR SVCREG " + name);
                    return false;
                }
            }
            // Check IDLocator
            if (!dbCleaner.isValid(name)) {
                if (recoveryMode) {
                    recoverArc(f, 3);
                    System.out.println("SUCCESS   " + name);
                    return true;
                } else {
                    System.out.println("ERROR IDLOC " + name);
                    return false;
                }
            }
            System.out.println("SUCCESS   " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public void verify() {        
        ExecutorService executor =  Executors.newFixedThreadPool(archiveConfig.getSystemThreadCount());
        ArrayList<FutureTask> tasks = new ArrayList<FutureTask>();
        // Index & Register XMLTapes
        for (File f : getTapes()) {
            FutureTask<String> future = new FutureTask<String>(new AdoreRegistryThread(f, "xmltape"), null);
            tasks.add(future);
            executor.execute(future);
        }
        for (File f : getArcs()) {
            FutureTask<String> future = new FutureTask<String>(new AdoreRegistryThread(f, "arc"), null);
            tasks.add(future);
            executor.execute(future);
        }
        executor.shutdown();
    }

    public ArrayList<File> getArcs() {
        return arcs;
    }

    public void setArcs(ArrayList<File> arcs) {
        this.arcs = arcs;
    }

    public ArrayList<File> getTapes() {
        return tapes;
    }

    public void setTapes(ArrayList<File> tapes) {
        this.tapes = tapes;
    }

    class AdoreRegistryThread implements Runnable {
        File file;
        String type;
        public AdoreRegistryThread(File file, String type) {
            this.file = file;
            this.type = type;
        }
        
        public void run() {
            boolean ok = false;
            if (type.equals("xmltape")) {
                ok = verifyTape(file);
            } else if (type.equals("arc")){
                ok = verifyArc(file);
            }
            if (ok) {
                log.debug("Processed " + file.getAbsolutePath());
            }
        }
    }
    
    public ArrayList getOAIPMHRecord(String id, String type) throws Exception {
        String baseUrl = null;
        if (type.equals("tape"))
            baseUrl = archiveConfig.getTapeRegistryOAIURL();
        if (type.equals("arc"))
            baseUrl = archiveConfig.getARCRegistryOAIURL();
        if (type.equals("oai_iesr"))
            baseUrl = repoConfig.getServiceRegistryOAIURL();
        if (baseUrl == null || type == null)
            throw new Exception("Unable to determine baseUrl");
        GetRecord r = new GetRecord(baseUrl, id, type);
        //System.out.println(baseUrl);
        //System.out.println(r.getResponseXML());
        return r.getRecords();
    }
    
    /**
     * Provide an absolute file path, will return filename minus dir path
     * and extension
     * @param name
     *        Absolute path of file to extract filename from.
     * @return
     *        Filename minus extension
     */
    public static String getFilenameMinusExt(String name) {
        int j = name.lastIndexOf(File.separator);
        name = name.substring(j + 1);
        int jj = name.lastIndexOf(".");
        name = name.substring(0, jj);
        return name;
    }
}         