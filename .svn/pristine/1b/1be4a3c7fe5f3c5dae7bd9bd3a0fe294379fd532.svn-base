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

import gov.lanl.adore.helper.IdNotFoundException;
import gov.lanl.adore.repo.RepoConfig;
import gov.lanl.identifier.sru.SRUDC;
import gov.lanl.identifier.sru.SRUSearchRetrieveResponse;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.util.xml.XmlUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import ORG.oclc.openurl.ContextObject;
import ORG.oclc.openurl.OpenURLException;
import ORG.oclc.openurl.descriptors.Descriptor;

public class AdoreSysDiagService extends ORG.oclc.openurl.services.AbstractService {

    private static final Logger log = Logger.getLogger(AdoreSysDiagService.class.getName());
    private byte[] bytes;
    private String contentType;
    private int status;
    private static RepoConfig config;

    static {
        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("adore.properties"));
            config = new RepoConfig(props);
        } catch (Exception ex) {
            log.error(" AdoreSysDiagService init failed:" + ex.getMessage());
        }

    }
    
    /**
     * @param contextObject
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws OpenURLException
     */
    public AdoreSysDiagService(ContextObject contextObject)
            throws OpenURLException, java.io.IOException {
        super((Descriptor) null, contextObject);
        String referent = contextObject.getReferents()[0].getDescriptors()[0].getDataType().getValue();
        log.debug("referent: " + referent);
        testSys(referent);
    }

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

    public void testSys(String id) {
        try {
            log.info("referent=" + id);
            StringBuffer response = new StringBuffer();
            response.append("<html>");
            response.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"adore.css\"/>");
            
            response.append("<h2> aDORe System TraceRoute for " + id + "</h2>\n");
            
            // ID Locator Request
            String currentProcess = "IDLocator";
            AdoreProcess ap = AdoreSysDiag.getIdLocatorResponse(config.getIdLocatorResolverURL(), id);
            response.append("<h3>" + currentProcess + " Request</h3>\n");
            response.append("<p class=\"request\"><a target=\"_adore\" href=\"" + ap.getRequest() + "\">" + ap.getRequest() + "</a></p>");

            // Process ID Locator Response
            ByteArrayInputStream bais = new ByteArrayInputStream(ap.getResponse().getBytes());
            SRUSearchRetrieveResponse sru = SRUSearchRetrieveResponse.read(bais);
            String collection = null;
            String type = null;
            if (sru.getNumOfRecords() == 1) {
                collection = sru.getRecords().get(0).getKeys(SRUDC.Key.SOURCE).firstElement();
                if (collection.contains("/arc/"))
                    type = "arc";
                else
                    type = "xmltape";
            }
            else if (sru.getNumOfRecords() > 1) {
               for (SRUDC i : sru.getRecords()) {
                   for (String s : i.getKeys(SRUDC.Key.SOURCE)) {
                       if (s.contains("/arc/")) {
                           collection = s;
                           type = "arc";
                           break;
                       } else {
                           collection = s;
                           type = "xmltape";
                       }
                   }
               }
            } else {
                response.append("Problem: " + ap.getResponse());
                response.append("</html>");
                throw new IdNotFoundException(response.toString());
            }
            
            response.append(XMLFormatter.prettyPrintAsTextArea(currentProcess + " Response", ap.getResponse()));
            
            // Service Registry Collection Request
            currentProcess = "Service Registry Collection";
            ap = AdoreSysDiag.getServiceRegistryResponse(config.getServiceRegistryResolverURL(), collection);
            response.append("<h3>" + currentProcess + " Request</h3>\n");
            response.append("<p class=\"request\"><a target=\"_adore\" href=\"" + ap.getRequest() + "\">" + ap.getRequest() + "</a></p>");
            
            bais = new ByteArrayInputStream(ap.getResponse().getBytes());
            IESRCollection iesrCol = new IESRCollection();
            iesrCol.read(bais);
            response.append(XMLFormatter.prettyPrintAsTextArea(currentProcess + " Response", ap.getResponse()));  
            
            
            // Service Registry Service Reqeust
            currentProcess = "Service Registry Service";
            String serviceUrl = null;
            
            try {
                // get the service interfaces
                TreeSet<String> services = (TreeSet<String>) iesrCol.getServices();
                for (String service : services) {
                    IESRService serviceProfile = new IESRService();
                    ap = AdoreSysDiag.getServiceRegistryResponse(config.getServiceRegistryResolverURL(), service);
                    bais = new ByteArrayInputStream(ap.getResponse().getBytes());
                    serviceProfile.read(bais);
                    if (serviceProfile.getType().equals("openurl")
                            && (serviceProfile.getSupportsStandard().equals("openurl-aDORe1") 
                                    || serviceProfile.getSupportsStandard().equals("openurl-aDORe4"))) {
                        response.append("<h3>" + currentProcess + " Request</h3>\n");
                        response.append("<p class=\"request\"><a target=\"_adore\" href=\"" + ap.getRequest() + "\">" + ap.getRequest() + "</a></p>");
                        serviceUrl = serviceProfile.getLocator();
                        break;
                    }
                }
            } catch (Exception e) {
                response.append("Problem identifying service url: " + ap.getResponse());
                response.append("</html>");
                throw new IdNotFoundException(response.toString());
            }
            response.append(XMLFormatter.prettyPrintAsTextArea(currentProcess + " Response", ap.getResponse()));
            
            if (type.equals("arc")) {
                currentProcess = "ARCfile Resource";
                ap = AdoreSysDiag.getDatastream(serviceUrl, id);
                response.append("<h3>" + currentProcess + " Request</h3>\n");
                response.append("<p class=\"request\"><a target=\"_adore\" href=\"" + ap.getRequest() + "\">" + ap.getRequest() + "</a></p>");
                response.append("Click link above to test resource.");
            } else {
                currentProcess = "XMLTape Resource";
                ap = AdoreSysDiag.getRecord(serviceUrl, id);
                response.append("<h3>" + currentProcess + " Request</h3>\n");
                response.append("<p class=\"request\"><a target=\"_adore\" href=\"" + ap.getRequest() + "\">" + ap.getRequest() + "</a></p>");
                response.append(XMLFormatter.prettyPrintAsTextArea(currentProcess + " Response", ap.getResponse()));
            }
            
            
            response.append("</html>");
            contentType = "text/html";
            bytes = response.toString().getBytes();
            status = HttpServletResponse.SC_OK;
        } catch (IdNotFoundException e) {
            status = HttpServletResponse.SC_NOT_FOUND;
            bytes = e.getMessage().getBytes();
            contentType = "text/html";
        } catch (Exception e) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            log.info("from servlet:" + e.toString());
        }
    }
}
