/*
 * Codec.java
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

package org.adore.didl.json;

import info.repo.didl.ComponentType;
import info.repo.didl.DIDLBaseType;
import info.repo.didl.DIDLFactoryType;
import info.repo.didl.DIDLType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ItemType;
import info.repo.didl.ResourceType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;
import org.adore.didl.content.DII;
import org.adore.didl.content.Diadm;
import org.adore.didl.helper.Env;
import org.adore.didl.helper.Helper;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A DIDL xml and json round trip converter
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>   
 */
public class Codec {
    Env env;
    DIDLFactoryType factory=null;
    BytestreamHandler handler;
    
    /**
     * Creates a new Codec instance
     */
    public Codec() {
        env=new Env();
        factory = env.getDIDLFactory();    
    }
    
    /**
     * Convert a DIDLObject to JSON
     * @param didl input
     */
    public String toJSON(DIDLType didl) throws Exception{
        JSONObject json=new JSONObject();
        
        if (didl.getDIDLDocumentId()!=null){
            json.put("DIDLDocumentId",didl.getDIDLDocumentId().toString());
        }
        
        for (ItemType item: didl.getItems()){
            json.put("entity",new JSONArray().put(processItem(item)));
        }
        return json.toString();
    }
    
    /**
     * Convert a DIDL String to JSON
     * @param didlxml a DIDL XML
     */
    public String toJSON(String didlxml) throws Exception{
        DIDLDeserializerType deserializer=env.getDIDLDeSerializer();
        DIDLType didl=(DIDLType)(deserializer.read(new ByteArrayInputStream(didlxml.getBytes())));
        return toJSON(didl);
    }
    
    /**
     * Convert a json String to didl object
     * @param jsonString a json object
     */
    public DIDLType toDIDL(String jsonString) throws Exception {
        JSONObject json=new JSONObject(jsonString);
        DIDLType didl = factory.newDIDL();
        if (json.has("DIDLDocumentId")){            didl.setDIDLDocumentId(new URI(json.getString("DIDLDocumentId")));
        }
        
        if (json.has("entity")){
            didl.addItem((ItemType)(processEntity(didl,json.getJSONArray("entity").getJSONObject(0))));
        }
        return didl;
    }

    /**
     */

    public void registerBytestreamHandler(BytestreamHandler handler){
	this.handler=handler;
	
    }
    
    /**
     * Convert a json string to didl xml string
     * @param jsonString a json object
     */
    public String toDIDLXML(String jsonString) throws Exception{
        DIDLSerializerType serializer=env.getDIDLSerializer();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serializer.write(out, toDIDL(jsonString));
        out.close();
        return out.toString();
    }
    
    private JSONObject processItem(ItemType item) throws Exception{
        JSONObject entity=new JSONObject();
        for (DescriptorType desc: item.getDescriptors()){
            if (desc.getStatements().get(0).getContent() instanceof DII){
                DII dii=(DII)desc.getStatements().get(0).getContent();
                entity.put("dii",dii.getValue());
            }
            
            if (desc.getStatements().get(0).getContent() instanceof Diadm){
                Diadm diadm=(Diadm)desc.getStatements().get(0).getContent();
                for (DC dc: diadm.getDC()){
                    entity.put(dc.getKey().value(),dc.getValue());
                }
                
                for (DCTerms dcterms: diadm.getDCTerms()){
                    entity.put(dcterms.getKey().value(),dcterms.getValue());
                }
            }
        }
        
        JSONArray array=new JSONArray();
        for (ItemType i:item.getItems()){
            array.put(processItem(i));
        }
        
        for (ComponentType com:item.getComponents()){
            array.put(processComponent(com));
        }
        
        entity.put("entity",array);
        return entity;
    }
    
