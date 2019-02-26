/*
 * Item.java
 *
 * Created on October 8, 2005, 4:36 PM
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
import info.repo.didl.ItemType;
import info.repo.didl.impl.DIDLBaseList;
import java.util.List;

/**
 * The <code>Item</code> extends <code>DescriptableBase</code> and provides 
 * a wrapper for <code>ItemType</code> objects.  Each Item object
 * provides a clean interface to manage Items and Components. 
 *
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public final class Item extends DescriptableBase implements ItemType {
    private final DIDLBaseList<ItemType> items = new DIDLBaseList<ItemType>();
    private final DIDLBaseList<ComponentType> components = new DIDLBaseList<ComponentType>();
    
    /**
     * Creates a new Item instance
     */
    public Item() {}
    
    /**
     * Creates a new Item instance with associated identifier
     * @param id identifier of instance
     */
    public Item(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        setId(id);
    }
    
    /**
     * Gets the list of Components
     * @return an ArrayList of ComponentType objects
     */
    public List<ComponentType> getComponents() {
        return components.getList();
    }
    
    /**
     * Add a ComponentType object to list
     * @param component a ComponentType instance
     * @return instance added to list
     */
    public ComponentType addComponent(final ComponentType component) {
        return components.add(component);
    }
    
    /**
     * Replace ComponentType object contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public ComponentType replaceComponent(final ComponentType n, final ComponentType o) {
        return components.replace(n,o);
    }
    
    /**
     * Remove a ComponentType
     * @param component object to be removed from the list
     * @return instance of removed object
     */
    public ComponentType removeComponent(final ComponentType component) {
        return components.remove(component);
    }
    
    /**
     * Gets the list of Items
     * @return an ArrayList of ItemType objects
     */
    public List<ItemType> getItems() {
        return items.getList();
    }
    
    /**
     * Add a ItemType object to list
     * @param item a ItemType instance
     * @return instance added to list
     */
    public ItemType addItem(final ItemType item) {
        return items.add(item);
    }
    
    /**
     * Replace ItemType object contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public ItemType replaceItem(final ItemType n, final ItemType o) {
        return items.replace(n,o);
    }
    
    /**
     * Remove a ItemType
     * @param item object to be removed from the list
     * @return instance of removed object
     */
    public ItemType removeItem(final ItemType item) {
        return items.remove(item);
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
        
        for (ItemType item: getItems()) {
            item.accept(visitor);
        }
        
        for (ComponentType component: getComponents()) {
            component.accept(visitor);
        }
        
        visitor.visitEnd(this);
    }
}