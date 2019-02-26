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

package gov.lanl.arc;

import java.util.Properties;

public class ARCProperties {
    
    public static final String DEFAULT_LOCAL_ARC_PREFIX = "info:lanl-repo/arc/";
    public static final String DEFAULT_LOCAL_DS_PREFIX = "info:lanl-repo/ds/";
    public static final String DEFAULT_LOCAL_OPENURL_SID = "info:sid/library.lanl.gov";
    public static final String DEFAULT_PR_SCHEMA_URI = "http://purl.lanl.gov/aDORe/schemas/2006-09/PMPrequest.xsd";
    public static final String DEFAULT_ARCREG_SCHEMA_URI = "http://purl.lanl.gov/aDORe/schemas/2006-09/ARCfileRegistry.xsd";
    public static final String DEFAULT_TB_SCHEMA_URI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeBasics.xsd";
    public static final String DEFAULT_TA_SCHEMA_URI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtape.xsd";
    
    public static final String TAG_PUT_RECORD_SCHEMA_URI = "schema.put-record.uri";
    public static final String TAG_ARC_REGISTRY_SCHEMA_URI = "schema.arc-record.uri";
    public static final String TAG_TAPE_BASICS_SCHEMA_URI = "schema.tb.uri";
    public static final String TAG_TAPE_ADMIN_SCHEMA_URI = "schema.ta.uri";
    public static final String TAG_LOCAL_ARCFILE_PREFIX = "adore-arcfile.PrefixURL";
    public static final String TAG_LOCAL_DATASTREAM_PREFIX = "adore-arcfile.DataStreamPrefix";
    public static final String TAG_LOCAL_OPENURL_REF_ID = "local.openurl-referrer.id";
    // Changed prop to conform to new convention (mod.prop)
    public static final String OLD_TAG_LOCAL_ARCFILE_PREFIX = "local.arcfile.prefix";
    public static final String OLD_TAG_LOCAL_DATASTREAM_PREFIX = "local.datastream.prefix";
    
    public static final String ARC_REG_NS = "http://library.lanl.gov/2005-08/aDORe/ARCfileRegistry/";
    public static final String ARC_REG_PREFIX = "ARCfileRegistry";
    public static final String PUT_RECORD_NS = "http://library.lanl.gov/2005-08/aDORe/PMPrequest/";
    public static final String PUT_RECORD_PREFIX = "";
    public static final String TAPE_BASCIS_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtapeBasics/";
    public static final String TAPE_BASICS_PREFIX = "tb";
    public static final String TAPE_ADMIN_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtape/";
    public static final String TAPE_ADMIN_PREFIX = "ta";
    public static final String OAIPMH_NS= "http://www.openarchives.org/OAI/2.0/";
    
    private static String localArcFilePrefix = "info:lanl-repo/arc/";
    private static String localDataStreamPrefix = "info:lanl-repo/ds/";
    private static String localOpenUrlReferrerID = "info:sid/library.lanl.gov";
    
