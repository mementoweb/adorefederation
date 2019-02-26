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
import info.repo.didl.DescriptorType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.impl.tools.Identifier;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * <code>DidlComponent</code> provides a simple didl component implementation.
 * The create() and parse() are used to construct and deconstruct a component.
 * The structure defined in the create() method must be in synch with that of
 * the parse() method.
 *
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class DidlComponent {
    private String id;

    private String mimetype;

    private URI resourceURI;

    private String content;

    /**
     * Constructor to create an empty component object
     * 
     */
    public DidlComponent() {
    }

    /**
     * Constructor to create a new component object
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
    public DidlComponent(String id, String mimetype, URI resourceUri,
            String content) {
        this.setId(id);
        this.setMimetype(mimetype);
        this.setResourceURI(resourceUri);
        this.setContent(content);
    }

    /**
     * Creates a new component of the structure defined in this implementation
     * 
     * @param didl
     *            DIDLType to which the component will be added
     * @return a ComponentType of the structure defined in the method
     * @throws TutorialException
     */
    public ComponentType create(DIDLType didl) throws TutorialException {
        ComponentType com = didl.newComponent();
        com.setId(Identifier.createXMLIdentifier());

        // Create Content Type
        DII dii = new DII(DII.IDENTIFIER, id);

        // Add Content Type to Statement
        StatementType stmt = didl.newStatement();
        stmt.setMimeType("application/xml; charset=utf-8");
        stmt.setContent(dii);
        com.addDescriptor(didl.newDescriptor()).addStatement(stmt);

        if (getResourceURI() != null) {
            ResourceType resource = didl.newResource();
            resource.setMimeType(getMimetype());
            resource.setRef(getResourceURI());
            try {
                resource.setContent(new ByteArray(getResourceURI().toURL()));
            } catch (MalformedURLException e) {
                throw new TutorialException(e.getMessage(), e);
            }
            com.addResource(resource);
        }

        if (getContent() != null) {
            ResourceType resource = didl.newResource();
            resource.setMimeType(getMimetype());
            resource.setContent(new ByteArray(getContent()));
            com.addResource(resource);
        }

        return com;
    }

    /**
     * Parses a generic component type to create a DidlComponent object
     * 
     * @param com
     *            a ComponentType obtained from a parsed didl
     * @return constructed DidlComponent object
     * @throws Exception
     */
    public static DidlComponent parse(ComponentType com) throws Exception {
        DidlComponent msc = new DidlComponent();

        for (DescriptorType desc : com.getDescriptors()) {
            Object content = desc.getStatements().get(0).getContent();
            if (DII.class.isInstance(content)) {
                DII dii = (DII) content;
                msc.setId(dii.getValue());
            }
        }

        for (ResourceType r : com.getResources()) {
            msc.setResourceURI(r.getRef());
            msc.setMimetype(r.getMimeType());

            if (msc.getResourceURI() == null) {
                ByteArray ba = (ByteArray) (r.getContent());
                msc.setContent(ba.getString());
            }
        }

        return msc;
    }

    /** Gets the content type identifier */
    public String getId() {
        return id;
    }

    /** Sets the content type identifier */
    public void setId(String id) {
        this.id = id;
    }

    /** Gets the content type resource uri */
    public URI getResourceURI() {
        return resourceURI;
    }

    /** Sets the content type resource uri */
    public void setResourceURI(URI resourceURI) {
        this.resourceURI = resourceURI;
    }

    /** Gets the content type mimetype */
    public String getMimetype() {
        return mimetype;
    }

    /** Sets the content type mimetype */
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    /** Gets the content of the resource */
    public String getContent() {
        return content;
    }

    /** Sets the content of the resource */
    public void setContent(String content) {
        this.content = content;
    }
}
