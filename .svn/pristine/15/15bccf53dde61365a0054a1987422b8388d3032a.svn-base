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

package gov.lanl.federator.processor;

import java.util.ArrayList;
import java.util.HashMap;

import gov.lanl.federator.FedConstants;
import gov.lanl.federator.FedAbstractTestCase;

public class DIDLProcessorTest extends FedAbstractTestCase {
    ArrayList<gov.lanl.util.oai.oaiharvesterwrapper.Record> al;

    String identifier = "oai:caltechbook.library.caltech.edu:1";

    String recordxml = "<record >"
            + "<header >"
            + "<identifier >oai:caltechbook.library.caltech.edu:1</identifier>"
            + "<datestamp >2005-11-14</datestamp>"
            + "<setSpec >7374617475733D707562</setSpec>"
            + "<setSpec >7375626A656374733D616C6C:6F7074696F6E6D65</setSpec></header>"
            + "\n<metadata >"
            + "\n<didl:DIDL xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\" xmlns:diext=\"http://library.lanl.gov/2004-04/STB-RL/DIEXT\" "
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" diext:DIDid=\"info:lanl-repo/i/e6ca6c94-0255-11db-9eeb-fe74e4523d80\" "
            + "diext:DIDcreated=\"2006-06-23T01:16:30Z\" xsi:schemaLocation=\"urn:mpeg:mpeg21:2002:02-DIDL-NS "
            + "http://purl.lanl.gov/STB-RL/schemas/2004-11/DIDL.xsd http://library.lanl.gov/2004-04/STB-RL/DIEXT http://purl.lanl.gov/STB-RL/schemas/2004-04/DIEXT.xsd\">"
            + "\n<didl:Item id=\"uuid-e6d36aec-0255-11db-9eeb-fe74e4523d80\">"
            + "\n<didl:Descriptor>"
            + "\n<didl:Statement mimeType=\"text/xml; charset=utf-8\">"
            + "\n<dii:Identifier xmlns:dii=\"urn:mpeg:mpeg21:2002:01-DII-NS\" schemaLocation=\"urn:mpeg:mpeg21:2002:01-DII-NS http://purl.lanl.gov/STB-RL/schemas/2003-09/DII.xsd\">"
            + "info:lanl-repo/isi/A1990AT40400011</dii:Identifier>"
            + "\n</didl:Statement>" + "\n</didl:Descriptor>" + "\n</didl:Item>"
            + "\n</didl:DIDL>" + "\n</metadata></record>";

    public DIDLProcessorTest() throws Exception {
        super();
    }

    public void setUp() throws Exception {
        ORG.oclc.oai.harvester.verb.Record r = new ORG.oclc.oai.harvester.verb.Record(recordxml);
        gov.lanl.util.oai.oaiharvesterwrapper.Record wrappedRecord = new gov.lanl.util.oai.oaiharvesterwrapper.Record(r);
        al = new ArrayList<gov.lanl.util.oai.oaiharvesterwrapper.Record>();
        al.add(wrappedRecord);
    }

    public void testGetDIDL() throws Exception {
        DIDLProcessor processor = new DIDLProcessor(al, 1);
        HashMap<String, String> result = processor.process();
        assertEquals(1, result.size());
        assertTrue(result.get(identifier).indexOf("didl:DIDL") != -1);
    }

    public void testXSLT() throws Exception {
        DIDLProcessor processor = new DIDLProcessor(al, 1, loader
                .getProperty(FedConstants.ADORE_FEDERATOR_CONFIGDIR)
                + "/xslt/identifiers.xsl");
        HashMap<String, String> result = processor.process();
        assertEquals(1, result.size());
        System.out.println(result.get(identifier));
    }
}
