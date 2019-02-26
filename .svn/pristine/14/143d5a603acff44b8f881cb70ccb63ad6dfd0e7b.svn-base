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

import gov.lanl.arc.ARCProperties;
import gov.lanl.archive.util.ARCFileFilter;
import gov.lanl.archive.util.FileUtil;
import gov.lanl.archive.util.XMLFileFilter;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeAdmin;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.create.TapeCreate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

/**
 * Wrapper class for AdoreArchive ingestion process.  Implements specified
 * processing plug-in to pre-process XMLtapes & ARCfiles.  Upon pre-processing
 * completion, a list of arcfiles and xmltapes are provided to this class for
 * indexing and registration is the adore archive enviroment.
 * <br>
 * Example: <br>
 * AdoreArchive archive = new AdoreArchive();<br>
 * archive.setArchiveConfigurations(configFile);<br>
 * archive.setArchiveProfile(profile);<br>
 * archive.setXmlTapeSource(AdoreArchive.getFileList(tapePath, new XMLFileFilter()));<br>
 * archive.setArcFileSource(AdoreArchive.getFileList(arcPath, new ARCFileFilter()));<br>
 * archive.setProcessor(prclass);<br>
 * archive.processSources();<br>
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 *
 */
public class AdoreArchive {
    
    static Logger log = Logger.getLogger(AdoreArchive.class.getName());
    private ArchiveConfig archiveConfig = null;
    private ProfileProcessor archiveProcessor = null;
    private ArchiveProfile archiveProfile = null;
    private String configFile = null;
    
    private List<String> errors = Collections.synchronizedList(new ArrayList<String>());
    private ArchiveRegister register;
    private boolean noregister = false;
    private List<String> success = Collections.synchronizedList(new ArrayList<String>());
    private ArrayList<File> xmlTapeSource;
    private ArrayList<File> arcFileSource;
    private List<ArchiveCollection> collections = Collections.synchronizedList(new ArrayList<ArchiveCollection>());
    
    /**
     * Main Method - Parses command line args<br>
     * 
     * Expects the following args:<br>
     *   --config [archiveProp]<br>
     *        Path to archive.properties file [REQUIRED]<br>
     *   --profile [collectionPrefix]<br>
     *        Profile prefix as defined in archive properties file [REQUIRED]<br>
     *   --xmltape [xmlContentPath] or - for stdin <br>
     *        Path to dir/file to process [OPTIONAL]<br>
     *   --arcfile [arcFilePath]<br>
     *        Path to dir/file to process [OPTIONAL]<br>
     *   --recursive [OPTIONAL]<br>
     *        Recursively search directory to get list of --xml || --arcfile source files
     *   --noregister [OPTIONAL]<br>
     *        Register XMLtapes and ARCfiles
     *   --createTape [OPTIONAL]
     *        Create tape from stdin 
     *   --stdInTapeName [OPTIONAL]
     *        Name of tape to be created
     * 
     * @param argv
     *        String Array containing processing configurations
     */
    public static void main(String[] argv) {
        if (argv.length == 0)
            printUsage();
        
        AdoreArchive archive;
        try {
            archive = AdoreArchive.getAdoreArchive(argv);
            archive.processSources();
            archive.printStatus();
        } catch (ArchiveException e) {
            e.printStackTrace();
        }
    }
    
    private static void printUsage() {
        System.out.println("Usage: " +
                "--config [archiveProp] " +
                "--profile [collectionPrefix] " +
                "--xmltape <xmlContentPath> or - for stdin " +
                "--arcfile <arcFilePath> " +
                "--createTape - create tape from stdin " +
                "--stdInTapeName - name of tape to be created " +
                "--noregister - disable registration");
    }
    
    /**
     * Provided an Arc file object, will index and register object for
     * use in Adore Archive, then add ArchiveCollection objects for new
     * repository.
     * @param file
     *        File object defining location of arc file to process
     * @return
     *        boolean indicating success or failure
     */
    protected boolean processArcFile(File file) {
        String arcFilePath = file.getAbsolutePath();
        String arcFile = getFilenameMinusExt(file.getName());
        String collection = archiveProfile.getCollectionProfilePrefix();
        int extent = 0;
        
        // Index Current ARCfile
        try {
            log.info("Indexing ARCfile: " + arcFile);
            extent = register.indexARCFile(collection, arcFile);
        } catch (ArchiveException e) {
            log.error(e.getMessage());
            errors.add(arcFilePath);
            return false;
        }
        
        // Register Current ARCfile
        try {
            log.info("Registering ARCfile: " + arcFile);
            register.registerARCFile(collection, arcFile);
        } catch (ArchiveException e) {
            log.error(e.getMessage());
            errors.add(arcFilePath);
            return false;
        }
        
        ArchiveCollection ac = new ArchiveCollection();
        ac.setCollectionType(ArchiveCollection.TYPE_ARC);
        ac.setCollectionPrefix(collection);
        ac.setCollectionCreated(new Date());
        ac.setCollectionFullname(archiveProfile.getCollectionProfileFullName());
        ac.setCollectionRawId(arcFile);
        ac.setCollectionUri(ARCProperties.getLocalArcFilePrefix() + arcFile);
        if (extent > 0)
            ac.setCollectionExtent(extent);
        log.info("Adding " + ac.getCollectionUri() + " to ArchiveCollection list");
        collections.add(ac);
        
        return true;
    }
    
