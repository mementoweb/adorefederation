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

import gov.lanl.identifier.Identifier;
import gov.lanl.identifier.IdentifierNotFoundException;
import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.index.IndexItem;
import gov.lanl.xmltape.index.berkeleydbImpl.IndexItemBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.BtreeStats;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.util.DbVerify;

/**
 * Berkeley DB implementation of xmltape index
 */

public class BDBIndex extends gov.lanl.xmltape.index.berkeleydbImpl.BDBIndex implements IdentifierIndexInterface {

    protected BDBEnv dbEnvironment;
    protected String databaseDirectory;
    protected boolean readOnly = false;
    protected static Logger log = Logger.getLogger(BDBIndex.class.getName());
    protected static TupleBinding identifierBinding = new IdentifierBinding();
    protected static TupleBinding indexBinding = new IndexItemBinding();
    
    public BDBIndex() {}
    
    public synchronized IndexItem getIndexItem(String identifier) {
		IndexItem item = null;
		DatabaseEntry theKey = new DatabaseEntry(identifier.getBytes());
		DatabaseEntry theData = new DatabaseEntry();
		// Get it
		try {
			OperationStatus retVal = dbEnvironment.getOAIDb().get(null, theKey, theData, null);
			if (retVal == OperationStatus.SUCCESS) {
				item = (IndexItem) indexBinding.entryToObject(theData);
			}

		} catch (DatabaseException ex) {
			ex.printStackTrace();
		}
		return item;
	}
    
    /**
     * Open an index instance
     * 
     * @param readonly
     *            allow index modification
     */
    public void open(boolean readonly) {
    	if (dbEnvironment == null || dbEnvironment.getEnv() == null)
            dbEnvironment = new BDBEnv(databaseDirectory, readonly);
    	else {
    		dbEnvironment.openDatabases(readonly);
    	}
    }

    /**
     * Close databases related to env; call before close. Closes the databases, but not the env
     * @throws IndexException
     */
    public void closeDatabases() throws IndexException {
        dbEnvironment.closeDatabases();
    }
    
