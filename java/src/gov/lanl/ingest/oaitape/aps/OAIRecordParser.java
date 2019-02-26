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

package gov.lanl.ingest.oaitape.aps;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ORG.oclc.oai.util.OAIUtil;
import gov.lanl.ingest.IngestProperties;

/**
 * OAIRecordParser is for parsing record with purpose of retrival metadata
 * content and about container
 * 
 * @author Lyudmila Balakireva, Research Library, LANL
 */

public class OAIRecordParser extends DefaultHandler {
    private static String OAIPMHNS = IngestProperties.OAIPMH_NS;

    private XMLReader _xr;

    private boolean metadataCapture = false;

    private StringBuffer metadata = new StringBuffer();

    private boolean recordCapture = false;

    private boolean aboutCapture = false;

    private ArrayList abouts = new ArrayList();

    private StringBuffer about = new StringBuffer();

    static Logger log = Logger.getLogger(OAIRecordParser.class.getName());

    public OAIRecordParser(String record) {
        super();
        try {
            _xr = XMLReaderFactory
                    .createXMLReader("org.apache.xerces.parsers.SAXParser");
            _xr.setFeature("http://xml.org/sax/features/namespace-prefixes",
                    true);
            _xr.setContentHandler(this);
            _xr.parse(new InputSource(new StringReader(record)));
        } catch (SAXException e) {
            log.error("Parsing problem:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("OAIRecordParser other problem:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getMetadata() {
        if (metadata.length() > 0) {
            return metadata.toString();
        } else {
            return null;
        }

    }

    public Iterator getAbouts() {
        if (about.length() > 0) {
            return abouts.iterator();
        } else {
            return null;
        }
    }

    public void startDocument() throws SAXException {
        super.startDocument();
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) throws SAXException {
        String fullName = namespaceURI + "#" + localName;

        if (metadataCapture == false && fullName.equals(OAIPMHNS + "#metadata")) {
            metadata.setLength(0);
            metadataCapture = true;
        } else if (metadataCapture == false && aboutCapture == false
                && fullName.equals(OAIPMHNS + "#about")) {
            about.setLength(0);
            aboutCapture = true;
        } else if (metadataCapture) {
            metadata.append("<");
            metadata.append(qName);
            int length = attrs.getLength();
            for (int i = 0; i < length; ++i) {
                metadata.append(" ");
                String aEName = attrs.getQName(i);
                metadata.append(aEName);
                metadata.append("=\"");
                metadata.append(OAIUtil.xmlEncode(attrs.getValue(i)));
                metadata.append("\"");
            }
            metadata.append(">");
        }
        if (aboutCapture) {
            about.append("<");
            about.append(qName);
            int length = attrs.getLength();
            for (int i = 0; i < length; ++i) {
                about.append(" ");
                String aEName = attrs.getQName(i);
                about.append(aEName);
                about.append("=\"");
                about.append(OAIUtil.xmlEncode(attrs.getValue(i)));
                about.append("\"");
            }
            about.append(">");
        }
        if (fullName.equals(OAIPMHNS + "#record")) {
            recordCapture = true;
        }

    }

    public void characters(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);

        if (metadataCapture) {
            metadata.append(OAIUtil.xmlEncode(s));
        } else if (aboutCapture) {
            about.append(OAIUtil.xmlEncode(s));
        }

    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);

        if (fullName.equals(OAIPMHNS + "#metadata")) {
            metadataCapture = false;
        }
        if (metadataCapture == true) {
            metadata.append("</");
            metadata.append(qName);
            metadata.append(">");
        }
        if (aboutCapture == true) {
            about.append("</");
            about.append(qName);
            about.append(">");
        }

        if (fullName.equals(OAIPMHNS + "#about")) {
            aboutCapture = false;
            abouts.add(about.toString());
        }

        if (fullName.equals(OAIPMHNS + "#record")) {
            recordCapture = false;
        }

    }

    public static String fullName(String namespaceURI, String localName) {
        StringBuffer sb = new StringBuffer();
        sb.append(namespaceURI);
        sb.append("#");
        sb.append(localName);
        return sb.toString();
    }

}
