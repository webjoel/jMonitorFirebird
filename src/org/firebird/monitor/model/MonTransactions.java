
package org.firebird.monitor.model;

import java.sql.Timestamp;

public class MonTransactions {

    private int id;
    private int attachmentId;
    private int state;
    private Timestamp timestamp;
    private int topTransaction;
    private int oldestTransaction;
    private int oldestActive;
    private int isolationMode;
    private int lockTimeout;
    private int readOnly;
    private int autoCommit;
    private int autoUndo;
    private int statId;
    private int serverPID;
    private String user;
    private String remoteProcess;

    public String getRemoteProcess() {
        return remoteProcess;
    }

    public void setRemoteProcess(String remoteProcess) {
        this.remoteProcess = remoteProcess;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAutoCommit() {
        if (autoCommit == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }

    public void setAutoCommit(int autoCommit) {
        this.autoCommit = autoCommit;
    }

    public String getAutoUndo() {
        if (autoUndo == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }

    public void setAutoUndo(int autoUndo) {
        this.autoUndo = autoUndo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsolationMode() {
        switch (isolationMode) {
            case 0:
                return "Consistency";
            case 1:
                return "Concurrency";
            case 2:
                return "Read Committed Record Version";
            case 3:
                return "Read Committed";
            default:
                return "Unknown";
        }
    }

    public void setIsolationMode(int isolationMode) {
        this.isolationMode = isolationMode;
    }

    public String getLockTimeout() {
        switch (lockTimeout) {
            case -1:
                return "Infinite Wait";
            case 0:
                return "No Wait";
            default:
                return "Timeout " + lockTimeout;
        }
    }

    public void setLockTimeout(int lockTimeout) {
        this.lockTimeout = lockTimeout;
    }

    public int getOldestActive() {
        return oldestActive;
    }

    public void setOldestActive(int oldestActive) {
        this.oldestActive = oldestActive;
    }

    public int getOldestTransaction() {
        return oldestTransaction;
    }

    public void setOldestTransaction(int oldestTransaction) {
        this.oldestTransaction = oldestTransaction;
    }

    public String getReadOnly() {
        if (readOnly == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }

    public void setReadOnly(int readOnly) {
        this.readOnly = readOnly;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getTopTransaction() {
        return topTransaction;
    }

    public void setTopTransaction(int topTransaction) {
        this.topTransaction = topTransaction;
    }

    public int getServerPID() {
        return serverPID;
    }

    public void setServerPID(int serverPID) {
        this.serverPID = serverPID;
    }
}
