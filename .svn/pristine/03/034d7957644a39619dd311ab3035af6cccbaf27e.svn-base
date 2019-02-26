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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ARCFileWriterTest extends TestCase {

    // Env Variables
    public static String prefix = "";
    //public static int maxSize = 50000000; //50 MB
    public static int maxSize = 1000000000; // 1 GB
    public static boolean compression = false;

    public static String arcFileDir;
    public static String arcFileName = ARCFileWriter.getUUIDArcName(prefix, false);
    public static String arcResource;
    public static ARCFileWriter writer;
    public static String mimeType;
    public static long start;
    public static long duration;
    public static ByteArrayOutputStream baos;
    public static int recordLength;
    public static int count = 1;
    
    public void setUp() {
        try {
            //writer = new ARCFileWriter(arcFileDir, arcFileName, compression);
            writer = new ARCFileWriter(arcFileDir, prefix, compression, maxSize);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public static void main(String args[]) {
        
        if (args.length > 0) {
            arcFileDir = new File(args[0]).getAbsolutePath();
            arcResource = args[1];
        } else if (arcFileDir == null || arcResource == null) {
            arcFileDir = System.getProperty("ARCFileWriterTest.ArcFileDir");
            arcResource = System.getProperty("ARCFileWriterTest.ArcResource");
        }
        
        TestSuite suite = new TestSuite("ArcFileWriterTest");
        suite.addTest(new ARCFileWriterTest() {
            protected void runTest() {
                try {
                    setResource();
                    long start = System.currentTimeMillis();
                    for (int i=0; i < count; i++) {
                       textWriteResource();
                    }
                    long duration = System.currentTimeMillis() - start;
                    System.out.println("total time to write " + count + " files:" + duration);
                    writer.close();
                } catch (ARCException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        junit.textui.TestRunner.run(suite);

    }
    
    public static void setResource() throws ARCException {
        File resource = new File(arcResource);
        FileInputStream fis;
        try {
            fis = new FileInputStream(resource);
            baos = new ByteArrayOutputStream(fis.available());
            byte[] buffer = new byte[512];
            int count = 0;
            BufferedInputStream bis = new BufferedInputStream(fis);
            while ((count = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            recordLength = baos.size();
            int jjj = arcResource.lastIndexOf(".");
            String ext = arcResource.substring(jjj + 1, arcResource.length());
            mimeType = new MimeTypeMapper().getMimeType(ext);
            baos.close();
        } catch (FileNotFoundException e) {
            throw new ARCException(e.getCause());
        } catch (IOException e) {
            throw new ARCException(e.getCause());
        }
    }
    
    public static void textWriteResource() throws ARCException {
        start = System.currentTimeMillis();
        long timeStamp = System.currentTimeMillis();
        writer.write(writer.getUUIDResourceURI(), 
                     "0.0.0.0", 
                     mimeType, 
                     baos, 
                     timeStamp, 
                     recordLength);
    }
}
