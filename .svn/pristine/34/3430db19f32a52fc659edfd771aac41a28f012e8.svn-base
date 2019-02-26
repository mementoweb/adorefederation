/*
 * DIDLDeserializerType.java
 *
 * Created on 12 January 2006, 10:06 AM
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
 * The <code>DIDLDeserializerType</code> defines an interface for building 
 * an object from an <code>InputStream</code>
 *<p>
 * The interface should be implemented for DIDL itself, and also for all Contents
 * class to be used in DIDL deserialization framework.
 * <p>
 * During deserialization, a deserializer will be initialized using the public no-arg 
 * constructor of the class, therefore, a non-arg constructor must be accessible
 * by the deserialization program. After initialization, a Content will be created 
 * by calling <code>Object obj=deserializer.read(stream)</code> method.   
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @see DIDLRegistryType
 */

public interface DIDLDeserializerType {
    /**
     * Creates a Content from InputStream, a user of this interface may
     * need explicit type casting to get the real data type.
     * 
     *@param in the InputStream 
     *@return the content object
     *@throws If any problem in deserialization
     *
     */
    public Object read(java.io.InputStream in) throws DIDLSerializationException;
    
     /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException ;
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException;
}
