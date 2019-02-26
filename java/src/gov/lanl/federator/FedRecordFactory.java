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

package gov.lanl.federator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.xml.sax.InputSource;

import ORG.oclc.oai.server.catalog.RecordFactory;
import ORG.oclc.oai.server.crosswalk.CrosswalkItem;

import gov.lanl.federator.schematable.MapTable;
import gov.lanl.federator.schematable.MapItem;
import gov.lanl.util.properties.GenericPropertyManager;

/**
 * FedRecordFactory converts gov.lanl.util.oai.oaiharvesterwrapper.Record to
 * federator
 */
public class FedRecordFactory extends RecordFactory {
    static Logger log = Logger.getLogger(FedRecordFactory.class.getName());

    /**
     * Construct a FedRecordFactory capable of producing the Crosswalk(s)
     * specified in the properties file.
     * 
     * @param properties
     *            Contains information to configure the factory: specifically,
     *            the names of the crosswalk(s) supported
     * @exception IllegalArgumentException
     */
    public FedRecordFactory(Properties properties)
            throws IllegalArgumentException {
        this(createCrosswalks(properties));

    }

    /**
     * Construct a FedRecordFactory from a hash map of metadataPrefix:crosswalk
     * pairs
     * 
     * @param crosswalks
     *            a hash map of metadataPrefix:crosswalk pairs
     * @throws IllegalArgumentException
     */
    public FedRecordFactory(HashMap crosswalks) throws IllegalArgumentException {
        super(crosswalks);
    }

    /**
     * Called method is called by contructor to create crosswalk map table
     * 
     * @param props
     *            properties object containing
     *            FedConstants.ADORE_FEDERATOR_CONFIGDIR
     * @return
     *            a hash map of metadataPrefix:crosswalk pairs
     */
    private static HashMap<String, CrosswalkItem> createCrosswalks(Properties props) {
        try {
            GenericPropertyManager gpl = new GenericPropertyManager();
            gpl.addAll(props);

            // init metadata formats table
            MapTable.parse(new InputSource(gpl
                    .getProperty(FedConstants.ADORE_FEDERATOR_CONFIGDIR)
                    + "/federator.xml"));
            HashMap<String, CrosswalkItem> crosswalkMap = new HashMap<String, CrosswalkItem>();

            Hashtable<String, MapItem> maptable = MapTable.getMapTable();
            log.info("size of maptable:" + maptable.size());
            for (MapItem item : maptable.values()) {
                CrosswalkItem walk = new CrosswalkItem(
                        item.getMetadataPrefix(), item.getSchema(), item
                                .getNS(), FedCrosswalk.class);
                crosswalkMap.put(item.getMetadataPrefix(), walk);
            }
            return crosswalkMap;
        } catch (Exception e) {
            log.error("Error processing crosswalk hash map", e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Utility method to parse the 'local identifier' from the OAI identifier
     * 
     * @param identifier
     *            OAI identifier (e.g. oai:oaicat.oclc.org:ID/12345)
     * @return local identifier (e.g. ID/12345).
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
        gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) nativeItem;
        return record.getHeader().getIdentifier();
    }

    /**
     * Gets the OAI datestamp from the item
     * 
     * @param nativeItem
     *            a native item presumably containing a datestamp somewhere
     * @return a String containing the datestamp for the item
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public String getDatestamp(Object nativeItem)
            throws IllegalArgumentException {
        gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) nativeItem;
        return record.getHeader().getDatestamp();
    }

    /**
     * Gets the OAI setspec from the item
     * 
     * @param nativeItem
     *            a native item presumably containing a setspec somewhere
     * @return a String containing the setspec for the item
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public Iterator getSetSpecs(Object nativeItem)
            throws IllegalArgumentException {
        ArrayList<String> list = new ArrayList<String>();
        gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) nativeItem;
        Iterator setSpecs = record.getHeader().getSetSpecs();
        if (setSpecs != null) {
            while (setSpecs.hasNext()) {
                list.add("<setSpec>" + ((String) setSpecs.next())
                        + ("</setSpec>"));
            }
        }
        return list.iterator();
    }

    /**
     * Gets the OAI about elements from the item
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
    public boolean isDeleted(Object nativeItem) throws IllegalArgumentException {
        return false;
    }

    /**
     * Not Supported
     */
    public String quickCreate(Object nativeItem, String schemaLocation,
            String metadataPrefix) {
        // Don't perform quick creates
        return null;
    }
}
