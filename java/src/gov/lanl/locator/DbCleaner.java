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

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.log4j.Logger;

public class DbCleaner {

    private static int tablenum;
    private BrokerUtils util;
    private PoolingDataSource masterds;
    private Properties conf;
    private ArrayList<String> activeDbInstances = new ArrayList<String>();
    static Logger log = Logger.getLogger(DbCleaner.class.getName());
    private final int ERROR = -1;
    private final int OK = 1;
    private final int NOTFOUND = 0;
    
    public DbCleaner(Properties conf) {
        this.conf = conf;
        try {
            tablenum = Integer.valueOf(conf.getProperty("tables")).intValue();
            String master = conf.getProperty("master");
            log.debug("master:" + master);
            util = new BrokerUtils();
            masterds = util.configDataSource(master, conf);
            verifyActiveDBIDs();
        } catch (Exception ex) {
            log.error("problem to init dbloader:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void verifyActiveDBIDs() {
        Enumeration keys = conf.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();

            if (key.startsWith("dbid")) {
                String dbid = (String) conf.getProperty(key);
                verifyActiveDBID(dbid);
            }
        }
    }

    private void verifyActiveDBID(String dbid) {
        try {
            Connection conn = masterds.getConnection();
            conn.setAutoCommit(false);
            String sql = "select count(*) from load_manager where dbid=\"" + dbid + "\"";
            log.debug(sql);
            Statement s = null;
            try {
                s = conn.createStatement();
                ResultSet result = s.executeQuery(sql);
                result.next();
                int tcount = result.getInt(1);
                if (tcount == 1) {
                    activeDbInstances.add(dbid);
                } else {
                    log.debug("dbid " + dbid + " is unknown or currently disabled, please check your master db load_manager.");
                }
                conn.setAutoCommit(true);
            } catch (SQLException e) {
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
            log.error("problem with verifyActiveDBID:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    } 
    
    private int getInternalRepoId(String repo_uri) throws SQLException {
        Connection conn = masterds.getConnection();
        String sql = "select repo_id from repo_id_map where repo_uri=\"" + repo_uri + "\"";
        log.debug(sql);
        Statement s = null;
        try {
            s = conn.createStatement();
            ResultSet result = s.executeQuery(sql);
            result.next();
            int id = result.getInt(1);
            if (id > 0) {
                return id;
            } else {
                log.debug("repo_uri " + repo_uri + " is not defined.");
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
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
        return 0;
    }
    
    private int removeIdentifiers(PoolingDataSource ds, String repo_uri) {
        int recordsDeleted = 0;
        try {
            // Get the internal repository id
            int repoId = getInternalRepoId(repo_uri);
            
            // Delete ids from resolver tables
            for (int i = 0; i < tablenum; i++) {
                String sql = "DELETE FROM resolver_" + i + " WHERE repo_id = " + repoId;
                log.debug(sql);
                recordsDeleted = recordsDeleted + executeUpdate(ds, sql);
            }
            
            // Delete repo_id from repo_id_map
            String sql = "DELETE FROM repo_id_map WHERE repo_id = " + repoId;
            log.debug(sql);
            executeUpdate(ds, sql);
            
        } catch (Exception ex) {
            log.error("problem with delete:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return recordsDeleted;
    }
    
    private void updateMasterDbCount(int recordsDeleted, String dbid) {
        // Update the master db cnt ref for this db instance
        String sql = "select dbcount from load_manager where dbid=\"" + dbid + "\"";
        Connection conn = null;
        Statement s = null;
        try {
            conn = masterds.getConnection();
            s = conn.createStatement();
            ResultSet result = s.executeQuery(sql);
            result.next();
            int id = result.getInt(1);
            int newCount = id - recordsDeleted;
            if (newCount > 0) {
                sql = "UPDATE load_manager SET dbcount = " + newCount + " where dbid=\"" + dbid + "\"";
                log.debug(sql);
                executeUpdate(masterds, sql);
            } else {
                log.debug("An error occurred");
            }
        } catch (SQLException e) {
            log.error("problem with delete:" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (s != null)
                    s.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                    log.error("problem with delete:" + e.getMessage());
                    throw new RuntimeException(e);
            }
        }
    }
    
    private int executeUpdate(PoolingDataSource ds, String sql) throws SQLException {
        Connection conn = null;
        Statement s = null;
        try {
            conn = ds.getConnection();
            s = conn.createStatement();
            return s.executeUpdate(sql);
        } catch (SQLException e) {
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
        return 0;
    }
    
    private boolean executeSelect(PoolingDataSource ds, String sql) throws SQLException {
        Connection conn = null;
        Statement s = null;
        try {
            conn = ds.getConnection();
            s = conn.createStatement();
            return s.execute(sql);
        } catch (SQLException e) {
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
        return true;
    }
    
    public int removeIdentifiers(String repo_uri) {
        int recordsDeleted = 0;
        PoolingDataSource pds = null;
        for (String dbid : activeDbInstances) {
            pds = util.configDataSource(dbid, conf);
            recordsDeleted = recordsDeleted + removeIdentifiers(pds, repo_uri);
            updateMasterDbCount(recordsDeleted, dbid);
        }
        return recordsDeleted;
    }   
    
    private int checkStatus(PoolingDataSource ds, String repo_uri) {
        int status = NOTFOUND;
        try {
            // Get the internal repository id
            int repoId = getInternalRepoId(repo_uri);
            
            if (repoId != 0) {
                // Check state of repo_uri
                String sql = "select repo_uri from repo_id_map where tcount is null and repo_uri=\"" + repo_uri + "\"";
                log.debug(sql);
                if (!executeSelect(ds, sql))
                   status = ERROR;
                else
                   status = OK;
            }
        } catch (Exception ex) {
            log.error("problem checking status of repo_uri:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return status;
    }
    
    public boolean isValid(String repo_uri) {
        PoolingDataSource pds = null;
        int status;
        for (String dbid : activeDbInstances) {
            pds = util.configDataSource(dbid, conf);
            status = checkStatus(pds, repo_uri);
            if (status == OK)
                return true;
        }
        return false;
    } 
    
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("usage: gov.lanl.locator.DbCleaner <configFile> <repoid>");
            System.exit(0);
        }
        
        Properties conf = new Properties();
        conf.load(new FileInputStream(args[0]));
        DbCleaner cleaner = new DbCleaner(conf);
        String repouri = args[1];
        if (!repouri.contains("xmltape") && !repouri.contains("arc")) {
            System.out.println("Unrecognized format; xmltape or arc expected.");
            System.exit(1);
        }
        int recordsDeleted = cleaner.removeIdentifiers(repouri);
        System.out.println("Records Deleted: " + recordsDeleted);
    }
}
