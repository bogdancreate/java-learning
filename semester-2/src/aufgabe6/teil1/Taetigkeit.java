package aufgabe6.teil1;

public interface Taetigkeit {
    public double getTime();
    public void add(Taetigkeit t);
    public void remove(Taetigkeit t);
    public int getAnzahl();
}
