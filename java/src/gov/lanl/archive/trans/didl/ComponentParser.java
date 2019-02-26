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

package gov.lanl.archive.trans.didl;

import gov.lanl.util.xml.XmlUtil;

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

/**
 * ComponentParser is for parsing record with purpose of retrival attributes of
 * resource and resource content; SAX Implementation
 * 
 */

public class ComponentParser extends DefaultHandler {
    public static final String DIDLNS = "urn:mpeg:mpeg21:2002:02-DIDL-NS";

    public static final String DCNS = "http://purl.org/dc/elements/1.1/";

    public static final String DCTERMSNS = "http://purl.org/dc/terms/";

    ResourceParserInterface _rprocessor;

    private XMLReader _xr;

    private boolean formatCapture = false;

    private boolean resourceCapture = false;

    private boolean itemCapture = false;

    private StringBuffer resource = new StringBuffer();

    private boolean componentCapture = false;

    private boolean createdCapture = false;

    private boolean creatorCapture = false;

    private boolean extentCapture = false;
    
    private boolean semanticCapture = false;

    private Map att = new HashMap();

    private int ccount = 0;

    private String item = "";

    private int icount = 0;

    private int rcount = 0;

    private StringBuffer format = new StringBuffer();

    private StringBuffer created = new StringBuffer();

    private StringBuffer creator = new StringBuffer();

    private StringBuffer extent = new StringBuffer();
    
    private StringBuffer semantic = new StringBuffer();

    private String strformat;

    private String strcreated;

    private String strcreator;

    private String strextent;
    
    private String strsemantic;

    static Logger log = Logger
            .getLogger(ComponentParser.class.getName());

    public ComponentParser(String meta, ResourceParserInterface ip) {
        super();
        try {
            _xr = XMLReaderFactory
                    .createXMLReader("org.apache.xerces.parsers.SAXParser");
            _xr.setFeature("http://xml.org/sax/features/namespace-prefixes",
                    true);
            _xr.setContentHandler(this);
            _rprocessor = ip;
            InputSource is = new InputSource(new StringReader(meta));
            _xr.parse(is);
        } catch (SAXException e) {
            log.error("Parsing problem of component:" + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Other ComponentParser problem:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void startDocument() throws SAXException {
        super.startDocument();
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) throws SAXException {
        String fullName = namespaceURI + "#" + localName;

        if (fullName.equals(DIDLNS + "#Item")) {
            itemCapture = true;
            icount = icount + 1;
        }
        if (itemCapture && icount == 2) {
            item = "metadata";
        }
        if (resourceCapture == false && fullName.equals(DIDLNS + "#Resource")) {
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
            att.put("item", item);
            att.put("format", strformat);
            att.put("created", strcreated);
            att.put("creator", strcreator);
            att.put("extent", strextent);
            att.put("semantic", strsemantic);

        } else if (resourceCapture) {
            resource.append("<");
            resource.append(qName);
            int length = attrs.getLength();
            for (int i = 0; i < length; ++i) {
                resource.append(" ");
                String aEName = attrs.getQName(i);
                resource.append(aEName);
                resource.append("=\"");
                resource.append(XmlUtil.encode(attrs.getValue(i)));
                resource.append("\"");
            }
            resource.append(">");
        }
        if (fullName.equals(DIDLNS + "#Component")) {
            componentCapture = true;
            ccount = ccount + 1;
        }
        if (componentCapture && fullName.equals(DCNS + "#format")) {
            formatCapture = true;
        }
        if (componentCapture && fullName.equals(DCNS + "#type")) {
            semanticCapture = true;
        }
        if (componentCapture && fullName.equals(DCTERMSNS + "#created")) {
            createdCapture = true;
        }
        if (componentCapture && fullName.equals(DCNS + "#creator")) {
            creatorCapture = true;
        }
        if (componentCapture && fullName.equals(DCTERMSNS + "#extent")) {
            extentCapture = true;
        }
    }

    public void characters(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);

        if (resourceCapture) {
            resource.append(XmlUtil.encode(s));
        }
        if (formatCapture) {
            format.append(XmlUtil.encode(s));
        }
        if (createdCapture) {
            created.append(XmlUtil.encode(s));
        }
        if (creatorCapture) {
            creator.append(XmlUtil.encode(s));
        }
        if (semanticCapture) {
            semantic.append(XmlUtil.encode(s));
        }
        if (extentCapture) {
            extent.append(XmlUtil.encode(s));
        }
    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);
        if (fullName.equals(DCNS + "#format")) {
            formatCapture = false;
            strformat = format.toString();
        }
        if (fullName.equals(DCTERMSNS + "#created")) {
            createdCapture = false;
            strcreated = created.toString();
        }
        if (fullName.equals(DCNS + "#creator")) {
            creatorCapture = false;
            strcreator = creator.toString();
            creator = new StringBuffer();
        }
        if (fullName.equals(DCNS + "#type")) {
            semanticCapture = false;
            strsemantic = semantic.toString();
            semantic = new StringBuffer();
        }
        if (fullName.equals(DCTERMSNS + "#extent")) {
            extentCapture = false;
            strextent = extent.toString();
        }
        if (fullName.equals(DIDLNS + "#Resource")) {
            resourceCapture = false;
            try {
                _rprocessor.getData(resource.toString(), att);
            } catch (Exception e) {
                log.error("component handler problem" + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
            att.clear();
        }
        if (resourceCapture == true) {
            resource.append("</");
            resource.append(qName);
            resource.append(">");
        }
        if (fullName.equals(DIDLNS + "#Component")) {
            componentCapture = false;
            rcount = 0;
            format.setLength(0);
            created.setLength(0);
            extent.setLength(0);
        }
        if (fullName.equals(DIDLNS + "#Item")) {
            itemCapture = false;
            item = "stream";
            att.put("item", "stream");
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
