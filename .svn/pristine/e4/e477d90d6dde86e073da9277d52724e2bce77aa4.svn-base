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

package gov.lanl.ockham.iesrdata;

import gov.lanl.ockham.iesrdata.IESRNamespaceContext;

import java.io.InputStream;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 * Utility class for XML handling in IESR
 * 
 */
public class XMLUtil {

    /**
     * Determines if the record is a collection or service instance
     * 
     * @param input
     *            InputStream containing XML record
     * @return "Collection" or "Service"
     * @throws IESRFormatException
     */
    public static String getType(InputStream input) throws IESRFormatException {
        try {
            Document doc = parseDocument(input);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(new IESRNamespaceContext());
            XPathExpression expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Collection");
            Object result = expr.evaluate(doc, XPathConstants.NODE);
            if (result != null) {
                return "Collection";
            }
            expr = xpath.compile("//iesr:iesrDescription/iesr:Service");

            result = expr.evaluate(doc, XPathConstants.NODE);
            if (result != null) {
                return "Service";
            } else
                throw new IESRFormatException("not a valid iesr format");
        } catch (Exception ex) {
            throw new IESRFormatException(ex);
        }
    }

    /**
     * Creates an IESR XML Header
     * 
     * @return an "iesr:iesrDescription" header xml fragment
     */
    static String getIESRHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<iesr:iesrDescription ");
        IESRNamespaceContext context = new IESRNamespaceContext();
        for (IESRNamespaceContext.PREFIX prefix : IESRNamespaceContext.PREFIX
                .values()) {
            sb.append("xmlns:").append(prefix.value()).append("=\"").append(
                    context.getNamespaceURI(prefix.value())).append("\" ");
        }
        sb.append(">");
        return sb.toString();
    }

    /**
     * Gets an IESR XML Footer
     * 
     * @return an "iesr:iesrDescription" footer
     */
    static String getIESRFooter() {
        return "</iesr:iesrDescription>";
    }

    /**
     * XML Prefix, Element, and Value Wrapping
     * 
     * @param prefix
     *            XMLPrefix
     * @param element
     *            element name
     * @param value
     *            element value
     * @return formatted xml including prefix, element, and value
     */
    public static String wrapElement(String prefix, String element, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(prefix).append(":").append(element).append(">");
        sb.append(xmlEncode(value));
        sb.append("</").append(prefix).append(":").append(element).append(">");
        return sb.toString();
    }

    /**
     * Wrap repeatable element. This is possible because IESR supports a flat
     * model (i.e. Dublin Core abstract model)
     * 
     * @param prefix
     *            XMLPrefix
     * @param element
     *            element name
     * @param values
     *            set of element values
     * @return formatted xml including prefix, element, and values
     */
    public static String wrapSet(String prefix, String element,
            Set<String> values) {
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(wrapElement(prefix, element, s)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Create a DOM document from an InputStream
     * 
     * @param input
     *            an INputStream containing XML
     * @return DOM document
     */
    public static Document parseDocument(InputStream input)
            throws IESRFormatException {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory
                    .newInstance();
            domFactory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(input);
            return doc;
        } catch (Exception ex) {
            throw new IESRFormatException(ex);
        }
    }

    /**
     * XML encode a string.
     * 
     * @param s
     *            any String
     * @return the String with &amp;, &lt;, and &gt; encoded for use in XML.
     */
    public static String xmlEncode(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }
}
