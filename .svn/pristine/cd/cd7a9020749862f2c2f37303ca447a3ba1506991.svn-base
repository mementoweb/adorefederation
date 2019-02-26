/*
 * DIDLTest.java
 * JUnit based test
 *
 * Created on January 12, 2006, 4:45 PM
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
import info.repo.didl.DIDLException;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ItemType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;

import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DIDLTest extends TestCase {
    private final String TEST_ID = "info:ugent-repo/i/05051971-05051971-05051971";
    
    public DIDLTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLTest.class);
        
        return suite;
    }
    
    public void testGetSetId() {
        System.out.println("getsetId");
        DIDL instance = new DIDL();
        
        instance.setId("123");
        
        assertEquals("123", instance.getId());
    }
    
    /**
     * Test of getDIDLDocumentId method, of class info.repo.didl.DIDL.
     */
    public void testGetSetDIDLDocumentId() {
        System.out.println("getsetDIDLDocumentId");
        
        try {
            DIDL instance = new DIDL();
            
            instance.setDIDLDocumentId(new URI(TEST_ID));
            
            URI expResult = new URI(TEST_ID);
            URI result = instance.getDIDLDocumentId();
            
            assertEquals(expResult, result);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        
    }
    
    /**
     * Test of getItems method, of class info.repo.didl.DIDL.
     */
    public void testGetItems() {
        System.out.println("getItems");
        
        DIDL instance = new DIDL();

        ItemType it1 = instance.newItem();
        
        instance.addItem(it1);
        
        ItemType it2 = instance.getItems().get(0);
        
        assertEquals(it1, it2);
    }
    
    /**
     * Test of addItem method, of class info.repo.didl.DIDL.
     */
    public void testAddItem() {
        System.out.println("addItem");
        
        DIDL instance = new DIDL();
        
        instance.addItem(instance.newItem());
        
        // Try to add a second item should produce a DIDLException
        try {
            instance.addItem(instance.newItem());
        }
        catch (DIDLException e) {
            if (e.code != e.ALREADY_DEFINED) {
                fail("Need ALREADY_DEFINED Exception");
            }
        }
    }
    
    /**
     * Test of replaceItem method, of class info.repo.didl.DIDL.
     */
    public void testReplaceItem() {
        System.out.println("replaceItem");
        
        DIDL instance = new DIDL();
        
        ItemType o = instance.newItem();
        ItemType n = instance.newItem();

        instance.addItem(o);
        
        ItemType result = instance.replaceItem(n, o);
        
        assertEquals(o, result);
    }
    
    /**
     * Test of removeItem method, of class info.repo.didl.DIDL.
     */
    public void testRemoveItem() {
        System.out.println("removeItem");

        DIDL instance = new DIDL();
        ItemType item = instance.newItem();
        
        instance.addItem(item);
        
        ItemType result = instance.removeItem(item);
        
        assertEquals(item, result);
        
        assertEquals(0, instance.getItems().size());
    }
    
    /**
     * Test of addDIDLInfo method, of class info.repo.didl.DIDL.
     */
    public void testAddDIDLInfo() {
        System.out.println("addDIDLInfo");
        
        DIDL instance = new DIDL();
        
        instance.addDIDLInfo(instance.newDIDLInfo());
        instance.addDIDLInfo(instance.newDIDLInfo());
        
        assertEquals(2, instance.getDIDLInfos().size());
    }
    
    /**
     * Test of getDIDLInfos method, of class info.repo.didl.DIDL.
     */
    public void testGetDIDLInfos() {
        System.out.println("getDIDLInfos");

        DIDL instance = new DIDL();

        DIDLInfoType inf = instance.newDIDLInfo();
        
        instance.addDIDLInfo(inf);
        
        assertEquals(inf, instance.getDIDLInfos().get(0));
    }
    
    /**
     * Test of replaceDIDLInfo method, of class info.repo.didl.DIDL.
     */
    public void testReplaceDIDLInfo() {
        System.out.println("replaceDIDLInfo");
        
        DIDL instance = new DIDL();
        
        DIDLInfoType n = instance.newDIDLInfo();
        DIDLInfoType o = instance.newDIDLInfo();
        
        instance.addDIDLInfo(o);
        
        DIDLInfoType result = instance.replaceDIDLInfo(n, o);
        
        assertEquals(o, result);
    }
    
    /**
     * Test of removeDIDLInfo method, of class info.repo.didl.DIDL.
     */
    public void testRemoveDIDLInfo() {
        System.out.println("removeDIDLInfo");
        
        DIDL instance = new DIDL();
        DIDLInfoType didlinfo = instance.newDIDLInfo();
        
        instance.addDIDLInfo(didlinfo);
        
        DIDLInfoType result = instance.removeDIDLInfo(didlinfo);
        
        assertEquals(didlinfo, result);
        assertEquals(0, instance.getDIDLInfos().size());
    }
    
    /**
     * Test of newItem method, of class info.repo.didl.DIDL.
     */
    public void testNewItem() {
        System.out.println("newItem");
        
        DIDL instance = new DIDL();
        
        assertTrue(instance.newItem() instanceof ItemType);
    }
    
    /**
     * Test of newComponent method, of class info.repo.didl.DIDL.
     */
    public void testNewComponent() {
        System.out.println("newComponent");
        
        DIDL instance = new DIDL();
        
        assertTrue(instance.newComponent() instanceof ComponentType);
    }
    
    /**
     * Test of newDescriptor method, of class info.repo.didl.DIDL.
     */
    public void testNewDescriptor() {
        System.out.println("newDescriptor");
        
        DIDL instance = new DIDL();
        
        assertTrue(instance.newDescriptor() instanceof DescriptorType);
    }
    
    /**
     * Test of newDIDLInfo method, of class info.repo.didl.DIDL.
     */
    public void testNewDIDLInfo() {
        System.out.println("newDIDLInfo");
        
        DIDL instance = new DIDL();
        
        assertTrue(instance.newDIDLInfo() instanceof DIDLInfoType);
    }
    
    /**
     * Test of newResource method, of class info.repo.didl.DIDL.
     */
    public void testNewResource() {
        System.out.println("newResource");
        
        DIDL instance = new DIDL();
        
        assertTrue(instance.newResource() instanceof ResourceType);
    }
    
    /**
     * Test of newStatement method, of class info.repo.didl.DIDL.
     */
    public void testNewStatement() {
        System.out.println("newStatement");
        
        DIDL instance = new DIDL();
        
        assertTrue(instance.newStatement() instanceof StatementType);
    }

    /**
     * Test of getDIDLDocumentId method, of class info.repo.didl.impl.DIDL.
     */
    public void testGetDIDLDocumentId() {
        System.out.println("getDIDLDocumentId");
        
        DIDL instance = new DIDL();
  
        try {
            URI expResult = new URI("urn:111-111-111");
            instance.setDIDLDocumentId(expResult);
            URI result = instance.getDIDLDocumentId();
            assertEquals(expResult, result);
        }
        catch (URISyntaxException e) {
            fail(e.getMessage());
        }
        
    }
}
