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

package gov.lanl.format.registry;

import junit.framework.*;
import java.util.Iterator;

/**
 * Format Test Case
 */
public class FormatTest extends TestCase {
    String fmtitem1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<fmt:format xmlns:fmt=\"http://library.lanl.gov/2005-08/aDORe/FormatRegistry/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/FormatRegistry/ http://purl.lanl.gov/aDORe/schemas/2006-09/FormatRegistry.xsd\">"
            + "<pr:identifier xmlns:pr=\"http://library.lanl.gov/2005-08/aDORe/PMPrequest/\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/PMPrequest/ http://purl.lanl.gov/aDORe/schemas/2006-09/PMPrequest.xsd\">info:lanl-repo/fmt/339</pr:identifier>"
            + "<dc:identifier xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">info:lanl-repo/fmt/339</dc:identifier>"
            + "<fmt:mimetype>audio/AMR</fmt:mimetype>"
            + "<fmt:DIDentity>urn:mpeg:mpeg21:2002:02-DIDL-NS#Component</fmt:DIDentity>"
            + "</fmt:format>";

    String fmtitem2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<fmt:format xmlns:fmt=\"http://library.lanl.gov/2005-08/aDORe/FormatRegistry/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/FormatRegistry/ http://purl.lanl.gov/aDORe/schemas/2006-09/FormatRegistry.xsd\">"
            + "<pr:identifier xmlns:pr=\"http://library.lanl.gov/2005-08/aDORe/PMPrequest/\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/PMPrequest/ http://purl.lanl.gov/aDORe/schemas/2006-09/PMPrequest.xsd\">info:lanl-repo/fmt/342</pr:identifier>"
            + "<dc:identifier xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">info:lanl-repo/fmt/342</dc:identifier>"
            + "<fmt:mimetype>audio/CN</fmt:mimetype>"
            + "<fmt:DIDentity>urn:mpeg:mpeg21:2002:02-DIDL-NS#Component</fmt:DIDentity>"
            + "<dc:source xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd\">http://www.iana.org/assignments/media-types/</dc:identifier>"
            + "<dc:description xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd\">http://www.rfc-editor.org/rfc/rfc3389.txt</dc:identifier>"
            + "</fmt:format>";

    String format_index = null;
    
    public void setUp() {
        try {
            format_index = System.getProperty("FormatIndex.URL");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testFormatItem() {
        try {
            FormatItem fi1 = new FormatItem("1", "2002-01-01", fmtitem1, true);
            FormatItem fi2 = new FormatItem("2", "2002-01-01", fmtitem2, true);

            System.out.println(fi1.toFlattext());
            System.out.println(fi2.toFlattext());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testFormatIndex() {
        try {
            FormatIndex fi = new FormatIndex(format_index, true);
            for (Iterator it = fi.getList().iterator(); it.hasNext();) {
                FormatItem item = (FormatItem) (it.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public static void main(String args[]) {
        TestSuite suite = new TestSuite("Format Tests");
        suite.addTest(new FormatTest() {
            protected void runTest() {
                testFormatItem();
                testFormatIndex();
            }
        });
        junit.textui.TestRunner.run(suite);
    }
}
