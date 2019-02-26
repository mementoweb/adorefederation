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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * OAI ListRecords Request
 */
public class ListRecords {
    static Logger log = Logger.getLogger(ListRecords.class.getName());

    int sleeptimeunit = 10000;//10 second

    private ArrayList<Record> records = new ArrayList<Record>();

    private String resumptionToken;

    private byte[] buffer;

    String responseDate;

    String request;
    
    String collectionId=null;

    ORG.oclc.oai.harvester.verb.ListRecords lr = null;

    /**
     * Default Constructor OAI ListRecords Request
     * @param baseurl - OAI baseURL
     * @param from - OAI from value (e.g 2005-01-18)
     * @param until - OAI until value (e.g 2005-07-18)
     * @param sets - OAI sets value
     * @param prefix - OAI metadataPrefix (e.g. oai_dc)
     * @throws Exception
     */
    public ListRecords(String baseurl, String from, String until, String sets,
            String prefix) throws Exception {
        lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(baseurl),
                from, until, sets, prefix);
        build();
        request = getParams(from, until, sets, prefix);
    }

    /**
     * Constructor using OAI resumption token
     * @param baseurl - OAI baseURL
     * @param token - OAI Resumption token provided by OAI Respository
     * @throws Exception
     */
    public ListRecords(String baseurl, String token) throws Exception {
        // In case of errors, try again for two times
        try {
            lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(baseurl), token);
        } catch (Exception ex) {
            log.warn(ex.toString());
            ex.printStackTrace();
            // Sleep one second
            Thread.sleep(sleeptimeunit);
            log.warn("Trying again...");
            lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(baseurl), token);
        }
        build();
        request = getParams(token);
    }

    /**
     * Current size of the OAI Records
     * @return Number of records
     */
    public int size() {
        return records.size();
    }

    public String getCollectionId(){
	return collectionId;
    }
    
    /**
     * Gets a list of OAI Records as an ArrayList
     * @return ArrayList of OAI Records
     */
    public ArrayList<Record> getRecords() {
        return records;
    }

    /**
     * Gets the OAI Resumption Token
     * @return OAI Resumption token
     */
    public String getResumptionToken() {
        return resumptionToken;
    }

    /** 
     * Returns the OAI ListRecords Response Buffer
     * @return byte array of response buffer
     */
    public byte[] getResponseBuffer() {
        return buffer;
    }

    private void build() {
        for (Iterator it = lr.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = new gov.lanl.util.oai.oaiharvesterwrapper.Record(
                    (ORG.oclc.oai.harvester.verb.Record) (it.next()));
            records.add(record);

        }
        resumptionToken = lr.getResumptionToken();
        buffer = lr.getResponseBuffer();
        responseDate = lr.getResponseDate();
    }

    /**
     * Add baseURL as setspec, a requirement of oai federator
     */
    public void setCollectionId(String id) {
	this.collectionId=id;
        for (Iterator it = records.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) (it
                    .next());
            Header header = record.getHeader();
            header.addSetSpec(new Sets(Sets.BASE_URL, id));
        }
    }

    /**
     * Get the time of OAI repository response
     * @return OAI compliant date as a String
     */
    public String getResponseDate() {
        return responseDate;
    }

    /**
     * Get the OAI request which was made
     * @return OAI request
     */
    public String getRequest() {
        return request;
    }

    private String getParams(String from, String until, String set,
            String metadataPrefix) throws IOException {
        try {
            StringBuffer query = new StringBuffer();
            query.append("verb=ListRecords");
            if (until != null && until.length() != 0) {
                query.append("&until=");
                query.append(URLEncoder.encode(until, "UTF-8"));
            }
            if (from != null && from.length() != 0) {
                query.append("&from=");
                query.append(URLEncoder.encode(from, "UTF-8"));
            }
            if (set != null && set.length() != 0) {
                query.append("&set=");
                query.append(URLEncoder.encode(set, "UTF-8"));
            }
            query.append("&metadataPrefix=");
            query.append(URLEncoder.encode(metadataPrefix, "UTF-8"));
            return query.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Construct the query portion of the http request.
     * 
     * @param resumptionToken
     *            the resumptionToken
     * @return a String containing the query portion of the http request.
     */
    private String getParams(String resumptionToken) throws IOException {

        try {
            StringBuffer query = new StringBuffer();
            query.append("verb=ListRecords");
            query.append("&resumptionToken=");
            query.append(URLEncoder.encode(resumptionToken, "UTF-8"));
            return query.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
}
