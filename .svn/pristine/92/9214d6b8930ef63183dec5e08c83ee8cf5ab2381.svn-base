/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
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

package gov.lanl.disseminator.service;

import gov.lanl.disseminator.model.Entity;
import gov.lanl.util.resource.Resource;

/**
 *  an abstraction of input to a service
 * <p/>
 * a service need two types of input information: the 
 * datastream itself, as wrapped by resource; and the metadata 
 * about the datastream, as wrapped by an entity.
 * 
 * @author liu_x
 *
 */
public class ServiceInput {
	private Entity entity;
	private Resource resource;
	
	private String rft_id;
	private String rft_version;
	private String svc_id;
	private String svc_args;
	
	public ServiceInput(Entity entity,Resource resource, String rft_id, String svc_id, String rft_version ,String svc_args){
		this.entity=entity;
		this.resource=resource;
	}
	
	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	/**
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * @return the rft_version
	 */
	public String getRft_version() {
		return rft_version;
	}

	/**
	 * @param rft_version the rft_version to set
	 */
	public void setRft_version(String rft_version) {
		this.rft_version = rft_version;
	}

	/**
	 * @return the rft_id
	 */
	public String getRft_id() {
		return rft_id;
	}

	/**
	 * @param rft_id the rft_id to set
	 */
	public void setRft_id(String rft_id) {
		this.rft_id = rft_id;
	}

	/**
	 * @return the svc_args
	 */
	public String getSvc_args() {
		return svc_args;
	}

	/**
	 * @param svc_args the svc_args to set
	 */
	public void setSvc_args(String svc_args) {
		this.svc_args = svc_args;
	}

	/**
	 * @return the svc_id
	 */
	public String getSvc_id() {
		return svc_id;
	}

	/**
	 * @param svc_id the svc_id to set
	 */
	public void setSvc_id(String svc_id) {
		this.svc_id = svc_id;
	}
}
