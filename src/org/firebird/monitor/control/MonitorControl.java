
package org.firebird.monitor.control;

import org.firebird.monitor.db.*;
import org.firebird.monitor.model.MonAttachments;
import org.firebird.monitor.model.MonDatabase;
import org.firebird.monitor.model.MonStatements;
import org.firebird.monitor.model.MonStats;
import org.firebird.monitor.model.MonTransactions;
import org.firebird.monitor.view.*;
import static org.firebird.monitor.util.MonitorUtil.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.joda.time.Period;

public class MonitorControl {

    private static Connection conn;
    private MonitorView mv = new MonitorView(this);
    private LogSQLControl lc;
    private ManagerModel mmu;
    private ManagerMonDatabase mmp;
    private ManagerMonAttachments mmc;
    private ManagerMonTransactions mmt;
    private ManagerMonStats mmst;
    private ManagerMonStatements mms;
    private MonDatabase md;
    private List<MonAttachments> lma;
    private List<MonTransactions> lmt;
    private List<MonStatements> lms;
    private List<MonStats> lmst;
    private DefaultTableModel connections;
    private DefaultTableModel transactions;
    private Timer timer;
    private String time;
    private Timestamp nowServer;
    private int attachmentId = 0;
    private int transactionId = 0;
    private int statementId = 0;
    private int statId = 0;
    private int newStatId = 0;
    private int newTransactionId = 0;
    private int oldTransactionId = 0;
    private int newAttachmentId = 0;
    private boolean connected = false;
    private String odsVersion = "";
    private String timeExecute;

    public MonitorControl() {
        timer = new Timer(mv.getTimeUpdate(), refreshMonitor);        
        lc = new LogSQLControl(this, false);
        lc.renamelogSQL();
        mv.setLocationRelativeTo(null);
        mv.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mv.pack();
        mv.setVisible(true);
    }

    public boolean isConnected() {
        return connected;
    }

    public Boolean getMonitoring() {
        return timer.isRunning();
    }

