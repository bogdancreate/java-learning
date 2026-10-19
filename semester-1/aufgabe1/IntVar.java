// IntVar.java
package aufgabe1;

import java.util.Scanner;

/**
 * IntVar zeigt den Umgang mit Variablen vom Typ int.
 * Uebungsaufgabe 1 zur Programmiertechnik 1.
 * @author Bohdan Cherednychenko
 * @version 21.10.2025
 */
public final class IntVar {
    private IntVar() { }

    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        /* DONE: Konstanten min und max definieren */
        final int min = 0x80000000; // -2147483648
        final int max = 0x7FFFFFFF; //  2147483647
        /* DONE: Eingabeaufforderung ausgeben */
        System.out.printf(
            "Zwei ganze Zahlen zwischen %d und %d eingeben:%n",
             min, max
        );
        /* DONE: zwei ganze Zahlen einlesen */
        int a = EINGABE.nextInt();
        int b = EINGABE.nextInt();
        /* DONE: die beiden Zahlen dezimal, okatal und hexadezimal ausgeben */
        System.out.printf("%d ist oktal %o und hexadezimal %x%n", a, a, a);
        System.out.printf("%d ist oktal %o und hexadezimal %x%n", b, b, b);
        /* DONE: alle zweistelligen arithmetischen Operatoren ausprobieren */
        System.out.printf("%d + %d ist %d%n", a, b, a + b);
        System.out.printf("%d - %d ist %d%n", a, b, a - b);
        System.out.printf("%d * %d ist %d%n", a, b, a * b);
        System.out.printf("%d / %d ist %d%n", a, b, a / b);
        System.out.printf("%d %% %d ist %d%n", a, b, a % b);
        /* DONE: alle Vergleichsoperatoren ausprobieren */
        System.out.printf("%d == %d ist %b%n", a, b, a == b);
        System.out.printf("%d != %d ist %b%n", a, b, a != b);
        System.out.printf("%d < %d ist %b%n", a, b, a < b);
        System.out.printf("%d <= %d ist %b%n", a, b, a <= b);
        System.out.printf("%d > %d ist %b%n", a, b, a > b);
        System.out.printf("%d >= %d ist %b%n", a, b, a >= b);
    }
}
