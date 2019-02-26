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

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;

public class OpenURLaDORe7RangeService extends XQueryResolverService {
    
    static Logger log = Logger.getLogger(OpenURLaDORe7RangeService.class.getName());
    
    private XQueryProfile profile;
    
    private XQueryResolver resolver;
    
    public OpenURLaDORe7RangeService(ContextObject contextObject) throws OpenURLException, IOException {
        super(contextObject);
    }
    
    public void run(String referent) {
        try {
            bytes = resolver.getResultsAsRange(referent, profile);
            contentType = "text/plain; charset=UTF-8";
            status = HttpServletResponse.SC_OK;
        } catch (ResolverException e) {
            bytes = new String("Specified resource (" + referent + ") was not found.").getBytes();
            status = HttpServletResponse.SC_NOT_FOUND;
        } catch (Exception e) {
            bytes = new String("An Internal Server Error Occurred.").getBytes();
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            log.info("from servlet:" + e.toString());
        }
    }

    public void setResolver(XQueryResolver resolver) {
        this.resolver = resolver;
    }
    
    public void setProfile(XQueryProfile profile) {
        this.profile = profile;
    }
}
