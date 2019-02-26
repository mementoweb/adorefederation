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

package gov.lanl.format.registry;

import java.io.StringReader;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Represents record in the format registry; XML Format Registry values
 * are parsed and set into fields for easier retrieval.
 * 
 * sample format registry record <br>
 *  <fmt:format xmlns:fmt="http://library.lanl.gov/2005-08/aDORe/FormatRegistry/" <br>
 *              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  <br>
 *              xsi:schemaLocation="http://library.lanl.gov/2005-08/aDORe/FormatRegistry/ http://purl.lanl.gov/aDORe/schemas/2006-09/FormatRegistry.xsd"><br>
 *     <fmt:identifier>info:lanl-repo/fmt/9</fmt:identifier><br>
 *     <dc:identifier xmlns:dc="http://purl.org/dc/elements/1.1/" xsi:schemaLocation="http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd">info:lanl-repo/fmt/9</dc:identifier><br>
 *     <fmt:mimetype>application/xml</fmt:mimetype><br>
 *     <fmt:DIDentity>urn:mpeg:mpeg21:2002:02-DIDL-NS#Component</fmt:DIDentity><br>
 *     <dc:format xmlns:dc="http://purl.org/dc/elements/1.1/" xsi:schemaLocation="http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd">http://library.lanl.gov/rtf/vendor_schemas/Biosis/2006-02/BIOSISPreviews.dtd</dc:identifier><br>
 *     <dc:source xmlns:dc="http://purl.org/dc/elements/1.1/" xsi:schemaLocation="http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd">info:lanl-repo/creator/LANL-RTF</dc:identifier><br>
 *     <dc:description xmlns:dc="http://purl.org/dc/elements/1.1/" xsi:schemaLocation="http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd">Defines BIOSIS Entities and BIOSOS XML Syntax</dc:identifier><br>
 *   </fmt:format><br>
 *  
 */
public class FormatItem extends DefaultHandler {

    String oaidatestamp;

    String oaiidentifier;

    String record;

    ArrayList dcIdentifiers = new ArrayList();

    String mimetype;

    String ontology;

    String DIDentity;

    String dcFormat;
    
    String dcSource;
    
    String dcDescription;
    
    StringBuffer stringbuffer = new StringBuffer();;

    boolean isDcIdentifier = false, isMimetype = false,
            isOntology = false, isDIDentity = false,
            isDcFormat = false, isDcSource = false, 
            isDcDescription = false;

    /**
     * Creates a new  simple FormatItem instance
     * @param identifier
     *         the format record's unique identifier
     * @param datestamp
     *         the format record's datestamp
     */
    public FormatItem(String identifier, String datestamp) {
        this(identifier, datestamp, null, false);
    }

    /**
     * Creates a FormatItem populated with an XML Format record
     * @param identifier
     *         the format record's unique identifier
     * @param datestamp
     *         the format record's datestamp
     * @param r
     *         the format xml record
     * @param validate
     *         do you want the xml record validated
     */
    public FormatItem(String identifier, String datestamp, String r,
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
            xr.setFeature("http://apache.org/xml/features/validation/schema",validate);
            xr.setFeature("http://xml.org/sax/features/namespace-prefixes",true);
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
        else if (qname.equals("fmt:mimetype"))
            isMimetype = true;
        else if (qname.equals("fmt:ontology"))
            isOntology = true;
        else if (qname.equals("fmt:DIDentity"))
            isDIDentity = true;
        else if (qname.equals("dc:format"))
            isDcFormat = true;
        else if (qname.equals("dc:source"))
            isDcSource = true;
        else if (qname.equals("dc:description"))
            isDcDescription = true;
        stringbuffer.setLength(0);

    }

    public void endElement(String uri, String name, String qname)
            throws SAXException {

        if (qname.equals("dc:identifier")) {
            isDcIdentifier = false;
            dcIdentifiers.add(stringbuffer.toString());
        } else if (qname.equals("fmt:mimetype")) {
            isMimetype = false;
            mimetype = stringbuffer.toString();
        } else if (qname.equals("fmt:ontology")) {
            isOntology = false;
            ontology = stringbuffer.toString();
        } else if (qname.equals("fmt:DIDentity")) {
            isDIDentity = false;
            DIDentity = stringbuffer.toString();
        } else if (qname.equals("dc:format")) {
            isDcFormat = false;
            dcFormat = stringbuffer.toString();
        } else if (qname.equals("dc:source")) {
            isDcSource = false;
            dcSource = stringbuffer.toString();
        } else if (qname.equals("dc:description")) {
            isDcDescription = false;
            dcDescription = stringbuffer.toString();
        }
        stringbuffer.setLength(0);
    }

    public void characters(char ch[], int start, int length) {
        String s = new String(ch, start, length);
        stringbuffer = stringbuffer.append(s);
    }

    /**
     * Gets the unique identifier of the format
     */
    public String getOAIIdentifier() {
        return oaiidentifier;
    }

    /**
     * Gets the creation/last modified datestamp of the format
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
     * Gets the mimetype of the format
     * (e.g. application/xml)
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Gets the URI of the associated ontology
     */
    public String getOntology() {
        return ontology;
    }

    /**
     * Gets the DIDEntity value
     * (e.g. urn:mpeg:mpeg21:2002:02-DIDL-NS#Component)
     */
    public String getDIDentity() {
        return DIDentity;
    }

    /**
     * Gets the dc:format value
     * (e.g. http://library.lanl.gov/rtf/vendor_schemas/Biosis/2006-02/BIOSISPreviews.dtd)
     */
    public String getDCFormat() {
        return dcFormat;
    }
    
    /**
     * Gets the dc:source value
     * (e.g. info:lanl-repo/creator/LANL-RTF)
     */
    public String getDCSource() {
        return dcSource;
    }
    
    /**
     * Gets the dc:description value
     */
    public String getDCDescription() {
        return dcDescription;
    }
    
    /**
     * String Concatenation of id:datestamp:record
     */
    public String toString() {
        return oaiidentifier + ":" + oaidatestamp + ":" + record;
    }

    /**
     * Translates to flat text file used by lanl-format
     * identifier\tontology\tmimetype\tDIDentity\tdcFormat\tdcSource\tdcDescription
     */
    public String toFlattext() {
        StringBuffer sb = new StringBuffer();
        sb.append(oaiidentifier).append("\t");
        sb.append(ontology == null ? "" : ontology);
        sb.append("\t");
        sb.append(mimetype == null ? "" : mimetype);
        sb.append("\t").append(DIDentity);
        sb.append(dcFormat == null ? "" : dcFormat);
        sb.append(dcSource == null ? "" : dcSource);
        sb.append(dcDescription == null ? "" : dcDescription);
        sb.append("\t\t");
        return sb.toString();
    }
}
