/*
 * DIDLBaseListTest.java
 * JUnit based test
 *
 * Created on February 2, 2006, 4:08 PM
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

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DIDLBaseListTest extends TestCase {
    
    public DIDLBaseListTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLBaseListTest.class);
        
        return suite;
    }

    /**
     * Test of getList method, of class info.repo.didl.impl.DIDLBaseList.
     */
    public void testAddGetList() {
        System.out.println("getList");
        
        DIDLBaseList<DIDLBaseType> instance = new DIDLBaseList<DIDLBaseType>();
        
        
        instance.add(new DIDL());
        instance.add(new Item());
        instance.add(new Descriptor());
        instance.add(new Component());
        instance.add(new Statement());
        instance.add(new Resource());
        
        List<DIDLBaseType> result = instance.getList();
        
        assertTrue(result.get(0) instanceof DIDL);
        assertTrue(result.get(1) instanceof Item);
        assertTrue(result.get(2) instanceof Descriptor);
        assertTrue(result.get(3) instanceof Component);
        assertTrue(result.get(4) instanceof Statement);
        assertTrue(result.get(5) instanceof Resource);
    }

    /**
     * Test of replace method, of class info.repo.didl.impl.DIDLBaseList.
     */
    public void testReplace() {
        System.out.println("replace");
        
        DIDL o = new DIDL();
        Item n = new Item();
        
        DIDLBaseList<DIDLBaseType> instance = new DIDLBaseList<DIDLBaseType>();
        
        instance.add(o);
        
        DIDLBaseType result = instance.replace(n, o);
        
        assertTrue(result == o);
        assertTrue(instance.getList().get(0) == n);
    }

    /**
     * Test of remove method, of class info.repo.didl.impl.DIDLBaseList.
     */
    public void testRemove() {
        System.out.println("remove");
        
        DIDL d = new DIDL();
        
        DIDLBaseList instance = new DIDLBaseList();
        
        instance.add(d);
        
        DIDLBaseType result = instance.remove(d);
        
        assertTrue(result == d);
        assertTrue(instance.getList().size() == 0);
        
    }
}
