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

package gov.lanl.repo;

import java.util.Properties;

public class RepoProperties {
    
    public static final String TAG_PUT_RECORD_SCHEMA_URI = "schema.put-record.uri";
    public static final String TAG_PMP_RESPONSE_SCHEMA_URI = "schema.pmp-response.uri";
    public static final String TAG_REPO_IDX_SCHEMA_URI = "schema.put-record.uri";
    public static final String TAG_OAIDB_VALIDATE_DIDL = "adore-repo-oaidb.validate.Didl";
    public static final String TAG_REGISTRY_XMLTAPE = "schema.t-registry.uri";
    public static final String TAG_ARC_REGISTRY_SCHEMA_URI = "schema.arc-record.uri";
    public static final String TAG_TAPE_BASICS_SCHEMA_URI = "schema.ba.uri";
    public static final String TAG_TAPE_ADMIN_SCHEMA_URI = "schema.ta.uri";
    public static final String TAG_DC_SCHEMA_URI = "schema.dc.uri";
    
    public static final String DIEXT_NS = "http://library.lanl.gov/2005-08/aDORe/DIDLextension/";
    public static final String DIDL_NS = "urn:mpeg:mpeg21:2002:02-DIDL-NS";
    public static final String PUT_RECORD_NS = "http://library.lanl.gov/2005-08/aDORe/PMPrequest/";
    public static final String PMP_RESPONSE_NS = "http://library.lanl.gov/2005-08/aDORe/PMPresponse/";
    public static final String OAIPMH_NS= "http://www.openarchives.org/OAI/2.0/";
    public static final String ARC_REG_NS = "http://library.lanl.gov/2005-08/aDORe/ARCfileRegistry/";
    public static final String TAPE_REG_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtapeRegistry/";
    public static final String TAPE_BASCIS_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtapeBasics/";
    public static final String TAPE_ADMIN_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtape/";
    public static final String DC_NS = "http://purl.org/dc/elements/1.1/";
    
    private static String didlValidation = "info:lanl-repo/i/";
    private static String putRecordSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/PMPrequest.xsd";
    private static String pmpResponseSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/PMPresponse.xsd";
    private static String tapeRegistrySchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeRegistry.xsd";
    private static String arcRegistrySchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/ARCfileRegistry.xsd";
    private static String tapeBasicsSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeBasics.xsd";
    private static String tapeAdminSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtape.xsd";
    private static String dcSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/dc.xsd";
    
    private static Properties repoProps = null;
    
    public static void load(Properties props) {
        repoProps = props;
        putRecordSchemaURI = props.getProperty(TAG_PUT_RECORD_SCHEMA_URI, putRecordSchemaURI);
        pmpResponseSchemaURI = props.getProperty(TAG_PMP_RESPONSE_SCHEMA_URI, pmpResponseSchemaURI);
        didlValidation = props.getProperty(TAG_OAIDB_VALIDATE_DIDL, didlValidation);
        tapeRegistrySchemaURI = props.getProperty(TAG_REGISTRY_XMLTAPE, tapeRegistrySchemaURI);
        arcRegistrySchemaURI = props.getProperty(TAG_ARC_REGISTRY_SCHEMA_URI, arcRegistrySchemaURI);
        tapeBasicsSchemaURI = props.getProperty(TAG_TAPE_BASICS_SCHEMA_URI, tapeBasicsSchemaURI);
        tapeAdminSchemaURI = props.getProperty(TAG_TAPE_ADMIN_SCHEMA_URI, tapeAdminSchemaURI);
        dcSchemaURI = props.getProperty(TAG_DC_SCHEMA_URI, dcSchemaURI);
    }

    public static Properties getProperties() {
        return repoProps;
    }
    
    /**
     * @return Returns the didlValidation.
     */
    public static String getDidlValidation() {
        return didlValidation;
    }

    /**
     * @return Returns the putRecordSchemaURI.
     */
    public static String getPutRecordSchemaURI() {
        return putRecordSchemaURI;
    }

    /**
     * @param putRecordSchemaURI The putRecordSchemaURI to set.
     */
    public static void setPutRecordSchemaURI(String putRecordSchemaURI) {
        RepoProperties.putRecordSchemaURI = putRecordSchemaURI;
    }

    /**
     * @param didlValidation The didlValidation to set.
     */
    public static void setDidlValidation(String didlValidation) {
        RepoProperties.didlValidation = didlValidation;
    }

    /**
     * @return Returns the tapeRegistrySchemaURI.
     */
    public static String getTapeRegistrySchemaURI() {
        return tapeRegistrySchemaURI;
    }

    /**
     * @param tapeRegistrySchemaURI The tapeRegistrySchemaURI to set.
     */
    public static void setTapeRegistrySchemaURI(String tapeRegistrySchemaURI) {
        RepoProperties.tapeRegistrySchemaURI = tapeRegistrySchemaURI;
    }

    /**
     * @return Returns the arcRegistrySchemaURI.
     */
    public static String getArcRegistrySchemaURI() {
        return arcRegistrySchemaURI;
    }

    /**
     * @param arcRegistrySchemaURI The arcRegistrySchemaURI to set.
     */
    public static void setArcRegistrySchemaURI(String arcRegistrySchemaURI) {
        RepoProperties.arcRegistrySchemaURI = arcRegistrySchemaURI;
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
        RepoProperties.tapeAdminSchemaURI = tapeAdminSchemaURI;
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
        RepoProperties.tapeBasicsSchemaURI = tapeBasicsSchemaURI;
    }

    /**
     * @return Returns the pmpResponseSchemaURI.
     */
    public static String getPmpResponseSchemaURI() {
        return pmpResponseSchemaURI;
    }

    /**
     * @param pmpResponseSchemaURI The pmpResponseSchemaURI to set.
     */
    public static void setPmpResponseSchemaURI(String pmpResponseSchemaURI) {
        RepoProperties.pmpResponseSchemaURI = pmpResponseSchemaURI;
    }
    
    /**
     * @return Returns the dcSchemaURI.
     */
    public static String getDublinCoreSchemaURI() {
        return dcSchemaURI;
    }

    /**
     * @param dcSchemaURI The dcSchemaURI to set.
     */
    public static void setDublinCoreSchemaURI(String dcSchemaURI) {
        RepoProperties.dcSchemaURI = dcSchemaURI;
    }
}
