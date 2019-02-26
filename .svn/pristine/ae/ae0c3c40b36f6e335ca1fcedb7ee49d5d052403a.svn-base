/*
 * ComponentTest.java
 * JUnit based test
 *
 * Created on February 7, 2006, 4:24 PM
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

import info.repo.didl.ResourceType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class ComponentTest extends TestCase {
    
    public ComponentTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ComponentTest.class);
        
        return suite;
    }

    /**
     * Test of addResource method, of class info.repo.didl.impl.Component.
     */
    public void testAddResource() {
        System.out.println("addResource");
        
        ResourceType r1 = new Resource();
        ResourceType r2 = new Resource();
        Component instance = new Component();
        
        ResourceType result1 = instance.addResource(r1);
        
        ResourceType result2 = instance.addResource(r2);
        
        assertEquals(r1, result1);
        assertEquals(instance.getResources().get(0), r1);
        
        assertEquals(r2, result2);
        assertEquals(instance.getResources().get(1), r2);
    }

    /**
     * Test of replaceResource method, of class info.repo.didl.impl.Component.
     */
    public void testReplaceResource() {
        System.out.println("replaceResource");
        
        ResourceType n = new Resource("n");
        ResourceType o = new Resource("o");
        Component instance = new Component();
        
        instance.addResource(o);
        
        ResourceType result = instance.replaceResource(n, o);
        
        assertEquals(result, o);
    }

    /**
     * Test of removeResource method, of class info.repo.didl.impl.Component.
     */
    public void testRemoveResource() {
        System.out.println("removeResource");
        
        ResourceType resource = new Resource();
        Component instance = new Component();
        
        instance.addResource(resource);
        
        ResourceType result = instance.removeResource(resource);
        
        assertEquals(result, resource);
        assertEquals(instance.getResources().size(), 0);
    }
}
