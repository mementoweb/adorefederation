/**
 *Copyright (c) 2000-2002 OCLC Online Computer Library Center,
 *Inc. and other contributors. All rights reserved.  The contents of this file, as updated
 *from time to time by the OCLC Office of Research, are subject to OCLC Research
 *Public License Version 2.0 (the "License"); you may not use this file except in
 *compliance with the License. You may obtain a current copy of the License at
 *http://purl.oclc.org/oclc/research/ORPL/.  Software distributed under the License is
 *distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express
 *or implied. See the License for the specific language governing rights and limitations
 *under the License.  This software consists of voluntary contributions made by many
 *individuals on behalf of OCLC Research. For more information on OCLC Research,
 *please see http://www.oclc.org/oclc/research/.
 *
 *The Original Code is JDBCRecordFactory.java.
 *The Initial Developer of the Original Code is Jeff Young.
 *Portions created by Luda Balakireva are
 *Copyright (C) __LANL___ _______________________. All Rights Reserved.
 *Contributor(s):______________________________________.
 */

package gov.lanl.repo.oaidb;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import ORG.oclc.oai.server.catalog.RecordFactory;

/**
 * JDBCRecordFactory converts JDBC "items" to "record" Strings.
 */

public class ResolverRecordFactory extends RecordFactory {

    private String repositoryIdentifier = null;

    protected String identifierLabel = null;

    protected String datestampLabel = null;

    public ResolverRecordFactory(Properties properties)
            throws IllegalArgumentException {
        super(properties);

        identifierLabel = properties
                .getProperty("RecordFactory.identifierLabel");
        if (identifierLabel == null) {
            throw new IllegalArgumentException(
                    "RecordFactory.identifierLabel is missing from the properties file");
        }
        datestampLabel = properties.getProperty("RecordFactory.datestampLabel");
        if (datestampLabel == null) {
            throw new IllegalArgumentException(
                    "RecordFactory.datestampLabel is missing from the properties file");
        }
    }

    /**
     * Extract the identifier from the native item
     * 
     * @param nativeItem
     *            native Item object
     * @return identifier
     */

    public String getOAIIdentifier(Object nativeItem)
            throws IllegalArgumentException {
        try {
            HashMap table = (HashMap) nativeItem;

            return (String) table.get(identifierLabel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String getLocalIdentifier(Object nativeItem)
            throws IllegalArgumentException {
        try {
            HashMap table = (HashMap) nativeItem;

            return (String) table.get(identifierLabel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String fromOAIIdentifier(String oaiIdentifier) {
        return oaiIdentifier;
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

        try {
            HashMap table = (HashMap) nativeItem;
            //System.out.println(((Timestamp)table.get(datestampLabel)));
            return ((Timestamp) table.get(datestampLabel)).toString()
                    .substring(0, 19).replace(' ', 'T').concat("Z");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * get the setspec from the item
     * 
     * @param nativeItem
     *            a native item presumably containing a setspec somewhere
     * @return a String containing the setspec for the item. Null if setSpecs
     *         aren't derived from the nativeItem.
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public Iterator getSetSpecs(Object nativeItem)
            throws IllegalArgumentException {
        return null;
    }

    /**
     * Get the about elements from the item
     * 
     * @param nativeItem
     *            a native item presumably containing about information
     *            somewhere
     * @return a Iterator of Strings containing &lt;about&gt;s for the item.
     *         Null if abouts aren't derived from the nativeItem
     * @exception IllegalArgumentException
     *                Something is wrong with the argument.
     */
    public Iterator getAbouts(Object nativeItem)
            throws IllegalArgumentException {
        return null;
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
