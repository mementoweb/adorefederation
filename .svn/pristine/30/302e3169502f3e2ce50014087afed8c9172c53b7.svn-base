/*
 * DescriptableBase.java
 *
 * Created on October 8, 2005, 4:43 PM
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

import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DescriptorType;

import java.util.List;

/**
 * The <code>DescriptableBase</code> extends <code>AttributableBase</code> and provides 
 * a wrapper for <code>DescriptorType</code> objects.  Each DescriptableBase object
 * provides a clean interface to the list of DescriptorType managed by DIDLBaseList.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see DIDLBaseList
 * @see DescriptorType
 */
public abstract class DescriptableBase extends AttributableBase {
    private final DIDLBaseList<DescriptorType> descriptors = new DIDLBaseList<DescriptorType>();
    
    /** 
     * Creates a new instance of DescriptableBase 
     * */
    public DescriptableBase() {
    }
    
    /**
     * Add a DescriptorType object to list
     * @param descriptor a DescriptorType instance
     * @return instance added to list
     */
    public DescriptorType addDescriptor(final DescriptorType descriptor) {
      return descriptors.add(descriptor);
    }
    
    /**
     * Gets the list of DescriptorType associated with this DescriptableBase instance
     * @return an ArrayList of DescriptorType objects
     */
    public List<DescriptorType> getDescriptors() {
      return descriptors.getList();
    }
    
    /**
     * Replace DescriptorType object contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public DescriptorType replaceDescriptor(final DescriptorType n, final DescriptorType o) {
       return descriptors.replace(n,o);
    }
    
    /**
     * Remove a DescriptorType from the DescriptableBase
     * @param record object to be removed from the list
     * @return instance of removed object
     */
    public DescriptorType removeDescriptor(final DescriptorType descriptor) {
       return descriptors.remove(descriptor);
    }
    
    /**
     * Abstract method for visitor pattern
     */
    public abstract void accept(DIDLVisitorType visitor);
}
