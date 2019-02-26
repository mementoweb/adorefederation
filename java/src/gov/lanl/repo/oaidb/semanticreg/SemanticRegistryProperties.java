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

package gov.lanl.repo.oaidb.semanticreg;

import java.util.Properties;

/**
 * Semantic Registry XML Namespace Properties
 *
 */
public class SemanticRegistryProperties {
    private static final String TAG_SEMANTIC_REGISTRY_SCHEMA_URI = "schema.semantic.uri";
    
    public static final String TAG_OAIDB_VALIDATE_SEMANTIC = "adore-repo-oaidb.validate.Semantic";
    
    private static String semanticValidation = "info:lanl-repo/sem/";

    private static String semanticRegistrySchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/SemanticRegistry.xsd";

    /** Semantic Registry Namespace */
    public static final String SEMANTIC_REG_NS = "http://library.lanl.gov/2005-08/aDORe/SemanticRegistry/";
    
    
    /**
     * Gets the semantic validation string
     * @return Returns the semanticValidation.
     */
    public static String getSemanticValidation() {
        return semanticValidation;
    }
    
    /**
     * @param semanticValidation The semanticValidation to set.
     */
    public static void setFormatValidation(String semanticValidation) {
        SemanticRegistryProperties.semanticValidation = semanticValidation;
    }
    
    /**
     * Gets the Semantic Registry Schema Location
     * 
     * @return Returns the semanticRegistrySchemaURI.
     */
    public static String getSemanticRegistrySchemaURI() {
        return semanticRegistrySchemaURI;
    }

    /**
     * Loads the SemanticRegistry schema information 
     * from properties object
     * 
     * @param props
     *            propeties object containing schema.semantic.uri
     */
    public static void load(Properties props) {
        semanticRegistrySchemaURI = props.getProperty(
                TAG_SEMANTIC_REGISTRY_SCHEMA_URI, semanticRegistrySchemaURI);
        semanticValidation = props.getProperty(TAG_OAIDB_VALIDATE_SEMANTIC, semanticValidation);
    }
}
