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

package gov.lanl.didlwriter.profile.sci;

import gov.lanl.didlwriter.profile.AdoreConstants;
import junit.framework.TestCase;
import java.io.ByteArrayInputStream;

import java.net.URI;
/**
 *
 * @author liu_x
 */
public class SciTest  extends TestCase{
    //root element
    private String did="info:lanl-repo/i/dd7b17ea-bddf-11d9-9de5-c11b6cd8559";
    
    //root item
    private String contentid="info:doi/10.1016/j.dyepig.2004.06.022";
    
    
    //marcXML component
    private String marcContentId="info:lanl-repo/ds/dcf1b5ce-e9fc-489b-bd9a-f68d762e867d";
    private String marcdigest="urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d";
    private String marcCreated="2000-01-01";
    private String marcMimetype="application/xml";
    private String marcProvenance="http://www.google.com";
    private String marcContent= "<record xmlns=\"http://www.loc.gov/MARC21/slim\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd\">" +
            "<leader>00000nab  2200000za 4500</leader></record>";
    
    //sciXML component
    private String scixmlContentId="info:lanl-repo/ds/99569f9a-23db-4eb2-a0d4-ec1ff34dae5f";
    private String scixmldigest="urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d";
    private String scixmlCreated="2001-01-01";
    private String scixmlMimetype="application/xml";
    private String scixmlProvenance="http://arc.com/scixml";
   
    //first fulltext component
    private String fulltext1ContentId="info:lanl-repo/ds/bb41803b-8bf7-4737-9615-db5a645677d";
    private String fulltext1digest="urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d";
    private String fulltext1FmtId="info:lanl-repo/fmt/5";
    private String fulltext1Created="2002-01-01";
    private String fulltext1Mimetype="application/pdf";
    private String fulltext1Provenance="http://arc.com/proveme";
  
    //second fulltext component
    private String fulltext2ContentId="info:lanl-repo/ds/bb41803b-8bf7-4737-9615-db5asdf677d";
    private String fulltext2digest="urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d";
    private String fulltext2FmtId="info:lanl-repo/fmt/12";
    private String fulltext2Created="2003-01-01";
    private String fulltext2Mimetype="text/html";
    private String fulltext2Provenance="http://arc.com/html/proveme";
  
    /** Creates a new instance of SciTest */
    public SciTest() {
    }
    
