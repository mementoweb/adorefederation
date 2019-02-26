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

package gov.lanl.util.xquery;

import gov.lanl.util.xml.XmlUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.xml.sax.XMLReader;

import nu.xom.Builder;
import nu.xom.Element;
import nu.xom.Nodes;
import nux.xom.pool.BuilderFactory;
import nux.xom.pool.BuilderPool;
import nux.xom.pool.PoolConfig;
import nux.xom.pool.XOMUtil;
import nux.xom.xquery.StreamingPathFilter;
import nux.xom.xquery.StreamingTransform;
import nux.xom.xquery.XQueryUtil;

public class XQueryProcessor {
    
    private static Map prefixes = new HashMap();
    private String tapePath;
    private String xQuery;
    private HashMap<String, Integer> results = new HashMap<String, Integer>(); 
    private StreamingTransform transform;
    private BuilderPool pool;
    
    public XQueryProcessor(XQueryProfile profile) {
        this(profile.getFilterPath(), profile.getXQuery(), profile.getNamespaceProfiles());
    }
    
    public XQueryProcessor(String tapePath, String xquery, HashMap nsPrefixes) {
        init(tapePath, xquery, nsPrefixes);
    }
    
    private void setDefaultNamespaces() {
        prefixes.put("didl", "urn:mpeg:mpeg21:2002:02-DIDL-NS");
        prefixes.put("xsd", "http://www.w3.org/2001/XMLSchema");
        prefixes.put("ta", "http://library.lanl.gov/2005-08/aDORe/XMLtape/");
    }
    
    private void setNamespaces(HashMap map) {
        setDefaultNamespaces();
        prefixes.putAll(map);
    }
    
    // Define Transorm Class
    public void init(String path, String xquery, HashMap nsPrefixes) {
        this.xQuery = xquery;
        this.tapePath = path;
        this.setNamespaces(nsPrefixes);
        final StreamingPathFilter filter = new StreamingPathFilter(tapePath, prefixes);
        PoolConfig poolConfig = new PoolConfig();
        pool = new BuilderPool(poolConfig, new BuilderFactory() {
            protected Builder newBuilder(XMLReader parser, boolean validate) {
                return new Builder(parser, validate, filter.createNodeFactory(null, new StreamingTransform() {
                    private Nodes NONE = new Nodes();
                    // execute XQuery against each element matching location path
                    public Nodes transform(Element subtree) {
                        Nodes results = XQueryUtil.xquery(subtree, xQuery);
                        addResults(results);
                        return NONE;
                    }}));
            }
          }
        );
    }

    public void processXMLtape(String xmltapePath) throws Exception {
        pool.getBuilder(false).build(getInputStream(new File(xmltapePath).getCanonicalFile()));
    }
    
    public InputStream getInputStream(File src) {
        int bufLen = 100 * 1024 * 1024;
        try {
            return new BufferedInputStream(new FileInputStream(src), bufLen);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    private void addResults(Nodes nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            String v = XmlUtil.decode(nodes.get(i).getValue());
            int c = (results.containsKey(v)) ? results.get(v).intValue() + 1 : 1;
            results.put(v, c);
        }
    }
    
    public HashMap<String, Integer>  getResults() {
        return results;
    }
    
    public Set<String> getResultsList() {
        TreeSet set = new TreeSet();
        set.addAll(results.keySet());
        return set;
    }
    
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(args[1]));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        XQueryProfile profile = XQueryProfileFactory.generateXQueryProfile(props);
        long s = System.currentTimeMillis();
        XQueryProcessor xqp = new XQueryProcessor(profile);
        try {
            xqp.processXMLtape(args[0]);
            HashMap<String, Integer> v = xqp.getResults();
            for (String key : v.keySet()) {
                System.out.println(key + " : " + v.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total Time:" + ((System.currentTimeMillis() - s) / 1000));
    }
}
