/*
 * DescriptorBaseType.java
 *
 * Created on October 21, 2005, 4:54 PM
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

package info.repo.didl;

import java.util.List;

/**
 * The <code>DescriptableBaseType</code> represents elements descriptable by DIDL Descriptors,
 * including Item, Component, and Descriptor itself. DescriptableBaseType provides the primary access
 * of <code>DescriptorType</code>
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */
public interface DescriptableBaseType extends AttributableBaseType {
    /**
     * Get a list of descriptors
     * @return a list of DescriptorType
     */
    public List<DescriptorType> getDescriptors();
    
    /**
     * Add a new sub descriptor
     * @param descriptor the Descriptor to be added
     * @return the descriptor added
     */
    public DescriptorType addDescriptor(DescriptorType descriptor);
    
    /**
     * Replace a sub descriptor with a new one
     * @param n the descriptor to be replaced
     * @param o the old descriptor
     * @return the descriptor replaces or null when the descriptor was not found
     */
    public DescriptorType replaceDescriptor(DescriptorType n, DescriptorType o);
    
    /**
     * Delete a sub descriptor
     * @param descriptor the descriptor to be deleted
     * @return the descriptor deleted or null when the descriptor was not found
     */
    public DescriptorType removeDescriptor(DescriptorType descriptor);
}
