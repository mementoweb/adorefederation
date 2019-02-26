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

import junit.framework.*;
import gov.lanl.xmltape.SingleTapeReader;
import gov.lanl.xmltape.TapeRecord;
import gov.lanl.xmltape.index.*;
import gov.lanl.xmltape.index.sets.SetSpecNamespace;
import gov.lanl.xmltape.index.sets.SetSpecProfile;
import gov.lanl.xmltape.index.sets.SetSpecProfileFactory;
import gov.lanl.xmltape.index.sets.SetSpecXPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @version $Id: BDBIndexTest.java 1240 2004-11-30 22:51:03Z liu_x $
 */

public class BDBIndexTest extends TestCase {

    BDBIndex indexer = null;

    IndexItem item1 = null, item2 = null, item3 = null, item4 = null,
            item5 = null;
    
    public static String xmlFileName;
    
    public static String identifier;
    
    public static String idxDir;
    
    public static String prop = null;
    
    public static Properties setSpecProps = new Properties();
    
    static Logger log = Logger.getLogger(BDBIndexTest.class.getName());

    public BDBIndexTest() {
    }
    
    public BDBIndexTest(String name) {
        super(name);
    }

    public static void main(String args[]) {

        if (args.length > 0) {
            idxDir = args[0];
            xmlFileName = args[1];
            identifier = args[2];
            prop = args[3];
        } else if (xmlFileName == null) {
            idxDir = System.getProperty("XMLTapeIdxWriter.idxDir");
            xmlFileName = System.getProperty("XMLTapeIdxWriter.XMLFileName");
            identifier = System.getProperty("XMLTapeIdxWriter.identifier");
            prop = System.getProperty("XMLTapeIdxWriter.setSpecProps");
        }

        if (prop != null) {
            try {
                setSpecProps.load(new FileInputStream(new File(prop)));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        TestSuite suite = new TestSuite("XMLTapeIdxWriter");
        suite.addTest(new BDBIndexTest() {
            protected void runTest() {
                try {
                    testAppend();
                    testWrite();                    
                    testCursor();
                    testListSets();
                    testListBySets();
                    testGetRecord();
                    testDatestamp();
                    testDelete();
                    if (xmlFileName != null) {
                        testIdxTape();
                        testIdxRead();
                        if (identifier != null)
                            testGetIdentifier();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        junit.textui.TestRunner.run(suite);
    }
    
    public void setUp() {
        try {
            item1 = new IndexItem();
            item1.setIdentifier("1");
            item1.setDatestamp("2002-10-09");
            item1.setIndex(0);
            item1.setOffset(100);

            ArrayList al = new ArrayList();
            al.add("1.1");
            al.add("1.2");
            item1.setSetSpecs(al);

            item2 = new IndexItem();
            item2.setIdentifier("2");
            item2.setDatestamp("2002-12-02");
            item2.setIndex(200);
            item2.setOffset(200);

            al = new ArrayList();
            al.add("2.1");
            al.add("2.2");
            al.add("1.1");
            item2.setSetSpecs(al);

            item3 = new IndexItem();
            item3.setIdentifier("3");
            item3.setDatestamp("2002-12-03");
            item3.setIndex(300);
            item3.setOffset(300);
            al = new ArrayList();
            al.add("3.1");
            al.add("3.2");
            item3.setSetSpecs(al);

            item4 = new IndexItem();
            item4.setIdentifier("4");
            item4.setDatestamp("2002-10-10");
            item4.setIndex(0);
            item4.setOffset(100);

            item5 = new IndexItem();
            item5.setIdentifier("5");
            item5.setDatestamp("2002-10-09");
            item5.setIndex(0);
            item5.setOffset(100);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

    public void testWrite() throws Exception {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(false);
            indexer.putIndexItem(item1);
            indexer.putIndexItem(item2);
            indexer.putIndexItem(item3);

            IndexItem i1 = indexer.getIndexItem("1");
            IndexItem i2 = indexer.getIndexItem("2");
            IndexItem i3 = indexer.getIndexItem("3");

            assertTrue(i1.equals(item1));
            assertTrue(i2.equals(item2));
            assertTrue(i3.equals(item3));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        } finally {
            indexer.close();
        }

    }

    public void testAppend() throws Exception {
        try {
            indexer = new BDBIndex(idxDir, "sample");

            indexer.open(false);
            indexer.putIndexItem(item4);
            IndexItem i4 = indexer.getIndexItem("4");
            assertTrue(i4.equals(item4));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        } finally {
            indexer.close();
        }

        //put another record

        try {
            indexer = new BDBIndex(idxDir, "sample");

            indexer.open(false);
            indexer.putIndexItem(item5);
            IndexItem i5 = indexer.getIndexItem("5");
            assertTrue(i5.equals(item5));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        } finally {
            indexer.close();
        }

        //test size
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(true);
            Vector v = indexer.read(null, 100, null, null);
            for (Iterator it = v.iterator(); it.hasNext();) {
                log.debug((IndexItem) (it.next()));
            }
            assertEquals(v.size(), 2);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        } finally {
            indexer.close();
        }

        //delete
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(false);
            indexer.delete("4");
            indexer.delete("5");
            Vector v = indexer.read(null, 100, null, null);
            assertEquals(v.size(), 0);

            indexer.close();
            assertTrue(true);
        } catch (IndexException ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

    public void testWritewoSets() throws Exception {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            item4 = new IndexItem();
            item4.setIdentifier("4");
            item4.setDatestamp("2002-10-10");
            item4.setIndex(0);
            item4.setOffset(100);
            indexer.open(false);
            indexer.putIndexItem(item4);
            IndexItem i4 = indexer.getIndexItem("4");
            assertTrue(i4.equals(item4));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        } finally {
            indexer.close();
        }
    }

    public void testCursor() throws Exception {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(true);
            Vector v = indexer.read(null, 100, null, null);
            for (Iterator it = v.iterator(); it.hasNext();) {
                log.debug((IndexItem) (it.next()));
            }
            assertEquals(v.size(), 3);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        } finally {
            indexer.close();
        }
    }

    public void testListSets() {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(true);
            assertEquals(indexer.getOAISetSpecs().size(), 6);
            indexer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testGetRecord() throws Exception {
        indexer = new BDBIndex(idxDir, "sample");
        indexer.open(true);
        IndexItem i1 = indexer.getIndexItem("1");
        assertTrue(i1.equals(item1));
        indexer.close();
    }

    public void testListBySets() {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(true);
            Vector v = indexer.read(null, 100, "1.1", null, null);
            assertTrue(v.size() == 2);
            assertTrue(v.firstElement().equals(item1)
                    || v.firstElement().equals(item2));
            indexer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testDatestamp() {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(true);
            //with from
            Vector v = indexer.read(null, 100, "2002-12-02", null);
            assertTrue(v.size() == 2);
            //with until
            v = indexer.read(null, 100, "2002-12-02", "2002-12-02");
            assertTrue(v.size() == 1);

            //with set+from,until
            v = indexer.read(null, 100, "1.1", "2002-12-02", "2002-12-10");
            assertTrue(v.size() == 1);
            indexer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testDelete() {
        try {
            indexer = new BDBIndex(idxDir, "sample");
            indexer.open(false);
            indexer.delete("1");
            indexer.delete("2");
            indexer.delete("3");
            indexer.close();
            assertTrue(true);
        } catch (IndexException ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

    public void testIdxTape() {
        long start = System.currentTimeMillis();
        try {
            indexer = new BDBIndex(idxDir, xmlFileName);
            TapeIndexer index = new TapeIndexer(indexer);
            boolean indexSetspec = true;
            if (indexSetspec) {
                SetSpecProfile sspp = SetSpecProfileFactory.generateSetSpecProfile(setSpecProps);
                // Process Namespace Defs
                for (Iterator i = sspp.getNamespaces().iterator(); i.hasNext();) {
                    SetSpecNamespace ssn = (SetSpecNamespace) i.next();
                    index.addSetNamespaces(ssn.getNamespacePrefix(), ssn.getNamespace());
                }
                // Process Set Spec XPath Information
                for (Iterator j = sspp.getXpaths().iterator(); j.hasNext();) {
                    SetSpecXPath ssx = (SetSpecXPath) j.next();
                    index.addSetElementXPath(ssx.getXpath(), ssx.getXpathPrefix());
                }
            }
            index.parse(xmlFileName);
            long duration = System.currentTimeMillis() - start;
            log.info("Total Indexing Time: " + duration + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testIdxRead() {
        try {
            TapeReaderApp app = new TapeReaderApp();
            app.indexdb = new BDBIndex(idxDir, xmlFileName);
            app.indexdb.open(true);
            long count = app.indexdb.count(null);
            log.info("Total number of records: " + count);
            assertTrue(count > 0);              
            List list = app.indexdb.getOAISetSpecs();
            for (Iterator it = list.iterator(); it.hasNext();) {
                String setSpec = (String) (it.next());
                log.debug("setIndex: " + setSpec + " :"
                        + app.indexdb.count(setSpec));
            }
            app.indexdb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testGetIdentifier() {
        try {
            long start = System.currentTimeMillis();
            TapeReaderApp app = new TapeReaderApp();
            app.indexdb = new BDBIndex(idxDir, xmlFileName);
            app.str = new SingleTapeReader(app.indexdb, xmlFileName);
            app.str.open();
            TapeRecord record = app.str.getRecord(identifier);
            long duration = System.currentTimeMillis() - start;
            log.info("Total Metadata Extraction Time: " + duration + " ms");
            log.debug("identifier=" + record.getIdentifier());
            log.debug("datestamp=" + record.getDatestamp());
            if (record.getSetSpecs() != null) {
                for (Iterator it = record.getSetSpecs().iterator(); it
                        .hasNext();) {
                    log.debug("setspec=" + (String) (it.next()));
                }
            }
            String metadata = record.getMetadata();
            log.debug("metadata=" + metadata);
            assertEquals(metadata.startsWith("<didl:DIDL") , true);
            app.str.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Simple Tests");
        suite.addTest(new BDBIndexTest("testAppend"));
        suite.addTest(new BDBIndexTest("testWrite"));
        suite.addTest(new BDBIndexTest("testCursor"));
        suite.addTest(new BDBIndexTest("testListSets"));
        suite.addTest(new BDBIndexTest("testListBySets"));
        suite.addTest(new BDBIndexTest("testGetRecord"));
        suite.addTest(new BDBIndexTest("testDatestamp"));
        suite.addTest(new BDBIndexTest("testDelete"));
        return suite;

    }

}
