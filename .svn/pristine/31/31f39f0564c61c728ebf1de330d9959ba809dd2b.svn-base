/*
 * MyDidlUtils.java
 * 
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package org.foo.didl.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyDidlUtils {
    public static String fetch(String reference) throws IOException {
        URL url = new URL(reference);
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("GET");
        c.setDoOutput(true);
        c.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        return parseISToString(c.getInputStream());
    }

    public static String parseISToString(java.io.InputStream is) {
        java.io.DataInputStream din = new java.io.DataInputStream(is);
        StringBuffer sb = new StringBuffer();
        try {
            String line = null;
            while ((line = din.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
        return stripXMLHeader(sb.toString());
    }

    public static String stripXMLHeader(String xml) {
        int x, y;
        if (xml.contains("<?")) {
            x = xml.indexOf("?");
            if (x == 1) {
                y = xml.lastIndexOf("?") + 2;
                xml = xml.substring(y, xml.length());
            }
        }
        return xml;
    }
}
