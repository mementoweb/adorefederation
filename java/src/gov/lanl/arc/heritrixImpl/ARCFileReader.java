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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.archive.io.arc.ARCReader;
import org.archive.io.arc.ARCReaderFactory;
import org.archive.io.arc.ARCRecord;

/**
 * ARCFileReader
 * 
 * <p>Class to read content from an arc file archive.  Currently, the class does not support 
 * compressed archives.</p>
 * 
 *   Usage:<br>
 *   String arcDir = "/lanl/repository/arc/";<br>
 *   String arcFile = arcDir + "EPRINT_30be7941-9d7f-48f1-9fa3-d3a023ae1774.arc";<br>
 *   String identifier = "info:lanl-repo/ds/678be854-1975-4e18-826d-93754a5779dc";<br>
 *   // Create ARCFileReader, a CDXInstance is automatically generated<br>
 *   ARCFileReader reader = new ARCFileReader(arcFile);<br>
 *   // Write a resourse to disc<br>
 *   reader.writeResource(identifier, arcDir + identifier + ".pdf");<br>
 *   // Or create a ByteArrayOutputStream for a resource using it's identifier<br>
 *   OutputStream idbaos = reader.getDataStreamUsingID(identifier);<br>
 *   idbaos.close();<br>
 *   // Or create a ByteArrayOutputStream from a byte offset<br>
 *   int offset = reader.getCdxInstance().getOffsetforIdentifier(identifier);<br>
 *   OutputStream offsetbaos = reader.getDataStreamUsingOffset(offset);<br>
 *   offsetbaos.close();<br>
 *
 * @author rchute 
 */

public class ARCFileReader {

    private static final String CDXSUFFIX = "cdx";
    private static final String ARCSUFFIX = "arc";
    
    private ARCReader arcReader;
    private String arcFile;
    private String arcReaderID;
    private CDXInstance cdxInstance = null;

    static Logger log = Logger.getLogger(ARCFileReader.class.getName());
    
    /**
     * Construtor which accepts URL or path to an ARC file
     * @param file - Absolute path of ARC File
     * @throws ARCException
     */
    public ARCFileReader(String file) throws ARCException {
        this(file, true);
    }
    
    /**
     * Construtor allows the index to be disabled
     * @param file - Absolute path of ARC File
     * @param useIndex - creates in internally managed CDX Instance
     * @throws ARCException
     */
    public ARCFileReader(String file, boolean useIndex) throws ARCException {
        try {
            arcFile = file;
            log.debug("ArcFile: " + arcFile);
            File arc = new File(arcFile);
            if (arc.getCanonicalFile().exists()) {
                arcReader = ARCReaderFactory.get(arc.getCanonicalFile());
            } else {
                throw new ARCException("Unable to access file: " + arc.getAbsolutePath());
            }
                
            this.setArcReaderID(file);
            
            if (useIndex) {
                // Generate Index if not present
                String cdxPath = stripExtension(file, ARCSUFFIX) + CDXSUFFIX;
                File cdxFile = new File(cdxPath);
                if (!cdxFile.exists())
                  this.generateIndex();
                cdxInstance = new CDXInstance(cdxPath);
            }

        } catch (Exception e) {
            throw new ARCException(e.getCause());
        }
    }
    
    public static boolean isRunning(Process process) {
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }
    
    /**
     * Get the ARC Reader ID - arc file name minus path & extension data
     * @return arc file name minus path / ext.
     */
    public String getArcReaderID() {
        return arcReaderID;
    }
    
    /** 
     * Called by constructor to set ARCID for ArcReader Instance
     * Used by the ARCManager when iterating through ARCReaders
     * @param name
     */
    private void setArcReaderID(String name) {
        String ext = "arc";
        name = name.substring(0, name.length() - ext.length());
        arcReaderID = name.substring(name.lastIndexOf(System.getProperty("file.separator")), name.length());
    }
    
    /**
     * Gets an Iterator object for the Heritrix ARCFileReader
     * @return ARCFileReader Iterator
     */
    public Iterator getIterator() {
        return arcReader.iterator();
    }
    
    /**
     * Direct access to Heritrix API's ARCFileReader
     * @return Heritrix ARCFileReader
     */
    public Iterator getARCReader() {
        return arcReader;
    }
    
    /**
     * Gets ARCRecord for record starting at specified offset
     * @return Heritrix ARCFileReader Object
     */
    public ARCRecord getRecordAtOffset(long offset) throws ARCException {
        ARCRecord record = null;
        try {
            record = arcReader.get(offset);
        } catch (IOException e) {
            throw new ARCException(e.getCause());
        }
        return record;
    }
    
