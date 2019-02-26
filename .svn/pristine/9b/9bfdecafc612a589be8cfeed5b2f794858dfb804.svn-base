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

package gov.lanl.util.oai.oaiharvesterwrapper;

import java.io.StringReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * OAI ListMetadataFormats Request
 */
public class ListMetadataFormats {
    ORG.oclc.oai.harvester.verb.ListMetadataFormats listmetadataformats = null;

    private Hashtable metadataTable = new Hashtable();

    static DocumentBuilder builder;

    static {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            builder = factory.newDocumentBuilder();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Construtor using the OAI baseURl - Gathers all available formats
     * @param baseURL - OAI baseURL
     * @throws Exception
     */
    public ListMetadataFormats(String baseURL) throws Exception {
        listmetadataformats = new ORG.oclc.oai.harvester.verb.ListMetadataFormats(
                new URL(baseURL));
        build();
    }

    /**
     * Construtor using the OAI baseURl & identifier - Gathers formats supported
     * by specified identifier
     * @param baseURL - OAI baseURL
     * @throws Exception
     */
    public ListMetadataFormats(String baseURL, String identifier)
            throws Exception {
        listmetadataformats = new ORG.oclc.oai.harvester.verb.ListMetadataFormats(
                new URL(baseURL), identifier);
        build();
    }

    private void build() throws Exception {
        for (Iterator it = listmetadataformats.getMetadataFormats().iterator(); it
                .hasNext();) {
            String data = (String) (it.next());
            Document doc = builder
                    .parse(new InputSource(new StringReader(data)));
            String metadataPrefix = doc.getDocumentElement()
                    .getElementsByTagName("metadataPrefix").item(0)
                    .getFirstChild().getNodeValue();
            String schema = doc.getDocumentElement().getElementsByTagName(
                    "schema").item(0).getFirstChild().getNodeValue();
            String metadataNamespace = doc.getDocumentElement()
                    .getElementsByTagName("metadataNamespace").item(0)
                    .getFirstChild().getNodeValue();
            metadataTable.put(metadataPrefix, metadataNamespace);
        }
    }

    /**
     * Get a list of supported metadata formats as a hashtable
     * @return hashtable of supported OAI metadata formats
     */
    public Hashtable getMetadataTable() {
        return metadataTable;
    }

    /**
     * Get the time of OAI repository response
     * @return OAI compliant date as a String
     */
    public String getResponseDate() {
        return listmetadataformats.getResponseDate();
    }

    /**
     * Get the OAI server response as in XML form
     * @return String of response in XML form
     */
    public String getResponseXML() {
        return new String(listmetadataformats.getResponseBuffer());
    }
}
