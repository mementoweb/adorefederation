/*
 * DescriptableBaseTest.java
 * JUnit based test
 *
 * Created on February 7, 2006, 4:16 PM
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

import info.repo.didl.DescriptorType;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DescriptableBaseTest extends TestCase {
    
    public DescriptableBaseTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DescriptableBaseTest.class);
        
        return suite;
    }

    /**
     * Test of addDescriptor method, of class info.repo.didl.impl.DescriptableBase.
     */
    public void testAddDescriptor() {
        System.out.println("addDescriptor");
        
        DescriptorType descriptor = new Descriptor();
        DescriptableBase instance = new Item();
        
        DescriptorType result = instance.addDescriptor(descriptor);
        assertEquals(result, descriptor);
    }

    /**
     * Test of getDescriptors method, of class info.repo.didl.impl.DescriptableBase.
     */
    public void testGetDescriptors() {
        System.out.println("getDescriptors");
        
        Descriptor d1 = new Descriptor();
        Descriptor d2 = new Descriptor();
        DescriptableBase instance = new Item();
        
        instance.addDescriptor(d1);
        instance.addDescriptor(d2);
        
        List<DescriptorType> result = instance.getDescriptors();
        
        assertEquals(result.get(0), d1);
        assertEquals(result.get(1), d2);
    }

    /**
     * Test of replaceDescriptor method, of class info.repo.didl.impl.DescriptableBase.
     */
    public void testReplaceDescriptor() {
        System.out.println("replaceDescriptor");
        
        DescriptorType n = new Descriptor("n");
        DescriptorType o = new Descriptor("o");
        DescriptableBase instance = new Item();
        
        instance.addDescriptor(o);
        
        DescriptorType result = instance.replaceDescriptor(n, o);
        
        assertEquals(result, o);
        assertEquals(instance.getDescriptors().get(0), n);
    }

    /**
     * Test of removeDescriptor method, of class info.repo.didl.impl.DescriptableBase.
     */
    public void testRemoveDescriptor() {
        System.out.println("removeDescriptor");
        
        DescriptorType descriptor = new Descriptor();
        DescriptableBase instance = new Item();
        
        instance.addDescriptor(descriptor);
        
        DescriptorType result = instance.removeDescriptor(descriptor);
        
        assertEquals(result, descriptor);
        assertEquals(instance.getDescriptors().size(), 0);
    }
}
