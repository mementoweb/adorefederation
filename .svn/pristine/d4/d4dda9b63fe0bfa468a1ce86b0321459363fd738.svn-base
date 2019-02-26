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

import gov.lanl.arc.heritrixImpl.ARCFileWriter;
import gov.lanl.ingest.IngestConstants;
import gov.lanl.util.csv.CSVWriter;
import gov.lanl.util.uuid.UUIDFactory;
import gov.lanl.xmltape.SeqTapeReader;
import gov.lanl.xmltape.TapeException;
import gov.lanl.xmltape.TapeRecord;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.archive.io.arc.ARCReader;
import org.archive.io.arc.ARCReaderFactory;
import org.archive.io.arc.ARCRecord;
import org.archive.io.arc.ARCRecordMetaData;

/**
 * SingleTapeIngester is controlling of processing of individual xmltape
 * 
 * 
 * @author Lyudmila Balakireva,Research Library,LANL
 */

public class SingleTapeIngester implements IngestConstants {

    private ARCFileWriter _arcw;

    private DerefProcessor processor;

    private String workdir;

    private CSVWriter _goodlog;

    private CSVWriter _badlog;
    
    private boolean errorLogAvail = false;

    private Vector dates;

    private String org;
    
    private static SimpleDateFormat df;

    public static String ARCIDPREFIX;

    static Logger log = Logger.getLogger(SingleTapeIngester.class.getName());

