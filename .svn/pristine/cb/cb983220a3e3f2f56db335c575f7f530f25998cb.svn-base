/*
 * DefaultRegistry.java
 *
 * Created on October 13, 2005, 8:45 AM
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

import info.repo.didl.DIDLException;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLRegistryType;
import info.repo.didl.serialize.DIDLSerializerType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public abstract class AbstractRegistry implements DIDLRegistryType {
    private final Map<Class, Class> serializerMap;
    private final Map<Class, Class> deserializerMap;
    
    /** 
     * Creates a new instance of DefaultRegistry 
     */
    public AbstractRegistry() {
        serializerMap = new HashMap<Class, Class>();
        deserializerMap = new HashMap<Class, Class>();
    }
    
    /**
     * Register is new serialization implementation
     */
    public void addSerializer(final Class type, final Class serializer) {
        if (type == null || serializer == null) {
            throw new IllegalArgumentException();
        }
        
        serializerMap.put(type, serializer);
    }
    
    /**
     * Get registered serialization implementation for type
     */
    public DIDLSerializerType getSerializer(final Class type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        
        if (serializerMap.get(type) == null) {
            return null;
        }
        
        Object o = null;
        
        try {
            Class serializer = serializerMap.get(type);
            Constructor c = serializer.getConstructor();
            o = c.newInstance();
        }
        catch (NoSuchMethodException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        catch (InstantiationException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        catch (InvocationTargetException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        
        return (DIDLSerializerType) o;
    }
    
    /**
     * Register is new de-serialization implementation
     */
    public void addDeserializer(final Class type, final Class deserializer) {
        if (type == null  || deserializer == null) {
            throw new IllegalArgumentException();
        }
        deserializerMap.put(type, deserializer);
    }
    
    /**
     * Get registered de-serialization implementation for type
     */
    public DIDLDeserializerType getDeserializer(final Class type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        
        if (deserializerMap.get(type) == null) {
            return null;
        }
        
        Object o = null;
        
        try {
            Class deserializer = deserializerMap.get(type);
            Constructor c = deserializer.getConstructor();
            o = c.newInstance();
        }
        catch (NoSuchMethodException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        catch (InstantiationException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        catch (InvocationTargetException e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e.getMessage());
        }
        
        return (DIDLDeserializerType) o;
    }
}