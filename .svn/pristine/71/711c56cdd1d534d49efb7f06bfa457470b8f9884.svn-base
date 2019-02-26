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

package gov.lanl.locator;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.log4j.Logger;

public class IdLocatorClient {
    
    static Logger log = Logger.getLogger(IdLocatorClient.class.getName());
    
    private String props;

    private MessageDigest algorithm;

    private int tablenum;

    private int srvnum;

    private boolean init = false;
    
    private static Vector<PoolingDataSource> dataSources;

    private BrokerUtils util;

    private Map map;

    private PoolingDataSource masterds;
    
    public IdLocatorClient() {}
    
    public IdLocatorClient(String propFile) {
        props = propFile;
    }
    
    public ArrayList<IdLocation> getLocations(String id) throws Exception {
        if (!init) 
            init();
        
        ArrayList<IdLocation> locations = new ArrayList<IdLocation>();
        ExecutorService es=null;
        try {
            long start = System.currentTimeMillis();
             es = Executors.newCachedThreadPool();
            ExecutorCompletionService<Vector> service = new ExecutorCompletionService<Vector>(es);
            
            byte[] digest = util.getDigest(id, algorithm);
            int num = util.getNumOfTable(tablenum, digest);
            List <Future<Vector>> futures = new ArrayList<Future<Vector>>(num);
            for (int i = 0; i < srvnum; i++) {
                futures.add(service.submit(new IdSearch(dataSources.get(i), digest, num)));
            }

           Vector dup = new Vector();
           //3L,TimeUnit.SECONDS // 
           
           try {
             for (int j = 0; j < srvnum; j++) {
                Future<Vector> fs = service.take();
                for (int k = 0; k < fs.get().size(); k++) {
                    dup.add(fs.get().get(k));
                }
              }
             }
           catch (Exception e){
               System.out.println("canceling threads");
                  e.printStackTrace();
              }
             finally  {
        	
        	 int n = 0;
        	 for (Future<Vector> f : futures)
        	 {
        	     if (!f.isDone()) n = n+1;	 
        	     f.cancel(true);
        	 }
                if (n>0) System.out.println(n+ " threads not done");
             }

            es.shutdown(); // no other tasks can be added to executor

            if (dup.size() == 0) {
                throw new IdNotFoundException("Unable to locate identifier: " + id);
            }
            
            dup.trimToSize();
            log.info("size:" + dup.size());

            // removing duplicates from array

            Integer[] a = new Integer[dup.size()];
            Integer[] deduped = util.dedup((Integer[]) dup.toArray(a));

            // mapping repoid to real urls and dates

            Map insertdates = util.getDates(masterds, deduped);
            String[] repourls = util.mapUrls(deduped, map, masterds);

            for (int i = 0; i < repourls.length; i++) {
                IdLocation loc = new IdLocation();
                loc.setId(id);
                loc.setRepo(repourls[i]);
                loc.setDate((String) insertdates.get(repourls[i]));
                locations.add(loc);
            }
            
            long end2 = System.currentTimeMillis();
            double elapsed2 = end2 - start;
            log.info("dist search time:" + elapsed2);
            
        } catch (Exception e) {
            log.error("from getIdLocation:" + e.toString());
            throw e;
        }
        finally {
            es.shutdownNow();
        }
        return locations;
    }
    
    public void init() throws Exception {
        try {
            org.apache.log4j.BasicConfigurator.configure();
            
            Properties conf = new Properties();
            if (new File(props).exists())
                conf.load(new FileInputStream(props));
            else
                conf.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(props));

            algorithm = MessageDigest.getInstance("MD5");
            tablenum = Integer.valueOf(conf.getProperty("tables")).intValue();
            String master = conf.getProperty("master");
            dataSources = new Vector<PoolingDataSource>();
            util = new BrokerUtils();
            Enumeration keys = conf.keys();

            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                if (key.startsWith("dbid")) {
                    String dbid = (String) conf.getProperty(key);
                    PoolingDataSource ds = util.configDataSource(dbid, conf);
                    dataSources.add(ds);
                }
            }

            srvnum = dataSources.size();
            masterds = util.configDataSource(master, conf);
            map = util.getRepoUrls(masterds);

            init = true;
        } catch (Exception ex) {
            log.error(" IdLocatorClient init failed:" + ex.getMessage());
        }
    }

    public boolean isInit() {
        return init;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
        this.init = false;
    }
    
    public static void main(String[] args) {
        IdLocatorClient client = new IdLocatorClient(args[0]);
        try {
            client.init();
            ArrayList<IdLocation> ids = new ArrayList<IdLocation>();
            
            StringTokenizer st = new StringTokenizer(args[1], ",");
            while (st.hasMoreTokens())
               ids.addAll(client.getLocations(st.nextToken()));

            for (IdLocation loc : ids) {
                System.out.println(loc.getId() + "," + loc.getRepo() + "," +  loc.getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
