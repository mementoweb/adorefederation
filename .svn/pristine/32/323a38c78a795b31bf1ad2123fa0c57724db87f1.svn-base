package gov.lanl.adore.diag;

import gov.lanl.archive.ArchiveConfig;
import gov.lanl.archive.ArchiveProfile;
import gov.lanl.archive.util.FileUtil;
import gov.lanl.archive.util.XMLFileFilter;
import gov.lanl.archive.wrappers.TapeWrapper;
import gov.lanl.repo.oaidb.srv.PutPostClient;
import gov.lanl.util.oai.oaiharvesterwrapper.GetRecord;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeAdmin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class AdoreXMLtapeRegistryChecker {
    static Logger log = Logger.getLogger(AdoreXMLtapeRegistryChecker.class.getName());
    private ArchiveConfig archiveConfig;
    private ArrayList<File> tapes = new ArrayList<File>();
    private String profile;
    private TapeWrapper tw;
    private ArchiveProfile archiveProfile;

    public AdoreXMLtapeRegistryChecker(Properties props) throws Exception {
        try {
            this.archiveConfig = new ArchiveConfig(props);
            this.tw = new TapeWrapper(props);
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
                "--xmltape <xmlContentPath>");
    }
    
    public static void main(String[]  args) {
        Hashtable parahash = new Hashtable();
        AdoreXMLtapeRegistryChecker c = null;
        boolean recover = true;
        String config = null, profile = null, tape = null, arc = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(0);
            }
            if (arg.startsWith("--"))
                parahash.put(arg.substring(2), args[++i]);
        }
        if (parahash.get("config") != null)
            config = (String) (parahash.get("config"));
        if (parahash.get("profile") != null)
            profile = (String) parahash.get("profile");
        if (parahash.get("xmltape") != null)
            tape = (String) parahash.get("xmltape");
        
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(new File(config)));
            c = new AdoreXMLtapeRegistryChecker(p);
            if (recover)
               c.setRecoveryProfile(profile);
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
        }
        c.verify();
    }
    
    public void setRecoveryProfile(String profile) throws Exception {
        this.profile = profile;
        setArchiveProfile(profile);
    }

    public boolean registerTape(File file) throws Exception {
        String xmlTapePath = file.getAbsolutePath();
        String xmlTape = getFilenameMinusExt(file.getName());
        int extent = 0;
        TapeAdmin ta;
        
        // Read XMLtape Admin Information
        SeqTapeReader reader;
        String tapeAdmin = null;
        ArrayList<String> arcIds;
        try {
            log.info("Reading XMLtape Admin Information: " + xmlTape);
            reader = new SeqTapeReader(xmlTapePath);
            reader.open();
            tapeAdmin = reader.getTapeAdminElement();
            ta = TapeAdmin.read(new InputSource(new ByteArrayInputStream(tapeAdmin.getBytes("UTF-8"))));
            arcIds = ta.getArcFileIds();
            reader.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        
        // Clean-out any existing XMLtape Registry Entry
        PutPostClient client = new PutPostClient();
        String result = client.execDelete(archiveConfig.getTapeRegistryPutRecordURL(), ta.getXmlTapeId());
        System.out.println("Deletion Response for " + ta.getXmlTapeId() + " : " + result);
        
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
        executor.shutdown();
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
                try {
					ok = registerTape(file);
				} catch (Exception e) {
					log.error("Error processing: " + file.getAbsolutePath());
					e.printStackTrace();
				}
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