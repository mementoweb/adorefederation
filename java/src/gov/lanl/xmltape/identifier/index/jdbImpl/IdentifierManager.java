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

package gov.lanl.xmltape.identifier.index.jdbImpl;

import gov.lanl.identifier.Identifier;
import gov.lanl.identifier.IndexException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class IdentifierManager implements Serializable {

    private static final long serialVersionUID = 1L;
    
    Set<Identifier> identifiers = new TreeSet<Identifier>();
    
    /**
     * Adds an Identifier to the tree set, may be retrieved using getIdentifier
     * @param identifier - a valid Identifier object
     * @throws IndexException 
     */
    public void add(Identifier identifier) throws IndexException {
        if (identifier.getRecordId() != null && identifier.getIdentifier() != null)
            identifiers.add(identifier);
        else
            throw new IndexException("An Identifier ID or Record ID was not defined.");
    }
    
    /**
     * Get a Identifier using the Identifier ID and type. 
     * @param identifier - resources unique id
     * @return the Identifier object for the specified id
     * @throws IndexException
     */
    public Identifier getIdentifier(String identifier) throws IndexException {
        for (Iterator<Identifier> i = identifiers.iterator(); i.hasNext(); ) {
            Identifier c = (Identifier) i.next();
            if (c.getIdentifier().equals(identifier))
                return c;
          }
        throw new IndexException("Index for specified identifier " + identifier + " was not found.");
    }
    
    /**
     * Remove an Identifier using the Content Identifier ID.  
     */
    public boolean deleteIdentifier(String identifier) throws IndexException {
        return deleteIdentifier(identifier, -1);
    }
    
    /**
     * Remove an Identifier using the id and type
     */
    public boolean deleteIdentifier(String identifier, int type) throws IndexException {
        for (Iterator i = identifiers.iterator(); i.hasNext(); ) {
            Identifier c = (Identifier) i.next();
            if (c.getIdentifier().equals(identifier)) {
                i.remove();
                return true;
            } 
        }
        throw new IndexException("Index for specified identifier " + identifier + " was not found.");
    }
    
    /**
     * Gets an iterator for the set of Identifiers
     * @return
     *        Iterator containing Identifiers
     */
    public Iterator iterator() {
        return identifiers.iterator();
    }
    
    /**
     * Gets the size of the set of Identifiers
     * @return
     *       int of Identifiers
     */
    public int size() {
        return identifiers.size();
    }
}
