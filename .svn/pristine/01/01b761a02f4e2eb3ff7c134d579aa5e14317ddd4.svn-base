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

package gov.lanl.didlwriter.example;

import java.net.URI;
import java.io.ByteArrayInputStream;

import gov.lanl.didlwriter.profile.AdoreConstants;
import gov.lanl.didlwriter.profile.sci.ScienceServer;

/**
 * 
 * @author liu_x
 */
public class SciExample {

    public static void main(String[] args) throws Exception {
        ScienceServer sci = new ScienceServer();
        sci.setContentId("info:doi/10.1016/j.dyepig.2004.06.022");
        sci.setDocumentId("info:lanl-repo/i/dd7b17ea-bddf-11d9-9de5-c11b6cd8559");
        sci.addComponent(
                        ScienceServer.COMPONENT_TYPE.MARCXML,
                        "info:lanl-repo/ds/dcf1b5ce-e9fc-489b-bd9a-f68d762e867d",
                        "urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d",
                        AdoreConstants.MARC_XML_FORMATID,
                        "2000-01-01",
                        "application/xml",
                        null,
                        "<record xmlns=\"http://www.loc.gov/MARC21/slim\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                                + "xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd\">"
                                + "<leader>00000nab  2200000za 4500</leader></record>");

        sci.addComponent(ScienceServer.COMPONENT_TYPE.SCIXML,
                "info:lanl-repo/ds/99569f9a-23db-4eb2-a0d4-ec1ff34dae5f",
                "urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d",
                AdoreConstants.SCI_XML_FORMATID, "2001-01-01",
                "application/xml", new URI("http://sciserver/"), null);

        sci.addComponent(ScienceServer.COMPONENT_TYPE.FULLTEXT,
                "info:lanl-repo/ds/bb41803b-8bf7-4737-9615-db5a645677d9",
                "urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d",
                "info:lanl-repo/fmt/5", "2001-01-01", "application/pdf",
                new URI("http://sciserver/pdf"), null);
        sci.addComponent(ScienceServer.COMPONENT_TYPE.FULLTEXT,
                "info:lanl-repo/ds/bb41803b-8bf7-4737-9615-db5a6456770",
                "urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d",
                "info:lanl-repo/fmt/12", "2001-01-01", "application/html",
                new URI("http://sciserver/html"), null);

        System.out.println(sci.toString());
        System.out.println(sci.getXML());
        ScienceServer sci2 = new ScienceServer();
        sci2.parse(new ByteArrayInputStream(sci.getXML().getBytes()));
        System.out.println(sci2.getXML());
    }
}
