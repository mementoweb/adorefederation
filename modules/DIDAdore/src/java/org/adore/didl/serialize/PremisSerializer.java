/*
 * PremisSerializer.java
 *
 * Created on November 30, 2005, 3:13 AM
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
import gov.loc.standards.premis.ObjectDocument.Object.CreatingApplication;
import gov.loc.standards.premis.ObjectDocument.Object.ObjectCharacteristics;
import gov.loc.standards.premis.ObjectDocument.Object.ObjectIdentifier;
import gov.loc.standards.premis.ObjectDocument.Object.Storage;
import gov.loc.standards.premis.ObjectDocument.Object.ObjectCharacteristics.Format.FormatRegistry;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import java.util.HashMap;
import java.util.Map;

import org.adore.didl.content.Premis;
import org.adore.didl.content.XMLConstants;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <code>PremisSerializer</code>  defines the XML serializer for
 * all Premis content type references.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class PremisSerializer implements DIDLSerializerType{
	/** Premis XML Schema Location */
    public final static String SCHEMA = "http://www.loc.gov/standards/premis/Object-v1-0.xsd";
    /** Premis XML Namespace */
    public final static String NAMESPACE = "http://www.loc.gov/standards/premis";
    /** Premis XML Namespace Prefix */
    public static final String PREFIX = "pre";
    
    /** Creates a new PremisSerializer instance */
    public PremisSerializer() {
    }
    
    /**
     * Writes a Premis object to the specified OutputStream as a Premis XML Fragment
     */
    public void write(java.io.OutputStream stream, java.lang.Object object) throws  DIDLSerializationException{
        try{
            Premis pre=(Premis)(object);
            XmlOptions opts = new XmlOptions();
            Map prefix = new HashMap();
            prefix.put(NAMESPACE, PREFIX);
            opts.setSaveNoXmlDecl();
            opts.setSaveSuggestedPrefixes(prefix);
            
            XmlObject doc = ObjectDocument.Factory.newInstance();
            gov.loc.standards.premis.ObjectDocument.Object obj = ((ObjectDocument)doc).addNewObject();
            ObjectCharacteristics characteristics = obj.addNewObjectCharacteristics();
            
            if(pre.getObjectIdentifierValue()!=null) {
                ObjectIdentifier objectIdentifier = obj.addNewObjectIdentifier();
                objectIdentifier.setObjectIdentifierType(pre.getObjectIdentifierType());
                objectIdentifier.setObjectIdentifierValue(pre.getObjectIdentifierValue());
            }
            
            if(pre.getSize() != -1) {
                characteristics.setSize(pre.getSize());
            }
            
            if(pre.getObjectCategory() != null) {
                obj.setObjectCategory(pre.getObjectCategory());
            }
            
            if (pre.getSize() != -1){
                if ((pre.getFormatName()!=null) && (pre.getFormatKey()!=null)){
                    FormatRegistry formatRegistry = characteristics.addNewFormat().addNewFormatRegistry();
                    formatRegistry.setFormatRegistryName(pre.getFormatName());
                    formatRegistry.setFormatRegistryKey(pre.getFormatKey());
                }
            }
            
            if(pre.getCreatingApplication()!= null) {
                CreatingApplication creating = obj.addNewCreatingApplication();
                creating.setDateCreatedByApplication(pre.getCreatingApplication());
            }
            
            if(pre.getCompositionLevel()!=-1){
                characteristics.setCompositionLevel(new java.math.BigInteger(Integer.toString(pre.getCompositionLevel())));
            }
            
            if (pre.getStorageMedium()!=null){
                Storage storage = obj.addNewStorage();
                storage.setStorageMedium(pre.getStorageMedium());
            }
            addSchemaLocation(doc.getDomNode().getFirstChild());
            doc.save(stream, opts);
            
        } catch (Exception ex){
            throw new DIDLSerializationException(ex);
        }
    }
    
    private void addSchemaLocation(Node node) {
        Element el = (Element) node;
        el.setAttribute("xmlns:" + XMLConstants.XSI_PREFIX, XMLConstants.XSI_NAMESPACE);
        el.setAttributeNS(XMLConstants.XSI_NAMESPACE, XMLConstants.SCHEMA_LOCATION_ATT, NAMESPACE+" "+SCHEMA);
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
