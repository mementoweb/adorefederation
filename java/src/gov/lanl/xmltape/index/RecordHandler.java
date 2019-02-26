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

package gov.lanl.xmltape.index;

import gov.lanl.YAParser.Locator;
import gov.lanl.YAParser.ParserException;
import gov.lanl.YAParser.YAParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * Random Access Tape Record Accessor
 * 
 */
public class RecordHandler {

    private YAParser parser = new YAParser();

    private Locator loc = null;

    private TapeIndexer indexer = null;

    private RandomAccessFile tapefile = null;

    /**
     * Gets the byte offset of record start from start of tape
     * 
     * @return offset value re record begins
     */
    public long getRecordStart() {
        return (new String(parser.getBuffer(), (int) parser.getCursor(),
                (int) (YAParser.BUFFERSIZE - parser.getCursor())).indexOf("<"))
                + parser.getCurrentIndex();
    }

    /**
     * Gets the byte length of record
     * 
     * @param start
     *            byte offset of record start from start of tape
     * @param end
     *            byte offset of record end from start of tape minus tape record
     *            element length
     * @return
     *            the byte length of record
     * @throws ParserException
     */
    public int getRecordOffset(long start, long end) throws ParserException {
        long length = end - start;
        long marker = start > YAParser.BUFFERSIZE ? start - YAParser.BUFFERSIZE
                : start;
        String str = "";
        if (marker + length > YAParser.BUFFERSIZE) {
            try {
                tapefile.seek(start);
                byte[] buffer = new byte[(int) length];
                tapefile.read(buffer);
                str = new String(buffer);
            } catch (IOException e) {
                throw new ParserException(e.getMessage(), loc);
            } catch (Exception e) {
                throw new ParserException(e.getMessage(), loc);
            }
        } else {
            str = new String(parser.getBuffer(), (int) marker, (int) length);
        }

        return str.substring(0, str.lastIndexOf(">")).getBytes().length + 1;
    }

    /**
     * Gets tape record contents given the records start index and its offset
     * value
     * 
     * @param start
     *            Byte index of where record begins
     * @param offset
     *            Byte length of record
     * @return Tape Record Contents
     * @throws ParserException
     */
    public String getRecord(long start, long offset) throws ParserException {
        String record = null;
        long marker = start > YAParser.BUFFERSIZE ? start - YAParser.BUFFERSIZE
                : start;
        if (marker + offset > YAParser.BUFFERSIZE) {
            try {
                tapefile.seek(start);
                byte[] buffer = new byte[(int) offset];
                tapefile.read(buffer);
                record = new String(buffer, "UTF-8");
            } catch (IOException e) {
                throw new ParserException(e.getMessage(), loc);
            } catch (Exception e) {
                throw new ParserException(e.getMessage(), loc);
            }
        } else {
            try {
                record = new String(parser.getBuffer(), (int) marker,
                        (int) offset, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new ParserException(e.getMessage(), loc);
            }
        }
        return record.substring(record.indexOf("<"),
                record.lastIndexOf(">") + 1);
    }

    /**
     * Sets the Locator instance the parser will use
     * 
     * @param loc
     *            Locator instance to store start and length values
     */
    public void setLocator(Locator loc) {
        this.loc = loc;
    }

    /**
     * Sets the parser
     * 
     * @param indexer
     *            an initialized TapeIndexer instance
     */
    public void setDocHandler(TapeIndexer indexer) {
        this.indexer = indexer;
    }

    /**
     * Parses the given file using set doc handler and Locator
     * 
     * @param file
     *            Path to file
     * @throws ParserException
     */
    public void parse(String file) throws ParserException {
        try {
            FileInputStream fr = new FileInputStream(file);
            tapefile = new RandomAccessFile(file, "r");
            parser.setLocator(loc);
            parser.setDocHandler(indexer);
            parser.parse(fr);
            fr.close();
        } catch (IOException e) {
            throw new ParserException(e.getMessage(), loc);
        } catch (Exception e) {
            throw new ParserException(e.getMessage(), loc);
        }
    }
}
