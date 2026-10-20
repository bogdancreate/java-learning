package SQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SearchFrame extends JFrame {
    // Logger
    private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger(SearchFrame.class.getName());

    private String dbUserMail;
    private String dbUserPassword;
    private final String dbAdminName = "dbsys39";
    private final String dbAdminPassword = "000000";
    private boolean istEingeloggt = false;

    private final JComboBox<String> comboLand;
    private final JComboBox<String> comboAusstattung;
    private JTextField txtAnreise;
    private JTextField txtAbreise;
    JButton btnSuchen;
    JButton btnBuchen;
    JButton btnEinloggen;

    // Table
    private final JTable resultTable;
    private final DefaultTableModel tableModel;

    public SearchFrame() {

        setTitle("Suche nach Ferienwohnungen");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for Filters
        JPanel filterPanel = new JPanel(new GridLayout(0,1,5,10));
        filterPanel.add(new JLabel("Land:"));
        comboLand = new JComboBox<>();
        comboAusstattung = new JComboBox<>();
        loadCountriesAndAusstattung();
//        comboLand.addItem("Deutschland");
//        comboLand.addItem("Spanien");
//        comboLand.addItem("Schweiz");
//        comboLand.addItem("Polen");
//        comboLand.addItem("Österreich");
        filterPanel.add(comboLand);
        filterPanel.add(new JLabel("Anreise (DD.MM.JJJJ)"));
        txtAnreise = new JTextField(10);
        txtAnreise = new JTextField("12.03.2006");
        filterPanel.add(txtAnreise);
        filterPanel.add(new JLabel("Abreise (DD.MM.JJJJ)"));
        txtAbreise = new JTextField(10);
        txtAbreise = new JTextField("15.03.2006");
        filterPanel.add(txtAbreise);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Optional Block Ausstattung
        filterPanel.add(new JLabel("Ausstattung:"));
//        comboAusstattung = new JComboBox<>();
//        comboAusstattung.addItem("-alle-");
//        comboAusstattung.addItem("Pool");
//        comboAusstattung.addItem("Sauna");
        filterPanel.add(comboAusstattung);

        add(filterPanel, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSuchen = new JButton("Suchen"));
        buttonPanel.add(btnBuchen = new JButton("Buchen"));
        buttonPanel.add(btnEinloggen = new JButton("Einloggen"));

        add(buttonPanel, BorderLayout.SOUTH);
        // Table for Results
        String[] columns = {"Wohnungsname", "Durchschnittliche Bewertung"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);
        // Button "Buchen" ist nicht drückbar bevor man eine Zeile ausgewählt hat
        btnBuchen.setEnabled(false);
        resultTable.getSelectionModel().addListSelectionListener(_ -> btnBuchen.setEnabled(resultTable.getSelectedRow() != -1));

        // Search Button realization
        btnSuchen.addActionListener(_ -> {
            String selectedLand = (String) comboLand.getSelectedItem();
            String ausstattung = (String) comboAusstattung.getSelectedItem();
            if (txtAnreise.getText().isEmpty() || txtAbreise.getText().isEmpty()) {
                System.out.println("Bitte geben Sie sowohl Anreise- als auch Abreisedatum ein.");
                return;
            }
            String anreise = txtAnreise.getText();
            String abreise = txtAbreise.getText();
            assert ausstattung != null;
            dbSearch(selectedLand, anreise, abreise, ausstattung);
        });
        
        btnBuchen.addActionListener(_ -> dbBook());
        btnEinloggen.addActionListener(_ -> {
            if(!istEingeloggt){
                dbEinloggen();
            }else {
                JOptionPane.showMessageDialog(this, "Sie sind schon eingeloggt");
            }});
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadCountriesAndAusstattung() {
        comboLand.removeAllItems();
        comboAusstattung.removeAllItems();
        comboAusstattung.addItem("-alle-"); // Default option for Ausstattung
        Connection conn = null;
        ResultSet rset = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String url = "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c";
            conn = DriverManager.getConnection(url, dbAdminName, dbAdminPassword);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            String landname = "SELECT landname FROM dbsys39.land";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(landname)) {

                while (rs.next()) {
                    String country = rs.getString("landname");
                    comboLand.addItem(country);
                }

            }
            String sqlAusstattung = "SELECT typ FROM dbsys39.ausstattung ";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlAusstattung)) {
                while (rs.next()) {
                    comboAusstattung.addItem(rs.getString("typ"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database login error: " + e.getMessage());
        } finally {
            try {
                if (rset != null) rset.close();
            } catch (Exception _) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception _) {
            }
        }

    }


    private void dbEinloggen() {
        do {
            dbUserMail = JOptionPane.showInputDialog(this, "Bitte geben Sie Ihre E-Mail ein:", "E-Mail Eingabe", JOptionPane.PLAIN_MESSAGE);
            if (dbUserMail == null) {
                istEingeloggt = false;
                return;   // Benutzer hat Abrechnen gedrückt
            }
        } while (dbUserMail.trim().isEmpty());
        do {
            dbUserPassword = JOptionPane.showInputDialog(this, "Bitte geben Sie Ihr Passwort ein:", "Passwort Eingabe", JOptionPane.PLAIN_MESSAGE);
            if (dbUserPassword == null) {
                istEingeloggt = false;
                return;   // Benutzer hat Abrechnen gedrückt
            }
        } while (dbUserPassword.trim().isEmpty());

        Connection conn = null;
        PreparedStatement stmtCheckUser = null;
        ResultSet rset = null;

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String url = "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c";
            conn = DriverManager.getConnection(url, dbAdminName, dbAdminPassword);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            String sqlCheckUser = "SELECT mail FROM dbsys39.Kunde WHERE mail = ? AND password = ?";
            stmtCheckUser = conn.prepareStatement(sqlCheckUser);
            stmtCheckUser.setString(1, dbUserMail);
            stmtCheckUser.setString(2, dbUserPassword);

            rset = stmtCheckUser.executeQuery();

            if (!rset.next()) {
                JOptionPane.showMessageDialog(this,
                        "Der Kunde mit E-Mail '" + dbUserMail + "' ist nicht registriert",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }else {
                istEingeloggt = true;
                JOptionPane.showMessageDialog(this, "Erfolgreich eingeloggt!");
            }
        } catch (SQLException e) {
            System.err.println("Database login error: " + e.getMessage());
        } finally {
            try { if (rset != null) rset.close(); } catch (Exception _) {}
            try { if (stmtCheckUser != null) stmtCheckUser.close(); } catch (Exception _) {}
            try { if (conn != null) conn.close(); } catch (Exception _) {}
        }
        if (istEingeloggt) {
            System.out.println("Eingeloggt als: " + dbUserMail);
        }
    }

    private boolean dbEinloggen(Connection conn) {
        do {
            dbUserMail = JOptionPane.showInputDialog(this, "Bitte geben Sie Ihre E-Mail ein:", "E-Mail Eingabe", JOptionPane.PLAIN_MESSAGE);
            if (dbUserMail == null) {
                return false;   // Benutzer hat Abrechnen gedrückt
            }
        } while (dbUserMail.trim().isEmpty());
        do {
            dbUserPassword = JOptionPane.showInputDialog(this, "Bitte geben Sie Ihr Passwort ein:", "Passwort Eingabe", JOptionPane.PLAIN_MESSAGE);
            if (dbUserPassword == null) {
                return false;   // Benutzer hat Abrechnen gedrückt
            }
        } while (dbUserPassword.trim().isEmpty());

        PreparedStatement stmtCheckUser = null;
        ResultSet rset = null;
        boolean loginSuccess = false;

        try {
            String sqlCheckUser = "SELECT mail FROM dbsys39.Kunde WHERE mail = ? AND password = ?";
            stmtCheckUser = conn.prepareStatement(sqlCheckUser);
            stmtCheckUser.setString(1, dbUserMail);
            stmtCheckUser.setString(2, dbUserPassword);

            rset = stmtCheckUser.executeQuery();

            if (rset.next()) {
                loginSuccess = true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Der Kunde mit E-Mail '" + dbUserMail + "' ist nicht registriert",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("Database login error: " + e.getMessage());
        } finally {
            try { if (rset != null) rset.close(); } catch (Exception _) {}
            try { if (stmtCheckUser != null) stmtCheckUser.close(); } catch (Exception _) {}
        }
        if (!istEingeloggt && loginSuccess) {
            istEingeloggt = true;
        }
        if (istEingeloggt) {
            System.out.println("Eingeloggt als: " + dbUserMail);
        }
        return loginSuccess;
    }

    private void dbBook() {
        int selectedRow = resultTable.getSelectedRow();
        // Keine Zeile ist ausgewählt
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Ferienwohnung aus der Tabelle aus.", "Keine Auswahl", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String wohnungsname = resultTable.getValueAt(selectedRow, 0).toString();
        String anreise = txtAnreise.getText();
        String abreise = txtAbreise.getText();


        Connection conn = null;
        PreparedStatement stmtGetID = null;
        PreparedStatement stmtInsert = null;
        ResultSet rset = null;

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String url = "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c";
            conn = DriverManager.getConnection(url, this.dbAdminName, this.dbAdminPassword);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            if (!istEingeloggt) {
                if (!dbEinloggen(conn)) {
                    conn.close();
                    istEingeloggt = false;
                    return; // User konnte sich nicht einloggen, Buchung wird abgebrochen
                }
            }
            // Gucken, ob die Wohnung noch verfügbar ist SELECT COUNT liefert 0 wenn nicht verfügbar und 1 wenn verfügbar
            String sqlVerify = "SELECT COUNT(*) FROM dbsys39.Buchung " +
                    "WHERE wohnungsname = ? " +
                    "AND startdatum <= TO_DATE(?, 'DD.MM.YYYY') " +
                    "AND enddatum >= TO_DATE(?, 'DD.MM.YYYY')";

            try (PreparedStatement stmtVerify = conn.prepareStatement(sqlVerify)) {
                stmtVerify.setString(1, wohnungsname);
                stmtVerify.setString(2, abreise);
                stmtVerify.setString(3, anreise);

                try (ResultSet rsetVerify = stmtVerify.executeQuery()) {
                    if (rsetVerify.next() && rsetVerify.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this,
                                "Diese Ferienwohnung ist in diesem Zeitraum leider nicht mehr verfügbar!",
                                "Buchungsfehler",
                                JOptionPane.ERROR_MESSAGE);
                        conn.rollback();
                        return;
                    }
                }
            }
            // Neues BuchungsID
            String sqlGetId = "SELECT COALESCE(MAX(buchungsnummer), 0) + 1 FROM dbsys39.Buchung";
            stmtGetID = conn.prepareStatement(sqlGetId);
            rset = stmtGetID.executeQuery();

            int neueBuchung = 1;
            if (rset.next()) {
                neueBuchung = rset.getInt(1);
            }
            // Insert die Daten
            String sqlInsert = "INSERT INTO dbsys39.Buchung (buchungsnummer, startdatum, enddatum, wohnungsname, mail, buchungsdatum) " +
                    "VALUES (?, TO_DATE(?, 'DD.MM.YYYY'), TO_DATE(?, 'DD.MM.YYYY'), ?, ?, SYSDATE)";
            stmtInsert = conn.prepareStatement(sqlInsert);
            stmtInsert.setInt(1, neueBuchung);      // 1 Buchungsnummer
            stmtInsert.setString(2, anreise);       // 2 Anreise
            stmtInsert.setString(3, abreise);       // 3 Abreise
            stmtInsert.setString(4, wohnungsname);  // 4 Wohnungsname
            stmtInsert.setString(5, dbUserMail);     // 5 Mail
            stmtInsert.executeUpdate();


            conn.commit();
            JOptionPane.showMessageDialog(this, "Erfolgreich gebucht! Buchungsnummer: " + neueBuchung);

        } catch (SQLException se) {
            LOGGER.log(java.util.logging.Level.SEVERE, "SQL Exception during booking. Rolling back...", se);
            if (conn != null) {
                try {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Fehler bei der Buchung! Dieses Datum ist schon ausgebucht");
                } catch (SQLException ex) {
                    LOGGER.log(java.util.logging.Level.SEVERE, "Database search failed", ex);
                }
            }
        } finally {
            // Alle Resource schließen
            try { if (rset != null) rset.close(); } catch (Exception _) {}
            try { if (stmtGetID != null) stmtGetID.close(); } catch (Exception _) {}
            try { if (stmtInsert != null) stmtInsert.close(); } catch (Exception _) {}
            try { if (conn != null) conn.close(); } catch (Exception _) {}
        }
    }

    private void dbSearch(String land, String anreise, String abreise, String ausstattung) {
        // Clear Table
        tableModel.setRowCount(0);

        String sql = "SELECT f.wohnungsname, AVG(b.anzahlsterne) as bew " +
                "FROM dbsys39.Ferienwohnung f " +
                "LEFT JOIN dbsys39.Buchung b ON f.wohnungsname = b.wohnungsname " +
                "JOIN dbsys39.Adress a ON f.adress_id = a.adress_id " +
                "WHERE a.landname = ? ";

        boolean filterByAusstattung = !ausstattung.equals("-alle-");
        if (filterByAusstattung) {
            sql += " AND f.wohnungsname IN (SELECT h.wohnungsname FROM dbsys39.hat h WHERE h.typ = ?) ";
        }

        sql += " AND f.wohnungsname NOT IN ( " +
                "      SELECT b2.wohnungsname FROM dbsys39.Buchung b2 " +
                "      WHERE b2.startdatum <= TO_DATE(?, 'DD.MM.YYYY') " +
                "        AND b2.enddatum >= TO_DATE(?, 'DD.MM.YYYY') " +
                "  ) " +
                "GROUP BY f.wohnungsname ORDER BY bew DESC NULLS LAST";


        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String url = "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c";
            conn = DriverManager.getConnection(url, this.dbAdminName, this.dbAdminPassword);


            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql);
            if (filterByAusstattung) {
                stmt.setString(1, land);         // 1 Land
                stmt.setString(2, ausstattung);  // 2 Ausstattung
                stmt.setString(3, abreise);      // 3 Abreise
                stmt.setString(4, anreise);      // 4 Anreise
            } else {
                // -alle- selected, so we don't filter by Ausstattung
                stmt.setString(1, land);         // 1 Land
                stmt.setString(2, abreise);      // 2 Abreise
                stmt.setString(3, anreise);      // 3 Anreise
            }


            rset = stmt.executeQuery();


            while (rset.next()) {
                String wohnungsname = rset.getString("wohnungsname");
                double bewertung = rset.getDouble("bew");
                String bewText = rset.wasNull() ? "Keine Bewertungen" : String.format("%.1f", bewertung);

                tableModel.addRow(new Object[]{wohnungsname, bewText});
            }

            conn.commit();

        } catch (SQLException se) {
            System.out.println();
            System.out.println("SQL Exception occurred while establishing connection to DBS: " + se.getMessage());
            System.out.println("- SQL state  : " + se.getSQLState());
            System.out.println("- Message    : " + se.getMessage());
            System.out.println("- Vendor code: " + se.getErrorCode());
            System.out.println();
            System.out.println("EXITING WITH FAILURE ... !!!");
            System.out.println();

            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                LOGGER.log(java.util.logging.Level.SEVERE, "Database search failed", e);
            }

        } finally {
            try { if (rset != null) rset.close(); } catch (Exception _) {}
            try { if (stmt != null) stmt.close(); } catch (Exception _) {}
            try { if (conn != null) conn.close(); } catch (Exception _) {}
        }
    }


    static void main() {
        SwingUtilities.invokeLater(SearchFrame::new);

    }
}
