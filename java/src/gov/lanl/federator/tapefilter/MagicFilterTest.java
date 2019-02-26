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

package gov.lanl.federator.tapefilter;

import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.util.oai.oaiharvesterwrapper.Sets;

import java.util.HashSet;

import junit.framework.TestCase;

public class MagicFilterTest extends TestCase {
    RegistryRecord record;

    String identifier = "info:lanl-repo/xmltape/123";

    String datestamp = "2000-01-01";

    String type = "xmltape";

    protected void setUp() throws Exception {
        IESRCollection coll = new IESRCollection();
        coll.setIdentifier(identifier);
        coll.setContentRange("2000-01-01");
        coll.setTitle("ISI 2000");
        HashSet<String> isPartOf = new HashSet<String>();
        isPartOf.add("info:sid/isi");
        coll.setIsPartOf(isPartOf);
        HashSet<String> types = new HashSet<String>();
        types.add("xmltape");
        coll.setTypes(types);

        HashSet<String> serves = new HashSet<String>();
        serves.add("http://info.info/oaipmh/123");
        serves.add("http://info.info/openurl/123");
        coll.setServices(serves);
        record = new RegistryRecord(identifier, datestamp, coll);
    }

    public void testEmpty() throws Exception {
        MagicFilter filter = new MagicFilter();
        assertTrue(filter.accept(record));
    }

    public void testFromUntil() throws Exception {
        MagicFilter filter = new MagicFilter("1999-01-01", null, null);
        assertTrue(filter.accept(record));
        filter = new MagicFilter(null, "2009-01-01", null);
        assertTrue(filter.accept(record));
        filter = new MagicFilter(datestamp, datestamp, null);
        assertTrue(filter.accept(record));
        filter = new MagicFilter("2003-01-01T03-09011Z", "2009-01-01", null);
        assertTrue(!filter.accept(record));
    }

    public void testBASEURLSets() throws Exception {
        Sets sets = new Sets(Sets.BASE_URL, identifier);
        MagicFilter filter = new MagicFilter(null, null, sets);
        assertTrue(filter.accept(record));
        sets = new Sets(Sets.BASE_URL, "random chaos");
        filter = new MagicFilter(null, null, sets);
        assertTrue(!filter.accept(record));
    }

    public void testCollectionSets() throws Exception {
        Sets sets = new Sets(Sets.COLLECTION, "info:sid/isi");
        MagicFilter filter = new MagicFilter(null, null, sets);
        assertTrue(filter.accept(record));
        sets = new Sets(Sets.COLLECTION, "random chaos");
        filter = new MagicFilter(null, null, sets);
        assertTrue(!filter.accept(record));
    }

    public void testCombinations() throws Exception {
        Sets sets = new Sets(Sets.COLLECTION, "info:sid/isi");
        MagicFilter filter = new MagicFilter("1999-01-01", "2003-01-01", sets);
        assertTrue(filter.accept(record));
        filter = new MagicFilter("2002-01-01", "2003-01-01", sets);
        assertTrue(!filter.accept(record));
        sets = new Sets(Sets.COLLECTION, "random chaos");
        filter = new MagicFilter("1999-01-01", "2003-01-01", sets);
        assertTrue(!filter.accept(record));
    }

}
