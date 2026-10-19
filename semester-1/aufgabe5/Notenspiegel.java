// Notenspiegel.java
package aufgabe5;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Notenspiegel liest die Namen von Fächern mit den zugehörigen Noten
 * in eine verkettete Liste ein und gibt dann einen Notenspiegel aus.
 * @author Bohdan Cherednychenko
 * @version 07.01.2026
 */
public final class Notenspiegel {
    private Notenspiegel() { }

    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        System.err.printf(
            "Faecher mit Noten zwischen %d und %d eingeben "
            + "(Ende mit Strg-D/Strg-Z):%n",
                Note.BESTE.intValue(), Note.SCHLECHTESTE.intValue());

        FachnotenListe liste = new FachnotenListe(); // leere Liste

        while (EINGABE.hasNext()) {
            try {
                // DONE: (1) Fach und Note einlesen, Wertobjekt erzeugen
                String fach = EINGABE.next();
                Note note;
                if (EINGABE.hasNextInt()) {
                    int note1 = EINGABE.nextInt();
                    note = Note.valueOf(note1);
                } else {
                    note = Note.valueOf(EINGABE.next());
                }
                Fachnote fach1 = new Fachnote(fach, note);

                // DONE: (2) neue Fachnote in Notenliste einfügen
                liste.insert(fach1);

            } catch (IllegalArgumentException x) {
                System.err.printf("Eingabefehler: %s%n", x.getMessage());
                continue;
            } catch (NoSuchElementException x) {
                System.err.println("Fach ohne Note ignoriert!");
                break;
            }
        }

        // DONE: (3) tabellarischen Notenspiegel ausgeben
        System.out.println("Notenspiegel:");
        int max = 0;
        FachnotenListe.Iterator it = liste.new Iterator();
        while (it.hasNext()) {
            Fachnote fn = it.next();
            int len = fn.fach().length();
            if (len > max) {
                max = len;
            }
        }
        it = liste.new Iterator();
        while (it.hasNext()) {
            Fachnote fn = it.next();
            String fach = fn.fach();
            Note note = fn.note();
            String notestr = note.toString();
            System.out.printf("%-" + max + "s %-4s", fach, notestr);
            int wert = note.intValue();
            if (wert == Note.BESTE.intValue()) {
                System.out.println("mit Bestnote bestanden");
            } else if (wert == Note.SCHLECHTESTE.intValue()) {
                System.out.println("nicht bestanden");
            } else {
                System.out.println("bestanden");
            }
        }
    } // main
}

