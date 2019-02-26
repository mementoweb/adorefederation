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

package gov.lanl.util.misc;

import gov.lanl.didlwriter.profile.AdoreConstants;
import gov.lanl.didlwriter.profile.pubmed.PubMedArticle;
import gov.lanl.util.DateUtil;
import gov.lanl.util.DigestUtil;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.SingleTapeWriter;
import gov.lanl.xmltape.TapeException;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.TapeRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * PubMedDidl
 * 
 */

public class PubMedDidl {
    private PubMedArticle didWriter;
    private SingleTapeWriter writer;
    private String tapeName;
    private String rootTapeName;
    private String rootTapeDir;
    private int tapeCount = 0;
    private int maxTapeSize = 500000000;
    private Logger log = Logger.getLogger(PubMedDidl.class.getName());
    private static javax.xml.parsers.DocumentBuilderFactory dbf = null;
    private static final String COPYRIGHT = "Copyright BioMed Central Ltd on behalf of the copyright holders";
    private static final String COLLECTION = "info:sid/ncbi.nlm.nih.gov:pubmed";

    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
    }

    static final int UNKNOWN = 0;

    static final int PUBMEDARTICLEXML = 1;

    static final String OAINS = "http://www.openarchives.org/OAI/2.0/";
    static final String PMANS = "http://dtd.nlm.nih.gov/2.0/xsd/archivearticle";

    String datestamp;

    long unitStart;
    
    public void getData(String resource) throws Exception {

		byte[] deref = resource.getBytes("UTF-8");

		String digest = DigestUtil.getSHA1Digest(deref);
		digest = DigestUtil.toURN(digest, "sha1");
		String id = "info:lanl-repo/ds/" + UUIDFactory.generateUUID().toString().substring(9);
		didWriter.addComponent(
				        PubMedArticle.COMPONENT_TYPE.PUBMEDARTICLEXML, 
				        id, 
				        digest,
						PubMedArticle.PUBMED_ARTICLE_FORMATID, 
						datestamp,
						PubMedArticle.PUBMED_CREATOR_ID, 
						"application/xml", 
						null,
						resource);

	}

    public String process(String article, String id) throws Exception {
        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();

        // Create DOM for Old DIDL
        InputSource IS = new InputSource(new StringReader(article));
        org.w3c.dom.Document doc = db.parse(IS);

        // Initialize AI DIDL Processor
        didWriter = new PubMedArticle();
        didWriter.setCopyright(COPYRIGHT);
        didWriter.setCollection(COLLECTION);

        // Package identifier - (i.e.
        // info:lanl-repo/i/cd7b17ea-bddf-11d9-9de5-c11b6cd85594)
        didWriter.setDocumentId(id);

        // Identifier of First Item
        //NodeList idnodes = doc.getElementsByTagNameNS(OAINS, "identifier");
        NodeList idnodes = doc.getElementsByTagNameNS(PMANS, "article-id");
        Node node = null;
        // Content ID - (i.e. info:doi/10.1007/s10610-004-5886-2)
        for (int i = 0; i < idnodes.getLength(); i++) {
        	node = idnodes.item(i);
        	NodeList cnodes = node.getChildNodes();
        	NamedNodeMap m = node.getAttributes();
        	for (int j = 0; j < m.getLength(); j++) {
        		if (m.getNamedItem("pub-id-type") != null && m.item(j).getNodeValue().equals("pmid")) {
                    if (cnodes.item(0).getNodeType() == Node.TEXT_NODE) {
                    	String v = "info:pmid/" + cnodes.item(0).getNodeValue();
                        didWriter.setContentId(v);
                        continue;
                    }
        		}
        	}
        }
        
        if (didWriter.getContentId() == null) {
        	return null;
        }

        idnodes = doc.getElementsByTagNameNS(OAINS, "datestamp");
        // Content ID - (i.e. info:doi/10.1007/s10610-004-5886-2)
        node = idnodes.item(0);
        NodeList cnodes = node.getChildNodes();
        int nlength = cnodes.getLength();
        for (int i = 0; i < nlength; i++) {
            Node cnode = cnodes.item(i);
            if (cnode.getNodeType() == Node.TEXT_NODE) {
            	datestamp = cnode.getNodeValue();
            }
        }
        
        Pattern recordmatch_pattern = Pattern.compile("(<article.*?</article>)", Pattern.DOTALL);
        Matcher recordmatch_matcher = recordmatch_pattern.matcher(article);
        while (recordmatch_matcher.find()) {
            String record = recordmatch_matcher.group();
            getData(record);
        }

        return didWriter.getXML();
    }
    
    public static void main(String[] args) throws Exception {
    	String tapeName = "PM_" + UUIDFactory.generateUUID().toString().substring(9);
    	String tapeDir = "/Users/rchute/tmp/";
    	PubMedDidl marcDidlWriter = new PubMedDidl();
        marcDidlWriter.openTapeWriter(tapeName, tapeDir);
    	marcDidlWriter.process();
    	marcDidlWriter.closeWriter();
    }
        
    public void process() throws Exception {
    	String _baseurl = "http://www.pubmedcentral.nih.gov/oai/oai.cgi";
    	String _set = "pmc-open";
    	String _prefix ="pmc_fm";
    	String _from = "2008-01-01";
    	String _until = "2008-07-14";
    	int count = 0;
    	gov.lanl.util.oai.oaiharvesterwrapper.ListRecords listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(_baseurl, _from, _until, _set, _prefix);
    	count += listRecords.size();
    	writeRecords(listRecords);
        String token = listRecords.getResumptionToken();
        while ((token != null) && (token.trim().length() != 0)) {
            listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(_baseurl, token);
            count += listRecords.size();
            writeRecords(listRecords);
            token = listRecords.getResumptionToken();
        }
        log.info("Done -- harvested  " + count + " records");
    }
    
    private void writeRecords(gov.lanl.util.oai.oaiharvesterwrapper.ListRecords listrecords) throws Exception {
        ArrayList al = listrecords.getRecords();
        for (Iterator it = al.iterator(); it.hasNext();) {
            gov.lanl.util.oai.oaiharvesterwrapper.Record record = (gov.lanl.util.oai.oaiharvesterwrapper.Record) (it.next());
            writeRecord(record);
        }
    }
    
    private void writeRecord(gov.lanl.util.oai.oaiharvesterwrapper.Record record) throws Exception {
    	String id = "info:lanl-repo/i/" + UUIDFactory.generateUUID().toString().substring(9);
    	String date = DateUtil.date2UTC(new Date());
    	String didl = process(record.getRecordXML(), id);
    	if (didl == null)
    		return;
        TapeRecord taperecord = new TapeRecord(id, date, didl);
        writer.writeRecord(taperecord);
        if (writer.getSize() >= maxTapeSize) {
        	closeWriter();
            tapeCount++;
            NumberFormat formatter = new DecimalFormat("##00");
            openTapeWriter(rootTapeName + "-" + formatter.format(tapeCount));
        }
    }
    
    public void openTapeWriter(String tapename)  throws TapeException {
    	openTapeWriter(tapename, rootTapeDir);
    }
    
    public void openTapeWriter(String tapename, String storageDir) throws TapeException {
    	this.tapeName = tapename;
    	if (rootTapeName == null)
    		rootTapeName = tapeName;
    	if (rootTapeDir == null)
    		rootTapeDir = storageDir;
		try {
			File dir = new File(storageDir);
			if (!dir.exists())
				dir.mkdirs();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(storageDir,tapename + ".xml")), "UTF-8"));
			this.writer = new SingleTapeWriter(writer);
		} catch (Exception ex) {
			throw new TapeException("error in openTapeWriter " + tapename, ex);
		}
	}
    
    public void closeWriter() throws IOException {
    	writer.setXmlTapeID(TapeProperties.getLocalXmlTapePrefix() + tapeName);
    	writer.writeDefaultAdmin();
    	writer.close();
    }
}
