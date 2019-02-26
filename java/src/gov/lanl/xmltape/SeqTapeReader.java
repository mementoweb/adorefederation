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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/**
 * A sequential xmltape reader, implemented using the XMLPull API, which does not require an index. 
 * Will sequentially list all records in XMLTape.
 */

public class SeqTapeReader implements TapeConstants {
    private File tapefile;

    private String tapefilename;

    private boolean isOpen = false;

    static Logger log = Logger.getLogger(SeqTapeReader.class.getName());

    private final static String PROPERTY_XMLDECL_STANDALONE = "http://xmlpull.org/v1/doc/features.html#xmldecl-standalone";

    private XmlPullParserFactory factory;

    private XmlPullParser parser;

    private ArrayList<String> adminList = new ArrayList<String>();
    
    private String tapeAdminElement;

    /**
     * Constructor requiring absolute path to XMLTape
     * @param file
     *            location of xmltape
     */
    public SeqTapeReader(String file) throws TapeException {
        try {
            tapefilename = file;
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
        } catch (XmlPullParserException ex) {
            log.error("Error initializing SeqTapeReader: " + ex.getMessage());
            throw new TapeException(ex);
        }
    }

    /**
     * Open XMLTape. Opens XMLTape using XMLPullParser and set tape admin information.
     */
    public void open() throws TapeException {
        try {
            tapefile = new File(tapefilename);
            parser = factory.newPullParser();
            log.debug("Opening Tape: " + tapefile);
            parser.setInput(new FileInputStream(tapefile), "UTF-8");
            isOpen = true;

            // move cursor two element level lower to reach TapeAdmin section, if exist.
            nextStartTag();
            assert parser.getName().equals(TAPE_TAGNAME);
            nextStartTag();
	    if ((parser.getNamespace().equals(TapeProperties.TAPE_ADMIN_NS))  && (parser.getName().equals(TAPE_ADMIN_TAGNAME))){
		tapeAdminElement = parser.getText();
		tapeAdminElement = tapeAdminElement + copyFragment(TapeProperties.TAPE_ADMIN_NS, TAPE_ADMIN_TAGNAME);
		tapeAdminElement = tapeAdminElement + TAPE_ADMIN_FOOTER;
		
		// we expect tapeadmin section
		while ((parser.getNamespace().equals(TapeProperties.TAPE_ADMIN_NS))
		       && (parser.getName().equals(TAPE_ADMIN_TAGNAME))) {
		    logEvent();
		    adminList.add(copyFragment(TapeProperties.TAPE_ADMIN_NS, TAPE_ADMIN_TAGNAME));
		    
		    nextStartTag();
		}
		// now parser is at the location of "<tapeRecord>" if exists
	    }

        } catch (java.io.IOException ex) {
            log.error("Error opening XMLtape: " + ex.getMessage());
            throw new TapeException(ex);
        } catch (XmlPullParserException ex) {
            log.error("Error parsing XMLtape: " + ex.getMessage());
            throw new TapeException(ex);
        }
    }
    
    /**
     * List All tapeadmins as XML fragment
     */
    public ArrayList<String> getTapeAdmins() {
        assert isOpen;
        return adminList;
    }
    
    /**
     * Read next record. 
     * Locates next record instance and returns contents as TapeRecord object
     * 
     * @return taperecord, return null if the end of tape is reached the tape.
     */
    public synchronized TapeRecord next() throws TapeException {
        assert isOpen;

        try {
            logEvent();
            if (parser.getEventType() == XmlPullParser.END_DOCUMENT)
                return null;

            assert parser.getName().equals(TAPE_RECORD_TAGNAME);
            // to tapeRecordAdmin
            nextStartTag();
            // to identifier
            nextStartTag();
            String identifier = nextText();
            log.debug("Processing: " + identifier);

            // to datestamp
            nextStartTag();
            String date = nextText();

            // now let see if a recordAdmin exists
            nextStartTag();
            ArrayList<String> admin = new ArrayList<String>();
            logEvent();
            // we expect tapeadmin section
            while ((parser.getNamespace().equals(TapeProperties.TAPE_ADMIN_NS))
                    && (parser.getName().equals(RECORD_ADMIN_TAGNAME))) {
                admin.add(copyFragment(TapeProperties.TAPE_ADMIN_NS, RECORD_ADMIN_TAGNAME));
                nextStartTag();
            }

            String metadata = null;
            if ((parser.getNamespace().equals(TapeProperties.TAPE_ADMIN_NS))
                    && (parser.getName().equals(RECORD_TAGNAME))) {
                metadata = copyFragment(TapeProperties.TAPE_ADMIN_NS, RECORD_TAGNAME);
                TapeRecord record = new TapeRecord(identifier, date, null, admin, metadata);
                // move cursor to "tapeRecord" or end of tape
                nextStartTag();
                return record;
            }
            else {
                log.error("empty <record> in <tapeRecord>");
                throw new TapeException("empty <record> in <tapeRecord>");
            }
        } catch (Exception ex) {
            throw new TapeException(ex);
        }

    }

