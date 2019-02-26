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

package gov.lanl.adore.diag;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.contextObjectFormat.AbstractContextObjectFormat;
import ORG.oclc.openurl.contextObjectProcessors.AbstractContextObjectProcessor;

/**
 * @author Luda
 * 
 * Process the AbstractContextObjectFormat
 */
public class AdoreSysDiagProcessor extends AbstractContextObjectProcessor {

    public static String COMMUNITY_PROFILE_URL = "http://www.openurl.info/registry/docs/pro/info:ofi/pro:sap2-2004";

    /**
     * Run all the services in all the ContextObjects and generate the response
     * components.
     * 
     * @param contextObjectFormat
     * @throws Throwable
     */
    public AdoreSysDiagProcessor(AbstractContextObjectFormat contextObjectFormat)
            throws Throwable {
        ContextObject[] contextObjects = contextObjectFormat.getContextObjects();
        AdoreSysDiagService service = new AdoreSysDiagService(contextObjects[0]);
        setBytes(service.getBytes());
        setContentType(service.getContentType());
        setStatus(service.getStatus());
        setRedirectURL(service.getRedirectURL());
    }
}
