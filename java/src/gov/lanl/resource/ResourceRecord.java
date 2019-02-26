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

package gov.lanl.resource;

import java.io.Serializable;

public class ResourceRecord implements Comparable, Serializable {
    private String date = null;
    private String identifier = null;
    private String mimetype = null;
    private String checksum = null;
    private String length = null;
    private String resourceUri = null;
    private String repoId = null;
    private String sourceUri = null;
    
    /**
     * Gets the base32-encoded SHA1 checksum of the resource
     * @return Returns the checksum.
     */
    public String getChecksum() {
        return checksum;
    }
    /**
     * Sets the base32-encoded SHA1 checksum of the resource
     * @param checksum The checksum to set.
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
    /**
     * Gets the UTC-formatted datastamp of when resource was added
     * @return Returns the date.
     */
    public String getDate() {
        return date;
    }
    /**
     * Sets the UTC-formatted datastamp of when resource was added
     * @param date The date to set.
     */
    public void setDate(String date) {
        this.date = date;     
    }
    /**
     * Gets the length of the resource
     * @return Returns the length.
     */
    public String getLength() {
        return length;
    }
    /**
     * Sets the length of the resource
     * @param length The length to set.
     */
    public void setLength(String length) {
        this.length = length;
    }
    /**
     * Gets the mimetype of the resource
     * @return Returns the mimetype.
     */
    public String getMimetype() {
        return mimetype;
    }
    /**
     * Sets the mimetype of the resource
     * @param mimetype The mimetype to set.
     */
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
    /**
     * Gets the unique identifier of the resource (i.e. an info uri)
     * @return Returns the identifier of the resource.
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * Sets the unique identifier of the resource (i.e. an info uri)
     * @param identifier The identifier of the resource 
     * (e.g. info:lanl-repo/ds/f4d339b1-fa2a-4a74-b342-e68ef0e2f833)
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * Gets the uri for the resource
     * @return Returns the resourceUri.
     */
    public String getResourceUri() {
        return resourceUri;
    }
    /**
     * Sets the uri for the resource
     * @param resourceUri The resourceUri to set.
     */
    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
    /**
     * Gets the repository identifier to which the resource belongs
     * @return Returns the identifier of the parent repository
     */
    public String getRepositoryId() {
        return repoId;
    }
    /**
     * Sets the repository identifier to which the resource belongs
     * @param repoId The repoId to set.
     */
    public void setRepositoryId(String repoId) {
        this.repoId = repoId;
    }
    
    public int compareTo(Object arg0) {
        final int EQUAL = 0;
        final int DIFFERENT = -1;
        ResourceRecord r2 = (ResourceRecord) arg0;
        
        if (this.getIdentifier().equals(r2.getIdentifier())) 
               return EQUAL;
        else 
               return DIFFERENT;
    }
	public String getSourceUri() {
		return sourceUri;
	}
	public void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}
}
