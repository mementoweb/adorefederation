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

package gov.lanl.util.xpath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jaxen.JaxenException;
import org.jaxen.dom4j.Dom4jXPath;
import org.xml.sax.InputSource;

public class DOM4JImpl {

    private Document doc = null;
    private ArrayList<NamespaceProfile> nsProfiles = null;

    public org.w3c.dom.Document generateW3CDOM() throws DocumentException {
        DOMWriter dw = new DOMWriter();
        return dw.write(this.doc);
    }
    
    public Document getDocument() {
        return doc;
    }

    public void setDocument(org.w3c.dom.Document w3cdoc) {
        DOMReader dr = new DOMReader();
        this.doc = dr.read(w3cdoc);
    }

    public void setDocument(String record) throws DocumentException {
        SAXReader xmlReader = new SAXReader();
        InputSource IS = new InputSource(new StringReader(record));
        this.doc = xmlReader.read(IS);
    }
    
    public void setDocument(File aFile) throws DocumentException {
        SAXReader xmlReader = new SAXReader();
        this.doc = xmlReader.read(aFile);
    }

    public void setNamespaces(ArrayList<NamespaceProfile> nsp) {
        this.nsProfiles = nsp;
    }
    
    public String xpathHead(String xpathExp) {
        ArrayList a = xpath(xpathExp);
        if (a != null && !a.isEmpty())
            return (String) a.get(0);
        else
            return null;
    }
    
    public ArrayList xpath(String xpathExp) {
        Dom4jXPath expression;
        ArrayList<String> values = new ArrayList<String>();
        try {
            expression = new Dom4jXPath(xpathExp);
            
            for(Iterator i = nsProfiles.iterator(); i.hasNext();) {
                NamespaceProfile np = (NamespaceProfile) i.next();
                expression.addNamespace(np.getNamespacePrefix(), np.getNamespace());
            }

            List nodeList = expression.selectNodes(doc);
            //values = new ArrayList<String>();
            for (Iterator i = nodeList.iterator(); i.hasNext();) {
                if ((nodeList != null) && (nodeList.size() > 0)) {
                    Node node = (Node) i.next();
                    if ((node != null)) {
                        String value = node.getText();
                        if (value != null && !value.equals(""))
                            values.add(value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;
    }

    public ArrayList xpathNodes(String xpathExp) {
        
        ArrayList<Node> values = new ArrayList<Node>();
        try {
        XPath xpath = doc.createXPath(xpathExp);
        List nodeList = xpath.selectNodes(doc);
        for (Iterator i = nodeList.iterator(); i.hasNext();) {
            if ((nodeList != null) && (nodeList.size() > 0)) {
                Node node = (Node) i.next();
                if ((node != null)) {
                    values.add(node);
                }
            }
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public boolean write(String name) throws IOException {
        boolean status = false;
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileWriter(name), format);
            writer.write(doc);
            writer.close();
            writer.flush();
            status = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public String transform(Document xmlDoc, String xslFile) {
        String tdoc = null;
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(
                    xslFile));
            Source source = new DocumentSource(xmlDoc);
            DocumentResult result = new DocumentResult();
            transformer.transform(source, result);
            tdoc = result.getDocument().asXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tdoc;
    }

}
