/*
 * MyComplexDidl.java
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

package org.foo.didl;

import info.repo.didl.ComponentType;
import info.repo.didl.DIDLException;
import info.repo.didl.DIDLFactoryType;
import info.repo.didl.DIDLType;
import info.repo.didl.ItemType;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;

import org.foo.didl.MyComplexComponent.COMPONENT_TYPE;
import org.foo.didl.content.DII;
import org.foo.didl.serialize.MySerializationFactory;

/**
 * MyComplexDidl is an example of how to create a more complex XML DIDL using the
 * DIDAPI.
 * 
 * This example will produce a DIDL similar to the structure outlined below:
 * 
 * <DIDL><br/> 
 *   <Item><br/> 
 *     <Descriptor><Statement>...</Statement></Desciptor><br/>
 *     <Item>
 *       <Component><br/> <Descriptor><Statement>...</Statement></Desciptor><br/>
 *         <Resource mimeType="..." ref="..."/><br/> 
 *         <Resource mimeType="...">...</Resource><br/>
 *       </Component><br/>
 *       <Component><br/> <Descriptor><Statement>...</Statement></Desciptor><br/>
 *         <Resource mimeType="..." ref="..."/><br/> 
 *         <Resource mimeType="...">...</Resource><br/>
 *       </Component><br/>
 *     </Item>
 *     <Component><br/> <Descriptor><Statement>...</Statement></Desciptor><br/>
 *       <Resource mimeType="..." ref="..."/><br/> 
 *     </Component><br/> 
 *   </Item><br/> 
 * </DIDL><br/>
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class MyComplexDidl {
    private String did;

    private String contentId;

    private MyComplexComponent modsXMLCom;

    private MyComplexComponent marcXMLCom;

    private ArrayList<MyComplexComponent> comList = new ArrayList<MyComplexComponent>();

    private MySerializationFactory sf;

    /**
     * Construct new MySimpleDidl object and initializes a new SerializationFactory
     * 
     */
    public MyComplexDidl() {
        sf = new MySerializationFactory();
    }

    /**
     * Adds a component to the didl
     * 
     * @param type
     *            denotes the component type
     * @param componentID
     *            value of the component id
     * @param mimetype
     *            the value of the mimetype of the resource
     * @param ref
     *            reference to the resolvable resource
     * @param content
     *            content of the resource, applies to resources stored by-value
     */
    public void addComponent(COMPONENT_TYPE type, String componentID,
            String mimetype, java.net.URI ref, String content)
            throws DIDLException {

        MyComplexComponent com = new MyComplexComponent(type, componentID,
                mimetype, ref, content);

        if (type == COMPONENT_TYPE.MODSXML) {
            modsXMLCom = com;
        } else if (type == COMPONENT_TYPE.MARCXML) {
            marcXMLCom = com;
        } else
            comList.add(com);
    }

    /**
     * Creates an XML instance of the current DIDLType
     * 
     * @return XML Serialized DIDL String
     * @throws Exception
     *             Error occurred during XML serialization
     */
    public String getXML() throws Exception {
        DIDLType didl = create();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DIDLSerializerType serializer = sf.getDIDLSerializer();
        serializer.write(stream, didl);
        return stream.toString();
    }

    /**
     * Creates a new DIDL of the structure defined in the class header
     * 
     * @return DIDL object ready for serialization
     * @throws MyDidlException
     *             Error occurred during DIDL Creation
     * 
     */
    private info.repo.didl.DIDLType create() throws MyDidlException {
        try {

            // Create a new DIDL using a DIDL factory method
            DIDLFactoryType f = sf.getDIDLFactory();
            DIDLType didl = (DIDLType) f.newDIDL();

            // Set the DIDL Document Attribute as a URI
            didl.setDIDLDocumentId(new URI(did));

            ItemType rootItem = didl.addItem(MyComplexDidlHelper.newItem(didl));

            // Set Item DII
            rootItem.addDescriptor(didl.newDescriptor()).addStatement(
                    MyComplexDidlHelper.newXMLStatement(didl, new DII(
                            DII.IDENTIFIER, contentId)));

            // Create a metadata item
            ItemType metaItem = didl.getItems().get(0).addItem(
                    MyComplexDidlHelper.newItem(didl));

            // Add the metadata-by-value components
            metaItem.addComponent(modsXMLCom.create(didl));
            metaItem.addComponent(marcXMLCom.create(didl));

            // Add the metadata-by-reference components;
            for (MyComplexComponent coc : comList) {
                rootItem.addComponent(coc.create(didl));
            }

            return didl;
        } catch (Exception ex) {
            throw new MyDidlException("error in create MyComplexDidl record",
                    ex);
        }
    }

    /**
     * Parses the InputStream to set identifiers, content types, and components.
     * Deserialization of custom content types (i.e. MyContent) requires the
     * deserializer to be added to the registry.  The deserialization registry 
     * the instance type of each object to determine the appropriate method for 
     * deserialization.
     * 
     * @param stream
     *            InputStream of the DIDL to be parsed
     * @throws MyDidlException
     *            Error occured attempting to parse InputStream
     */
    public void parse(java.io.InputStream stream) throws MyDidlException {
        try {
            DIDLDeserializerType deserializer = sf.getDIDLDeSerializer();
            DIDLType didl = (DIDLType) deserializer.read(stream);

            // Set the DIDL Document Attribute as a URI
            did = didl.getDIDLDocumentId().toString();

            // Extract the DIDL contentId from the DII Content record
            contentId = ((DII) (didl.getItems().get(0).getDescriptors().get(0)
                    .getStatements().get(0).getContent())).getValue();

            // Extract the first component, which we know to be of type MODSXML
            modsXMLCom = MyComplexComponent
                    .parse(COMPONENT_TYPE.MODSXML, didl.getItems().get(0)
                            .getItems().get(0).getComponents().get(0));
            
            // Extract the second component, which we know to be of type MARCXML
            marcXMLCom = MyComplexComponent
                    .parse(COMPONENT_TYPE.MARCXML, didl.getItems().get(0)
                            .getItems().get(0).getComponents().get(1));

            // Extract any other components. This generalized approach works well 
            // for components with a single resource (i.e. full text pdf) 
            for (ComponentType com : didl.getItems().get(0).getComponents()) {
                comList.add(MyComplexComponent.parse(COMPONENT_TYPE.RESOURCE, com));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MyDidlException("error in parse MyComplexDidl", ex);
        }
    }

    /** Returns documentId and contentId as a String. For debugging use. */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("did: ")
        .append(did)
        .append("\ncontentid: ")
        .append(contentId);
        return sb.toString();
    }

    /** Gets an array of didl components */
    public ArrayList<MyComplexComponent> getComponentList() {
        return comList;
    }

    /** Sets the array of didl components */
    public void setComponentList(ArrayList<MyComplexComponent> comList) {
        this.comList = comList;
    }

    /** Gets the DIDL Content Identifier */
    public String getContentId() {
        return contentId;
    }

    /** Sets the DIDL Content Identifier */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /** Gets the DIDL Document Identifier */
    public String getDocumentId() {
        return did;
    }

    /** Sets the DIDL Document Identifier */
    public void setDocumentId(String did) {
        this.did = did;
    }

    /** Gets the MarcXML Component */
    public MyComplexComponent getMarcXMLCom() {
        return marcXMLCom;
    }

    /** Sets the MarcXML Component */
    public void setMarcXMLCom(MyComplexComponent marcXMLCom) {
        this.marcXMLCom = marcXMLCom;
    }

    /** Gets the ModsXML Component */
    public MyComplexComponent getModsXMLCom() {
        return modsXMLCom;
    }

    /** Sets the ModsXML Component */
    public void setModsXMLCom(MyComplexComponent modsXMLCom) {
        this.modsXMLCom = modsXMLCom;
    }
}
