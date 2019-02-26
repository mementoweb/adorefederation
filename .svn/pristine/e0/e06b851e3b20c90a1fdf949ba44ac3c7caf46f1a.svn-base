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

package gov.lanl.federator.schematable;

/**
 * Maintainer of metadataprefix/service mapping table
 */

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class MapTable extends DefaultHandler {
    Hashtable<String, MapItem> metadataPrefixlist;

    StringBuffer text;

    MapItem item;

    static MapTable maptable = new MapTable();

    static Logger log = Logger.getLogger(MapTable.class.getName());

    private MapTable() {
        metadataPrefixlist = new Hashtable<String, MapItem>();
    }

    public synchronized static Hashtable<String, MapItem> getMapTable() {
        return maptable.metadataPrefixlist;
    }

    public static Vector<MapItem> getPrefixList() throws Exception {
        Vector<MapItem> v = new Vector<MapItem>();
        for (MapItem i : maptable.metadataPrefixlist.values()) {
            v.add(i);
        }
        return v;
    }

    public static Vector<String> getSchemaLocations() throws Exception{
    	 Vector<String> v = new Vector<String>();
         for (MapItem i : maptable.metadataPrefixlist.values()) {
             v.add(i.metadataNamespace+" "+i.getSchema());
         }
         return v;
    }
    
    public static void parse(InputSource source) throws Exception {
        try {
            XMLReader xr = XMLReaderFactory
                    .createXMLReader("org.apache.xerces.parsers.SAXParser");
            xr.setContentHandler(maptable);
            xr.setErrorHandler(maptable);
            xr.setFeature("http://xml.org/sax/features/validation", false);
            xr.setFeature("http://xml.org/sax/features/namespace-prefixes",
                    true);
            xr.parse(source);
        } catch (Exception ex) {
            log.error("federator table error");
            log.error(ex.toString());
            throw (ex);
        }

    }

    public void startElement(String uri, String name, String qName,
            Attributes attrs) throws SAXException {

        if (name.equals("item")) {
            item = new MapItem();
        }
        text = new StringBuffer("");
    }

    public void endElement(String uri, String name, String qName)
            throws SAXException {

        if (name.equals("metadataPrefix")) {
            item.metadataPrefix = text.toString().trim();
        }

        if (name.equals("schema")) {
            item.schema = text.toString().trim();
        }

        if (name.equals("metadataNamespace")) {
            item.metadataNamespace = text.toString().trim();
        }
        if (name.equals("service")) {
            item.service = text.toString().trim();
        }
        if (name.equals("item")) {
            metadataPrefixlist.put(item.metadataPrefix, item);
        }
    }

    public void characters(char ch[], int start, int length) {

        String s = new String(ch, start, length);
        text.append(s);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Iterator it = metadataPrefixlist.values().iterator(); it.hasNext();) {
            MapItem prx = (MapItem) (it.next());
            sb.append(prx.toString());
            sb.append("\n");

        }
        return sb.toString();
    }

}
