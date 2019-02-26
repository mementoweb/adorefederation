/*
 * DIDLRegistryType.java
 *
 * Created on January 10, 2006, 9:26 AM
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

package info.repo.didl.serialize;

/**
 * This class is used to register serialization implementations
 * for Content.
 * <p>
 * DIDLInfo, Statement, and Resource all allow any <code>Object</code> embedded
 * in the DIDL, which we called "Content", These contents however have to put 
 * into the general framework of DIDL serialization/deserialization, in this case
 * by implementing a DIDLSerializerType and DIDLDeserializerType 
 *<p> 
 * Notice the "Content" and its serializer/deserializer are decoupled in order to 
 * allow maximal flexibility of implementation. e.g. One type of "Content" may have
 * an XML serializer and another DBMS serializer. Therefore in the API we need
 * to build mapping between a "Content" Type and it serializer/deserializer.
 *<p>
 *The <code>DIDLRegistryType</code> is created to maintain the mapping relationship
 * between Content and its serializer/deserializer.
 *
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @see DIDLSerializerType
 * @see DIDLDeserializerType
 * @see info.repo.didl.DIDLInfoType
 * @see info.repo.didl.ResourceType
 * @see info.repo.didl.StatementType
 */
public interface DIDLRegistryType {
    public void addSerializer(Class type, Class serializer);    
    public DIDLSerializerType getSerializer(Class type) throws DIDLSerializationException;
    
    public void addDeserializer(Class type, Class deserializer);    
    public DIDLDeserializerType getDeserializer(Class type) throws DIDLSerializationException;
}
