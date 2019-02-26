/*
 *Copyright (c) 2000-2002 OCLC Online Computer Library Center, Inc. and
 *other contributors. All rights reserved.  The contents of this file, as
 *updated from time to time by the OCLC Office of Research, are subject to
 *OCLC Research Public License Version 2.0 (the "License"); you may not
 *use this file except in compliance with the License. You may obtain a
 *current copy of the License at http://purl.oclc.org/oclc/research/ORPL/.
 *Software distributed under the License is distributed on an "AS IS"
 *basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 *License for the specific language governing rights and limitations under
 *the License.  This software consists of voluntary contributions made by
 *many individuals on behalf of OCLC Research. For more information on
 *OCLC Research, please see http://www.oclc.org/oclc/research/.

 *The Original Code is ERRoLResolver.java.
 *The Initial Developer of the Original Code is Jeff Young.
 *Portions created by LANL are
 *Copyright (C)2006 Los Alamos National Security. All Rights Reserved.
 *Contributor(s): Ryan Chute.
 */

package gov.lanl.arc.resolver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ORG.oclc.openurl.OpenURLResolver;
import ORG.oclc.openurl.contextObjectProcessors.AbstractContextObjectProcessor;

public class MultiArcResolver extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Properties defaultProperties = new Properties();
    private OpenURLResolver resolver;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            // load default parameters for multi-tape OpenURL requests
            String mOpenUrlProperties = getServletContext().getInitParameter("mOpenUrlProperties");
            defaultProperties.load(new FileInputStream(mOpenUrlProperties));
            // Define the OpenURL Resolver API Properties
            defaultProperties.put("info:ofi/fmt:kev:mtx:ctx","ORG.oclc.openurl.contextObjectFormat.FmtKevMtxCtx");
            defaultProperties.put("info:ofi/fmt:xml:xsd:ctx","ORG.oclc.openurl.contextObjectFormat.FmtXmlXsdCtx");
            defaultProperties.put("info:ofi/fmt:kev:mtx:journal","ORG.oclc.openurl.metadataFormats.FmtJournal");
            defaultProperties.put("info:ofi/fmt:xml:xsd:journal","ORG.oclc.openurl.metadataFormats.FmtJournal");
            // Define our Processor Implementation
            defaultProperties.put("processorClassName","gov.lanl.arc.resolver.ArcProcessor");
            resolver = new OpenURLResolver(defaultProperties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage(), e);
        }
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        OutputStream out = resp.getOutputStream();
        try {
            String requestURI = req.getRequestURI();
            String arcname = getArcname(requestURI);
            String arcId = arcname;

            // get query args
            Map rawMap = new HashMap(req.getParameterMap());
            // add tape name as res_id
            rawMap.put("res_id",new String[]{arcId});
            AbstractContextObjectProcessor processor = resolver.resolve(rawMap);
            
            // relay the response from the processed request
            int status = processor.getStatus();
            resp.setStatus(status);
            switch (status) {
            case HttpServletResponse.SC_MOVED_TEMPORARILY:
                resp.sendRedirect(resp.encodeURL(processor.getRedirectURL()));
                break;
            default:
                resp.setContentType(processor.getContentType());
                out.write(processor.getBytes());
                break;
            }
            out.close();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Throwable e2 = e.getTargetException();
            throw new ServletException(e.getMessage(), e2);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
    
    private String getArcname(String requestURI) {
        String[] tokens = requestURI.split("/");
        int l = tokens.length;
        for (int i = 0; i < l; i++) {
            String token = tokens[i];
            if (token.contains("openurl-aDORe") && l > i + 1)
                return tokens[i+1];
            else if (token.contains("openurl-aDORe") && l == i + 1)
                return tokens[i-1];
        }
        return null;
    }
}
