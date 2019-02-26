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

package gov.lanl.identifier.sru;

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <code>SRUDCParser</code> is a SAX parser implementation for DC
 * content type.  The de-serializers passes in an empty DC object 
 * and parses the InputStream to populate the DC object.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class SRUDCParser extends DefaultHandler implements SRUConstants {
    private StringBuffer dcvalue = new StringBuffer();
    private boolean inline = false;
    private SRUDC dc;
    
    /**
     * Parses an InputStream to populate the specified DC object
     * @param dc empty DC object to populate
     * @param stream InputStream containing DC XML fragment
     * @throws Exception error occurred during parse routine
     */
    public void parse(SRUDC dc, InputStream stream) throws Exception {
        this.dc=dc;
        XMLReader parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
        parser.setFeature("http://xml.org/sax/features/namespaces", true);
        parser.setContentHandler(this);
        parser.parse(new InputSource(stream));
    }
    
    /**
     * Receive notification of the beginning of an element. 
     * If DC namespace is found in the uri, the element is flagged for extraction.
     */
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException {
        try {
            if (DC_NAMESPACE.equals(uri)){
                inline=true;
            } else
                throw new SRUException("not a DC namespace");
        } catch (Exception ex){
            throw new SAXException(ex);
        }
    }
    
    /**
     * Receive notification of the end of an element.
     * For DC uri, checks localName against Key name, inserts dc value upon match.
     */
    public void endElement(
            String uri,
            String localName,
            String qName)
            throws SAXException{
        try {
            if (DC_NAMESPACE.equals(uri)){
                for (SRUDC.Key key: SRUDC.Key.values()){
                    if (localName.equals(key.getField())){
                        dc.addKey(key, dcvalue.toString());
                        break;
                    }
                    
                }
                if (dc.getKeys()==null){
                    throw new SAXException(localName + " is not expected");
                }
            } else{
                throw new SAXException(uri +" namespace is not expected");
            }
            
        } catch (Exception e) {
            throw new SAXException("SAXException in endElement", e);
        }
    }
    
    /**
     * Appends element to a string buffer if element is flagged for extraction
     */
    public void characters(char[] ch, int start, int length)
    throws SAXException {
        if (inline) {
            dcvalue.append(new String(ch, start, length));
        }
    }
    
    
}
