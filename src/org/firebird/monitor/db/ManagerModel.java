
package org.firebird.monitor.db;

import org.firebird.monitor.model.Databases;
import java.sql.*;

public class ManagerModel<T> {

    private static Databases database;
    private Connection conn;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pstmtTime;

    public static Databases getDatabase() {
        return database;
    }

    public static void setDatabase(Databases d) {
        ManagerModel.database = d;
    }

    public void setSql(String sql) {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPreparedStatement(PreparedStatement pstmt) {
        try {
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConnection(Connection c) {
        conn = c;
        try {
            pstmtTime = conn.prepareStatement("select current_timestamp from rdb$database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpdate(String sql) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet getResultSet() {
        return rs;
    }

    public Connection getConnection() {
        return conn;
    }

    public Timestamp getNow() {
        Timestamp now = null;

        try {
            rs = pstmtTime.executeQuery();
            while (rs.next()) {
                now = rs.getTimestamp(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return now;
    }
}
