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

package gov.lanl.xmltape.moai;

import gov.lanl.xmltape.oai.TapeRecordFactory;
import gov.lanl.xmltape.registry.DirectoryRegistry;
import gov.lanl.xmltape.registry.OAIRegistry;
import gov.lanl.xmltape.registry.Registry;
import gov.lanl.xmltape.registry.RegistryException;
import gov.lanl.xmltape.registry.TapeConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ORG.oclc.oai.server.OAIHandler;
import ORG.oclc.oai.server.catalog.AbstractCatalog;
import ORG.oclc.oai.server.verb.ServerVerb;


/**
 * OAI Repository Accessor Handler
 * A main servlet class, it supports multiple oai repository by calling oaicat
 * 
 * @author xliu
 */

public class Handler extends HttpServlet {
    static Logger log = Logger.getLogger(Handler.class.getName());

    private Properties defaultProperties = new Properties();

    private Registry registry;

    private HashMap tapeOAICatalogs = new HashMap();

    private HashMap serverVerbs = null;
    
    private final String DEFAULT_INDEX_IMPL = "gov.lanl.xmltape.index.BasicTapeIndex";
    
    private String defaultIndexImpl = DEFAULT_INDEX_IMPL;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            //load default parameters for oai requests
            String propfile = getServletContext().getInitParameter("moaiProperties");
            defaultProperties.load(new FileInputStream(propfile));
            
            //load registry
            if (defaultProperties.getProperty("moai.DirectoryRegistry") != null) {
                registry = new DirectoryRegistry(defaultProperties.getProperty("moai.DirectoryRegistry"));
            } else if (defaultProperties.getProperty("moai.OAIRegistry") != null) {
                log.info("use OAIRegistry " + defaultProperties.getProperty("moai.OAIRegistry"));
                registry = new OAIRegistry(defaultProperties.getProperty("moai.OAIRegistry"));
            } else {
                throw new MOAIException("cannot find xmltape registry");
            }

            //load default index impl
            if (defaultProperties.getProperty("moai.indexPlugin") != null) {
                defaultIndexImpl = defaultProperties.getProperty("moai.indexPlugin");
            }
            
            serverVerbs = ServerVerb.getVerbs(defaultProperties);
            log.info("Handler loading finished");

        } catch (Exception ex) {
            log.error("moai handler init failed:" + ex.getMessage());
        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            resp.setContentType("text/xml");
            //fetch xmltape name
            String requestURI = req.getRequestURI();
            String tapename = getTapename(requestURI);
            log.info("processing " + tapename);
            HashMap localAttributes = getCatalog(tapename);
            String result = OAIHandler.getResult(localAttributes, req, resp,
                    null, serverVerbs, null, "");
            Writer out = OAIHandler.getWriter(req, resp);
            out.write(result);
            out.close();
        } catch (RegistryException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e
                    .getMessage());
        }
    }
    
    private String getTapename(String requestURI) {
        String[] tokens = requestURI.split("/");
        int l = tokens.length;
        for (int i = 0; i < l; i++) {
            String token = tokens[i];
            if (token.equals("Handler") && l > i + 1)
                return tokens[i+1];
            else if (token.equals("Handler") && l == i + 1)
                return tokens[i-1];
        }
        return null;
    }

    private HashMap createCatalog(String tapename) throws RegistryException {

        try {
            log.info("create catalog for " + tapename);
            HashMap localAttributes = new HashMap();
            //bulid complete properties
            Properties localProps = new Properties();
            localProps.putAll(defaultProperties);
            TapeConfig tapeconfig = registry.getTapeConfig(tapename);
            localProps.putAll(tapeconfig.getProperties());
            if (!localProps.contains("TapeOAICatalog.indexPlugin"))
               localProps.put("TapeOAICatalog.indexPlugin", defaultIndexImpl);
            logProperties(localProps);
            AbstractCatalog oaiCatalog = AbstractCatalog.factory(localProps);
            oaiCatalog.setRecordFactory(new TapeRecordFactory(localProps));
            localAttributes.put("OAIHandler.properties", localProps);
            localAttributes.put("OAIHandler.version", OAIHandler.getVERSION());
            localAttributes.put("OAIHandler.catalog", oaiCatalog);
            return localAttributes;
        } catch (Throwable ex) {
            throw new RegistryException(ex);
        }
    }

    private HashMap getCatalog(String tapename) throws RegistryException {
        //     logger.debug("getCatalog for " + srwURL);
        HashMap localAttributes = (HashMap) tapeOAICatalogs.get(tapename);
        //     logger.debug("found=" + localAttributes);
        if (localAttributes == null) {
            localAttributes = createCatalog(tapename);
            tapeOAICatalogs.put(tapename, localAttributes);
        }
        return localAttributes;
    }

    private void logProperties(Properties prop) {
        for (Enumeration enums = prop.keys(); enums.hasMoreElements();) {
            String key = (String) (enums.nextElement());
            String value = (String) (prop.get(key));
            log.info(key + "=" + value);
        }

    }
}
