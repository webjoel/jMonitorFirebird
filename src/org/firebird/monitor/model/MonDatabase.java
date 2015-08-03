
package org.firebird.monitor.model;

public class MonDatabase {

    private String databaseName;
    private int pageSize;
    private int sqlDialect;
    private int sweepInterval;
    private int readOnly;
    private int forcedWrites;
    private String odsVersion;

    public String getForcedWrites() {
        if (forcedWrites == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getReadOnly() {
        if (readOnly == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }

    public int getSqlDialect() {
        return sqlDialect;
    }

    public int getSweepInterval() {
        return sweepInterval;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setForcedWrites(int forcedWrites) {
        this.forcedWrites = forcedWrites;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setReadOnly(int readOnly) {
        this.readOnly = readOnly;
    }

    public void setSqlDialect(int sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    public void setSweepInterval(int sweepInterval) {
        this.sweepInterval = sweepInterval;
    }

    public String getOdsVersion() {
        return odsVersion;
    }

    public void setOdsVersion(String odsVersion) {
        this.odsVersion = odsVersion;
    }
}
