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

package gov.lanl.format.registry;

import java.util.Iterator;
import java.util.Date;

public class FormatApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: java gov.lanl.format.registry.FormatApp <format_registry>");
            System.err.println("     <format_registry> is the URL of format registry");
            System.err.println("     Transform format registry to plain text file.");
            System.exit(-1);
        }

        FormatIndex fi = new FormatIndex(args[0], true);
        System.out.println("#");
        System.out.println("# Format registry file");
        System.out.println("# Created at " + new Date().toString()
                + " by java.gov.lanl.format.registry.FormatApp");
        System.out.println("# Format Registry URL: " + args[0]);

        for (Iterator it = fi.getList().iterator(); it.hasNext();) {
            FormatItem item = (FormatItem) (it.next());
            System.out.println(item.toFlattext());
        }
        System.exit(0);
    }
}
