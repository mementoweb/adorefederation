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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import gov.lanl.registryclient.parser.SerializationException;
import gov.lanl.registryclient.parser.Metadata;

/**
 * IESR Service Record; only fields interesting to aDORe
 * environment are supported
 * 
 */
public class IESRService implements Serializable, Metadata {
    
    private static final long serialVersionUID = 4327569292446396480L;

    private String identifier;

    private String title;

    private String locator;

    private String type;

    private String supportsStandard;

    private String serves;

    /**
     * Creates a new IESRService instance
     */
    public IESRService() {}

    /**
     * Gets the unique service identifier
     * (e.g. info:lanl-repo/int/11bb3b71-fe22-4905-9d29-83c9bfbeee2c/openurl-aDORe3)
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the unique service identifier
     * (e.g. info:lanl-repo/int/11bb3b71-fe22-4905-9d29-83c9bfbeee2c/openurl-aDORe3)
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the service location url
     * (e.g. http://localhost:8080/adore-arcfile-resolver/11bb3b71-fe22-4905-9d29-83c9bfbeee2c/openurl-aDORe3)
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Sets the service location url
     * (e.g. http://localhost:8080/adore-arcfile-resolver/11bb3b71-fe22-4905-9d29-83c9bfbeee2c/openurl-aDORe3)
     */
    public void setLocator(String locator) {
        this.locator = locator;
    }

    /**
     * Gets the collection id to which the service belongs
     * (e.g. info:lanl-repo/arc/11bb3b71-fe22-4905-9d29-83c9bfbeee2c)
     */
    public String getServes() {
        return serves;
    }

    /**
     * Sets the collection id to which the service belongs
     * (e.g. info:lanl-repo/arc/11bb3b71-fe22-4905-9d29-83c9bfbeee2c)
     */
    public void setServes(String serves) {
        this.serves = serves;
    }

    /**
     * Gets the version/profile which the service supports
     * (e.g. openurl-aDORe3)
     */
    public String getSupportsStandard() {
        return supportsStandard;
    }

    /**
     * Sets the version/profile which the service supports
     * (e.g. openurl-aDORe3)
     */
    public void setSupportsStandard(String supportsStandard) {
        this.supportsStandard = supportsStandard;
    }

    /**
     * Gets the service title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the service title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the service interface type
     * (e.g. openurl)
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the service interface type
     * (e.g. openurl)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Check if two IESRService instances are equal
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IESRService))
            return false;
        IESRService svr = (IESRService) obj;
        return (identifier.equals(svr.identifier) && serves.equals(svr.serves)
                && type.equals(svr.type) && locator.equals(svr.locator) && supportsStandard
                .equals(svr.supportsStandard));
    }

    /**
     * Ensure that the required fields are set
     */
    public boolean validate() {
        return ((identifier != null) && (serves != null) && (type != null)
                && (locator != null) && (supportsStandard != null));
    }

    /**
     * Serialize the IESRService object to an OutputStream
     * 
     * @param stream
     *            OutputStream to serialize the object to
     * @throws IESRSerializationException
     */
    public void write(OutputStream stream) throws SerializationException {
        try {

            if (!validate())
                throw new SerializationException("not a valid service object");

            PrintWriter out = new PrintWriter(stream, true);
            out.println(XMLUtil.getIESRHeader());

            out.println("<iesr:Service>");

            if (getIdentifier() != null) {
                out.println(XMLUtil.wrapElement("dc", "identifier",
                        getIdentifier()));
            }

            if (getTitle() != null) {
                out.println(XMLUtil.wrapElement("dc", "title", getTitle()));
            }

            if (getServes() != null) {
                out.println(XMLUtil.wrapElement("iesr", "serves", getServes()));
            }

            if (getType() != null) {
                out.println(XMLUtil.wrapElement("dc", "type", getType()));
            }

            if (getLocator() != null) {
                out.println(XMLUtil.wrapElement("rslpcd", "locator",
                        getLocator()));
            }

            if (getSupportsStandard() != null) {
                out.println(XMLUtil.wrapElement("iesr", "supportsStandard",
                        getSupportsStandard()));
            }

            out.println("</iesr:Service>");
            out.println(XMLUtil.getIESRFooter());
            out.close();
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    /**
     * Deserialize an IESRService object from an InputStream
     * 
     * @param input
     *            an InputStream containing the XML record
     * @throws SerializationException
     */
    public void read(InputStream input) throws SerializationException {
        try {
            Document doc = XMLUtil.parseDocument(input);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(new IESRNamespaceContext());
            XPathExpression expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Service/dc:title/text()");
            Object result = expr.evaluate(doc, XPathConstants.STRING);
            setTitle((String) result);

            expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Service/dc:identifier/text()");
            Object identifier = expr.evaluate(doc, XPathConstants.STRING);
            if (identifier == null)
                throw new SerializationException("empty identifier");
            setIdentifier((String) identifier);

            expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Service/dc:type/text()");
            Object type = expr.evaluate(doc, XPathConstants.STRING);
            if (type == null)
                throw new SerializationException("empty type");
            setType((String) type);

            expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Service/rslpcd:locator/text()");
            Object locator = expr.evaluate(doc, XPathConstants.STRING);
            if (locator == null)
                throw new SerializationException("empty locator");
            setLocator((String) locator);

            expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Service/iesr:serves/text()");
            Object serves = expr.evaluate(doc, XPathConstants.STRING);
            if (serves == null)
                throw new SerializationException("empty serves");

            setServes((String) serves);

            expr = xpath
                    .compile("//iesr:iesrDescription/iesr:Service/iesr:supportsStandard/text()");
            Object supportStandard = expr.evaluate(doc, XPathConstants.STRING);
            if (supportStandard == null)
                throw new SerializationException("empty supportStandard");

            setSupportsStandard((String) supportStandard);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }
}
