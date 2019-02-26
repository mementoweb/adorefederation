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

package gov.lanl.arc.dkImpl;

import gov.lanl.arc.ARCException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ARCFileReaderTest extends TestCase {

    public static String arcFile;
    public static String identifier;
    public static ARCFileReader reader;
    public static long start;
    public static long duration;
    public static long offset;
    
    public void setUp() {
        try {
            reader = new ARCFileReader();
            reader.setArcFile(arcFile);
            reader.setIdentifier(identifier);
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
                    long s = System.currentTimeMillis();
                    testIndexArcFile();
                    testGetDataStream();
                    long d = System.currentTimeMillis() - s;
                    System.out.println("Total Time: " + d);
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
        try {
            reader.generateIndex();
        } catch (Exception e) {
            throw new ARCException(e.getCause());
        }  
        duration = System.currentTimeMillis() - start;;
        System.out.println("\nIndex Generation: " + duration + " ms");
    }
    
    public static void testGetDataStream() {
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        start = System.currentTimeMillis();
        try {
            ARCInputStream reader = new ARCInputStream(new File(arcFile),
                    offset);
            byte[] body = reader.readAll();
            baos = new ByteArrayOutputStream();
            baos.write(body);
            fos = new FileOutputStream(arcFile + identifier);
            fos.write(baos.toByteArray());
            fos.close();
            long dsUsingCdxTime = (System.currentTimeMillis() - start);
            System.out.println("Using CDX: " + dsUsingCdxTime + " ms");
            baos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
