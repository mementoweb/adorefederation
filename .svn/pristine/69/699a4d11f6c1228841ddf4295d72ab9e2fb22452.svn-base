/*
 * DIDLInfo.java
 *
 * Created on October 8, 2005, 5:15 PM
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

import info.repo.didl.DIDLInfoType;
import info.repo.didl.DIDLVisitorType;

/**
 * The <code>DIDLInfo</code> wrapper allows information applicable only to the 
 * DIDL document. A DIDLInfo may contain any data inside. A DIDLInfo 
 * must not contain attributes.
 * <p>
 * DIDLInfo Content object types must have associated serializer and deserializer
 * implementations.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see info.repo.didl.serialize.DIDLSerializerType
 * @see info.repo.didl.serialize.DIDLDeserializerType
 */
public final class DIDLInfo extends DIDLBase implements DIDLInfoType {
    private Object content;
    
    /**
     * Creates a new instance of DIDLInfo
     */
    public DIDLInfo() {}
    
    /**
     * Creates a new instance of DIDLInfo setting the DIDLBase identifier
     * @param id
     */
    public DIDLInfo(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        setId(id);
    }

    /**
     * Gets the Content object for this DIDLInfo instance
     */
    public Object getContent() {
        return content;
    }

    /**
     * Sets the Content object for this DIDLInfo instance
     */
    public void setContent(final Object content) {
        if (content == null) {
            throw new IllegalArgumentException();
        }
        
        this.content = content;
    }
    
    /**
     * Implements Visitor pattern to DIDLInfo object
     */
    public void accept(final DIDLVisitorType visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        
        visitor.visitStart(this);
        visitor.visitEnd(this);
    }
}
