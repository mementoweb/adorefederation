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

public class ContextObjectContainer {

	 
	   Entity referent; // required
	   Entity referringEntity; //optional
	   Entity requester; // optional
	   Entity  serviceType; // zero or more
	   Entity  resolver; // zero or more
	   Entity referrer; // optional
	   Entity currentreferent;
	   EntityIterator nodeitr ;
	
	public void setReferrer( Entity e){
	 	referrer = e;
		
	}
	public void setReferent(Entity e) throws Exception{
		nodeitr = new EntityIterator(e);
		//nodeitr=n.getNodes("entity");
		referent=e;
		currentreferent=e;
	}
	
	public void nextReferent() {
	
		 currentreferent = nodeitr.next();
	}
	
	public boolean hasNext(){
		return nodeitr.hasNext();
	}
	public void setResolver(Entity n){
		resolver=n;
	}
	public void setRequester(Entity n){
		requester=n;
	}
	public void setServiceType(Entity n){
		serviceType=n;
	}
	public void setReferringEntity(Entity n){
		referringEntity=n;
	}
	public Entity getReferrer(){
		return referrer;
	}
	public Entity getReferentMeanIt(){
		return referent;
	}
	public Entity getReferent() throws Exception{
		return currentreferent;
	}
	
	public Entity getResolver(){
		return resolver;
	}
	public Entity getRequester(){
	return	requester;
	}
	public Entity getServiceType(){
	return	serviceType;
	}
	public Entity getReferringEntity(){
	return	referringEntity;
	}

}
