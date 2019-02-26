/*
 * ItemType.java
 *
 * Created on October 8, 2005, 9:48 AM
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

import java.util.List;

/**
 * The <code>ItemType</code> element is a grouping of possible sub-items and/or 
 * <code>Components</code>, bound to a set of relevant Descriptors containing descriptive 
 * information about the item. The <code>ItemType</code> provides access mechanism to 
 * these information.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */
public interface ItemType extends DescriptableBaseType {
    /**
     * Get a list of Item objects
     * @return a list of sub Items
     */
    public List<ItemType> getItems();
    
    /**
     * Add a sub Item
     * @param item a new ItemType object
     * @return the item added
     */
    public ItemType addItem(ItemType item);
    
    /**
     * Replace a sub item with a new one
     * @param n the new ItemType object
     * @param o the old ItemType object
     * @return the item replaced or null when the item was not found
     */
    public ItemType replaceItem(ItemType n, ItemType o);
    
    /**
     * Remove a sub item
     * @param item the sub ItemType to delete
     * @return the item replaced or null when the item was not found
     */
    public ItemType removeItem(ItemType item);
    
    /**
     * Get a list of Component objects
     * @return a List of Components
     */
    public List<ComponentType> getComponents();
    
    /**
     * Add a Component
     * @param component a new ComponentType object
     * @return the component added
     */
    public ComponentType addComponent(ComponentType component);
    
    /**
     * Replace a ComponentType with a new one
     * @param n the new ComponentType object
     * @param o the old ComponentType object
     * @return the component type replaced or null when the component was not found
     */
    public ComponentType replaceComponent(ComponentType n, ComponentType o);
    
    /**
     * Remove a component
     * @param component the component to delete
     * @return the component deleted or null when the component was not found
     */
    public ComponentType removeComponent(ComponentType component);
}
