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

package gov.lanl.YAParser;

import java.io.BufferedInputStream;

/**
 * Yet Another XML Parser<br>
 * <br>
 * This very simple XML Parser is built to get byte offset information of XML
 * tags, which is lacked in SAX, DOM, and XMLPull APIs (e.g.
 * org.xml.sax.Locator), and in lower level APIs of several popular Java XML
 * parsers: Xerces and XP, etc.<br>
 * <br>
 * The idea of this parser is borrowed from QParser<http://www.javaworld.com/javaworld/javatips/jw-javatip128.html>.
 * Some features of QParser are dropped such as XML processing insturctions,
 * entity, and comments, they are ignored in the parsing process. Improvements
 * over QParser include buffered input and accurate byte offset. <br>
 * Usage is similar to SAX parser by implementing DocHandler.
 * 
 * <pre>
 * FileInputStream fr = new FileInputStream(file);
 * YAParser parser = new YAParser();
 * parser.setLocator(loc);
 * parser.setDocHandler(reporter);
 * parser.parse(fr);
 * </pre>
 * 
 * <br>
 * Known problems: In order to calculate byte offset, lower level java io APIs
 * are used, translation from byte array to corresponding characters is left to
 * users. Entity, CharacterText are ignored in the processing. <br>
 * Suggestion: YAParser's usage should be strictly limited to calculating byte
 * offset. For other purpose please use standard XML Parsers.
 * 
 * @author liu_x
 * 
 * 
 */

public class YAParser {

    DocHandler doc = null;

    Locator loc = null;

    private byte[] buffer = null;

    public final static int BUFFERSIZE = 100000;
    
    private int PRE = 0, OPEN_TAG = 1, CLOSE_TAG = 2,
            TEXT = 3, SINGLE_TAG = 4, OTHER = 5, IN_TAG = 6, START_TAG = 7,
            PRETEXT = 8, QUOTE = 9, CDATA = 10, cursor = 0;
    
    private long totalsize = 0;

    /**
     * Sets the DocHandler implementation
     * 
     * @param doc
     *            processing class implementing DocHandler interface
     */
    public void setDocHandler(DocHandler doc) {
        this.doc = doc;
    }

    /**
     * Sets Locator instance used to manage byte offsets in document
     * 
     * @param loc
     *            locator instance to manage offsets
     */
    public void setLocator(Locator loc) {
        this.loc = loc;
    }

    /**
     * Parses InputStream and determines byte offsets for element flags defined
     * in DocHandler implemenation
     * 
     * @param in
     *            InputStream to be parsed
     * @throws ParserException
     * @throws java.io.IOException
     * @throws Exception
     */
    public void parse(java.io.InputStream in) throws ParserException,
            java.io.IOException, Exception {
        buffer = new byte[BUFFERSIZE];
        long size = 0, depth = 0;
        int mode = PRE;
        long start = 0;
        StringBuffer sb = new StringBuffer();

        // placeholder for last two bytes in CDATA section, useful for checking
        // when CDATA is ended.
        char[] cdata = new char[2];
        String tagName = null;
        loc.setStartIndex(0);
        loc.setEndIndex(0);
        doc.startDocument();

        BufferedInputStream bin= new BufferedInputStream(in);
        while ((size = bin.read(buffer, 0, BUFFERSIZE)) != -1) {
            for (cursor = 0; cursor < size; cursor++) {
                char c = (char) buffer[cursor];
                if (mode == PRE) {
                    if (c == '<') {
                        mode = START_TAG;
                    }
                    start = totalsize + cursor;
                } else if (mode == OTHER) {
                    if (c == '>') {
                        mode = PRETEXT;
                    }
                } else if (mode == CDATA) {
                    if ((c == '>') && (cdata[0] == ']') && (cdata[1] == ']'))
                        mode = PRETEXT;
                    // 2006-02-02 rc - Added to handle comments
                    else if ((c == '>') && (cdata[0] == '-') && (cdata[1] == '-')) {
                        mode = PRETEXT;
                    }
                    else {
                        cdata[0] = cdata[1];
                        cdata[1] = c;
                    }

                } else if (mode == START_TAG) {
                    if (c == '/') {
                        mode = CLOSE_TAG;
                        tagName = null;
                    } else if (c == '?') {
                        mode = OTHER;
                    } else if (c == '!') {
                        mode = CDATA;
                    } else {
                        mode = OPEN_TAG;
                        tagName = null;
                        sb.append(c);
                    }
                }

                else if (mode == OPEN_TAG) {
                    if (c == '>') {
                        if (tagName == null)
                            tagName = sb.toString();
                        sb.setLength(0);
                        depth++;
                        loc.setStartIndex(start);
                        loc.setEndIndex(totalsize + cursor);
                        doc.startElement(tagName);
                        tagName = null;
                        mode = PRETEXT;
                    } else if (c == '/') {
                        mode = SINGLE_TAG;
                    } else if (Character.isWhitespace(c)) {
                        tagName = sb.toString();
                        sb.setLength(0);
                        mode = IN_TAG;
                    } else {
                        sb.append(c);
                    }

                } else if (mode == IN_TAG) {
                    if (c == '>') {
                        loc.setStartIndex(start);
                        loc.setEndIndex(totalsize + cursor);
                        doc.startElement(tagName);
                        depth++;
                        tagName = null;
                        mode = PRETEXT;
                    }
                    // in quote like <foo a="b">
                    else if (c == '"') {
                        mode = QUOTE;
                    } else if (c == '/') {
                        mode = SINGLE_TAG;
                    }

                }
                // we don't process attribute quote
                else if (mode == QUOTE) {
                    if (c == '"') {
                        mode = IN_TAG;
                    }
                }

                else if (mode == SINGLE_TAG) {
                    if (tagName == null) {
                        tagName = sb.toString();
                    }
                    if (c != '>') {
                        throw new ParserException("Expected > for tag: <"
                                + tagName + "/>", loc);
                    }
                    loc.setStartIndex(start);
                    loc.setEndIndex(totalsize + cursor);
                    doc.startElement(tagName);
                    doc.endElement(tagName);
                    tagName = null;
                    sb.setLength(0);
                    mode = PRETEXT;

                    if (depth == 0) {
                        doc.endDocument();
                        return;
                    }
                }

                else if (mode == CLOSE_TAG) {
                    if (c == '>') {
                        tagName = sb.toString();
                        sb.setLength(0);
                        depth--;
                        loc.setStartIndex(start);
                        loc.setEndIndex(totalsize + cursor);
                        doc.endElement(tagName);
                        if (depth == 0) {
                            doc.endDocument();
                            return;
                        }
                        mode = PRETEXT;
                    } else {
                        sb.append(c);
                    }
                } else if (mode == PRETEXT) {
                    if (c == '<') {
                        mode = START_TAG;
                        start = totalsize + cursor;
                    } else {
                        mode = TEXT;
                        start = totalsize + cursor;
                    }
                } else if (mode == TEXT) {
                    if (c == '<') {
                        doc.text(buffer, (int) (start - totalsize),
                                (int) (cursor - start + totalsize));
                        mode = START_TAG;
                        start = totalsize + cursor;
                    }
                }

            }
            //end for

            //flush text before enter another buffer
            if (mode == TEXT) {
                doc.text(buffer, (int) (start - totalsize),
                        (int) (size - start + totalsize));
                start = totalsize + size;
            }

            totalsize += size;
        }
        //end while
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getCursor() {
        return cursor;
    } 
    
    public long getCurrentIndex() {
        return totalsize + cursor;
    } 
}
