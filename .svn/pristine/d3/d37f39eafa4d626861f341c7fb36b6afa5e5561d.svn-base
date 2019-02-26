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

package gov.lanl.repo.oaidb.srv;

import gov.lanl.repo.ExistService;
import gov.lanl.repo.PMPException;
import gov.lanl.repo.PMPRecord;
import gov.lanl.repo.RepoProperties;
import gov.lanl.repo.RepoWriter;
import gov.lanl.util.DateUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ORG.oclc.oai.harvester.verb.GetRecord;
import ORG.oclc.oai.harvester.verb.Record;
import ORG.oclc.oai.server.OAIHandler;
import ORG.oclc.oai.server.catalog.AbstractCatalog;
import ORG.oclc.oai.server.verb.ServerVerb;

/**
 * RepoHandler.java
 * 
 * A main servlet class of OAI DB registration repository, it supports OAI-PMH in http get with baseurl OAIHandler;
 * in http post  two separate services with baseurl PutRecordHandle : PutRecord (PMP specs service)
 * and  UpdateRecord admin utility
 *    
 */

public class RepoHandler extends HttpServlet {
    Logger log = Logger.getLogger(RepoHandler.class.getName());

    private Properties properties = new Properties();
    
    private AbstractCatalog a;

    private HashMap serverVerbs = null;

    private HashMap attributes = new HashMap();

    String propfile = null;

    private Properties myprop = new Properties();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            //load default parameters for oai requests

            propfile = getServletContext().getInitParameter("properties");
            properties.load(new FileInputStream(propfile));
            log.info("load " + propfile + " successful");
            myprop.putAll(properties);

            myprop.setProperty("tables", properties
                    .getProperty("JDBCLimitedOAICatalog.tables"));

            a = AbstractCatalog.factory(properties);
            
            logProperties(myprop);

            attributes.put("OAIHandler.properties", properties);

