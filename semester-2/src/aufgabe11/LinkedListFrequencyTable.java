/**package aufgabe11;

//public class LinkedListFrequencyTable<T> extends AbstractFrequencyTable<T> {

    class Node<T> {
        Node<T> next;
        Node<T> prev;
        Word<T> data;

        Node(T x, Node n, Node p) {
            data = x;
            next = n;
            prev = p;
        }
    }

    private int size;
    private Node begin;
    private Node end;

    public LinkedListFrequencyTable() {
        clear();
    }

    @Override
    public final void clear() {
        // Ihr Code:
        begin = new Node(null, null, null);
        end = new Node(null, null, begin);
        begin.next = end;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(String w, int f) {
        // throw muss auskommentiert werden!
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Ihr Code:
        if (w == null) {
            throw new NullPointerException();
        }
        if (f <= 0) {
            throw new IllegalArgumentException();
        }

        Node p = begin.next;
        while (p != end) {
            if (p.data.getWord().equals(w)) {
                p.data.addFrequency(f);
                resort(p);
                return;
            }
            p = p.next;
        }

        Node r = new Node(new Word(w, f), end, end.prev);
        r.prev.next = r;
        end.prev = r;
        size++;
        resort(r);
    }

    public void resort(Node p) {
        while (p.prev != begin && p.data.getFrequency() > p.prev.data.getFrequency()) {
            Word t = p.data;
            p.data = p.prev.data;
            p.prev.data = t;
            p = p.prev;
        }
    }

    @Override
    public Word get(int pos) {
        // throw muss auskommentiert werden!
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Ihr Code:
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException(); // Заменили return null
        }
        Node p = begin.next;
        for (int i = 0; i < pos; i++) {
            p = p.next;
        }

        return p.data;
    }

    @Override
    public int get(String w) {
        // throw muss auskommentiert werden!
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Ihr Code:
        // ...
        if (w == null) {
            return 0;
        }
        Node p = begin.next;
        while (p != end) {
            if (p.data.getWord().equals(w)) {
                return p.data.getFrequency();
            }
            p = p.next;
        }
        return 0;
    }
}
 **/