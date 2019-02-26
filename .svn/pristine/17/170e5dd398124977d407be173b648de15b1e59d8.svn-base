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

package gov.lanl.repo.oaidb;

import gov.lanl.repo.oaidb.OAIPMHSets;

import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class GenericOAIDBSets extends OAIPMHSets {

    static Logger log = Logger.getLogger(GenericOAIDBSets.class.getName());
    
    public boolean checkSets(Iterator sets) throws Exception {
        while (sets.hasNext()) {
            String set = (String) sets.next();
            int i = set.indexOf(":");
            if (i < 1) {
                throw new Exception("set type is not supported by  repository");
            }
            String prefix = set.substring(0, i);
            log.debug("prefix:" + prefix);
            if (!prefix.equals("collection")) {
                throw new Exception("set type " + prefix
                        + "is not supported by  repository");
            }

            String rest = set.substring(i + 1);
            log.debug("rest:" + rest);
            rest.replace('*', '%');
            URLDecoder.decode(rest);
            if (!set.equals("collection:"
                    + URLEncoder.encode(rest).replace('%', '*'))) {
                throw new Exception(
                        "set encoding type is not supported by  repository");
            }

        }
        return true;
    }

    public Iterator getSets(String meta) throws Exception {
        StringReader xmlStream = new StringReader(meta);
        log.debug("meta:" + meta);
        SAXBuilder builder = new SAXBuilder(
                "org.apache.xerces.parsers.SAXParser");
        Document doc = builder.build(xmlStream);
        String ipath = "//dcterms:isPartOf";
        XPath xpath = XPath.newInstance(ipath);
        xpath.addNamespace("dcterms", "http://purl.org/dc/terms/");
        xpath.addNamespace("idx", "http://library.lanl.gov/2005-08/aDORe/RepoIndex/");
        ArrayList setSpecs = new ArrayList();
        List items = xpath.selectNodes(doc);

        if (items.size() > 0) {
            Iterator i = items.iterator();
            while (i.hasNext()) {
                Element item = (Element) i.next();
                setSpecs.add("collection:"
                        + URLEncoder.encode(item.getText()).replace('%', '*'));
                log.debug("fromSets:" + item.getText());
            }
        }
        return setSpecs.iterator();

    }
}
