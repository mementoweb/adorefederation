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

import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <code>SRUSearchRetrieveResponseParser</code> is a SAX parser implementation 
 * for SRUSearchRetrieveResponseParser content type.  The de-serializers passes 
 * in an empty SRUSearchRetrieveResponse object and parses the InputStream to 
 * populate the SRUSearchRetrieveResponse object.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class SRUSearchRetrieveResponseParser extends DefaultHandler implements SRUConstants {
    private StringBuffer value = new StringBuffer();
    private boolean inline = false;
    private SRUSearchRetrieveResponse sru;
    private SRURecord record;
    private Class recordImpl;
    private boolean rec = false;
    
    /**
     * Parses an InputStream to populate the specified DC object
     * @param sru empty SRUSearchRetrieveResponse object to populate
     * @param stream InputStream containing DC XML fragment
     * @throws Exception error occurred during parse routine
     */
    public void parse(SRUSearchRetrieveResponse sru, InputStream stream, SRURecord srcRecordimpl) throws Exception {
        this.sru = sru;
        this.recordImpl = srcRecordimpl.getClass();
        XMLReader parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
        parser.setFeature("http://xml.org/sax/features/namespaces", true);
        parser.setContentHandler(this);
        parser.parse(new InputSource(stream));
        sru.addRecord(record);
    }
    
    private SRURecord getNewSRURecord() throws Exception {
        return (SRURecord) recordImpl.newInstance();
    }
    
    /**
     * Receive notification of the beginning of an element. 
     * If SRU namespace is found in the uri, the element is flagged for extraction.
     */
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException {
        try {
            if (SRU_NAMESPACE.equals(uri) && localName.equals(TAG_SRU_SRR_VERSION))
                inline = true;
            else if (SRU_NAMESPACE.equals(uri) && localName.equals(TAG_SRU_SRR_REQUEST_QUERY))
                inline = true;
            else if (SRU_NAMESPACE.equals(uri) && localName.equals(TAG_SRU_SRR_RECORD))
                rec = true;
            else if (rec) {
                if (record != null) {
                   sru.addRecord(record);
                   record = getNewSRURecord();
                } else
                   record = getNewSRURecord();
                value = new StringBuffer();
            }
        } catch (Exception ex){
            throw new SAXException(ex);
        }
    }
    
    /**
     * Receive notification of the end of an element.
     * For record uri, checks localName against Key name, inserts dc value upon match.
     */
    public void endElement(
            String uri,
            String localName,
            String qName)
            throws SAXException{
        try {
            if (SRU_NAMESPACE.equals(uri)){
                if (localName.equals(TAG_SRU_SRR_VERSION)){
                    sru.setVersion(value.toString());
                    value = new StringBuffer();
                }
                if (localName.equals(TAG_SRU_SRR_REQUEST_QUERY) && value.length() > 0){
                    StringTokenizer st = new StringTokenizer(value.toString(), "=");
                    String key = st.nextToken();
                    String val = st.nextToken();
                    sru.setSearchRequest(key, val);
                    value = new StringBuffer();
                }
            } else if (rec){
                for (String key: record.getKeys()){
                    if (localName.equals(key)){
                        record.addKey(key, value.toString());
                        value = new StringBuffer();
                        break;
                    }
                }
                if (record.getValuePairs()==null){
                    throw new SAXException(localName + " is not expected");
                }
                rec = false;
            } else{
                throw new SAXException(uri +" namespace is not expected");
            }
            
        } catch (Exception e) {
            throw new SAXException("SAXException in endElement", e);
        }
    }
    
    /**
     * Appends element to a string buffer if element is flagged for extraction
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inline) {
            value.append(new String(ch, start, length));
        }
    }
    
    
}
