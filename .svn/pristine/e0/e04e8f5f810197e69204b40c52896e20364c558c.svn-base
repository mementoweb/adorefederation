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

package gov.lanl.arc.heritrixImpl;

import gov.lanl.arc.ARCException;
import gov.lanl.util.MimeTypeMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ARCFileReaderTest extends TestCase {

    public static String arcFile;
    public static String identifier;
    public static ARCFileReader reader;
    public static CDXInstance cdxInstance;
    public static long start;
    public static long duration;
    
    public void setUp() {
        try {
            reader = new ARCFileReader(arcFile, true);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public static void main(String args[]) {
        
        if (args.length > 0) {
            identifier = args[0];
            arcFile = args[1];
        } else if (identifier == null || arcFile == null) {
            identifier = System.getProperty("ARCFileReaderTest.Id");
            arcFile = System.getProperty("ARCFileReaderTest.ArcFile");
        }
        
        TestSuite suite = new TestSuite("ArcFileReaderTest");
        suite.addTest(new ARCFileReaderTest() {
            protected void runTest() {
                try {
                    testIndexArcFile();
                    testWriteResource();
                    testGetDataStreamUsingCDX();
                    testGetDataStreamWithOutCDX();
                } catch (ARCException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        junit.textui.TestRunner.run(suite);

    }
    
    public void testIndexArcFile() throws ARCException {
        start = System.currentTimeMillis();
        
        // Index Arc File
        reader.generateIndex();
        long idxGenTime = (System.currentTimeMillis() - start);
        File cdx = new File(reader.stripExtension(arcFile, ".arc") + ".cdx");
        long cdxSize = cdx.length();
        assertTrue(cdxSize > 0);
        
        // Validate CDX Instance
        cdxInstance = reader.getCdxInstance();
        int cdxCount = cdxInstance.size();
        assertTrue(cdxCount > 0);

        System.out.println("\nIndex Generation: " + idxGenTime + " ms");
        System.out.println("Index Size: " + cdxSize + " bytes");
        System.out.println("CDX Count: " + cdxCount + " records");
    }
    
    public static void testGetDataStreamUsingCDX() {
        OutputStream baos = null;
        start = System.currentTimeMillis();
        try {
            baos = reader.getDataStreamUsingID(identifier);
            long dsUsingCdxTime = (System.currentTimeMillis() - start);
            System.out.println("Using CDX: " + dsUsingCdxTime + " ms");
            baos.close();
        } catch (ARCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void testGetDataStreamWithOutCDX() {
        OutputStream baos = null;
        start = System.currentTimeMillis();
        try {
            reader.clearCdxInstance();
            baos = reader.getDataStreamUsingID(identifier);
            long dsNoCdxTime = (System.currentTimeMillis() - start);
            System.out.println("Without CDX: " + dsNoCdxTime + " ms");
            baos.close();
        } catch (ARCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void testWriteResource() {
        try {
             start = System.currentTimeMillis();
             String mimeType = reader.getCdxInstance().getMimeTypeforIdentifier(identifier);
             String ext = new MimeTypeMapper().getExtension(mimeType);
             String resource = reader.stripExtension(arcFile, ".arc") + ext;
             reader.writeResource(identifier, resource);
             long writeResouceTime = (System.currentTimeMillis() - start);
             assertTrue(new File(resource).length() > 0);
             System.out.println("Resource to Disc Total Time: " + writeResouceTime + " ms");
        } catch (ARCException e) {
            e.printStackTrace();
        }
    }
    
}
