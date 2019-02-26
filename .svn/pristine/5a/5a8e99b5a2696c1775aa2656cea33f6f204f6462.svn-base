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

import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeException;
import gov.lanl.xmltape.TapeRecord;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.identifier.index.record.Record;
import gov.lanl.xmltape.index.TapeIndexInterface;

import java.util.Properties;

public class SeqTapeAccessor implements RecordAccessor {

    private RecordAccessorConfig config;
    private SeqTapeReader str;
    private Record record;
    private boolean hasNextCalled = false;
    
    public void init(Properties props) throws IndexException {
        // Initialize Configurations
        this.config = new RecordAccessorConfig(props);
        try {
            record = (Record) Class.forName(config.getRecordPlugin().trim()).newInstance();
            str = new SeqTapeReader(config.getXmltapeFile());
            str.open();
        } catch (Exception e) {
            throw new IndexException(e.getMessage());
        }
    }

    public void init(Properties props, TapeIndexInterface indexdb, IdentifierIndexInterface idIdx) throws IndexException {
    	// N/A
    }
    
    public void open() {
        try {
            str.open();
        } catch (TapeException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            str.close();
        } catch (TapeException e) {
            e.printStackTrace();
        }
    }
    
    public Record next() {
        Record rec = null;
        if (hasNextCalled && record != null) {
            rec = record;
            hasNextCalled = false;
        } else {
            try {
                rec = record.getClass().newInstance();
                TapeRecord tr = str.next();
                if (tr != null)   
                  rec.createRecord(tr.getMetadata());
                else
                  rec = null;
            } catch (TapeException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return rec;
    }

    public boolean hasNext() {
        if ((record = next()) != null) {
            hasNextCalled = true;
            return hasNextCalled;
        } else {
            try {
                str.close();
            } catch (TapeException e) {
                e.printStackTrace();
            }
            return false;
        }   
    }
    
    public String getMetadata(String id) {
        try {
            TapeRecord tr = null;
            while ((tr = str.next()) != null) {
                if (tr.getIdentifier().equals(id))
                   return tr.getMetadata();
            }
        } catch (TapeException e) {
            e.printStackTrace();
        }

        return null;
    }

}
