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

package gov.lanl.xmltape;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Parses a TapeAdmin XML fragment to populate a TapeAdmin instance
 * 
 */
public class TapeAdminParser extends DefaultHandler {
    private boolean xmlTapeIdCapture = false;

    private boolean arcFileIdCapture = false;

    private boolean processSoftwareCapture = false;

    private boolean processTimeCapture = false;

    private TapeAdmin ta;

    /**
     * Parse the SAX input source to populate the TapeAdmin
     * 
     * @param ta
     *            TapeAdmin instance to populate
     * @param xml
     *            SAX InputSource containing XML fragment
     * @throws Exception
     */
    public void parse(TapeAdmin ta, InputSource xml) throws Exception {
        this.ta = ta;
        XMLReader parser = XMLReaderFactory
                .createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        parser.setFeature("http://xml.org/sax/features/namespaces", true);
        parser.setContentHandler(this);
        parser.parse(xml);
    }

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (TapeAdmin.TB_NAMESPACE.equals(uri)) {
            if (localName.equals("XMLtapeId"))
                xmlTapeIdCapture = true;
            if (localName.equals("ARCfileId"))
                arcFileIdCapture = true;
            if (localName.equals("processSoftware"))
                processSoftwareCapture = true;
            if (localName.equals("processTime"))
                processTimeCapture = true;
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (TapeAdmin.TB_NAMESPACE.equals(uri)) {
            if (localName.equals("XMLtapeId"))
                xmlTapeIdCapture = false;
            if (localName.equals("ARCfileId"))
                arcFileIdCapture = false;
            if (localName.equals("processSoftware"))
                processSoftwareCapture = false;
            if (localName.equals("processTime"))
                processTimeCapture = false;
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (xmlTapeIdCapture)
            ta.setXmlTapeId(new String(ch, start, length));
        if (arcFileIdCapture)
            ta.addArcFileId(new String(ch, start, length));
        if (processSoftwareCapture)
            ta.setProcessSoftware(new String(ch, start, length));
        if (processTimeCapture)
            ta.setProcessTime(new String(ch, start, length));
    }
}
