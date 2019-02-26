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

package gov.lanl.locator;

import gov.lanl.identifier.sru.SRUDC;
import gov.lanl.identifier.sru.SRUSearchRetrieveResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;
import ORG.oclc.openurl.descriptors.Descriptor;

/**
 */
public class IdBrokerService extends ORG.oclc.openurl.services.AbstractService {

    static Logger log = Logger.getLogger(IdBrokerService.class.getName());
    
    static IdLocatorClient client;

    static {
        try {
            client = new IdLocatorClient("idlocator.properties");
        } catch (Exception ex) {
            log.error("IdBrokerService init failed:" + ex.getMessage());
        }
    }

    private byte[] bytes;

    private String contentType;

    private int status;

    /**
     * @param contextObject
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws OpenURLException
     */
    public IdBrokerService(ContextObject contextObject)
            throws OpenURLException, java.io.IOException {
        super((Descriptor) null, contextObject);
        String referent = contextObject.getReferents()[0].getDescriptors()[0].getDataType().getValue();
        log.debug("referent: " + referent);
        getRepos(referent);
    }

    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Get the Mime type for the current resource
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Get the status of the current response
     */
    public int getStatus() {
        return status;
    }

    public String getRedirectURL() {
        return null;
    }

    public void getRepos(String id) {
        try {
            log.debug("referent=" + id);

            ArrayList<IdLocation> locations = client.getLocations(id);

            // Compose SRU Response
            SRUSearchRetrieveResponse srr = new SRUSearchRetrieveResponse();
            for (IdLocation loc : locations) {
                SRUDC dc = new SRUDC();
                dc.addKey(SRUDC.Key.IDENTIFIER, loc.getId());
                dc.addKey(SRUDC.Key.SOURCE, loc.getRepo());
                dc.addKey(SRUDC.Key.DATE, loc.getDate());
                srr.addRecord(dc);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SRUSearchRetrieveResponse.write(baos, srr);
            if (baos == null) {
                throw new IdNotFoundException("Specified identifier not found.");
            }

            contentType = "text/xml";
            bytes = baos.toByteArray();
            status = HttpServletResponse.SC_OK;
        } catch (IdNotFoundException e) {
            status = HttpServletResponse.SC_NOT_FOUND;
            bytes = e.getMessage().getBytes();
            contentType = "text/plain";
        } catch (Exception e) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            log.error("from servlet:" + e.toString());
        }
    }
}
