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
package gov.lanl.ockham.client.adore;

import java.net.URL;
import java.io.IOException;

import gov.lanl.ockham.OckhamAbstractTestCase;

public class AdoreTest extends OckhamAbstractTestCase {
    String tapeid = "info:lanl-repo/xmltape/123";

    String arcid = "info:lanl-repo/arc/myarc";

    String wrongid = "http://example.com";

    public AdoreTest() throws Exception {
        super();
    }

    public void testAddXMLTape() throws Exception {

        Add add = new Add(new URL(putbaseurl), "http://example.com/oaipmh",
                "http://example.com/openurl");
        assertTrue(add.put("info:sid/isi", tapeid, "2000-01-01", "2", arcid));
        Get get = new Get(new URL(putbaseurl));
        assertNotNull(get.get(tapeid));
        assertNotNull(get.get(Util.getOpenURLaDORe1ServiceId(tapeid)));
        assertNotNull(get.get(Util.getOpenURLaDORe2ServiceId(tapeid)));
        assertNotNull(get.get(Util.getOpenURLaDORe3ServiceId(tapeid)));
    }

    public void testAddArc() throws Exception {
        testDeleteArc();
        Add add = new Add(new URL(putbaseurl), null,
                "http://example.com/openurl");
        assertTrue(add.put("info:sid/isi", arcid, "2000-01-01", "10", null));
        Get get = new Get(new URL(putbaseurl));
        assertNotNull(get.get(arcid));
        assertNotNull(get.get(Util.getOpenURLaDORe3ServiceId(arcid)));
        assertNotNull(get.get(Util.getOpenURLaDORe4ServiceId(arcid)));
    }

    public void testDeleteXMLTape() throws Exception {
        Delete delete = new Delete(new URL(putbaseurl));
        delete.delete(tapeid);
    }

    public void testGetEmptyId() throws Exception {
        Get get = new Get(new URL(putbaseurl));
        try {
            get.get("really wrong id");
            assertTrue(false);
        } catch (IOException ex) {
            assertTrue(true);
        }
    }

    public void testDeleteArc() throws Exception {
        Delete delete = new Delete(new URL(putbaseurl));
        delete.delete(arcid);
    }

    public void tearDown() throws Exception {
        Delete delete = new Delete(new URL(putbaseurl));
        delete.delete(tapeid);
        delete.delete(arcid);
    }

}
