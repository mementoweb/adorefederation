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

import gov.lanl.identifier.sru.SRUDC;
import gov.lanl.identifier.sru.SRUException;
import gov.lanl.identifier.sru.SRUSearchRetrieveResponse;
import gov.lanl.resource.ResourceException;
import gov.lanl.resource.ResourceRecord;
import gov.lanl.resource.filesystem.ResourceReader;
import gov.lanl.resource.openurl.IdentifierNotFoundException;
import gov.lanl.resource.openurl.ResolverException;
import gov.lanl.util.properties.PropertiesUtil;
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

import org.oclc.oomRef.descriptors.ByValueMetadataImpl;

/**
 * The OpenURLaDORe4 OpenURL Service
 * 
 * @author Ryan Chute
 */
public class OpenURLaDORe8Service implements Service {

    private ResourceReader reader;
    private static final String svc = "info:lanl-repo/svc/getMetadata";
    
    /**
     * Construct an openurl-aDORe8 web service class.
     * 
     * @param openURLConfig
     * @param classConfig
     * @throws ResolverException 
     */
    public OpenURLaDORe8Service(OpenURLConfig openURLConfig, ClassConfig classConfig) throws ResolverException {
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

    	int status = HttpServletResponse.SC_OK;
        String responseFormat = null;
        InputStream response = null;

        Object rft = contextObject.getReferent().getDescriptors()[0];
        String id = ((URI) rft).toASCIIString();

        Object res = contextObject.getResolvers()[0].getDescriptors()[0];
        String resolver = ((URI) res).toASCIIString();
        
        responseFormat = "application/xml";

        try {
            byte[] out = getMetadata(resolver, id);
            response = new ByteArrayInputStream(out);
        } catch (IdentifierNotFoundException ex) {
            response = new ByteArrayInputStream((" id doesn't exist " + ex.getMessage()).getBytes("UTF-8"));
            responseFormat = "text/plain";
            status = HttpServletResponse.SC_NOT_FOUND;
        } catch (ResolverException ex) {
            response = new ByteArrayInputStream((" service exception " + ex.getMessage()).getBytes("UTF-8"));
            responseFormat = "text/plain";
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        return new OpenURLResponse(status, responseFormat, response);
    }
    
    public byte[] getMetadata(String repoId, String rftId) throws ResolverException, IdentifierNotFoundException {
        try {
        	ResourceRecord rr = reader.getMetadata(repoId, rftId);
            SRUSearchRetrieveResponse srr = new SRUSearchRetrieveResponse();
            srr.setSearchRequest("dc.identifier", rftId);
            SRUDC dc = new SRUDC();
			dc.addKey(SRUDC.Key.IDENTIFIER, rr.getIdentifier());
			dc.addKey(SRUDC.Key.IDENTIFIER, rr.getResourceUri());
			dc.addKey(SRUDC.Key.SOURCE, rr.getRepositoryId());
		    dc.addKey(SRUDC.Key.DATE, rr.getDate());
		    dc.addKey(SRUDC.Key.FORMAT, rr.getMimetype());
		    dc.addKey(SRUDC.Key.IDENTIFIER, "urn:sha1:" + rr.getChecksum());
			srr.addRecord(dc);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SRUSearchRetrieveResponse.write(baos, srr);
            byte[] bytes = baos.toByteArray();

            if (bytes == null)
                throw new ResolverException("Unable to resolve identifier: " + rftId);

            return bytes;
        } catch (ResourceException e) {
            throw new ResolverException("Error attempting to obtain record: " + e.getMessage());
        } catch (SRUException e) {
        	throw new ResolverException("Error attempting to process record: " + e.getMessage());
		}
    }
}
