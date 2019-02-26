/*
 * DIDLFactoryType.java
 *
 * Created on October 8, 2005, 11:20 AM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */ 

package info.repo.didl;

/**
 * Factory for creating a new DIDL 
 *
 *
 * 
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */

 //TODO the DIDLFactoryType is designed with similar intention of XMLReaderFactory
 //of APIS, so that we can hide implementation details of this API. However,
 //the issues related to DIDLFactory method is not fully resolved in version 1. e.g.
 //implementation must be directly called in some use cases. This issue is hopefully
 //solved in next release of this API.
         
public interface DIDLFactoryType {
    
    /**
     * create an empty DIDL
     * @return an empty DIDL object
     */
    public DIDLType newDIDL();
}