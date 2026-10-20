package workshop1;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Aufgabe3_Textanalyse {
    public static void aufgabe() throws IOException {
        //
        // a) (6P)
        // Die Datei Kafka_Der_Prozess.txt soll eingelesen werden und verschiedene Auswertungen erfolgen.
        // Ergänzen Sie die Funktion einlesen so, dass die eingelesenen Wörter als Liste zurückgeliefert werden.
        System.out.println("\nAufgabe 3a (6P):");
        long start = System.nanoTime(); // aktuelle Zeit in nsec
        List<String> lst_Kafka = einlesen("D:\\Downloads\\Workshop1\\Workshop1\\data\\Kafka_Der_Prozess.txt");
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);

        // Geben Sie die Anzahl der eingelesenen Wörter aus. Benutzen Sie dazu Ihre eingelesene Liste.
        // Ihr Code: ...
        System.out.println("Anzahl der eingelesenen Wörter: " + lst_Kafka.size());

        // Sortieren Sie die Liste und geben Sie die ersten 10 Wörter und die letzten 10 Wörter aus.
        start = System.nanoTime();
        // Ihr Code: ...
        start = System.nanoTime();
        Collections.sort(lst_Kafka);
        System.out.println("Erste 10 Wörter: " + lst_Kafka.subList(0, 10));
        System.out.println("Letzte 10 Wörter: " + lst_Kafka.subList(lst_Kafka.size() - 10, lst_Kafka.size()));
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);

        // Speichern Sie die Liste in eine TreeSet und geben Sie ersten die 100  Wörter aus.
        // Hinweis: Verwenden Sie einen der Konstruktoren der Klasse TreeSet!
        // Ihr Code: ...
        TreeSet<String> hWords = new TreeSet<>(lst_Kafka);
        System.out.println("Erste 100 Wörter aus TreeSet: ");
        int j = 0;
        for (String word : hWords) {
            if (j >= 100) break;
            System.out.println(word);
            j++;
        }

        //
        // b) (6P)
        // Verwenden Sie die bereits eingelesene Liste lst_Kafka und erstellen Sie eine Häufigkeitstabelle als SortedMap.
        // Wieviel unterschiedliche Wörter gibt es?
        // Geben Sie mit Hilfe von subMap alle Wörter (mit ihren Häufigkeiten) aus, die mit "Ver" beginnen.
        // Geben Sie die 20 häufigsten Wörter (mit ihren Häufigkeiten) aus.
        System.out.println("\nAufgabe 3b (6P):");
        start = System.nanoTime();
        SortedMap<String, Integer> fqTable_Kafka = ermittleHaeufigkeiten(lst_Kafka);
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);
        // Ihr Code: ...
        System.out.println("Anzahl unterschiedlicher Wörter: " + fqTable_Kafka.size());

        SortedMap<String, Integer> verMap = fqTable_Kafka.subMap("ver", "ves");
        System.out.println("Wörter beginnend mit 'ver': " + verMap);

        // Wir verschieben die Datensätze in eine ArrayList und sortieren sie in absteigender Reihenfolge der Ergebnisse (Häufigkeit).
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(fqTable_Kafka.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("Die 20 häufigsten Wörter:");
        for (int i = 0; i < Math.min(20, entryList.size()); i++) {
            System.out.println(entryList.get(i).getKey() + " -> " + entryList.get(i).getValue());
        }


        //
        // c) (6P)
        // Ermitteln Sie für den Kafka-Text eine Häufigkeitstabelle der falsch geschriebenen Wörter.
        // Wieviel falsch geschriebene Wörter gibt es?
        // Geben Sie die 20 häufigsten falsch geschriebenen Wörter (mit ihren Häufigkeiten) aus.
        // Ein Wort gilt als falsch geschrieben, wenn es nicht in der Wörterbuchdatei word_list_german_spell_checked.txt vorkommt.
        // Lesen Sie die alle Wörter der Datei ein und speichern Sie sie in eine effiziente Datenstruktur ab.
        // Achtung: Das Einlesen der Datei dauert etwas Zeit, da sie mehr als 2 Millionen Einträge enthält.
        System.out.println("\nAufgabe 3c (6P):");

        start = System.nanoTime();
        // word_list_german_spell_checked.txt einlesen:
        // Ihr Code: ..
        Set<String> dictionary = new HashSet<>();

        //lesen
        try (LineNumberReader dictReader = new LineNumberReader(new FileReader("D:\\Downloads\\Workshop1\\Workshop1\\data\\word_list_german_spell_checked.txt", StandardCharsets.UTF_8))) {
            String dictLine;
            while ((dictLine = dictReader.readLine()) != null) {
                dictionary.add(dictLine.trim().toLowerCase());
            }
        }
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);
        // Ihr Code:
        //suchen Fehler
        SortedMap<String, Integer> wrongWordsMap = new TreeMap<>();
        for (String word : lst_Kafka) {
            if (!dictionary.contains(word)) {
                wrongWordsMap.put(word, wrongWordsMap.getOrDefault(word, 0) + 1);
            }
        }
        System.out.println("Anzahl falsch geschriebener Wörter: " + wrongWordsMap.size());

        //top20
        List<Map.Entry<String, Integer>> wrongEntryList = new ArrayList<>(wrongWordsMap.entrySet());
        wrongEntryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        System.out.println("Die 20 häufigsten falschen Wörter:");
        for (int i = 0; i < Math.min(20, wrongEntryList.size()); i++) {
            System.out.println(wrongEntryList.get(i).getKey() + " -> " + wrongEntryList.get(i).getValue());
        }
    }

    private static List<String> einlesen(String fileName) throws IOException {
        LineNumberReader in = new LineNumberReader(new FileReader(fileName, StandardCharsets.UTF_8));
        List<String> list = new ArrayList<>();
        String line;

        while ((line = in.readLine()) != null) {
            String[] wf = line.split("[^a-z^A-Z^ß^ä^ö^ü^Ä^Ö^Ü]+");
            for (String w: wf) {
                if (w.length() == 0 || w.length() == 1)
                    continue;
                // ...
                list.add(w.toLowerCase());
            }
        }
        return list;
    }

    private static SortedMap<String, Integer> ermittleHaeufigkeiten(List<String> wListe)  {
        // Ihr Code:
        // ...
        SortedMap<String, Integer> map = new TreeMap<>();
        for (String w : wListe) {
            map.put(w, map.getOrDefault(w, 0) + 1);
        }
        return map;
    }
}
