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

package gov.lanl.format.registry;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import ORG.oclc.oai.harvester.verb.Record;

/**
 * Performs a ListRecords operation on the Format Registry; 
 * provides a list of formats, as FormatItems
 *
 */
public class FormatIndex {
    ArrayList formatlists = new ArrayList();

    String _baseURL;

    /**
     * Read format index from format registry
     * 
     * @param baseurl
     *            OAI-PMH baseurl of format index
     * @param validate
     *            XML schema validation of FormatIndex
     */
    public FormatIndex(String baseurl, boolean validate) throws FormatException {
        try {
            _baseURL = baseurl;
            ORG.oclc.oai.harvester.verb.ListRecords lr;
            lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(_baseURL),
                    null, null, null, "format");

            for (Iterator it = lr.iterator(); it.hasNext();) {
                Record r = (Record) it.next();
                FormatItem fi = new FormatItem(r.getIdentifier(), r
                        .getDatestamp(), r.getMetadata(), validate);
                formatlists.add(fi);
            }

            while (lr.getResumptionToken() != null) {
                lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(
                        _baseURL), lr.getResumptionToken());
                for (Iterator it = lr.iterator(); it.hasNext();) {
                    Record r = (Record) it.next();
                    FormatItem fi = new FormatItem(r.getIdentifier(), r
                            .getDatestamp(), r.getMetadata(), validate);
                    formatlists.add(fi);
                }
            }
        } catch (Exception ex) {
            throw new FormatException("Error accessing format registry", ex);
        }
    }

    /**
     * Gets the list of registered formats
     * 
     * @return an array of FormatItem objects
     */
    public ArrayList getList() {
        return formatlists;
    }
}
