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

package gov.lanl.semantic.registry;

import junit.framework.*;
import java.util.Iterator;

/**
 * Semantic Test Case
 */
public class SemanticTest extends TestCase {
    String semitem1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<sem:semantic xmlns:sem=\"http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/ http://purl.lanl.gov/aDORe/schemas/2006-09/SemanticRegistry.xsd\">"
            + "<sem:identifier>info:lanl-repo/sem/1</pr:identifier>"
            + "<dc:identifier xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">info:lanl-repo/sem/1</dc:identifier>"
            + "<dc:title xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">Bibliographic</dc:title>"
            + "<sem:DIDentity>urn:mpeg:mpeg21:2002:02-DIDL-NS#Component</sem:DIDentity>"
        + "</sem:semantic>";

    String semitem2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<sem:semantic xmlns:sem=\"http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/ http://purl.lanl.gov/aDORe/schemas/2006-09/SemanticRegistry.xsd\">"
            + "<sem:identifier>info:lanl-repo/sem/6</pr:identifier>"
            + "<dc:identifier xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">info:lanl-repo/sem/6</dc:identifier>"
            + "<dc:title xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">Base32 SHA1 Digest</dc:title>"
            + "<sem:DIDentity>urn:mpeg:mpeg21:2002:02-DIDL-NS#Component</sem:DIDentity>"
        + "</sem:semantic>";

    String semantic_index = null;
    
    public void setUp() {
        try {
            semantic_index = System.getProperty("SemanticIndex.URL");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSemanticItem() {
        try {
            SemanticItem si1 = new SemanticItem("1", "2002-01-01", semitem1, true);
            SemanticItem si2 = new SemanticItem("2", "2002-01-01", semitem2, true);

            System.out.println(si1.toFlattext());
            System.out.println(si2.toFlattext());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSemanticIndex() {
        try {
            SemanticIndex fi = new SemanticIndex(semantic_index, true);
            for (Iterator it = fi.getList().iterator(); it.hasNext();) {
                SemanticItem item = (SemanticItem) (it.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public static void main(String args[]) {
        TestSuite suite = new TestSuite("Semantic Tests");
        suite.addTest(new SemanticTest() {
            protected void runTest() {
                testSemanticItem();
                testSemanticIndex();
            }
        });
        junit.textui.TestRunner.run(suite);
    }
}
