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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceUtil {
    
    public static Resource getResource(String url) throws Exception {
        Resource resource = new Resource();
        URL location;
        try {
            location = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) (location.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                resource.setBytes(StreamUtil.getByteArray(is));
                resource.setContentType(huc.getContentType());
            } else
                throw new Exception("Cannot get " + location.toString());
        } catch (MalformedURLException e) {
            throw new Exception("A MalformedURLException occurred for " + url);
        } catch (IOException e) {
            throw new Exception("An IOException occurred attempting to connect to " + url);
        }
        return resource;
    }
}
