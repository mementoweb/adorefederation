/*
 * DIDL.java
 *
 * Created on October 8, 2005, 4:22 PM
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
import info.repo.didl.DIDLException;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.DIDLType;
import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ItemType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.DIDLBaseList;
import java.net.URI;
import java.util.List;

/**
 * The <code>DIDL</code> class provides a container in which the various 
 * DIDL elements are stored (i.e. Items, Components).  
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see AttributableBase
 * @see DIDLBaseList
 */
public final class DIDL extends AttributableBase implements DIDLType {
    private final DIDLBaseList<DIDLInfoType> DIDLInfoList = new DIDLBaseList<DIDLInfoType>();
    private final DIDLBaseList<ItemType> items = new DIDLBaseList<ItemType>();
    private URI DIDLDocumentId;
    
    public DIDL() {}
    
    /**
     * Creates a new DIDL instance defining its DIDLBase identifier
     * @param id DIDLBase identifier
     */
    public DIDL(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        setId(id);
    }
    
    /**
     * Gets the DIDLDocumentId
     */
    public URI getDIDLDocumentId() {
        return DIDLDocumentId;
    }
    
    /**
     * Sets the DIDLDocumentId
     */
    public void setDIDLDocumentId(final URI DIDLDocumentId) {
        this.DIDLDocumentId = DIDLDocumentId;
    }
    
    /**
     * Gets the list of ItemTypes
     */
    public List<ItemType> getItems() {
        return items.getList();
    }
    
    /**
     * Adds an ItemType to the list of items
     */
    public ItemType addItem(final ItemType item) {
        if (item != null && items.getList().size() > 0) {
            throw new DIDLException(DIDLException.ALREADY_DEFINED, "An Item was already defined");
        }
        return items.add(item);
    }
    
    /**
     * Replace an ItemType contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public ItemType replaceItem(final ItemType n, final ItemType o) {
        return items.replace(n,o);
    }
    
    /**
     * Remove an ItemType from the list of Items
     * @param item object to be removed from the list
     * @return item of removed object
     */
    public ItemType removeItem(final ItemType item) {
        return items.remove(item);
    }
    
    /**
     * Adds a DIDLInfoType to the list of DIDLInfo objects
     */
    public DIDLInfoType addDIDLInfo(final DIDLInfoType didlinfo) {
        return DIDLInfoList.add(didlinfo);
    }
    
    /**
     * Gets the list of DIDLInfoType objects
     */
    public List<DIDLInfoType> getDIDLInfos() {
        return DIDLInfoList.getList();
    }
    
    /**
     * Replace a DIDLInfoType contained in the list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public DIDLInfoType replaceDIDLInfo(final DIDLInfoType n, final DIDLInfoType o) {
        return DIDLInfoList.replace(n,o);
    }
    
    /**
     * Remove a DIDLInfoType from the list of Items
     * @param didlinfo object to be removed from the list
     * @return instance of removed object
     */
    public DIDLInfoType removeDIDLInfo(final DIDLInfoType didlinfo) {
        return DIDLInfoList.remove(didlinfo);
    }
    
    /**
     * Creates a new ItemType instance
     */
    public ItemType newItem() {
        return new Item();
    }
    
    /**
     * Creates a new ItemType instance with specified DIDLBase identifier
     * @param id identifier for parent DIDLBase instance
     * @return new ItemType instance
     */
    public ItemType newItem(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        return new Item(id);
    }
    
    /**
     * Creates a new ComponentType instance
     */
    public ComponentType newComponent() {
        return new Component();
    }
    
    /**
     * Creates a new ComponentType instance with specified DIDLBase identifier
     * @param id identifier for parent DIDLBase instance
     * @return new ComponentType instance
     */
    public ComponentType newComponent(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        return new Component(id);
    }
    
    /**
     * Creates a new DescriptorType instance
     */
    public DescriptorType newDescriptor() {
        return new Descriptor();
    }
    
    /**
     * Creates a new DescriptorType instance with specified DIDLBase identifier
     * @param id identifier for parent DIDLBase instance
     * @return new DescriptorType instance
     */
    public DescriptorType newDescriptor(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        return new Descriptor(id);
    }
    
    /**
     * Creates a new DIDLInfoType instance
     */
    public DIDLInfoType newDIDLInfo() {
        return new DIDLInfo();
    }
    
    /**
     * Creates a new DIDLInfoType instance with specified DIDLBase identifier
     * @param id identifier for parent DIDLBase instance
     * @return new DIDLInfoType instance
     */
    public DIDLInfoType newDIDLInfo(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        return new DIDLInfo(id);
    }
    
    /**
     * Creates a new ResourceType instance
     */
    public ResourceType newResource() {
        return new Asset(Asset.Type.RESOURCE);
    }
    
    /**
     * Creates a new ResourceType instance with specified DIDLBase identifier
     * @param id identifier for parent DIDLBase instance
     * @return new ResourceType instance
     */
    public ResourceType newResource(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        return new Asset(Asset.Type.RESOURCE,id);
    }
    
    /**
     * Creates a new StatementType instance
     */
    public StatementType newStatement() {
        return new Asset(Asset.Type.STATEMENT);
    }
    
    /**
     * Creates a new StatementType instance with specified DIDLBase identifier
     * @param id identifier for parent DIDLBase instance
     * @return new StatementType instance
     */
    public StatementType newStatement(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        return new Asset(Asset.Type.STATEMENT, id);
    }
    
    /**
     * Implements the visitor pattern for serialization
     */
    public void accept(final DIDLVisitorType visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        
        visitor.visitStart(this);
        
        for (DIDLInfoType didlInfo: getDIDLInfos()) {
            didlInfo.accept(visitor);
        }
        
        for (ItemType item: getItems()) {
            item.accept(visitor);
        }
        
        visitor.visitEnd(this);
    }
}