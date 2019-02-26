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

package gov.lanl.disseminator;

import gov.lanl.util.resource.Resource;

/**
 * helper application so we can test applications without a live resourcemanager
 * 
 * @author liu_x
 * 
 */
public class DummyResourceManager {

	String data;

	public DummyResourceManager(String data) {
		this.data = data;
	}

	public Resource getResource(String identifier) throws Exception {
		Resource resource = new Resource();
		resource.setBytes(data.getBytes());
		resource.setContentType("application/xml");
		return resource;
	}

}
