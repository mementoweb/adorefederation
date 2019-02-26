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

import java.util.Set;
import java.util.HashSet;
import junit.framework.TestCase;

/**
 * 
 * test metadata parser, it uses oaidc as an example
 * 
 * @author liu_x
 * 
 */
public class MetadataParserTest extends TestCase {
    MetadataParser parser = null;

    String record = "<oai_dc:dc xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" "
	    + " xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n"
	    + " <dc:title>title1</dc:title>\n"
	    + " <dc:identifier>identifier1</dc:identifier>\n" + " </oai_dc:dc>";

    protected void setUp() throws Exception {
	ParserContext pc = new ParserContext();
	pc.putImplClass("oai_dc", OAIDCMetadata.class);
	parser = new MetadataParser();
	parser.setParserContext(pc);
    }

    public void testParse() throws Exception {
	OAIDCMetadata dc = (OAIDCMetadata) parser.parse(record, "oai_dc");
	Set<String> titles = new HashSet<String>();
	titles.add("title1");
	assertEquals(dc.getTitles(), titles);
	Set<String> identifiers = new HashSet<String>();
	identifiers.add("identifier1");
	assertEquals(dc.getIdentifiers(), identifiers);
    }

    public void testTwoParsers() throws Exception {
	ParserContext pc = parser.getParserContext();
	pc.putImplClass("oai_dc", OAIDCMetadata.class);
	pc.putImplClass("oai_dc_test", OAIDCMetadata.class);
	OAIDCMetadata dc = (OAIDCMetadata) parser.parse(record, "oai_dc_test");
	Set<String> titles = new HashSet<String>();
	titles.add("title1");
	assertEquals(dc.getTitles(), titles);
	Set<String> identifiers = new HashSet<String>();
	identifiers.add("identifier1");
	assertEquals(dc.getIdentifiers(), identifiers);
    }

}
