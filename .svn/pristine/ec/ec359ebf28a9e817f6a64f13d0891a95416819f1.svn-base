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

package gov.lanl.disseminator.adore.didl2model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Stack;

import info.repo.didl.ComponentType;
import info.repo.didl.DIDLInfoType;
import info.repo.didl.DIDLType;
import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ItemType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;
import org.adore.didl.content.DII;
import org.adore.didl.content.Diadm;
import org.adore.didl.content.Premis;
import org.adore.didl.helper.Env;
import org.apache.log4j.Logger;

import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.DmtConstants;

public class DIDL2Entity implements DIDLVisitorType {

	private static Logger logger = Logger.getLogger("adore-disseminator");

	Stack<Entity> stack = new Stack<Entity>();

	Entity result;

	public DIDL2Entity(String didlxml) throws DIDLSerializationException {
		Env env = new Env();
		DIDLDeserializerType deserializer = env.getDIDLDeSerializer();
		DIDLType didl = (DIDLType) deserializer.read(new ByteArrayInputStream(
				didlxml.getBytes()));
		didl.accept(this);
	}

	public Entity getEntity() {
		return result;
	}

	public void visitStart(final DIDLType didl) {
		stack.push(new Entity());
		stack.peek().setProperty(Entity.IDENTIFIER_ATT,
				didl.getDIDLDocumentId().toString());
		stack.peek().setProperty(Entity.FORMAT_ATT, DmtConstants.DIDL);
		stack.peek().setIsDataStream(false);
	}

	public void visitEnd(final DIDLType didl) {
		result = stack.pop();
	}

	public void visitStart(final ItemType item) {
		stack.push(stack.peek().addEntity(new Entity()));
		stack.peek().setIsDataStream(false);
		stack.peek().setProperty("item", "true");
	}

	public void visitEnd(final ItemType item) {
		if (stack.peek().getProperty("format") == null) {
			stack.peek().setProperty("format", DmtConstants.UNKNOWNFORMAT);
		}
		stack.pop();
	}

	public void visitStart(final DIDLInfoType didlInfo) {
	}

	public void visitEnd(final DIDLInfoType didlInfo) {
	}

	public void visitStart(final DescriptorType descriptor) {
	}

	public void visitEnd(final DescriptorType descriptor) {
	}

	public void visitStart(final ComponentType component) {
		stack.push(stack.peek().addEntity(new Entity()));
		stack.peek().setIsDataStream(true);
	}

	public void visitEnd(final ComponentType component) {
		if (stack.peek().getProperty("format") == null) {
			stack.peek().setProperty("format", DmtConstants.UNKNOWNFORMAT);
		}
		stack.pop();
	}

	public void visitStart(final StatementType statement) {
		if (statement.getContent() instanceof DII) {
			DII dii = (DII) statement.getContent();

			if ((dii.getType()) == DII.IDENTIFIER) {
				stack.peek().setProperty(Entity.IDENTIFIER_ATT,
						((DII) statement.getContent()).getValue());
			}
		} else if (statement.getContent() instanceof Diadm) {
			Diadm diadm = (Diadm) statement.getContent();
			for (DC dc : diadm.getDC()) {
				if (dc.getKey().equals(DC.Key.FORMAT)) {
					logger.debug("format:" + dc.getValue());
					stack.peek().setProperty(Entity.FORMAT_ATT, dc.getValue());
				}
				if (dc.getKey().equals(DC.Key.TYPE)) {
					logger.debug("semantic:" + dc.getValue());
					stack.peek()
							.setProperty(Entity.SEMANTIC_ATT, dc.getValue());
				}
			}
			for (DCTerms dcterm : diadm.getDCTerms()) {
				if (dcterm.getKey().equals(DCTerms.Key.IS_FORMAT_OF))
					stack.peek().setProperty("isFormatOf", dcterm.getValue());
			}

		} else if (statement.getContent() instanceof Premis) {
			Premis premis = (Premis) statement.getContent();
			if ((premis.getFormatKey() != null)
					&& (!"".equals(premis.getFormatKey())))
				stack.peek().setProperty(Entity.FORMAT_ATT,
						premis.getFormatKey());
		}
	}

	public void visitEnd(final StatementType statement) {
	}

	public void visitStart(final ResourceType resource) {
		if (resource.getRef() != null) {
			stack.peek().setProperty(Entity.REF_ATT,
					resource.getRef().toString());
		} else if (resource.getContent() instanceof ByteArray) {
			try {
				stack.peek().setContent(
						((ByteArray) resource.getContent()).getBytes());
			} catch (IOException ex) {
				logger.warn("serialization error of bytearray");
			}
		}

		if (stack.peek().getProperty(Entity.FORMAT_ATT) == null) { // fallback
																	// to
																	// mimetype
			if ("application/xml".equals(resource.getMimeType()))
				stack.peek().setProperty(Entity.FORMAT_ATT, DmtConstants.XML);
			else if ("application/pdf".equals(resource.getMimeType()))
				stack.peek().setProperty(Entity.FORMAT_ATT, DmtConstants.PDF);
			else if ("text/html".equals(resource.getMimeType()))
				stack.peek().setProperty(Entity.FORMAT_ATT, DmtConstants.HTML);
			else
				stack.peek().setProperty(Entity.FORMAT_ATT,
						DmtConstants.UNKNOWNFORMAT);
		}

	}

	public void visitEnd(final ResourceType resource) {
	}

}
