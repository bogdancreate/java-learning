package workshop1;

import java.util.*;

public class Aufgabe1_WarmUp {
    public static void aufgabe() {
        System.out.println("\nAufgabe 1a (3P):");
        aufgabe_a();

        System.out.println("\nAufgabe 1b (3P):");
        aufgabe_b();

        System.out.println("\nAufgabe 1c (3P):");
        aufgabe_c();

        System.out.println("\nAufgabe 1d (3P):");
        aufgabe_d();
    }

    private static void aufgabe_a() {
        // Kopieren Sie alle Elemente aus der Liste l1 in eine Liste l2 ohne Duplikate.
        // D.h. l2 enthält alle Elemente aus l1, wobei kein Element mehrfach vorkommt.
        // Die Reihenfolge der Elemente soll erhalten bleiben. Verzichten Sie auf Set-Typen!

        List<String> l1 = new LinkedList<>();
        l1.add("drei");
        l1.add("vier");
        l1.add("fuenf");
        l1.add("vier");
        l1.add("zwei");
        l1.add("eins");
        l1.add("fuenf");
        l1.add("acht");
        l1.add("vier");
        l1.add("drei");
        l1.add("zwei");
        l1.add("drei");
        System.out.println(l1);

        List<String> l2 = new LinkedList<>();
        // Ihr Code: ...
        for (String s : l1) {
            if(!l2.contains(s)) {
                l2.add(s);
            }
        }
        System.out.println(l2);
    }

    private static void aufgabe_b() {
        // Kopieren Sie alle Elemente aus l1 und l2 in eine gemeinsame List l3.
        // Verzichten Sie auf eine for-Schleife!

        List<String> l1 = new LinkedList<>();
        l1.add("vier");
        l1.add("fuenf");
        l1.add("vier");
        l1.add("eins");
        l1.add("fuenf");
        l1.add("acht");
        l1.add("vier");

        List<Integer> l2 = new ArrayList<>();
        l2.add(12);
        l2.add(1);
        l2.add(12);
        l2.add(11);
        l2.add(1);
        l2.add(12);

        // Ihr Code: ...
        List<Object> l3 = new ArrayList<>();
        l3.addAll(l1);
        l3.addAll(l2);
        System.out.println(l3);
    }

    private static void aufgabe_c() {
        // Entfernen Sie mit einer Schleife jedes zweite Elemente aus der Liste l1.
        // Falls l1 = {"eins", "zwei", "drei", "vier", "fuenf"}, dann muss l1 nach der Bearbeitung
        // l1 = {"eins", "drei", "fuenf"} sein.
        List<String> l1 = new LinkedList<>();
        l1.add("eins");
        l1.add("zwei");
        l1.add("drei");
        l1.add("vier");
        l1.add("fuenf");
        l1.add("sechs");
        l1.add("sieben");
        l1.add("acht");
        l1.add("neun");
        l1.add("zehn");
        l1.add("elf");
        l1.add("zwoelf");
        System.out.println(l1);

        // Hinweis: Versuchen Sie die Aufgabe mit einem Iterator zu lösen, damit die Laufzeit der Aufgabe linear in der Länge der Liste ist.
        Iterator<String> it = l1.iterator();
        // Ihr Code: ...
        int i = 1;
        while (it.hasNext()) {
            it.next();
            if (i % 2 == 0) {
                it.remove();
            }
            ++i;
        }
        System.out.println(l1);
    }

    private static void aufgabe_d() {
        // Eine Matrix m lässt sich als Liste von Liste implementieren.
        // Fügen Sie folgende 3 Listen in eine Liste m von Listen ein.
        // Geben Sie die gesamte Matrix m und
        // das Element in der 3. Zeile und 4. Spalte (2.3) aus.
        // Ändern Sie das Element in der 3. Zeile und 4. Spalte in 4.6.

        List<Double> l1 = new LinkedList<>(List.of(1.2, 3.2, 5.1, 7.1));
        List<Double> l2 = new LinkedList<>(List.of(8.3, 2.0, 1.1, 5.2));
        List<Double> l3 = new LinkedList<>(List.of(6.8, 3.5, 1.7, 2.3));

        List<List<Double>> m = new LinkedList<>();

        // Ihr Code: ...
        m.add(l1);
        m.add(l2);
        m.add(l3);
        System.out.println(m);
        System.out.println(m.get(2).get(3));
        m.get(2).set(3, 4.6);
        System.out.println(m);
    }
}
