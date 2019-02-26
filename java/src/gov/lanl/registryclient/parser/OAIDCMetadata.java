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

package gov.lanl.registryclient.parser;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * A simple OAIDC implementation
 * 
 */
public class OAIDCMetadata implements Metadata {
    public static final String DC_NS = "http://purl.org/dc/elements/1.1/";

    public static final String DC_PREFIX = "dc";

    public static final String OAIDC_NS = "http://www.openarchives.org/OAI/2.0/oai_dc/";

    public static final String OAIDC_PREFIX = "oai_dc";

    Set<String> titles = new TreeSet<String>();

    Set<String> identifiers = new TreeSet<String>();

    public void read(InputStream input) throws SerializationException {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory
                    .newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(input);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(new OAIDCNamespaceContext());
            XPathExpression expr = xpath.compile("//oai_dc:dc/dc:title");
            NodeList nodes = (NodeList) expr.evaluate(doc,
                    XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                titles.add(nodes.item(i).getTextContent());
            }

            expr = xpath.compile("//oai_dc:dc/dc:identifier");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                identifiers.add(nodes.item(i).getTextContent());
            }

        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    public void write(OutputStream output) throws SerializationException {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(output,
                    "UTF-8"), true);
            out.print("<oai_dc:dc ");
            out.print(" " + "xmlns:" + DC_PREFIX + "=\"" + DC_NS + "\" ");
            out.println("xmlns:" + OAIDC_PREFIX + "=\"" + OAIDC_NS + "\"> ");

            for (String s : titles) {
                out.println("<dc:title>" + s + "</dc:title>");
            }

            for (String s : identifiers) {
                out.println("<dc:identifier>" + s + "</dc:identifier>");
            }
            out.println("</oai_dc:dc>");
            out.close();
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }

    }

    public Set<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<String> identifiers) {
        this.identifiers = identifiers;
    }

    public Set<String> getTitles() {
        return titles;
    }

    public void setTitles(Set<String> titles) {
        this.titles = titles;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof OAIDCMetadata))
            return false;
        OAIDCMetadata data = (OAIDCMetadata) obj;
        return (identifiers.equals(data.identifiers) && titles
                .equals(data.titles));

    }

}

class OAIDCNamespaceContext implements NamespaceContext {

    public String getNamespaceURI(String prefix) {
        if (prefix == null)
            throw new NullPointerException("Null prefix");
        else if (OAIDCMetadata.OAIDC_PREFIX.equals(prefix))
            return OAIDCMetadata.OAIDC_NS;
        else if (OAIDCMetadata.DC_PREFIX.equals(prefix))
            return OAIDCMetadata.DC_NS;

        return "";
    }

    // This method isn't necessary for XPath processing.
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }

    // This method isn't necessary for XPath processing either.
    public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }

}
