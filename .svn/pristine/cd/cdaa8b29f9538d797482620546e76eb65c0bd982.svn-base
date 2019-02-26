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

package gov.lanl.harvester;

import java.util.Properties;

public class HarvesterProperties {

    private static final String TAG_OAIPMH_SCHEMA_URI = "schema.oai-pmh.uri";
    private static final String TAG_LOCAL_OAIPMH_SCHEMA_URI = "schema.local-oai-pmh.uri";
    
    public static final String OAIPMH_NS= "http://www.openarchives.org/OAI/2.0/";
    public static final String DIDL_NS = "urn:mpeg:mpeg21:2002:02-DIDL-NS";
    
    private static String oaipmhSchemaURI = "http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";
    private static String localOaipmhSchemaURI = "http://purl.lanl.gov/aDORe/schemas/2006-09/OAIPMHext.xsd";
    
    public static void load(Properties props) {
        oaipmhSchemaURI = props.getProperty(TAG_OAIPMH_SCHEMA_URI, oaipmhSchemaURI);
        localOaipmhSchemaURI = props.getProperty(TAG_LOCAL_OAIPMH_SCHEMA_URI, localOaipmhSchemaURI);
    }

    /**
     * @return Returns the localOaipmhSchemaURI.
     */
    public static String getLocalOaipmhSchemaURI() {
        return localOaipmhSchemaURI;
    }

    /**
     * @param localOaipmhSchemaURI The localOaipmhSchemaURI to set.
     */
    public static void setLocalOaipmhSchemaURI(String localOaipmhSchemaURI) {
        HarvesterProperties.localOaipmhSchemaURI = localOaipmhSchemaURI;
    }

    /**
     * @return Returns the oaipmhSchemaURI.
     */
    public static String getOaipmhSchemaURI() {
        return oaipmhSchemaURI;
    }

    /**
     * @param oaipmhSchemaURI The oaipmhSchemaURI to set.
     */
    public static void setOaipmhSchemaURI(String oaipmhSchemaURI) {
        HarvesterProperties.oaipmhSchemaURI = oaipmhSchemaURI;
    }
}
