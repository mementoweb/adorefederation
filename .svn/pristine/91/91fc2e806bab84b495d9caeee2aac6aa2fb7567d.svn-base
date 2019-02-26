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

package gov.lanl.arc.resolver;

import gov.lanl.arc.ARCEnvConfig;
import gov.lanl.arc.ARCException;
import gov.lanl.arc.ARCProperties;
import gov.lanl.arc.heritrixImpl.ARCFileReader;
import gov.lanl.arc.registry.OAIRegistry;
import gov.lanl.arc.registry.RegistryException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;
import ORG.oclc.openurl.descriptors.Descriptor;

public abstract class ArcResolverService extends
        ORG.oclc.openurl.services.AbstractService {
    
    static OAIRegistry registry;

    static Logger log = Logger.getLogger(ArcResolverService.class.getName());
    
    static {
        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("arc.properties"));
            props.list(System.out);
            // init ARCProperties
            ARCProperties.load(props);
            // load registry
            if (props.getProperty("ARC.OAIRegistry") != null) {
                registry = new OAIRegistry(props.getProperty("ARC.OAIRegistry"));
                log.info(props.getProperty("ARC.OAIRegistry"));
            } else {
                throw new Exception("cannot find arcfile registry");
            }
        } catch (Exception ex) {
            log.error(" ArcResolverService init failed:" + ex.getMessage());
        }
    }

    protected byte[] bytes;

    protected String contentType;

    protected int status;

    /**
     * @param contextObject
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws OpenURLException
     */
    public ArcResolverService(ContextObject contextObject)
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

    protected ARCFileReader getARCFileReader(String resolver) 
        throws RegistryException, URISyntaxException, ARCException {

        //load directories from registry
        ARCEnvConfig arcconfig = registry.getARCConfig(resolver);
        log.debug(arcconfig.getARCFileName());
        String arcfile = new URI(arcconfig.getARCFileName()).getPath();
        if (arcfile.charAt(2) == ':')
               arcfile = arcfile.substring(1).replace("/", "\\");
        log.debug("ARCfile: " + arcfile);
        String cdxfile = new URI(arcconfig.getARCIndexFileName()).getPath();
        if (cdxfile.charAt(2) == ':')
               cdxfile = cdxfile.substring(1).replace("/", "\\");
        log.debug("IndexDir: " + cdxfile);

        return new ARCFileReader(arcfile, true);
    }
}
