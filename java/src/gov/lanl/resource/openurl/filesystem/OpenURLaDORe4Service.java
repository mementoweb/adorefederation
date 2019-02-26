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

package gov.lanl.resource.openurl.filesystem;

import gov.lanl.resource.ResourceException;
import gov.lanl.resource.filesystem.ResourceReader;
import gov.lanl.resource.openurl.IdentifierNotFoundException;
import gov.lanl.resource.openurl.ResolverException;
import gov.lanl.util.properties.PropertiesUtil;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.ContextObject;
import info.openurl.oom.OpenURLRequest;
import info.openurl.oom.OpenURLRequestProcessor;
import info.openurl.oom.OpenURLResponse;
import info.openurl.oom.Service;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;
import info.openurl.oom.entities.ServiceType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

/**
 * The OpenURLaDORe4 OpenURL Service
 * 
 * @author Ryan Chute
 */
public class OpenURLaDORe4Service implements Service {

    private ResourceReader reader;
    private static final String svc = "info:lanl-repo/svc/getDatastream";
    
    /**
     * Construct an openurl-aDORe1 web service class.
     * 
     * @param openURLConfig
     * @param classConfig
     * @throws ResolverException 
     */
    public OpenURLaDORe4Service(OpenURLConfig openURLConfig, ClassConfig classConfig) throws ResolverException {
            String propFile;
            try {
                propFile = classConfig.getArg("props");
                this.reader = new ResourceReader(PropertiesUtil.loadConfigByClasspath(propFile));
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
        InputStream response = null;

        Object rft = contextObject.getReferent().getDescriptors()[0];
        String id = ((URI) rft).toASCIIString();

        Object res = contextObject.getResolvers()[0].getDescriptors()[0];
        String resolver = ((URI) res).toASCIIString();
        
        responseFormat = "text/xml; charset=utf-8";

        try {
        	Resource r = reader.getResource(resolver, id);
        	responseFormat = r.getContentType();
        	if (r.getBytes() == null)
                response = r.getInputStream();
        	else
        		response = new ByteArrayInputStream(r.getBytes());
        } catch (IdentifierNotFoundException ex) {
            response = new ByteArrayInputStream((" id doesn't exist " + ex.getMessage()).getBytes("UTF-8"));
            responseFormat = "text/plain";
        } catch (ResolverException ex) {
            response = new ByteArrayInputStream((" service exception " + ex.getMessage()).getBytes("UTF-8"));
            responseFormat = "text/plain";
        } catch (ResourceException e) {
        	response = new ByteArrayInputStream(("Error attempting to obtain record: " + e.getMessage()).getBytes("UTF-8"));
        	responseFormat = "text/plain";
		}

        return new OpenURLResponse(HttpServletResponse.SC_OK, responseFormat, response);
    }
}
