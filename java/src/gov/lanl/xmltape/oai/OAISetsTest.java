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

package gov.lanl.xmltape.oai;

import junit.framework.TestCase;

/**
 * OAISetsTest.java 1239 2004-11-30 22:28:02Z
 * @author liu_x
 *
 */

public class OAISetsTest extends TestCase {
    String setSpec1 = "format:info*3Alanl-repo*2Fpro*2Fcontent";

    String setSpec2 = "collection:info*3Asid*2Flibrary.lanl.gov*3Aisi";

    String setSpec3 = "format";

    String setSpec4 = "collection";

    public void testSetspecs() {
        try {
            assertEquals(new SetSpec("format", "info:lanl-repo/pro/content"),
                    new SetSpec(setSpec1));
            assertEquals(new SetSpec("collection",
                    "info:sid/library.lanl.gov:isi"), new SetSpec(setSpec2));
            assertEquals(new SetSpec("format", null), new SetSpec(setSpec3));
            assertEquals(new SetSpec("collection", null), new SetSpec(setSpec4));

        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSetsList() throws Exception {
        String input = "<setSpec>format:info*3Alanl-repo*2Fpro*2Fcontent</setSpec><setSpec>collection:info*3Asid*2Flibrary.lanl.gov*3Aisi</setSpec><setSpec>format</setSpec><setSpec>collection</setSpec>";
        SetsList list = new SetsList(input);
        assertTrue(list.getSpecList().contains(setSpec1));
        assertTrue(list.getSpecList().contains(setSpec2));
        assertTrue(!list.getSpecList().contains(setSpec3));
        assertTrue(!list.getSpecList().contains(setSpec4));
    }

}
