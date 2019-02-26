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

import java.util.Set;
import java.util.HashSet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.registryclient.parser.SerializationException;

/**
 * 
 * @author liu_x
 * 
 */
public class CollectionSerializerTest extends TestCase {

    String doc = "<iesr:iesrDescription xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:iesr=\"http://iesr.ac.uk/terms/#\""
            + " xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:rslpcd=\"http://purl.org/rslp/terms#\">\n"
            + "<iesr:Collection><dc:title>SciServer 2004</dc:title><dc:identifier>info:lanl-repo/xmltape/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2</dc:identifier>\n"
            + "<dc:type>xmltape</dc:type><iesr:hasService>info:lanl-repo/oaipmh/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2</iesr:hasService>\n"
            + "<iesr:hasService>info:lanl-repo/openurl/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2</iesr:hasService>\n"
            + "<rslpcd:contentsDateRange>2004-01-01</rslpcd:contentsDateRange><dcterms:isPartOf>info:sid/library.lanl.gov:sciserver</dcterms:isPartOf>\n"
            + "</iesr:Collection></iesr:iesrDescription>";

    String emptydoc = "<iesr:iesrDescription xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:iesr=\"http://iesr.ac.uk/terms/#\""
            + " xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:rslpcd=\"http://purl.org/rslp/terms#\"/>";

    public void testParser() throws Exception {
        IESRCollection coll = new IESRCollection();
        coll.read(new ByteArrayInputStream(doc.getBytes()));
        assertEquals("SciServer 2004", coll.getTitle());
        assertEquals(
                "info:lanl-repo/xmltape/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2",
                coll.getIdentifier());

        Set<String> types = new HashSet<String>();
        types.add("xmltape");
        assertEquals(types, coll.getTypes());

        Set<String> services = new HashSet<String>();
        services
                .add("info:lanl-repo/oaipmh/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2");
        services
                .add("info:lanl-repo/openurl/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2");
        assertEquals(services, coll.getServices());

        assertEquals("2004-01-01", coll.getContentRange());
        Set<String> isPartOfs = new HashSet<String>();
        isPartOfs.add("info:sid/library.lanl.gov:sciserver");
        assertEquals(isPartOfs, coll.getIsPartOf());
    }

    /**
     * Test deserialize->serialize->deserialize sequence, the result should be
     * same
     * 
     * @throws Exception
     */
    public void testDeserialize() throws Exception {
        IESRCollection coll = new IESRCollection();
        coll.read(new ByteArrayInputStream(doc.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        coll.write(output);
        String doc1 = output.toString();
        // System.out.println(doc1);
        assertTrue(doc1.indexOf("iesr:Collection") != -1);
        assertTrue(doc1
                .indexOf("info:lanl-repo/openurl/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2") != -1);
        IESRCollection coll1 = new IESRCollection();
        coll1.read(new ByteArrayInputStream(doc1.getBytes()));
        assertEquals(coll1.getIdentifier(), coll.getIdentifier());
        assertEquals(coll1.getContentRange(), coll.getContentRange());
        assertEquals(coll1.getIsPartOf(), coll.getIsPartOf());
        assertEquals(coll1.getTitle(), coll.getTitle());
        assertEquals(coll1.getServices(), coll.getServices());
    }

    /**
     * Test validation by throwing in some bad documents validation is
     * rudimentray only, but we do want to catch most obvious errors.
     * 
     * @throws Exception
     */
    public void testValidation() throws Exception {
        try {
            IESRCollection coll = new IESRCollection();
            ;
            coll.read(new ByteArrayInputStream(emptydoc.getBytes()));
            assertFalse("validation doesn't work", true);
        } catch (SerializationException ex) {
            assertTrue("pass validation test", true);
        }

    }

    public void testEmptyFieldsy() throws Exception {
        try {
            IESRCollection coll = new IESRCollection();
            coll.write(new ByteArrayOutputStream());
            assertTrue("empty fields test", false);
        } catch (SerializationException ex) {
            assertTrue("pass Empty fields test", true);
        }
    }

}
