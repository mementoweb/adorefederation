/*
 * AssetTest.java
 * JUnit based test
 *
 * Created on February 7, 2006, 4:44 PM
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

import java.net.URI;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class AssetTest extends TestCase {
    
    public AssetTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(AssetTest.class);
        
        return suite;
    }

    /**
     * Test of getMimeType method, of class info.repo.didl.impl.Asset.
     */
    public void testSetGetMimeType() {
        System.out.println("setgetMimeType");
        
        Asset instance = new Asset(Asset.Type.STATEMENT);
        
        instance.setMimeType("text/plain");
        String result = instance.getMimeType();
        
        assertEquals("text/plain", result);
    }

    /**
     * Test of getRef method, of class info.repo.didl.impl.Asset.
     */
    public void testSetGetRef() {
        System.out.println("setgetRef");
        
        Asset instance = new Asset(Asset.Type.RESOURCE);
        
        URI uri = null;
        
        try {
            uri = new URI("info:ugent-repo/foobar");
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        
        instance.setRef(uri);
        
        assertEquals(instance.getRef(), uri);
    }

    /**
     * Test of getContentEncoding method, of class info.repo.didl.impl.Asset.
     */
    public void testSetGetContentEncoding() {
        System.out.println("setgetContentEncoding");
        
        Asset instance = new Asset(Asset.Type.STATEMENT);
        
        instance.setContentEncoding(new String[] { "foo", "bar" });
        
        assertTrue(instance.getContentEncoding()[0].equals("foo"));
        assertTrue(instance.getContentEncoding()[1].equals("bar"));
    }

    /**
     * Test of getEncoding method, of class info.repo.didl.impl.Asset.
     */
    public void testSetGetEncoding() {
        System.out.println("setgetEncoding");
        
        Asset instance = new Asset(Asset.Type.RESOURCE);
        
        instance.setEncoding("blabla");
        
        String result = instance.getEncoding();
        assertEquals("blabla", result);
    }

    /**
     * Test of getContent method, of class info.repo.didl.impl.Asset.
     */
    public void testGetContent() {
        System.out.println("setgetContent");
        
        Asset instance = new Asset(Asset.Type.RESOURCE);
        Object o = new Object();
        instance.setContent(o);

        assertEquals(instance.getContent(), o);
    }
}
