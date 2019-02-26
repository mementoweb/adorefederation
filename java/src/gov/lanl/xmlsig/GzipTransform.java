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

package gov.lanl.xmlsig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;

public class GzipTransform extends TransformSpi {

    /** {@link org.apache.commons.logging}logging facility */
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(GzipTransform.class.getName());

    /** Field implementedTransformURI */
    public static final String implementedTransformURI = "http://www.iana.org/assignments/http-parameters#gzip";

    /**
     * Method engineGetURI
     * 
     *  
     */
    protected String engineGetURI() {
        return GzipTransform.implementedTransformURI;
    }

    public boolean wantsOctetStream() {
        return true;
    }

    public boolean wantsNodeSet() {
        return false;
    }

    public boolean returnsOctetStream() {
        return true;
    }

    public boolean returnsNodeSet() {
        return false;
    }

    /**
     * Method enginePerformTransform
     * 
     * @param input
     *  
     */
    protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input)
            throws IOException, TransformationException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (input.isByteArray()) {
                byte[] gzipinput = input.getBytes();
                GZIPInputStream in = new GZIPInputStream(
                        new ByteArrayInputStream(gzipinput));

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    baos.write(buf, 0, len);
                }
            }

            XMLSignatureInput output = new XMLSignatureInput(baos.toByteArray());

            return output;

        } catch (Exception ex) {
            Object exArgs[] = { ex.getMessage() };
            throw new TransformationException("generic.EmptyMessage", exArgs,
                    ex);

        }

    }

    static {
        org.apache.xml.security.Init.init();
    }
}
