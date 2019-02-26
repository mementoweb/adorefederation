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

package gov.lanl.util.xpath.marcxml;

import gov.lanl.util.xpath.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;


public class MarcXmlParser {
	static DOM4JImpl doc;
	
    public MarcXmlParser(byte[] raw) throws Exception {
        // Check the length
        int contentLength = raw.length;
        if (contentLength == 0) {
           throw new Exception("Unable to process content with a length of 0");
        }
        
        String xml = new String(raw, "UTF-8");
        doc = new DOM4JImpl();
        DocumentProfile docProfile = DocumentProfileFactory.generateDocProfile(new MarcXmlProperties());
        try {
            doc.setDocument(xml);
            doc.setNamespaces(docProfile.getNamespaceProfiles());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList getNodeList(String xpathExp){
		return doc.xpathNodes(xpathExp);
	}
    
    public HashMap processContent() throws Exception {
    	return processContent(new MarcXmlProperties());
    }
    
    public HashMap processContent(Properties props) throws Exception {
        HashMap map = new HashMap<String, ArrayList>();
        try {
            // Populate Metadata Object
            if (props == null)
                props = new MarcXmlProperties();
            DocumentProfile docProfile = DocumentProfileFactory.generateDocProfile(props);
            for (Iterator i = docProfile.getDocFields(); i.hasNext();) {
                DocumentField df = (DocumentField) i.next();
                String field = df.getFieldName();
                String xpath = df.getXpath();
                ArrayList values = doc.xpath(xpath);
                map.put(field,values);
            }
        } catch (Exception e) { // run time exception
            throw new Exception("General exception in MarcXmlParser: " + e.getMessage());
        }
        return map;
    }
    
    public static HashMap getMarcPropMap(byte[] raw, Properties props) throws Exception {
    	MarcXmlParser parser = new MarcXmlParser(raw);
    	HashMap map = parser.processContent(props);
    	parser = null;
    	return map;
    }
}
