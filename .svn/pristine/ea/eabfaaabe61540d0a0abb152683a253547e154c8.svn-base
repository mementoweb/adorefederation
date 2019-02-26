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

package gov.lanl.arc.registry;

import gov.lanl.arc.ARCProperties;
import gov.lanl.arc.ARCEnvConfig;
import gov.lanl.repo.oaidb.arcreg.ARCRegistryConstants;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ORG.oclc.oai.util.OAIUtil;

public class MetaHarvester extends DefaultHandler implements ARCRegistryConstants {
    public static final String ARC = ARCProperties.ARC_REG_NS;

    public static final String PR = ARCProperties.PUT_RECORD_NS;

    public static final String DC = "http://purl.org/dc/elements/1.1/";

    public static final String DCTERMS = "http://purl.org/dc/terms/";

    public static final String BA = ARCProperties.TAPE_BASCIS_NS;

    public static final String TA = ARCProperties.TAPE_ADMIN_NS;

    private XMLReader _xr;
    
    private StringBuffer arcFileId = new StringBuffer();
    private StringBuffer arcFileFile = new StringBuffer();
    private StringBuffer arcFileIndex = new StringBuffer();
    private StringBuffer openurlURL = new StringBuffer();
    private StringBuffer arcFileAdmin = new StringBuffer();
    private StringBuffer arcFileDigest = new StringBuffer();
    private boolean arcFileIdCapture = false;
    private boolean arcFileFileCapture = false;
    private boolean arcFileIndexCapture = false;
    private boolean openurlURLCapture = false;
    private boolean arcFileAdminCapture = false;
    private boolean arcFileDigestCapture = false;
    
    private StringBuffer forced = new StringBuffer();
    private StringBuffer pdefault = new StringBuffer();
    private boolean forcedCapture = false;
    private boolean defaultCapture = false;

    public MetaHarvester(String meta) throws SAXException, IOException {
        super();
        _xr = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");
        _xr.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        _xr.setContentHandler(this);
        _xr.parse(new InputSource(new StringReader(meta)));
    }

    public Properties Meta2Prop() throws IOException {
        Properties p = new Properties();
        p.setProperty(ARCEnvConfig.TAG_ARC_FILE_ID, arcFileId.toString());
        p.setProperty(ARCEnvConfig.TAG_ARC_FILE_NAME, arcFileFile.toString());
        p.setProperty(ARCEnvConfig.TAG_ARC_FILE_INDEX, arcFileIndex.toString());
        p.setProperty(ARCEnvConfig.TAG_ARC_FILE_DIGEST, arcFileDigest.toString());
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
        if (arcFileIdCapture) {
            arcFileId.append(OAIUtil.xmlEncode(s));
        } else if (arcFileFileCapture) {
            arcFileFile.append(OAIUtil.xmlEncode(s));
        } else if (arcFileIndexCapture) {
            arcFileIndex.append(OAIUtil.xmlEncode(s));
        } else if (openurlURLCapture) {
            openurlURL.append(OAIUtil.xmlEncode(s));
        } else if (arcFileAdminCapture) {
            arcFileAdmin.append(OAIUtil.xmlEncode(s));
        } else if (arcFileDigestCapture) {
            arcFileDigest.append(OAIUtil.xmlEncode(s));
        } else if (s.trim().length() > 0) {
            //System.out.println("Unrecognized content:" + s);
        }
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) {
        String fullName = namespaceURI + "#" + localName;

        if (fullName.equals(BA + "#" + ELEMENT_ARC_FILE_ID)) {
            arcFileIdCapture = true;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_FILEPATH)) {
            arcFileFileCapture = true;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_IDX)) {
            arcFileIndexCapture = true;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_OPENURL_URL)) {
            openurlURLCapture = true;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_ADMIN)) {
            arcFileAdminCapture = true;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_DIGEST)) {
            arcFileDigestCapture = true;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);
        
        if (fullName.equals(BA + "#" + ELEMENT_ARC_FILE_ID)) {
            arcFileIdCapture = false;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_FILEPATH)) {
            arcFileFileCapture = false;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_IDX)) {
            arcFileIndexCapture = false;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_OPENURL_URL)) {
            openurlURLCapture = false;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_ADMIN)) {
            arcFileAdminCapture = false;
        } else if (fullName.equals(namespaceURI + "#" + ELEMENT_ARC_FILE_DIGEST)) {
            arcFileDigestCapture = false;
        }
    }
}
