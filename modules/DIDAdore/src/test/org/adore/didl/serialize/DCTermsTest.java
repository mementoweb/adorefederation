/*
 * DCTest.java
 *
 * Created on January 25, 2006, 3:58 PM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package org.adore.didl.serialize;

import org.adore.didl.content.DCTerms;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DCTermsTest extends TestCase{
    private String xml="<dcterms:isPartOf xsi:schemaLocation=\"http://purl.org/dc/terms/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dcterms.xsd\""+
            " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
            " xmlns:dcterms=\"http://purl.org/dc/terms/\">info:sid/library.lanl.gov:biosis</dcterms:isPartOf>";
    private String value="info:sid/library.lanl.gov:biosis";
    
    /** Creates a new instance of DCTest */
    public DCTermsTest() {
    }
    
    public  void testCreate() throws Exception{
        DCTerms terms=new DCTerms(DCTerms.Key.IS_PART_OF,value);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        terms.write(stream, terms);
        String output=stream.toString();
	//        System.err.println(output);
        assertTrue("find dcterms is part of in seriazliation", output.indexOf(value)!=-1);
    }
    
    public void testParse() throws Exception{
        DCTerms terms=new DCTerms();
        DCTerms term1=(DCTerms)terms.read(new ByteArrayInputStream(xml.getBytes()));
	//        System.err.println(term1.getKey()+":"+term1.getValue());
        assertTrue("dcterms isParOf value matched in deserialization",term1.getValue().equals(value));
        assertTrue("dcterms key matched in deserialization",term1.getKey().equals(DCTerms.Key.IS_PART_OF));
    }
}
