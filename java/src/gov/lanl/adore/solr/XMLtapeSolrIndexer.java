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

package gov.lanl.adore.solr;

import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeRecord;

/**
 * Generic aDORe Solr Indexing Utility. <br>
 * Input: <br>
 * tapefile - XMLTape to be indexed<br>
 * xslFile - XSLT to generate SOLR doc<br>
 */

public class XMLtapeSolrIndexer {
    public static void main(String[] args) throws Exception {
        String tapefile = null;
        String xslFile = null;
        AdoreSolrIndexer idx = new AdoreSolrIndexer();
        try {
            if (args.length != 2)
                throw new Exception("Missing args");
            tapefile = args[0];
            xslFile = args[1];
        } catch (Exception exp) {
            System.out.println("java gov.lanl.adore.solr.XMLtapeSolrIndexer <tapefile> <stylesheet>");
            System.exit(-1);
        }
        long start = System.currentTimeMillis();
        SeqTapeReader reader = new SeqTapeReader(tapefile);
        reader.open();

        for (TapeRecord r = reader.next(); r != null; r = reader.next()) {
			XSLTTransformer xtran = XSLTPool.borrowObject(xslFile);
			String output = xtran.transform(r.getMetadata());
			XSLTPool.returnObject(xslFile, xtran);
			idx.addSolrDocument(output);
        }
        int docs = idx.close();
        reader.close();
        
        System.out.println("\nTotal documents: " + docs);
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Elapse time: " + time);
        int throughput = 0;
        if (time != 0) {
            throughput = (int) (docs / time);
            System.out.println("Throughput: " + throughput + " records/second");
        }
    }
}
