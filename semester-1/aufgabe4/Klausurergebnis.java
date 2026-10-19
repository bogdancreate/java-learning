// Klausurergebnis.java
package aufgabe4;
//import aufgabe4.schweiz.Noten;
import java.util.Locale;
import java.util.Scanner;

/**
 * Klausurergebnis erstellt eine Notenstatistik.
 * <p>
 * Das Programm liest Noten als Strings ein und bestimmt die beste
 * und die schlechteste Note, den Durchschnitt der Bestandenen
 * sowie die Durchfallquote in Prozent.
 * Das Programm beruecksichtigt dabei nur die laut Noten.istZulaessig
 * erlaubten Noten. Andere Noten werden unter Ausgabe einer Warnung ignoriert.
 * Welche Noten besser und schlechter sind, welche als bestanden oder
 * durchgefallen gelten und wie die String-Darstellung der Noten aussieht,
 * wird mit Methoden der Klasse Noten bestimmt.
 * </p>
 * Das Programm gibt als Klausurergebnis folgende Werte aus:
 * <ul>
 * <li>die Anzahl der beruecksichtigten Noten</li>
 * <li>die Anzahl der Bestandenen</li>
 * <li>die beste Note</li>
 * <li>die schlechteste Note</li>
 * <li>den Durchschnitt der Bestandenen</li>
 * <li>die Durchfallquote</li>
 * </ul>
 *
 * @author Bohdan Cherednychenko
 * @version 09.12.2025
 */
public final class Klausurergebnis {
    private Klausurergebnis() { }

    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMAN);
        int zeahler = 0;
        double summe = 0;
        int passed = 0;
        double best = Noten.BESTE;
        double worst = Noten.SCHLECHTESTE;
        final double hundert = 100;
        // Noten einlesen
        System.out.println("Noten Ganze,Zehntel oder Ganze.Zehntel eingeben "
                           + "(Ende mit Strg-D/Strg-Z):");
        while (EINGABE.hasNext()) {
            String note = EINGABE.next();

            // DONE: (1) Note pruefen
            try {
                if (!Noten.istZulaessig(note)) {
                    continue;
            // DONE: (2) Note erfassen (Summe, Zaehler, Beste, Schlechteste)
                }
                double wert = Noten.toDouble(note);
                zeahler++;

                if (Noten.istBestanden(wert)) {
                    passed++;
                    summe += wert;
                }

                best = Noten.bessere(best, wert);
                worst = Noten.schlechtere(worst, wert);



            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        } // while

        System.out.println();

        // DONE: (3) Durchschnitt, Durchfallquote berechnen, alles ausgeben

        // DONE: Durchschnitt, Durchfallquote berechnen, alles ausgeben
        System.out.println(
            "Anzahl beruecksichtigter Noten: " + zeahler
        );
        System.out.println(
            "Anzahl Bestandene: " + passed
        );
        if (zeahler > 0) {
            System.out.printf(
                "Beste Note: %s%n",
                Noten.toString(best)
            );
            System.out.printf(
                "Schlechteste Note: %s%n",
                Noten.toString(worst)
            );
            if (passed > 0) {
                double durchschnitt = summe / passed;
                System.out.printf(
                    "Durchschnitt Bestandene: %s%n",
                    Noten.toString(durchschnitt)
                );
            } else {
                System.out.println("Durchschnitt Bestandene: keine Bestandene");
            }
            double failedpercent = ((zeahler - passed) * hundert) / zeahler;
            System.out.printf("Durchfallquote: %.1f%%%n", failedpercent);
        }
    } // main
}