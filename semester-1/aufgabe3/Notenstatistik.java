// Notenstatistik.java
package aufgabe3;

import java.util.Locale;
import java.util.Scanner;

/**
 * erstellt eine Notenstatistik.
 * <p>
 * Das Programm erwartet Puefungsnoten im Format <code>Ganze,Zehntel</code>
 * oder <code>Ganze.Zehntel</code>, wobei <code>Ganze</code> und
 * <code>Zehntel</code> nur aus je einer Dezimalziffer bestehen duerfen.
 * Andere Eingaben werden wegen Formatfehler ignoriert.
 * </p>
 * <p>
 * Das Programm gibt die folgende Statistik aus:
 * </p>
 * <ul>
 * <li>die Anzahl der beruecksichtigten Noten</li>
 * <li>die Anzahl der Bestandenen</li>
 * <li>die beste Note</li> 
 * <li>die schlechteste Note</li>
 * <li>den Durchschnitt der Bestandenen</li>
 * <li>die Durchfallquote in Prozent</li>
 * </ul>
 * <p>
 * Es werden in der Statistik nur die nach HTWG-Pruefungsordnung
 * zulaessigen Noten (1,0 1,3 1,7 2,0 2,3 2,7 3,0 3,3 3,7 4,0 5,0)
 * beruecksichtigt. Andere Eingaben werden wegen falscher Vorkommastelle
 * oder falscher Nachkommastelle ignoriert.
 * Alle Noten bis 4,0 gelten als bestanden, nur die 5,0 als durchgefallen.
 * </p>
 *
 * @author DONE: Bohdan Cherednyckenko
 * @version DONE: 19.11.2025
 */
public final class Notenstatistik {
    private Notenstatistik() { }

    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);
        int anzahlNoten = 0;
        int anzahlBestandene = 0;
        double besteNote = 0.0;
        double schlechtesteNote = 0.0;
        double summeBestanden = 0.0;
        // Noten einlesen
        System.out.println("Noten Ganze,Zehntel oder Ganze.Zehntel eingeben "
                 + "(Ende mit Strg-D/Strg-Z):");
        while (EINGABE.hasNext()) {
            String note = EINGABE.next();
            // DONE: (1) Note pruefen (Format, Vor- und Nachkommastelle)
            if (note.length() != 3) {
                System.out.println("Note " + note
                         + " wird wegen Formatfehler ignoriert!");
                continue;
            }
            char vork = note.charAt(0);
            char trenner = note.charAt(1);
            char nachk = note.charAt(2);
            if (!Character.isDigit(vork) || !Character.isDigit(nachk)) {
                System.out.println("Note " + note
                         + " wird wegen Formatfehler ignoriert!");
                continue;
            }
            if (trenner != '.' && trenner != ',') {
                System.out.println("Note " + note
                         + " wird wegen Formatfehler ignoriert!");
                continue;
            }
            int vorkInt = vork - '0';
            int nachkInt = nachk - '0';
            switch (vorkInt) {
            case 1, 2, 3:
                if (nachkInt != 3 && nachkInt != 0 && nachkInt != 7) {
                    System.out.println("Note " + note + " wird wegen"
                             + "Nachkommastelle " + nachkInt + " ignoriert!");
                    continue;
                }
                break;
            case 4, 5:
                if (nachkInt != 0) {
                    System.out.println("Note " + note
                         + " wird wegen Nachkommastelle " + nachkInt
                         + " ignoriert!");
                    continue;
                }
                break;
            default:
                System.out.println("Note " + note
                     + " wird wegen Vorkommastelle " + vorkInt + " ignoriert!");
                continue;
            }
            // DONE: (2) Note erfassen (Summe, Zaehler, Beste, Schlechteste)
            double noteWert = vorkInt + nachkInt / 10.0;
            anzahlNoten++;

            if (anzahlNoten == 1) {
                besteNote = noteWert;
                schlechtesteNote = noteWert;
            } else {
                if (noteWert < besteNote) {
                    besteNote = noteWert;
                }
                if (noteWert > schlechtesteNote) {
                    schlechtesteNote = noteWert;
                }
            }
            if (noteWert <= 4.0) {
                anzahlBestandene++;
                summeBestanden += noteWert;
            }
        }
        // DONE: (3) Durchschnitt, Durchfallquote berechnen, alles ausgeben
        double durchschnitt = 0.0;
        if (anzahlBestandene > 0) {
            durchschnitt = summeBestanden / anzahlBestandene;
        }

        double durchfallquote = 0.0;
        if (anzahlNoten > 0) {
            durchfallquote =
                    (anzahlNoten - anzahlBestandene) * 100.0 / anzahlNoten;
        }
        System.out.printf("%nAnzahl" +
                        + "beruecksichtigter Noten: %d%n", anzahlNoten);
        System.out.printf("Anzahl Bestandene: %d%n", anzahlBestandene);
        if (anzahlNoten > 0) {
            System.out.printf("Beste Note: %.1f%n", besteNote);
            System.out.printf("Schlechteste Note: %.1f%n", schlechtesteNote);
            System.out.printf("Durchschnitt Bestandene: %.1f%n", durchschnitt);
            System.out.printf("Durchfallquote: %.1f%%%n", durchfallquote);
        }
    }
}