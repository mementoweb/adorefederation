/*
 * Copyright (c) 2006  The Regents of the University of California
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
 * 
 */

package gov.lanl.adore.demo.didl;

import gov.lanl.adore.demo.TutorialException;
import gov.lanl.adore.demo.didl.content.DII;
import info.repo.didl.ComponentType;
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
import java.util.ArrayList;

/**
 * DidlObject is an example of how to create a simple XML DIDL using the DIDAPI.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class DidlObject {

    private String did;

    private String contentId;

    private ArrayList<DidlComponent> comList = new ArrayList<DidlComponent>();

    /**
     * Construct an empty DidlObject object
     * 
     */
    public DidlObject() {
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
    public void addComponent(String id, String mimetype, URI resourceUri,
            String content) {
        comList.add(new DidlComponent(id, mimetype, resourceUri, content));
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
        serializer.getRegistry().addSerializer(DII.class, DII.class);
        serializer.write(stream, didl);
        return stream.toString();
    }

    /**
     * Creates a new DIDL of the structure defined in the class header
     * 
     * @return DIDL object ready for serialization
     * @throws TutorialException
     *             Error occurred during DIDL Creation
     * 
     */
    private DIDLType create() throws TutorialException {
        try {

            // Create a new DIDL
            DIDLType didl = (DIDLType) new DIDL();

            // Set the DIDL Document Attribute as a URI
            didl.setDIDLDocumentId(new URI(did));

            // Create a new Item and set the unique identifier
            ItemType item = didl.newItem();
            item.setId(Identifier.createXMLIdentifier());

            // Create a new DII content ref for our DIDL
            DII dii = new DII(DII.IDENTIFIER, contentId);

            // Create a Statement and set the DII
            StatementType stmt = didl.newStatement();
            stmt.setMimeType("application/xml; charset=utf-8");
            stmt.setContent(dii);
            item.addDescriptor(didl.newDescriptor()).addStatement(stmt);

            // Create a metadata item
            ItemType rootItem = didl.addItem(item);

            // Add the metadata-by-value components
            for (DidlComponent com : comList) {
                rootItem.addComponent(com.create(didl));
            }

            return didl;
        } catch (Exception ex) {
            throw new TutorialException("error in create DidlObject record", ex);
        }
    }

    /**
     * Parses the InputStream to set identifiers, content types, and components.
     * Deserialization of custom content types (i.e. DII) requires the
     * deserializer to be added to the registry. The de-serialization registry
     * the instance type of each object to determine the appropriate method for
     * de-serialization.
     * 
     * @param stream
     *            InputStream of the DIDL to be parsed
     * @throws MyDidlException
     *             Error occured attempting to parse InputStream
     */
    public void parse(java.io.InputStream stream) throws TutorialException {
        try {
            // Initialize a DIDLDeserializer to which we can register new
            // content handlers
            DIDLDeserializer deserializer = new DIDLDeserializer();

            // Register MyContent Deserializer & Content Strategy
            deserializer.getRegistry().addDeserializer(DII.class, DII.class);
            deserializer.getStrategy().addContentStrategy(
                    new SimpleContentCondition(null, null, DII.DII_NAMESPACE,
                            DII.class));

            // Pass InputStream to the deserializer to get parsed DIDL
            DIDLType didl = (DIDLType) deserializer.read(stream);

            // Obtain DIDLDocumentID from the parsed DIDL
            did = didl.getDIDLDocumentId().toString();

            // Get content object from parsed didl and set associated values
            DII dii = (DII) (didl.getItems().get(0).getDescriptors().get(0)
                    .getStatements().get(0).getContent());
            contentId = dii.getValue();

            // Parse compoents to create a DidlComponent object
            for (ComponentType com : didl.getItems().get(0).getComponents()) {
                comList.add(DidlComponent.parse(com));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TutorialException("error in parse DidlObject", ex);
        }
    }

    /**
     * Gets the DIDL Document ID for the current document.
     */
    public String getDIDLDocumentId() {
        return did;
    }

    /**
     * Gets the list of Components contained in initialized DIDL.
     */
    public ArrayList<DidlComponent> getComList() {
        return comList;
    }

    /**
     * Gets the ContentID for the current document. This information is
     * contained in the DII Descriptor (i.e. DOI).
     */
    public String getContentId() {
        return contentId;
    }
}
