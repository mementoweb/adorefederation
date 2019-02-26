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

import java.util.Iterator;

import org.archive.io.arc.ARCRecord;
import org.archive.io.arc.ARCRecordMetaData;

public class ARCFileMetadata {
    
    private String fileURI;
    private String ipAddress;
    private String archiveDate;
    private String contentType;
    private String length;
    
    public ARCFileMetadata (String arcFilePath) {
        try {
            ARCFileReader arcReader = new ARCFileReader(arcFilePath, false);
            Iterator reader = arcReader.getIterator();
            ARCRecord record = null;
            ARCRecordMetaData data = null;
            record = (ARCRecord) reader.next();
            data = record.getMetaData();
            fileURI = data.getUrl();
            ipAddress = data.getIp();
            archiveDate = data.getDate();
            contentType = data.getMimetype();
            length = Long.toString(data.getLength());
        } catch (ARCException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the archiveDate.
     */
    public String getArchiveDate() {
        return archiveDate;
    }

    /**
     * @return Returns the contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @return Returns the fileURI.
     */
    public String getFileURI() {
        return fileURI;
    }

    /**
     * @return Returns the ipAddress.
     */
    public String getIpAddress() {
        return ipAddress;
    }
    
    /**
     * @return Returns the ipAddress.
     */
    public String getHeaderLength() {
        return length;
    }
    
    public String getArcFileHeader() {
        return fileURI + " " + ipAddress + " " + archiveDate + " " + contentType + " " + length;
    }
    
    
}
