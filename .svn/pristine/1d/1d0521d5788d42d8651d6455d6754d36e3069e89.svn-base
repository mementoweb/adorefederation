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

package gov.lanl.disseminator.service.didl;

import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.service.DefaultXSLTService;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import javax.xml.transform.TransformerException;

public class GetDIDLV extends DefaultXSLTService {

	public GetDIDLV(OpenURLConfig openURLConfig, ClassConfig classConfig)
			throws TransformerException {
		super(openURLConfig, classConfig);
		// TODO Auto-generated constructor stub
	}

	public Resource serve(ContextObjectContainer co) throws Exception {
		Entity referent = co.getReferent();
		String xslt = getXSLTPath();
		System.out.println("xslt" + xslt);
		Resource result = new Resource();
		String xml = new String(referent.getContent());
		if (xslt.equals("none")) {

			result.setContentType(getMimeType());
			result.setBytes(xml.getBytes("UTF-8"));
			// result.setContentType("application/xml");
		}

		else {
			String output = transform(xml);
			result.setContentType(getMimeType());
			result.setBytes(output.getBytes("UTF-8"));

		}
		return result;

	}
}
