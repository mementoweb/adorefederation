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

/**
 * Constants used by XMLTape files and in XMLTape file processing.
 */

public interface TapeConstants {
    
    public static final int    MAX_TAPE_SIZE=1000000000;
    public static final String DATE_TAGNAME="date";
    public static final String IDENTIFIER_TAGNAME="identifier";
    public static final String RECORD_ADMIN_FOOTER="</ta:tapeRecordAdmin>\n";
    public static final String RECORD_ADMIN_HEADER ="<ta:tapeRecordAdmin>\n";
    public static final String RECORD_ADMIN_TAGNAME="recordAdmin";
    public static final String RECORD_ANY_ADMIN_FOOTER="</ta:recordAdmin>\n";
    public static final String RECORD_ANY_ADMIN_HEADER ="<ta:recordAdmin>\n";
    public static final String RECORD_DATA_END="</ta:record>\n";
    public static final String RECORD_DATA_START="<ta:record>\n";
    public static final String RECORD_FOOTER="</ta:tapeRecord>\n";
    public static final String RECORD_HEADER="<ta:tapeRecord>\n";
    public static final String RECORD_TAGNAME="record";
    public static final String TAPE_ADMIN_FOOTER="</ta:tapeAdmin>\n";
    public static final String TAPE_ADMIN_TAGNAME="tapeAdmin";
    public static final String TAPE_DATESTAMP="ta:date";
    public static final String TAPE_RECORD_DATA="ta:record";
    public static final String TAPE_FOOTER="</ta:tape>";
    public static final String TAPE_IDENTIFIER="ta:identifier";
    public static final String TAPE_RECORD_ADMIN_TAGNAME="tapeRecordAdmin";
    public static final String TAPE_RECORD_TAGNAME="tapeRecord";
    public static final String TAPE_TAGNAME="tape";
    public static final String XSI_NS="http://www.w3.org/2001/XMLSchema-instance";
    public static final String TAPE_BASICS_XMLTAPEID_HEADER = "<tb:XMLtapeId>";
    public static final String TAPE_BASICS_XMLTAPEID_FOOTER = "</tb:XMLtapeId>";
    public static final String TAPE_BASICS_ARCFILEID_HEADER = "<tb:ARCfileId>";
    public static final String TAPE_BASICS_ARCFILEID_FOOTER = "</tb:ARCfileId>";
    public static final String TAPE_BASICS_TIME_TAGNAME = "processTime";
    public static final String TAPE_BASICS_TIME_HEADER = "<tb:processTime>";
    public static final String TAPE_BASICS_TIME_FOOTER = "</tb:processTime>";
    public static final String TAPE_BASICS_SOFTWARE_HEADER = "<tb:processSoftware>";
    public static final String TAPE_BASICS_SOFTWARE_FOOTER = "</tb:processSoftware>";
    public static final String TAPE_BASICS_FOOTER = "</tb:XMLtapeBasics>";
    
    
    public static String TAPE_BASICS_HEADER = "<tb:XMLtapeBasics xmlns:tb=\"" + TapeProperties.TAPE_BASICS_NS + "\"  xmlns:xsi=\""+ XSI_NS +"\"  xsi:schemaLocation=\""+ TapeProperties.TAPE_BASICS_NS+ " "+ TapeProperties.getTapeBasicsSchemaURI() +"\">\n";
    public static String TAPE_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<ta:tape  xmlns:ta=\""+ TapeProperties.TAPE_ADMIN_NS +"\" xmlns:xsi=\""+ XSI_NS +"\"  xsi:schemaLocation=\""+ TapeProperties.TAPE_ADMIN_NS+ " "+ TapeProperties.getTapeAdminSchemaURI() +"\">\n";
    public static String TAPE_ADMIN_HEADER = "<ta:tapeAdmin  xmlns:ta=\"" + TapeProperties.TAPE_ADMIN_NS + "\"  xmlns:xsi=\""+ XSI_NS + "\"  xsi:schemaLocation=\"" + TapeProperties.TAPE_ADMIN_NS + " " + TapeProperties.getTapeAdminSchemaURI() + "\">\n";
    public static int    HEADER_OVERHEAD = TAPE_ADMIN_HEADER.length() + TAPE_ADMIN_FOOTER.length() + RECORD_HEADER.length() + RECORD_FOOTER.length() + RECORD_ADMIN_HEADER.length() + RECORD_ADMIN_FOOTER.length();

}