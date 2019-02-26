/*
 * MyContentSerializer.java
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

import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.foo.didl.content.MyContent;

/**
 * MyContentSerializer demonstrates how a content object may be
 * serialized as XML.  The class implements the DIDLSerializerType
 * interface which is called during the DIDL creation process.  
 * <br/>
 * During DIDL Serialization the registry is checked for a implem.
 * for the current object type.  Provided there is a match, the 
 * DIDL serialization interface will delegate to the implem. class.
 * This is an example of an implem. class for a MyContent object.
 * <br/>
 * In MySimpleDidl we register this implem. in getXML(), while
 * in MyComplexDidl we call MySerializationFactory and ask it to 
 * return a DIDLSerializerType containing the registered content
 * type implementations.
 * 
 * The XML serialized MyContent object will be of the form:
 * <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"> 
 *   <xs:element name="resource" type="xs:string"/> 
 *   <xs:element name="copyright" type="xs:string"/> 
 *   <xs:element name="usage" type="xs:string"/> 
 *   <xs:attribute name="id" type="xs:ID"/> 
 * </xs:schema>
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 *
 */
public class MyContentSerializer implements DIDLSerializerType {

    /**
     * Writes a Diadm object to the specified OutputStream as a Diadm XML Fragment
     */
    public void write(OutputStream stream, Object obj)
            throws DIDLSerializationException {
        MyContent msc = (MyContent) obj;
        try {
            PrintWriter out = new PrintWriter(stream, true);
            out.print("<" + MyContent.PREFIX + ":content xmlns:" + MyContent.PREFIX + "=\"" + MyContent.NAMESPACE + "\"");
            out.print(" id=\"" + msc.getId() + "\">");

            if (msc.getResourceUri() != null)
                out.print("<" + MyContent.PREFIX + ":resource>" + msc.getResourceUri()
                        + "</" + MyContent.PREFIX + ":resource>");
            if (msc.getCopyright() != null)
                out.print("<" + MyContent.PREFIX + ":copyright>" + msc.getCopyright()
                        + "</" + MyContent.PREFIX + ":copyright>");
            if (msc.getUsage() != null)
                out.print("<" + MyContent.PREFIX + ":usage>" + msc.getUsage() + "</" + MyContent.PREFIX + ":usage>");

            out.print("</" + MyContent.PREFIX + ":content>");
            out.close();
        } catch (Exception ex) {
            throw new DIDLSerializationException(ex.getMessage());
        }
    }
    
     /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException {
        throw new DIDLSerializationException("no property is supported");
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
         throw new DIDLSerializationException("no property is supported");
    }
}
