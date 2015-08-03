
package org.firebird.monitor.model;

import java.sql.Timestamp;

public class MonStatements {

    private int statementId;
    private int attachmentId;
    private int transactionId;
    private int state;
    private Timestamp timestamp;
    private String sqlText;
    private int statId;

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public int getStatId() {
        return statId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }

    public String getState() {
        if (state == 0) {
            return "Idle";
        } else {
            return "Active";
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStatementId() {
        return statementId;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
