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

import gov.lanl.util.resource.Resource;

import java.io.InputStream;
import java.util.Properties;

public interface ResourceIndexInterface {
    /**
     * Sets the configuration properties
     * @param props
     *            an initialized property object
     */
    public abstract void setProperties(Properties props);
    /**
     * Sets the resource base directory 
     * @param baseDir
     *            absolute path to location where files are stored
     */
    public abstract void setBaseDir(String baseDir);
    /**
     * Writes a ResourceRecord to according to the implementation model
     * @param record
     *            an initialized ResourceRecord instance
     */
    public abstract void writeRecord(String repoId, ResourceRecord record) throws ResourceException;
    
    /**
     * Returns resource using an InputStream
     * @param repoId
     *            repository id
     * @param recordId
     *            record identifier for the resource to be returned
     * @return obtained resource wrapped in a Resource object
     */
    public abstract Resource getResource(String repoId, String recordId) throws ResourceException;
    
    /**
     * Returns list of identifier contained in the provide repository id
     * @param repoId
     *            repository id
     * @param from
     *            returned ids must be greater than or equal to from
     * @param until
     *            returned ids must be less than or equal to until
     * @return a stream of newline seperated identifiers
     */
    public abstract InputStream listIdentifiers(String repoId, String from, String until) throws ResourceException;
	
    public abstract void open(String repoId) throws ResourceException ;
    public abstract void close(String repoId) throws ResourceException ;
	public abstract ResourceRecord getMetadata(String repoId, String recordId) throws ResourceException;
}
