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

package gov.lanl.xmltape.registry;

import java.io.*;
import java.lang.StringBuffer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import ORG.oclc.oai.harvester.verb.Record;
import ORG.oclc.oai.util.OAIUtil;
import gov.lanl.repo.oaidb.tapereg.TapeRegistryConstants;
import gov.lanl.xmltape.TapeProperties;

public class MetaHarvester extends DefaultHandler implements TapeRegistryConstants {
    public static final String TAP = TapeProperties.TAPE_REGISTRY_NS;
    public static final String DC = "http://purl.org/dc/elements/1.1/";
    public static final String DCTERMS = "http://purl.org/dc/terms/";
    public static final String BA = TapeProperties.TAPE_BASICS_NS;
    public static final String TA = TapeProperties.TAPE_ADMIN_NS;
    private XMLReader _xr;
    private String identifier;
    private String datestamp;
    
    private StringBuffer xmlTapeId = new StringBuffer();
    private StringBuffer xmlTapeFile = new StringBuffer();
    private StringBuffer xmlTapeIndex = new StringBuffer();
    private StringBuffer oaipmhURL = new StringBuffer();
    private StringBuffer oaipmhProperties = new StringBuffer();
    private StringBuffer tapeAdmin = new StringBuffer();
    private StringBuffer xmlTapeDigest = new StringBuffer();
    
    private boolean xmlTapeIdCapture = false;
    private boolean xmlTapeFileCapture = false;
    private boolean xmlTapeIndexCapture = false;
    private boolean oaipmhURLCapture = false;
    private boolean oaipmhPropertiesCapture = false;
    private boolean tapeAdminCapture = false;
    private boolean xmlTapeDigestCapture = false;

    public MetaHarvester(Record record) throws SAXException, IOException {
        super();
        identifier = record.getIdentifier();
        datestamp = record.getDatestamp();
        _xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        _xr.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        _xr.setContentHandler(this);
        InputSource is = new InputSource(new StringReader(record.getMetadata()));
        _xr.parse(is);
    }

