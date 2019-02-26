/*
 * ResourceType.java
 *
 * Created on October 8, 2005, 10:21 AM
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

import java.net.URI;

/**
 * The <code>ResourceType</code> defines an Asset. A <code>Resource</code> can
 * be either defined by inline or by Ref.  A <code>ResourceType</code> may 
 * include any data format, and it may have any attribute from other namespace.
 * <p>
 *
 * The <code>ResourceType</code> is described by MimeType, Ref, Encoding, 
 * ContentEncoding attributes, and the <code>ResourceType</code> contains the 
 * real thing, which we called "Content".
 *<p>
 * Any <code>Object</code> can be the content of a Resource, however, since the 
 * DIDL serialization/deserialization involves the Content too, a 
 * serializer/deserializer should be defined for the Content for this purpose.
 *<p>
 * The <code>ResourceType</code> provides access mechanism of attributes and 
 * content. Please reference DIDL specification to learn more intricate rules 
 * about how to use these attributes. 
 *
 *@author Xiaoming Liu <liu_x@lanl.gov>
 *@author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 *@author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 *@see info.repo.didl.serialize.DIDLSerializerType
 *@see info.repo.didl.serialize.DIDLDeserializerType
 */
public interface ResourceType extends AttributableBaseType {
    public String getMimeType();
    public void setMimeType(String mimeType);
    
    public URI getRef();
    public void setRef(URI ref);
    
    public String getEncoding();
    public void setEncoding(String encoding);
    
    public String[] getContentEncoding();
    public void setContentEncoding(String[] contentEncodings);
    
    public Object getContent();
    public void setContent(Object data);
}
