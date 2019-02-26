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
import gov.lanl.arc.ARCResourceNotFoundException;
import gov.lanl.util.csv.CSVReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/** 
 * CDXInstance.java<br>
 * <br>
 * Represents a set of CDXRecords, used as an in-memory index.<br>
 * <br>
 *     Usage:<br>
 *     String arcFile = _absolute-path-to-arc-file_<br>
 *     String identifier = _unique-resource-identifier_<br>
 *     CDXInstance cdx = new CDXInstance(arcFile);<br>
 *     int offset = cdx.getOffsetforIdentifier(identifier);<br>
 * <br>
 * Note: The CDXManager may be used to manage sets of CDXInstances.<br>
 *      
 * @author rchute
 */

public class CDXInstance implements Comparable {
    
    CDXRecord cdx;
    Set<CDXRecord> cdxSet = new TreeSet<CDXRecord>();
    String cdxInstanceId;
    
    /**
     * Create a CDXInstance from an CDX file
     * @param cdxFile
     * @throws ARCException
     */
    public CDXInstance(String cdxFile) throws ARCException {
        try {
            this.readHeritrixCDX(cdxFile);
            this.setCDXInstanceID(cdxFile);
        } catch (IOException e) {
            throw new ARCException("Error initializing CDXInstance " + e.getCause());
        }
    }
    
    private void readHeritrixCDX(String cdxFile) throws IOException  {
        BufferedReader cdxReader = null;
        String row;
        ArrayList fields;
        CSVReader reader = new CSVReader(' ');
		try {
			cdxReader = new BufferedReader(new FileReader(cdxFile));
			for (int line = 0; true; line++) {
				row = cdxReader.readLine();
				if (row == null)
					return;
				if (line > 0) {
					reader.parse(row);
					fields = reader.list;
					cdx = new CDXRecord();
					cdx.setDate((String) fields.get(0));
					cdx.setIpAddress((String) fields.get(1));
					cdx.setUrl((String) fields.get(2));
					cdx.setMimetype((String) fields.get(3));
					cdx.setChecksum((String) fields.get(5));
					cdx.setByteOffset((String) fields.get(6));
					cdx.setLength((String) fields.get(7));
					cdx.setArcFileName((String) fields.get(8));
					cdxSet.add(cdx);
				} else if (line == 0 && row.startsWith("CDX")) {
					row = row.substring(row.indexOf(" ") + 1, row.length());
					reader.parse(row);
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			cdxReader.close();
		}
	}

    /**
	 * Get mime type for specified resource identifier within the current
	 * CDXInstance
	 * 
	 * @param identifier -
	 *            unique resource identifier
	 * @return offset value of resource
	 * @throws ARCException
	 */
    public String getMimeTypeforIdentifier(String identifier) throws ARCException {
        CDXRecord c;
        for (Iterator i = cdxSet.iterator(); i.hasNext(); ) {
            c = (CDXRecord) i.next();
            if (c.getUrl().equals(identifier))
                return c.getMimetype();
          }
        throw new ARCResourceNotFoundException("Specified Identifier " + identifier + " was not found.");
    }
    
    /** 
     * Get byte offset for specified resource identifier within the current CDXInstance
     * @param identifier - unique resource identifier
     * @return offset value of resource
     * @throws ARCException
     */
    public int getOffsetforIdentifier(String identifier) throws ARCException {
        CDXRecord c;
        for (Iterator i = cdxSet.iterator(); i.hasNext(); ) {
            c = (CDXRecord) i.next();
            if (c.getUrl().equals(identifier))
                return Integer.parseInt(c.getByteOffset());
          }
        throw new ARCResourceNotFoundException("Specified Identifier " + identifier + " was not found.");
    }
    
    /**
     * Gets a byte array of identifiers
     * Format: id\n
     */
    public byte[] listIdentifiers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] newline = "\n".getBytes();

        CDXRecord c;
        for (Iterator i = cdxSet.iterator(); i.hasNext();) {
            c = (CDXRecord) i.next();
            String uri = c.getUrl();
            if (!uri.startsWith("filedesc")) {
                byte[] bytes = c.getUrl().getBytes();
                try {
                    baos.write(bytes);
                    baos.write(newline);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (baos != null)
                            baos.close();
                    } catch (Exception dbe) {
                    }
                }
            }
        }

        return baos.toByteArray();
    }
    
    /**
     * Gets a byte array of mimetypes
     * Format: mimetypes\n
     */
    public byte[] listMimetypes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        CDXRecord c;
        Set<String> set = new HashSet<String>();
        for (Iterator i = cdxSet.iterator(); i.hasNext();) {
            c = (CDXRecord) i.next();
            String type = c.getMimetype();
            if (!set.contains(type) && !c.getUrl().startsWith("filedesc")) {
                set.add(type);
            }
        }
        
        byte[] newline = "\n".getBytes();
        for (String type : set) {
            try {
                baos.write(type.getBytes());
                baos.write(newline);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null)
                        baos.close();
                } catch (Exception dbe) {
                }
            }
        }

        return baos.toByteArray();
    }
    
    /**
     * @return Returns the cdxInstanceId.
     */
    public String getCdxInstanceId() {
        return cdxInstanceId;
    }
    
    public int size() {
        return cdxSet.size();
    }
    
    private void setCDXInstanceID(String name) {
            String ext = "cdx";
            name = name.substring(0, name.length() - ext.length());
            cdxInstanceId = name.substring(name.lastIndexOf(System.getProperty("file.separator")), name.length());
    }
    
    /**
     * Simple Compare of two CDXInstances using cdxInstance to differentiate objects
     * @param arg0 - CDXInstance to compare current instance to
     * @return 0 if equal, -1 if different
     */
    public int compareTo(Object arg0) {
        final int EQUAL = 0;
        final int DIFFERENT = -1;
        CDXInstance cdx2 = (CDXInstance) arg0;
        
        if (this.getCdxInstanceId().equals(cdx2.getCdxInstanceId())) 
               return EQUAL;
        else 
               return DIFFERENT;
    }
    
    public Set<CDXRecord> getCDXRecords() {
        return cdxSet;
    }
    
    /**
     * Main - Used to quickly evaluate performance, prints read and offset lookup times
     * @param args - String[] where 0 = cdxFile and 1 = identifier
     */
    public static void main(String[] args) {
        try {
            System.out.println("---------------------------------------------");
            long start = System.currentTimeMillis();
            CDXInstance cdx = new CDXInstance(args[0]);
             long duration = (System.currentTimeMillis() - start);
             System.out.println("Read CDX Total Time: " + duration);
             System.out.println("---------------------------------------------");
             start = System.currentTimeMillis();
             int offset = cdx.getOffsetforIdentifier(args[1]);
             duration = (System.currentTimeMillis() - start);
             System.out.println("Offset Value: " + offset);
             System.out.println("Get Offset Total Time: " + duration);
             System.out.println("---------------------------------------------");
        } catch (ARCException e) {
            e.printStackTrace();
        }
    }
}
