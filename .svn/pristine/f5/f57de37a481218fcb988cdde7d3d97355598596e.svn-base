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

package gov.lanl.federator.schematable;

import junit.framework.TestCase;
import java.io.StringReader;
import java.util.Hashtable;

import org.xml.sax.InputSource;

public class MapTableTest extends TestCase {

    String prefix = "identifiers";

    String schema = "http://greatwhiteshark.lanl.gov/schemas-didl/identifiers.xsd";

    String namespace = "http://library.lanl.gov/2003-11/lww/listOfIdentifiers";

    String service = "identifiers.xsl";

    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<FederatorTable>" + "<item>" + "<metadataPrefix>" + prefix
            + "</metadataPrefix>" + "<schema>" + schema + "</schema>"
            + "<metadataNamespace>" + namespace + "</metadataNamespace>"
            + "<service>" + service + "</service>" + "</item>"
            + "</FederatorTable>";

    public void testParse() throws Exception {
        MapTable.parse(new InputSource(new StringReader(xml)));
        Hashtable<String, MapItem> table = MapTable.getMapTable();
        assertTrue(table.containsKey(prefix));
        assertEquals(1, table.size());
        assertEquals(schema, table.get(prefix).getSchema());
        assertEquals(namespace, table.get(prefix).getNS());
        assertEquals(service, table.get(prefix).getService());
    }

}
