package aufgabe5;

import java.awt.Color;

import java.awt.Color;

public class PythagorasBaum1 {

    public static void main(String[] args) {
        // Konfiguration des Fensters
        StdDraw.setCanvasSize(1000, 600);
        StdDraw.setXscale(-50, 50);
        StdDraw.setYscale(0, 60);

        // Doppelte Pufferung für schnelles Zeichnen aktivieren
        StdDraw.show(0);

        // Zeichne Variante 1 (Links auf dem Bild)
        // Startet bei x=-25, y=0 mit einer Breite von 8
        drawVariante1(-25, 0, 8, 0, 30);

        // Zeichne Variante 2 (Rechts auf dem Bild)
        // Startet bei x=15, y=0
        drawVariante2(15, 0, 8, 0);

        // Alles auf dem Bildschirm anzeigen
        StdDraw.show(0);
    }

    /**
     * Variante 1: Konstanter Neigungswinkel delta.
     * Zeichnet nur die Seitenwände, um den Effekt aus Abb. 1 zu erzielen.
     */
    public static void drawVariante1(double x, double y, double w, double gamma, double delta) {
        if (w < 0.2) return; // Abbruchbedingung

        double radG = Math.toRadians(gamma);
        double radD = Math.toRadians(delta);

        // Berechnung der Eckpunkte nach den trigonometrischen Formeln
        double s = w * Math.sin(radG);
        double c = w * Math.cos(radG);

        double bx = x + c;     double by = y + s;
        double cx = x + c - s; double cy = y + s + c;
        double dx = x - s;     double dy = y + c;

        // Farbwahl: Braun für Äste, Grün für feine Zweige
        if (w < 1.2) StdDraw.setPenColor(StdDraw.GREEN);
        else StdDraw.setPenColor(new Color(150, 75, 0));

        // WICHTIG: Nur die zwei seitlichen Linien zeichnen (kein Boden, kein Deckel)
        StdDraw.line(x, y, dx, dy);   // Linke Seite (A -> D)
        StdDraw.line(bx, by, cx, cy); // Rechte Seite (B -> C)

        // Berechnung von Punkt E (Verzweigungspunkt)
        double u = w * Math.cos(radD);
        double ex = dx + u * Math.cos(radD + radG);
        double ey = dy + u * Math.sin(radD + radG);

        // Rekursive Aufrufe für die nächsten Stufen
        drawVariante1(dx, dy, w * Math.cos(radD), gamma + delta, delta);
        drawVariante1(ex, ey, w * Math.sin(radD), gamma + delta - 90, delta);
    }

    /**
     * Variante 2: Zufällige Winkel und zufällige Höhen (Rechtecke).
     */
    public static void drawVariante2(double x, double y, double w, double gamma) {
        if (w < 0.2) return;

        // Zufällige Parameter für natürliches Aussehen
        double delta = 20 + Math.random() * 50;
        double h = w * (0.6 + Math.random() * 0.8); // Zufällige Höhe h

        double radG = Math.toRadians(gamma);
        double radD = Math.toRadians(delta);

        // Berechnung für Rechteck-Seiten
        double sH = h * Math.sin(radG);
        double cH = h * Math.cos(radG);
        double sW = w * Math.sin(radG);
        double cW = w * Math.cos(radG);

        double bx = x + cW;    double by = y + sW;
        double cx = bx - sH;   double cy = by + cH;
        double dx = x - sH;    double dy = y + cH;

        if (w < 1.2) StdDraw.setPenColor(StdDraw.GREEN);
        else StdDraw.setPenColor(new Color(150, 75, 0));

        // Nur Seitenlinien zeichnen
        StdDraw.line(x, y, dx, dy);
        StdDraw.line(bx, by, cx, cy);

        // Punkt E für die Verzweigung
        double u = w * Math.cos(radD);
        double ex = dx + u * Math.cos(radD + radG);
        double ey = dy + u * Math.sin(radD + radG);

        drawVariante2(dx, dy, w * Math.cos(radD), gamma + delta);
        drawVariante2(ex, ey, w * Math.sin(radD), gamma + delta - 90);
    }
}
