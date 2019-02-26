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

package gov.lanl.xmltape;

import gov.lanl.xmltape.index.IndexException;
import gov.lanl.xmltape.index.IndexItem;
import gov.lanl.xmltape.index.TapeIndexInterface;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * An XMLTape Reader, which uses the index file to determine the length and offset
 * for a specified identifier. This is most efficient tape reader implementation.
 */

public class SingleTapeReader implements TapeConstants {
    private int size = 0;

    private TapeIndexInterface indexdb = null;

    private RandomAccessFile tapefile = null;

    private String file = null;

    static Logger log = Logger.getLogger(SingleTapeReader.class.getName());

    /**
     * Constructor
     * @param indexdb
     *           Initialize Implementation of TapeIndexInterface
     * @param file
     *           Absolute path to XMLtape to be read
     */
    public SingleTapeReader(TapeIndexInterface indexdb, String file) {
        this.indexdb = indexdb;
        this.file = file;
    }

    /**
     * Opens the initialized XMLTape for reading
     * @throws TapeException
     */
    public void open() throws TapeException {
        try {
            this.indexdb.open(true);
            tapefile = new RandomAccessFile(file, "r");
        } catch (java.io.IOException ex) {
            throw new TapeException(ex);
        } catch (gov.lanl.xmltape.index.IndexException ex) {
            throw new TapeException(ex);
        }

    }

    /**
     * Gets the complete list of OAISetSpecs contain in the XMLTape
     * @return
     *        List containing OAISetSpec values available in XMLTape
     */
    public List getOAISetSpecs() {
        return indexdb.getOAISetSpecs();
    }

    /**
     * Get TapeRecord for the specified unique identifier.  
     * Reads the tape index file to determine the offset and length values,
     * then creates TapeRecord from the extracted bytes.
     * @param identifier
     *         Unique identifier for the record to be extracted
     * @return
     *         TapeRecord object containing the XML representing the specified identifer
     * @throws TapeException
     */
    public synchronized TapeRecord getRecord(String identifier)
            throws TapeException {
        TapeRecord record;
        try {
            IndexItem item = indexdb.getIndexItem(identifier);
            if (item == null)
                return null;
            log.debug("index " + item.getIndex());
            tapefile.seek(item.getIndex());
            byte[] buffer = new byte[(int) item.getOffset()];
            tapefile.read(buffer);

             record=new TapeRecord(item.getIdentifier(),
                     item.getDatestamp(),
                    item.getSetSpecs(),
                    new String(buffer,"UTF-8"));
        } catch (java.io.IOException ex) {
            throw new TapeException(ex);
        } catch (gov.lanl.xmltape.index.IndexException ex) {
            throw new TapeException(ex);
        }
        return record;
    }

    /**
     * Read records from xmltape with sets
     * 
     * @param identifier
     *            cursor
     * @param count
     *            number of records to read
     * @param setSpec
     *            whether the real xmldata should be read
     *  
     */
    public synchronized Vector getRecords(String identifier, int count,
            String setSpec, String from, String until) throws TapeException {
        Vector records = new Vector();
        try {
            Vector indexs = null;
            if (setSpec != null)
                indexs = indexdb.read(identifier, count, setSpec, from, until);
            else
                indexs = indexdb.read(identifier, count, from, until);

            records = new Vector();
            for (Iterator it = indexs.iterator(); it.hasNext();) {
                IndexItem item = (IndexItem) (it.next());
                log.info("index " + item.getIndex());
                tapefile.seek(item.getIndex());
                byte[] buffer = new byte[(int) item.getOffset()];
                tapefile.read(buffer);
                TapeRecord record=new TapeRecord(item.getIdentifier(),
                        item.getDatestamp(),
                        item.getSetSpecs(),
                        new String(buffer,"UTF-8"));
                records.add(record);
            }
        } catch (java.io.IOException ex) {
            throw new TapeException(ex);
        } catch (gov.lanl.xmltape.index.IndexException ex) {
            throw new TapeException(ex);
        }
        return records;
    }
    
    /**
     * Closes the XMLTape and Index File
     * @throws TapeException
     */
    public void close() throws TapeException {
        try {
			indexdb.close();
		} catch (IndexException e) {
			e.printStackTrace();
		}
        try {
            tapefile.close();
        } catch (IOException e) {
			e.printStackTrace();
			throw new TapeException(e);
		}
    }
}
