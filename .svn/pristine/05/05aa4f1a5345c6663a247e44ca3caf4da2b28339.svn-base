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

import gov.lanl.archive.trans.TransProperties;
import gov.lanl.util.xml.XmlUtil;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * TapeParser.java
 * Program for parsing XML tape and spliting it to didl and base64 stream.
 * 
 * @author ludab
 */

public class TapeParser extends DefaultHandler {
    private StringBuffer _didl = new StringBuffer();

    private String _didid; // id of didlfragment

    private String _didcreated; //creation date

    private boolean didlCapture = false;

    FragmentProcessor _fragmentprocessor;

    String _streamtype;

    String _prefix = TransProperties.getLocalDataStreamPrefix();

    private XMLReader _xr;

    public TapeParser(java.io.InputStream stream, FragmentProcessor ip) throws Exception {
        super();
        _xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        _xr.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        _xr.setContentHandler(this);
        _fragmentprocessor = ip;

        InputSource is = new InputSource(stream);
        is.setEncoding("UTF-8");
        _xr.parse(is);

    }

    public void startDocument() throws SAXException {
        super.startDocument();
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) throws SAXException {
        String fullName = namespaceURI + "#" + localName;

        if (fullName.equals(namespaceURI + "#DIDL")) {
            didlCapture = true;
            _didl.setLength(0);
            for (int i = 0; i < attrs.getLength(); i++) {
                String shortname = attrs.getLocalName(i);
                if (shortname.equals("DIDid")) {
                    _didid = attrs.getValue(i);
                }
                if (shortname.equals("DIDcreated")) {
                    _didcreated = attrs.getValue(i);
                }
            }
        }

        if (didlCapture == true) {
            _didl.append("<");
            _didl.append(qName);
            int length = attrs.getLength();
            for (int i = 0; i < length; ++i) {
                _didl.append(" ");
                String aEName = attrs.getQName(i);
                _didl.append(aEName);
                _didl.append("=\"");
                _didl.append(XmlUtil.encode(attrs.getValue(i)));
                _didl.append("\"");
            }
            _didl.append(">");
        }
    }

    public void characters(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);

        if (didlCapture == true) {
            _didl.append(XmlUtil.encode(s));
        }
    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        String fullName = fullName(namespaceURI, localName);

        if (didlCapture == true) {
            _didl.append("</");
            _didl.append(qName);
            _didl.append(">");
        }
        if (fullName.equals(namespaceURI + "#DIDL")) {
            didlCapture = false;
            try {
                _fragmentprocessor.processDidl(_didl.toString(), _didid, _didcreated);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SAXException(e.getMessage());
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
