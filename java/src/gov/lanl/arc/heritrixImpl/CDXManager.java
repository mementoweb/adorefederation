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

package gov.lanl.arc.heritrixImpl;

import gov.lanl.arc.ARCException;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/** 
 * CDXManager.java<br>
 * <br>
 * Represents a set of CDXInstances, may be used as a multi-arcfile in-memory index.<br>
 * 
 * @author rchute
 * 
 */

public class CDXManager {

    Set<CDXInstance> cdxInstanceSet = new TreeSet<CDXInstance>();
    
    /**
     * Adds a CDXInstance to the tree set, may be retrieved using getCDXInstance
     * @param cdxInstance - a vali
     * @throws ARCException 
     */
    public void add(CDXInstance cdxInstance) throws ARCException {
        if (cdxInstance.getCdxInstanceId() != null)
            cdxInstanceSet.add(cdxInstance);
        else
            throw new ARCException("A CDX Instance ID was not defined.");
    }
    
    /**
     * Get a cdx instance using the cdx instance id.  CDX Instance ID's are typically
     * the cdx filename, minus an extension.
     * @param cdxInstanceID - cdx filename minus extension
     * @return the CDXInstance for the specified id
     * @throws ARCException
     */
    public CDXInstance getCDXInstance(String cdxInstanceID) throws ARCException {
        CDXInstance c;
        for (Iterator i = cdxInstanceSet.iterator(); i.hasNext(); ) {
            c = (CDXInstance) i.next();
            if (c.getCdxInstanceId().equals(cdxInstanceID))
                return c;
          }
        throw new ARCException("CDX Instance for specified identifier " + cdxInstanceID + " was not found.");
    }
}
