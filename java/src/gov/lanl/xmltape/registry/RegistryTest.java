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
 
package gov.lanl.xmltape.registry;

import java.util.Iterator;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RegistryTest extends TestCase {

    public RegistryTest(String name) {
        super(name);
    }

    public void testDirectoryRegistry() throws Exception {
        DirectoryRegistry registry = new DirectoryRegistry(
                "/home/liu_x/projects/didl/scripts/env/moai/tapeconf");
        for (Iterator it = registry.iterator(); it.hasNext();) {
            Properties p = ((TapeConfig) it.next()).getProperties();
            p.list(System.out);
        }
    }

    public void testOAIRegistry() throws Exception {
        OAIRegistry registry = new OAIRegistry(
                "http://cox.lanl.gov:9090/taperegistry/OAIHandler");
        TapeConfig config = registry
                .getTapeConfig("biosis_2004_wk51_088c0e50-43df-11d9-84ef-86dbc0ce814d");

    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Enviroment Tests");
        suite.addTest(new RegistryTest("testDirectoryRegistry"));
        suite.addTest(new RegistryTest("testOAIRegistry"));
        return suite;

    }

}