    /**
     * Provided an XMLtape file object, will index and register object for
     * use in Adore Archive
     * @param file
     *        File object defining location of xmlTape file to process
     * @return
     *        boolean indicating success or failure
     */
    protected boolean processXMLtape(File file) {
        String xmlTapePath = file.getAbsolutePath();
        String xmlTape = getFilenameMinusExt(file.getName());
        String collection = archiveProfile.getCollectionProfilePrefix();
        int extent = 0;
        
        // Index Current XMLtape
        try {
            log.info("Indexing XMLtape: " + xmlTape);
            extent = register.indexTape(collection, xmlTape);
            register.indexIdentifiers(collection, xmlTape);
        } catch (ArchiveException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            errors.add(xmlTapePath);
            return false;
        }
        
        // Read XMLtape Admin Information
        SeqTapeReader reader;
        String tapeAdmin = null;
        ArrayList<String> arcIds;
        try {
            log.info("Reading XMLtape Admin Information");
            reader = new SeqTapeReader(xmlTapePath);
            reader.open();
            tapeAdmin = reader.getTapeAdminElement();
            TapeAdmin ta = TapeAdmin.read(new InputSource(new ByteArrayInputStream(tapeAdmin.getBytes("UTF-8"))));
            arcIds = ta.getArcFileIds();
            reader.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            errors.add(xmlTapePath);
            return false;
        }

        // Register Current XMLtape
        try {
            log.info("Registering XMLtape: " + xmlTape);
            register.registerTape(collection, xmlTape, tapeAdmin);
        } catch (ArchiveException e) {
            log.error(e.getMessage());
            errors.add(xmlTapePath);
            return false;
        }
        
        ArchiveCollection ac = new ArchiveCollection();
        ac.setCollectionType(ArchiveCollection.TYPE_TAPE);
        ac.setCollectionPrefix(collection);
        ac.setCollectionCreated(new Date());
        ac.setCollectionFullname(archiveProfile.getCollectionProfileFullName());
        ac.setCollectionRawId(xmlTape);
        ac.setCollectionUri(TapeProperties.getLocalXmlTapePrefix() + xmlTape);
        if (extent > 0)
            ac.setCollectionExtent(extent);
        if (arcIds.size() > 0)
            ac.setCollectionAssociations(arcIds);
        log.info("Adding " + ac.getCollectionUri() + " to ArchiveCollection list");
        collections.add(ac);
        
        return true;
    }
    
    /**
     * Deprecated, use processSources()
     * @deprecated 
     */
    public void runIt() throws ArchiveException {
        processSources();
    }
    
