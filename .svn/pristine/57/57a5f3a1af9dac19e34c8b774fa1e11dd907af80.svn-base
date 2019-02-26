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

package gov.lanl.adore.repo.adorearchive;

import gov.lanl.adore.repo.RepoConfig;
import gov.lanl.adore.repo.RepoException;
import gov.lanl.adore.repo.RepoRegister;
import gov.lanl.archive.ArchiveCollection;
import gov.lanl.archive.ArchiveConfig;
import gov.lanl.archive.ArchiveException;
import gov.lanl.ockham.client.adore.Add;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/** 
 * AdoreArchive Service Registry Implementation
 *
 */
public class AdoreArchiveRepoRegister extends RepoRegister{
    
    static Logger log = Logger.getLogger(AdoreArchiveRepoRegister.class.getName());
    private ArchiveConfig archiveConfig = null;

    /**
     * Creates a new AdoreArchiveRepoRegister using RepoConfig instance
     * 
     * @param _config
     *            properties object containing IDLocator and ArchiveConfig props
     * @throws RepoException
     */
    public AdoreArchiveRepoRegister(RepoConfig _config) throws RepoException {
        super(_config);
        
        // Get Properties as ArchiveConfig object
        try {
            archiveConfig = new ArchiveConfig(config.getProperties());
        } catch (ArchiveException e) {
            throw new RepoException(e);
        }
    }

    /**
     * Register xmltape repository in service registry
     * 
     * @param c
     *            a repository successfully registered using AdoreArchive class
     * @throws RepoException
     */
    public synchronized void addTapeToServiceRegistry(ArchiveCollection c) throws RepoException {
        try {
            URL handler = new URL(config.getServiceRegistryRecordHandlerURL());
            String oaipmh = archiveConfig.getTapeAccessorURL();
            String openurl = archiveConfig.getTapeResolverURL();
            String xqueryurl = archiveConfig.getTapeXQueryResolverURL();
            String prefix = c.getCollectionPrefix();
            
            // Init Service Registry Handler
            Add serviceReg = new Add(handler, oaipmh, openurl, xqueryurl);
            
            // Check to see if we need to index subject, format, semantics
            if (xqueryurl != null) {
                if (config.getCollectionSvcRegAddItemFormats(prefix)) {
                    TreeSet<String> formats = getValues(xqueryurl, c.getCollectionUri(), "info:lanl-repo/svc/formats.list");
                    formats.addAll(getValues(xqueryurl, c.getCollectionUri(), "info:lanl-repo/svc/mimetypes.list"));
                    serviceReg.setItemFormats(formats);
                }
                if (config.getCollectionSvcRegAddItemTypes(prefix)) 
                    serviceReg.setItemTypes(getValues(xqueryurl, c.getCollectionUri(), "info:lanl-repo/svc/semantics.list"));
                if (config.getCollectionSvcRegAddSubjects(prefix))
                    if (getValues(xqueryurl, c.getCollectionUri(), "info:lanl-repo/svc/provenanceLocal.list").size() > 0) {
                        TreeSet<String> s = new TreeSet<String>();
                        s.add("info:lanl-repo/provenance/LANL");
                        serviceReg.setSubjects(s);
                    }
                // MOD rtfteam@lanl.gov 20070619 support for temporalrange iesr flag
                if (config.getCollectionSvcRegAddTemporalRange(prefix)) 
                    serviceReg.setInlineTemporal(getValues(xqueryurl, c.getCollectionUri(), "info:lanl-repo/svc/temporal.range").toString());
            }
            
            // Check for additional properties (e.g. usesControlledList | temporal)
            String propsDir = config.getCollectionSvcRegPropDir(prefix);
            File addMetadata = (propsDir != null) ? new File(propsDir, c.getCollectionRawId() + ".properties") : null;
            if (addMetadata != null && addMetadata.exists())
                serviceReg.setAdditionalMetadata(addMetadata);
            else if (propsDir != null && new File(propsDir, "default.properties").exists())
                serviceReg.setAdditionalMetadata(new File(propsDir, "default.properties"));
            
            boolean success = serviceReg.put(c.getCollectionFullname(),
                                             c.getCollectionUri(),
                                             c.getCollectionCreated(),
                                             c.getCollectionExtent(),
                                             c.getCollectionAssociations());

            if (!success)
                throw new ArchiveException(
                        "Unable to add tape to service registry; service may be down or a duplciate entry may exist.");

        } catch (Exception ex) {
            throw new RepoException("Error in register tape file " + c.getCollectionUri() + ": " + ex.getMessage(), ex);
        }
    }

