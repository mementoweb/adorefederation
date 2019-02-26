/*
 * DIDBaseType.java
 *
 * Created on October 8, 2005, 10:41 AM
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

/**
 * The <code>DIDLBaseType</code> interface is the primary data-type for the entire
 * DIDL access model. It represents all valid elements in the DIDL tree.
 * <p>An element may have an XML id 
 * <a href="http://www.w3.org/TR/xml-id/">http://www.w3.org/TR/xml-id/</a>, 
 * and support visitor pattern.  
 * <p>
 * In this version we only support DIDL, Item, Component, Resource, Statement,
 * and DIDLInfo elements defined by DIDL specification.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */

//TODO not sure if xml:id and xml schema ID (as defined in didl schema) is finally resolved. 

public interface DIDLBaseType {
    /**
     * Get an object identifier
     * @return the object identifier
     */
    public String getId();
    
    /**
     * Set an object identifier
     * @param id the object identifier
     */
    public void setId(String id);
    
    /**
     * Visit each DIDL member with a DIDLVisitorType
     * @param visitor the DIDLVisitorType to be used
     */
    public void accept(DIDLVisitorType visitor);
}
