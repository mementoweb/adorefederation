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

package gov.lanl.ingest.oaitape;


import gov.lanl.ingest.IngestProperties;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ORG.oclc.oai.util.OAIUtil;

/**
 * this module is for parsing xmltapes of oaiharvest in preingestion area with
 * purpose of retrival record
 * 
 * @author Lyudmila Balakireva, Research Library, LANL
 */

public class OAITapeParser extends DefaultHandler {

    private StringBuffer _tapeid = new StringBuffer();

    private StringBuffer _datestamp = new StringBuffer();

    private boolean tapeidCapture = false;

    private boolean dateCapture = false;

    OAIRecordHandler _fragmentprocessor;

    private XMLReader _xr;

    private boolean recordCapture = false;

    private StringBuffer record = new StringBuffer();

    static Logger log = Logger.getLogger(OAITapeParser.class.getName());

    public OAITapeParser(InputStream stream, OAIRecordHandler ip) {
        super();

        try {

            _xr = XMLReaderFactory
                    .createXMLReader("org.apache.xerces.parsers.SAXParser"); 
            _xr.setFeature("http://xml.org/sax/features/namespace-prefixes", 
                    true);
            _xr.setContentHandler(this);
            _fragmentprocessor = ip;
            InputSource is = new InputSource(stream);
              is.setEncoding("UTF-8");
              _xr.parse(is);
        } catch (SAXException e) {
            log.error("Parsing Problem:" + e.getMessage()); 
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Other Problem" + e.getMessage()); 
            throw new RuntimeException(e);
        }

    }

    public void startDocument() throws SAXException {
        super.startDocument();
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) throws SAXException {
        String fullName = namespaceURI + "#" + localName; 

        if (fullName.equals(IngestProperties.TAPE_NS + "#identifier")) { 
            tapeidCapture = true;
            _tapeid.setLength(0);
        }

        if (fullName.equals(IngestProperties.TAPE_NS + "#date")) { 
            dateCapture = true;
            _datestamp.setLength(0);
        }

        if (fullName.equals(IngestProperties.OAIPMH_NS + "#record")) { 
            recordCapture = true;
            record.setLength(0);
        }

        if (recordCapture) {
            record.append("<"); 
            record.append(qName);
            int length = attrs.getLength();
            for (int i = 0; i < length; ++i) {
                record.append(" "); 
                String aEName = attrs.getQName(i);
                record.append(aEName);
                record.append("=\""); 
                record.append(OAIUtil.xmlEncode(attrs.getValue(i)));
                record.append("\""); 
            }
            record.append(">"); 
        }

    }

    public void characters(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);

        if (tapeidCapture == true) {
            _tapeid.append(s);
        }

        else if (dateCapture == true) {
            _datestamp.append(s);
        }

        if (recordCapture) {
            record.append(OAIUtil.xmlEncode(s));
        }

    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);

        if (tapeidCapture == true) {
            tapeidCapture = false;
        }

        if (dateCapture == true) {
            dateCapture = false;
        }

        if (recordCapture == true) {
            record.append("</"); 
            record.append(qName);
            record.append(">"); 
        }

        if (fullName.equals(IngestProperties.OAIPMH_NS + "#record")) { 
            recordCapture = false;
            try {
                _fragmentprocessor.process(record.toString(), _tapeid
                        .toString(), _datestamp.toString());

            } catch (Exception e) {
                log.error("Tape Handler Problem " + e.getMessage()); 
                throw new RuntimeException(e.getMessage());
            }

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
