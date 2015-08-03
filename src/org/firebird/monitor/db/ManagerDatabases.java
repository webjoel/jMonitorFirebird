
package org.firebird.monitor.db;

import org.firebird.monitor.model.ListDatabases;
import org.firebird.monitor.model.Databases;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;

public class ManagerDatabases {

    private String xml;
    private ListDatabases listaDatabases;
    private List<Databases> ld;
    private Databases database;
    private String dist;

    public ManagerDatabases() {
        dist = System.getProperty("user.dir") + "/registration.xml";
    }

    public void saveDatabases(ListDatabases listaDatabases) {
        //format xml
        XStream stream = new XStream(new DomDriver());
        stream.alias("database", Databases.class);
        stream.alias("registration", ListDatabases.class);

        //make file
        try {
            File f = new File(dist);
            f.delete();
            FileWriter fw = new FileWriter(f, true);
            fw.write(stream.toXML(listaDatabases));
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readDatabases(String path) {
        //load xml
        XStream stream = new XStream(new DomDriver());
        stream.alias("database", Databases.class);
        stream.alias("registration", ListDatabases.class);

        try {
            File f = new File(dist);
            if (f.exists() == false) {
                //registration example
                Databases d = new Databases();
                d.setPath("127.0.0.1/3050:C:/Database/Data.fdb");
                d.setUser("SYSDBA");
                d.setPass("masterkey");
                d.setCharset("NONE");
                ListDatabases l = new ListDatabases();
                l.addDatabase(d);
                FileWriter fw = new FileWriter(f, true);
                fw.write(stream.toXML(l));
                fw.flush();
                fw.close();
            }
            FileInputStream x = new FileInputStream(dist);
            listaDatabases = (ListDatabases) stream.fromXML(x);
            xml = stream.toXML(listaDatabases);
            ld = listaDatabases.getDatabases();
            for (Databases d : ld) {
                if (d.getPath().equals(path)) {
                    database = d;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getXml() {
        return xml;
    }

    public Databases getDatabase() {
        return database;
    }

    public List<Databases> getLd() {
        return ld;
    }
}
