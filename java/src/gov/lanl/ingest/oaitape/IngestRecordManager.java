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

package gov.lanl.ingest.oaitape;

import gov.lanl.arc.ARCException;
import gov.lanl.util.csv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class IngestRecordManager {

    Set<IngestRecord> ingestRecordSet = new TreeSet<IngestRecord>();
    
    public void add(IngestRecord ingestRecord) {
        ingestRecordSet.add(ingestRecord);
    }
    
    public IngestRecord getIngestRecord(String ingestRecordID) throws ARCException {
        IngestRecord c;
        for (Iterator i = ingestRecordSet.iterator(); i.hasNext(); ) {
            c = (IngestRecord) i.next();
            if (c.getLocalIdentifier().equals(ingestRecordID))
                return c;
          }
        throw new ARCException("Ingest Record for specified identifier " + ingestRecordID + " was not found.");
    }
    
    public void writeLogFile(String output) throws IOException {
        IngestRecord c;
        PrintWriter pw = new PrintWriter( new FileWriter(output));
        CSVWriter csv = new CSVWriter(pw, false, ',', System.getProperty("line.separator") );
        csv.writeCommentln("Ingest Log Format : ref, xpath, sourceuri, digest, localID");
        
        for (Iterator i = ingestRecordSet.iterator(); i.hasNext(); ) {
            c = (IngestRecord) i.next();
            csv.write(c.getRef());
            csv.write(c.getDerefXPath());
            csv.write(c.getSourceURI());
            csv.write(c.getDigest());
            csv.write(c.getLocalIdentifier());
            csv.writeln();
          }
        csv.close();
    }
}
