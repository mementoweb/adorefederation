/*
 * PremisDeserializer.java
 *
 * Created on December 1, 2005, 1:22 AM
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

package org.adore.didl.serialize;

import gov.loc.standards.premis.ObjectDocument;
import gov.loc.standards.premis.ObjectDocument.Object;
import info.repo.didl.serialize.DIDLSerializationException;

import org.adore.didl.content.Premis;

/**
 * <code>PremisDeserializer</code>  defines the XML deserializer for
 * all Premis content type references.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class PremisDeserializer implements info.repo.didl.serialize.DIDLDeserializerType{
	/** Premis XML Schema Location */
	public final static String SCHEMA = "http://www.loc.gov/standards/premis/Object-v1-0.xsd";
	/** Premis XML Namespace */
    public final static String NAMESPACE = "http://www.loc.gov/standards/premis";
    
    private static final int BUFFER_SIZE = 1024;
    
    /** Creates a new PremisDeserializer instance */
    public PremisDeserializer() {
    }
    
    /**
     * Reads a Premis XML Fragment from specified InputStream 
     * and returns a populated Premis instance. 
     */
    public Premis read(java.io.InputStream stream) throws DIDLSerializationException {
        
        Premis pre = new Premis();
        try {
            ObjectDocument doc=ObjectDocument.Factory.parse(stream);
            Object obj= doc.getObject();
            
            if(obj.getObjectIdentifierList().size() != 0) {
                pre.setObjectIdentifier(obj.getObjectIdentifierList().get(0).getObjectIdentifierType(),
                        obj.getObjectIdentifierList().get(0).getObjectIdentifierValue());
            }
            
            if(obj.getObjectCharacteristicsList().size() != 0) {
                pre.setSize(obj.getObjectCharacteristicsList().get(0).getSize());
                pre.setCompositionLevel(obj.getObjectCharacteristicsList().get(0).getCompositionLevel().intValue());
            }
            
            if(obj.getObjectCategory() != null) {
                pre.setObjectCategory(obj.getObjectCategory());
                pre.setStorageMedium(obj.getStorageList().get(0).getStorageMedium());
            }
            
            if(obj.getObjectCharacteristicsList().size() != 0) {
                if(obj.getObjectCharacteristicsList().get(0).getFormat() != null) {
                    if(obj.getObjectCharacteristicsList().get(0).getFormat().getFormatRegistryList().size() != 0) {
                        pre.setFormat(
                                obj.getObjectCharacteristicsList().get(0).getFormat().getFormatRegistryList().get(0).getFormatRegistryName(),
                                obj.getObjectCharacteristicsList().get(0).getFormat().getFormatRegistryList().get(0).getFormatRegistryKey());
                    }
                }
            }
            if(obj.getCreatingApplicationList().size() != 0) {
                pre.setCreatingApplication(obj.getCreatingApplicationList().get(0).getDateCreatedByApplication());
            }
            
        } catch (Exception e) {
            throw new DIDLSerializationException(e);
        }
        return pre;
    }
    
    
       
    /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException {
        throw new DIDLSerializationException("no property is supported");
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
         throw new DIDLSerializationException("no property is supported");
    }
}
