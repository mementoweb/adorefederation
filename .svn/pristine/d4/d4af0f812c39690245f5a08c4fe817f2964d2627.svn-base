/*
 * DIDLSerializerTest.java
 * JUnit based test
 *
 * Created on January 18, 2006, 9:05 AM
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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DIDLSerializerTest extends TestCase {
    private DIDLType didl01, didl02, didl03, didl04, didl05, didl06, didl07, didl08, didl09, didl10,didl11;
    protected final static String SAMPLE_TEXT = "All's well that ends well.";
    protected final static String SAMPLE_TEXT_ENC = "QWxsJ3Mgd2VsbCB0aGF0IGVuZHMgd2VsbC4=";
    protected final static String SAMPLE_ID = "urn:my:didl:id";
    protected final static String SAMPLE_XML = 
            "<diadm:DIADM " + 
               "xsi:schemaLocation=\"http://library.lanl.gov/2004-01/STB-RL/DIADM http://purl.lanl.gov/STB-RL/schemas/2004-01/DIADM.xsd\" " +
               "xmlns:diadm=\"http://library.lanl.gov/2004-01/STB-RL/DIADM\">" +
               "<dc:format xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">" +
               "info:ugent-repo/pro/simple_image" +
               "</dc:format>" + 
               "<dc:rights xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xsi:schemaLocation=\"http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dc.xsd\">" +
               "Copyright Ghent University" + 
               "</dc:rights>" +
               "<dcterms:isPartOf xmlns:dcterms=\"http://purl.org/dc/terms/\" xsi:schemaLocation=\"http://purl.org/dc/terms/ http://dublincore.org/schemas/xmls/qdc/2003/04/02/dcterms.xsd\">" + 
               "<![CDATA[info:sid/library.ugent.be/topo]]>&#160;&amp;<foo bar=\"&#160;&amp;\"/>" + 
               "</dcterms:isPartOf>" +
               "<!--\nAll's well\nthat ends well -->" +
            "</diadm:DIADM>";
    protected final static byte[] SAMPLE_BIN = new byte[] { 0x0A, 0x0B, 0x0B, 0x0A };
    protected final static String SAMPLE_BIN_ENC = "CgsLCg==";
    protected final static String SAMPLE_REF = "info:ugent-repo/i/%3Ctest%3E";
    protected final static String SAMPLE_DII = "info:ugent-repo/i/1234567890";
    protected final static String SAMPLE_URL = "http://www.google.com";
    protected final static String SAMPLE_LABEL = "test123";
    protected final static String MYDIDL_SCHEMALOCATION="http://example.com";
    
    public DIDLSerializerTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        didl01 = setUpDIDL01();
        didl02 = setUpDIDL02();
        didl03 = setUpDIDL03();
        didl04 = setUpDIDL04();
        didl05 = setUpDIDL05();
        didl06 = setUpDIDL06();
        didl07 = setUpDIDL07();
        didl08 = setUpDIDL08();
//        didl09 = setUpDIDL09();
        didl10 = setUpDIDL10();
        didl11 = setUpDIDL11();
    }
    
    // Simple DIDL only document id and DIEXT
    protected static DIDLType setUpDIDL01() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl01 = factory.newDIDL();
        
        didl01.setDIDLDocumentId(new URI(SAMPLE_ID));
        didl01.getAttributes().add(new MyAtt(SAMPLE_LABEL));
        
        return didl01;
    }
    
    // Add a simple text field
    protected static DIDLType setUpDIDL02() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl02 = factory.newDIDL();
        
        ResourceType res = didl02.addItem(didl02.newItem())
        .addComponent(didl02.newComponent())
        .addResource(didl02.newResource());
        
        res.setMimeType("text/plain");
        res.setContent(new ByteArray(SAMPLE_TEXT));
        
        StatementType stat = didl02.getItems()
                                   .get(0)
                                   .addDescriptor(didl02.newDescriptor())
                                   .addStatement(didl02.newStatement());
        stat.setMimeType("text/plain");
        stat.setContent(new ByteArray(SAMPLE_TEXT));
        
        return didl02;
    }
    
    // Add a simple text field encoded in base64
    protected static DIDLType setUpDIDL03() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl03 = factory.newDIDL();
        
        ResourceType res = didl03.addItem(didl03.newItem())
        .addComponent(didl03.newComponent())
        .addResource(didl03.newResource());
        
        res.setMimeType("text/plain");
        res.setEncoding("base64");
        res.setContent(new ByteArray(SAMPLE_TEXT));
        
        StatementType stat = didl03.getItems().get(0)
                                     .addDescriptor(didl03.newDescriptor())
                                     .addStatement(didl03.newStatement());
        
        stat.setMimeType("text/plain");
        stat.setEncoding("base64");
        stat.setContent(new ByteArray(SAMPLE_TEXT));
                
        return didl03;
    }
    
    // Add a XML
    protected static DIDLType setUpDIDL04() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl04 = factory.newDIDL();
        
        ResourceType res = didl04.addItem(didl04.newItem())
        .addComponent(didl04.newComponent())
        .addResource(didl04.newResource());
        
        res.setMimeType("application/xml");
        res.setContent(new ByteArray(SAMPLE_XML));
        
        StatementType stat = didl04.getItems().get(0)
                                   .addDescriptor(didl04.newDescriptor())
                                   .addStatement(didl04.newStatement());
        
        stat.setMimeType("application/xml");
        stat.setContent(new ByteArray(SAMPLE_XML));
        
        return didl04;
    }
    
    // Add a BIN
    protected static DIDLType setUpDIDL05() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl05 = factory.newDIDL();
        
        ResourceType res = didl05.addItem(didl05.newItem())
        .addComponent(didl05.newComponent())
        .addResource(didl05.newResource());
        
        res.setMimeType("application/xml");
        res.setEncoding("base64");
        res.setContent(new ByteArray(SAMPLE_BIN));
        
        StatementType stat = didl05.getItems().get(0)
                                   .addDescriptor(didl05.newDescriptor())
                                   .addStatement(didl05.newStatement());
        
        stat.setMimeType("application/xml");
        stat.setEncoding("base64");
        stat.setContent(new ByteArray(SAMPLE_BIN));
        
        return didl05;
    }
    
    // Add a Ref
    protected static DIDLType setUpDIDL06() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl06 = factory.newDIDL();
        
        ResourceType res = didl06.addItem(didl06.newItem())
        .addComponent(didl06.newComponent())
        .addResource(didl06.newResource());
        
        res.setMimeType("application/xml");
        res.setContentEncoding(new String[] { "xyz" , "abc" });
        res.setRef(new URI(SAMPLE_REF));
        
        StatementType stat = didl06.getItems().get(0)
                                  .addDescriptor(didl06.newDescriptor())
                                  .addStatement(didl06.newStatement());
        
        stat.setMimeType("application/xml");
        stat.setContentEncoding(new String[] { "xyz" , "abc" });
        stat.setRef(new URI(SAMPLE_REF));
        
        return didl06;
    }
    
    // DIDL With XML inline DIDLInfo
    protected static DIDLType setUpDIDL07() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl07 = factory.newDIDL();
        
        DIDLInfoType info = didl07.addDIDLInfo(didl07.newDIDLInfo());
        
        info.setContent(new ByteArray(SAMPLE_XML));
        
        return didl07;
    }
    
    // Add external attributes
    protected static DIDLType setUpDIDL08() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl08 = factory.newDIDL();
        
        ResourceType res = didl08.addItem(didl08.newItem())
        .addComponent(didl08.newComponent())
        .addResource(didl08.newResource());
        res.getAttributes().add(new MyAtt("test"));
        res.setMimeType("application/x-javascript");
        res.setContent(new ByteArray("<![CDATA[\nfunction test(){\n alert('hi');\n}\n]]>"));
        
        StatementType stat = didl08.getItems().get(0)
                                   .addDescriptor(didl08.newDescriptor())
                                   .addStatement(didl08.newStatement());
        stat.getAttributes().add(new MyAtt("test"));
        stat.setMimeType("application/x-javascript");
        stat.setContent(new ByteArray("<![CDATA[\nfunction test(){\n alert('hi');\n}\n]]>"));
                                    
        didl08.getItems().get(0)
        .getComponents().get(0)
        .getAttributes()
        .add(new MyAtt("test"));
        
        didl08.getItems().get(0)
        .getAttributes()
        .add(new MyAtt("test"));
        
        didl08.getAttributes()
        .add(new MyAtt("test"));
        
        return didl08;
    }
    
    // DIDL with DII
//    protected static DIDLType setUpDIDL09() throws Exception {
//        DIDLFactoryType factory = new DIDLFactory();
//        DIDLType didl09 = factory.newDIDL();
//        
//        StatementType stat = didl09.addItem(didl09.newItem())
//        .addDescriptor(didl09.newDescriptor())
//        .addStatement(didl09.newStatement());
//        
//        stat.setMimeType("application/xml; encoding=UTF-8");
//        stat.setContent(new DII(DII.IDENTIFIER, SAMPLE_DII));
//        
//        return didl09;
//    }
    
    // add same bytearray in same resource with different encoding
    protected static DIDLType setUpDIDL10() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl10 = factory.newDIDL();
       
        ByteArray google=new ByteArray(new java.net.URL(SAMPLE_URL));
        ResourceType res = didl10.addItem(didl10.newItem())
        .addComponent(didl10.newComponent())
        .addResource(didl10.newResource());
        
        
        res.setMimeType("text/html");
        res.setEncoding("base64");
        res.setContent(google);
        
        ResourceType res2=didl10.getItems().get(0)
        .getComponents().get(0)
        .addResource(didl10.newResource());
        
        res2.setMimeType("text/html");
        res2.setRef(new URI(SAMPLE_URL));
        res2.setContent(google);
        
        StatementType stat = didl10.getItems().get(0)
                                   .addDescriptor(didl10.newDescriptor())
                                   .addStatement(didl10.newStatement());
        
        stat.setMimeType("text/html");
        stat.setEncoding("base64");
        stat.setContent(google);
        
        StatementType stat2 = didl10.getItems().get(0)
                                   .addDescriptor(didl10.newDescriptor())
                                   .addStatement(didl10.newStatement());
        
        stat2.setMimeType("text/html");
        stat2.setRef(new URI(SAMPLE_URL));
        stat2.setContent(google);
        
        return didl10;
    }
    
    
      // Simple DIDL only document id 
    protected static DIDLType setUpDIDL11() throws Exception {
        DIDLFactoryType factory = new DIDLFactory();
        DIDLType didl11 = factory.newDIDL();
        
        didl11.setDIDLDocumentId(new URI(SAMPLE_ID));
        
        return didl11;
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLSerializerTest.class);
        
        return suite;
    }
    
    /**
     * Test of getRegistry method, of class info.repo.didl.serialize.xml.DIDLSerializer.
     */
    public void testGetRegistry() {
        System.out.println("getRegistry");
        
        try {
            DIDLSerializer instance = new DIDLSerializer();
            
            DIDLRegistryType reg = instance.getRegistry();
            reg.addSerializer(MyAtt.class, MyAtt.class);
            reg.addDeserializer(MyAtt.class, MyAtt.class);
            
            DIDLSerializerType ser = reg.getSerializer(MyAtt.class);
            DIDLDeserializerType des = reg.getDeserializer(MyAtt.class);
            
            assertTrue(ser instanceof MyAtt);
            assertTrue(des instanceof MyAtt);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * Test of write method, of class info.repo.didl.serialize.xml.DIDLSerializer.
     */
    public void testWrite() throws Exception {
        System.out.println("write");
        
        OutputStream outputStream;
        
        DIDLSerializer instance = new DIDLSerializer();
        DIDLRegistryType reg = instance.getRegistry();
        reg.addSerializer(MyAtt.class, MyAtt.class);
        
         outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl01);
        tryDIDL01(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl02);
        tryDIDL02(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl03);
        tryDIDL03(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl04);
        tryDIDL04(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl05);
        tryDIDL05(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl06);
        tryDIDL06(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl07);
        tryDIDL07(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl08);
        tryDIDL08(outputStream.toString());
        
//        outputStream = new ByteArrayOutputStream();
//        instance.write(outputStream, didl09);
//        tryDIDL09(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl10);
        tryDIDL10(outputStream.toString());
        
        
        outputStream = new ByteArrayOutputStream();
        instance.setProperty(SimpleSerializationProperty.SCHEMA_LOCATION,MYDIDL_SCHEMALOCATION);
        instance.write(outputStream, didl11);
        tryDIDL11(outputStream.toString());
        
    }
    
    private void tryDIDL01(String xml) {
        System.err.println(xml);
        
        assertTrue("Found DIDL namespace", xml.indexOf("xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\"") != -1);
        assertTrue("Found DIDL schema", xml.indexOf("http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-21_schema_files/did/didl.xsd") != -1);
        assertTrue("Found Document ID", xml.indexOf("DIDLDocumentId=\"" + SAMPLE_ID + "\"") != -1);
    }
    
    private void tryDIDL02(String xml) {
        System.err.println(xml);
        
        assertTrue("Found Resource", xml.indexOf("<didl:Resource mimeType=\"text/plain\">" + SAMPLE_TEXT + "</didl:Resource>") != -1);
        assertTrue("Found Statement", xml.indexOf("<didl:Statement mimeType=\"text/plain\">" + SAMPLE_TEXT + "</didl:Statement>") != -1);
    }
    
    private void tryDIDL03(String xml) {
        System.err.println(xml);
        
        assertTrue("Found encoded Resource", xml.indexOf("<didl:Resource mimeType=\"text/plain\" encoding=\"base64\">" + SAMPLE_TEXT_ENC + "</didl:Resource>") != -1);
        assertTrue("Found encoded Statement", xml.indexOf("<didl:Statement mimeType=\"text/plain\" encoding=\"base64\">" + SAMPLE_TEXT_ENC + "</didl:Statement>") != -1);
    }
    
    private void tryDIDL04(String xml) {
        System.err.println(xml);
        
        assertTrue("Found XML", xml.indexOf("<didl:Resource mimeType=\"application/xml\">" + SAMPLE_XML + "</didl:Resource>") != -1);
        assertTrue("Found XML", xml.indexOf("<didl:Statement mimeType=\"application/xml\">" + SAMPLE_XML + "</didl:Statement>") != -1);
    }
    
    private void tryDIDL05(String xml) {
        System.err.println(xml);
        
        assertTrue("Found encoded BIN", xml.indexOf("<didl:Resource mimeType=\"application/xml\" encoding=\"base64\">" + SAMPLE_BIN_ENC + "</didl:Resource>") != -1);
        assertTrue("Found encoded BIN", xml.indexOf("<didl:Statement mimeType=\"application/xml\" encoding=\"base64\">" + SAMPLE_BIN_ENC + "</didl:Statement>") != -1);
    }
    
    private void tryDIDL06(String xml) {
        System.err.println(xml);
        
        assertTrue("Found ref", xml.indexOf("<didl:Resource mimeType=\"application/xml\" contentEncoding=\"xyz abc\" ref=\"" + SAMPLE_REF + "\"></didl:Resource>") != -1);
        assertTrue("Found ref", xml.indexOf("<didl:Statement mimeType=\"application/xml\" contentEncoding=\"xyz abc\" ref=\"" + SAMPLE_REF + "\"></didl:Statement>") != -1);
    }
    
    private void tryDIDL07(String xml) {
        System.err.println(xml);
        
        assertTrue("Found didlinfo", xml.indexOf("<didl:DIDLInfo>" + SAMPLE_XML +"</didl:DIDLInfo>") != -1);
    }
    
    private void tryDIDL08(String xml) {
        System.err.println(xml);
        
        assertTrue("Found my Resource", xml.matches("(?s).*Resource[^>]+my:label=\"test\".*"));
        assertTrue("Found my Statement", xml.matches("(?s).*Statement[^>]+my:label=\"test\".*"));
        assertTrue("Found my Component", xml.matches("(?s).*Component[^>]+my:label=\"test\".*"));
        assertTrue("Found my Item", xml.matches("(?s).*Item[^>]+my:label=\"test\".*"));
        assertTrue("Found my DIDL", xml.matches("(?s).*DIDL[^>]+my:label=\"test\".*"));
    }
    
//    private void tryDIDL09(String xml) {
//        System.err.println(xml);
//        
//        assertTrue("Found DII", xml.matches("(?s).*dii:Identifier[^>]+>" + SAMPLE_DII + "<.*"));
//    }
    
    private void tryDIDL10(String xml) {
        System.err.println(xml);     
        assertTrue("Found encoded BIN", xml.indexOf("<didl:Resource mimeType=\"text/html\" encoding=\"base64\">") != -1);
        assertTrue("Found Ref BIN", xml.indexOf("<didl:Resource mimeType=\"text/html\" ref=\"" + SAMPLE_URL + "\"></didl:Resource>")!= -1);
        
        assertTrue("Found encoded BIN", xml.indexOf("<didl:Statement mimeType=\"text/html\" encoding=\"base64\">") != -1);
        assertTrue("Found Ref BIN", xml.indexOf("<didl:Statement mimeType=\"text/html\" ref=\"" + SAMPLE_URL + "\"></didl:Statement>")!= -1);
    }
    
      private void tryDIDL11(String xml) {
        System.err.println(xml);     
        assertTrue("Found my DIDL SchemaLocation", xml.indexOf(MYDIDL_SCHEMALOCATION) != -1);
       
    }
    
}
