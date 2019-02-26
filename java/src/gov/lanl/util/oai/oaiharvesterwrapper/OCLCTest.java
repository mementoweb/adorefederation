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

package gov.lanl.util.oai.oaiharvesterwrapper;

import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class OCLCTest extends TestCase {
    String sampleidentifier;

    String baseURL = null;

    String metadataPrefix;

    public void setUp() {
        try {
            baseURL = System.getProperty("OAIbaseURL");
            sampleidentifier = System.getProperty("SampleIdentifier");
            metadataPrefix = System.getProperty("metadataPrefix");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testThreads() {
        int size = 5;
        OCLCWrapper[] wrappers = new OCLCWrapper[size];
        for (int i = 0; i < size; i++) {
            wrappers[i] = new OCLCWrapper(i, baseURL, this);
            wrappers[i].start();
        }

        for (int i = 0; i < size; i++) {
            try {
                wrappers[i].join();
            } catch (Exception ex) {
                ex.printStackTrace();
                fail(ex.toString());
            }
        }

    }

    public void testGetRecordSize() {
        try {
            ORG.oclc.oai.harvester.verb.GetRecord getrecord = new ORG.oclc.oai.harvester.verb.GetRecord(
                    new URL(baseURL), sampleidentifier, metadataPrefix);
            int size = 0;
            for (Iterator it = getrecord.iterator(); it.hasNext();) {
                //System.out.println(((
                // ORG.oclc.oai.harvester.verb.Record)it.next()).getMetadata());
                it.next();
                size++;
            }
            assertTrue(size == 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public static void main(String args[]) {
        TestSuite suite = new TestSuite("OCLC Tests");
        suite.addTest(new OCLCTest() {
            protected void runTest() {
                testThreads();
            }
        });
        junit.textui.TestRunner.run(suite);

    }
}

class OCLCWrapper extends Thread {
    int seqno;

    TestCase testcase;

    String baseURL;

    public OCLCWrapper(int seqno, String baseURL, TestCase testcase) {
        //    ORG.oclc.oai.harvester.verb.Record.setFactory(ORG.oclc.oai.harvester.verb.Record.XMLFACTORY);
        this.seqno = seqno;
        this.testcase = testcase;
        this.baseURL = baseURL;
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                XMLReader saxParser = XMLReaderFactory
                        .createXMLReader("org.apache.xerces.parsers.SAXParser");
                saxParser.setFeature("http://xml.org/sax/features/validation",
                        false);
                saxParser.setFeature(
                        "http://xml.org/sax/features/namespace-prefixes", true);
                saxParser.setFeature("http://xml.org/sax/features/namespaces",
                        true);
                System.out.println("harvester round:" + (seqno * 5 + i));
                ORG.oclc.oai.harvester.verb.ListRecords listRecords = new ORG.oclc.oai.harvester.verb.ListRecords(
                        new URL(baseURL), null, null, null, "didl");
                for (Iterator it = listRecords.iterator(); it.hasNext();) {
                    ORG.oclc.oai.harvester.verb.Record record = (ORG.oclc.oai.harvester.verb.Record) (it
                            .next());
                    try {
                        saxParser.parse(new InputSource(new StringReader(record
                                .getMetadata())));
                    } catch (Exception ex) {
                        System.out.println(ex);
                        System.out.println(record.getMetadata());
                        testcase.fail("Thread " + seqno + "; Round" + i
                                + ex.toString());
                    }

                }
                testcase.assertTrue(true);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            testcase.fail(ex.toString());
        }

    }
}