    /**
     * Sets and calls pre-processing plug-in, iterates through provided XMLtapes
     * and ARCfiles to index and register resulting output in the Adore Archive.
     * @throws ArchiveException
     */
    public void processSources() throws ArchiveException {
        if (archiveProcessor == null)
            throw new ArchiveException(
                    "Profile Process has not been initialized.");

        // Initialize Local XMLtape and ARCfile Props
        TapeProperties.load(archiveConfig.getProperties());
        ARCProperties.load(archiveConfig.getProperties());
        
        // Initialize & Process Source Content
        try {
            if (xmlTapeSource != null && !xmlTapeSource.isEmpty())
                archiveProcessor.setSourceArray(ArchiveConstants.TYPE_XML, xmlTapeSource);
            if (arcFileSource != null && !arcFileSource.isEmpty())
                archiveProcessor.setSourceArray(ArchiveConstants.TYPE_BINARY, arcFileSource);
            archiveProcessor.setArchiveConfig(archiveConfig);
            archiveProcessor.setArchiveProfile(archiveProfile);
            archiveProcessor.runIt();
        } catch (Exception e) {
            log.error("Error occurred during processing: " + e.getMessage());
            throw new ArchiveException("Error Occurred During Processing: ", e);
        }

        
        ArrayList<File> xmlTapes = archiveProcessor.getXMLTapeList();
        ArrayList<File> arcFiles = archiveProcessor.getArcFileList();
        
        register = new ArchiveRegister(archiveConfig);
        
        if (!noregister) {
			ExecutorService executor = Executors.newFixedThreadPool(archiveConfig.getSystemThreadCount());
			ArrayList<FutureTask> tasks = new ArrayList<FutureTask>();
			// Index & Register XMLTapes
			for (Iterator i = xmlTapes.iterator(); i.hasNext();) {
				FutureTask<String> future = new FutureTask<String>(new AdoreArchiveThread((File) i.next(), "xmltape"),null);
				tasks.add(future);
				executor.execute(future);
			}

			// Index & Register ArcFiles
			for (Iterator i = arcFiles.iterator(); i.hasNext();) {
				FutureTask<String> future = new FutureTask<String>(new AdoreArchiveThread((File) i.next(), "arc"), null);
				tasks.add(future);
				executor.execute(future);
			}

			try {
				executor.shutdown();
				while (!isDone(tasks)) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				log.error("An error occurred during thread interruption: "+ e.getMessage());
			}
		}
    }
    
