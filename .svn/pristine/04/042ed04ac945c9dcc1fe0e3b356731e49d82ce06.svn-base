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
import gov.lanl.xmltape.identifier.index.jdbImpl.IdentifierIndex;

public class IdentifierIndexerTest {
    
    public static void main(String[] args) {
        IdentifierIndex idx = new IdentifierIndex();
        long s = System.currentTimeMillis();
        int cnt = 0;
        try {
            idx.setIndexDir("/Users/rchute/tmp/tapes/");
            idx.open(false);
            
            int i = 0;
            int j = 0;
            String docId = null, id = null;
            String date = "2005-09-12T20:34:56Z";
            for (; i < 200000; i++) {
                if (j == 10 || i ==0) {
                    docId = UUIDFactory.generateUUID().getNudeId();
                    j=0;
                }
                id = UUIDFactory.generateUUID().getNudeId();
                idx.putIdentifier(new Identifier(docId, id, date));
                j++;
                cnt++;
            }
        } catch (IndexException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Close Index
        try {
            idx.close();
        } catch (IndexException e) {
            e.printStackTrace();
        }
        
        System.out.println("time to process " + cnt + " records:" + (System.currentTimeMillis() - s));
    }
    
    public static void printMemoryUsage() {
        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }   
}
