/*
 * Copyright (c) 2009 Los Alamos National Security, LLC.
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

import gov.lanl.adore.repo.RepoConfig;
import gov.lanl.adore.repo.adorearchive.AdoreArchiveRepository;
import gov.lanl.archive.ArchiveCollection;
import gov.lanl.archive.ArchiveConfig;
import gov.lanl.archive.ArchiveProfile;
import gov.lanl.locator.DbCleaner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AdoreFederatedIDLoader {
    static Logger log = Logger.getLogger(AdoreFederatedIDLoader.class.getName());
    private ArchiveConfig archiveConfig;
    private RepoConfig repoConfig;
    private ArrayList<String> tapes = new ArrayList<String>();
    private ArrayList<String> arcs = new ArrayList<String>();
    private String profile;
    private ArchiveProfile archiveProfile;
    private AdoreArchiveRepository repoProcessor;
    private DbCleaner dbCleaner;

    public AdoreFederatedIDLoader(Properties props) throws Exception {
        try {
            this.archiveConfig = new ArchiveConfig(props);
            this.repoConfig = new RepoConfig(props);
            this.repoProcessor = new AdoreArchiveRepository();
            if (repoConfig != null)
                this.repoProcessor.setRepoConfig(repoConfig);
            Properties idLoc = new Properties();
            idLoc.load(new FileInputStream(repoConfig.getIdLocatorDbProps()));
            this.dbCleaner = new DbCleaner(idLoc);
        } catch (Exception e) {
        	e.printStackTrace();
            throw e;
        }
    }

    public void setArchiveProfile(String profilePrefix) throws Exception {
        archiveProfile = new ArchiveProfile(profilePrefix); 
        // Set the Mandatory Properties, use default if missing
        String fullName = archiveConfig.getCollectionFullName(profilePrefix);
        if (fullName == null || fullName.trim().equals("")) {
            log.warn(profilePrefix + ".FullName is not defined; using default");
            fullName = archiveConfig.getCollectionFullName("default");
            if (fullName == null || fullName.trim().equals(""))
                throw new Exception("default.FullName is not defined");
        }
        String pmhDefaultProp = archiveConfig.getCollection_pmh_defaultproperty(profilePrefix);
        if (pmhDefaultProp == null || pmhDefaultProp.trim().equals("")) {
            log.warn(profilePrefix + ".pmh-defaultproperty is not defined; using default");
            pmhDefaultProp = archiveConfig.getCollection_pmh_defaultproperty("default");
            if (pmhDefaultProp == null || pmhDefaultProp.trim().equals(""))
                throw new Exception("default.pmh-defaultproperty is not defined");
        }
    }
    
    private static void printUsage() {
        System.out.println("Usage: " +
                "--config [archiveProp] " +
                "--profile [collectionPrefix] " +
                "--xml <tape id> " +
                "--arc <arc id> ");
    }
    
    public static void main(String[]  args) {
        Hashtable parahash = new Hashtable();
        AdoreFederatedIDLoader c = null;
        String config = null, profile = null, tape = null, arc = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(0);
            } else if (arg.startsWith("--wait")) {
    			try {
    				// This is a test
    				Thread.sleep(2000);
    			} catch (InterruptedException e1) {
    				e1.printStackTrace();
    			}
            }
            else if (arg.startsWith("--"))
                parahash.put(arg.substring(2), args[++i]);
        }
        if (parahash.get("config") != null)
            config = (String) (parahash.get("config"));
        if (parahash.get("profile") != null)
            profile = (String) parahash.get("profile");
        if (parahash.get("xml") != null)
            tape = (String) parahash.get("xml");
        if (parahash.get("arc") != null)
            arc = (String) parahash.get("arc");
        
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(new File(config)));
            c = new AdoreFederatedIDLoader(p);
            c.setRecoveryProfile(profile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (tape != null) {
        	ArrayList<String> xmlList = null;
            if (tape.contains(".list")) {
            	xmlList = getRepoList(tape);
            } else {
            	xmlList = new ArrayList<String>();
            	xmlList.add(tape);
            }            	
            c.setTapes(xmlList);
        } 
        
        if (arc != null) {
        	ArrayList<String> arcList = null;
            if (arc.contains(".list")) {
            	arcList = getRepoList(arc);
            } else {
            	arcList = new ArrayList<String>();
            	arcList.add(arc);
            }            	
            c.setArcs(arcList);
        }
        c.loadIds();
    }
    
    private static ArrayList<String> getRepoList(String file) {
    	ArrayList<String> ids = new ArrayList<String>();
    	BufferedReader list;
		try {
			list = new BufferedReader(new FileReader(file));
	    	for (int line = 0; true; line++) {
				String row = list.readLine();
				if (row == null)
					break;
				ids.add(row);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ids;
    }
    
    public void setRecoveryProfile(String profile) throws Exception {
        this.profile = profile;
        setArchiveProfile(profile);
    }

    public void loadIds() {        
        for (String id : getTapes()) {
        	if (!dbCleaner.isValid(id)) {
				try {
					boolean status = loadIdsForTape(id);
					log.info("Loaded " + id + ": " + status);
				} catch (Exception e) {
					log.error("Error processing ids for: " + id);
				}
			}
        }
        for (String id : getArcs()) {
        	if (!dbCleaner.isValid(id)) {
				try {
					boolean status = loadIdsForArc(id);
					log.info("Loaded " + id + ": " + status);
				} catch (Exception e) {
					log.error("Error processing ids for: " + id);
				}
			}
        }
    }
    
    public boolean loadIdsForTape(String id) throws Exception {
        String xmlTape = id.substring(id.lastIndexOf('/')+ 1);
        
        log.info("Creating ArchiveCollection for: " + xmlTape);
        ArchiveCollection ac = new ArchiveCollection();
        ac.setCollectionType(ArchiveCollection.TYPE_TAPE);
        ac.setCollectionPrefix(profile);
        ac.setCollectionCreated(new Date());
        ac.setCollectionFullname(archiveProfile.getCollectionProfileFullName());
        ac.setCollectionRawId(xmlTape);
        ac.setCollectionUri(id);
        
        log.info("Registering " + ac.getCollectionUri() + " with ID Locator");
        repoProcessor.processIdLocator(ac);
        
        return true;
    }
    
    public boolean loadIdsForArc(String id) throws Exception {
        String arcFile = id.substring(id.lastIndexOf('/')+ 1);
        String collection = archiveProfile.getCollectionProfilePrefix();
        
        log.info("Creating ArchiveCollection for: " + arcFile);
        ArchiveCollection ac = new ArchiveCollection();
        ac.setCollectionType(ArchiveCollection.TYPE_ARC);
        ac.setCollectionPrefix(collection);
        ac.setCollectionCreated(new Date());
        ac.setCollectionFullname(archiveProfile.getCollectionProfileFullName());
        ac.setCollectionRawId(arcFile);
        ac.setCollectionUri(id);
        
        log.info("Registering " + ac.getCollectionUri() + " with ID Locator");
        repoProcessor.processIdLocator(ac);
        
        return true;
    }

    public ArrayList<String> getArcs() {
        return arcs;
    }

    public void setArcs(ArrayList<String> arcs) {
        this.arcs = arcs;
    }

    public ArrayList<String> getTapes() {
        return tapes;
    }

    public void setTapes(ArrayList<String> tapes) {
        this.tapes = tapes;
    }
}         