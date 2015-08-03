
package org.firebird.monitor.db;

import java.sql.Connection;
import java.sql.DriverManager;
import org.firebird.monitor.model.Databases;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {

    private Databases database;

    public void setDatabase(Databases database) {
        this.database = database;
    }

    public Connection getConnection() throws Exception  {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            Properties prop = new Properties();
            prop.put("user", database.getUser());
            prop.put("password", database.getPass());
            prop.put("charset", database.getCharset());
            //prop.put("lc_ctype", "ISO8859_1");
            return DriverManager.getConnection("jdbc:firebirdsql:" + database.getPath(), prop);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
