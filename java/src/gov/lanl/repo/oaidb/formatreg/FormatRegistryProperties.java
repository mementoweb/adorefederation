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

package gov.lanl.repo.oaidb.formatreg;

import java.util.Properties;

/**
 * Format Registry XML Namespace Properties
 *
 */
public class FormatRegistryProperties {
    private static final String TAG_FORMAT_REGISTRY_SCHEMA_URI = "schema.format.uri";
    
    public static final String TAG_OAIDB_VALIDATE_FORMAT = "adore-repo-oaidb.validate.Format";
    
    private static String formatValidation = "info:lanl-repo/fmt/";

    private static String formatRegistrySchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/FormatRegistry.xsd";

    /** Format Registry Namespace */
    public static final String FORMAT_REG_NS = "http://library.lanl.gov/2005-08/aDORe/FormatRegistry/";
    
    
    /**
     * Gets the format validation string
     * @return Returns the formatValidation.
     */
    public static String getFormatValidation() {
        return formatValidation;
    }
    
    /**
     * @param formatValidation The formatValidation to set.
     */
    public static void setFormatValidation(String formatValidation) {
        FormatRegistryProperties.formatValidation = formatValidation;
    }
    
    /**
     * Gets the Format Registry Schema Location
     * 
     * @return Returns the formatRegistrySchemaURI.
     */
    public static String getFormatRegistrySchemaURI() {
        return formatRegistrySchemaURI;
    }

    /**
     * Loads the PutRecord and FormatRegistry schema information 
     * from properties object
     * 
     * @param props
     *            propeties object containing schema.format.uri
     */
    public static void load(Properties props) {
        formatRegistrySchemaURI = props.getProperty(
                TAG_FORMAT_REGISTRY_SCHEMA_URI, formatRegistrySchemaURI);
        formatValidation = props.getProperty(TAG_OAIDB_VALIDATE_FORMAT, formatValidation);
    }
}
