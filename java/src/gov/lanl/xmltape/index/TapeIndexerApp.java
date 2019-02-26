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

import gov.lanl.xmltape.index.sets.SetSpecNamespace;
import gov.lanl.xmltape.index.sets.SetSpecXPath;
import gov.lanl.xmltape.index.sets.SetSpecProfile;
import gov.lanl.xmltape.index.sets.SetSpecProfileFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Generic XMLTape Indexing Utility. <br>
 * Input: <br>
 * tapefile - XMLTape to be indexed<br>
 * indexdir - Destination Directory for Index Files<br>
 * indexerPlugin - TapeIndexerInterface Implementation<br>
 * indexPlugin - TapeIndexInterface Implementation<br>
 */

public class TapeIndexerApp {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("s", false, "index setSpec");
        options.addOption("p", true, "setSpec properties file");
        CommandLineParser parser = new PosixParser();
        boolean indexSetspec = false;
        String tapefile = null;
        String indexdir = null;
        String indexPlugin = null;
        Properties setSpecProps = null;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("s") || cmd.hasOption("p")) {
                indexSetspec = true;
                if (cmd.hasOption("p")) {
                    setSpecProps = new Properties();
                    String prop = cmd.getOptionValue("p");
                    setSpecProps.load(new FileInputStream(new File(prop)));
                }
                else {
                    indexSetspec = false;
                    System.out.println("SetSpec Properties Required to index set information; disabling set indexing");
                }
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
                            "java gov.lanl.xmltape.index.TapeIndexerApp [-s] [-p] <tapefile> <indexdir> <indexPlugin>",
                            options);
            System.exit(-1);
        }
        long start = System.currentTimeMillis();

        // Initialize Index Type
        Class impl = Class.forName(indexPlugin);
        TapeIndexInterface indexdb = (TapeIndexInterface) impl.newInstance();
        indexdb.setTapeName(getFilenameMinusExt(new File(tapefile).getName()));
        indexdb.setIndexDir(indexdir);

        // Initialize Indexer
        TapeIndexer indexer = new TapeIndexer(indexdb);
        if (indexSetspec) {
            SetSpecProfile sspp = SetSpecProfileFactory.generateSetSpecProfile(setSpecProps);
            // Process Namespace Defs
            for (Iterator i = sspp.getNamespaces().iterator(); i.hasNext();) {
                SetSpecNamespace ssn = (SetSpecNamespace) i.next();
                indexer.addSetNamespaces(ssn.getNamespacePrefix(), ssn.getNamespace());
            }
            // Process Set Spec XPath Information
            for (Iterator j = sspp.getXpaths().iterator(); j.hasNext();) {
                SetSpecXPath ssx = (SetSpecXPath) j.next();
                indexer.addSetElementXPath(ssx.getXpath(), ssx.getXpathPrefix());
            }
        }

        indexer.parse(tapefile);
        long duration = System.currentTimeMillis() - start;
        System.out.println("\nTotal Indexing Time: " + duration + " ms");

        // Debug Configuration - Writes CSV Version of Indexed Values
        //BasicTapeIndex bti = (BasicTapeIndex) indexdb;
        //bti.toCSV();
    }
    
    /**
     * Provide an absolute file path, will return filename minus dir path
     * and extension
     * @param name
     *        Absolute path of file to extract filename from.
     * @return
     *        Filename minus extension
     */
    public static String getFilenameMinusExt(String name) {
        int j = name.lastIndexOf(File.separator);
        name = name.substring(j + 1);
        int jj = name.lastIndexOf(".");
        name = name.substring(0, jj);
        return name;
    }
}
