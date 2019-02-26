/* ArcUtils -- ARC file access utilities
 * Copyright (c) 2004 Statsbiblioteket
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package gov.lanl.arc.dkImpl;

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.zip.*;

import gov.lanl.arc.ARCProperties;
import gov.lanl.util.uuid.*;
import sun.misc.BASE64Decoder;

/**
 * A class that handles writing of ARC (v1) data.
 * Just handles headers and size. Doesn't handle ARC v2 files.
 *  
 */

public class ARCFileOutput {
    private String prefix = null;

    private OutputStream arcstream;

    private int byteswritten;

    private String name;

    private String date;

    public String organization;
    
    public String dsPrefix = ARCProperties.getLocalDataStreamPrefix();

    public String source;

    private String filename;

    private boolean zipped = false;

    private String base = "BASE64";

    private String flag = "0";

    public static int MAX_ARC_SIZE; // = 1000000000;

    private Date datecreated;

    /**
     * Create a new ARC file writer, using the given file name. File format will
     * be <name>.arc
     */
    public ARCFileOutput(String org, String name, boolean zipNext) {
        this.organization = org;
        this.name = name;
        this.source = "plain/text";
        this.zipNext = zipNext;
        datecreated = new Date();
    }

    /**
     * Create a new ARC file writer, using the given prefix. File format will be
     * <prefix>uuid.arc.  MaxSize is used to defined max size of each arc file.
     */
    public ARCFileOutput(String org, String prefix, String source, int maxsize) {
        this.organization = org;
        this.prefix = prefix;
        this.source = source;
        MAX_ARC_SIZE = maxsize;
    }

    /**
     * Create a new ARC file writer, using the given prefix. File format will be
     * <prefix>uuid.arc. Default of 1000000000 used for maxsize.
     */
    public ARCFileOutput(String org, String prefix, String source) {
        this.organization = org;
        this.prefix = prefix;
        this.source = source;
        MAX_ARC_SIZE = 1000000000;
    }

    private boolean zipNext = zipped;

    /**
     * Set the *next* ARC file to be zipped or not.
     * 
     * @param on
     *            If true, the next zip file written will be zipped, otherwise
     *            it will not.
     */
    public void setZipped(boolean on) {
        zipNext = on;
    }

    /**
     * Set base encoding type (e.g. base64)
     * @param define base encoding type
     */
    public void setBase(String base) {
        this.base = base;
    }

    public void setReserved(String flag) {
        this.flag = flag;
    }

    /**
     * Get the date on which the tape was created
     * @return Date object of creation
     */
    public Date getDate() {
        return datecreated;
    }

    /**
     * Strip directory path and extensions
     * @return file name minus path and extension
     */
    public String getFilename() {
        if (!zipNext) {
            //stripe off dir and .arc
            int jj = filename.lastIndexOf("/");
            int jjj = filename.lastIndexOf(".");
            String fname = filename.substring(jj + 1, jjj);
            return fname;
        } else {
            //stripe off dir and .arc.gz
            int jj = filename.lastIndexOf("/");
            int jjj = filename.lastIndexOf(".");
            String fname = filename.substring(jj + 1, jjj);
            int kk = fname.lastIndexOf(".");
            name = fname.substring(0, kk);
            return fname;
        }
    }

    /**
     * Set the exact name, date and organization to be used for this ARC file.
     * 
     * @param name
     *            A file name (without extensions). If null, the
     *            prefix+datestamp method will be used.
     * @param date
     *            A date string, in the format yyyyMMddHHmmss.
     * @param org
     *            An organization name. When this method is called, any
     *            previously open ARC file is closed, and a new file is readied
     *            for further writing, using the new name, date and organization
     *            This should only be used for testing, in practice you always
     *            want the automagically generated timestamp.
     */
    public void setMetaInfo(String name, String date, String org)
            throws IOException {
        this.name = name;
        this.date = date;
        this.organization = org;
        makeNewArcFile();
    }

    public void makeNewArcFile() throws IOException {
        if (arcstream != null)
            arcstream.close();
        zipped = zipNext;
        StringBuffer datebuf = new StringBuffer();
        //String filename;
        String date;
        if (name == null) {
            /* Does time formatting have to be this complex? */
            new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(), datebuf,
                    new FieldPosition(0));
            date = datebuf.toString();
            /* LB changed date to uuid */
            String uuid = UUIDFactory.generateUUID().toString();
            // System.out.println("uuid:" + uuid);
            uuid = uuid.substring(9);
            filename = prefix + uuid + ".arc" + (zipped ? ".gz" : "");

            // filename = prefix+date+".arc"+(zipped?".gz":"");
        } else {

            new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(), datebuf,
                    new FieldPosition(0));
            date = datebuf.toString();

