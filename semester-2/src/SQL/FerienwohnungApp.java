package SQL;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class FerienwohnungApp extends JFrame {
    // DB-Verbindungsdaten
    private static final String DB_URL = "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c";
    private static final String DB_USER = "dbsys05";
    private static final String DB_PASS = "dbsys05";

    // SCHEMA
    private static final String SCHEMA = "DBSYS12.";

    private Connection conn;

    // GUI
    private JComboBox<String> cbLand, cbAusstattung;
    private JTextField tfAnreise, tfAbreise, tfKundenMail;
    private JTextArea taResultate;
    private JButton btnSuchen, btnBuchen;
    private JList<String> listWohnungen;
    private DefaultListModel<String> listModel;

    public FerienwohnungApp() {
        iConnection();
        iGUI();
    }

    private void iConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            conn.setAutoCommit(false); //Commit/Rollback
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler bei der DB-Verbindung: " + e.getMessage());
            System.exit(1);
        }
    }

    private void iGUI() {
        setTitle("Ferienwohnung Buchungssystem (Kunden-Interface)");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Obere panel suchen
        JPanel pnlSuche = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlSuche.setBorder(BorderFactory.createTitledBorder("1. Suche nach Ferienwohnungen"));

        pnlSuche.add(new JLabel("Land:"));

        cbLand = new JComboBox<>(getDropdownDaten("LAND_NAME", SCHEMA + "LAND"));
        pnlSuche.add(cbLand);

        pnlSuche.add(new JLabel("Ausstattung (Optional):"));
        cbAusstattung = new JComboBox<>();
        cbAusstattung.addItem("- Keine Auswahl -");
        for(String ausstattung : getDropdownDaten("BEZEICHNUNG", SCHEMA + "AUSSTATTUNG")) {
            cbAusstattung.addItem(ausstattung);
        }
        pnlSuche.add(cbAusstattung);

        pnlSuche.add(new JLabel("Anreise (JJJJ-MM-TT):"));
        tfAnreise = new JTextField("2026-07-01");
        pnlSuche.add(tfAnreise);

        pnlSuche.add(new JLabel("Abreise (JJJJ-MM-TT):"));
        tfAbreise = new JTextField("2026-07-15");
        pnlSuche.add(tfAbreise);

        btnSuchen = new JButton("Wohnungen suchen");
        pnlSuche.add(btnSuchen);

        add(pnlSuche, BorderLayout.NORTH);

        // zentrale panel, ergebniss
        JPanel pnlMitte = new JPanel(new GridLayout(1, 2, 10, 10));
        listModel = new DefaultListModel<>();
        listWohnungen = new JList<>(listModel);
        pnlMitte.add(new JScrollPane(listWohnungen));

        taResultate = new JTextArea();
        taResultate.setEditable(false);
        pnlMitte.add(new JScrollPane(taResultate));

        add(pnlMitte, BorderLayout.CENTER);

        // untere panel buchen
        JPanel pnlUnten = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlUnten.setBorder(BorderFactory.createTitledBorder("2. Buchen"));
        pnlUnten.add(new JLabel("Ihre Mailadresse:"));
        tfKundenMail = new JTextField(25);
        pnlUnten.add(tfKundenMail);

        btnBuchen = new JButton("Ausgewählte Wohnung buchen");
        pnlUnten.add(btnBuchen);

        add(pnlUnten, BorderLayout.SOUTH);

        // ActionListener
        btnSuchen.addActionListener(e -> sucheWohnungen());
        btnBuchen.addActionListener(e -> bucheWohnung());
    }

    // Hilfsmethode zum dynamischen Erstellen von Listen
    private String[] getDropdownDaten(String spalte, String tabelle) {
        ArrayList<String> daten = new ArrayList<>();
        String sql = "SELECT DISTINCT " + spalte + " FROM " + tabelle + " WHERE " + spalte + " IS NOT NULL ORDER BY " + spalte;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                daten.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Dropdown-Fehler (" + tabelle + "): " + e.getMessage());
        }
        return daten.toArray(new String[0]);
    }

    // USE CASE 1: (Teil h)
    private void sucheWohnungen() {
        listModel.clear();
        taResultate.setText("");

        String land = (String) cbLand.getSelectedItem();
        String ausstattung = (String) cbAusstattung.getSelectedItem();
        String anreise = tfAnreise.getText().trim(); // YYYY-MM-DD
        String abreise = tfAbreise.getText().trim(); // YYYY-MM-DD

        if (land == null || anreise.isEmpty() || abreise.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte Land, Anreise- und Abreisedatum angeben!");
            return;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f.WOHN_NAME, AVG(b.ANZAHL_STERNE) AS durchschnitts_bewertung ")
                .append("FROM DBSYS12.FERIENWOHNUNG f ");

        // Wenn die Ausstattung ausgewählt ist, füge JOIN mit der HAT-Tabelle hinzu.
        if (!ausstattung.equals("- Keine Auswahl -")) {
            sql.append("JOIN DBSYS12.HAT h ON f.WOHN_NAME = h.WOHN_NAME ");
        }

        sql.append("JOIN DBSYS12.ADRESSE a ON f.ADRESSE_ID = a.ADRESSE_ID ")
                .append("LEFT JOIN DBSYS12.BUCHUNG b ON f.WOHN_NAME = b.WOHN_NAME ")
                .append("WHERE a.LAND_NAME = ? "); // Das erste Zeichen '?' -> Land

        // Wenn die Ausstattung ausgewählt ist
        if (!ausstattung.equals("- Keine Auswahl -")) {
            sql.append("  AND h.BEZEICHNUNG = ? "); // Zweites Zeichen '?' -> Ausstattung (falls ausgewählt)
        }

        // NOT EXISTS
        sql.append("  AND NOT EXISTS ( ")
                .append("      SELECT 1 ")
                .append("      FROM DBSYS12.BUCHUNG b2 ")
                .append("      WHERE b2.WOHN_NAME = f.WOHN_NAME ")
                .append("        AND b2.BEGIN_DATUM <= TO_DATE(?, 'YYYY-MM-DD') ") // Abreise
                .append("        AND b2.END_DATUM >= TO_DATE(?, 'YYYY-MM-DD') ")   // Anreise
                .append("        AND b2.STORNO_DATUM IS NULL ")
                .append("  ) ")
                .append("GROUP BY f.WOHN_NAME ")
                .append("ORDER BY durchschnitts_bewertung DESC NULLS LAST");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // 1. Land
            pstmt.setString(paramIndex++, land);

            // 2. Ausstattung
            if (!ausstattung.equals("- Keine Auswahl -")) {
                pstmt.setString(paramIndex++, ausstattung);
            }

            // 3. NOT EXISTS
            pstmt.setString(paramIndex++, abreise);
            pstmt.setString(paramIndex++, anreise);

            try (ResultSet rs = pstmt.executeQuery()) {
                StringBuilder details = new StringBuilder("Suchergebnisse (nach Bewertung sortiert):\n=================================\n");
                while (rs.next()) {
                    String name = rs.getString("WOHN_NAME"); // Внутри getString имена колонок регистронезависимы, кавычки не нужны
                    double sterne = rs.getDouble("durchschnitts_bewertung");
                    String sterneText = rs.wasNull() ? "Keine Bewertungen" : String.format("%.1f / 5 Sterne", sterne);

                    listModel.addElement(name);
                    details.append(name).append(" -> ").append(sterneText).append("\n");
                }
                taResultate.setText(details.toString());
                if (listModel.isEmpty()) {
                    taResultate.setText("Keine freien Ferienwohnungen für diesen Zeitraum gefunden.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler bei der Suche: " + e.getMessage());
        }
    }

    // USE CASE 2: Buchen
    private void bucheWohnung() {
        String ausgewaehlteWohnung = listWohnungen.getSelectedValue();
        String mail = tfKundenMail.getText().trim();
        String anreise = tfAnreise.getText().trim();
        String abreise = tfAbreise.getText().trim();

        if (ausgewaehlteWohnung == null || mail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte eine Wohnung auswählen und E-Mailadresse eingeben!");
            return;
        }

        // Automatische Generierung der nächsten Buchungs-ID
        String sqlMaxId = "SELECT NVL(MAX(BUCHUNGSNUMMER), 0) + 1 FROM " + SCHEMA + "BUCHUNG";
        String sqlInsert = "INSERT INTO " + SCHEMA + "BUCHUNG (BUCHUNGSNUMMER, BEGIN_DATUM, END_DATUM, MAILADRESSE, WOHN_NAME) " +
                "VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";

        try (Statement stmtId = conn.createStatement();
             ResultSet rsId = stmtId.executeQuery(sqlMaxId);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

            int neueBuchungsNummer = 1;
            if (rsId.next()) {
                neueBuchungsNummer = rsId.getInt(1);
            }

            pstmtInsert.setInt(1, neueBuchungsNummer);
            pstmtInsert.setString(2, anreise);
            pstmtInsert.setString(3, abreise);
            pstmtInsert.setString(4, mail);
            pstmtInsert.setString(5, ausgewaehlteWohnung);

            pstmtInsert.executeUpdate();

            // Alles gut -> speichern Transaktion.
            conn.commit();
            JOptionPane.showMessageDialog(this, "Erfolgreich gebucht! Ihre Buchungsnummer: " + neueBuchungsNummer);

            // Wir aktualisieren die Liste, da die Wohnung für diesen Zeitraum nun belegt ist.
            sucheWohnungen();

        } catch (SQLException e) {
            // Fehler -> Transaktion rückgängig machen
            try {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Buchung fehlgeschlagen! Rollback ausgeführt.\nFehler: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Rollback-Fehler: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new FerienwohnungApp().setVisible(true);
    }
}


