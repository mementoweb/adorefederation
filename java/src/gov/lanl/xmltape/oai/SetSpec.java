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

package gov.lanl.xmltape.oai;

import java.net.*;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class SetSpec {

    static Logger log = Logger.getLogger(SetSpec.class.getName());
    
    private String type;
    private String value;
    
    /**
     * Creates sets with type (collection, or format) 
     * and plain value (without encoding)
     */
    public SetSpec(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Create setSpec from a hierarchical setSpec
     */
    public SetSpec(String spec) throws SetformatException {
        if (spec.indexOf(':') == -1) {
            type = spec;
            value = null;
        } else {
            int idx = spec.indexOf(':');
            type = spec.substring(0, idx);
            value = decodeValue(spec.substring(idx + 1));
        }
    }

    /**
     * Gets a formatted version of the current SetSpec
     * The aDORe framework uses the following syntax for 
     * setSpec entries: $type:$url_encoded_value
     * 
     * @return formated and encoded setSpec String
     */
    public String getSetSpec() {
        StringBuffer sb = new StringBuffer();
        sb.append(type);

        if (value != null) {
            sb.append(":");
            sb.append(encodeValue(value));
        }
        return sb.toString();

    }

    private String decodeValue(String input) {
        //first replace * with %
        String str = input.replace('*', '%');
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.warn(ex.toString());
            return null;
        }
    }

    private String encodeValue(String input) {
        String str = null;
        try {
            str = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.warn(ex.toString());
            return null;
        }
        return str.replace('%', '*');
    }

    /**
     * Gets the current setSpec type (i.e. format, collection, etc.)
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the current setSpec values
     */
    public String getValue() {
        return value;
    }

    /**
     * Equality check for two SetSpec objects
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof SetSpec)) {
            return false;
        }
        SetSpec spec = (SetSpec) obj;
        return (this.getSetSpec().equals(spec.getSetSpec()));
    }

    /**
     * Generate the output format of:
     * <set>
     *   <setSpec></setSpec> 
     *   <setName></setName> 
     * </set>
     */
    public String getxml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<set><setSpec>").append(getSetSpec()).append(
                "</setSpec><setName>");
        if (value == null)
            sb.append(type);
        else
            sb.append(value);
        sb.append("</setName></set>");
        return sb.toString();
    }
}
