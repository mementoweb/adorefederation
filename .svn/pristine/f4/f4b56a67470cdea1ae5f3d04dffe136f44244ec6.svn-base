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

package gov.lanl.archive.trans;

import java.util.Properties;

public class TransProperties {

    private static final String TAG_TAPE_BASICS_SCHEMA_URI = "schema.ba.uri";
    private static final String TAG_TAPE_ADMIN_SCHEMA_URI = "schema.ta.uri";
    private static final String TAG_LOCAL_DATASTREAM_PREFIX = "local.datastream.prefix";
    private static final String TAG_LOCAL_OPENURL_REF_ID = "local.openurl-referrer.id";
    
    public static final String TAG_DATASTREAM_PREFIX = "arcidprefix";
    public static final String OAIPMH_NS= "http://www.openarchives.org/OAI/2.0/";
    public static final String TAPE_NS= "http://library.lanl.gov/2005-08/aDORe/XMLtape/";
    public static final String TAPE_BASICS_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtapeBasics/";
    public static final String DIDL_NS = "urn:mpeg:mpeg21:2002:02-DIDL-NS";
    public static final String DII_NS = "urn:mpeg:mpeg21:2002:01-DII-NS";
    
    private static String tapeBasicsSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeBasics.xsd";
    private static String tapeAdminSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtape.xsd";
    private static String localDataStreamPrefix = "info:lanl-repo/ds/";
    private static String localOpenUrlReferrerID = "info:sid/library.lanl.gov";
    
    public static void load(Properties props) {
        tapeBasicsSchemaURI = props.getProperty(TAG_TAPE_BASICS_SCHEMA_URI, tapeBasicsSchemaURI);
        tapeAdminSchemaURI = props.getProperty(TAG_TAPE_ADMIN_SCHEMA_URI, tapeAdminSchemaURI);
        localDataStreamPrefix = props.getProperty(TAG_LOCAL_DATASTREAM_PREFIX, localDataStreamPrefix);
        localOpenUrlReferrerID = props.getProperty(TAG_LOCAL_OPENURL_REF_ID, localOpenUrlReferrerID);
    }
    
    /**
     * @return Returns the tapeBasicsSchemaURI.
     */
    public static String getTapeBasicsSchemaURI() {
        return tapeBasicsSchemaURI;
    }

    /**
     * @param tapeBasicsSchemaURI The tapeBasicsSchemaURI to set.
     */
    public static void setTapeBasicsSchemaURI(String tapeBasicsSchemaURI) {
        TransProperties.tapeBasicsSchemaURI = tapeBasicsSchemaURI;
    }

    /**
     * @return Returns the dataStreamPrefix.
     */
    public static String getLocalDataStreamPrefix() {
        return localDataStreamPrefix;
    }

    /**
     * @param dataStreamPrefix The dataStreamPrefix to set.
     */
    public static void setLocalDataStreamPrefix(String dataStreamPrefix) {
        TransProperties.localDataStreamPrefix = dataStreamPrefix;
    }

    /**
     * @return Returns the localOpenUrlReferrerID.
     */
    public static String getLocalOpenUrlReferrerID() {
        return localOpenUrlReferrerID;
    }

    /**
     * @param localOpenUrlReferrerID The localOpenUrlReferrerID to set.
     */
    public static void setLocalOpenUrlReferrerID(String localOpenUrlReferrerID) {
        TransProperties.localOpenUrlReferrerID = localOpenUrlReferrerID;
    }

    /**
     * @return Returns the tapeAdminSchemaURI.
     */
    public static String getTapeAdminSchemaURI() {
        return tapeAdminSchemaURI;
    }

    /**
     * @param tapeAdminSchemaURI The tapeAdminSchemaURI to set.
     */
    public static void setTapeAdminSchemaURI(String tapeAdminSchemaURI) {
        TransProperties.tapeAdminSchemaURI = tapeAdminSchemaURI;
    }
}
