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

package gov.lanl.xmltape.index.berkeleydbImpl;

import gov.lanl.xmltape.index.IndexException;
import gov.lanl.xmltape.index.IndexItem;
import gov.lanl.xmltape.index.TapeIndexInterface;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.BtreeStats;
import com.sleepycat.je.CheckpointConfig;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryCursor;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.util.DbVerify;

/**
 * Berkey DB implementation of xmltape index
 */

public class BDBIndex implements TapeIndexInterface {
    String envDir = null, dbname = null;

    String datestampIndexname = null;

    private Environment env = null;

    private Database indexdb = null;

    TupleBinding indexBinding = new IndexItemBinding();

    static Logger log = Logger.getLogger(BDBIndex.class.getName());

    /**
     * Field secondaryDbMap
     * Information about secondary db A secondary db is created for each
     * setSpec, in the secondarydb, the key is the identifier of a record
     * 
     * secondary db information is maintained
     */
    HashMap<String, SecondaryDatabase> secondaryDbMap = new HashMap<String, SecondaryDatabase>();

    public BDBIndex() {}
    
    public BDBIndex(Environment bdbEnv, String dbname) {
    	this.env = bdbEnv;
    	this.dbname = dbname;
    }
    
    /**
     * Constructor BDBIndex(String envDir, String dbname)
     * @param envDir - file system path to directory containing index
     * @param dbname - database name contained in the bdb instance
     */
    public BDBIndex(String envDir, String dbname) {
        this.setIndexDir(envDir);
        this.dbname = dbname;
        this.datestampIndexname = this.dbname + ".datestamp";
    }

