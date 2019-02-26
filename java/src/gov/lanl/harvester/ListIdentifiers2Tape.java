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

import gov.lanl.xmltape.TapeWriter;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * ListIdentifiers2Tape.java <br>
 * <br>
 * May be used to write OAI List Identifiers result-set to a specified XML tape.
 * 
 * @author Xiaoming Liu
 */

public class ListIdentifiers2Tape extends Records2Tape {

    static Logger log = Logger.getLogger(ListIdentifiers2Tape.class.getName());
    
    public ListIdentifiers2Tape(String baseurl, String from, String until,
            String set, String prefix, TapeWriter writer) throws Exception {
        super(baseurl, from, until, set, prefix, writer);

    }

    public void start() throws Exception {
        int count = 0;
        ArrayList identifierList = new ArrayList();
        log.info(_baseurl + "&verb=ListIdentifiers&from=" + _from
                + "&until=" + _until + "&set=" + _set + "&metadataPrefix="
                + _prefix);
        gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers listIdentifiers = new gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers(
                _baseurl, _from, _until, _set, _prefix);
        addOAIAdmin(listIdentifiers.getResponseDate());

        log.info("succeed: size=" + listIdentifiers.size());
        count += listIdentifiers.size();
        identifierList.addAll(listIdentifiers.getIdentifiers());
        String token = listIdentifiers.getResumptionToken();
        while ((token != null) && (token.trim().length() != 0)) {
            System.gc();
            log.info(_baseurl + "verb=ListIdentifiers&resumptionToken="
                    + token);
            listIdentifiers = new gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers(
                    _baseurl, token);
            log.info("succeed: size=" + listIdentifiers.size());
            count += listIdentifiers.size();
            identifierList.addAll(listIdentifiers.getIdentifiers());
            token = listIdentifiers.getResumptionToken();
        }
        log.info("Done -- reading identifiers  " + count + " records");

        for (Iterator it = identifierList.iterator(); it.hasNext();) {
            String identifier = (String) (it.next());
            log.info(_baseurl + "verb=GetRecord&identifier=" + identifier
                    + "&metadataPrefix=" + _prefix);
            gov.lanl.util.oai.oaiharvesterwrapper.GetRecord getrecord = new gov.lanl.util.oai.oaiharvesterwrapper.GetRecord(
                    _baseurl, identifier, _prefix);
            log.info("succeed");
            writeRecords(getrecord);

        }
        log.info("Done -- harvested  " + count + " records");
    }

    private void writeRecords(
            gov.lanl.util.oai.oaiharvesterwrapper.GetRecord getrecord)
            throws java.io.IOException {
        ArrayList al = getrecord.getRecords();
        for (Iterator it = al.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) (it
                    .next());
            writeRecord(record);
        }
    }

}
