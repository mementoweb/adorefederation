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

package gov.lanl.adore.repo;

import gov.lanl.archive.AdoreArchive;
import gov.lanl.archive.ArchiveCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Wrapper class for AdoreRepository ingestion process.  Implements specified
 * processing plug-in to pre-process XMLtapes & ARCfiles.  Upon pre-processing
 * completion, a list of arcfiles and xmltapes are provided to this class for
 * indexing and registration is the adore archive enviroment.
 * <br>
 * Example: <br>
 *
 *         AdoreArchive archive = AdoreArchive.getAdoreArchive(args);
 *         archive.processSources();
 *         
 *         AdoreRepository repo = new AdoreRepository();
 *         repo.setRepoConfigurations(archive.getArchiveConfigurations().getProperties());
 *         repo.setRepoTypeProcessor(AdoreRepository.DEFAULT_FEDERATION_REPO_TYPE);
 *         repo.processCollections(archive.getRegisteredCollections());
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 *
 */
public class AdoreRepository implements RepoConstants {

    static Logger log = Logger.getLogger(AdoreRepository.class.getName());

    /**
     * Main Method - Parses command line args<br>
     * 
     * Expects the following args:<br>
     *   --config [archiveProp]<br>
     *        Path to archive.properties file [REQUIRED]<br>
     *   --profile [collectionPrefix]<br>
     *        Profile prefix as defined in archive properties file [REQUIRED]<br>
     *   --xmltape [xmlContentPath] or - for stdin <br>
     *        Path to dir/file to process [OPTIONAL]<br>
     *   --arcfile [arcFilePath]<br>
     *        Path to dir/file to process [OPTIONAL]<br>
     *   --recursive [OPTIONAL]<br>
     *        Recursively search directory to get list of --xml || --arcfile source files
     *   --createTape [OPTIONAL]
     *        Create tape from stdin 
     *   --stdInTapeName [OPTIONAL]
     *        Name of tape to be created
     * 
     * @param args
     *        String Array containing processing configurations
     */
    public static void main(String[] args) {
        if (args.length == 0)
            printUsage();

        try {
            // Initialize Processing Classes
            AdoreArchive archive = AdoreArchive.getAdoreArchive(args);
            archive.processSources();
            
            AdoreRepository repo = new AdoreRepository();
            repo.setRepoConfigurations(archive.getArchiveConfigurations().getProperties());
            repo.setRepoTypeProcessor(AdoreRepository.DEFAULT_FEDERATION_REPO_TYPE);
            repo.processCollections(archive.getRegisteredCollections());
            repo.printStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void printUsage() {
        System.out.println("gov.lanl.adore.repo.AdoreRepository " +
                "--config [archiveProp] " +
                "--profile [collectionPrefix] " +
                "--xmltape <xmlContentPath> or - for stdin " +
                "--arcfile <arcFilePath> " +
                "--createTape - create tape from stdin " +
                "--stdInTapeName - name of tape to be created ");
    }
    
    /** Default Ingestion Implementation (e.g. gov.lanl.adore.repo.adorearchive.AdoreArchiveRepository) */
    public final static String DEFAULT_FEDERATION_REPO_TYPE = "gov.lanl.adore.repo.adorearchive.AdoreArchiveRepository";
    
    private RepoType processor = null;

    private RepoConfig repoConfig = null;

    private LinkedHashMap<String, Boolean> status = new LinkedHashMap<String, Boolean>();
    
    /**
     * Gets the current Archive Configuration object; use getProperties() on
     * returned object to get all properties defined in source archive.properties.
     * @return
     *       Configuration object containing env and profile properties.
     */
    public RepoConfig getRepoConfigurations() {
        return repoConfig;
    }
    
    /**
     * Prints processing status
     */
    private void printStatus() {
        if (status.size() > 0) {
            System.out.println("\nStatus:");
            for (String key : status.keySet()) {
                System.out.println(key + ": " + ((status.get(key)) ? "Success" : "Error"));
            }
        } else {
            System.out.println("Unable to register repositories in the Service Registry or ID Locator due to an error.");
        }
    }

    /**
     * Takes the provided list of ArchiveCollection objects and processing implementation & 
     * registers them with the Service Registry & ID Locator
     * @param collections
     *        Repository successfully registered using AdoreArchive class
     */
    public void processCollections(ArrayList<ArchiveCollection> collections) throws RepoException {
        if (processor == null)
            setRepoTypeProcessor(DEFAULT_FEDERATION_REPO_TYPE);
        
        processor.processCollections(collections);
        status = processor.getStatus();
    }

    /**
     * Sets the Repository Configuration object. 
     * @param props
     *         adore properties object
     * @throws RepoException 
     */
    public void setRepoConfigurations(Properties props) throws RepoException {
        try {
            repoConfig = new RepoConfig(props);
        } catch (Exception e) {
            throw new RepoException(e);
        }
    }

    /**
     * Sets the Repository Configuration object. 
     * @param repoConfigFile
     *         Path to adore.properties file
     */
    public void setRepoConfigurations(String repoConfigFile)
            throws RepoException {
        File cfile = new File(repoConfigFile);
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(cfile));
            this.setRepoConfigurations(props);
        } catch (FileNotFoundException e) {
            throw new RepoException("Adore Properties File was not found at "
                    + repoConfigFile);
        } catch (IOException e) {
            throw new RepoException("Error reading file from " + repoConfigFile);
        } catch (Exception e) {
            throw new RepoException(e);
        }
    }

    /**
     * Sets the Repository Type Processor
     * @param repoType
     *        RepoType implementation
     */
    public void setRepoTypeProcessor(RepoType repoType) {
        this.processor = repoType;
        if (repoConfig != null)
            this.processor.setRepoConfig(repoConfig);
    }

    /**
     * Sets the Repository Type Processor
     * @param classpath
     *        String classpath of implementation 
     *        (e.g. gov.lanl.adore.repo.adorearchive.AdoreArchiveRepository)
     */
    public void setRepoTypeProcessor(String classpath) throws RepoException {
        try {
            setRepoTypeProcessor((RepoType) Class.forName(classpath).newInstance());
        } catch (InstantiationException e) {
            throw new RepoException(e);
        } catch (IllegalAccessException e) {
            throw new RepoException(e);
        } catch (ClassNotFoundException e) {
            throw new RepoException(e);
        }
    }
}
