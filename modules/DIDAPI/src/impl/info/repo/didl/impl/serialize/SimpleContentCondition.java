/*
 * SimpleContentCondition.java
 *
 * Created on January 16, 2006, 12:31 PM
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

import info.repo.didl.AttributeType;
import info.repo.didl.serialize.ContentStrategyConditionType;


/**
 * <code>SimpleContentCondition</code> are used to define the deserialization
 * strategy type for a Content Type.  Register a ContentStrategy for each content type.
 * 
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class SimpleContentCondition implements ContentStrategyConditionType{
    private final Class implementation;
    private final AttributeType attributes;
    private final String mimeType;
    private final String namespace;
    
    public SimpleContentCondition(
            final AttributeType attributes, final String mimeType,
            final String namespace, final Class implementation
            ) {
        if (implementation == null) {
            throw new IllegalArgumentException();
        }
        this.attributes = attributes;
        this.mimeType = mimeType;
        this.namespace = namespace;
        this.implementation = implementation;
    }
    
     /**
     * A non-symmetric equivalence relation is required here:
     * <br>
     * When:<br>
     *  - a namespace or mimetype is set and the request doesn't match, the match fails<br>
     *  - a namespace or mimetype field is null, it's a wildcard.<br>
     * <br>
     * This method doesn't do attributeType match
     */
    public boolean match(final AttributeType attributes, final String mimeType, final String namespace){
        if ((this.mimeType==mimeType)&&(this.namespace==namespace))
            return true;
        
        //check mimetype
        if (this.mimeType!=null){
            if (mimeType==null)
                return false;
            else if (!this.mimeType.equals(mimeType))
                return false;
        }
        
        //check namespace
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