    private JSONObject processComponent(ComponentType com) throws Exception{
        JSONObject entity=new JSONObject();
        for (DescriptorType desc: com.getDescriptors()){
            if (desc.getStatements().get(0).getContent() instanceof DII){
                DII dii=(DII)desc.getStatements().get(0).getContent();
                entity.put("dii",dii.getValue());
            }
            
            if (desc.getStatements().get(0).getContent() instanceof Diadm){
                Diadm diadm=(Diadm)desc.getStatements().get(0).getContent();
                for (DC dc: diadm.getDC()){
                    entity.put(dc.getKey().value(),dc.getValue());
                }
                
                for (DCTerms dcterms: diadm.getDCTerms()){
                    entity.put(dcterms.getKey().value(),dcterms.getValue());
                }
            }
        }
        
        JSONArray array=new JSONArray();
        
        for (ResourceType res:com.getResources()){
            JSONObject jres=new JSONObject();
            jres.put("mimeType",res.getMimeType());
            if (res.getContentEncoding()!=null)
                jres.put("contentEncoding",res.getContentEncoding());
            
            if (res.getEncoding()!=null)
                jres.put("encoding",res.getEncoding());
            
            if (res.getRef()!=null)
                jres.put("ref",res.getRef().toString());
            else{
                
                if (res.getContent() instanceof ByteArray){
                    if (res.getEncoding()!=null){                     
                        jres.put("contentURL",handler.write(((ByteArray)res.getContent()).getBytes()));
                    } else
                        jres.put("content",((ByteArray)res.getContent()).getString());
                } else
                    jres.put("content",res.getContent().toString());
            }
            array.put(jres);
        }
        entity.put("resource",array);
        return entity;
    }
   
    private DIDLBaseType processEntity(DIDLType didl,JSONObject entity) throws Exception{
        Diadm diadm=new Diadm();
        DII dii=null;
        
        if (entity.has("dii")){
            dii=new DII(DII.IDENTIFIER,entity.getString("dii"));
        }
        
        for (DC.Key key: DC.Key.values()){
            if (entity.has(key.value())){
                diadm.addDC(new DC(key,entity.getString(key.value())));
            }
        }
        
        ArrayList<DCTerms> dctermsList=new ArrayList();
        for (DCTerms.Key key: DCTerms.Key.values()){
            if (entity.has(key.value())){
                diadm.addDCTerms(new DCTerms(key,entity.getString(key.value())));
            }
        }
        
        //entity is a component if it has a resource
        if (entity.has("resource")){
            ComponentType com=Helper.newComponent(didl);
            if (dii!=null){
                com.addDescriptor(didl.newDescriptor())
                .addStatement(Helper.newXMLStatement(didl,dii));
            }
            
            if ((diadm.getDC().size()!=0) || (diadm.getDCTerms().size()!=0)){
                com.addDescriptor(didl.newDescriptor())
                .addStatement(Helper.newXMLStatement(didl,diadm));
            }
            
            JSONArray resources = entity.getJSONArray("resource");
            if (resources.length()!=0){
                for (int i=0;i<resources.length();i++){
                    JSONObject jres=resources.getJSONObject(i);
                    ResourceType resource=didl.newResource();
                    
                    resource.setMimeType(jres.getString("mimeType"));
                    
                    if(jres.has("contentEncoding")){
                        resource.setEncoding(jres.getString("contentEncoding"));
                    }
                    
                    if(jres.has("encoding")){
                        resource.setEncoding(jres.getString("encoding"));
                    }
                    
                    if (jres.has("ref")){
                        resource.setRef(new URI(jres.getString("ref")));
                        resource.setContent(Helper.newByteArray(new URL(jres.getString("ref"))));
                    } else if (jres.has("content")){
                        resource.setContent(Helper.newByteArray(jres.getString("content")));
                    } else if (jres.has("contentURL")){
                        resource.setContent(Helper.newByteArray(new URL(jres.getString("contentURL"))));
                    }
                    
                    com.addResource(resource);
                }
            }
            return com;
        }
        
        //otherwise it's an item
        else{
            // the entity is mapping to Codec item iff there is a sub-entity
            
            ItemType item=Helper.newItem(didl);
            
            if (dii!=null){
                item.addDescriptor(didl.newDescriptor())
                .addStatement(Helper.newXMLStatement(didl,dii));
            }
            
            if ((diadm.getDC().size()!=0) || (diadm.getDCTerms().size()!=0)){
                item.addDescriptor(didl.newDescriptor())
                .addStatement(Helper.newXMLStatement(didl,diadm));
            }
            
            if (entity.has("entity")){
                JSONArray subentity=entity.getJSONArray("entity");
                
                for (int i=0;i<subentity.length();i++){
                    DIDLBaseType elem=processEntity(didl,subentity.getJSONObject(i));
                    if (elem instanceof ItemType){
                        item.addItem((ItemType)elem);
                    } else
                        item.addComponent((ComponentType)elem);
                }
            }
            return item;
            
        }
    }
}
