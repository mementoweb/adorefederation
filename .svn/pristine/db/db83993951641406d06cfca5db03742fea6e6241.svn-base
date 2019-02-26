/*
 * ItemTest.java
 * JUnit based test
 *
 * Created on February 6, 2006, 11:19 AM
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
import info.repo.didl.ItemType;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class ItemTest extends TestCase {
    
    public ItemTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ItemTest.class);
        
        return suite;
    }

    /**
     * Test of getComponents method, of class info.repo.didl.impl.Item.
     */
    public void testGetComponents() {
        System.out.println("getComponents");
        
        Item instance = new Item();
        
        instance.addComponent(new Component("1"));
        instance.addComponent(new Component("2"));
        
        List<ComponentType> result = instance.getComponents();

        assertTrue(result.get(0).getId().equals("1"));
        assertTrue(result.get(1).getId().equals("2"));
    }

    /**
     * Test of addComponent method, of class info.repo.didl.impl.Item.
     */
    public void testAddComponent() {
        System.out.println("addComponent");
        
        Item instance = new Item();
        
        Component c = new Component("1");
        ComponentType result = instance.addComponent(c);
        assertTrue(result.getId().equals("1"));
        assertTrue(result == c);
    }

    /**
     * Test of replaceComponent method, of class info.repo.didl.impl.Item.
     */
    public void testReplaceComponent() {
        System.out.println("replaceComponent");
        
        ComponentType n = new Component("new");
        ComponentType o = new Component("old");
        Item instance = new Item();
        
        instance.addComponent(o);
        
        ComponentType result = instance.replaceComponent(n, o);

        assertTrue(result.getId().equals("old"));
        assertTrue(result == o);
    }

    /**
     * Test of removeComponent method, of class info.repo.didl.impl.Item.
     */
    public void testRemoveComponent() {
        System.out.println("removeComponent");
        
        ComponentType component = new Component("remove");
        Item instance = new Item();
        instance.addComponent(component);
        
        ComponentType result = instance.removeComponent(component);
        
        assertTrue(result == component);
        assertTrue(instance.getComponents().size() == 0);
    }

    /**
     * Test of getItems method, of class info.repo.didl.impl.Item.
     */
    public void testGetItems() {
        System.out.println("getItems");
        
        Item instance = new Item();
        
        instance.addItem(new Item("1"));
        instance.addItem(new Item("2"));
        
        List<ItemType> result = instance.getItems();

        assertTrue(result.get(0).getId().equals("1"));
        assertTrue(result.get(1).getId().equals("2"));
    }

    /**
     * Test of addItem method, of class info.repo.didl.impl.Item.
     */
    public void testAddItem() {
        System.out.println("addItem");
        
        Item instance = new Item();
        
        Item i = new Item("1");
        ItemType result = instance.addItem(i);
        assertTrue(result.getId().equals("1"));
        assertTrue(result == i);
    }

    /**
     * Test of replaceItem method, of class info.repo.didl.impl.Item.
     */
    public void testReplaceItem() {
        System.out.println("replaceItem");
        
        ItemType n = new Item("new");
        ItemType o = new Item("old");
        Item instance = new Item();
        
        instance.addItem(o);
        
        ItemType result = instance.replaceItem(n, o);
        
        assertTrue(result == o);
        assertTrue(instance.getItems().get(0) == n);
    }

    /**
     * Test of removeItem method, of class info.repo.didl.impl.Item.
     */
    public void testRemoveItem() {
        System.out.println("removeItem");
        
        ItemType item = new Item("remove");
        Item instance = new Item();

        instance.addItem(item);
        
        ItemType result = instance.removeItem(item);
        
        assertTrue(result == item);
        assertTrue(instance.getItems().size() == 0);
    }
}
