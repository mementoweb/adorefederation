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

package gov.lanl.util.oai;

import java.net.*;
import java.util.*;
import java.io.UnsupportedEncodingException;

/**
 * Token implementation borrowed from oaiarc.sourceforge.net
 * @author liu_x@cs.odu.edu 
 * @version March-13-2001 
 */
public class TokenModem {
    //encoding the token
    
    /**
     * Encode token in a abv. string version \n
     * Rules for encoding i=userinfo f=from u=until s=set m=metadataprefix
     */
    public static String encode(Token t) {
        return "m=" + t.prefix + clear("f", t.from) + clear("u", t.until)
                + clear("s", t.set) + clear("i", t.userinfo);
    }

    /**
     * Decode the abv. token
     * @return Token object version of abv. token
     */
    public static Token decode(String token) throws TokenformatException {
        Token record;
        try {
            String value = token;
            value = URLDecoder.decode(token, "UTF-8");
            StringTokenizer st = new StringTokenizer(value, "&");
            int count = 0;
            String prefix = null, from = null, until = null, set = null, userinfo = null;
            for (; st.hasMoreTokens();) {
                count++;
                String piece = st.nextToken();
                switch (piece.charAt(0)) {
                case 'm':
                    prefix = piece.substring(2);
                    break;
                case 'f':
                    from = piece.substring(2);
                    break;
                case 'u':
                    until = piece.substring(2);
                    break;
                case 's':
                    set = piece.substring(2);
                    break;
                case 'i':
                    userinfo = piece.substring(2);
                    break;
                default:
                    throw new TokenformatException("token format error");
                }

            }
            if (count < 4)
                throw new TokenformatException("token format error");
            record = new Token(from, until, set, prefix, userinfo);

        } //end try
        catch (Exception e) {
            System.out.println(e);
            throw new TokenformatException("token format error");
        }
        return record;
    }

    private static String clear(String label, String value) {
        try {
            if (value == null)
                return "";
            else
                return URLEncoder.encode("&", "UTF-8") + label + "=" + URLEncoder.encode(value, "UTF-8");
                //return "|" + label + "=" + URLEncoder.encode(value, "UTF-8");
        }
        //not possible exception
        catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
            return null;
        }
    }
}
