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

package gov.lanl.resource.filesystem;

import gov.lanl.resource.ResourceException;
import gov.lanl.resource.ResourceIndexInterface;
import gov.lanl.resource.ResourceReaderInterface;
import gov.lanl.resource.ResourceRecord;
import gov.lanl.util.resource.Resource;

import java.io.InputStream;
import java.util.Properties;

public class ResourceReader implements ResourceReaderInterface {
    private String baseDir;
    private ResourceIndexInterface idx;
    
    public ResourceReader(Properties props) throws ResourceException {
        try {
            baseDir = props.getProperty("adore-resource.StoreDirectory");
            String resourceIdxPlugin = props.getProperty("adore-resource.ResourceIdxPlugin");
            idx = (ResourceIndexInterface) Class.forName(resourceIdxPlugin).newInstance();
            idx.setBaseDir(baseDir);
            idx.setProperties(props);
        } catch (InstantiationException e) {
            throw new ResourceException(e);
        } catch (IllegalAccessException e) {
            throw new ResourceException(e);
        } catch (ClassNotFoundException e) {
            throw new ResourceException(e);
        }
    }
    
    /**
     * Get resource as an InputStream
     * @param repoId
     *           repository identifier to which the resource belongs
     * @param recordId
     *           resource identifier
     * @return a Resource object containing resource bytes or InputStream
     * @throws ResourceException
     */
    public Resource getResource(String repoId, String recordId) throws ResourceException {
        return idx.getResource(repoId, recordId);
    }
    
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
    public InputStream listIdentifiers(String repoId, String from, String until) throws ResourceException {
        return idx.listIdentifiers(repoId, from, until);
    }

	public ResourceRecord getMetadata(String repoId, String recordId)
			throws ResourceException {
		return idx.getMetadata(repoId, recordId);
	}
}
