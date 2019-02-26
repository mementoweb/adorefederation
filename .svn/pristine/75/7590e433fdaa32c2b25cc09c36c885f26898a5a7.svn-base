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

package gov.lanl.semantic.registry;

import java.util.Iterator;
import java.util.Date;

public class SemanticApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: java gov.lanl.semantic.registry.SemanticApp <semantic_registry>");
            System.err.println("     <semantic_registry> is the URL of Semantic registry");
            System.err.println("     Transform semantic registry to plain text file");
            System.exit(-1);
        }

        SemanticIndex fi = new SemanticIndex(args[0], true);
        System.out.println("#");
        System.out.println("# Semantic registry file");
        System.out.println("# Created at " + new Date().toString()
                + " by java.gov.lanl.semantic.registry.SemanticApp");
        System.out.println("# Semantic Registry URL: " + args[0]);

        for (Iterator it = fi.getList().iterator(); it.hasNext();) {
            SemanticItem item = (SemanticItem) (it.next());
            System.out.println(item.toFlattext());
        }
        System.exit(0);
    }
}
