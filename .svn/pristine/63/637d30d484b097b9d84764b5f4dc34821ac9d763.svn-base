/*
 * DIDLInfoTest.java
 * JUnit based test
 *
 * Created on February 2, 2006, 4:20 PM
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DIDLInfoTest extends TestCase {
    
    public DIDLInfoTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLInfoTest.class);
        
        return suite;
    }

    /**
     * Test of getContent method, of class info.repo.didl.impl.DIDLInfo.
     */
    public void testSetGetContent() {
        System.out.println("getContent");
        
        DIDLInfo instance = new DIDLInfo();
        
        Object expResult = new String("Hello, World");
        
        instance.setContent(expResult);
        
        assertTrue(instance.getContent() == expResult);
    }
}
