/*
 * XMLRegistry.java
 *
 * Created on October 13, 2005, 8:42 AM
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

package info.repo.didl.impl.serialize;

import info.repo.didl.impl.content.ByteArray;


/**
 * <code>XMLRegistry</code> provides a content type registry 
 * for serialization and deserialization.  The <code>ByteArray</code>
 * implementation is registered by default.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class XMLRegistry extends AbstractRegistry {
    
    /** 
     * Creates a new instance of XMLRegistry 
     */
    public XMLRegistry() {
        super();
        
        // ByteArray is a catch all for content
        addSerializer(ByteArray.class, ByteArray.class);
        addDeserializer(ByteArray.class, ByteArray.class);
    }
}
