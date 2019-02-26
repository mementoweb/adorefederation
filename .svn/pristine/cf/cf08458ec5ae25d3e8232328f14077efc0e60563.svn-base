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

package gov.lanl.xmltape.resolver.xquery;

import gov.lanl.util.xquery.XQueryProfile;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;
import ORG.oclc.openurl.descriptors.Descriptor;

public abstract class XQueryResolverService extends ORG.oclc.openurl.services.AbstractService {
    
    protected byte[] bytes;

    protected String contentType;

    protected int status = -1;

    /**
     * @param contextObject
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws OpenURLException
     */
    public XQueryResolverService(ContextObject contextObject)
            throws OpenURLException, java.io.IOException {
        super((Descriptor) null, contextObject);
    }

    /**
     * Gets the bytes to be used as a response
     */
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

    /**
     * Unused
     */
    public String getRedirectURL() {
        return null;
    }

    /**
     * Get the XQuery Profile
     */
    public abstract void setProfile(XQueryProfile profile);
    
    /**
     * Get the XQuery Resolver
     */
    public abstract void setResolver(XQueryResolver resolver);
    
    /**
     * Perform the service request
     */
    public abstract void run(String referent);
    
}
