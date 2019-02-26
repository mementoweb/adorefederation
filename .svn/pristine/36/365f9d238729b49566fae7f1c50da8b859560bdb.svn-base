/*
 * DC.java
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

package org.adore.didl.content;

import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DC implements DIDLSerializerType, DIDLDeserializerType {
    
	/** DC Namespace Constant - http://purl.org/dc/elements/1.1/ */
    public static final String NAMESPACE = "http://purl.org/dc/elements/1.1/";
    /** DC SchemaLocation Constant - http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd */
    public static final String SCHEMA_LOCATION = "http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd";
    /** DC Namespace Prefix Constant - dc */
    public static final String PREFIX = "dc";
    private Key key;
    private String value;
    
    /**
     * An enumeration of keys defined by DC
     */
    public enum Key {
        TITLE("title"), CREATOR("creator"), SUBJECT("subject"), DESCRIPTION("description"), PUBLISHER("publisher"),
        CONTRIBUTOR("contributor"), DATE("date"), TYPE("type"), FORMAT("format"), IDENTIFIER("identifier"),
        SOURCE("source"), LANGUAGE("language"), RELATION("relation"), COVERAGE("coverage"), RIGHTS("rights");
        
        private String value=null;
        Key(String value) {
            this.value = value;
        }
        
        public String value() {
            return value;
        }
        
    };
    
    /** 
     * Creates a new DC instance 
     */
    public DC() {
    }
    
    /** 
     * Creates a new DC instance using the specified key/value pair
     */
    public DC(Key key, String value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * Sets Key type for DC instance
     */
    public void setKey(Key key){
        this.key=key;
    }
    
    /**
     * Sets value for DC instance
     */
    public void setValue(String value){
        this.value=value;
    }
    
    /** 
     * Gets the Key for the DC instance
     */
    public Key getKey() {
        return key;
    }
    
    /**
     * Gets the value for DC instance
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Writes set key/value pair as DC XML element
     */
    public void write(OutputStream stream, java.lang.Object obj) throws DIDLSerializationException{
        DC dc=(DC)obj;
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(stream,XMLConstants.DEFAULT_ENCODING), true);
            out.print("<"+PREFIX+":");
            out.print(dc.getKey().value());
            out.print(" "+"xmlns:"+PREFIX+"=\""+ NAMESPACE+"\" " );
            out.print("xmlns:" + XMLConstants.XSI_PREFIX  + "=\"" + XMLConstants.XSI_NAMESPACE  + "\" ");
            out.print("xsi:schemaLocation=\""+NAMESPACE+" "+SCHEMA_LOCATION+"\">");
            out.print(dc.getValue());
            out.print("</"+PREFIX+":"+dc.getKey().value()+">");
            out.close();
        } catch (Exception ex) {
            throw new DIDLSerializationException(ex.getMessage());
        }
    }
    
    /**
     * Parses DC element to contstruct DC object
     */
    public  DC read(InputStream stream) throws DIDLSerializationException{
        try {
            DC dc=new DC();
            DCParser parser=new DCParser();
            parser.parse(dc,stream);
            return dc;
        } catch (Exception ex) {
            throw new DIDLSerializationException(ex.getMessage());
        }
        
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
