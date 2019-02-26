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

package gov.lanl.xmltape.identifier.openurl;

import gov.lanl.identifier.IndexException;

import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;
import ORG.oclc.openurl.descriptors.Descriptor;

public abstract class IdentifierResolverService extends ORG.oclc.openurl.services.AbstractService {
    
    static IdentifierResolverIface idResolver;
    
    //static final String DEFAULT_ID_RESOLVER = "gov.lanl.xmltape.identifier.openurl.IdentifierResolver";
    static final String DEFAULT_ID_RESOLVER = "gov.lanl.xmltape.identifier.index.bdbImpl.BDBIdentifierResolver";

    static Logger log = Logger.getLogger(IdentifierResolverService.class.getName());
    
    static {
        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("tape.properties"));
            String impl = props.getProperty("adore-xmltape-resolver.ResolverPlugin", DEFAULT_ID_RESOLVER);
            try {
            	idResolver = (IdentifierResolverIface) Class.forName(impl).newInstance();
            	idResolver.setProperties(props);
            } catch (InstantiationException e) {
                throw new IndexException(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                throw new IndexException(e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                throw new IndexException("Implementing class, " + impl + " is not available in classpath", e);
            }
        } catch (Exception ex) {
            log.error(" IdentifierResolverService init failed:" + ex.getMessage());
        }

    }

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
    public IdentifierResolverService(ContextObject contextObject)
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

    public String getRedirectURL() {
        return null;
    }
}
