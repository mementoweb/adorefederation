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

package gov.lanl.identifier;

import java.io.Serializable;
import gov.lanl.util.DateUtil;

public class Identifier implements Comparable, Serializable {
    
    private static final long serialVersionUID = 1L;
    private String recordId;
    private String identifier;
    private String datestamp;
    
    /** 
     * Constructs a new Identifier object with a null datestamp
     * @param recordId
     *        DocumentID / RecordID in which the identifier resides
     * @param identifier
     *        Unique identifier for content or datastream
     */
    public Identifier(String recordId, String identifier) {
        this(recordId, identifier, null);
    }
    
    /**
     * Constructs a new Identifier object with a datestamp, 
     * useful when duplicate identifiers may exist (i.e. contentids)
     * @param recordId
     *        DocumentID / RecordID in which the identifier resides
     * @param identifier
     *        Content / datastream level identifier
     * @param datestamp
     *        Document / Record creation date (i.e. DIDLDocumentCreated)
     *        
     */
    public Identifier(String recordId, String identifier, String datestamp) {
        this.setRecordId(recordId);
        this.setIdentifier(identifier);
        this.setDatestamp(datestamp);
    }
    
    /**
     * Gets the parent record identifier
     */
    public String getRecordId() {
        return recordId;
    }
    
    /**
     * Sets the parent record identifier, in which the content 
     * level identifier exists
     * @param didlDocId
     *        Parent record identifier
     */
    public void setRecordId(String didlDocId) {
        this.recordId = didlDocId;
    }
    
    /**
     * Gets the content / datastream level id
     * @return
     *        Content / datastream level identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /** 
     * Sets the content / datastream level id
     * @param identifier
     *        Content / datastream level identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    /**
     * Gets the document creation datestamp
     * @return
     *        Document creation datestamp in UTC format
     */
    public String getDatestamp() {
        return datestamp;
    }
    
    /**
     * Sets the document creation datestamp
     * @param datestamp
     *        Document creation datestamp in UTC format
     */
    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }
    public boolean equals(Object obj) {
        Identifier item = (Identifier) obj;
        return (item.recordId.equals(recordId))
                && (item.identifier.equals(identifier)) 
                && (item.datestamp == datestamp);
    }
    
    /**
     * Casts Identifier field values to delimited string; 
     * a colon is used as delimiter.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(recordId).append(":").append(identifier).append(":").append(datestamp);
        return sb.toString();
    }

    /**
     * Implements Comparable compareTo for identifier equivalence
     * @param arg0
     *          Identifier for comparison
     * @return
     *          0 if EQUAL, -1 if LESSER, 1 if GREATER
     */
    public int compareTo(Object arg0) {
        final int EQUAL = 0;
        final int LESSER = -1;
        final int GREATER = 1;
        Identifier i = (Identifier) arg0;
        
        if (this.equals(i)) 
               return EQUAL;
        else if(i.getDatestamp() != null && this.getDatestamp() != null) {
            String a = i.getDatestamp();
            String b = this.getDatestamp();
            if (DateUtil.utc2Date(b).after(DateUtil.utc2Date(a))) {
                return LESSER;
            } else 
                return GREATER;
        } else
               return LESSER;
    }
}
