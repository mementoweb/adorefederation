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

package gov.lanl.adore.demo.mets;

import edu.harvard.hul.ois.mets.FLocat;
import edu.harvard.hul.ois.mets.File;
import edu.harvard.hul.ois.mets.FileGrp;
import edu.harvard.hul.ois.mets.FileSec;
import edu.harvard.hul.ois.mets.Mets;
import edu.harvard.hul.ois.mets.helper.MetsElement;
import edu.harvard.hul.ois.mets.helper.MetsException;
import edu.harvard.hul.ois.mets.helper.MetsReader;
import edu.harvard.hul.ois.mets.helper.MetsValidator;
import edu.harvard.hul.ois.mets.helper.MetsWriter;
import gov.lanl.adore.demo.DocProcessor;
import gov.lanl.adore.demo.TutorialArcWriter;
import gov.lanl.adore.demo.TutorialException;
import gov.lanl.arc.ARCException;
import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.util.DateUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * <code>MetsProcessor</code> implements the DocProcessor interface developed
 * for the tutorial. The implementation uses the METS Toolkit, developed by
 * Harvard, to process METS documents for ingestion into the aDORe Archive.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class MetsProcessor implements DocProcessor {

    static Logger log = Logger.getLogger(MetsProcessor.class.getName());

    private Mets mets;

    private TutorialArcWriter arcWriter;

    private String id;

    private String date;

    /**
     * Reads in an InputStream for processing. The MetsReader is used to parse
     * and validate document.
     * 
     * @param source
     *            InputStream contain document to process
     * @throws TutorialException
     */
    public void read(InputStream source) throws TutorialException {
        mets = new Mets();
        try {
            MetsReader r = new MetsReader(source);
            mets.read(r);
            mets.validate(new MetsValidator());

            // Gets value used for tape record id
            id = mets.getOBJID();
            log.debug("Processing " + id);
        } catch (MetsException e) {
            throw new TutorialException("Error processing METS document: "
                    + e.getMessage());
        } catch (Exception e) {
            throw new TutorialException("Error processing source "
                    + e.getMessage());
        }
    }

    /**
     * Iterates through content objects to obtain FLocat elements. URLs obtained
     * from the FLocat element are resolved and written to the initialized
     * ARCFileWriter instance. Once written, the ARC File Resolver URL to the
     * resource is returned. The FLocat ref is updated in the document.
     */
    public void process() throws TutorialException {
        for (Iterator i = mets.getContent().iterator(); i.hasNext();) {
            MetsElement me = (MetsElement) i.next();
            if (me.getQName().contains("fileSec")) {
                FileSec fs = (FileSec) me;
                for (Iterator j = fs.getContent().iterator(); j.hasNext();) {
                    me = (MetsElement) j.next();
                    if (me.getQName().contains("fileGrp")) {
                        FileGrp fg = (FileGrp) me;
                        for (Iterator k = fg.getContent().iterator(); k
                                .hasNext();) {
                            me = (MetsElement) k.next();
                            if (me.getQName().contains("file")) {
                                File file = (File) me;
                                try {
                                    for (Iterator l = file.getContent()
                                            .iterator(); l.hasNext();) {
                                        me = (MetsElement) l.next();
                                        if (me.getQName().contains("FLocat")) {
                                            FLocat loc = (FLocat) me;
                                            log.debug(file.getMIMETYPE()
                                                    + " | "
                                                    + loc.getXlinkHref());
                                            String arcUrl = arcWriter.write(loc
                                                    .getXlinkHref(), file
                                                    .getMIMETYPE());
                                            loc.setXlinkHref(arcUrl);
                                        }
                                    }
                                } catch (ARCException e) {
                                    throw new TutorialException(
                                            "Error processing " + file.getID()
                                                    + ": " + e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Serializes processed document to XML String format
     * 
     * @return Escaped XML String instance of document
     * @throws TutorialException
     */
    public String write() throws TutorialException {
        date = DateUtil.date2UTC(new Date());
        log.debug("Date: " + date);
        ByteArrayOutputStream out;
        try {
            mets.validate(new MetsValidator());
            out = new ByteArrayOutputStream();
            mets.write(new MetsWriter(out));
        } catch (MetsException e) {
            throw new TutorialException(e.getMessage());
        }
        return format(out.toString());
    }

    /**
     * Gets the last modified date of the TapeRecord. For example:
     * 2006-03-07T12:00:00Z
     * 
     * @return UTC Date as String
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets local repository id for document (i.e. OAI-Identifier). For example:
     * info:lanl-repo/i/dd7b17ea-bddf-11d9-9de5-c11b6cd8559
     * 
     * @return URI as String
     */
    public String getId() {
        return id;
    }

    /**
     * Should the provided xml content contain an xml declaration, will strip
     * off the declaration and return doc
     */
    private static String format(String xml) {
        if (xml.startsWith("<?")) {
            int end = xml.indexOf("?>");
            xml = xml.substring(end + 2);
        }
        return xml;
    }

    /**
     * Sets the ARCFileWriter and ARC Resolver Base Url. These are used by
     * pre-processing implementation if you wish to harvest referenced resources
     * to store locally in ARCFiles.
     * 
     * @param arcw
     *            Initialized ARCFileWriter instance
     * @param arcUrl
     *            BaseUrl to ARC File Resolver instance
     */
    public void setARCOutput(ARCFileWriter arcw, String arcUrl) {
        arcWriter = new TutorialArcWriter(arcw, arcUrl);
    }

}
