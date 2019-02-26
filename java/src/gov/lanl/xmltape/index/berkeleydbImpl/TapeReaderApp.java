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

package gov.lanl.xmltape.index.berkeleydbImpl;

import gov.lanl.xmltape.SingleTapeReader;
import gov.lanl.xmltape.TapeRecord;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.ParseException;

/**
 * @version $Id: TapeReaderApp.java 1286 2005-01-21 19:25:16Z liu_x $
 */

public class TapeReaderApp extends HttpServlet {
    public static String indexdir = "/Users/rchute/Desktop/test/Biosis_1975_prev1975_3cd35e72-722e-11dc-82d9-c673cd7330d2-02.idx";

    public static String tapefile = "/Users/rchute/Desktop/test/Biosis_1975_prev1975_3cd35e72-722e-11dc-82d9-c673cd7330d2-02.xml";

    BDBIndex indexdb = null;

    SingleTapeReader str = null;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        boolean readIdentifier = false, readIndexes = false, readCount = false;
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
            if (str.length != 2)
                throw new ParseException("");
            tapefile = str[0];
            indexdir = str[1];

        } catch (ParseException exp) {
            HelpFormatter formatter = new HelpFormatter();
            formatter
                    .printHelp(
                            "java gov.lanl.xmltape.index.berkeleydbImpl.TapeReaderApp [-i <identifier>]  [-s] <tapefile> <indexdir>",
                            options);
            System.exit(-1);
        }

        String[] paths = tapefile.split("/");
        String tapename = paths[paths.length - 1];
        if (tapename.indexOf(".") != -1)
            tapename = tapename.substring(0, tapename.indexOf("."));
        System.out.println(tapename);

        TapeReaderApp app = new TapeReaderApp();
        app.indexdb = new BDBIndex(indexdir, tapename);

        if (readIdentifier) {
        	System.out.println("Obtain Record: " + identifier);
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

            if (app.indexdb.getDatestampIndex())
                System.out.println("datestampIndex exist");
            else 
                System.out.println("datestampIndex doesn't exist");
            
            app.indexdb.close();
        }
        System.out.println("Total Time: " + (System.currentTimeMillis() - start));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/xml");
        String identifier = request.getParameter("identifier");
        PrintWriter out = response.getWriter();
        try {
            TapeRecord record = str.getRecord(identifier);
            out.println(record.getMetadata());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        out.close();
    }

    public void init(ServletConfig config) throws ServletException {
        try {
            ServletContext context = config.getServletContext();
            indexdb = new BDBIndex(indexdir, "sample");
            str = new SingleTapeReader(indexdb, tapefile);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new ServletException(ex.toString());
        }
    }

}
