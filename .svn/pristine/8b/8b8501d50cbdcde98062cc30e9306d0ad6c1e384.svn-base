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

package gov.lanl.disseminator.service;

import gov.lanl.adore.helper.ResourceManager;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.transform.TransformerException;

public class GetDataStream extends AbstractService {

	public GetDataStream(OpenURLConfig openURLConfig, ClassConfig classConfig)
			throws TransformerException {
		super(openURLConfig, classConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Resource serve(ContextObjectContainer co) throws Exception {

		ResourceManager rm = this.getResourceManager();
		Resource r = new Resource();
		Entity entity = co.getReferent();
		String id = entity.getProperty("rft_id");

		if (ResourceManager.TYPE_XMLTAPE.equals(rm.getResourceType(id))) {
			r.setBytes(entity.getContent());
		} else {
			setEntity(co);
			Entity identity = co.getReferent();
			if (identity.getContent() != null) {
				r.setBytes(identity.getContent());
				r.setContentType(identity.getProperty(Entity.MIME_TYPE));
			} else {
				r = rm.getResource(id, "arc");
			}
		}
		return r;
	}

	public URI getServiceID() throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}
}