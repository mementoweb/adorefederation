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
import gov.lanl.identifier.sru.SRUDC;
import gov.lanl.identifier.sru.SRUSearchRetrieveResponse;
import gov.lanl.locator.IdLocation;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.transform.TransformerException;

public class GetLocations extends AbstractService {
	public GetLocations(OpenURLConfig openURLConfig, ClassConfig classConfig)
			throws TransformerException {
		super(openURLConfig, classConfig);
	}

	@Override
	public Resource serve(ContextObjectContainer co) throws Exception {
		// AdoreObtain o = new AdoreObtain();
		ResourceManager rm = getResourceManager();
		Resource r = new Resource();
		List<IdLocation> locations = rm.getLocations(co.getReferent()
				.getProperty("rft_id"));

		// Compose SRU Response
		SRUSearchRetrieveResponse srr = new SRUSearchRetrieveResponse();
		for (IdLocation loc : locations) {
			SRUDC dc = new SRUDC();
			dc.addKey(SRUDC.Key.IDENTIFIER, loc.getId());
			dc.addKey(SRUDC.Key.SOURCE, loc.getRepo());
			if (loc.getDate() != null)
				dc.addKey(SRUDC.Key.DATE, loc.getDate());
			srr.addRecord(dc);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SRUSearchRetrieveResponse.write(baos, srr);
		r.setContentType("application/xml");
		r.setBytes(baos.toByteArray());
		return r;

	}

	public URI getServiceID() throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
