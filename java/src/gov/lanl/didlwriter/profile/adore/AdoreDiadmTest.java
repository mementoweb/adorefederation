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

package gov.lanl.didlwriter.profile.adore;

import org.adore.didl.content.Diadm;

import org.adore.didl.serialize.DiadmSerializer;
import org.adore.didl.serialize.DiadmDeserializer;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import java.util.ArrayList;

public class AdoreDiadmTest extends TestCase {
    String format = "application/pdf";

    String sem1 = "journal";

    String sem2 = "book";

    String diadmstr = "<diadm:DIADM xmlns:diadm=\"http://library.lanl.gov/2005-08/aDORe/DIADM/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/DIADM/ http://purl.lanl.gov/aDORe/schemas/2006-09/DIADM.xsd\">"
            + "<dc:format xmlns:dc=\"http://purl.org/dc/elements/1.1/\" >application/pdf</dc:format>"
            + "<dc:type xmlns:dc=\"http://purl.org/dc/elements/1.1/\" >journal</dc:type>"
            + "<dc:type xmlns:dc=\"http://purl.org/dc/elements/1.1/\" >book</dc:type>"
            + "</diadm:DIADM>";

    AdoreDiadm aDiadm;

    public void setUp() {
        aDiadm = new AdoreDiadm();
        aDiadm.setFormat(format);
        ArrayList<String> al = new ArrayList<String>();
        al.add(sem1);
        al.add(sem2);
        aDiadm.setSemantic(al);
    }

    public void testCreate() throws Exception {
        Diadm diadm = aDiadm.create();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DiadmSerializer serializer = new DiadmSerializer();
        serializer.write(stream, diadm);

        String output = stream.toString();
        System.err.println(output);
        assertTrue("find format in  diadm seriazliation", output
                .indexOf(format) != -1);
        assertTrue("find semantic 1 in  diadm seriazliation", output
                .indexOf(sem1) != -1);
        assertTrue("find semantic 2 in  diadm seriazliation", output
                .indexOf(sem2) != -1);

    }

    public void testParse() throws Exception {
        DiadmDeserializer de = new DiadmDeserializer();
        Diadm diadm = de.read(new ByteArrayInputStream(diadmstr.getBytes()));
        AdoreDiadm newaDiadm = new AdoreDiadm();
        newaDiadm.parse(diadm);
        assertEquals(format, newaDiadm.getFormat());
        assertEquals(aDiadm.getSemantic(), newaDiadm.getSemantic());
        assertEquals(aDiadm, newaDiadm);
    }

}