    private static boolean isDone(ArrayList<FutureTask> list) {
        for (FutureTask ft : list) {
           if (!ft.isDone()) 
               return false;
        }
        return true;
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
    
    /**
     * Gets the current Archive Configuration object; use getProperties() on
     * returned object to get all properties defined in source archive.properties.
     * @return
     *       Configuration object containing env and profile properties.
     */
    public ArchiveConfig getArchiveConfigurations() {
        return archiveConfig;
    }
    
    /**
     * Gets the Archive Profile specifying the current pre-processing profile
     * and collection information.
     * @return
     *       Configuration object containing pre-processing properties.
     */
    public ArchiveProfile getProfile() {
        return archiveProfile;
    }

    /**
     * Sets the Archive Configuration object. 
     * @param archiveConfigFile
     *         Path to archive.properties file
     */
    public void setArchiveConfigurations(String archiveConfigFile) {
        File cfile = new File(archiveConfigFile);
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(cfile));
            archiveConfig = new ArchiveConfig(props);
        } catch (FileNotFoundException e) {
            log.error("Archive Properties File was not found at " + configFile);
        } catch (IOException e) {
            log.error("Error reading file from " + configFile);
        } catch (ArchiveException e) {
            log.error("Unable to validate " + configFile);
            log.error(e);
        }
    }

    /**
     * Sets the Archive Profile object; provided prefix must 
     * exist in archive.properties.
     * @param profilePrefix
     *         Profile prefix which exists in archive.properties file
     * @throws ArchiveException 
     */
    public void setArchiveProfile(String profilePrefix) throws ArchiveException {
        archiveProfile = new ArchiveProfile(profilePrefix); 
        // Set the Mandatory Properties, use default if missing
        String fullName = archiveConfig.getCollectionFullName(profilePrefix);
        if (fullName == null || fullName.trim().equals("")) {
            log.warn(profilePrefix + ".FullName is not defined; using default");
            fullName = archiveConfig.getCollectionFullName("default");
            if (fullName == null || fullName.trim().equals(""))
                throw new ArchiveException("default.FullName is not defined");
            archiveConfig.properties.put(profilePrefix + ".FullName", fullName);
        }
        String pmhDefaultProp = archiveConfig.getCollection_pmh_defaultproperty(profilePrefix);
        if (pmhDefaultProp == null || pmhDefaultProp.trim().equals("")) {
            log.warn(profilePrefix + ".pmh-defaultproperty is not defined; using default");
            pmhDefaultProp = archiveConfig.getCollection_pmh_defaultproperty("default");
            if (pmhDefaultProp == null || pmhDefaultProp.trim().equals(""))
                throw new ArchiveException("default.pmh-defaultproperty is not defined");
            archiveConfig.properties.put(profilePrefix + ".pmh-defaultproperty", pmhDefaultProp);
        }
        String idIdxRecordImpl = archiveConfig.getCollectionIdentifierIndexRecordPlugin(profilePrefix);
        if (idIdxRecordImpl == null || idIdxRecordImpl.trim().equals("")) {
            log.warn(profilePrefix + ".identifierIdxRecordPlugin is not defined; using default");
            idIdxRecordImpl = archiveConfig.getCollectionIdentifierIndexRecordPlugin("default");
            if (idIdxRecordImpl == null || idIdxRecordImpl.trim().equals(""))
                throw new ArchiveException("default.identifierIdxRecordPlugin is not defined");
            archiveConfig.properties.put(profilePrefix + ".identifierIdxRecordPlugin", idIdxRecordImpl);
        }
        // Set collection xmltape and arc dirs
        String tapeDir = archiveConfig.getCollectionTapeStoreDirectory(profilePrefix);
        String idxDir = archiveConfig.getCollectionTapeIndexDirectory(profilePrefix);
        String arcDir = archiveConfig.getCollectionArcStoreDirectory(profilePrefix);
        // Set the Mandatory Properties, throw exception if missing
        String processorClass = archiveConfig.getCollectionProcessor(profilePrefix).trim();
        if (processorClass == null || processorClass.trim().equals(""))
            throw new ArchiveException(profilePrefix + ".processorClass is not defined");
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

    /**
     * Sets the Processor Class for pre-processing.
     * @param apclass
     *         Full classpath for pre-processor
     * @throws ArchiveException
     */
    public void setProcessor(String apclass) throws ArchiveException {
        try {
            this.archiveProcessor = (ProfileProcessor) Class.forName(apclass).newInstance();
        } catch (InstantiationException e) {
            throw new ArchiveException("InstantiationException for " + apclass, e);
        } catch (IllegalAccessException e) {
            throw new ArchiveException("IllegalAccessException for" + apclass, e);
        } catch (ClassNotFoundException e) {
            throw new ArchiveException("ClassNotFoundException for " + apclass, e);
        }
    }
    
    /**
     * Gets an initialized AdoreArchive instance from previously parsed args
     */
    public static AdoreArchive getAdoreArchive(String config, String profile,
            String tape, String arc, boolean recursive) throws ArchiveException {
        AdoreArchive archive = new AdoreArchive();

        archive.setArchiveConfigurations(config);
        archive.setArchiveProfile(profile);
        archive.setProcessor(archive.getProfile()
                .getCollectionProcessorPlugin());
        if (tape != null && new File(tape).exists())
            archive.setXmlTapeSource(FileUtil.getFileList(tape,
                    new XMLFileFilter(), recursive));

        if (arc != null && new File(arc).exists())
            archive.setArcFileSource(FileUtil.getFileList(arc,
                    new ARCFileFilter(), recursive));

        return archive;
    }
    
    /**
     * Gets an initialized AdoreArchive instance from command-line args
     * @param argv
     *         String[] of Command-line Args (see main() method)
     * @return
     *        an AdoreArchive instance initialized from command-line args
     * @throws ArchiveException
     */
    public static AdoreArchive getAdoreArchive(String[] argv) throws ArchiveException {
        Hashtable parahash = new Hashtable();
        boolean recursive = false;
        boolean createTape = false;
        boolean noregister = false;
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(0);
            }
            if (arg.startsWith("--recursive")) {
                recursive = true;
            } else if (arg.startsWith("--createTape")) {
                createTape = true;
            } else if (arg.startsWith("--noregister")) {
                noregister = true;
            }else if (arg.startsWith("--"))
                parahash.put(arg.substring(2), argv[++i]);
        }
        if (parahash.get("profile") == null || parahash.get("config") == null) {
            printUsage();
            System.exit(0);
        }

        String config = null, profile = null, tape = null, arc = null;
        if (parahash.get("config") != null)
            config = (String) (parahash.get("config"));
        if (parahash.get("profile") != null)
            profile = (String) parahash.get("profile");
        if (parahash.get("xmltape") != null)
            tape = (String) parahash.get("xmltape");
        if (parahash.get("arcfile") != null)
            arc = (String) parahash.get("arcfile");
        
        AdoreArchive archive = getAdoreArchive(config, profile, tape, arc, recursive);
        
        // Check to noregister request
        if (noregister)
        	archive.setDisableRegistration(noregister);
        
        // Check for xml stream from stdin
        if (tape != null && createTape && tape.equals("-")) {
            ArchiveConfig c = archive.getArchiveConfigurations();
            // Get tape name
            String file = null;
            if (parahash.get("stdInTapeName") != null) {
                file = (String) parahash.get("stdInTapeName");
                file = file.endsWith(".xml") ? file : file + ".xml";
            } else {
                file = profile + "_" + UUIDFactory.generateUUID().getNudeId() + ".xml";
            }
            log.info("Creating XMLtape, " + file + ", from stdin");
            // Set-up output
            String tmpDir = c.getSystemTmp();
            String out;
            if (tmpDir != null)
                out = new File(c.getSystemTmp(), file).getAbsolutePath();
            else {
                try {        
                    out = File.createTempFile("tmpTapeCreate", null).getAbsolutePath();
                } catch (IOException e) {
                    System.err.println("Error creating tmpDir: " + e.getMessage());
                    throw new ArchiveException(e);
                }
            }
            // Set-up props
            String prop = profile + ".TapeCreateProps";
            String props = c.getProperties().getProperty(prop);
            if (props == null) {
                System.out.println("Required property, " + prop + ", is not defined.");
                System.exit(0);
            }             
            // Init the tape create impl
            TapeCreate tc = new TapeCreate(tape, out, props);
            // Override default tmp dir?
            if (tmpDir != null)
                tc.setTmpDir(new File(tmpDir));
            try {
                tc.createTape();
            } catch (Exception e) {
                System.err.println("Error creating tape from stdin; unable to continue");
                e.printStackTrace();
                System.exit(1);
            }
            ArrayList<File> files = new ArrayList<File>();
            log.info("Adding " + out + " to XMLtape Processing Queue");
            files.add(new File(out));
            archive.setXmlTapeSource(files);
            
            // Ensure clean-up
            new File(out).deleteOnExit();
        } else if (tape != null && tape.equals("-")) {
            File f = new File("/dev/stdin");
            
            if (parahash.get("stdInTapeName") != null) {
                String d = (String) parahash.get("stdInTapeName");
                d = d.endsWith(".xml") ? d : d + ".xml";
                File dir = new File(archive.getArchiveConfigurations().getSystemTmp());
                if (!dir.exists())
                    dir.mkdirs();
                File dest = new File(dir, d);
                log.debug("Output stdIn to " + dest.getAbsolutePath());
                if (copyFile(f, dest))
                    f = dest;
            }
            
            ArrayList<File> files = new ArrayList<File>();
            files.add(f);
            archive.setXmlTapeSource(files);
            
            // Ensure clean-up
            f.deleteOnExit();
        }
        
        return archive;
    }
    
    private static boolean copyFile(File src, File dest) {
        InputStream in = null;
        OutputStream out = null;
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        try {
            in = new BufferedInputStream(new FileInputStream(src));
            out = new BufferedOutputStream(new FileOutputStream(dest));
            buf = new byte[bufLen];
            byte[] tmp = null;
            int len = 0;
            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                out.write(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception e) {
                }
            if (out != null)
                    try {
                        out.close();
                    } catch (Exception e) {
                    }
        }
        return true;
    }
    
    /**
     * Sets the ArrayList of ARCfile File objects for indexing & registration
     * @param arcFileSource
     *         List of ARCfile objects for indexing and registration
     */
    public void setArcFileSource(ArrayList<File> arcFileSource) {
        this.arcFileSource = arcFileSource;
    }

    /**
     * Disables registration of XMLtapes and ARCfiles
     * @param noregister
     *         Disables registration of XMLtape and ARCfiles
     */
    public void setDisableRegistration(boolean noregister) {
        this.noregister = noregister;
    }
    
    /**
     * Sets the ArrayList of XMLtape File objects for indexing & registration
     * @param xmlTapeSource
     *         List of XMLtape objects for indexing and registration
     */
    public void setXmlTapeSource(ArrayList<File> xmlTapeSource) {
        this.xmlTapeSource = xmlTapeSource;
    }

    /**
     * Prints processing status
     */
    private void printStatus() {
    	if (noregister)
    		System.out.println("NOTE: Archive Registration Disabled; XMLtape " +
    				"and/or ARCfile were not registered in repository.");
    	
        if (errors.size() > 0) {
            System.out.println("Errors encountered for the following files: ");
            for (Iterator i = errors.iterator(); i.hasNext();) {
                System.out.println("   " + (String) i.next());
            }
        }
        
        if (success.size() > 0) {
            System.out.println("Successfully processed: ");
            for (Iterator i = success.iterator(); i.hasNext();) {
                System.out.println("   " + (String) i.next());
            }
        }
    }
    
    /**
     * Gets the list of processed collection objects
     */
    public ArrayList<ArchiveCollection> getRegisteredCollections() {
        return new ArrayList<ArchiveCollection>(collections);
    }
    
    class AdoreArchiveThread implements Runnable {
        File file;
        String type;
        public AdoreArchiveThread(File file, String type) {
            this.file = file;
            this.type = type;
        }
        
        public void run() {
            boolean ok = false;
            if (type.equals("xmltape")) {
                ok = processXMLtape(file);
            } else if (type.equals("arc")){
                ok = processArcFile(file);
            }
            if (ok) {
                log.debug("Processed " + file.getAbsolutePath());
                success.add(file.getAbsolutePath());
            }
        }
    }
}
