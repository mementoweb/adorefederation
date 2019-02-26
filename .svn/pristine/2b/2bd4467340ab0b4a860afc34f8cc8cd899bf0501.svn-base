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
 * OAI ListSets Request
 */
public class ListSets {
    ORG.oclc.oai.harvester.verb.ListSets listsets = null;

    /**
     * Construtor using the OAI baseURl - Gathers list of sets
     * @param baseURL - OAI baseURL
     * @throws Exception
     */
    public ListSets(String baseURL) throws Exception {
        listsets = new ORG.oclc.oai.harvester.verb.ListSets(new URL(baseURL));
    }

    /**
     * Get the time of OAI repository response
     * @return OAI compliant date as a String
     */
    public String getResponseDate() {
        return listsets.getResponseDate();
    }

    /**
     * Get the OAI server response as in XML form
     * @return String of response in XML form
     */
    public String getResponseXML() {
        return new String(listsets.getResponseBuffer());

    }
}
