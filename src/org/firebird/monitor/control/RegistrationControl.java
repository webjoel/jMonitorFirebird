
package org.firebird.monitor.control;

import org.firebird.monitor.db.Connect;
import org.firebird.monitor.db.ManagerDatabases;
import org.firebird.monitor.db.ManagerModel;
import org.firebird.monitor.model.Databases;
import org.firebird.monitor.model.ListDatabases;
import org.firebird.monitor.view.RegistrationView;
import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;

public class RegistrationControl {

    private List<Databases> databases;
    private Connect con = new Connect();
    private Connection conn = null;
    private MonitorControl mc;
    private RegistrationView rv;

    public RegistrationControl(MonitorControl m) {
        mc = m;
        rv = new RegistrationView(mc.getMonitorView(), true, this);
        listPatchs();
        rv.setLocationRelativeTo(null);
        rv.pack();
        rv.setVisible(true);
    }

    public void listPatchs() {
        rv.removePatchAllItens();
        ManagerDatabases md = new ManagerDatabases();
        md.readDatabases("");
        databases = md.getLd();
        for (Databases d : databases) {
            rv.addPatchItem(d.getPath());
        }
    }

    public void openRegistration() {
        if ((rv.getPatchIndex() >= 0) && (!rv.getPatchItem().equals(""))) {
            Databases d = databases.get(rv.getPatchIndex());
            rv.setPatchTextField(d.getPath());
            rv.setUserTextField(d.getUser());
            rv.setPassTextField(d.getPass());
            rv.setCharsetTextField(d.getCharset());
        } else {
            rv.setPatchTextField("");
            rv.setUserTextField("");
            rv.setPassTextField("");
            rv.setCharsetTextField("");
            rv.setFocusPatch();
        }
    }

    public void saveRegistration() {
        if (rv.getPatchTextField().isEmpty() || rv.getUserTextField().isEmpty() ||
                rv.getPassTextField().isEmpty() || rv.getCharsetTextField().isEmpty()) {
            JOptionPane.showMessageDialog(mc.getMonitorView(), "Incomplete Data!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ListDatabases ld = new ListDatabases();
            for (Databases d : databases) {
                if (!d.getPath().equals(rv.getPatchTextField())) {
                    ld.addDatabase(d);
                }
            }
            Databases d = new Databases();
            d.setPath(rv.getPatchTextField());
            d.setUser(rv.getUserTextField());
            d.setPass(rv.getPassTextField());
            d.setCharset(rv.getCharsetTextField());
            ld.addDatabase(d);
            ManagerDatabases md = new ManagerDatabases();
            md.saveDatabases(ld);
            listPatchs();
        }
    }

    public void newRegistration() {
        if (!rv.getPatchItem().equals("")) {
            rv.addPatchItem("");
            rv.setPatchIndex(rv.getPatchItemCount() - 1);
        }
        rv.setPatchTextField("");
        rv.setUserTextField("");
        rv.setPassTextField("");
        rv.setCharsetTextField("");
        rv.setFocusPatch();
    }

    public void deleteRegistration() {
        if (rv.getPatchItem().equals("")) {
            rv.removePatchItem(rv.getPatchItem());
            rv.setPatchIndex(0);
            openRegistration();
        } else {
            databases.remove(rv.getPatchIndex());
            ListDatabases ld = new ListDatabases();
            for (Databases d : databases) {
                ld.addDatabase(d);
            }
            ManagerDatabases gd = new ManagerDatabases();
            gd.saveDatabases(ld);
            listPatchs();
        }
    }

    public void connectDatabase() {        
        if (rv.getPatchIndex() >= 0) {
            ManagerDatabases md = new ManagerDatabases();
            md.readDatabases(rv.getPatchSelected());
            Databases d = md.getDatabase();
            ManagerModel.setDatabase(d);
            con.setDatabase(d);
            try {
                conn = con.getConnection();

                if (conn != null) {                  
                    //start monitoring
                    mc.setConnection(conn);
                    mc.setConnected(true);
                    mc.setStartMonitoring();
                    rv.dispose();
                } else {
                    JOptionPane.showMessageDialog(rv, "Error connecting to the database!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(rv, "Connection not selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
