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
import gov.lanl.resource.ResourceRecord;
import gov.lanl.util.resource.Resource;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

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
 * Berkeley DB implementation of Filesystem based Resource Repository
 */

public class BDBIndex {

    protected BDBEnv bdbEnv;
    protected String databaseDirectory;
    protected boolean readOnly = false;
    protected static Logger log = Logger.getLogger(BDBIndex.class.getName());
    protected static TupleBinding resourceBinding = new ResourceBinding();
    
    public BDBIndex() {}
    
    /**
     * Open an index instance
     * 
     * @param readonly
     *            allow index modification
     */
    public void open(boolean readonly) {
    	if (bdbEnv == null || bdbEnv.getEnv() == null)
            bdbEnv = new BDBEnv(databaseDirectory, readonly);
    	else {
    		bdbEnv.openDatabases(readonly);
    	}
    }

    /**
     * Close databases related to env; call before close. Closes the databases, but not the env
     * @throws IndexException
     */
    public void closeDatabases() throws ResourceException {
        bdbEnv.closeDatabases();
    }
    
    /**
     * Close current index instance
     */
    public void close() {
        try {
			bdbEnv.shutDown();
			bdbEnv = null;
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public ResourceRecord getMetadata(String identifier) {
    	ResourceRecord item = null;
		DatabaseEntry theKey = new DatabaseEntry(identifier.getBytes());
		DatabaseEntry theData = new DatabaseEntry();
		// Get it
		try {
			OperationStatus retVal = bdbEnv.getResourceRecordDb().get(null, theKey, theData, null);
			if (retVal == OperationStatus.SUCCESS) {
				item = (ResourceRecord) resourceBinding.entryToObject(theData);
			}
		} catch (DatabaseException ex) {
			ex.printStackTrace();
		}
		return item;
	}
    
    public Resource getResource(String identifier) throws ResourceException {
		Resource r = new Resource();
		try {
			ResourceRecord record = getMetadata(identifier);
			String resourceUri = record.getResourceUri();
			// Check to see if were working a true URI
			if (resourceUri.startsWith("file://")) {
				r.setContentType(record.getMimetype());
				resourceUri = new URI(record.getResourceUri()).getPath();
			}
			InputStream is = new BufferedInputStream(new FileInputStream(resourceUri));
			r.setInputStream(is);
		} catch (URISyntaxException e) {
			log.debug(e,e);
			throw new ResourceException("Specified URI syntax is invalid.",e);
		} catch (FileNotFoundException e) {
			log.debug(e,e);
			throw new ResourceException("Specified URI syntax is invalid.",e);
		}
		return r;
	}

    /**
     * Adds an ResourceRecord instance to the ResourceRecord index
     * @param id
     *            ResourceRecord to be added to current index instance
     */
    public void putResourceRecord(ResourceRecord rec) throws ResourceException {
        Transaction txn = null;
        try {
            TransactionConfig config = new TransactionConfig();
            config.setNoSync(true);
            txn = bdbEnv.getEnv().beginTransaction(null, config);
            storeResourceRecord(txn, rec);
            txn.commitNoSync();
            txn = null;
        } catch (Exception e) {
            try {
                if (txn != null)
                    txn.abort();
            } catch (DatabaseException dbe2) {
            }
            ErrorHandler.error("putResourceRecord", e);
            log.error(e,e);
        }
    }
    
    /**
     * Adds an Identifier Collection to the Identifiers TreeSet
     * @param ids
     *            Identifier to be added to current index instance
     */
    public void putResourceRecord(ArrayList<ResourceRecord> recs) throws ResourceException {
        for (ResourceRecord rec : recs) {
            putResourceRecord(rec);
        }
    }
    
    /**
     * Delete ResourceRecord for the specified identifier
     * 
     * @param record
     *            identifier of Identifier to be deleted from index
     */
    public void delete(String recId) {
        try {            
            // Delete from primary db
            DatabaseEntry theKey = new DatabaseEntry(recId.getBytes("UTF-8"));
            bdbEnv.getResourceRecordDb().delete(null, theKey);
        } catch (Exception ex) {

        }
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
    public ArrayList<ResourceRecord> getResourceRecord(String id) throws ResourceException {
        ArrayList<ResourceRecord> v = new ArrayList<ResourceRecord>();
        Cursor cursor = null;

        try {
            cursor = bdbEnv.getResourceRecordDb().openCursor(null, null);
            
            try {
                DatabaseEntry foundKey = new DatabaseEntry(id.getBytes("UTF-8"));
                DatabaseEntry foundData = new DatabaseEntry();
                ResourceBinding binding = new ResourceBinding();
                
                OperationStatus status = cursor.getSearchKey(foundKey, foundData, (LockMode) null);
                if (status == OperationStatus.NOTFOUND) {
                    throw new ResourceException("cannot find " + id);
                } else if (status == OperationStatus.SUCCESS) {
                    do {
                    	ResourceRecord rec = (ResourceRecord) binding.entryToObject(foundData);
                        v.add(rec);
                    } while (cursor.getNextDup(foundKey, foundData, (LockMode) null) != OperationStatus.NOTFOUND);
                }
            } catch (DatabaseException ex) {
                throw new ResourceException("query error", ex);
            } catch (UnsupportedEncodingException ex) {
                throw new ResourceException("UnsupportedEncodingException occured:", ex);
            } finally {
            	if (cursor != null)
                    cursor.close();
            }
        } catch (DatabaseException ex) {
        	ErrorHandler.error("getIdentifiers - Error attempting close cursor ", ex);
            throw new ResourceException("cursor error", ex);
        } 

        //Collections.sort(v);
        return v;
    }
    
    /**
     * List number of identifier records
     */
    public long count() throws ResourceException {
        long cnt = 0;
        try {
             cnt =  ((BtreeStats) (bdbEnv.getResourceRecordDb().getStats(null))).getLeafNodeCount();
        } catch (DatabaseException dbe) {
            ErrorHandler.error("count", dbe);
        }
        return cnt;
    }
    
    public byte[] listIdentifiers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Cursor cursor = null;
        try {
        	cursor = bdbEnv.getResourceRecordDb().openCursor(null, null);
        	
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
            ResourceBinding binding = new ResourceBinding();
            byte[] nlf = "\n".getBytes();
            
            long s = System.currentTimeMillis();
            int cnt = 0, total = 0;
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                ResourceRecord id = (ResourceRecord) binding.entryToObject(foundData);
                baos.write(id.getIdentifier().getBytes());
                baos.write(nlf);   
                if (cnt == 1000) {
                    total = total + cnt;
                    log.debug(total + " | " + (System.currentTimeMillis() - s) + " | " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
                    cnt = 0;
                } else
                    cnt++;
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
    
    protected void storeResourceRecord(Transaction txn, ResourceRecord rec) throws DatabaseException, IOException {
        DatabaseEntry key = new DatabaseEntry(rec.getIdentifier().getBytes("UTF-8"));
        DatabaseEntry data = new DatabaseEntry();
        ResourceBinding binding = new ResourceBinding();
        binding.objectToEntry(rec, data);
        bdbEnv.getResourceRecordDb().put(txn, key, data);
    }
    
    public boolean isValid() {
        try {
            DbVerify verifier = new DbVerify(bdbEnv.getEnv(), BDBEnv.RESOURCE_DATABASE, true);
            if (!verifier.verify(System.out))
                throw new ResourceException("Invalid Index: " + BDBEnv.RESOURCE_DATABASE);
        } catch (Exception e) {
        	return false;
        }
        return true;
    }

	public BDBEnv getDbEnvironment() {
		return bdbEnv;
	}

	public void setDbEnvironment(BDBEnv dbEnvironment) {
		this.bdbEnv = dbEnvironment;
	}
}
