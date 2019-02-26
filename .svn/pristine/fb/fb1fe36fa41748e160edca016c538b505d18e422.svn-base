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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Basic MimeType to Extension Mapping Class
 *          
 * @author rchute
 *
 */
public class MimeTypeMapper extends Properties {

    private String propFile;
    
    /**
     * Initializes a MimeTypeMapper using the default mapping set.
     * 
     */
    public MimeTypeMapper() {
        this.loadDefaults();
    }
    
    /**
     * Initializes a MimeTypeMapper using a properties file
     * 
     * Format of properties file:
     *     $ext=$mimetype
     * Example:
     *     pdf=application/pdf
     * 
     * @param propFile - path to mimetype mappings file
     */
    public MimeTypeMapper(String propFile) {
        this.propFile = propFile;
        this.setup();
    }
    
    /**
     * Initializes a MimeTypeMapper using an existing properties object
     * 
     * Format of properties:
     *     $ext=$mimetype
     * Example:
     *     pdf=application/pdf
     * 
     */
    public MimeTypeMapper(Properties props) {
        for (Map.Entry e : props.entrySet()) {
            this.setProperty((String) e.getKey(), (String) e.getValue());
        }
    }
    
    private void setup() {
        try {
            File props = new File(propFile);
            FileInputStream fs = new FileInputStream(props);
            this.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getMimeType(String ext) {
        return this.getProperty(ext);
    }
    
    /**
     * Initializes the MimeTypeMapper using default mimetype registry
     * Defaults include standard text, image, audio, video, and application formats
     */
    public void loadDefaults() {
        this.setProperty("evy","application/envoy");
        this.setProperty("fif","application/fractals");
        this.setProperty("spl","application/futuresplash");
        this.setProperty("hta","application/hta");
        this.setProperty("acx","application/internet-property-stream");
        this.setProperty("hqx","application/mac-binhex40");
        this.setProperty("doc","application/msword");
        this.setProperty("dot","application/msword");
        this.setProperty("bin","application/octet-stream");
        this.setProperty("class","application/octet-stream");
        this.setProperty("dms","application/octet-stream");
        this.setProperty("exe","application/octet-stream");
        this.setProperty("lha","application/octet-stream");
        this.setProperty("lzh","application/octet-stream");
        this.setProperty("oda","application/oda");
        this.setProperty("axs","application/olescript");
        this.setProperty("pdf","application/pdf");
        this.setProperty("prf","application/pics-rules");
        this.setProperty("p10","application/pkcs10");
        this.setProperty("crl","application/pkix-crl");
        this.setProperty("ai","application/postscript");
        this.setProperty("eps","application/postscript");
        this.setProperty("ps","application/postscript");
        this.setProperty("rtf","application/rtf");
        this.setProperty("setpay","application/set-payment-initiation");
        this.setProperty("setreg","application/set-registration-initiation");
        this.setProperty("xla","application/vnd.ms-excel");
        this.setProperty("xlc","application/vnd.ms-excel");
        this.setProperty("xlm","application/vnd.ms-excel");
        this.setProperty("xls","application/vnd.ms-excel");
        this.setProperty("xlt","application/vnd.ms-excel");
        this.setProperty("xlw","application/vnd.ms-excel");
        this.setProperty("sst","application/vnd.ms-pkicertstore");
        this.setProperty("cat","application/vnd.ms-pkiseccat");
        this.setProperty("stl","application/vnd.ms-pkistl");
        this.setProperty("pot","application/vnd.ms-powerpoint");
        this.setProperty("pps","application/vnd.ms-powerpoint");
        this.setProperty("ppt","application/vnd.ms-powerpoint");
        this.setProperty("mpp","application/vnd.ms-project");
        this.setProperty("wcm","application/vnd.ms-works");
        this.setProperty("wdb","application/vnd.ms-works");
        this.setProperty("wks","application/vnd.ms-works");
        this.setProperty("wps","application/vnd.ms-works");
        this.setProperty("hlp","application/winhlp");
        this.setProperty("bcpio","application/x-bcpio");
        this.setProperty("cdf","application/x-cdf");
        this.setProperty("z","application/x-compress");
        this.setProperty("tgz","application/x-compressed");
        this.setProperty("cpio","application/x-cpio");
        this.setProperty("csh","application/x-csh");
        this.setProperty("dcr","application/x-director");
        this.setProperty("dir","application/x-director");
        this.setProperty("dxr","application/x-director");
        this.setProperty("dvi","application/x-dvi");
        this.setProperty("gtar","application/x-gtar");
        this.setProperty("gz","application/x-gzip");
        this.setProperty("hdf","application/x-hdf");
        this.setProperty("ins","application/x-internet-signup");
        this.setProperty("isp","application/x-internet-signup");
        this.setProperty("iii","application/x-iphone");
        this.setProperty("js","application/x-javascript");
        this.setProperty("latex","application/x-latex");
        this.setProperty("mdb","application/x-msaccess");
        this.setProperty("crd","application/x-mscardfile");
        this.setProperty("clp","application/x-msclip");
        this.setProperty("dll","application/x-msdownload");
        this.setProperty("m13","application/x-msmediaview");
        this.setProperty("m14","application/x-msmediaview");
        this.setProperty("mvb","application/x-msmediaview");
        this.setProperty("wmf","application/x-msmetafile");
        this.setProperty("mny","application/x-msmoney");
        this.setProperty("pub","application/x-mspublisher");
        this.setProperty("scd","application/x-msschedule");
        this.setProperty("trm","application/x-msterminal");
        this.setProperty("wri","application/x-mswrite");
        this.setProperty("cdf","application/x-netcdf");
        this.setProperty("nc","application/x-netcdf");
        this.setProperty("pma","application/x-perfmon");
        this.setProperty("pmc","application/x-perfmon");
        this.setProperty("pml","application/x-perfmon");
        this.setProperty("pmr","application/x-perfmon");
        this.setProperty("pmw","application/x-perfmon");
        this.setProperty("p12","application/x-pkcs12");
        this.setProperty("pfx","application/x-pkcs12");
        this.setProperty("p7b","application/x-pkcs7-certificates");
        this.setProperty("spc","application/x-pkcs7-certificates");
        this.setProperty("p7r","application/x-pkcs7-certreqresp");
        this.setProperty("p7c","application/x-pkcs7-mime");
        this.setProperty("p7m","application/x-pkcs7-mime");
        this.setProperty("p7s","application/x-pkcs7-signature");
        this.setProperty("sh","application/x-sh");
        this.setProperty("shar","application/x-shar");
        this.setProperty("sit","application/x-stuffit");
        this.setProperty("sv4cpio","application/x-sv4cpio");
        this.setProperty("sv4crc","application/x-sv4crc");
        this.setProperty("tar","application/x-tar");
        this.setProperty("tcl","application/x-tcl");
        this.setProperty("tex","application/x-tex");
        this.setProperty("texi","application/x-texinfo");
        this.setProperty("texinfo","application/x-texinfo");
        this.setProperty("roff","application/x-troff");
        this.setProperty("t","application/x-troff");
        this.setProperty("tr","application/x-troff");
        this.setProperty("man","application/x-troff-man");
        this.setProperty("me","application/x-troff-me");
        this.setProperty("ms","application/x-troff-ms");
        this.setProperty("ustar","application/x-ustar");
        this.setProperty("src","application/x-wais-source");
        this.setProperty("cer","application/x-x509-ca-cert");
        this.setProperty("crt","application/x-x509-ca-cert");
        this.setProperty("der","application/x-x509-ca-cert");
        this.setProperty("pko","application/ynd.ms-pkipko");
        this.setProperty("zip","application/zip");
        this.setProperty("au","audio/basic");
        this.setProperty("snd","audio/basic");
        this.setProperty("mid","audio/mid");
        this.setProperty("rmi","audio/mid");
        this.setProperty("mp3","audio/mpeg");
        this.setProperty("aif","audio/x-aiff");
        this.setProperty("aifc","audio/x-aiff");
        this.setProperty("aiff","audio/x-aiff");
        this.setProperty("m3u","audio/x-mpegurl");
        this.setProperty("ra","audio/x-pn-realaudio");
        this.setProperty("ram","audio/x-pn-realaudio");
        this.setProperty("wav","audio/x-wav");
        this.setProperty("bmp","image/bmp");
        this.setProperty("cod","image/cis-cod");
        this.setProperty("gif","image/gif");
        this.setProperty("ief","image/ief");
        this.setProperty("jpe","image/jpeg");
        this.setProperty("jpeg","image/jpeg");
        this.setProperty("jpg","image/jpeg");
        this.setProperty("jp2","image/jp2");
        this.setProperty("jpm","image/jpm");
        this.setProperty("jpx","image/jpx");
        this.setProperty("jfif","image/pipeg");
        this.setProperty("tif","image/tiff");
        this.setProperty("tiff","image/tiff");
        this.setProperty("ras","image/x-cmu-raster");
        this.setProperty("cmx","image/x-cmx");
        this.setProperty("ico","image/x-icon");
        this.setProperty("pnm","image/x-portable-anymap");
        this.setProperty("pbm","image/x-portable-bitmap");
        this.setProperty("pgm","image/x-portable-graymap");
        this.setProperty("ppm","image/x-portable-pixmap");
        this.setProperty("rgb","image/x-rgb");
        this.setProperty("xbm","image/x-xbitmap");
        this.setProperty("xpm","image/x-xpixmap");
        this.setProperty("xwd","image/x-xwindowdump");
        this.setProperty("mht","message/rfc822");
        this.setProperty("mhtml","message/rfc822");
        this.setProperty("nws","message/rfc822");
        this.setProperty("css","text/css");
        this.setProperty("323","text/h323");
        this.setProperty("htm","text/html");
        this.setProperty("html","text/html");
        this.setProperty("stm","text/html");
        this.setProperty("uls","text/iuls");
        this.setProperty("bas","text/plain");
        this.setProperty("c","text/plain");
        this.setProperty("h","text/plain");
        this.setProperty("txt","text/plain");
        this.setProperty("rtx","text/richtext");
        this.setProperty("sct","text/scriptlet");
        this.setProperty("tsv","text/tab-separated-values");
        this.setProperty("htt","text/webviewhtml");
        this.setProperty("htc","text/x-component");
        this.setProperty("etx","text/x-setext");
        this.setProperty("vcf","text/x-vcard");
        this.setProperty("mp2","video/mpeg");
        this.setProperty("mpa","video/mpeg");
        this.setProperty("mpe","video/mpeg");
        this.setProperty("mpeg","video/mpeg");
        this.setProperty("mpg","video/mpeg");
        this.setProperty("mpv2","video/mpeg");
        this.setProperty("mov","video/quicktime");
        this.setProperty("qt","video/quicktime");
        this.setProperty("mj2","video/mj2");
        this.setProperty("mp4","video/mp4");
        this.setProperty("lsf","video/x-la-asf");
        this.setProperty("lsx","video/x-la-asf");
        this.setProperty("asf","video/x-ms-asf");
        this.setProperty("asr","video/x-ms-asf");
        this.setProperty("asx","video/x-ms-asf");
        this.setProperty("avi","video/x-msvideo");
        this.setProperty("movie","video/x-sgi-movie");
        this.setProperty("flr","x-world/x-vrml");
        this.setProperty("vrml","x-world/x-vrml");
        this.setProperty("wrl","x-world/x-vrml");
        this.setProperty("wrz","x-world/x-vrml");
        this.setProperty("xaf","x-world/x-vrml");
        this.setProperty("xof","x-world/x-vrml");
        this.setProperty("xml","application/xml");
    }

    public String getExtension(String mimeType) {
        Enumeration e = this.propertyNames();
        while (e.hasMoreElements()) {
            String prop = (String) e.nextElement();
            if (mimeType.equals(this.get(prop))) 
                    return "." + prop;
        }
        return "";
    }
}
