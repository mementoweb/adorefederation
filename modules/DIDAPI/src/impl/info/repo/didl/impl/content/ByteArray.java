/*
 * ByteArray.java
 *
 * Created on 12 januari 2006, 15:12
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

package info.repo.didl.impl.content;

import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;


/**
 * Default Content Type for all Asset instances. 
 *
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class ByteArray implements DIDLSerializerType, DIDLDeserializerType {
	/**
	 * Default Buffer Size
	 */
    public static int BUF_SIZE = 1024;
    private byte[] data;
    
    // a latentURL will only be read when requested. Once requested, the data
    // is written into array, and latentURL is reset to null.
    private URL latentURL = null;
    
    /** 
     * Creates a new instance of ByteArray 
     */
    public ByteArray() {
    }
    
    /**
     * Creates a new instance of ByteArray with specified data
     * @param data byte array to set as data
     */
    public ByteArray(final byte[] data) {
        setData(data);
    }
    
    /**
     * Creates a new instance of ByteArray with specified data
     * @param data String to set as data
     */
    public ByteArray(final String data) {
        setData(data);
    }
    
    /**
     * Creates a new instance of ByteArray with specified InputStream
     * @param stream InputStream to set as data
     */
    public ByteArray(final InputStream stream) throws IOException {
        setData(stream);
    }
    
    /**
     * Creates a ByteArray by URL, content loaded upon request
     * @param url InputStream to set as data
     */
    public ByteArray(java.net.URL url) {
        latentURL=url;
    }
    
    /**
     * Gets the byte array of data, latentURL content harvested when called
     * @return byte array of data field
     * @throws IOException
     */
    public byte[] getBytes() throws IOException {
        checkLatentURL();
        if (latentURL!=null){
            setData(latentURL.openStream());
            latentURL=null;
        }
        
        return data;
    }
    
    /**
     * Gets String form of data
     * @return String object of data
     * @throws IOException
     */
    public String getString() throws IOException{
        checkLatentURL();
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Gets data as an InputStream
     * @return data as InputStream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException{
        checkLatentURL();
        return new ByteArrayInputStream(data);
    }
    
    /**
     * Sets data using a byte array
     * @param data byte array containing data
     */
    public void setData(final byte[] data) {
        this.data = (byte[]) data.clone();
    }
    
    /**
     * Sets data using a String
     * @param data String instance of data
     */
    public void setData(final String data) {
        this.data = data.getBytes();
    }
    
    /**
     * Sets data using an InputStream
     * @param stream InputStream to be converted to byte array
     * @throws IOException
     */
    public void setData(final InputStream stream) throws IOException {
        byte[] buffer = new byte[BUF_SIZE];
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        while((len = stream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
        
        this.data = out.toByteArray();
    }
    
    /**
     * Writes object to specified OutputStream
     */
    public void write(java.io.OutputStream out, Object obj) throws DIDLSerializationException {
        ByteArray ba = (ByteArray) obj;
        try {
            out.write(ba.getBytes());
        } catch (IOException e) {
            throw new DIDLSerializationException(e);
        }
    }
    
    /**
     * Reads InputStream, converts to byte array and returns ByteArray object
     */
    public Object read(java.io.InputStream in) throws DIDLSerializationException {
        ByteArray ba = new ByteArray();
        
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                bout.write(buffer, 0, len);
            }
            ba.setData(bout.toByteArray());
        } catch (IOException e) {
            throw new DIDLSerializationException(e);
        }
        
        return ba;
    }
    
    private void checkLatentURL() throws IOException{
        if (latentURL != null){
            setData(latentURL.openStream());
            latentURL=null;
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
