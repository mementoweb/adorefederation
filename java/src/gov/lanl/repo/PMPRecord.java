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

package gov.lanl.repo;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ORG.oclc.oai.util.OAIUtil;

/**
 * PMPRecord.java
 * 
 * Represents a single record from an OAI ListRecords response.
 * 
 * @author Jeffrey A. Young, OCLC Online Computer Library Center
 * @author ludab
 */
public class PMPRecord extends DefaultHandler {
	private static final boolean debug = false;

	//public static final String OAI20_NS =
	// "http://www.openarchives.org/OAI/2.0/";
	public static final String OAI20_NS = RepoProperties.PUT_RECORD_NS;

	public static final String OAI20_STATUS = fullName(OAI20_NS, "status");

	private static HashMap xmlReaders = new HashMap();

	private String record = null;

	private boolean identifierCapture = false;

	private StringBuffer identifier = new StringBuffer();

	private boolean datestampCapture = false;

	private StringBuffer datestamp = new StringBuffer();

	private boolean setSpecCapture = false;

	private ArrayList setSpecs = new ArrayList();

	private StringBuffer setSpec;

	private String status = null;

	private boolean metadataCapture = false;

	private StringBuffer metadata = new StringBuffer();

	private boolean aboutCapture = false;

	private ArrayList abouts = new ArrayList();

	private StringBuffer about = new StringBuffer();

	private StringBuffer verb = new StringBuffer();

	private boolean rootCapture = false;

	private StringBuffer ns = new StringBuffer();

	private XMLReader getXMLReader() throws SAXException {
		Thread currentThread = Thread.currentThread();
		XMLReader xmlReader = (XMLReader) xmlReaders.get(currentThread);
		if (xmlReader == null) {
			try { // Xerces
				xmlReader = XMLReaderFactory
						.createXMLReader("org.apache.xerces.parsers.SAXParser");
			} catch (SAXException e) {
				try { // Crimson
					xmlReader = XMLReaderFactory
							.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
				} catch (SAXException e1) {
					try { // Piccolo
						xmlReader = XMLReaderFactory
								.createXMLReader("com.bluecase.xml.Piccolo");
					} catch (SAXException e2) {
						try { // Oracle
							xmlReader = XMLReaderFactory
									.createXMLReader("oracle.xml.parser.v2.SAXParser");
						} catch (SAXException e3) {
							try { // default
								xmlReader = XMLReaderFactory.createXMLReader();
							} catch (SAXException e4) {
								throw new SAXException(
										"No SAX parser available");
							}
						}
					}
				}
			}
			xmlReaders.put(currentThread, xmlReader);
		}
		return xmlReader;
	}

