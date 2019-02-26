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

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

/**
 * Facilitates XPATH usage of namespace
 * 
 * @see gov.lanl.ockham.iesrdata.IESRCollection
 * @see gov.lanl.ockham.iesrdata.IESRService
 * 
 */
public class IESRNamespaceContext implements NamespaceContext {
    public enum PREFIX {
        IESR("iesr"), DC("dc"), DCTERMS("dcterms"), RSLPCD("rslpcd");
        private String value = null;

        PREFIX(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    };

    public String getNamespaceURI(String prefix) {
        if (prefix == null)
            throw new NullPointerException("Null prefix");
        else if (PREFIX.IESR.value().equals(prefix))
            return "http://iesr.ac.uk/terms/#";
        else if (PREFIX.DC.value().equals(prefix))
            return "http://purl.org/dc/elements/1.1/";
        else if (PREFIX.DCTERMS.value().equals(prefix))
            return "http://purl.org/dc/terms/";
        else if (PREFIX.RSLPCD.value().equals(prefix))
            return "http://purl.org/rslp/terms#";

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
