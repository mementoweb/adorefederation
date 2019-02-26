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
 * StreamListIdentifiers.java<br>
 * <br>
 * May be used to stream OAI List Identifier result-set to stdout.
 * 
 * @author Xiaoming Liu
 */

public class StreamListIdentifiers {
    private String _from;

    private String _baseurl;

    private String _set;

    private String _prefix;

    private String _until;

    private PrintStream _printer;

    private String _resumptionToken;

    private int count = 0;
    
    static Logger log = Logger.getLogger(StreamListIdentifiers.class.getName());

    public StreamListIdentifiers(String baseurl, String from, String until,
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
        gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers listIdentifiers = new gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers(
                _baseurl, _from, _until, _set, _prefix);
        log.info(_baseurl + " from=" + _from + " until=" + _until + " set="
                + _set + " prefix=" + _prefix + "size="
                + listIdentifiers.size());
        count += listIdentifiers.size();
        byte[] contents = listIdentifiers.getResponseBuffer();
        _printer.write(contents, 0, contents.length);

        String token = listIdentifiers.getResumptionToken();
        while ((token != null) && (token.trim().length() != 0)) {
            // System GC calls will not clean items still in context
            //System.gc();
            listIdentifiers = new gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers(
                    _baseurl, token);
            log.info(_baseurl + "token==" + token + " size="
                    + listIdentifiers.size());
            count += listIdentifiers.size();
            contents = listIdentifiers.getResponseBuffer();
            _printer.write(contents, 0, contents.length);
            token = listIdentifiers.getResumptionToken();
        }
        log.info("Done -- harvested  " + count + " records");
    }

}
