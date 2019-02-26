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

package gov.lanl.xmltape;

import java.util.Properties;

public class TapeProperties {
    
    public static final String TAG_DIDL_SCHEMA_URI = "schema.didl.uri";
    public static final String TAG_TAPE_BASICS_SCHEMA_URI = "schema.ba.uri";
    public static final String TAG_TAPE_SCHEMA_URI = "schema.ta.uri";
    public static final String TAG_TAPE_REGISTRY_SCHEMA_URI = "schema.t-registry.uri";
    public static final String TAG_LOCAL_XMLTAPE_PREFIX = "adore-xmltape.PrefixURL";
    
    public static final String DIDL_NS = "urn:mpeg:mpeg21:2002:02-DIDL-NS";
    public static final String DII_NS = "urn:mpeg:mpeg21:2002:01-DII-NS";
    public static final String TAPE_ADMIN_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtape/";
    public static final String TAPE_BASICS_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtapeBasics/";
    public static final String TAPE_REGISTRY_NS = "http://library.lanl.gov/2005-08/aDORe/XMLtapeRegistry/";
   
    private static String tapeAdminSchemaURI="http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtape.xsd";
    private static String tapeBasicsSchemaURI="http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeBasics.xsd";
    private static String didlSchemaURI="http://purl.lanl.gov/aDORe/schemas/2006-09/DIDL.xsd";
    private static String tapeRegistrySchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeRegistry.xsd";
    private static String localXmlTapePrefix = "info:lanl-repo/xmltape/";

    public static void load(Properties props) {
        didlSchemaURI = props.getProperty(TAG_DIDL_SCHEMA_URI, didlSchemaURI);
        tapeBasicsSchemaURI = props.getProperty(TAG_TAPE_BASICS_SCHEMA_URI, tapeBasicsSchemaURI);
        tapeAdminSchemaURI = props.getProperty(TAG_TAPE_SCHEMA_URI, tapeAdminSchemaURI);
        tapeRegistrySchemaURI = props.getProperty(TAG_TAPE_REGISTRY_SCHEMA_URI, tapeRegistrySchemaURI);
        localXmlTapePrefix = props.getProperty(TAG_LOCAL_XMLTAPE_PREFIX, localXmlTapePrefix); 
    }

    /**
     * Gets the TapeAdminSchemaURI property
     * @return Returns the tapeAdminSchemaURI.
     */
    public static String getTapeAdminSchemaURI() {
        return tapeAdminSchemaURI;
    }

    /**
     * Sets the tapeAdminSchemaURI property
     * @param tapeAdminSchemaURI The tapeAdminSchemaURI to set.
     */
    public static void setTapeAdminSchemaURI(String tapeAdminSchemaURI) {
        TapeProperties.tapeAdminSchemaURI = tapeAdminSchemaURI;
    }

    /**
     * Gets the tapeBasicsSchemaURI property
     * @return Returns the tapeBasicsSchemaURI.
     */
    public static String getTapeBasicsSchemaURI() {
        return tapeBasicsSchemaURI;
    }

    /**
     * Sets the tapeBasicsSchemaURI property
     * @param tapeBasicsSchemaURI The tapeBasicsSchemaURI to set.
     */
    public static void setTapeBasicsSchemaURI(String tapeBasicsSchemaURI) {
        TapeProperties.tapeBasicsSchemaURI = tapeBasicsSchemaURI;
    }

    /**
     * Gets the tapeRegistrySchemaURI property
     * @return Returns the tapeRegistrySchemaURI.
     */
    public static String getTapeRegistrySchemaURI() {
        return tapeRegistrySchemaURI;
    }

    /**
     * Sets the tapeRegistrySchemaURI property
     * @param tapeRegistrySchemaURI The tapeRegistrySchemaURI to set.
     */
    public static void setTapeRegistrySchemaURI(String tapeRegistrySchemaURI) {
        TapeProperties.tapeRegistrySchemaURI = tapeRegistrySchemaURI;
    }

    /**
     * Gets the didlSchemaURI property
     * @return Returns the didlSchemaURI.
     */
    public static String getDidlSchemaURI() {
        return didlSchemaURI;
    }

    /**
     * Sets the didlSchemaURI property
     * @param didlSchemaURI The didlSchemaURI to set.
     */
    public static void setDidlSchemaURI(String didlSchemaURI) {
        TapeProperties.didlSchemaURI = didlSchemaURI;
    }

    /**
     * Gets the localXmlTapePrefix property
     * @return Returns the localXmlTapePrefix.
     */
    public static String getLocalXmlTapePrefix() {
        return localXmlTapePrefix;
    }

    /**
     * Sets the localXmlTapePrefix property
     * @param localXmlTapePrefix The localXmlTapePrefix to set.
     */
    public static void setLocalXmlTapePrefix(String localXmlTapePrefix) {
        TapeProperties.localXmlTapePrefix = localXmlTapePrefix;
    }
}
