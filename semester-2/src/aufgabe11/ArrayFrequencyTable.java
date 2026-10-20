package aufgabe11;


import java.util.Arrays;

public class ArrayFrequencyTable<T> extends AbstractFrequencyTable<T> {

    private int size = 0;
    private Word[] fqTable;
    private final int DEFAULT_SIZE = 100;

    public ArrayFrequencyTable() {
        clear();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public final void clear() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Ihr Code:
        this.fqTable = (Word<T>[]) new Word[DEFAULT_SIZE];
        this.size = 0;
    }

    @Override
    public void add(T w, int f) {
        // throw muss auskommentiert werden!
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Ihr Code:
        if (w == null){
            throw new NullPointerException();
        }
        if (f < 0){
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
        fqTable[size] = new Word(w, f);
        size++;
        resort(size - 1);
    }

        public void  resort(int i) {
            for (int j = i; j > 0 && fqTable[j].getFrequency() > fqTable[j - 1].getFrequency(); j--) {
                Word t = fqTable[j];
                fqTable[j] = fqTable[j - 1];
                fqTable[j - 1] = t;
            }
        }

        @Override
        public Word get(int pos) {
            // throw muss auskommentiert werden!
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            // Ihr Code:
            return  fqTable[pos];
        }

    @Override
    public int get(T w) {
        // throw muss auskommentiert werden!
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Ihr Code:
        // ...
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
}
