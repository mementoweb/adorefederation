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
import gov.lanl.xmltape.identifier.index.bdbImpl.BDBIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IdentifierIndexReaderBDBBench {
    
    private String indexDir;
    
    public static void main(String[] args) {
        IdentifierIndexReaderBDBBench iib = new IdentifierIndexReaderBDBBench(args[0]);
        try {
            FileOutputStream fos = new FileOutputStream(new File(args[0], "ids.txt"));
            fos.write(iib.listIdentifers());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public IdentifierIndexReaderBDBBench(String indexDir) {
        this.indexDir = indexDir;
    }
    
    public byte[] listIdentifers() {
        byte[] ids = new byte[0];
        BDBIndex idx = new BDBIndex();
        int cnt = 0;
        long s = System.currentTimeMillis();
        File dir = new File(indexDir);
		if (!dir.exists())
		    return ids;
		idx.setIndexDir(dir.getAbsolutePath());
		idx.open(true);
		ids = idx.listIdentifiers();
		idx.close();

        System.out.println("time to return identifiers:" + (System.currentTimeMillis() - s));
        return ids;
    }
    
    public static void printMemoryUsage() {
        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
    
}
