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

package gov.lanl.xmltape.index;

import gov.lanl.util.DateUtil;
import gov.lanl.util.csv.CSVWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * BasicTapeIndex.java<br>
 * Serialized Java Object XML Tape Index Implementation
 * 
 * @author rchute
 *
 */
public class BasicTapeIndex implements TapeIndexInterface {
    
    private IndexItemManager idxSet = new IndexItemManager();
    boolean readOnly = false;
    
    private String indexDir;
    private String tapeName;
    private String indexFile;
    
    /**
     * Constructor BasicTapeIndex <br>
     * Ensure indexDir, tapeName, and indexFile are set when using this constructor.
     */
    public BasicTapeIndex() {
    }
    
    /**
     * Constructor BasicTapeIndex
     * @param indexdir 
     *            Path the directory containing index file
     * @param tapename
     *            Name of the XML Tape to be read from / indexed
     * @throws IndexException
     */
    public BasicTapeIndex(String indexdir, String tapename) throws IndexException {
        this.indexDir = indexdir;
        this.tapeName = tapename;
        this.indexFile = getDefaultIndexFile();
    }
    
    /**
     * Serializes Object and closes ObjectOutputStream
     */
    public void close() throws IndexException {
        if (!readOnly) {
            if (indexFile == null)
                this.indexFile = getDefaultIndexFile();
            writeIndex();
        }
    }
    
    /**
     * Returns the number of IndexItem records associated with the specified 
     * set name.  A null paramater will return the count for the entire db.
     * @return 
     *        number of records in this database or set
     */
    public long count(String setSpec) throws Exception {
        if (setSpec == null)
            return this.idxSet.size();
        long count = 0;
        Iterator i = idxSet.iterator();
        while (i!=null) {
            IndexItem item = (IndexItem) i.next();
            if (item.getSetSpecs().contains(setSpec))
                count++;
        }
        return count;
    }

    /**
     * Delete IndexItem for the specified identifier
     * @param identifier
     *            identifier of IndexItem to be deleted from index
     */
    public void delete(String identifier) throws IndexException {
        if (!readOnly) {
            idxSet.deleteIndexItem(identifier);
        }
    }

    /**
     * Gets the IndexItem for the specified unique id
     * @param identifier
     *            unique record id
     * @return IndexItem
     *            object matching specified id
     */
    public IndexItem getIndexItem(String identifier) throws IndexException {
        return idxSet.getIndexItem(identifier);
    }

    /**
     * Get list of OAI SetsSpecs; iterates through IndexItems to determine list.
     * @return 
     *            List of OAI Set Names
     */
    public List getOAISetSpecs() {
        ArrayList al = new ArrayList();
        for (Iterator it = idxSet.iterator(); it.hasNext();) {
            IndexItem indexname = (IndexItem) (it.next());
            ArrayList sal = indexname.getSetSpecs();
            if (sal != null) {
                for (int s = 0; s < sal.size(); s++) {
                    if (!al.contains(sal.get(s)))
                        al.add(sal.get(s));
                }
            }
        }
        return al;
    }

    /**
     * Opens index file, optionally in read-only mode
     * @param readonly
     *            Open database as readonly
     *  
     */
    public void open(boolean readonly) throws IndexException {
        this.readOnly = readonly;
        if (idxSet.size() == 0) {
            openIndexFile(readonly);
        }
    }
    
    private void openIndexFile(boolean readonly) throws IndexException {
        this.readOnly = readonly;
        
        if (indexFile == null)
            this.indexFile = getDefaultIndexFile();
        
        File tape = new File(indexFile);
        
        if (tape.exists()) {
            setIndexSet(tape);
        }
    }

    /**
     * Adds an IndexItem instance to the IndexItem TreeSet
     * @param item
     *            IndexItem to be added to current index instance
     */
    public synchronized void putIndexItem(IndexItem item) throws IndexException {
        idxSet.add(item);
    }

    /**
     * Read from index, without datestamp information.
     * @param identifier
     *            unique record id
     * @param count
     *            max number of IndexItems to return
     * @return
     *            Vector of IndexItems
     */
    public synchronized Vector read(String identifier, int count) throws IndexException {
        Vector<IndexItem> v = new Vector<IndexItem>();
        
        for (Iterator it = idxSet.iterator(); it.hasNext();) {
            IndexItem indexname = (IndexItem) (it.next());
            String id = indexname.getIdentifier();
            if ((identifier == null) || (id.equals(identifier)))
                v.add(indexname);
            if (v.size() >= count) {
               return v;
            }
        }
        
        return v;
    }
    
    /**
     * Read from datestamp based database, without using OAI Set Spec
     */
    public synchronized Vector read(String identifier, int count, String from, String until) throws IndexException {
        
        if (from == null)
            from = "1970-01-01";

        if (until == null)
            until = "2999-01-01";
        
        Vector<IndexItem> v = new Vector<IndexItem>();
            
        for (Iterator it = idxSet.iterator(); it.hasNext();) {
            IndexItem indexname = (IndexItem) (it.next());
            String id = indexname.getIdentifier();
            Date dateStamp = DateUtil.utc2Date(indexname.getDatestamp());
            if ((identifier == null || id.equals(identifier))
                    && (dateStamp.after(DateUtil.utc2Date(from)) || dateStamp.equals(DateUtil.utc2Date(from)))
                    && (dateStamp.before(DateUtil.utc2Date(until)) || dateStamp.equals(DateUtil.utc2Date(until)))) {
                v.add(indexname);
            }
            if (v.size() >= count) {
                return v;
            }
        }
        
        return v;
    }

