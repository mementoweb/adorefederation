/*
 * DIDLBaseTest.java
 * JUnit based test
 *
 * Created on February 2, 2006, 4:06 PM
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
public class DIDLBaseTest extends TestCase {
    
    public DIDLBaseTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLBaseTest.class);
        
        return suite;
    }

    /**
     * Test of getId method, of class info.repo.didl.impl.DIDLBase.
     */
    public void testSetGetId() {
        System.out.println("getId");
        
        DIDL instance = new DIDL();
        
        String expResult = "uoas-sa-sa-sa0012912-1234";
        
        instance.setId(expResult);
        
        String result = instance.getId();
        
        assertEquals(expResult, result);
        
    }

    /**
     * Generated implementation of abstract class info.repo.didl.impl.DIDLBase. Please fill dummy bodies of generated methods.
     */
    private class DIDLBaseImpl extends DIDLBase {

        public void accept(info.repo.didl.DIDLVisitorType visitor) {
            // TODO fill the body in order to provide useful implementation
            
        }

        public java.lang.String getId() {
            // TODO fill the body in order to provide useful implementation
            
            return null;
        }

        public void setId(java.lang.String id) {
            // TODO fill the body in order to provide useful implementation
            
        }
    }

    
}
