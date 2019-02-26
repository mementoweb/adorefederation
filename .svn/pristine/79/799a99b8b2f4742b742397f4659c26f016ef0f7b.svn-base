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

import gov.lanl.repo.oaidb.OAIPMHId;
import gov.lanl.repo.oaidb.OAIPMHSets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.jdom.input.SAXBuilder;

/**
 * RepoWriter.java
 * 
 * @author ludab
 */

public class RepoWriter {

    private Connection persistentConnection;

    private String jdbcURL = null;

    private String jdbcLogin = null;

    private String jdbcPasswd = null;

    private String idclass = null;

    private String setsclass = null;

    private String dateclass = null;

    private String ns = null;

    private String validation = "on";

    private String schemaLocation = null;

    DataSource dataSource;

    SAXBuilder builder = new SAXBuilder();

    private String identifier;

    // table specific

    private static Vector tables;

    private static String numTables = null;

    static SqlTables sqltables;

    static Logger log = Logger.getLogger(RepoWriter.class.getName());

    public RepoWriter(Properties properties, DataSource ds) throws IOException {
        this.dataSource = ds;
        SetTables(properties);
        SetRepoBlobFeatures(properties);

        try {
            persistentConnection = getNewConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }

    }

    public RepoWriter(Properties properties) throws IOException {
        String jdbcDriverName = properties
                .getProperty("JDBCLimitedOAICatalog.jdbcDriverName");
        if (jdbcDriverName == null) {
            throw new IllegalArgumentException(
                    "JDBCLimitedOAICatalog.jdbcDriverName is missing from the properties file");
        }
        jdbcURL = properties.getProperty("JDBCLimitedOAICatalog.jdbcURL");
        if (jdbcURL == null) {
            throw new IllegalArgumentException(
                    "jdbcURL is missing from the properties file");
        }
        jdbcLogin = properties.getProperty("JDBCLimitedOAICatalog.jdbcLogin");
        if (jdbcLogin == null) {
            throw new IllegalArgumentException(
                    "jdbcLogin is missing from the properties file");
        }
        jdbcPasswd = properties.getProperty("JDBCLimitedOAICatalog.jdbcPasswd");
        if (jdbcPasswd == null) {
            throw new IllegalArgumentException(
                    "jdbcPasswd is missing from the properties file");
        }
        
        SetTables(properties);
        SetRepoBlobFeatures(properties);

        try {
            dataSource = setupDataSource(jdbcURL, jdbcDriverName, jdbcLogin,
                    jdbcPasswd);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            persistentConnection = getNewConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public RepoWriter(String f) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(f)));

        String jdbcDriverName = properties.getProperty("jdbcDriverName");
        if (jdbcDriverName == null) {
            throw new IllegalArgumentException(
                    "jdbcDriverName is missing from the properties file");
        }
        jdbcURL = properties.getProperty("jdbcURL");
        if (jdbcURL == null) {
            throw new IllegalArgumentException(
                    "jdbcURL is missing from the properties file");
        }

        jdbcLogin = properties.getProperty("jdbcLogin");
        if (jdbcLogin == null) {
            throw new IllegalArgumentException(
                    "jdbcLogin is missing from the properties file");
        }

        jdbcPasswd = properties.getProperty("jdbcPasswd");
        if (jdbcPasswd == null) {
            throw new IllegalArgumentException(
                    "jdbcPasswd is missing from the properties file");
        }

        SetTables(properties);
        SetRepoBlobFeatures(properties);

