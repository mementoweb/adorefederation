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

/**
 * OAI ListIdentifiers Request
 *
 */
public class ListIdentifiers {
    private ArrayList headers = new ArrayList();

    private ArrayList identifiers = new ArrayList();

    private byte[] buffer;

    private String resumptionToken;

    String responseDate;

    String request;
    
    String collectionId;

    ORG.oclc.oai.harvester.verb.ListIdentifiers li;

    /**
     * Default Constructor OAI ListIdentifier Request
     * @param baseurl - OAI baseURL
     * @param from - OAI from value (e.g 2005-01-18)
     * @param until - OAI until value (e.g 2005-07-18)
     * @param sets - OAI sets value
     * @param prefix - OAI metadataPrefix (e.g. oai_dc)
     * @throws Exception
     */
    public ListIdentifiers(String baseurl, String from, String until,
            String sets, String prefix) throws Exception {
        li = new ORG.oclc.oai.harvester.verb.ListIdentifiers(new URL(baseurl),
                from, until, sets, prefix);
        build();

    }

    /**
     * Constructor using OAI resumption token
     * @param baseurl - OAI baseURL
     * @param token - OAI Resumption token provided by OAI Respository
     * @throws Exception
     */
    public ListIdentifiers(String baseurl, String token) throws Exception {
        li = new ORG.oclc.oai.harvester.verb.ListIdentifiers(new URL(baseurl),
                token);
        build();
    }

    /**
     * Current size of the OAI Headers
     * @return Number of headers
     */
    public int size() {
        return headers.size();
    }

    /**
     * Iterates through the list of headers and generates an XML form 
     * @return OAI Headers in XML form
     */
    public ArrayList getXMLHeaders() {
        ArrayList result = new ArrayList();
        for (Iterator it = headers.iterator(); it.hasNext();) {
            Header header = (Header) (it.next());
            result.add(header.getHeaderXML());
        }
        return result;
    }

    public String getCollectionId(){
	return collectionId;
    }
    
    /**
     * Gets a list of OAI Identifiers as an ArrayList
     * @return ArrayList of OAI Identifiers
     */
    public ArrayList getIdentifiers() {
        return identifiers;
    }

    /**
     * Gets the OAI Resumption Token
     * @return OAI Resumption token
     */
    public String getResumptionToken() {
        return resumptionToken;
    }

    /** 
     * Returns the OAI ListIdentifiers Response Buffer
     * @return byte array of response buffer
     */
    public byte[] getResponseBuffer() {
        return buffer;
    }

    private void build() {
        for (Iterator it = li.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Header header = new gov.lanl.util.oai.oaiharvesterwrapper.Header(
                    (ORG.oclc.oai.harvester.verb.Header) (it.next()));

            headers.add(header);
            identifiers.add(header.getIdentifier());
            //add baseurl sets to headers;
        }
        resumptionToken = li.getResumptionToken();
        buffer = li.getResponseBuffer();
        responseDate = li.getResponseDate();
        request = li.getRequest();
    }

    /**
     * Add baseURL to as setspec, a requirement of oai federator
     */
    public void setCollectionId(String id) {
	this.collectionId=id;
        for (Iterator it = headers.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Header header = (gov.lanl.util.oai.oaiharvesterwrapper.Header) (it
                    .next());
            header.addSetSpec(new Sets(Sets.BASE_URL,id));
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

}
