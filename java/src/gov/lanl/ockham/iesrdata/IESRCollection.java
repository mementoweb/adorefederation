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
import java.util.Set;
import java.util.TreeSet;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import gov.lanl.registryclient.parser.Metadata;
import gov.lanl.registryclient.parser.SerializationException;

/**
 * IESR Collection Record; only fields interesting to aDORe
 * environment are supported
 * 
 * see http://iesr.ac.uk/profile/
 * 
 */
public class IESRCollection implements Serializable, Metadata {

    private static final long serialVersionUID = 3411374229504292964L;

    private String title;

    private String identifier;

    private Set<String> types;

    private Set<String> services;

    private String contentRange;

    private Set<String> isPartOf;

    // Extended IESR Support
    private String temporalRange;
    private Set<String> itemFormats;
    private Set<String> subjects;
    private Set<String> vocabularies;
    private Set<String> itemTypes;
    private Set<String> associations;
    private int extent;

    /**
     * Creates a new IESRCollection instance
     */
    public IESRCollection() {
        types = new TreeSet<String>();
        services = new TreeSet<String>();
        isPartOf = new TreeSet<String>();
        itemFormats = new TreeSet<String>();
        subjects = new TreeSet<String>();
        vocabularies = new TreeSet<String>();
        itemTypes = new TreeSet<String>();
        associations = new TreeSet<String>();
    }

    /**
     * Gets the range of dates of creation of the individual items in the collection
     */
    public String getContentRange() {
        return contentRange;
    }

    /**
     * Sets the range of dates of creation of the individual items in the collection
     */
    public void setContentRange(String contentRange) {
        this.contentRange = contentRange;
    }

    /**
     * Gets the collection identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the collection identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the name of the collection
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the name of the collection
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets set of services that makes the collection available
     */
    public Set<String> getServices() {
        return services;
    }

    /**
     * Sets set of services that makes the collection available
     */
    public void setServices(Set<String> services) {
        this.services = services;
    }

    /**
     * Adds a service that makes the collection available
     */
    public void addService(String String) {
        this.services.add(String);
    }

    /**
     * Sets list of abstract collection associations
     */
    public void setIsPartOf(Set<String> isPartOf) {
        this.isPartOf = isPartOf;
    }

    /**
     * Gets list of abstract collection associations
     */
    public Set<String> getIsPartOf() {
        return this.isPartOf;
    }

    /**
     * Adds an abstract collection association
     * (e.g. info:sid/library.lanl.gov:inspec)
     */
    public void addIsPartOf(String String) {
        this.isPartOf.add(String);
    }

    /**
     * Sets the list of collection types
     */
    public void setTypes(Set<String> types) {
        this.types = types;
    }

    /**
     * Gets the list of collection types
     */
    public Set<String> getTypes() {
        return this.types;
    }

    /**
     * Adds a collection type
     */
    public void addType(String type) {
        this.types.add(type);
    }

    /**
     * Gets a list of item mimetypes in the collection
     */
    public Set<String> getItemFormats() {
        return itemFormats;
    }

    /**
     * Sets a list of item mimetypes in the collection
     */
    public void setItemFormats(Set<String> itemFormats) {
        this.itemFormats = itemFormats;
    }
    
    /**
     * Add an items mimetype
     */
    public void addItemFormat(String itemFormat) {
        this.itemFormats.add(itemFormat);
    }

    /**
     * Gets a list of keyword or subject descriptors
     */
    public Set<String> getSubjects() {
        return subjects;
    }

    /**
     * Sets a list of keyword or subject descriptors
     */
    public void setSubjects(Set<String> subjects) {
        this.subjects = subjects;
    }
    
    /**
     * Adds a keyword or subject descriptor
     */
    public void addSubject(String subject) {
        this.subjects.add(subject);
    }

    /** 
     * Gets the temporal coverage of the intellectual 
     * content of the items in the collection
     */
    public String getTemporalRange() {
        return temporalRange;
    }

