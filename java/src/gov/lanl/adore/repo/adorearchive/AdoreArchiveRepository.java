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
import gov.lanl.adore.repo.RepoConstants;
import gov.lanl.adore.repo.RepoException;
import gov.lanl.adore.repo.RepoType;
import gov.lanl.arc.ARCProperties;
import gov.lanl.archive.ArchiveCollection;
import gov.lanl.archive.ArchiveConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

public class AdoreArchiveRepository implements RepoType, RepoConstants {

    static Logger log = Logger.getLogger(AdoreArchiveRepository.class.getName());
    
    private ArchiveConfig archiveConfig = null;

    private AdoreArchiveRepoRegister repoRegister = null;
    
    private LinkedHashMap<String, Boolean> status = new LinkedHashMap<String, Boolean>();

    /**
     * Gets status of processed collections
     */
    public LinkedHashMap<String, Boolean> getStatus() {
        return status;
    }

    /**
     * Provided an Arc file object, will register file in service registry
     * and id locator for use in Adore Repository
     * @param c
     *            a repository successfully registered using AdoreArchive class
     * @return
     *        boolean indicating success or failure
     */
    public boolean processArcCollection(ArchiveCollection c) {
        String arcId = c.getCollectionRawId();
        
        // Add to Service Registry
        try {
            log.debug("Publishing " + c.getCollectionUri() + " to Service Registry");
            repoRegister.addArcToServiceRegistry(c);
        } catch (Exception e) {
            log.error(e.getMessage());
            status.put(c.getCollectionUri(), false);
            return false;
        }

        // Add to ID Locator
        try {
            String resolverUrl = archiveConfig.getARCResolverURL() + "/" + arcId + "/openurl-aDORe3";
            String repouri = ARCProperties.getLocalArcFilePrefix() + arcId;
            log.debug("Publishing ids for " + repouri + " to " + resolverUrl);
            repoRegister.publishIdentifiers(resolverUrl, repouri);
        } catch (Exception e) {
            log.error(e.getMessage());
            updateStatus(c.getCollectionUri(), false);
            return false;
        }

        return true;
    }
    
    /**
     * Takes the provided list of ArchiveCollection objects and
     * registers them with the Service Registry & ID Locator
     * @param collections
     *        Repository successfully registered using AdoreArchive class
     * @throws RepoException 
     */
    public void processCollections(ArrayList<ArchiveCollection> collections) throws RepoException {
        if (archiveConfig == null || repoRegister == null)
           throw new RepoException("RepoConfig has not been set, init using setRepoConfig()."); 
        
        int i = archiveConfig.getSystemThreadCount();
        if (i > 4)
            i = 4;
        ExecutorService executor =  Executors.newFixedThreadPool(i);
        ArrayList<FutureTask> tasks = new ArrayList<FutureTask>();
        for (ArchiveCollection c : collections) {
            FutureTask<String> future = new FutureTask<String>(new AdoreArchiveRepositoryThread(c), null);
            tasks.add(future);
            executor.execute(future);
        }
        
        try {
            executor.shutdown();
            while(!isDone(tasks)) {
              Thread.sleep(1000);
            }
          } catch (InterruptedException e) {
              log.error("An error occurred during thread interruption: " + e.getMessage());
          }
    }

    private static boolean isDone(ArrayList<FutureTask> list) {
        for (FutureTask ft : list) {
           if (!ft.isDone()) 
               return false;
        }
        return true;
    }
    
    public boolean processIdLocator(ArchiveCollection c) {
        String id = c.getCollectionRawId();
        String urlPrefix;
        if (c.getCollectionType().equals(ArchiveCollection.TYPE_ARC)) {
            urlPrefix = archiveConfig.getARCResolverURL();
        } else {
            urlPrefix = archiveConfig.getTapeResolverURL();
        }
        
        // Add to ID Locator
        try {
            String baseurl = urlPrefix + "/" + id + "/openurl-aDORe3";
            String repouri = c.getCollectionUri();
            log.debug("Publishing ids for " + repouri + " to " + baseurl);
            repoRegister.publishIdentifiers(baseurl, repouri);
        } catch (Exception e) {
            log.error(e.getMessage());
            updateStatus(c.getCollectionUri(), false);
            return false;
        }
        
        return true;
    }
    
    /**
     * Provided an XMLtape file object, will register file in service registry
     * and id locator for use in Adore Repository
     * @param c
     *            a repository successfully registered using AdoreArchive class
     * @return
     *        boolean indicating success or failure
     */
    public boolean processXMLtapeCollection(ArchiveCollection c) {
        String tapeId = c.getCollectionRawId();
        
        // Add to Service Registry
        try {
            log.debug("Publishing " + c.getCollectionUri() + " to Service Registry");
            repoRegister.addTapeToServiceRegistry(c);
        } catch (Exception e) {
            log.error(e.getMessage());
            updateStatus(c.getCollectionUri(), false);
            return false;
        }

        // Add to ID Locator
        try {
            String baseurl = archiveConfig.getTapeResolverURL() + "/" + tapeId + "/openurl-aDORe3";
            String repouri = c.getCollectionUri();
            log.debug("Publishing ids for " + repouri + " to " + baseurl);
            repoRegister.publishIdentifiers(baseurl, repouri);
        } catch (Exception e) {
            log.error(e.getMessage());
            updateStatus(c.getCollectionUri(), false);
            return false;
        }

        return true;
    }

    /**
     * Sets RepoConfig and initializes AdoreArchiveRepoRegister & ArchiveConfig instances
     */
    public void setRepoConfig(RepoConfig config) {
        try {
            // Set Adore Archive Props
            this.archiveConfig = new ArchiveConfig(config.getProperties());
            
            // Initialize the Repository Register
            this.repoRegister = new AdoreArchiveRepoRegister(config);
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    private synchronized void updateStatus(String collectionUri, boolean status) {
        this.status.put(collectionUri, status);
    }
    
    class AdoreArchiveRepositoryThread implements Runnable {
        ArchiveCollection c;
        public AdoreArchiveRepositoryThread(ArchiveCollection c) {
            this.c = c;
        }
        
        public void run() {
            boolean ok = false;
            if (c.getCollectionType().equals(ArchiveCollection.TYPE_TAPE)) {
                ok = processXMLtapeCollection(c);
            } else if (c.getCollectionType().equals(ArchiveCollection.TYPE_ARC)){
                ok = processArcCollection(c);
            }
            if (ok) {
                log.info(c.getCollectionUri() + " was processed successfully.");
                updateStatus(c.getCollectionUri(), true);
            }
        }
    }
}
