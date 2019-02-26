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


public class CDXRecord implements Comparable {

    private String date;
    private String ipAddress;
    private String uri;
    private String mimetype;
    private String checksum;
    private String byteOffset;
    private String length;
    private String arcFileName;
    
    /**
     * @return Returns the arcFileName.
     */
    public String getArcFileName() {
        return arcFileName;
    }
    /**
     * @param arcFileName The arcFileName to set.
     */
    public void setArcFileName(String arcFileName) {
        this.arcFileName = arcFileName;
    }
    /**
     * @return Returns the byteOffset.
     */
    public String getByteOffset() {
        return byteOffset;
    }
    /**
     * @param byteOffset The byteOffset to set.
     */
    public void setByteOffset(String byteOffset) {
        this.byteOffset = byteOffset;
    }
    /**
     * @return Returns the checksum.
     */
    public String getChecksum() {
        return checksum;
    }
    /**
     * @param checksum The checksum to set.
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
    /**
     * @return Returns the date.
     */
    public String getDate() {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * @return Returns the ipAddress.
     */
    public String getIpAddress() {
        return ipAddress;
    }
    /**
     * @param ipAddress The ipAddress to set.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    /**
     * @return Returns the length.
     */
    public String getLength() {
        return length;
    }
    /**
     * @param length The length to set.
     */
    public void setLength(String length) {
        this.length = length;
    }
    /**
     * @return Returns the mimetype.
     */
    public String getMimetype() {
        return mimetype;
    }
    /**
     * @param mimetype The mimetype to set.
     */
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return uri;
    }
    /**
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.uri = url;
    }
    
    public int compareTo(Object arg0) {
        final int EQUAL = 0;
        final int DIFFERENT = -1;
        CDXRecord cdx2 = (CDXRecord) arg0;
        
        if (this.getUrl().equals(cdx2.getUrl())) 
               return EQUAL;
        else 
               return DIFFERENT;
    }
    
    
}

