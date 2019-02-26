/*
 * DIDLType.java
 *
 * Created on October 8, 2005, 9:34 AM
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

import java.net.URI;
import java.util.List;

/**
 * The <code>DIDLType</code> interface represents the entire DIDL document, A 
 * DIDL may have any attributes from other namespace. Conceptually, it is the 
 * root of the DIDL tree, and provides the primary access to the DIDL's data 
 * through <code>ItemType<code>, <code>DIDLType</code> also provides accesses 
 * to DIDL-level information such as DIDLDocumentId and DIDLInfo.  

 * <p> The <code>DIDLType</code> interface also contains the factory methods 
 * needed to create elements such as Item, Component, Descriptor, Statement, 
 * Resource, and DIDLInfo 
 *
 *
 * DIDL element may contain exactly one Container or Item.
 * 
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */

//TODO we may want to add more factory methods for easy creation of elements, such as
// createItem(String id);

public interface DIDLType extends AttributableBaseType {
    /**
     * Get the DIDLDocumentId.
     * @return a URI containing the DIDLDocumentId
     */
    public URI getDIDLDocumentId();
    
    /**
     * Set the DIDLDocumentId.
     * @param uri a URI containing the DIDLDocumentId
     */
    public void setDIDLDocumentId(URI uri);
    
    /**
     * Get a list of DIDLInfoType objects.
     * @return a list of all DIDLInfo objects in this DIDLType
     */
    public List<DIDLInfoType> getDIDLInfos();
    
    /**
     * Add a new DIDLInfoType object
     * @param didlInfo a new DIDLInfoType object
     * @return the DIDLInfoType object added
     */
    public DIDLInfoType addDIDLInfo(DIDLInfoType didlInfo);
    
    /**
     * Replace a DIDLInfoType object
     * @param n a new DIDLInfoType object
     * @param o the old DIDLInfoType object
     * @return the DIDLInfoType replaced or null when the
     * object was not found
     */
    public DIDLInfoType replaceDIDLInfo(DIDLInfoType n, DIDLInfoType o);
    
    /**
     * Remove a DIDLInfoType object from the DIDLType
     * @param didlInfo the DIDLInfoType object to remove
     * @return the DIDLInfoType object removed or null when the
     * object was not found
     */
    public DIDLInfoType removeDIDLInfo(DIDLInfoType didlInfo);
    
    /**
     * Get a list of ItemType objects.
     * @return a list of all ItemType objects in this DIDLType
     */
    public List<ItemType> getItems();
    
    /**
     * Add a new ItemType object
     * @param item a new ItemType object
     * @return the ItemTypeObject added
     */
    public ItemType addItem(ItemType item);
    
    /**
     * Replace a in the DIDLType a ItemType object
     * @param n a new ItemType object
     * @param o the old ItemType object
     * @return the ItemType replaced or null when the old Item
     * can't be found
     */
    public ItemType replaceItem(ItemType n, ItemType o);
    
    /**
     * Remove a ItemType object from the DIDLType
     * @param item the ItemType object to remove
     * @return the ItemType object removed or null when the
     * object was not found
     */
    public ItemType removeItem(ItemType item) ;
    
    /**
     * Create a new ItemType object
     * @return a new Item
     */
    public ItemType newItem();
    
    /**
     * Create a new ComponentType object
     * @return a new Component
     */
    public ComponentType newComponent();
    
    /**
     * Create a new DescriptorType object
     * @return a new Descriptor
     */
    public DescriptorType newDescriptor();
    
    /**
     * Create a new DIDLInfoType object
     * @return a new DIDLInfo
     */
    public DIDLInfoType newDIDLInfo();
    
    /**
     * Create a new StatementType object
     * @return a new Statement
     */
    public StatementType newStatement();
    
    /**
     * Create a new ResourceType object
     * @return a new Resource
     */
    public ResourceType newResource();
}
