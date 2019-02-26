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

package gov.lanl.ockham.service;

import gov.lanl.util.HttpDate;
import gov.lanl.util.properties.PropertiesUtil;
import info.openurl.oom.ContextObject;
import info.openurl.oom.OpenURLRequest;
import info.openurl.oom.OpenURLRequestProcessor;
import info.openurl.oom.OpenURLResponse;
import info.openurl.oom.Service;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;
import info.openurl.oom.entities.ServiceType;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

/**
 * A simple OpenURL OCKHAM Registry Service
 * 
 * @author Ryan Chute
 */
public class RegistryOpenURLService implements Service {

    private ServiceRegistryDB registry;

    /**
     * Construct an OCKHAM web service class.
     * 
     * @param openURLConfig
     * @param classConfig
     * @throws TransformerException
     */
    public RegistryOpenURLService(OpenURLConfig openURLConfig,
            ClassConfig classConfig) throws ServiceException {
        try {
            String propFile = classConfig.getArg("props");
            this.registry = new ServiceRegistryDB(PropertiesUtil
                    .loadConfigByCP(propFile));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public URI getServiceID() throws URISyntaxException {
        return new URI("info:lanl-repo/svc/ockham");
    }

    public String getRecord(String identifier) throws ServiceException,
            IdDoesNotExistException {
        String record = null;
        record = registry.getRecord(identifier);
        return record;
    }

    public OpenURLResponse resolve(ServiceType serviceType, ContextObject contextObject,
            OpenURLRequest openURLRequest, OpenURLRequestProcessor processor) throws UnsupportedEncodingException {

        String responseFormat = null;
        String response = null;

        Object rft = contextObject.getReferent().getDescriptors()[0];
        String id = ((URI) rft).toASCIIString();

        responseFormat = "text/xml; charset=utf-8";

        try {
            response = getRecord(id);
        } catch (ServiceException ex) {
            response = " service excpetion " + ex.getMessage();
            responseFormat = "text/plain";
        } catch (IdDoesNotExistException ex) {
            response = " id doesn't exist " + ex.getMessage();
            responseFormat = "text/plain";
        }
        byte[] r = response.getBytes("UTF-8");
        HashMap header_map = new HashMap();
        header_map.put("Content-Length", r.length + "");
        header_map.put("Date", HttpDate.getHttpDate());
        return new OpenURLResponse(HttpServletResponse.SC_OK, responseFormat, r,header_map);
    }
}
