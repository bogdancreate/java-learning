package aufgabe5;

import java.awt.Color;

public class PythagorasTree {

    public static void main(String[] args) {
        // Die Leinwandeinrichtng
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-50, 50); // maßstab x  -50 - 50
        StdDraw.setYscale(0, 100);  // maßstab y  0 - 100

        // Doppelpufferung für schnelles Rendern aktivieren (Schalten Sie den Modus der Zeichnungsakkumulation im Speicher ein.)
        StdDraw.show(0);

        // Anfangsparameter: Basis bei (x=-5, y=0), Breite 10, Winkel 0
        drawTree(0, 20, 10, 0, 30);

        // Wir kopieren den gesamten vorbereiteten Baum aus dem Speicher auf den Bildschirm.
        StdDraw.show(0);
    }

    /**
     * rekursive Methode zum Zeichnen des pythagoreischen Baums
     * @param x, y   - Koordinaten der unteren linken Ecke (Punkt A)
     * @param w      - Seitenlänge eines Quadrats
     * @param gamma  - aktuelle Baumrotation (in Grad)
     * @param delta  - relativer Neigungswinkel (30)
     */
    public static void drawTree(double x, double y, double w, double gamma, double delta) {
        // Ende
        if (w < 0.175) return;

        //Umrechnung von Grad in Bogenmaß für Math.sin/cos
        double radG = Math.toRadians(gamma);
        double radD = Math.toRadians(delta);

        double s = w * Math.sin(radG);
        double c = w * Math.cos(radG);

        //berechnen die Koordinaten der verbleibenden Ecken des Quadrats.
        double bx = x + c;         double by = y + s;
        double cx = x + c - s;     double cy = y + s + c;
        double dx = x - s;         double dy = y + c;

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
        drawTree(dx, dy, w * Math.cos(radD), gamma + delta, delta);

        // Рекурсия для правой ветви (на стороне EC)
        drawTree(ex, ey, w * Math.sin(radD), gamma + delta - 90, delta);
    }
}
