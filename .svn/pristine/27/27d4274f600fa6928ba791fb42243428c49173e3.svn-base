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

import gov.lanl.repo.RepoProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SemanticXML implements SemanticRegistryConstants {

    static Logger log = Logger.getLogger(SemanticXML.class.getName());

    String header = "<PMPrequest xmlns=\""
            + RepoProperties.PUT_RECORD_NS
            + "\" xmlns:ns1=\""
            + RepoProperties.PUT_RECORD_NS
            + "\" xmlns:oai=\""
            + RepoProperties.OAIPMH_NS
            + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\""
            + RepoProperties.PUT_RECORD_NS + " "
            + RepoProperties.getPutRecordSchemaURI() + "\">";

    String pr = "xmlns=\"" + RepoProperties.PUT_RECORD_NS + "\"";

    String dc = "xmlns:dc=\"" + RepoProperties.DC_NS + "\"";

    String sem = "xmlns:sem=\"" + SemanticRegistryProperties.SEMANTIC_REG_NS + "\"";

    String xsem = " xsi:schemaLocation=\""
            + SemanticRegistryProperties.SEMANTIC_REG_NS + " "
            + SemanticRegistryProperties.getSemanticRegistrySchemaURI() + "\"";

    String xdc = " xsi:schemaLocation=\"" + RepoProperties.DC_NS + " "
            + RepoProperties.getDublinCoreSchemaURI() + "\"";

    String xsi = " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";

    static final String[] mandatory_list = { ELEMENT_SEM_ID,
            ELEMENT_SEM_DC_ID };

    public String ProptoXML(Properties prop) throws Exception {
        for (int i = 0; i < mandatory_list.length; i++) {
            if (prop.getProperty(mandatory_list[i]) == null)
                throw new Exception("Format Registry mandatory property "
                        + mandatory_list[i] + " is not defined");
        }

        String body = "<PutRecord " + pr + "><record " + pr + "><metadata " + pr + ">" 
                + "<" + ELEMENT_SEM_SEMANTIC + " " + sem + xsi + " " + xsem + ">" 
                + getXML(prop, ELEMENT_SEM_ID, null)
                + getXML(prop, ELEMENT_SEM_DC_ID, dc + xdc)
                + getXML(prop, ELEMENT_SEM_DC_TITLE, dc + xdc)
                + getXML(prop, ELEMENT_SEM_DC_DESC, dc + xdc)
                + getXML(prop, ELEMENT_SEM_DIDENTITY, null) 
                + "</" + ELEMENT_SEM_SEMANTIC + ">" + "</metadata></record></PutRecord>";

        String footer = "</PMPrequest>";

        return header + body + footer;
    }

    private static String getXML(Properties props, String element, String ns) {
        StringBuffer xml = new StringBuffer();
        for (Object key : props.keySet()) {
            String k = (String) key;
            if (k.startsWith(element)) {
                String v = (String) props.get(k);
                xml.append("<" + element + ((ns != null) ? " " + ns : "") + ">");
                xml.append(v);
                xml.append("</" + element + ">");
            }
        }
        return xml.toString();
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            for (int i = 0; i < args.length; i++) {
                props.load(new FileInputStream(args[i]));
                SemanticXML xml = new SemanticXML();
                System.out.println(xml.ProptoXML(props));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
