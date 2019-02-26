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

/**
 * SeqTapeReaderApp.java
 * 
 * @author liu_x
 */

package gov.lanl.xmltape;

import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class SeqTapeReaderApp {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        boolean readIdentifier = false, readDatestamp = false, readAdmin = false, readMetadata = false;
        String tapefile = null;
        String identifier = null;
        Options group = new Options();

        group.addOption(new Option("a", false, "list all"));
        group.addOption(new Option("n", false, "list admin"));
        group.addOption(new Option("i", false, "list identifiers"));
        group.addOption(new Option("d", false, "list datestamp"));
        group.addOption(new Option("m", false, "list metadata"));

        StringBuffer usage = new StringBuffer("SeqTapeReader")
                .append(" [options] <tape> [identifier]");

        CommandLineParser parser = new PosixParser();

        try {
            CommandLine cmd = parser.parse(group, args);
            if (cmd.hasOption("a")) {
                readIdentifier = true;
                readDatestamp = true;
                readAdmin = true;
                readMetadata = true;
            } else if (cmd.hasOption("n"))
                readAdmin = true;
            else if (cmd.hasOption("i"))
                readIdentifier = true;
            else if (cmd.hasOption("d"))
                readDatestamp = true;
            else if (cmd.hasOption("m"))
                readMetadata = true;
            else
                readIdentifier = true;

            String[] str = cmd.getArgs();
            if (str.length == 1) {
                tapefile = str[0];
            } else if (str.length == 2) {
                tapefile = str[0];
                identifier = str[1];
            } else
                throw new ParseException("");
        } catch (ParseException exp) {
            HelpFormatter formatter = new HelpFormatter();
            formatter
                    .printHelp(
                            "java gov.lanl.xmltape.SeqTapeReaderApp [-anidm] <tapefile> [identifier]",
                            group);
            System.exit(-1);
        }

        SeqTapeReader reader = new SeqTapeReader(tapefile);
        reader.open();

        for (TapeRecord r = reader.next(); r != null; r = reader.next()) {
            if ((identifier != null) && (!r.getIdentifier().equals(identifier)))
                continue;
            if (readIdentifier)
                System.out.print(r.getIdentifier() + "\t");

            if (readDatestamp)
                System.out.print(r.getDatestamp() + "\t");

            if (readAdmin) {
                for (Iterator it = r.getAdmin().iterator(); it.hasNext();) {
                    System.out.print((String) (it.next()));
                }
                System.out.print("\t");
            }

            if (readMetadata) {
                System.out.print(r.getMetadata());
            }

            System.out.println();

            if (identifier != null)
                break;

        }

        reader.close();
        System.err.println("Total Time: "
                + (System.currentTimeMillis() - start));
    }
}
