/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
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
 * 
 */

package gov.lanl.ockham.service;

import gov.lanl.util.properties.PropertiesUtil;
import junit.framework.TestCase;

public class ServiceRegistryDBTest extends TestCase {
    String pfile = null;

    ServiceRegistryDB registry = null;

    String identifier = "123";

    String text = "<something/>";
    public final static String DB_CONFFILE = "ockham.properties";
    
    public void setUp() throws Exception {
        registry = new ServiceRegistryDB(PropertiesUtil.loadConfigByCP(DB_CONFFILE));

    }

    public void testPut() throws Exception {
        registry.putRecord(identifier, text, "Collection");
        String result = registry.getRecord(identifier);
        assertEquals(text, result);
        result = registry.getRecord(identifier);
        assertEquals(text, result);
    }

    public void testDelete() throws Exception {
        testPut();
        registry.deleteRecord(identifier);
        try {
            registry.getRecord(identifier);
            assertTrue(false);
        } catch (IdDoesNotExistException ex) {
            assertTrue(true);
        }
    }

    public void testWrongPut() throws Exception {
        try {
            registry.putRecord(identifier, text, "collections");
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    public void tearDown() throws Exception {
        registry.deleteRecord(identifier);
    }
}
