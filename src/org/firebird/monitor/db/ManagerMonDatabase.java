
package org.firebird.monitor.db;

import org.firebird.monitor.model.MonDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ManagerMonDatabase extends ManagerModel {

    private PreparedStatement pstmt;

    public ManagerMonDatabase(Connection conn) {
        try {
            pstmt = conn.prepareStatement("select d.mon$database_name, d.mon$page_size, d.mon$sql_dialect, " +
                    "d.mon$sweep_interval, d.mon$read_only, d.mon$forced_writes, " +
                    "d.mon$ods_major||'.'||d.mon$ods_minor from mon$database d");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MonDatabase getProperties() {
        MonDatabase md = null;

        try {
            setPreparedStatement(pstmt);
            md = new MonDatabase();
            while (getResultSet().next()) {
                md.setDatabaseName(getResultSet().getString(1));
                md.setPageSize(getResultSet().getInt(2));
                md.setSqlDialect(getResultSet().getInt(3));
                md.setSweepInterval(getResultSet().getInt(4));
                md.setReadOnly(getResultSet().getInt(5));
                md.setForcedWrites(getResultSet().getInt(6));
                md.setOdsVersion(getResultSet().getString(7));
            }
            return md;
        } catch (Exception e) {
            e.printStackTrace();
            return md;
        }
    }
}
