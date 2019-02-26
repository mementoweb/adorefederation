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

package gov.lanl.federator;

import gov.lanl.util.oai.oaiharvesterwrapper.GetRecord;
import gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers;
import gov.lanl.util.oai.oaiharvesterwrapper.ListMetadataFormats;
import gov.lanl.util.oai.oaiharvesterwrapper.ListRecords;
import gov.lanl.util.oai.oaiharvesterwrapper.ListSets;
import gov.lanl.util.oai.oaiharvesterwrapper.Record;

import java.util.Iterator;

public class FedTest extends FedAbstractTestCase {
    String sampleidentifier;

    String baseURL = null;

    public FedTest(String title) throws Exception {
        super(title);
    }

    public void setUp() throws Exception {
        try {
            baseURL = loader.getProperty("adore_federator_oaibaseurl");
            sampleidentifier = System
                    .getProperty("adore_federator_sampleidentifier");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    // use oaiharvester to test integrity of our enviroment

    public void testOAIVerbs() {
        try {
            // case1:identify
            gov.lanl.util.oai.oaiharvesterwrapper.Identify identify = new gov.lanl.util.oai.oaiharvesterwrapper.Identify(
                    baseURL);
            // case 2: getrecord

            GetRecord getrecord = new GetRecord(baseURL, sampleidentifier,
                    "didl");
            // case 3: listidentifiers
            ListIdentifiers listidentifiers = new ListIdentifiers(baseURL,
                    null, null, null, "didl");
            assertTrue(listidentifiers.size() > 0);
            // case4: listrecords
            ListRecords listrecords = new ListRecords(baseURL, null, null,
                    null, "didl");
            assertTrue(listrecords.size() > 0);
            // case 5: listsets
            ListSets listsets = new ListSets(baseURL);
            // case6. listmetadataformats
            ListMetadataFormats listmetadataformats = null;
            if (sampleidentifier == null)
                listmetadataformats = new ListMetadataFormats(baseURL);
            else
                listmetadataformats = new ListMetadataFormats(baseURL,
                        sampleidentifier);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

    // test DIXO
    public void testDIXO() {
        try {
            GetRecord getrecord_didl = new GetRecord(baseURL, sampleidentifier,
                    "didl");
            GetRecord getrecord_mets = new GetRecord(baseURL, sampleidentifier,
                    "mets");
            GetRecord getrecord_identifiers = new GetRecord(baseURL,
                    sampleidentifier, "identifiers");

            for (Iterator it = getrecord_identifiers.getRecords().iterator(); it
                    .hasNext();) {
                assertNotNull(((Record) it.next()).getMetadata());
            }
            // assertTrue(getrecord_didl.size()==1);
            // assertTrue(getrecord_mets.size()==1);
            assertEquals(getrecord_identifiers.size(), 1);

            assertTrue(((Record) (getrecord_identifiers.getRecords().get(0)))
                    .getMetadata().length() < ((Record) (getrecord_didl
                    .getRecords().get(0))).getMetadata().length());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

}
