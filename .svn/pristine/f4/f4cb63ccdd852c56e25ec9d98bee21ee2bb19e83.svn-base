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

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

public class BrokerUtils {

    static Logger log = Logger.getLogger(BrokerUtils.class.getName());
    
    public synchronized byte[] getDigest(String aKey, MessageDigest algorithm)
            throws java.io.UnsupportedEncodingException {
        byte[] key = aKey.getBytes("UTF-8");
        algorithm.reset();
        algorithm.update(key);
        byte[] digest = algorithm.digest();
        return digest;

    }

    public int getNumOfTable(int tablenum, byte[] digest) {
        return Math.abs(readInt(digest, digest.length - 4)) % tablenum;
    }

    public static final int readInt(byte buf[], int offset) {
        return buf[offset] << 24 | (0x00ff & buf[offset + 1]) << 16
                | (0x00ff & buf[offset + 2]) << 8 | (0x00ff & buf[offset + 3]);
    }

    public Integer[] dedup(Integer[] a) {
        Arrays.sort(a);
        int n = a.length;
        int i, k;
        k = 0;
        for (i = 1; i < n; i++) {
            if (!a[k].equals(a[i])) {
                a[k + 1] = a[i];
                k++;
            }
        }

        Integer[] deduped = new Integer[k + 1];
        for (int j = 0; j < k + 1; j++) {
            deduped[j] = a[j];
        }
        return deduped;
    }

    public PoolingDataSource configDataSource(String dbid, Properties conf) {
        String url = conf.getProperty(dbid + ".url");
        String driver = conf.getProperty(dbid + ".driver");
        String login = conf.getProperty(dbid + ".login");
        String pwd = conf.getProperty(dbid + ".pwd");
        log.debug(url + ";" + driver + ";" + login + ";" + pwd);
        PoolingDataSource ds = setupDataSource(url, driver, login, pwd);
        return ds;
    }

    public static PoolingDataSource setupDataSource(String connectURI, String jdbcDriverName,
			String username, String password) {
		// Ensure the driver is loaded
		try {
			java.lang.Class.forName(jdbcDriverName).newInstance();
		} catch (Exception e) {
			log.error(
					"Error when attempting to obtain DB Driver: "
							+ jdbcDriverName + " on "
							+ new Date().toString(), e);
		}

		// First, we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		//
		//GenericObjectPool connectionPool = new GenericObjectPool(null);
		//connectionPool.setMinIdle(30);
        //connectionPool.setMaxActive(70);
		GenericObjectPool connectionPool = new GenericObjectPool(
                null, // PoolableObjectFactory, can be null
                50, // max active
                GenericObjectPool.WHEN_EXHAUSTED_BLOCK, // action when exhausted
                3000, // max wait (milli seconds)
                10, // max idle
                false, // test on borrow
                false, // test on return
                60000, // time between eviction runs (millis)
                5, // number to test on eviction run
                30000, // min evictable idle time (millis)
                true // test while idle
                );
		
		// ConnectionManager._pool = connectionPool;
		// we keep it for two reasons
		// #1 We need it for statistics/debugging
		// #2 PoolingDataSource does not have getPool()
		// method, for some obscure, weird reason.

		//
		// Next, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string from configuration
		//
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, username, password);

		//
		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, false, true);
		
		PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

		return dataSource;
	}
    
    public Map getRepoUrls(PoolingDataSource ds) {
        Map map = new HashMap();
        try {
            Connection conn = ds.getConnection();
            String sql = "select repo_id,repo_uri from  repo_id_map";
            Statement s = null;
            try {
                s = conn.createStatement();
                s.executeQuery(sql);
                ResultSet r = s.getResultSet();
                while (r.next()) {
                    Integer id = r.getInt(1);
                    String repouri = r.getString(2);
                    map.put((Object) id, (Object) repouri);
                }
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
            log.error("problem to select from repo_id_map:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return map;
    }

    public Map getRepoIds(PoolingDataSource ds) {
        Map map = new HashMap();
        try {
            Connection conn = ds.getConnection();
            String sql = "select repo_id,repo_uri from  repo_id_map";

            Statement s = null;
            try {
                s = conn.createStatement();
                s.executeQuery(sql);
                ResultSet r = s.getResultSet();
                while (r.next()) {
                    Integer id = r.getInt(1);
                    String repouri = r.getString(2);
                    map.put((Object) repouri, (Object) id);
                }
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
            log.error("problem to select ids from repo_id_map:"
                    + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return map;
    }

    public Map getDates(PoolingDataSource ds, Integer[] ids) {
        Map map = new HashMap();
        try {
            Connection conn = ds.getConnection();
            String sql = "select repo_uri,sdate from repo_id_map  "
                    + "where repo_id in (";
            int i;
            for (i = 0; i < ids.length - 1; i++) {
                sql = sql + ids[i] + ",";
            }
            sql = sql + ids[i] + ")";

            Statement s = null;
            try {
                s = conn.createStatement();
                s.executeQuery(sql);
                ResultSet r = s.getResultSet();
                while (r.next()) {
                    String repouri = r.getString(1);
                    String sdate = r.getString(2);
                    map.put(repouri, sdate);
                }
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
            log.error("problem to select dates from repo_id_map:"
                    + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return map;
    }

    public String getRepoUrl(PoolingDataSource ds, Integer repo_id) {
        String repouri = "";
        try {
            Connection conn = ds.getConnection();
            String sql = "select repo_uri from  repo_id_map"
                    + " where repo_id=" + repo_id;

            Statement s = null;
            try {
                s = conn.createStatement();
                s.executeQuery(sql);
                ResultSet r = s.getResultSet();
                while (r.next()) {
                    repouri = r.getString(1);
                }
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
            log.error("problem to select repouri from repo_id_map:"
                    + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return repouri;
    }

    public Integer getRepoId(PoolingDataSource ds, String repo_uri) {
        Integer repo_id = new Integer(0);
        try {
            Connection conn = ds.getConnection();
            Statement s = null;
            try {
                s = conn.createStatement();

                s.executeQuery("select ifnull(max(repo_id),0)+1  from repo_id_map");
                ResultSet r = s.getResultSet();
                while (r.next()) {
                    repo_id = r.getInt(1);
                }
                String sql = "insert into repo_id_map(repo_id,repo_uri) values ("+ repo_id + ",'" + repo_uri + "')";
                s.executeUpdate(sql);
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
            log.error("problem to insert repouri to repo_id_map:"
                    + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return repo_id;
    }

    public String[] mapUrls(Integer[] a, Map map, PoolingDataSource ds) {
        String[] tmp = new String[a.length];
        try {
            for (int i = 0; i < a.length; i++) {
                if (map.containsKey((Object) a[i])) {
                    tmp[i] = (String) map.get((Object) a[i]);
                } else {
                    tmp[i] = getRepoUrl(ds, a[i]);
                    map.put(a[i], tmp[i]);
                }
            }
        } catch (Exception ex) {
            log.error("problem to map repouris:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return tmp;
    }

}
