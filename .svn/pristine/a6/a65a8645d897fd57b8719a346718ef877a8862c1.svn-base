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

import java.util.List;
import java.util.ArrayList;

import java.net.URI;

/**
 * 
 * @author liu_x
 */
public class ScienceServer {
    public enum COMPONENT_TYPE {
        MARCXML, SCIXML, FULLTEXT
    };

    private static final String COPYRIGHT = "copyright SCISERVER";

    private static final String COLLECTION = "info:sid/library.lanl.gov:sciserver";

    private String did;

    private String contentId;

    private AdoreComponent marcXMLCom;

    private AdoreComponent sciXMLCom;

    private List<AdoreComponent> rawComList;

    /** Creates a new instance of Sci */
    public ScienceServer() {
        rawComList = new ArrayList<AdoreComponent>();
    }

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

    public AdoreComponent getMarcXMLCom() {
        return marcXMLCom;
    }

    public AdoreComponent getSciXMLCom() {
        return sciXMLCom;
    }

    public List<AdoreComponent> getFulltextCom() {
        return rawComList;
    }

    /**
     * Adds a component to the didl
     * 
     * @param type
     *            The ScienceServer.COMPONENT_TYPE type
     * @param componentID
     *            The value of the component id
     * @param digest
     *            The sha1 digest
     * @param format
     *            The value of the format
     * @param created
     *            The value of the created time
     * @param mimetype
     *            The value of the mimetype of the resource
     * @param provenance
     *            The provenance URI (original URI)
     * @param content
     *            content of the resource, only applying to MARCXML
     */
    public void addComponent(ScienceServer.COMPONENT_TYPE type,
            String componentID, String digest, String format, String created,
            String mimetype, URI provenance, String content)
            throws LANLDIDLException {

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
        if (provenance != null) {
            diadm.setIsFormatOf(provenance.toString());
        }

        if (created != null)
            diadm.setCreated(created);

        if (format != null)
            diadm.setFormat(format);

        AdoreResource resource = new AdoreResource();
        resource.setMimeType(mimetype);
        resource.setRef(componentID);
        com.getResources().add(resource);

        if ((type == ScienceServer.COMPONENT_TYPE.MARCXML) && (content != null)) {
            AdoreResource inlineResource = new AdoreResource();
            inlineResource.setMimeType(mimetype);
            inlineResource.setContent(content);
            com.getResources().add(inlineResource);
        }

        com.setDIIIdentifier(diiId);
        if (diiRelatedId != null)
            com.setDIIRelatedIdentifier(diiRelatedId);
        com.setADiadm(diadm);

        if (type == COMPONENT_TYPE.MARCXML) {
            diadm.setCreator(AdoreConstants.RTF_CREATORID);
            diadm.getSemantic().add(AdoreConstants.BIBLIOGRAPHIC_SEMANTICID);
            marcXMLCom = com;
        } else if (type == COMPONENT_TYPE.SCIXML) {
            diadm.getSemantic().add(AdoreConstants.BIBLIOGRAPHIC_SEMANTICID);
            sciXMLCom = com;
        } else {
            diadm.getSemantic().add(AdoreConstants.FULLTEXT_SEMANTICID);
            rawComList.add(com);
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
            diadm.setRights(COPYRIGHT);
            diadm.setFormat(AdoreConstants.ITEM_SCI_FORMATID);
            diadm.setIsPartOf(COLLECTION);

            AdoreItem rootItem = new AdoreItem();
            rootItem.setADiadm(diadm);
            rootItem.setADII(dii);

            // set marcxml provenance
            if (marcXMLCom.getADiadm().getIsFormatOf() == null)
                marcXMLCom.getADiadm().setIsFormatOf(sciXMLCom.getDIIIdentifier().getId());

            // add Component
            rootItem.getComs().add(marcXMLCom);
            rootItem.getComs().add(sciXMLCom);
            rootItem.getComs().addAll(rawComList);

            didl.setItem(rootItem);
            return didl;
        } catch (Exception ex) {
            throw new LANLDIDLException("error in create ScienceServer record",
                    ex);
        }

    }

    public void parse(java.io.InputStream stream) throws LANLDIDLException {
        try {
            AdoreDIDL didl = new AdoreDIDL();
            didl.parse(stream);

            did = didl.getDidid();

            contentId = didl.getItem().getADII().getId();

            marcXMLCom = didl.getItem().getComs().get(0);
            sciXMLCom = didl.getItem().getComs().get(1);
            rawComList = didl.getItem().getComs().subList(2,
                    didl.getItem().getComs().size());

        } catch (Exception ex) {
            throw new LANLDIDLException("error in parse AI", ex);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("did: ").append(did).append("\ncontentid: ")
                .append(contentId);
        return sb.toString();

    }

}
