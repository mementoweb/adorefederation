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

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import gov.lanl.util.DigestUtil;
import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeRecord;

/**
 * Generic aDORe Indexing Utility. <br>
 * Input: <br>
 * tapefile - XMLTape to be indexed<br>
 * xslFile - XSLT to generate SOLR doc<br>
 */
public class XMLtapeMarcExtractor {
	static Logger logger = Logger.getLogger(XMLtapeMarcExtractor.class.getName());
	public static void main(String[] args) throws Exception {
        String tapefile = null;
        String xslFile = null;
        int docs = 0;
        try {
            if (args.length != 2)
                throw new Exception("Missing args");
            tapefile = args[0];
            xslFile = args[1];
        } catch (Exception exp) {
            System.out.println("java gov.lanl.adore.solr.XMLtapeRawIndexer <tapefile> <stylesheet>");
            System.exit(-1);
        }
        long start = System.currentTimeMillis();
        SeqTapeReader reader = new SeqTapeReader(tapefile);
		Pattern id_pattern = Pattern.compile("(info:lanl-repo/[a-zA_Z]+/[a-zA-Z0-9]+)</dii:Identifier>", Pattern.DOTALL);
        Pattern recordmatch_pattern = Pattern.compile("(<marc:record.*?</marc:record>)", Pattern.DOTALL);
        reader.open();
        TapeRecord r;
        Article a;
        while ((r = reader.next()) != null) {
        	a = new Article();
			String didl = r.getMetadata();
        	//long s = System.currentTimeMillis();
			Matcher id_matcher = id_pattern.matcher(didl);
	        if (id_matcher.find()) {
	        	String id = id_matcher.group();
	        	id = id.substring(0, id.lastIndexOf('<'));
	            a.setContentId(id);
	            a.setContentHash(DigestUtil.getSHA1Digest(id.getBytes()));
	        } else {
	        	logger.error("Unable to locate dii identifier");
	        	continue;
	        }
	        //System.out.println("get id: " + a.getContentId() + " " + (System.currentTimeMillis() - s));
	        //s = System.currentTimeMillis();
	        Matcher recordmatch_matcher = recordmatch_pattern.matcher(didl);
	        if (recordmatch_matcher.find()) {
	            String record = recordmatch_matcher.group();
		        //System.out.println("get record: " + (System.currentTimeMillis() - s));
		        //s = System.currentTimeMillis();
	            a = MarcXMLRecordParser.parse(new ByteArrayInputStream(record.getBytes()), a);
	            //System.out.println("parse record: " + (System.currentTimeMillis() - s));
	        }
			docs++;
			if (docs % 100 == 0)
	            System.out.println((System.currentTimeMillis() - start) / docs);
        }
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
