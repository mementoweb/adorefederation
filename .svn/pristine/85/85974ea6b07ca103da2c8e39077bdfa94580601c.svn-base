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

import gov.lanl.arc.ARCEnvConfig;
import gov.lanl.arc.ARCProperties;
import gov.lanl.arc.heritrixImpl.ARCFileMetadata;
import gov.lanl.arc.heritrixImpl.ARCFileReader;
import gov.lanl.repo.oaidb.arcreg.ARCRegXML;
import gov.lanl.repo.oaidb.arcreg.ARCRegistryConstants;
import gov.lanl.repo.oaidb.srv.PutPostClient;
import gov.lanl.repo.oaidb.srv.PutResponseHandler;
import gov.lanl.util.DigestUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ARCWrapper implements ARCRegistryConstants {
    static Logger log = Logger.getLogger(ARCWrapper.class.getName());

    private static final int ARC_INDEX = 1;
    private static final int ARC_REGISTER = 2;
    
    private ARCEnvConfig config;
    private static String propFile;
    private static String arcFile;
    private static int verb;
    private static String collection = null;
    
    /**
     * Usage gov.lanl.archive.wrappers.ARCWrapper --verb register --propFile [propFile] --arcFile [arcFile]
     * --verb Options: index / register
     * --propFile - location of properties file
     * --arcFile - arcname
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

        ARCWrapper arcRepo = new ARCWrapper(propFile);
        arcFile = getFileNameMinusExt(arcFile);

        if (verb == ARC_INDEX)
          arcRepo.indexARCFile(collection, arcFile);
        
        if (verb == ARC_REGISTER)
          arcRepo.registerARCFile(collection, arcFile);
    }

    
    /**
     * Print Usage for main()
     */
    private static void printUsage() {
        log.warn("Usage gov.lanl.archive.wrappers.ARCWrapper --verb register --propFile <propFile> --arcFile <arcFile> --collection biosis");
        log.warn("--verb Options: index / register");
        log.warn("--propFile - location of properties file");
        log.warn("--arcFile - arcname");
        log.warn("--collection - collection");
        System.exit(0);
    }
    
    /**
     * Construtor - Uses initialized properties object
     * @param prop
     *        Properties used to initialize the ARCWrapper
     * @throws Exception
     */
    public ARCWrapper(Properties prop) throws Exception {
        config = new ARCEnvConfig(prop);
        config.validate();
    }

    /**
     * Construtor - Uses source properties file.
     * @param propfile
     *        Path to properties file containing arc configurations
     * @throws Exception
     */
    public ARCWrapper(String propfile) throws Exception {
        Properties prop = new Properties();
        log.debug("Initializing ARCWrapper from " + propfile);
        prop.load(new FileInputStream(new File(propfile)));
        config = new ARCEnvConfig(prop);
        config.validate();
    }

    /**
     * Index specified ARCfile
     * @param arcname
     *        Name of arc file to be indexed, minus extension
     * @throws Exception
     */
    public int indexARCFile(String collection, String arcname) throws Exception {
        int cnt = 0;
        try {
            log.debug("Indexing ARCfile: " + arcname);
            
            // Determine where the files reside
            String arcDir = config.getARCStoredDirectory();
            if (collection != null && config.getCollectionArcStoreDirectory(collection) != null)
                arcDir = config.getCollectionArcStoreDirectory(collection);
            
            String arcfile = arcDir + File.separator + arcname2file(arcname);
            ARCFileReader afr = new ARCFileReader(arcfile, false);
            afr.generateIndex();
            cnt = afr.getCdxInstance().size() - 1; // Ignore arc file cdx entry
        } catch (Exception ex) {
            log.error("Error in indexARCFile for " + arcname + ": " + ex.getMessage());
            throw new Exception(
                    "error in index arc file " + arcname, ex);
        }
        return cnt;
    }
    
    /**
     * Register ARC file, it's implemented by constructing a property list based
     * on configuration and transfer to arc register program.
     * 
     * @param arcname
     *            name of arc file
     */
    public void registerARCFile(String collection, String arcname) throws Exception {
        try {
            log.debug("Registering ARCfile: " + arcname);
            
            // Determine where the files reside
            String arcDir = config.getARCStoredDirectory();
            if (collection != null && config.getCollectionArcStoreDirectory(collection) != null)
                arcDir = config.getCollectionArcStoreDirectory(collection);
            
            Properties props = new Properties();
            String arcFileID = ARCProperties.getLocalArcFilePrefix() + arcname;
            String fileUriPrefix = "file://";
            String arcFilePath = new File (arcDir, arcname2file(arcname)).getAbsolutePath();
            String digest = DigestUtil.getSHA1Digest(new FileInputStream(arcFilePath));
            digest = DigestUtil.toURN(digest, "sha1");
            log.debug("ARCfile Digest: " + digest);
            String srcArcFilePath = arcFilePath;
            String arcIdxPath = new File (arcDir, arcname2index(arcname)).getAbsolutePath();
            
            // Handles Windows File URI Properly
            if (arcFilePath.charAt(1) == ':') {
            	  fileUriPrefix = "file:///";
            	  arcFilePath = arcFilePath.replace("\\", "/");
            	  arcIdxPath = arcIdxPath.replace("\\", "/");
            }
            
            String arcFilePathURI = fileUriPrefix + arcFilePath;
            String arcIdxPathURI = fileUriPrefix + arcIdxPath;
            String openurlURL = config.getARCResolverURL() + "/" + arcname + "/" + "openurl-aDORe4";
            String arcAdminInfo = new ARCFileMetadata(srcArcFilePath).getArcFileHeader();
            
            log.debug(ELEMENT_ARC_FILE_ID + " = " + arcFileID);
            props.put(ELEMENT_ARC_FILE_ID, arcFileID);
            log.debug(ELEMENT_ARC_FILE_FILEPATH + " = " + arcFilePathURI);
            props.put(ELEMENT_ARC_FILE_FILEPATH, arcFilePathURI);
            log.debug(ELEMENT_ARC_FILE_IDX + " = " + arcIdxPathURI);
            props.put(ELEMENT_ARC_FILE_IDX, arcIdxPathURI);
            log.debug(ELEMENT_OPENURL_URL + " = " + openurlURL);
            props.put(ELEMENT_OPENURL_URL, openurlURL);
            log.debug(ELEMENT_ARC_FILE_ADMIN + " = " + arcAdminInfo);
            props.put(ELEMENT_ARC_FILE_ADMIN, arcAdminInfo);
            log.debug(ELEMENT_ARC_FILE_DIGEST + " = " + digest);
            props.put(ELEMENT_ARC_FILE_DIGEST, digest);
            
            ARCRegXML repo = new ARCRegXML();
            PutPostClient client = new PutPostClient();
            String result = client.execPut(config.getARCRegistryPutRecordURL(), repo.ProptoXML(props));
            log.debug(props.toString());
            log.debug("\n**** ARCfile Registry PutRecord Response ****\n " + result);
            PutResponseHandler pmp = new PutResponseHandler(result);
            if (!pmp.isSuccess())
                throw new Exception(pmp.getError());
        } catch (Exception ex) {
            log.error("Error in registerARCFile for " + arcname + ": " + ex.getMessage());
            throw new Exception(ex.getMessage(), ex);
        }
    }
    
    /**
     * Gets arc filename from arcname
     * @param arcname
     *        Filename without extension suffix
     * @return
     *        Name of arc file    
     */
    private static String arcname2file(String arcname) {
        return arcname + ".arc";
    }

    /**
     * Gets arc index filename from arcname
     * @param arcname
     *        Filename without extension suffix
     * @return
     *        Name of index file    
     */
    private static String arcname2index(String arcname) {
        return arcname + ".cdx";
    }
    
    /**
     * Gets the file name minus extension suffix
     * @param name
     *        Path to file
     * @return
     *        Name of file minus extension 
     */
    public static String getFileNameMinusExt(String name) {
        if (name.contains(File.separator)) {
            int j = name.lastIndexOf("/");
            name = name.substring(j + 1);
        }
        if (name.contains(".")) {
            int jj = name.lastIndexOf(".");
            name = name.substring(0, jj);
        }
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
        if ((ht.get("arcFile") == null)) {
            printUsage();
            System.exit(1);
        }
        if ((ht.get("propFile") == null)) {
            printUsage();
            System.exit(1);
        }        
        else {
            String verbtext = (String) (ht.get("verb"));
            if (verbtext.equals("index"))
                verb = ARC_INDEX;
            else if (verbtext.equals("register"))
                verb = ARC_REGISTER;
            else {
                printUsage();
                System.exit(1);
            }
        }
        if ((ht.get("propFile") != null)) {
            propFile = (String) (ht.get("propFile"));
        }
        if ((ht.get("arcFile") != null)) {
            arcFile = (String) (ht.get("arcFile"));
        }
        if ((ht.get("collection") != null)) {
            collection = (String) (ht.get("collection"));
        }
    }
}