        try {
            dataSource = setupDataSource(jdbcURL, jdbcDriverName, jdbcLogin,
                    jdbcPasswd);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            persistentConnection = getNewConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }

    }

    private void SetRepoBlobFeatures(Properties properties)
            throws IllegalArgumentException {
        idclass = properties.getProperty("OAIPMHidclass");
        if (idclass == null) {
            throw new IllegalArgumentException(
                    "idclass  is missing from the properties file");
        }

        setsclass = properties.getProperty("OAIPMHsetsclass");
        if (setsclass == null) {
            throw new IllegalArgumentException(
                    "setsclass  is missing from the properties file");
        }

        validation = properties.getProperty("validation");
        if (validation == null) {
            throw new IllegalArgumentException(
                    "validation flag  is missing from the properties file");
        }
        if (validation.equals("on")) {
            schemaLocation = properties.getProperty("schema");
            ns = properties.getProperty("metadatanamespace");
            if (schemaLocation == null) {
                throw new IllegalArgumentException(
                        "schemaLocation is missing from the properties file");
            }

            if (ns == null) {
                throw new IllegalArgumentException(
                        "namespace is missing from the properties file");
            }

        }
    }

    private Connection getNewConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private Connection startConnection() throws SQLException {
        if (persistentConnection != null) {
            if (persistentConnection.isClosed())
                persistentConnection = getNewConnection();
            return persistentConnection;
        } else {
            return getNewConnection();
        }
    }

    private void endConnection(Connection con) throws SQLException {
        if (persistentConnection == null)
            con.close();
    }

    private String GetTableName(String id) throws SQLException {
        Connection con = null;
        String suffix = null;

        con = startConnection();
        suffix = sqltables.GetSuffix(con, id);
        return suffix;
    }

    public static DataSource setupDataSource(String connectURI,
            String jdbcDriverName, String user, String pwd) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(jdbcDriverName);
        ds.setUsername(user);
        ds.setPassword(pwd);
        ds.setUrl(connectURI);
        return ds;
    }

    public static void shutdownDataSource(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        bds.close();
    }

    public static void printDataSourceStats(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        log.info("NumActive: " + bds.getNumActive());
        log.info("NumIdle: " + bds.getNumIdle());
    }

    public static void SetTables(Properties properties) {
        numTables = properties.getProperty("tables");
        int numtab = Integer.parseInt(numTables);
        tables = new Vector(numtab);

        if (numtab > 2) {
            try {
                Class whatclass = Class.forName(properties
                        .getProperty("tablesuffixes"));

                sqltables = (SqlTables) whatclass.newInstance();
                tables = sqltables.GetSuffixes();
            } catch (Exception e) {
                throw new IllegalArgumentException("tablesuffixes is invalid:");
            }
        } else {
            tables.add(" ");
        }

    }

    public String getId() {
        return this.identifier;
    }

    public String setId(String meta) {
        String id;

        try {

            Class whatclass = Class.forName(idclass);
            OAIPMHId oaiid = (OAIPMHId) whatclass.newInstance();

            id = oaiid.getId(meta);

        } catch (Exception e) {
            throw new IllegalArgumentException(" getId() classid is invalid:"
                    + e.toString());
        }

        return id;
    }

    public boolean checkIdType(String id) {
        boolean status;

        try {

            Class whatclass = Class.forName(idclass);
            OAIPMHId oaiid = (OAIPMHId) whatclass.newInstance();

            status = oaiid.checkId(id);
        } catch (Exception e) {

            throw new IllegalArgumentException(e.toString());
        }

        return status;
    }

    public Iterator setSets(String meta) {
        Iterator sets;
        log.debug("setsclass:" + setsclass);
        try {
            Class whatclass = Class.forName(setsclass);
            OAIPMHSets oaisets = (OAIPMHSets) whatclass.newInstance();
            sets = oaisets.getSets(meta);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("class for sets is invalid:");
        }

        return sets;
    }

    public boolean checkSetsType(Iterator sets) {
        boolean status;

        log.debug("setsclass:" + setsclass);
        try {
            Class whatclass = Class.forName(setsclass);
            OAIPMHSets oaisets = (OAIPMHSets) whatclass.newInstance();
            status = oaisets.checkSets(sets);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.toString());
        }

        return status;
    }

    public boolean validate(String meta) throws IllegalArgumentException {
        if (validation.equals("on")) {
            StringReader xmlStream = new StringReader(meta);
            SAXBuilder builder = new SAXBuilder(
                    "org.apache.xerces.parsers.SAXParser");
            builder.setValidation(true);
            builder.setFeature(
                    "http://apache.org/xml/features/validation/schema", true);
            builder
                    .setProperty(
                            "http://apache.org/xml/properties/schema/external-schemaLocation",
                            ns + "  " + schemaLocation);

            try {
                log.debug("RepoWriter: " + meta);
                builder.build(xmlStream);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("**** Error in RepoWriter: " + meta);
                throw new IllegalArgumentException(
                        "metadata validation problem");
            }

        }
        return true;
    }

    public String check_id(String id, String meta) throws PMPException {
        if (id == null) {
            id = setId(meta);
            this.identifier = id;

            if (id == null) {
                throw new PMPException("id  is undefined", 3);
            }
        } else {
            this.identifier = id;
            checkIdType(id);
        }

        return id;
    }

    public void putRecord(String oaiRecord) throws PMPException,
            IllegalArgumentException {
        PMPRecord record = new PMPRecord(oaiRecord);
        String id = record.getIdentifier();
        String verb = record.getVerb();
        String meta = record.getMetadata();
        Iterator iset = record.getSetSpecs();

        if (verb.equals("PutRecord")) {
            putRecord(id, meta, iset);
        }
    }

    public void putRecord(String id, String meta, Iterator iset)
            throws PMPException, IllegalArgumentException {
        Connection con = null;

        try {
            if (meta == null) {
                throw new IllegalArgumentException("metadata is undefined");
            }
            int size = meta.length();

            if (validate(meta)) {

                id = check_id(id, meta);

                if (iset == null) {
                    iset = setSets(meta);
                } else {
                    ArrayList<String> s = new ArrayList();
                    while (iset.hasNext()) {
                        s.add((String)iset.next());
                    }
                    if (!checkSetsType(s.iterator())) {
                        throw new IllegalArgumentException(
                                "sets are not supported");
                    }
                    iset = s.iterator();
                }

                Vector sets = new Vector();
                int tnum = Integer.parseInt(numTables);
                String tablesuffix = " ";
                if (tnum > 2) {
                    tablesuffix = GetTableName(id);
                }

                log.debug(populateMetadataRecordInsert(id, size, meta,
                        tablesuffix));
                log.debug("*** PutRecord Meta: " + meta);
                // populate metadata_record table;
                con = startConnection();
                Statement s = con.createStatement();
                s.executeUpdate(populateMetadataRecordInsert(id, size, meta,
                        tablesuffix));

                // populate sets and record_sets tables
                if (iset != null && iset.hasNext()) {
                    while (iset.hasNext()) {
                        sets.add((String) iset.next());
                    }
                    int setsize = sets.size();
                    log.debug("setsize: " + setsize);
                    s.executeUpdate(populateSetsInsert(sets));
                    for (int j = 0; j < setsize; ++j) {
                        ResultSet rstsetid = s
                                .executeQuery(populateSetsQuery((String) sets
                                        .get(j)));
                        rstsetid.next();
                        int intset = rstsetid.getInt(1);
                        s.executeUpdate(populateRecordSetsInsert(id,
                                tablesuffix, intset));
                        rstsetid.close();
                    }
                }
                s.close();
            }
        } catch (SQLException e) {
            int i = e.getErrorCode();

            if (i == 1062) {
                throw new PMPException(e.getMessage(), 1062);
            } else {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

    }

    public void updateRecord(String id, String meta, Iterator iset)
            throws Exception {
        Connection con = null;

        try {
            if (meta == null) {
                throw new IllegalArgumentException("metadata is undefined");
            }
            int size = meta.length();

            if (validate(meta)) {
                id = check_id(id, meta);

                if (iset == null) {
                    iset = setSets(meta);
                } else {
                    if (!checkSetsType(iset)) {
                        throw new IllegalArgumentException(
                                "sets are  not supported");
                    }
                }

                Vector sets = new Vector();
                int tnum = Integer.parseInt(numTables);
                String tablesuffix = " ";
                if (tnum > 2) {
                    tablesuffix = GetTableName(id);
                }

                // populate metadata_record table;

                con = startConnection();
                Statement s = con.createStatement();
                s.executeUpdate(populateMetadataRecordUpdate(id, size, meta,
                        tablesuffix));
                s.close();
            }

        } catch (SQLException e) {
            int i = e.getErrorCode();
            throw new Exception(e);
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);

        }
    }

    // Delete Record
    public void deleteRecord(String oaiId) throws Exception {
        Connection conn = null;
        Statement s = null;
        try {
            conn = startConnection();
            s = conn.createStatement();
            // Get Digest Value to flush 
            String sql = "select digest_id from metadata_record where oai_id = \"" + oaiId + "\"";
            ResultSet result = s.executeQuery(sql);
            result.next();
            String digest = result.getString(1);
            // Delete Set/Record Mapping
            sql =  "delete from record_sets where digest_id = \"" + digest + "\"";
            s.executeUpdate(sql);
            // Delete Primary Record Entry
            sql =  "delete from metadata_record where digest_id = \"" + digest + "\"";
            s.executeUpdate(sql);
            s.close();
        } catch (SQLException e) {
        } finally {
            try {
                if (s != null)
                    s.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new Exception(e.getMessage());
            }
        }
    }
    
    // Query Creation 
    public String populateMetadataRecordInsert(String id, int size,
            String meta, String tablesuffix) {
        String date = "UTC_TIMESTAMP()";
        this.identifier = id;

        String sql = "insert  into metadata_record" + tablesuffix
                + "(digest_id,oai_id,oai_date,size,data) values (" + " md5('"
                + id + "'),'" + id + "'," + date + "," + size + ",'" + meta
                + "')";
        log.debug(sql);
        return sql;

    }

    public String populateMetadataRecordUpdate(String id, int size,
            String meta, String tablesuffix) {
        String date = "UTC_TIMESTAMP()";
        String sql = "update metadata_record" + tablesuffix + "  set  data ='"
                + meta + "', size=" + size + "  where  digest_id =  md5('" + id
                + "')";
        log.debug(sql);
        return sql;

    }

    public String populateMetadataRecordDelete(String id, String tablesuffix) {
        String sql = "update metadata_record" + tablesuffix
                + " set flag='D' where digest_id = md5('" + id + "')";
        log.debug(sql);
        return sql;

    }

    public String populateSetsInsert(Vector sets) {
        int setsize = sets.size();
        // construct insert to sets
        String setsql = "insert ignore into sets (set_specs) values ";

        for (int i = 0; i < setsize; ++i) {
            setsql = setsql + "('" + sets.get(i) + "')";
            if ((i + 1) < setsize) {
                setsql = setsql + ", ";
            }
        }
        log.debug(setsql);
        return setsql;
    }

    public String populateRecordSetsInsert(String id, String tablesuffix,
            int set_id) {
        String recordsets = "insert ignore into record_sets" + tablesuffix
                + "  (digest_id,set_id) values (md5('" + id + "')," + set_id
                + ")";
        log.debug(recordsets);
        return recordsets;
    }

    public String populateSetsQuery(String set) {
        String setid = "select set_id from sets where set_specs='" + set + "'";
        log.debug(setid);
        return setid;
    }

}
