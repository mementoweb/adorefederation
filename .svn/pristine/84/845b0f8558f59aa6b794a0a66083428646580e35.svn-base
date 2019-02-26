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

package gov.lanl.xmltape;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * XML Document Container for storage within XMLTapes
 */

public class TapeRecord {
    final String identifier;

    final String datestamp;

    final String metadata;

    final ArrayList admin;

    final ArrayList setSpecs;

    /**
     * Constructor for records w/out metadata
     * @param identifier
     *            Unique Identifier for Tape Record
     * @param datestamp
     *            Last Modified Datastamp of Tape Record
     */
    public TapeRecord(String identifier, String datestamp) {
        this(identifier, datestamp, new ArrayList(), new ArrayList(), null);
    }

    /**
     * Constructor for records without set information
     * @param identifier
     *            Unique Identifier for Tape Record
     * @param datestamp
     *            Last Modified Datastamp of Tape Record
     * @param metadata
     *            XML Metadata for Tape Record
     */
    public TapeRecord(String identifier, String datestamp, String metadata) {
        this(identifier, datestamp, new ArrayList(), new ArrayList(), metadata);
    }

    /**
     * Constructor for records with set and metadata information
     * @param identifier
     *            Unique Identifier for Tape Record
     * @param datestamp
     *            Last Modified Datastamp of Tape Record
     * @param metadata
     *            XML Metadata for Tape Record
     * @param setSpecs
     *            ArrayList containing set spec information
     */
    public TapeRecord(String identifier, String datestamp, ArrayList setSpecs,
            String metadata) {
        this(identifier, datestamp, setSpecs, new ArrayList(), metadata);
    }

    /**
     * Constructor for records with set, metadata, and admin information
     * @param identifier
     *            Unique Identifier for Tape Record
     * @param datestamp
     *            Last Modified Datastamp of Tape Record
     * @param metadata
     *            XML Metadata for Tape Record
     * @param setSpecs
     *            ArrayList containing set spec information
     * @param admin 
     *            XML Admin Fragment for Tape Record
     */
    public TapeRecord(String identifier, String datestamp, ArrayList setSpecs,
            ArrayList admin, String metadata) {
        this.identifier = identifier;
        this.datestamp = datestamp;
        this.metadata = metadata;
        this.setSpecs = setSpecs;
        this.admin = admin;
    }

    /**
     * Gets the unique identifier
     * @return
     *       Unique Identifier for Tape Record
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets the last Modified Datastamp
     * @return
     *      Last Modified Datastamp of Tape Record
     */
    public String getDatestamp() {
        return datestamp;
    }

    /**
     * Gets metadata contained in tape record
     * @return
     *      XML Metadata for Tape Record
     */
    public String getMetadata() {
        return metadata;
    }

    /** 
     * Get the length of the metadata string value
     * @return
     *      Length of TapeRecord metadata
     */
    public int length() {
        return metadata.length();
    }

    /**
     * Get the list of set specs associated with TapeRecord
     * @return
     *      List of SetSpecs associated with record
     */
    public ArrayList getSetSpecs() {
        return setSpecs;
    }

    /**
     * Get the Tape Admin String
     * @return
     *     XML Fragment of TapeAdmin information
     */
    public ArrayList getAdmin() {
        return admin;
    }

    /**
     * Gets a String Version of TapeRecord
     * @return
     *     String of TapeRecord information
     */
    public String toString(){
             StringBuffer sb=new StringBuffer();
             sb.append("identifier : ").append(identifier).append("\n");
             sb.append("datestamp : ").append(datestamp).append("\n");
             for (Iterator it=admin.iterator();it.hasNext();){
                 String str=(String)(it.next());
                 sb.append("admin: ").append(str).append("\n");
             }
             sb.append("metadata: ").append(metadata);
             return sb.toString();
         }
}
