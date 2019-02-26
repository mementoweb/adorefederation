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

package gov.lanl.xmltape.resolver.xquery.sru;

import java.io.InputStream;
import java.util.Vector;

import gov.lanl.util.sru.SRUException;
import gov.lanl.util.sru.SRURecord;
import gov.lanl.util.sru.SRUValuePair;
import gov.lanl.util.xml.XmlUtil;

public class SRUXQuery implements SRURecord {
    public static final String NAMESPACE = "http://library.lanl.gov/2007-02/aDORe/SRURecordCount/";
    public static final String SCHEMA_LOCATION = "http://purl.lanl.gov/aDORe/schemas/2007-02/SRURecordCount.xsd";
    public static final String PREFIX = "sru_xq";
    public static final String TAG = "RecordCount";
    public final static String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    public final static String XSI_PREFIX = "xsi";
    
    private Vector<SRUValuePair> valuepairs = new Vector<SRUValuePair>();
    
    public void addKey(String key, String value) {
        valuepairs.add(new SRUValuePair(key, value));
    }
    
    public Vector<String> getKeys() {
        Vector v = new Vector(Key.values().length);
        for (Key k : Key.values())
            v.add(k.getField());
        return v;
    }
    
    public Vector<SRUValuePair> getValuePairs() {
        return valuepairs;
    }
    
    public SRURecord read(InputStream stream) throws SRUException {
        try {
            SRUXQuery xq = this;
            SRUXQueryParser parser = new SRUXQueryParser();
            parser.parse(xq,stream);
            return xq;
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
    }
    
    public String toXML() throws SRUException {
        SRURecord xq = this;
        try{
            StringBuffer out = new StringBuffer("");
            out.append("<" + PREFIX + ":");
            out.append(TAG);
            out.append(" " + "xmlns:" + PREFIX + "=\"" + NAMESPACE + "\" " );
            out.append("xmlns:" + XSI_PREFIX  + "=\"" + XSI_NAMESPACE  + "\" " );
            out.append(XSI_PREFIX + ":schemaLocation=\"" + NAMESPACE + " " + SCHEMA_LOCATION + "\">");
            for (SRUValuePair vp : xq.getValuePairs()) {
                String field = vp.getField();
                out.append("<");
                out.append(field);
                out.append(" ");
                out.append(Key.getAttributes(field));
                out.append(">");
                out.append(XmlUtil.encode(vp.getValue()));
                out.append("</");
                out.append(field);
                out.append(">");
            }
            out.append("</" + PREFIX + ":" + TAG + ">");
            return out.toString();
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
    }
    
    public enum Key {
        IDENTIFIER("dc:identifier", "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd\""), 
        EXTENT("dcterms:extent", "xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://purl.org/dc/terms/ http://purl.lanl.gov/aDORe/schemas/2006-09/dcterms.xsd\"");
        
        private final String field;
        private final String atts;
        
        Key(String field, String nsInfo) {
            this.field = field;
            this.atts = nsInfo;
        }
        
        public String getField() {
            return field;
        }
        
        public String getAttributes() {
            return atts;
        }
        
        public static String getAttributes(String field) {
            for (Key k : Key.values()) {
                if (k.getField().equals(field))
                    return k.getAttributes();
            }
            return null;
        }
    }

    public String getNamespace() {
        return NAMESPACE;
    };
}
