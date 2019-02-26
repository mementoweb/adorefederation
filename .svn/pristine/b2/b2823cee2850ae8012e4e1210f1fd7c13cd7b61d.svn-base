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

package gov.lanl.harvester;

import gov.lanl.xmltape.TapeRecord;
import gov.lanl.xmltape.TapeWriter;
import gov.lanl.harvester.HarvesterProperties;

import java.util.ArrayList;
import java.util.Date;

/**
 * Records2Tape.java<br>
 * <br>
 * The harvesting mode based on listidentifiers+Getrecord This is most
 * likely used for debug purpose, we can accurately identify which record causes
 * error. This method is also useful for extremely long record, such as base64
 * encoded binary files.
 *
 * @author Xiaoming Liu
 */

public abstract class Records2Tape {
    protected String _from;

    protected String _baseurl;

    protected String _set;

    protected String _prefix;

    protected String _until;

    protected TapeWriter _writer;

    protected String _resumptionToken;

    protected int count = 0;

    protected String metadataNamespace;

    public static final String StandardOAISchema = HarvesterProperties.getOaipmhSchemaURI();

    public static final String LocalOAISchema = HarvesterProperties.getLocalOaipmhSchemaURI();

    public Records2Tape(String baseurl, String from, String until, String set,
            String prefix, TapeWriter writer) throws Exception {
        _baseurl = baseurl;
        _from = from;
        _set = set;
        _until = until;
        _prefix = prefix;
        _writer = writer;

        //below are commented out because APS doesn't work correctly with
        // listFormats
        //replace with hardcoded value
        /*
         * gov.lanl.util.oai.oaiharvesterwrapper.ListMetadataFormats listFormats =
         * new
         * gov.lanl.util.oai.oaiharvesterwrapper.ListMetadataFormats(baseurl);
         * metadataNamespace=(String)(listFormats.getMetadataTable().get(prefix));
         */

        if (prefix.equals("didl")) {
            metadataNamespace = HarvesterProperties.DIDL_NS;
        } else if (prefix.equals("oai_dc")) {
            metadataNamespace = "http://www.openarchives.org/OAI/2.0/oai_dc/";
        } else if (prefix.equals("oai_didl")) {
            metadataNamespace = HarvesterProperties.DIDL_NS;
        }

    }

    protected void addOAIAdmin(String responseDate) throws Exception {
        StringBuffer oaischema = new StringBuffer(
                "xmlns=\"" + HarvesterProperties.getOaipmhSchemaURI()  + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  xsi:schemaLocation=\"" + HarvesterProperties.getOaipmhSchemaURI()  + " " + HarvesterProperties.getOaipmhSchemaURI() + "\"");
        //construct request element for tapeadmin
        StringBuffer request = new StringBuffer();
        request.append("<request ").append(oaischema).append(
                " verb=\"ListRecords\" ");
        if (_from != null)
            request.append(" from=\"").append(_from).append("\"");

        if (_until != null)
            request.append(" until=\"").append(_until).append("\"");

        if (_set != null)
            request.append(" set=\"").append(_set).append("\"");

        request.append(" metadataPrefix=\"").append(_prefix).append("\">");

        StringBuffer oaiadmin = new StringBuffer();

        oaiadmin.append("<responseDate ").append(oaischema).append(">").append(
                responseDate).append("</responseDate>\n").append(request)
                .append(_baseurl).append("</request>\n");
        _writer.addAdmin(oaiadmin.toString());

    }

    protected void writeRecord(
            gov.lanl.util.oai.oaiharvesterwrapper.Record record)
            throws java.io.IOException {
        gov.lanl.util.oai.oaiharvesterwrapper.Header header = record
                .getHeader();
        String provenance = Provenance.toXML(_baseurl, header.getIdentifier(),
                header.getDatestamp(), metadataNamespace, new Date(), false);
        ArrayList al = new ArrayList();
        al.add(provenance);
        TapeRecord taperecord = new TapeRecord(header.getIdentifier(), header
                .getDatestamp(), null, al, record.getRecordXML().replaceFirst(
                StandardOAISchema, LocalOAISchema));
        _writer.write(taperecord);
    }

    public abstract void start() throws Exception;

}
