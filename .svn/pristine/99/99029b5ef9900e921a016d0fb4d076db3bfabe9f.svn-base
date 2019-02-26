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
import gov.lanl.xmltape.identifier.index.access.RecordAccessor;
import gov.lanl.xmltape.identifier.index.access.RecordAccessorConfig;
import gov.lanl.xmltape.identifier.index.access.SeqTapeAccessor;
import gov.lanl.xmltape.identifier.index.record.Record;

import java.util.Properties;

/**
 * Generic Identifier Indexing Utility. <br>
 * Input: <br>
 * tapefile - XMLTape to be indexed<br>
 * indexdir - Destination Directory for Index Files<br>
 * indexerPlugin - TapeIndexerInterface Implementation<br>
 */

public class IdentifierIndexerApp {
    public static void main(String[] args) throws Exception {
        String tapefile = null;
        String indexdir = null;
        String indexPlugin = null;
        String recordPlugin = null;
        try {
            if (args.length != 4)
                throw new Exception("Missing args");
            tapefile = args[0];
            indexdir = args[1];
            indexPlugin = args[2];
            recordPlugin = args[3];
        } catch (Exception exp) {
            System.out.println("java gov.lanl.identifier.index.IdentifierIndexerApp <tapefile> <indexdir> <indexPlugin> <recordPlugin>");
            System.exit(-1);
        }
        
        Properties props = new Properties();
        props.put(RecordAccessorConfig.TAG_TAPE_FILE_NAME, tapefile);
        props.put(RecordAccessorConfig.TAG_TAPE_IDX_RECORD_PLUGIN, indexPlugin);
        props.put(RecordAccessorConfig.TAG_TAPE_IDX_FILE, indexdir);
        props.put(RecordAccessorConfig.TAG_TAPE_IDX_RECORD_PLUGIN, recordPlugin);
        
        // Initialize Index Type
        RecordAccessor ta = new SeqTapeAccessor();
        ta.init(props);
        IdentifierIndexInterface idx = IdentifierIndexRegistry.getIdentifierIndexImpl(indexPlugin, indexdir);
        idx.setIndexDir(indexdir);
        idx.open(false);

        // Iterate through records, adding identifiers to index
        while(ta.hasNext()) {
            Record rec = ta.next();
            try {
                idx.putIdentifiers(rec.getIdentifiers());
            } catch (IndexException e) {
            }
        }
        
        // Close Index
        try {
            idx.close();
            ta.close();
        } catch (IndexException e) {
        }
    }
}
