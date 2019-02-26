/*
 * DOMList.java
 *
 * Created on November 28, 2005, 7:59 PM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package info.repo.didl.impl;

import info.repo.didl.DIDLBaseType;
import info.repo.didl.DIDLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The <code>DIDLBaseList</code> maintains an ArrayList of DIDLBaseType elements.
 * This class provides a generic wrapper for various DIDL elements.
 * <p>Example:<br>
 * <code>DIDLBaseList<ResourceType> resources = new DIDLBaseList<ResourceType>();</code>
 * 
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */

public class DIDLBaseList<T extends DIDLBaseType>{
    private List<T> list;
    
    /** 
     * Creates a new instance of DIDLBaseList
     * */
    public DIDLBaseList() {
        list =new ArrayList<T>();
    }
    
    /**
     * Gets the list of objects associated with this DIDLBaseList instance
     * @return an ArrayList of extended DIDLBase objects
     */
    public List<T> getList() {
        return list == null ? null : Collections.unmodifiableList(list);
    }
    
    /**
     * Add an extended DIDLBase object to list
     * @param record an extended DIDLBase object
     * @return record instance added to list
     */
    public T add(T record) {
        list.add(record);
        return record;
    }
    
    /**
     * Replace extended DIDLBase object contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     * @throws DIDLException record not found exception
     */
    public T replace(T n, T o) throws DIDLException {
        if (list == null) {
            throw new DIDLException(DIDLException.NOT_FOUND, "record not found");
        }
        
        int index = list.indexOf(o);
        
        if (index == -1) {
            throw new DIDLException(DIDLException.NOT_FOUND, "record not found");
        } else {
            list.set(index, n);
        }
        return o;
    }
    
    /**
     * Remove an object from the DIDLBaseList
     * @param record object to be removed from the list
     * @return instance of removed object
     * @throws DIDLException record not found exception
     */
    public T remove(T record) throws DIDLException {
        if (list == null) {
            throw new DIDLException(DIDLException.NOT_FOUND, "record not found");
        }
        
        if (list.remove(record)) {
            return record;
        } else {
            throw new DIDLException(DIDLException.NOT_FOUND, "record not found");
        }
    }
}
