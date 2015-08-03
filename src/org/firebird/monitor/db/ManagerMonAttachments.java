
package org.firebird.monitor.db;

import org.firebird.monitor.model.MonAttachments;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ManagerMonAttachments extends ManagerModel {

    private PreparedStatement pstmt;

    public ManagerMonAttachments(Connection conn) {
        try {
            pstmt = conn.prepareStatement("select a.mon$attachment_id, a.mon$remote_address, a.mon$state, " +
                    "a.mon$timestamp from mon$attachments a " +
                    "where a.mon$attachment_id <> current_connection " +
                    "order by a.mon$remote_address");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MonAttachments> getAttachments() {
        List<MonAttachments> lma = new ArrayList<MonAttachments>();
        try {
            setPreparedStatement(pstmt);
            while (getResultSet().next()) {
                MonAttachments ma = new MonAttachments();
                ma.setId(getResultSet().getInt(1));
                ma.setIp(getResultSet().getString(2));
                ma.setState(getResultSet().getInt(3));
                ma.setTimestamp(getResultSet().getTimestamp(4));
                lma.add(ma);
            }
            return lma;
        } catch (Exception e) {
            e.printStackTrace();
            return lma;
        }
    }
}
