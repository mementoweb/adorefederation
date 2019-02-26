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

package gov.lanl.semantic.registry;

import java.io.StringReader;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Represents record in the semantic registry; XML Semantic Registry values
 * are parsed and set into fields for easier retrieval.
 * 
 * sample semantic registry record<br>
 *  <sem:semantic xmlns:sem="http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/" <br>
 *                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
 *                xsi:schemaLocation="http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/ http://purl.lanl.gov/aDORe/schemas/2006-09/SemanticRegistry.xsd"><br>
 *    <sem:identifier>info:lanl-repo/sem/1</pr:identifier><br>
 *    <dc:identifier xmlns:dc="http://purl.org/dc/elements/1.1/"
 *                   xsi:schemaLocation="http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd">info:lanl-repo/sem/1</dc:identifier><br>
 *    <dc:title xmlns:dc="http://purl.org/dc/elements/1.1/"
 *              xsi:schemaLocation="http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd">Bibliographic</dc:title><br>
 *    <sem:DIDentity>urn:mpeg:mpeg21:2002:02-DIDL-NS#Component</sem:DIDentity><br>
 *  </sem:semantic><br>
 *  
 */
public class SemanticItem extends DefaultHandler {

    String oaidatestamp;

    String oaiidentifier;

    String record;

    ArrayList dcIdentifiers = new ArrayList();

    String title;

    String description;

    String DIDentity;

    StringBuffer stringbuffer = new StringBuffer();;

    boolean isDcIdentifier = false, isTitle = false,
            isDescription = false, isDIDentity = false;

    /**
     * Creates a new  simple SemanticItem instance
     * @param identifier
     *         the semantic record's unique identifier
     * @param datestamp
     *         the semantic record's datestamp
     */
    public SemanticItem(String identifier, String datestamp) {
        this(identifier, datestamp, null, false);
    }

    /**
     * Creates a SemanticItem populated with an XML Semantic record
     * @param identifier
     *         the semantic record's unique identifier
     * @param datestamp
     *         the semantic record's datestamp
     * @param r
     *         the semantic xml record
     * @param validate
     *         do you want the xml record validated
     */
    public SemanticItem(String identifier, String datestamp, String r,
            boolean validate) {
        oaidatestamp = datestamp;
        oaiidentifier = identifier;
        record = r;

        XMLReader xr = null;
        try {
            xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            xr.setContentHandler(this);
            xr.setErrorHandler(this);

            xr.setFeature("http://xml.org/sax/features/validation", validate);
            xr.setFeature("http://apache.org/xml/features/validation/schema", validate);
            xr.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
            xr.parse(new InputSource(new StringReader(r)));
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    //===========================================================
    // SAX DocumentHandler methods
    //===========================================================

    public void startElement(String uri, String name, String qname,
            Attributes attrs) throws SAXException {
        if (qname.equals("dc:identifier"))
            isDcIdentifier = true;
        else if (qname.equals("dc:title"))
            isTitle = true;
        else if (qname.equals("dc:description"))
            isDescription = true;
        else if (qname.equals("fmt:DIDentity"))
            isDIDentity = true;

        stringbuffer.setLength(0);

    }

    public void endElement(String uri, String name, String qname)
            throws SAXException {

        if (qname.equals("dc:identifier")) {
            isDcIdentifier = false;
            dcIdentifiers.add(stringbuffer.toString());
        } else if (qname.equals("dc:title")) {
            isTitle = false;
            title = stringbuffer.toString();
        } else if (qname.equals("dc:description")) {
            isDescription = false;
            description = stringbuffer.toString();
        } else if (qname.equals("fmt:DIDentity")) {
            isDIDentity = false;
            DIDentity = stringbuffer.toString();
        }

        stringbuffer.setLength(0);
    }

    public void characters(char ch[], int start, int length) {
        String s = new String(ch, start, length);
        stringbuffer = stringbuffer.append(s);
    }

    /**
     * Gets the unique identifier of the semantic
     * (e.g. info:lanl-repo/sem/1)
     */
    public String getOAIIdentifier() {
        return oaiidentifier;
    }

    /**
     * Gets the creation/last modified datestamp of the semantic
     */
    public String getDatestamp() {
        return oaidatestamp;
    }

    /**
     * Gets the XML record
     */
    public String getRecord() {
        return record;
    }

    /**
     * Gets the list of associated identifiers
     */
    public ArrayList getDCIdentifiers() {
        return dcIdentifiers;
    }

    /**
     * Gets the title of the semantic
     * (e.g. Bibliographic)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets a verbose description of semantics usage
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the DIDEntity value
     * (e.g. urn:mpeg:mpeg21:2002:02-DIDL-NS#Component)
     */
    public String getDIDentity() {
        return DIDentity;
    }

    /**
     * String Concatenation of id:datestamp:record
     */
    public String toString() {
        return oaiidentifier + ":" + oaidatestamp + ":" + record;
    }

    /**
     * Translates to flat text file
     * identifier\tontology\tmimetype\tDIDentity
     */
    public String toFlattext() {
        StringBuffer sb = new StringBuffer();
        sb.append(oaiidentifier).append("\t");
        sb.append(title == null ? "" : title);
        sb.append("\t");
        sb.append(description == null ? "" : "\"" + description + "\"");
        sb.append("\t").append(DIDentity);
        sb.append("\t\t");
        return sb.toString();
    }
}
