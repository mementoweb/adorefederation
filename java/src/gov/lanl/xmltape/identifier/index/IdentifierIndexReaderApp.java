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

package gov.lanl.xmltape.identifier.index;

import gov.lanl.identifier.IndexException;

/**
 * Generic Identifier Indexing Utility. <br>
 * Input: <br>
 * tapefile - XMLTape to be indexed<br>
 * indexdir - Destination Directory for Index Files<br>
 * indexerPlugin - TapeIndexerInterface Implementation<br>
 */

public class IdentifierIndexReaderApp {
    public static void main(String[] args) throws Exception {
        String indexdir = null;
        String indexPlugin = null;
        String recId = null;
        String id = null;
        try {
            if (args.length != 3)
                throw new Exception("Missing args");
            indexdir = args[0];
            indexPlugin = args[1];
            id = args[2];
        } catch (Exception exp) {
            System.out.println("java gov.lanl.identifier.index.IdentifierIndexerApp <indexdir> <indexPlugin> <identifier>");
            System.exit(-1);
        }
        long t = System.currentTimeMillis();
        IdentifierIndexInterface idx = IdentifierIndexRegistry.getIdentifierIndexImpl(indexPlugin, indexdir);
        System.out.println("IdentifierIndexInterface Init: " + (System.currentTimeMillis() - t));
        long s = System.currentTimeMillis();
        idx.open(true);
        System.out.println("IdentifierIndexInterface Open: " + (System.currentTimeMillis() - t));
        s = System.currentTimeMillis();
        try {
            recId = idx.getIdentifier(id).getRecordId();
        } catch (Exception e) {
        	//System.out.println("ID not found, checking for document id");
        }
        System.out.println("Identifier Lookup: " + (System.currentTimeMillis() - s));
        s = System.currentTimeMillis();
        // check if doc identifier
        if (recId == null) {
            try {
                if (idx.isDocId(id) != null)
                    recId = id;
            } catch (IndexException e) {
            	System.out.println("Exception attempting to resolve identifier ");
                e.printStackTrace();
            }
            System.out.println("DocId Check: " + (System.currentTimeMillis() - s));
        }
        s = System.currentTimeMillis();
        // Close Index
        try {
            idx.close();
        } catch (IndexException e) {
        }
        System.out.println("Index Close: " + (System.currentTimeMillis() - s));
        System.out.println(recId);
        System.out.println("Total: " + (System.currentTimeMillis() - t));
    }
}
