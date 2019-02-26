/*
 * DiadmDeserializer.java
 *
 * Created on November 28, 2005, 4:31 PM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package org.adore.didl.serialize;

import info.repo.didl.serialize.DIDLSerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;
import org.adore.didl.content.Diadm;
import org.adore.didl.content.XMLConstants;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <code>DiadmDeserializer</code>  defines the XML deserializer for
 * all Diadm content type references.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DiadmDeserializer extends DefaultHandler implements info.repo.didl.serialize.DIDLDeserializerType {
	/** Diadm XML Namespace */
    public static final String DIADM_NAMESPACE = "http://library.lanl.gov/2005-08/aDORe/DIADM/";
    /** Diadm XML SchemaLocation */              
    public static final String DIADM_SCHEMA_LOCATION = "http://purl.lanl.gov/aDORe/schemas/2006-09/DIADM.xsd";
    /** Diadm XML Prefix */
    public static final String DIADM_PREFIX = "diadm";
    /** Diadm XML Local Name */
    public static final String DIADM_LOCALNAME="DIADM";
    
    private static final int BUFFER_SIZE = 1024;
    private boolean isDiadm=false;                // True if we're parsing Diadm
    private boolean inline = false;               // True if we're parsing inline data...
    private ByteArrayOutputStream inlineBuffer;   // Buffer containing the inline data...
    private PrintWriter buffer;                   // Writer to write inline data to...
    private Map<String,String> prefixMap;         // Namespace declarations in scope...
    private Diadm diadm;
    
    /**
     * Reads a Diadm XML Fragment from specified InputStream 
     * and returns a populated Diadm instance. 
     */
    public Diadm read(InputStream stream) throws DIDLSerializationException {
        diadm=new Diadm();
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader(XMLConstants.DEFAULT_PARSER_NAME);
            parser.setFeature("http://xml.org/sax/features/namespaces", true);
            parser.setFeature("http://xml.org/sax/features/validation", false);
            parser.setContentHandler(this);
            parser.parse(new InputSource(stream));  
        } catch (Exception e) {
            throw new DIDLSerializationException(e);
        }
        
        return diadm;
    }
    
    /**
     * Receive notification of the beginning of an element. 
     * If DIADM namespace, flags checks for DC and DCTerms elements.
     */
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException {
        try {
            
            if (DIADM_NAMESPACE.equals(uri)){
                isDiadm=true;
            } else if (DC.NAMESPACE.equals(uri) ){
                startInline();
            } else if (DCTerms.NAMESPACE.equals(uri)){
                startInline();
            }
            else{
                throw new SAXException(uri +" namespace is not expected");
            }
            
            if (inline) {
                buffer.write("<" + qName);
                for (int i = 0; i < attributes.getLength() ; i++) {
                    buffer.write(" " + attributes.getQName(i) + "=\"" + escape(attributes.getValue(i)) + "\"");
                }
                
                for (Iterator it = prefixMap.keySet().iterator(); it.hasNext() ;) {
                    String prefix = (String) it.next();
                    buffer.write(" xmlns:" + prefix + "=\"" + escape(prefixMap.get(prefix)) + "\"");
                }
                
                buffer.write(">");
            }
        } catch (Exception ex){
            throw new SAXException(ex);
        }
    }
    
    /**
     * Receive notification of the end of an element.
     * If DIADM namespace, flags DC and DCTerms element closure.
     */
    public void endElement(
            String uri,
            String localName,
            String qName)
            throws SAXException{
        try {
            if (inline){
                buffer.write("</" + qName + ">");
            }
            if (DC.NAMESPACE.equals(uri)){
                endInline("dc");
            } else if(DCTerms.NAMESPACE.equals(uri)){
                endInline("dcterms");
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
            String out = escape(new String(ch, start, length));
            buffer.write(out.toCharArray(), 0, out.length());
        }
    }
    
    /**
     * Adds prefix/uri to the prefix hashmap; will init a null prefix hashmap
     */
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        if (prefixMap == null) {
            prefixMap = new HashMap<String,String>();
        }
        prefixMap.put(prefix, uri);
    }
    
    /**
     * Sets the prefix hashmap to null
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        prefixMap = null;
    }
    
    // Start deserialization of Content

    private void startInline() {
        // From this moment we have to store the inline data into memory...
        inline = true;
        inlineBuffer = new ByteArrayOutputStream();
        buffer = new PrintWriter(inlineBuffer);
    }
    
    // End deserialization of Content
    
    /**
     *@param type the type element, can be either "dc" or "dcterms"
     */
    private void endInline(String type) throws Exception {
        inline = false;
        buffer.close();
        inlineBuffer.close();
        if (type.equals("dc")){
            DC dc=new DC();
            dc=dc.read(new ByteArrayInputStream(inlineBuffer.toByteArray()));
            diadm.addDC(dc);
        }
        if (type.equals("dcterms")){
            DCTerms term=new DCTerms();
            term=term.read(new ByteArrayInputStream(inlineBuffer.toByteArray()));
            diadm.addDCTerms(term);
        }
        
        inlineBuffer = null;
    }
    
    private String escape(String str) {
        return str.replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot")
        .replaceAll("'", "&apos");
    }  
    
    
       
    /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException {
        throw new DIDLSerializationException("no property is supported");
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
         throw new DIDLSerializationException("no property is supported");
    }
}
