/*
 * MySimpleDidl.java
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

import info.repo.didl.DIDLType;
import info.repo.didl.ItemType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.DIDL;
import info.repo.didl.impl.serialize.DIDLDeserializer;
import info.repo.didl.impl.serialize.DIDLSerializer;
import info.repo.didl.impl.serialize.SimpleContentCondition;
import info.repo.didl.impl.tools.Identifier;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import org.foo.didl.content.MyContent;
import org.foo.didl.serialize.MyContentDeserializer;
import org.foo.didl.serialize.MyContentSerializer;

/**
 * MySimpleDidl is an example of how to create a simple XML DIDL using the
 * DIDAPI.
 * <br/>
 * This example will produce a DIDL similar to the structure outlined below:
 * <br/>
 * <DIDL><br/> 
 *   <Item><br/> 
 *     <Descriptor><Statement>...</Statement></Desciptor><br/>
 *     <Component><br/> 
 *       <Descriptor><Statement>...</Statement></Desciptor><br/>
 *       <Resource mimeType="..." ref="..."/><br/> 
 *       <Resource mimeType="...">...</Resource><br/>
 *     </Component><br/> 
 *   </Item><br/> 
 * </DIDL><br/>
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class MySimpleDidl {
    public static final String DEFAULT_COPYRIGHT = "Copyright (c) 2004-2006, Los Alamos National Laboratory.";

    public static final String DEFAULT_USAGE = "Public granted rights to use, reproduce, and distribute this information";

    private String did;

    private String contentId;

    private String copyright;

    private String usage;

    private MySimpleComponent myComponent;

    /**
     * Construct an empty MySimpleDidl object
     * 
     */
    public MySimpleDidl() {
    }

    /**
     * Sets the component by passing in the following information:
     * 
     * @param id
     *            value of the component id
     * @param mimetype
     *            the value of the mimetype of the resource
     * @param resourceUri
     *            reference to the resolvable resource
     * @param content
     *            content of the resource, applies to resources stored by-value
     */
    public void setComponent(String id, String mimetype, URI resourceUri,
            String content) {
        myComponent = new MySimpleComponent(id, mimetype, copyright, usage,
                resourceUri, content);
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
        DIDLSerializer serializer = new DIDLSerializer();
        serializer.getRegistry().addSerializer(MyContent.class,
                MyContentSerializer.class);
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
    private DIDLType create() throws MyDidlException {
        try {

            // Create a new DIDL
            DIDLType didl = (DIDLType) new DIDL();

            // Set the DIDL Document Attribute as a URI
            didl.setDIDLDocumentId(new URI(did));

            // Create a new Item and set the unique identifier
            ItemType item = didl.newItem();
            item.setId(Identifier.createXMLIdentifier());

            // Create a new MyContent object for the first item
            MyContent mc = new MyContent();
            mc.setId(contentId);
            mc.setCopyright(copyright);
            mc.setUsage(usage);

            // Create a Statement and set MyContent
            StatementType stmt = didl.newStatement();
            stmt.setMimeType("application/xml; charset=utf-8");
            stmt.setContent(mc);
            item.addDescriptor(didl.newDescriptor()).addStatement(stmt);

            // Create a metadata item
            ItemType rootItem = didl.addItem(item);

            // Add the metadata-by-value components
            rootItem.addComponent(myComponent.create(didl));

            return didl;
        } catch (Exception ex) {
            throw new MyDidlException("error in create MySimpleDidl record", ex);
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
            // Initialize a DIDLDeserializer to which we can register new
            // content handlers
            DIDLDeserializer deserializer = new DIDLDeserializer();

            // Register MyContent Deserializer & Content Strategy
            deserializer.getRegistry().addDeserializer(MyContent.class,
                    MyContentDeserializer.class);
            deserializer.getStrategy().addContentStrategy(
                    new SimpleContentCondition(null, null, MyContent.NAMESPACE,
                            MyContent.class));

            // Pass the InputStream to the deserializer to obtain the parsed
            // DIDL
            DIDLType didl = (DIDLType) deserializer.read(stream);

            // Obtain DIDLDocumentID from the parsed DIDL
            did = didl.getDIDLDocumentId().toString();

            // Obtain MyContent object from parsed didl and set associated
            // values
            MyContent mc = (MyContent) (didl.getItems().get(0).getDescriptors()
                    .get(0).getStatements().get(0).getContent());
            contentId = mc.getId();
            if (mc.getCopyright() != null)
                copyright = mc.getCopyright();
            if (mc.getUsage() != null)
                usage = mc.getUsage();

            // Obtain Component and parse object to create a MySimpleComponent
            // object
            myComponent = MySimpleComponent.parse(didl.getItems().get(0)
                    .getComponents().get(0));

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MyDidlException("error in parse MySimpleDidl", ex);
        }
    }

    /**
     * Returns documentId and contentId as a String. For debugging use. 
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("did: ")
        .append(did)
        .append("\ncontentid: ")
        .append(contentId);
        return sb.toString();
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

    /** Gets the DIDL Component Type */
    public MySimpleComponent getMyComponent() {
        return myComponent;
    }

    /** Gets the copyright notice for use by content types */
    public String getCopyright() {
        return copyright;
    }

    /** Sets the copyright notice for use by content types */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /** Sets the usage notice for use by content types */
    public String getUsage() {
        return usage;
    }

    /** Gets the usage notice for use by content types */
    public void setUsage(String usage) {
        this.usage = usage;
    }
}
