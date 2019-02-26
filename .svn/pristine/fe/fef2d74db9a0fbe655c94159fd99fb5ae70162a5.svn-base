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

package gov.lanl.resource.filesystem.index.bdb;

import gov.lanl.resource.ResourceException;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.CheckpointConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class BDBEnv {

    protected static final String RESOURCE_DATABASE = "resource_record";
    protected static Logger log = Logger.getLogger(BDBEnv.class.getName());
    protected Environment env;
    protected Database recDb = null;
    protected String dbDir;
    protected static TupleBinding resourceBinding = new ResourceBinding();
    
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
			env = new Environment(dir, config);
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
		if (recDb == null) {
            recDb = openDatabase(readOnly, RESOURCE_DATABASE);
		    log.debug("openDatabases(): idDb "  + (System.currentTimeMillis() - s));
		    s = System.currentTimeMillis();
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
        db = env.openDatabase(null, databaseName, config);
        }
        catch (DatabaseException dbe) {
        ErrorHandler.error("openDatabase", dbe, false);
        dbe.printStackTrace();
        }
        return db;
    }
                               
    public Environment getEnv() { return env; }
    public Database getResourceRecordDb() { return recDb; }
    
    public void closeDatabases() throws ResourceException {
        try {
        	if (recDb != null)
        	    recDb.close();
            recDb = null;
        } catch (DatabaseException ex) {
        	ErrorHandler.error("closeDatabases", ex, false);
        	ex.printStackTrace();
        }
    }
    
    public void shutDown() throws ResourceException {
        try {
        	boolean readOnly = env.getConfig().getReadOnly();
        	closeDatabases();
        	if (!readOnly) {
        		boolean changes = false;
     	        while (env.cleanLog() > 0) {changes = true;}
     	        if (changes) {
					CheckpointConfig force = new CheckpointConfig();
					force.setForce(true);
					env.checkpoint(force);
				}
        	}
            env.close();
            env = null;
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
