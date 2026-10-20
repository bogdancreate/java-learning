package aufgabe1;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 *
 * @author bohdancherednychenko
 */
public class LinkedListFrequencyTable<T> extends AbstractFrequencyTable<T> {

    private static class Node<T> {
        private Element<T> word;
        private Node<T> next;
        private Node<T> prev;

        public Node(Element<T> w, Node<T> n, Node<T> p) {
            word = w;
            next = n;
            prev = p;
        }
    }

    private Node<T> begin;
    private Node<T> end;
    private int size;

    public LinkedListFrequencyTable() {
        clear();
    }

    @Override
    public final void clear() {
        begin = new Node<>(null, null, null);
        end = new Node<>(null, null, begin);
        begin.next = end;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T w, int f) {
        if (w == null) {
            throw new NullPointerException();
        }
        if (f < 0) {
            throw new IllegalArgumentException();
        }

        Node<T> p = begin.next;
        while (p != end) {
            if (p.word.getWord().equals(w)) {
                p.word.addFrequency(f);
                moveToLeft(p);
                return;
            }
            p = p.next;
        }

        Node<T> r = new Node<>(new Element<T>(w, f), end, end.prev);
        r.prev.next = r;
        end.prev = r;
        size++;
        moveToLeft(r);
    }

    private void moveToLeft(Node<T> node) {
        while (node.prev != begin && node.word.getFrequency() > node.prev.word.getFrequency()) {
            Element<T> t = node.word;
            node.word = node.prev.word;
            node.prev.word = t;

            node = node.prev;
        }
    }

    @Override
    public Element<T> get(int pos) {
        if (pos < 0 || pos >= size) {
            return null;
        }
        Node<T> p = begin.next;
        for (int i = 0; i < pos; i++) {
            p = p.next;
        }
        return p.word;
    }

    @Override
    public int get(T w) {
        if (w == null) {
            return 0;
        }
        Node<T> p = begin.next;
        while (p != end) {
            if (p.word.getWord().equals(w)) {
                return p.word.getFrequency();
            }
            p = p.next;
        }
        return 0;
    }

    @Override
    public Iterator<Element<T>> iterator() {
        return new Iterator<>() {
            private Node<T> current = begin.next;

            @Override
            public boolean hasNext() {
                return current != end;
            }

            @Override
            public Element<T> next() {
                if (!hasNext()) throw new NoSuchElementException();
                Element<T> data = current.word;
                current = current.next;
                return data;
            }
        };
    }
}