    /** 
     * Sets the temporal coverage of the intellectual 
     * content of the items in the collection
     */
    public void setTemporalRange(String temporalRange) {
        this.temporalRange = temporalRange;
    }

    /**
     * Gets the list of associated controlled-vocabularies
     */
    public Set<String> getVocabularies() {
        return vocabularies;
    }

    /**
     * Sets the list of associated controlled-vocabularies
     */
    public void setVocabularies(Set<String> vocabularies) {
        this.vocabularies = vocabularies;
    }
    
    /**
     * Adds an associated controlled-vocabulary
     */
    public void addVocabulary(String vocabulary) {
        this.vocabularies.add(vocabulary);
    }
    
    /**
     * Gets the list of item types
     */
    public Set<String> getItemTypes() {
        return itemTypes;
    }

    /**
     * Sets the list of item types
     */
    public void setItemTypes(Set<String> itemTypes) {
        this.itemTypes = itemTypes;
    }
    
    /**
     * Adds an item type (e.g. info:lanl-repo/sem/1)
     */
    public void addItemType(String itemType) {
        this.itemTypes.add(itemType);
    }
    
    /**
     * Gets the list of collection-to-collection associations
     */
    public Set<String> getAssociations() {
        return associations;
    }

    /**
     * Sets the list of collection-to-collection associations
     */
    public void setAssociations(Set<String> associations) {
        this.associations = associations;
    }
    
    /**
     * Adds a collection-to-collection association
     */
    public void addAssociation(String association) {
        this.associations.add(association);
    }
    
    /**
     * Gets the number for records contained in collection
     */
    public int getExtent() {
        return extent;
    }

    /**
     * Sets the number for records contained in collection
     */
    public void setExtent(int extent) {
        this.extent = extent;
    }
    
    /**
     * Check if two IESRCollection instances are equal
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IESRCollection))
            return false;
        IESRCollection coll = (IESRCollection) obj;
        return (identifier.equals(coll.identifier)
             && services.equals(coll.services)
             && isPartOf.equals(coll.isPartOf) 
             && types.equals(coll.types)
             && itemFormats.equals(coll.itemFormats) 
             && subjects.equals(coll.subjects) 
             && vocabularies.equals(coll.vocabularies)
             && itemTypes.equals(coll.itemTypes) 
             && contentRange.equals(coll.contentRange));
    }

    /**
     * Ensure that the required fields are set
     */
    public boolean validate() {
        return ((identifier != null) 
                && (services != null) 
                && (types != null) 
                && (isPartOf != null));
    }