    /**
     * Close xmltape
     */
    public void close() throws TapeException {
        if (!isOpen)
            throw new TapeException("Tape is not open");
    }

    /**
     * Copy XMLFragment to serializer, and move the cursor to next START_TAG
     * 
     * @param namespace
     *            namespace of enclosing element
     * @param tagname
     *            namespace of encloding element
     * @param return
     *            XML Fragment, or null
     */
    private String copyFragment(String namespace, String tagname)
            throws XmlPullParserException, IOException {
        XmlSerializer serializer = factory.newSerializer();
        StringWriter sw = new StringWriter();
        serializer.setOutput(sw);
        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            // stop writing when encountering end of elemeent
            if ((parser.getEventType() == XmlPullParser.END_TAG)
                    && (parser.getNamespace().equals(namespace))
                    && (tagname.equals(parser.getName()))) {

                break;
            }

            else if ((parser.getEventType() == XmlPullParser.START_TAG)
                    && (parser.getNamespace().equals(namespace))
                    && (tagname.equals(parser.getName()))) {
                parser.nextToken();
            } else {
                writeToken(parser.getEventType(), serializer);
                parser.nextToken();
            }
        }
        sw.close();
        return sw.toString();
    }

    
    private void writeStartTag(XmlSerializer serializer)
            throws XmlPullParserException, IOException {
        // check for case when feature xml roundtrip is supported
        if (!parser
                .getFeature(XmlPullParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES)) {
            for (int i = parser.getNamespaceCount(parser.getDepth() - 1); i <= parser
                    .getNamespaceCount(parser.getDepth()) - 1; i++) {
                serializer.setPrefix(parser.getNamespacePrefix(i), parser
                        .getNamespaceUri(i));
            }
        }
        serializer.startTag(parser.getNamespace(), parser.getName());

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            serializer.attribute(parser.getAttributeNamespace(i), parser
                    .getAttributeName(i), parser.getAttributeValue(i));
        }
    }

    private void writeToken(int eventType, XmlSerializer serializer)
            throws XmlPullParserException, IOException {
        switch (eventType) {
        case XmlPullParser.START_TAG:
            writeStartTag(serializer);
            break;

        case XmlPullParser.END_TAG:
            serializer.endTag(parser.getNamespace(), parser.getName());
            break;

        case XmlPullParser.IGNORABLE_WHITESPACE:
            // comment it to remove ignorable whtespaces from XML infoset
            String s = parser.getText();
            serializer.ignorableWhitespace(s);
            break;

        case XmlPullParser.TEXT:
            serializer.text(parser.getText());
            break;

        case XmlPullParser.ENTITY_REF:
            serializer.entityRef(parser.getName());
            break;

        case XmlPullParser.CDSECT:
            serializer.cdsect(parser.getText());
            break;

        case XmlPullParser.PROCESSING_INSTRUCTION:
            serializer.processingInstruction(parser.getText());
            break;

        case XmlPullParser.COMMENT:
            serializer.comment(parser.getText());
            break;

        case XmlPullParser.DOCDECL:
            serializer.docdecl(parser.getText());
            break;
        }
    }

    /**
     * Skip until next START_TAG or END_DOCUMENT
     */
    private void nextStartTag() throws XmlPullParserException, IOException {
        // move the cursor to next Start_TAG
        do {
            logEvent();
            parser.nextToken();
            logEvent();
        } while (!((parser.getEventType() == XmlPullParser.START_TAG) || (parser
                .getEventType() == XmlPullParser.END_DOCUMENT)));

    }

    /**
     * Skip until next TEXT and read it into String
     */
    private String nextText() throws XmlPullParserException, IOException {
        // move the cursor to next Start_TAG
	StringBuffer sb=new StringBuffer();
	parser.nextToken();
        while(parser.getEventType() != XmlPullParser.END_TAG){
	    sb.append(parser.getText());
	    parser.nextToken();
        } 
        return sb.toString();

    }

    private void logEvent() throws XmlPullParserException {
        //log.debug(Integer.toString(parser.getEventType()) + " "
        //        + parser.getNamespace() + " " + parser.getName());
    }

    /**
     * Gets the entire TapeAdmin Element XML Fragment
     * @return
     *         tapeAdmin fragment including root element tag
     */
    public String getTapeAdminElement() {
        return tapeAdminElement;
    }

}
