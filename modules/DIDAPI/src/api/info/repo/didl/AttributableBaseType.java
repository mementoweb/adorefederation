/*
 * AttributeBaseType.java
 *
 * Created on October 21, 2005, 4:52 PM
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

import java.util.Set;

/**
 * The <code>AttributeBaseType</code> inherits from <code>DIDLBaseType</code> and 
 * represents elements which may have "anyAttributes" from other namespaces, as 
 * defined by DID model. DIDL, Item, Component, Resource, Statement may have any 
 * attributes, while DIDLInfo cannot.
 * 
 * These attributes are grouped into a <code>Set</code> by namespace. The 
 * <code>AttributeType</code> holds values of each namespace.
 * <code>AttributeBaseType</code> provides mechanisms of accessing these attributes. 
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @see AttributeType
 */
public interface AttributableBaseType extends DIDLBaseType {
    /**
     * Get a AttributeType set.
     * @return a set of AttributeTypes
     */
    public Set<AttributeType> getAttributes();
    
    /**
     * Set a AttributeType set.
     * @param atts a set of AttributeTypes
     */
    public void setAttributes(Set<AttributeType> atts);
}