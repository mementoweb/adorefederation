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

package gov.lanl.ockham.client.adore;

import gov.lanl.ockham.ServiceRegistryConstants;
import gov.lanl.ockham.client.app.Registry;
import gov.lanl.ockham.client.app.UnrecognizedIdentifierException;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.registryclient.parser.SerializationException;
import gov.lanl.util.properties.PropertiesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * Dummy add client for adore environment, in which given an identifier, other
 * registration inofrmation can be automatically generated. This is an safer
 * operation than client must generate the whole XML fragment.
 * 
 * This class mirrors functionality of previous repository index.
 * 
 */
public class Add {
    public static final String PREFIX_ADORE_XMLTAPE_RESOLVERURL = "adore-xmltape.ResolverURL";
    public static final String PREFIX_ADORE_XMLTAPE_ACCESSORURL = "adore-xmltape.AccessorURL";
    public static final String PREFIX_ADORE_ARCFILE_RESOLVERURL = "adore-arcfile.ResolverURL";
    public static final String PREFIX_ADORE_XMLTAPE_XQUERY_RESOLVERURL = "adore-xmltape-xquery.ResolverURL";
    
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger log = Logger.getLogger(Add.class.getName());
    private String oaipmhBaseurl = null;
    private String openurlBaseurl = null;
    private String xqueryBaseurl = null;
    private Registry registry = null;
    private Properties additionalMetadata = null;
    
    private TreeSet<String> itemFormats;
    private TreeSet<String> itemTypes;
    private TreeSet<String> subjects;
    //MOD rtfteam@lanl.gov 20070619 -- added via the properties profile during processing
    private String inlineTemporal = null;
    
    public Add(URL baseurl, String oaipmhBaseurl, String openurlBaseurl) {
        this(baseurl, oaipmhBaseurl, openurlBaseurl, null);
    }
    
    public Add(URL baseurl, String oaipmhBaseurl, String openurlBaseurl, String xqueryBaseurl) {
        registry = new Registry(baseurl);
        this.oaipmhBaseurl = oaipmhBaseurl;
        this.openurlBaseurl = openurlBaseurl;
        this.xqueryBaseurl = xqueryBaseurl;
    }
    
    public boolean put(String collection, 
                       String identifier, 
                       String created,
                       String extent, 
                       String associations)
            throws UnrecognizedIdentifierException, IOException,
            MalformedURLException, SerializationException {

        // Process Associations
        String[] a = (associations != null) ? associations.split(",") : new String[0];
        ArrayList<String> al = new ArrayList<String>();
        for (int i = 0; i < a.length; i++)
            al.add(a[i]);
        
        // Process Extent (total # of records)
        int count = 0;
        try {
            count = Integer.parseInt(extent);
        } catch (NumberFormatException e) {
            // Not an int, use 0 as default
        }
        
        // Process Date
        Date d = null;
        if (created != null)
            try {
                d = formatter.parse(created);
            } catch (ParseException e) {
                throw new SerializationException("Incorrect date format; yyyy-MM-dd expected");
            }
        
        return put(collection, identifier, d, count, al);
    }
    
