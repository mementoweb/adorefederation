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

package gov.lanl.ockham.iesrdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.registryclient.parser.SerializationException;

/**
 * 
 * test Service serialization
 * 
 * @author liu_x
 * 
 */
public class ServiceSerializerTest extends TestCase {
    String doc = "<iesr:iesrDescription xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:iesr=\"http://iesr.ac.uk/terms/#\""
            + " xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:rslpcd=\"http://purl.org/rslp/terms#\">"
            + "\n<iesr:Service><dc:title>SciServer OAI-PMH Search</dc:title>"
            + "\n<dc:identifier>info:lanl-repo/oaipmh/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2</dc:identifier>"
            + "\n<rslpcd:locator>http://localhost:8080/adore-archive-accessor/Handler/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2</rslpcd:locator>"
            + "\n<dc:type>oai-pmh</dc:type><iesr:output>application/xml</iesr:output><iesr:supportsStandard>oai-pmh-2_0</iesr:supportsStandard>"
            + "\n<iesr:serves>info:lanl-repo/xmltape/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2</iesr:serves>"
            + "\n</iesr:Service></iesr:iesrDescription>";

    public void testParser() throws Exception {

        IESRService service = new IESRService();
        service.read(new ByteArrayInputStream(doc.getBytes()));
        assertEquals("SciServer OAI-PMH Search", service.getTitle());
        assertEquals("info:lanl-repo/oaipmh/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2", service.getIdentifier());
        assertEquals("http://localhost:8080/adore-archive-accessor/Handler/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2", service.getLocator());
        assertEquals("oai-pmh", service.getType());
        assertEquals("info:lanl-repo/xmltape/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2", service.getServes());
        assertEquals("oai-pmh-2_0", service.getSupportsStandard());
    }

    /**
     * Test deserialize->serialize->deserialize sequence, the result should be
     * same
     * 
     * @throws Exception
     */
    public void testDeserialize() throws Exception {
        IESRService service = new IESRService();
        service.read(new ByteArrayInputStream(doc.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        service.write(output);
        String doc1 = output.toString();
        // System.out.println(doc1);
        assertTrue(doc1.indexOf("iesr:Service") != -1);
        assertTrue(doc1.indexOf("info:lanl-repo/oaipmh/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2") != -1);
        IESRService svr1 = new IESRService();
        svr1.read(new ByteArrayInputStream(doc1.getBytes()));
        assertEquals(svr1.getIdentifier(), service.getIdentifier());
        assertEquals(svr1.getTitle(), service.getTitle());
        assertEquals(svr1.getLocator(), service.getLocator());
        assertEquals(svr1.getType(), service.getType());
        assertEquals(svr1.getServes(), service.getServes());
        assertEquals(svr1.getSupportsStandard(),service.getSupportsStandard());
    }

    public void testEmptyFieldsy() throws Exception {
        try {
            IESRService svr = new IESRService();
            svr.write(new ByteArrayOutputStream());
            assertTrue("empty fields test", false);
        } catch (SerializationException ex) {
            assertTrue("pass Empty fields test", true);
        }
    }

}
