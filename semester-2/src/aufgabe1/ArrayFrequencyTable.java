package aufgabe1;

import java.util.Iterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 *
 * @author oliverbittel
 */
public class ArrayFrequencyTable<T> extends AbstractFrequencyTable<T> {

    private int size = 0;
    private Element<T>[] fqTable;
    private final int DEFAULT_SIZE = 100;

    public ArrayFrequencyTable() {
        clear();
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void clear() {
        this.fqTable = (Element<T>[]) new Element[DEFAULT_SIZE];
        this.size = 0;
    }

    @Override
    public void add(T w, int f) {
        if (w == null){
            throw new NullPointerException();
        }
        if (f < 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            if (fqTable[i].getWord().equals(w)) {
                fqTable[i].addFrequency(f);
                resort(i);
                return;
            }
        }
        if (fqTable.length == size) {
            fqTable = Arrays.copyOf(fqTable, size * 2);
        }
        fqTable[size] = new Element<T>(w, f);
        size++;
        resort(size - 1);
    }

    private void resort(int i) {
        while (i > 0 && fqTable[i].getFrequency() > fqTable[i - 1].getFrequency()) {
            Element<T> t = fqTable[i];
            fqTable[i] = fqTable[i - 1];
            fqTable[i - 1] = t;
            i--;
        }
    }

    @Override
    public Element<T> get(int pos) {
        // Ihr Code:
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        return  fqTable[pos];
    }

    @Override
    public int get(T w) {
        // Ihr Code:
        if (w == null) {
            return 0;
        }
        for (int i = 0; i < size; i++) {
            if (fqTable[i].getWord().equals(w)) {
                return fqTable[i].getFrequency();
            }
        }
        return 0;
    }

    @Override
    public Iterator<Element<T>> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public Element<T> next() {
                if (!hasNext()) throw new NoSuchElementException();
                return fqTable[cursor++];
            }
        };
    }
}
