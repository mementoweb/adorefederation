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

import gov.lanl.util.oai.oaiharvesterwrapper.GetRecord;
import gov.lanl.util.oai.oaiharvesterwrapper.ListMetadataFormats;
import gov.lanl.util.oai.oaiharvesterwrapper.ListSets;
import gov.lanl.xmltape.TapeWriter;

import java.io.File;
import java.util.Hashtable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
;

/**
 * Harvester.java<br>
 *<br>
 * OAI Harvesting Application <br>
 * Primarily used to write OAI Records to XMLTape or StdOut, but also contains
 * a utility methods to list sets, metadata formats, and identifiers.
 * 
 *  @author Xiaoming Liu
 */

public class Harvester {
    static int IDENTIFY = 1;

    static int GETRECORD = 2;

    static int LISTIDENTIFIERS = 3;

    static int LISTRECORDS = 4;

    static int LISTSETS = 5;

    static int LISTMETADATAFORMATS = 6;

    static int LISTIDENTIFIERSGETRECORD = 7;

    static int VERBERROR = 8;

    static String baseURL;

    static String identifier;

    static String from;

    static String sets;

    static String until;

    static int verb;

    static String loglevel;
    
    static String metadataPrefix;

    static String tapedir;

    static String tapeprefix;

    static boolean stdout;

    static boolean gzip;

    static int maxsize = 0;

    static Logger log = Logger.getLogger(Harvester.class.getName());

    public static void main(String[] argv) throws Exception {
        //check if parameter is correct
        Hashtable parahash = new Hashtable();

        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if (arg.equals("--h")) {
                printUsage();
                System.exit(ErrorCode.OK);
            }

            log.info(arg + "=" + argv[i + 1]);
            // options
            if (arg.startsWith("--"))
                parahash.put(arg.substring(2), argv[++i]);
            else {
                printUsage();
                System.exit(ErrorCode.CMDLINE_ERR);
            }

        }

        readParameters(parahash);

        if (baseURL == null) {
            printUsage();
            System.exit(ErrorCode.CMDLINE_ERR);
        }

        if (loglevel != null)
            log.setLevel(Level.toLevel(loglevel));
        
        try {
            switch (verb) {
            case 1:
                gov.lanl.util.oai.oaiharvesterwrapper.Identify identify = new gov.lanl.util.oai.oaiharvesterwrapper.Identify(
                        baseURL);
                System.out.println(identify.getResponseXML());
                break;
            case 2:
                if (metadataPrefix == null) {
                    printUsage();
                    System.exit(ErrorCode.CMDLINE_ERR);
                }
                GetRecord getrecord = new GetRecord(baseURL, identifier,
                        metadataPrefix);
                System.out.println(getrecord.getResponseXML());
            case 3:
                if (metadataPrefix == null) {
                    printUsage();
                    System.exit(ErrorCode.CMDLINE_ERR);
                }
                StreamListIdentifiers listidentifiers = new StreamListIdentifiers(
                        baseURL, from, until, sets, metadataPrefix, System.out);
                listidentifiers.start();
                break;

            case 7:
            case 4:
                if (metadataPrefix == null) {
                    printUsage();
                    System.exit(ErrorCode.CMDLINE_ERR);
                }

                if (stdout) {
                    StreamListRecords listrecords = new StreamListRecords(
                            baseURL, from, until, sets, metadataPrefix,
                            System.out);
                    listrecords.start();
                } else {
                    TapeWriter writer;
                    if (maxsize != 0)
                        writer = new TapeWriter(new File(tapedir), tapeprefix,
                                gzip, maxsize);
                    else
                        writer = new TapeWriter(new File(tapedir), tapeprefix,
                                gzip);
                    if (verb == 4) {
                        ListRecords2Tape listrecords = new ListRecords2Tape(
                                baseURL, from, until, sets, metadataPrefix,
                                writer);
                        listrecords.start();
                    } else {
                        ListIdentifiers2Tape l2t = new ListIdentifiers2Tape(
                                baseURL, from, until, sets, metadataPrefix,
                                writer);
                        l2t.start();
                    }
                    writer.close();

                }

                break;
            case 5:
                ListSets listsets = new ListSets(baseURL);
                System.out.println(listsets.getResponseXML());
                break;
            case 6:
                ListMetadataFormats listmetadataformats = null;
                if (identifier == null)
                    listmetadataformats = new ListMetadataFormats(baseURL);
                else
                    listmetadataformats = new ListMetadataFormats(baseURL,
                            identifier);
                System.out.println(listmetadataformats.getResponseXML());

            }

        } catch (Exception ex) {
            log.error(ex.toString());
            ex.printStackTrace();
            System.exit(ErrorCode.HARVEST_ERR);
        }

