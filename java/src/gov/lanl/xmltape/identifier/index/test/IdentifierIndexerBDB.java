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

package gov.lanl.xmltape.identifier.index.test;

import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.identifier.index.access.RecordAccessor;
import gov.lanl.xmltape.identifier.index.access.SeqTapeAccessor;
import gov.lanl.xmltape.identifier.index.bdbImpl.BDBIndex;
import gov.lanl.xmltape.identifier.index.record.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class IdentifierIndexerBDB {
    
    public static void main(String[] args) {
        Properties props = new Properties();
        RecordAccessor ta = null;
        BDBIndex idx = new BDBIndex();
        long s = System.currentTimeMillis();
        int cnt = 0;
        try {
            props.load(new FileInputStream(args[0]));
            ta = new SeqTapeAccessor();
            //ta = new RandomTapeAccessor();
            ta.init(props);
            File dir = new File(props.getProperty("RecordAccessor.indexDBDir") + props.getProperty("RecordAccessor.databasename"));
            if (!dir.exists())
                dir.mkdir();
            idx.setIndexDir(dir.getAbsolutePath());
            idx.open(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        int marker = 0; 
        long reset = System.currentTimeMillis();
        // Iterate through records, adding identifiers to index
        while(ta.hasNext()) {
            if (marker == 100) {
                System.out.print(cnt + " | " + (System.currentTimeMillis() - reset) + " | ");
                printMemoryUsage();
                marker = 0;
                reset = System.currentTimeMillis();
            } else {
                marker++;
            }
            
            Record rec = ta.next();
            try {
                idx.putIdentifiers(rec.getIdentifiers());
                cnt++;
            } catch (IndexException e) {
                System.out.println("unable to process ids from " + rec);
            }
        }
        
        idx.close();
		ta.close();
        
        System.out.println("time to process " + cnt + " records:" + (System.currentTimeMillis() - s));
    }
    
    public static void printMemoryUsage() {
        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
    
}
