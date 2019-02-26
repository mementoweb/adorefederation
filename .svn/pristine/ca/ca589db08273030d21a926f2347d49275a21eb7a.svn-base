/*
 * SchemaProperty.java
 *
 * Created on October 3, 2006, 9:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package info.repo.didl.impl.serialize;

import java.util.Properties;
import info.repo.didl.serialize.DIDLSerializationException;
/**
 * helper class to implement property interface of DIDLSerializaer
 *
 * @author liu_x
 */
public class SimpleSerializationProperty {
    private Properties properties;
    public static final String SCHEMA_LOCATION = "xsi:schemaLocation";
    
    public SimpleSerializationProperty() {
        properties=new Properties();
    }
    
    /**
     * initialization and create a property
     */
    public SimpleSerializationProperty(String id, String value) throws DIDLSerializationException{
        this();
        setProperty(id,value);
    }
    
    
    /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException{
        if ((id==null)||(value==null)||(!SCHEMA_LOCATION.equals(id)))
            throw new DIDLSerializationException(id+ " "+value + "cannot be set");
        properties.put(id,value);
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
        if (id==null || (!SCHEMA_LOCATION.equals(id)) || properties.get(id)==null)
            throw new DIDLSerializationException(id+ "cannot be found");
        return (String)(properties.get(SCHEMA_LOCATION));
    }
    
    
    public String getSchemaLocation(){
        try{
            return getProperty(SCHEMA_LOCATION);
        }
        catch (DIDLSerializationException ex){
            throw new IllegalArgumentException(ex);
        }
    }
    
    public void setSchemaLocation(String location){
        try{
            setProperty(SCHEMA_LOCATION, location);
        }
         catch (DIDLSerializationException ex){
            throw new IllegalArgumentException(ex);
        }
    }
    
}
