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

package gov.lanl.xmltape.create;

import gov.lanl.xmltape.SingleTapeWriter;
import gov.lanl.xmltape.TapeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

/**
 * Command-line tool to create XMLTapes
 */
public class TapeCreate {

    static Logger log = Logger.getLogger(TapeCreate.class.getName());

    private static String createTapeInfoURI(String prefix, String tapename) {
        if (prefix != null) {
            tapename = prefix + "xmltape/" + tapename;
        }
        return tapename;
    }

    /**
     * Main Method - Expect the following:
     * -i [input]<br>
     * -o [tape]<br> 
     * -p [properties]
     * @param args
     *        Command-line args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        StringBuffer usage = new StringBuffer("TapeCreate")
                .append(" -i <input> -o <tape> -p <properties>");

        CommandLineParser parser = new PosixParser();

        // Create the options
        Options options = new Options();

        Option source = new Option("i", "input", true,
                "System Path to input file");
        source.setArgName("input");
        options.addOption(source);

        Option dest = new Option("o", "output", true,
                "System Path to output file");
        dest.setArgName("output");
        options.addOption(dest);

        Option props = new Option("p", "props", true,
                "System Path to output file");
        props.setArgName("props");
        options.addOption(props);

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.getOptionValue("i").trim().length() == 0)
                throw new ParseException("");

            if (cmd.getOptionValue("o").trim().length() == 0)
                throw new ParseException("");

            if (cmd.getOptionValue("p").trim().length() == 0)
                throw new ParseException("");

            TapeCreate creator = new TapeCreate(cmd);
            creator.createTape();

        } catch (ParseException exp) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage.toString(), options);
            System.exit(-1);
        }
    }

    private String input;

    private String outputfile;

    private boolean pipedInput = false;

    private String properties;

    private int sum_records = 0;
    
    private File tmpDir;

    /**
     * Create a new TapeCreate instance
     */
    public TapeCreate() {
    }

    /**
     * Create a new TapeCreate instance using an Apache Commons CLI CommandLine object
     * 
     * @param cmd
     *            an Apache Commons CommandLine object
     * @throws Exception
     */
    public TapeCreate(CommandLine cmd) throws Exception {
        this.input = cmd.getOptionValue("i");
        this.outputfile = cmd.getOptionValue("o");
        this.properties = cmd.getOptionValue("p");
    }

    /**
     * Create a new TapeCreate instance from provided values
     * @param inputPath
     *           Path to input file
     * @param outputPath
     *           Path to output file
     * @param procProps
     *           Path to Tape Create Properties file
     */
    public TapeCreate(String inputPath, String outputPath, String procProps) {
        this.input = inputPath;
        this.outputfile = outputPath;
        this.properties = procProps;
    }

    /**
     * Create XMLtape from provided input using the tape creation props
     * @throws Exception
     */
    public void createTape() throws Exception {
        if (input.equals("-")) {
            // Set piped input to true and define placeholder path
            pipedInput = true;
            input = "/dev/stdin";
        }

        File f = new File(input);
        if (!f.exists())
            throw new TapeException("The source directory " + input
                    + "doesn't exist");

        // Initialize Tape Creation Processor
        TapeCreateConfig tcc = new TapeCreateConfig(properties);
        TapeCreateInterface tci = (TapeCreateInterface) Class.forName(
                tcc.getTapeCreatePlugin()).newInstance();
        tci.setProcessingProperies(tcc);

        // Check XMLTape Naming Convention
        if (!outputfile.endsWith(".xml")) {
            if (outputfile.contains(".")) {
                outputfile = outputfile.substring(0, outputfile
                        .lastIndexOf("."));
            }
            outputfile = outputfile + ".xml";
        }

        log.debug("outputfile=" + outputfile);

        // Initialize tape writer
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(outputfile)), "UTF-8"));
        
        SingleTapeWriter tapewriter;
        if (tmpDir != null)
            tapewriter = new SingleTapeWriter(writer, tmpDir);
        else
            tapewriter= new SingleTapeWriter(writer);

        // Generate the XMLTapeID = info uri prefix + tape name minus ext.
        String tapeidsuffix = new File(outputfile).getName();
        tapeidsuffix = tapeidsuffix.substring(0, tapeidsuffix.lastIndexOf("."));
        tapeidsuffix = createTapeInfoURI(tcc.getLocalInfoURIPrefix(),
                tapeidsuffix);
        log.debug("tapeidsuffix=" + tapeidsuffix);

        tapewriter.setXmlTapeID(tapeidsuffix);
        tapewriter.writeDefaultAdmin();

        if (pipedInput) {
            sum_records = tci.writeRecords(new InputStreamReader(System.in,
                    "UTF-8"), tapewriter);
        } else if (f.isDirectory()) {
            // Loop inside the directory to process each file
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory())
                    continue;
                sum_records = tci.writeRecords(new InputStreamReader(
                        new FileInputStream(files[i]), "UTF-8"), tapewriter);
            }
        } else
            sum_records = tci.writeRecords(new InputStreamReader(
                    new FileInputStream(f), "UTF-8"), tapewriter);

        tapewriter.close();

        log.info("Total write " + sum_records + " records");
    }

    /**
     * Gets the system path of file to be processed; "-" for stdIn
     * @return
     *       Path to input file
     */
    public String getInput() {
        return input;
    }
    
    /**
     * Gets the system path of the output file
     * @return
     *       Path to output file
     */
    public String getOutputfile() {
        return outputfile;
    }

    /**
     * Gets the system path of of the Tape Create properties file
     * @return 
     *       Path to properties file
     */
    public String getProperties() {
        return properties;
    }

    /**
     * Sets the system path of file to be processed; "-" for stdIn
     * @param input
     *       Path to input file
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Sets the system path of the output file
     * @param outputfile
     *       Path to output file
     */
    public void setOutputfile(String outputfile) {
        this.outputfile = outputfile;
    }

    /**
     * Sets the system path of the Tape Create properties file
     * @param properties
     *       Path to properties file
     */
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * Gets the tmp dir path
     * @return
     *       System path to tmp directory
     */
    public File getTmpDir() {
        return tmpDir;
    }

    /**
     * Sets the tmp dir path
     * @param tmpDir
     *       System path to tmp directory
     */
    public void setTmpDir(File tmpDir) {
        this.tmpDir = tmpDir;
    }
}