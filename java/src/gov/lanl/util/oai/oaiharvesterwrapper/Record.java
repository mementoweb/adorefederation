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

package gov.lanl.util.oai.oaiharvesterwrapper;

import java.util.Iterator;

/**
 * OAI Record Object
 */
public class Record {
    private ORG.oclc.oai.harvester.verb.Record orirecord;

    private String metadata;

    private Header header;

    private Iterator abouts;

    /**
     * Constructor - Takes OAI Record and Generates a Header Object
     * @param record
     */
    public Record(ORG.oclc.oai.harvester.verb.Record record) {
        metadata = record.getMetadata();
        header = new Header(record.getIdentifier(), record.getDatestamp(),
                record.getSetSpecs());
        header.setDeleted(record.getRecordXML().contains("status=\"deleted\""));
        abouts = record.getAbouts();
    }

    /**
     * Get the generated header object containing identifier, datastamp, set info
     * @return OAI Header Object
     */
    public Header getHeader() {
        return header;
    }

    /** 
     * Get the metadata associated with the current record
     * @return OAI metadata in String form
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Construct complete record with OAI-PMH namespace definition unfornately,
     * oclc harvest stipe off OAI-PMH namespace for record
     * @return Complete OAI Record in XML form
     */
    public String getRecordXML() {
        StringBuffer sb = new StringBuffer("");
        sb.append("<record xmlns=\"http://www.openarchives.org/OAI/2.0/\" ");
        sb.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
        sb.append("xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd\">\n");
        sb.append(header.getHeaderXML());
        if (!header.isDeleted()) {
            sb.append("\n<metadata>\n");
            sb.append(metadata);
            sb.append("\n</metadata>\n");
            if (abouts != null) {
                for (; abouts.hasNext();) {
                    sb.append("<about>\n");
                    sb.append((String) (abouts.next()));
                    sb.append("</about>\n");
                }
            }
        }
        sb.append("</record>\n");
        return sb.toString();
    }
}
