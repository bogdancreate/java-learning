import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("Juhuu die erste AlDa-Übung ist geschafft");
        int N = 10000000;
        //wiedercholen 5 mal
        for (int j = 0; j < 5; j++) {
            //A 2.
            //Großes Array erstellen
            int[] a = new int[N];

            Random random = new Random();

            //Array mit Zufallszahlen füllen
            for (int i = 0; i < N; i++) {
                a[i] = random.nextInt(10000000);
            }

            //B2: Worst Case
            int val = -1;

            //B 1. Timer starten
            long start = System.nanoTime();
            int result = linearSearch(a, val);
            //Timer stoppen
            long end = System.nanoTime();
            //Laufzeit ausgeben
            long laufzeit = end - start;
            System.out.println("N = " + N
                    + " | Ergebnis = " + result
                    + " | Laufzeit = " + laufzeit + " ns");

            //verdoppeln
            N = N * 2;
        }

    }

    //A 1.
    public static int linearSearch(int[] a, int val) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == val) {
                return i;
            }
        }
        return -1;
    }
}