    /**
     * Register arcfile repository in service registry
     * 
     * @param c
     *            a repository successfully registered using AdoreArchive class
     * @throws RepoException
     */
    public synchronized void addArcToServiceRegistry(ArchiveCollection c)
            throws RepoException {
        try {
            URL handler = new URL(config.getServiceRegistryRecordHandlerURL());
            Add serviceReg = new Add(handler, null, archiveConfig.getARCResolverURL());
            
            boolean success = serviceReg.put(c.getCollectionFullname(),
                                             c.getCollectionUri(),
                                             c.getCollectionCreated(),
                                             c.getCollectionExtent(),
                                             c.getCollectionAssociations());

            if (!success)
                throw new RepoException(
                        "Unable to add arc to service registry; service may be down or a duplciate entry may exist.");

        } catch (Exception ex) {
            throw new RepoException("Error attempting to register arc file in service registry " + c.getCollectionUri() + ": " + ex.getMessage(), ex);
        }
    }

    /**
     * Register ArchiveCollection instance in service registry
     * 
     * @param c
     *            a repository successfully registered using AdoreArchive class
     * @throws RepoException
     */
    public synchronized void addToServiceRegistry(ArchiveCollection c) throws RepoException {
        if (c.getCollectionType().equals(ArchiveCollection.TYPE_ARC)) {
            this.addArcToServiceRegistry(c);
        } else if (c.getCollectionType().equals(ArchiveCollection.TYPE_TAPE)) {
            this.addTapeToServiceRegistry(c);
        }
    }
    
    public static final TreeSet<String> getValues(String baseurl, String rftId, String svcId) {
        TreeSet<String> values = new TreeSet<String>();
        String[] fragments = rftId.split("/");
        String e_repoUri = null;
        String e_svcId = null;
        try {
            e_repoUri = URLEncoder.encode(rftId, "UTF-8");
            e_svcId = URLEncoder.encode(svcId, "UTF-8"); 
        } catch (UnsupportedEncodingException e) {
           log.error("UnsupportedEncodingException resulted attempting to encode " + rftId);
        }
        
        String openurl = baseurl + "/" + fragments[2] + "/openurl-aDORe7"
                         + "?rft_id=" + e_repoUri
                         + "&svc_id=" + e_svcId
                         + "&url_ver=Z39.88-2004";

        log.info("Obtaining Content Values from: " + openurl);
        
        try {
            URL url = new URL(openurl);
            long s = System.currentTimeMillis();
            // Old: openStream can cause deadlock threads, which require process to be killed
            //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            
            // New: setting the timeouts ensures the client does not deadlock indefinitely
            // when the server has problems. Timeout is set to 30 minutes.
            URLConnection conn = url.openConnection();
            int timeoutMs = 1000 * 60 * 30; 
            conn.setConnectTimeout(timeoutMs);
            conn.setReadTimeout(timeoutMs);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            log.info("Query Time: " + (System.currentTimeMillis() - s) + "ms");
            String line;
            while ((line = in.readLine()) != null) {
                values.add(line);
            }
            in.close();
        } catch (Exception ex) {
            log.error("problem with openurl:" + openurl + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return values;
    }
}
