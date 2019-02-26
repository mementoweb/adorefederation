/*
 * DII.java
 * 
 * Created on October 13, 2005, 10:06 AM
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

package org.foo.didl.content;

import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <code>DII</code> defines the object container and serializers for
 * all DII content type references.
 * 
 * NOTE: This is a duplicate of org.adore.didl.DII. 
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DII implements DIDLSerializerType, DIDLDeserializerType {
    /** Type Definition for DII Identifier element */
    public static final short IDENTIFIER = 0;
    /** Type Definition for DII RelatedIdentifier element */
    public static final short RELATED_IDENTIFIER = 1;
    /** Type Definition for DII Type element */
    public static final short TYPE = 2;
    /** DII XML Namespace */
    public static final String DII_NAMESPACE = "urn:mpeg:mpeg21:2002:01-DII-NS";
    /** DII XML SchemaLocation */
    public static final String DII_SCHEMA_LOCATION = "http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-21_schema_files/dii.xsd/dii.xsd";
     
    private int type = -1;
    private String value;
    private static final int BUFFER_SIZE = 1024;
    
    private static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String SCHEMA_LOCATION_ATT = "schemaLocation";
   
    private static final String DII_PREFIX = "dii";
    private static final String XSI_PREFIX = "xsi";
    
    /** Creates a new DII instance */
    public DII() {
    }
    
    /** Creates a new DII instances with the specified type / value fields */
    public DII(int type, String value) {
        super();
        this.type = type;
        this.value = value;
    }
    
    /** Creates a new DII instances with the specified type / value fields */
    public DII(int type, URI uri) {
        super();
        this.type = type;
        this.value = uri.toString();
    }
    
    /**
     * Gets the element type of DII instance
     * @return type alias
     */
    public int getType() {
        return type;
    }
    
    /**
     * Gets the value of current DII instance
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Writes a DII object to the specifed OutputStream as a DII XML Fragment
     */
    public void write(OutputStream stream, Object object) throws DIDLSerializationException {
        DII dii = (DII) object;
        
        PrintWriter out = new PrintWriter(stream);
 
        out.print("<" + DII_PREFIX + ":");
        
        switch (dii.getType()) {
            case DII.IDENTIFIER:
                out.print("Identifier");
                break;
            case DII.RELATED_IDENTIFIER:
                out.print("RelatedIdentifier");
                break;
            case DII.TYPE:
                out.print("Type");
                break;
            default:
                throw new DIDLSerializationException("Unkown DII Type: " + dii);
        }
        
        out.print(" xmlns:" + DII_PREFIX + "=\"" + DII_NAMESPACE + "\"" );
        out.print(" xmlns:" + XSI_PREFIX + "=\"" + XSI_NAMESPACE + "\"" );
        out.print(" " + XSI_PREFIX + ":schemaLocation=\"" + DII_NAMESPACE + " " + DII_SCHEMA_LOCATION + "\"");
        out.print(">");
        
        out.print(dii.getValue());
        
        out.print("</" + DII_PREFIX + ":");
        
        switch (dii.getType()) {
            case DII.IDENTIFIER:
                out.print("Identifier");
                break;
            case DII.RELATED_IDENTIFIER:
                out.print("RelatedIdentifier");
                break;
            case DII.TYPE:
                out.print("Type");
                break;
            default:
                throw new DIDLSerializationException("Unknown DII Type: " + dii);
        }
        
        out.print(">");
        
        out.close();
    }
    
    private void addSchemaLocation(Node node) {
        Element el = (Element) node;
        el.setAttribute("xmlns:" + XSI_PREFIX, XSI_NAMESPACE);
        el.setAttributeNS(XSI_NAMESPACE, SCHEMA_LOCATION_ATT, DII_NAMESPACE + " " + DII_SCHEMA_LOCATION);
    }
    
    /**
     * Reads a DII XML Fragment from specified InputStream and returns a populated DII instance. 
     */
    public Object read(InputStream stream) throws DIDLSerializationException {
        DII dii = null;
        
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            
            int len = 0;
            while ( (len = stream.read(buffer)) != -1) {
                bout.write(buffer, 0 , len);
            }
            
            bout.close();
            
            String xml = bout.toString();
            
            int b = xml.indexOf(">");
            int e = xml.indexOf("<", b);
            String value = xml.substring(b+1,e);
                
            if (xml.matches("(s?).*<\\S+:Identifier .*")) {
                dii = new DII(DII.IDENTIFIER, value);
            }
            else if (xml.matches("(s?).*<\\S+:RelatedIdentifier .*")) {
                dii = new DII(DII.RELATED_IDENTIFIER, value);
            }
            else if (xml.matches("(s?).*<\\S+:Type .*")) {
                dii = new DII(DII.TYPE, value);
            }
            else {
                throw new DIDLSerializationException("Unknown DII Type");
            }
        } catch (IOException e) {
            throw new DIDLSerializationException(e.getMessage());
        } 
        
        return dii;
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