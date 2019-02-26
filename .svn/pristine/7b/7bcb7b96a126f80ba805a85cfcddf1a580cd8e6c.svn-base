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

import gov.lanl.adore.djatoka.kdu.KduCompressExe;
import gov.lanl.didlwriter.profile.AdoreConstants;
import gov.lanl.didlwriter.profile.marc.Marc;
import gov.lanl.resource.ResourceException;
import gov.lanl.resource.ResourceRecord;
import gov.lanl.resource.filesystem.ResourceWriter;
import gov.lanl.util.DateUtil;
import gov.lanl.util.DigestUtil;
import gov.lanl.util.HttpDate;
import gov.lanl.util.csv.CSVReader;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.SingleTapeWriter;
import gov.lanl.xmltape.TapeException;
import gov.lanl.xmltape.TapeProperties;
import gov.lanl.xmltape.TapeRecord;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * MarcDidl
 * 
 */

public class MarcDidl {
    private Marc didWriter;
    private SingleTapeWriter writer;
    private String tapeName;
    private String rootTapeName;
    private String rootTapeDir;
    private int tapeCount = 0;
    private int maxTapeSize = 500000000;
    private Logger log = Logger.getLogger(MarcDidl.class.getName());
    private static javax.xml.parsers.DocumentBuilderFactory dbf = null;
    private static final String COPYRIGHT = "Copyright Library of Congress";
    private static final String COLLECTION = "info:sid/library.lanl.gov:loc-memory";
    private static String repoId = "info:lanl-repo/resourcedb/cc5fc4f7-e50a-455f-b3ce-a6a8b54824e6";
    private String rwIndex = "gov.lanl.resource.filesystem.index.FilesystemBasedIndex";
    private ResourceWriter rw;

    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
    }

    static final int UNKNOWN = 0;

    static final int MARCXML = 1;

    static final String OAINS = "http://www.openarchives.org/OAI/2.0/";

    String datestamp;

    long unitStart;
    
    public void getData(String resource) throws Exception {

		byte[] deref = null;
		resource = resource.replaceAll(
				"http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd",
				"http://purl.lanl.gov/aDORe/schemas/2006-09/MARC21slim.xsd");
		deref = resource.getBytes("UTF-8");

		String digest = DigestUtil.getSHA1Digest(deref);
		digest = DigestUtil.toURN(digest, "sha1");
		String id = "info:lanl-repo/ds/" + UUIDFactory.generateUUID().toString().substring(9);
		didWriter.addComponent(Marc.COMPONENT_TYPE.MARCXML, id, digest,
						AdoreConstants.MARC_XML_FORMATID, datestamp,
						"info:lanl-repo/creator/LOC", "application/xml", null,
						resource);
		
		// Now add component for LoC Image
		// 1. Obtain 856 values
		// 2. Construct URL and download image
		// 3. Compress image

        Pattern urlPattern = Pattern.compile("<marc:subfield code=\"u\">(.*)</marc:subfield>", Pattern.DOTALL);
        Matcher urlPattern_matcher = urlPattern.matcher(resource);
        if (urlPattern_matcher.find(1)) {
            String url = urlPattern_matcher.group();
            url = url.substring(url.indexOf(">") + 1, url.lastIndexOf("<"));
            url = url.substring(url.lastIndexOf("/") + 1);
            String[] a = url.split("\\.");
            if (a[0].equals("ppprs")) {
            	try {
				String imageUrl = "http://memory.loc.gov/pnp/" + a[0] + "/" + getRoundedIdentifier(a[1]) + "/" + a[1] + "u.tif";
				URL u = new URL(imageUrl);
				URLConnection uc = u.openConnection();
				uc.setRequestProperty(TAG_USER_AGENT, "aDORe-OAI-PMH-Client");
				InputStream inputStream = uc.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				KduCompressExe c = new KduCompressExe();
				c.compressImage(inputStream, baos, null);
				inputStream.close();
				String imageId = "info:lanl-repo/ds/" + UUIDFactory.generateUUID().toString().substring(9);
				String mimeType = "image/jp2";
				String imageDigest = DigestUtil.getSHA1Digest(baos.toByteArray());
				Date utcDate = new Date();
				String recordLength = Integer.toString(baos.toByteArray().length);
				InputStream is = new ByteArrayInputStream(baos.toByteArray());
				rw.write(imageId, mimeType, is, utcDate, recordLength, imageDigest, repoId, imageUrl);
				didWriter.addComponent(Marc.COMPONENT_TYPE.RESOURCE, imageId,
						         imageDigest, "info:lanl-repo/fmt/6", DateUtil.date2UTC(utcDate),
								"info:lanl-repo/creator/LANL", mimeType, null,
								null);
				is.close();
            	} catch (Exception e) {
            		// Don't worry about it... keep the marc and we'll move along
            	}
			}
        }
	}
    
	public ResourceRecord getResourceRecord(String sourceUri) throws ResourceException {
    	try {
    		File f = new File("/Users/rchute/tmp/cc5fc4f7-e50a-455f-b3ce-a6a8b54824e6/resolver.idx");
            BufferedReader idxReader = new BufferedReader(new FileReader(f));
            CSVReader reader = new CSVReader();
            InputStream is = null;
            String row = null;
            ArrayList<?> fields;
            for (int line = 0; true; line++) {
                row = idxReader.readLine();
                if (row == null)
                    break;
                if (line != 0) {
                    reader.parse(row);
                    fields = reader.list;
                    if (sourceUri.equals(((String) fields.get(7)))) {
                    	ResourceRecord r = new ResourceRecord();
                    	r.setDate((String) fields.get(0));
                    	r.setIdentifier((String) fields.get(1));
                    	r.setMimetype((String) fields.get(2));
                    	r.setChecksum((String) fields.get(3));
                    	r.setLength((String) fields.get(4));
                    	r.setResourceUri((String) fields.get(5));
                    	r.setRepositoryId((String) fields.get(6));
                    	r.setSourceUri((String) fields.get(7));
                    	return r;
					}
                }
            }
        } catch (Exception e) {
            throw new ResourceException("Error attempting to access resource directory", e);
        }
        return null;
	}
    
    private String getRoundedIdentifier(String id) {
    	boolean marked = false;
    	int t = 0;
    	int l = id.toCharArray().length;
    	StringBuffer sb = new StringBuffer();
    	for (char i : id.toCharArray()) {
    		t++;
    		if (i == '0')
    			sb.append(i);
    		else if (!marked && t < 4)
    			sb.append(i);
    		else
    			sb.append('0');
    	}
    	return sb.toString();
    }

    public String process(String marc, String id) throws Exception {
        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();

        // Create DOM for Old DIDL
        InputSource IS = new InputSource(new StringReader(marc));
        org.w3c.dom.Document doc = db.parse(IS);

        // Initialize AI DIDL Processor
        didWriter = new Marc();
        didWriter.setCopyright(COPYRIGHT);
        didWriter.setCollection(COLLECTION);

        // Package identifier - (i.e.
        // info:lanl-repo/i/cd7b17ea-bddf-11d9-9de5-c11b6cd85594)
        didWriter.setDocumentId(id);

        // Identifier of First Item
        NodeList idnodes = doc.getElementsByTagNameNS(OAINS, "identifier");

        // Content ID - (i.e. info:doi/10.1007/s10610-004-5886-2)
        Node node = idnodes.item(0);
        NodeList cnodes = node.getChildNodes();
        int nlength = cnodes.getLength();
        for (int i = 0; i < nlength; i++) {
            Node cnode = cnodes.item(i);
            if (cnode.getNodeType() == Node.TEXT_NODE) {
                didWriter.setContentId(cnode.getNodeValue());
            }
        }

        idnodes = doc.getElementsByTagNameNS(OAINS, "datestamp");
        // Content ID - (i.e. info:doi/10.1007/s10610-004-5886-2)
        node = idnodes.item(0);
        cnodes = node.getChildNodes();
        nlength = cnodes.getLength();
        for (int i = 0; i < nlength; i++) {
            Node cnode = cnodes.item(i);
            if (cnode.getNodeType() == Node.TEXT_NODE) {
            	datestamp = cnode.getNodeValue();
            }
        }
        
        Pattern recordmatch_pattern = Pattern.compile("(<marc:record.*?</marc:record>)", Pattern.DOTALL);
        Matcher recordmatch_matcher = recordmatch_pattern.matcher(marc);
        while (recordmatch_matcher.find()) {
            String record = recordmatch_matcher.group();
            getData(record);
        }

        return didWriter.getXML();
    }
    
    public static void main(String[] args) throws Exception {
    	String tapeName = "LOC_" + UUIDFactory.generateUUID().toString().substring(9);
    	String tapeDir = "/Users/rchute/tmp/";
    	MarcDidl marcDidlWriter = new MarcDidl();
        marcDidlWriter.openTapeWriter(tapeName, tapeDir);
    	marcDidlWriter.process();
    	marcDidlWriter.closeWriter();
    }
        
    public void process() throws Exception {
    	rw = new ResourceWriter("/Users/rchute/tmp/", rwIndex);
    	rw.open(repoId);
    	String _baseurl = "http://memory.loc.gov/cgi-bin/oai2_0";
    	String _set = "manz"; //"manz";
    	String _prefix ="marc21";
    	int count = 0;
    	gov.lanl.util.oai.oaiharvesterwrapper.ListRecords listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(_baseurl, null, null, _set, _prefix);
    	count += listRecords.size();
    	writeRecords(listRecords);
        String token = listRecords.getResumptionToken();
        while ((token != null) && (token.trim().length() != 0)) {
            listRecords = new gov.lanl.util.oai.oaiharvesterwrapper.ListRecords(_baseurl, token);
            count += listRecords.size();
            writeRecords(listRecords);
            token = listRecords.getResumptionToken();
        }
        rw.close(repoId);
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
        TapeRecord taperecord = new TapeRecord(id, date, didl);
        System.out.println(didl);
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
    
    public static final String TAG_USER_AGENT = "User-Agent";
    public byte[] getBinary(String url, String agent)
			throws MalformedURLException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		uc.setRequestProperty(TAG_USER_AGENT, agent);

		InputStream raw = uc.getInputStream();
		InputStream in = new BufferedInputStream(raw);

		int bufferSize = 4096;
		byte[] buffer = new byte[bufferSize];
		int bytesRead;

		while ((bytesRead = in.read(buffer, 0, bufferSize)) != -1) {
			out.write(buffer, 0, bytesRead);
			out.flush();
		}

		in.close();
		return out.toByteArray();

	}
    
    public String getPage(String url, String agent)
			throws MalformedURLException, IOException {
		URL u = new URL(url);
		URLConnection uc = u.openConnection();

		uc.setRequestProperty(TAG_USER_AGENT, agent);

		String cT = uc.getContentType();
		// System.out.println("contentType:" + cT);
		String encoding = null;
		if (cT != null) {
			int i = cT.indexOf(";");
			if (i > 0) {
				encoding = cT.substring(i + 1).trim();
				int j = encoding.indexOf("=");
				if (j > 0) {
					encoding = encoding.substring(j + 1).trim();
				}
			}
		}

		// this parameter not always set
		if (encoding == null) {
			encoding = uc.getContentEncoding();
		}

		if (encoding == null) {
			encoding = "UTF-8";
		}
		// System.out.println("encoding:" + encoding);

		String inputLine;
		StringBuffer b = new StringBuffer();
		BufferedReader d = new BufferedReader(new InputStreamReader(uc
				.getInputStream(), encoding));

		while ((inputLine = d.readLine()) != null) {
			b.append(inputLine);
		}
		d.close();

		return b.toString();
	}
}
