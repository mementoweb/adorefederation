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

package gov.lanl.archive.trans.didl.pro;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.archive.trans.TransProperties;
import gov.lanl.archive.trans.didl.ComponentParser;
import gov.lanl.archive.trans.didl.ResourceParserInterface;
import gov.lanl.archive.trans.didl.DidlTransformer;
import gov.lanl.didlwriter.profile.AdoreConstants;
import gov.lanl.didlwriter.profile.airef.AIREF;
import gov.lanl.util.DateUtil;
import gov.lanl.util.DigestUtil;
import gov.lanl.util.uuid.UUIDFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import sun.misc.BASE64Decoder;

/**
 * ProDidl
 * 
 */

public class ProDidl implements DidlTransformer, ResourceParserInterface {
    private AIREF didWriter;
    private Logger log = Logger.getLogger(ProDidl.class.getName());
    private static javax.xml.parsers.DocumentBuilderFactory dbf = null;
    private static byte[] xmlDec = new byte[0];
    private static final String COPYRIGHT = "Copyright Thomson Corporation";
    private static final String COLLECTION = "info:sid/library.lanl.gov:pro";
    
    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
        try {
            xmlDec = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    static final int UNKNOWN = 0;

    static final int SEM1 = 1;

    static final int SEM2 = 2;

    static final int RAW = 3;
    
    static final String ARCIDPREFIX = TransProperties.getLocalDataStreamPrefix();

    static final String DIINS = TransProperties.DII_NS;

    ARCFileWriter arcwriter;

    Properties properties;

    String arcurl;

    long unitStart;

    public void getData(String resource, Map att) {
        Integer rindex = (Integer) att.get("rindex");
        boolean skip = false;
        boolean hasXmlDeclaration = true;

        // derefs first resource skips alls others per item.
        if (rindex.toString().equals("1")) {
            byte[] deref = null;
            try {
                String encoding = null;
                String semantic = null;

                if (att.containsKey("semantic")) {
                    semantic = (String) att.get("semantic");
                }
                
                if (att.containsKey("encoding")) {
                    encoding = (String) att.get("encoding");
                }

                if (encoding != null) {
                    if (encoding.equals("base64")) {
                        BASE64Decoder decoder = new BASE64Decoder();
                        deref = decoder.decodeBuffer(resource);
                        hasXmlDeclaration = new String(deref).contains("<?xml") ? true : false;
                    }
                } else {
                    hasXmlDeclaration = resource.contains("<?xml") ? true : false;
                    resource = resource.replaceAll("http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd",
                                                   "http://purl.lanl.gov/aDORe/schemas/2006-09/MARC21slim.xsd");
                    // Check for in-valid MarcXML content
                    //if (!MarcXMLValidator.validMarcXML(resource)) { // works, just takes extra time
                       if (!resource.contains("xmlns:xsi")) {
                           StringBuffer str = new StringBuffer(resource.substring(0, resource.indexOf("xmlns:marc") - 1));
                           str.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "); 
                           str.append(resource.substring(resource.indexOf("xmlns:marc")));
                           resource = str.toString();
                       }   
                    //}
                    deref = resource.getBytes("UTF-8");
                }

                String mimetype = (String) att.get("mimetype");
                String created = (String) att.get("created");
                String creator = (String) att.get("creator");

                if (!skip) {
                    if (mimetype.contains("xml") && !hasXmlDeclaration) {
                        byte[] newDeref = new byte[xmlDec.length + deref.length];
                        System.arraycopy(xmlDec, 0, newDeref, 0, xmlDec.length);
                        System.arraycopy(deref, 0, newDeref, xmlDec.length, deref.length);
                        deref = newDeref;
                    }
                    
                    String digest = DigestUtil.getSHA1Digest(deref);
                    digest = DigestUtil.toURN(digest, "sha1");
                    String arcid = ARCIDPREFIX + UUIDFactory.generateUUID().toString().substring(9);
                    String arcmimetype = mimetype;

                    int type = UNKNOWN;

                    // clean-up
                    if (arcmimetype.indexOf(" ") > 0) {
                        arcmimetype = arcmimetype.replaceAll(" ", "");
                    }
                    // set types
                    if (arcmimetype.indexOf("marc+") > 0 && semantic.indexOf("sem/1") > 0) {
                        arcmimetype = arcmimetype.replaceAll("marc\\+", "");
                        type = SEM1;
                    } else if (arcmimetype.indexOf("marc+") > 0 && semantic.indexOf("sem/2") > 0) {
                        arcmimetype = arcmimetype.replaceAll("marc\\+", "");
                        type = SEM2;
                    } else if (arcmimetype.indexOf("plain") > 0) {
                        type = RAW;
                    }
                                    
                    arcwriter.write(arcid, "0.0.0.0", arcmimetype, deref);

                    if (type == SEM1) {
                        // Store both in-line resource and ref to arc
                        didWriter.addComponent(
                                AIREF.COMPONENT_TYPE.SEM1,
                                arcid,
                                digest,
                                AdoreConstants.MARC_XML_FORMATID,
                                created,
                                AdoreConstants.RTF_CREATORID,
                                arcmimetype,
                                null,
                                resource);
                    } if (type == SEM2) {
                        // Store both in-line resource and ref to arc
                        didWriter.addComponent(
                                AIREF.COMPONENT_TYPE.SEM2,
                                arcid,
                                digest,
                                AdoreConstants.MARC_XML_FORMATID,
                                created,
                                AdoreConstants.RTF_CREATORID,
                                arcmimetype,
                                null,
                                resource);
                    } else if (type == RAW) {
                        // Store ref to arc
                        didWriter.addComponent(
                                AIREF.COMPONENT_TYPE.RAW,
                                arcid,
                                digest,
                                AdoreConstants.PRO_RAW_FORMATID,
                                created,
                                creator,
                                arcmimetype,
                                null,
                                null);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public Vector process(String oldDidl, String id, ARCFileWriter arcwriter) throws Exception {
        Vector data = new Vector();
        this.arcwriter = arcwriter;

        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();

        // Create DOM for Old DIDL
        InputSource IS = new InputSource(new StringReader(oldDidl));
        org.w3c.dom.Document doc = db.parse(IS);

        // Initialize ISI DIDL Processor
        didWriter = new AIREF();
        didWriter.setCopyright(COPYRIGHT);
        didWriter.setCollection(COLLECTION);
        
        // Package identifier - (i.e.
        // info:lanl-repo/i/cd7b17ea-bddf-11d9-9de5-c11b6cd85594)
        didWriter.setDocumentId(id);

        // Identifier of First Item
        NodeList idnodes = doc.getElementsByTagNameNS(DIINS, "Identifier");

        // Content ID - (i.e. info:doi/10.1007/s10610-004-5886-2)
        Node node = idnodes.item(0);
        NodeList cnodes = node.getChildNodes();
        int nlength = cnodes.getLength();
        for (int i = 0; i < nlength; i++) {
            Node cnode = cnodes.item(i);
            if (cnode.getNodeType() == Node.TEXT_NODE) {
                didWriter.setContentId(cnode.getNodeValue());
            }
        }

        ComponentParser cp = new ComponentParser(oldDidl, this);
        data.add(didWriter.getXML());
        data.add(DateUtil.date2UTC(new Date()));

        return data;
    }

    public void setProperties(Properties props) throws Exception {
        properties = props;

        // Initialize Convert Properties
        TransProperties.load(properties);
    }
}