    static {
        df = new SimpleDateFormat(FORMAT_INGEST_DATE_TIME);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    /**
     * this method creates arc and log writers as well as plugin instance
     * 
     * @param dir
     *            directory where to create arc file
     * @param arcfileprefix
     *            used to construct arc file name <arcfileprefix>uuid.arc
     * @param org
     *            organization name to write into arc file header
     * @param prclass
     *            plugin - fully qualified class name
     */

    public void setUP(String dir, String arcfileprefix, String org,
            String prclass, String maxsize, String arcidprefix) {

        this.workdir = dir;
        ARCIDPREFIX = arcidprefix;
        String arcname = workdir + arcfileprefix;
        this.org = org;
        try {
            this.processor = (DerefProcessor) Class.forName(prclass).newInstance();
             Integer i = new Integer(maxsize);
            this._arcw = new ARCFileWriter(workdir, arcfileprefix);
            PrintWriter pw = new PrintWriter( new FileWriter(workdir + _arcw.getFileID() + "-ok.csv"));
            errorLogAvail = false;
            this._goodlog  = new CSVWriter(pw, false, ',', System.getProperty("line.separator") );
            this._goodlog.writeCommentln("tape_record_id, arc_id, arc_date, ref, derefXPath, sourceURI, digest, localIdentifier ");
        } catch (IOException e) {
            log.error("IO problem with arc " + arcname + " " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Set UP problem with arc" + arcname + " "
                    + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * this method lanches processing
     * 
     * @param tapename
     *            to parse
     * @param dir
     *            directory where to create arc file
     * @param prefix
     *            used to construct arc file name <arcfileprefix>uuid.arc
     * @param org
     *            organization name to write into arc file header
     * @param prclass
     *            plugin - fully qualified class name
     * @throws Exception
     */

    public void ingestTape(String tapename, String dir, String prefix,
            String org, String prclass, String maxsize, String arcidprefix)
            throws IOException {
        
        try {
            this.setUP(dir, prefix, org, prclass, maxsize, arcidprefix);
            // TODO: Determine is gzip needs to be handled here
            SeqTapeReader reader = new SeqTapeReader(tapename);
            reader.open();
            TapeRecord r = reader.next();
            while (r!=null){
                this.process(r.getMetadata(),r.getIdentifier(),r.getDatestamp());
                r = reader.next();
            }
            this.endClose();
        } catch (TapeException e) {
              log.error("TapeReading:" + e.getMessage(),e);
                throw new RuntimeException(e);
          } catch (Exception ex) {
                log.error("IngestTape:" + ex.getMessage(),ex);
                throw new RuntimeException(ex);
          }
    }


    public static void main(String[] args) {

        if (args.length != 5) {
            printHelp();
            System.exit(-1);
        }

        SingleTapeIngester ingest = new SingleTapeIngester();
        String tapename = args[0];
        String dirtowritearcfile = args[1];
        String prefixforarcfile = args[2];
        String prclass = args[3];
        String org = args[4];
        String maxsize = args[5];
        String arcidprefix = args[6];
        ingest.setUP(dirtowritearcfile, prefixforarcfile, org, prclass,
                maxsize, arcidprefix);

        /** parse Tape from FS or System.in and write arc and control files */
        SeqTapeReader reader;
        try {
            reader = new SeqTapeReader(tapename);
            reader.open();
            TapeRecord r = reader.next();
            while (r!=null){
               ingest.process(r.getMetadata(),r.getIdentifier(),r.getDatestamp());
               r = reader.next();
        }
        } catch (TapeException e) {
            e.printStackTrace();
        }

        /** close arc writer and control files */

        ingest.endClose();

    }

    /**
     * this utility method prints list of parameters needed to execute the
     * program
     */

    public static void printHelp() {
        StringBuffer usage = new StringBuffer();
        usage.append("IntermidIngest <tape>  <dir to write arc>  <prefix for arc file> \n");
        usage.append("Example:\n");
        usage.append("IntermidIngest  ");
        usage.append("/aps/data/.2005-03-29-12:27:37/aps_6845c913-7930-4708-973e-c3211cf3c0c0.xml");
        usage.append("  /aps/data/  aps_test_  gov.lanl.ingest.aps.DidlSigProcessor info:sid/library.lanl.gov \n");
        System.out.println(usage.toString());

    }

    /**
     * this method call back process in OAIRecordParser
     * 
     * @param record
     * @param id
     * @param datestamp
     *  
     */

    public void process(String record, String id, String datestamp) {
        ARCFileWriter arcwriter = null;
        String tempArcPrefix = "tmp";
        File tempArcFile;
        try {
            String tmparc = tempArcPrefix    + UUIDFactory.generateUUID().toString().substring(9) + ".arc";
            String tmpFile = workdir + tmparc;
            
            arcwriter = new ARCFileWriter(workdir, tmparc, false);
            ProcessInfo info = processor.processContent(record, arcwriter);

            Vector nLogInfo = info.returnVectorofLogInfo();
            boolean shouldCommit = info.getStatus();

            if (shouldCommit) {
                if (nLogInfo.size() > 0) {
                    Vector arcnames = this.appendARC(tmpFile);
                    for (int i = 0; i < nLogInfo.size(); i++) {
                        String[] loginfo = (String[]) nLogInfo.get(i);
                        _goodlog.write(id);
                        _goodlog.write((String) arcnames.get(i));
                        _goodlog.write((String) this.dates.get(i));
                        _goodlog.write(loginfo);
                        _goodlog.writeln();
                    }
                }
            } else {
                if (!errorLogAvail) {
                    initializeErrorLog();
                }
                    
                StringBuffer localdatebuf = new StringBuffer();
                Date currentDate = new Date();
                df.format(currentDate, localdatebuf, new FieldPosition(0));
                String localdate = localdatebuf.toString();

                if (nLogInfo.size() == 0) {
                    _badlog.write(id);
                    _badlog.write(localdate);
                    String message = info.getMessage();
                    if (message != null && message != "")
                        _badlog.write(info.getMessage());
                    else if (record.contains("status=\"deleted\""))
                        _badlog.write("Record Previously Deleted");
                    else 
                        _badlog.write("");
                    _badlog.writeln();
                } else {
                    int total = nLogInfo.size();
                    String[] loginfo = (String[]) nLogInfo.get(total - 1);
                    _badlog.write(id);
                    _badlog.write(localdate);
                    _badlog.write(loginfo);
                    _badlog.writeln();
                }
            }
            arcwriter.close();
            arcwriter = null;
        } catch (Exception ex) {
            log.error("process:" + ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (arcwriter != null)
                   arcwriter.close();
                deleteFiles(workdir, tempArcPrefix);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void initializeErrorLog() throws IOException {
        PrintWriter pw2 = new PrintWriter(new FileWriter(workdir + _arcw.getFileID() + "-bad.csv"));
        this._badlog  = new CSVWriter(pw2, false, ',',  System.getProperty("line.separator") );
        this._badlog.writeCommentln("tape_record_id, arc_date, message");
        this.errorLogAvail = true;
    }
    
    public boolean deleteFile(String fullFileName) {
        File fileObj = new File(fullFileName);

        if (!fileObj.exists())
            return false;
        // Delete only files, not directories/folders
        if (fileObj.isDirectory())
            return false;

        int maxSleep = 50;
        int incSleep = 5;
        int totalSleep = 0;
        boolean resultFlag = false;
        int i = 1;
        // Repeatetively attempt to delete file until success or max time
        // exceeded
        while (!(resultFlag = fileObj.delete()) && (totalSleep < maxSleep)) {
            // ok may be there is some problem in the file handles etc.
            // do a GC and sleep for some time.
            System.gc();

            try {
                Thread.sleep(incSleep);
            } catch (InterruptedException ex) {
            }

            totalSleep += incSleep;
            i++;
            log.debug(" Wait so far = " + totalSleep + "  Attempts = " + i);
        }

        log.debug(" deleted = " + resultFlag + " total Attempts = " + i);
        return resultFlag;
    }
    
    public void deleteFiles(String d, String e) {
        PrefixFilter filter = new PrefixFilter(e);
        File dir = new File(d);

        String[] list = dir.list(filter);
        File file;
        if (list.length == 0)
            return;

        for (int i = 0; i < list.length; i++) {
            boolean isdeleted = deleteFile(d + list[i]);
            log.debug(d + list[i]);
            log.debug("deleted: " + isdeleted);
        }
    }
    
    public void endClose() {
        try {
            if (_arcw != null)
              _arcw.close();
            if (_goodlog != null)
              _goodlog.close();
            if (errorLogAvail)
              _badlog.close();
        	processor.finalize();
        } catch (Exception e) {
            log.error("Problem to close arc or cvs files");
            throw new RuntimeException(e);
        }
    }

    /**
     * this method appends temporary arc file to permanent one
     * 
     * @param name
     *            of temporary file
     * @return Vector of arc permanent file names
     */

    public Vector appendARC(String name) {
        try {
            ARCReader reader = ARCReaderFactory.get(new File(name));
            
            Vector filenames = new Vector();
            this.dates = new Vector();

            while (reader.hasNext()) {
                StringBuffer localdatebuf = new StringBuffer();
                ByteArrayOutputStream byteout = new ByteArrayOutputStream();
                ARCRecord r = (ARCRecord) reader.next();

                ARCRecordMetaData m = r.getMetaData();
                //int j = 0;
                //while ((j = r.read()) != -1) {
                //    byteout.write(j);
                //}
                
                // Buffered Bytes Method
                byte[] buffer = new byte[128];
                int count = 0; 
                BufferedInputStream bis = new BufferedInputStream(r);
                while  ((count = bis.read(buffer)) != -1) {
                      byteout.write(buffer, 0, count);
                } 

                Date currentDate = new Date();
                df.format(currentDate, localdatebuf, new FieldPosition(0));
                this.dates.add(localdatebuf.toString());
                _arcw.write(m.getUrl(), m.getIp(), m.getMimetype(), byteout.toByteArray(), currentDate.getTime());
                byteout.close();
                filenames.add(_arcw.getFileID());
                localdatebuf.delete(0, localdatebuf.length());

            }
            return filenames;
        } catch (Exception e) {
            log.error("Append problem:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    class PrefixFilter implements FilenameFilter {
        private String prefix;

        public PrefixFilter(String prefix) {
            this.prefix = prefix;
        }

        public boolean accept(File dir, String name) {
            return (name.startsWith(prefix));
        }
    }

}