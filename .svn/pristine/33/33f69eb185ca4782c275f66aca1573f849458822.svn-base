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

package gov.lanl.resource.filesystem;

import gov.lanl.resource.ResourceException;
import gov.lanl.resource.ResourceIndexInterface;
import gov.lanl.resource.ResourceRecord;
import gov.lanl.resource.ResourceWriterInterface;
import gov.lanl.util.DateUtil;
import gov.lanl.util.DigestUtil;
import gov.lanl.util.MimeTypeMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class ResourceWriter implements ResourceWriterInterface {
    private String baseDir;
    private ResourceIndexInterface idx;
    private static MimeTypeMapper mapper;
    private Properties props;
    
    public ResourceWriter(Properties props) throws ResourceException {
        try {
            baseDir = props.getProperty("adore-resource.StoreDirectory");
            String resourceIdxPlugin = props.getProperty("adore-resource.ResourceIdxPlugin");
            idx = (ResourceIndexInterface) Class.forName(resourceIdxPlugin).newInstance();
            idx.setBaseDir(baseDir);
            idx.setProperties(props);
        } catch (InstantiationException e) {
            throw new ResourceException(e);
        } catch (IllegalAccessException e) {
            throw new ResourceException(e);
        } catch (ClassNotFoundException e) {
            throw new ResourceException(e);
        }
    }
    
    public ResourceWriter(String baseDir, String resourceIdxPlugin) throws ResourceException {
		try {
			this.baseDir = baseDir;
			idx = (ResourceIndexInterface) Class.forName(resourceIdxPlugin).newInstance();
			idx.setBaseDir(baseDir);
		} catch (InstantiationException e) {
			throw new ResourceException(e);
		} catch (IllegalAccessException e) {
			throw new ResourceException(e);
		} catch (ClassNotFoundException e) {
			throw new ResourceException(e);
		}
	}
    
    public void open(String repoId) throws ResourceException {
    	idx.open(repoId);
    }
    
    public void close(String repoId) throws ResourceException {
    	idx.close(repoId);
    }
    
    /**
     * Write resource and index information
     * @param id
     *          unique identifier of resource (i.e. info uri)
     * @param mimeType
     *          mimetype of resource
     * @param inputStream
     *          inputstream containing resource
     * @param utcDate
     *          utc datestamp of ingestion time
     * @param recordLength
     *          length of resource in bytes
     * @param checksum
     *          base32-encoded sha1 digest of resource 
     * @param repoId
     *          repository identifier to which the resource belongs
     * @throws ResourceException
     */
    public void write(String id, 
                      String mimeType,
                      InputStream inputStream, 
                      Date utcDate, 
                      String recordLength,
                      String checksum,
                      String repoId,
                      String sourceUri) throws ResourceException {
        ResourceRecord r = new ResourceRecord();
        r.setIdentifier(id);
        r.setMimetype(mimeType);
        String date;
        if (utcDate != null)
        	date = DateUtil.date2UTC(utcDate);
        else
        	date = DateUtil.date2UTC(new Date());
        r.setDate(date);
        r.setLength(recordLength);
        r.setChecksum(checksum);
        r.setRepositoryId(repoId);
        r.setSourceUri(sourceUri);
        
        // Get the repository id
        String repo = repoId.substring(repoId.lastIndexOf("/") + 1);

        // Ensure the repository directory exists
        File f;
        if (!(f = new File(baseDir,repo)).exists())
            f.mkdirs();
        // Get the resource digest value
        //String resIdDigest = null;
        //try {
        //    resIdDigest = DigestUtil.getSHA1Digest(id.getBytes());
        //} catch (Exception e) {
        //    throw new ResourceException(e);
        //}
        // Create the resource directory
        //if (!(f = new File(f,resIdDigest)).exists())
        //    f.mkdirs();
        //f = new File(f,id.substring(id.lastIndexOf("/")).concat(mapper.getExtension(mimeType)));
        f = new File(f,checksum.concat(mapper.getExtension(mimeType)));
        if (!f.exists()) {
			boolean fos = write(inputStream, f);
			if (fos) {
				String fileUri = f.getAbsolutePath();
				String fileUriPrefix = "file://";
				// Handles Windows File URI Properly
				if (fileUri.charAt(1) == ':') {
					fileUriPrefix = "file:///";
					fileUri = fileUri.replace("\\", "/");
				}
				fileUri = fileUriPrefix + fileUri;
				r.setResourceUri(fileUri);
				idx.writeRecord(repoId, r);
			} else {
				throw new ResourceException(
						"An error occurred attempting to write to " + f.getAbsolutePath());
			}
		}
    }
    
    private static boolean write(InputStream src, File dest) {
        InputStream in = null;
        OutputStream out = null;
        byte[] buf = null;
        int bufLen = 5000 * 1024;
        try {
            in = new BufferedInputStream(src);
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
    
    static {
        Properties props = new Properties();
        props.setProperty("hqx","application/mac-binhex40");
        props.setProperty("doc","application/msword");
        props.setProperty("pdf","application/pdf");
        props.setProperty("ps","application/postscript");
        props.setProperty("rtf","application/rtf");
        props.setProperty("xls","application/vnd.ms-excel");
        props.setProperty("ppt","application/vnd.ms-powerpoint");
        props.setProperty("tgz","application/x-compressed");
        props.setProperty("gtar","application/x-gtar");
        props.setProperty("gz","application/x-gzip");
        props.setProperty("js","application/x-javascript");
        props.setProperty("latex","application/x-latex");
        props.setProperty("mdb","application/x-msaccess");
        props.setProperty("p12","application/x-pkcs12");
        props.setProperty("tex","application/x-tex");
        props.setProperty("zip","application/zip");
        props.setProperty("au","audio/basic");
        props.setProperty("mp3","audio/mpeg");
        props.setProperty("wav","audio/x-wav");
        props.setProperty("bmp","image/bmp");
        props.setProperty("gif","image/gif");
        props.setProperty("jpeg","image/jpeg");
        props.setProperty("jp2","image/jp2");
        props.setProperty("jpm","image/jpm");
        props.setProperty("jpx","image/jpx");
        props.setProperty("tif","image/tiff");
        props.setProperty("ras","image/x-cmu-raster");
        props.setProperty("cmx","image/x-cmx");
        props.setProperty("ico","image/x-icon");
        props.setProperty("pnm","image/x-portable-anymap");
        props.setProperty("pbm","image/x-portable-bitmap");
        props.setProperty("pgm","image/x-portable-graymap");
        props.setProperty("ppm","image/x-portable-pixmap");
        props.setProperty("rgb","image/x-rgb");
        props.setProperty("xbm","image/x-xbitmap");
        props.setProperty("xpm","image/x-xpixmap");
        props.setProperty("css","text/css");
        props.setProperty("html","text/html");
        props.setProperty("txt","text/plain");
        props.setProperty("mpg","video/mpeg");
        props.setProperty("mov","video/quicktime");
        props.setProperty("mp4","video/mp4");
        props.setProperty("asf","video/x-ms-asf");
        props.setProperty("vrml","x-world/x-vrml");
        props.setProperty("xml","application/xml");
        mapper = new MimeTypeMapper(props);
    }

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public Properties getProperties() {
		return props;
	}

	public void getProperties(Properties props) {
		this.props = props;
	}
}
