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
 * ListRecords2Tape.java<br>
 *<br>
 * May be used to write OAI List Record result-set to a specified XML tape.

 * @author Xiaoming Liu
 */

public class ListRecords2Tape extends Records2Tape {

    static Logger log = Logger.getLogger(ListRecords2Tape.class.getName());
    
    public ListRecords2Tape(String baseurl, String from, String until,
            String set, String prefix, TapeWriter writer) throws Exception {
        super(baseurl, from, until, set, prefix, writer);

    }

    public void start() throws Exception {
        int count = 0;        
        
        gov.lanl.util.oai.oaiharvesterwrapper.ListRecords listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(
                _baseurl, _from, _until, _set, _prefix);
        addOAIAdmin(listRecords.getResponseDate());
        log.info(_baseurl + "?verb=ListRecords&from=" + _from + "&until="
                + _until + "&set=" + _set + "&metadataPrefix=" + _prefix
                + ",size=" + listRecords.size());
        count += listRecords.size();

        writeRecords(listRecords);

        String token = listRecords.getResumptionToken();
        while ((token != null) && (token.trim().length() != 0)) {
            //System.gc();

            listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(
                    _baseurl, token);
            log.info(_baseurl + "?verb=ListRecords&resumptionToken=" + token
                    + ", size=" + listRecords.size());
            count += listRecords.size();
            writeRecords(listRecords);
            token = listRecords.getResumptionToken();
        }
        log.info("Done -- harvested  " + count + " records");
    }

    private void writeRecords(
            gov.lanl.util.oai.oaiharvesterwrapper.ListRecords listrecords)
            throws java.io.IOException {
        ArrayList al = listrecords.getRecords();
        for (Iterator it = al.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) (it
                    .next());
            writeRecord(record);
        }
    }

}
