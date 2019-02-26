/**
 * Copyright 2006 OCLC Online Computer Library Center Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gov.lanl.resource.openurl;

import gov.lanl.util.HttpDate;
import info.openurl.oom.ContextObject;
import info.openurl.oom.OpenURLRequest;
import info.openurl.oom.OpenURLRequestProcessor;
import info.openurl.oom.OpenURLResponse;
import info.openurl.oom.Transport;
import info.openurl.oom.config.OpenURLConfig;
import info.openurl.oom.entities.Resolver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jeffrey A. Young
 * 
 * OpenURL Servlet
 */
public class OpenURLServlet extends HttpServlet {
    /**
     * Initial version
     */
    private static final long serialVersionUID = 1L;
    private OpenURLConfig openURLConfig;
    private OpenURLRequestProcessor processor;
    private Transport[] transports;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        try {
            // load the configuration file from the classpath
            openURLConfig = new org.oclc.oomRef.config.OpenURLConfig(config);
            
            // Construct the configured transports
            transports = openURLConfig.getTransports();
            
            // Construct a processor
            processor = openURLConfig.getProcessor();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage(), e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            String requestURI = req.getRequestURI();
            if (isOREServiceRequest(requestURI)) {
            	resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
                String requestURL = req.getRequestURL().toString();
                String redirect = requestURL.replace("/rem/rdf", "").replace("/aggregation", "");
                redirect += "/openurl-aDORe9?url_ver=Z39.88-2004&rft_id=info:lanl-repo/loc/LOC_f32f0258-0297-47f0-af03-b030e3c0d3bd&svc_id=info:lanl-repo/svc/ore.rdf";
            	resp.sendRedirect(redirect);
            }
            String collectionId = getCollectionId(requestURI);
            Resolver resolver = processor.resolverFactory(new URI(collectionId));
            // BaseURL
            StringBuffer requestURL = req.getRequestURL();
            String base = requestURL.substring(0,requestURL.lastIndexOf("/"));
            Resolver baseURL = processor.resolverFactory(new URI(base));
            
            // Try each Transport until someone takes responsibility
            OpenURLRequest openURLRequest = null;
            for (int i=0; openURLRequest == null && i<transports.length; ++i) {
                openURLRequest = transports[i].toOpenURLRequest(processor, req);
            }
            
            if (openURLRequest == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid Request");
                return;
            }
            
            // Override the Context Object to add the resolver id
            ContextObject contextObject = openURLRequest.getContextObjects()[0];
            contextObject = processor.contextObjectFactory(
                    contextObject.getReferent(),
                    contextObject.getReferringEntities(),
                    contextObject.getRequesters(),
                    contextObject.getServiceTypes(),
                    new Resolver[] { resolver, baseURL },
                    contextObject.getReferrers());
            openURLRequest = processor.openURLRequestFactory(contextObject);
            
            // Process the ContextObjects
            OpenURLResponse result = processor.resolve(openURLRequest);
            
            // See if anyone handled the request
            int status;
            if (result == null) {
                status = HttpServletResponse.SC_NOT_FOUND;
            } else {
                status = result.getStatus();
                Cookie[] cookies = result.getCookies();
                if (cookies != null) {
                    for (int i=0; i< cookies.length; ++i) {
                        resp.addCookie(cookies[i]);
                    }
                }
                
                Map sessionMap = result.getSessionMap();
                if (sessionMap != null) {
                    HttpSession session = req.getSession(true);
                    Iterator iter = sessionMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Entry) iter.next();
                        session.setAttribute((String) entry.getKey(), entry.getValue());
                    }
                }
                
                Map headerMap = result.getHeaderMap();
                if (headerMap != null) {
                    Iterator iter = headerMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Entry) iter.next();
                        resp.setHeader((String) entry.getKey(),
                                (String) entry.getValue());
                    }
                }
            }
            
            // Allow the processor to generate a variety of response types
            switch (status) {
            case HttpServletResponse.SC_MOVED_TEMPORARILY:
                resp.sendRedirect(
                        resp.encodeRedirectURL(
                                result.getRedirectURL()));
                break;
            case HttpServletResponse.SC_SEE_OTHER:
            case HttpServletResponse.SC_MOVED_PERMANENTLY:
                resp.setStatus(status);
                resp.setHeader("Location", result.getRedirectURL());
                break;
            case HttpServletResponse.SC_NOT_FOUND:
                resp.sendError(status);
                break;
            default:
                OutputStream out = resp.getOutputStream();
                resp.setStatus(status);
                resp.setContentType(result.getContentType());
                InputStream is = result.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                int totalBytes = is.available();
                while ((len = is.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                resp.setContentLength(totalBytes);
                resp.setDateHeader("Date", System.currentTimeMillis());
                out.close();
                break;
            }
        } catch (Throwable e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        doGet(req, resp);
    }
    
    private String getCollectionId(String requestURI) {
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
    
    private boolean isOREServiceRequest(String requestURI) {
    	if(requestURI.contains("rem") || requestURI.contains("aggregation"))
    		return true;
    	else 
    		return false;
    }
}