    /**
     * Read from index, when sets is used
     * 
     * @param identifier
     *            start from an entry after this identifier
     * @param setSpec
     *            OAI SetSpec
     * @param count
     *            number of records to be read
     * @param from
     *            UTC from date
     * @param until
     *            UTC until date
     */
    public synchronized Vector read(String identifier, int count, String setSpec, String from, String until) throws IndexException {
        if (from == null)
            from = "1970-01-01";

        if (until == null)
            until = "2999-01-01";
        
        Vector<IndexItem> v = new Vector<IndexItem>();
        
        for (Iterator it = idxSet.iterator(); it.hasNext();) {
            IndexItem indexname = (IndexItem) it.next();
            String id = indexname.getIdentifier();
            Date dateStamp = DateUtil.utc2Date(indexname.getDatestamp());
            if ((identifier == null || id.equals(identifier))
                && indexname.getSetSpecs().contains(setSpec)
                && (dateStamp.after(DateUtil.utc2Date(from)) || dateStamp.equals(DateUtil.utc2Date(from)))
                && (dateStamp.before(DateUtil.utc2Date(until)) || dateStamp.equals(DateUtil.utc2Date(until)))) {
                v.add(indexname);
            }
            if (v.size() >= count) {
               break;
            }
        }
        
        return v;
    }

    /**
     * Sets path to directory containing index files
     * @param indexDir
     *            Absolute path to dir containing index
     */
    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }

    /**
     * Sets the tape name for the specified database name.
     * @param tapeName
     *            XMLTape name minus extension
     */
    public void setTapeName(String tapeName) {
        this.tapeName = tapeName;
    }
    
    /**
     * Writes IndexItem values to a CSV file in the following format; <br>
     * identifier, datestamp, length, offset, sets. <br> 
     * The CSV file is written to the indexDir.
     * @throws IOException
     */
    public void toCSV() throws IOException {
        String tape = tapeName.substring(tapeName.lastIndexOf(File.separator) + 1, tapeName.length());
        tape = tape.substring(0, tape.lastIndexOf('.'));
        String csvFile = new File(indexDir, tape + ".csv").getAbsolutePath();
        this.toCSV(csvFile);
    }
    
    /**
     * Writes IndexItem values to a CSV file in the following format; <br>
     * identifier, datestamp, length, offset, sets. <br> 
     * @param output
     *            path to the csv file to be written
     * @throws IOException
     */
    public void toCSV(String output) throws IOException {
        IndexItem c;
        PrintWriter pw = new PrintWriter( new FileWriter(output));
        CSVWriter csv = new CSVWriter(pw, false, ',', System.getProperty("line.separator") );
        csv.writeCommentln("Tape Index: identifier, datestamp, length, offset, sets");
        
        for (Iterator i = idxSet.iterator(); i.hasNext(); ) {
            c = (IndexItem) i.next();
            csv.write(c.getIdentifier());
            csv.write(c.getDatestamp());
            csv.write(Long.toString(c.getIndex()));
            csv.write(Long.toString(c.getOffset()));
            
            if (c.getSetSpecs() != null) {
                String set = "";
                ArrayList sets = c.getSetSpecs();
                String[] s = new String[sets.size()];
                sets.toArray(s);
                for (int j=0 ; j < s.length;  j++)
                     set += s[j] + ";";
                csv.write(set);
            }
            else
                    csv.write("");

            csv.writeln();
          }
        csv.close();
    }
    
    /**
     * Gets the default index file path. The default path / file structure 
     * is indexDir + (tapeName - extension) + .jdx extension.
     * @return
     *            absolute path to index file to be created
     */
    public String getDefaultIndexFile () {
        String tape = null;
        if (tapeName.contains(File.separator)) {
            tape = tapeName.substring(tapeName.lastIndexOf(File.separator) + 1, tapeName.length());
            tape = tape.substring(0, tape.lastIndexOf('.'));
        } else {
            tape = tapeName;
        }
        String idxFile = new File(indexDir, tape + ".jdx").getAbsolutePath();
        return idxFile;
    }
    
    /**
     * Sets the name of the index file, may also be set in constructor
     * @param idxFile
     *            name of index file
     */
    public void setIndexFile (String idxFile) {
        this.indexFile = idxFile;
    }
    
    /**
     * Serialize IndexItemManager TreeSet to object's indexFile
     * @throws IndexException
     */
    public void writeIndex() throws IndexException {
        if (this.indexFile  !=  null) {
            File idx = new File(this.indexFile);
            this.writeIndex(idx);
        } else {
            throw new IndexException("indexFile is yet to be initialized");
        }
        
    }

    /**
     * Serialize IndexItemManager TreeSet to specified idxFile
     * @throws IndexException
     */
    public void writeIndex(File idxFile) throws IndexException {
        if (idxFile.exists())
            idxFile.delete();
        if (idxSet.size() > 0) {
            FileOutputStream out;
            ObjectOutputStream s;
            try {
                out = new FileOutputStream(idxFile);
                s = new ObjectOutputStream(out);
                s.writeObject(idxSet);
                s.flush();
                s.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setIndexSet(File src) {
        InputStream in = null;
        int bufLen = 20 * 1024 * 1024;
        try {
            in = new BufferedInputStream(new FileInputStream(src), bufLen);
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            idxSet = (IndexItemManager) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception e) {
                }
        }
    }
    
    public boolean isValid() throws IndexException {
        try {
            if (count(null) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new IndexException("Invalid Index: " + tapeName);
        }
    }
}
