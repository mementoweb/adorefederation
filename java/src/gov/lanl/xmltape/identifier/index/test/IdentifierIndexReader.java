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
import gov.lanl.xmltape.identifier.index.jdbImpl.IdentifierIndex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class IdentifierIndexReader {
    
    public static void main(String[] args) {
        // Query test
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(args[0]));
            String baseDir = props.getProperty("RecordAccessor.indexDBDir");
            String idxName = props.getProperty("RecordAccessor.databasename");
            
            IdentifierIndex idx = new IdentifierIndex();
            idx.setIndexDir(baseDir);
            idx.open(true);
            
            long s = System.currentTimeMillis();
            Identifier id = idx.getIdentifier("info:lanl-repo/ds/a11a2de4-9497-4057-81a8-9541d701437f");
            long d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms");
            
            s = System.currentTimeMillis();
            id = idx.getIdentifier("info:lanl-repo/ds/a11a2de4-9497-4057-81a8-9541d701437f");
            d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms");
            
            s = System.currentTimeMillis();
            id = idx.getIdentifier("info:lanl-repo/isi/A1968ZA25000003");
            d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms");
            
            s = System.currentTimeMillis();
            id = idx.getIdentifier("info:lanl-repo/isi/A1968ZA25000003");
            d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms");
            
            s = System.currentTimeMillis();
            id = idx.getIdentifier("info:lanl-repo/isi/A1968ZA25000003");
            d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms");
            
            idx.close();
        } catch (IndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
