/*
 * DIDLFactoryTest.java
 * JUnit based test
 *
 * Created on February 2, 2006, 4:18 PM
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
import info.repo.didl.DIDLType;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DIDLFactoryTest extends TestCase {
    
    public DIDLFactoryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLFactoryTest.class);
        
        return suite;
    }

    /**
     * Test of newDIDL method, of class info.repo.didl.impl.DIDLFactory.
     */
    public void testNewDIDL() {
        System.out.println("newDIDL");
        
        DIDLFactory instance = new DIDLFactory();
        
        DIDLType result = instance.newDIDL();
        assertTrue(result instanceof DIDLType);
    }
    
}
