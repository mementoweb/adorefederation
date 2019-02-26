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

package gov.lanl.objectdb.openurl;

import gov.lanl.util.HttpDate;
import gov.lanl.util.properties.PropertiesUtil;
import gov.lanl.util.sru.SRUSearchRetrieveResponse;
import gov.lanl.util.sru.dc.SRUDC;
import info.openurl.oom.ContextObject;
import info.openurl.oom.OpenURLRequest;
import info.openurl.oom.OpenURLRequestProcessor;
import info.openurl.oom.OpenURLResponse;
import info.openurl.oom.Service;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;
import info.openurl.oom.entities.ServiceType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

/**
 * The OpenURLaDORe2 OpenURL Service
 * 
 * @author Ryan Chute
 */
public class OpenURLaDORe2Service implements Service {

    private ResolverConnectionHandler db;
    private static final String svc = "info:lanl-repo/svc/locate.sru";
    
    /**
     * Construct an openurl-aDORe1 web service class.
     * 
     * @param openURLConfig
     * @param classConfig
     * @throws ResolverException 
     */
    public OpenURLaDORe2Service(OpenURLConfig openURLConfig, ClassConfig classConfig) throws ResolverException {
            String propFile;
            try {
                propFile = classConfig.getArg("props");
                this.db = new ResolverConnectionHandler(PropertiesUtil.loadConfigByClasspath(propFile));
            } catch (TransformerException e) {
                throw new ResolverException("Error attempting to obtain props file from classpath, disabling " + svc + " : " + e.getMessage());
            } catch (IOException e) {
                throw new ResolverException("Error attempting to open props file from classpath, disabling " + svc + " : " + e.getMessage());
            } catch (Exception e) {
                throw new ResolverException("Unknown error occurred, disabling " + svc + " : " + e.getMessage());
            }
            
    }

    public URI getServiceID() throws URISyntaxException {
        return new URI(svc);
    }

    public OpenURLResponse resolve(ServiceType serviceType, 
                                   ContextObject contextObject, 
                                   OpenURLRequest openURLRequest, 
                                   OpenURLRequestProcessor processor) 
        throws UnsupportedEncodingException {

        String responseFormat = null;
        byte[] response = null;

        Object rft = contextObject.getReferent().getDescriptors()[0];
        String id = ((URI) rft).toASCIIString();

        responseFormat = "text/xml; charset=utf-8";

        try {
            response = getRecordsList(id);
        } catch (IdentifierNotFoundException ex) {
            response = (" id doesn't exist " + ex.getMessage()).getBytes("UTF-8");
            responseFormat = "text/plain";
        } catch (ResolverException ex) {
            response = (" service exception " + ex.getMessage()).getBytes("UTF-8");
            responseFormat = "text/plain";
        }
        HashMap header_map = new HashMap();
        header_map.put("Content-Length", response.length + "");
        header_map.put("Date", HttpDate.getHttpDate());
        return new OpenURLResponse(HttpServletResponse.SC_OK, responseFormat, response, header_map);
    }
    
    public byte[] getRecordsList(String referent) throws ResolverException {
        try {
            ArrayList<OpenURLaDORe2Record> ids = db.getRecordsList(referent);
            SRUSearchRetrieveResponse srr = new SRUSearchRetrieveResponse();
            srr.setSearchRequest("dc.identifier", referent);
            for (OpenURLaDORe2Record id : ids) {
                SRUDC dc = new SRUDC();
                dc.addKey(SRUDC.Key.IDENTIFIER.getField(), id.getIdentifier());
                dc.addKey(SRUDC.Key.SOURCE.getField(), id.getSource());
                if (id.getDate() != null)
                    dc.addKey(SRUDC.Key.DATE.getField(), id.getDate());
                srr.addRecord(dc);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SRUSearchRetrieveResponse.write(baos, srr);
            byte[] bytes = baos.toByteArray();

            if (bytes == null)
                throw new ResolverException("Unable to resolve identifier: " + referent);
            
            return bytes; 
            
        } catch (Exception e) {
            throw new ResolverException("Error occured during resolution", e);
        }
    }
}
