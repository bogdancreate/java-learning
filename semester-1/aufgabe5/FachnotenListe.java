package aufgabe5;
import java.util.NoSuchElementException;
/**
 * FachnotenListe.
 */
public class FachnotenListe {
    /**
     * Erzeugt eine leere FachnotenListe.
     */
    public FachnotenListe() {
    }
    /**
     * Das erste Element der Liste.
     */
    private Element head = null;
    /**
     * Returnt Element der Liste.
     * @return head
     */
    public Element head() {
        return head;
    }
    /**
     * Fügt neue Fachnote am Anfang der Liste ein.
     * @param n die einzufügende Fachnote.
     * @return Liste
     */
    public FachnotenListe insert(Fachnote n) {
        this.head = new Element(this.head, n);
        return this;
    }

    static final class Element {
    /**
     * Das nächste Element in der Liste.
     * Null, wenn es das letzte Element ist.
     */
        private final Element next;
    /**
     * Gespeicherte Fachnote.
     */
        private final Fachnote n;
    /**
     * Konstruktor für ein neues Element der Liste.
     * @param e Nachfolgende Element
     * @param n Note
     * @return
     */
        Element(Element e, Fachnote n) {
            this.next = e;
            this.n = n;
        }
        public Fachnote fachnote() {
            return this.n;
        }
        public Element next() {
            return this.next;
        }
    }
    /**
     * Iterator für die FachnotenListe.
     */
    public final class Iterator {
        /**
         * Erzeugt einen neuen Iterator.
         */
        public Iterator() {
        }
        // private IntList IntList.this;
        private Element current = FachnotenListe.this.head;

        /**
         * pr&uuml;t, ob das Listenende erreicht ist.
         * Der heimliche Parameter this verweist auf das Objekt des Aufrufs.
         * @return false, wenn das Listenende erreicht ist, sonst true.
         */
        public boolean hasNext(/* final Iterator this */) {
            return this.current != null;
        }

        /**
         * liefert die aktuelle Zahl und iteriert zum n&auml;chsten Jahr.
         * Aufruf am Listenende liefert NoSuchElementException.
         * Der heimliche Parameter this verweist auf das Objekt des Aufrufs.
         * @return die aktuelle Zahl
         */
        public Fachnote next(/* final Iterator this */) {
            if (this.current == null) {
                throw new NoSuchElementException();
            }

            Element e = this.current;
            this.current = this.current.next;
            return e.n;
        }
    }
}