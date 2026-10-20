package aufgabe6.teil1;

import java.util.ArrayList;
import java.util.List;

public abstract class ZusammengesetzteTaetigkeit implements Taetigkeit {
    protected  List<Taetigkeit> meineTaetigkeit = new ArrayList<>();


    @Override
    public void add(Taetigkeit tk) {
        meineTaetigkeit.add(tk);
    }

    @Override
    public void remove(Taetigkeit tk) {
        meineTaetigkeit.remove(tk);
    }

    @Override
    public int getAnzahl() {
        int gesamtAnzahl = 0;
        for (Taetigkeit t : meineTaetigkeit) {
            gesamtAnzahl += t.getAnzahl();
        }
        return gesamtAnzahl;
    }
}
