/*
 * MyAtt.java
 *
 * Created on January 18, 2006, 1:34 PM
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

package info.repo.didl.impl.serialize;

import info.repo.didl.DIDLException;
import info.repo.didl.impl.AbstractAttribute;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class MyAtt extends AbstractAttribute implements AttributeSerializer, DIDLDeserializerType {
    private final static String NAMESPACE = "urn:you:know:i:aint:no:namespace";
    private final static String SCHEMA    = "http://dont.bother.me";
    
    public MyAtt() {
    }
    
    public MyAtt(String label) {
        setValue("label", label);
    }
    
    public String getLabel() {
        return (String) getValue("label");
    }
    
    public void write(OutputStream out, Object obj) throws DIDLSerializationException {
        MyAtt my = (MyAtt) obj;
        
        PrintWriter pw = new PrintWriter(out);
        pw.print("my:label=\"" + my.getValue("label") + "\" ");
        pw.print("xmlns:my=\"" +NAMESPACE + "\"");
        pw.close();
    }
    
    public Object read(InputStream stream) throws DIDLSerializationException {
        MyAtt att = new MyAtt();
        
        try {
            ObjectInputStream in = new ObjectInputStream(stream);
            Map nvmap = (Map) in.readObject();
            in.close();
            
            String label = (String) nvmap.get("label");
            
            att.setValue("label", label);
        } 
        catch (ClassNotFoundException e) {
            throw new DIDLSerializationException(e.getMessage());
        }
        catch (IOException e) {
            throw new DIDLException(DIDLException.UNKNOWN_ERROR, e.getMessage());
        }
        
        return att;
    }
    
    public String getSchema() {
        return SCHEMA;
    }
    
    public String getNamespace() {
        return NAMESPACE;
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
