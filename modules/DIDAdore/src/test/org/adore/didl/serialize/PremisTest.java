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

import org.adore.didl.content.Premis;
import org.adore.didl.serialize.PremisSerializer;
import org.adore.didl.serialize.PremisDeserializer;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class PremisTest extends TestCase{
    private String xml="<pre:object xsi:schemaLocation=\"http://www.loc.gov/standards/premis http://www.loc.gov/standards/premis/Object-v1-0.xsd\""
            +" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:pre=\"http://www.loc.gov/standards/premis\">"
            +"<pre:objectIdentifier><pre:objectIdentifierType>URI</pre:objectIdentifierType>"
            +"<pre:objectIdentifierValue>info:lanl-repo/ds/dcf1b5ce-e9fc-489b-bd9a-f68d762e867d</pre:objectIdentifierValue>"
            +"</pre:objectIdentifier><pre:objectCategory>File</pre:objectCategory><pre:objectCharacteristics>"
            +"<pre:compositionLevel>0</pre:compositionLevel><pre:size>415025</pre:size><pre:format><pre:formatRegistry>"
            +"<pre:formatRegistryName>http://purl.lanl.gov/aDORe/demo/adore-format-registry/OAIHandler</pre:formatRegistryName>"
            +"<pre:formatRegistryKey>info:lanl-repo/fmt/3</pre:formatRegistryKey></pre:formatRegistry></pre:format></pre:objectCharacteristics>"
            +"<pre:creatingApplication><pre:dateCreatedByApplication>2005-05-06T03:34:50Z</pre:dateCreatedByApplication></pre:creatingApplication>"
            +"<pre:storage><pre:storageMedium>Hard disk</pre:storageMedium></pre:storage></pre:object>";
    private String id="info:lanl-repo/ds/dcf1b5ce-e9fc-489b-bd9a-f68d762e867d";
    private String category="File";
    private String storageMedium="Hard disk";
    private int compositionLevel=0;
    private int size=415025;
    private String created="2005-05-06T03:34:50Z";
    private String fmt="info:lanl-repo/fmt/3";
    private String fmtRegistry="http://purl.lanl.gov/aDORe/demo/adore-format-registry/OAIHandler";
    
    
    /** Creates a new instance of DCTest */
    public PremisTest() {
    }
    
    public  void testCreate() throws Exception{
        Premis premis = new Premis();
        premis.setObjectIdentifier("URI", id);
        premis.setObjectCategory(category);
        premis.setStorageMedium(storageMedium);
        premis.setCompositionLevel(compositionLevel);
        premis.setCreatingApplication(created);
        premis.setFormat(fmtRegistry,fmt);
        premis.setSize(size);
        
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PremisSerializer serializer=new PremisSerializer();
        serializer.write(stream, premis);
        
        String output=stream.toString();
	//        System.err.println(output);
        assertTrue("find id", output.indexOf(id)!=-1);
        assertTrue("find category", output.indexOf(category)!=-1);
        assertTrue("find storageMedium", output.indexOf(storageMedium)!=-1);
        assertTrue("find compositionLevel", output.indexOf(Integer.toString(compositionLevel))!=-1);
        assertTrue("find created", output.indexOf(created)!=-1);
        assertTrue("find fmt", output.indexOf(fmt)!=-1);
        assertTrue("find fmtRegistry", output.indexOf(fmtRegistry)!=-1);
        assertTrue("find size", output.indexOf(Integer.toString(size))!=-1);
    }
    
    public void testParse() throws Exception{
        PremisDeserializer de=new PremisDeserializer();
        Premis pre=de.read(new ByteArrayInputStream(xml.getBytes()));
        assertEquals(pre.getObjectIdentifierValue(),id);
        assertEquals(pre.getObjectCategory(),category);
        assertEquals(pre.getStorageMedium(),storageMedium);
        assertEquals(pre.getCompositionLevel(),compositionLevel);
        assertEquals(pre.getCreatingApplication(),created);
        assertEquals(pre.getFormatName(),fmtRegistry);
        assertEquals(pre.getFormatKey(),fmt);
        assertEquals(pre.getSize(),size);
    }
}
