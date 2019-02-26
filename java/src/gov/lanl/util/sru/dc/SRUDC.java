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

package gov.lanl.util.sru.dc;

import gov.lanl.util.sru.SRUConstants;
import gov.lanl.util.sru.SRUException;
import gov.lanl.util.sru.SRURecord;
import gov.lanl.util.sru.SRUValuePair;

import java.io.InputStream;
import java.util.Vector;

/**
 * <code>SRUDC</code> is a DC object model for use in
 * SRU responses. 
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class SRUDC implements SRURecord, SRUConstants {
    public static final String DC_NAMESPACE = "info:srw/schema/1/dc-v1.1";
    public static final String DC_SCHEMA_LOCATION = "http://www.loc.gov/standards/sru/dc-schema.xsd";
    public static final String DC_PREFIX = "sru_dc";
    public static final String DC_TAG = "dc";
    
    private Vector<SRUValuePair> key = new Vector<SRUValuePair>();
    
    /**
     * An enumeration of keys defined by DC
     */
    public enum Key {
        TITLE("title"), CREATOR("creator"), SUBJECT("subject"), DESCRIPTION("description"), PUBLISHER("publisher"),
        CONTRIBUTOR("contributor"), DATE("date"), TYPE("type"), FORMAT("format"), IDENTIFIER("identifier"),
        SOURCE("source"), LANGUAGE("language"), RELATION("relation"), COVERAGE("coverage"), RIGHTS("rights");
        
        private final String field;
        
        Key(String field) {
            this.field = field;
        }
        
        public String getField() {
            return field;
        }
    };
    
    /** 
     * Creates a new DC instance 
     */
    public SRUDC() {
    }
    
    /**
     * Adds Key type for DC instance
     */
    public void addKey(String key, String value){
        this.key.add(new SRUValuePair(key, value));
    }
    
    
    /** 
     * Gets the SRUValuePair of DC keys
     */
    public Vector<SRUValuePair> getValuePairs() {
        return key;
    }
    
    /**
     * Gets the Vector of DC values for the given field
     */
    public Vector<String> getKeys(Key field) {
        Vector<String> values = new Vector<String>();
        for (SRUValuePair vp : key) {
            if (field.getField().equals(vp.getField()))
                values.add(vp.getValue());
        }
        return values;
    }
    
    /**
     * Writes set key/value pair as DC XML element
     */
    public String toXML() throws SRUException {
        SRUDC dc = this;
        try{
            StringBuffer out = new StringBuffer("");
            out.append("<" + DC_PREFIX + ":");
            out.append(DC_TAG);
            out.append(" " + "xmlns:" + DC_PREFIX + "=\"" + DC_NAMESPACE + "\" " );
            out.append("xmlns:" + XSI_PREFIX  + "=\"" + XSI_NAMESPACE  + "\" " );
            out.append("xsi:schemaLocation=\"" + DC_NAMESPACE + " " + DC_SCHEMA_LOCATION + "\">");
            for (SRUValuePair vp : dc.getValuePairs()) {
                out.append("<" + DC_PREFIX + ":");
                out.append(vp.getField());
                out.append(">");
                out.append(vp.getValue());
                out.append("</" + DC_PREFIX + ":");
                out.append(vp.getField());
                out.append(">");
            }
            out.append("</" + DC_PREFIX + ":" + DC_TAG + ">");
            return out.toString();
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
    }
    
    /**
     * Parses DC element to construct DC object
     */
    public SRUDC read(InputStream stream) throws SRUException {
        try {
            SRUDC dc = this;
            SRUDCParser parser = new SRUDCParser();
            parser.parse(dc,stream);
            return dc;
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
    }

    public Vector<String> getKeys() {
        Vector v = new Vector(Key.values().length);
        for (Key k : Key.values())
            v.add(k.getField());
        return v;
    }
    
    public String getNamespace() {
        return DC_NAMESPACE;
    };
}
