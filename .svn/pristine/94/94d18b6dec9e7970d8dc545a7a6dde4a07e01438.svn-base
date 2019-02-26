/*
 * MyContentDeserializer.java
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.foo.didl.content.MyContent;

import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;

/**
 * MyContentDeserializer demonstrates how an XML serialized content object 
 * can be deserialized.  The class implements the DIDLDeserializerType 
 * interface which is called during the DIDL parse process.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 *
 */

public class MyContentDeserializer implements DIDLDeserializerType {
    ArrayList<String> nodes;

    /**
     * Reads a MyContent XML Fragment from specified InputStream 
     * and returns a populated MyContent instance. 
     */
    public Object read(InputStream in) throws DIDLSerializationException {
        MyContent msc = new MyContent();
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                bout.write(buffer, 0, len);
            }
            bout.close();

            String xml = bout.toString();

            XPathProcessor xpp = new XPathProcessor();
            xpp.setDocument(xml);
            xpp.addNamespace(MyContent.NAMESPACE, MyContent.PREFIX);
            msc.setId(xpp.xpath("//@id").get(0));
            // Check for resource elements
            nodes = xpp.xpath("//" + MyContent.PREFIX + ":resource");
            if (nodes.size() > 0)
                msc.setResourceUri(nodes.get(0));
            // Check for copyright elements
            nodes = xpp.xpath("//" + MyContent.PREFIX + ":copyright");
            if (nodes.size() > 0)
                msc.setCopyright(nodes.get(0));
            // Check for usage elements
            nodes = xpp.xpath("//" + MyContent.PREFIX + ":usage");
            if (nodes.size() > 0)
                msc.setUsage(nodes.get(0));

            return msc;
        } catch (IOException e) {
            throw new DIDLSerializationException(e.getMessage());
        } catch (Exception e) {
            throw new DIDLSerializationException(e.getMessage());
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
