
package org.firebird.monitor.db;

import org.firebird.monitor.model.MonStats;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ManagerMonStats extends ManagerModel {

    private PreparedStatement pstmt;

    public ManagerMonStats(Connection conn) {
        try {
            pstmt = conn.prepareStatement("select r.mon$stat_id, i.mon$page_reads, i.mon$page_writes, " +
                    "i.mon$page_fetches, i.mon$page_marks, r.mon$record_seq_reads, " +
                    "r.mon$record_idx_reads, r.mon$record_inserts, r.mon$record_updates, " +
                    "r.mon$record_deletes, r.mon$record_backouts, r.mon$record_purges, " +
                    "r.mon$record_expunges " +
                    "from mon$record_stats r " +
                    "left join mon$io_stats i on (i.mon$stat_id = r.mon$stat_id) " +
                    "where r.mon$stat_id = ?");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MonStats> getStats(int statsId) {
        List<MonStats> lmt = new ArrayList<MonStats>();
        try {
            pstmt.setLong(1, statsId);
            setPreparedStatement(pstmt);
            while (getResultSet().next()) {
                MonStats mt = new MonStats();
                mt.setStatsId(getResultSet().getInt(1));
                mt.setPageReads(getResultSet().getLong(2));
                mt.setPageWrites(getResultSet().getLong(3));
                mt.setPageFetches(getResultSet().getLong(4));
                mt.setPageMarks(getResultSet().getLong(5));
                mt.setSeqReads(getResultSet().getLong(6));
                mt.setIdxReads(getResultSet().getLong(7));
                mt.setInserts(getResultSet().getLong(8));
                mt.setUpdates(getResultSet().getLong(9));
                mt.setDeletes(getResultSet().getLong(10));
                mt.setBackouts(getResultSet().getLong(11));
                mt.setPurges(getResultSet().getLong(12));
                mt.setExpunges(getResultSet().getLong(13));

                lmt.add(mt);
            }
            return lmt;
        } catch (Exception e) {
            e.printStackTrace();
            return lmt;
        }
    }
}
