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

import javax.servlet.http.HttpServletResponse;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.contextObjectFormat.AbstractContextObjectFormat;
import ORG.oclc.openurl.contextObjectProcessors.AbstractContextObjectProcessor;

/**
 * Process the AbstractContextObjectFormat
 */
public class XQueryOpenUrlProcessor extends AbstractContextObjectProcessor {
    
    public static String COMMUNITY_PROFILE_URL = "http://www.openurl.info/registry/docs/pro/info:ofi/pro:sap2-2004";
    public static final String SVC_ADORE7_SUBJECTS_LIST = "info:lanl-repo/svc/subjects.list";
    public static final String SVC_ADORE7_SEMANTICS_LIST = "info:lanl-repo/svc/semantics.list";
    public static final String SVC_ADORE7_FORMATS_LIST = "info:lanl-repo/svc/formats.list";
    public static final XQueryResolver resolver = new XQueryResolver();
    
    /**
     * Run all the services in all the ContextObjects
     * and generate the response components.
     * 
     * @param contextObjectFormat
     * @throws Throwable
     */
    public XQueryOpenUrlProcessor(AbstractContextObjectFormat contextObjectFormat)
       throws Throwable {
        ContextObject[] contextObjects = contextObjectFormat.getContextObjects();

        String svcId = null;
        String referent = null;
        if (contextObjects.length > 0) {
            ContextObject co = contextObjects[0];
            svcId = co.getServiceTypes()[0].getDescriptors()[0].getDataType().getValue();
            referent = co.getReferents()[0].getDescriptors()[0].getDataType().getValue();

            XQueryResolverService service = resolver.getService(svcId, co);
            
            if (service == null)
                sendErrorResponse("Unrecognized svc_id");
            else
                service.run(referent);
            
            int status = service.getStatus();
            setStatus(status);
            
            byte[] bytes = service.getBytes();
            if (bytes != null)
                setBytes(bytes);
            
            String type = service.getContentType();
            if (type != null)
                setContentType(type);
            else if (status == HttpServletResponse.SC_NOT_FOUND)
                setContentType("text/plain; charset=UTF-8");
            
            setRedirectURL(service.getRedirectURL());
        } else {
            sendErrorResponse("No contextObject to process.");
        }
    }
    
    private void sendErrorResponse(String message) {
        setStatus(HttpServletResponse.SC_NOT_FOUND);
        setBytes(message.getBytes());
        setContentType("text/plain; charset=UTF-8");
    }
}