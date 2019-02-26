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

package gov.lanl.objectdb.openurl;

import gov.lanl.objectdb.ObjectDbConnectionManager;
import gov.lanl.objectdb.ObjectDbException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ResolverConnectionHandler {
    static Logger log = Logger.getLogger(ResolverConnectionHandler.class.getName());
    private Properties properties;
    private ObjectDbConnectionManager db;
    private String adore1Query;
    private String adore2Query;
    private String adore3Query;
    
    public ResolverConnectionHandler(Properties props) throws IOException {
        this.properties = props;
        init();
    }

    private void init() throws IOException {
        String jdbcDriverName = properties.getProperty("adore-objectdb-resolver.jdbcDriverName");
        if (jdbcDriverName == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.jdbcDriverName is missing from the properties file");
        }
        String jdbcURL = properties.getProperty("adore-objectdb-resolver.jdbcURL");
        if (jdbcURL == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.jdbcURL is missing from the properties file");
        }
        String jdbcLogin = properties.getProperty("adore-objectdb-resolver.jdbcLogin");
        if (jdbcLogin == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.jdbcLogin is missing from the properties file");
        }
        String jdbcPasswd = properties.getProperty("adore-objectdb-resolver.jdbcPasswd");
        if (jdbcPasswd == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.jdbcPasswd is missing from the properties file");
        }
        adore1Query = properties.getProperty("adore-objectdb-resolver.adore1Query");
        if (adore1Query == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.adore1Query is missing from the properties file");
        }
        adore2Query = properties.getProperty("adore-objectdb-resolver.adore2Query");
        if (adore2Query == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.adore2Query is missing from the properties file");
        }
        adore3Query = properties.getProperty("adore-objectdb-resolver.adore3Query");
        if (adore3Query == null) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.adore3Query is missing from the properties file");
        }
        boolean isPersistentConnection = false;
        String temp = properties.getProperty("adore-objectdb-resolver.isPersistentConnection");
        if ("true".equalsIgnoreCase(temp))
            isPersistentConnection = true;
        db = new ObjectDbConnectionManager(jdbcDriverName, jdbcURL, jdbcLogin, jdbcPasswd, isPersistentConnection);
    }
    
    private String populateAdore1Query(String identifier)
            throws ResolverException {
        StringTokenizer tokenizer = new StringTokenizer(adore1Query, "\\");
        StringBuffer sb = new StringBuffer();
        if (tokenizer.hasMoreTokens())
            sb.append(tokenizer.nextToken());
        else
            throw new ResolverException("Invalid adore1Query");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token.charAt(0)) {
            case 'i':
                sb.append(identifier);
                break;
            case 'o':
                sb.append(identifier);
                break;
            default: // ignore it
                sb.append("\\");
                sb.append(token.charAt(0));
            }
            sb.append(token.substring(1));
        }
        log.debug(sb.toString());
        return sb.toString();
    }

    private String populateAdore2Query(String identifier)
            throws ResolverException {
        StringTokenizer tokenizer = new StringTokenizer(adore2Query, "\\");
        StringBuffer sb = new StringBuffer();
        if (tokenizer.hasMoreTokens())
            sb.append(tokenizer.nextToken());
        else
            throw new ResolverException("Invalid adore2Query");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token.charAt(0)) {
            case 'i':
                sb.append(identifier);
                break;
            case 'o':
                sb.append(identifier);
                break;
            default: // ignore it
                sb.append("\\");
                sb.append(token.charAt(0));
            }
            sb.append(token.substring(1));
        }
        log.debug(sb.toString());
        return sb.toString();
    }
    
    private String populateAdore3Query(String from, String until, String set)
            throws ObjectDbException {
        StringTokenizer tokenizer = new StringTokenizer(adore3Query, "\\");
        StringBuffer sb = new StringBuffer();
        if (tokenizer.hasMoreTokens())
            sb.append(tokenizer.nextToken());
        else
            throw new ResolverException("Invalid adore3Query");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token.charAt(0)) {
            case 'f':
                if (from == null) {
                    from = "0000-00-00T00:00:00Z";
                }   
                sb.append(db.formatDate(from));
                break;
            case 'u':
                if (until == null || until.equals("9999-12-31T23:59:59Z")) {
                    until = db.getNow();
                }          
                sb.append(db.formatDate(until));
                break;
            case 's':
                sb.append(set);
                break;
            default: // ignore it
                sb.append("\\");
                sb.append(token.charAt(0));
            }
            sb.append(token.substring(1));
        }
        log.debug(sb.toString());
        return sb.toString();
    }
    
    public String getRecord(String identifier) throws ResolverException {
        Connection con = null;
        try {
            con = db.startConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(populateAdore1Query(identifier));
            if (!rs.next()) {
                db.endConnection(con);
                throw new ResolverException(identifier);
            }
            String data = rs.getString(1);
            db.endConnection(con);
            return data;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    db.endConnection(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new ResolverException(ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new ResolverException(e.getMessage());
        }
    }
    
    public ArrayList<OpenURLaDORe2Record> getRecordsList(String identifier) throws ResolverException {
        Connection con = null;
        ArrayList<OpenURLaDORe2Record> records = new ArrayList<OpenURLaDORe2Record>();
        try {
            con = db.startConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(populateAdore2Query(identifier));
            while (rs.next()) {
                OpenURLaDORe2Record r = new OpenURLaDORe2Record();
                r.setIdentifier((String) rs.getString(1));
                r.setDate((String) rs.getString(2));
                r.setSource((String) rs.getString(3));
                records.add(r);
            }
            if (records.isEmpty()) {
                db.endConnection(con);
                throw new ResolverException(identifier);
            }
            db.endConnection(con);
            return records;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    db.endConnection(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new ResolverException(ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new ResolverException(e.getMessage());
        }
    }
    
    public byte[] listIdentifiers(String from, String until, String set) throws ResolverException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] newline = "\n".getBytes();
        Connection con = null;
        try {
            con = db.startConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(populateAdore3Query(from, until, set));
            while (rs.next()) {
                baos.write(rs.getString(1).getBytes());
                baos.write(newline);
            }
        } catch (Exception e) {
            if (con != null) {
                try {
                    db.endConnection(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new ResolverException(ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new ResolverException(e.getMessage());
        } 
        return baos.toByteArray();
    }
    
}