            if (name.endsWith("arc"))
                filename = name;
            else
                filename = name + ".arc" + (zipped ? ".gz" : "");
        }
        arcstream = new FileOutputStream(filename);
        byteswritten = 0;
        int jj = filename.lastIndexOf("/");
        String cname = filename.substring(jj + 1);
        writeChunk("filedesc://" + cname, "0.0.0.0", date, source, "1 " + flag
                + " " + organization + "\n"
                + "URL IP-address Archive-Date Content-Type Archive-length\n");
        filename = this.filename;
    }

    /**
     * Write a string to the current ARC file. The file length must already have
     * been checked before writing.
     */
    protected void write(byte[] bytes) throws IOException {
        arcstream.write(bytes, 0, bytes.length);
        byteswritten += bytes.length;
        //System.out.println("Wrote up to "+byteswritten);
    }

    /**
     * Write an ARC chunk to the file. Does not check file size.
     */
    public void writeChunknolimit(String url, String ip, String mime,
            byte[] body) throws IOException {
        int size = body.length;
        StringBuffer datebuf = new StringBuffer();
        new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(), datebuf,
                new FieldPosition(0));
        date = datebuf.toString();
        String header = (url.startsWith("filedesc:") ? "" : "\n") + url + " "
                + ip + " " + date + " " + mime + " " + size + "\n";
        byte[] headerbytes = header.getBytes();

        if (arcstream == null) {
            makeNewArcFile();
        }

        if (zipped) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            GZIPOutputStream zipout = new GZIPOutputStream(bytes);
            zipout.write(headerbytes, 0, headerbytes.length);
            zipout.write(body, 0, body.length);
            zipout.finish();
            byte[] zippedbytes = bytes.toByteArray();
            zipout.close();
            bytes.close();
            write(zippedbytes);
        } else {
            write(headerbytes);
            write(body);
        }
        arcstream.flush();
    }

    /**
     * Write an ARC chunk to the file. Checks file size.
     */
    public void writeChunk(String url, String ip, String date, String mime,
            byte[] body) throws IOException {
        int size = body.length;
        String header = (url.startsWith("filedesc:") ? "" : "\n") + url + " "
                + ip + " " + date + " " + mime + " " + size + "\n";
        byte[] headerbytes = header.getBytes();

        if ((arcstream == null)
                || byteswritten + headerbytes.length + body.length > MAX_ARC_SIZE) {
            name = null;
            makeNewArcFile();
        }

        //System.out.println("Writing "+url+" mime "+mime);
        if (zipped) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            GZIPOutputStream zipout = new GZIPOutputStream(bytes);
            zipout.write(headerbytes, 0, headerbytes.length);
            zipout.write(body, 0, body.length);
            zipout.finish();
            byte[] zippedbytes = bytes.toByteArray();
            zipout.close();
            bytes.close();
            write(zippedbytes);
        } else {
            write(headerbytes);
            write(body);
        }
        arcstream.flush();
    }

    /** 
     * Writes an ARC chunk to the file. Checks file size. 
     */
    public void writeChunk(String url, String ip, String date, String mime,
            String body) throws IOException {

        writeChunk(url, ip, date, mime, body.getBytes());
    }

    /**
     * Writes an ARC chunk to the file. Made it to use with perl String body
     * base64 encoded string
     */
    public void writeChunk(String url, String ip, String mime, String body)
            throws IOException {
        StringBuffer datebuf = new StringBuffer();
        new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(), datebuf,
                new FieldPosition(0));
        String date = datebuf.toString();

        if (base.equals("BASE64")) {
            byte[] decoded;
            BASE64Decoder decoder = new BASE64Decoder();
            decoded = decoder.decodeBuffer(body);
            writeChunk(url, ip, date, mime, decoded);
        } else {
            writeChunk(url, ip, date, mime, body.getBytes());
        }
        datebuf.delete(0, datebuf.length());

    }

    /**
     * Generate a UUID compliant arc file name of format <PREFIX><UUID><EXT>
     * @param prefix
     * @param compression
     * @return
     */
    public static String getUUIDArcName(String prefix, boolean compression) {
        String uuid = UUIDFactory.generateUUID().toString();
        uuid = uuid.substring(9);
        return prefix + uuid + ".arc" + (compression ? ".gz" : "");
    }
    
    /**
     * Generate a UUID compliant resource id
     * @return uuid for resource including local datastream prefix
     */
    public String getUUIDResourceURI() {
        String uuid = UUIDFactory.generateUUID().toString();
        uuid = uuid.substring(9);
        return dsPrefix + uuid;
    }
    
    /**
     * Close Arc File Output Stream
     */
    public void close() {
        try {
            if (arcstream != null)
                arcstream.close();
        } catch (IOException e) {
        }
    }
}