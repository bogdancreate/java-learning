package aufgabe6;
/**
 * Unbenotete Leistung.
 * @author Bohdan Cherednychenko
 * @version 19.01.2026
 */
public class UnbenoteteLeistung extends Leistung {

    private final boolean bestanden;
    /**
     * Verbindet den Name des Fächers und das Ergebnis.
     * @param fach Name des Fächers
     * @param bestanden true für "BE", false für "NB"
     */
    public UnbenoteteLeistung(String fach, boolean bestanden) {
        super(fach);
        this.bestanden = bestanden;
    }
    @Override
    public boolean istBestanden() {
        return this.bestanden;
    }
    @Override
    public boolean istBenotet() {
        return false;
    }
}