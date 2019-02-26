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

package gov.lanl.ingest.oaitape.modoai;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.ingest.IngestProperties;
import gov.lanl.ingest.oaitape.DerefProcessor;
import gov.lanl.ingest.oaitape.IngestRecord;
import gov.lanl.ingest.oaitape.OAITapeUtils;
import gov.lanl.ingest.oaitape.ProcessInfo;
import gov.lanl.util.uuid.UUIDFactory;

import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

/**
 * DidlNoSigProcessor is plugin for DIDL metadata without XMLsignatures
 */

public class DidlNoSigProcessor extends DerefProcessor implements
        ComponentParserInterface {

    static Logger log = Logger.getLogger(DidlNoSigProcessor.class.getName());

    ARCFileWriter arcwriter;
    IngestRecord csv;
    ProcessInfo info;

    public ProcessInfo processContent(String record, ARCFileWriter arcwriter) {
        this.info = new ProcessInfo();
        this.arcwriter = arcwriter;
        try {
            long start = System.currentTimeMillis();
            ComponentParser cp = new ComponentParser(record, this);
            info.setStatus(true);

            long end = System.currentTimeMillis();
            double elapsed = end - start;
            log.info("totaltime:" + elapsed);
        } catch (Exception e) {
            info.setStatus(false);
            info.setMessage(e.getMessage());
            log.warn("DidlNoSigProcessor:" + e.getMessage());
        }
        return this.info;
    }

    /**
     * Dereferences MODOAI Datastream to ARC File, indicates results in CSV
     * 
     * @param resource
     * @param att
     * 
     * Generate CSV log indicating result of request
     * Log Format:  ref,xpath,digest,id
     */
    
    public void getData(String resource, Map att) {

        // csv values: ref,xpath,digest,id
        int rindex = ((Integer) att.get("rindex")).intValue();
        int cindex = ((Integer) att.get("cindex")).intValue();
        csv = new IngestRecord();
        OAITapeUtils util = new OAITapeUtils();

        // derefs first resource skips alls others per item.

        if (rindex == 1) {
            String ref = null;
            if (att.containsKey("ref")) {
                ref = (String) att.get("ref");
                csv.setRef(ref);
            } 

            String mimetype = (String) att.get("mimetype");
            cindex = cindex - 1;
            String cindex_str = (new Integer(cindex)).toString();
            byte[] deref = null;

            try {
                if (ref != null) {
                    String derefxpath = "//didl:Component[" + cindex
                            + "]/didl:Resource[0]/@ref";
                    csv.setDerefXPath(derefxpath);
                    String contenc = null;
                    if (att.containsKey("zipencoding")) {
                        contenc = (String) att.get("zipencoding");
                    }
                    deref = util.resolveRef(ref);

                    if (contenc != null) {
                        if (contenc.equals("gzip")) {
                            deref = util.unzipStream(deref);
                        }
                    }
                } else {
                    String derefxpath = "//didl:Component[" + cindex_str
                            + "]/didl:Resource[0]/*";

                    csv.setDerefXPath(derefxpath);

                    String encoding = null;
                    if (att.containsKey("encoding")) {
                        encoding = (String) att.get("encoding");
                    }

                    if (encoding != null) {
                        if (encoding.equals("base64")) {
                            BASE64Decoder decoder = new BASE64Decoder();
                            deref = decoder.decodeBuffer(resource);
                        }
                    } else {
                        deref = resource.getBytes("UTF-8");
                    }

                }
                String digest = "urn:sha1:"
                        + util.calculateDigest(deref, "SHA1");
                String arcid = IngestProperties.getLocalDataStreamPrefix()
                        + UUIDFactory.generateUUID().toString().substring(9);
                csv.setLocalIdentifier(arcid);
                csv.setDigest(digest);
                arcwriter.write(arcid, "0.0.0.0", mimetype, deref);
                String[] strArray = csv.toArray();
                info.addLogInfo(strArray);

            }

            catch (Exception e) {
                csv.setMessage(e.getMessage());
                String[] strArray = new String[1];
                strArray[0] = csv.getMessage();
                info.addLogInfo(strArray);
                e.printStackTrace();
                log.warn("DidlNoSigProcessorComponent:"
                        + Arrays.toString((String[]) strArray));
                throw new RuntimeException(e.getMessage());
            }

        }

    }

}
