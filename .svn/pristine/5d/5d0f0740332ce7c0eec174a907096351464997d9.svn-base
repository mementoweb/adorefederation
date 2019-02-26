/*
 * SimpleAttributeCondition.java
 *
 * Created on January 16, 2006, 12:30 PM
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

package info.repo.didl.impl.serialize;

import info.repo.didl.serialize.AttributeStrategyConditionType;

/**
 * <code>SimpleAttributeCondition</code> are used to define the deserialization
 * strategy type for an attribute.  Register a AttributeStrategy for each content type.
 * 
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class SimpleAttributeCondition implements AttributeStrategyConditionType {
    public String namespace;
    public Class implementation;
    
    /**
     * Creates a new SimpleAttributeCondition instance. 
     * @param namespace unique ns for strategy type
     * @param implementation content type class
     */
    public SimpleAttributeCondition(final String namespace, final Class implementation) {
        if (implementation == null) {
            throw new IllegalArgumentException();
        }
        this.namespace = namespace;
        this.implementation = implementation;
    }
    
    /**
     * A non-symmetric equivalence relation is required here:
     * <br>
     * When:<br>
     *  - a namespace is set and the request doesn't match, the match fails<br>
     *  - a namespace field is null, it's a wildcard.<br>
     *
     */
    public boolean match(final String namespace ){
        if (this.namespace==namespace)
            return true;
        
        if (this.namespace!=null){
            if (namespace==null)
                return false;
            else if (!this.namespace.equals(namespace))
                return false;
        }
        return true;
        
    }
    
    /**
     * Gets the Content Type Object Class
     */
    public Class getImplClass(){
        return implementation;
    }
    
}
