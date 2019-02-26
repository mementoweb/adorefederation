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
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 * General File Utility Class
 * 
 * @author rchute
 */
public class FileUtil {

    /**
     * Decompress GZIP file, pass in absolute path to compressed file
     * @param inFilename
     * @return GZIPInputStream 
     */
    public static InputStream streamFromGZIPFile(String inFilename) {
        GZIPInputStream in = null;
        try {
            // Open the compressed file
            in = new GZIPInputStream(new FileInputStream(inFilename));
        } catch (IOException e) {
            System.out.println("Exception attempting to decompress gzip file:");
            e.printStackTrace();
        }
        return in;
    }
    
    public static boolean serialize(InputStream is, String file) {
        OutputStream out = null;
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(is);
            out = new BufferedOutputStream(new FileOutputStream(new File(file)));
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
    
    public static InputStream getInputStream(String file) throws IOException {
    	return getInputStream(new File(file));
    }
    
    public static InputStream getInputStream(File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }
    
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        byte[] bytes = new byte[(int)length];
    
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        is.close();
        return bytes;
    }
    
    public static String getContents(File src) {
        InputStream in = null;
        StringBuffer sb = new StringBuffer();
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        try {
            in = new BufferedInputStream(new FileInputStream(src));
            buf = new byte[bufLen];
            byte[] tmp = null;
            int len = 0;
            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                sb.append(new String(tmp));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception e) {
                }
        }
        return sb.toString();
    }
    
    public static boolean copyFile(File src, File dest) {
        InputStream in = null;
        OutputStream out = null;
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        try {
            in = new BufferedInputStream(new FileInputStream(src));
            out = new BufferedOutputStream(new FileOutputStream(dest));
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
    
    public static boolean decompressGZIPFile(File src, File dest) {
        InputStream in = null;
        OutputStream out = null;
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        try {
            InputStream is;
            if (src.getAbsolutePath().endsWith(".gz"))
                is = streamFromGZIPFile(src.getAbsolutePath());
            else
                is = new FileInputStream(src);
            in = new BufferedInputStream(is);
            out = new BufferedOutputStream(new FileOutputStream(dest));
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
    
    /**
     * Gets a ArrayList of File objects provided a dir or file path.
     * @param filePath
     *        Absolute path to file or directory
     * @param fileFilter
     *        Filter dir content by extention; allows "null"
     * @param recursive
     *        Recursively search for files
     * @return
     *        ArrayList of File objects matching specified criteria.
     */
    public static ArrayList<File> getFileList(String filePath, FileFilter fileFilter, boolean recursive) {
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            File[] fa = file.listFiles(fileFilter);
            for (int i = 0; i < fa.length; i++) {
                if (fa[i].isFile())
                  files.add(fa[i]);
                else if (recursive && fa[i].isDirectory())
                  files.addAll(getFileList(fa[i].getAbsolutePath(), fileFilter, recursive));
            }
        }
        else if (file.exists() && file.isFile()) {
            files.add(file);
        }
        
        return files;
    }
}
