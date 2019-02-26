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

package gov.lanl.disseminator.service.marc;

import gov.lanl.disseminator.DmtConstants;
import gov.lanl.disseminator.service.DefaultXSLTService;
import gov.lanl.util.resource.Resource;
import gov.lanl.util.xpath.marcxml.MarcXmlParser;
import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;
import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.service.AbstractService;

import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class GetCitation extends DefaultXSLTService {

	public GetCitation(OpenURLConfig openURLConfig, ClassConfig classConfig)
			throws TransformerException {
		super(openURLConfig, classConfig);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construct a Hello World web service class.
	 * 
	 * @param openURLConfig
	 * @param classConfig
	 * @throws TransformerException
	 */

	/**
	 * 
	 * public synchronized String transform (String input,String rft_id,String
	 * rft_version, String svc_args) throws Exception{ XSLTTransformer xtran =
	 * XSLTPool.borrowObject("/etc/xslt/marc/fullrecord.xsl"); String output =
	 * xtran.transform(input);
	 * XSLTPool.returnObject("/etc/xslt/marc/fullrecord.xsl", xtran); return
	 * output;
	 *  }
	 * 
	 */
	@Override
	public Resource serve(ContextObjectContainer co) throws Exception {
		try {
			// co.getServiceType().setProperty("type", getType());
			// Entity e = matchEntity(co);

			Entity e;
			if (getType().equals("meta")) {
				e = getMarcXML();
			} else {
				co.getServiceType().setProperty("type", getType());
				e = matchEntity(co);
			}

			// String xml = new String (e.getContent());
			Resource r = new Resource();
			r.setContentType("application/xml");
			r.setBytes(e.getContent());

			// if
			// (co.getServiceType().getProperty("svc_id").equals(DmtConstants.getcitation))
			// {
			if (co.getServiceType().hasProperty("svc.start")) {
				int start = Integer.parseInt(co.getServiceType().getProperty(
						"svc.start"));
				int end = Integer.parseInt(co.getServiceType().getProperty(
						"svc.end"));
				int count = 0;
				MarcXmlParser parser = new MarcXmlParser(r.getBytes());
				//HashMap marcmap = MarcXmlParser.processContent(r.getBytes());
				ArrayList nlist = parser.getNodeList("//marc:record");
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				String header = "<collection xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://purl.lanl.gov/aDORe/schemas/2006-09/MARC21slim.xsd\" "
						+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
						+ " xmlns:marc=\"http://www.loc.gov/MARC21/slim\">";

				out.write(header.getBytes());
				OutputFormat outformat = OutputFormat.createPrettyPrint();
				outformat.setEncoding("UTF-8");
				XMLWriter writer = new XMLWriter(out, outformat);
				for (Iterator k = nlist.iterator(); k.hasNext();) {
					Node value = (Node) k.next();
					count = count + 1;

					if (count >= start && count <= end)
						writer.write(value);
					writer.flush();
				}

				out.write("</collection>".getBytes());
				r.setBytes(out.toByteArray());
			}
			// }

			if (co.getServiceType().getProperty("svc_id").indexOf(".size") > 0) {
				MarcXmlParser parser = new MarcXmlParser(r.getBytes());
				//HashMap marcmap = MarcXmlParser.processContent(r.getBytes());
				ArrayList nlist = parser.getNodeList("//marc:record");
				String testout = "<collection>" + nlist.size()
						+ "</collection>";
				r.setBytes(testout.getBytes());
			}

			// if (r == null)
			// throw new NullPointerException("no resource passed in");
			// Entity referent = co.getReferentMeanIt();
			String service = co.getServiceType().getProperty("svc_id");
			// List args = e.getParams(service);
			HashMap args = e.getParams(service);
			if (args.containsKey("present")) {

				String xml = new String(r.getBytes());
				String output = transform(xml);
				Resource result = new Resource();
				result.setContentType("text/html");
				result.setBytes(output.getBytes("UTF-8"));

				return result;
			} else {
				// just plain marc-xml or restricted to size or just size

				return r;
			}
		} catch (Exception e) {

			e.printStackTrace();
			throw new Exception();
		}
	}

}
