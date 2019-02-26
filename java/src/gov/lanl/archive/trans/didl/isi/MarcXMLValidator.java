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

package gov.lanl.archive.trans.didl.isi;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MarcXMLValidator {
    
    private static final Logger log = Logger.getLogger(MarcXMLValidator.class.getName());
    
    public static boolean validMarcXML(String xml) {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setFeature("http://xml.org/sax/features/validation", true);
            reader.setErrorHandler(new MarcXMLErrorHandler());
            reader.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

class MarcXMLErrorHandler implements ErrorHandler {
    public void warning(SAXParseException exception) throws SAXException {
        // ignore warnings
    }
    public void error(SAXParseException exception) throws SAXException {
        // ignore errors, we're looking for really malformed xml
    }
    public void fatalError(SAXParseException exception) throws SAXException {
        // this file is just not valid
        if (exception.getMessage().contains("xsi"))
            throw new SAXException("Error: xsi prefix declaration missing");
        else
            throw new SAXException(exception);
    }
} 
