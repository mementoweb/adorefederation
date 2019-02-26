/*
 * DescriptorTest.java
 * JUnit based test
 *
 * Created on February 7, 2006, 4:12 PM
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

import info.repo.didl.StatementType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DescriptorTest extends TestCase {
    
    public DescriptorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DescriptorTest.class);
        
        return suite;
    }

    /**
     * Test of addStatement method, of class info.repo.didl.impl.Descriptor.
     */
    public void testAddStatement() {
        System.out.println("addStatement");
        
        Statement statement = new Statement("123");
        Descriptor instance = new Descriptor();
        
        instance.addStatement(statement);
        
        assertTrue(instance.getStatements().get(0) == statement);
    }

    /**
     * Test of replaceStatement method, of class info.repo.didl.impl.Descriptor.
     */
    public void testReplaceStatement() {
        System.out.println("replaceStatement");
        
        StatementType n = new Statement("n");
        StatementType o = new Statement("o");
        Descriptor instance = new Descriptor();
        
        instance.addStatement(o);
        
        StatementType result = instance.replaceStatement(n, o);
        
        assertTrue(result == o);
        assertTrue(instance.getStatements().get(0) == n);
    }

    /**
     * Test of removeStatement method, of class info.repo.didl.impl.Descriptor.
     */
    public void testRemoveStatement() {
        System.out.println("removeStatement");
        
        StatementType statement = new Statement();
        Descriptor instance = new Descriptor();
        
        instance.addStatement(statement);
        
        StatementType result = instance.removeStatement(statement);
        
        assertTrue(result == statement);
        assertTrue(instance.getStatements().size() == 0);
    }
}
