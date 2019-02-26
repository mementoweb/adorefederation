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

package gov.lanl.ingest.oaitape.modoai;

import gov.lanl.ingest.IngestProperties;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ORG.oclc.oai.util.OAIUtil;

/**
 * ComponentParser is for parsing record with purpose of retrival attributes of
 * resource and resource content
 * 
 * @author Lyudmila Balakireva,Research Library, LANL
 */

public class ComponentParser extends DefaultHandler {

    ComponentParserInterface _rprocessor;

    private XMLReader _xr;

    private boolean resourceCapture = false;

    private StringBuffer resource = new StringBuffer();

    private boolean componentCapture = false;

    private Map att = new HashMap();

    private int ccount = 0;

    private int rcount = 0;

    static Logger log = Logger.getLogger(ComponentParser.class.getName());

    public ComponentParser(String meta, ComponentParserInterface ip) {

        super();
        try {
            _xr = XMLReaderFactory
                    .createXMLReader("org.apache.xerces.parsers.SAXParser");
            _xr.setFeature("http://xml.org/sax/features/namespace-prefixes",
                    true);
            _xr.setContentHandler(this);
            _rprocessor = ip;
            _xr.parse(new InputSource(new StringReader(meta)));
        } catch (SAXException e) {
            log.error("Parsing problem of component:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Other ComponentParser problem" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void startDocument() throws SAXException {
        super.startDocument();
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) throws SAXException {
        String fullName = namespaceURI + "#" + localName;

        if (resourceCapture == false && fullName.equals(IngestProperties.DIDL_NS + "#Resource")) {
            resource.setLength(0);
            resourceCapture = true;
            rcount = rcount + 1;

            for (int i = 0; i < attrs.getLength(); i++) {
                String shortname = attrs.getLocalName(i);
                if (shortname.equals("ref")) {
                    att.put("ref", attrs.getValue(i));
                }
                if (shortname.equals("mimeType")) {
                    att.put("mimetype", attrs.getValue(i));
                    //System.out.println("mimetype" + attrs.getValue(i));
                }
                if (shortname.equals("encoding")) {
                    att.put("encoding", attrs.getValue(i));
                }
                if (shortname.equals("contentEncoding")) {
                    att.put("zipencoding", attrs.getValue(i));
                }

            }
            att.put("cindex", new Integer(ccount));
            att.put("rindex", new Integer(rcount));

        } else if (resourceCapture) {

            resource.append("<");
            resource.append(qName);
            int length = attrs.getLength();
            for (int i = 0; i < length; ++i) {
                resource.append(" ");
                String aEName = attrs.getQName(i);
                resource.append(aEName);
                resource.append("=\"");
                resource.append(OAIUtil.xmlEncode(attrs.getValue(i)));
                resource.append("\"");

            }
            resource.append(">");
        }

        if (fullName.equals(IngestProperties.DIDL_NS + "#Component")) {
            componentCapture = true;
            ccount = ccount + 1;
        }

    }

    public void characters(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);

        if (resourceCapture) {
            resource.append(OAIUtil.xmlEncode(s));
        }

    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);

        if (fullName.equals(IngestProperties.DIDL_NS + "#Resource")) {
            resourceCapture = false;
            try {

                _rprocessor.getData(resource.toString(), att);
            } catch (Exception e) {
                log.error("component hadler problem" + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }

            att.clear();
        }

        if (resourceCapture == true) {
            resource.append("</");
            resource.append(qName);
            resource.append(">");
        }

        if (fullName.equals(IngestProperties.DIDL_NS + "#Component")) {
            componentCapture = false;
            rcount = 0;
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
