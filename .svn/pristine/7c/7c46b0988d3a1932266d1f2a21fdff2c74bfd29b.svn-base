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

import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.util.xml.XmlUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * XMLTape Writer for tapes which are to be serialized to disc.
 */

public class SingleTapeWriter implements TapeConstants {
    private java.io.Writer _writer;

    private java.io.Writer tmpWriter;

    private File tmpFile;
    
    private int size = 0;

    static SimpleDateFormat formatter;

    private Date creationtime = null;

    private StringBuffer tapeadmin = new StringBuffer();
    
    private String xmlTapeID = null;
    
    private ArrayList<String> arcFileIDs = new ArrayList<String>();

    static Logger log = Logger.getLogger(SingleTapeWriter.class.getName());

    static {
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(tz);
    }

    /**
     * Constructor defining output configs for XMLtape, uses Java System Tmp
     * @param writer
     *           Initialized java.io.Writer object
     * @throws java.io.IOException
     */
    public SingleTapeWriter(java.io.Writer writer) throws java.io.IOException {
        this(writer, File.createTempFile("tmpXmlTape", null));
    }
    
    /**
     * Constructor defining output configs for XMLtape, specify tmp dir or file
     * @param writer
     *           Initialized java.io.Writer object
     * @param tmp 
     *           Temp Dir or File
     * @throws java.io.IOException
     */
    public SingleTapeWriter(java.io.Writer writer, File tmp) throws java.io.IOException {
        _writer = writer;
        
        if (tmp.isDirectory())
            tmpFile = new File(tmp, UUIDFactory.generateUUID().toString().substring(9) + ".xml");
        else
            tmpFile = tmp;
        
        tmpWriter = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(tmpFile), "UTF-8"));
        creationtime = new Date();

        _writer.write(TAPE_HEADER);
        
        // Ensure Clean-up
        tmpFile.deleteOnExit();
    }

    /**
     * Get Date object when XMLTape was created.
     * @return
     *      Date indicating XMLTape creation time
     */
    public Date getCreationTime() {
        return creationtime;
    }

    /**
     * Get entire TapeAdmin XML fragment stored in tapeadmin string buffer
     * @return
     *         XML Fragment of TapeAdmin, includes tape admin tags
     */
    public String getTapeAdmin() {
        return tapeadmin.toString();
    }

    /**
     * Write default TapeAdmin information, such as tool used and current time
     */
    public void writeDefaultAdmin() throws java.io.IOException {
        StringBuffer sb = new StringBuffer("");
        sb.append(TAPE_BASICS_HEADER);
        
        if (xmlTapeID != null) {
            sb.append(TAPE_BASICS_XMLTAPEID_HEADER);
            sb.append(xmlTapeID);
            sb.append(TAPE_BASICS_XMLTAPEID_FOOTER);
        }
        for(String id :arcFileIDs) {
            sb.append(TAPE_BASICS_ARCFILEID_HEADER);
            sb.append(id);
            sb.append(TAPE_BASICS_ARCFILEID_FOOTER);
        }

        sb.append(TAPE_BASICS_SOFTWARE_HEADER);
        sb.append(SingleTapeWriter.class.getName());
        sb.append(TAPE_BASICS_SOFTWARE_FOOTER);
        sb.append(TAPE_BASICS_TIME_HEADER);
        sb.append(formatter.format(creationtime));
        sb.append(TAPE_BASICS_TIME_FOOTER);
        sb.append(TAPE_BASICS_FOOTER);

        writeTapeAdmin(sb.toString());
    }

    /** 
     * Write TapeAdmin Fragment to XMLTape. 
     * Method appends Tape Admin Header and Footer tags.
     * @param admin
     *        XML Fragment to be stored in TapeAdmin element
     * @throws java.io.IOException
     */
    public void writeTapeAdmin(String admin) throws java.io.IOException {
        _writer.write(TAPE_ADMIN_HEADER);
        _writer.write(admin);
        _writer.write(TAPE_ADMIN_FOOTER);
        tapeadmin.append(TAPE_ADMIN_HEADER).append(admin).append(TAPE_ADMIN_FOOTER);
    }

    /**
     * Writes a TapeRecord to the initialized XMLTape Writer
     * @param record
     *          Valid TapeRecord Object
     * @throws java.io.IOException
     */
    public void writeRecord(TapeRecord record) throws java.io.IOException {
        tmpWriter.write(RECORD_HEADER);
        tmpWriter.write(RECORD_ADMIN_HEADER);
        tmpWriter.write("<ta:identifier>");
        tmpWriter.write(XmlUtil.encode(record.getIdentifier()));
        tmpWriter.write("</ta:identifier>\n");
        tmpWriter.write("<ta:date>");
        tmpWriter.write(record.getDatestamp());
        tmpWriter.write("</ta:date>\n");

        for (Iterator it = record.getAdmin().iterator(); it.hasNext();) {
            tmpWriter.write(RECORD_ANY_ADMIN_HEADER);
            tmpWriter.write((String) (it.next()));
            tmpWriter.write(RECORD_ANY_ADMIN_FOOTER);
        }
        
        tmpWriter.write(RECORD_ADMIN_FOOTER);
        tmpWriter.write("<ta:record>");
        tmpWriter.write(record.getMetadata());
        tmpWriter.write("</ta:record>");
        tmpWriter.write(RECORD_FOOTER);
        size = size + record.getMetadata().length() + HEADER_OVERHEAD;
    }

    private void writeRecords() throws java.io.IOException {
        try {
            final char nl = '\n';
            FileInputStream fis = new FileInputStream(tmpFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String aLine = null;
            while((aLine = in.readLine()) != null) {
                _writer.append(aLine);
                _writer.append(nl);
            }
            tmpFile.delete();
        } catch (FileNotFoundException e) {
            log.error("An error occured attempting to write records: " + e.getMessage());
            throw new java.io.IOException(e.getMessage()); 
        }
    }
    
    /**
     * Writes out closing XMLTape Element tags and closes the currently initialized writer.
     * @throws java.io.IOException
     */
    public void close() throws java.io.IOException {
        tmpWriter.close();
        writeRecords();
        _writer.write(TAPE_FOOTER);
        _writer.close();
    }

    /**
     * Gets the number of TapeRecords contained in current XMLTape
     * @return
     *       Number of TapeRecords contained in XMLTape
     */
    public int getSize() {
        return size;
    }

    /**
     * @param arcFileID 
     *          The arcFileID to set.
     * Note: Kept for backward compatibility, use addArcFileID.
     */
    public void setArcFileID(String arcFileID) {
        this.arcFileIDs.add(arcFileID);
    }
    
    /**
     * Sets the List of Arc Files Identifiers
     * @param arcFileIDs
     */
    public void setArcFileID(ArrayList<String> arcFileIDs) {
        this.arcFileIDs = arcFileIDs;
    }
    
    /**
     * @param arcFileID 
     *          The arcFileID to set.
     */
    public void addArcFileID(String arcFileID) {
        this.arcFileIDs.add(arcFileID);
    }

    /**
     * @param xmlTapeID 
     *          The xmlTapeID to set.
     */
    public void setXmlTapeID(String xmlTapeID) {
        this.xmlTapeID = xmlTapeID;
    }
}
