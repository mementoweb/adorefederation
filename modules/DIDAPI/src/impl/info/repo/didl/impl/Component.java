/*
 * Component.java
 *
 * Created on October 8, 2005, 4:59 PM
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
import info.repo.didl.ComponentType;
import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ResourceType;
import info.repo.didl.impl.DIDLBaseList;
import java.util.List;

/**
 * The <code>Component</code> extends <code>DescriptableBase</code> and provides 
 * a wrapper for <code>ComponentType</code> objects.  Each Component object
 * provides a clean interface to manage Component resources. 
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public final class Component extends DescriptableBase implements ComponentType {
    private final DIDLBaseList<ResourceType> resources = new DIDLBaseList<ResourceType>();
    
    /**
     * Creates a new Component instance
     */
    public Component() {}
    
    /**
     * Creates a new Component instance with associated identifier
     * @param id identifier of instance
     */
    public Component(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        setId(id);
    }
    
    /**
     * Gets the list of Resources
     * @return an ArrayList of ResourceType objects
     */
    public List<ResourceType> getResources() {
        return resources.getList();
    }
    
    /**
     * Add a ResourceType object to list
     * @param resource a ResourceType instance
     * @return instance added to list
     */
    public ResourceType addResource(final ResourceType resource) {
       return resources.add(resource);
    }
    
    /**
     * Replace ResourceType object contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public ResourceType replaceResource(final ResourceType n, final ResourceType o) {
        return resources.replace(n,o);
    }

    /**
     * Remove a ResourceType
     * @param record object to be removed from the list
     * @return instance of removed object
     */
    public ResourceType removeResource(final ResourceType resource) {
       return resources.remove(resource);
    }
    
    /**
     * Implements the visitor pattern for serialization
     */
    public void accept(final DIDLVisitorType visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        
        visitor.visitStart(this);
        
        for (DescriptorType descriptor: getDescriptors()) {
            descriptor.accept(visitor);
        }
        
        for (ResourceType resource: getResources()) {
            resource.accept(visitor);
        }
        
        visitor.visitEnd(this);
    }
}