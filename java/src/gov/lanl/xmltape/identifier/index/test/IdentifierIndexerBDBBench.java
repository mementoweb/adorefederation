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

import gov.lanl.identifier.Identifier;
import gov.lanl.identifier.IndexException;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.identifier.index.bdbImpl.BDBIndex;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IdentifierIndexerBDBBench {
    
    private static final String URI_RECORD_PREFIX = "info:lanl-repo/i/";
    private static final String URI_DS_PREFIX = "info:lanl-repo/ds/";
    private String basedir;
    private String date = "2005-09-12T20:34:56Z";
    private String recId = URI_RECORD_PREFIX + UUIDFactory.generateUUID().getNudeId();;
    private String dsId;
    
    public static void main(String[] args) {
        IdentifierIndexerBDBBench iib = new IdentifierIndexerBDBBench(args[0]);
        try {
            FileInputStream fstream = new FileInputStream(args[1]);
            DataInputStream in = new DataInputStream(fstream);
            while (in.available() !=0) {
                iib.process(Integer.parseInt(in.readLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public IdentifierIndexerBDBBench(String basedir) {
        this.basedir = basedir;
    }
    
    public void process(int iters) {
        BDBIndex idx = new BDBIndex();
        int cnt = 0;
        int recIdCnt = 0;
        long s = System.currentTimeMillis();
        File dir = new File(basedir, iters + "_identifiers");
		if (!dir.exists())
		    dir.mkdir();
		idx.setIndexDir(dir.getAbsolutePath());
		idx.open(false);
        
        while(cnt < iters) {
            try {
                if (recIdCnt == 3) {
                   recId = URI_RECORD_PREFIX + UUIDFactory.generateUUID().getNudeId();
                   recIdCnt = 0;
                } else {
                   recIdCnt++;
                }
                dsId = URI_DS_PREFIX + UUIDFactory.generateUUID().getNudeId();
                idx.putIdentifier(new Identifier(recId, dsId, date));
                cnt++;
            } catch (IndexException e) {
                System.out.println("unable to process ids from " + dsId);
            }
        }
        
        idx.close();
        
        System.out.println("time to process " + cnt + " records:" + (System.currentTimeMillis() - s));
    }
    
    public static void printMemoryUsage() {
        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
    
}
