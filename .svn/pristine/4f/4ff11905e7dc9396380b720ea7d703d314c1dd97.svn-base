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

package gov.lanl.objectdb.oaidb;

import gov.lanl.identifier.Identifier;
import gov.lanl.objectdb.ObjectDbConnectionManager;
import gov.lanl.objectdb.ObjectDbException;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class OAIDBConnectionHandler {
    static Logger log = Logger.getLogger(OAIDBConnectionHandler.class.getName());
    private Properties properties;
    private ObjectDbConnectionManager db;
    
    public OAIDBConnectionHandler(Properties props) throws IOException {
        this.properties = props;
        init();
    }

    private void init() throws IOException {
        String jdbcDriverName = properties.getProperty("JDBCLimitedOAICatalog.jdbcDriverName");
        if (jdbcDriverName == null) {
            throw new IllegalArgumentException(
                    "JDBCLimitedOAICatalog.jdbcDriverName is missing from the properties file");
        }
        String jdbcURL = properties.getProperty("JDBCLimitedOAICatalog.jdbcURL");
        if (jdbcURL == null) {
            throw new IllegalArgumentException(
                    "JDBCLimitedOAICatalog.jdbcURL is missing from the properties file");
        }
        String jdbcLogin = properties.getProperty("JDBCLimitedOAICatalog.jdbcLogin");
        if (jdbcLogin == null) {
            throw new IllegalArgumentException(
                    "JDBCLimitedOAICatalog.jdbcLogin is missing from the properties file");
        }
        String jdbcPasswd = properties.getProperty("JDBCLimitedOAICatalog.jdbcPasswd");
        if (jdbcPasswd == null) {
            throw new IllegalArgumentException(
                    "JDBCLimitedOAICatalog.jdbcPasswd is missing from the properties file");
        }
        boolean isPersistentConnection = false;
        String temp = properties.getProperty("JDBCLimitedOAICatalog.isPersistentConnection");
        if ("true".equalsIgnoreCase(temp))
            isPersistentConnection = true;
        db = new ObjectDbConnectionManager(jdbcDriverName, jdbcURL, jdbcLogin, jdbcPasswd, isPersistentConnection);
    }
    
    public int populateIdentifiers(ArrayList<Identifier> identifiers) throws ObjectDbException {
        Connection con = null;
        int updateCounts = 0;
        try {
            con = db.startConnection();
            con.setAutoCommit(false);
            // Create a prepared statement
            String sql = "INSERT INTO identifiers(identifier, digest_id) VALUES(?,MD5(?))";
            PreparedStatement pstmt = con.prepareStatement(sql);
            Set<String> recordIds = new TreeSet<String>();
            for (Identifier i : identifiers) {
                recordIds.add(i.getRecordId());
                pstmt.setString(1, i.getIdentifier());
                pstmt.setString(2, i.getRecordId());
                pstmt.addBatch();
            }
            for (String rec : recordIds) {
                pstmt.setString(1, rec);
                pstmt.setString(2, rec);
                pstmt.addBatch();
            }
            
            // Execute the batch
            updateCounts = getUpdateCount(pstmt.executeBatch());
            con.commit();
            db.endConnection(con);
        } catch (BatchUpdateException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new ObjectDbException(e1.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectDbException(e.getMessage());
        } finally {
            if (con != null) {
                try {
                    db.endConnection(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new ObjectDbException(ex.getMessage());
                }
            }
        }
        return updateCounts;
    }
    
    private static int getUpdateCount(int[] updateCounts) {
        int u = 0;
        for (int i=0; i<updateCounts.length; i++) {
            if (updateCounts[i] >= 0) {
                u += updateCounts[i];
            } 
        }
        return u;
    }
    
    public int deleteIdentifiers(String recordIdentifier) throws ObjectDbException {
        Connection con = null;
        int updateCounts = 0;
        try {
            con = db.startConnection();
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM identifiers WHERE digest_id = MD5('" + recordIdentifier + "')";
            updateCounts = stmt.executeUpdate(sql);
            db.endConnection(con);
        } catch (SQLException e) {
            if (con != null) {
                try {
                    db.endConnection(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new ObjectDbException(ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new ObjectDbException(e.getMessage());
        }
        return updateCounts;
    }
}
