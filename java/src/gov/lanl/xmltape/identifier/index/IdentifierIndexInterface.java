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

package gov.lanl.xmltape.identifier.index;

import gov.lanl.identifier.Identifier;
import gov.lanl.identifier.IdentifierNotFoundException;
import gov.lanl.identifier.IndexException;

import java.util.ArrayList;

public interface IdentifierIndexInterface {

    /**
     * Open an index instance
     * 
     * @param readonly
     *            allow index modification
     */
    public void open(boolean readonly) throws IndexException;

    /**
     * Close current index instance
     * @throws IndexException
     */
    public void close() throws IndexException;
    
    /**
     * Close any databases associated with index; this will be called be for close which shuts down the env.
     */
    public void closeDatabases() throws IndexException;
    
    /**
     * Read an index item,
     * 
     * @param id
     *            identifier
     * @return the matched record if found, otherwise return null
     * @throws IdentifierNotFoundException 
     */
    public Identifier getIdentifier(String id) throws IndexException, IdentifierNotFoundException;

    /**
     * Read from index, list of identifiers
     * @param id
     *            content or datastream identifier
     * @return the matched records if found, otherwise return null
     */
    public ArrayList<Identifier> getIdentifiers(String id) throws IndexException;
    
    /**
     * Adds an Identifier instance to the Identifier TreeSet
     * @param id
     *            Identifier to be added to current index instance
     */
    public void putIdentifier(Identifier id) throws IndexException;
    
    /**
     * Adds Identifiers instance to the Identifier TreeSet
     * @param ids
     *            Identifiers to be added to current index instance
     */
    public void putIdentifiers(ArrayList<Identifier> ids) throws IndexException;
    
    /**
     * Delete Identifier for the specified identifier
     * @param identifier
     *            identifier of Identifier to be deleted from index
     * @throws IdentifierNotFoundException 
     */
    public void delete(String identifier) throws IndexException, IdentifierNotFoundException;
    
    /**
     * List number of records
     */
    public long count() throws IndexException;
    
    /**
     * Sets path to directory containing index files
     * @param indexDir
     *            Absolute path to dir containing index
     */
    public void setIndexDir(String indexDir);
    
    /**
     * Gets path to directory containing index files
     */
    public String getIndexDir();
    
    /**
     * Determine if current identifier is a record identifier
     * @param identifier - identifier to test
     * @return if docId, returns datestamp else returns null
     * @throws IndexException
     */
    public String isDocId(String identifier) throws IndexException;
    
    /**
     * Gets a String Array of identifiers
     * @throws IndexException 
     */
    public byte[] listIdentifiers() throws IndexException;
    
    /**
     * Verifies that the active index is valid
     * (i.e. contains necessary indexes and not corrupt)
     */
    public boolean isValid() throws IndexException;

}
