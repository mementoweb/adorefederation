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

package gov.lanl.repo;

import org.apache.log4j.Logger;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;

import java.io.*;

/**
 * ExistService.java
 * 
 * Creating embedded exist xml database
 * 
 * @author ludab 
 */

public class ExistService {

	static Logger log = Logger.getLogger(ExistService.class.getName());
	
    Collection col = null;

    public ExistService() {
        try {

            String driver = "org.exist.xmldb.DatabaseImpl";
            Class c = Class.forName(driver);
            Database database = (Database) c.newInstance();
            DatabaseManager.registerDatabase(database);
            database.setProperty("create-database", "true");
            Collection root = DatabaseManager.getCollection("xmldb:exist:///db");
            CollectionManagementService mgtService = (CollectionManagementService) root.getService("CollectionManagementService", "1.0");
            col = mgtService.createCollection("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (col != null)
                col.close();
        } catch (Exception e) {
            System.err.println("Can not close collection");
        }

    }

    public String readFileFromDisk(String fileName) throws Exception {
        File file = new File(fileName);
        FileInputStream insr = new FileInputStream(file);

        byte[] fileBuffer = new byte[(int) file.length()];

        insr.read(fileBuffer);
        insr.close();
        return new String(fileBuffer);
    }

    public String readFileFromInput() throws Exception {
        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;
        StringBuffer b = new StringBuffer();
        while ((inputLine = is.readLine()) != null) {
            b.append(inputLine);
        }
        is.close();
        return b.toString();
    }

    public String GetUpdated(String id, String data, String xupdate) {
        //Collection col = null;
        XMLResource udocument = null;
        String xml = null;
        try {

            XMLResource document = (XMLResource) col.createResource(id,
                    "XMLResource");
            document.setContent(data);
            col.storeResource(document);
            XUpdateQueryService service = (XUpdateQueryService) col.getService(
                    "XUpdateQueryService", "1.0");

            service.updateResource(id, xupdate);
            udocument = (XMLResource) col.getResource(id);

            if (udocument != null) {
                xml = (String) udocument.getContent();
            }

            col.removeResource(udocument);

        } catch (Exception e) {
            e.printStackTrace();

        }
        //finally {
        //try {
        //if (col != null) col.close();
        //}
        //catch (Exception e) { System.err.println("Can not close
        // collection");}

        //}
        return xml;
    }

    public void Load(String id, String data)    {
        try {
            XMLResource document = (XMLResource) col.createResource(id,
                    "XMLResource");
            document.setContent(data);
            col.storeResource(document);
        } catch (Exception e) {
            e.printStackTrace();

       }

    }

    public static void main(String[] args) throws Exception {

        Collection mycol = null;
        try {

            String driver = "org.exist.xmldb.DatabaseImpl";
            Class c = Class.forName(driver);

            Database database = (Database) c.newInstance();
            DatabaseManager.registerDatabase(database);
            database.setProperty("create-database", "true");
            Collection root = DatabaseManager
                    .getCollection("xmldb:exist:///db");
            CollectionManagementService mgtService = (CollectionManagementService) root.getService("CollectionManagementService", "1.0");
            mycol = mgtService.createCollection("test");

            ExistService xs = new ExistService();
            String data = xs.readFileFromDisk(args[0]);
            String id = "23";

            log.debug("data:" + data);
            XMLResource document = (XMLResource) mycol.createResource(id,"XMLResource");
            document.setContent(data);
            mycol.storeResource(document);
            log.debug("Document " + args[0] + " inserted");

            String xupdate = "<xu:modifications version=\"1.0\""
                    + "      xmlns:xu=\"http://www.xmldb.org/xupdate\""
                    + "    xmlns:dc=\"http://purl.org/dc/elements/1.1/\""
                    + " xmlns:pr=\"" + RepoProperties.PUT_RECORD_NS
                    + "\"" + "  xmlns:tap=\""
                    + RepoProperties.TAG_REGISTRY_XMLTAPE + "\">"
                    + "   <xu:update select=\"/tap:registry/tap:file\">"
                    + "/lanl/repository/my.properties" + "</xu:update>"
                    + "</xu:modifications>";
            log.debug("xupdate:" + xupdate);

            XUpdateQueryService service = (XUpdateQueryService) mycol.getService("XUpdateQueryService", "1.0");
            service.updateResource(id, xupdate);

            // retrive it back

            XMLResource udocument = (XMLResource) mycol.getResource(id);
            if (udocument != null) {
            	log.debug("Document " + id);
                log.debug(udocument.getContent());
            } else {
            	log.debug("Document not found");
            }
        } catch (XMLDBException e) {
            e.printStackTrace();

            System.err.println("XML:DB Exception occurred " + e.errorCode);
        } finally {
            if (mycol != null) {
                mycol.close();
            }
        }
    }
}