            //logger.setLevel(Level.parse(properties.getProperty("Logger.Level")));
            log.info("Handler loading finished");

        } catch (Throwable e) {
            e.printStackTrace();
            log.error("repohandler init failed:" + e.getMessage());

        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String protocol = req.getRequestURI();
        log.debug("proto: " + protocol);
        //if (protocol.matches("OAIHandler")) {
        if (protocol.contains("OAIHandler")) {
            doGet(req, resp);
        } else {

            String verb = req.getParameter("verb").trim();

            String xmlout;
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<PMPresponse xmlns=\"" + RepoProperties.PMP_RESPONSE_NS
                    + "\" >\n";

            String date = DateUtil.date2UTC(new Date());
            String identifier = "";
            String requestURI = req.getScheme() + "://" + req.getServerName()
                    + ":" + req.getServerPort() + req.getRequestURI();

            RepoWriter rw = null;

            try {
                PrintWriter out = resp.getWriter();
                String xml = null;
                PMPRecord precord = null;
                rw = new RepoWriter(myprop);

                // if PMP service, need to go to see verb in pmpxml
                if (verb.equals("PutRecord")) {
                    xml = req.getParameter("xml").trim();
                    xml = URLDecoder.decode(xml, "UTF-8");
                    precord = new PMPRecord(xml);
                    verb = precord.getVerb();
                    identifier = precord.getIdentifier();
                }
                
                // Perform PMP Action
                if (verb.equals("PutRecord")) {
                    rw.putRecord(precord.getIdentifier(),
                                 precord.getMetadata(), 
                                 precord.getSetSpecs());
                    identifier = rw.getId();
                    xmlout = header
                            + makePMP(date, identifier, requestURI, verb)
                            + "</PMPresponse>";
                    out.println(xmlout);
                    out.close();
                } else if (verb.equals("DeleteRecord")) {
                    String id = URLDecoder.decode(req.getParameter("identifier").trim(), "UTF-8");
                    rw.deleteRecord(id);
                    xmlout = header
                            + makePMP(date, id, requestURI, verb)
                            + "</PMPresponse>";
                    out.println(xmlout);
                    out.close();

                } else if (verb.equals("UpdateRecord")) {
                    String xupdate = URLDecoder.decode(req.getParameter("xml")
                            .trim(), "UTF-8");
                    String id = URLDecoder.decode(req
                            .getParameter("identifier").trim(), "UTF-8");

                    GetRecord gr = new GetRecord(new URL(requestURI), id,
                            myprop.getProperty("prefix"));
                    Iterator i = gr.iterator();

                    if (i.equals(null))
                        throw new PMPException(
                                "the record with id does not exist", 3);

                    Record record = (Record) i.next();
                    ExistService xs = new ExistService();
                    System.out.println(record.getMetadata());
                    String newxml = xs.GetUpdated(id, record.getMetadata(),
                            xupdate);
                    newxml = removeXMLHeader(newxml);
                    System.out.println(newxml);
                    rw.updateRecord(id, newxml, null);

                    xmlout = header + makePMP(date, id, requestURI, verb)
                            + "</PMPresponse>";
                    out.println(xmlout);
                    out.close();
                } else {
                    //badVerb exception
                    xmlout = header
                            + makePMP(date, identifier, requestURI, verb)
                            + "<error code=\"badVerb\" > verb is not supported </error>"
                            + "</PMPresponse>";
                    out.println(xmlout);
                    out.close();
                }
            } catch (PMPException e) {
                int i = e.getErrorCode();
                identifier = rw.getId();

                PrintWriter out = resp.getWriter();

                if (i == 1062) {
                    xmlout = header
                            + makePMP(date, identifier, requestURI, verb)
                            + "<error code=\"idExists\" >" + e.toString()
                            + "</error>" + "</PMPresponse>";
                } else if (i == 3) {

                    xmlout = header
                              + makePMP(date, identifier, requestURI, verb)
                            + "<error code=\"idDoesNotExist\" >" + e.toString()
                            + "</error>" + "</PMPresponse>";
                    out.println(xmlout);
                    out.close();

                } else {
                    // can be different MySql problem than 1062 
                    xmlout = header
                            + makePMP(date, identifier, requestURI, verb)
                            + "<error code=\"cannotPutFormat\" >"
                            + e.toString() + "</error>" + "</PMPresponse>";
                }
                out.println(xmlout);
                out.close();
            } catch (IllegalArgumentException e) {

                // can be moved to PMP hood with error code.

                identifier = rw.getId();
                PrintWriter out = resp.getWriter();

                xmlout = header + makePMP(date, identifier, requestURI, verb)
                        + "<error code=\"cannotPutFormat\" >" + e.toString()
                        + "</error>" + "</PMPresponse>";
                out.println(xmlout);
                out.close();
            }

            catch (Exception e) {
                // runtime exception: will see if we need server falue or PMP response.
                // see example in doGet();

                identifier = rw.getId();
                PrintWriter out = resp.getWriter();
                xmlout = header + makePMP(date, identifier, requestURI, verb)
                        + "<error code=\"cannotPutFormat\" >" + e.toString()
                        + "</error>" + "</PMPresponse>";
                out.println(xmlout);
                out.close();
            }
        }

    }

    public String makePMP(String date, String identifier, String requestURI,
            String verb) {
        String pmp = "<responseDate>" + date + "</responseDate>"
                + "<request verb=\"" + verb + "\"  identifier=\"" + identifier
                + "\">" + requestURI + "</request>";

        return pmp;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String protocol = req.getRequestURI();
            if (protocol.contains("PutRecordHandler")) {
                doPost(req, resp);
            }
            resp.setContentType("text/xml");
            attributes.put("OAIHandler.catalog", a);
            serverVerbs = ServerVerb.getVerbs(properties);
            String result = OAIHandler.getResult(attributes, req, resp, null,
                    serverVerbs, null, "");
            Writer out = OAIHandler.getWriter(req, resp);
            out.write(result);
            out.close();
        } catch (Throwable e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e
                    .getMessage());
        }

    }

    private String removeXMLHeader(String xml) {
        int j = xml.indexOf(">");
        xml = xml.substring(j + 1);
        return xml;
    }

    private void logProperties(Properties prop) {
        for (Enumeration enumprop = prop.keys(); enumprop.hasMoreElements();) {
            String key = (String) (enumprop.nextElement());
            String value = (String) (prop.get(key));
            log.debug(key + "=" + value);
        }
    }
}
