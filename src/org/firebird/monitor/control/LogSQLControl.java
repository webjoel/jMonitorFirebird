
package org.firebird.monitor.control;

import org.firebird.monitor.view.LogSQLView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

public class LogSQLControl {

    private final String DIR_LOG = System.getProperty("user.dir");
    private String dist;
    private File f;
    private MonitorControl mc;
    private LogSQLView lv;
    private String newLine;

    public LogSQLControl(MonitorControl m, boolean open) {
        dist = DIR_LOG + "/sql.log";
        f = new File(dist);
        newLine = System.getProperty("line.separator");
        
        if (open) {
            mc = m;
            lv = new LogSQLView(mc.getMonitorView(), true, this);
            readLogSQL(dist);
            lv.setLocationRelativeTo(null);
            lv.pack();
            lv.setVisible(true);
        }
    }

    public void saveLogSQL(String now, String sql) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(f, true));
            bf.write(now + newLine + newLine);
            bf.write(sql + newLine + newLine);
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readLogSQL(String d) {
        try {
            FileInputStream stream = new FileInputStream(d);
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder str = new StringBuilder();
            String line = null;
            lv.clearLogSQL();
            while ((line = reader.readLine()) != null) {
                lv.addLogSQL(line + newLine);
            }
        } catch (FileNotFoundException e) {
            lv.addLogSQL("");
        } catch (IOException e) {
            e.printStackTrace();
            lv.addLogSQL("");
        }
    }

    public void loadLogSQL() {
        JFileChooser fc = new JFileChooser();
        
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setCurrentDirectory(new File(DIR_LOG));

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fo = fc.getSelectedFile();
            readLogSQL(fo.getPath());
        } else {
            JOptionPane.showMessageDialog(lv, "No file selected!", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void renamelogSQL() {
        f.renameTo(new File(DIR_LOG + "/sql_" + System.currentTimeMillis() + ".log"));
    }
}
