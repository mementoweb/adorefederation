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

package gov.lanl.util.oai.oaiharvesterwrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class Sets {
    
    public final static int BASE_URL = 0;
    public final static int COLLECTION = 1;
    public final static int FORMAT = 2;
    // all, for silly requests such as set=baseurl; set=profile; set=collection
    public final static int ALL = 3;

    private int type;
    private String value;
    
    static Logger log = Logger.getLogger(Sets.class.getName());

    /**
     * Create sets with type (baseurl, collection, or profile) and real value
     * @param type
     * @param value
     */
    public Sets(int type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Create sets with setSpec
     *  
     */
    public Sets(String spec) throws SetformatException {
        if (spec == null) {
            type = ALL;
            value = null;
        } else if (spec.indexOf(':') == -1) {
            if (spec.equals("baseurl") || spec.equals("collection")
                    || spec.equals("format")) {
                type = ALL;
                value = null;
            } else {
                log.warn("Error parsing " + spec);
                throw new SetformatException(spec);
            }
        } else {
            int idx = spec.indexOf(':');
            String key = spec.substring(0, idx);
            value = decodeSetSpec(spec.substring(idx + 1));
            if (key.equals("baseurl"))
                type = BASE_URL;
            else if (key.equals("collection"))
                type = COLLECTION;
            else if (key.equals("format"))
                type = FORMAT;
            else
                throw new SetformatException(spec);
        }
    }

    public String getSetSpec() {
        StringBuffer sb = new StringBuffer();
        switch (type) {
        case BASE_URL:
            sb.append("baseurl:");
            break;
        case COLLECTION:
            sb.append("collection:");
            break;
        case FORMAT:
            sb.append("format:");
            break;
        }

        sb.append(encodeSetSpec(value));
        log.debug("setSpec=" + sb.toString());
        return sb.toString();

    }

    public static String decodeSetSpec(String input) {
         // first replace * with %
        String str = input.replace('*', '%');
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.warn(ex.toString());
            return null;
        }

    }

    public static String encodeSetSpec(String input) {
        String str = null;
        try {
            str = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.warn(ex.toString());
            return null;
        }
        return str.replace('%', '*');
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
