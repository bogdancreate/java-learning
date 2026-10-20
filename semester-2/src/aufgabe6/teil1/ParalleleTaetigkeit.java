package aufgabe6.teil1;

public class ParalleleTaetigkeit extends ZusammengesetzteTaetigkeit {

    @Override
    public double getTime() {
        double max = 0;
        for (Taetigkeit t : meineTaetigkeit) {
            if (t.getTime() > max ) {
                max = t.getTime();
            }
        }
        return max;
    }

    @Override
    public String toString() {
        return "Parallel" + meineTaetigkeit.toString();
    }
}
