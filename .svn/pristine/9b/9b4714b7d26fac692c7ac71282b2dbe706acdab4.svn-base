/*
 * DateTest.java
 *
 * Created on February 7, 2006, 4:42 PM
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

import info.repo.didl.DIDLType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.DIDLFactory;
import info.repo.didl.impl.serialize.SimpleContentCondition;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * <code>DateApp</code> is written to illustrates how to develop serialization
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DateApp {
    
    /** Creates a new instance of DateTest */
    public DateApp() {
    }
    
    /**
     * Creates a DIDL XML with a <code>Date</code> content in DIDL Statement
     *
     * The DIDL specification essentially allows ANYTHING in Statement, Resource,
     * and DIDLInfo, which we called content wrapper. in DIDAPI we model 
     " ANYTHING" to any Java <code>Object</code>, and it can be directly put into
     * content wrapper:
     * <pre>
     *  Statement.setContent(Object obj)
     * </pre>
     *
     * This is all required for creating and navigating DID in memory. However, 
     * things is a little complex when serializing and de-serializing DIDL, because
     * it also involves how to serialize/deserialize the Content. In DIDAPI we
     * approach the problem in following way:
     * 
     * (a) a content class needs serializer/deserializer class to be correctly 
     * serialized/deserilized in DIDL serialization. 
     * (b) the content class and its serializer should be be registered in 
     * <code>DIDLSerializer.getRegistry().addSerializer(Class content, Class serializer)</code>
     * the DID serializer will use this registry to find correct serializer class
     * for a special content.
     * (c) when putting the content into contentwrapper, a user may specify 
     * serialization parameters defined by DIDL, including mimeType, Ref, encoding,
     * and contentEncoding. 
     *
     * The follwing example illustrates how to put a Date object into DIDL 
     * Statement, and how to add DateSerializer into DIDLSerailizer 
     *
     * @return DIDL XML string
     * @throws any exception during creating and serializing the DIDL
     */
    public String serialize() throws java.io.IOException, info.repo.didl.serialize.DIDLSerializationException{
        
        //create a DIDL Factory
        DIDLFactory factory= new DIDLFactory();
        
        //create an empty DIDL
        DIDLType didl=factory.newDIDL();
        
        //create a date object
        Date date=new Date();
        
        //create a new Statement
        StatementType stmt=didl.newStatement();
        
        //set mimetype about date
        stmt.setMimeType("text/plain");
        
        // add date into statement
        stmt.setContent(date);
        
        //create Item/Descriptor structure in the empty DIDL, and insert satement
        didl.addItem(didl.newItem())
        .addDescriptor(didl.newDescriptor())
        .addStatement(stmt);
        
        //create an XML serializer
        info.repo.didl.impl.serialize.DIDLSerializer
                serializer = new info.repo.didl.impl.serialize.DIDLSerializer();
        
        //add Date->DateSerializer to serialization registry
        
        serializer.getRegistry().addSerializer(Date.class,DateSerializer.class);
        
        //serialize the didl to a stream
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        serializer.write(stream,didl);
        
        //close the stream
        stream.close();
        
        //turn the stream to a string
        return stream.toString();
    }
    
    /**
     * Deserialize a DIDL XML with a <code>Date</code> content in DIDL Statement
     *
     * this process is the inverse of serialization process by creating
     * a DIDL object from a DIDL XML string. However DIDL deserializer
     * needs additional step: once the DIDL deserializer find ANYTHING in a 
     * content wrapper, it needs to decide which type of object it is, and then 
     * invoking correct de-seralizier of this object, and re-construct the object.
     * This mechanism is managed by <code>DIDLStrategy</code>
     * 
     * There can be many stategy implementations,  in this case we use a
     * SimpleContentCondition, which does the string match of mimetype and
     * namespace (if applicable).
     *
     * @param didlxml the didlxml including a Date statement
     * @return date object contained in statement
     * @throws any exception during deserializing the DIDL
     */
    
    public Date deserialize(String didlxml) throws java.io.IOException,
            info.repo.didl.serialize.DIDLSerializationException{
       
        // create a DIDLDeserializer  
        info.repo.didl.impl.serialize.DIDLDeserializer
                deserializer = new info.repo.didl.impl.serialize.DIDLDeserializer();
        
        // tell DIDL deserializer that anything with "text/plain" mimetype should
        // be converted to Date.
        deserializer.getStrategy().addContentStrategy
                (new SimpleContentCondition(null,"text/plain",null, Date.class));
        
        // associating Date class with its deserializer 
        deserializer.getRegistry().addDeserializer(Date.class,DateDeserializer.class);
        
        //deserialize the DIDL
        DIDLType didl =(DIDLType)(deserializer.read(new ByteArrayInputStream(didlxml.getBytes())));
       
        //read date from the statement
        Date date=(Date)(didl.getItems().get(0)
        .getDescriptors().get(0)
        .getStatements().get(0)
        .getContent());
        
        return date;
    }
    
    
    /**
     * Round trip of a DIDL
     * (1) create a DIDL with date inside of it
     * (2)serialize DIDL to an XML string
     * (3)de-serialize the string to another DIDL
     * (4) get the date object back 
     */
    public static void main(String[] args){
        DateApp app=new DateApp();
        try{
            String didl=app.serialize();
            System.err.println(didl);
            
            Date date=app.deserialize(didl);
            System.err.println(date.toString());
            
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}