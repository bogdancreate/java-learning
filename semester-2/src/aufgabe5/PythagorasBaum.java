package aufgabe5;

import java.awt.Color;

public class PythagorasBaum {

    public static void main(String[] args) {
        // Die Leinwandeinrichtng
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-50, 50); // maßstab x  -50 - 50
        StdDraw.setYscale(0, 100);  // maßstab y  0 - 100

        // Doppelpufferung für schnelles Rendern aktivieren (Schalten Sie den Modus der Zeichnungsakkumulation im Speicher ein.)
        StdDraw.show(0);

        // Anfangsparameter: Basis bei (x=-5, y=0), Breite 10, Winkel 0
        drawTree1(0, 0, 10, 0);

        // Wir kopieren den gesamten vorbereiteten Baum aus dem Speicher auf den Bildschirm.
        StdDraw.show(0);
    }

    /**
     * rekursive Methode zum Zeichnen des pythagoreischen Baums
     * @param x, y   - Koordinaten der unteren linken Ecke (Punkt A)
     * @param w      - Seitenlänge eines Quadrats
     * @param gamma  - aktuelle Baumrotation (in Grad)
     */
    public static void drawTree1(double x, double y, double w, double gamma) {
        // Ende
        if (w < 0.175) return;

        //Zufällige Parameter für Neigung und Hoehe
        double delta = 10 + Math.random() * 50;
        double h = w * (0.5 + Math.random() * 1.0);

        //Umrechnung von Grad in Bogenmaß für Math.sin/cos
        double radG = Math.toRadians(gamma);
        double radD = Math.toRadians(delta);

        // Berechnung der Eckpunkte für ein Rechteck (Breite w, Höhe h)
        // Wir nutzen h für die vertikale Ausdehnung (Seiten A-D und B-C)
        double sH = h * Math.sin(radG);
        double cH = h * Math.cos(radG);
        double sW = w * Math.sin(radG);
        double cW = w * Math.cos(radG);

        double bx = x + cW;    double by = y + sW;
        double cx = bx - sH;   double cy = by + cH;
        double dx = x - sH;    double dy = y + cH;

        //berechnen den Scheitelpunkt des Dreiecks E.
        double u = w * Math.cos(radD);
        double ex = dx + u * Math.cos(radD + radG);
        double ey = dy + u * Math.sin(radD + radG);

        //Farbauswahl
        if (w < 0.5) {
            StdDraw.setPenColor(StdDraw.GREEN);
        } else {
            StdDraw.setPenColor(new Color(139, 69, 19)); // brauner Baumstamm
        }

        //zeichnen
        StdDraw.line(x, y, dx, dy);   // Linke Seite (A -> D)
        StdDraw.line(bx, by, cx, cy); // Rechte Seite (B -> C)

        // Рекурсия для левой ветви (на стороне DE)
        drawTree1(dx, dy, w * Math.cos(radD), gamma + delta);

        // Рекурсия для правой ветви (на стороне EC)
        drawTree1(ex, ey, w * Math.sin(radD), gamma + delta - 90);
    }
}
