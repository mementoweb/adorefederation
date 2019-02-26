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
import java.util.ArrayList;
import java.util.Iterator;

public class GetRecord {
    ORG.oclc.oai.harvester.verb.GetRecord gr = null;
    String collectionId;
    private ArrayList records = new ArrayList();

    /**
     * OAI Record Request
     * @param baseURL - OAI baseURL
     * @param identifier - OAI Identifier
     * @param prefix - OAI metadataPrefix
     * @throws Exception
     */
    public GetRecord(String baseURL, String identifier, String prefix)
            throws Exception {
        gr = new ORG.oclc.oai.harvester.verb.GetRecord(new URL(baseURL),
                identifier, prefix);
        for (Iterator it = gr.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = new gov.lanl.util.oai.oaiharvesterwrapper.Record(
                    (ORG.oclc.oai.harvester.verb.Record) (it.next()));
            records.add(record);
        }
    }

    /**
     * Add baseURL to as setspec, a requirement of oai federator
     */
    public void setCollectionId(String id) {
	this.collectionId=id;
        for (Iterator it = records.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) (it
                    .next());
            Header header = record.getHeader();

            header.addSetSpec(new Sets(Sets.BASE_URL,id));
        }
    }

    /**
     * Get the number of records contained in GetRecord object
     * @return number of OAI Records
     */
    public int size() {
        return records.size();
    }

    /** 
     * Gets list of records generated by the constructor
     * @return ArrayList of OAI Records
     */
    public ArrayList getRecords() {
        return records;
    }

    /**
     * Gets to complete OAI GetRecord Response as unprocessed string
     * @return String version of GetRecord Response Buffer
     */
    public String getResponseXML() {
        return new String(gr.getResponseBuffer());

    }
}