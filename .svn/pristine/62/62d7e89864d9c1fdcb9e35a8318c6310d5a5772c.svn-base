/*
 * ComponentType.java
 *
 * Created on October 8, 2005, 10:16 AM
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
 *
 * The <code>ComponentType</code> groups Resource elements with a set of 
 * Descriptors containing descriptive information about the resource. A 
 * component may have any attributes from other namespace. The 
 * <code> ComponentType</code> provides access mechanism to these information.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */
public interface ComponentType extends DescriptableBaseType {
    /**
     * Get a list of resources
     * @return a list of resources
     */
    public List<ResourceType> getResources();
    
    /**
     * Add a new resource
     * @param resource a new resource
     * @return the resource added
     */
    public ResourceType addResource(ResourceType resource);
    
    /**
     * Replace a resource with a new one
     * @param n the new resource
     * @param o the old resource
     * @return the resource replaced or null when the resource was not found
     */
    public ResourceType replaceResource(ResourceType n, ResourceType o);
    
    /**
     * Delete a resource from the component
     * @param resource the resource to be deleted
     * @return the deleted resource
     */
    public ResourceType removeResource(ResourceType resource);
}
