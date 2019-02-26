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

import org.adore.didl.content.DC;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DCTest extends TestCase{
    private String xml="<dc:format xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\" " +
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xmlns:dc=\"http://purl.org/dc/elements/1.1/\">info:lanl-repo/pro/ai</dc:format>";
    private String format="info:lanl-repo/pro/ai";
       
    /** Creates a new instance of DCTest */
    public DCTest() {
    }
    
    public  void testCreate() throws Exception{
        DC dc=new DC(DC.Key.FORMAT,format);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        dc.write(stream, dc);
        String output=stream.toString();
	//        System.err.println(output);
        assertTrue("find dc format in seriazliation", output.indexOf(format)!=-1);
    }
    
    public void testParse() throws Exception{
        DC dc=new DC();
        DC dc1=(DC)dc.read(new ByteArrayInputStream(xml.getBytes()));
	//        System.err.println(dc1.getKey()+":"+dc1.getValue());
        assertTrue("dc format value matched in deserialization",dc1.getValue().equals(format));
        
        assertTrue("dc format key matched in deserialization",dc1.getKey().equals(DC.Key.FORMAT));
    }
}
