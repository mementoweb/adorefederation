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

package gov.lanl.archive.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import gov.lanl.arc.ARCProperties;
import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.archive.ArchiveConfig;
import gov.lanl.archive.ArchiveConstants;
import gov.lanl.archive.ArchiveException;
import gov.lanl.archive.ArchiveIO;
import gov.lanl.archive.ArchiveProfile;
import gov.lanl.archive.ProfileProcessor;
import gov.lanl.archive.trans.didl.FragmentProcessor;
import gov.lanl.archive.trans.didl.DidlTransformer;
// MOD bg need these for processDidl
import gov.lanl.archive.trans.didl.SipParser;
// import gov.lanl.util.DateUtil;
// import java.util.Date;
// end MOD
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.SingleTapeWriter;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.TapeRecord;

/**
 * aDORe Archive DIDL Content Pre-processor - Expects a Source Array of XMLtape
 * file objects containing DIDL content in all taperecords. Uses an underlying
 * DidlTransformer implem. to perform the DIDL conversion.
 * 
 */
public class MigrateSipDIDLProcessor implements ProfileProcessor,
        FragmentProcessor {

    static Logger log = Logger.getLogger(DIDLProcessor.class.getName());

    private ArrayList<File> arcFileList = new ArrayList<File>();

    private ArrayList<File> xmlTapeList = new ArrayList<File>();

    private ArrayList<File> sourceXmlTapeList;

    private ArchiveConfig archiveConfig;

    private ArchiveProfile archiveProfile;

    private DidlTransformer transformer;

    private ArchiveIO archiveIO;

    private SingleTapeWriter writer;

    private ARCFileWriter arcw;

    private String arcName;

    private String tapeName;

    // 2007-06-27: Added for tape rollover support
    private String arcDir;
    private String tapeDir;
    private int tapeCount = 0;
    private int maxTapeSize = 2000000000;
    private String rootTapeName;
    
    /**
     * Gets list of ARCfile File Objects for indexing & registration
     * 
     * @return ArrayList of File objects for post-processing
     */
    public ArrayList<File> getArcFileList() {
        return arcFileList;
    }

    /**
     * Gets list of XMLtape File Objects for indexing & registration
     * 
     * @return ArrayList of File objects for post-processing
     */
    public ArrayList<File> getXMLTapeList() {
        return xmlTapeList;
    }

    /**
     * Sets ArchiveConfig Properties
     * 
     * @param config
     *            ArchiveConfig for adore archive
     */
    public void setArchiveConfig(ArchiveConfig config) {
        this.archiveConfig = config;
    }

    /**
     * Sets ArchiveProfile Pre-processing Properties
     * 
     * @param profile
     *            ArchiveProfile for collection pre-processing
     */
    public void setArchiveProfile(ArchiveProfile profile) {
        this.archiveProfile = profile;
    }

    /**
     * Sets list of files to be processed
     * 
     * @param type
     *            Content Type Alias
     * @param sourceFiles
     *            List of files to be preprocessed
     */
    public void setSourceArray(int type, ArrayList sourceFiles) {
        if (type == ArchiveConstants.TYPE_XML) {
            this.sourceXmlTapeList = sourceFiles;
        }
    }

    /**
     * Invoke method to pre-process the DIDL XMLtapes
     */
    public void runIt() throws Exception {

        // Initialize Archive IO, ArcProperties, TapeProperties
        this.archiveIO = new ArchiveIO(archiveConfig);

        // Begin Processing XMLtapes
        for (Iterator i = sourceXmlTapeList.iterator(); i.hasNext();) {
            String sourceFile = ((File) i.next()).getAbsolutePath();
            processXMLtape(sourceFile);
        }
    }

    /**
     * Process source XMLtape, parse & update DIDL DO's, write datastreams to
     * arc file.
     * 
     * @param sourceFile
     *            Absolute path to the XMLfile for processing
     * @return boolean indicating success
     */
    public boolean processXMLtape(String sourceFile) {

        if (sourceFile.equals("/dev/stdin"))
            tapeName = archiveProfile.getCollectionProfilePrefix() + "_"
                    + UUIDFactory.generateUUID().toString().substring(9);
        else
            tapeName = getFileNameMinusExt(sourceFile);

        arcName = UUIDFactory.generateUUID().toString().substring(9);
        try {
            // Get XMLtape & ARCfile Storage Dirs
            tapeDir = archiveConfig.getTapeStoredDirectory();
            arcDir = archiveConfig.getARCStoredDirectory();

            if (archiveProfile.getCollectionTapeStoreDirectory() != null)
                tapeDir = archiveProfile.getCollectionTapeStoreDirectory();
            if (archiveProfile.getCollectionArcStoreDirectory() != null)
                arcDir = archiveProfile.getCollectionArcStoreDirectory();

            // 2007-06-27: Consolidated init methods for tape rollover support
            rootTapeName = tapeName;
            initWriters(tapeName, null);

            // need to change the flow here somewhere -- get the darned sip, then write
            FileInputStream fis = new FileInputStream(new File(sourceFile));
            // MOD bg here - use SipParser instead of Luda's tape parser
            SipParser handler = new SipParser(fis, (FragmentProcessor) this);

            closeWriters();

        } catch (Exception e) {
            log.error("Caught Exception Processing " + tapeName + ": \n"
                    + e.getMessage());
            return false;
        }
        return true;
    }

    public void initWriters(String t, String a) throws ArchiveException  {
        if (t == null)
            tapeName = archiveProfile.getCollectionProfilePrefix() + "_" + UUIDFactory.generateUUID().toString().substring(9);
        else
            tapeName = t;
        if (a == null)
            arcName = UUIDFactory.generateUUID().toString().substring(9);
        // Open XMLtape Writer
        writer = archiveIO.openSingleTapeWriter(tapeName, tapeDir);
        // Open ARC Writer
        arcw = archiveIO.openARCFileWriter(arcName, arcDir);
    }
    
    public void closeWriters() throws IOException  {
        // Prime the TapeAdmin Section
        writer.setXmlTapeID(TapeProperties.getLocalXmlTapePrefix() + tapeName);
        ArrayList<String> arcs = arcw.getArcFileNameList();
        for (String arc : arcs)
            writer.addArcFileID(ARCProperties.getLocalArcFilePrefix() + getFileNameMinusExt(arc));
        writer.writeDefaultAdmin();
        
        // Close tape and arc writers
        writer.close();
        arcw.close();
        
        // Populate XMLtape & ARCfile lists
        xmlTapeList.add(new File(tapeDir, tapeName + ".xml"));
        for (String file : arcw.getArcFileNameList())
           arcFileList.add(new File(arcDir, file));
    }
    
    /**
     * Call-back Method - Implements FragmentProcessor.processDidl()
     */
    public void processDidl(String metadata, String id, String datestamp)
            throws Exception {
        Pattern sipDidlPattern = Pattern.compile(
                "(<didl:DIDL.*?</didl:DIDL>).*?<params>(.*?)</params>",
                Pattern.DOTALL);
        Matcher sipDidlMatcher = sipDidlPattern.matcher(metadata);

        // find DIDL and params
        String params = "";
        if (sipDidlMatcher.find()) {
            metadata = sipDidlMatcher.group(1);
            params = sipDidlMatcher.group(2);
        }

        // extract resource param groups (in groups of 3)
        Pattern paramPattern = Pattern.compile("<param>(.*?)</param>.*?<param>(.*?)</param>.*?<param>(.*?)</param>", Pattern.DOTALL);
        Matcher paramMatcher = paramPattern.matcher(params);
        while (paramMatcher.find()) {
            String ref = paramMatcher.group(1).trim();
            String mimeType = paramMatcher.group(2).trim();
            String resource = paramMatcher.group(3).trim();

            // write ARC resource
            arcw.write(ref, "0.0.0.0", mimeType, resource.getBytes());
        }

        long a = System.currentTimeMillis();
        // bg comment out Vector data = transformer.process(metadata, id, arcw);
        // added kludge just to show what the standard old-fashioned process was
        Vector data = new Vector();
        data.add(metadata);
        data.add(datestamp);
        log.debug("record: " + (System.currentTimeMillis() - a));
        String date = (String) data.get(1);
        String didl = (String) data.get(0);

        if (didl != null) {
            writer.writeRecord(new TapeRecord(id, date, didl));
            // 2007-06-27: Added for tape rollover support
            if (writer.getSize() >= maxTapeSize) {
                closeWriters();
                tapeCount++;
                NumberFormat formatter = new DecimalFormat("##00");
                initWriters(rootTapeName + "-" + formatter.format(tapeCount), null);
            }
        }
    }

    /**
     * Sets the Converter Class to be used for DIDL pre-processing
     * 
     * @param conClass
     *            Converter Class to be used
     * @throws ArchiveException
     */
    private void setConverterClass(String conClass) throws ArchiveException {
        try {
            this.transformer = (DidlTransformer) Class.forName(conClass)
                    .newInstance();
            transformer.setProperties(archiveConfig.getProperties());
        } catch (InstantiationException e) {
            throw new ArchiveException(
                    "InstantiationException for " + conClass, e);
        } catch (IllegalAccessException e) {
            throw new ArchiveException("IllegalAccessException for" + conClass,
                    e);
        } catch (ClassNotFoundException e) {
            throw new ArchiveException(
                    "ClassNotFoundException for " + conClass, e);
        } catch (Exception e) {
            throw new ArchiveException(e.getMessage(), e);
        }
    }

    /**
     * Gets the file name minus extension suffix
     * 
     * @param name
     *            Path to file
     * @return Name of file minus extension
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
}
