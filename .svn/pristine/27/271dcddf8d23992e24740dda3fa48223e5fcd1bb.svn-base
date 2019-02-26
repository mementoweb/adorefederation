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

package gov.lanl.xmltape.oai;

import org.apache.log4j.Logger;

/**
 * OAI2TapeAdapter.java 1286 2005-01-21 19:25:16Z liu_x $
 * Translate OAI request to corresponding xmltape requests in oaicat
 * configuration, forcedDatetamp and forcedSets are allowed. These configuration
 * are in conflicts with internal tape storge. e.g. if forcedDatestmap is used,
 * all records in xmltape assume same forcedDatestamp, regardless of their true
 * datestamp.
 * 
 * This adapter tries to translate a oai request to a tape request, with
 * application of all specific logic here.
 * 
 *  
 */

public class OAI2TapeAdapter {
    String from;

    String until;

    String set;
    
    static Logger log = Logger.getLogger(OAI2TapeAdapter.class.getName());

    public OAI2TapeAdapter(Config conf, String from, String until, String set) {
        log.info("input=" + from + " " + until + " " + set);
        //let's processing sets first
        if (set != null) {
            //if setSpec matches any static setSpec, it will not query xmltape
            // by sets
            if (conf.useForcedSets()) {
                log.info(set);
                if (conf.getSetSpecs().contains(set)) {
                    this.set = null;
                } else
                    this.set = "emptyset";
            } else
                this.set = set;
        }

        //if tapeoaidatestamp is null, use xmltape oaidatestmap
        if (!conf.useForcedDatestamp()) {
            this.from = from;
            this.until = until;
        } else {
            //if no match, return an empty result
            if ((conf.getForcedDatestamp().compareTo(from) < 0)
                    || (conf.getForcedDatestamp().compareTo(until) > 0)) {
                this.from = "2999-12-31";
                this.until = "2999-12-31";
            }
            //otherwise we return everything
            else {
                this.from = "1970-01-01";
                this.until = "2999-12-31";
            }
        }
        log.info("output=" + this.from + " " + this.until + " " + this.set);
    }

    public String getFrom() {
        return from;
    }

    public String getUntil() {
        return until;
    }

    public String getSet() {
        return set;
    }
}
