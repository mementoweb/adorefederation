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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.log4j.Logger;

public class DbLoader {
    
    private static final String DEFAULT_RFRID = "info:sid/library.lanl.gov";
    
    private MessageDigest algorithm;

    private static int tablenum;

    private BrokerUtils util;

    private static Map map;

    private static int MAX_NUMBER_OF_ROWS = 90000000;

    private PoolingDataSource masterds;

    private Properties conf;

    private String tmpdir;
    
    private String rfrid = DEFAULT_RFRID;

    static Logger log = Logger.getLogger(DbLoader.class.getName());

    public DbLoader(Properties conf) {
        this.conf = conf;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            tablenum = Integer.valueOf(conf.getProperty("tables")).intValue();
            rfrid = conf.getProperty("rfrid", rfrid);
            tmpdir = conf.getProperty("tmpdir");
            if (tmpdir == null || tmpdir.equals(""))
                tmpdir = System.getProperty("java.io.tmpdir");
            String master = conf.getProperty("master");
            log.debug("master:" + master);
            util = new BrokerUtils();
            masterds = util.configDataSource(master, conf);
            syncLoadManager();
            map = util.getRepoIds(masterds);
        } catch (Exception ex) {
            log.error("problem to init dbloader:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void syncLoadManager() {
        Enumeration keys = conf.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();

            if (key.startsWith("dbid")) {
                String dbid = (String) conf.getProperty(key);
                updateLoadManager(dbid);
            }
        }
    }