    /**
     * Close current index instance
     */
    public void close() {
        try {
			dbEnvironment.shutDown();
			dbEnvironment = null;
		} catch (IndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Read from index, list of identifiers
     * @param identifier
     *            content or datastream identifier
     * @return the matched records if found, otherwise return null
     */
    public Identifier getIdentifier(String identifier) throws IndexException, IdentifierNotFoundException {
        try {
            DatabaseEntry key = new DatabaseEntry(identifier.getBytes("UTF-8"));
            DatabaseEntry data = new DatabaseEntry();
            OperationStatus status = dbEnvironment.getIdentifierDb().get(null, key, data, LockMode.DEFAULT);
            if (status == OperationStatus.SUCCESS)
                 return (Identifier) identifierBinding.entryToObject(data);
            else 
                 throw new IdentifierNotFoundException("No block found for '"+ identifier +"'.");
        } catch (DatabaseException ex) {
            throw new IndexException("Query Error", ex);
        } catch (UnsupportedEncodingException e) {
            throw new IndexException("Identifier Encoding Error", e);
        } 
    }

    /**
     * Adds an Identifier instance to the Identifier TreeSet
     * @param id
     *            Identifier to be added to current index instance
     */
    public void putIdentifier(Identifier id) throws IndexException {
        Transaction txn = null;
        try {
            TransactionConfig config = new TransactionConfig();
            config.setNoSync(true);
            txn = dbEnvironment.getEnv().beginTransaction(null, config);
            storeIdentifier(txn, id);
            txn.commitNoSync();
            txn = null;
        } catch (Exception e) {
            try {
                if (txn != null)
                    txn.abort();
            } catch (DatabaseException dbe2) {
            }
            ErrorHandler.error("putIdentifier", e);
        }
    }
    
    /**
     * Adds an Identifier Collection to the Identifiers TreeSet
     * @param ids
     *            Identifier to be added to current index instance
     */
    public void putIdentifiers(ArrayList<Identifier> ids) throws IndexException {
        for (Identifier id : ids) {
            putIdentifier(id);
        }
    }
    
    /**
     * Delete Identifier for the specified identifier
     * 
     * @param identifier
     *            identifier of Identifier to be deleted from index
     */
    public void delete(String identifier) {
        try {            
            // Delete from primary db
            DatabaseEntry theKey = new DatabaseEntry(identifier.getBytes("UTF-8"));
            dbEnvironment.getIdentifierDb().delete(null, theKey);
        } catch (Exception ex) {

        }
    }

    /**
     * Determine if current identifier is a record identifier
     * @param recId - identifier to test
     * @return if docId, returns datestamp else returns null
     * @throws IndexException
     */
    public String isDocId(String recId) throws IndexException {
        Cursor cursor = null;
        try {
            cursor = dbEnvironment.getOAIDb().openCursor(null, null);

            DatabaseEntry foundKey = new DatabaseEntry(recId.getBytes("UTF-8"));
            DatabaseEntry foundData = new DatabaseEntry();
            IndexItemBinding binding = new IndexItemBinding();
            
            OperationStatus status = cursor.getSearchKey(foundKey, foundData, null);
            while (status == OperationStatus.SUCCESS) {
            	IndexItem id = (IndexItem) binding.entryToObject(foundData);
                if (id.getIdentifier().equals(recId))
                   return id.getDatestamp();
            }
            
        } catch (DatabaseException dbe) {
            ErrorHandler.error("isDocId", dbe);
        } catch (UnsupportedEncodingException e) {
            ErrorHandler.error("isDocId", e);
        } finally {
            try {
                if (cursor != null)
                    cursor.close();
            } catch (DatabaseException dbe2) {
            	ErrorHandler.error("isDocId - Error attempting close cursor ", dbe2);
            }
        }
        return null;
    }

    /**
     * Sets path to directory containing index files
     * @param indexDir
     *            Absolute path to dir containing index
     */
    public void setIndexDir(String indexDir) {
        this.databaseDirectory = indexDir;
    }

    /**
     * Gets path to directory containing index files
     */
    public String getIndexDir() {
        return databaseDirectory;
    }
    
    /**
     * Read from index, list of identifiers
     * @param id
     *            content or datastream identifier
     * @return the matched records if found, otherwise return null
     */
    public ArrayList<Identifier> getIdentifiers(String id) throws IndexException {
        ArrayList<Identifier> v = new ArrayList<Identifier>();
        Cursor cursor = null;

        try {
            cursor = dbEnvironment.getIdentifierDb().openCursor(null, null);
            
            try {
                DatabaseEntry foundKey = new DatabaseEntry(id.getBytes("UTF-8"));
                DatabaseEntry foundData = new DatabaseEntry();
                IdentifierBinding binding = new IdentifierBinding();
                
                OperationStatus status = cursor.getSearchKey(foundKey, foundData, (LockMode) null);
                if (status == OperationStatus.NOTFOUND) {
                    throw new IndexException("cannot find " + id);
                } else if (status == OperationStatus.SUCCESS) {
                    do {
                        Identifier identifier = (Identifier) binding.entryToObject(foundData);
                        v.add(identifier);
                    } while (cursor.getNextDup(foundKey, foundData, (LockMode) null) != OperationStatus.NOTFOUND);
                }
            } catch (DatabaseException ex) {
                throw new IndexException("query error", ex);
            } catch (UnsupportedEncodingException ex) {
                throw new IndexException("UnsupportedEncodingException occured:", ex);
            } finally {
            	if (cursor != null)
                    cursor.close();
            }
        } catch (DatabaseException ex) {
        	ErrorHandler.error("getIdentifiers - Error attempting close cursor ", ex);
            throw new IndexException("cursor error", ex);
        } 

        //Collections.sort(v);
        return v;
    }
    
    /**
     * List number of identifier recordss
     */
    public long count() throws IndexException {
        long cnt = 0;
        try {
             cnt =  ((BtreeStats) (dbEnvironment.getIdentifierDb().getStats(null))).getLeafNodeCount();
        } catch (DatabaseException dbe) {
            ErrorHandler.error("count", dbe);
        }
        return cnt;
    }
    
    public byte[] listIdentifiers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TreeSet<String> docIds = new TreeSet<String>();
        Cursor cursor = null;
        try {
        	cursor = dbEnvironment.getIdentifierDb().openCursor(null, null);
        	
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
            IdentifierBinding binding = new IdentifierBinding();
            byte[] nlf = "\n".getBytes();
            
            long s = System.currentTimeMillis();
            int cnt = 0, total = 0;
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                Identifier id = (Identifier) binding.entryToObject(foundData);
                docIds.add(id.getRecordId());
                baos.write(id.getIdentifier().getBytes());
                baos.write(nlf);
                
                if (cnt == 1000) {
                    total = total + cnt;
                    log.debug(total + " | " + (System.currentTimeMillis() - s) + " | " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
                    cnt = 0;
                } else
                    cnt++;
            }

            for (String id : docIds) {
                baos.write(id.getBytes());
                baos.write(nlf);
            }
            
        } catch (DatabaseException dbe) {
            ErrorHandler.error("listIdentifiers", dbe);
        } catch (IOException e) {
            ErrorHandler.error("listIdentifiers", e);
        } finally {
            try {
                if (cursor != null)
                    cursor.close();
            } catch (DatabaseException dbe2) {
            	ErrorHandler.error("listIdentifiers - Error attempting close cursor ", dbe2);
            }
        }
        return  baos.toByteArray();
    }

    protected void storeIdentifier(Transaction txn, Identifier id) throws DatabaseException, IOException {
        DatabaseEntry key = new DatabaseEntry(id.getIdentifier().getBytes("UTF-8"));
        DatabaseEntry data = new DatabaseEntry();
        IdentifierBinding binding = new IdentifierBinding();
        binding.objectToEntry(id, data);
        dbEnvironment.getIdentifierDb().put(txn, key, data);
    }
    
    public boolean isValid() {
        try {
            DbVerify verifier = new DbVerify(dbEnvironment.getEnv(), BDBEnv.IDENTIFIER_DATABASE, true);
            if (!verifier.verify(System.out))
                throw new IndexException("Invalid Index: " + BDBEnv.IDENTIFIER_DATABASE);
            verifier = new DbVerify(dbEnvironment.getEnv(), BDBEnv.ID_BY_IDENTIFIER_DATABASE, true);
            if (!verifier.verify(System.out))
                throw new IndexException("Invalid Index: " + BDBEnv.ID_BY_IDENTIFIER_DATABASE);
        } catch (Exception e) {
        	return false;
        }
        return true;
    }

	public BDBEnv getDbEnvironment() {
		return dbEnvironment;
	}

	public void setDbEnvironment(BDBEnv dbEnvironment) {
		this.dbEnvironment = dbEnvironment;
	}
}
