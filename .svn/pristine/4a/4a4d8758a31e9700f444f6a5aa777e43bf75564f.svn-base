/*
 * DateSerializer.java
 *
 * Created on February 7, 2006, 4:23 PM
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

package gov.lanl.didl.example.content;

import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>DateSerializer</code> demonstrates how to write a serializer for <code>
 * Date</code> object.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DateSerializer implements DIDLSerializerType{
    SimpleDateFormat formatter;
    
    /**
     * Creates a new instance of DateSerializer
     */
    public DateSerializer() {
        formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }
    
    /**
     * Converts the date object to a string, and writes it into an outputStream
     *
     */
    public void write(OutputStream stream, Object obj) throws  DIDLSerializationException{
        try{
            Date date= (Date) obj;
            PrintWriter out = new PrintWriter(stream, true);
            out.print(formatter.format(date));
            out.close();
        } catch (Exception ex){
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
