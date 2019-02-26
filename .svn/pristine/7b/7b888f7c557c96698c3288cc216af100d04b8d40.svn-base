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

package gov.lanl.xmltape.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represent key information for each TapeRecord
 */

public class IndexItem implements Comparable, Serializable {
    
    private static final long serialVersionUID = 1L;

    String identifier;

    String datestamp;

    // index of tape metadata record
    long index;

    //offset of tape metadata record
    long offset;

    //setSpecs
    ArrayList setSpecs;

    /**
     * Gets unique identifier for TapeRecord
     * @return
     *      identifier of TapeRecord
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets Last Modified Datestamp
     * @return
     *     datastamp of TapeRecord
     */
    public String getDatestamp() {
        return datestamp;
    }

    /**
     * Gets byte count into XMLTape at which the record begins
     * @return
     *       byte at which the record starts
     */
    public long getIndex() {
        return index;
    }

    /**
     * Gets byte length of XML Tape Record
     * @return
     *       length of record, in bytes
     */
    public long getOffset() {
        return offset;
    }

    /**
     * Gets array list of set specs
     * @return
     *       list of sets specs associated with TapeRecord
     */
    public ArrayList getSetSpecs() {
        return setSpecs;
    }

    /**
     * Sets unique identifier for TapeRecord
     * @param identifier
     *      identifier of TapeRecord
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Sets Last Modified Datestamp
     * @param datestamp
     *     datastamp of TapeRecord
     */
    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    /**
     * Sets byte count into XMLTape at which the record begins
     * @param index
     *       byte at which the record starts
     */
    public void setIndex(long index) {
        this.index = index;
    }

    /**
     * Sets byte length of XML Tape Record
     * @param offset
     *       length of record, in bytes
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

    /**
     * Sets array list of set specs
     * @param setSpecs
     *       list of sets specs associated with TapeRecord
     */
    public void setSetSpecs(ArrayList setSpecs) {
        this.setSpecs = setSpecs;
    }

    /**
     * Determine if two IndexItems are equivalent
     */
    public boolean equals(Object obj) {
        IndexItem item = (IndexItem) obj;
        return (item.identifier.equals(identifier))
                && (item.datestamp.equals(datestamp)) && (item.index == index)
                && (item.offset == offset);
    }

    /**
     * Casts IndexItem field values to delimited string; 
     * a colon is used as delimiter.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(identifier).append(":").append(datestamp).append(":").append(
                index).append(":").append(offset);
        if (setSpecs != null) {
            for (Iterator it = setSpecs.iterator(); it.hasNext();) {
                sb.append((String) (it.next()));
            }
        }
        return sb.toString();
    }

    /**
     * Implements Comparable compareTo for identifier equivalence
     * @param arg0
     *          IndexItem for comparison
     * @return
     *          0 if EQUAL, -1 if DIFFERENT
     */
    public int compareTo(Object arg0) {
        final int EQUAL = 0;
        final int DIFFERENT = -1;
        IndexItem i = (IndexItem) arg0;
        
        if (this.getIdentifier().equals(i.getIdentifier())) 
               return EQUAL;
        else 
               return DIFFERENT;
    }

}
