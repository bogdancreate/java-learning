package aufgabe8;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class Taschenrechner extends JFrame {

    // GUI-Komponenten
    private JTextField tfX, tfY, tfResult;
    private JRadioButton rbDeg, rbRad;
    private JCheckBox cbHellesDisplay;
    private JButton btnAdd, btnSub, btnMul, btnDiv, btnSin, btnCos, btnPow, btnLog2, btnClear;

    // Standardfarben für das Display
    private final Color LIGHT_BG = Color.WHITE;
    private final Color LIGHT_FG = Color.BLACK;
    private final Color DARK_BG = Color.BLACK;
    private final Color DARK_FG = new Color(255, 200, 0); // Orange/Gelb

    public Taschenrechner() {
        setTitle("Taschenrechner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel für die Textfelder (Eingabe/Ausgabe)
        JPanel panelFields = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFields.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        panelFields.add(new JLabel("Operand x"));
        tfX = new JTextField("0");
        panelFields.add(tfX);

        panelFields.add(new JLabel("Operand y"));
        tfY = new JTextField("0");
        panelFields.add(tfY);

        panelFields.add(new JLabel("Resultat"));
        tfResult = new JTextField("0");
        tfResult.setEditable(false); // Resultat sollte nicht direkt editiert werden
        panelFields.add(tfResult);

        // 2. Panel für die Einstellungen (Deg, Rad, Helles Display)
        JPanel panelSettings = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        rbDeg = new JRadioButton("Deg", true);
        rbRad = new JRadioButton("Rad");
        ButtonGroup groupDegree = new ButtonGroup();
        groupDegree.add(rbDeg);
        groupDegree.add(rbRad);

        cbHellesDisplay = new JCheckBox("Helles Display", true);

        panelSettings.add(rbDeg);
        panelSettings.add(rbRad);
        panelSettings.add(cbHellesDisplay);

        // 3. Panel für die Operations-Buttons
        JPanel panelOperations = new JPanel(new GridLayout(2, 4, 5, 5));
        panelOperations.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnAdd = new JButton("+");
        btnMul = new JButton("*");
        btnSub = new JButton("-");
        btnDiv = new JButton("/");
        btnSin = new JButton("sin");
        btnCos = new JButton("cos");
        btnPow = new JButton("x^y");
        btnLog2 = new JButton("log2");

        panelOperations.add(btnAdd);
        panelOperations.add(btnMul);
        panelOperations.add(btnSub);
        panelOperations.add(btnDiv);
        panelOperations.add(btnSin);
        panelOperations.add(btnCos);
        panelOperations.add(btnPow);
        panelOperations.add(btnLog2);

        // 4. Panel für den Clear-Button
        JPanel panelClear = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelClear.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        btnClear = new JButton("Clear");
        panelClear.add(btnClear);

        // Zusammenbauen der Panels im Hauptfenster
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(panelFields);
        centerPanel.add(panelSettings);
        centerPanel.add(panelOperations);

        add(centerPanel, BorderLayout.CENTER);
        add(panelClear, BorderLayout.SOUTH);

        // Event-Handling registrieren
        initListeners();

        // Initiales Display-Design setzen
        updateDisplayTheme();

        pack();
        setLocationRelativeTo(null); // Zentrieren
    }

    private void initListeners() {
        // Theme-Umschalter
        cbHellesDisplay.addActionListener(e -> updateDisplayTheme());

        // Zweistellige Operationen
        btnAdd.addActionListener(e -> calculateBinary((x, y) -> x + y));
        btnSub.addActionListener(e -> calculateBinary((x, y) -> x - y));
        btnMul.addActionListener(e -> calculateBinary((x, y) -> x * y));
        btnDiv.addActionListener(e -> calculateBinary((x, y) -> y != 0 ? x / y : Double.NaN));
        btnPow.addActionListener(e -> calculateBinary((x, y) -> Math.pow(x, y)));

        // Einstellige Operationen
        btnSin.addActionListener(e -> calculateUnary(x -> rbDeg.isSelected() ? Math.sin(Math.toRadians(x)) : Math.sin(x)));
        btnCos.addActionListener(e -> calculateUnary(x -> rbDeg.isSelected() ? Math.cos(Math.toRadians(x)) : Math.cos(x)));
        btnLog2.addActionListener(e -> calculateUnary(x -> Math.log(x) / Math.log(2)));

        // Clear-Button
        btnClear.addActionListener(e -> {
            tfX.setText("0.000000");
            tfY.setText("0.000000");
            tfResult.setText("0.000000");
            resetFieldColor(tfX);
            resetFieldColor(tfY);
        });

        // KeyListener für die Fehler-Farbrücksetzung bei erneuter Eingabe
        KeyListener colorResetListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                resetFieldColor((JTextField) e.getSource());
            }
        };
        tfX.addKeyListener(colorResetListener);
        tfY.addKeyListener(colorResetListener);
    }

    // Hilfsmethode zur Theme-Anpassung
    private void updateDisplayTheme() {
        Color bg = cbHellesDisplay.isSelected() ? LIGHT_BG : DARK_BG;
        Color fg = cbHellesDisplay.isSelected() ? LIGHT_FG : DARK_FG;

        updateFieldTheme(tfX, bg, fg);
        updateFieldTheme(tfY, bg, fg);
        updateFieldTheme(tfResult, bg, fg);
    }

    private void updateFieldTheme(JTextField field, Color bg, Color fg) {
        field.setBackground(bg);
        // Falls das Feld aktuell wegen eines Fehlers rot ist, behalten wir Rot bei
        if (!field.getForeground().equals(Color.RED)) {
            field.setForeground(fg);
        }
    }

    private void resetFieldColor(JTextField field) {
        Color fg = cbHellesDisplay.isSelected() ? LIGHT_FG : DARK_FG;
        field.setForeground(fg);
    }

    // Funktionale Interfaces für Berechnungen
    interface BinaryOp { double apply(double x, double y); }
    interface UnaryOp { double apply(double x); }

    // Allgemeine Methode für +, -, *, /, x^y
    private void calculateBinary(BinaryOp op) {
        try {
            double x = parseOperand(tfX);
            double y = parseOperand(tfY);
            double res = op.apply(x, y);
            displayResult(res);
        } catch (NumberFormatException ex) {
            System.err.println("Fehler: Ungültige Eingabe bei den Operanden.");
        }
    }

    // Allgemeine Methode für sin, cos, log2
    private void calculateUnary(UnaryOp op) {
        try {
            double x = parseOperand(tfX);
            double res = op.apply(x);

            // Setzt Operand y laut Aufgabenstellung auf 0
            tfY.setText("0");
            resetFieldColor(tfY);

            displayResult(res);
        } catch (NumberFormatException ex) {
            System.err.println("Fehler: Ungültige Eingabe bei Operand x.");
        }
    }

    // Parser mit optionaler Fehler-Farbmarkierung
    private double parseOperand(JTextField field) throws NumberFormatException {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            field.setForeground(Color.RED);
            throw e;
        }
    }

    
    // Formatierte Ausgabe des Resultats
    private void displayResult(double result) {
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            tfResult.setText("Fehler");
        } else { tfResult.setText(String.format(Locale.US, "%.6f", result));
        }
    }

    public static void main(String[] args) {
        // Swing-Thread-Sicherheit garantieren
        SwingUtilities.invokeLater(() -> new Taschenrechner().setVisible(true));
    }
}