    public Properties Meta2Prop() throws IOException {
        Properties p = new Properties();
        String tapefile = xmlTapeFile.toString();
        String indexfile = xmlTapeIndex.toString();
        String pmh = oaipmhProperties.toString();
        String xmltapeid = xmlTapeId.toString();
        String digest = xmlTapeDigest.toString();
        String repoid = "";

        // if (tapefile.startsWith("file://")) {
        // tapefile = tapefile.substring(7);
        // }
        // if (indexfile.startsWith("file://")) {
        // indexfile = indexfile.substring(7);
        // }
        // if (pmh.startsWith("file://")) {
        // pmh = pmh.substring(7);
        // }

        if (tapefile.startsWith("file://")) {
            try {
                tapefile = new URI(tapefile).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (indexfile.startsWith("file://")) {
            try {
                indexfile = new URI(indexfile).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (pmh.startsWith("file://")) {
            try {
                pmh = new URI(pmh).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (xmltapeid.startsWith(TapeProperties.getLocalXmlTapePrefix())) {
            int i = TapeProperties.getLocalXmlTapePrefix().length();
            repoid = xmltapeid.substring(i);
        }

        p.setProperty(TapeConfig.TAG_TAPE_REPO_ID, repoid);
        p.setProperty(TapeConfig.TAG_TAPE_URI, xmltapeid);
        p.setProperty(TapeConfig.TAG_TAPE_FILE_NAME, tapefile);
        p.setProperty(TapeConfig.TAG_TAPE_FILE_INDEX, indexfile);
        p.setProperty(TapeConfig.TAG_TAPE_REG_DATESTAMP, datestamp);
        p.setProperty(TapeConfig.TAG_TAPE_PMH_DEFAULT, pmh);
        p.setProperty(TapeConfig.TAG_TAPE_FILE_DIGEST, digest);
        p.setProperty(TapeConfig.TAG_TAPE_ADMIN, tapeAdmin.toString());
        return p;

    }

    public static String fullName(String namespaceURI, String localName) {
        StringBuffer sb = new StringBuffer();
        sb.append(namespaceURI);
        sb.append("#");
        sb.append(localName);
        return sb.toString();
    }

    /**
     * SAX parser call-back method for extracting record content.
     */
    public void characters(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);

        if (xmlTapeIdCapture && (!tapeAdminCapture)) {
                xmlTapeId.setLength(0);
            xmlTapeId.append(s);
        } else if (xmlTapeFileCapture) {
            xmlTapeFile.append(s);
        } else if (xmlTapeIndexCapture) {
            xmlTapeIndex.append(s);
        } else if (oaipmhURLCapture) {
            oaipmhURL.append(s);
        } else if (oaipmhPropertiesCapture) {
            oaipmhProperties.append(s);
        } else if (xmlTapeDigestCapture) {
            xmlTapeDigest.append(s);
        } else if (tapeAdminCapture) {
            tapeAdmin.append(OAIUtil.xmlEncode(s));
        } 
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) {
        String fullName = namespaceURI + "#" + localName;

        if (fullName.equals(BA + "#" +  ELEMENT_XML_TAPE_ID_NOPREFIX)&& !tapeAdminCapture ) {
            xmlTapeIdCapture = true;
        } else if (fullName.equals(TAP + "#" + ELEMENT_XML_TAPE_FILEPATH)) {
            xmlTapeFileCapture = true;
        } else if (fullName.equals(TAP + "#" +  ELEMENT_XML_TAPE_IDX)) {
            xmlTapeIndexCapture = true;
        } else if (fullName.equals(TAP + "#" + ELEMENT_OAIPMH_URL)) {
            oaipmhURLCapture = true;
        } else if (fullName.equals(TAP + "#" + ELEMENT_OAIPMH_PROPERTIES)) {
            oaipmhPropertiesCapture = true;
        } else if (fullName.equals(TAP + "#" +  ELEMENT_XML_TAPE_DIGEST)) {
            xmlTapeDigestCapture = true;
        } else if (fullName.equals(TA + "#" + ELEMENT_TAPE_ADMIN_NOPREFIX)) {
            tapeAdminCapture = true;
        } else if (tapeAdminCapture)
            {
            tapeAdmin.append("<");
            tapeAdmin.append(qName);
            int length = attrs.getLength();
            for (int i=0; i<length; ++i) {
                tapeAdmin.append(" ");
                String aEName = attrs.getQName(i);
                tapeAdmin.append(aEName);
                tapeAdmin.append("=\"");
                tapeAdmin.append(OAIUtil.xmlEncode(attrs.getValue(i)));
                tapeAdmin.append("\"");
            }
        }

    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);
        if (fullName.equals(BA + "#" + ELEMENT_XML_TAPE_ID_NOPREFIX)&& !tapeAdminCapture) {
            xmlTapeIdCapture = false;
        } else if (fullName.equals(TAP + "#" + ELEMENT_XML_TAPE_FILEPATH)) {
            xmlTapeFileCapture = false;
        } else if (fullName.equals(TAP + "#" + ELEMENT_XML_TAPE_IDX)) {
            xmlTapeIndexCapture = false;
        } else if (fullName.equals(TAP + "#" + ELEMENT_OAIPMH_URL)) {
            oaipmhURLCapture = false;
        } else if (fullName.equals(TAP + "#" + ELEMENT_OAIPMH_PROPERTIES)) {
            oaipmhPropertiesCapture = false;
        } else if (fullName.equals(TAP + "#" + ELEMENT_XML_TAPE_DIGEST)) {
            xmlTapeDigestCapture = false;
        } else if (fullName.equals(TA + "#" + ELEMENT_TAPE_ADMIN_NOPREFIX)) {
            tapeAdminCapture = false;
        } else if (tapeAdminCapture) {
            tapeAdmin.append("</");
            tapeAdmin.append(qName);
            tapeAdmin.append(">");
        }

    }
}