	/**
	 * Provides access to discrete parts of the OAI &lt;record&gt; response.
	 * 
	 * @param record
	 *            String containing the &lt;record&gt; portion of the OAI XML
	 *            response.
	 * 
	 * @exception SAXException
	 *                XML parser problem
	 * @exception IOException
	 */
	public PMPRecord(String record) throws PMPException {
		this.record = record;
		try {
			XMLReader xmlReader = getXMLReader();
			xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
			xmlReader.setContentHandler(this);

			if (debug) {
				System.out.println("Record.Record: record=" + record);
			}
			xmlReader.parse(new InputSource(new StringReader(record)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new PMPException(e.getMessage(), 2);
		}

	}

	/**
	 * Get the XML &lt;record&gt;
	 * 
	 * @return the record as an XML string
	 */
	public String getRecordXML() {
		return record;
	}

	/**
	 * Does the record contain a status="deleted" attribute?
	 * 
	 * @return true=record is flagged as deleted, false=record is not flagged as
	 *         deleted.
	 */
	public boolean isDeleted() {
		return "deleted".equals(status);
	}

	/**
	 * Get the content of the &lt;identifier&gt; element
	 * 
	 * @return The record's OAI identifier
	 */
	public String getIdentifier() {
		if (identifier.length() > 0) {
			return identifier.toString();
		} else {
			return null;
		}

	}

	public String getVerb() {
		return verb.toString();
	}

	public String getNS() {
		if (ns.length() > 0) {
			return ns.toString();
		} else {
			return null;
		}
	}

	/**
	 * Get the content of the &lt;datestamp&gt; element.
	 * 
	 * @return the record's datestamp.
	 */
	public String getDatestamp() {
		return datestamp.toString();
	}

	public String getMetadata() {
		if (metadata.length() > 0) {
			return metadata.toString();
		} else {
			return null;
		}

	}

	/**
	 * Get the record's setSpecs.
	 * 
	 * @return an Iterator containing Strings of setSpec values. (null of none)
	 */
	public Iterator getSetSpecs() {
		if (setSpecs.size() > 0)
			return setSpecs.iterator();
		else
			return null;
	}

	/**
	 * Get the content of the record's &lt;metadata&gt; element.
	 * 
	 * @return an XML String containing the metadata content for the record.
	 */

	/**
	 * Get the record's 'about' elements.
	 * 
	 * @return an Iterator containing XML Strings for each &lt;about&gt; entry
	 *         for the record.
	 */
	public Iterator getAbouts() {
		if (about.length() > 0)
			return abouts.iterator();
		else
			return null;
	}

	/**
	 * SAX parser call-back method for extracting record content.
	 */
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attrs) {
		if (debug) {
			System.out.println("startElement: namespaceURI=" + namespaceURI
					+ ", localName=" + localName + ", qName=" + qName
					+ ", attrs=" + attrs);
		}
		String fullName = namespaceURI + "#" + localName;

		if (metadataCapture == false
				&& fullName.equals(namespaceURI + "#metadata")) {
			metadata.setLength(0);
			System.out.println("metadata:" + metadata);
			metadataCapture = true;
			rootCapture = true;
		} else if (metadataCapture == false && aboutCapture == false
				&& fullName.equals(namespaceURI + "#about")) {
			about.setLength(0);
			aboutCapture = true;
		} else if (metadataCapture) {
			metadata.append("<");
			metadata.append(qName);
			int length = attrs.getLength();
			for (int i = 0; i < length; ++i) {
				metadata.append(" ");
				String aEName = attrs.getQName(i);
				metadata.append(aEName);
				metadata.append("=\"");
				metadata.append(OAIUtil.xmlEncode(attrs.getValue(i)));
				metadata.append("\"");
			}
			metadata.append(">");
			if (rootCapture) {
				ns.append(namespaceURI);
			}
			rootCapture = false;
		} else if (aboutCapture) {
			about.append("<");
			about.append(qName);
			int length = attrs.getLength();
			for (int i = 0; i < length; ++i) {
				about.append(" ");
				String aEName = attrs.getQName(i);
				about.append(aEName);
				about.append("=\"");
				about.append(OAIUtil.xmlEncode(attrs.getValue(i)));
				about.append("\"");
			}
			about.append(">");
		} else if (fullName.equals(namespaceURI + "#identifier")) {
			identifierCapture = true;
		} else if (fullName.equals(namespaceURI + "#datestamp")) {
			datestampCapture = true;
		} else if (fullName.equals(namespaceURI + "#setSpec")) {
			setSpecCapture = true;
			setSpec = new StringBuffer();
		} else if (fullName.equals(namespaceURI + "#record")) {
			// ignore
		} else if (fullName.equals(namespaceURI + "#PMPrequest")) {
			// ignore
		} else if (fullName.equals(namespaceURI + "#PutRecord")) {
			verb.append("PutRecord");
		} else if (fullName.equals(namespaceURI + "#DeleteRecord")) {
			verb.append("DeleteRecord");
		} else if (fullName.equals(namespaceURI + "#header")) {
			int length = attrs.getLength();
			for (int i = 0; i < length; ++i) {
				String aEName = fullName(attrs.getURI(i), attrs.getLocalName(i));
				if (OAI20_STATUS.equals(aEName)) {
					status = attrs.getValue(i);
				}
			}
		} else {
			//   System.out.println("Unrecognized element: " + qName);
		}
	}

	/**
	 * SAX parser call-back method for extracting record content.
	 */
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (debug) {
			System.out.println("endElement: namespaceURI=" + namespaceURI
					+ ", localName=" + localName + ", qName=" + qName);
		}
		String fullName = fullName(namespaceURI, localName);

		if (fullName.equals(namespaceURI + "#metadata")) {
			metadataCapture = false;
		}
		if (metadataCapture) {
			metadata.append("</");
			metadata.append(qName);
			metadata.append(">");
		} else if (aboutCapture) {
			about.append("</");
			about.append(qName);
			about.append(">");
		} else if (identifierCapture) {
			// do nothing
		} else if (datestampCapture) {
			// do nothing
		} else if (setSpecCapture) {
			// do nothing
		}
		if (fullName.equals(namespaceURI + "#about")) {
			aboutCapture = false;
			abouts.add(about.toString());
		} else if (fullName.equals(namespaceURI + "#identifier")) {
			identifierCapture = false;
		} else if (fullName.equals(namespaceURI + "#datestamp")) {
			datestampCapture = false;
		} else if (fullName.equals(namespaceURI + "#setSpec")) {
			setSpecs.add(setSpec.toString());
			setSpecCapture = false;
		}
	}

	/**
	 * SAX parser call-back method for extracting record content.
	 */
	public void characters(char[] buf, int offset, int len) {
		String s = new String(buf, offset, len);
		if (debug) {
			System.out.println("characters: s=" + s);
		}
		if (identifierCapture) {
			identifier.append(OAIUtil.xmlEncode(s));
		} else if (datestampCapture) {
			datestamp.append(OAIUtil.xmlEncode(s));
		} else if (setSpecCapture) {
			setSpec.append(OAIUtil.xmlEncode(s));
		} else if (metadataCapture) {
			metadata.append(OAIUtil.xmlEncode(s));
		} else if (aboutCapture) {
			about.append(OAIUtil.xmlEncode(s));
		} else if (s.trim().length() > 0) {
			if (debug)
				System.out.println("Unrecognized content:" + s);
		}
	}

	public static String fullName(String namespaceURI, String localName) {
		StringBuffer sb = new StringBuffer();
		sb.append(namespaceURI);
		sb.append("#");
		sb.append(localName);
		return sb.toString();
	}
}
