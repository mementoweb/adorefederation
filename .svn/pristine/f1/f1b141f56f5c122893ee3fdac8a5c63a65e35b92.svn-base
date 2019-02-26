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

import gov.lanl.YAParser.DocHandler;
import gov.lanl.YAParser.Locator;
import gov.lanl.util.xml.XmlUtil;
import gov.lanl.xmltape.TapeConstants;
import gov.lanl.xmltape.index.sets.SetSpecXPathProcessor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Parse an XMLtape and provides record information to 
 * underlying TapeIndexInterface to store index information
 *
 */
public class TapeIndexer implements DocHandler, TapeConstants {
    TapeIndexInterface indexdb = null;

    Locator loc = new Locator();

    boolean isIdentifier = false;

    boolean isDatestamp = false;

    boolean isDescriptor = false;

    boolean isSetSpec = false;

    boolean indexSets = false;

    long count = 0;

    long start = 0;

    long end = 0;
    
    long offset = 0;

    StringBuffer identifier = new StringBuffer();

    StringBuffer datestamp = new StringBuffer();

    StringBuffer setSpec = new StringBuffer();

    RecordHandler handler = new RecordHandler();

    // general configuration of element name.
    ArrayList<String[]> setElementXPath = new ArrayList<String[]>();

    ArrayList<String[]> setNamespaces = new ArrayList<String[]>();

    static Logger log = Logger.getLogger(TapeIndexer.class.getName());
    long cursor = 0;
    long reset = System.currentTimeMillis();
    
    /**
     * Constuctor specifying TapeIndexInterface implementation
     * 
     * @param indexdb
     *            Index Type to be generated
     */
    public TapeIndexer(TapeIndexInterface indexdb) {
        this.indexdb = indexdb;
    }

    /**
     * Add Element XPath Information for Set Processing
     * 
     * @param elem
     * @param prefix
     */
    public void addSetElementXPath(String elem, String prefix) {
        String[] element = new String[] { elem, prefix };
        if (!setElementXPath.contains(element)) {
            setElementXPath.add(element);
        }
    }

    /**
     * Add Namespace Information for Set Processing
     * 
     * @param prefix
     * @param namespace
     */
    public void addSetNamespaces(String prefix, String namespace) {
        String[] nspace = new String[] {namespace, prefix};
        if (!setNamespaces.contains(nspace)) {
            setNamespaces.add(nspace);
        }
    }

    /**
     * Parses provided file reference using the YAParser, which obtains byte
     * offset values. Byte Offset and document length values are writen to the
     * current TapeIndexImplementation
     * 
     * @param file
     *            absolute path to XMLTape
     */
    public void parse(String file) throws Exception {
        if (setElementXPath.size() > 0)
            indexSets = true;

        indexdb.open(false);
        handler.setLocator(loc);
        handler.setDocHandler(this);
        handler.parse(file);
        indexdb.close();
    }

    // implementation of DocHandler is below this line

    /**
     * Initializes document
     */
    public void startDocument() {
    }

    /**
     * Actions to perform once end of document is reached
     */
    public void endDocument() {
    }

    /**
     * Sets processing flags for target XML elements
     */
    public void startElement(String elem) {
        if (elem.equals(TAPE_IDENTIFIER)) {
            isIdentifier = true;
        } else if (elem.equals(TAPE_DATESTAMP)) {
            isDatestamp = true;
        } else if (elem.equals(TAPE_RECORD_DATA)) {
            start = handler.getRecordStart();
        }
    }

    /**
     * Sets IndexItem values for DIDL records
     */
    public void endElement(String elem) throws Exception {
        if (elem.equals(TAPE_IDENTIFIER)) {
            isIdentifier = false;
        } else if (elem.equals(TAPE_DATESTAMP)) {
            isDatestamp = false;
        } else if (elem.equals(TAPE_RECORD_DATA)) {
            end = loc.getEndIndex();
            offset = handler.getRecordOffset(start, end - elem.length());
            IndexItem item = new IndexItem();
            item.setIdentifier(XmlUtil.decode(identifier.toString()));
            item.setDatestamp(datestamp.toString());
            item.setIndex(start);
            // item.setOffset(end - start + 1);
            item.setOffset(offset);

            // Get Set Information Using XPath
            if (indexSets) {
                String record = handler.getRecord(start, offset);
                ArrayList<String> sets = getSetSpecArray(record);
                item.setSetSpecs(sets);
            }

            // Add Item to Index Implementation
            indexdb.putIndexItem(item);

            count++;

            // Clear References
            identifier.setLength(0);
            datestamp.setLength(0);
        }
    }
    
    /**
     * Get TapeRecord Count
     */
    public int getTapeRecordCount() {
        return new Long(count).intValue();
    }
    
    /**
     * Sets identifer, datestamp, set values for flags set in startElement
     */
    public void text(byte[] buffer, int start, int length) {
        if (isIdentifier)
            identifier.append(new String(buffer, start, length));

        if (isDatestamp)
            datestamp.append(new String(buffer, start, length));
    }

    // implementation of DocHandler is above this line

    /**
     * URLEncode SetSpec values, then replace % with *
     */
    private String setSpecEncode(String input) {
        String str = null;
        try {
            str = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
        return str.replace('%', '*');
    }

    /**
     * Sets the TapeIndexInterface Implementation
     * @param indexdb
     *       a tape index implementation
     */
    public void setTapeIndexType(TapeIndexInterface indexdb) {
        this.indexdb = indexdb;
    }

    /**
     * Gets SetSpec Values using XPath defined in external props
     * @param record
     *       XML Record
     * @return
     *       An array of the records setSpec values
     */
    public ArrayList<String> getSetSpecArray(String record) {
        SetSpecXPathProcessor doc = new SetSpecXPathProcessor();
        ArrayList setSpecs = new ArrayList();
        try {
            doc.setDocument(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(record);
            return setSpecs;
        }
        
        // Define Namespace Information for XPath Processor
        for (Iterator n = setNamespaces.iterator(); n.hasNext();) {
            String[] nsi = (String[]) n.next();
            doc.addNamespace(nsi[0], nsi[1]);
        }
        // Process XPath to obtain set information
        for (Iterator i = setElementXPath.iterator(); i.hasNext();) {
            String[] values = (String[]) i.next();
            String xpath = values[0];
            String prefix = null;
            if (xpath == null || xpath.trim().equals(""))
                continue;
            if (values[1] != null)
                prefix = values[1];
            log.debug("XPath:" + xpath);
            ArrayList results = doc.xpath(xpath);
            for (Iterator k = results.iterator(); k.hasNext();) {
                String value = (String) k.next();
                String setSpec = null;
                if (value != null && prefix != null) {
                    setSpec = prefix + ":" + setSpecEncode(value);
                } else {
                    setSpec = setSpecEncode(value);
                }
                if (!setSpecs.contains(setSpec)) {
                    setSpecs.add(setSpec);
                }
            }
        }
        return setSpecs;
    }

}
