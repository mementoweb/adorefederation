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
import gov.lanl.identifier.IdentifierNotFoundException;
import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.identifier.index.bdbImpl.BDBIndex;

import java.io.FileInputStream;
import java.util.Properties;

public class IdentifierIndexReaderBDB {
    
    public static void main(String[] args) {
        // Query test
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(args[0]));
            String baseDir = props.getProperty("RecordAccessor.indexDBDir");
            String idxName = props.getProperty("RecordAccessor.databasename");
            
            BDBIndex idx = new BDBIndex();
            idx.setIndexDir(baseDir + idxName);
            idx.open(true);
            
            try {
                long s = System.currentTimeMillis();
                Identifier id = idx.getIdentifier("info:lanl-repo/ds/d55bf03a-2f05-11dc-bd1e-89e8ffdd03cd");
                long d = System.currentTimeMillis();
                System.out.println(id.getRecordId() + " took " + (d - s) + " ms");
            } catch (IdentifierNotFoundException e) {
                System.out.println(e.getMessage());
            }
            
            idx.close();
        } catch (IndexException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
