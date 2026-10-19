package aufgabe6;
import aufgabe5.Note;
/**
 * Repräsentiert eine Leistung mit Note.
 * @author Bohdan Cherednychenko
 * @version 19.01.2026
 */
public class BenoteteLeistung extends Leistung {
    private final Note note;
    /**
     *  Erzeugt eine neue benotete Leistung.
     * @param fach der Name des Fäches
     * @param note Note als String
     */
    public BenoteteLeistung(String fach, String note) {
        super(fach);
        this.note = Note.valueOf(note);
    }
    @Override
    public boolean istBenotet() {
        return true;
    }
    @Override
    public boolean istBestanden() {
        return this.note.istBestanden();
    }
    @Override
    public String getNote() {
        return this.note.toString();
    }
    //Die Note als Wort bzw. Wörter.
    @Override
    public String getNoteInWorten() {
        int wert = this.note.intValue();
        if (wert <= 15) {
            return "sehr gut";
        } else if (wert <= 25) {
            return "gut";
        } else if (wert <= 35) {
            return "befriedigend";
        } else if (wert <= 40) {
            return "ausreichend";
        } else {
            return "nicht ausreichend";
        }
    }
}