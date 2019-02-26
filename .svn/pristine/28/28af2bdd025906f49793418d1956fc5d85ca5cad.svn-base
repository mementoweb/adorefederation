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

package gov.lanl.arc.dkImpl;

import gov.lanl.arc.ARCProperties;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ARCWriter.java
 * 
 * Program to write ARC file from directory structure
 * 
 * @author ludab
 *
 */

public class ARCFileWriter {

    String date;

    public String getDateCreated() {
        return this.date;
    }

    public static void main(String args[]) {
        try {

            String arcdir = args[0]; // directory to write arc file
            String arcfilename = args[1]; //name of tape or arcfile
            String preload = args[2]; // preload directory
            String prefix = null; // we ignore it

            if (!preload.endsWith(File.separator));
               preload = preload + File.separator;
            
            StringBuffer datebuf = new StringBuffer();
            new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(), datebuf,
                    new FieldPosition(0));
            String date = datebuf.toString();

            ARCFileOutput arcw = new ARCFileOutput(ARCProperties.getLocalOpenUrlReferrerID(),
                    prefix, "text/plain");
            arcw.setMetaInfo(arcdir + arcfilename, date,
                    ARCProperties.getLocalOpenUrlReferrerID());
            arcw.setZipped(false);

            String rawdir = preload + "file" + File.separator + arcfilename;
            String mimetype = null;

            String[] dir = new java.io.File(rawdir).list();

            //System.out.println(dir.length);
            StringBuffer localdatebuf = new StringBuffer();

            for (int i = 0; i < dir.length; i++) {
                int jj = dir[i].lastIndexOf(".");
                String ext = dir[i].substring(jj + 1);
                String id = dir[i].substring(0, jj);

                if (ext.equals("xml")) {
                    mimetype = "application/biosis+xml";
                } else if (ext.equals("txt")) {
                    mimetype = "text/plain";
                } else {
                    mimetype = "unknown";
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataInputStream is = new DataInputStream(new FileInputStream(
                        rawdir + "/" + dir[i]));

                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(),
                        localdatebuf, new FieldPosition(0));
                String localdate = localdatebuf.toString();

                baos.write("\n".getBytes());
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesRead;
                while ((bytesRead = is.read(buffer, 0, bufferSize)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }

                baos.write("\n".getBytes());
                arcw.writeChunk(ARCProperties.getLocalDataStreamPrefix() + id, "0.0.0.0",
                        localdate, mimetype, baos.toByteArray());
                localdatebuf.delete(0, localdatebuf.length());
                String fullname = arcw.getFilename();
                //  System.out.println("ARCfile:" + fullname);
            }
            arcw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
