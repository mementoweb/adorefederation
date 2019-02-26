/*
 * DIDLDeserializer.java
 *
 * Created on October 13, 2005, 2:32 PM
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

import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLRegistryType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLStrategyType;
import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * <code>DIDLDeserializer</code> provides a wrapper for the various
 * components necessary for deserialization.  Instantiation will
 * initialize XMLStrategy and XMLRegistry objects.  Be sure to
 * register the content type deserializers and content strategies.
 *
 * <code>
 * xxx.getRegistry().addDeserializer(YYY.class,YYYDeserializer.class);
 * xxx.getStrategy().addContentStrategy(new SimpleContentCondition(null,null,YYYDeserializer.NAMESPACE,YYY.class));
 * </code>
 *
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DIDLDeserializer implements DIDLDeserializerType {
    private static String COPIER_CLASS="copier:class";
    
    private DIDLHandler handler;
    
    /**
     * Creates a new DIDLDeserializer instance, initializes an
     * XMLStrategy and XMLRegistry through DIDLHandler();
     */
    public DIDLDeserializer() {
        this.handler = new DIDLHandler();
    }
    
    /**
     * Gets the XMLRegistry initialized in DIDLHandler
     * @return XMLRegistry as DIDLRegistryType
     */
    public DIDLRegistryType getRegistry() {
        return handler.getRegistry();
    }
    
    /**
     * Gets the XMLStrategy initialized in DIDLHandler
     * @return XMLStrategy as DIDLStrategyType
     */
    public DIDLStrategyType getStrategy() {
        return handler.getStrategy();
    }
    
    /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException{
        if ((id==null)||(value==null)||(!COPIER_CLASS.equals(id)))
            throw new DIDLSerializationException(id+ " "+ value + "cannot be set");
        try{
            handler.setCopierClass(value);
        } catch (ClassNotFoundException ex){
            throw new DIDLSerializationException(ex);
        }
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
        if ((id==null)||(!COPIER_CLASS.equals(id)))
            throw new DIDLSerializationException(id+ "not supported");
        return handler.getCopierClass();
    }
    
    /**
     * Reads DIDL content using DIDLHandler implementation
     */
    public Object read(InputStream stream) throws DIDLSerializationException {
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setFeature("http://xml.org/sax/features/namespaces", true);
            parser.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            parser.setContentHandler(handler);
            parser.parse(new InputSource(stream));
        } catch (Exception e) {
            throw new DIDLSerializationException(e);
        }
        
        return handler.getDIDL();
    }
}
