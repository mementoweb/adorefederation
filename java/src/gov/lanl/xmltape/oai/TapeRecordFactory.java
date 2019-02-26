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

package gov.lanl.xmltape.oai;

import gov.lanl.xmltape.TapeRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import ORG.oclc.oai.server.catalog.RecordFactory;

/**
 * TapeRecordFactory.java 1286 2005-01-21 19:25:16Z liu_x
 * 
 * TapeRecordFactory converts native XML "items" to "record" Strings. This
 * factory assumes the native XML item looks exactly like the <record>element
 * of an OAI GetRecord response, with the possible exception that the <metadata>
 * element contains multiple metadataFormats from which to choose.
 */

public class TapeRecordFactory extends RecordFactory {
    Config tapeConfiguration;

    /**
     * Construct an TapeRecordFactory capable of producing the Crosswalk(s)
     * specified in the properties file.
     * 
     * @param properties
     *            Contains information to configure the factory: specifically,
     *            the names of the crosswalk(s) supported
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public TapeRecordFactory(Properties properties)
            throws IllegalArgumentException {
        super(properties);
        tapeConfiguration = new Config(properties);
    }

    /**
     * Utility method to parse the 'local identifier' from the OAI identifier
     * It's always same in our case
     * 
     * @param identifier
     *            OAI identifier
     * @return local identifier
     */
    public String fromOAIIdentifier(String identifier) {
        return identifier;
    }

    /**
     * Construct an OAI identifier from the native item
     * 
     * @param nativeItem
     *            native Item object
     * @return OAI identifier
     */
    public String getOAIIdentifier(Object nativeItem) {
        if (nativeItem instanceof gov.lanl.xmltape.TapeRecord) {
            TapeRecord record = (TapeRecord) nativeItem;
            return record.getIdentifier();
        }
        return null;
    }

    /**
     * get the datestamp from the item
     * 
     * @param nativeItem
     *            a native item presumably containing a datestamp somewhere
     * @return a String containing the datestamp for the item
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public String getDatestamp(Object nativeItem)
            throws IllegalArgumentException {
        if (nativeItem instanceof gov.lanl.xmltape.TapeRecord) {
            if (tapeConfiguration.useForcedDatestamp())
                return tapeConfiguration.getForcedDatestamp();
            else {
                TapeRecord record = (TapeRecord) nativeItem;
                return record.getDatestamp();
            }
        }
        return null;
    }

    /**
     * get the setspec from the item
     * 
     * @param nativeItem
     *            a native item presumably containing a setspec somewhere
     * @return a String containing the setspec for the item
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public Iterator getSetSpecs(Object nativeItem)
            throws IllegalArgumentException {
        if (nativeItem instanceof gov.lanl.xmltape.TapeRecord) {

            if (tapeConfiguration.useForcedSets())
                return tapeConfiguration.getSetSpecs().iterator();

            TapeRecord record = (TapeRecord) nativeItem;
            if (record.getSetSpecs() != null) {
                return record.getSetSpecs().iterator();
            }
        }
        return null;
    }

    /**
     * Get the about elements from the item
     * 
     * @param nativeItem
     *            a native item presumably containing about information
     *            somewhere
     * @return a Iterator of Strings containing &lt;about&gt;s for the item
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public Iterator getAbouts(Object nativeItem)
            throws IllegalArgumentException {
        ArrayList list = new ArrayList();
        return list.iterator();
    }

    /**
     * Is the record deleted?
     * 
     * @param nativeItem
     *            a native item presumably containing a possible delete
     *            indicator
     * @return true if record is deleted, false if not
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public boolean isDeleted(Object nativeItem) {
        return false;
    }

    /**
     * Allows classes that implement RecordFactory to override the default
     * create() method. This is useful, for example, if the entire
     * &lt;record&gt; is already packaged as the native record. Return null if
     * you want the default handler to create it by calling the methods above
     * individually.
     * 
     * @param nativeItem
     *            the native record
     * @param schemaLocation
     *            the schemaURL desired for the response
     * @param metadataPrefix
     *            metadataPrefix from the request
     * @return a String containing the OAI &lt;record&gt; or null if the default
     *         method should be used.
     */
    public String quickCreate(Object nativeItem, String schemaLocation,
            String metadataPrefix) {
        // Don't perform quick creates
        return null;
    }
}
