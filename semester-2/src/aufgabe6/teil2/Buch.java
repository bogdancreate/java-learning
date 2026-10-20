package aufgabe6.teil2;

public class Buch {
    private String name;

    Person entleiher;

    public Buch(String  name) {
        this.name = name;
        this.entleiher = null;
    }

    public String getName() {
        return this.name;
    }

    public Person getEntleiher() {
        return entleiher;
    }

    public boolean wirdAusgeliehen(Person p) {
        if (entleiher != null || p == null) return false;
        if (this.entleiher == p) return true;
        this.entleiher = p;
        p.addBuch(this);
        return true;
    }

    public boolean wirdZurueckGegeben() {
        if (this.entleiher == null) return false;

        Person zurueckP = this.entleiher;
        this.entleiher = null;
        zurueckP.removeBuch(this);
        return true;
    }

    public void print() {
        if (this.entleiher == null) {
            System.out.println("Buch: " + name + " wird von niemandem ausgeliehen");
        } else {
            System.out.println("Buch: " + name + " wird von Person: " + this.entleiher.getName() + " ausgeliehen");
        }
    }
}