/*
 * Copyright (c) 2006  The Regents of the University of California
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

package gov.lanl.adore.demo;

import gov.lanl.arc.ARCProperties;
import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.archive.ArchiveConfig;
import gov.lanl.archive.ArchiveConstants;
import gov.lanl.archive.ArchiveIO;
import gov.lanl.archive.ArchiveProfile;
import gov.lanl.archive.ProfileProcessor;
import gov.lanl.archive.trans.TransProperties;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.SingleTapeWriter;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.TapeRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * <code>TutorialProcessor</code> implements the aDORe Archive Profile
 * Processor. The class defines the pre-ingestion process for source xml
 * content. <br>
 * <br>
 * From this class, we'll do the following:<br> 
 * - Implement the <code>ProfileProcessor</code> interface <br> 
 * - Create a new interface, <code>DocProcessor</code>, for processing 
 * differing formats (i.e. METS, DIDL) <br> 
 * - Prime our XMLTape & ARCFile Writers <br> 
 * - Pre-process Document using <code>DocProcessor</code> implementation <br> 
 * - Write documents to XMLTape <br> 
 * - Add generated XMLTapes & ARCFile to list of items to be registered
 * 
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * @see ProfileProcessor
 * @see DocProcessor
 * 
 */
public class TutorialProcessor implements ProfileProcessor {

    static Logger log = Logger.getLogger(TutorialProcessor.class.getName());

    private ArrayList<File> arcFileList = new ArrayList<File>();

    private ArrayList<File> xmlTapeList = new ArrayList<File>();

    private ArrayList inputArray;

    private ArchiveConfig archiveConfig;

    private ArchiveProfile archiveProfile;

    private ArchiveIO archiveIO;

    private SingleTapeWriter writer;

    private ARCFileWriter arcw;

    private String arcName;

    private String tapeName;

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
            this.inputArray = sourceFiles;
        }
    }

    /**
     * Main processing method called by application. This implementation
     * initializes the relevant properties objects, initializes the XMLTape and
     * ARCFileWriters, then iterates through the source input. Once the XMLTapes
     * and ARCFiles have been generated, the files are added to the list of
     * items to be registered by the application.
     */
    public void runIt() throws Exception {
        // Initialize Archive IO, ArcProperties, TapeProperties
        this.archiveIO = new ArchiveIO(archiveConfig);
        TapeProperties.load(archiveConfig.getProperties());
        ARCProperties.load(archiveConfig.getProperties());
        TransProperties.load(archiveConfig.getProperties());
        log.debug("Initialized Properties");

        // Prime Tape & ARC File Names
        tapeName = UUIDFactory.generateUUID().toString().substring(9);
        arcName = UUIDFactory.generateUUID().toString().substring(9);

        // Open Tape Writer
        writer = archiveIO.openSingleTapeWriter(tapeName);
        writer.setXmlTapeID(TapeProperties.getLocalXmlTapePrefix() + tapeName);
        writer.setArcFileID(ARCProperties.getLocalArcFilePrefix() + tapeName);
        writer.writeDefaultAdmin();
        log.debug("Initialized XML Tape Writer");

        // Open ARC Writer
        arcw = archiveIO.openARCFileWriter(arcName);
        log.debug("Initialized ARC File Writer");

        // Begin Processing Input
        for (Iterator i = inputArray.iterator(); i.hasNext();) {
            String sourceFile = ((File) i.next()).getAbsolutePath();
            log.debug("Processing " + sourceFile);
            processDocument(sourceFile);
        }

        // Close tape and arc writers
        writer.close();
        arcw.close();

        // Populate XMLtape & ARCfile lists
        String tapeDir = archiveConfig.getTapeStoredDirectory();
        String arcDir = archiveConfig.getARCStoredDirectory();
        xmlTapeList.add(new File(tapeDir, tapeName + ".xml"));
        arcFileList.add(new File(arcDir, arcName + ".arc"));
    }

    /**
     * Processes each document extracting resource references and updating the
     * associated doc reference. The DocProcessor interface was introduced to
     * accommodate multiple document formats (i.e. METS, DIDL, etc).
     * 
     * @param sourceFile
     *            File containing a single XML document
     * @throws Exception
     */
    private void processDocument(String sourceFile) throws Exception {
        String plugin = archiveProfile.getCollectionConverterPlugin();
        log.debug("Initializing Converter Plug-in: " + plugin);
        DocProcessor dp = (DocProcessor) Class.forName(plugin).newInstance();
        dp.setARCOutput(arcw, archiveConfig.getARCResolverURL() + "?res_id="
                + archiveConfig.getLocalArcFilePrefix() + arcName);

        try {
            dp.read(new FileInputStream(new File(sourceFile)));

            // Write Resources to ARC
            dp.process();

            // Get XML String of Document
            String metadata = dp.write();

            String date = dp.getDate();
            String id = dp.getId();
            if (metadata != null) {
                writer.writeRecord(new TapeRecord(id, date, metadata));
            } else {
                throw new Exception(
                        "Error processing record: No metadata found in document");
            }
        } catch (FileNotFoundException e) {
            log.error(e.getClass().getName() + " occurred for " + sourceFile);
            throw new Exception("Error processing " + sourceFile + ": "
                    + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Unable to process " + sourceFile + ": "
                    + e.getMessage());
        }
    }
}
