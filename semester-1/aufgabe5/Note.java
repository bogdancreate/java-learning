package aufgabe5;
/**
 * Class Note für die Note.
 */
public final class Note  {
    /**
     * Die beste Note.
     */
    public static final Note BESTE = new Note(10);
    /**
     * Schlechteste Note.
     */
    public static final Note SCHLECHTESTE = new Note(50);
    /**
     * Finale Note.
     */
    private final int note;

    /**
     * Returnt die Note zurück.
     * @param note
     */
    private Note(int note) {
        this.note = note;
    }

    /**
     * Überprüft alle zulässige Noten und gibt den Fehler.
     * @param note die geprueft wird.
     * @return note
     */
    public static Note valueOf(int note) {
        if (!isValid(note)) {
            throw new IllegalArgumentException("unzulaessige Note " + note);
        }
        return new Note(note);
    }
    /**
     * Überprüft alle zulässige Noten, gibt den Fehler.
     * @param note zuruek als String.
     * @return den Wert in int
     */
    public static Note valueOf(String note) {
        if (note == null || note.length() != 3) {
            throw new IllegalArgumentException("unzulaessige Note " + note);
        }
        char v = note.charAt(0);
        char n = note.charAt(2);
        int vorkomma = v - '0';
        int nachkomma = note.charAt(2) - '0';
        if (note.charAt(1) != ',' && note.charAt(1) != '.') {
            throw new IllegalArgumentException("unzulaessige Note " + note);
        }
        if (!Character.isDigit(v) || !Character.isDigit(n)) {
            throw new IllegalArgumentException("unzulaessige Note " + note);
        }
        return valueOf(vorkomma * 10 + nachkomma);
    }
    /**
     * Returnt Note zurück.
     * @return Note
     */
    public int intValue() {
        return this.note;
    }
    /**
     * Wandelt die Note in String.
     * @return Note in String
     */
    public String toString() {
        return ((note / 10) + "," + (note % 10));
    }
    /**
     * Überprüft alle zullässige Noten.
     * @param note
     * @return
     */
    private static boolean isValid(int note) {
        switch (note) {
        case 10, 13, 17, 20, 23, 27, 30, 33, 37, 40, 50:
            return true;
        default: return false;

        }
    }
    /**
     * Prüft, ob die Note bestanden ist.
     * @return true, oder false
     */
    public boolean istBestanden() {
        if (note <= 40) {
            return true;
        }
        return false;
    }
    /**
     * Prüft ob Note gleich einem anderen Objekt ist.
     * @param o wir vergliechen
     * @return true oder false
     */
    public boolean equals(Object o) {
        if (o instanceof Note) {
            Note that = (Note) o;
            return this.note == that.note;
        }
        return false;
    }
    /**
     * Liefert den Hash-Code für Note.
     * @return hash-code
     */
    public int hashCode() {
        return this.note;
    }

}
