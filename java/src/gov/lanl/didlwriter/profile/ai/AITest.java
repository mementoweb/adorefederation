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

package gov.lanl.didlwriter.profile.ai;

import gov.lanl.didlwriter.profile.AdoreConstants;
import junit.framework.TestCase;
import java.io.ByteArrayInputStream;

import java.net.URI;
/**
 *
 * @author liu_x
 */
public class AITest  extends TestCase{
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
    //biosisXML component
    private String biosisxmlContentId="info:lanl-repo/ds/99569f9a-23db-4eb2-a0d4-ec1ff34dae5f";
    private String biosisxmldigest="urn:sha1:c256af1f352b80d105b7003e6b24974f4b16a62d";
    private String biosisxmlCreated="2001-01-01";
    private String biosisxmlMimetype="application/xml";
    private String biosisxmlProvenance="http://arc.com/scixml";
  
    /** Creates a new instance of SciTest */
    public AITest() {
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
        
        assertTrue(didlxml.indexOf(biosisxmlContentId)!=-1);
        assertTrue(didlxml.indexOf(biosisxmldigest)!=-1);
        assertTrue(didlxml.indexOf(biosisxmlCreated)!=-1);
        assertTrue(didlxml.indexOf(biosisxmlMimetype)!=-1);
        assertTrue(didlxml.indexOf(biosisxmlProvenance)!=-1);
    }
    
    
    private String createDIDL() throws Exception{
        AI biosis = new AI();
        biosis.setCollection("info:sid/library.lanl.gov:biosis");
        biosis.setCopyright("copyright BIOSIS");
        biosis.setContentId(contentid);
        biosis.setDocumentId(did);
        biosis.addComponent(
                AI.COMPONENT_TYPE.MARCXML,
                marcContentId,
                marcdigest,
                AdoreConstants.MARC_XML_FORMATID,
                marcCreated,
                AdoreConstants.RTF_CREATORID,
                marcMimetype,
                new URI(marcProvenance),
                marcContent);
        
        biosis.addComponent(
                AI.COMPONENT_TYPE.AIXML,
                biosisxmlContentId,
                biosisxmldigest,
                AdoreConstants.BIOSIS_XML_FORMATID,
                biosisxmlCreated,
                null,
                biosisxmlMimetype,
                new URI(biosisxmlProvenance),
                null);
        return biosis.getXML();
    }
    
    
    public void testParse() throws Exception{
        String didlxml=createDIDL();
        AI biosis = new AI();
        biosis.parse(new ByteArrayInputStream(didlxml.getBytes()));
        
        assertEquals(biosis.getDocumentId(),did);
        assertEquals(biosis.getContentId(),contentid);
        
        assertEquals(biosis.getMarcXMLCom().getDIIIdentifier().getId(),marcContentId);
        assertEquals(biosis.getMarcXMLCom().getDIIRelatedIdentifier().getId(),marcdigest);
        assertEquals(biosis.getMarcXMLCom().getDIIRelatedIdentifier().getRelationshipType(),"info:lanl-repo/sem/digest");
        assertEquals(biosis.getMarcXMLCom().getADiadm().getCreated(),marcCreated);
        assertEquals(biosis.getMarcXMLCom().getADiadm().getIsFormatOf(),marcProvenance);
        
        assertEquals(biosis.getAIXMLCom().getDIIIdentifier().getId(),biosisxmlContentId);
        assertEquals(biosis.getAIXMLCom().getDIIRelatedIdentifier().getId(),biosisxmldigest);
        assertEquals(biosis.getAIXMLCom().getDIIRelatedIdentifier().getRelationshipType(),"info:lanl-repo/sem/digest");
        assertEquals(biosis.getAIXMLCom().getADiadm().getCreated(),biosisxmlCreated);
        assertEquals(biosis.getAIXMLCom().getADiadm().getIsFormatOf(),biosisxmlProvenance);
    }
}
