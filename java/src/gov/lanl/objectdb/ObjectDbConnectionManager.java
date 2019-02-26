package gov.lanl.objectdb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ObjectDbConnectionManager {
    static Logger log = Logger.getLogger(ObjectDbConnectionManager.class.getName());
    private String jdbcDriverName;
    private String jdbcURL;
    private String jdbcLogin;
    private String jdbcPasswd;
    private boolean isPersistentConnection = false;
    private Connection persistentConnection;
    
    public ObjectDbConnectionManager(String driver, String url, String user, String pass, boolean persist) throws IOException {
        this.jdbcDriverName = driver;
        this.jdbcURL = url;
        this.jdbcLogin = user;
        this.jdbcPasswd = pass;
        this.isPersistentConnection = persist;
        // Verify the jdbcDriverName is valid
        try {
            Class.forName(jdbcDriverName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "adore-objectdb-resolver.jdbcDriverName is invalid: "+ jdbcDriverName);
        }
        // Init the connection
        if (isPersistentConnection) {
            try {
                persistentConnection = getNewConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }
    
    public Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPasswd);
    }

    public Connection startConnection() throws SQLException {
        if (persistentConnection != null) {
            return checkConnection(persistentConnection);
        } else {
            return getNewConnection();
        }
    }

    public Connection checkConnection(Connection connection) throws SQLException {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeQuery("SELECT 0");
        } catch (SQLException ex) {
            connection = getNewConnection();
        }
        return connection;
    }
    
    public void endConnection(Connection con) throws SQLException {
        if (persistentConnection == null)
            con.close();
    }
    
    public String formatDate(String date) {
        date = date.replace('T', ' ');
        date = date.replace('Z', ' ');
        date = date.trim();
        log.info(jdbcURL);
        log.info("formatDate: from " + date);
        return date;
    }
    
    public String getNow() throws ObjectDbException {
        Connection con = null;
        String date = null;
        try {
            con = startConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery("select UTC_TIMESTAMP()");
            while (rs.next()) {
                date = rs.getString(1);
            }
            return date;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    endConnection(con);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new ObjectDbException(ex.getMessage());
                }
            }
            e.printStackTrace();
            log.error(jdbcURL);
            throw new ObjectDbException(e.getMessage());
        }
    }
}