    /**
     * Generates an CDX Index file for the current arc file
     * CDX file is written to same directory as the ARC File
     */
    public void generateIndex() throws ARCException {
        try {
            ARCReader.createCDXIndexFile(arcFile);
        } catch (IOException e) {
            throw new ARCException(e.getCause());
        } catch (ParseException e) {
            throw new ARCException(e.getCause());
        }
    }
    
    public void close() throws IOException {
    	if (arcReader != null) {
    		arcReader.close();
    	}
    }
    
    /**
     * Get the complete arc record for a specified resource identifier
     * @param identifier - Unique Identifier for resource contained in arc file
     * @return ARCRecord for specified identifier
     * @throws ARCException
     */
    public ARCRecord getRecordByIdentifier(String identifier) throws ARCException {
        // If Available, use index.
        if (cdxInstance != null) {
            int offset = cdxInstance.getOffsetforIdentifier(identifier);
            return this.getRecordAtOffset(offset);
        }
        // Otherwise, iterate through the ARCRecords till we find what we need
        else {
            Iterator reader = arcReader.iterator();
            ARCRecord record = null;
            String url;
            while (reader.hasNext()) {
                record = (ARCRecord) reader.next();
                 url = record.getMetaData().getUrl();
                 //System.out.println(url);
                 if (url.equals(identifier))
                           return record;
            }
        }
        throw new ARCException("Specified identifier, " + identifier + " not found.");    
    }
    
    /**
     * Writer Extracted Resource to Disc
     * @param identifier - Unique ID of resource contained in current ARCFileReader file
     * @param filePath - Path of the file to be exported
     * @throws ARCException
     */
    public void writeResource(String identifier, String filePath) throws ARCException {
        ByteArrayOutputStream baos = getDataStreamUsingID(identifier);
        File outputFile = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outputFile);
            fos.write(baos.toByteArray());
            fos.close();
        } catch (FileNotFoundException e) {
            throw new ARCException("Error creating file: \n " + e.getCause());
        } catch (IOException e) {
            throw new ARCException("Error during I/O: \n " + e.getCause());
        }
    }
    
    /**
     * Gets a resource as a ByteArrayOutputStream based on a byte offset.
     * This method is useful when an external index is used for determine the offset.
     * @param offset - Byte Offset from start of arc file to the target resource
     * @return ByteArrayOutputStream for specified offset value
     * @throws ARCException
     */
    public ByteArrayOutputStream getDataStreamUsingOffset(long offset) throws ARCException {
        ARCRecord r = this.getRecordAtOffset(offset);
        ByteArrayOutputStream baos = this.createOutputStream(r);
         return baos;
    }
    
    /**
     * Gets a resource as a ByteArrayOutputStream based on an identifier.
     * @param identifier - unique identifier of the target resource
     * @return ByteArrayOutputStream for specified identifier
     * @throws ARCException
     */
    public ByteArrayOutputStream getDataStreamUsingID(String identifier) throws ARCException {
        ARCRecord r = this.getRecordByIdentifier(identifier);
        ByteArrayOutputStream baos = this.createOutputStream(r);
        return baos;
    }
    
    protected ByteArrayOutputStream createOutputStream(ARCRecord r) throws ARCException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(r.available());

            if (r.getMetaData().getMimetype().contains("xml")) {
                byte b = "<".getBytes()[0];
                while (r.read() != b) {}
                baos.write(b);
            }
            
            // Buffered Bytes Method
            byte[] buffer = new byte[512];
            int count = 0; 
            BufferedInputStream bis = new BufferedInputStream(r);
            while  ((count = bis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
            } 
            
             baos.close();
             return baos;
        } catch (IOException e) {
            throw new ARCException("Error in createOutputStream: \n " + e.getCause());
        }
    }
    
    /** 
     * Simple Utility to strip a file extension from a path/filename
     * @param name - absolute path/filename to extension from
     * @param ext - extension to be removed
     * @return Name minus extension, if name ends with ext.
     */
    public static String stripExtension(String name, String ext) {
        if (name.endsWith(ext)) {
            name = name.substring(0, name.length() - ext.length());
        }
        return name;
    }

    /**
     * @return Returns the cdxInstance, generates Index & CDXInstance if null
     * @throws ARCException 
     */
    public CDXInstance getCdxInstance() throws ARCException {
        if (cdxInstance == null) {
            try {
                this.generateIndex();
                String cdxPath = stripExtension(arcFile, ARCSUFFIX) + CDXSUFFIX;
                cdxInstance = new CDXInstance(cdxPath);
            } catch (ARCException e) {
                throw new ARCException("Error in generating index: \n " + e.getCause());
            }
        }
        return cdxInstance;
    }
    
    public void clearCdxInstance()  {
        cdxInstance = null;
    }   
}
