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

package gov.lanl.format.registry.migrate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ORG.oclc.oai.harvester.verb.Header;

public class OAIRepoDiff {
    private ArrayList<String> repo1Ids = new ArrayList<String>();

    private ArrayList<String> repo2Ids = new ArrayList<String>();
    
    private HashMap<String, String> diff = new HashMap<String, String>();

    private URI repo1;

    private URI repo2;

    private String metadataPrefix = "oai_dc";
    
    public OAIRepoDiff(URI a, URI b, String format) throws Exception {
        repo1 = a;
        repo2 = b;
        metadataPrefix = format;
    }

    public void printReport() {
        for (String key : diff.keySet()) {
            System.out.println(key + " > " + diff.get(key));
        }
        if (diff.isEmpty())
            System.out.println("No changes.");
    }
    
    public void diff() {
        List lb = new ArrayList(repo1Ids);
        lb.retainAll(repo2Ids);
        String a = repo1.toString();
        for (String id : repo1Ids) {
            if (!lb.contains(id))
                diff.put(id, a);
        }
        String b = repo2.toString();
        for (String id : repo2Ids) {
            if (!lb.contains(id))
                diff.put(id, b);
        }
    }
    
    public void processIds() throws Exception {
        repo1Ids = getIds(repo1);
        repo2Ids = getIds(repo2);
    }
    
    public ArrayList<String> getIds(URI uri) throws Exception {
        if (uri.getScheme().equals("file"))
            return getFileIds(new File(uri));
        else if (uri.getScheme().equals("http"))
            return getOAIRepoIds(uri.toURL(), metadataPrefix);
        else
            throw new Exception("Unknown URI Scheme; file or http expected");
    }
    
    public static ArrayList<String> getFileIds(File file) throws Exception {
        ArrayList<String> ids = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0 && !line.startsWith("#")) {
                    String id = line.substring(0, line.indexOf("\t"));
                    ids.add(id);
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error processing flat file: ", ex);
        }
        return ids;
    }

    public static ArrayList<String> getOAIRepoIds(URL baseUrl, String format)
            throws Exception {
        ArrayList<String> ids = new ArrayList<String>();
        try {
            ORG.oclc.oai.harvester.verb.ListIdentifiers lr;
            lr = new ORG.oclc.oai.harvester.verb.ListIdentifiers(baseUrl, null,
                    null, null, format);

            for (Iterator it = lr.iterator(); it.hasNext();) {
                Header r = (Header) it.next();
                ids.add(r.getIdentifier());
            }

            while (lr.getResumptionToken() != null) {
                lr = new ORG.oclc.oai.harvester.verb.ListIdentifiers(baseUrl,
                        lr.getResumptionToken());
                for (Iterator it = lr.iterator(); it.hasNext();) {
                    Header rt = (Header) it.next();
                    ids.add(rt.getIdentifier());
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error obtaining ids from OAI repository: ", ex);
        }
        return ids;
    }
    
    public static void main(String[] args) {
        try {
            URI a = new URI(args[0]);
            URI b = new URI(args[1]);
            OAIRepoDiff diff = new OAIRepoDiff(a, b, "format");
            diff.processIds();
            diff.diff();
            diff.printReport();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
