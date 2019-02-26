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

package gov.lanl.ockham.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.*;

public class ServiceRegistryDB {

    private boolean isPersistentConnection = true;

    private String jdbcLogin = null;

    private String jdbcPasswd = null;

    private String jdbcURL = null;

    private Connection persistentConnection;

    Logger logger = Logger.getLogger(ServiceRegistryDB.class);

    /**
     * Construct a ServiceRegistryDB object
     * 
     * @param properties
     *            a properties object containing initialization parameters
     * @exception IOException
     *                an I/O error occurred during database initialization.
     */
    public ServiceRegistryDB(Properties properties) throws IOException {
        String jdbcDriverName = properties
                .getProperty("adore-service-registry.jdbcDriverName");
        if (jdbcDriverName == null) {
            throw new IllegalArgumentException(
                    "adore-service-registry.jdbcDriverName is missing from the properties file");
        }
        jdbcURL = properties.getProperty("adore-service-registry.jdbcURL");
        if (jdbcURL == null) {
            throw new IllegalArgumentException(
                    "adore-service-registry.jdbcURL is missing from the properties file");
        }

        jdbcLogin = properties.getProperty("adore-service-registry.jdbcLogin");
        if (jdbcLogin == null) {
            throw new IllegalArgumentException(
                    "adore-service-registry.jdbcLogin is missing from the properties file");
        }

        jdbcPasswd = properties
                .getProperty("adore-service-registry.jdbcPasswd");
        if (jdbcPasswd == null) {
            throw new IllegalArgumentException(
                    "adore-service-registry.jdbcPasswd is missing from the properties file");
        }

        String temp = properties
                .getProperty("adore-service-registry.isPersistentConnection");
        if ("false".equalsIgnoreCase(temp))
            isPersistentConnection = false;

        // open the connection
        try {
            Class.forName(jdbcDriverName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "adore-service-registry.jdbcDriverName is invalid: "
                            + jdbcDriverName);
        }

        if (isPersistentConnection) {
            try {
                persistentConnection = getNewConnection();
            } catch (ServiceException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }

    public String getRecord(String ockhamIdentifier)
            throws IdDoesNotExistException, ServiceException {
        Connection con = null;
        try {
            con = startConnection();
            Statement stmt = con.createStatement();
            String query = getMetadataQuery(ockhamIdentifier);
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.next()) {
                endConnection(con);
                throw new IdDoesNotExistException(ockhamIdentifier);
            }

            endConnection(con);

            String record = null;
            record = rs.getString(1);

            return record;
        } catch (SQLException e) {
            if (con != null)
                endConnection(con);
            throw new ServiceException(e);
        }
    }

    public void putRecord(String identifier, String text, String type)
            throws ServiceException {

        Connection con = null;
        try {
            con = startConnection();
            Statement stmt = con.createStatement();

            // update OAI_RECORDS tables
            StringBuilder sb = new StringBuilder();
            sb
                    .append(
                            "insert into OAI_RECORDS(identifier,metadata,schemaId) values('")
                    .append(identifier).append("','").append(text).append(
                            "', 1)");

            stmt.executeUpdate(sb.toString());

            // update OAI_RECORDS_SET_MAP table
            sb = new StringBuilder();
            sb.append("insert into OAI_RECORD_SET_MAP(set_spec, record_id)")
                    .append(" values('").append(type2setSpec(type)).append(
                            "','").append(identifier).append("')");

            stmt.executeUpdate(sb.toString());

            sb = new StringBuilder();

            sb.append("insert into OAI_RECORD_SET_MAP(set_spec, record_id)")
                    .append(" values('Peer:LANL','").append(identifier).append(
                            "')");

            stmt.executeUpdate(sb.toString());
            endConnection(con);
        } catch (SQLException e) {
            if (con != null)
                endConnection(con);
            throw new ServiceException(e);
        }
    }

    public void deleteRecord(String identifier) throws ServiceException {
        Connection con = null;
        try {
            con = startConnection();
            Statement stmt = con.createStatement();

            // update OAI_RECORDS tables
            StringBuilder sb = new StringBuilder();
            sb.append("delete from OAI_RECORDS where identifier='").append(
                    identifier).append("'");

            stmt.executeUpdate(sb.toString());

            // update OAI_RECORDS_SET_MAP table
            sb = new StringBuilder();
            sb.append("delete from OAI_RECORD_SET_MAP where record_id='")
                    .append(identifier).append("'");

            stmt.executeUpdate(sb.toString());

            endConnection(con);
        } catch (SQLException e) {
            if (con != null)
                endConnection(con);
            throw new ServiceException(e);
        }

    }

    private String getMetadataQuery(String ockhamIdentifier) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT metadata ");
        query.append("FROM OAI_RECORDS ");
        query.append("WHERE identifier = '" + ockhamIdentifier + "'");

        return query.toString();
    }

    /**
     * Convert type to setSpec
     * 
     * OCKHAM OAI_RECORD_SET_MAP table use keywords
     * "collections","agents","services", while IESR schema uses
     * "Collection","Agent","Service"
     * 
     * It makes sense to use IESR schema in all external interface, however in
     * database we would like to keep OCKHAM's convention, therefore a
     * convention here.
     * 
     * @param type
     * @return
     * @exception IllegalArgumentException,
     *                if the type doesn't match three keywords
     */
    private String type2setSpec(String type) throws IllegalArgumentException {

        String set_spec = null;

        if ("Collection".equals(type))
            set_spec = "collections";
        else if ("Agent".equals(type))
            set_spec = "agents";
        else if ("Service".equals(type))
            set_spec = "services";
        else
            throw new IllegalArgumentException("invalid svc_type " + type);
        return set_spec;
    }

    private String getFilteredMetadataQuery(String ockhamIdentifier, String type) {

        // Map OpenURL svc.type parameter to OCKHAM set_spec
        String set_spec = type2setSpec(type);

        StringBuffer query = new StringBuffer();
        query.append("SELECT metadata ");
        query.append("FROM OAI_RECORDS ");
        query
                .append("INNER JOIN OAI_RECORD_SET_MAP ON OAI_RECORDS.identifier=OAI_RECORD_SET_MAP.record_id ");
        query.append("WHERE identifier='" + ockhamIdentifier + "'");
        query.append("AND OAI_RECORD_SET_MAP.set_spec='" + set_spec + "'");

        return query.toString();
    }

    private Connection getNewConnection() throws ServiceException {
        try {
            logger.info("create connection");
            return DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPasswd);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    private Connection startConnection() throws ServiceException {
        try {
            if (persistentConnection != null) {
                if (persistentConnection.isClosed())
                    persistentConnection = getNewConnection();
                return checkConnection(persistentConnection);
            } else {
                return checkConnection(getNewConnection());
            }
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    private void endConnection(Connection con) throws ServiceException {
        try {
            if (persistentConnection == null)
                con.close();
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    private Connection checkConnection(Connection connection)
            throws ServiceException {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeQuery("SELECT CURRENT_DATE");
        } catch (SQLException ex) {
            connection = getNewConnection();
        }
        return connection;
    }

    /**
     * Close the repository
     */
    public void close() {
        try {
            if (persistentConnection != null)
                persistentConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        persistentConnection = null;
    }
}
