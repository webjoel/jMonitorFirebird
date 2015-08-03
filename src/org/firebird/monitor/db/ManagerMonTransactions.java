
package org.firebird.monitor.db;

import org.firebird.monitor.model.MonTransactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ManagerMonTransactions extends ManagerModel {

    private PreparedStatement pstmt0;
    private PreparedStatement pstmt1;

    public ManagerMonTransactions(Connection conn) {
        try {
            pstmt0 = conn.prepareStatement("select t.mon$transaction_id, t.mon$attachment_id, t.mon$state, " +
                    "t.mon$timestamp, t.mon$top_transaction, t.mon$oldest_transaction, " +
                    "t.mon$oldest_active, t.mon$isolation_mode, t.mon$lock_timeout, " +
                    "t.mon$read_only, t.mon$auto_commit, t.mon$auto_undo, t.mon$stat_id, " +
                    "a.mon$server_pid, a.mon$user, a.mon$remote_process from mon$transactions t " +
                    "inner join mon$attachments a on (a.mon$attachment_id = t.mon$attachment_id) " +
                    "inner join mon$io_stats i on (i.mon$stat_id = t.mon$stat_id) " +
                    "where t.mon$attachment_id <> current_connection " +
                    "order by coalesce(t.mon$lock_timeout,0) asc, i.mon$page_fetches desc, " +
                    "i.mon$page_reads desc, i.mon$page_writes desc, i.mon$page_marks desc, t.mon$timestamp asc");

            pstmt1 = conn.prepareStatement("select t.mon$transaction_id, t.mon$attachment_id, t.mon$state, " +
                    "t.mon$timestamp, t.mon$top_transaction, t.mon$oldest_transaction, " +
                    "t.mon$oldest_active, t.mon$isolation_mode, t.mon$lock_timeout, " +
                    "t.mon$read_only, t.mon$auto_commit, t.mon$auto_undo, t.mon$stat_id, " +
                    "a.mon$server_pid, a.mon$user, a.mon$remote_process from mon$transactions t " +
                    "inner join mon$attachments a on (a.mon$attachment_id = t.mon$attachment_id) " +
                    "inner join mon$io_stats i on (i.mon$stat_id = t.mon$stat_id) " +
                    "where t.mon$attachment_id <> current_connection and t.mon$attachment_id = ? " +
                    "order by coalesce(t.mon$lock_timeout,0) asc, i.mon$page_fetches desc, " +
                    "i.mon$page_reads desc, i.mon$page_writes desc, i.mon$page_marks desc, t.mon$timestamp asc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MonTransactions> getTransactions(int attachment) {
        List<MonTransactions> lmt = new ArrayList<MonTransactions>();
        String andWhere = "";
        try {
            if (attachment != 0) {
                pstmt1.setLong(1, attachment);
                setPreparedStatement(pstmt1);
            } else {
                setPreparedStatement(pstmt0);
            }

            while (getResultSet().next()) {
                MonTransactions mt = new MonTransactions();
                mt.setId(getResultSet().getInt(1));
                mt.setAttachmentId(getResultSet().getInt(2));
                mt.setState(getResultSet().getInt(3));
                mt.setTimestamp(getResultSet().getTimestamp(4));
                mt.setTopTransaction(getResultSet().getInt(5));
                mt.setOldestTransaction(getResultSet().getInt(6));
                mt.setOldestActive(getResultSet().getInt(7));
                mt.setIsolationMode(getResultSet().getInt(8));
                mt.setLockTimeout(getResultSet().getInt(9));
                mt.setReadOnly(getResultSet().getInt(10));
                mt.setAutoCommit(getResultSet().getInt(11));
                mt.setAutoUndo(getResultSet().getInt(12));
                mt.setStatId(getResultSet().getInt(13));
                mt.setServerPID(getResultSet().getInt(14));
                mt.setUser(getResultSet().getString(15));
                mt.setRemoteProcess(getResultSet().getString(16));

                lmt.add(mt);
            }
            return lmt;
        } catch (Exception e) {
            e.printStackTrace();
            return lmt;
        }
    }
}
