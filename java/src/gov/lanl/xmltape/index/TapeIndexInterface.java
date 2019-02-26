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

import java.util.List;
import java.util.Vector;

/**
 * Tapeindex can be supplied by various platform; 
 * (BasicTapeIndex, plaintext, Berkeley DB, etc).
 * All must implement interface below
 *   
 */

public interface TapeIndexInterface {

    /**
     * Read an index item,
     * 
     * @param identifier
     *            identifier
     * @return the matched record if found, otherwise return null
     */
    public IndexItem getIndexItem(String identifier) throws IndexException;

    /**
     * Adds an IndexItem instance to the IndexItem TreeSet
     * @param item
     *            IndexItem to be added to current index instance
     */
    public void putIndexItem(IndexItem item) throws IndexException;

    /**
     * Open an indexer
     * 
     * @param readonly
     *            should the indexer be allowed to be modified?
     */
    public void open(boolean readonly) throws IndexException;

    /**
     * Close an indexer
     * @throws IndexException
     */
    public void close() throws IndexException;

    /**
     * Gets list of OAI SetsSpecs
     * @return 
     *            List of OAI Set Names
     */
    public List getOAISetSpecs();

    /**
     * Read number of indexitems into a Vector, the cursor is decided by from
     * and identifier it can be considered that 'from' is primary index and
     * identifier is secondary index.
     * 
     * @param identifier
     *            cursor, if identifier is null, start from beginning
     * @param count
     *            number of records to be read
     * @param from
     *            the place to start read the index, inclusive.
     * @param until
     *            the place to end. inclusive.
     * @return records. if the size of Vector is less than requested number, the
     *         end of index is reached
     *  
     */
    public Vector read(String identifier, int count, String from, String until)
            throws IndexException;

    /**
     * Read number of indexitems into a Vector with SetSpec
     * 
     * @param identifier
     *            cursor, if identifier is null, start from beginning
     * @param count
     *            number of records to be read
     * @param setSpec
     *            OAI SetSpecification
     * @return records. if the size of Vector is less than requested number, the
     *         end of index is reached
     *  
     */
    public Vector read(String identifier, int count, String setSpec,
            String from, String until) throws IndexException;

    /**
     * Delete IndexItem for the specified identifier
     * @param identifier
     *            identifier of IndexItem to be deleted from index
     */
    public void delete(String identifier) throws IndexException;

    /**
     * List number of records
     * 
     * @param setSpec
     *            read specific setSpec, if setSpec is null, return all records
     */
    public long count(String setSpec) throws Exception;
    
    /**
     * Sets path to directory containing index files
     * @param indexDir
     *            Absolute path to dir containing index
     */
    public void setIndexDir(String indexDir);
    
    /**
     * Sets the tape name for the specified database name.
     * @param tapeName
     *            XMLTape name minus extension
     */
    public void setTapeName(String tapeName);
    
    /**
     * Verifies that the active index is valid
     * (i.e. contains necessary indexes and not corrupt)
     */
    public boolean isValid() throws IndexException;
}
