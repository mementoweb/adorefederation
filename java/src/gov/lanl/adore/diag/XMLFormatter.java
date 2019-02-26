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

package gov.lanl.adore.diag;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

public class XMLFormatter {
    public static String prettyPrint(String document) {
        try {
            SAXReader xmlReader = new SAXReader(false);
            InputSource is = new InputSource(new StringReader(document));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(xmlReader.read(is));
            writer.close(); 
            
            return new String(out.toByteArray(), "UTF-8");
        } catch (Exception e) {
            document = e.getMessage();
        }
        return document;
    }
    
    public static String prettyPrintAsTextArea(String title, String document) {
        title = "<h3>" + title + "</h3>";
        String area = "<textarea cols=\"120\" rows=\"10\" class=\"response\">" + XMLFormatter.prettyPrint(document) + "</textarea>";
        
        return title + "\n" + area;
    }
}
