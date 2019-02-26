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

package gov.lanl.archive.trans.didl.sci;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.archive.trans.TransProperties;
import gov.lanl.archive.trans.didl.ComponentParser;
import gov.lanl.archive.trans.didl.ResourceParserInterface;
import gov.lanl.archive.trans.didl.DidlTransformer;
import gov.lanl.didlwriter.profile.AdoreConstants;
import gov.lanl.didlwriter.profile.sci.ScienceServer;
import gov.lanl.util.DateUtil;
import gov.lanl.util.DigestUtil;
import gov.lanl.util.StreamUtil;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.util.xml.XmlUtil;

import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
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
 * ScienceServerDidl.java sciserver xml structure:
 * 
 */

public class ScienceServerDidl implements DidlTransformer, ResourceParserInterface {
    private ScienceServer didWriter;

    private Logger log = Logger.getLogger(ScienceServerDidl.class.getName());

    private static javax.xml.parsers.DocumentBuilderFactory dbf = null;
    
    private static final String ARCIDPREFIX = TransProperties.getLocalDataStreamPrefix();

    private static final String DIINS = TransProperties.DII_NS;

    private static byte[] xmlDec = new byte[0];
    
    static final int MARCXML = 1;

    static final int SCIXML = 2;
    
    private ARCFileWriter arcwriter;
    
    private Properties properties;
    
    private String arcurl;
    
    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
        try {
            xmlDec = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {}
    }

    public void getData(String resource, Map att) {
        Integer rindex = (Integer) att.get("rindex");
        boolean skip = false;
        boolean hasXmlDeclaration = true;

        // derefs first resource skips alls others per item.
        if (rindex.toString().equals("1")) {
            String ref = null;
            if (att.containsKey("ref")) {
                ref = (String) att.get("ref");
            }
            
            byte[] deref = null;
            try {
                if (ref != null) {
                    if (ref.startsWith("http://dx.doi.org")) {
                        skip = true;
                    } else {
                        String contenc = null;
                        InputStream in = StreamUtil.getInputStream(new URL(ref));
                        if (att.containsKey("zipencoding")) {
                            contenc = (String) att.get("zipencoding");
                            if (contenc.equals("gzip")) {
                                deref = StreamUtil.unzipStream(in);
                            }
                        } 

                        if (deref == null) {
                            deref = StreamUtil.getByteArray(in);
                        }
                    }
                } else {
                    String encoding = null;
                    
                    if (att.containsKey("encoding")) {
                        encoding = (String) att.get("encoding");
                        if (encoding.equals("base64")) {
                            BASE64Decoder decoder = new BASE64Decoder();
                            deref = decoder.decodeBuffer(resource);
                        }
                    } 

                    if (deref == null) {
                        hasXmlDeclaration = resource.contains("<?xml") ? true : false;
                        resource = resource.replaceAll("http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd",
                                                       "http://purl.lanl.gov/aDORe/schemas/2006-09/MARC21slim.xsd");
                        deref = resource.getBytes("UTF-8");
                    }
                }
                
                String mimetype = (String) att.get("mimetype");
                String created = (String) att.get("created");
                //String format = (String) att.get("format");
                //long extent = resource.getBytes().length;

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
                    int type = 0;
                    
                    if (arcmimetype.indexOf(" ") > 0) {
                        arcmimetype = arcmimetype.replaceAll(" ", "");
                    }
                    if (arcmimetype.indexOf("marc+") > 0) {
                        arcmimetype = arcmimetype.replaceAll("marc\\+", "");
                        type = MARCXML;
                    }
                    if (arcmimetype.indexOf("sciserver+") > 0) {
                        arcmimetype = arcmimetype.replaceAll("sciserver\\+", "");
                        type = SCIXML;
                    }
                    arcwriter.write(arcid, "0.0.0.0", arcmimetype, deref);

                    String item = (String) att.get("item");

                    if (item.equals("metadata")) {
                        if (type == MARCXML) {
                            didWriter.addComponent(
                                    ScienceServer.COMPONENT_TYPE.MARCXML,
                                    arcid,
                                    digest,
                                    AdoreConstants.MARC_XML_FORMATID,
                                    created,
                                    arcmimetype,
                                    null,
                                    resource);
                        } else {
                            didWriter.addComponent(
                                    ScienceServer.COMPONENT_TYPE.SCIXML,
                                    arcid,
                                    digest,
                                    AdoreConstants.SCI_XML_FORMATID,
                                    created,
                                    arcmimetype,
                                    null,
                                    null);
                        }
                    } else {
                        didWriter.addComponent(
                                ScienceServer.COMPONENT_TYPE.FULLTEXT,
                                arcid,
                                digest,
                                "info:lanl-repo/fmt/5",  // pdf
                                created,
                                arcmimetype,
                                new URI(XmlUtil.encode(ref)),
                                null);
                    }

                } // skip
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
        
        // Initialize ScienceServer DIDL Processor
        didWriter = new ScienceServer();

        // Package identifier - (i.e. info:lanl-repo/i/cd7b17ea-bddf-11d9-9de5-c11b6cd85594)
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
