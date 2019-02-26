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

import gov.lanl.arc.ARCResourceNotFoundException;
import gov.lanl.arc.heritrixImpl.ARCFileReader;
import gov.lanl.arc.registry.RegistryException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;

public class OpenURLaDORe4Service extends ArcResolverService {
    
    static Logger log = Logger.getLogger(OpenURLaDORe4Service.class.getName());
    
    public OpenURLaDORe4Service(ContextObject contextObject) throws OpenURLException, IOException {
        super(contextObject);
        String referent = contextObject.getReferents()[0].getDescriptors()[0].getDataType().getValue();
        log.debug("referent: " + referent);
        String resolver = contextObject.getResolvers()[0].getDescriptors()[0].getDataType().getValue();
        log.debug("resolver: " + resolver);
        getDatastream(referent, resolver);
    }

    private void getDatastream(String referent, String resolver) {
    	ARCFileReader reader = null;
    	ByteArrayOutputStream baos = null;
        try {
            // search arc file for specified value
            reader = getARCFileReader(resolver);
            baos = reader.getDataStreamUsingID(referent);

            if (baos == null) {
                status = HttpServletResponse.SC_NOT_FOUND;
                bytes = new String("Specified resource (" + referent + ") was not found.").getBytes();
                return;
            }

            contentType = reader.getCdxInstance().getMimeTypeforIdentifier(referent);
            bytes = baos.toByteArray();
            status = HttpServletResponse.SC_OK;
        } catch (ARCResourceNotFoundException e) {
            bytes = new String("Specified resource (" + referent + ") was not found.").getBytes();
            status = HttpServletResponse.SC_NOT_FOUND;
        } catch (RegistryException e) {
            bytes = new String("Specified resource (" + resolver + ") not found in registry.").getBytes();
            status = HttpServletResponse.SC_NOT_FOUND;
        } catch (Exception e) {
            bytes = new String("An Internal Server Error Occurred.").getBytes();
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            log.info("from servlet:" + e.toString());
        } finally {
				try {
		        	if (reader != null)
					   reader.close();
		        	if (baos != null)
		         	   baos.close();
				} catch (IOException e) {
					log.error("from servlet: unable to close filehandles for " + e.toString());
				}
        }
    }
}
