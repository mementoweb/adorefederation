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

package gov.lanl.archive.trans.didl;

import gov.lanl.archive.trans.TransProperties;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ORG.oclc.oai.util.OAIUtil;
import java.io.CharArrayWriter;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SipParser.java
 * Program for parsing rtfsipDIDL stdin and passing back to sipdidlprocessor for handling.
 * cannot treat as xml since there may be incorrect embedded stuff in the rtfsipDIDL
 * 
 * @author goldsmith from luda's tapeparser
 */

public class SipParser extends DefaultHandler {
    public static final int INITIAL_BUFFER_SIZE = 1000 * 1024;

// pattern matching
   String recordmatch="(<rtfsipDIDL.*?</rtfsipDIDL>)";
   String identifier_regex="DIDLDocumentId=\"([^\"]+)";
   String datestamp_regex="diext:DIDLDocumentCreated=\"([^\"]+)";
   String meta_regex="(<didl:DIDL.*?</didl:DIDL>)";

    Pattern recordmatch_pattern = Pattern.compile(recordmatch, Pattern.DOTALL);;
    Pattern identifier_regex_pattern = Pattern.compile(identifier_regex);
    Pattern meta_regex_pattern = Pattern.compile(meta_regex,Pattern.DOTALL);
    Pattern datestamp_regex_pattern = Pattern.compile(datestamp_regex);

    FragmentProcessor _fragmentprocessor;

    public SipParser(java.io.InputStream stream, FragmentProcessor ip) throws Exception {
        super();
        InputStreamReader reader = new InputStreamReader(stream);
        _fragmentprocessor = ip;

        char[] buffer = new char[INITIAL_BUFFER_SIZE];
        int count = 0;
        CharArrayWriter charbuffer = new CharArrayWriter(INITIAL_BUFFER_SIZE);
        int recordend = 0;
        while ((count = reader.read(buffer)) != -1) {
            charbuffer.write(buffer, 0, count);
            String record = null;
            //search recordmatch_pattern
            String records = charbuffer.toString();
            Matcher recordmatch_matcher = recordmatch_pattern.matcher(records);
            while (recordmatch_matcher.find()) {
                record = recordmatch_matcher.group();
	        String identifier = recordIdentifier(record);
		String datestamp = recordDatestamp(record);
                recordend = recordmatch_matcher.end();
//System.out.println("identifier="+identifier);
//System.out.println("datestamp="+datestamp);
		_fragmentprocessor.processDidl(record, identifier,datestamp);
            } //while record
            if (recordend != 0) {
                //reset the buffer and put non-processed data
                charbuffer.reset();
                charbuffer.write(records.substring(recordend));
                recordend = 0;
            }
        } //while count buffer read
    }  //public taperead

   public String recordIdentifier(String record) {
                String identifier = null;
       		Matcher identifier_regex_matcher = identifier_regex_pattern
               		 .matcher(record);
		if (identifier_regex_matcher.find()) {
            		identifier = identifier_regex_matcher.group(1);
		}
      return identifier;
   }
   public String recordDatestamp(String record) {
		String datestamp = null;
		Matcher datestamp_regex_matcher = datestamp_regex_pattern
               		 .matcher(record);
		if (datestamp_regex_matcher.find()) {
   		         datestamp = datestamp_regex_matcher.group(1);
                }
        return datestamp;
    }
}
