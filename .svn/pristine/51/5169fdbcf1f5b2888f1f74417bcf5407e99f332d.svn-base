/*
 * DIDLTest.java
 *
 * Created on January 26, 2006, 10:39 AM
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

package org.adore.didl;

import info.repo.didl.ComponentType;
import info.repo.didl.DIDLFactoryType;
import info.repo.didl.DIDLType;
import info.repo.didl.ResourceType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;

import junit.framework.TestCase;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;
import org.adore.didl.content.DII;
import org.adore.didl.content.Diadm;
import org.adore.didl.helper.Env;
import org.adore.didl.helper.Helper;

/**
 *
 * in this test case we create a DIDL with following structure
 * -- Item
 *    -- dii
 *    -- diadm
 *      --dc:format
 *      --dcterms:isPartOf
 *    -- component
 *      --resource (text)
 *      --resource (xml)
 *      --resource (ref)
 *      
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DIDLTest extends TestCase{
    private String diitext="info:repo:dii";
    private String didlDocumentId="uuid:123";
    private String fmt="info:repo:fmt/3";
    private String col="info:repo:isi";
    private String text ="sailing";
    private String xml ="<foo xmlns=\"urn:foo\">abc</foo>";
    private String ref="http://www.google.com";
    
    
    
    /** Creates a new instance of DIDLTest */
    public DIDLTest() {
        
    }
    
    public void testCreate() throws Exception{
        String didlxml=createDIDL();
        //     System.err.println(didlxml);
        assertTrue(didlxml.indexOf(diitext)!=-1);
        assertTrue(didlxml.indexOf(didlDocumentId)!=-1);
        assertTrue(didlxml.indexOf(fmt)!=-1);
        assertTrue(didlxml.indexOf(col)!=-1);
        assertTrue(didlxml.indexOf(text)!=-1);
        assertTrue(didlxml.indexOf(xml)!=-1);
        assertTrue(didlxml.indexOf(ref)!=-1);
        
    }
    
    private String createDIDL() throws Exception{
        Env env=new Env();
        DIDLFactoryType factory = env.getDIDLFactory();
        DIDLType didl = factory.newDIDL();
        didl.setDIDLDocumentId(new URI(didlDocumentId));
        didl.getAttributes().add(Helper.newDIEXT());
        
        didl.addItem(Helper.newItem(didl)) //set dii
        .addDescriptor(didl.newDescriptor())
        .addStatement(Helper.newXMLStatement(didl,new DII(DII.IDENTIFIER,diitext)));
        
        Diadm diadm= new Diadm(); //create diadm for item
        diadm.addDC(new DC(DC.Key.FORMAT,fmt));
        diadm.addDCTerms(new DCTerms(DCTerms.Key.IS_PART_OF,col));
        
        
        didl.getItems().get(0)
        .addDescriptor(didl.newDescriptor())
        .addStatement(Helper.newXMLStatement(didl,diadm));
        
        didl.getItems().get(0)
        .addComponent(Helper.newComponent(didl));
        
        
        ResourceType res=didl.newResource();
        res.setMimeType("text/plain");
        res.setContent(Helper.newByteArray(text));
        
        
        didl.getItems().get(0)
        .getComponents().get(0)
        .addResource(res);
        
        res=didl.newResource();
        res.setMimeType("text/xml");
        res.setContent(Helper.newByteArray(xml));
        
        
        didl.getItems().get(0)
        .getComponents().get(0)
        .addResource(res);
        
        res=didl.newResource();
        res.setMimeType("text/html");
        res.setRef(new URI(ref));
        res.setContent(Helper.newByteArray(new URL(ref)));
        
        
        didl.getItems().get(0)
        .getComponents().get(0)
        .addResource(res);
        
        
        DIDLSerializerType serializer=env.getDIDLSerializer();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serializer.write(out, didl);
        out.close();
        return out.toString();
        
        
    }
    
    public void testParse() throws Exception{
        String didlxml=createDIDL();
        Env env=new Env();
        DIDLDeserializerType deserializer=env.getDIDLDeSerializer();
        System.out.println(didlxml);
        DIDLType didl=(DIDLType)deserializer.read(new ByteArrayInputStream(didlxml.getBytes()));
        assertEquals(didl.getDIDLDocumentId().toString(),didlDocumentId);
        DII dii= (DII)didl.getItems().get(0).getDescriptors().get(0).getStatements().get(0).getContent();
        assertEquals(dii.getValue(),diitext);
        
        Diadm diadm=(Diadm)didl.getItems().get(0).getDescriptors().get(1).getStatements().get(0).getContent();
        assertEquals(diadm.getDC().get(0).getKey(),DC.Key.FORMAT);
        assertEquals(diadm.getDC().get(0).getValue(),fmt);
        assertEquals(diadm.getDCTerms().get(0).getKey(),DCTerms.Key.IS_PART_OF);
        assertEquals(diadm.getDCTerms().get(0).getValue(),col);
        
        ComponentType com=didl.getItems().get(0).getComponents().get(0);
        
        assertEquals(com.getResources().get(0).getMimeType(),"text/plain");
        assertEquals(((ByteArray)(com.getResources().get(0).getContent())).getString(),text);
        
        assertEquals(com.getResources().get(1).getMimeType(),"text/xml");
        //System.err.println(((ByteArray)(com.getResources().get(1).getContent())).getString());
	//   System.err.println(xml);
        assertEquals(((ByteArray)(com.getResources().get(1).getContent())).getString(),xml);
        
        assertEquals(com.getResources().get(2).getMimeType(),"text/html");
        assertEquals(com.getResources().get(2).getRef().toString(),ref);
        
        
        
    }
}
