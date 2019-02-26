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

package gov.lanl.util.sru;

public interface SRUConstants {

    // SRU Constants
    public final static String SRW_NAMESPACE = "http://www.loc.gov/zing/srw/";
    public final static String SRW_PREFIX = "srw";
    
    // General XML Constants
    public final static String DEFAULT_MIME_TYPE = "application/xml; charset=utf-8";
    public final static String DEFAULT_PARSER_NAME = "com.sun.org.apache.xerces.internal.parsers.SAXParser";
    public final static String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    public final static String SCHEMA_LOCATION_ATT = "schemaLocation";
    public final static String XSI_PREFIX = "xsi";
    public final static String DEFAULT_ENCODING = "UTF-8";
    
    // SearchRetrieveResponse
    public final static String SRU_NAMESPACE = "http://www.loc.gov/zing/srw/";
    public final static String SRU_SCHEMA_LOCATION = "http://www.loc.gov/standards/sru/xml-files/srw-types.xsd";
    public static final String SRU_PREFIX = "srw";
    
    public final static String TAG_SRU_SRR = "searchRetrieveResponse";
    public final static String TAG_SRU_SRR_VERSION = "version";
    public final static String TAG_SRU_SRR_NUMREC = "numberOfRecords";   
    public final static String TAG_SRU_SRR_RECORDS = "records";
    public final static String TAG_SRU_SRR_RECORD = "record";
    public final static String TAG_SRU_SRR_SCHEMA = "recordSchema";
    public final static String TAG_SRU_SRR_PACKING = "recordPacking";
    public final static String TAG_SRU_SRR_DATA = "recordData";
    public final static String TAG_SRU_SRR_REQUEST = "echoedSearchRetrieveRequest";
    public final static String TAG_SRU_SRR_REQUEST_QUERY = "query"; 
}
