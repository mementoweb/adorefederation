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

import gov.lanl.util.DateUtil;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.create.TapeCreate;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

public class TapeTest extends TestCase {

    public static String testdir = null;
    
    public String rawdir = testdir + File.separator + "raw";

    public static String didlfiles = "didl";

    public static String utf8file = "utf8.xml";

    public String tapedir = testdir + File.separator + "tapes";

    public static String didltape = "didltape.xml";

    public static String utf8tape = "utf8tape.xml";
    
    public int count = 3125;
    
    public String procPropFile = "regex.properties";

    static Logger log = Logger.getLogger(TapeTest.class.getName());

    static String dc  = "<oai_dc:dc " + "xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">"
               + "<dc:date>2001-12-14</dc:date>" 
               + "</oai_dc:dc>";

    public TapeTest(String name) {
        super(name);
    }

    public TapeTest() {
    }
    
    public void testGzipTape() {
        try {
            TapeWriter writer = new TapeWriter(new File(tapedir), "testgziptape", true, 100000000);
            for (int i = 0; i < count; i++) {
                TapeRecord record = getTapeRecord();
                writer.write(record);
                log.debug(record);
            }
            writer.close();
            assertTrue(writer.getFilelist().size() == 1);
            assertTrue((new File(tapedir, (String) writer.getFilelist().get(0))).length() > 0);
        }

        catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testPlainTape() {
        try {
            TapeWriter writer = new TapeWriter(new File(tapedir), "testplaintape", false, 10);
            for (int i = 0; i < count; i++) {
                TapeRecord record = getTapeRecord();
                writer.write(record);
                log.debug(record);
            }
            writer.close();
            assertTrue(writer.getFilelist().size() == count);
        }

        catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testStdout() {
        try {            
            StdoutTapeWriter writer=new StdoutTapeWriter();
            for (int i = 0; i < count; i++) {
                TapeRecord record = getTapeRecord();
                writer.write(record);
                log.debug(record);
            }
            writer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testTapeCreate() {
        try {
            TapeCreate tapecreate = new TapeCreate(
                    rawdir + File.separator + didlfiles, 
                    tapedir + File.separator + didltape,
                    procPropFile);
            tapecreate.createTape();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSeqTapeReader() {
        try {
            testSingleWriter();
            SeqTapeReader reader = new SeqTapeReader(tapedir + File.separator + "tapeSingleWriter.xml");
            reader.open();
            ArrayList<String> al = reader.getTapeAdmins();
            for (String s : al) {
                log.debug(s);
            }
            TapeRecord r = reader.next();
            while (r != null) {
                log.warn(r.toString());
                r = reader.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

    public void testSingleWriter() {
        try {
            SingleTapeWriter writer = new SingleTapeWriter(
                    new FileWriter(new File(tapedir + File.separator + "tapeSingleWriter.xml")));
            writer.writeTapeAdmin(" <tb:XMLtapeBasics xmlns:tb=\"" + TapeProperties.TAPE_BASICS_NS + "\" ><tb:conversion-software>gov.lanl.xmltape.SingleTapeWriter</tb:conversion-software><tb:conversion-time>" + DateUtil.date2UTC(new Date()).toString() + "</tb:conversion-time></tb:XMLtapeBasics>");
            for (int i = 0; i < count; i++) {
               TapeRecord record = getTapeRecord();
               writer.writeRecord(record);
               log.debug(record);
            }
            writer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }
    
    private String getMetaData(String id, String date) {
        String sampleDidl = "<didl:DIDL DIDLDocumentId=\"" + id + "\" diext:DIDLDocumentCreated=\"" + date + "\" xsi:schemaLocation=\"urn:mpeg:mpeg21:2002:02-DIDL-NS http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-21_schema_files/did/didl.xsd http://library.lanl.gov/2005-08/aDORe/DIDLextension/ http://purl.lanl.gov/aDORe/schemas/2006-09-08/DIDLextension.xsd\" xmlns:didl=\"urn:mpeg:mpeg21:2002:02-DIDL-NS\" xmlns:diext=\"http://library.lanl.gov/2005-08/aDORe/DIDLextension/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" 
            + "</didl:DIDL>";

        return sampleDidl;
    }
    
    private TapeRecord getTapeRecord () {
        String id =  UUIDFactory.generateUUID().toString();
        id = "test_" + id.substring(9);
        String date = DateUtil.date2UTC(new Date());
        TapeRecord record = new TapeRecord(
                id, 
                date, 
                null,
                getMetaData(id, date));
        
        return record;
    }

    /*
     * public static Test suite(){ TestSuite suite=new TestSuite("Simple
     * Tests"); suite.addTest(new TapeTest("testSeqTapeReader")); return suite; }
     */

    public static void main(String args[]) {
        
        if (args.length > 0) {
            testdir = args[0];
        } else if (testdir == null) {
            testdir = System.getProperty("XMLTapeTest.TestDir");
        }
        
        TestSuite suite = new TestSuite("XML Tape Tests");
        suite.addTest(new TapeTest() {
            protected void runTest() {
                testGzipTape();
                testPlainTape();
                testTapeCreate();
                testStdout();
                testSeqTapeReader();
            }
        });
        junit.textui.TestRunner.run(suite);

    }
}