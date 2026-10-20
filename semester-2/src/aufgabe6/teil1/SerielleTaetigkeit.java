package aufgabe6.teil1;

public class SerielleTaetigkeit extends ZusammengesetzteTaetigkeit {

    @Override
    public double getTime() {
        double summe = 0;
        for (Taetigkeit t : meineTaetigkeit) {
            summe += t.getTime();
        }
        return summe;
    }

    @Override
    public String toString() {
        return "Seriell" + meineTaetigkeit.toString();
    }
}
