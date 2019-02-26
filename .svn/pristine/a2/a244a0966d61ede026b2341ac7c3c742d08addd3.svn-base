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

package gov.lanl.xmltape.identifier.index.access;

import java.util.Properties;
import java.util.Vector;

import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.SingleTapeReader;
import gov.lanl.xmltape.TapeException;
import gov.lanl.xmltape.TapeRecord;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.identifier.index.record.Record;
import gov.lanl.xmltape.index.TapeIndexInterface;

public class RandomTapeAccessor implements RecordAccessor {
    RecordAccessorConfig config;
    SingleTapeReader str;
    Vector<Record> records = new Vector<Record>();
    Record record;
    int cursor = -1;
    int length = 0;
    IdentifierIndexInterface idIdx = null;
    
    public void init(Properties props) throws IndexException {
        TapeIndexInterface indexdb;
        try {
            Class idxImpl = Class.forName(config.getIndexPlugin());
            indexdb = (TapeIndexInterface) idxImpl.newInstance();
            indexdb.setTapeName(config.getDatabaseName());
            indexdb.setIndexDir(config.getIndexDBDir());
        } catch (ClassNotFoundException e) {
            throw new IndexException("ClassNotFoundException: Unable to find " + e.getMessage() + " in classpath");
        } catch (InstantiationException e) {
            throw new IndexException("InstantiationException: Error occured creating plug-in instance; " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IndexException(e.getMessage());
        }
        init(props, indexdb, null);
    }
    
    public void init(Properties props, TapeIndexInterface indexdb, IdentifierIndexInterface idIdx) throws IndexException {
        // Initialize Configurations
    	this.idIdx = idIdx;
        this.config = new RecordAccessorConfig(props);
        try {
            if (config.getRecordPlugin() != null) {
                Class recordImpl = Class.forName(config.getRecordPlugin());
                record = (Record) recordImpl.newInstance();
            }     
            str = new SingleTapeReader(indexdb, config.getXmltapeFile());
            str.open();
        } catch (ClassNotFoundException e) {
            throw new IndexException("ClassNotFoundException: Unable to find " + e.getMessage() + " in classpath");
        } catch (InstantiationException e) {
            throw new IndexException("InstantiationException: Error occured creating plug-in instance; " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IndexException(e.getMessage());
        } catch (TapeException e) {
            throw new IndexException("TapeException: Error occured creating SingleTapeReader instance; " + e.getMessage());
        }
    }
    
    public Record next() {
        if (length == 0) {
            initRecordsVector();
        }
        cursor++;
        return records.get(cursor);
    }

    public boolean hasNext() {
        if (length == 0) {
            initRecordsVector();
        }
        
        if (cursor + 1 < length)
            return true;
        else 
            return false;
    }

    public void close() {
        try {
            if (idIdx != null)
            	idIdx.closeDatabases();
        } catch (IndexException e) {
			e.printStackTrace();
		}
        try {
            str.close();
        } catch (TapeException e) {
            e.printStackTrace();
        }
    }
    
    private void initRecordsVector() {
        try {
            Vector<TapeRecord> vtr = (Vector<TapeRecord>) str.getRecords(null,100000,null,null,null);
            for (TapeRecord tr : vtr) {
                Record rec;
                try {
                    rec = record.getClass().newInstance();
                    if (tr.getIdentifier() != null) {
                       rec.createRecord(tr.getMetadata());
                       records.add(rec);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (TapeException e) {
            // Do nothing for now
        }
        length = records.size();
    }

    public String getMetadata(String id) {
        String data = null;
        try {
            data = str.getRecord(id).getMetadata();
        } catch (TapeException e) {
            e.printStackTrace();
        }
        return data;
    }
}
