/*
 * DateDeserializer.java
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * <code>DateDeserializer</code> demonstrates how to write a deserializer for <code>
 * Date</code> object.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DateDeserializer  implements info.repo.didl.serialize.DIDLDeserializerType {
    SimpleDateFormat formatter;
    
    public DateDeserializer(){
        formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }
    
    /**
     * Reads the stream in, converts it to a String, and then creates a Date object
     *
     */
    public Date read(InputStream stream) throws DIDLSerializationException {
        try{
            java.io.BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
            String date=reader.readLine();
            reader.close();
            return formatter.parse(date);
        } catch (IOException ex) {
            throw new DIDLSerializationException(ex);
        } catch (java.text.ParseException ex) {
            throw new DIDLSerializationException(ex);
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