import java.util.Random;

public class Aufgabe1 {
    public static void main(String[] args) {
//        int[] a = {4, 8 , 15, 20, 16, 19, 55};
//        System.out.println(linearSearch(a, 15));
//        System.out.println(linearSearch(a, 200));
//        int N = 1000000;
//        int[] a = new int[N];
//        Random random = new Random();
//        for (int i = 0; i < a.length; i++){
//            a[i] = random.nextInt(1000);
//        }
//        long start = System.nanoTime();
//
//        int result = linearSearch(a, -1);
//
//        long end = System.nanoTime();
//
//        long duration = end - start;
//        System.out.println(result);
//        System.out.println(duration);
        int[] size = {100000, 200000, 400000, 800000, 1600000, 3200000, 6400000, 12800000};
        Random random = new Random();

        for (int N : size) {
            int a[] = new int[N];
            int result = 0;

            long start = System.nanoTime();
            for (int i = 0; i < 100; i++)
                result = linearSearch(a, -1);
            long end = System.nanoTime();

            long duration = (end - start)/100;
            System.out.println("Array groesse: " + N);
            System.out.println("Zeit: " + duration);
        }
    }
    public static int linearSearch(int[] a, int val) {
        for (int i = 0; i < a.length; i++){
            if (a[i] == val) {
                return i;
            }
        }
        return -1;
    }
}


// 1.Die Messungen zeigen dass die Laufzeit bei der groesseren Arrays insgesamt steigt.
//  Bei einer Verdopplung der Arraygroesse verdoppelt sich
// 2. die Laufzeit ungefaehr, aber nicht immer genau. Das liegt zB ab der JVM.
// 3.Die lineare Suche hat im Worst Case die Zeitkomplexitaet O(n). Alle n Elemente ueberprueft werden muessen. 
// Das passt zur Big-O-Definition, weil die Anzahl der
// benoetigten Operationen hoechstens linear mit der Arraygroesse waechst.