        System.exit(ErrorCode.OK);
    }

    /** Prints the usage. */
    private static void printUsage() {
        log.error("usage: java gov.lanl.harvester.Harvester [--help] [--l <loglevel>] [--verb <verb> ]");
        log.error("[--from <from>] [--until <until>] [--metadataPrefix <metadataprefix>] ");
        log.error("[--identifier <identifier>] [--baseurl <baseurl>]");
        log.error("[--stdout|--createtapes <tapedir> --tapeprefix <prefix> --maxsize <bytes>");
        log.error("[--gzip false/true]");
        log.error("--log         - Log level, refer org.apache.log4j for possible values");
        log.error("example1: harvest and output to standard out");
        log.error("java gov.lanl.harvester.Harvester --verb ListRecords --metadataPrefix didl --baseurl http://foo.org --stdout");
        log.error("example2: harvest and write XMLTapes with prefix aps, with tapemaxsize 1M");
        log.error("java gov.lanl.harvester.Harvester --verb ListRecords --metadataPrefix didl --baseurl http://foo.org --createtapes /tmp --tapeprefix aps --maxsize 1000000");
        log.error("example3: harvest and write XMLTapes with prefix aps, with tapemaxsize 1M, using listidentifiers+getrecord");
        log.error("java gov.lanl.harvester.Harvester --verb ListIdentifiersGetRecord --metadataPrefix didl --baseurl http://foo.org --createtapes /tmp --tapeprefix aps --maxsize 1000000");

    }

    private static void readParameters(Hashtable ht) {
        if ((ht.get("verb") == null)) {
            printUsage();
            System.exit(ErrorCode.CMDLINE_ERR);
        }
        if ((ht.get("baseurl") == null)) {
            printUsage();
            System.exit(ErrorCode.CMDLINE_ERR);
        }

        else {
            String verbtext = (String) (ht.get("verb"));
            if (verbtext.equals("GetRecord"))
                verb = GETRECORD;
            else if (verbtext.equals("Identify"))
                verb = IDENTIFY;
            else if (verbtext.equals("ListIdentifiers"))
                verb = LISTIDENTIFIERS;
            else if (verbtext.equals("ListRecords"))
                verb = LISTRECORDS;
            else if (verbtext.equals("ListSets"))
                verb = LISTSETS;
            else if (verbtext.equals("ListMetadataFormats"))
                verb = LISTMETADATAFORMATS;
            else if (verbtext.equals("ListIdentifiersGetRecord"))
                verb = LISTIDENTIFIERSGETRECORD;
            else {
                printUsage();
                System.exit(ErrorCode.CMDLINE_ERR);
            }

        }

        baseURL = (String) (ht.get("baseurl"));

        if ((ht.get("log") == null)) {
            loglevel = "INFO";
        } else
            loglevel = (String) ht.get("loglevel");

        if (ht.get("from") != null) {
            from = (String) (ht.get("from"));
        }
        if (ht.get("set") != null) {
            sets = (String) (ht.get("set"));
        }

        if (ht.get("until") != null) {
            until = (String) (ht.get("until"));
        }

        if (ht.get("identifier") != null) {
            identifier = (String) (ht.get("identifier"));
        }

        if (ht.get("metadataprefix") != null) {
            metadataPrefix = (String) (ht.get("metadataprefix"));
        }

        if (ht.get("gzip") != null) {
            if (ht.get("gzip").equals("true")) {
                gzip = true;
            } else
                gzip = false;
        }
        gzip = false;

        //stdout is default

        if (ht.get("createtapes") == null)
            stdout = true;
        else {
            tapedir = (String) (ht.get("createtapes"));
            tapeprefix = (String) (ht.get("tapeprefix"));
            if (ht.get("maxsize") != null)
                maxsize = Integer.parseInt((String) (ht.get("maxsize")));
        }

    }

}
