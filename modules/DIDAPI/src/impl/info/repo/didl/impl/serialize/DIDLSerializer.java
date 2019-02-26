/*
 * MySerializer.java
 *
 * Created on October 13, 2005, 11:18 AM
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

import info.repo.didl.AttributeType;
import info.repo.didl.ComponentType;
import info.repo.didl.DIDLBaseType;
import info.repo.didl.DIDLException;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.DIDLType;
import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ItemType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.Asset;
import info.repo.didl.impl.tools.Base64;
import info.repo.didl.impl.tools.Strings;
import info.repo.didl.serialize.DIDLRegistryType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <code>DIDLSerializer</code> provides a wrapper for the various
 * components necessary for serialization.  Instantiation will
 * initialize the XMLRegistry object.
 * <p>
 * Be sure to register the content type deserializers.
 *
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DIDLSerializer implements DIDLVisitorType, DIDLSerializerType {
    private final static String DIDL_PREFIX = "didl";
    private final static String XSI_PREFIX = "xsi";
    
    private final static String DIDL_ELEMENT = "DIDL";
    private final static String DIDL_DOCUMENT_ID_ATT = "DIDLDocumentId";
    private final static String DIDLINFO_ELEMENT = "DIDLInfo";
    private final static String ITEM_ELEMENT = "Item";
    private final static String COMPONENT_ELEMENT = "Component";
    private final static String DESCRIPTOR_ELEMENT = "Descriptor";
    private final static String STATEMENT_ELEMENT = "Statement";
    private final static String RESOURCE_ELEMENT = "Resource";
    
    private final static String MIME_TYPE_ATT = "mimeType";
    private final static String ENCODING_ATT = "encoding";
    private final static String REF_ATT = "ref";
    private final static String CONTENT_ENCODING_ATT = "contentEncoding";
    private final static String SCHEMA_LOCATION_ATT = "schemaLocation";
    
    private final static String DIDL_NAMESPACE = "urn:mpeg:mpeg21:2002:02-DIDL-NS";
    private final static String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    private final static String DIDL_SCHEMALOCATION="http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-21_schema_files/did/didl.xsd";
    
    private PrintWriter out;
    private DIDLRegistryType registry;
    private SimpleSerializationProperty simpleProperty;
    
    
    /**
     * Creates a new DIDLSerializer instance; uses XMLRegistry as registry
     */
    public DIDLSerializer() {
        this.registry = new XMLRegistry();
        init();
        
    }
    
    /**
     * Creates a new DIDLSerializer instance; register any DIDLRegistryType
     */
    public DIDLSerializer(final DIDLRegistryType registry) {
        this.registry = registry;
        init();
    }
    
    private void init(){
        simpleProperty=new SimpleSerializationProperty();
        simpleProperty.setSchemaLocation(DIDL_SCHEMALOCATION);
    }
    
    
    /**
     * Gets the DIDLRegistryType instance
     * @return the DIDLRegistryType instance, XMLRegistry by default
     */
    public DIDLRegistryType getRegistry() {
        return registry;
    }
    
    
    /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException {
        simpleProperty.setProperty(id,value);
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
        return simpleProperty.getProperty(id);
    }
    
    
    /**
     * Writes DIDL using instantiated visitor type pattern
     */
    public void write(final OutputStream outputStream, final Object object) throws DIDLSerializationException{
        if (outputStream == null || object == null) {
            throw new IllegalArgumentException();
        }
        
        this.out = new PrintWriter(outputStream, true);
        DIDLBaseType base = (DIDLBaseType) object;
        base.accept(this);
    }
    
    /**
     * Visitor Pattern Implementation defining structure of the DIDL
     */
    public void visitStart(final DIDLType didl) {
        // 2006-02-07 rc: Removed header for each DIDL
        // out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.print("<" + DIDL_PREFIX + ":" + DIDL_ELEMENT + " ");
        out.print("xmlns:" + DIDL_PREFIX + "=\"" + DIDL_NAMESPACE + "\" ");
        out.print("xmlns:" + XSI_PREFIX  + "=\"" + XSI_NAMESPACE  + "\" ");
        
        if (didl.getDIDLDocumentId() != null) {
            out.print(DIDL_DOCUMENT_ID_ATT + "=\"" + didl.getDIDLDocumentId().toString() + "\" ");
        }
        
        Map<String,String> nsMap = new HashMap<String,String>();
        nsMap.put(DIDL_NAMESPACE,simpleProperty.getSchemaLocation() );
        
        for (Iterator<AttributeType> it = didl.getAttributes().iterator();
        it.hasNext();) {
            String att = serializeAttribute(it.next(), nsMap);
            out.print(" " + att);
        }
        
        List<String> locs = new ArrayList<String>();
        for (Iterator<String> it = nsMap.keySet().iterator();
        it.hasNext();) {
            String key = it.next();
            locs.add(key);
            locs.add(nsMap.get(key));
        }
        
        out.print(" " + XSI_PREFIX + ":" + SCHEMA_LOCATION_ATT + "=\"" +
                Strings.join(locs.toArray(new String[0]), " ") +
                "\"");
        out.println(">");
    }
    
    /**
     * Visitor Pattern Implementation closing of DIDL element
     */
    public void visitEnd(final DIDLType didl) {
        out.println("</" + DIDL_PREFIX + ":" + DIDL_ELEMENT + ">");
    }
    
    /**
     * Visitor Pattern Implementation defining structure of an Item
     */
    public void visitStart(final ItemType item) {
        out.print("<" + DIDL_PREFIX + ":" + ITEM_ELEMENT);
        if (item.getId() != null) {
            out.print(" id=\"" + item.getId() + "\"");
        }
        
        Map<String,String> nsMap = new HashMap<String,String>();
        
        for (Iterator<AttributeType> it = item.getAttributes().iterator();
        it.hasNext();) {
            String att = serializeAttribute(it.next(), nsMap);
            out.print(" " + att);
        }
        
        List<String> locs = new ArrayList<String>();
        for (Iterator<String> it = nsMap.keySet().iterator();
        it.hasNext();) {
            String key = it.next();
            locs.add(key);
            locs.add(nsMap.get(key));
        }
        
        if (locs.size() > 0) {
            out.print(" " + XSI_PREFIX + ":" + SCHEMA_LOCATION_ATT + "=\"" +
                    Strings.join(locs.toArray(new String[0]), " ") +
                    "\"");
        }
        
        out.println(">");
    }
    
    /**
     * Visitor Pattern Implementation closing of Item element
     */
    public void visitEnd(final ItemType item) {
        out.println("</" + DIDL_PREFIX + ":" + ITEM_ELEMENT + ">");
    }
    
    /**
     * Visitor Pattern Implementation defining structure of a DIDLInfo element
     */
    public void visitStart(final DIDLInfoType didlInfo) {
        out.print("<" + DIDL_PREFIX + ":" + DIDLINFO_ELEMENT + ">");
        out.print(serializeRawContent(didlInfo.getContent()).toString());
    }
    
    /**
     * Visitor Pattern Implementation closing a DIDLInfo element
     */
    public void visitEnd(final DIDLInfoType didlInfo) {
        out.println("</" + DIDL_PREFIX + ":" + DIDLINFO_ELEMENT + ">");
    }
    
    /**
     * Visitor Pattern Implementation defining structure of a Descriptor
     */
    public void visitStart(final DescriptorType descriptor) {
        out.print("<" + DIDL_PREFIX + ":" + DESCRIPTOR_ELEMENT);
        
        if (descriptor.getId() != null) {
            out.print(" id=\"" + descriptor.getId() + "\"");
        }
        
        Map<String,String> nsMap = new HashMap<String,String>();
        
        for (Iterator<AttributeType> it = descriptor.getAttributes().iterator();
        it.hasNext();) {
            String att = serializeAttribute(it.next(), nsMap);
            out.print(" " + att);
        }
        
        List<String> locs = new ArrayList<String>();
        for (Iterator<String> it = nsMap.keySet().iterator();
        it.hasNext();) {
            String key = it.next();
            locs.add(key);
            locs.add(nsMap.get(key));
        }
        
        if (locs.size() > 0) {
            out.print(" " + XSI_PREFIX + ":" + SCHEMA_LOCATION_ATT + "=\"" +
                    Strings.join(locs.toArray(new String[0]), " ") +
                    "\"");
        }
        
        out.println(">");
    }
    
    /**
     * Visitor Pattern Implementation closing a Descriptor element
     */
    public void visitEnd(final DescriptorType descriptor) {
        out.println("</" + DIDL_PREFIX + ":" + DESCRIPTOR_ELEMENT + ">");
    }
    
    /**
     * Visitor Pattern Implementation defining structure of a Component
     */
    public void visitStart(final ComponentType component) {
        out.print("<" + DIDL_PREFIX + ":" + COMPONENT_ELEMENT);
        if (component.getId() != null) {
            out.print(" id=\"" + component.getId() + "\"");
        }
        
        Map<String,String> nsMap = new HashMap<String,String>();
        
        for (Iterator<AttributeType> it = component.getAttributes().iterator();
        it.hasNext();) {
            String att = serializeAttribute(it.next(), nsMap);
            out.print(" " + att);
        }
        
        List<String> locs = new ArrayList<String>();
        for (Iterator<String> it = nsMap.keySet().iterator();
        it.hasNext();) {
            String key = it.next();
            locs.add(key);
            locs.add(nsMap.get(key));
        }
        
        if (locs.size() > 0) {
            out.print(" " + XSI_PREFIX + ":" + SCHEMA_LOCATION_ATT + "=\"" +
                    Strings.join(locs.toArray(new String[0]), " ") +
                    "\"");
        }
        
        out.println(">");
    }
    
    /**
     * Visitor Pattern Implementation closing a Component element
     */
    public void visitEnd(final ComponentType component) {
        out.println("</" + DIDL_PREFIX + ":" + COMPONENT_ELEMENT + ">");
    }
    
    /**
     * Visitor Pattern Implementation defining structure of a Statement
     */
    public void visitStart(final StatementType statement) {
        out.print("<" + DIDL_PREFIX + ":" + STATEMENT_ELEMENT);
        
        if (statement.getMimeType() != null) {
            out.print(" mimeType=\"" + statement.getMimeType() + "\"");
        }
        
        if (statement.getEncoding() != null) {
            out.print(" encoding=\"" + statement.getEncoding() + "\"");
        }
        
        if (statement.getContentEncoding() != null) {
            out.print(" contentEncoding=\"" +
                    Strings.join(statement.getContentEncoding(), " ") +
                    "\"");
        }
        
        if (statement.getRef() != null) {
            out.print(" ref=\"" + statement.getRef().toString() + "\"");
        }
        
        Map<String,String> nsMap = new HashMap<String,String>();
        
        for (Iterator<AttributeType> it = statement.getAttributes().iterator(); it.hasNext();) {
            String att = serializeAttribute(it.next(), nsMap);
            out.print("  " + att);
        }
        
        List<String> locs = new ArrayList<String>();
        for (Iterator<String> it = nsMap.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            locs.add(key);
            locs.add(nsMap.get(key));
        }
        
        if (locs.size() > 0) {
            out.print(" " + XSI_PREFIX + ":" + SCHEMA_LOCATION_ATT + "=\"" +
                    Strings.join(locs.toArray(new String[0]), " ") +
                    "\"");
        }
        
        out.print(">");
        
        out.print(serializeContent((Asset) statement));
    }
    
    /**
     * Visitor Pattern Implementation closing a Statement
     */
    public void visitEnd(final StatementType statement) {
        out.println("</" + DIDL_PREFIX + ":" + STATEMENT_ELEMENT + ">");
    }
    
    /**
     * Visitor Pattern Implementation defining structure of a Resource
     */
    public void visitStart(final ResourceType resource) {
        out.print("<" + DIDL_PREFIX + ":" + RESOURCE_ELEMENT);
        
        if (resource.getMimeType() != null) {
            out.print(" mimeType=\"" + resource.getMimeType() + "\"");
        }
        
        if (resource.getEncoding() != null) {
            out.print(" encoding=\"" + resource.getEncoding() + "\"");
        }
        
        if (resource.getContentEncoding() != null) {
            out.print(" contentEncoding=\"" +
                    Strings.join(resource.getContentEncoding(), " ") +
                    "\"");
        }
        
        if (resource.getRef() != null) {
            out.print(" ref=\"" + resource.getRef().toString() + "\"");
        }
        
        Map<String,String> nsMap = new HashMap<String,String>();
        
        for (Iterator<AttributeType> it = resource.getAttributes().iterator(); it.hasNext();) {
            String att = serializeAttribute(it.next(), nsMap);
            out.print(" " + att);
        }
        
        List<String> locs = new ArrayList<String>();
        for (Iterator<String> it = nsMap.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            locs.add(key);
            locs.add(nsMap.get(key));
        }
        
        if (locs.size() > 0) {
            out.print(" " + XSI_PREFIX + ":" + SCHEMA_LOCATION_ATT + "=\"" +
                    Strings.join(locs.toArray(new String[0]), " ") +
                    "\"");
        }
        
        out.print(">");
        
        out.print(serializeContent((Asset) resource));
    }
    
    /**
     * Visitor Pattern Implementation closing a Resource
     */
    public void visitEnd(final ResourceType resource) {
        out.println("</" + DIDL_PREFIX + ":" + RESOURCE_ELEMENT + ">");
    }
    
    protected String serializeAttribute(final AttributeType attribute, final Map<String,String> nsMap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        
        try {
            AttributeSerializer serializer = (AttributeSerializer)
            getRegistry().getSerializer(attribute.getClass());
            
            if (serializer == null) {
                throw new DIDLSerializationException("No serializer found for " + attribute.getClass().getName());
            } else {
                nsMap.put(serializer.getNamespace(), serializer.getSchema());
                serializer.write(stream, attribute);
            }
        } catch (Exception e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e);
        }
        
        return stream.toString();
    }
    
    private String serializeContent(Asset asset) {
        try {
            if (asset.getRef() != null) {
                return "";
            } else if (asset.getEncoding() != null) {
                
                if (asset.getEncoding().equals("base64")) {
                    return Base64.encodeBytes(serializeRawContent(asset.getContent()).toByteArray());
                } else {
                    throw new DIDLSerializationException("Unknown encoding: " + asset.getEncoding());
                }
            } else {
                return serializeRawContent(asset.getContent()).toString();
            }
        } catch (Exception e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e);
        }
    }
    
    private ByteArrayOutputStream serializeRawContent(Object obj ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        
        try {
            DIDLSerializerType serializer =
                    getRegistry().getSerializer(obj.getClass());
            
            if (serializer == null) {
                throw new DIDLSerializationException("No serializer found for " + obj.getClass().getName());
            } else {
                serializer.write(stream, obj);
            }
        } catch (Exception e) {
            throw new DIDLException(DIDLException.CONFIGURATION_ERROR, e);
        }
        return stream;
    }
}
