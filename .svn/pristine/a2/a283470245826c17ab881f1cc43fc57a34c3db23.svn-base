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

package gov.lanl.federator;

import java.util.Map;
import java.util.Properties;
import java.util.Vector;

public class DIDLOAICatalogTest extends FedAbstractTestCase {
    DIDLOAICatalog catalog;

    String sampleid;

    public DIDLOAICatalogTest(String title) throws Exception {
        super(title);
    }

    protected void setUp() throws Exception {
    	Properties props=new Properties();
    	props.putAll(System.getProperties());
    	props.putAll(System.getenv());
        catalog = new DIDLOAICatalog(props);
        sampleid = loader.getProperty(FedConstants.ADORE_FEDERATOR_SAMPLEID);
    }

    public void testGetSchemaLocations() throws Exception {
        Vector v = catalog.getSchemaLocations(sampleid);
        assertTrue(v.size() > 0);

    }

    public void testListIdentifiers() throws Exception {
        Map list = catalog.listIdentifiers(null, null, null, "didl");
        assertNotNull(list.get("headers"));
        assertNotNull(list.get("identifiers"));
        assertNotNull(list.get("resumptionMap"));
    }

    public void testEmptyListIdentifiers() throws Exception {
        Map list = catalog.listIdentifiers(null, null, "reall wrong set",
                "didl");
        assertNotNull(list.get("headers"));
        assertNotNull(list.get("identifiers"));
        assertNull(list.get("resumptionMap"));
    }

    public void testListRecords() throws Exception {
        Map list = catalog.listRecords(null, null, null, "didl");
        assertNotNull(list.get("records"));
        assertNotNull(list.get("resumptionMap"));
    }

    public void testListRecordsWithTranform() throws Exception {
        Map list = catalog.listRecords(null, null, null, "identifiers");
        assertNotNull(list.get("records"));
        assertNotNull(list.get("resumptionMap"));
    }

    public void testEmptyListRecords() throws Exception {
        Map list = catalog.listRecords(null, null, "reall wrong set", "didl");
        assertNotNull(list.get("records"));
        assertNull(list.get("resumptionMap"));
    }

    public void testListSets() throws Exception {
        Map sets = catalog.listSets();
        assertNotNull(sets.get("sets"));
    }

    public void testGetRecord() throws Exception {
        String result = catalog.getRecord(sampleid, "didl");
        assertTrue(result.length() > 0);
        result = catalog.getRecord(sampleid, "identifiers");
        assertTrue(result.length() > 0);
    }

}
