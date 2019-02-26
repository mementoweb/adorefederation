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
public class SRUDC implements SRUConstants {
    
    private Vector<ValuePair> key = new Vector<ValuePair>();
    
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
     * Stores a Key/Value Pair
     *
     */
    public class ValuePair {
        private String field;
        private String value;
        
        /**
         * Creates a new ValuePair 
         * @param field
         *        Element from Key enum
         * @param value
         *        String value associated with Key
         */
        public ValuePair(Key field, String value) {
            this.field = field.getField();
            this.value = value;
        }
        
        /**
         * Gets the Key's field name
         */
        public String getField() {
            return field;
        }
        
        /**
         * Gets the key's value
         */
        public String getValue() {
            return value;
        }
    }
    
    /** 
     * Creates a new DC instance 
     */
    public SRUDC() {
    }
    
    /**
     * Adds Key type for DC instance
     */
    public void addKey(String field, String value){
        this.key.add(new ValuePair(Key.valueOf(field), value));
    }
    
    /**
     * Adds Key type for DC instance
     */
    public void addKey(Key field, String value){
        this.key.add(new ValuePair(field, value));
    }
    
    /** 
     * Gets the HashMap of DC keys
     */
    public Vector<ValuePair> getKeys() {
        return key;
    }
    
    /**
     * Gets the Vector of DC values for the given field
     */
    public Vector<String> getKeys(Key field) {
        Vector<String> values = new Vector<String>();
        for (ValuePair vp : key) {
            if (field.getField().equals(vp.getField()))
                values.add(vp.getValue());
        }
        return values;
    }
    
    /**
     * Writes set key/value pair as DC XML element
     */
    public static String toXML(java.lang.Object obj) throws SRUException {
        SRUDC dc = (SRUDC) obj;
        try{
            StringBuffer out = new StringBuffer("");
            out.append("<" + DC_PREFIX + ":");
            out.append(DC_TAG);
            out.append(">");
            for (ValuePair vp : dc.getKeys()) {
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
     * Parses DC element to contstruct DC object
     */
    public static SRUDC read(InputStream stream) throws SRUException {
        try {
            SRUDC dc=new SRUDC();
            SRUDCParser parser = new SRUDCParser();
            parser.parse(dc,stream);
            return dc;
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
        
    }
}