    public MonitorView getMonitorView() {
        return mv;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public int getStatementId() {
        return statementId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getStatId() {
        return statId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getOdsVersion() {
        return odsVersion;
    }

    public void setConnection(Connection c) {
        conn = c;
        mmu = new ManagerModel();
        mmu.setConnection(conn);
        mmp = new ManagerMonDatabase(conn);
        mmc = new ManagerMonAttachments(conn);
        mmt = new ManagerMonTransactions(conn);
        mmst = new ManagerMonStats(conn);
        mms = new ManagerMonStatements(conn);
    }

    public void setRefreshMonitor() {
        refreshMonitorView();
        timer.setDelay(mv.getTimeUpdate());
    }
    ActionListener refreshMonitor = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            setRefreshMonitor();
        }
    };

    public void setStartMonitoring() {
        timer.start();
        mv.setStopButton();
    }

    public void setStopMonitoring() {
        if (isConnected() && getMonitoring()) {
            timer.stop();
            mv.setStartButton();
        }
    }

    public void openRegistrations() {
        closeConnection();
        setStopMonitoring();
        new RegistrationControl(this);
    }

    public void openAbout() {
        JOptionPane.showMessageDialog(mv, "jMonitorFirebird - Monitoring Process Database\n\n" +
                "Version: 1.2.9   -   Year: 2010\n\n" +
                "Programmer: Joel da Rosa\n\n" +
                "Contact: \n" +
                "webjoel@hotmail.com\n", "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void openLogSQL() {
        new LogSQLControl(this, true);
    }

    public void closeConnection() {
        try {
            if ((conn != null) && (!conn.isClosed())) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshMonitorView() {
        //properties        
        md = mmp.getProperties();
        mv.setDatabaseName(md.getDatabaseName());
        odsVersion = md.getOdsVersion();
        nowServer = mmu.getNow();

        //attachments
        lma = mmc.getAttachments();
        mv.setNumberAttachments(lma.size());
        connections = mv.getTableModelConnections();
        connections.setNumRows(0);
        for (MonAttachments a : lma) {
            Period p = new Period(a.getTimestamp().getTime(), nowServer.getTime());
            time = p.getHours() + ":" + p.getMinutes() + ":" + p.getSeconds();
            connections.addRow(new Object[]{a.getIp(), a.getState(), formatStringDateTime("HH:mm:ss", time), a.getId()});
        }

        //transactions
        int count = 0;
        if (mv.getChooseAttachment()) {
            newAttachmentId = attachmentId;
        } else {
            newAttachmentId = 0;
            attachmentId = 0;
        }

        lmt = mmt.getTransactions(newAttachmentId);
        mv.setNumberTransactions(lmt.size());
        transactions = mv.getTableModelTransactions();
        transactions.setNumRows(0);
        for (MonTransactions t : lmt) {
            if (count == 0) {
                newTransactionId = t.getId();
                newStatId = t.getStatId();
                count++;
            }

            Period p = new Period(t.getTimestamp().getTime(), nowServer.getTime());
            time = p.getHours() + ":" + p.getMinutes() + ":" + p.getSeconds();
            timeExecute = formatStringDateTime("HH:mm:ss", time);
            transactions.addRow(new Object[]{t.getServerPID(), t.getState(),
                        timeExecute, t.getIsolationMode(),
                        t.getLockTimeout(), t.getAttachmentId(), t.getId(), t.getUser(),
                        t.getStatId(), t.getReadOnly(), t.getAutoCommit(), t.getAutoUndo(),
                        t.getRemoteProcess()});
        }

        //statements
        if (count == 0) {
            mv.setSql("");
        }

        if (mv.getChooseTransaction()) {
            newTransactionId = transactionId;
            newStatId = statId;
        } else {
            transactionId = 0;
            statId = 0;
        }
        
        if (oldTransactionId != newTransactionId) {
            oldTransactionId = newTransactionId;
            lms = mms.getStatements(newTransactionId);
            mv.setSql("");
            for (MonStatements s : lms) {
                mv.setSql(s.getSqlText());
                statementId = s.getStatementId();
                //save log sql 
                lc.saveLogSQL(formatDateTimeSystem("MM/dd/yyyy HH:mm:ss"), s.getSqlText());
            }
        }
        mv.setTransactionLabel(newTransactionId);

        //io/record stats
        lmst = mmst.getStats(newStatId);
        mv.setInserts(Long.valueOf(0));
        mv.setUpdates(Long.valueOf(0));
        mv.setDeletes(Long.valueOf(0));
        mv.setPageReads("0");
        for (MonStats st : lmst) {
            mv.setInserts(st.getInserts());
            mv.setUpdates(st.getUpdates());
            mv.setDeletes(st.getDeletes());
            
            double readsKb = st.getPageReads() * (md.getPageSize() / 1024);
            if (readsKb >= 1024) {
                double readsMb = readsKb / 1024;
                if (readsMb >= 1024) {
                    mv.setPageReads(formatStringDecimal("0.00", readsMb / 1024) + " GB");
                } else {
                    mv.setPageReads(formatStringDecimal("0.00", readsMb) + " MB");
                }
            } else {
                mv.setPageReads(formatStringDecimal("0.00", readsKb) + " KB");
            }
        }
    }

    public void cancelConnection() {
        if (odsVersion.equals("11.2")) {
            if (attachmentId == 0) {
                //mmu.setUpdate("delete from mon$attachments where mon$attachment_id <> current_connection");
            } else {
                mmu.setUpdate("delete from mon$attachments where mon$attachment_id = " + attachmentId);
            }
        } else {
            JOptionPane.showMessageDialog(mv, "Firebird 2.5 requerid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelStatement() {
        if (newTransactionId == 0) {
            //mmu.setUpdate("delete from mon$statements where mon$attachment_id <> current_connection");
        } else {
            mmu.setUpdate("delete from mon$statements where mon$transaction_id = " + newTransactionId);
        }
    }
}
