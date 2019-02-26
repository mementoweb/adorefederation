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

package gov.lanl.registryclient;

import junit.framework.TestCase;
import java.util.Set;
import java.util.HashSet;

import gov.lanl.registryclient.parser.*;

public class OAIRegistryTest extends TestCase {
    String url = "http://caltechbook.library.caltech.edu/perl/oai2";

    String identifier = "oai:caltechbook.library.caltech.edu:1";

    String title = "Cavitation and Bubble Dynamics";

    String prefix = "oai_dc";

    OAIRegistry registry;

    public void setUp() throws Exception {
        registry = new OAIRegistry(url, new MetadataParser(new ParserContext(
                "oai_dc", gov.lanl.registryclient.parser.OAIDCMetadata.class)));
    }

    public void testRecord() throws Exception {
        RegistryRecord record = registry.getRecord(identifier, prefix);
        assertEquals(identifier, record.getIdentifier());
        OAIDCMetadata meta = (OAIDCMetadata) (record.getMetaData());
        Set<String> titles = new HashSet<String>();
        titles.add(title);
        assertEquals(titles, meta.getTitles());
    }
}
