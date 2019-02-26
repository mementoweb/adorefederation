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

package gov.lanl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class StreamUtil {
    
    public static InputStream getInputStream(URL location) throws Exception {
        InputStream in;
        try {
            HttpURLConnection huc = (HttpURLConnection) (location.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                in = huc.getInputStream();
            } else
                throw new Exception("Cannot get " + location.toString());
        } catch (MalformedURLException e) {
            throw new Exception("A MalformedURLException occurred for " + location.toString());
        } catch (IOException e) {
            throw new Exception("An IOException occurred attempting to connect to " + location.toString());
        }
        return in;
    }
    
    public static OutputStream getOutputStream(URL location) throws Exception {
        return StreamUtil.getOutputStream(getInputStream(location));    
    }
    
    public static OutputStream getOutputStream(InputStream ins) throws java.io.IOException, Exception {
        return getOutputStream(ins, 4096);
    }
    
    public static OutputStream getOutputStream(InputStream ins, int bufferSize) throws java.io.IOException, Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];
        int count = 0; 
        BufferedInputStream bis = new BufferedInputStream(ins);
        while  ((count = bis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
        } 
        baos.close();
        return baos;
    }
    
    public static byte[] getByteArray(InputStream ins) throws java.io.IOException, Exception {
        return ((ByteArrayOutputStream) getOutputStream(ins)).toByteArray();
    }
    
    public static byte[] unzipStream(InputStream ins) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream in = new GZIPInputStream(ins);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            baos.write(buf, 0, len);
        }
        return baos.toByteArray();
    }
    
    public static boolean copyStream(InputStream in, OutputStream out) {
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        try {
            buf = new byte[bufLen];
            byte[] tmp = null;
            int len = 0;
            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                out.write(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception e) {
                }
            if (out != null)
                    try {
                        out.close();
                    } catch (Exception e) {
                    }
        }
        return true;
    }
}
