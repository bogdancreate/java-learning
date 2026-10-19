
package aufgabe4;
import java.util.Locale;
/**
 * Hilfsklasse zur Verarbeitung und Validierung von Noten.
 *
 * @author Bohdan Cherednychenko
 * @version 09.12.2025
 */
public final class Noten {
    /**
     * Privater Konstruktor, um Instanziierung zu verhindern.
     */
    private Noten() {
    }
    /**
     * Beste mögliche Note.
     */
    public static final double BESTE = 6.0;
    /**
     * Schlechteste mögliche Note.
     */
    public static final double SCHLECHTESTE = 0.0;
    /**
     * Erzeugt eine IllegalArgumentException für unzulässige Noten.
     *
     * @param x die unzulässige Note als String
     * @return eine IllegalArgumentException mit Fehlermeldung
     */
    private static IllegalArgumentException fehler(String x) {
        return new IllegalArgumentException(
            "Unzulaessige Note " + x + " wird ignoriert!"
        );
    }
    /**
     * Prüft, ob eine Notenangabe als String zulässig ist.
     *
     * @param x die zu prüfende Note als String (z. B. "1,0" oder "2.3")
     * @return true, falls die Note zulässig ist
     * @throws IllegalArgumentException falls die Note unzulässig ist
     */
    public static boolean istZulaessig(String x) {
        if (x.length() != 3) {
            throw fehler(x);
        }
        char vorkomma = x.charAt(0);
        char split = x.charAt(1);
        char nachkomma = x.charAt(2);
        int v = vorkomma - '0';
        int n = nachkomma - '0';

        if (v < 1 || v > 5) {
            throw fehler(x);
        } else if ((split != ',' && split != '.')
                || !Character.isDigit(vorkomma)
                || !Character.isDigit(nachkomma)) {
            throw fehler(x);
        }

        switch (v) {
        case 1, 2, 3:
            switch (n) {
            case 0, 3, 7:
                return true;
            default:
                throw fehler(x);
            }
        case 4, 5:
            if (n != 0) {
                throw fehler(x);
            }
            return true;
        default:
            return false;
        }
    }
    /**
     * Wandelt eine gültige Notenangabe als String in einen double-Wert um.
     *
     * @param x die Note als String
     * @return die Note als double-Wert
     * @throws NumberFormatException falls der String nicht geparst werden kann
     */
    public static double toDouble(String x) {
        String notenorm = x.replace(',', '.');
        return Double.parseDouble(notenorm);
    }
    /**
     * Wandelt eine Note als double in eine String-Darstellung um.
     *
     * @param x die Note als double
     * @return die Note als String im deutschen Format
     * @throws IllegalArgumentException falls die Note außerhalb des gültigen Bereichs liegt
     */
    public static String toString(double x) {
        if (x < SCHLECHTESTE || x > BESTE) {
            throw new IllegalArgumentException(
                "Unzulaessige Note " + x + "!"
            );
        }
        return String.format(Locale.GERMAN, "%.1f", x);
    }
    /**
     * Prüft, ob eine Note bestanden ist.
     *
     * @param x die Note als double
     * @return true, falls die Note bestanden ist
     */
    public static boolean istBestanden(double x) {
        return x <= 4;
    }
    /**
     * Liefert die bessere (kleinere) von zwei Noten.
     *
     * @param x erste Note
     * @param y zweite Note
     * @return die bessere der beiden Noten
     */
    public static double bessere(double x, double y) {
        return x > y ? y : x;
    }
    /**
     * Liefert die schlechtere (größere) von zwei Noten.
     *
     * @param x erste Note
     * @param y zweite Note
     * @return die schlechtere der beiden Noten
     */
    public static double schlechtere(double x, double y) {
        return x < y ? y : x;
    }
}
