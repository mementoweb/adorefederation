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

package gov.lanl.xmltape.index;

import gov.lanl.xmltape.SingleTapeReader;
import gov.lanl.xmltape.TapeRecord;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Generic XMLTape Reader Utility. 
 * <br>
 * Input: <br>
 * tapefile - XMLTape to be indexed<br>
 * indexdir - Destination Directory for Index Files<br>
 * identifier - Unique identifier of record to be extracted<br>
 * indexPlugin - TapeIndexInterface Implementation<br>
 */

public class TapeReaderApp {
    public static String indexdir = null;

    public static String tapefile = null;
    
    public static String indexPlugin = null;

    TapeIndexInterface indexdb = null;

    SingleTapeReader str = null;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        boolean readIdentifier = false; 
        boolean readIndexes = false;
        boolean readCount = false;
        String identifier = null;
        Options options = new Options();

        options.addOption("s", false, "list status of the tape index");
        options.addOption("i", true, "read record");
        CommandLineParser parser = new PosixParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("s")) {
                readIndexes = true;
            }

            if (cmd.hasOption("i")) {
                readIdentifier = true;
                identifier = cmd.getOptionValue("i");
            }

            String[] str = cmd.getArgs();
            if (str.length != 3)
                throw new ParseException("");
            tapefile = str[0];
            indexdir = str[1];
            indexPlugin = str[2];

        } catch (ParseException exp) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(
              "java gov.lanl.xmltape.index.TapeReaderApp [-i <identifier>]  [-s] <tapefile> <indexdir> <indexPlugin>",
              options);
            System.exit(-1);
        }

        TapeReaderApp app = new TapeReaderApp();
        
        // Initialize Index Type
        Class impl = Class.forName(indexPlugin);
        app.indexdb = (TapeIndexInterface) impl.newInstance();
        app.indexdb.setTapeName(tapefile);
        app.indexdb.setIndexDir(indexdir);

        if (readIdentifier) {
            app.str = new SingleTapeReader(app.indexdb, tapefile);
            app.str.open();
            TapeRecord record = app.str.getRecord(identifier);
            System.out.println("identifier=" + record.getIdentifier());
            System.out.println("datestamp=" + record.getDatestamp());
            if (record.getSetSpecs() != null) {
                for (Iterator it = record.getSetSpecs().iterator(); it
                        .hasNext();) {
                    System.out.println("setspec=" + (String) (it.next()));
                }
            }
            System.out.println("metadata=" + record.getMetadata());
            app.str.close();
        }

        else if (readIndexes) {
            app.indexdb.open(true);
            System.out.println("total number of records: "
                    + app.indexdb.count(null));
                          
            List list = app.indexdb.getOAISetSpecs();
            for (Iterator it = list.iterator(); it.hasNext();) {
                String setSpec = (String) (it.next());
                System.out.println("setIndex: "+ setSpec + " :" + app.indexdb.count(setSpec));
            }
            
            app.indexdb.close();
        }
        System.out.println("Total Time: " + (System.currentTimeMillis() - start));
    }
}
