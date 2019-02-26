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

package gov.lanl.repo.oaidb.arcreg;

import gov.lanl.repo.RepoProperties;

import java.util.Properties;

import org.apache.log4j.Logger;

public class ARCRegXML implements ARCRegistryConstants {
    
    static Logger log = Logger.getLogger(ARCRegXML.class.getName());
    
    String header = "<PMPrequest xmlns=\"" + RepoProperties.PUT_RECORD_NS + "\" xmlns:ns1=\"" + RepoProperties.PUT_RECORD_NS + "\" xmlns:oai=\"" + RepoProperties.OAIPMH_NS + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"" + RepoProperties.PUT_RECORD_NS + " " + RepoProperties.getPutRecordSchemaURI() + "\">";

    String pr = "xmlns=\"" + RepoProperties.PUT_RECORD_NS + "\"";

    String dcterms = "xmlns=\"http://purl.org/dc/terms/\"";

    String dc = "xmlns=\"http://purl.org/dc/elements/1.1/\"";
    
    String tb = "xmlns:tb=\"" + RepoProperties.TAPE_BASCIS_NS + "\"";

    String arc = "xmlns=\"" + RepoProperties.ARC_REG_NS + "\"";

    String x = "xsi:schemaLocation=\"" + RepoProperties.ARC_REG_NS + " " + RepoProperties.getArcRegistrySchemaURI() + "\"";

    String xsi = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";

    static final String[] mandatory_list = { ELEMENT_ARC_FILE_ID, ELEMENT_ARC_FILE_FILEPATH,
        ELEMENT_ARC_FILE_IDX, ELEMENT_OPENURL_URL };

    public String ProptoXML(Properties prop) throws Exception {
        for (int i = 0; i < mandatory_list.length; i++) {
            if (prop.getProperty(mandatory_list[i]) == null)
                throw new Exception("arcregistry mandatory property "
                        + mandatory_list[i] + " is not defined");
        }
        
        String digest = (prop.getProperty(ELEMENT_ARC_FILE_DIGEST) != null) ? "<" + ELEMENT_ARC_FILE_DIGEST + ">" + prop.getProperty(ELEMENT_ARC_FILE_DIGEST) + "</" + ELEMENT_ARC_FILE_DIGEST + ">" : "";
        
        String body = "<PutRecord " + pr + " ><record " + pr + "><metadata  " + pr + ">"
                + "<" + ELEMENT_ARC_FILE_REGISTRY + " " + arc + " " + tb + " " + xsi + " " + x + " >"
                + "<" + ELEMENT_ARC_FILE_ID + ">" + prop.getProperty(ELEMENT_ARC_FILE_ID) + "</" + ELEMENT_ARC_FILE_ID + ">"
                + "<" + ELEMENT_ARC_FILE_FILEPATH + ">" + prop.getProperty(ELEMENT_ARC_FILE_FILEPATH) + "</" + ELEMENT_ARC_FILE_FILEPATH + ">"
                + "<" + ELEMENT_ARC_FILE_IDX + ">" + prop.getProperty(ELEMENT_ARC_FILE_IDX) + "</" + ELEMENT_ARC_FILE_IDX + ">"
                + "<" + ELEMENT_OPENURL_URL + ">" + prop.getProperty(ELEMENT_OPENURL_URL) + "</" + ELEMENT_OPENURL_URL + ">"
                + "<" + ELEMENT_ARC_FILE_ADMIN + ">" + prop.getProperty(ELEMENT_ARC_FILE_ADMIN) + "</" + ELEMENT_ARC_FILE_ADMIN + ">"
                + digest
                + "</" + ELEMENT_ARC_FILE_REGISTRY + ">"
                + "</metadata></record></PutRecord>";
        
        String footer = "</PMPrequest>";
        log.debug("**** ARCfile Registry PutRecord Request ****\n " + header + body + footer);
        return header + body + footer;
    }

}
