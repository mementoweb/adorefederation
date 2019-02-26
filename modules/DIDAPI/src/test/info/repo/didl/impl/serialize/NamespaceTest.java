/*
 * NamespaceTest.java
 *
 * JUnit based test to demonstrate error with namespace
 *
 * In DIDL
 *
 * Created on $Date: 2006/10/18 23:08:09 $
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

package info.repo.didl.impl.serialize;

import info.repo.didl.DIDLFactoryType;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.DIDLType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.DIDLFactory;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLRegistryType;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.URI;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class NamespaceTest extends TestCase {
    
    protected final static  String didlxml1="<didl:DIDL xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\" DIDLDocumentId=\"didl01\" xmlns:foo=\"info:foo\">\n"+
            "<didl:Item>\n"+
            "<didl:Descriptor>"+
            "<didl:Statement mimeType=\"application/xml\">"+
            "<foo:bar>thanks</foo:bar>"+
            "</didl:Statement>"+
            "</didl:Descriptor>"+
            "</didl:Item>"+
            "</didl:DIDL>";
    
    
    protected final static  String didlxml2="<didl:DIDL xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\" DIDLDocumentId=\"didl01\" >\n"+
            "<didl:Item>\n"+
            "<didl:Descriptor>"+
            "<didl:Statement mimeType=\"application/xml\">"+
            "<bar xmlns=\"http://example.com/abc\">test2</bar>"+
            "</didl:Statement>"+
            "</didl:Descriptor>"+
            "</didl:Item>"+
            "</didl:DIDL>";
    
    protected final static  String odddidl="<didl:DIDL xmlns:bar=\"info:bar\"  xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\" DIDLDocumentId=\"didl01\" >\n"+
            "<didl:Item>\n"+
            "<didl:Descriptor>"+
            "<didl:Statement mimeType=\"application/xml\">"+
            "<foo xmlns=\"info:foo\">"+
            "<bar:bar fruit=\"apple\">"+
            "<junk:JUNK xmlns:junk=\"info:junk\"><bar:bar>test2</bar:bar></junk:JUNK>"+
            "</bar:bar></foo>"+
            "</didl:Statement>"+
            "</didl:Descriptor>"+
            "</didl:Item>"+
            "</didl:DIDL>";
    
    protected final static String odddidl2="<didl:DIDL xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\" DIDLDocumentId=\"didl01\" >\n"+
            "<didl:Item>\n"+
            "<didl:Descriptor>"+
            "<didl:Statement mimeType=\"application/xml\" xmlns:bar=\"info:bar\" xmlns:xlink=\"info:xlink\">"+
            "<foo xmlns=\"info:foo\">"+
            "<bar:bar xlink:href=\"a\">Hello</bar:bar>"+
            "<bar:bar xlink:href=\"b\">World</bar:bar>"+
            "</foo>"+
            "</didl:Statement>"+
            "</didl:Descriptor>"+
            "</didl:Item>"+
            "</didl:DIDL>";
    
    
    protected final static String xmlcopier="info.repo.didl.impl.serialize.MegginsonXMLCopier";
    protected final static String datacopier="info.repo.didl.impl.serialize.MegginsonDataCopier";
    protected final static String verbatimcopier="info.repo.didl.impl.serialize.VerbatimFragmentCopier";
    
    public void testMagginsonXMLCopierParse() throws Exception{
        parse(didlxml1,xmlcopier);
        parse(didlxml2,xmlcopier);
        parse(odddidl,xmlcopier);
        parse(odddidl2,xmlcopier);
        
    }
    
    public void testMaggisonDataCopierParse() throws Exception{
        parse(didlxml1,datacopier);
        parse(didlxml2,datacopier);
        parse(odddidl,datacopier);
        parse(odddidl2,datacopier);
        
    }
    
    /**
     * We know VerbatimCopier cannot properly process complex namespace.
     */
    public void testVerbatimCopierParse(){
        try{
             parse(didlxml1,verbatimcopier);
             assertTrue("verbatimcopier",false);
        }
        catch (Exception ex){
            assertTrue("verbatimcopier",true);
        }
       
    }
    
    private void parse(String xml, String copierClass) throws Exception{
            DIDLDeserializer instance = new DIDLDeserializer();
            instance.setProperty("copier:class",copierClass);
            DIDLType didl= (DIDLType) instance.read(new ByteArrayInputStream(xml.getBytes()));
            StatementType stmt=didl.getItems().get(0)
            .getDescriptors().get(0)
            .getStatements().get(0);
            
            assertEquals("statement mimetype ","application/xml",stmt.getMimeType());
            
            ByteArray ba=(ByteArray)(stmt.getContent());
            
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setFeature("http://xml.org/sax/features/namespaces", true);
            System.out.println(new String(ba.getBytes()));
            parser.parse(new InputSource(new ByteArrayInputStream(ba.getBytes())));
       
    }
    
    
}
