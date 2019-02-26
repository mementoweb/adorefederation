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

package gov.lanl.didlwriter.profile.isi;

import gov.lanl.didlwriter.profile.adore.AdoreDIDL;
import gov.lanl.didlwriter.profile.adore.AdoreDIIIdentifier;
import gov.lanl.didlwriter.profile.adore.AdoreDIIRelatedIdentifier;
import gov.lanl.didlwriter.profile.adore.AdoreDiadm;
import gov.lanl.didlwriter.profile.adore.AdoreItem;
import gov.lanl.didlwriter.profile.adore.AdoreComponent;
import gov.lanl.didlwriter.profile.adore.AdoreResource;

import gov.lanl.didlwriter.LANLDIDLException;
import gov.lanl.didlwriter.profile.AdoreConstants;

import info.repo.didl.serialize.DIDLSerializationException;

import java.net.URI;

public class AIREF {
    public enum COMPONENT_TYPE { SEM1, SEM2, RAW };

    private String copyright = null;

    private String collection = null;
    
    private String did;

    private String contentId;

    private AdoreComponent sem1Com;

    private AdoreComponent sem2Com;
    
    private AdoreComponent rawCom;

    public void setDocumentId(String identifier) {
        this.did = identifier;
    }

    public String getDocumentId() {
        return did;
    }

    public void setContentId(String identifier) {
        this.contentId = identifier;
    }

    public String getContentId() {
        return this.contentId;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    
    public AdoreComponent getSem1MarcXMLCom() {
        return sem1Com;
    }

    public AdoreComponent getSem2MarcXMLCom() {
        return sem2Com;
    }

    public AdoreComponent getRawXMLCom() {
        return rawCom;
    }
    
    /**
     * Adds a component to the didl
     * 
     * @param type
     *            The AIREF.COMPONENT_TYPE of component
     * @param componentID
     *            The value of the component id
     * @param digest
     *            The base32 encoded sha1 digest of resource
     * @param format
     *            The value of the format
     * @param created
     *            The timestamp of resource creation
     * @param creator
     *            The group/person who created the resource
     * @param mimetype
     *            The value of the mimetype of the resource
     * @param provenance
     *            The provenance URI (original URI)
     * @param content
     *            content of the resource, only applying to MARCXML
     */
    public void addComponent(AIREF.COMPONENT_TYPE type, String componentID,
            String digest, String format, String created, String creator, 
            String mimetype, URI provenance, String content) throws LANLDIDLException {

        AdoreComponent com = new AdoreComponent();

        // set dii:identifier
        AdoreDIIIdentifier diiId = new AdoreDIIIdentifier();
        diiId.setId(componentID);

        AdoreDIIRelatedIdentifier diiRelatedId = null;
        if (digest != null) {
            diiRelatedId = new AdoreDIIRelatedIdentifier();
            diiRelatedId.setId(digest);
            diiRelatedId.setRelationshipType("info:lanl-repo/sem/6");
        }

        AdoreDiadm diadm = new AdoreDiadm();
        // set provenance
        if (provenance != null)
            diadm.setIsFormatOf(provenance.toString());

        if (created != null)
            diadm.setCreated(created);

        if (format != null)
            diadm.setFormat(format);

        AdoreResource resource = new AdoreResource();
        resource.setMimeType(mimetype);
        resource.setRef(componentID);
        com.getResources().add(resource);

        if ((type == AIREF.COMPONENT_TYPE.SEM1 ||
              type == AIREF.COMPONENT_TYPE.SEM2) 
                && (content != null)) {
            AdoreResource inlineResource = new AdoreResource();
            inlineResource.setMimeType(mimetype);
            inlineResource.setContent(content);
            com.getResources().add(inlineResource);
        }

        com.setDIIIdentifier(diiId);
        if (diiRelatedId != null)
            com.setDIIRelatedIdentifier(diiRelatedId);

        com.setADiadm(diadm);

        if (type == COMPONENT_TYPE.SEM1) {
            if (creator != null && creator.length() > 0)
                diadm.setCreator(creator);
            diadm.getSemantic().add(AdoreConstants.BIBLIOGRAPHIC_SEMANTICID);
            sem1Com = com;
        } else if  (type == COMPONENT_TYPE.SEM2) {
            if (creator != null && creator.length() > 0)
                diadm.setCreator(creator);
            diadm.getSemantic().add(AdoreConstants.CITATION_SEMANTICID);
            sem2Com = com;
        } else if (type == COMPONENT_TYPE.RAW) {
            if (creator != null && creator.length() > 0)
                diadm.setCreator(creator);
            diadm.getSemantic().add(AdoreConstants.BIBLIOGRAPHIC_SEMANTICID);
            diadm.getSemantic().add(AdoreConstants.CITATION_SEMANTICID);
            rawCom = com;
        }
    }

    public String getXML() throws LANLDIDLException, DIDLSerializationException {
        AdoreDIDL didl = create();
        return didl.getXML();
    }

    public AdoreDIDL create() throws LANLDIDLException {
        try {
            AdoreDIDL didl = new AdoreDIDL();
            didl.setDidid(did);

            AdoreDIIIdentifier dii = new AdoreDIIIdentifier();
            dii.setId(contentId);

            AdoreDiadm diadm = new AdoreDiadm();
            if (copyright != null)
                diadm.setRights(copyright);
            diadm.setFormat(AdoreConstants.ITEM_AI_REF_FORMATID);
            if (collection != null)
                diadm.setIsPartOf(collection);
            else
                throw new Exception("Collection is not defined");

            AdoreItem rootItem = new AdoreItem();
            rootItem.setADiadm(diadm);
            rootItem.setADII(dii);

            // set marcxml provenance
            if (sem1Com.getADiadm().getIsFormatOf() == null)
                sem1Com.getADiadm().setIsFormatOf(rawCom.getDIIIdentifier().getId());
            if (sem2Com != null && sem2Com.getADiadm().getIsFormatOf() == null)
                sem2Com.getADiadm().setIsFormatOf(rawCom.getDIIIdentifier().getId());
            
            // add Component
            rootItem.getComs().add(sem1Com);
            if (sem2Com != null)
                rootItem.getComs().add(sem2Com);
            rootItem.getComs().add(rawCom);

            didl.setItem(rootItem);
            return didl;
        } catch (Exception ex) {
            throw new LANLDIDLException("error in create AIREF record",
                    ex);
        }

    }

    public void parse(java.io.InputStream stream) throws LANLDIDLException {
        try {
            AdoreDIDL didl = new AdoreDIDL();
            didl.parse(stream);
            did = didl.getDidid();
            contentId = didl.getItem().getADII().getId();
            sem1Com = didl.getItem().getComs().get(0);
            AdoreComponent com = didl.getItem().getComs().get(1);
            if (com.getResources().get(0).getMimeType().contains("xml")) {
                sem2Com = com;
            } else {
                rawCom = com;
            }
            if (rawCom == null)
                rawCom = didl.getItem().getComs().get(2);
        } catch (Exception ex) {
            throw new LANLDIDLException("error in parse AIREF", ex);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("did: ").append(did).append("\ncontentid: ")
                .append(contentId);
        return sb.toString();

    }

}
