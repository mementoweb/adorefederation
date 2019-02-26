/*
 * XPathProcessor.java
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

package org.foo.didl.serialize;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.xml.sax.InputSource;

/**
 * <code>XPathProcessor</code> provides a basic XPath 
 * utility for use during content type de-serialization.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class XPathProcessor {

    private Document doc = null;

    private static javax.xml.parsers.DocumentBuilderFactory dbf = null;

    private ArrayList<String[]> nsp = new ArrayList<String[]>();

    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces",
                        Boolean.TRUE);
    }

    /** 
     * Sets the XML Document
     */
    public void setDocument(String record) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        InputSource IS = new InputSource(new StringReader(record));
        doc = builder.build(IS);
    }

    /**
     * Register a new namespace & associated prefix
     */
    public void addNamespace(String prefix, String namespace) {
        nsp.add(new String[] { prefix, namespace });
    }

    /**
     * Execute specfied xpath query
     */
    public ArrayList<String> xpath(String xpathExp) {
        XPath x;
        ArrayList<String> values = new ArrayList<String>();
        try {
            x = XPath.newInstance(xpathExp);
            for (Iterator i = nsp.iterator(); i.hasNext();) {
                String[] nsinfo = (String[]) i.next();
                Namespace ns = Namespace.getNamespace(nsinfo[1], nsinfo[0]);
                x.addNamespace(ns);
            }
            List nodeList = x.selectNodes(doc);
            for (Iterator i = nodeList.iterator(); i.hasNext();) {
                if ((nodeList != null) && (nodeList.size() > 0)) {
                    Object obj = i.next();
                    if (obj != null && org.jdom.Element.class.isInstance(obj)) {
                        org.jdom.Element node = (org.jdom.Element) obj;
                        values.add(node.getText());
                    } else if (obj != null
                            && org.jdom.Attribute.class.isInstance(obj)) {
                        org.jdom.Attribute node = (org.jdom.Attribute) obj;
                        values.add(node.getValue());
                    }
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return values;
    }
}