    /**
     * Serialize the IESRCollection object to an OutputStream
     * 
     * @param output
     *            OutputStream to serialize the object to
     * @throws IESRSerializationException
     */
    public void write(OutputStream output) throws SerializationException {
        try {
            if (!this.validate())
                throw new SerializationException(
                        "not a valid collection object");

            PrintWriter out = new PrintWriter(output, true);
            out.println(XMLUtil.getIESRHeader());
            out.println("<iesr:Collection>");

            if (getIdentifier() != null) {
                out.println(XMLUtil.wrapElement("dc", "identifier", getIdentifier()));
            }

            if (getTitle() != null) {
                out.println(XMLUtil.wrapElement("dc", "title", getTitle()));
            }

            if (getContentRange() != null) {
                out.println(XMLUtil.wrapElement("rslpcd", "contentsDateRange", getContentRange()));
            }

            if (!getTypes().isEmpty()) {
                out.println(XMLUtil.wrapSet("dc", "type", getTypes()));
            }

            if (!getItemFormats().isEmpty()) {
                out.println(XMLUtil.wrapSet("iesr", "itemFormat", getItemFormats()));
            }
            
            if (!getSubjects().isEmpty()) {
                out.println(XMLUtil.wrapSet("dc", "subject", getSubjects()));
            }
            
            if (!getVocabularies().isEmpty()) {
                out.println(XMLUtil.wrapSet("iesr", "usesControlledList", getVocabularies()));
            }
            
            if (!getItemTypes().isEmpty()) {
                out.println(XMLUtil.wrapSet("iesr", "itemType", getItemTypes()));
            }
            
            if (!getAssociations().isEmpty()) {
                out.println(XMLUtil.wrapSet("rslpcd", "hasAssociation", getAssociations()));
            }
            
            if (!getServices().isEmpty()) {
                out.println(XMLUtil.wrapSet("iesr", "hasService", getServices()));
            }

            if (!getIsPartOf().isEmpty()) {
                out.println(XMLUtil.wrapSet("dcterms", "isPartOf", getIsPartOf()));
            }
            
            if (getTemporalRange() != null) {
                out.println(XMLUtil.wrapElement("dcterms", "temporal", getTemporalRange()));
            }      
            
            if (getExtent() > 0) {
                out.println(XMLUtil.wrapElement("dcterms", "extent", Integer.toString(getExtent())));
            }
            
            out.println("</iesr:Collection>");
            out.println(XMLUtil.getIESRFooter());
            out.close();
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    /**
     * Deserialize an IESRCollection object from an InputStream
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
            
            // dc:title
            XPathExpression expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/dc:title/text()");
            Object result = expr.evaluate(doc, XPathConstants.STRING);
            this.setTitle((String) result);

            // dc:identifier
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/dc:identifier/text()");
            Object identifier = expr.evaluate(doc, XPathConstants.STRING);
            if (identifier == null)
                throw new SerializationException("empty identifier");
            this.setIdentifier((String) identifier);

            // rslpcd:contentsDateRange
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/rslpcd:contentsDateRange/text()");
            Object contentRange = expr.evaluate(doc, XPathConstants.STRING);
            this.setContentRange((String) contentRange);

            // iesr:hasService
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/iesr:hasService");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes == null || nodes.getLength() == 0) {
                throw new SerializationException("no services associated with collection");
            }
            for (int i = 0; i < nodes.getLength(); i++) {
                this.addService(nodes.item(i).getTextContent());
            }

            // dcterms:isPartOf
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/dcterms:isPartOf");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes == null || nodes.getLength() == 0) {
                throw new SerializationException("no collection information");
            }
            for (int i = 0; i < nodes.getLength(); i++) {
                this.addIsPartOf(nodes.item(i).getTextContent());
            }

            // dc:type
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/dc:type");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes == null || nodes.getLength() == 0) {
                throw new SerializationException("no dc:type information");
            }
            for (int i = 0; i < nodes.getLength(); i++) {
                this.addType(nodes.item(i).getTextContent());
            }
            
            // dcterms:extent
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/dcterms:extent/text()");
            Object extent = expr.evaluate(doc, XPathConstants.STRING);
            if (extent != null) {
                int count = 0;
                try {
                    count = Integer.parseInt((String) extent);
                } catch (NumberFormatException e) {
                    // Not an int, use 0 as default
                }
                if (count > 0)
                    this.setExtent(count);
            }
            
            // rslpcd:temporalRange
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/rslpcd:temporal/text()");
            Object temporalRange = expr.evaluate(doc, XPathConstants.STRING);
            this.setTemporalRange((String) temporalRange);
            
            // dc:subject
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/dc:subject");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes != null || nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    this.addSubject(nodes.item(i).getTextContent());
                }
            }
            
            // iesr:itemFormat
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/iesr:itemFormat");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes != null || nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    this.addItemFormat(nodes.item(i).getTextContent());
                }
            }
            
            // iesr:usesControlledList
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/iesr:usesControlledList");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes != null || nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    this.addVocabulary(nodes.item(i).getTextContent());
                }
            }
            
            // iesr:itemType
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/iesr:itemType");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes != null || nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    this.addItemType(nodes.item(i).getTextContent());
                }
            }
            
            // rslpcd:hasAssociation
            expr = xpath.compile("//iesr:iesrDescription/iesr:Collection/rslpcd:hasAssociation");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodes != null || nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    this.addAssociation(nodes.item(i).getTextContent());
                }
            }
            
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }
}
