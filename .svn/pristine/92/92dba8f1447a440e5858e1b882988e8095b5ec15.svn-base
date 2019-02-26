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

package gov.lanl.didlwriter.profile.adore;

import java.net.URI;

import gov.lanl.didlwriter.LANLDIDLException;
import info.repo.didl.DIDLType;
import info.repo.didl.ResourceType;
import info.repo.didl.impl.content.ByteArray;

/**
 * Simplified DIDL Resource for aDORe usage
 * 
 */
public class AdoreResource {
    private String mimeType;

    private String ref;

    private String content;

    /**
     * Gets the contents of the DIDL Resource element
     * 
     * @return inline resource content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the contents of the DIDL Resource element
     * 
     * @param content
     *            inline resource content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the mimetype of the DIDL Resource
     * 
     * @return the mimeType of the resource
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mimetype of the DIDL Resource
     * 
     * @param mimeType
     *            the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets the URL Reference of the DIDL Resource
     * 
     * @return the ref of the resource
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the URL Reference of the DIDL Resource
     * 
     * @param ref
     *            the ref of the resource
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * Creates a ResourceType from the provided values
     * 
     * @param didl
     *            the DIDL instance the resource will be added to
     * @return a DIDL ResourceType to be added to a Component
     * @throws LANLDIDLException
     */
    public ResourceType create(DIDLType didl) throws LANLDIDLException {
        try {
            ResourceType resource = didl.newResource();
            resource.setMimeType(getMimeType());
            if (getRef() != null) {

                resource.setRef(new URI(getRef()));
            } else {
                resource.setContent(new ByteArray(content));
            }

            return resource;

        } catch (Exception ex) {
            throw new LANLDIDLException("error in create DIDL", ex);
        }
    }

    /**
     * Populates an AdoreResource from a DIDL ResourceType
     * 
     * @param resource
     *            initialized ResourceType
     * @throws LANLDIDLException
     */
    public void parse(ResourceType resource) throws LANLDIDLException {
        try {
            setMimeType(resource.getMimeType());
            if (resource.getRef() != null) {
                setRef(resource.getRef().toString());
            } else {
                content = ((ByteArray) (resource.getContent())).getString();
            }

        } catch (Exception ex) {
            throw new LANLDIDLException("error in create DIDL", ex);
        }

    }
}
