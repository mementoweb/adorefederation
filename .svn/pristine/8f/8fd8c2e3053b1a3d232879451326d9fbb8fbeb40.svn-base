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

package gov.lanl.registryclient.parser;

import junit.framework.TestCase;
import java.util.Set;
import java.util.TreeSet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class OAIDCMetadataTest extends TestCase {
    Set<String> titles = null;

    Set<String> identifiers = null;

    String record = "<oai_dc:dc xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" "
	    + " xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n"
	    + " <dc:title>title1</dc:title>\n"
	    + " <dc:identifier>identifier1</dc:identifier>\n" + " </oai_dc:dc>";

    protected void setUp() throws Exception {
	super.setUp();
	titles = new TreeSet<String>();
	titles.add("title1");
	identifiers = new TreeSet<String>();
	identifiers.add("identifier1");
    }

    public void testParse() throws Exception {
	OAIDCMetadata meta = new OAIDCMetadata();
	System.out.println(record);
	meta.read(new ByteArrayInputStream(record.getBytes()));
	assertEquals(titles, meta.getTitles());
	assertEquals(identifiers, meta.getIdentifiers());
    }

    public void testWrite() throws Exception {
	OAIDCMetadata meta = new OAIDCMetadata();
	meta.setTitles(titles);
	meta.setIdentifiers(identifiers);
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	meta.write(out);
	out.close();
	String xml = out.toString();
	assertTrue(xml.indexOf("title1") != -1);
	assertTrue(xml.indexOf("identifier1") != -1);

    }

    public void testReadWrite() throws Exception {
	// create first record
	OAIDCMetadata meta = new OAIDCMetadata();
	meta.read(new ByteArrayInputStream(record.getBytes()));
	// save it
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	meta.write(out);
	out.close();

	// read it into another record
	OAIDCMetadata meta1 = new OAIDCMetadata();
	meta1.read(new ByteArrayInputStream(out.toByteArray()));

	// compare
	assertEquals(meta, meta1);
    }

}
