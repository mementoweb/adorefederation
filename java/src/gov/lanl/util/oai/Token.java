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

/**
 * General Local OAI resumption token implemenation
 */
public class Token {

    public String from;

    public String until;

    public String set;

    public String prefix;

    public String userinfo;

    /**
     * Constructor to generate a OAI Token
     * @param from - OAI from (e.g. 2004-01-01T00:00:00Z)
     * @param until - OAI until (e.g. 2005-01-01T00:00:00Z)
     * @param set - OAI set, may be null
     * @param prefix - OAI metadataFormat (e.g. didl)
     * @param userinfo - Local User Info (e.g. http://pogo.lanl.gov/xmltape1/1973_bibtape|cfEMx8LI)
     */
    public Token(String from, String until, String set, String prefix,
            String userinfo) {
        this.from = from;
        this.until = until;
        this.set = set;
        this.prefix = prefix;
        this.userinfo = userinfo;
    }

    /** 
     * Returns Token instance variables as a string
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("from=").append(from).append("\n");
        sb.append("until=").append(until).append("\n");
        sb.append("set=").append(set).append("\n");
        sb.append("prefix=").append(prefix).append("\n");
        sb.append("userinfo=").append(userinfo).append("\n");
        return sb.toString();
    }

    /**
     * Compare two tokens, true if tokens are identical
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj == null)
            return false;

        if (!(obj instanceof Token)) {
            return false;
        } else {
            Token newtoken = (Token) obj;
            return toString().equals(newtoken.toString());
        }

    }

}
