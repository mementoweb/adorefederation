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

import java.io.InputStream;
import java.util.Date;

public interface ResourceWriterInterface {

	public void open(String repoId) throws ResourceException;
	
	public void close(String repoId) throws ResourceException;
	
    /**
     * Write resource and index information
     * @param id
     *          unique identifier of resource (i.e. info uri)
     * @param mimeType
     *          mimetype of resource
     * @param inputStream
     *          inputstream containing resource
     * @param utcDate
     *          utc datestamp of ingestion time
     * @param recordLength
     *          length of resource in bytes
     * @param checksum
     *          base32-encoded sha1 digest of resource 
     * @param repoId
     *          repository identifier to which the resource belongs
     * @param sourceUri
     *          uri from which this resource was obtained/derived
     * @throws ResourceException
     */
    public abstract void write(String id, 
            String mimeType,
            InputStream inputStream, 
            Date utcDate, 
            String recordLength,
            String checksum,
            String repoId,
            String sourceUri) throws ResourceException;
}