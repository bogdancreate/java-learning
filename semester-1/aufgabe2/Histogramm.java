// Histogramm.java
package aufgabe2;

import java.util.Scanner;

/**
 * Histogramm liest ganze Zahlen zwischen 1 und 6 ein und
 * gibt deren Haeufigkeitsverteilung als Histogramm aus.
 * @author Bohdan Cherednychenko
 * @version 31.10.2025
 */
public final class Histogramm {
    private Histogramm() { }

    private static final Scanner EINGABE = new Scanner(System.in);
    private static final int S = 6;
    private static final int MIN = 1;
    private static final int MAX = 6;
    private static final int FIVE = 5;
    private static final int R = 4;
     /**
     * main ist der Startpunkt des Programms.
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        // DONE: (1) Zaehler-Array anlegen
        int[] zaehler = new int[S];

        System.out.println(
            "Ganze Zahlen zwischen 1 und 6 eingeben (Ende mit Strg-Z):"
        );

        while (EINGABE.hasNextInt()) {
            int n = EINGABE.nextInt();
            // DONE: (2) Eingabe pruefen und zaehlen
            if (n >= MIN && n <= MAX) {
                zaehler[n - 1]++;
            } else {
                System.out.println("Falsche Eingabe wird ignoritrt: " + n);
            }
        }
        System.out.println("Strg-Z");
        System.out.println("Histogramm:");
        // DONE: (3) Histogramm ausgeben
        for (int i = 0; i < S; i++) {
            for (int j = 0; j < zaehler[i]; j++) {
                if (j % FIVE == R) {
                    System.out.print("$");
                } else {
                    System.out.print(i + 1);
                }
            }
            System.out.printf(" (%d)%n", zaehler[i]);
        }

    }
}