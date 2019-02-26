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

package gov.lanl.repo.oaidb.tapereg;

import gov.lanl.repo.RepoProperties;
import gov.lanl.repo.oaidb.OAIPMHId;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class RepoTapeId extends OAIPMHId {

    static Logger log = Logger.getLogger(RepoTapeId.class.getName());
    
    public boolean checkId(String id) throws Exception {

        return true;
    }

    public String getId(String meta) throws Exception {
        String id = null;
        try {

            StringReader xmlStream = new StringReader(meta);
            SAXBuilder builder = new SAXBuilder(
                    "org.apache.xerces.parsers.SAXParser");
            Document doc = builder.build(xmlStream);
            String ipath = null;
            String rootNS = doc.getRootElement().getNamespace().getURI();
            log.debug("Root NS: " + rootNS);
            if (rootNS == RepoProperties.TAPE_REG_NS)
                ipath = "//tb:XMLtapeId";
            else
                throw new Exception("**** Expected " + RepoProperties.TAPE_REG_NS + ", but " + rootNS + " is referenced");
            
            log.debug("meta in side:" + meta);
            XPath xpath = XPath.newInstance(ipath);
            xpath.addNamespace("ta",    RepoProperties.TAPE_ADMIN_NS);
            xpath.addNamespace("tb", RepoProperties.TAPE_BASCIS_NS);

            List items = xpath.selectNodes(doc);

            log.debug("more - items:" + items.size());
            if (items.size() > 0) {
                Iterator i = items.iterator();
                while (i.hasNext()) {
                    Element item = (Element) i.next();
                    id = item.getText();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return id;
    }
}
