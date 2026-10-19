// LeistungsListe.java
package aufgabe6;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LeistungsListe kapselt eine Liste von Leistungen.
 * @author Bohdan Cherednychenko
 * @version 20.01.2026
 */
public final class LeistungsListe implements Iterable<Leistung> {
    /**
     * Erzeugt eine leere LeistungsListe.
     */
    public LeistungsListe() {
    }
    // DONE: Listenimplementierung
    /**
     * Repräsentiert ein Element der einfach verketteten Liste.
     * Enthält eine {@link Leistung}
     * sowie eine Referenz auf das nächste Element.
     */
    private static final class Element {
        /**
         * Der gespeicherte Leistungswert.
         */
        private final Leistung n;
        /**
         * Referenz auf das nächste Element der Liste.
         */
        private final Element next;
        /**
         * Erzeugt ein neues Listenelement.
         * @param n die gespeicherte Leistung
         * @param next das nächste Element der Liste
         */
        private Element(Leistung n, Element next) {
            this.n = n;
            this.next = next;
        }

    }
    /**
     * Erstes Element der Liste.
     */
    private Element head = null;
    /**
     * Fügt eine {@link Leistung} am Anfang der Liste ein.
     * @param n die einzufügende Leistung
     */
    public void insert(Leistung n) {
        this.head = new Element(n, this.head);
    }
    /**
     * Liefert einen Iterator zum Durchlaufen der LeistungsListe.
     * @return ein {@link Iterator} über {@link Leistung}-Objekte
     */
    @Override
    public Iterator<Leistung> iterator() {
        return new Iterator<Leistung>() {
            private Element current = head;
                /**
                 * Prüft, ob weitere Elemente in der Liste vorhanden sind.
                 * @return true falls ein weiteres Element existiert,
                 * sonst false.
                 */
                @Override
                public boolean hasNext() {
                    return this.current != null;
                }
                /**
                 * Liefert das nächste {@link Leistung} objekt der Liste.
                 * @return das nächste {@link Leistung} Objekt
                 * @throws NoSuchElementException falls kein w. El. existiert
                 */
                @Override
                public Leistung next() {
                    if (this.current == null) {
                        throw new NoSuchElementException();
                    }
                    Leistung n = this.current.n;
                    this.current = this.current.next;
                    return n;
                }
        };
    }
}