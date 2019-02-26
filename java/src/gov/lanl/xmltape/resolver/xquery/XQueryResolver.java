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

import gov.lanl.util.sru.SRUSearchRetrieveResponse;
import gov.lanl.util.xquery.XQueryProcessor;
import gov.lanl.util.xquery.XQueryProfile;
import gov.lanl.xmltape.registry.OAIRegistry;
import gov.lanl.xmltape.registry.RegistryException;
import gov.lanl.xmltape.registry.TapeConfig;
import gov.lanl.xmltape.resolver.xquery.sru.SRUXQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import ORG.oclc.openurl.ContextObject;

public class XQueryResolver {
    private OAIRegistry registry;

    private Logger log = Logger.getLogger(XQueryResolver.class.getName());

    private HashMap cachedQueryLists = new HashMap();
    
    private Properties props;
    
    private HashMap<String, XQueryProfile> profiles;
    
    private HashMap<String, XQueryResolverService> svcCache = new HashMap<String, XQueryResolverService>();
    
    private HashMap<String, String> svcImpl;

    public XQueryResolver() {
        init();
    }
    
    private void init() {
        try {
            props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("tape.properties"));
            
            // load registry
            if (props.getProperty("adore-xmltape-registry.OAIURL") != null) {
                registry = new OAIRegistry(props.getProperty("adore-xmltape-registry.OAIURL"));
                log.info(props.getProperty("adore-xmltape-registry.OAIURL"));
            } else {
                throw new Exception("cannot find adore-xmltape-registry.OAIURL");
            }
            
            // Initialize the list of services
            if (props.containsKey("adore-xmltape-xquery.svcDir")) {
                String svcDir = props.getProperty("adore-xmltape-xquery.svcDir");
                ServiceProfileUtil spu = new ServiceProfileUtil(svcDir);
                profiles = spu.getServiceProfileMap();
                svcImpl= spu.getServiceImplMap();
            } else 
                throw new Exception("adore-xmltape-xquery.svcDir is not defined.");
        } catch (Exception ex) {
            log.error(" XQueryResolver init failed:" + ex.getMessage());
        }
    }

    public byte[] getResultsAsList(String referent, XQueryProfile profile) throws ResolverException {
        String id = referent;

        try {
            // Get Tape information from Tape Registy
            TapeConfig config = registry.getTapeConfig(id);
            
            // Get tape path from file uri
            String tape = cleanPath(config.getXMLTapeFile());
            log.debug("Tape: " + tape);
            String key = tape + '|' + profile.getProfileId() + '|' + "list";

            byte[] bytes = null;
            try {
                if (cachedQueryLists.get(key) != null
                        && ((File) cachedQueryLists.get(key)).exists()
                        && ((File) cachedQueryLists.get(key)).length() > 0) {
                    File tmp = (File) cachedQueryLists.get(key);
                    bytes = ServiceProfileUtil.getBytesFromFile(tmp);
                } else {
                    XQueryProcessor xqp = new XQueryProcessor(profile);
                    long s = System.currentTimeMillis();
                    xqp.processXMLtape(tape);
                    bytes = getBytes(xqp.getResultsList());
                    
                    log.debug("XQuery Time for " + key + ": "
                            + ((System.currentTimeMillis() - s) / 1000));

                    File tmp = File.createTempFile("xqueryCache", null);
                    tmp.deleteOnExit();
                    FileOutputStream fos = new FileOutputStream(tmp);
                    fos.write(bytes);
                    cachedQueryLists.put(key, tmp);
                }
            } catch (Exception e) {
                log.error("***** Error Processing XQuery: " + tape + "\n ***** " + e.getMessage());
                log.debug(e);
            }
            
            return bytes;

        } catch (RegistryException e) {
            throw new ResolverException("Error attempting to obtain tape configuration: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new ResolverException("Error processing URI: " + e.getMessage());
        }
    }
    
    public byte[] getResultsAsRange(String referent, XQueryProfile profile) throws ResolverException {
        String id = referent;

        try {
            // Get Tape information from Tape Registy
            TapeConfig config = registry.getTapeConfig(id);
            
            // Get tape path from file uri
            String tape = cleanPath(config.getXMLTapeFile());
            log.debug("Tape: " + tape);
            String key = tape + '|' + profile.getProfileId() + '|' + "range";

            byte[] bytes = null;
            try {
                if (cachedQueryLists.get(key) != null
                        && ((File) cachedQueryLists.get(key)).exists()
                        && ((File) cachedQueryLists.get(key)).length() > 0) {
                    File tmp = (File) cachedQueryLists.get(key);
                    bytes = ServiceProfileUtil.getBytesFromFile(tmp);
                } else {
                    XQueryProcessor xqp = new XQueryProcessor(profile);
                    long s = System.currentTimeMillis();
                    xqp.processXMLtape(tape);
                    Set<String> result = xqp.getResultsList();
                    String output = Collections.min(result) + "-" + Collections.max(result);
                    bytes = output.getBytes();
                    
                    log.debug("XQuery Time for " + key + ": "
                            + ((System.currentTimeMillis() - s) / 1000));

                    File tmp = File.createTempFile("xqueryCache", null);
                    tmp.deleteOnExit();
                    FileOutputStream fos = new FileOutputStream(tmp);
                    fos.write(bytes);
                    cachedQueryLists.put(key, tmp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return bytes;

        } catch (RegistryException e) {
            throw new ResolverException("Error attempting to obtain tape configuration: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new ResolverException("Error processing URI: " + e.getMessage());
        }
    }
    
    public byte[] getResultsAsSRU(String referent, XQueryProfile profile) throws ResolverException {
        String id = referent;

        try {
            // Get Tape information from Tape Registy
            TapeConfig config = registry.getTapeConfig(id);
            
            // Get tape path from file uri
            String tape = cleanPath(config.getXMLTapeFile());
            log.debug("Tape: " + tape);
            String key = tape + '|' + profile.getProfileId() + '|' + "sru";

            byte[] bytes = null;
            try {
                if (cachedQueryLists.get(key) != null
                        && ((File) cachedQueryLists.get(key)).exists()
                        && ((File) cachedQueryLists.get(key)).length() > 0) {
                    File tmp = (File) cachedQueryLists.get(key);
                    bytes = ServiceProfileUtil.getBytesFromFile(tmp);
                } else {
                    XQueryProcessor xqp = new XQueryProcessor(profile);
                    long s = System.currentTimeMillis();
                    xqp.processXMLtape(tape);
                    // Compose SRU Response
                    SRUSearchRetrieveResponse srr = new SRUSearchRetrieveResponse();
                    srr.setSearchRequest(profile.getProfileId() + '.' + "sru", id);
                    for (String k : xqp.getResults().keySet()) {
                        SRUXQuery xq = new SRUXQuery();
                        xq.addKey(SRUXQuery.Key.IDENTIFIER.getField(), k);
                        xq.addKey(SRUXQuery.Key.EXTENT.getField(), xqp.getResults().get(k).toString());
                        srr.addRecord(xq);
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    SRUSearchRetrieveResponse.write(baos, srr);
                    bytes = baos.toByteArray();
                    
                    log.debug("XQuery Time for " + key + ": "
                            + ((System.currentTimeMillis() - s) / 1000));

                    File tmp = File.createTempFile("xqueryCache", null);
                    tmp.deleteOnExit();
                    FileOutputStream fos = new FileOutputStream(tmp);
                    fos.write(bytes);
                    cachedQueryLists.put(key, tmp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return bytes;

        } catch (RegistryException e) {
            throw new ResolverException("Error attempting to obtain tape configuration: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new ResolverException("Error processing URI: " + e.getMessage());
        }
    }
    
    public XQueryResolverService getService(String svcId, ContextObject contextObject) {
        if (svcCache.containsKey(svcId))
            return svcCache.get(svcId);
        else {
            XQueryResolverService svc = null;
            try {
                XQueryProfile xqp = profiles.get(svcId);
                String impl = svcImpl.get(svcId).trim();
                Class c = Class.forName(impl);
                svc = (XQueryResolverService) c.getConstructor(ContextObject.class).newInstance(contextObject);
                svc.setProfile(xqp);
                svc.setResolver(this);
                svcCache.put(svcId, svc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return svc;
        }
    }
    
    private static String cleanPath(String uri) throws URISyntaxException {
        String path = new URI(uri).getPath();
        if (path.charAt(2) == ':')
            path = path.substring(1).replace("/", "\\");
        return path;
    }
    
    public static byte[] getBytes(Set<String> set) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] newline = "\n".getBytes();

        for (String v : set) {
            try {
                baos.write(v.getBytes());
                baos.write(newline);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null)
                        baos.close();
                } catch (Exception dbe) {
                }
            }
        }

        return baos.toByteArray();
    }
}