    private void updateLoadManager(String dbid) {
        try {
            Connection conn = masterds.getConnection();
            conn.setAutoCommit(false);
            String sql = "select count(*) from load_manager where dbid=\""
                    + dbid + "\"";

            Statement s = null;
            try {
                s = conn.createStatement();
                ResultSet result = s.executeQuery(sql);
                result.next();
                int tcount = result.getInt(1);
                if (tcount == 0) {
                    String isql = "insert into load_manager(dbid,dbcount,status)"
                            + " values(\"" + dbid + "\",0,\"A\")";
                    s.executeUpdate(isql);
                    conn.commit();
                }
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                log.error("problem to updateLoadManager:" + e.getMessage());
            } finally {
                try {
                    if (s != null)
                        s.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        } catch (Exception ex) {
            log.error("problem with load_manager:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private String getActiveDb(int fcount) {
        String dbid = null;
        try {
            Connection conn = masterds.getConnection();
            conn.setAutoCommit(false);
            String sql = "select dbid,dbcount from load_manager where status=\"A\" order by dbid";
            Statement s = null;
            try {
                s = conn.createStatement();
                ResultSet result = s.executeQuery(sql);
                result.next();
                dbid = result.getString(1);
                int tcount = result.getInt(2);
                int total = fcount + tcount;
                String updatesql;
                if (tcount + fcount >= MAX_NUMBER_OF_ROWS) {
                    updatesql = "update load_manager set dbcount=" + total
                            + ",status=\"F\"  where dbid=\"" + dbid + "\"";
                } else {
                    updatesql = "update load_manager set dbcount=" + total
                            + " where dbid=\"" + dbid + "\"";
                }
                s.executeUpdate(updatesql);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                log.error("problem to getActiveDb:" + e.getMessage());
            } finally {
                try {
                    if (s != null)
                        s.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            log.error("Problem in active db determination" + e.getMessage());
            throw new RuntimeException(e);
        }

        return dbid;
    }

    private int getLinesCount(File file) {
        int counter = 0;
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(file));
            while ((inFile.readLine()) != null) {
                counter++;
            }
            inFile.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return counter;
    }

    private synchronized void updateMapTableOnStart(Integer repoid) {
        try {
            Connection conn = masterds.getConnection();
            Statement ss = null;
            try {
                ss = conn.createStatement();
                String sql = "update repo_id_map set sdate=now()  where repo_id=" + repoid;
                ss.executeUpdate(sql);
            } catch (SQLException e) {
                log.error("problem to updateMapTableOnStart:" + e.getMessage());
            } finally {
                try {
                    if (ss != null)
                        ss.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        } catch (Exception ex) {
            log.error("problem with sdate update:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private synchronized void updateMapTableOnFinish(int count, Integer repoid) {
        try {
            Connection conn = masterds.getConnection();
            Statement ss = null;
            try {
                ss = conn.createStatement();
                String sql = "update repo_id_map set fdate=now(),tcount="
                        + count + "  where repo_id=" + repoid;
                ss.executeUpdate(sql);
            } catch (SQLException e) {
                log.error("problem to updateMapTableOnFinish:" + e.getMessage());
            } finally {
                try {
                    if (ss != null)
                        ss.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        } catch (Exception ex) {
            log.error("problem with fdate update:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void moveFile(File f, String sdir) {
        try {
            File dir = new File(sdir);
            dir.mkdirs();
            f.renameTo(new File(dir, f.getName()));
        } catch (Exception ex) {
            log.error("problem to move file to storage:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void loadFile(File file, String repouri, PoolingDataSource ds, int count) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            Integer repoid = mapRepoId(repouri);
            updateMapTableOnStart(repoid);
            String str;
            while ((str = in.readLine()) != null) {
                load(ds, repoid, str);
            }
            updateMapTableOnFinish(count, repoid);
        } catch (Exception ex) {
            log.error("problem with loadFile:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private Integer mapRepoId(String repouri) {
        Integer repoid;
        if (map.containsKey(repouri)) {
            repoid = (Integer) map.get(repouri);
        } else {
            repoid = util.getRepoId(masterds, repouri);
            map.put(repouri, repoid);
        }
        return repoid;
    }
    
    private void load(PoolingDataSource ds, Integer repoid, String id) {
        try {
            byte[] digest = util.getDigest(id, algorithm);
            int num = util.getNumOfTable(tablenum, digest);

            String sql = "insert into resolver_" + num
                    + "(digest,repo_id)  values(?,?)";

            Connection conn = ds.getConnection();
            PreparedStatement pStmt = null;
            try {
                pStmt = conn.prepareStatement(sql);
                pStmt.setBytes(1, digest);
                pStmt.setInt(2, repoid);
                pStmt.executeUpdate();
            } catch (SQLException e) {
                log.error("problem to load:" + e.getMessage());
            } finally {
                try {
                    if (pStmt != null)
                        pStmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        } catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
            //System.out.println("Digest: " + digest);
            System.out.println("RepoId: " + repoid);
            outOfBoundsEx.printStackTrace();
        } catch (Exception ex) {
            log.error("problem with insert:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    public File downloadFile(String baseurl, String repouri) {
        String e_repouri = null;
        try {
            e_repouri = URLEncoder.encode(repouri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
           log.error("UnsupportedEncodingException resulted attempting to encode " + repouri + " or " + rfrid);
        }
        
        String openurl = baseurl 
                         + "?rft_id=" + e_repouri
                         + "&svc_id=info%3Alanl-repo%2Fsvc%2Fidentifiers.list"
                         + "&url_ver=Z39.88-2004";

        log.info("Obtaining Identifiers from: " + openurl);
        
        if (!new File(tmpdir).exists())
            new File(tmpdir).mkdir();
        
        File file = new File(tmpdir, repouri.substring(repouri.lastIndexOf("/")));
        
        // Ensure clean-up
        file.deleteOnExit();
        
        try {
            FileWriter wr = new FileWriter(file);
            URL url = new URL(openurl);
            //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            //String line;
            //while ((line = in.readLine()) != null) {
            //    wr.write(line);
            //    wr.write("\n");
            //}
            //in.close();
            writeUrlToFile(url, wr);
            wr.close();
        } catch (Exception ex) {
            log.error("problem with openurl:" + openurl + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return file;
    }

    public static InputStream getInputStream(URL location) throws Exception {
        InputStream in;
        try {
            HttpURLConnection huc = (HttpURLConnection) (location.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                in = huc.getInputStream();
            } else
                throw new Exception("Cannot get " + location.toString());
        } catch (MalformedURLException e) {
            throw new Exception("A MalformedURLException occurred for " + location.toString());
        } catch (IOException e) {
            throw new Exception("An IOException occurred attempting to connect to " + location.toString());
        }
        return in;
    }
    
    public static void writeUrlToFile(URL src, FileWriter wr) throws Exception {
        InputStream in = null;
        byte[] buf = null;
        int bufLen = 20000 * 1024;
        try {
            in = new BufferedInputStream(getInputStream(src));
            buf = new byte[bufLen];
            byte[] tmp = null;
            int len = 0;
            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                wr.write(new String(tmp));
                wr.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception e) {
                }
        }
    }
    
    public void processFile(File file, String repouri) {
        int fcount = getLinesCount(file);
        String dbid = getActiveDb(fcount);
        loadFile(file, repouri, util.configDataSource(dbid, conf), fcount);
        moveFile(file, getStorageDir(dbid));
    }
    
    public String getStorageDir(String dbid) {
        return conf.getProperty(dbid + ".sdir");
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("usage: gov.lanl.locator.DbLoader <configFile> <baseurl> <repoid>");
            System.exit(0);
        }
        
        Properties conf = new Properties();
        conf.load(new FileInputStream(args[0]));
        DbLoader loader = new DbLoader(conf);
        String service = null;
        String repouri = args[2];
        String repoid = repouri.substring(repouri.lastIndexOf("/") + 1);
        if (repouri.contains("xmltape") || repouri.contains("arc"))
            service = args[1] + "/" + repoid + "/openurl-aDORe3";
        else {
            System.out.println("Unrecognized format; xmltape or arc expected.");
            System.exit(1);
        }
        
        File file = loader.downloadFile(service, repouri);
        loader.processFile(file, repouri);
    }
}
