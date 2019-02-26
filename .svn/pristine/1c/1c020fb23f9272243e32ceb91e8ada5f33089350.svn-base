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

public class IdentifierIndexReaderTest {
    
    public static void main(String[] args) {
        // Query test
        try {
            IdentifierIndex idx = new IdentifierIndex();
            idx.open(true);
            
            long s = System.currentTimeMillis();
            Identifier id = idx.getIdentifier("info:doi/10.1007/s10610-004-5886-2");
            long d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms - " + id.getDatestamp());
            
            s = System.currentTimeMillis();
            id = idx.getIdentifier("info:doi/10.1007/s10610-004-5886-2");
            d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms - " + id.getDatestamp());
            
            s = System.currentTimeMillis();
            id = idx.getIdentifier("info:doi/10.1007/s10610-004-5886-2");
            d = System.currentTimeMillis();
            System.out.println(id.getRecordId() + " took " + (d - s) + " ms - " + id.getDatestamp());
            
            idx.close();
        } catch (IndexException e) {
            e.printStackTrace();
        } 
    }
}
