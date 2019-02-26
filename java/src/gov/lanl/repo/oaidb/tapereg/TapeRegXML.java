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

package gov.lanl.repo.oaidb.tapereg;

import java.util.Properties;

import org.apache.log4j.Logger;

import gov.lanl.repo.RepoProperties;

public class TapeRegXML implements TapeRegistryConstants {

    static Logger log = Logger.getLogger(TapeRegXML.class.getName());
    
    String header = "<PMPrequest xmlns=\"" + RepoProperties.PUT_RECORD_NS + "\" xmlns:ns1=\"" + RepoProperties.PUT_RECORD_NS + "\" xmlns:oai=\"" + RepoProperties.OAIPMH_NS + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"" + RepoProperties.PUT_RECORD_NS + " " + RepoProperties.getPutRecordSchemaURI() + "\">";

    String pr = "xmlns=\"" + RepoProperties.PUT_RECORD_NS + "\"";

    String dcterms = "xmlns=\"http://purl.org/dc/terms/\"";
    
    String ta = "xmlns:ta=\"" + RepoProperties.TAPE_ADMIN_NS + "\"";

    String tb = "xmlns:tb=\"" + RepoProperties.TAPE_BASCIS_NS + "\"";
    
    String dc = "xmlns=\"http://purl.org/dc/elements/1.1/\"";

    String tap = "xmlns=\"" + RepoProperties.TAPE_REG_NS + "\"";

    String x = "xsi:schemaLocation=\"" + RepoProperties.TAPE_REG_NS + " " + RepoProperties.getTapeRegistrySchemaURI() + "\"";

    String xsi = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";

    static final String[] mandatory_list = { ELEMENT_XML_TAPE_ID, ELEMENT_XML_TAPE_FILEPATH, ELEMENT_XML_TAPE_IDX,
        ELEMENT_OAIPMH_PROPERTIES };

    public String ProptoXML(Properties prop) throws Exception {
        for (int i = 0; i < mandatory_list.length; i++) {
            if (prop.getProperty(mandatory_list[i]) == null)
                throw new Exception("taperegistry mandatory property "
                        + mandatory_list[i] + " is not defined");
        }

        String oaipmhUrl = prop.getProperty(ELEMENT_OAIPMH_URL);
        if (oaipmhUrl == null || oaipmhUrl.trim() == "")
            oaipmhUrl = "<" + ELEMENT_OAIPMH_URL + "/>";
        else
            oaipmhUrl = "<" + ELEMENT_OAIPMH_URL + ">" + oaipmhUrl + "</" + ELEMENT_OAIPMH_URL + ">";
        
        String tapeAdmin = prop.getProperty(ELEMENT_TAPE_ADMIN);
        if (tapeAdmin == null || tapeAdmin.trim() == "")
            tapeAdmin = "<" + ELEMENT_TAPE_ADMIN + "/>";
        
        String tapeDigest = prop.getProperty(ELEMENT_XML_TAPE_DIGEST);
        if (tapeDigest == null || tapeDigest.trim() == "")   
            tapeDigest = "<" + ELEMENT_XML_TAPE_DIGEST + "/>";
        else
            tapeDigest = "<" + ELEMENT_XML_TAPE_DIGEST + ">" + tapeDigest + "</" + ELEMENT_XML_TAPE_DIGEST + ">";
            
        String body = "<PutRecord " + pr + " ><record " + pr + "><metadata  " + pr + ">"
                + "<" + ELEMENT_XML_TAPE_REGISTRY + " " + tap + " " + ta + " " + tb + " " + xsi + " " + x + " >"
                + "<" + ELEMENT_XML_TAPE_ID + ">" + prop.getProperty(ELEMENT_XML_TAPE_ID) + "</" + ELEMENT_XML_TAPE_ID + ">"
                + "<" + ELEMENT_XML_TAPE_FILEPATH + ">" + prop.getProperty(ELEMENT_XML_TAPE_FILEPATH) + "</" + ELEMENT_XML_TAPE_FILEPATH + ">"
                + "<" + ELEMENT_XML_TAPE_IDX + ">" + prop.getProperty(ELEMENT_XML_TAPE_IDX) + "</" + ELEMENT_XML_TAPE_IDX + ">"
                + oaipmhUrl
                + "<" + ELEMENT_OAIPMH_PROPERTIES + ">" + prop.getProperty(ELEMENT_OAIPMH_PROPERTIES) + "</" + ELEMENT_OAIPMH_PROPERTIES + ">"
                + tapeAdmin
                + tapeDigest
                + "</" + ELEMENT_XML_TAPE_REGISTRY + ">"
                + "</metadata></record></PutRecord>";
        String footer = "</PMPrequest>";
        log.debug("**** XMLtape Registry PutRecord Request ****\n " + header + body + footer);
        return header + body + footer;
    }

}
