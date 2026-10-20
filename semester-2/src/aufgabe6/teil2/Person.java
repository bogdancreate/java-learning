package aufgabe6.teil2;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String name;
    private List<Buch> ausgelieheneBuecher;

    public Person(String name) {
        this.name = name;
        this.ausgelieheneBuecher = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAnzahlAusgelieheneBuecher() {
        return ausgelieheneBuecher.size();
    }

    // Hauptmethode zum Ausleihen eines Buches
    public boolean leihtAus(Buch b) {
        if (b == null) {
            return false;
        }
        return b.wirdAusgeliehen(this);
    }

    public void addBuch(Buch b) {
        if (!ausgelieheneBuecher.contains(b)) {
            ausgelieheneBuecher.add(b);
        }
    }

    public boolean gibtZurueck(Buch b) {
        if (b == null) return false;
        if (!ausgelieheneBuecher.contains(b)) return false;
        return b.wirdZurueckGegeben();
    }

    public void removeBuch(Buch b) {
        ausgelieheneBuecher.remove(b);
    }

    public void print() {
        if (ausgelieheneBuecher.isEmpty()) {
            System.out.println("Person: " + name + " hat keine Buecher ausgeliehen");
        } else {
            System.out.println("Person: " + name + " hat folgende Buecher ausgeliehen: ");
            for (int i = 0; i < ausgelieheneBuecher.size(); ++i) {
                System.out.print(ausgelieheneBuecher.get(i).getName());
                if (i < ausgelieheneBuecher.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
