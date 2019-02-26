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

import java.net.URL;

/**
 * OAI Identify Request
 *
 */
public class Identify {
    ORG.oclc.oai.harvester.verb.Identify identify = null;

    /**
     * Simple OAI Identify request 
     * @param baseURL - OAI baseURL
     * @throws Exception
     */
    public Identify(String baseURL) throws Exception {
        identify = new ORG.oclc.oai.harvester.verb.Identify(new URL(baseURL));
    }

    /**
     * Get OAI Reponse Date
     * @return OAI Response date as a String
     */
    public String getResponseDate() {
        return identify.getResponseDate();
    }

    /**
     * Get Entire OAI Identify Response as XML
     * @return OAI response in xml form
     */
    public String getResponseXML() {
        return new String(identify.getResponseBuffer());
    }
    
    /**
     * Get Granularity of the specified OAI Repository
     * @return String form of OAI Date/Time Granularity
     */
    
    public String getGranularity() {
        return new String(identify.getGranularity());

    }
}