    public void testCreate() throws Exception{
        String didlxml=createDIDL();
        System.err.println(didlxml);
        assertTrue(didlxml.indexOf(did)!=-1);
        assertTrue(didlxml.indexOf(contentid)!=-1);
        
        assertTrue(didlxml.indexOf(marcCreated)!=-1);
        assertTrue(didlxml.indexOf(marcContentId)!=-1);
        assertTrue(didlxml.indexOf(marcdigest)!=-1);
        assertTrue(didlxml.indexOf(marcCreated)!=-1);
        assertTrue(didlxml.indexOf(marcMimetype)!=-1);
        assertTrue(didlxml.indexOf(marcProvenance)!=-1);
        
        assertTrue(didlxml.indexOf(scixmlContentId)!=-1);
        assertTrue(didlxml.indexOf(scixmldigest)!=-1);
        assertTrue(didlxml.indexOf(scixmlCreated)!=-1);
        assertTrue(didlxml.indexOf(scixmlMimetype)!=-1);
        assertTrue(didlxml.indexOf(scixmlProvenance)!=-1);
        
        assertTrue(didlxml.indexOf(fulltext1ContentId)!=-1);
        assertTrue(didlxml.indexOf(fulltext1digest)!=-1);
        assertTrue(didlxml.indexOf(fulltext1FmtId)!=-1);
        assertTrue(didlxml.indexOf(fulltext1Created)!=-1);
        assertTrue(didlxml.indexOf(fulltext1Mimetype)!=-1);
        assertTrue(didlxml.indexOf(fulltext1Provenance)!=-1);
        
        assertTrue(didlxml.indexOf(fulltext2ContentId)!=-1);
        assertTrue(didlxml.indexOf(fulltext2digest)!=-1);
        assertTrue(didlxml.indexOf(fulltext2FmtId)!=-1);
        assertTrue(didlxml.indexOf(fulltext2Created)!=-1);
        assertTrue(didlxml.indexOf(fulltext2Mimetype)!=-1);
        assertTrue(didlxml.indexOf(fulltext2Provenance)!=-1);
    }
    
    
    private String createDIDL() throws Exception{
        ScienceServer sci=new ScienceServer();
        sci.setContentId(contentid);
        sci.setDocumentId(did);
        sci.addComponent(
                ScienceServer.COMPONENT_TYPE.MARCXML,
                marcContentId,
                marcdigest,
                AdoreConstants.MARC_XML_FORMATID,
                marcCreated,
                marcMimetype,
                new URI(marcProvenance),
                marcContent);
        
        sci.addComponent(
                ScienceServer.COMPONENT_TYPE.SCIXML,
                scixmlContentId,
                scixmldigest,
                AdoreConstants.SCI_XML_FORMATID,
                scixmlCreated,
                scixmlMimetype,
                new URI(scixmlProvenance),
                null);
        
        sci.addComponent(
                ScienceServer.COMPONENT_TYPE.FULLTEXT,
                fulltext1ContentId,
                fulltext1digest,
                fulltext1FmtId,
                fulltext1Created,
                fulltext1Mimetype,
                new URI(fulltext1Provenance),
                null);
        sci.addComponent(
                ScienceServer.COMPONENT_TYPE.FULLTEXT,
                fulltext2ContentId,
                fulltext2digest,
                fulltext2FmtId,
                fulltext2Created,
                fulltext2Mimetype,
                new URI(fulltext2Provenance),
                null);
        return sci.getXML();
    }
    
    
    public void testParse() throws Exception{
        String didlxml=createDIDL();
        ScienceServer sci=new ScienceServer();
        sci.parse(new ByteArrayInputStream(didlxml.getBytes()));
        
        assertEquals(sci.getDocumentId(),did);
        assertEquals(sci.getContentId(),contentid);
        
        
        assertEquals(sci.getMarcXMLCom().getDIIIdentifier().getId(),marcContentId);
        assertEquals(sci.getMarcXMLCom().getDIIRelatedIdentifier().getId(),marcdigest);
        assertEquals(sci.getMarcXMLCom().getDIIRelatedIdentifier().getRelationshipType(),"info:lanl-repo/sem/digest");
        assertEquals(sci.getMarcXMLCom().getADiadm().getCreated(),marcCreated);
        assertEquals(sci.getMarcXMLCom().getADiadm().getIsFormatOf(),marcProvenance);
        
        assertEquals(sci.getSciXMLCom().getDIIIdentifier().getId(),scixmlContentId);
        assertEquals(sci.getSciXMLCom().getDIIRelatedIdentifier().getId(),scixmldigest);
        assertEquals(sci.getSciXMLCom().getDIIRelatedIdentifier().getRelationshipType(),"info:lanl-repo/sem/digest");
        assertEquals(sci.getSciXMLCom().getADiadm().getCreated(),scixmlCreated);
        assertEquals(sci.getSciXMLCom().getADiadm().getIsFormatOf(),scixmlProvenance);
        
        
        assertEquals(sci.getFulltextCom().get(0).getDIIIdentifier().getId(),fulltext1ContentId);
        assertEquals(sci.getFulltextCom().get(0).getDIIRelatedIdentifier().getId(),fulltext1digest);
        assertEquals(sci.getFulltextCom().get(0).getDIIRelatedIdentifier().getRelationshipType(),"info:lanl-repo/sem/digest");
        assertEquals(sci.getFulltextCom().get(0).getADiadm().getCreated(),fulltext1Created);
        assertEquals(sci.getFulltextCom().get(0).getADiadm().getIsFormatOf(),fulltext1Provenance);
     
        
        assertEquals(sci.getFulltextCom().get(1).getDIIIdentifier().getId(),fulltext2ContentId);
        assertEquals(sci.getFulltextCom().get(1).getDIIRelatedIdentifier().getId(),fulltext2digest);
        assertEquals(sci.getFulltextCom().get(1).getDIIRelatedIdentifier().getRelationshipType(),"info:lanl-repo/sem/digest");
        assertEquals(sci.getFulltextCom().get(1).getADiadm().getCreated(),fulltext2Created);
        assertEquals(sci.getFulltextCom().get(1).getADiadm().getIsFormatOf(),fulltext2Provenance);
    }
}
