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
import info.openurl.oom.ContextObject;
import info.openurl.oom.OpenURLRequest;
import info.openurl.oom.OpenURLRequestProcessor;
import info.openurl.oom.OpenURLResponse;
import info.openurl.oom.Service;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;
import info.openurl.oom.entities.ServiceType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.oclc.oomRef.descriptors.ByValueMetadataImpl;

/**
 * The OpenURLaDORe3 OpenURL Service
 * 
 * @author Ryan Chute
 */
public class OpenURLaDORe3Service implements Service {

    private ResolverConnectionHandler db;
    private static final String svc = "info:lanl-repo/svc/identifiers.list";
    
    /**
     * Construct an openurl-aDORe3 web service class.
     * 
     * @param openURLConfig
     * @param classConfig
     * @throws ResolverException 
     */
    public OpenURLaDORe3Service(OpenURLConfig openURLConfig, ClassConfig classConfig) throws ResolverException {
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
        String from = null;
        String until = null;

        Object rft = contextObject.getReferent().getDescriptors()[0];
        String id = ((URI) rft).toASCIIString();
        HashMap<String, String> kev = setDateRangeValues(contextObject);
        if (kev.containsKey("from"))
            from = kev.get("from");
        if (kev.containsKey("until"))
            until = kev.get("until");
        responseFormat = "text/plain; charset=utf-8";

        try {
            response = listIdentifiers(from, until, id);
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
    
    private static HashMap<String, String> setDateRangeValues(ContextObject co) {
        HashMap<String, String> map = new HashMap<String, String>();
        Object[] svcData = (Object[]) co.getServiceTypes()[0].getDescriptors();
        if (svcData != null && svcData.length > 0) {
            Object tmp = svcData[0];
            if (tmp.getClass().isInstance(ByValueMetadataImpl.class)) {
                ByValueMetadataImpl kev = ((ByValueMetadataImpl) tmp);
                if (kev.getFieldMap().size() > 0) {
                    if (kev.getFieldMap().containsKey("svc.from")
                            && ((String[]) kev.getFieldMap().get("svc.from"))[0] != "")
                        map.put("from", ((String[]) kev.getFieldMap().get(
                                "svc.from"))[0]);
                    if (kev.getFieldMap().containsKey("svc.until")
                            && ((String[]) kev.getFieldMap().get("svc.until"))[0] != "")
                        map.put("until", ((String[]) kev.getFieldMap().get(
                                "svc.until"))[0]);
                }
            }
        }
        return map;
    }
    
    public byte[] listIdentifiers(String from, String until, String source) throws ResolverException {
        return db.listIdentifiers(from, until, source);
    }
}
