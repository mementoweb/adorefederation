/*
 * DIDLFactory.java
 *
 * Created on October 8, 2005, 4:20 PM
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

package info.repo.didl.impl;

import info.repo.didl.DIDLFactoryType;
import info.repo.didl.DIDLType;

/**
 * Factory class for the creation of DIDLType objects
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public final class DIDLFactory implements DIDLFactoryType {
    
    /** 
     * Creates a new instance of DIDLFactory 
     * */
    public DIDLFactory() {
    }
    
    /**
     * Creates a new DIDL object instance
     */
    public DIDLType newDIDL() {
        return new DIDL();
    }
}
