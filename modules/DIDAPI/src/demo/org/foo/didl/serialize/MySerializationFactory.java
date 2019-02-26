/*
 * MySerializationFactory.java
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

package org.foo.didl.serialize;

import info.repo.didl.DIDLFactoryType;
import info.repo.didl.impl.DIDLFactory;
import info.repo.didl.impl.serialize.SimpleContentCondition;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializerType;

import org.foo.didl.content.DII;
import org.foo.didl.content.MyContent;

/**
 * <code>MySerializationFactory</code> is a wrapper for serialization and 
 * de-serialization functionality.  The necessary content type de/serializers 
 * are registered and primed for use.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 *
 */
public class MySerializationFactory {

    /** Creates a new MySerializationFactory instance */
    public MySerializationFactory() {
    }

    /** 
     * Gets a DIDLDeserializer instance initialized with the deserializers and content
     * strategies used within the tutorial didl implementations. 
     * Initialized content types include DII and MyContent.
     * @return initialized adore DIDLSerializerType instance
     */
    public DIDLSerializerType getDIDLSerializer() {
        info.repo.didl.impl.serialize.DIDLSerializer serializer = new info.repo.didl.impl.serialize.DIDLSerializer();
        serializer.getRegistry().addSerializer(MyContent.class,
                MyContentSerializer.class);
        serializer.getRegistry().addSerializer(DII.class, DII.class);
        return serializer;
    }

    /** 
     * Gets a DIDLDeserializer instance initialized with the deserializers and content
     * strategies used within the tutorial didl implementations. 
     * Initialized content types include DII and MyContent.
     * @return initialized adore DIDLDeserializerType instance
     */
    public DIDLDeserializerType getDIDLDeSerializer() {
        info.repo.didl.impl.serialize.DIDLDeserializer deserializer = new info.repo.didl.impl.serialize.DIDLDeserializer();

        // Register MyContent
        deserializer.getRegistry().addDeserializer(MyContent.class,
                MyContentDeserializer.class);
        deserializer.getStrategy().addContentStrategy(
                new SimpleContentCondition(null, null, MyContent.NAMESPACE,
                        MyContent.class));

        // Register DII
        deserializer.getRegistry().addDeserializer(DII.class, DII.class);
        deserializer.getStrategy().addContentStrategy(
                new SimpleContentCondition(null, null, DII.DII_NAMESPACE,
                        DII.class));

        return deserializer;
    }

    /**
     * Gets a DIDLFactory object from which a new DIDL may be easily created
     */
    public DIDLFactoryType getDIDLFactory() {
        return new DIDLFactory();
    }
}