    private static String putRecordSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/PMPrequest.xsd";
    private static String arcRegistrySchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/ARCfileRegistry.xsd";
    private static String tapeBasicsSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeBasics.xsd";
    private static String tapeAdminSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtape.xsd";

    
    public static void load(Properties props) {
        putRecordSchemaURI = props.getProperty(TAG_PUT_RECORD_SCHEMA_URI, DEFAULT_PR_SCHEMA_URI);
        arcRegistrySchemaURI = props.getProperty(TAG_ARC_REGISTRY_SCHEMA_URI, DEFAULT_ARCREG_SCHEMA_URI);
        tapeBasicsSchemaURI = props.getProperty(TAG_TAPE_BASICS_SCHEMA_URI, DEFAULT_TB_SCHEMA_URI);
        tapeAdminSchemaURI = props.getProperty(TAG_TAPE_ADMIN_SCHEMA_URI, DEFAULT_TA_SCHEMA_URI);
        localOpenUrlReferrerID = props.getProperty(TAG_LOCAL_OPENURL_REF_ID, DEFAULT_LOCAL_OPENURL_SID);
        // Check for old style props, override is new is present
        localArcFilePrefix = props.getProperty(OLD_TAG_LOCAL_ARCFILE_PREFIX, DEFAULT_LOCAL_ARC_PREFIX);
        localDataStreamPrefix = props.getProperty(OLD_TAG_LOCAL_DATASTREAM_PREFIX, DEFAULT_LOCAL_DS_PREFIX);
        if (props.containsKey(TAG_LOCAL_ARCFILE_PREFIX))
            localArcFilePrefix = props.getProperty(TAG_LOCAL_ARCFILE_PREFIX, DEFAULT_LOCAL_ARC_PREFIX);
        if (props.containsKey(TAG_LOCAL_DATASTREAM_PREFIX))
            localDataStreamPrefix = props.getProperty(TAG_LOCAL_DATASTREAM_PREFIX, DEFAULT_LOCAL_DS_PREFIX);
    }
    
    /**
     * Gets the ARC Registry Schema URI
     */
    public static String getArcRegistrySchemaURI() {
        return arcRegistrySchemaURI;
    }
    
    /**
     * Sets the ARC Registry Schema URI
     */
    public static void setArcRegistrySchemaURI(String schemaURI) {
        ARCProperties.arcRegistrySchemaURI = schemaURI;
    }
    
    /**
     *  Gets the the PutRecord Schema URI
     */
    public static String getPutRecordSchemaURI() {
        return putRecordSchemaURI;
    }
    
    /**
     *  Sets the the PutRecord Schema URI
     */
    public static void setPutRecordSchemaURI(String putRecordSchemaURI) {
        ARCProperties.putRecordSchemaURI = putRecordSchemaURI;
    }
    
    /**
     * Gets the Tape Admin Schema URI
     */
    public static String getTapeAdminSchemaURI() {
        return tapeAdminSchemaURI;
    }
    
    /**
     * Sets the Tape Admin Schema URI
     */
    public static void setTapeAdminSchemaURI(String tapeAdminSchemaURI) {
        ARCProperties.tapeAdminSchemaURI = tapeAdminSchemaURI;
    }
    
    /**
     * Gets the Tape Basics Schema URI
     */
    public static String getTapeBasicsSchemaURI() {
        return tapeBasicsSchemaURI;
    }
    
    /**
     * Sets the Tape Basics Schema URI
     */
    public static void setTapeBasicsSchemaURI(String tapeBasicsSchemaURI) {
        ARCProperties.tapeBasicsSchemaURI = tapeBasicsSchemaURI;
    }
    
    /**
     * Gets the institution's local Arc File Prefix
     */
    public static String getLocalArcFilePrefix() {
        return localArcFilePrefix;
    }
    
    /**
     * Sets the institution's local Arc File Prefix
     */
    public static void setLocalArcFilePrefix(String localArcFilePrefix) {
        ARCProperties.localArcFilePrefix = localArcFilePrefix;
    }

    /**
     * Gets the institution's local OpenURL Referrer ID
     */
    public static String getLocalOpenUrlReferrerID() {
        return localOpenUrlReferrerID;
    }
    
    /**
     * Sets the institution's local OpenURL Referrer ID
     */
    public static void setLocalOpenUrlReferrerID(String localOpenUrlReferrerID) {
        ARCProperties.localOpenUrlReferrerID = localOpenUrlReferrerID;
    }

    /**
     * Gets the institution's local datastream prefix
     */
    public static String getLocalDataStreamPrefix() {
        return localDataStreamPrefix;
    }

    /**
     * Sets the institution's local datastream prefix
     */
    public static void setLocalDataStreamPrefix(String localDataStreamPrefix) {
        ARCProperties.localDataStreamPrefix = localDataStreamPrefix;
    }

}
