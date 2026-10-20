package aufgabe6.teil1;

public class ElementareTaetigkeit implements Taetigkeit {
    private double time;
    private String besch;

    public ElementareTaetigkeit(String besch, double time) {
        this.besch = besch;
        this.time = time;
    }

    @Override
    public double getTime() {
        return this.time;
    }

    @Override
    public void add(Taetigkeit t) {
        // Elementare Tätigkeiten können keine Untertätigkeiten haben
    }

    @Override
    public void remove(Taetigkeit t) {
        //Nichts zu entfernen
    }

    @Override
    public int getAnzahl() {
        return  1;
    }

    @Override
    public String toString() {
        return this.besch + " (" + this.time + " min)";
    }
}
