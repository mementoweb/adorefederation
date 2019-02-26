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

package gov.lanl.util.resource;

import gov.lanl.util.StreamUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Resource {
    private String contentType;
    private byte[] bytes;
    private InputStream is;

    public byte[] getBytes() {
    	if (bytes != null)
            return bytes;
    	else if (is != null) {
    		try {
				bytes = StreamUtil.getByteArray(is);
				is = null;
				return bytes;
			} catch (Exception e) {}
    	} 
		return null;
    }
    
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public InputStream getInputStream() {
    	if (is != null)
		    return is;
    	else if (bytes != null)
    		return new ByteArrayInputStream(bytes);
    	else
    		return null;
	}

	public void setInputStream(InputStream is) {
		this.is = is;
	}
}
