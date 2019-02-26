/*
 * DIDLBase.java
 *
 * Created on October 8, 2005, 4:37 PM
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
import info.repo.didl.DIDLVisitorType;

/**
 * <code>DIDLBase</code> represents all valid elements in the DIDL tree.
 * <p>
 * An element may have an XML id 
 * <a href="http://www.w3.org/TR/xml-id/">http://www.w3.org/TR/xml-id/</a>, 
 * and support visitor patterns.  
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
 */
public abstract class DIDLBase implements DIDLBaseType {
    private String identifier;
    
    /**
     * Get an object identifier
     * @return the object identifier
     */
    public String getId() {
        return identifier;
    }
    
    /**
     * Set an object identifier
     * @param id the object identifier
     */
    public void setId(final String identifier) {
        this.identifier = identifier;
    }
    
    /**
     * Visit each DIDL member with a DIDLVisitorType
     * @param visitor the DIDLVisitorType to be used
     */
    public abstract void accept(DIDLVisitorType visitor);
}
