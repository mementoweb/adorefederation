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

package gov.lanl.util.properties;

import junit.framework.TestCase;
import java.io.ByteArrayInputStream;

public class GenericPropertyManagerTest extends TestCase {

    public void testGet() throws Exception {
        GenericPropertyManager loader = new GenericPropertyManager();
        String pfile = "fruit=apple\nanimal=tiger\n";
        loader.addAll(PropertiesUtil.loadProperty(new ByteArrayInputStream(pfile.getBytes())));
        assertEquals("apple", loader.getProperty("fruit"));
        assertEquals("tiger", loader.getProperty("animal"));
    }

    public void testSet() throws Exception {
        GenericPropertyManager loader = new GenericPropertyManager();
        loader.setProperty("fruit", "apple");
        assertEquals("apple", loader.getProperty("fruit"));

    }

    public void testLoadSet() throws Exception {
        GenericPropertyManager loader = new GenericPropertyManager();
        String pfile = "fruit=apple\nanimal=tiger\n";
        loader.addAll(PropertiesUtil.loadProperty(new ByteArrayInputStream(pfile.getBytes())));
        loader.setProperty("fruit", "pineapple");
        assertEquals("pineapple", loader.getProperty("fruit"));
        loader.addAll(PropertiesUtil.loadProperty(new ByteArrayInputStream(pfile.getBytes())));
        loader.setProperty("fruit", "apple");
    }

    public void testOverwrite() throws Exception {
        GenericPropertyManager loader = new GenericPropertyManager();
        loader.setProperty("flower", "sunflower");
        String pfile = "fruit=apple\nanimal=tiger\n";
        loader.addAll(PropertiesUtil.loadProperty(new ByteArrayInputStream(pfile.getBytes())));
        assertEquals("sunflower", loader.getProperty("flower"));
    }

    public void testSystemProperty() throws Exception {
        System.setProperty("flower", "trumpet vine");
        GenericPropertyManager loader = new GenericPropertyManager();
        assertEquals("trumpet vine", loader.getProperty("flower"));
    }

    public void testSystemEnv() throws Exception {
        GenericPropertyManager loader = new GenericPropertyManager();
        // we assume HOME environment always exists
        assertNotNull(loader.getProperty("HOME"));
        assertEquals(1, loader.getProperties().size());
    }
}
