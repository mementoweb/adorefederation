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

package gov.lanl.xmltape.identifier.index.bdbImpl;

import com.sleepycat.je.*;

import gov.lanl.identifier.IndexException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class BDBEnv {

    protected static final String IDENTIFIER_DATABASE = "identifier";
    protected static final String ID_BY_IDENTIFIER_DATABASE = "identifier_by_record";
    protected static Logger log = Logger.getLogger(BDBEnv.class.getName());
    protected Environment dbEnvironment;
    protected Database idDb = null;
    protected Database oaiDb = null;
    protected String dbDir;
    
    public BDBEnv(String dbDir, boolean readOnly) {
        openEnv(dbDir, readOnly);
        openDatabases(readOnly);
    }

    private void openEnv(String dbDir, boolean readOnly) {
        try {
            if (!readOnly && !System.getProperty("os.name").contains("Win") && new File(dbDir).exists()) {
            	try {
					Runtime.getRuntime().exec("chmod -R 755 " + dbDir).waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	log.debug("Changed permissions for 755 for: " + dbDir);
            }
        	this.dbDir = dbDir;
			File dir = new File(dbDir);
			if (!readOnly) {
				if (!dir.exists()) {
					if (readOnly) { // Don't create database if read-only
						ErrorHandler.error("Database directory " + dbDir
								+ " does not exist;\nit will not be created"
								+ " because the read-only flag is set");
					}
					if (!dir.mkdirs()) {
						ErrorHandler.error("Database directory " + dbDir
								+ " does not exist;\nan attempt to create"
								+ " it failed");
					}
				}
			}
			long s = System.currentTimeMillis();
			EnvironmentConfig config = new EnvironmentConfig();
			config.setAllowCreate(!readOnly);
			config.setTransactional(!readOnly);
			config.setReadOnly(readOnly);
			config.setConfigParam("java.util.logging.FileHandler.on", "false");
			//config.setConfigParam("java.util.logging.ConsoleHandler.on", "true");
			config.setConfigParam("java.util.logging.DbLogHandler.on", "false");
			if (readOnly) {
				config.setConfigParam("je.env.runEvictor", "false");
				config.setConfigParam("je.env.runINCompressor", "false");
				config.setConfigParam("je.env.runCheckpointer", "false");
				config.setConfigParam("je.env.runCleaner", "false");
				config.setConfigParam("je.log.checksumRead", "false");
				config.setLocking(false);
			} else {
				config.setConfigParam("je.cleaner.minUtilization", "90");
			}
			dbEnvironment = new Environment(dir, config);
			log.info("openEnv(): " +  dbDir + " | "  + (System.currentTimeMillis() - s));
		} catch (DatabaseException dbe) {
			ErrorHandler.error("openEnvironment", dbe);
			dbe.printStackTrace();
		} catch (IOException e) {
			ErrorHandler.error("openEnvironment", e);
			e.printStackTrace();
		}
    }

    protected void openDatabases(boolean readOnly) {
		long s = System.currentTimeMillis();
		if (idDb == null) {
            idDb = openDatabase(readOnly, IDENTIFIER_DATABASE);
		    log.debug("openDatabases(): idDb "  + (System.currentTimeMillis() - s));
		    s = System.currentTimeMillis();
		}
	
		if (oaiDb == null) {
			String oaiDbName;
			if (dbDir.contains(".idx"))
			     oaiDbName = dbDir.substring(dbDir.lastIndexOf("/")+1, dbDir.lastIndexOf("."));
			else
				 oaiDbName = dbDir.substring(dbDir.lastIndexOf("/")+1);
            oaiDb = openDatabase(readOnly, oaiDbName);
		    log.debug("openDatabases(): oaiDb "  + (System.currentTimeMillis() - s));
		}
        
        // BDB Stats
        //StatsConfig config = new StatsConfig();
        //config.setClear(true);
        //try {
		//	System.err.println(dbEnvironment.getStats(config));
		//} catch (DatabaseException e) {
		//	e.printStackTrace();
		//}
    }

    private Database openDatabase(boolean readOnly, String databaseName) {
        DatabaseConfig config = new DatabaseConfig();
        config.setAllowCreate(!readOnly);
        config.setTransactional(!readOnly);
        config.setReadOnly(readOnly);
        config.setSortedDuplicates(false);
        Database db = null;
        try {
        // If readOnly is false, then the database config specifies that we
        // can use transactions. When opening the database, the first argument
        // is an optional transaction. If that argument is null and readOnly
        // is false (transactional is true), then autocommit is in effect for
        // this operation.
        db = dbEnvironment.openDatabase(null, databaseName, config);
        }
        catch (DatabaseException dbe) {
        ErrorHandler.error("openDatabase", dbe, false);
        dbe.printStackTrace();
        }
        return db;
    }

    private SecondaryDatabase openSecondaryDatabase(boolean readOnly,
                            String indexName,
                            Database primaryDb,
                            SecondaryKeyCreator kc)
    {
        SecondaryConfig config = new SecondaryConfig();
        config.setAllowCreate(!readOnly);
        config.setTransactional(!readOnly);
        config.setAllowPopulate(!readOnly);
        config.setReadOnly(readOnly);
        config.setKeyCreator(kc);
        // We need to allow duplicates for all our secondary databases
        config.setSortedDuplicates(true);

        SecondaryDatabase db = null;
        try {
        db = dbEnvironment.openSecondaryDatabase(null, indexName, primaryDb, config);
        }
        catch (DatabaseException dbe) {
        ErrorHandler.error("openSecondaryDatabase", dbe, false);
        }
        return db;
    }
                               
    public Environment getEnv() { return dbEnvironment; }
    public Database getIdentifierDb() { return idDb; }
    public Database getOAIDb() { return oaiDb; }
    
    public void closeDatabases() throws IndexException {
        try {
        	if (oaiDb != null)
        	    oaiDb.close();
        	if (idDb != null)
        	    idDb.close();
            oaiDb = null;
            idDb = null;
        } catch (DatabaseException ex) {
        	ErrorHandler.error("closeDatabases", ex, false);
        	ex.printStackTrace();
        }
    }
    
    public void shutDown() throws IndexException {
        try {
        	boolean readOnly = dbEnvironment.getConfig().getReadOnly();
        	closeDatabases();
        	if (!readOnly) {
        		boolean changes = false;
     	        while (dbEnvironment.cleanLog() > 0) {changes = true;}
     	        if (changes) {
					CheckpointConfig force = new CheckpointConfig();
					force.setForce(true);
					dbEnvironment.checkpoint(force);
				}
        	}
            dbEnvironment.close();
            dbEnvironment = null;
            if (!readOnly && !System.getProperty("os.name").contains("Win")) {
            	Runtime.getRuntime().exec("rm -f " + dbDir + "/je.lck");
            	Runtime.getRuntime().exec("chmod -R 555 " + dbDir);
            }
        } catch (DatabaseException ex) {
        	ErrorHandler.error("shutDown", ex, false);
        	ex.printStackTrace();
            //throw new IndexException("identifier db close error", ex);
        } catch (IOException e) {
        	ErrorHandler.error("shutDown", e, false);
			e.printStackTrace();
		}
    }
}
