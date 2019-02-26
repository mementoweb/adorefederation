/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
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
 * 
 */

package gov.lanl.disseminator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import gov.lanl.disseminator.DisseminatorException;

/**
 * abstraction of input to matchmaker
 * 
 * 
 * @author liu_x
 * 
 */
public class Entity {
	
	public static String FORMAT_ATT="format"; //attribute to save format
	public static String IDENTIFIER_ATT="identifier"; //attribute to save identifier
	public static String SEMANTIC_ATT="semantic"; //attribute for semantic
	public static String REF_ATT="ref"; //URL of datastream
	public static String MIME_TYPE="mimetype";
	
	private HashMap<String,String>properties;

	private byte[] content;
	
	private boolean isDataStream=false;

	private List<Entity> subEntities;

	private List<String> services;
    //private HashMap<String, List<String>> srvparam;
	private HashMap<String, HashMap<String, String>> srvparam;
	public Entity() {
		subEntities = new ArrayList<Entity>();
		services = new ArrayList<String>();
		properties=new HashMap<String,String>();
		//srvparam = new HashMap<String ,List <String>>();
		//HashMap srvmap = new HashMap<String,String>();
		srvparam = new HashMap<String,HashMap<String, String>>(); 
	}

	public Entity(String identifier) {
		this();
		properties.put("identifier", identifier);

	}

	public String getProperty(String key){
		return properties.get(key);
	}

	public void setProperty(String key, String value){
		properties.put(key, value);
	}

	public boolean hasProperty(String key){
	return	properties.containsKey(key);
	}
	
	public void setIsDataStream(boolean isDataStream){
		properties.put("isDataStream",String.valueOf(isDataStream));
		this.isDataStream=isDataStream;
		
	}
	
	public boolean isDataStream(){
		return isDataStream;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	public HashMap getProperties(){
		return properties;
	}

	/**
	 * Get a list of entities
	 * 
	 * @return a List of entities
	 */
	public List<Entity> getEntities() {
		return subEntities == null ? null : Collections
				.unmodifiableList(subEntities);
	}

	public Entity addEntity(Entity entity) {
		subEntities.add(entity);
		return entity;
	}

	public Entity replaceEntity(Entity n, Entity o)
			throws DisseminatorException {
		int index = subEntities.indexOf(o);

		if (index == -1) {
			throw new DisseminatorException("record not found");
		} else {
			subEntities.set(index, n);
		}
		return o;
	}

	public Entity removeEntity(Entity e) throws DisseminatorException {
		if (subEntities.remove(e)) {
			return e;
		} else {
			throw new DisseminatorException("record not found");
		}
	}

	public List<String> getServices() {
		return services == null ? null : Collections.unmodifiableList(services);
	}

	public String addService(String service) {
		services.add(service);
		return service;
	}

	public void  addParamToService(String service,String pname,String param){
		if (srvparam.containsKey(service))
		{
		
			//List<String> args = (List)srvparam.get(service);
			HashMap<String, String> args=(HashMap)srvparam.get(service);
			//args.add(param);
			args.put(pname, param);
			srvparam.put(service,args);
			System.out.println("existservice:"+ service+ "param:"+param);
		}
		else {
			//List<String> args = new ArrayList<String>();
			HashMap<String, String> args = new HashMap<String, String>();
			//args.add(param);
			args.put(pname, param);
			srvparam.put(service,args);
			System.out.println("service:"+ service+ "param:"+param);
		}	
			
	}
	
	public HashMap  getParams(String service){
		if (srvparam.containsKey(service))
		{
		    
			//List args =(List) srvparam.get(service);
			HashMap args=(HashMap)srvparam.get(service);
		  System.out.println("containsKey:"+service);
		  return args;
		}
		else
		{
			System.out.println("new array:");
			return new HashMap<String,String>();
		}
	}
	
	public String replaceService(String n, String o)
			throws DisseminatorException {
		int index = services.indexOf(o);

		if (index == -1) {
			throw new DisseminatorException("record not found");
		} else {
			services.set(index, n);
		}
		return o;
	}

	public String removeService(String s) throws DisseminatorException {
		if (services.remove(s)) {
			return s;
		} else {
			throw new DisseminatorException("record not found");
		}
	}

	/**
	 * locate the entity with a key/value pair, 
	 * and return the matched entity
	 * @param key
	 * @param value
	 * @return
	 */
	public Entity searchWithin(String key, String value) {
		return search(this, key,value);
	}

	// added in case of multiple entities match the same property
	public void searchAll(String key,String value,List l)
	{
		searchMore(this,key,value,l);
	}
	public void searchMore(Entity e,String key,String value,List l){
		if (check(e,key,value)!=null){
			l.add((Entity) e); 
		}
		for (Entity subent : e.getEntities()) {
			searchMore(subent,key,value,l);
		}
		
	}
	
	public Entity check (Entity entity, String key, String value) {
		if (entity.getProperty(key).equals(value)) {
			return entity;
		}
		  return null;
		}
		
	public Entity search(Entity entity, String key, String value) {
		if (entity.getProperty(key).equals(value)) {
			return entity;
		} else {
			for (Entity subent : entity.getEntities()) {
				Entity curr = search(subent, key,value);
				if (curr != null)
					return curr;
			}
		}

		return null;
	}
	

}
