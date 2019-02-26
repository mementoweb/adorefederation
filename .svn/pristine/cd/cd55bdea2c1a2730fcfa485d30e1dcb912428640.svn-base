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

package gov.lanl.ingest.oaitape.simple;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.ingest.IngestProperties;
import gov.lanl.ingest.oaitape.DerefProcessor;
import gov.lanl.ingest.oaitape.IngestException;
import gov.lanl.ingest.oaitape.IngestRecord;
import gov.lanl.ingest.oaitape.OAITapeUtils;
import gov.lanl.ingest.oaitape.ProcessInfo;
import gov.lanl.util.uuid.UUIDFactory;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Generic plug-in used to harvest resources from OAI Repository using the
 * dc identifier value.  
 * 
 * @author rchute, Reserch Library, LANL
 */

public class DCIdentifierPDFProcessor extends DerefProcessor {

    static final String DCNS = "http://purl.org/dc/elements/1.1/";

    static javax.xml.parsers.DocumentBuilderFactory dbf = null;

    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces",
                        Boolean.TRUE);
    }

    static Logger log = Logger.getLogger(DCIdentifierPDFProcessor.class.getName());

    ARCFileWriter arcwriter;
    IngestRecord csv;
    ProcessInfo info;
    
    /**
     * Processes an OAI Record, resolves dc identifier reference, and writes 
     * specified resource to defined ARCFileWriter.  
     * 
     * @param record - string on OAI record
     * @param arcwriter - arc file writer the resource is to be written to
     */
    public ProcessInfo processContent(String record, ARCFileWriter arcwriter) {
        this.info = new ProcessInfo();
        OAITapeUtils  util = new OAITapeUtils();
        this.arcwriter = arcwriter;
        try {
            csv = new IngestRecord();
            String id = this.getIdentifier(record);
            String resourceFile = null;
            log.debug(id);
            int e = id.substring(id.lastIndexOf("."),id.length()).length() - 1;

            if (e >= 2 && e <= 4)
                resourceFile    = id;
            else
                resourceFile = util.getPdfLink(util.getPage(id,"OAI Resource Harvester"));
            
            if (resourceFile == null)
                throw new IngestException("Unable to locate supported file.");
            String link = resourceFile;
            csv.setSourceURI(link);
            String mimetype = "application/pdf";
            // Download bytes
            byte[] deref = util.getBinary(link);
            
            String digest = "urn:sha1:"
                    + util.calculateDigest(deref, "SHA1");
            String arcid = IngestProperties.getLocalDataStreamPrefix()
                    + UUIDFactory.generateUUID().toString().substring(9);
            csv.setLocalIdentifier(arcid);
            csv.setDigest(digest);
            arcwriter.write(arcid, "0.0.0.0", mimetype, deref);
            csv.setSuccess(true);
            String[] strArray = csv.toArray();
            info.addLogInfo(strArray);
            info.setStatus(true);
            // slow down not to be blocked
        } catch (Exception e) {
            info.setStatus(false);
            info.setMessage(e.getMessage());
            log.warn("DCIdentifierPDFProcessor:" + e.getMessage());
        }

        return this.info;
    }

    /**
     * Gets dc identifier for the specified record
     * 
     * @param record
     * @return dc identifier
     */

    public String getIdentifier(String record) throws Exception {
        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
        InputSource IS = new InputSource(new StringReader(record));
        org.w3c.dom.Document doc = db.parse(IS);
        
        NodeList idnodes = doc.getElementsByTagNameNS(DCNS, "identifier");
        String id = null;
        String tempid = null;
    
        for (int i = 0; i < idnodes.getLength(); i++) {
            Node node = idnodes.item(i);
            NodeList cnodes = node.getChildNodes();
            for (int j = 0; j < cnodes.getLength(); j++) {
                Node cnode = cnodes.item(j);
                System.out.println(cnode.getNodeValue());
                if (cnode.getNodeType() == Node.TEXT_NODE)    {
                       tempid = cnode.getNodeValue();
                       log.debug(tempid);
                   if (tempid.startsWith("http") || tempid.startsWith("/")) {
                       id = cnode.getNodeValue();
                       csv.setDerefXPath("//dc:identifier[" + i + "]/*");
                   }
                }      
            }
        }    
        return id;
    }
        
}
