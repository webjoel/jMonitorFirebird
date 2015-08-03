
package org.firebird.monitor.model;

import static org.firebird.monitor.util.Crypto.*;

public class Databases {

    private String path;
    private String user;
    private String pass;
    private String charset;

    public String getPass() {
        return decript(pass);
    }

    public String getPath() {
        return path;
    }

    public String getUser() {
        return decript(user);
    }

    public void setPass(String pass) {
        this.pass = encript(pass);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUser(String user) {
        this.user = encript(user);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
