/*
 * AttributableBase.java
 *
 * Created on October 10, 2005, 11:59 AM
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
import info.repo.didl.AttributeType;
import java.util.HashSet;
import java.util.Set;

/**
 * <code>AttributableBase</code> will provide a base implementation for
 * each DIDL element.  It extends <code>DIDLBase</code> and manages a 
 * <code>Set</code> of <code>AttributeType</code> objects.  
 * <p>
 * In this version we only support DIDL, Item, Component, Resource, Statement,
 * and DIDLInfo elements defined by DIDL specification.
 * <p>
 * This class provides a base implementation class which each DIDL
 * element will extend.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see DIDLBase
 * @see AttributeType
 */
public abstract class AttributableBase extends DIDLBase {
    private Set<AttributeType> attributes;
    
    /** 
     * Creates a new instance of AttributableBase 
     * */
    public AttributableBase() {
        this.attributes = new HashSet<AttributeType>();
    }
    
    /**
     * Gets the Set of AttributeTypes
     * @return the Set of AttributeTypes
     */
    public Set<AttributeType> getAttributes() {
        return attributes;
    }
    
    /**
     * Sets the java.util.Set of AttributeType
     * @param attributes the set of attributes
     */
    public void setAttributes(final Set<AttributeType> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException();
        }
        
        this.attributes = attributes;
    }
}
