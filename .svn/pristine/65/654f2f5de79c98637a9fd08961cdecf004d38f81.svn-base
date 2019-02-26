/*
 * DiadmTest.java
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
import org.adore.didl.content.DC;
import org.adore.didl.content.Diadm;
import org.adore.didl.serialize.DiadmSerializer;
import org.adore.didl.serialize.DiadmDeserializer;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DiadmTest extends TestCase{
    private String xml="<diadm:Admin xsi:schemaLocation=\"http://library.lanl.gov/2005-08/aDORe/DIADM/ http://purl.lanl.gov/STB-RL/schemas/2004-01/DIADM.xsd\""+
            " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:diadm=\"http://library.lanl.gov/2005-08/aDORe/DIADM/\">"+
            "<dcterms:isPartOf xmlns:dcterms=\"http://purl.org/dc/terms/\">info:sid/library.lanl.gov:biosis</dcterms:isPartOf>"+
            "<dc:format xmlns:dc=\"http://purl.org/dc/elements/1.1/\">info:lanl-repo/pro/ai</dc:format>"+
            "</diadm:Admin>";
    
   
    private String fmt="info:lanl-repo/pro/ai";
    private String col="info:sid/library.lanl.gov:biosis";
    
    /** Creates a new instance of DCTest */
    public DiadmTest() {
    }
    
    public  void testCreate() throws Exception{
        Diadm diadm=new Diadm();
        DCTerms term=new DCTerms(DCTerms.Key.IS_PART_OF,col);
        DC dc=new DC(DC.Key.FORMAT,fmt);
        diadm.addDC(dc);
        diadm.addDCTerms(term);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DiadmSerializer serializer=new DiadmSerializer();
        serializer.write(stream, diadm);
        
        String output=stream.toString();
	//        System.err.println(output);
        assertTrue("find dcterms in  diadm seriazliation", output.indexOf(col)!=-1);
        assertTrue("find dc in  diadm seriazliation", output.indexOf(fmt)!=-1);
    }
    
    public void testParse() throws Exception{
        DiadmDeserializer de=new DiadmDeserializer();
        Diadm diadm=de.read(new ByteArrayInputStream(xml.getBytes()));
        DC dc=diadm.getDC().get(0);
        DCTerms term=diadm.getDCTerms().get(0);
	//        System.err.println(dc.getKey()+":"+dc.getValue());
	//        System.err.println(term.getKey()+":"+term.getValue());
        assertTrue("dcterms  value matched in deserialization",term.getValue().equals(col));
        assertTrue("dcterms key matched in deserialization",term.getKey().equals(DCTerms.Key.IS_PART_OF));
        assertTrue("dc value matched in deserialization",dc.getValue().equals(fmt));
        assertTrue("dc key matched in deserialization",dc.getKey().equals(DC.Key.FORMAT));
    }
    
   
    
}
