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

package gov.lanl.util.sru;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

public class SRUSearchRetrieveResponse implements SRUConstants {
    private String version = "1.1";
    private HashMap<String, String> searchRequests = new HashMap<String, String>();
    private Vector<SRURecord> records = new Vector<SRURecord>();

    public int getNumOfRecords() {
        return records.size();
    }
    public Vector<SRURecord> getRecords() {
        return records;
    }
    public void setRecords(Vector<SRURecord> records) {
        this.records = records;
    }
    public void addRecord(SRURecord record) {
        this.records.add(record);
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public HashMap<String, String> getSearchRequest() {
        return searchRequests;
    }
    public void setSearchRequest(String key, String value) {
        this.searchRequests.put(key, value);
    }
    
    /**
     * Writes set key/value pair as DC XML element
     */
    public static void write(OutputStream stream, java.lang.Object obj) throws SRUException {
        SRUSearchRetrieveResponse r = (SRUSearchRetrieveResponse) obj;
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(stream, DEFAULT_ENCODING), true);
            // Open searchRetrieveResponse
            out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.print("<" + SRW_PREFIX + ":");
            out.print(TAG_SRU_SRR);
            out.print(" " + "xmlns:" + SRW_PREFIX + "=\"" + SRW_NAMESPACE + "\" " );
            out.print(">");
            // Open version
            out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_VERSION + ">");
            // Define version
            out.print(r.getVersion());
            // Close version
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_VERSION + ">");
            
            // Open numberOfRecords
            out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_NUMREC + ">");
            // Define numberOfRecords
            out.print(r.getNumOfRecords());
            // Close numberOfRecords
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_NUMREC + ">");
            
            // Open Records
            out.println("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_RECORDS + ">");
            // Iterate through records
            for (SRURecord record : r.getRecords()) {
                // Open Record
                out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_RECORD + ">");
                // Open recordSchema
                out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_SCHEMA + ">");
                // Define recordSchema
                out.print(record.getNamespace());
                // Close recordSchema
                out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_SCHEMA + ">");
                // Open recordPacking
                out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_PACKING + ">");
                // Define recordPacking
                out.print("xml");
                // Close recordPacking
                out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_PACKING + ">");
                // Open recordData
                out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_DATA + ">");
                // Define recordData
                out.print(record.toXML());
                // Close recordData
                out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_DATA + ">");
                out.println("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_RECORD + ">");
            }
            
            // Define the request echo
            out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_REQUEST + ">");
            
            // Set version
            out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_VERSION + ">");
            out.print(r.getVersion());
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_VERSION + ">");
           
            // Set query
            int i = 0;
            out.print("<" + SRW_PREFIX + ":" + TAG_SRU_SRR_REQUEST_QUERY + ">");
            for (java.util.Map.Entry e : r.getSearchRequest().entrySet()) {
                String phrase = e.getKey() + "=\"" + e.getValue() + "\"";
                if (i >0)
                    out.print(" AND " + phrase);
                else
                    out.print(phrase);
                i++;
            }
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_REQUEST_QUERY + ">");
            
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_REQUEST + ">");
            
            // Close records
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR_RECORDS + ">");
            // Close searchRetrieveResponse
            out.print("</" + SRW_PREFIX + ":" + TAG_SRU_SRR + ">");
            out.close();
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
    }
    
    /**
     * Parses SRUSearchRetrieveResponse XML to construct SRUSearchRetrieveResponse object
     */
    public static SRUSearchRetrieveResponse read(SRURecord record, InputStream stream) throws SRUException {
        try {
            SRUSearchRetrieveResponse sru = new SRUSearchRetrieveResponse();
            SRUSearchRetrieveResponseParser parser = new SRUSearchRetrieveResponseParser();
            parser.parse(sru,stream,record);
            return sru;
        } catch (Exception ex) {
            throw new SRUException(ex.getMessage());
        }
    }
}
