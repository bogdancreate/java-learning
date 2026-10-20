package workshop2;// Workshop 2 SS 2024
// O. Bittel

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Workshop2 {
    private static final String BLUE = "\u001B[34m";
    private static final String BLACK = "\u001B[30m";
    private static final String WHITE = "\u001B[38m";

    public static void main(String[] args) throws IOException {
        System.out.println("|-------------------------------------------------------------|");
        System.out.println("| Willkommen zum Workshop 2 im SS 2026                        |");
        System.out.println("| Funktionales Programmieren                                  |");
        System.out.println("| 66% (40 von 60 Punkte) der Teile müssen fehlerfrei laufen!  |");
        System.out.println("|-------------------------------------------------------------|");

        teil1(); //  8 Punkte
        teil2(); //  8 Punkte
        teil3(); // 12 Punkte
        teil4(); // 32 Punkte

    }

    public static void teil1() {
        /* Bestimme die Anzahl der Zeichen in myString ohne das Zeichen 'a'.
         */

        String myString = "Bestimme die Anzahl der Zeichen in einem String ohne das Zeichen 'a'";
        System.out.println(BLUE + "\nTeil 1: " + myString);

        // a) (2 Punkte) Imperativ (mit einer Schleife):
        // ...
        int count = 0;
        for (int i = 0; i < myString.length(); i++) {
            if (myString.charAt(i) != 'a') {
                count++;
            }
        }
        System.out.println(BLACK + "Imperativ: " + count);

        // b) (3 Punkte) Funktional.
        // Ausschließlich Methodenaufrufe aus der Klasse String sind gestattet.
        // Hinweis: String.replaceAll könnte hilfreich sein.
        // ...
        int countFunktional = myString.replaceAll("a", "").length();
        System.out.println("Funktional: " + countFunktional);

        // c) (3 Punkte) Mit Streams.
        // Hinweis: Erzeugen Sie aus dem String einen Zeichen-Strom.
        // ...
        long countStreams = myString.chars()
                .filter(c -> c != 'a')
                .count();
        System.out.println("Streams: " + countStreams);
    }

    public static void teil2() {
        // Bestimme die Haeufigkeit des Strings myString in der String-Liste myList.
        System.out.println(BLUE + "\nTeil 2: Häufigkeit eines Strings in einer String-Liste");
        List<String> myList = List.of("das", "der", "die", "der", "der", "die", "der", "das", "die", "der");
        String myString = "der";

        // a) (2 Punkte) Externe Iteration mit einer for-each-Schleife:
        int countForEach = 0;
        for (String s : myList) {
            if (s.equals(myString)) {
                countForEach++;
            }
        }
        System.out.println(BLACK + "Externe Iteration: " + countForEach);

        // b) (3 Punkte) Interne Iteration mit forEach():
        AtomicInteger nAtomic = new AtomicInteger(0);

        myList.forEach(s -> {
            if (s.equals(myString)) {
                nAtomic.incrementAndGet();
            }
        });
        System.out.println("Interne Iteration: " + nAtomic.get());

        // c) (3 Punkte) Mit einem Stream:
        long countStream = myList.stream()
                .filter(s -> s.equals(myString))
                .count();
        System.out.println("Streams: " + countStream);
    }

    public static void teil3() {
        // Liste von Städte und Strom-Operationen
        System.out.println(BLUE + "\nTeil 3: " + "Liste von Städte und Strom-Operationen");
        List<Stadt> sLst = new LinkedList<>();
        sLst.add(new Stadt("Muenchen","Deutschland",1_484_226 ));
        sLst.add(new Stadt("Paris","Frankreich",2_175_601));
        sLst.add(new Stadt("Valencia","Spanien",794_288  ));
        sLst.add(new Stadt("Porto","Portugal",238_000 ));
        sLst.add(new Stadt("Berlin","Deutschland",3_669_491));
        sLst.add(new Stadt("Mailand","Italien",1_396_059));
        sLst.add(new Stadt("Toulouse","Frankreich",493_465 ));
        sLst.add(new Stadt("Konstanz","Deutschland",84_911));
        sLst.add(new Stadt("Frankfurt","Deutschland",759_224));
        sLst.add(new Stadt("Marseille","Frankreich",919_305));
        sLst.add(new Stadt("Stuttgart","Deutschland",626_275));
        sLst.add(new Stadt("Lyon","Frankreich",522_969 ));
        sLst.add(new Stadt("Rom","Italien",4_333_274));
        sLst.add(new Stadt("Turin","Italien",870_952 ));
        sLst.add(new Stadt("Madrid","Spanien",3_266_126 ));
        sLst.add(new Stadt("Barcelona","Spanien",1_636_762 ));
        sLst.add(new Stadt("Lissabon","Portugal",2_963_272 ));

        // a) (3 Punkte)
        // Sortieren Sie die Liste von Städten alphabetisch nach dem Land und bei gleichem Land
        // nach dem Namen der Stadt. Geben Sie die sortierten Städte aus.
        // Versuchen Sie Comparator.comparing einzusetzen.
        System.out.println(BLACK);
        System.out.println("Städte alphabetisch nach dem Land sortiert und bei gleichem Land nach dem Namen der Stadt\n");
        // ...
        sLst.stream()
                .sorted(Comparator.comparing(Stadt::land).thenComparing(Stadt::name))
                .forEach(System.out::println);

        // b) (3 Punkte) Erstellen Sie eine Statistik über die Einwohnerzahl (Anzahl, Summe, Minimum, Maximum und Mittelwert)
        System.out.println(BLACK);
        System.out.println("Statistik");
        // ...
        IntSummaryStatistics stats = sLst.stream()
                .mapToInt(Stadt::ewz)
                .summaryStatistics();
        System.out.println(stats);

        // c) (3 Punkte) Sammeln Sie alle Millionenstädte nach der Einwohnerzahl absteigend sortiert in einer Liste und geben Sie die Liste aus:
        System.out.println(BLACK);
        System.out.println("Millionenstädte sortiert");
        List<Stadt> mLst = sLst.stream()
                .filter(s -> s.ewz() >= 1_000_000)
                .sorted(Comparator.comparing(Stadt::ewz).reversed())
                .toList();
        System.out.println(mLst);

        // d) (3 Punkte) Sammeln Sie alle Städte in einer Map. Die Map speichert für jedes Land eine Liste aller Städte des Landes.
        // Geben Sie die Map aus.
        System.out.println(BLACK);
        System.out.println("Alle Städte in einer Map");
        // ...
        Map<String, List<Stadt>> stadtMap = sLst.stream()
                .collect(Collectors.groupingBy(Stadt::land));
        System.out.println(stadtMap);
    }


    public static void teil4() throws IOException {
        // Kino-Filme und Stromoperationen
        System.out.print(BLUE + "\nTeil 5: Kinofilme");
        System.out.println(BLACK);

        // a) (4 Punkte)
        // Speisen Sie alle Kinofilme (jeweils eine Zeile) aus der Datei data/movies-top-grossing.txt
        // in einen Strom von Zeilen ein
        // und geben Sie nur den Titel der Kinofilme sortiert aus.
        // Bestimmen Sie außerdem die Anzahl der Filme.
        System.out.println("\n5 a)");
        BufferedReader in = new BufferedReader(new FileReader("D:\\Downloads\\Workshop2\\Workshop2\\data\\movies-top-grossing.txt"));
        // ...
        List<String> lines = in.lines().collect(Collectors.toList());
        in.close();
        // ...
        lines.stream()
                .map(Movie::new)
                .map(Movie::title)
                .sorted()
                .forEach(System.out::println);

        System.out.println("Anzahl der Filme: " + lines.size());

        // b) (1 Punkt)
        // Die record-Klasse Movie stellt zwei Konstruktoren zur Verfügung.
        // Testen Sie die beiden Konstruktoren und erklären Sie die Funktionsweise.
        System.out.println("\n5 b)");
        Movie m1 = new Movie("101 Dalmatians", List.of("Benfield, John", "Braid, Hilda", "Capron, Brian"),1996);
        Movie m2 = new Movie("101 Dalmatians (1996)/Benfield, John/Braid, Hilda/Capron, Brian");
        // Ausgabe von m1 und m2 sollte identisch sein:
        System.out.println(m1);
        System.out.println(m2);
        /* Erklärung: Der erste Konstruktor ist der standardmäßig generierte, kanonische Record-Konstruktor.
         Der zweite Konstruktor ist ein Custom-Konstruktor, der einen formatierten String entgegennimmt,
         diesen über die privaten Hilfsmethoden parst (Titel extrahieren, Jahr extrahieren, Schauspieler splitten)
         und das Ergebnis via 'this(...)' an den kanonischen Konstruktor weiterreicht.
         */

        // c) (4 Punkte)
        // Mit Hilfe von Strom-Operationen sollen alle Kinofilme in eine Movie-Liste gespeichert werden.
        // Ebenfalls mit Strom-Operationen sollen dann die ersten 3 Filme zeilenweise ausgegeben werden.
        System.out.println("\n5 c)");
        in = new BufferedReader(new FileReader("D:\\Downloads\\Workshop2\\Workshop2\\data\\movies-top-grossing.txt"));
        // ...
        List<Movie> movieListe = in.lines()
                .map(Movie::new)
                .collect(Collectors.toList());
        in.close();
        // ...
        movieListe.stream()
                .limit(3)
                .forEach(System.out::println);

        // d) (4 Punkte)
        // Mit Hilfe von Strom-Operationen und der Movie-Liste aus c) sollen
        // alle Filmtitel mit Jahreszahl ausgegeben werden,
        // in denen der Schauspieler Morgan Freeman mitgespielt hat.
        System.out.println("\n5 d)");
        String s1 = "Freeman, Morgan";
        movieListe.stream()
                .filter(m -> m.actors().contains(s1))
                .forEach(m -> System.out.println(m.title() + " (" + m.year() + ")"));
        // ...

        // e) (4 Punkte)
        // Ermitteln Sie mit Stromoperationen, wie viele verschiedene
        // Schauspielerinnen und Schauspieler die Filmdatei enthält?
        // Hinweis: berücksichtigen Sie flatMap!
        System.out.println("\n5 e)");
        // ...
        long c = movieListe.stream()
                .flatMap(m -> m.actors().stream())
                .distinct()
                .count();
        System.out.println("Anzahl verschiedener Schauspieler: " + c);

        // f) (5 Punkte)
        // Erstellen Sie mit Strom-Operationen eine Map, die für jede Jahreszahl
        // die entsprechende Liste von Filme abspeichert.
        // Geben Sie dann mit Strom-Operationen und der erstellten Map
        // alle Film des Jahres 1990 in jeweils einer Zeile aus.
        // Hinweis: collect als terminale Operation mit Collectors.groupingBy.
        // Siehe auch Collectors-Beispiele im Skript.
        System.out.println("\n5 f)");
        // ...

        // g) (5 Punkte, etwas schwieriger)
        // Erstellen Sie mit Stromoperationen eine Map, die für jede Jahreszahl
        // die entsprechende Anzahl von Filmtiteln abspeichert.
        // Geben Sie dann mit Stromoperationen die Map zeilenweise nach der Jahreszahl sortiert aus.
        // Hinweis: collect mit Collectors.groupingBy mit downstream-Collector Collectors.counting().
        // Siehe auch Collectors-Beispiele im Skript.
        System.out.println("\n5 g)");
        // ...

        // h) (5 Punkte, etwas schwieriger)
        // Erstellen Sie eine TreeMap mapYearToTitles, die für jede Jahreszahl die entsprechende Liste von Filmtiteln abspeichert.
        // Geben Sie dann die Map aus.
        // Hinweis: collect mit Collectors.groupingBy mit downstream-Collector Collectors.mapping.
        // Siehe auch Collectors-Beispiele im Skript.
        System.out.println("\n5 h)");
        // ...
        // System.out.println(mapYearToTitles);
    }
}