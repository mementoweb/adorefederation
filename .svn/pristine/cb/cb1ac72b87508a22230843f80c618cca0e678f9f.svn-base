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
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class IndexItemManager implements Serializable {

    private static final long serialVersionUID = 1L;
    
    Set<IndexItem> indexItemSet = new TreeSet<IndexItem>();
    
    /**
     * Adds a IndexItem to the tree set, may be retrieved using getIndexItem
     * @param indexItem - a valid IndexItem
     * @throws TapeException 
     */
    public void add(IndexItem indexItem) throws IndexException {
        if (indexItem.getIdentifier() != null)
            indexItemSet.add(indexItem);
        else
            throw new IndexException("A IndexItem Identifier was not defined.");
    }
    
    /**
     * Get a Index Item using the Index Item ID.  Index Item ID's are typically
     * the cdx filename, minus an extension.
     * @param identifier - resources unique id
     * @return the IndexItem for the specified id
     * @throws TapeException
     */
    public IndexItem getIndexItem(String identifier) throws IndexException {
        IndexItem c;
        for (Iterator i = indexItemSet.iterator(); i.hasNext(); ) {
            c = (IndexItem) i.next();
            if (c.getIdentifier().equals(identifier))
                return c;
          }
        throw new IndexException("Index Item for specified identifier " + identifier + " was not found.");
    }
    
    /**
     * Remove an Index Item using the Index Item ID.  
     */
    public boolean deleteIndexItem(String identifier) throws IndexException {
        for (Iterator i = indexItemSet.iterator(); i.hasNext(); ) {
            IndexItem c = (IndexItem) i.next();
            if (c.getIdentifier().equals(identifier)) {
                i.remove();
                return true;
            } 
        }
        System.out.println("Index Item for specified identifier " + identifier + " was not found.");
        return false;
    }
    
    /**
     * Gets an iterator for the set of IndexItems
     * @return
     *        Iterator containing IndexItems
     */
    public Iterator iterator() {
        return indexItemSet.iterator();
    }
    
    /**
     * Gets the size of the set of IndexItems
     * @return
     *       int of IndexItems
     */
    public int size() {
        return indexItemSet.size();
    }
}
