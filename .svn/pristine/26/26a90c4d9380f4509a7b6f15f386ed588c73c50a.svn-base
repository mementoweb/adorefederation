/*
 * AbstractAttributeTest.java
 * JUnit based test
 *
 * Created on February 20, 2006, 1:12 PM
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

import junit.framework.*;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class AbstractAttributeTest extends TestCase {
    
    class MyAttr extends AbstractAttribute {
        
    }
    
    class MyAttr2 extends AbstractAttribute {
        
    }
    
    public AbstractAttributeTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(AbstractAttributeTest.class);
        
        return suite;
    }

    /**
     * Test of getValue method, of class info.repo.didl.impl.AbstractAttribute.
     */
    public void testGetValue() {
        System.out.println("putgetValue");
        
        AbstractAttribute instance = new MyAttr();
        
        String key = "key";
        Object value = "value";
        
        instance.setValue(key, value);
        Object result = instance.getValue(key);
        assertEquals(value, result);   
    }

    /**
     * Test of equals method, of class info.repo.didl.impl.AbstractAttribute.
     */
    public void testEquals() {
        System.out.println("equals");
        
        AbstractAttribute instance = new MyAttr();
        AbstractAttribute instance2 = new MyAttr2();
        
        assertTrue(instance.equals(instance));
        assertTrue(instance.equals(new MyAttr()));
        assertFalse(instance.equals(instance2));
    }

}
