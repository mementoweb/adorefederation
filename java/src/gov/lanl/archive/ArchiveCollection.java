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

package gov.lanl.archive;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents an aDORe Repository which has been ingested by a tier-1 
 * implementation.  This object is used to register the new repo in the
 * tier-2 service registry and identifier locator.
 * @author rchute
 *
 */

public class ArchiveCollection {
    public static final String TYPE_ARC = "arc";
    public static final String TYPE_TAPE = "xmltape";
    
    private String collectionType;
    private String collectionPrefix;
    private String collectionFullname;
    private String collectionUri;
    private String collectionRawId;
    private Date collectionCreated;
    private int collectionExtent;
    private ArrayList<String> collectionAssociations = new ArrayList<String>();

    public ArchiveCollection(){}
    
    /**
     * Get the collection profile prefix 
     * @return
     *         Collection Profile Prefix
     */
    public String getCollectionPrefix() {
        return collectionPrefix;
    }
    
    /**
     * Sets the collection profile prefix (e.g. biosis)
     * @param collectionPrefix
     *          Collection Profile Prefix			
     */
    public void setCollectionPrefix(String collectionPrefix) {
        this.collectionPrefix = collectionPrefix;
    }
    
    /**
     * Get the Repository Type (e.g. "arc" or "xmltape")
     * @return
     * 			Repository Type
     */
    public String getCollectionType() {
        return collectionType;
    }
    
    /**
     * Set the Repository Type (e.g. "arc" or "xmltape")
     * @param collectionType
     * 			Repository Type
     */
    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
    
    /**
     * Gets the URI of the repository (e.g. info:lanl-repo/xmltape/abc)
     * @return
     * 			uri of repository
     */
    public String getCollectionUri() {
        return collectionUri;
    }
    
    /**
     * Sets the URI of the the repository (e.g. info:lanl-repo/xmltape/abc)
     * @param uri
     * 			uri of repository
     */
    public void setCollectionUri(String uri) {
        this.collectionUri = uri;
    }
    
    /**
     * Gets the repository id without URI prefix
     * @return
     * 			unique repository id, minus uri prefix
     */
    public String getCollectionRawId() {
        return collectionRawId;
    }
    
    /**
     * Sets the repository id without URI prefix
     * @param repoId
     * 			unique repository id, minus uri prefix
     */
    public void setCollectionRawId(String repoId) {
        this.collectionRawId = repoId;
    }
    
    /**
     * Gets the number of records in repository
     * @return
     * 			number of records in repository
     */
    public int getCollectionExtent() {
        return collectionExtent;
    }
    
    /**
     * Sets the number of records in repository
     * @param collectionExtent
     * 			number of records in repository
     */
    public void setCollectionExtent(int collectionExtent) {
        this.collectionExtent = collectionExtent;
    }
    
    /**
     * Get the list of other repositories this collection depend on
     * (i.e. gets the list of arcfiles associated with an xmltape)
     * @return
     * 			list of collection uris this repo depends on
     */
    public ArrayList<String> getCollectionAssociations() {
        return collectionAssociations;
    }
    
    /**
     * Set the list of other repositories this collection depend on
     * (i.e. sets the list of arcfiles associated with an xmltape)
     * @param collectionAssociations
     * 			list of collection uris this repo depends on
     */
    public void setCollectionAssociations(ArrayList<String> collectionAssociations) {
        this.collectionAssociations = collectionAssociations;
    }
    
    /**
     * Add to the list of other repositories this collection depend on
     * (i.e. add an arcfile associated with an xmltape)
     * @param collectionAssociation
     * 			collection uri associated with repo
     */
    public void addCollectionAssociations(String collectionAssociation) {
        this.collectionAssociations.add(collectionAssociation);
    }
    
    /**
     * Get the date/time at which the repository was ingested
     * @return
     * 			date/time the collection was ingested
     */
    public Date getCollectionCreated() {
        return collectionCreated;
    }
    
    /**
     * Set the date/time at which the repository was ingested
     * @param collectionCreated
     * 			date/time the collection was ingested
     */
    public void setCollectionCreated(Date collectionCreated) {
        this.collectionCreated = collectionCreated;
    }
    
    /**
     * Get the URI used to identify the processing profile
     * (e.g. info:sid/library.lanl.gov/biosis)
     * @return
     * 			the uri of the processing profile
     */
    public String getCollectionFullname() {
        return collectionFullname;
    }
    
    /**
     * Set the URI used to identify the processing profile
     * (e.g. info:sid/library.lanl.gov/biosis)
     * @param collectionFullname
     * 			the uri of the processing profile
     */
    public void setCollectionFullname(String collectionFullname) {
        this.collectionFullname = collectionFullname;
    }
}
