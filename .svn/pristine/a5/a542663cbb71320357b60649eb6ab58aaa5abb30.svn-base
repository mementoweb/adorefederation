/*
 * Env.java
 *
 * Created on November 29, 2005, 1:03 AM
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

package org.adore.didl.helper;

import info.repo.didl.DIDLFactoryType;
import info.repo.didl.impl.DIDLFactory;
import info.repo.didl.impl.serialize.SimpleAttributeCondition;
import info.repo.didl.impl.serialize.SimpleContentCondition;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import org.adore.didl.attribute.DIEXT;
import org.adore.didl.content.DII;
import org.adore.didl.content.Diadm;
import org.adore.didl.content.Premis;
import org.adore.didl.serialize.DIEXTSerializer;
import org.adore.didl.serialize.DiadmDeserializer;
import org.adore.didl.serialize.DiadmSerializer;
import org.adore.didl.serialize.PremisDeserializer;
import org.adore.didl.serialize.PremisSerializer;

/**
 * <code>Env</code> is a wrapper for serialization and de-serialization 
 * functionality.  The necessary content type de/serializers are registered
 * and primed for use.
 * 
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class Env {
    
	/** Creates a new Env instance */
    public Env() {
    }
    
    /** 
     * Gets a DIDLSerializer instance initialized with the serializers used within 
     * the adore didl implementations. Initialized content types include DIEXT, Diadm, 
     * DII, and Premis.
     * @return initialized adore DIDLSerializerType instance
     */
    public DIDLSerializerType getDIDLSerializer(){
        info.repo.didl.impl.serialize.DIDLSerializer
                serializer = new info.repo.didl.impl.serialize.DIDLSerializer();
        serializer.getRegistry().addSerializer(DIEXT.class,DIEXTSerializer.class);
        serializer.getRegistry().addSerializer(Diadm.class, DiadmSerializer.class);
        serializer.getRegistry().addSerializer(DII.class, DII.class);
        serializer.getRegistry().addSerializer(Premis.class,PremisSerializer.class);
        return serializer;
    }
    
    /** 
     * Gets a DIDLDeserializer instance initialized with the deserializers and content
     * strategies used within the adore didl implementations. 
     * Initialized content types include DIEXT, Diadm, DII, and Premis.
     * @return initialized adore DIDLDeserializerType instance
     */
    public DIDLDeserializerType getDIDLDeSerializer() throws DIDLSerializationException{
        info.repo.didl.impl.serialize.DIDLDeserializer 
                deserializer = new info.repo.didl.impl.serialize.DIDLDeserializer();
        deserializer.setProperty("copier:class","info.repo.didl.impl.serialize.VerbatimFragmentCopier");
        
        //register diext
        deserializer.getRegistry().addDeserializer(DIEXT.class, DIEXTSerializer.class);
        deserializer.getStrategy().addAttributeStrategy(new SimpleAttributeCondition(DIEXTSerializer.DIEXT_NAMESPACE, DIEXT.class));
        
        //register dii
        deserializer.getRegistry().addDeserializer(DII.class, DII.class);
        
        deserializer.getStrategy().addContentStrategy(new SimpleContentCondition(null,null,DII.DII_NAMESPACE, DII.class));
        
        //register Premis
        deserializer.getRegistry().addDeserializer(Premis.class,PremisDeserializer.class);
        deserializer.getStrategy().addContentStrategy(new SimpleContentCondition(null,null,PremisDeserializer.NAMESPACE,Premis.class));
        
        //register diadm
        deserializer.getRegistry().addDeserializer(Diadm.class,DiadmDeserializer.class);
        deserializer.getStrategy().addContentStrategy(new SimpleContentCondition(null,null,DiadmDeserializer.DIADM_NAMESPACE, Diadm.class));
  
        return deserializer;
    }
    
    /**
     * Gets a DIDLFactory object from which a new DIDL may be easily created
     */
    public DIDLFactoryType getDIDLFactory(){
        return new DIDLFactory();
    }
    
    
    
}