    public boolean put(String collection, 
                       String identifier, 
                       Date created, 
                       int extent,
                       ArrayList<String> associations)
            throws UnrecognizedIdentifierException, IOException,
            MalformedURLException, SerializationException {
        String[] fragments = identifier.split("/");
        if ("xmltape".equals(Util.getRepositoryType(identifier))) {
            IESRCollection coll = new IESRCollection();
            coll.setIdentifier(identifier);
            coll.addIsPartOf(collection);
            coll.setTitle(identifier);
            coll.addType(Util.getRepositoryType(identifier));
            coll.setContentRange(formatter.format(created));
            
            // Additional Metadata
            if (itemFormats != null && !itemFormats.isEmpty())
                coll.setItemFormats(itemFormats);
            else
                coll.addItemFormat("application/xml");
            
            if (itemTypes != null && !itemTypes.isEmpty())
                coll.setItemTypes(itemTypes);
            
            if (subjects != null && !subjects.isEmpty())
                coll.setSubjects(subjects);

            //MOD  rtfteam@lanl.gov 20070619 -- added via the properties profile during processing
            if (inlineTemporal != null )
                coll.setTemporalRange(inlineTemporal);
            
            if (extent > 0)
                coll.setExtent(extent);
            
            if (associations != null && !associations.isEmpty())
                coll.setAssociations(new TreeSet<String>(associations));
            
            if (additionalMetadata != null) {
                Set<String> v;
                v = PropertiesUtil.getPropertiesList(additionalMetadata, "usesControlledList");
                if (v.size() > 0) { coll.setVocabularies(v);}
                v = PropertiesUtil.getPropertiesList(additionalMetadata, "temporal");
                if (v.size() > 0) { coll.setTemporalRange((String)v.toArray()[0]);}
            }
            
            coll.addService(Util.getOAIPMHServiceId(identifier));
            coll.addService(Util.getOpenURLaDORe1ServiceId(identifier));
            coll.addService(Util.getOpenURLaDORe2ServiceId(identifier));
            coll.addService(Util.getOpenURLaDORe3ServiceId(identifier));
            if (xqueryBaseurl != null)
                coll.addService(Util.getOpenURLaDORe7ServiceId(identifier));
            
            IESRService oaisvr = new IESRService();
            oaisvr.setIdentifier(Util.getOAIPMHServiceId(identifier));
            oaisvr.setTitle("OAI-PMH service for xmltape " + fragments[2]);
            String oai1 = oaipmhBaseurl.substring(0, oaipmhBaseurl.lastIndexOf("/"));
            String oai2 = oaipmhBaseurl.substring(oaipmhBaseurl.lastIndexOf("/") + 1);
            oaisvr.setLocator(oai1 + "/" + fragments[2] + "/" + oai2);
            oaisvr.setType("oai-pmh");
            oaisvr.setServes(identifier);
            oaisvr.setSupportsStandard("oai-pmh-2_0");

            IESRService adore1 = new IESRService();
            adore1.setIdentifier(Util.getOpenURLaDORe1ServiceId(identifier));
            adore1.setTitle("openurl-aDORe1 service for xmltape " + fragments[2]);
            adore1.setLocator(openurlBaseurl + "/" + fragments[2] + "/openurl-aDORe1");
            adore1.setSupportsStandard("openurl-aDORe1");
            adore1.setServes(identifier);
            adore1.setType("openurl");
            
            IESRService adore2 = new IESRService();
            adore2.setIdentifier(Util.getOpenURLaDORe2ServiceId(identifier));
            adore2.setTitle("openurl-aDORe2 service for xmltape " + fragments[2]);
            adore2.setLocator(openurlBaseurl + "/" + fragments[2] + "/openurl-aDORe2");
            adore2.setSupportsStandard("openurl-aDORe2");
            adore2.setServes(identifier);
            adore2.setType("openurl");
            
            IESRService adore3 = new IESRService();
            adore3.setIdentifier(Util.getOpenURLaDORe3ServiceId(identifier));
            adore3.setTitle("openurl-aDORe3 service for xmltape " + fragments[2]);
            adore3.setLocator(openurlBaseurl + "/" + fragments[2] + "/openurl-aDORe3");
            adore3.setSupportsStandard("openurl-aDORe3");
            adore3.setServes(identifier);
            adore3.setType("openurl");
            
            IESRService adore7 = null;
            if (xqueryBaseurl != null) {
                adore7 = new IESRService();
                adore7.setIdentifier(Util.getOpenURLaDORe7ServiceId(identifier));
                adore7.setTitle("openurl-aDORe7 service for xmltape " + fragments[2]);
                adore7.setLocator(xqueryBaseurl + "/" + fragments[2] + "/openurl-aDORe7");
                adore7.setSupportsStandard("openurl-aDORe7");
                adore7.setServes(identifier);
                adore7.setType("openurl");
            }
            
            return (registry.put(coll) && registry.put(oaisvr) && registry.put(adore1) && registry.put(adore2) && registry.put(adore3) && ((adore7 == null) ? true : registry.put(adore7)));
        } else if ("arc".equals(Util.getRepositoryType(identifier))) {
            IESRCollection coll = new IESRCollection();
            coll.setIdentifier(identifier);
            coll.addIsPartOf(collection);
            coll.addType("arc");
            coll.setTitle("arc file " + identifier);
            coll.setContentRange(formatter.format(created));
            coll.addService(Util.getOpenURLaDORe3ServiceId(identifier));
            coll.addService(Util.getOpenURLaDORe4ServiceId(identifier));
            
            // Additional Metadata
            if (extent > 0)
                coll.setExtent(extent);
            
            IESRService adore3 = new IESRService();
            adore3.setIdentifier(Util.getOpenURLaDORe3ServiceId(identifier));
            adore3.setTitle("openurl-aDORe3 service for arc file" + fragments[2]);
            adore3.setLocator(openurlBaseurl + "/" + fragments[2] + "/openurl-aDORe3");
            adore3.setSupportsStandard("openurl-aDORe3");
            adore3.setType("openurl");
            adore3.setServes(identifier);
            
            IESRService adore4 = new IESRService();
            adore4.setIdentifier(Util.getOpenURLaDORe4ServiceId(identifier));
            adore4.setTitle("openurl-aDORe4 service for arc file" + fragments[2]);
            adore4.setLocator(openurlBaseurl + "/" + fragments[2] + "/openurl-aDORe4");
            adore4.setSupportsStandard("openurl-aDORe4");
            adore4.setType("openurl");
            adore4.setServes(identifier);
            
            return (registry.put(coll) && registry.put(adore3) && registry.put(adore4));
        } else {
            throw new UnrecognizedIdentifierException(
                    " not xmltape or arc file");
        }
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("usage: java gov.lanl.ockham.put.client.adore.Add <collection> <identifier> <created> [extent] [associations]");
            System.exit(0);
        }

