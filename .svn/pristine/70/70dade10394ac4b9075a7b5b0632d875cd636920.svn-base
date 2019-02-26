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

package gov.lanl.adore.demo.didl;

import gov.lanl.adore.demo.DocProcessor;
import gov.lanl.adore.demo.TutorialArcWriter;
import gov.lanl.adore.demo.TutorialException;
import gov.lanl.arc.ARCException;
import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.util.DateUtil;
import gov.lanl.util.xml.XmlUtil;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * <code>DidlProcessor</code> implements the DocProcessor interface developed
 * for the tutorial. The implementation uses the DIDAPI to process DIDL
 * documents for ingestion into the aDORe Archive.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class DidlProcessor implements DocProcessor {

    static Logger log = Logger.getLogger(DidlProcessor.class.getName());

    private DidlObject didl;

    private TutorialArcWriter arcWriter;

    private String id;

    private String date;

    /**
     * Reads in an InputStream for processing. The DidlObject implementation of
     * the DIDAPI is used to parse source.
     * 
     * @param source
     *            InputStream contain document to process
     * @throws TutorialException
     */
    public void read(InputStream source) throws TutorialException {
        didl = new DidlObject();
        try {
            didl.parse(source);
            id = didl.getDIDLDocumentId();
            log.debug("Processing " + id);
        } catch (Exception e) {
            throw new TutorialException("Error processing InputStream: "
                    + e.getMessage());
        }
    }

    /**
     * Iterates through DIDLComponents looking for resource URIs. Resources are
     * then resolved and written to the initialized ARCFileWriter instance. Once
     * the resource has been written to an arc file, the arc file resolver url
     * to the resource is set as the new resource uri.
     */
    public void process() throws TutorialException {
        for (DidlComponent com : didl.getComList()) {
            if (com.getResourceURI() != null) {
                String source = com.getResourceURI().toString();
                String mime = com.getMimetype();
                String arcUrl = "";
                try {
                    log.debug(mime + " | " + source);
                    arcUrl = XmlUtil.encode(arcWriter.write(source, mime));
                    com.setResourceURI(new URI(arcUrl));
                } catch (ARCException e) {
                    throw new TutorialException("Error writing resource "
                            + source + " to arc file: " + e.getMessage());
                } catch (URISyntaxException e) {
                    throw new TutorialException("Error writing creating uri: "
                            + arcUrl + " " + e.getMessage());
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
        String out;
        try {
            out = didl.getXML();
            out = format(out);
            date = DateUtil.date2UTC(new Date());
            log.debug("Date: " + date);
        } catch (Exception e) {
            throw new TutorialException(
                    "Error occurred attempting to serialize didl to xml: "
                            + e.getMessage());
        }
        return out;
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
     * off the declaration and return doc.
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
