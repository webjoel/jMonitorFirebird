
package org.firebird.monitor.model;

import java.sql.Timestamp;

public class MonAttachments {

    private int id;
    private int state;
    private String ip;
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getState() {
        if (state == 0) {
            return "Idle";
        } else {
            return "Active";
        }
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Long getTime() {
        return timestamp.getTime();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