        String identifier = args[1];
        String collection = args[0];
        String created = args[2];
        String extent = (args.length > 3) ? args[3] : null;
        String associations = (args.length > 4) ? args[4] : null;
        
        String oaiUrl = null;
        String resolverUrl = null;
        String xqueryBaseurl = null;
        if ("xmltape".equals(Util.getRepositoryType(identifier))) {
            oaiUrl = PropertiesUtil.getGlobalProperty(PREFIX_ADORE_XMLTAPE_ACCESSORURL);
            resolverUrl = PropertiesUtil.getGlobalProperty(PREFIX_ADORE_XMLTAPE_RESOLVERURL);
            xqueryBaseurl = PropertiesUtil.getGlobalProperty(PREFIX_ADORE_XMLTAPE_XQUERY_RESOLVERURL);
        } else if ("arc".equals(Util.getRepositoryType(identifier))) {
            resolverUrl = PropertiesUtil.getGlobalProperty(PREFIX_ADORE_ARCFILE_RESOLVERURL);
        } else {
            System.err.println("Unable to resolve repository type from identifier: " + identifier);
            System.exit(1);
        }
                
        Add add = new Add(new URL(PropertiesUtil.getGlobalProperty(ServiceRegistryConstants.PUT_BASEURL)), oaiUrl, resolverUrl, xqueryBaseurl);
        
        boolean result = add.put(collection, identifier, created, extent, associations);
        if (result) {
            System.err.println(args[1] + " added");
        } else {
            System.err.println("error in process " + args[1]);
            System.exit(1);
        }
    }

    public Properties getAdditionalMetadata() {
        return additionalMetadata;
    }
    
    public void setAdditionalMetadata(File propsFile) throws FileNotFoundException, IOException {
        this.additionalMetadata = new Properties();
        this.additionalMetadata.load(new FileInputStream(propsFile));
    }
    
    public void setAdditionalMetadata(Properties additionalMetadata) {
        this.additionalMetadata = additionalMetadata;
    }

    public TreeSet<String> getItemFormats() {
        return itemFormats;
    }

    //MOD rtfteam@lanl.gov 20070619 -- added via the properties profile during processing
    public void setInlineTemporal(String inlineTemporal) {
       this.inlineTemporal = inlineTemporal;
    }
    public String getInlineTemporal() {
        return inlineTemporal;
    }
    //end mod
    public void setItemFormats(TreeSet<String> itemFormats) {
        this.itemFormats = itemFormats;
    }

    public TreeSet<String> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(TreeSet<String> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public TreeSet<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(TreeSet<String> subjects) {
        this.subjects = subjects;
    }
}
