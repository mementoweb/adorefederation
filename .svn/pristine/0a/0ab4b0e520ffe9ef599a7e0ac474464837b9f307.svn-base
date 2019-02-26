/*
 * DIDLDeserializerTest.java
 * JUnit based test
 *
 * Created on January 19, 2006, 8:53 AM
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

import info.repo.didl.AttributeType;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.DIDLType;
import info.repo.didl.ResourceType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.serialize.AttributeStrategyConditionType;
import info.repo.didl.serialize.ContentStrategyConditionType;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLRegistryType;
import info.repo.didl.serialize.DIDLSerializerType;
import info.repo.didl.serialize.DIDLStrategyType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class DIDLDeserializerTest extends TestCase {
    private DIDLType didl01, didl02, didl03, didl04, didl05, didl06, didl07, didl08, didl09,didl10;
    
    public DIDLDeserializerTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        didl01 = DIDLSerializerTest.setUpDIDL01();
        didl02 = DIDLSerializerTest.setUpDIDL02();
        didl03 = DIDLSerializerTest.setUpDIDL03();
        didl04 = DIDLSerializerTest.setUpDIDL04();
        didl05 = DIDLSerializerTest.setUpDIDL05();
        didl06 = DIDLSerializerTest.setUpDIDL06();
        didl07 = DIDLSerializerTest.setUpDIDL07();
        didl08 = DIDLSerializerTest.setUpDIDL08();
//        didl09 = DIDLSerializerTest.setUpDIDL09();
        didl10 = DIDLSerializerTest.setUpDIDL10();
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(DIDLDeserializerTest.class);
        
        return suite;
    }
    
    /**
     * Test of getRegistry method, of class info.repo.didl.serialize.xml.DIDLDeserializer.
     */
    public void testGetRegistry() {
        System.out.println("getRegistry");
        
        try {
            DIDLDeserializer instance = new DIDLDeserializer();
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
     * Test of getStrategy method, of class info.repo.didl.serialize.xml.DIDLDeserializer.
     */
    public void testGetStrategy() {
        System.out.println("getStrategy");
        
        DIDLDeserializer instance = new DIDLDeserializer();
        DIDLStrategyType strat = instance.getStrategy();
        
        ContentStrategyConditionType s1 = new SimpleContentCondition(null, "text/plain", "urn:my:date", Calendar.class);
        ContentStrategyConditionType s2 = new SimpleContentCondition(null, "text/plain", null, String.class);
        ContentStrategyConditionType s3 = new SimpleContentCondition(null, null, "urn:my:date", Date.class );
        
        strat.addContentStrategy(s1);
        strat.addContentStrategy(s2);
        strat.addContentStrategy(s3);
        
        assertTrue("s1 exact", strat.getContentImplementation(null, "text/plain", "urn:my:date").equals(Calendar.class));
        assertTrue("s2 with ns", strat.getContentImplementation(null, "text/plain", "urn:my:whatever").equals(String.class));
        assertTrue("s2 without ns", strat.getContentImplementation(null, "text/plain", null).equals(String.class));
        assertTrue("s3 without mimetype", strat.getContentImplementation(null, null, "urn:my:date").equals(Date.class));
        assertTrue("s3 with mimetype", strat.getContentImplementation(null, "text/xml", "urn:my:date").equals(Date.class));
        assertTrue("catch all", strat.getContentImplementation(null, null, "urn:my:whatever").equals(ByteArray.class));
        assertTrue("catch all", strat.getContentImplementation(null, "xyz/abc", null).equals(ByteArray.class));
        assertTrue("catch all", strat.getContentImplementation(null, "xyz/abc", "urn:my:whatever").equals(ByteArray.class));
        assertTrue("catch all", strat.getContentImplementation(null, null, null).equals(ByteArray.class));
        
        AttributeStrategyConditionType a1 = new SimpleAttributeCondition("urn:my:date", Date.class);
        AttributeStrategyConditionType a2 = new SimpleAttributeCondition(null, String.class);
        
        strat.addAttributeStrategy(a1);
        strat.addAttributeStrategy(a2);
        
        assertTrue("a1 exact", strat.getAttributeImplementation("urn:my:date").equals(Date.class));
        assertTrue("a2 with ns", strat.getAttributeImplementation("urn:my:whatever").equals(String.class));
        assertTrue("a2 without ns", strat.getAttributeImplementation(null).equals(String.class));
    }
    
    /**
     * Test of read method, of class info.repo.didl.serialize.xml.DIDLDeserializer.
     */
    public void testRead() throws Exception {
        System.out.println("read");
        
        DIDLDeserializer instance = new DIDLDeserializer();
        instance.getRegistry().addDeserializer(MyAtt.class, MyAtt.class);
        instance.getStrategy().addAttributeStrategy(
                new SimpleAttributeCondition((new MyAtt()).getNamespace(), MyAtt.class)
                );
        
        DIDLType d1 = (DIDLType) instance.read(serialize(didl01));
        tryDIDL01(d1);
        
        DIDLType d2 = (DIDLType) instance.read(serialize(didl02));
        tryDIDL02(d2);
        
        DIDLType d3 = (DIDLType) instance.read(serialize(didl03));
        tryDIDL03(d3);
        
        DIDLType d4 = (DIDLType) instance.read(serialize(didl04));
        tryDIDL04(d4);
        
        DIDLType d5 = (DIDLType) instance.read(serialize(didl05));
        tryDIDL05(d5);
        
        DIDLType d6 = (DIDLType) instance.read(serialize(didl06));
        tryDIDL06(d6);
        
        DIDLType d7 = (DIDLType) instance.read(serialize(didl07));
        tryDIDL07(d7);
         
        if (instance.getProperty("copier:class").equals("info.repo.didl.impl.serialize.VerbatimFragmentCopier")){
            DIDLType d8 = (DIDLType) instance.read(serialize(didl08));
            tryDIDL08(d8);
        }
        
//        DIDLType d9 = (DIDLType) instance.read(serialize(didl09));
//        tryDIDL09(d9);
        
        DIDLType d10 = (DIDLType) instance.read(serialize(didl10));
        tryDIDL10(d10);
    }
    
    private void tryDIDL01(DIDLType didl) throws Exception{
        assertTrue("DIDLDocumentId", didl.getDIDLDocumentId().toString().equals(DIDLSerializerTest.SAMPLE_ID));
        AttributeType att = didl.getAttributes().iterator().next();
        assertTrue("MyAtt instance", att instanceof MyAtt);
        assertTrue("MyAtt value", ((MyAtt) att).getLabel().equals(DIDLSerializerTest.SAMPLE_LABEL) );
    }
    
    private void tryDIDL02(DIDLType didl) throws Exception{
        ResourceType res = didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(0);
        
        assertTrue("Resource mimetype", res.getMimeType().equals("text/plain"));
        assertTrue("Resource is ByteArray", res.getContent() instanceof ByteArray);
        assertTrue("Resource content", ((ByteArray) res.getContent()).getString().equals(DIDLSerializerTest.SAMPLE_TEXT));
    }
    
    private void tryDIDL03(DIDLType didl) throws Exception{
        ResourceType res = didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(0);
        
        assertTrue("Resource mimetype", res.getMimeType().equals("text/plain"));
        assertTrue("Resource encoding", res.getEncoding().equals("base64"));
        assertTrue("Resource is ByteArray", res.getContent() instanceof ByteArray);
        assertTrue("Resource content", ((ByteArray) res.getContent()).getString().equals(DIDLSerializerTest.SAMPLE_TEXT));
    }
    
    private void tryDIDL04(DIDLType didl) throws Exception{
        ResourceType res = didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(0);
        
        assertTrue("Resource mimetype", res.getMimeType().equals("application/xml"));
        assertTrue("Resource is ByteArray", res.getContent() instanceof ByteArray);
        // assertTrue("Resource content", ((ByteArray) res.getContent()).getString().equals(DIDLSerializerTest.SAMPLE_XML));
    }
    
    private void tryDIDL05(DIDLType didl) throws Exception{
        ResourceType res = didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(0);
        
        assertTrue("Resource mimetype", res.getMimeType().equals("application/xml"));
        assertTrue("Resource encoding", res.getEncoding().equals("base64"));
        assertTrue("Resource is ByteArray", res.getContent() instanceof ByteArray);
        byte[] sample = ((ByteArray) res.getContent()).getBytes();
        assertTrue("Resource content",
                sample.length == 4 &&
                sample[0] == 0x0A &&
                sample[1] == 0x0B &&
                sample[2] == 0x0B &&
                sample[3] == 0x0A);
    }
    
    private void tryDIDL06(DIDLType didl) throws Exception{
        ResourceType res = didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(0);
        
        assertTrue("Resource mimetype", res.getMimeType().equals("application/xml"));
        String[] ce = res.getContentEncoding();
        assertTrue("Resource contentEncoding", ce != null && ce.length == 2 && ce[0].equals("xyz") && ce[1].equals("abc"));
        assertTrue("Resource ref", res.getRef().toString().equals(DIDLSerializerTest.SAMPLE_REF));
    }
    
    private void tryDIDL07(DIDLType didl) throws Exception{
        DIDLInfoType info = didl.getDIDLInfos().get(0);
        
        assertTrue("DIDLInfo content is ByteArray", info.getContent() instanceof ByteArray);
       // assertTrue("DIDLInfo content", ((ByteArray)info.getContent()).getString().equals(DIDLSerializerTest.SAMPLE_XML));
    }
    
    private void tryDIDL08(DIDLType didl) throws Exception{
        ResourceType res = didl.getItems().get(0)
                            .getComponents().get(0)
                            .getResources().get(0);
        assertTrue("Resource mimetype", res.getMimeType().equals("application/x-javascript"));
        assertTrue("Resource content", ((ByteArray) res.getContent()).getString().equals("<![CDATA[\nfunction test(){\n alert('hi');\n}\n]]>"));
        Set<AttributeType> atts = res.getAttributes();
        
        int found = 0;
        for (Iterator<AttributeType> it = atts.iterator(); it.hasNext(); ) {
            AttributeType att = it.next();
            
            if (att.equals(new MyAtt())) {
                MyAtt myatt = (MyAtt) att;
                
                assertTrue("Resource my:label", myatt.getValue("label").equals("test"));
                
                found++;
            } 
        }
        
        assertTrue("Resource 1 ext. attrib.", found == 1);
        
        AttributeType comp_att = didl.getItems().get(0)
                                .getComponents().get(0)
                                .getAttributes().iterator().next();
        
        assertTrue("Component att", comp_att instanceof MyAtt);
        assertTrue("Component att value", ((MyAtt) comp_att).getValue("label").equals("test"));
        
        AttributeType item_att = didl.getItems().get(0)
                                .getAttributes().iterator().next();
        
        assertTrue("Item att", item_att instanceof MyAtt);
        assertTrue("Item att value", ((MyAtt) item_att).getValue("label").equals("test"));
        
        AttributeType didl_att = didl.getAttributes().iterator().next();
        
        assertTrue("DIDL att", didl_att instanceof MyAtt);
        assertTrue("DIDL att value", ((MyAtt) didl_att).getValue("label").equals("test"));
    }
    
//    private void tryDIDL09(DIDLType didl) throws Exception{
//        StatementType stat = didl.getItems().get(0)
//                                 .getDescriptors().get(0)
//                                 .getStatements().get(0);
//        
//        assertTrue("Statement mimeType", stat.getMimeType().equals("application/xml; encoding=UTF-8"));
//        assertTrue("Statement DII", stat.getContent() instanceof DII);
//        assertTrue("Statement DII value", ((DII) stat.getContent()).getValue().equals(DIDLSerializerTest.SAMPLE_DII));
//    }
    
     private void tryDIDL10(DIDLType didl) throws Exception{
        ResourceType res1 = didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(0);
        
        assertTrue("Resource mimetype", res1.getMimeType().equals("text/html"));
        assertTrue("Resource encoding", res1.getEncoding().equals("base64"));
        assertTrue("Resource is ByteArray", res1.getContent() instanceof ByteArray);
      
        ResourceType res2= didl.getItems().get(0)
        .getComponents().get(0)
        .getResources().get(1);
        
        assertTrue("Resource mimetype", res2.getMimeType().equals("text/html"));
        assertNull("Resource encoding", res2.getEncoding());
        assertTrue("Resource is ByteArray", res2.getContent() instanceof ByteArray);
        
    }
    
    private InputStream serialize(DIDLType didl) throws Exception {
        DIDLSerializer instance = new DIDLSerializer();
        DIDLRegistryType reg = instance.getRegistry();
        reg.addSerializer(MyAtt.class, MyAtt.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        instance.write(outputStream, didl);
        System.err.println(outputStream.toString());
        ByteArrayInputStream  inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }
}
