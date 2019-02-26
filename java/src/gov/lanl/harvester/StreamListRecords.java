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

import java.io.PrintStream;

import org.apache.log4j.Logger;

/**
 * StreamListRecords.java<br>
 *<br>
 * May be used to stream OAI List Record result-set to stdout.
 * 
 * @author Xiaoming Liu
 */

public class StreamListRecords {
    private String _from;

    private String _baseurl;

    private String _set;

    private String _prefix;

    private String _until;

    private PrintStream _printer;

    private String _resumptionToken;

    private int count = 0;
    
    static Logger log = Logger.getLogger(StreamListRecords.class.getName());

    public StreamListRecords(String baseurl, String from, String until,
            String set, String prefix, PrintStream pr) throws Exception {
        _baseurl = baseurl;
        _from = from;
        _set = set;
        _until = until;
        _prefix = prefix;
        _printer = pr;

    }

    public void start() throws Exception {
        int count = 0;
        gov.lanl.util.oai.oaiharvesterwrapper.ListRecords listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(
                _baseurl, _from, _until, _set, _prefix);
        log.info(_baseurl + "&verb=ListRecords&from=" + _from + "&until="
                + _until + "&set=" + _set + "&metadataPrefix=" + _prefix
                + ", size=" + listRecords.size());
        count += listRecords.size();
        byte[] contents = listRecords.getResponseBuffer();
        _printer.write(contents, 0, contents.length);

        String token = listRecords.getResumptionToken();
        while ((token != null) && (token.trim().length() != 0)) {
            //System.gc();

            listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(
                    _baseurl, token);
            log.info(_baseurl + "&verb=ListRecords&resumptionToken=="
                    + token + " , size=" + listRecords.size());
            count += listRecords.size();
            contents = listRecords.getResponseBuffer();
            _printer.write(contents, 0, contents.length);
            token = listRecords.getResumptionToken();
        }
        log.info("Done -- harvested  " + count + " records");
    }

}
