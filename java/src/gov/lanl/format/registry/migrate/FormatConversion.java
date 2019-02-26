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

package gov.lanl.format.registry.migrate;

import gov.lanl.format.registry.FormatException;
import gov.lanl.repo.oaidb.formatreg.FormatRegistryConstants;
import gov.lanl.repo.oaidb.formatreg.FormatXML;
import gov.lanl.repo.oaidb.srv.PutPostClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import ORG.oclc.oai.harvester.verb.Record;

public class FormatConversion implements FormatRegistryConstants {
    private ArrayList<FormatItem> oldRecords = new ArrayList<FormatItem>();
    private ArrayList<FormatRecord> newRecords = new ArrayList<FormatRecord>(); 
    private String _srcURL;
    private FormatXML xmlFormatter = new FormatXML();

    /**
     * Read format index from format registry
     * 
     * @param srcUrl
     *            OAI-PMH baseurl of format index
     * @param validate
     *            XML schema validation of FormatIndex
     */
    public FormatConversion(String srcUrl, boolean validate) throws FormatException {
        try {
            _srcURL = srcUrl;
            ORG.oclc.oai.harvester.verb.ListRecords lr;
            lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(_srcURL),
                    null, null, null, "format");
            
            for (Iterator it = lr.iterator(); it.hasNext();) {
                Record r = (Record) it.next();
                FormatItem fi = new FormatItem(r.getIdentifier(), r
                        .getDatestamp(), r.getMetadata(), validate);
                oldRecords.add(fi);
            }

            while (lr.getResumptionToken() != null) {
                lr = new ORG.oclc.oai.harvester.verb.ListRecords(new URL(
                        _srcURL), lr.getResumptionToken());
                for (Iterator it = lr.iterator(); it.hasNext();) {
                    Record r = (Record) it.next();
                    FormatItem fi = new FormatItem(r.getIdentifier(), r
                            .getDatestamp(), r.getMetadata(), validate);
                    oldRecords.add(fi);
                }
            }
            convert();
        } catch (Exception ex) {
            throw new FormatException("Error in format registry conversion", ex);
        }
    }
    
    public ArrayList<FormatRecord> getRecords() {
        return newRecords;
    }
    
    private void convert() throws Exception {
        for (FormatItem f : oldRecords) {
            Properties props = new Properties();
            String id = f.getOAIIdentifier();
            String date = f.getDatestamp();
            String fmt = null;
            String desc = null;
            props.put(ELEMENT_FMT_ID, id);
            for (int i=0; i < f.getDCIdentifiers().size(); i++) {
                if (i > 0)
                    props.put(ELEMENT_FMT_DC_ID + "." + i, (String) f.getDCIdentifiers().get(i));
                else
                    props.put(ELEMENT_FMT_DC_ID, (String) f.getDCIdentifiers().get(i));
            }
            
            if (f.getDIDentity() != null)
                props.put(ELEMENT_FMT_DIDENTITY, f.getDIDentity());
            if (f.getOntology() != null)
                props.put(ELEMENT_FMT_ONTOLOGY, f.getOntology());
            if (f.getMimetype() != null) {
                if (f.getMimetype() != null)
                    props.put(ELEMENT_FMT_MIMETYPE, getMimetype(f.getMimetype()));
                if ((fmt = getFormat(f.getMimetype())) != null)
                    props.put(ELEMENT_FMT_DC_FMT, fmt);
                props.put(ELEMENT_FMT_DC_SRC, getSource(f.getMimetype()));
                if ((desc = getDescription(f.getMimetype())) != null)
                    props.put(ELEMENT_FMT_DC_DESC, desc);
            }
            
            String xml = xmlFormatter.ProptoXML(props);
            newRecords.add(new FormatRecord(id, date, xml));
        }
    }
    
    public HashMap<String, String> postRecords(String destUrl) {
        PutPostClient client = new PutPostClient();
        HashMap<String, String> status = new HashMap<String, String>(); 
        for (FormatRecord record : newRecords) {
            try {
                client.execPut(destUrl, record.getXml());
                status.put(record.getId(), "Success");
            } catch (Exception e) {
                status.put(record.getId(), "An error occurred posting: " + record.getXml());
            }
        }
        return status;
    }
    
    public static void main(String[] args) {
        try {
            FormatConversion fc = new FormatConversion(args[0], false);
            HashMap<String, String> status = fc.postRecords(args[1]);
            for (String key : status.keySet()) {
                System.out.println(key + ": " + status.get(key));
            }
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }
    
    private static String getMimetype(String mimetype) {
        if (mimetype.endsWith("+xml") && !ex.contains(mimetype))
            return "application/xml";
        else
            return mimetype;
    }
    
    private static String getFormat(String mimetype) {
        if (mimetype.endsWith("+xml") && !ex.contains(mimetype)) {
            String c = mimetype.substring(mimetype.lastIndexOf("/") + 1, mimetype.lastIndexOf("+"));
            if (c.equalsIgnoreCase("biosis"))
                return "http://purl.lanl.gov/vendors/schemas/Biosis/2006-02/BIOSISPreviews.dtd";
            else if (c.equalsIgnoreCase("eix"))
                return "http://purl.lanl.gov/vendors/schemas/EIX/2006-02/EIX.xsd";
            else if (c.equalsIgnoreCase("isi"))
                return "http://purl.lanl.gov/vendors/schemas/ISI/2006-03/ISI.xsd";
            else if (c.equalsIgnoreCase("inspec"))
                return "http://purl.lanl.gov/vendors/schemas/Inspec/2006-03/Inspec_from_dtd.xsd";
            else if (c.equalsIgnoreCase("inspecbf"))
                return "http://purl.lanl.gov/vendors/schemas/InspecBf/2005-12/Inspec_Backfile.dtd";
            else if (c.equalsIgnoreCase("marc"))
                return "http://purl.lanl.gov/vendors/schemas/MARC/2006-07/MARC21slim.xsd";
            else 
                return null;
        } else 
            return null;
        
    }
    
    private static String getSource(String mimetype) {
        if (mimetype.endsWith("+xml") && !ex.contains(mimetype)) {
            return "info:lanl-repo/creator/LANL-RTF";
        } else {
            return "http://www.iana.org/assignments/media-types/";
        }
    }
    
    private static String getDescription(String mimetype) {
        if (mimetype.endsWith("+xml")) {
            String c = mimetype.substring(mimetype.lastIndexOf("/") + 1, mimetype.lastIndexOf("+"));
            return "Defines " + c + " XML Structure";
        } else {
            return null;
        }
    }
    
    static ArrayList<String> ex;
    static {
        ex = new ArrayList<String>();
        ex.add("application/vnd.criticaltools.wbs+xml");
        ex.add("application/beep+xml");
        ex.add("application/vnd.irepository.package+xml");
        ex.add("application/cnrp+xml");
        ex.add("application/cpl+xml");
        ex.add("application/vnd.liberty-request+xml");
        ex.add("application/vnd.llamagraphics.life-balance.exchange+xml");
        ex.add("application/vnd.pwg-xhtml-print+xml");
        ex.add("application/vnd.wv.csp+xml");
        ex.add("application/vnd.wv.ssp+xml");
        ex.add("application/watcherinfo+xml");
        ex.add("application/xhtml+xml");
        ex.add("application/pidf+xml");
        ex.add("application/reginfo+xml");
    }
}
