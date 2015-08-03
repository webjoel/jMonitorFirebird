
package org.firebird.monitor.db;

import org.firebird.monitor.model.MonStatements;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ManagerMonStatements extends ManagerModel {

    private PreparedStatement pstmt;

    public ManagerMonStatements(Connection conn) {
        try {
            pstmt = conn.prepareStatement("select s.mon$statement_id, s.mon$attachment_id, s.mon$transaction_id, " +
                    "s.mon$state, s.mon$timestamp, s.mon$sql_text, s.mon$stat_id " +
                    "from mon$statements s " +
                    "where s.mon$attachment_id <> current_connection " +
                    "and s.mon$transaction_id = ?");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MonStatements> getStatements(int transactionId) {
        List<MonStatements> lms = new ArrayList<MonStatements>();
        try {
            pstmt.setLong(1, transactionId);
            setPreparedStatement(pstmt);
            while (getResultSet().next()) {
                MonStatements ms = new MonStatements();
                ms.setStatementId(getResultSet().getInt(1));
                ms.setAttachmentId(getResultSet().getInt(2));
                ms.setTransactionId(getResultSet().getInt(3));
                ms.setState(getResultSet().getInt(4));
                ms.setTimestamp(getResultSet().getTimestamp(5));
                ms.setSqlText(getResultSet().getString(6));
                ms.setStatId(getResultSet().getInt(7));
                lms.add(ms);
            }
            return lms;
        } catch (Exception e) {
            e.printStackTrace();
            return lms;
        }
    }
}
