/*
 * ArcUtils -- ARC file access utilities Copyright (c) 2004 Det Kongelige
 * Bibliotek og Statsbiblioteket
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package gov.lanl.arc.dkImpl;

import java.io.*;

// GetPage2 emulates the behaviour of av_getpage
// Which we probably should use instead due to performance-reasons

public class GetPage2 {
    public static void main(String[] argv) {
        if (argv.length < 2) {
            System.err.println("Usage: java GetPage2 <arcfile> <offset>");
            System.exit(1);
        }
        String filename = argv[0];
        long offset = Long.parseLong(argv[1]);
        try {
            ARCInputStream reader = new ARCInputStream(new File(filename),
                    offset);
            StringBuffer header = new StringBuffer(200);
            // Is the following too slow: should we rather do like this:
            //System.out.println(reader.getUrl() + " " + reader.getIp() + " "
            //           + reader.getDate() + " " + reader.getMime() + " " +
            // reader.getLength()
            //           );
            header.append(reader.getUrl());
            header.append(" ");
            header.append(reader.getIp());
            header.append(" ");
            header.append(reader.getDate());
            header.append(" ");
            header.append(reader.getMime());
            header.append(" ");
            header.append(reader.getLength());

            System.out.println(header);
            byte[] body = reader.readAll();
            String s = new String(body);
            System.out.println(s);
        } catch (IOException e) {
            System.err.println("Error for " + filename + " offset " + offset
                    + ": " + e);
            e.printStackTrace();
        }
    }
}
