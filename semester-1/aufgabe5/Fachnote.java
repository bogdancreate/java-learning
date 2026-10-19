package aufgabe5;
/**
 * Repräsentiert eine Note zu einem bestimmten Fach.
 */
public final class Fachnote {
    private final String fach;
    private final Note note;
    /**
     * main description
     * @return den Namen des Faches zurück.
     */
    public String fach() {
        return fach;
    }
    /**
     * note bekommen
     * @return die Note zurück.
     */
    public Note note() {
        return note;
    }
    /**
     * Erzeugt eine Fachnote mit Fachname und Note.
     *
     * @param fach der Name des Faches
     * @param note die zugehörige Note
     * @throws IllegalArgumentException falls fach null oder leer ist
     *         oder note null ist
     */
    public Fachnote(String fach, Note note) throws IllegalArgumentException {
        if (fach == null || fach.length() == 0 || note == null) {
            throw new IllegalArgumentException("Fachname und Note "
                + "dürfen nicht null sein und "
                + "der Fachname darf nicht die Länge 0 haben");
        }
        this.fach = fach;
        this.note = note;
    }
}