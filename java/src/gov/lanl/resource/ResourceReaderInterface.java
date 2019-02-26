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

public interface ResourceReaderInterface {

    /**
     * Get resource as an InputStream
     * @param repoId
     *           repository identifier to which the resource belongs
     * @param recordId
     *           resource identifier
     * @return an Resource object containing resource bytes/inputstream and content type
     * @throws ResourceException
     */
    public abstract Resource getResource(String repoId, String recordId)
            throws ResourceException;

    public abstract ResourceRecord getMetadata(String repoId, String recordId)
             throws ResourceException;
    
    /**
     * Get a text/plain list of identifiers
     * @param repoId
     *           repository identifier to return resource identifiers for
     * @param from
     *           utc date for which all returned ids will be greater than of equal
     * @param until
     *           utc date for which all returned ids will be less than of equal
     * @return a text/plain list of identifiers
     * @throws ResourceException
     */
    public abstract InputStream listIdentifiers(String repoId, String from,
            String until) throws ResourceException;

}