    /**
     * Method open()
     * @param readonly
     *            enviroment and database readonly
     * 
     * in this application we assume the enviroment (directory) must be created
     * by user to avoid problems.
     * 
     * if readonly is true, everything is read only if readonly is false, new
     * database can be created and new records can be added.
     *  
     */
    public void open(boolean readonly) throws IndexException {
        try {
            if (!readonly && !System.getProperty("os.name").contains("Win") && new File(envDir).exists()) {
            	try {
					Runtime.getRuntime().exec("chmod -R 755 " + envDir).waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	log.debug("Changed permissions for 755 for: " + envDir);
            }
        	if (env == null) {
			    EnvironmentConfig envConfig = new EnvironmentConfig();
				envConfig.setTransactional(false);
				envConfig.setAllowCreate(!readonly);
				envConfig.setReadOnly(readonly);
				env = new Environment(new File(envDir), envConfig);
        	}
			openDatabases(readonly);

        } catch (DatabaseException ex) {
            throw new IndexException("db creation error", ex);
        } catch (IOException e) {
			e.printStackTrace();
			throw new IndexException("error changing dir permisions " + e.getMessage(), e);
		}
    }
    
    private void openDatabases(boolean readonly) throws IndexException {
        try {
            // Make a database within that environment
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setTransactional(false);
            dbConfig.setAllowCreate(!readonly);
            dbConfig.setReadOnly(readonly);
            dbConfig.setSortedDuplicates(false);
            indexdb = env.openDatabase(null, dbname, dbConfig);

            // We want to load all secondary database first.
            for (Iterator it = env.getDatabaseNames().iterator(); it.hasNext();) {
                String myname = (String) (it.next());
                if (myname.indexOf(dbname + ".") != -1) {
                    log.debug("secondary db: " + myname);
                    secondaryDbMap.put(myname, getSecondaryDB(myname, readonly));
                }
            }

            // create datestamp secondary db
            if ((!readonly) && (secondaryDbMap.get(datestampIndexname) == null)) {
                log.debug("no datestampIndex exists: ");
                secondaryDbMap.put(datestampIndexname, getSecondaryDB(
                        datestampIndexname, readonly));
                log.debug("create datestampIndex: " + datestampIndexname);
            }

        } catch (DatabaseException ex) {
            throw new IndexException("db creation error", ex);
        }
    }

    /**
     * Method close()
     * Closes all bdb connections
     */
    public void close() throws IndexException {
        try {
        	boolean readOnly = env.getConfig().getReadOnly();
        	for (Iterator it = secondaryDbMap.values().iterator(); it.hasNext();) {
                SecondaryDatabase sd = (SecondaryDatabase) (it.next());
                sd.close();
            }
        	if (!env.getConfig().getReadOnly()) {
        		boolean changes = false;
     	        while (env.cleanLog() > 0) {changes = true;}
     	        if (changes) {
					CheckpointConfig force = new CheckpointConfig();
					force.setForce(true);
					env.checkpoint(force);
				}
        	}
            indexdb.close();
            env.close();
            if (!readOnly && !System.getProperty("os.name").contains("Win")) {
            	Runtime.getRuntime().exec("rm -f " + envDir + "/je.lck");
            	Runtime.getRuntime().exec("chmod -R 555 " + envDir);
            }
        } catch (DatabaseException ex) {
            throw new IndexException("db close error: " + ex.getMessage(), ex);
        } catch (IOException e) {
			e.printStackTrace();
			throw new IndexException("error changing dir permisions " + e.getMessage(), e);
		}
    }

    /**
     * Method getIndexItem(String identifier)
     * @param identifier - unique record id
     * @return IndexItem object matching specified id
     */
    public synchronized IndexItem getIndexItem(String identifier)
            throws IndexException {
        IndexItem item = null;
        DatabaseEntry theKey = new DatabaseEntry(identifier.getBytes());
        DatabaseEntry theData = new DatabaseEntry();
        // Get it
        try {
            OperationStatus retVal = indexdb.get(null, theKey, theData, null);
            if (retVal == OperationStatus.SUCCESS) {
                item = (IndexItem) indexBinding.entryToObject(theData);
            }

        } catch (DatabaseException ex) {
            throw new IndexException("query error", ex);
        }
        return item;
    }

    /**
     * Method putIndexItem(IndexItem item)
     * @param item - IndexItem to be added to current bdb instance
     */
    public synchronized void putIndexItem(IndexItem item) throws IndexException {

        /*
         * check if there exists an seconddb for sets in this item, if not,
         * create a new secondarydb.
         */
        try {
            if ((item.getSetSpecs() != null)
                    && (!secondaryDbMap.keySet()
                            .containsAll(item.getSetSpecs()))) {
                for (Iterator it = item.getSetSpecs().iterator(); it.hasNext();) {
                    String setSpec = (String) (it.next());
                    String iname = setSpec2indexname(setSpec);
                    if (!secondaryDbMap.keySet().contains(iname)) {
                        secondaryDbMap.put(iname, getSecondaryDB(iname, false));
                    }
                }

            }

            DatabaseEntry theKey = new DatabaseEntry(item.getIdentifier()
                    .getBytes());
            DatabaseEntry theData = new DatabaseEntry();
            indexBinding.objectToEntry(item, theData);
            OperationStatus retVal = indexdb.put(null, theKey, theData);
            if (retVal != OperationStatus.SUCCESS) {
                throw new IndexException(item.getIdentifier()
                        + " cannot be put");
            }
        } catch (DatabaseException ex) {
            throw new IndexException("put record error", ex);
        }

    }


    /**
     * Method getDatestampIndex()
     * Iterates through secondaryDbMap to verify existence of datestamp index
     * @return true is datestampIndex exists
     */
    public boolean getDatestampIndex() {
        for (Iterator it = secondaryDbMap.keySet().iterator(); it.hasNext();) {
            String indexname = (String) (it.next());
            if (indexname.equals(datestampIndexname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method getOAISetSpecs()
     * Read OAI SetsSpec, in this case it's the same as secondary database
     * names;
     */
    public List getOAISetSpecs() {
        ArrayList<String> al = new ArrayList<String>();
        for (Iterator it = secondaryDbMap.keySet().iterator(); it.hasNext();) {

            String indexname = (String) (it.next());
            if (!indexname.equals(datestampIndexname)) {
                String setSpec = indexname2setSpec(indexname);
                al.add(setSpec);
            }
        }
        return al;
    }

    /**
     * Method read(String identifier, int count)
     * Read from primary database, without datestamp information.
     * @param identifier - unique record id
     * @param count - Number of IndexItems to return
     * @return Vector of IndexItems
     */
    public synchronized Vector read(String identifier, int count)
            throws IndexException {

        DatabaseEntry theKey = new DatabaseEntry();
        Vector<IndexItem> v = new Vector<IndexItem>();
        boolean isfinished = false;
        if (identifier != null)
            theKey = new DatabaseEntry(identifier.getBytes());
        DatabaseEntry theData = new DatabaseEntry();

        try {
            Cursor cursor = indexdb.openCursor(null, null);
            try {
                if (identifier == null) {
                    OperationStatus retVal = cursor.getFirst(theKey, theData,
                            null);
                    if (retVal == OperationStatus.NOTFOUND) {
                        isfinished = true;
                    } else {
                        v.add((IndexItem) indexBinding.entryToObject(theData));
                    }

                } else {
                    OperationStatus retVal = cursor.getSearchKey(theKey,
                            theData, null);
                    if (retVal == OperationStatus.NOTFOUND) {
                        throw new IndexException("cannot found " + identifier);
                    }
                }

                while ((!isfinished) && (v.size() < count)) {
                    OperationStatus retVal = cursor.getNext(theKey, theData,
                            null);
                    if (retVal == OperationStatus.SUCCESS) {
                        v.add((IndexItem) indexBinding.entryToObject(theData));
                    } else {
                        isfinished = true;
                    }
                }

            } catch (DatabaseException ex) {
                throw new IndexException("query error", ex);
            } finally {
                cursor.close();
            }
        } catch (DatabaseException ex) {
            throw new IndexException("cursor error", ex);
        }
        return v;

    }

    /**
     * Method read(String identifier, int count, 
     *               String setSpec, String from, String until)
     * Read from secondary database, when sets is used
     * 
     * @param identifier - start from an entry after this identifier
     * @param setSpec - OAI SetSpec
     * @param count - number of records to be read
     * @param from - UTC from date
     * @param until - UTC until date
     */
    public synchronized Vector read(String identifier, int count,
            String setSpec, String from, String until) throws IndexException {
        String iname = setSpec2indexname(setSpec);
        if (secondaryDbMap.get(iname) == null) {
            return new Vector();
        }

        SecondaryDatabase seconddb = (SecondaryDatabase) (secondaryDbMap
                .get(iname));
        return read(seconddb, identifier, count, from, until);
    }

    /**
     * Method read(String identifier, int count, 
     *               String from, String until)
     * Read from datestamp based database, without using OAI Set Spec
     */
    public synchronized Vector read(String identifier, int count, String from,
            String until) throws IndexException {
        log.debug("Map Size: " + secondaryDbMap.size());
        SecondaryDatabase seconddb = (SecondaryDatabase) (secondaryDbMap
                .get(datestampIndexname));
        return read(seconddb, identifier, count, from, until);
    }

    /**
     * Method delete(String id)
     * Delete specified identifier from the Index
     * @param id - identifier of IndexItem to be deleted from index
     */
    public void delete(String id) throws IndexException {
        try {
            IndexItem item = getIndexItem(id);
            Iterator it = item.getSetSpecs().iterator();
            if (it.hasNext()) {
                String setSpec = (String) (it.next());
                String iname = setSpec2indexname(setSpec);
                SecondaryDatabase db = (SecondaryDatabase) (secondaryDbMap
                        .get(iname));
                DatabaseEntry theKey = new DatabaseEntry(id.getBytes());
                db.delete(null, theKey);
            }
            DatabaseEntry theKey = new DatabaseEntry(id.getBytes());
            indexdb.delete(null, theKey);
        } catch (DatabaseException ex) {
            throw new IndexException("deletion error", ex);
        }
    }

    /**
     * Method count(String setSpec)
     * Returns the number of IndexItem records associated with the specified 
     * set name.  A null paramater will return the count for the entire db.
     * @return number of records in this database or set
     */
    public long count(String setSpec) throws IndexException {
        try {
            if (setSpec == null) {
                return ((BtreeStats) (indexdb.getStats(null)))
                        .getLeafNodeCount();
            } else {
                String indexname = setSpec2indexname(setSpec);
                SecondaryDatabase db = (SecondaryDatabase) (secondaryDbMap
                        .get(indexname));
                return ((BtreeStats) (db.getStats(null))).getLeafNodeCount();
            }
        } catch (DatabaseException ex) {
            throw new IndexException("count error", ex);
        }
    }

    /**
     * Method getSecondaryDB(String indexname, boolean readonly)
     * Two kinds of secondarydatabase exists. 
     *    (1) dbname."datestamp" (2) dbname.setSpec
     * 
     * In the case of setSpec based secondarydatabase, only matched records will
     * be indexed, otherwise all records are indexed. It's implemented by
     * passing parameter to DatestampKeyGeneraotr.
     * 
     * @param indexname - Name of database instance to return
     * @param readonly - true to open in read only mode
     * @return SecondaryDatabase for the specified indexname
     */
    private SecondaryDatabase getSecondaryDB(String indexname, boolean readonly)
            throws DatabaseException {
        SecondaryConfig secConf = new SecondaryConfig();
        secConf.setAllowCreate(!readonly);
        secConf.setSortedDuplicates(true);
        secConf.setReadOnly(readonly);
        SecondaryDatabase secDb = null;
        DatestampKeyGenerator keygen = null;
        if (indexname.equals(datestampIndexname)) {
            keygen = new DatestampKeyGenerator(null, indexBinding);
        } else {
            String setSpec = indexname2setSpec(indexname);
            keygen = new DatestampKeyGenerator(setSpec, indexBinding);
        }

        secConf.setKeyCreator(keygen);
        secDb = env.openSecondaryDatabase(null, indexname, indexdb, secConf);
        return secDb;
    }

    /**
     * Converts a set name to a db instance name
     * @param setSpec - set name to converted
     * @return name of indexname associated with specified setSpec
     */
    private String setSpec2indexname(String setSpec) {
        return dbname + "." + setSpec;
    }

    /**
     * Converts a db instance index name to a set name
     * @param setSpec - index name to converted
     * @return name of setSpec associated with specified indexname
     */
    private String indexname2setSpec(String indexname) {
        return indexname.substring(dbname.length() + 1);
    }

    /**
     * Read data from SecondayDatabase, returns Vector of IndexItems
     * @param seconddb - BDB Instance containing specified
     * @param identifier - Unique id to read ('null' allowed)
     * @param count - Max Size of IndexItem Vector to return
     * @param from - UTC from date
     * @param until - UTC until date
     * @return Vector of IndexItems
     * @throws IndexException
     */
    private Vector read(SecondaryDatabase seconddb, String identifier,
            int count, String from, String until) throws IndexException {

        if (from == null)
            from = "1970-01-01";

        if (until == null)
            until = "2999-01-01";

        IndexItem item = null;
        DatabaseEntry datestampKey;
        DatabaseEntry theKey = new DatabaseEntry();
        DatabaseEntry theData = new DatabaseEntry();

        Vector<IndexItem> v = new Vector<IndexItem>();
        boolean isfinished = false;

        try {
            log.debug("read " + seconddb.getDatabaseName() + " " + identifier
                    + " " + count + " " + from + " " + until);
            datestampKey = new DatabaseEntry(UTCTimeFormatter.parse(from));
            SecondaryCursor cursor = seconddb.openSecondaryCursor(null, null);
            try {
                // start from the first match of datestamp if no identifier
                // exists
                if (identifier == null) {
                    OperationStatus retVal = cursor.getSearchKeyRange(
                            datestampKey, theKey, theData, null);
                    if (retVal == OperationStatus.NOTFOUND) {
                        isfinished = true;
                    } else {
                        item = (IndexItem) indexBinding.entryToObject(theData);
                        if (item.getDatestamp().compareTo(until) > 0)
                            return v;
                        else {
                            v.add(item);
                        }
                    }

                } else {
                    theKey = new DatabaseEntry(identifier.getBytes());
                    OperationStatus retVal = cursor.getSearchBoth(datestampKey,
                            theKey, theData, null);
                    if (retVal == OperationStatus.NOTFOUND) {
                        throw new IndexException("cannot find " + identifier);
                    }
                }

                while ((!isfinished) && (v.size() < count)) {
                    OperationStatus retVal = cursor.getNext(theKey, theData,
                            null);
                    if (retVal == OperationStatus.SUCCESS) {
                        item = (IndexItem) indexBinding.entryToObject(theData);
                        if (item.getDatestamp().compareTo(until) > 0)
                            isfinished = true;
                        else
                            v.add(item);
                    } else {
                        isfinished = true;
                    }
                }

            } catch (DatabaseException ex) {
                throw new IndexException("query error", ex);
            } finally {
                cursor.close();
            }
        } catch (DatabaseException ex) {
            throw new IndexException("cursor error", ex);
        } catch (ParseException ex) {
            throw new IndexException("from format error", ex);
        }
        return v;

    }

    /**
     * Sets path to directory containing current BDB Instances
     * @param _envDir - Absolute path to dir containing index
     */
    public void setIndexDir(String _envDir) {
        this.envDir = _envDir;
        File f = new File(_envDir);
        if (!f.exists())
            f.mkdirs();
    }

    /**
     * Sets the tape name and date stamp index name for the specified
     * database name.
     * @param _dbname - XMLTape name minus extension
     */
    public void setTapeName(String _dbname) {
        this.dbname = _dbname;
        if (datestampIndexname == null)
          this.datestampIndexname = this.dbname + ".datestamp";
    }
    
    public boolean isValid() throws IndexException {
        try {
            DbVerify verifier = new DbVerify(env, dbname, true);
            if (!verifier.verify(System.out))
                throw new IndexException("Invalid Index: " + dbname);
            verifier = new DbVerify(env, datestampIndexname, true);
            if (!verifier.verify(System.out))
                throw new IndexException("Invalid Index: " + datestampIndexname);
        } catch (DatabaseException e) {
            throw new IndexException(e.getMessage());
        }
        return true;
    }
}
