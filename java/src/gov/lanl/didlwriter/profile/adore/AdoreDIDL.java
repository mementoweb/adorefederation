/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
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

package gov.lanl.didlwriter.profile.adore;

import gov.lanl.didlwriter.LANLDIDLException;

import org.adore.didl.attribute.DIEXT;
import org.adore.didl.helper.Env;
import org.adore.didl.helper.Helper;
import org.adore.didl.content.DII;

import info.repo.didl.DIDLFactoryType;
import info.repo.didl.DIDLType;
import info.repo.didl.AttributeType;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Generic Adore DIDL Profiling Object
 */
public class AdoreDIDL {

    /** Public SchemaLocation for DIDL Schema */
    public static final String didlSchema = "http://purl.lanl.gov/aDORe/schemas/2006-09/DIDL.xsd";

    /** Public SchemaLocation for DII Schema */
    public static final String diiSchema = "http://purl.lanl.gov/aDORe/schemas/2006-09/DII.xsd";

    private String didid;

    private String didcreated;

    private String didmodified;

    private AdoreItem item;

    private Env env;

    static SimpleDateFormat formatter;

    static {
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(tz);
    }

    /**
     * Creates a new AdoreDIDL instance, initializes Env to register serializers
     */
    public AdoreDIDL() {
        env = new Env();
    }

    /**
     * Gets the DIDL Creation Date
     * 
     * @return the UTC date the record was creation
     */
    public String getDidcreated() {
        return didcreated;
    }

    /**
     * Gets the DIDL Document Identifier
     * 
     * @return the DIDLDocumentId
     */
    public String getDidid() {
        return didid;
    }

    /**
     * Sets the DIDL Document Identifier
     * 
     * @param didid
     *            the DIDLDocumentId
     */
    public void setDidid(String didid) {
        this.didid = didid;
    }

    /**
     * Gets the DIDL Modified Date
     * 
     * @return the UTC date the record was last modified
     */
    public String getDidmodified() {
        return didmodified;
    }

    /**
     * Gets the aDORe DIDL Item Object
     * 
     * @return the item as an AdoreItem instance
     */
    public AdoreItem getItem() {
        return item;
    }

    /**
     * Sets the aDORe DIDL Item Object
     * 
     * @param item
     *            the item as an AdoreItem instance
     */
    public void setItem(AdoreItem item) {
        this.item = item;
    }

    /**
     * Creates a DIDLType instance using the provided identifiers and date
     * stamps
     * 
     * @return an initialized DIDLType instance
     * @throws LANLDIDLException
     */
    public DIDLType create() throws LANLDIDLException {
        try {
            DIDLFactoryType f = env.getDIDLFactory();
            DIDLType didl = (DIDLType) f.newDIDL();

            didl.setDIDLDocumentId(new URI(didid));

            DIEXT diext = Helper.newDIEXT();
            if (didcreated != null) {
                diext.setCreated(formatter.parse(didcreated));
            }
            if (didmodified != null) {
                diext.setLastModified(formatter.parse(didmodified));
            }
            didl.getAttributes().add(diext);

            didl.addItem(item.create(didl));
            return didl;

        } catch (Exception ex) {
            throw new LANLDIDLException("error in create DIDL", ex);
        }
    }

    /**
     * Gets an XML Serialized Representation of current AdoreDIDL
     * 
     * @return current DIDL as XML string
     * @throws LANLDIDLException
     * @throws DIDLSerializationException
     */
    public String getXML() throws LANLDIDLException, DIDLSerializationException {
        DIDLType didl = create();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DIDLSerializerType serializer = env.getDIDLSerializer();
        serializer.setProperty("xsi:schemaLocation", didlSchema);
        DII.DII_SCHEMA_LOCATION = diiSchema;
        serializer.write(stream, didl);
        return stream.toString();
    }

    /**
     * Initializes an AdoreDIDL instance from an DIDLType object
     * 
     * @param didl
     *            populated DIDLType instance
     * @throws LANLDIDLException
     */
    public void parse(DIDLType didl) throws LANLDIDLException {
        setDidid(didl.getDIDLDocumentId().toString());

        for (AttributeType att : didl.getAttributes()) {
            if (DIEXT.class.isInstance(att)) {
                DIEXT diext = (DIEXT) att;
                didcreated = formatter.format(diext.getCreated());
                didmodified = formatter.format(diext.getLastModified());
                break;
            }
        }
        AdoreItem item = new AdoreItem();
        item.parse(didl.getItems().get(0));
        setItem(item);
    }

    /**
     * Initializes an AdoreDIDL instance from a InputStream; stream must be
     * compatible with SAX InputSource
     * 
     * @param stream
     *            SAX InputSource Compatible InputStream
     * @throws LANLDIDLException
     */
    public void parse(java.io.InputStream stream) throws LANLDIDLException {
        try {
            DIDLDeserializerType deserializer = env.getDIDLDeSerializer();
            DIDLType didl = (DIDLType) deserializer.read(stream);
            parse(didl);
        } catch (Exception ex) {
            throw new LANLDIDLException("error in parse DIDL", ex);
        }

    }

}
