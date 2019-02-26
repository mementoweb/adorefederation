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

import java.net.URL;

import org.jdom.input.SAXBuilder;

import ORG.oclc.oai.harvester.io.OAIReader;

/**
 * GenRegUpdate.java 
 * 
 * Generic update program for xmltape registry based on
 * xupdate and embeded xindice database
 */

public class GenRegUpdateBulk {

    public void Exec(String propfile, String harvurl, String metadataPrefix,
            String set, String filename) {

        RepoWriterConversion rWriter = null;

        try {
            SAXBuilder builder = new SAXBuilder();
            RepoWriter rw = new RepoWriter(propfile);
            ORG.oclc.oai.harvester.verb.Record record = null;

            ExistService xs = new ExistService();
            URL url = new URL(harvurl);
            OAIReader oaiReader = new OAIReader(url, null, null, (String) set,
                    metadataPrefix);

            while ((record = oaiReader.readNext()) != null) {
                String id = record.getIdentifier();
                String meta = record.getMetadata();
                String xupdate = null;

                xupdate = xs.readFileFromDisk(filename);
                String xml = xs.GetUpdated(id, meta, xupdate);
                int j = xml.indexOf(">");
                xml = xml.substring(j + 1);

                rw.updateRecord(id, xml, null);
            }
            xs.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        RepoWriterConversion rWriter = null;

        try {
            SAXBuilder builder = new SAXBuilder();
            RepoWriter rw = new RepoWriter(args[0]);
            ORG.oclc.oai.harvester.verb.Record record = null;

            String harvurl = args[1];
            String metadataPrefix = args[2];

            ExistService xs = new ExistService();
            URL url = new URL(harvurl);
            OAIReader oaiReader = new OAIReader(url, null, null, (String) null,
                    metadataPrefix);

            while ((record = oaiReader.readNext()) != null) {

                String id = record.getIdentifier();
                String meta = record.getMetadata();

                String filename = args[3];
                String xupdate = null;

                if (filename.equals("-")) {
                    xupdate = xs.readFileFromInput();
                } else {
                    xupdate = xs.readFileFromDisk(args[3]);
                }
                // System.out.println("xupdate:" + xupdate);

                String xml = xs.GetUpdated(id, meta, xupdate);
                int j = xml.indexOf(">");
                xml = xml.substring(j + 1);

                rw.updateRecord(id, xml, null);
            }
            xs.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
